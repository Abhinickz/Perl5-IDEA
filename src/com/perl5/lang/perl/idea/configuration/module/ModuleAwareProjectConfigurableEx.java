/*
 * Copyright 2016 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.idea.configuration.module;

import com.intellij.application.options.ModuleListCellRenderer;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.util.Condition;
import com.intellij.platform.ModuleAttachProcessor;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ListSpeedSearch;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hurricup on 11.09.2016.
 */
public abstract class ModuleAwareProjectConfigurableEx<T extends UnnamedConfigurable> implements SearchableConfigurable,
		Configurable.NoScroll
{
	@NotNull
	private final Project myProject;
	private final String myDisplayName;
	private final String myHelpTopic;
	private final Map<Module, T> myModuleConfigurables = new HashMap<Module, T>();
	private final static String ourProjectItemKey = "*.project";

	public ModuleAwareProjectConfigurableEx(@NotNull Project project, String displayName, String helpTopic)
	{
		myProject = project;
		myDisplayName = displayName;
		myHelpTopic = helpTopic;
	}

	@Nls
	@Override
	public String getDisplayName()
	{
		return myDisplayName;
	}

	@Override
	public String getHelpTopic()
	{
		return myHelpTopic;
	}

	protected boolean isSuitableForModule(@NotNull Module module)
	{
		return true;
	}

	@Override
	public JComponent createComponent()
	{
		if (myProject.isDefault())
		{
			T configurable = createDefaultProjectConfigurable();
			if (configurable != null)
			{
				myModuleConfigurables.put(null, configurable);
				return configurable.createComponent();
			}
		}
		final List<Module> modules = ContainerUtil.filter(ModuleAttachProcessor.getSortedModules(myProject), new Condition<Module>()
		{
			@Override
			public boolean value(Module module)
			{
				return isSuitableForModule(module);
			}
		});

		final T projectConfigurable = createProjectConfigurable();

		if (modules.size() == 1 && projectConfigurable == null)
		{
			Module module = modules.get(0);
			final T configurable = createModuleConfigurable(module);
			myModuleConfigurables.put(module, configurable);
			return configurable.createComponent();
		}
		final Splitter splitter = new Splitter(false, 0.25f);
		@SuppressWarnings("Since15") CollectionListModel<Module> listDataModel = new CollectionListModel<Module>(modules);
		final JBList moduleList = new JBList(listDataModel);
		new ListSpeedSearch(moduleList, new Function<Object, String>()
		{
			@Override
			public String fun(Object o)
			{
				if (o == null)
				{
					return getProjectConfigurableItemName();
				}
				if (o instanceof Module)
				{
					return ((Module) o).getName();
				}
				return null;
			}
		});
		moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		moduleList.setCellRenderer(new ModuleListCellRenderer()
		{
			@Override
			public void customize(JList list, Module module, int index, boolean selected, boolean hasFocus)
			{
				if (module == null)
				{
					setText(getProjectConfigurableItemName());
					setIcon(getProjectConfigurableItemIcon());
				}
				else
				{
					super.customize(list, module, index, selected, hasFocus);
				}
			}
		});
		splitter.setFirstComponent(new JBScrollPane(moduleList));
		final CardLayout layout = new CardLayout();
		final JPanel cardPanel = new JPanel(layout);
		splitter.setSecondComponent(cardPanel);

		if (projectConfigurable != null)
		{
			myModuleConfigurables.put(null, projectConfigurable);
			final JComponent component = projectConfigurable.createComponent();
			cardPanel.add(component, ourProjectItemKey);
			listDataModel.add(0, null);
		}

		for (Module module : modules)
		{
			final T configurable = createModuleConfigurable(module);
			myModuleConfigurables.put(module, configurable);
			final JComponent component = configurable.createComponent();
			cardPanel.add(component, module.getName());
		}
		moduleList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				final Module value = (Module) moduleList.getSelectedValue();
				layout.show(cardPanel, value == null ? ourProjectItemKey : value.getName());
			}
		});
		if (moduleList.getItemsCount() > 0)
		{
			moduleList.setSelectedIndex(0);
			Module module = listDataModel.getElementAt(0);
			layout.show(cardPanel, module == null ? ourProjectItemKey : module.getName());
		}
		return splitter;
	}

	@Nullable
	protected T createDefaultProjectConfigurable()
	{
		return null;
	}

	/**
	 * This configurable is for project-wide settings
	 *
	 * @return configurable or null if none
	 */
	@Nullable
	protected T createProjectConfigurable()
	{
		return null;
	}

	@NotNull
	protected String getProjectConfigurableItemName()
	{
		return "Common";
	}

	@Nullable
	protected Icon getProjectConfigurableItemIcon()
	{
		return null;
	}


	@NotNull
	protected abstract T createModuleConfigurable(Module module);

	@Override
	public boolean isModified()
	{
		for (T configurable : myModuleConfigurables.values())
		{
			if (configurable.isModified())
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void apply() throws ConfigurationException
	{
		for (T configurable : myModuleConfigurables.values())
		{
			configurable.apply();
		}
	}

	@Override
	public void reset()
	{
		for (T configurable : myModuleConfigurables.values())
		{
			configurable.reset();
		}
	}

	@Override
	public void disposeUIResources()
	{
		for (T configurable : myModuleConfigurables.values())
		{
			configurable.disposeUIResources();
		}
		myModuleConfigurables.clear();
	}

	@NotNull
	@Override
	public String getId()
	{
		return getClass().getName();
	}

	@Override
	public Runnable enableSearch(String option)
	{
		return null;
	}

	@NotNull
	protected final Project getProject()
	{
		return myProject;
	}
}

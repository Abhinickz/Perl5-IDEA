/*
 * Copyright 2015 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.run;

import com.intellij.execution.ui.CommonProgramParametersPanel;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.RawCommandLineEditor;
import com.perl5.PerlBundle;
import com.perl5.lang.perl.idea.run.debugger.PerlDebugOptionsSets;
import org.jdesktop.swingx.combobox.MapComboBoxModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @author VISTALL
 * @since 16-Sep-15
 */
public class PerlConfigurationEditor extends SettingsEditor<PerlConfiguration>
{
	private TextFieldWithBrowseButton myScriptField;
	private ComboBox myConsoleCharset;
	private ComboBox myStartMode;
	private CommonProgramParametersPanel myParametersPanel;
	private RawCommandLineEditor myPerlParametersPanel;
	private PerlAlternativeSdkPanel myAlternativeSdkPanel;
	private Project myProject;
	private JTextField myScriptCharset;
	private JCheckBox myIsNonInteractiveModeEnabled;
	private JCheckBox myIsCompileTimeBreakpointsEnabled;

	public PerlConfigurationEditor(Project project)
	{
		myProject = project;
	}

	@Override
	protected void resetEditorFrom(PerlConfiguration perlConfiguration)
	{
		myScriptField.setText(perlConfiguration.getScriptPath());
		myParametersPanel.reset(perlConfiguration);
		myConsoleCharset.setSelectedItem(perlConfiguration.getConsoleCharset());
		myAlternativeSdkPanel.reset(perlConfiguration.getAlternativeSdkPath(), perlConfiguration.isUseAlternativeSdk());
		myPerlParametersPanel.setText(perlConfiguration.getPerlParameters());
		myStartMode.setSelectedItem(perlConfiguration.getStartMode());
		myScriptCharset.setText(perlConfiguration.getScriptCharset());
		myIsCompileTimeBreakpointsEnabled.setSelected(perlConfiguration.isCompileTimeBreakpointsEnabled());
		myIsNonInteractiveModeEnabled.setSelected(perlConfiguration.isNonInteractiveModeEnabled());
	}

	@Override
	protected void applyEditorTo(PerlConfiguration perlConfiguration) throws ConfigurationException
	{
		perlConfiguration.setPerlParameters(myPerlParametersPanel.getText());
		perlConfiguration.setScriptPath(myScriptField.getText());
		myParametersPanel.applyTo(perlConfiguration);
		perlConfiguration.setConsoleCharset(StringUtil.nullize((String) myConsoleCharset.getSelectedItem(), true));
		perlConfiguration.setAlternativeSdkPath(myAlternativeSdkPanel.getPath());
		perlConfiguration.setUseAlternativeSdk(myAlternativeSdkPanel.isPathEnabled());
		perlConfiguration.setStartMode(myStartMode.getSelectedItem().toString());
		perlConfiguration.setScriptCharset(myScriptCharset.getText());
		perlConfiguration.setNonInteractiveModeEnabled(myIsNonInteractiveModeEnabled.isSelected());
		perlConfiguration.setCompileTimeBreakpointsEnabled(myIsCompileTimeBreakpointsEnabled.isSelected());
	}

	@NotNull
	@Override
	protected JComponent createEditor()
	{
		myScriptField = new TextFieldWithBrowseButton();
		myScriptField.addBrowseFolderListener(
				PerlBundle.message("perl.run.config.select.script.header"),
				PerlBundle.message("perl.run.config.select.script.prompt"),
				myProject,
				FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor().withFileFilter(new Condition<VirtualFile>()
		{
			@Override
			public boolean value(VirtualFile virtualFile)
			{
				return PerlConfigurationProducer.isExecutableFile(virtualFile);
			}
		}), TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT);

		//noinspection Since15
		myStartMode = new ComboBox(new MapComboBoxModel<String, String>(PerlDebugOptionsSets.STARTUP_OPTIONS))
		{
			@Override
			public void setRenderer(ListCellRenderer renderer)
			{
				super.setRenderer(new ColoredListCellRenderer<String>()
				{
					@Override
					protected void customizeCellRenderer(JList list, String value, int index, boolean selected, boolean hasFocus)
					{
						append(PerlDebugOptionsSets.STARTUP_OPTIONS.get(value));
					}
				});
			}
		};

		myConsoleCharset = new ComboBox(new CollectionComboBoxModel(new ArrayList<String>(Charset.availableCharsets().keySet())));

		myScriptField.getTextField().getDocument().addDocumentListener(new DocumentAdapter()
		{
			@Override
			protected void textChanged(DocumentEvent documentEvent)
			{
				VirtualFile file = LocalFileSystem.getInstance().findFileByPath(myScriptField.getText());
				if (file != null)
				{
					myConsoleCharset.setSelectedItem(file.getCharset().displayName());
				}
				else
				{
					myConsoleCharset.setSelectedItem(null);
				}
			}
		});

		myAlternativeSdkPanel = new PerlAlternativeSdkPanel();

		myParametersPanel = new CommonProgramParametersPanel()
		{
			@Override
			protected void addComponents()
			{

				LabeledComponent<?> scriptLabel = LabeledComponent.create(myScriptField, PerlBundle.message("perl.run.option.script"));
				scriptLabel.setLabelLocation(BorderLayout.WEST);
				add(scriptLabel);

				LabeledComponent<?> consoleEncoding = LabeledComponent.create(myConsoleCharset, PerlBundle.message("perl.run.option.output.encoding"));
				consoleEncoding.setLabelLocation(BorderLayout.WEST);
				add(consoleEncoding);

				myPerlParametersPanel = new RawCommandLineEditor();
				LabeledComponent<RawCommandLineEditor> perlParametersPanel = LabeledComponent.create(myPerlParametersPanel, PerlBundle.message("perl.run.option.perl.parameters"));
				perlParametersPanel.setLabelLocation(BorderLayout.WEST);
				copyDialogCaption(perlParametersPanel);
				add(perlParametersPanel);

				super.addComponents();
				add(myAlternativeSdkPanel);

				// debugger-related settings
				myScriptCharset = new JTextField();
				LabeledComponent<JTextField> myScriptCharsetLabel = LabeledComponent.create(myScriptCharset, PerlBundle.message("perl.run.option.script.encoding"));
				myScriptCharsetLabel.setLabelLocation(BorderLayout.WEST);
				add(myScriptCharsetLabel);

				LabeledComponent<?> startMode = LabeledComponent.create(myStartMode, PerlBundle.message("perl.run.option.debugger.startup.mode"));
				startMode.setLabelLocation(BorderLayout.WEST);
				add(startMode);

				myIsNonInteractiveModeEnabled = new JCheckBox(PerlBundle.message("perl.run.option.debugger.noninteractive.mode"));
				add(myIsNonInteractiveModeEnabled);

				myIsCompileTimeBreakpointsEnabled = new JCheckBox(PerlBundle.message("perl.run.option.debugger.compile.time.breakpoints"));
				add(myIsCompileTimeBreakpointsEnabled);

			}
		};
		myParametersPanel.setProgramParametersLabel(PerlBundle.message("perl.run.option.script.parameters"));
		return myParametersPanel;
	}
}

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

package com.perl5.lang.perl.idea.run.debugger.ui;

import com.intellij.ide.actions.OpenFileAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.FileColorManager;
import com.intellij.ui.ListCellRendererWrapper;
import com.intellij.ui.SortedListModel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.perl5.lang.perl.idea.run.debugger.PerlDebugThread;
import com.perl5.lang.perl.idea.run.debugger.PerlRemoteFileSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Comparator;

/**
 * Created by hurricup on 14.05.2016.
 */
public class PerlScriptsPanel extends JPanel
{
	@NotNull
	private final Project myProject;
	private final PerlDebugThread myDebugThread;
	private SortedListModel<String> myModel = SortedListModel.create(new Comparator<String>()
	{
		@Override
		public int compare(String o1, String o2)
		{
			return StringUtil.compare(o1, o2, false);
		}
	});

	public PerlScriptsPanel(@NotNull Project project, PerlDebugThread debugThread)
	{
		super(new BorderLayout());
		init();
		myProject = project;
		myDebugThread = debugThread;
	}


	@Nullable
	private VirtualFile getVirtualFileByName(String virtualFileName)
	{
		VirtualFile result = VfsUtil.findFileByIoFile(new File(virtualFileName), true);

		if (result != null)
			return result;

		return VirtualFileManager.getInstance().findFileByUrl(PerlRemoteFileSystem.PROTOCOL_PREFIX + virtualFileName);
	}

	private void init()
	{
		final JBList jbList = new JBList(myModel);
		jbList.setCellRenderer(new ListCellRendererWrapper<String>()
		{
			@Override
			public void customize(JList list, String virtualFileName, int index, boolean selected, boolean hasFocus)
			{
				VirtualFile virtualFile = getVirtualFileByName(virtualFileName);

				if (virtualFile == null)
				{
					setText(virtualFileName);
				}
				else
				{
					setBackground(FileColorManager.getInstance(myProject).getFileColor(virtualFile));
					setText(virtualFile.getPath());
					setIcon(virtualFile.getFileType().getIcon());
				}
			}
		});
		jbList.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
				{
					String filePath = (String) jbList.getSelectedValue();
					VirtualFile selectedVirtualFile = getVirtualFileByName(filePath);
					if (selectedVirtualFile == null)
					{
						selectedVirtualFile = myDebugThread.loadRemoteSource(filePath);
					}

					if (selectedVirtualFile != null)
					{
						OpenFileAction.openFile(selectedVirtualFile, myProject);
					}
				}
			}
		});

		add(new JBScrollPane(jbList), BorderLayout.CENTER);
	}


	public void add(final String value)
	{
		if (!myModel.getItems().contains(value))
		{
			ApplicationManager.getApplication().invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					myModel.add(value);
				}
			});
		}
	}

	public void remove(final String value)
	{
		if (myModel.getItems().contains(value))
		{
			ApplicationManager.getApplication().invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					myModel.remove(value);
				}
			});
		}
	}
}
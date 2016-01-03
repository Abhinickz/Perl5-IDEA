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

package com.perl5.lang.mason.idea.editor.notification;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.IdeaProjectSettingsService;
import com.intellij.openapi.roots.ui.configuration.ProjectSettingsService;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.EditorNotifications;
import com.perl5.lang.mason.filetypes.MasonPurePerlComponentFileType;
import com.perl5.lang.mason.idea.configuration.MasonPerlSettings;
import com.perl5.lang.mason.idea.configuration.MasonPerlSettingsConfigurable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 03.01.2016.
 */
public class MasonPathsNotification extends EditorNotifications.Provider<EditorNotificationPanel> implements DumbAware
{
	private static final Key<EditorNotificationPanel> KEY = Key.create("perl.mason.roots.not.set.panel");
	private final Project myProject;

	public MasonPathsNotification(Project myProject)
	{
		this.myProject = myProject;
	}

	@NotNull
	@Override
	public Key<EditorNotificationPanel> getKey()
	{
		return KEY;
	}

	@Nullable
	@Override
	public EditorNotificationPanel createNotificationPanel(@NotNull final VirtualFile file, @NotNull FileEditor fileEditor)
	{
		if (file.getFileType() instanceof MasonPurePerlComponentFileType && MasonPerlSettings.getInstance(myProject).componentRoots.size() == 0)
		{
			EditorNotificationPanel panel = new EditorNotificationPanel();
			panel.setText("Mason components roots are not configured");
			panel.createActionLabel("Configure", new Runnable()
			{
				@Override
				public void run()
				{
					ShowSettingsUtil.getInstance().editConfigurable(myProject, new MasonPerlSettingsConfigurable(myProject, "Mason Settings"));
					EditorNotifications.getInstance(myProject).updateNotifications(file);
				}
			});
			return panel;
		}

		return null;
	}


}

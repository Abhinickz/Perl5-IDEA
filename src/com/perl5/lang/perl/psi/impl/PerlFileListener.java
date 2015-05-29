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

package com.perl5.lang.perl.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.*;
import com.perl5.lang.perl.idea.refactoring.RenameRefactoringQueue;
import com.perl5.lang.perl.util.PerlPackageUtil;
import com.perl5.lang.perl.util.PerlUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 29.05.2015.
 */
public class PerlFileListener implements VirtualFileListener
{
	Project myProject;
	ProjectFileIndex myProjectFileIndex;

	public PerlFileListener(Project project)
	{
		myProject = project;
		myProjectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
	}

	@Override
	public void propertyChanged(@NotNull VirtualFilePropertyEvent event)
	{
		VirtualFile virtualFile = event.getFile();
		if( myProjectFileIndex.isInSource(virtualFile) )
		{
			if( "name".equals(event.getPropertyName()) && virtualFile.isDirectory())
			{
				String oldPath = virtualFile.getPath().replaceFirst(event.getNewValue().toString()+"$", event.getOldValue().toString());
				RenameRefactoringQueue queue = new RenameRefactoringQueue(myProject);
				PerlPackageUtil.handlePackagePathChange(queue,virtualFile,oldPath);
				queue.run();
			}
		}
	}

	@Override
	public void contentsChanged(@NotNull VirtualFileEvent event)
	{
//		System.out.println("contentsChanged " + event);

	}

	@Override
	public void fileCreated(@NotNull VirtualFileEvent event)
	{
//		System.out.println("fileCreated " + event);
	}

	@Override
	public void fileDeleted(@NotNull VirtualFileEvent event)
	{
//		System.out.println("fileDeleted " + event);
	}

	@Override
	public void fileMoved(@NotNull VirtualFileMoveEvent event)
	{
		if( myProjectFileIndex.isInSource(event.getNewParent()) )
		{
			VirtualFile movedFile = event.getFile();

			if( "pm".equals(movedFile.getExtension()) )
			{
				VirtualFile file = event.getFile();
				RenameRefactoringQueue queue = new RenameRefactoringQueue(myProject);
				PerlPackageUtil.adjustMovedFileNamespaces(queue, file, event.getOldParent().getPath() + '/' + file.getName());
				queue.run();
			}
		}
	}

	@Override
	public void fileCopied(@NotNull VirtualFileCopyEvent event)
	{
//		System.out.println("fileCopied " + event);
	}

	@Override
	public void beforePropertyChange(@NotNull VirtualFilePropertyEvent event)
	{

	}

	@Override
	public void beforeContentsChange(@NotNull VirtualFileEvent event)
	{

	}

	@Override
	public void beforeFileDeletion(@NotNull VirtualFileEvent event)
	{

	}

	@Override
	public void beforeFileMovement(@NotNull VirtualFileMoveEvent event)
	{

	}
}

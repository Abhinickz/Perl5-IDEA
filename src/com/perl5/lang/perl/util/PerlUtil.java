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

package com.perl5.lang.perl.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.IncorrectOperationException;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by hurricup on 27.05.2015.
 * Misc helper methods
 */
public class PerlUtil
{

	/**
	 * Searches for innermost source root for a file
	 *
	 * @param project project to search in
	 * @param file    containing file
	 * @return innermost root
	 */
	public static VirtualFile getFileClassRoot(Project project, VirtualFile file)
	{
		Module module = ModuleUtil.findModuleForFile(file, project);

		if (module != null)
		{
			for (VirtualFile classRoot : ModuleRootManager.getInstance(module).orderEntries().classes().getRoots())
				if (VfsUtil.isAncestor(classRoot, file, false))
					return classRoot;
		} else
			throw new IncorrectOperationException("Unable to find class root for file outside of the modules");

		return null;
	}

	/**
	 * Searches for innermost source root for a file by it's absolute path
	 *
	 * @param module   module to search in
	 * @param filePath containing filename
	 * @return innermost root
	 */
	public static VirtualFile getFileClassRoot(Module module, String filePath)
	{
		if (module != null)
		{
			File file = new File(filePath);

			for (VirtualFile classRoot : ModuleRootManager.getInstance(module).orderEntries().classes().getRoots())
			{
				File sourceRootFile = new File(classRoot.getPath());
				if (VfsUtil.isAncestor(sourceRootFile, file, false))
					return classRoot;
			}
		}

		return null;
	}

	public static Collection<String> filterInternalNames(Collection<String> unfilteredNames)
	{
		HashSet<String> result = new HashSet<String>();
		for (String name : unfilteredNames)
			if (name.charAt(0) != '*' && !result.contains(name))
				result.add(name);
		return result;
	}

}

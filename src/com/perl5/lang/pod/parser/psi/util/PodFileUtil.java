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

package com.perl5.lang.pod.parser.psi.util;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.perl5.lang.perl.fileTypes.PerlFileTypePackage;
import com.perl5.lang.perl.util.PerlPackageUtil;
import com.perl5.lang.perl.util.PerlUtil;
import com.perl5.lang.pod.filetypes.PodFileType;
import com.perl5.lang.pod.parser.psi.PodFile;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by hurricup on 27.03.2016.
 */
public class PodFileUtil
{
	private static final Set<String> myClassLikeExtensions = new THashSet<String>(Arrays.asList(
			PodFileType.EXTENSION,
			PerlFileTypePackage.EXTENSION
	));

	@Nullable
	public static String getPackageName(PodFile file)
	{
		VirtualFile virtualFile = file.getVirtualFile();
		VirtualFile classRoot = PerlUtil.getFileClassRoot(file);

		if (virtualFile != null && myClassLikeExtensions.contains(virtualFile.getExtension()))
		{
			if (classRoot == null)
			{
				return virtualFile.getName();
			}
			else
			{
				String relativePath = VfsUtil.getRelativePath(virtualFile, classRoot);
				if (relativePath != null)
				{
					return StringUtil.join(relativePath.replaceAll(".(" + PodFileType.EXTENSION + "|" + PerlFileTypePackage.EXTENSION + ")$", "").split("/"), PerlPackageUtil.PACKAGE_SEPARATOR);
				}
			}
		}
		return null;
	}

	public static String getFilenameFromPackage(@NotNull String packageName)
	{
		return StringUtil.join(PerlPackageUtil.getCanonicalPackageName(packageName).split(PerlPackageUtil.PACKAGE_SEPARATOR), "/") + "." + PodFileType.EXTENSION;
	}
}

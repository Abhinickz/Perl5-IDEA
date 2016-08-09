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

package com.perl5.lang.perl.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NullableLazyValue;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.perl5.compat.PerlStubIndex;
import com.perl5.lang.ea.psi.PerlExternalAnnotationDeclaration;
import com.perl5.lang.ea.psi.PerlExternalAnnotationNamespace;
import com.perl5.lang.ea.psi.stubs.PerlExternalAnnotationDeclarationStubIndex;
import com.perl5.lang.ea.psi.stubs.PerlExternalAnnotationNamespaceStubIndex;
import com.perl5.lang.perl.PerlScopes;
import com.perl5.lang.perl.psi.PerlNamespaceDefinition;
import com.perl5.lang.perl.psi.PerlSubBase;
import com.perl5.lang.perl.psi.utils.PerlNamespaceAnnotations;
import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Created by hurricup on 09.08.2016.
 */
public class PerlAnnotationsUtil
{
	private static NullableLazyValue<VirtualFile> myLazyAnnotationsRootVirtualFile = new NullableLazyValue<VirtualFile>()
	{
		@Nullable
		@Override
		protected VirtualFile compute()
		{
			String annotaionsRoot = getPluginAnnotationsRoot();
			return annotaionsRoot == null ? null : VfsUtil.findFileByIoFile(new File(annotaionsRoot), true);
		}
	};

	@Nullable
	public static String getPluginAnnotationsRoot()
	{
		String pluginRoot = PerlPluginUtil.getPluginRoot();
		return pluginRoot == null ? null : pluginRoot + "/annotations";
	}

	@Nullable
	public static VirtualFile getPluginAnnotationsRootVirtualFile()
	{
		return myLazyAnnotationsRootVirtualFile.getValue();
	}

	@Nullable
	public static PerlNamespaceAnnotations getExternalAnnotations(@NotNull PerlNamespaceDefinition namespaceDefinition)
	{
		String canonicalName = namespaceDefinition.getPackageName();

		if (StringUtil.isNotEmpty(canonicalName))
		{
			Project project = namespaceDefinition.getProject();
			for (PerlExternalAnnotationNamespace declaration : PerlStubIndex.getElements(
					PerlExternalAnnotationNamespaceStubIndex.KEY,
					canonicalName,
					project,
					PerlScopes.getProjectAndLibrariesScope(project),
					PerlExternalAnnotationNamespace.class
			))
			{
				return declaration.getAnnotations();
			}
		}
		return null;
	}

	@Nullable
	public static PerlSubAnnotations getExternalAnnotations(@NotNull PerlSubBase subBase)
	{
		Project project = subBase.getProject();
		String canonicalName = subBase.getCanonicalName();

		if (StringUtil.isNotEmpty(canonicalName))
		{
			for (PerlExternalAnnotationDeclaration declaration : PerlStubIndex.getElements(
					PerlExternalAnnotationDeclarationStubIndex.KEY,
					canonicalName,
					project,
					PerlScopes.getProjectAndLibrariesScope(project),
					PerlExternalAnnotationDeclaration.class
			))
			{
				return declaration.getSubAnnotations();
			}
		}

		return null;
	}

}

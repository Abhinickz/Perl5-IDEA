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

package com.perl5.lang.perl.psi.references;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveResult;
import com.perl5.lang.perl.psi.PerlNamespace;
import com.perl5.lang.perl.psi.PerlNamespaceDefinition;
import com.perl5.lang.perl.psi.PerlPerlGlob;
import com.perl5.lang.perl.psi.PerlSubDefinition;
import com.perl5.lang.perl.util.PerlFunctionUtil;
import com.perl5.lang.perl.util.PerlGlobUtil;
import com.perl5.lang.perl.util.PerlPackageUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hurricup on 28.05.2015.
 */
public class PerlNamespaceReference extends PerlReferencePoly
{
	private final String packageName;

	public PerlNamespaceReference(@NotNull PsiElement element, TextRange textRange)
	{
		super(element, textRange);
		assert element instanceof PerlNamespace;
		packageName = ((PerlNamespace) element).getName();
	}

	@NotNull
	@Override
	public Object[] getVariants()
	{
		return new Object[0];
	}

	@NotNull
	@Override
	public ResolveResult[] multiResolve(boolean incompleteCode)
	{
		Project project = myElement.getProject();
		List<ResolveResult> result = new ArrayList<ResolveResult>();

		PsiElement parent = myElement.getParent();

		if( parent instanceof PerlNamespaceDefinition )
		{
			// resolves to a psi file
			assert myElement instanceof PerlNamespace;
			String properPath = PerlPackageUtil.getPackagePathName(((PerlNamespace) myElement).getName());
			PsiFile file = myElement.getContainingFile();

			if( file.getVirtualFile().getPath().endsWith(properPath))
			{
				result.add(new PsiElementResolveResult(file));
			}
		}
		else
		{
			// defined namespaces
			for (PerlNamespaceDefinition ns : PerlPackageUtil.findNamespaceDefinitions(project, packageName))
			{
				result.add(new PsiElementResolveResult(ns.getNamespace()));
			}
		}

		return result.toArray(new ResolveResult[result.size()]);
	}

	@Nullable
	@Override
	public PsiElement resolve()
	{
		ResolveResult[] resolveResults = multiResolve(false);
		return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
	}
}

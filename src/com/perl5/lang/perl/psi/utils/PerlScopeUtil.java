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

package com.perl5.lang.perl.psi.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.perl5.lang.perl.extensions.PerlImplicitVariablesProvider;
import com.perl5.lang.perl.psi.PerlCompositeElement;
import com.perl5.lang.perl.psi.PerlVariableDeclarationWrapper;
import com.perl5.lang.perl.psi.impl.PerlFileImpl;
import com.perl5.lang.perl.psi.properties.PerlLexicalScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 17.02.2016.
 */
public class PerlScopeUtil
{
	public static boolean treeWalkUp(@Nullable PsiElement place, @NotNull PsiScopeProcessor processor)
	{
		PsiElement lastParent = null;
		PsiElement run = place;
		ResolveState state = ResolveState.initial();
		while (run != null)
		{
			if (place != run && !run.processDeclarations(processor, state, lastParent, place))
			{
				return false;
			}
			lastParent = run;

			if (run instanceof PsiFile)
			{
				if (run instanceof PerlFileImpl)
				{
					run = ((PerlFileImpl) ((PerlFileImpl) run).getOriginalFile()).getFileContext();
				}
				else
				{
					run = null;
				}
			}
			else
			{
				run = run.getParent();
			}
		}
		return true;
	}

	public static boolean processChildren(@NotNull PsiElement element,
										  @NotNull PsiScopeProcessor processor,
										  @NotNull ResolveState resolveState,
										  @Nullable PsiElement lastParent,
										  @NotNull PsiElement place)
	{
		PsiElement run = lastParent == null ? element.getLastChild() : lastParent.getPrevSibling();
		while (run != null)
		{
			if (run instanceof PerlCompositeElement &&
					!(run instanceof PerlLexicalScope) &&
					!run.processDeclarations(processor, resolveState, null, place)
					)
				return false;
			run = run.getPrevSibling();
		}

		// checking implicit variables
		if (element instanceof PerlImplicitVariablesProvider)
		{
			for (PerlVariableDeclarationWrapper wrapper : ((PerlImplicitVariablesProvider) element).getImplicitVariables())
			{
				if (!processor.execute(wrapper, resolveState))
					return false;
			}
		}

		return true;
	}
}

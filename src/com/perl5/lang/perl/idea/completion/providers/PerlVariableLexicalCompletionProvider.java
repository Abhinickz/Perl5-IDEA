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

package com.perl5.lang.perl.idea.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ProcessingContext;
import com.perl5.lang.perl.idea.completion.util.PerlVariableCompletionUtil;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.properties.PerlNamespaceElementContainer;
import com.perl5.lang.perl.psi.references.scopes.PerlVariableScopeProcessor;
import com.perl5.lang.perl.psi.utils.PerlScopeUtil;
import com.perl5.lang.perl.psi.utils.PerlVariableType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 07.06.2015.
 */
public class PerlVariableLexicalCompletionProvider extends CompletionProvider<CompletionParameters>
{

	public void addCompletions(@NotNull CompletionParameters parameters,
							   ProcessingContext context,
							   @NotNull final CompletionResultSet resultSet)
	{
		PsiElement variableNameElement = parameters.getOriginalPosition();

		if (variableNameElement == null)
			variableNameElement = parameters.getPosition();

		if (!(variableNameElement instanceof PerlVariableNameElement))
			return;
//		assert variableNameElement instanceof PerlVariableNameElement : "Got type " + variableNameElement.getClass();

		final PsiElement perlVariable = variableNameElement.getParent();

		// fixme move this to pattern
		if (perlVariable instanceof PerlNamespaceElementContainer && ((PerlNamespaceElementContainer) perlVariable).getNamespaceElement() != null)
			return;


		PsiScopeProcessor processor = new PerlVariableScopeProcessor()
		{
			@Override
			public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state)
			{
				PerlVariableDeclarationWrapper variable = (PerlVariableDeclarationWrapper) element;

				if (perlVariable instanceof PsiPerlScalarVariable)
				{
					String variableName = variable.getName();
					if (variableName != null)
					{
						if (variable.getActualType() == PerlVariableType.SCALAR)
						{
							resultSet.addElement(PerlVariableCompletionUtil.getScalarLookupElement(variableName));
						}
						else if (variable.getActualType() == PerlVariableType.ARRAY)
						{
							resultSet.addElement(PerlVariableCompletionUtil.getArrayElementLookupElement(variableName));
						}
						else if (variable.getActualType() == PerlVariableType.HASH)
						{
							resultSet.addElement(PerlVariableCompletionUtil.getHashElementLookupElement(variableName));
						}
					}
				}
				else if (perlVariable instanceof PsiPerlArrayVariable)
				{
					String variableName = variable.getName();
					if (variableName != null)
					{
						if (variable.getActualType() == PerlVariableType.ARRAY)
						{
							resultSet.addElement(PerlVariableCompletionUtil.getArrayLookupElement(variableName));

						}
						else if (variable.getActualType() == PerlVariableType.HASH)
						{
							resultSet.addElement(PerlVariableCompletionUtil.getHashSliceElementLookupElement(variableName));
						}
					}
				}
				else if (perlVariable instanceof PsiPerlArrayIndexVariable)
				{
					String variableName = variable.getName();
					if (variableName != null)
					{
						if (variable.getActualType() == PerlVariableType.ARRAY)
						{
							resultSet.addElement(PerlVariableCompletionUtil.getArrayLookupElement(variableName));
						}
					}
				}
				else if (perlVariable instanceof PsiPerlHashVariable)
				{
					String variableName = variable.getName();
					if (variableName != null)
					{
						if (variable.getActualType() == PerlVariableType.HASH)
						{
							resultSet.addElement(PerlVariableCompletionUtil.getHashLookupElement(variableName));
						}
					}
				}

				return true;
			}
		};
		PerlScopeUtil.treeWalkUp(variableNameElement, processor);

	}

}

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

package com.perl5.lang.perl.idea.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.perl5.lang.perl.psi.PerlVariable;
import com.perl5.lang.perl.psi.PerlVariableDeclarationWrapper;
import com.perl5.lang.perl.psi.PerlVariableNameElement;
import com.perl5.lang.perl.psi.PerlVisitor;
import com.perl5.lang.perl.psi.references.PerlPolyVariantReference;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 13.06.2015.
 */
public class PerlVariableUnresolvableInspection extends PerlInspection
{
	@NotNull
	@Override
	public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly)
	{
		return new PerlVisitor()
		{
			@Override
			public void visitPerlVariable(@NotNull PerlVariable element)
			{
				PsiElement parent = element.getParent();

				if (parent instanceof PerlVariableDeclarationWrapper || element.isBuiltIn())
					return;

				PerlVariableNameElement variableNameElement = element.getVariableNameElement();

				if (variableNameElement != null)
				{
					for (PsiReference reference : variableNameElement.getReferences())
						if (reference instanceof PerlPolyVariantReference && ((PerlPolyVariantReference) reference).multiResolve(false).length > 0
								|| reference.resolve() != null
								)
						{
							return;
						}

					registerProblem(holder, variableNameElement, "Unable to find variable declaration.");
				}
			}
		};
	}
}

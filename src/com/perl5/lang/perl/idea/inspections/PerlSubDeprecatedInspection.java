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
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by hurricup on 14.06.2015.
 */
public class PerlSubDeprecatedInspection extends PerlInspection
{
	@NotNull
	@Override
	public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly)
	{
		return new PerlVisitor()
		{
			@Override
			public void visitSubNameElement(@NotNull PerlSubNameElement o)
			{
				PsiElement container = o.getParent();

				if( container instanceof PerlSubDefinition && ((PerlSubDefinition) container).getSubAnnotations().isDeprecated())
					markDeprecated(holder, o, "This sub defined as deprecated");
				else if( container instanceof PerlSubDeclaration && ((PerlSubDeclaration) container).getSubAnnotations().isDeprecated())
					markDeprecated(holder, o, "This sub declared as deprecated");
				else
				{
					for (PerlSubDefinition subDefinition : o.getSubDefinitions())
					{
						if (subDefinition.getSubAnnotations().isDeprecated())
						{
							markDeprecated(holder, o, "This sub defined as deprecated");
							return;
						}
					}
					for (PerlSubDeclaration subDeclaration : o.getSubDeclarations())
					{
						if (subDeclaration.getSubAnnotations().isDeprecated())
						{
							markDeprecated(holder, o, "This sub declared as deprecated");
							return;
						}
					}
				}
			}
		};
	}

}

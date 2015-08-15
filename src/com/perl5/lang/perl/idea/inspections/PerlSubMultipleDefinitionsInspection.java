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
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementVisitor;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.util.PerlGlobUtil;
import com.perl5.lang.perl.util.PerlSubUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 14.06.2015.
 */
public class PerlSubMultipleDefinitionsInspection extends PerlInspection
{
	@NotNull
	@Override
	public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly)
	{
		return new PerlVisitor()
		{
			@Override
			public void visitSubDefinition(@NotNull PsiPerlSubDefinition o)
			{
				Project project = o.getProject();

				String canonicalName = o.getCanonicalName();
				if (PerlSubUtil.getSubDefinitions(project, canonicalName).size() > 1)
					if (!"main".equals(o.getPackageName()))
						registerProblem(holder, o.getNameIdentifier(), "Multiple subs definitions found");

				for (PerlGlobVariable target : PerlGlobUtil.getGlobsDefinitions(project, canonicalName))
					if (target.isLeftSideOfAssignment())
					{
						registerProblem(holder, o.getNameIdentifier(), "Sub definition clashes with typeglob alias");
						break;
					}

				if (PerlSubUtil.getConstantsDefinitions(project, canonicalName).size() > 0)
					registerProblem(holder, o.getNameIdentifier(), "Sub definition clashes with constant definition");
			}

			@Override
			public void visitGlobVariable(@NotNull PsiPerlGlobVariable o)
			{
				if (o.getParent() instanceof PsiPerlAssignExpr && o.getNextSibling() != null)
				{

					Project project = o.getProject();

					String canonicalName = o.getCanonicalName();

					if (PerlSubUtil.getSubDefinitions(project, canonicalName).size() > 0)
						registerProblem(holder, o.getNameIdentifier(), "Typeglob clashes with sub definition");

					int globsNumber = 0;
					for (PerlGlobVariable target : PerlGlobUtil.getGlobsDefinitions(project, canonicalName))
						if (target.isLeftSideOfAssignment() && ++globsNumber > 1)
						{
							registerProblem(holder, o.getNameIdentifier(), "Multiple typeglob aliases found");
							break;
						}

					if (PerlSubUtil.getConstantsDefinitions(project, canonicalName).size() > 0)
						registerProblem(holder, o.getNameIdentifier(), "Typeglob clashes with constant definition");
				}
			}

			@Override
			public void visitPerlConstant(@NotNull PerlConstant o)
			{
				Project project = o.getProject();

				String canonicalName = o.getCanonicalName();
				if (PerlSubUtil.getSubDefinitions(project, canonicalName).size() > 0)
					registerProblem(holder, o.getNameIdentifier(), "Constant clashes with sub definition");

				for (PerlGlobVariable target : PerlGlobUtil.getGlobsDefinitions(project, canonicalName))
					if (target.isLeftSideOfAssignment())
					{
						registerProblem(holder, o.getNameIdentifier(), "Constant clashes with with typeglob alias");
						break;
					}

				if (PerlSubUtil.getConstantsDefinitions(project, canonicalName).size() > 1)
					registerProblem(holder, o.getNameIdentifier(), "Multiple constants definitions found");
			}
		};
	}

}

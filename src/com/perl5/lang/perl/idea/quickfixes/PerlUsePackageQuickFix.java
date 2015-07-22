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

package com.perl5.lang.perl.idea.quickfixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.perl.psi.PerlUseStatement;
import com.perl5.lang.perl.psi.PsiPerlNamespaceBlock;
import com.perl5.lang.perl.psi.utils.PerlElementFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 19.07.2015.
 */
public class PerlUsePackageQuickFix implements LocalQuickFix
{
	String myPackageName;

	public PerlUsePackageQuickFix(String packageName)
	{
		myPackageName = packageName;
	}

	@Nls
	@NotNull
	@Override
	public String getName()
	{
		return String.format("Add use %s;", myPackageName);
	}

	@NotNull
	@Override
	public String getFamilyName()
	{
		return "Add use statement";
	}

	@Override
	public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor)
	{
		PerlUseStatement newStatement = PerlElementFactory.createUseStatement(project, myPackageName);
		PsiElement statementContainer = descriptor.getPsiElement();

		PsiElement baseStatement = PsiTreeUtil.findChildOfType(statementContainer, PerlUseStatement.class);
		if (baseStatement != null)
		{
			statementContainer = baseStatement.getParent();
			if (((PerlUseStatement) baseStatement).isPragmaOrVersion()) // pragma or version
				while (true)
				{
					// trying to find next use statement
					PsiElement nextStatement = baseStatement;

					while ((nextStatement = nextStatement.getNextSibling()) != null
							&& (nextStatement instanceof PsiWhiteSpace || nextStatement instanceof PsiComment)
							)
					{
					}

					if (nextStatement instanceof PerlUseStatement && ((PerlUseStatement) nextStatement).isPragmaOrVersion())    // found more use pragma/version
						baseStatement = nextStatement;
					else
						break;    // we've got last pragma statement
				}
			else    // not a pragma
			{
				baseStatement = baseStatement.getPrevSibling();

				// moving up to previous statement or beginning of the block
				while (baseStatement != null && (baseStatement instanceof PsiWhiteSpace || baseStatement instanceof PsiComment))
					baseStatement = baseStatement.getPrevSibling();

				// got last statement before use ...
			}
		} else    // no uses found
		{
			baseStatement = PsiTreeUtil.findChildOfType(statementContainer, PsiPerlNamespaceBlock.class);
			if (baseStatement != null)    // got a namespace block
			{
				statementContainer = baseStatement;
				baseStatement = null;
			} else
			{
				baseStatement = statementContainer.getFirstChild();
				if (!(baseStatement instanceof PsiComment && baseStatement.getText().startsWith("#!")))    // not a shebang
					baseStatement = null;
			}
		}

		PsiElement newLineElement = PerlElementFactory.createNewLine(project);

		if (baseStatement != null) // add after element
			statementContainer.addBefore(newLineElement,
					statementContainer.addAfter(newStatement, baseStatement));
		else if (statementContainer.getFirstChild() != null)   // add as first element of the file
		{
			statementContainer.addAfter(newLineElement,
					statementContainer.addBefore(newStatement, statementContainer.getFirstChild()));
		} else    // just add
		{
			statementContainer.add(newStatement);
			statementContainer.add(newLineElement);
		}
	}
}

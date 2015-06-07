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
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import com.perl5.PerlIcons;
import com.perl5.lang.perl.idea.completion.PerlInsertHandlers;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.impl.PerlFileElement;
import com.perl5.lang.perl.util.PerlArrayUtil;
import com.perl5.lang.perl.util.PerlHashUtil;
import com.perl5.lang.perl.util.PerlScalarUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Created by hurricup on 01.06.2015.
 *
 */
public class PerlVariableGlobalCompletionProvider extends CompletionProvider<CompletionParameters>
{
	public void addCompletions(@NotNull final CompletionParameters parameters,
							   ProcessingContext context,
							   @NotNull final CompletionResultSet resultSet)
	{

		final PsiElement variableNameElement = parameters.getPosition();
		final PsiElement perlVariable = variableNameElement.getParent();
		final PsiFile perlFile = variableNameElement.getContainingFile();
		assert perlFile instanceof PerlFileElement;

		if (perlVariable instanceof PsiPerlScalarVariable)
			ApplicationManager.getApplication().runReadAction(new Runnable()
			{
				@Override
				public void run()
				{

					// global scalars
					for (String name : PerlScalarUtil.listDefinedGlobalScalars(variableNameElement.getProject()))
					{
						resultSet.addElement(LookupElementBuilder.create(name).withIcon(PerlIcons.SCALAR_GUTTER_ICON));
					}
					// global arrays
					for (String name : PerlArrayUtil.listDefinedGlobalArrays(variableNameElement.getProject()))
					{
						resultSet.addElement(LookupElementBuilder
										.create(name)
										.withIcon(PerlIcons.ARRAY_GUTTER_ICON)
										.withInsertHandler(PerlInsertHandlers.ARRAY_ELEMENT_INSERT_HANDLER)
										.withPresentableText(name + "[]")
						);
					}
					// global hashes
					for (String name : PerlHashUtil.listDefinedGlobalHahses(variableNameElement.getProject()))
					{
						resultSet.addElement(LookupElementBuilder
										.create(name)
										.withIcon(PerlIcons.HASH_GUTTER_ICON)
										.withInsertHandler(PerlInsertHandlers.HASH_ELEMENT_INSERT_HANDLER)
										.withPresentableText(name + "{}")
						);
					}

				}
			});
		else if (perlVariable instanceof PsiPerlArrayVariable)
			ApplicationManager.getApplication().runReadAction(new Runnable()
			{
				@Override
				public void run()
				{

					boolean useScalars = ((PsiPerlArrayVariable) perlVariable).getScalarSigils() != null;

					// global scalars
					if (useScalars)
					{
						for (String name : PerlScalarUtil.listDefinedGlobalScalars(variableNameElement.getProject()))
						{
							resultSet.addElement(LookupElementBuilder.create(name).withIcon(PerlIcons.SCALAR_GUTTER_ICON));
						}
					}
					// global arrays
					for (String name : PerlArrayUtil.listDefinedGlobalArrays(variableNameElement.getProject()))
					{
						resultSet.addElement(LookupElementBuilder.create(name).withIcon(PerlIcons.ARRAY_GUTTER_ICON));
					}
					// global hashes
					for (String name : PerlHashUtil.listDefinedGlobalHahses(variableNameElement.getProject()))
					{
						resultSet.addElement(LookupElementBuilder
										.create(name)
										.withIcon(PerlIcons.HASH_GUTTER_ICON)
										.withInsertHandler(PerlInsertHandlers.HASH_ELEMENT_INSERT_HANDLER)
										.withPresentableText(name + "{}")
						);
					}
				}
			});
		else if (perlVariable instanceof PsiPerlArrayIndexVariable)
			ApplicationManager.getApplication().runReadAction(new Runnable()
			{
				@Override
				public void run()
				{
					boolean useScalars = ((PsiPerlArrayIndexVariable) perlVariable).getScalarSigils() != null;

					// global scalars
					if (useScalars)
					{
						for (String name : PerlScalarUtil.listDefinedGlobalScalars(variableNameElement.getProject()))
						{
							resultSet.addElement(LookupElementBuilder.create(name).withIcon(PerlIcons.SCALAR_GUTTER_ICON));
						}
					}
					// global arrays
					for (String name : PerlArrayUtil.listDefinedGlobalArrays(variableNameElement.getProject()))
					{
						resultSet.addElement(LookupElementBuilder.create(name).withIcon(PerlIcons.ARRAY_GUTTER_ICON));
					}
				}
			});
		else if (perlVariable instanceof PsiPerlHashVariable)
			ApplicationManager.getApplication().runReadAction(new Runnable()
			{
				@Override
				public void run()
				{

					boolean useScalars = ((PsiPerlHashVariable) perlVariable).getScalarSigils() != null;

					// global scalars
					if (useScalars)
					{
						for (String name : PerlScalarUtil.listDefinedGlobalScalars(variableNameElement.getProject()))
						{
							resultSet.addElement(LookupElementBuilder.create(name).withIcon(PerlIcons.SCALAR_GUTTER_ICON));
						}
					}
					// global hashes
					for (String name : PerlHashUtil.listDefinedGlobalHahses(variableNameElement.getProject()))
					{
						resultSet.addElement(LookupElementBuilder.create(name).withIcon(PerlIcons.HASH_GUTTER_ICON));
					}

				}
			});
	}
}
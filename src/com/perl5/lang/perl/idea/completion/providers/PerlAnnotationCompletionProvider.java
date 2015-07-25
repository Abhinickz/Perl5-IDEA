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

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.util.ProcessingContext;
import com.perl5.PerlIcons;
import com.perl5.lang.perl.idea.completion.inserthandlers.PerlAnnotationInsertHandler;
import com.perl5.lang.perl.lexer.PerlAnnotations;
import com.perl5.lang.perl.util.PerlSubUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

/**
 * Created by hurricup on 03.06.2015.
 *
 */
public class PerlAnnotationCompletionProvider extends CompletionProvider<CompletionParameters>
{
	public static final HashSet<LookupElementBuilder> ANNOTATIONS_LOOKUP_ELEMENTS = new HashSet<>();

	static
	{
		for (String annotation : PerlAnnotations.TOKEN_TYPES.keySet())
			ANNOTATIONS_LOOKUP_ELEMENTS.add( LookupElementBuilder
					.create(annotation)
					.withInsertHandler(PerlAnnotationInsertHandler.INSTANCE)
			);
	}


	@Override
	protected void addCompletions(@NotNull final CompletionParameters parameters, ProcessingContext context, @NotNull final CompletionResultSet resultSet)
	{
		resultSet.addAllElements(ANNOTATIONS_LOOKUP_ELEMENTS);
	}


}

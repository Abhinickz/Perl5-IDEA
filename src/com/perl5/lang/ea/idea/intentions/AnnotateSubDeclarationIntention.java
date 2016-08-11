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

package com.perl5.lang.ea.idea.intentions;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.perl5.PerlBundle;
import com.perl5.lang.perl.psi.PerlSubBase;
import com.perl5.lang.perl.psi.PerlSubNameElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by hurricup on 11.08.2016.
 */
public class AnnotateSubDeclarationIntention extends AnnotateSubIntentionBase
{
	@Nullable
	@Override
	protected PsiElement getElementToAnnotate(PsiElement subNameElement)
	{
		List<PerlSubBase> subDefinitions = ((PerlSubNameElement) subNameElement).getSubDefinitions();
		return subDefinitions == null ? null : subDefinitions.get(0);
	}

	@Override
	public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element)
	{
		return super.isAvailable(project, editor, element) && !(element.getParent() instanceof PerlSubBase);
	}


	@NotNull
	@Override
	public String getText()
	{
		return PerlBundle.message("perl.annotate.declaration");
	}

}

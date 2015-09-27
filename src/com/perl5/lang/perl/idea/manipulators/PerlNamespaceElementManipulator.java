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

package com.perl5.lang.perl.idea.manipulators;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.util.IncorrectOperationException;
import com.perl5.lang.perl.psi.PerlNamespaceElement;
import com.perl5.lang.perl.psi.impl.PerlNamespaceElementImpl;
import com.perl5.lang.perl.util.PerlPackageUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 27.09.2015.
 */
public class PerlNamespaceElementManipulator extends AbstractElementManipulator<PerlNamespaceElement>
{
	@Override
	public PerlNamespaceElement handleContentChange(@NotNull PerlNamespaceElement element, @NotNull TextRange range, String newContent) throws IncorrectOperationException
	{
		if (newContent.isEmpty())
			throw new IncorrectOperationException("You can't set empty package name");

		String currentName = element.getText();

		boolean currentTail = currentName.endsWith("::") || currentName.endsWith("'");
		boolean newTail = newContent.endsWith("::") || newContent.endsWith("'");

		if (newTail && !currentTail)
			newContent = PerlPackageUtil.PACKAGE_SEPARATOR_TAIL_RE.matcher(newContent).replaceFirst("");
		else if (!newTail && currentTail)
			newContent = newContent + "::";

		return (PerlNamespaceElement) ((PerlNamespaceElementImpl) element).replaceWithText(newContent);
	}

	@NotNull
	@Override
	public TextRange getRangeInElement(@NotNull PerlNamespaceElement element)
	{
		return PerlPackageUtil.getPackageRangeFromOffset(0, element.getText());
	}
}

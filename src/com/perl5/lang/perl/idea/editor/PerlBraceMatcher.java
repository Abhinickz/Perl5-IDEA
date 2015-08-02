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

package com.perl5.lang.perl.idea.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 23.05.2015.
 */
public class PerlBraceMatcher implements PairedBraceMatcher, PerlElementTypes
{
	private static final BracePair[] PAIRS = new BracePair[]{
			new BracePair(LEFT_PAREN, RIGHT_PAREN, false),
			new BracePair(LEFT_BRACKET, RIGHT_BRACKET, false),
			new BracePair(LEFT_BRACE, RIGHT_BRACE, true),
			new BracePair(EMBED_MARKER_OPEN, EMBED_MARKER_CLOSE, false),
			new BracePair(EMBED_MARKER_OPEN, EMBED_MARKER_SEMICOLON, false),
	};

	@Override
	public BracePair[] getPairs()
	{
		return PAIRS;
	}

	@Override
	public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType)
	{
		return true;
	}

	@Override
	public int getCodeConstructStart(PsiFile file, int openingBraceOffset)
	{
		PsiElement element = file.findElementAt(openingBraceOffset);
		if (element == null || element instanceof PsiFile) return openingBraceOffset;

		PsiElement codeBlock = element.getParent();

		if (codeBlock != null && codeBlock instanceof PsiPerlBlock)
		{
			PsiElement blockContainer = codeBlock.getParent();

			if (blockContainer != null)
			{
				if (blockContainer instanceof PsiPerlSubDefinition
						|| blockContainer instanceof PsiPerlForCompound
						|| blockContainer instanceof PsiPerlForeachCompound
						)
					return blockContainer.getTextOffset();
				else if (blockContainer instanceof PsiPerlConditionalBlock
						|| blockContainer instanceof PsiPerlUnconditionalBlock)
				{
					PsiElement keyword = blockContainer.getPrevSibling();

					while (keyword != null && (keyword instanceof PsiWhiteSpace || keyword instanceof PsiComment))
					{
						keyword = keyword.getPrevSibling();
					}

					if (keyword != null)
						return keyword.getTextOffset();
				}
			}
		}

		return openingBraceOffset;
	}
}

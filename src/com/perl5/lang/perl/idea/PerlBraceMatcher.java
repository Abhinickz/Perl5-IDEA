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

package com.perl5.lang.perl.idea;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 23.05.2015.
 */
public class PerlBraceMatcher implements PairedBraceMatcher, PerlElementTypes
{
	private static final BracePair[] PAIRS = new BracePair[]{
			new BracePair(PERL_LPAREN, PERL_RPAREN, false),
			new BracePair(PERL_LBRACK, PERL_RBRACK, false),
			new BracePair(PERL_LBRACE, PERL_RBRACE, false),
//			new BracePair(PERL_LANGLE, PERL_RANGLE, false),
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
		return openingBraceOffset;
	}
}

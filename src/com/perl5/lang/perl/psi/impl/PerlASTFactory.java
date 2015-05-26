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

package com.perl5.lang.perl.psi.impl;

import com.intellij.lang.DefaultASTFactoryImpl;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.CharTableImpl;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.CharTable;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PerlASTFactory extends DefaultASTFactoryImpl implements PerlElementTypes
{
	private static final CharTable WHITESPACES = new CharTableImpl();

	@Override
	public LeafElement createComment( @NotNull IElementType type, CharSequence text)
	{
		if( type == PERL_HEREDOC_END )
			return new PerlHeredocTerminatorImpl(type, text);
		else
			return super.createComment(type, text);
	}

	@NotNull
	@Override
	public LeafElement createLeaf(@NotNull IElementType type, CharSequence text)
	{
		if( type == PERL_STRING_CONTENT )
			return new PerlStringContentImpl(type, text);
		else
			return super.createLeaf(type, text);
	}

}

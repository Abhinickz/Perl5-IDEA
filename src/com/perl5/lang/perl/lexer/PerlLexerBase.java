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

package com.perl5.lang.perl.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

/**
 * Created by hurricup on 03.05.2015.
 */
public interface PerlLexerBase extends FlexLexer, PerlElementTypes
{
	public abstract void startCustomBlock(int newState);
	public abstract void endCustomBlock();

	// @todo should be parseString
	public abstract IElementType processStringOpener();

	public abstract IElementType guessMul();
	public abstract IElementType guessAmp();
	public abstract IElementType guessMod();
	public abstract IElementType guessDiv();
	public abstract IElementType guessColon();
	public abstract IElementType guessOpenAngle();
	public abstract IElementType guessCloseAngle();

	public abstract IElementType checkOperatorXAssign();

	public abstract IElementType endBarewordStringComma();

	public abstract IElementType parseRegex();
	public abstract IElementType getParsedToken();

	public abstract void popState();
	public abstract void pushState();

	public abstract IElementType getPackageTokenType();
	public abstract IElementType guessBareword();
	public abstract IElementType getReservedTokenType();
	public abstract IElementType getHandleTokenType();
	public abstract IElementType getSubTokenType();
	public abstract IElementType guessPackageName();

	public abstract IElementType processSemicolon();

	public abstract IElementType processHeredocOpener();

	/**
	 * Parses current token as a variable
	 * @return sigil's token type
	 */
	public abstract IElementType parseBuiltInVariable();

	public abstract IElementType processOpenerWhiteSpace();

	public abstract void processTransOpener();
	public abstract IElementType processTransQuote();
	public abstract IElementType processTransChar();
	public abstract IElementType processTransCloser();

	public abstract void processRegexOpener();

	public abstract void processQuoteLikeListOpener();
	public abstract IElementType processQuoteLikeListQuote();

	public abstract void processQuoteLikeStringOpener();

	public abstract IElementType processQuoteLikeQuote();
	public abstract IElementType processQuoteLikeWord();

}


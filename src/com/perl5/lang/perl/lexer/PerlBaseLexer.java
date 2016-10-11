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

import com.intellij.psi.tree.IElementType;

import java.util.regex.Pattern;

/**
 * Created by hurricup on 10.08.2015.
 */
public abstract class PerlBaseLexer extends PerlProtoLexer implements PerlElementTypes
{
	private static final String BASIC_IDENTIFIER_PATTERN_TEXT = "[_\\p{L}\\d][_\\p{L}\\d]*"; // something strange in Java with unicode props; Added digits to opener for package Encode::KR::2022_KR;
	private static final String PACKAGE_SEPARATOR_PATTERN_TEXT =
			"(?:" +
					"(?:::)+'?" +
					"|" +
					"(?:::)*'" +
					")";
	public static final Pattern AMBIGUOUS_PACKAGE_PATTERN = Pattern.compile(
			"(" +
					PACKAGE_SEPARATOR_PATTERN_TEXT + "?" +        // optional opening separator,
					"(?:" +
					BASIC_IDENTIFIER_PATTERN_TEXT +
					PACKAGE_SEPARATOR_PATTERN_TEXT +
					")*" +
					")" +
					"(" +
					BASIC_IDENTIFIER_PATTERN_TEXT +
					")");


	public abstract IElementType parseBarewordMinus();


	public IElementType lexQualifiedIdentifier(int fullState, int pushBackState)
	{
		CharSequence yytext = yytext();
		int tokenSize = yytext.length();
		int pushbackSize = 0;

		while (true)
		{
			char currentChar = yytext.charAt(tokenSize - 1 - pushbackSize);
			if (currentChar == '\'' || currentChar == ':')
			{
				break;
			}
			pushbackSize++;
			assert pushbackSize < tokenSize;
		}

		if (pushbackSize > 0)
		{
			yypushback(pushbackSize);
			yybegin(pushBackState);
		}
		else
		{
			yybegin(fullState);
		}
		return PACKAGE;
	}


	// check that current token surrounded with braces
	protected boolean isBraced()
	{
		return getTokenHistory().getLastSignificantTokenType() == LEFT_BRACE && getNextNonSpaceCharacter() == '}';
	}


	protected IElementType startVariableLexing(int sigilSize, IElementType sigilType)
	{
		return startVariableLexing(sigilSize, PerlLexerGenerated.LEX_VARIABLE_NAME, sigilType);
	}

	protected IElementType startBracedVariableLexing(int sigilSize, IElementType sigilType)
	{
		return startVariableLexing(sigilSize, PerlLexerGenerated.LEX_BRACED_VARIABLE_NAME, sigilType);
	}


	private IElementType startVariableLexing(int sigilSize, int newState, IElementType sigilType)
	{
		yypushback(yylength() - sigilSize);
		yybegin(newState);
		return sigilType;
	}
}

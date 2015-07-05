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

package com.perl5.lang.perl.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.lexer.PerlLexer;
import com.perl5.lang.perl.psi.utils.PerlBuilder;
import com.perl5.lang.perl.util.PerlPackageUtil;
import com.perl5.lang.perl.util.PerlSubUtil;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by hurricup on 01.05.2015.
 */
public class PerlParserUitl extends GeneratedParserUtilBase implements PerlElementTypes
{
	// tokens that can be converted between each other depending on context
	public static final TokenSet CONVERTABLE_TOKENS = TokenSet.create(
			IDENTIFIER,
			VARIABLE_NAME,
			SUB
	);

	// tokens, which can succeed ambiguous bareword filehandle
	public static final TokenSet HANDLE_POSTFIX = TokenSet.create(
			OPERATOR_COMMA_ARROW,
			OPERATOR_COMMA,
			RIGHT_PAREN,
			RIGHT_BRACE,
			SEMICOLON
	);

	public static final TokenSet PRINT_HANDLE_NEGATE_SUFFIX = TokenSet.orSet(
			TokenSet.create(
					LEFT_ANGLE,
					LEFT_BRACE,
					LEFT_BRACKET,
					LEFT_PAREN,
					OPERATOR_LT_NUMERIC
			),
			PerlLexer.OPERATORS_TOKENSET
	);


	// commands, that accepts filehandles as first parameter
	public static final HashSet<String> PRE_HANDLE_OPS = new HashSet<>(Arrays.asList(
			"opendir",
			"chdir",
			"telldir",
			"seekdir",
			"rewinddir",
			"readdir",
			"closedir",

			"sysopen",
			"syswrite",
			"sysseek",
			"sysread",

			"open",
			"close",
			"read",
			"write",
			"stat",
			"ioctl",
			"fcntl",
			"lstat",
			"truncate",
			"tell",
			"select",
			"seek",
			"getc",
			"flock",
			"fileno",
			"eof",
			"eof",
			"binmode"
	));


	/**
	 * Wrapper for Builder class in order to implement additional per parser information in PerlBuilder
	 *
	 * @param root        root element
	 * @param builder     psibuilder
	 * @param parser      psiparser
	 * @param extendsSets extends sets
	 * @return PerlBuilder
	 */
	public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser, TokenSet[] extendsSets)
	{
		ErrorState state = new ErrorState();
		ErrorState.initState(state, builder, root, extendsSets);
		return new PerlBuilder(builder, state, parser);
	}

	/**
	 * Smart parser for ->, makes }->[ optional
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean parseArrowSmart(PsiBuilder b, int l)
	{
		IElementType tokenType = b.getTokenType();
		if (b.getTokenType() == OPERATOR_DEREFERENCE)
		{
			return consumeToken(b, OPERATOR_DEREFERENCE);
		} else
		{
			assert b instanceof PerlBuilder;
			PerlTokenData prevToken = ((PerlBuilder) b).lookupToken(-1);
			IElementType prevTokenType = prevToken == null ? null : prevToken.getTokenType();

			// optional }->[ or ]->{
			if (
					(prevTokenType == RIGHT_BRACE || prevTokenType == RIGHT_BRACKET)
							&& (tokenType == LEFT_BRACE || tokenType == LEFT_BRACKET || tokenType == LEFT_PAREN)
					)
				return true;
		}

		return false;
	}


	public static boolean parseExpressionLevel(PsiBuilder b, int l, int g)
	{
		return PerlParser.expr(b, l, g);
	}

	/**
	 * Named unary operators
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean isUnaryOperator(PsiBuilder b, int l)
	{
		assert b instanceof PerlBuilder;
		PerlTokenData prevTokenData = ((PerlBuilder) b).lookupToken(-1);

		if (prevTokenData != null && prevTokenData.getTokenType() == OPERATOR_DEREFERENCE)
			return false;

		IElementType tokenType = b.getTokenType();
		IElementType nextTokenType = b.lookAhead(1);

		if (tokenType == SUB && nextTokenType != LEFT_PAREN && nextTokenType != PACKAGE)
			// todo we should check current namespace here
			return PerlSubUtil.BUILT_IN_UNARY.contains(b.getTokenText());
		else if (tokenType == PACKAGE && nextTokenType == SUB && b.lookAhead(2) != LEFT_PAREN)
			return PerlSubUtil.isUnary(b.getTokenText(), ((PerlBuilder) b).lookupToken(1).getTokenText());

		return false;
	}

	/**
	 * Named list operators
	 *
	 * @param b PerlBuilder
	 * @param l Parsing level
	 * @return parsing result
	 */
	public static boolean isListOperator(PsiBuilder b, int l)
	{
		PerlTokenData prevTokenData = ((PerlBuilder) b).lookupToken(-1);

		if (prevTokenData != null && prevTokenData.getTokenType() == OPERATOR_DEREFERENCE)
			return false;

		IElementType tokenType = b.getTokenType();
		IElementType nextTokenType = b.lookAhead(1);

		if (tokenType == SUB && nextTokenType != LEFT_PAREN && nextTokenType != PACKAGE)
			// todo we should check current namespace here
			return !PerlSubUtil.BUILT_IN_UNARY.contains(b.getTokenText());
		else if (tokenType == PACKAGE && nextTokenType == SUB && b.lookAhead(2) != LEFT_PAREN)
			return !PerlSubUtil.isUnary(b.getTokenText(), ((PerlBuilder) b).lookupToken(1).getTokenText());

		return false;
	}


	/*
// @todo actually, prototypes and signatures depends on feature in current block; We should do this in parse time
//private sub_declaration_parameters ::=
//    sub_prototype sub_attributes ?
//    | sub_attributes
//
//private sub_definition_parameters ::=
//    sub_attributes ? sub_signature
//    | sub_declaration_parameters
//
//private sub_prototype ::= "(" sub_prototype_element * (";" sub_prototype_element *) ? ")"
//private sub_prototype_element ::=
//        "\\" ( "[" sub_prototype_char + "]" | sub_prototype_char )
//        | sub_prototype_char
//
//private sub_prototype_char ::= "$" | "@" | "+" | "*" | "&"
//
//private sub_attributes ::= 'NYI'
//
//// @todo this requires use feature 'signatures' and no warnings 'experimental:signatures';
//private sub_signature ::= 'NYI'
	* */
	public static boolean parseSubPrototype(PsiBuilder b, int l)
	{
//		boolean isSignatureEnabled  = getCurrentBlockState(b).getFeatures().isSignaturesEnabled();

//		System.out.println("Sub definition parsing, Signatures enabled: "+isSignatureEnabled);

		while (!b.eof() && b.getTokenType() != RIGHT_PAREN)
			consumeToken(b, b.getTokenType());

		return true;
	}

	// @todo this is really raw
	public static boolean parseSubAttributes(PsiBuilder b, int l)
	{
//		boolean isSignatureEnabled  = getCurrentBlockState(b).getFeatures().isSignaturesEnabled();
//		System.out.println("Sub declaration parsing, Signatures enabled: "+isSignatureEnabled);

		while (!b.eof() && b.getTokenType() != LEFT_BRACE)
		{
			PerlBuilder.Marker m = b.mark();
			b.advanceLexer();
			m.collapse(SUB_ATTRIBUTE);
		}

		return true;
	}

	public static boolean parseSubSignature(PsiBuilder b, int l)
	{
//		boolean isSignatureEnabled  = getCurrentBlockState(b).getFeatures().isSignaturesEnabled();
//		System.out.println("Sub declaration parsing, Signatures enabled: "+isSignatureEnabled);
		return false;
	}

	/**
	 * Smart semi checker decides if we need semi here
	 *
	 * @param b Perl builder
	 * @param l Parsing level
	 * @return checking result
	 */
	public static boolean statementSemi(PsiBuilder b, int l)
	{
		IElementType tokenType = b.getTokenType();
		if (tokenType == SEMICOLON)
		{
			consumeToken(b, SEMICOLON);
			return true;
		} else if (tokenType == RIGHT_BRACE || tokenType == REGEX_QUOTE_CLOSE)
			return true;
		else if (b.eof()) // eof
			return true;

		b.mark().error("Semicolon expected");

		return true;
	}


	/**
	 * Replaces identifier as a variable name
	 *
	 * @param b Perlbuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean convertIdentifier(PsiBuilder b, int l, IElementType tokenType)
	{
		IElementType currentTokenType = b.getTokenType();
		if (currentTokenType == tokenType)
		{
			b.advanceLexer();
			return true;
		} else if (CONVERTABLE_TOKENS.contains(currentTokenType))
		{
			b.remapCurrentToken(tokenType);
			b.advanceLexer();
			return true;
		}

		return false;
	}

	/**
	 * Completes namespace, invoked when we are 100% sure that PACKAGE_IDENTIFIER is a package
	 *
	 * @param b Perlbuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean convertPackageIdentifier(PsiBuilder b, int l)
	{
		IElementType currentTokenType = b.getTokenType();
		if (currentTokenType == PACKAGE)
		{
			b.advanceLexer();
			return true;
		} else if (currentTokenType == PACKAGE_IDENTIFIER)
		{
			b.remapCurrentToken(PACKAGE);
			b.advanceLexer();
			return true;
		}

		return false;
	}

	/**
	 * Merges sequence [package] identifier to a package
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return result
	 */
	public static boolean mergePackageName(PsiBuilder b, int l)
	{
		IElementType tokenType = b.getTokenType();

		if (tokenType == PACKAGE)
		{
			b.advanceLexer();
			return true;
		} else if (CONVERTABLE_TOKENS.contains(tokenType))    // single word package
		{
			b.remapCurrentToken(PACKAGE);
			b.advanceLexer();
			return true;
		} else if (
				tokenType == PACKAGE_IDENTIFIER && CONVERTABLE_TOKENS.contains(b.lookAhead(1))
				)
		{
			PsiBuilder.Marker m = b.mark();
			b.advanceLexer();
			b.advanceLexer();
			m.collapse(PACKAGE);
			return true;
		}

		return false;
	}


	/**
	 * Checks current token and convert it if necessary
	 *
	 * @param b         PerlBuilder
	 * @param l         parsing level
	 * @param fromToken possible source token
	 * @param toToken   token we want to have
	 * @return parsing result
	 */
	public static boolean checkAndConvertToken(PsiBuilder b, int l, IElementType fromToken, IElementType toToken)
	{
		IElementType tokenType = b.getTokenType();
		if (tokenType == toToken)
		{
			b.advanceLexer();
			return true;
		} else if (tokenType == fromToken)
		{
			b.remapCurrentToken(toToken);
			b.advanceLexer();
			return true;
		}
		return false;

	}

	public static boolean sigilCode(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, OPERATOR_BITWISE_AND, SIGIL_CODE);
	}

	public static boolean operatorBitwiseAnd(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, SIGIL_CODE, OPERATOR_BITWISE_AND);
	}

	public static boolean sigilGlob(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, OPERATOR_MUL, SIGIL_GLOB);
	}

	public static boolean operatorMul(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, SIGIL_GLOB, OPERATOR_MUL);
	}

	public static boolean sigilHash(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, OPERATOR_MOD, SIGIL_HASH);
	}

	public static boolean operatorMod(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, SIGIL_HASH, OPERATOR_MOD);
	}

	public static boolean leftAngle(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, OPERATOR_LT_NUMERIC, LEFT_ANGLE);
	}

	public static boolean operatorLtNumeric(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, LEFT_ANGLE, OPERATOR_LT_NUMERIC);
	}

	public static boolean rightAngle(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, OPERATOR_GT_NUMERIC, RIGHT_ANGLE);
	}

	public static boolean operatorGtNumeric(PsiBuilder b, int l)
	{
		return checkAndConvertToken(b, l, RIGHT_ANGLE, OPERATOR_GT_NUMERIC);
	}

	/**
	 * Joining several regex tokens into one to lighten PSI tree. Temporary solution, until regex parsing is implemented
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean joinRegexTokens(PsiBuilder b, int l)
	{
		if (b.getTokenType() == REGEX_TOKEN)
		{
			PsiBuilder.Marker m = b.mark();

			while (b.getTokenType() == REGEX_TOKEN)
				b.advanceLexer();

			m.collapse(REGEX_TOKEN);
			return true;
		}
		return false;
	}

	/**
	 * Checks and parses identifier as a handle
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean smartHandle(PsiBuilder b, int l)
	{
		if (CONVERTABLE_TOKENS.contains(b.getTokenType()) && HANDLE_POSTFIX.contains(b.lookAhead(1)))    // we can convert and afterward token is ok
		{
			assert b instanceof PerlBuilder;

			PerlTokenData lastToken = ((PerlBuilder) b).lookupToken(-1);

			if (lastToken.getTokenType() == SUB
					|| lastToken.getTokenType() == LEFT_PAREN
					&& (lastToken = ((PerlBuilder) b).lookupToken(-2)).getTokenType() == SUB
					)
				if (PRE_HANDLE_OPS.contains(lastToken.getTokenText()))
				{
					b.remapCurrentToken(HANDLE);
					((PerlBuilder) b).registerHandle(b.getTokenText());
					b.advanceLexer();
					return true;
				}
		}
		return false;
	}

	/**
	 * parser for print/say/printf filehandle
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean parsePrintHandle(PsiBuilder b, int l)
	{
		IElementType currentTokenType = b.getTokenType();
		IElementType nextTokenType = b.lookAhead(1);
		assert b instanceof PerlBuilder;

		if (
				CONVERTABLE_TOKENS.contains(currentTokenType)                // it's identifier
						&& !PRINT_HANDLE_NEGATE_SUFFIX.contains(nextTokenType)        // no negation tokens
						&& ((PerlBuilder) b).isRegisteredHandle(b.getTokenText())    // handle was opened here fixme this is not true. print eats any handle
				)
		{
			b.remapCurrentToken(HANDLE);
			b.advanceLexer();
			return true;
		}

		return false;
	}

	/**
	 * Checks and parses bareword filehandle for <FH> operations
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean parseReadHandle(PsiBuilder b, int l)
	{
		IElementType currentTokenType = b.getTokenType();
		IElementType nextTokenType = b.lookAhead(1);

		if (CONVERTABLE_TOKENS.contains(currentTokenType) && (nextTokenType == OPERATOR_GT_NUMERIC || nextTokenType == RIGHT_ANGLE))
		{
			b.remapCurrentToken(HANDLE);
			b.advanceLexer();
			return true;
		}

		return false;
	}

	/**
	 * Parses invocable method
	 * As input we may have:
	 * PACKAGE_IDENTIFIER IDENTIFIER	Foo::sub
	 * IDENTIFIER PACKAGE_IDENTIFIER	sub Foo::
	 * IDENTIFIER IDENTIFIER			sub Foo
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean parseMethod(PsiBuilder b, int l)
	{
		IElementType currentTokenType = b.getTokenType();
		IElementType nextTokenType = b.lookAhead(1);

		assert b instanceof PerlBuilder;

		// can be
		// 	Foo::method
		//  Foo::Bar
		if ((currentTokenType == PACKAGE_IDENTIFIER || currentTokenType == PACKAGE) && CONVERTABLE_TOKENS.contains(nextTokenType))
		{
			PerlTokenData nextTokenData = ((PerlBuilder) b).lookupToken(1);
			String potentialSubName = PerlPackageUtil.getCanonicalPackageName(b.getTokenText()) + "::" + nextTokenData.getTokenText();
			boolean subExists = ((PerlBuilder) b).isKnownSub(potentialSubName);
			boolean packageExists = ((PerlBuilder) b).isKnownPackage(potentialSubName);

			if (subExists || !packageExists)
				return convertPackageIdentifier(b, l) && convertIdentifier(b, l, SUB);
			else
				return mergePackageName(b, l);
		}
		// 	method [Foo]
		else if (CONVERTABLE_TOKENS.contains(currentTokenType))
		{
			PerlTokenData prevTokenData = ((PerlBuilder) b).lookupToken(-1);


			// ->sub
			if (prevTokenData != null && prevTokenData.getTokenType() == OPERATOR_DEREFERENCE)
				return convertIdentifier(b, l, SUB);
				// may be
				// 	method Foo::
				//	method Foo::Bar
				//  method Foo::othermethod
			else if (nextTokenType == PACKAGE_IDENTIFIER || nextTokenType == PACKAGE)
			{
				IElementType nextNextTokenType = b.lookAhead(2);
				if (CONVERTABLE_TOKENS.contains(nextNextTokenType))
					// it's somesub Package::method
					return convertIdentifier(b, l, SUB);
				else
					// it's method Package::
					return convertIdentifier(b, l, SUB) && convertPackageIdentifier(b, l);
			}
			// may be
			// 	method Foo
			else if (CONVERTABLE_TOKENS.contains(nextTokenType))
			{
				PerlTokenData nextTokenData = ((PerlBuilder) b).lookupToken(1);

				String potentialSubName = nextTokenData.getTokenText() + "::" + b.getTokenText();
				if (((PerlBuilder) b).isKnownSub(potentialSubName))
					return convertIdentifier(b, l, SUB) && convertIdentifier(b, l, PACKAGE);
				else
					return convertIdentifier(b, l, SUB);
			} else // it's just sub
				return convertIdentifier(b, l, SUB);
		}

		return false;
	}


	/**
	 * Parses {IDENTIFIER}
	 *
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return parsing result
	 */
	public static boolean parseBracedString(PsiBuilder b, int l)
	{
		if (consumeToken(b, LEFT_BRACE)
				&& CONVERTABLE_TOKENS.contains(b.getTokenType())
				&& b.lookAhead(1) == RIGHT_BRACE
				)
		{
			b.remapCurrentToken(STRING_CONTENT);
			b.advanceLexer();
			b.advanceLexer();
			return true;
		}
		return false;
	}

}

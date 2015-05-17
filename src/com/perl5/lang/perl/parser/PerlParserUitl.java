package com.perl5.lang.perl.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.perl5.lang.perl.exceptions.PerlParsingException;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.psi.PerlBuilder;
import com.perl5.lang.perl.util.*;

/**
 * Created by hurricup on 01.05.2015.
 */
public class PerlParserUitl extends GeneratedParserUtilBase implements PerlElementTypes
{
	/**
	 * Wrapper for Builder class in order to implement additional per parser information in PerlBuilder
	 * @param root           	root element
	 * @param builder			psibuilder
	 * @param parser			psiparser
	 * @param extendsSets		extends sets
	 * @return					PerlBuilder
	 */
	public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser, TokenSet[] extendsSets) {
		ErrorState state = new ErrorState();
		ErrorState.initState(state, builder, root, extendsSets);
		return new PerlBuilder(builder, state, parser);
	}



	public static boolean parseCallParameters(PsiBuilder b, int l)
	{
		assert b instanceof PerlBuilder;

//		if( methodName == null) // not working on scalar calling, we s
			// can't happen
//			return false;
//			throw new Error("No method captured, smth is wrong");
//		else if(packageName == null) // method from unknown package
//		{
//			parseExpressionLevel(b,l,2);
//		}
//		else // method and package are known
//		{
			PsiBuilder.Marker m = b.mark();
			boolean r = PerlParser.block(b,l);
			if( !r || nextTokenIs(b, "", PERL_COMMA, PERL_ARROW_COMMA))
				m.rollbackTo();
			else
				m.drop();

			parseExpressionLevel(b,l,2); // nothing below comma
//		}
		return true;
	}


	/**
	 * Parsing file entry point. Inits codeblock states, default namespace
	 * @param b	PerlBuilder
	 * @param l	parsing level
	 * @return	parsing result
	 */
	public static boolean parseFile(PsiBuilder b, int l)
	{
		assert b instanceof PerlBuilder;

		((PerlBuilder) b).initCodeBlockStateStack(); // init states stack
		getCurrentBlockState(b).setNamespace(((PerlBuilder) b).getNamespace("main")); // switch to main namespace

		PsiBuilder.Marker m = b.mark();
		boolean r = PerlParser.file_items(b, l);

		if(r)
		{
			m.done(BLOCK);
		}
		else
		{
			m.drop();
		}

		((PerlBuilder) b).popCodeBlockState(b.getTokenText());

		return r;
	}


	/**
	 * Function marks current bareword as a filehandle
	 * @param b PerlBuilder
	 * @param l parsing level
	 * @return result
	 */
	public static boolean parseBarewordHandle(PsiBuilder b, int l ) {

		if( isBareword(b.getTokenType()) && b.lookAhead(1) != PERL_LPAREN)
		{
			assert b instanceof PerlBuilder;

			String handle = b.getTokenText();
			((PerlBuilder) b).setLastParsedHandle(handle);

			PsiBuilder.Marker m = b.mark();
			b.advanceLexer();

			m.collapse(PERL_HANDLE);

			return true;
		}

		return false;
	}


	/**
	 * Smart parser for ->, makes }->[ optional
	 * @param b
	 * @param l
	 * @return
	 */
	public static boolean parseArrowSmart(PsiBuilder b, int l )
	{
		IElementType tokenType = b.getTokenType();
		if( b.getTokenType() == PERL_DEREFERENCE )
		{
			return consumeToken(b, PERL_DEREFERENCE);
		}
		else
		{
			assert b instanceof PerlBuilder;
			PerlTokenData prevToken = ((PerlBuilder) b).getAheadToken(-1);
			IElementType prevTokenType = prevToken == null ? null : prevToken.getTokenType();

			// optional }->[ or ]->{
			if(
				( prevTokenType == PERL_RBRACE || prevTokenType == PERL_RBRACK )
				&& ( tokenType == PERL_LBRACE || tokenType == PERL_LBRACK )
					)
				return true;
		}

		return false;
	}


	public static boolean parseExpressionLevel(PsiBuilder b, int l, int g )
	{
		return PerlParser.expr(b,l,g);
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

	public static boolean parseSubPrototype(PsiBuilder b, int l )
	{
//		boolean isSignatureEnabled  = getCurrentBlockState(b).getFeatures().isSignaturesEnabled();

//		System.out.println("Sub definition parsing, Signatures enabled: "+isSignatureEnabled);

		while( !b.eof() && b.getTokenType() != PERL_RPAREN )
			consumeToken(b, b.getTokenType());

		return true;
	}

	// @todo this is really raw
	public static boolean parseSubAttributes(PsiBuilder b, int l )
	{
//		boolean isSignatureEnabled  = getCurrentBlockState(b).getFeatures().isSignaturesEnabled();
//		System.out.println("Sub declaration parsing, Signatures enabled: "+isSignatureEnabled);

		while( b.getTokenType() != PERL_LBRACE )
		{
			PerlBuilder.Marker m = b.mark();
			b.advanceLexer();
			m.collapse(PERL_FUNCTION_ATTRIBUTE);
		}

		return true;
	}

	public static boolean parseSubSignature(PsiBuilder b, int l )
	{
//		boolean isSignatureEnabled  = getCurrentBlockState(b).getFeatures().isSignaturesEnabled();
//		System.out.println("Sub declaration parsing, Signatures enabled: "+isSignatureEnabled);
		return false;
	}

	public static boolean statementSemi(PsiBuilder b, int l)
	{
		IElementType tokenType = b.getTokenType();
		if( tokenType == PERL_SEMI )
		{
			consumeToken(b, PERL_SEMI);
			return true;
		}
		else if( tokenType == PERL_RBRACE)
			return true;

		// @todo think what to do here. Currently any statement being finished, even incorrect one
		return false;
	}

	/**
	 * Trying to parse:  version and replace token type
	 * @param b	PerlBuilder
	 * @param l level
	 * @return	result
	 */
	public static boolean parseVersion(PsiBuilder b, int l ) {
		// here is the logic when we allows to use barewords as strings
		IElementType tokenType = b.getTokenType();

		if(tokenType == PERL_NUMBER_VERSION || tokenType == PERL_NUMBER)
		{
			assert b instanceof PerlBuilder;
			((PerlBuilder) b).setLastParsedVersion(b.getTokenText());
			PsiBuilder.Marker m = b.mark();
			b.advanceLexer();
			m.collapse(PERL_VERSION);

			return true;
		}

		return false;
	}

	protected static PerlCodeBlockState getCurrentBlockState(PsiBuilder b)
	{
		assert b instanceof PerlBuilder;
		return ((PerlBuilder) b).getCurrentBlockState();
	}


	public static boolean isBareword(IElementType tokenType)
	{
		return tokenType == PERL_KEYWORD || tokenType == PERL_OPERATOR_UNARY || tokenType == PERL_BLOCK_NAME;
	}

}

package com.perl5.lang.perl.lexer.elements;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.perl5.highlighter.PerlSyntaxHighlighter;
import com.perl5.highlighter.SelfStyled;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hurricup on 19.04.2015.
 */
public class PerlArray extends PerlVariable implements SelfStyled
{
	private static final TextAttributesKey[] attributesKeys = new TextAttributesKey[]{PerlSyntaxHighlighter.PERL_ARRAY};

	public PerlArray(PerlVariableScope scope, boolean isBuiltIn) {
		super("PERL_ARRAY", scope, isBuiltIn);
	}

	@Override
	public TextAttributesKey[] getTextAttributesKey()
	{
		return attributesKeys;
	}

	public static final ArrayList<String> BUILT_IN = new ArrayList<String>( Arrays.asList(
			"@+",
			"@-",
			"@_",
			"@ARGV",
			"@INC",
			"@LAST_MATCH_START",

			// hash slices
			"@!",
			"@+",
			"@-",
			"@^H",
			"@ENV",
			"@INC",
			"@OVERLOAD",
			"@SIG"
	));
}

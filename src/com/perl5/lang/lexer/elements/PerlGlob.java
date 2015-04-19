package com.perl5.lang.lexer.elements;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hurricup on 19.04.2015.
 */
public class PerlGlob extends PerlVariable
{
	public PerlGlob(PerlVariableScope scope, boolean isBuiltIn) {
		super("PERL_GLOB", scope, isBuiltIn);
	}

	public static final ArrayList<String> BUILT_IN = new ArrayList<String>( Arrays.asList(
			"*ARGV",
			"*STDERR",
			"*STDOUT",
			"*ARGVOUT",
			"*STDIN"
	));
}

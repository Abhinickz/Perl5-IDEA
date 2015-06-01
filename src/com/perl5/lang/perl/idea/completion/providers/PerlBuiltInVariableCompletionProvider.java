package com.perl5.lang.perl.idea.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.perl5.lang.perl.psi.PerlPerlArray;
import com.perl5.lang.perl.psi.PerlPerlGlob;
import com.perl5.lang.perl.psi.PerlPerlHash;
import com.perl5.lang.perl.psi.PerlPerlScalar;
import com.perl5.lang.perl.util.PerlArrayUtil;
import com.perl5.lang.perl.util.PerlGlobUtil;
import com.perl5.lang.perl.util.PerlHashUtil;
import com.perl5.lang.perl.util.PerlScalarUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 01.06.2015.
 */
public class PerlBuiltInVariableCompletionProvider extends CompletionProvider<CompletionParameters>
{
	public void addCompletions(@NotNull CompletionParameters parameters,
							   ProcessingContext context,
							   @NotNull CompletionResultSet resultSet)
	{

		PsiElement variableName = parameters.getPosition();
		PsiElement variable = variableName.getParent();

		if (variable instanceof PerlPerlScalar)
			fillScalarCompletions(parameters, context, resultSet);
		else if (variable instanceof PerlPerlArray)
			fillArrayCompletions(parameters, context, resultSet);
		else if (variable instanceof PerlPerlHash)
			fillHashCompletions(parameters, context, resultSet);
		else if (variable instanceof PerlPerlGlob)
			fillGlobCompletions(parameters, context, resultSet);
	}

	private void fillScalarCompletions(@NotNull CompletionParameters parameters,
									   ProcessingContext context,
									   @NotNull CompletionResultSet resultSet)
	{

		for( String name: PerlScalarUtil.BUILT_IN )
		{
			resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
		}
		for( String name: PerlArrayUtil.BUILT_IN )
		{
			resultSet.addElement(LookupElementBuilder.create(name.substring(1) + "[]"));
		}
		for( String name: PerlHashUtil.BUILT_IN )
		{
			resultSet.addElement(LookupElementBuilder.create(name.substring(1) + "{}"));
		}
	}
	private void fillArrayCompletions(@NotNull CompletionParameters parameters,
									  ProcessingContext context,
									  @NotNull CompletionResultSet resultSet)
	{
		// built in arrays
		for( String name: PerlArrayUtil.BUILT_IN )
		{
			resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
		}
		for( String name: PerlHashUtil.BUILT_IN )
		{
			resultSet.addElement(LookupElementBuilder.create(name.substring(1) + "{}"));
		}

	}
	private void fillHashCompletions(@NotNull CompletionParameters parameters,
									 ProcessingContext context,
									 @NotNull CompletionResultSet resultSet)
	{
		// built-in hashes
		for( String name: PerlHashUtil.BUILT_IN )
		{
			resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
		}

	}
	private void fillGlobCompletions(@NotNull CompletionParameters parameters,
									 ProcessingContext context,
									 @NotNull CompletionResultSet resultSet)
	{
		// built-in globs
		for( String name: PerlGlobUtil.BUILT_IN )
		{
			resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
		}
	}
}

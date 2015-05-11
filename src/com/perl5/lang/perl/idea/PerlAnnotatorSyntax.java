package com.perl5.lang.perl.idea;

/**
 * Created by hurricup on 25.04.2015.
 */
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.perl.idea.highlighter.PerlSyntaxHighlighter;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.util.*;
import org.jetbrains.annotations.NotNull;

public class PerlAnnotatorSyntax implements Annotator, PerlElementTypes
{
	private void colorize(Annotation annotation, TextAttributesKey key, boolean builtin, boolean deprecated)
	{
		TextAttributes attributes = key.getDefaultAttributes();

		if( builtin )
			attributes = TextAttributes.merge(attributes, PerlSyntaxHighlighter.PERL_BUILT_IN.getDefaultAttributes());
		if( deprecated )
			attributes = TextAttributes.merge(attributes, PerlSyntaxHighlighter.PERL_DEPRECATED.getDefaultAttributes());

		annotation.setEnforcedTextAttributes(attributes);
	}


	@Override
	public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {

		IElementType elementType = element.getNode().getElementType();
		if( elementType == PERL_SCALAR )
		{
			colorize(
					holder.createInfoAnnotation(element, null),
					PerlSyntaxHighlighter.PERL_SCALAR,
					PerlScalarUtil.isBuiltIn(element.getText()),
					false);
		}
		else if( elementType == PERL_HASH )
		{
			colorize(
					holder.createInfoAnnotation(element, null),
					PerlSyntaxHighlighter.PERL_HASH,
					PerlHashUtil.isBuiltIn(element.getText()),
					false);
		}
		else if( elementType == PERL_ARRAY )
		{
			colorize(
					holder.createInfoAnnotation(element, null),
					PerlSyntaxHighlighter.PERL_ARRAY,
					PerlArrayUtil.isBuiltIn(element.getText()),
					false);
		}
		else if( elementType == PERL_PACKAGE )
		{
			String packageName = element.getText();
			PerlPackageUtil.PACKAGE_TYPE packageType = PerlPackageUtil.getPackageType(packageName);

			String message = packageType == PerlPackageUtil.PACKAGE_TYPE.DEPRECATED ?
					"Package "+packageName+" is marked as deprecated and may be removed in future perl versions"
					: null;

			colorize(
					holder.createInfoAnnotation(element, message),
					packageType == PerlPackageUtil.PACKAGE_TYPE.PRAGMA ? PerlSyntaxHighlighter.PERL_PACKAGE_PRAGMA: PerlSyntaxHighlighter.PERL_PACKAGE,
					packageType != null,
					packageType == PerlPackageUtil.PACKAGE_TYPE.DEPRECATED);

		}
		else if( elementType == PERL_STRING)
		{
			PsiElement quoteElement = element.getParent().getFirstChild();
			String quoteElementText = quoteElement.getText();

			Annotation annotation = holder.createInfoAnnotation(element, null);

			if( quoteElement == element || "'".equals(quoteElementText) || "q".equals(quoteElementText) || "qw".equals(quoteElementText)) // bareword string, single quoted string
			{
				annotation.setTextAttributes(PerlSyntaxHighlighter.PERL_SQ_STRING);
			}
			else if( "\"".equals(quoteElementText) || "qq".equals(quoteElementText)) // interpolated
			{
				annotation.setTextAttributes(PerlSyntaxHighlighter.PERL_DQ_STRING);
			}
			else if( "`".equals(quoteElementText) || "qx".equals(quoteElementText)) // executable
			{
				annotation.setTextAttributes(PerlSyntaxHighlighter.PERL_DX_STRING);
			}
			else
			{
				throw new Error("Unable to detect string type");
			}
		}
		else if( elementType == PERL_FUNCTION)
		{
			colorize(
					holder.createInfoAnnotation(element, null),
					PerlSyntaxHighlighter.PERL_FUNCTION,
					PerlFunctionUtil.isBuiltIn(element.getText()),
					false);
		}
	}
}
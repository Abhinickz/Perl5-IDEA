// This is a generated file. Not intended for manual editing.
package com.perl5.lang.pod.lexer;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.pod.PodElementType;
import com.perl5.lang.pod.PodTokenType;
import com.perl5.lang.pod.psi.impl.PodParagraphImpl;
import com.perl5.lang.pod.psi.impl.PodSectionImpl;

public interface PodElementTypes
{

	IElementType PARAGRAPH = new PodElementType("PARAGRAPH");
	IElementType SECTION = new PodElementType("SECTION");

	IElementType POD_CODE = new PodTokenType("POD_CODE");
	IElementType POD_NEWLINE = new PodTokenType("POD_NEWLINE");
	IElementType POD_TAG = new PodTokenType("POD_TAG");
	IElementType POD_TEXT = new PodTokenType("POD_TEXT");

	class Factory
	{
		public static PsiElement createElement(ASTNode node)
		{
			IElementType type = node.getElementType();
			if (type == PARAGRAPH)
			{
				return new PodParagraphImpl(node);
			}
			else if (type == SECTION)
			{
				return new PodSectionImpl(node);
			}
			throw new AssertionError("Unknown element type: " + type);
		}
	}
}

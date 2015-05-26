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

package com.perl5.lang.perl.idea;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.psi.PerlRequireStatement;
import com.perl5.lang.perl.psi.PerlRequireTerm;
import com.perl5.lang.perl.psi.PerlStatementModifier;
import com.perl5.lang.perl.psi.PerlUseStatement;
import com.perl5.lang.perl.psi.impl.PerlAnonArrayImpl;
import com.perl5.lang.perl.psi.impl.PerlAnonHashImpl;
import com.perl5.lang.perl.psi.impl.PerlBlockImpl;
import com.perl5.lang.perl.psi.impl.PerlParenthesisedExprImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hurricup on 20.05.2015.
 */
public class PerlFoldingBuilder extends FoldingBuilderEx
{
	@NotNull
	@Override
	public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick)
	{
		// @todo handle this
		if( root instanceof OuterLanguageElementImpl )
			return FoldingDescriptor.EMPTY;

		List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

		descriptors.addAll(getDescriptorsFor(root, document, PerlBlockImpl.class, 0, 0));
		descriptors.addAll(getDescriptorsFor(root, document, PerlAnonHashImpl.class, 0, 0));
		descriptors.addAll(getDescriptorsFor(root, document, PerlAnonArrayImpl.class, 0, 0));
		descriptors.addAll(getDescriptorsFor(root, document, PerlParenthesisedExprImpl.class, 0, 0));
		descriptors.addAll(getDescriptorsFor(root, document, PsiComment.class, 0, 1));

		descriptors.addAll(getCommentsDescriptors(root, document));
		descriptors.addAll(getImportDescriptors(root, document));

		return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
	}

	/**
	 * Searching for sequential comments (starting from newline or subblock beginning) and making folding descriptors for such blocks of size > 1
	 * @param root	root to search in
	 * @param document	document to search in
	 * @return	list of FoldingDescriptros
	 */
	private List<FoldingDescriptor> getCommentsDescriptors(@NotNull PsiElement root, @NotNull Document document)
	{
		List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

		Collection<PsiComment> comments = PsiTreeUtil.findChildrenOfType(root,PsiComment.class);

		int currentOffset = 0;

		for( PsiComment comment: comments)
		{
			if( currentOffset < comment.getTextOffset() )
			{
				boolean isCollapsable = false;
				PsiElement prev = comment;

				while(true )
				{
					prev = prev.getPrevSibling();

					// first element in block or after comment/pod, which eat newline
					if( prev == null || prev instanceof PsiComment)
					{
						isCollapsable = true;
						break;
					}
					else if( prev instanceof PsiWhiteSpace )
					{
						// whitespace with newline
						if(prev.getText().equals("\n"))
						{
							isCollapsable = true;
							break;
						}
					}
					// non-whitespace block
					else
						break;
				}

				if( isCollapsable )
				{
					int blockStart = comment.getTextOffset();
					int blockEnd = blockStart;
					ASTNode blockNode = comment.getNode();
					PsiElement currentComment = comment;
					int commentsNumber = 0;

					while (currentComment != null)
					{
						if (currentComment.getNode().getElementType() == PerlElementTypes.PERL_COMMENT)
						{
							blockEnd = currentComment.getTextOffset() + currentComment.getTextLength() - 1;
							commentsNumber++;
						}

						IElementType tokenType = currentComment.getNode().getElementType();
						if (tokenType != PerlElementTypes.PERL_COMMENT && tokenType != TokenType.WHITE_SPACE && tokenType != TokenType.NEW_LINE_INDENT)
							break;

						currentComment = currentComment.getNextSibling();
					}

					if (blockEnd != blockStart && commentsNumber > 1)
					{
						currentOffset = blockEnd;
						descriptors.add(new FoldingDescriptor(blockNode, new TextRange(blockStart, blockEnd)));
					}
				}
			}
		}

		return descriptors;
	}

	/**
	 * Searching for sequential uses and requires, ignoring stament modifieers, comments, pods and whitespaces and making folding descriptors for such blocks of size > 1
	 * @param root	root to search in
	 * @param document	document to search in
	 * @return	list of FoldingDescriptros
	 */
	private List<FoldingDescriptor> getImportDescriptors(@NotNull PsiElement root, @NotNull Document document)
	{
		List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

		Collection<PsiElement> imports = PsiTreeUtil.findChildrenOfAnyType(root, PerlUseStatement.class, PerlRequireStatement.class);

		int currentOffset = 0;

		for( PsiElement perlImport: imports)
		{
			if( currentOffset < perlImport.getTextOffset() )
			{
				int blockStart = perlImport.getTextOffset();
				int blockEnd = blockStart;
				ASTNode blockNode = perlImport.getNode();
				PsiElement currentImport = perlImport;

				int importsNumber = 0;

				while (currentImport != null)
				{
					if (currentImport instanceof PerlUseStatement || currentImport instanceof PerlStatementModifier || currentImport instanceof PerlRequireStatement)
					{
						blockEnd = currentImport.getTextOffset() + currentImport.getTextLength() - 1;
						if( !(currentImport instanceof PerlStatementModifier) )
							importsNumber++;
					}
					else if( currentImport.getNode().getElementType() == PerlElementTypes.PERL_SEMI )
						blockEnd = currentImport.getTextOffset() + 1;
					else if( !(currentImport instanceof PsiComment || currentImport instanceof PsiWhiteSpace ) )
						break;

					currentImport = currentImport.getNextSibling();
				}

				if (blockEnd != blockStart && importsNumber > 1)
				{
					currentOffset = blockEnd;
					descriptors.add(new FoldingDescriptor(blockNode, new TextRange(blockStart, blockEnd)));
				}
			}
		}

		return descriptors;
	}

	/**
	 * Finding psi elements of specific types and add Folding descriptor for them if they are more than certain lines lenght
	 * @param root root element for searching
	 * @param document	document for searching
	 * @param c	PsiElement class to search for
	 * @param startMargin beginning margin for collapsable block
	 * @param endMargin end margin for collapsable block
	 * @param <T> PsiElement subclass
	 * @return list of folding descriptors
	 */
	private <T extends PsiElement> List<FoldingDescriptor> getDescriptorsFor(@NotNull PsiElement root, @NotNull Document document, Class<? extends T> c, int startMargin, int endMargin)
	{
		List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

		// Anon arrays
		for (final T block : PsiTreeUtil.findChildrenOfType(root, c))
		{
			TextRange range = block.getTextRange();
			int startOffset = range.getStartOffset()+startMargin;
			int endOffset = range.getEndOffset()-endMargin;
			int startLine = document.getLineNumber(startOffset);
			int endLine = document.getLineNumber(endOffset);

			if( endLine - startLine > 2 )
				descriptors.add(new FoldingDescriptor(block.getNode(),new TextRange(startOffset, endOffset)));
		}
		return descriptors;
	}

	@Nullable
	@Override
	public String getPlaceholderText(@NotNull ASTNode node)
	{
		IElementType elementType = node.getElementType();

		if( elementType == PerlElementTypes.BLOCK)
			return "{code block}";
		else if ( elementType == PerlElementTypes.ANON_ARRAY)
			return "[array]";
		else if ( elementType == PerlElementTypes.ANON_HASH)
			return "{hash}";
		else if ( elementType == PerlElementTypes.PARENTHESISED_EXPR)
			return "(list expression...)";
		else if ( elementType == PerlElementTypes.PERL_HEREDOC)
			return "<< heredoc >>";
		else if ( elementType == PerlElementTypes.PERL_POD)
			return "= POD block =";
		else if ( elementType == PerlElementTypes.TEMPLATE_BLOCK_HTML)
			return ">? HTML block <?";
		else if ( elementType == PerlElementTypes.PERL_COMMENT_BLOCK)
			return "# Block comment";
		else if ( elementType == PerlElementTypes.PERL_COMMENT)
			return "# comments...";
		else if ( elementType == PerlElementTypes.USE_STATEMENT || elementType == PerlElementTypes.REQUIRE_STATEMENT)
			return "imports...";
		else
			return "unknown entity";
	}

	@Override
	public boolean isCollapsedByDefault(@NotNull ASTNode node)
	{
		IElementType elementType = node.getElementType();
		if ( elementType == PerlElementTypes.PERL_COMMENT_BLOCK)
			return true;
		else if ( elementType == PerlElementTypes.PERL_POD)
			return true;
		else if ( elementType == PerlElementTypes.PERL_COMMENT)
			return true;
		else if ( elementType == PerlElementTypes.USE_STATEMENT || elementType == PerlElementTypes.REQUIRE_STATEMENT)
			return true;
		else
			return false;
	}
}

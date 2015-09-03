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

package com.perl5.lang.perl.idea.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.perl5.lang.perl.idea.formatter.settings.PerlCodeStyleSettings;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hurricup on 03.09.2015.
 */
public class PerlFormattingBlock extends AbstractBlock implements PerlElementTypes
{
	private final Indent myIndent;
	private final CommonCodeStyleSettings mySettings;
	private final PerlCodeStyleSettings myPerl5Settings;
	private final SpacingBuilder mySpacingBuilder;
	private final Alignment myAlignment;
	private List<Block> mySubBlocks;


	public PerlFormattingBlock(
			@NotNull ASTNode node,
			@Nullable Wrap wrap,
			@Nullable Alignment alignment,
			@NotNull CommonCodeStyleSettings codeStyleSettings,
			@NotNull PerlCodeStyleSettings perlCodeStyleSettings,
			@NotNull SpacingBuilder spacingBuilder,
			int binaryExpressionIndex
	)
	{
		super(node, wrap, alignment);
		mySettings = codeStyleSettings;
		myPerl5Settings = perlCodeStyleSettings;
		mySpacingBuilder = spacingBuilder;
		myAlignment = alignment;
		myIndent = new PerlIndentProcessor(perlCodeStyleSettings).getChildIndent(node, binaryExpressionIndex);
	}

	private static boolean shouldCreateBlockFor(ASTNode node)
	{
		return node.getTextRange().getLength() != 0 && node.getElementType() != TokenType.WHITE_SPACE;
	}

	@NotNull
	@Override
	protected List<Block> buildChildren()
	{
		if (mySubBlocks == null)
		{
			mySubBlocks = buildSubBlocks();
		}
		return new ArrayList<Block>(mySubBlocks);
	}

	private List<Block> buildSubBlocks()
	{
		final List<Block> blocks = new ArrayList<Block>();
		System.err.println("Creating sub-blocks for " + myNode);

		Alignment alignment = null;//Alignment.createAlignment();

		for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext())
		{
			if (!shouldCreateBlockFor(child)) continue;
			System.err.println("Creating sub-block for " + child);
			blocks.add(createChildBlock(myNode, child, alignment, -1));
		}

		return blocks;
	}

	private PerlFormattingBlock createChildBlock(
			ASTNode parent,
			ASTNode child,
			Alignment alignment,
			int binaryExpressionIndex
	)
	{
		return new PerlFormattingBlock(child, myWrap, alignment, mySettings, myPerl5Settings, mySpacingBuilder, binaryExpressionIndex);
	}

	@Nullable
	@Override
	public Spacing getSpacing(Block child1, Block child2)
	{
		return null;
	}

	@Override
	public boolean isLeaf()
	{
		return myNode.getFirstChildNode() == null;
	}

	@Override
	public Indent getIndent()
	{
		return myIndent;
	}

	@Nullable
	@Override
	public Alignment getAlignment()
	{
		return super.getAlignment();
	}
}

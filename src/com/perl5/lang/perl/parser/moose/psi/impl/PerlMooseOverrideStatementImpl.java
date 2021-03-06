/*
 * Copyright 2016 Alexandr Evstigneev
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

package com.perl5.lang.perl.parser.moose.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.util.IncorrectOperationException;
import com.perl5.lang.perl.idea.stubs.subsdefinitions.PerlSubDefinitionStub;
import com.perl5.lang.perl.parser.moose.psi.PerlMooseOverrideStatement;
import com.perl5.lang.perl.parser.moose.psi.PerlMoosePsiUtil;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.mixins.PerlSubDefinitionBaseImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 25.11.2015.
 */
public class PerlMooseOverrideStatementImpl extends PerlSubDefinitionBaseImpl<PerlSubDefinitionStub> implements PerlMooseOverrideStatement
{
	public PerlMooseOverrideStatementImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	public PerlMooseOverrideStatementImpl(@NotNull PerlSubDefinitionStub stub, @NotNull IStubElementType nodeType)
	{
		super(stub, nodeType);
	}

	@Nullable
	@Override
	public PsiReference[] getReferences(PsiElement element)
	{
		return PerlMoosePsiUtil.getModifiersNameReference(getExpr(), element);
	}


	@Override
	public PsiPerlBlock getBlockSmart()
	{
		return null;
	}

	@Nullable
	@Override
	public PsiElement getSignatureContainer()
	{
		return null;
	}


	@Nullable
	public PsiPerlExpr getExpr()
	{
		return findChildByClass(PsiPerlExpr.class);
	}

	@Override
	public PsiElement getSubNameElement()
	{
		PsiElement expr = getExpr();

		if (expr instanceof PsiPerlParenthesisedExpr)
		{
			expr = expr.getFirstChild();
			if (expr != null)
			{
				expr = expr.getNextSibling();
			}
		}

		if (expr instanceof PsiPerlCommaSequenceExpr)
		{
			PsiElement nameContainer = expr.getFirstChild();
			if (nameContainer instanceof PerlString)
			{
				return nameContainer;
			}
		}

		return null;
	}

	@Override
	protected String getSubNameHeavy()
	{
		PsiElement nameContainer = getSubNameElement();

		if (nameContainer instanceof PerlString)
		{
			return ((PerlString) nameContainer).getStringContent();
		}

		return null;
	}

	@Override
	public boolean isMethod()
	{
		return true;
	}

	@Override
	public PsiElement setName(@NotNull String name) throws IncorrectOperationException
	{
		if (name.isEmpty())
		{
			throw new IncorrectOperationException("You can't set an empty method name");
		}

		PsiElement nameIdentifier = getNameIdentifier();
		if (nameIdentifier instanceof PerlString)
		{
			((PerlString) nameIdentifier).setStringContent(name);
		}

		return this;
	}
}


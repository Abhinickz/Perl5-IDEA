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

package com.perl5.lang.perl.psi.mixins;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.perl5.lang.perl.psi.PerlStringContentElement;
import com.perl5.lang.perl.psi.PsiPerlHeredocOpener;
import com.perl5.lang.perl.psi.impl.PerlNamedElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 07.08.2015.
 */
public abstract class PerlHeredocOpenerMixin extends PerlNamedElementImpl implements PsiPerlHeredocOpener
{
	public PerlHeredocOpenerMixin(@NotNull ASTNode node)
	{
		super(node);
	}

	@Nullable
	@Override
	public PsiElement getNameIdentifier()
	{
		return getStringNameIdentifier();
	}

	public PerlStringContentElement getStringNameIdentifier()
	{
		return PsiTreeUtil.findChildOfType(this, PerlStringContentElement.class);
	}

	@Nullable
	@Override
	public String getName()
	{
		PerlStringContentElement stringNameIdentifier = getStringNameIdentifier();
		if (stringNameIdentifier != null)
			return stringNameIdentifier.getName();
		return null;
	}

	@Override
	public PsiElement setName(@NotNull String name) throws IncorrectOperationException
	{
		PerlStringContentElement stringNameIdentifier = getStringNameIdentifier();
		if (stringNameIdentifier != null)
			return stringNameIdentifier.setName(name);
		return null;
	}


}

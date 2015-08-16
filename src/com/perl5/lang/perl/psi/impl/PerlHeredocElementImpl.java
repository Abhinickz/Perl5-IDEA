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

package com.perl5.lang.perl.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 10.06.2015.
 */
public class PerlHeredocElementImpl extends ASTWrapperPsiElement
{
	public PerlHeredocElementImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	//	public PerlHeredocElementImpl(IElementType type, CharSequence text)
//	{
//		super(type, text);
//	}
//
//	@Override
//	public void accept(@NotNull PsiElementVisitor visitor)
//	{
//		if (visitor instanceof PerlVisitor) ((PerlVisitor) visitor).visitHeredocElement(this);
//		else super.accept(visitor);
//	}

//	@NotNull
//	@Override
//	public LiteralTextEscaper<PsiCommentImpl> createLiteralTextEscaper()
//	{
//		return new PerlHeredocLiteralEscaper(this);
//	}
}

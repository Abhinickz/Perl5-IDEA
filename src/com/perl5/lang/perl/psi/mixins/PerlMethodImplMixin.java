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
import com.perl5.lang.perl.psi.PsiPerlMethod;
import com.perl5.lang.perl.psi.PerlNamespace;
import com.perl5.lang.perl.psi.PerlSubName;
import com.perl5.lang.perl.psi.PsiPerlObjectElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 24.05.2015.
 */
public abstract class PerlMethodImplMixin extends PerlPackageMemberMixin implements PsiPerlMethod
{
	public PerlMethodImplMixin(@NotNull ASTNode node){
		super(node);
	}

	@Override
	public String getExplicitPackageName()
	{
		PerlNamespace namespace = getNamespaceElement();

		if( namespace != null )
			return namespace.getName();
		else
		{
			PsiPerlObjectElement object = getObjectElement();
			if( object != null )
				return object.guessNamespace();
		}

		return null;
	}

	@Override
	public PerlNamespace getNamespaceElement()
	{
		return findChildByClass(PerlNamespace.class);
	}

	@Override
	public PerlSubName getSubNameElement()
	{
		return findChildByClass(PerlSubName.class);
	}

	@Override
	public boolean hasExplicitNamespace()
	{
		return getNamespaceElement() != null || getObjectElement() != null;
	}
}

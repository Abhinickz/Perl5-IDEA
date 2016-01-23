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

package com.perl5.lang.perl.psi.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.IncorrectOperationException;
import com.perl5.lang.perl.extensions.PerlRenameUsagesSubstitutor;
import com.perl5.lang.perl.psi.references.resolvers.PerlSubReferenceResolver;
import org.jetbrains.annotations.NotNull;

public class PerlSubReference extends PerlSubReferenceSimple
{
	private static final ResolveCache.PolyVariantResolver<PerlSubReference> RESOLVER = new PerlSubReferenceResolver();

	public PerlSubReference(@NotNull PsiElement element, TextRange textRange)
	{
		super(element, textRange);
	}

	@NotNull
	@Override
	public ResolveResult[] multiResolve(boolean incompleteCode)
	{
		return ResolveCache.getInstance(myElement.getProject()).resolveWithCaching(this, RESOLVER, true, false);
	}

	@Override
	public TextRange getRangeInElement()
	{
		TextRange range = super.getRangeInElement();

/*
		// fixme this should be some kinda interface
		PsiElement resolveResult = resolve();
		if( resolveResult instanceof PerlClassAccessorDeclaration && ((PerlClassAccessorDeclaration) resolveResult).isFollowsBestPractice() && range.getEndOffset() > 4)
		{
			range = new TextRange(4, range.getEndOffset());
		}
*/

		return range;
	}

	@Override
	public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException
	{
		PsiElement target = resolve();
		if (target instanceof PerlRenameUsagesSubstitutor)
		{
			newElementName = ((PerlRenameUsagesSubstitutor) target).getSubstitutedUsageName(newElementName, myElement);
		}
		return super.handleElementRename(newElementName);
	}
}

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

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import com.intellij.util.IncorrectOperationException;
import com.perl5.lang.perl.psi.PerlNamespaceDefinition;
import com.perl5.lang.perl.psi.PerlNamespaceElement;
import com.perl5.lang.perl.psi.PerlStringContentElement;
import com.perl5.lang.perl.psi.PsiPerlNamespaceDefinition;
import com.perl5.lang.perl.util.PerlPackageUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hurricup on 28.05.2015.
 */
public class PerlNamespaceReference extends PerlReferencePoly
{
	private final String canonicalPackageName;

	public PerlNamespaceReference(@NotNull PsiElement element, TextRange textRange)
	{
		super(element, textRange);

		if (element instanceof PerlNamespaceElement)
			canonicalPackageName = ((PerlNamespaceElement) element).getCanonicalName();
		else if (element instanceof PerlStringContentElement)
			canonicalPackageName = PerlPackageUtil.getCanonicalPackageName(element.getText());
		else
			throw new RuntimeException("Incorrect referencable element: " + element.getClass());

		String text = element.getText();
		if (text.endsWith("::"))
			setRangeInElement(new TextRange(0, text.length() - 2));
		else if (text.endsWith("'"))
		{
			setRangeInElement(new TextRange(0, text.length() - 1));
		}
	}

	@NotNull
	@Override
	public Object[] getVariants()
	{
		return new Object[0];
	}

	@NotNull
	@Override
	public ResolveResult[] multiResolve(boolean incompleteCode)
	{
		Project project = myElement.getProject();
		List<ResolveResult> result = new ArrayList<>();

		PsiElement parent = myElement.getParent();

		for (PsiPerlNamespaceDefinition namespaceDefinition : PerlPackageUtil.getNamespaceDefinitions(project, canonicalPackageName))
		{
			if (!parent.isEquivalentTo(namespaceDefinition))
				result.add(new PsiElementResolveResult(namespaceDefinition));
		}

		return result.toArray(new ResolveResult[result.size()]);
	}


	@Override
	public boolean isReferenceTo(PsiElement element)
	{
		PsiElement parent = element.getParent();
		if (parent instanceof PerlNamespaceDefinition)
			return super.isReferenceTo(element) || super.isReferenceTo(parent);
		;
		return super.isReferenceTo(element);
	}

	@Override
	public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException
	{
		return super.handleElementRename(newElementName);
	}
}

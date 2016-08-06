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
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.util.IncorrectOperationException;
import com.perl5.PerlIcons;
import com.perl5.lang.perl.idea.stubs.PerlSubBaseStub;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.utils.PerlPsiUtil;
import com.perl5.lang.perl.psi.utils.PerlReturnType;
import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import com.perl5.lang.perl.util.PerlPackageUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

import static com.perl5.lang.perl.lexer.PerlElementTypesGenerated.SUB_NAME;

/**
 * Created by hurricup on 05.06.2015.
 */
public abstract class PerlSubBaseImpl<Stub extends PerlSubBaseStub> extends PerlStubBasedPsiElementBase<Stub> implements PerlSubBase<Stub>
{
	public PerlSubBaseImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	public PerlSubBaseImpl(@NotNull Stub stub, @NotNull IStubElementType nodeType)
	{
		super(stub, nodeType);
	}

	@Nullable
	@Override
	public String getPackageName()
	{
		Stub stub = getStub();
		if (stub != null)
		{
			return stub.getPackageName();
		}

		String namespace = getExplicitPackageName();
		if (namespace == null)
		{
			namespace = getContextPackageName();
		}

		return namespace;
	}

	@Nullable
	@Override
	public PsiElement getNameIdentifier()
	{
		return getSubNameElement();
	}

	@Override
	public PsiElement setName(@NotNull String name) throws IncorrectOperationException
	{
		PsiElement subNameElement = getSubNameElement();
		if (subNameElement != null)
		{
			PerlPsiUtil.renameElement(subNameElement, name);
		}

		return this;
	}

	@Override
	public String getName()
	{
		return getSubName();
	}

	@Nullable
	@Override
	public String getCanonicalName()
	{
		String packageName = getPackageName();
		if (packageName == null)
		{
			return null;
		}

		return packageName + PerlPackageUtil.PACKAGE_SEPARATOR + getSubName();
	}

	@Override
	public String getSubName()
	{
		Stub stub = getStub();
		if (stub != null)
		{
			return stub.getSubName();
		}

		return getSubNameHeavy();
	}

	protected String getSubNameHeavy()
	{
		PsiElement subNameElement = getSubNameElement();
		if (subNameElement != null)
		{
			String subNameText = subNameElement.getText();
			int namespaceDelimiterIndex = subNameText.lastIndexOf(':');
			return namespaceDelimiterIndex == -1 ? subNameText : subNameText.substring(namespaceDelimiterIndex + 1);
		}

		return null;
	}

	@Nullable
	@Override
	public String getContextPackageName()
	{
		return PerlPackageUtil.getContextPackageName(this);
	}

	@Nullable
	@Override
	public String getExplicitPackageName()
	{
		PsiElement subNameElement = getSubNameElement();
		if (subNameElement == null)
		{
			return null;
		}
		String subNameText = subNameElement.getText();
		int namespaceDelimiterIndex = subNameText.lastIndexOf(':');
		return namespaceDelimiterIndex == -1 ? null : PerlPackageUtil.getCanonicalPackageName(subNameText.substring(0, namespaceDelimiterIndex + 1));
	}

	@Override
	public PsiElement getSubNameElement()
	{
		return findChildByType(SUB_NAME);
	}

	@NotNull
	@Override
	public List<PerlAnnotation> getAnnotationList()
	{
		return PerlPsiUtil.collectAnnotations(this);
	}

	@Override
	public PerlSubAnnotations getSubAnnotations()
	{
		Stub stub = getStub();
		if (stub != null)
		{
			return stub.getSubAnnotations();
		}

		PerlSubAnnotations myAnnotations = new PerlSubAnnotations();

		for (PerlAnnotation annotation : getAnnotationList())
		{
			if (annotation instanceof PsiPerlAnnotationAbstract)
			{
				myAnnotations.setIsAbstract(true);
			}
			else if (annotation instanceof PsiPerlAnnotationDeprecated)
			{
				myAnnotations.setIsDeprecated(true);
			}
			else if (annotation instanceof PsiPerlAnnotationMethod)
			{
				myAnnotations.setIsMethod(true);
			}
			else if (annotation instanceof PsiPerlAnnotationOverride)
			{
				myAnnotations.setIsOverride(true);
			}
			else if (annotation instanceof PsiPerlAnnotationReturns) // returns
			{
				PsiElement possibleNamespace = annotation.getLastChild();
				if (possibleNamespace instanceof PerlNamespaceElement)
				{
					myAnnotations.setReturns(((PerlNamespaceElement) possibleNamespace).getCanonicalName());
					myAnnotations.setReturnType(PerlReturnType.REF);
					// todo implement brackets and braces
				}
			}
		}

		return myAnnotations;
	}

	@Override
	public boolean isDeprecated()
	{
		return getSubAnnotations().isDeprecated();
	}

	@Override
	public boolean isMethod()
	{
		return getSubAnnotations().isMethod();
	}

	@Override
	public boolean isStatic()
	{
		return !isMethod();
	}

	@Override
	public boolean isXSub()
	{
		return false;
	}

	@Nullable
	@Override
	public Icon getIcon(int flags)
	{
		if (isMethod())
		{
			return PerlIcons.METHOD_GUTTER_ICON;
		}
		else
		{
			return PerlIcons.SUB_GUTTER_ICON;
		}
	}


	@Override
	public int getTextOffset()
	{
		PsiElement nameIdentifier = getNameIdentifier();

		return nameIdentifier == null
				? super.getTextOffset()
				: nameIdentifier.getTextOffset();
	}

	@Override
	public String toString()
	{
		return "sub " + getCanonicalName();
	}
}

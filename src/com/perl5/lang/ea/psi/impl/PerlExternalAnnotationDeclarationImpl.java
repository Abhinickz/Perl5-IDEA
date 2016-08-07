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

package com.perl5.lang.ea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.ea.psi.PerlExternalAnnotationDeclaration;
import com.perl5.lang.ea.psi.PerlExternalAnnotationNamespace;
import com.perl5.lang.perl.idea.stubs.subsdeclarations.PerlSubDeclarationStub;
import com.perl5.lang.perl.psi.mixins.PerlSubDeclarationImplMixin;
import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 06.08.2016.
 */
public class PerlExternalAnnotationDeclarationImpl extends PerlSubDeclarationImplMixin implements PerlExternalAnnotationDeclaration
{
	public PerlExternalAnnotationDeclarationImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	public PerlExternalAnnotationDeclarationImpl(@NotNull PerlSubDeclarationStub stub, @NotNull IStubElementType nodeType)
	{
		super(stub, nodeType);
	}

	@Nullable
	@Override
	public String getContextPackageName()
	{
		PerlExternalAnnotationNamespace namespace = PsiTreeUtil.getParentOfType(this, PerlExternalAnnotationNamespace.class);
		return namespace == null ? null : namespace.getPackageName();
	}

	@Nullable
	@Override
	public PerlSubAnnotations getExternalSubAnnotations()
	{
		return null;
	}
}

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

package com.perl5.lang.pod.parser.psi.mixin;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.perl5.lang.pod.parser.psi.*;
import com.perl5.lang.pod.parser.psi.stubs.PodSectionStub;
import com.perl5.lang.pod.parser.psi.util.PodRenderUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 26.03.2016.
 */
public abstract class PodStubBasedSectionMixin extends StubBasedPsiElementBase<PodSectionStub> implements PodStubBasedSection
{
	public PodStubBasedSectionMixin(@NotNull PodSectionStub stub, @NotNull IStubElementType nodeType)
	{
		super(stub, nodeType);
	}

	public PodStubBasedSectionMixin(@NotNull ASTNode node)
	{
		super(node);
	}

	@Override
	public PsiElement getTitleBlock()
	{
		return findChildByClass(PodSectionTitle.class);
	}

	@Override
	public PsiElement getContentBlock()
	{
		return findChildByClass(PodSectionContent.class);
	}

	@Override
	public void renderElement(StringBuilder builder, PodRenderingContext context)
	{

		renderElementTitle(builder, new PodRenderingContext());
		renderElementContent(builder, context);
	}

	public void renderElementTitle(StringBuilder builder, PodRenderingContext context)
	{
		PsiElement content = getTitleBlock();
		PodRenderUtil.renderPsiRange(content, content, builder, context);
	}

	public void renderElementContent(StringBuilder builder, PodRenderingContext context)
	{
		PsiElement content = getContentBlock();
		PodRenderUtil.renderPsiRange(content, content, builder, context);
	}

	@Override
	@Nullable
	public String getTitleText()
	{
		PodSectionStub stub = getStub();
		if (stub != null)
			return stub.getTitleText();

		return getTitleTextFromPsi();
	}

	@Nullable
	protected String getTitleTextFromPsi()
	{
		PsiElement titleElement = getTitleBlock();
		return titleElement == null ? null : titleElement.getText();
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "(" + getElementType().toString() + ")";
	}

	@Override
	public boolean isIndexed()
	{
		return findChildByClass(PodFormatterX.class) != null;
	}

}

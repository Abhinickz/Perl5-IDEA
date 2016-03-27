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

package com.perl5.lang.pod.parser.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.perl5.lang.pod.PodLanguage;
import com.perl5.lang.pod.filetypes.PodFileType;
import com.perl5.lang.pod.parser.psi.PodFile;
import com.perl5.lang.pod.parser.psi.PodRenderingContext;
import com.perl5.lang.pod.parser.psi.util.PodRenderUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by hurricup on 21.04.2015.
 */
public class PodFileImpl extends PsiFileBase implements PodFile
{
	public PodFileImpl(@NotNull FileViewProvider viewProvider)
	{
		super(viewProvider, PodLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public FileType getFileType()
	{
		return PodFileType.INSTANCE;
	}

	@Override
	public String toString()
	{
		return "POD file";
	}

	@Override
	public Icon getIcon(int flags)
	{
		return super.getIcon(flags);
	}

	// fixme this is debugging method
	public String getAsHTML()
	{
		StringBuilder builder = new StringBuilder();
		renderElementAsHTML(builder, new PodRenderingContext());
		return builder.toString();
	}

	// fixme this is debugging method
	public String getAsText()
	{
		StringBuilder builder = new StringBuilder();
		renderElementAsText(builder, new PodRenderingContext());
		return builder.toString();
	}

	@Override
	public void renderElementAsHTML(StringBuilder builder, PodRenderingContext context)
	{
		PodRenderUtil.renderPsiRangeAsHTML(getFirstChild(), null, builder, context);
	}

	@Override
	public void renderElementAsText(StringBuilder builder, PodRenderingContext context)
	{
		PodRenderUtil.renderPsiRangeAsText(getFirstChild(), null, builder, context);
	}
}
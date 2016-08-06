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

package com.perl5.lang.ea;

import com.intellij.lang.PsiParser;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.perl5.lang.ea.psi.elementTypes.PerlExternalAnnotationsFileElementType;
import com.perl5.lang.ea.psi.impl.PerlExternalAnnotationsFileImpl;
import com.perl5.lang.perl.PerlParserDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 03.08.2016.
 */
public class PerlExternalAnnotationsParserDefinition extends PerlParserDefinition
{
	public static final IFileElementType FILE = new PerlExternalAnnotationsFileElementType();

	@NotNull
	@Override
	public PsiParser createParser(Project project)
	{
		return new PerlExternalAnnotationsParser();
	}

	@Override
	public PsiFile createFile(FileViewProvider viewProvider)
	{
		return new PerlExternalAnnotationsFileImpl(viewProvider);
	}

	@Override
	public IFileElementType getFileNodeType()
	{
		return FILE;
	}
}

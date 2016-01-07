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

package com.perl5.lang.mason.elementType;

import com.intellij.lang.Language;
import com.intellij.lexer.Lexer;
import com.intellij.psi.PsiElement;
import com.perl5.lang.mason.filetypes.MasonPurePerlComponentFileType;
import com.perl5.lang.perl.idea.stubs.PerlFileElementType;
import com.perl5.lang.perl.lexer.PerlLexerAdapter;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 06.01.2016.
 */
public class MasonFileElementType extends PerlFileElementType
{
	public MasonFileElementType(String debugName, Language language)
	{
		super(debugName, language);
	}

	@Nullable
	@Override
	protected Lexer getLexer(PsiElement psi)
	{
		if (psi.getContainingFile().getViewProvider().getVirtualFile().getFileType() == MasonPurePerlComponentFileType.INSTANCE)
		{
			return new PerlLexerAdapter(psi.getProject());
		}
		return super.getLexer(psi);
	}
}

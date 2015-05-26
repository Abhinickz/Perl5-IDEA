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

package com.perl5.lang.perl.idea;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.lexer.FlexAdapter;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.perl5.lang.perl.PerlParserDefinition;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.lexer.PerlLexer;
import com.perl5.lang.perl.parser.PerlPackage;
import com.perl5.lang.perl.psi.PerlUserFunction;
import com.perl5.lang.perl.psi.impl.PerlNamedElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;

/**
 * Created by hurricup on 24.05.2015.
 */
public class PerlFindUsagesProvider implements FindUsagesProvider, PerlElementTypes
{
//	private static final DefaultWordsScanner WORDS_SCANNER =
//			new DefaultWordsScanner(new FlexAdapter(new PerlLexer((Reader) null)),
//					PerlParserDefinition.IDENTIFIERS,
//					PerlParserDefinition.COMMENTS,
//					PerlParserDefinition.LITERALS
//			);

	@Nullable
	@Override
	public WordsScanner getWordsScanner() {
//		return WORDS_SCANNER; todo solve the concurrency problem
		return new DefaultWordsScanner(new FlexAdapter(new PerlLexer((Reader) null)),
				PerlParserDefinition.IDENTIFIERS,
				PerlParserDefinition.COMMENTS,
				PerlParserDefinition.LITERALS
		);

	}

	@Override
	public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
		return psiElement instanceof PsiNamedElement;
	}

	@Nullable
	@Override
	public String getHelpId(@NotNull PsiElement psiElement) {
		return null;
	}

	@NotNull
	@Override
	public String getType(@NotNull PsiElement element) {
		if (element instanceof PerlUserFunction) {
			return "function";
		} else if (element instanceof PerlPackage) {
			return "package";
		} else {
			return "";
		}
	}

	@NotNull
	@Override
	public String getDescriptiveName(@NotNull PsiElement element) {
		if (element instanceof PerlNamedElementImpl) {
			return ((PerlNamedElementImpl) element).getName();
		} else {
			return "";
		}
	}

	@NotNull
	@Override
	public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
		if (element instanceof PerlNamedElementImpl) {
			return ((PerlNamedElementImpl) element).getName();
		} else {
			return "";
		}
	}
}

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

package com.perl5.lang.perl.idea.highlighter;

import com.intellij.lang.StdLanguages;
import com.intellij.lexer.LayeredLexer;
import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.sql.dialects.mysql.MysqlLexer;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.lexer.PerlLexerAdapter;
import com.perl5.lang.pod.idea.highlighter.PodHighlightingLexer;

/**
 * Created by hurricup on 24.04.2015.
 *
 */
public class PerlHighlightningLexer extends LayeredLexer
{
	public PerlHighlightningLexer()
	{
		super(new PerlLexerAdapter());

		registerSelfStoppingLayer(
				new PodHighlightingLexer(),
				new IElementType[]{PerlElementTypes.PERL_POD},
				IElementType.EMPTY_ARRAY
		);
	}
}

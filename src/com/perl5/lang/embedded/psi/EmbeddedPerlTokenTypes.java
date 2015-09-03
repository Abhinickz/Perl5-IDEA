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

package com.perl5.lang.embedded.psi;

import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.embedded.EmbeddedPerlLanguage;

/**
 * Created by hurricup on 19.05.2015.
 */
public interface EmbeddedPerlTokenTypes
{
	IElementType TEMPLATE_BLOCK_HTML = new EmbeddedPerlTokenType("TEMPLATE_BLOCK_HTML");
	IElementType OUTER_ELEMENT_TYPE = new EmbeddedPerlElementType("OUTER_ELEMENT_TYPE");
	IElementType HTML_TEMPLATE_DATA = new TemplateDataElementType("HTML_TEMPLATE_DATA", EmbeddedPerlLanguage.INSTANCE, TEMPLATE_BLOCK_HTML, OUTER_ELEMENT_TYPE);
}

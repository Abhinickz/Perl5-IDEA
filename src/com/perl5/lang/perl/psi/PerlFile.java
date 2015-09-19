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

package com.perl5.lang.perl.psi;

import com.intellij.openapi.vfs.VirtualFile;
import com.perl5.lang.perl.psi.properties.PerlLexicalScope;

import java.util.List;

/**
 * Created by hurricup on 09.08.2015.
 */
public interface PerlFile extends PerlLexicalScope, PerlNamespaceContainer
{
	/**
	 * Checks variable type from cache or using callback guessVariableTypeHeavy
	 *
	 * @param element variable element
	 * @return variable type string
	 */
	public String getVariableType(PerlVariable element);

	/**
	 * Checks method namespace in cache or using callback getContextPackageNameHeavy
	 *
	 * @param element method element
	 * @return variable type string
	 */
	public String getMethodNamespace(PerlMethod element);

	/**
	 * Returns lib paths for current file
	 *
	 * @return lib paths list in normal order
	 */
	public List<VirtualFile> getLibPaths();
}

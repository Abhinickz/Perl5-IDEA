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

package com.perl5.lang.perl.psi.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IStubFileElementType;
import com.perl5.lang.perl.psi.stubs.globs.PerlGlobStubElementType;
import com.perl5.lang.perl.psi.stubs.namespace.definitions.PerlNamespaceDefinitionStubElementType;
import com.perl5.lang.perl.psi.stubs.subs.definitions.PerlSubDefinitionStubElementType;

/**
 * Created by hurricup on 25.05.2015.
 */
public interface PerlStubElementTypes
{
	IStubElementType SUB_DEFINITION = new PerlSubDefinitionStubElementType("SUB_DEFINITION");
	IStubElementType PERL_GLOB = new PerlGlobStubElementType("PERL_GLOB");
	IStubElementType PERL_NAMESPACE = new PerlNamespaceDefinitionStubElementType("PERL_NAMESPACE");

}

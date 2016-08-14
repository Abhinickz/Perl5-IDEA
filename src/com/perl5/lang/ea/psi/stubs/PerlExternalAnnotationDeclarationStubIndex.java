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

package com.perl5.lang.ea.psi.stubs;

import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import com.perl5.lang.ea.psi.PerlExternalAnnotationDeclaration;
import com.perl5.lang.perl.idea.stubs.PerlStubIndexesVersions;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 06.08.2016.
 */
public class PerlExternalAnnotationDeclarationStubIndex extends StringStubIndexExtension<PerlExternalAnnotationDeclaration>
{
	public static final StubIndexKey<String, PerlExternalAnnotationDeclaration> KEY = StubIndexKey.createIndexKey("perl.external.annotations");

	@Override
	public int getVersion()
	{
		return super.getVersion() + PerlStubIndexesVersions.EXTERNAL_ANNOTATIONS_SUB_INDEX_VERSION;
	}

	@NotNull
	@Override
	public StubIndexKey<String, PerlExternalAnnotationDeclaration> getKey()
	{
		return KEY;
	}

}

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

package com.perl5.lang.perl.psi.stubs.variables;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.perl5.lang.perl.psi.PerlVariable;
import com.perl5.lang.perl.psi.stubs.PerlStubElementTypes;

/**
 * Created by hurricup on 30.05.2015.
 */
public class PerlVariableStubImpl extends StubBase<PerlVariable> implements PerlVariableStub
{
	private final String myPackageName;
	private final String myVariableName;

	public PerlVariableStubImpl(StubElement parent, IStubElementType elementType, String myPackageName, String myVariableName)
	{
		super(parent, elementType);
		this.myPackageName = myPackageName;
		this.myVariableName = myVariableName;
	}

	@Override
	public String getPackageName()
	{
		return myPackageName;
	}

	@Override
	public String getVariableName()
	{
		return myVariableName;
	}
}

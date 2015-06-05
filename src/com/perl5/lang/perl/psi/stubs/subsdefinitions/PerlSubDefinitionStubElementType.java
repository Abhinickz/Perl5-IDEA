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

package com.perl5.lang.perl.psi.stubs.subsdefinitions;

import com.intellij.psi.stubs.*;
import com.perl5.lang.perl.PerlLanguage;
import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import com.perl5.lang.perl.psi.utils.PerlSubArgument;
import com.perl5.lang.perl.psi.PsiPerlSubDefinition;
import com.perl5.lang.perl.psi.impl.PsiPerlSubDefinitionImpl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hurricup on 25.05.2015.
 *
 */
public class PerlSubDefinitionStubElementType extends IStubElementType<PerlSubDefinitionStub,PsiPerlSubDefinition>
{

	public PerlSubDefinitionStubElementType(String name)
	{
		super(name, PerlLanguage.INSTANCE);
	}

	@Override
	public PsiPerlSubDefinition createPsi(@NotNull PerlSubDefinitionStub stub)
	{
		return new PsiPerlSubDefinitionImpl(stub,this);
	}

	@Override
	public PerlSubDefinitionStub createStub(@NotNull PsiPerlSubDefinition psi, StubElement parentStub)
	{
		return new PerlSubDefinitionStubImpl(parentStub, psi.getPackageName(), psi.getSubNameElement().getName(), psi.getSubArgumentsList(), psi.isMethod(), psi.getSubAnnotations());
	}

	@NotNull
	@Override
	public String getExternalId()
	{
		return "perl."+super.toString();
	}

	@Override
	public void indexStub(@NotNull PerlSubDefinitionStub stub, @NotNull IndexSink sink)
	{
		sink.occurrence(PerlSubDefinitionsStubIndex.KEY, stub.getCanonicalName());
	}

	@Override
	public void serialize(@NotNull PerlSubDefinitionStub stub, @NotNull StubOutputStream dataStream) throws IOException
	{
		dataStream.writeName(stub.getPackageName());
		dataStream.writeName(stub.getSubName());

		List<PerlSubArgument> arguments = stub.getSubArgumentsList();
		dataStream.writeInt(arguments.size());
		for( PerlSubArgument argument: arguments )
			argument.serialize(dataStream);

		stub.getSubAnnotations().serialize(dataStream);

		dataStream.writeBoolean(stub.isMethod());
	}

	@NotNull
	@Override
	public PerlSubDefinitionStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException
	{
		String packageName = dataStream.readName().toString();
		String functionName = dataStream.readName().toString();
		int argumentsNumber = dataStream.readInt();

		List<PerlSubArgument> arguments = new ArrayList<>(argumentsNumber);

		for( int i = 0; i < argumentsNumber; i++ )
			arguments.add(PerlSubArgument.deserialize(dataStream));

		PerlSubAnnotations annotations = PerlSubAnnotations.deserialize(dataStream);

		boolean isMethod = dataStream.readBoolean();

		return new PerlSubDefinitionStubImpl(parentStub,packageName,functionName,arguments,isMethod, annotations);
	}
}

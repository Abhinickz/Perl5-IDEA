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

package com.perl5.lang.perl.psi.references.resolvers;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.perl.extensions.packageprocessor.PerlExportDescriptor;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.references.PerlVariableReference;
import com.perl5.lang.perl.psi.utils.PerlVariableType;
import com.perl5.lang.perl.util.PerlArrayUtil;
import com.perl5.lang.perl.util.PerlHashUtil;
import com.perl5.lang.perl.util.PerlScalarUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hurricup on 27.09.2015.
 */
public class PerlVariableReferenceResolver implements ResolveCache.PolyVariantResolver<PerlVariableReference>
{
	@NotNull
	@Override
	public ResolveResult[] resolve(@NotNull PerlVariableReference perlVariableReference, boolean incompleteCode)
	{
		PerlVariable myVariable = perlVariableReference.getVariable();

		List<ResolveResult> result = new ArrayList<ResolveResult>();

		PsiElement variableContainer = myVariable.getParent();

		if (variableContainer instanceof PerlVariableDeclarationWrapper) // it's a declaration
		{
			return ResolveResult.EMPTY_ARRAY;
		}

		PerlVariableDeclarationWrapper lexicalDeclaration = myVariable.getLexicalDeclaration();

		if (lexicalDeclaration == null || lexicalDeclaration.isGlobalDeclaration() && !(lexicalDeclaration instanceof PerlVariableLight))
		{
			// not found explicit lexically visible declarations

			// imports
			Map<String, Set<String>> importsMap = null;
			PerlVariableType actualType = myVariable.getActualType();
			Project project = myVariable.getProject();
			PerlNamespaceContainer namespaceContainer = PsiTreeUtil.getParentOfType(myVariable, PerlNamespaceContainer.class);
			assert namespaceContainer != null;

			String variableName = myVariable.getName();

			if (actualType == PerlVariableType.SCALAR)
			{
				for (PerlExportDescriptor importEntry : namespaceContainer.getImportedScalarDescriptors())
				{
					if (importEntry.getExportedName().equals(variableName))
					{
						for (PerlVariableDeclarationWrapper targetVariable : PerlScalarUtil.getGlobalScalarDefinitions(project, importEntry.getTargetCanonicalName()))
						{
							result.add(new PsiElementResolveResult(targetVariable));
						}
					}
				}
			}
			else if (actualType == PerlVariableType.ARRAY)
			{
				for (PerlExportDescriptor importEntry : namespaceContainer.getImportedArrayDescriptors())
				{
					if (importEntry.getExportedName().equals(variableName))
					{
						for (PerlVariableDeclarationWrapper targetVariable : PerlArrayUtil.getGlobalArrayDefinitions(project, importEntry.getTargetCanonicalName()))
						{
							result.add(new PsiElementResolveResult(targetVariable));
						}
					}
				}
			}
			else if (actualType == PerlVariableType.HASH)
			{
				for (PerlExportDescriptor importEntry : namespaceContainer.getImportedHashDescriptors())
				{
					if (importEntry.getExportedName().equals(variableName))
					{
						for (PerlVariableDeclarationWrapper targetVariable : PerlHashUtil.getGlobalHashDefinitions(project, importEntry.getTargetCanonicalName()))
						{
							result.add(new PsiElementResolveResult(targetVariable));
						}
					}
				}
			}

			// our variable declaration
			for (PerlGlobVariable glob : myVariable.getRelatedGlobs())
			{
				result.add(new PsiElementResolveResult(glob));
			}

			// globs
			for (PerlVariableDeclarationWrapper globalDeclaration : myVariable.getGlobalDeclarations())
			{
				result.add(new PsiElementResolveResult(globalDeclaration));
			}
		}
		else
		{
			result.add(new PsiElementResolveResult(lexicalDeclaration));
		}

		return result.toArray(new ResolveResult[result.size()]);
	}
}

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

package com.perl5.lang.perl.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;
import com.perl5.compat.PerlStubIndex;
import com.perl5.lang.perl.extensions.packageprocessor.PerlExportDescriptor;
import com.perl5.lang.perl.idea.stubs.variables.PerlVariablesStubIndex;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.psi.PerlVariableDeclarationWrapper;
import com.perl5.lang.perl.util.processors.PerlImportsCollector;
import com.perl5.lang.perl.util.processors.PerlScalarImportsCollector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * Created by hurricup on 19.04.2015.
 */
public class PerlScalarUtil implements PerlElementTypes, PerlScalarUtilBuiltIn
{
	/**
	 * Checks if variable is built in
	 *
	 * @param variable variable name
	 * @return checking result
	 */
	public static boolean isBuiltIn(String variable)
	{
		return BUILT_IN.contains(variable);
	}

	/**
	 * Searching project files for global scalar definitions by specific package and variable name
	 *
	 * @param project       project to search in
	 * @param canonicalName canonical variable name package::name
	 * @return Collection of found definitions
	 */
	public static Collection<PerlVariableDeclarationWrapper> getGlobalScalarDefinitions(Project project, String canonicalName)
	{
		return getGlobalScalarDefinitions(project, canonicalName, GlobalSearchScope.allScope(project));
	}

	public static Collection<PerlVariableDeclarationWrapper> getGlobalScalarDefinitions(Project project, String canonicalName, GlobalSearchScope scope)
	{
		assert canonicalName != null;
		return PerlStubIndex.getElements(PerlVariablesStubIndex.KEY_SCALAR,
				canonicalName,
				project,
				scope,
				PerlVariableDeclarationWrapper.class
		);
	}


	/**
	 * Returns list of defined global scalars
	 *
	 * @param project project to search in
	 * @return collection of variable canonical names
	 */
	public static Collection<String> getDefinedGlobalScalarNames(Project project)
	{
		return PerlUtil.getIndexKeysWithoutInternals(PerlVariablesStubIndex.KEY_SCALAR, project);
	}

	/**
	 * Processes all global scalars names with specific processor
	 *
	 * @param project   project to search in
	 * @param processor string processor for suitable strings
	 * @return collection of constants names
	 */
	public static boolean processDefinedGlobalScalarNames(Project project, Processor<String> processor)
	{
		return PerlStubIndex.getInstance().processAllKeys(PerlVariablesStubIndex.KEY_SCALAR, project, processor);
	}

	/**
	 * Returns a map of imported scalars names
	 *
	 * @param project   Project to search in
	 * @param namespace namespace to search in
	 * @param file      PsiFile to search in
	 * @return result map
	 */
	@NotNull
	public static List<PerlExportDescriptor> getImportedScalarsDescritptors(@NotNull Project project, @NotNull final String namespace, @NotNull PsiFile file)
	{
		PerlImportsCollector collector = new PerlScalarImportsCollector();
		PerlUtil.processImportedEntities(project, namespace, file, collector);
		return collector.getResult();
	}

}

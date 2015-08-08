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

import com.perl5.lang.perl.psi.properties.PerlNamedElement;

import java.util.List;

/**
 * Created by hurricup on 31.05.2015.
 */
public interface PerlSubNameElement extends PerlNamedElement
{
	/**
	 * Attempts to find sub definition
	 *
	 * @return sub definition elements list
	 */
	public List<PerlSubDefinition> getSubDefinitions();

	/**
	 * Attempts to find sub declarations
	 *
	 * @return sub declarations elements list
	 */
	public List<PerlSubDeclaration> getSubDeclarations();

	/**
	 * Searching for related globs
	 *
	 * @return related globs
	 */
	public List<PerlGlobVariable> getRelatedGlobs();

	/**
	 * Searching for related constants
	 *
	 * @return related constants
	 */
	public List<PerlConstant> getConstantDefinitions();


	/**
	 * Trying to get the package name from explicit specification or by traversing
	 *
	 * @return package name for current element
	 */
	public String getPackageName();

	/**
	 * Returns fullname package::element
	 *
	 * @return name
	 */
	public String getCanonicalName();


	/**
	 * Checks if this sub name is built in
	 *
	 * @return checking result
	 */
	public boolean isBuiltIn();


}

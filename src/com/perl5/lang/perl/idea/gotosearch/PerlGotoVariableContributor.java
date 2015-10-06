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

package com.perl5.lang.perl.idea.gotosearch;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.perl5.lang.perl.psi.PerlVariableDeclarationWrapper;
import com.perl5.lang.perl.psi.PsiPerlGlobVariable;
import com.perl5.lang.perl.util.PerlArrayUtil;
import com.perl5.lang.perl.util.PerlGlobUtil;
import com.perl5.lang.perl.util.PerlHashUtil;
import com.perl5.lang.perl.util.PerlScalarUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hurricup on 12.08.2015.
 */
public class PerlGotoVariableContributor implements ChooseByNameContributor
{
	@NotNull
	@Override
	public String[] getNames(Project project, boolean includeNonProjectItems)
	{
		Collection<String> result = new ArrayList<String>();

		for (String name : PerlScalarUtil.getDefinedGlobalScalarNames(project))
			result.add("$" + name);

		for (String name : PerlArrayUtil.getDefinedGlobalArrayNames(project))
			result.add("@" + name);

		for (String name : PerlHashUtil.getDefinedGlobalHashNames(project))
			result.add("%" + name);

		for (String name : PerlGlobUtil.getDefinedGlobsNames(project))
			result.add("*" + name);

		return result.toArray(new String[result.size()]);
	}

	@NotNull
	@Override
	public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems)
	{
		Collection<PerlVariableDeclarationWrapper> result;

		if (name.startsWith("$"))
			result = PerlScalarUtil.getGlobalScalarDefinitions(project, name.substring(1));
		else if (name.startsWith("@"))
			result = PerlArrayUtil.getGlobalArrayDefinitions(project, name.substring(1));
		else if (name.startsWith("%"))
			result = PerlHashUtil.getGlobalHashDefinitions(project, name.substring(1));
		else if (name.startsWith("*"))
		{
			Collection<PsiPerlGlobVariable> globResult = PerlGlobUtil.getGlobsDefinitions(project, name.substring(1));
			return globResult.toArray(new NavigationItem[globResult.size()]);
		}
		else
			throw new RuntimeException("Cant' be");

		return result.toArray(new NavigationItem[result.size()]);
	}
}

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

package com.perl5.lang.perl.util.processors;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by hurricup on 03.09.2015.
 */
public class PerlImportsCollector extends PerlNamespaceEntityProcessor<String>
{
	final protected char mySigil;
	final protected Map<String, Set<String>> myResult;

	public PerlImportsCollector(char sigil, Map<String, Set<String>> result)
	{
		mySigil = sigil;
		myResult = result;
	}

	/**
	 * Processing entity and returns result of checking, instead of stop flag
	 *
	 * @param namespaceName namespace to check in
	 * @param entity        entity name
	 * @return check result
	 */
	protected boolean processEntity(String namespaceName, String entity)
	{
		if (entity != null && !entity.isEmpty() && entity.charAt(0) == getSigil())
		{
			if (!myResult.containsKey(namespaceName))
				myResult.put(namespaceName, new HashSet<String>());

			myResult.get(namespaceName).add(entity.substring(1));
			return true;
		}
		return false;
	}


	@Override
	public boolean process(String namespaceName, String entity)
	{
		processEntity(namespaceName, entity);
		return true;
	}

	public char getSigil()
	{
		return mySigil;
	}

	public Map<String, Set<String>> getResult()
	{
		return myResult;
	}
}

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

import com.intellij.util.Processor;

/**
 * Created by hurricup on 17.08.2015.
 */
public class PerlInternalIndexKeysProcessor implements Processor<String>
{
	private static final String MAIN_TEMPLATE = "main::";
	private final boolean myForceShortMain;

	public PerlInternalIndexKeysProcessor(boolean forceShortMain)
	{
		myForceShortMain = forceShortMain;
	}


	public PerlInternalIndexKeysProcessor()
	{
		this(false);
	}

	@Override
	public boolean process(String string)
	{
		return string.charAt(0) != '*';
	}

	public boolean isForceShortMain()
	{
		return myForceShortMain;
	}

	public String adjustName(String originalName)
	{
		if (originalName == null || !isForceShortMain() || !originalName.startsWith(MAIN_TEMPLATE))
		{
			return originalName;
		} else
		{
			return originalName.substring(4);
		}
	}
}

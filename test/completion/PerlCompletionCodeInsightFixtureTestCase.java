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

package completion;

import base.PerlLightCodeInsightFixtureTestCase;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.impl.CodeInsightTestFixtureImpl;
import com.perl5.lang.perl.idea.intellilang.PerlLanguageInjector;
import com.perl5.lang.perl.util.PerlPackageUtil;
import gnu.trove.THashSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by hurricup on 04.03.2016.
 */
public abstract class PerlCompletionCodeInsightFixtureTestCase extends PerlLightCodeInsightFixtureTestCase
{
	protected List<String> LIBRARY_PACKAGES = Arrays.asList(
			"MyTest::Some::Package",
			"MyTest::Something"
	);

	protected List<String> BUILT_IN_PACKAGES = new ArrayList<>(PerlPackageUtil.BUILT_IN_ALL);

	protected List<String> BUILT_IN_VERSIONS = Arrays.asList(
			"v5.10", "v5.12", "v5.14", "v5.16", "v5.18", "v5.20", "v5.22",
			"v5.11", "v5.13", "v5.15", "v5.17", "v5.19", "v5.9.5"
	);

	protected List<String> REF_TYPES = Arrays.asList("ARRAY", "CODE", "FORMAT", "GLOB", "HASH", "IO", "LVALUE", "REF", "Regexp", "SCALAR", "VSTRING");

	protected List<String> getLanguageMarkers()
	{
		ArrayList<String> languageMarkers = new ArrayList<>(PerlLanguageInjector.LANGUAGE_MAP.keySet());
		assert !languageMarkers.isEmpty();
		return languageMarkers;
	}

	@Override
	public void initWithFileContent(String filename, String extension, String content) throws IOException
	{
		super.initWithFileContent(filename, extension, content);
		CodeInsightTestFixtureImpl.ensureIndexesUpToDate(getProject());
		myFixture.complete(CompletionType.BASIC, 1);
	}

	public void assertCompletionIs(String... pattern)
	{
		assertCompletionIs(Arrays.asList(pattern));
	}

	@SafeVarargs
	public final void assertCompletionIs(List<String>... expected)
	{
		initWithFileSmart();
		assertLookupIs(mergeLists(expected));
	}

	@SafeVarargs
	protected final List<String> mergeLists(List<String>... sources)
	{
		Set<String> resultSet = new THashSet<>();
		for (List<String> strings : sources)
		{
			resultSet.addAll(strings);
		}
		return new ArrayList<>(resultSet);
	}
}

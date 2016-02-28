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

package parser;

/**
 * Created by hurricup on 28.02.2016.
 * Following are tests for samples from https://github.com/adamkennedy/PPI
 */
public abstract class PerlParserTestPPIBase extends PerlParserTestBase
{
	public static final String DATA_PATH = "testData/parser/ppi";

	protected abstract String getTestsGroup();
	
	@Override
	protected String getTestDataPath()
	{
		return DATA_PATH;
	}

	@Override
	public void doTest(String filename, boolean checkErrors)
	{
		super.doTest(getTestsGroup() + "/" + filename, checkErrors);
	}

}

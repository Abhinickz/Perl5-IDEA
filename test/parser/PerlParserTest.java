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

import com.intellij.psi.LanguageFileViewProviders;
import com.perl5.lang.perl.PerlLanguage;
import com.perl5.lang.perl.psi.PerlFileViewProviderFactory;

/**
 * Created by hurricup on 31.01.2016.
 */
public class PerlParserTest extends PerlParserTestBase
{
	@Override
	protected String getTestDataPath()
	{
		return "testData/parser/perl";
	}

	public void testUseVars()
	{
		doTest();
	}

	public void testIncorrectIndexes()
	{
		doTest();
	}

	public void testCharGroups()
	{
		doTest();
	}

	public void testMojoliciousHelperDeclaration()
	{
		doTest();
	}

	public void testCoreModules()
	{
		doTest();
	}

	public void testMultiTokenElements()
	{
		doTest();
	}

	public void testHandleAcceptors()
	{
		doTest();
	}

	public void testDynaLoaderCall()
	{
		doTest();
	}

	public void testFancyImport()
	{
		doTest();
	}

	public void testUniversalCan()
	{
		doTest();
	}

	public void testDefinedGlob()
	{
		doTest();
	}

	public void testCallPrecedance()
	{
		doTest(false);
	}

	public void testSortArguments()
	{
		doTest();
	}

	public void testPerlPod()
	{
		doTest();
	}

	public void testPerlPodEmpty()
	{
		doTest();
	}

	public void testPerlPodIncomplete()
	{
		doTest();
	}

	public void testQuoteLikeWithHeredocs()
	{
		doTest();
	}

	public void testVarsAndCasts()
	{
		doTest();
	}

	public void testSubPrototypes()
	{
		doTest();
	}

	public void testLookAheadEofBug()
	{
		doTest(false);
	}

	public void testVariableAttributes()
	{
		doTest();
	}

	public void testLazyParsableBlocks()
	{
		doTest();
	}

	public void testCommentInRegexp()
	{
		doTest();
	}

	public void testCommentInString()
	{
		doTest();
	}

	public void testHeredocs()
	{
		doTest();
	}

	public void testHeredocSequential()
	{
		doTest();
	}

	public void testHeredocUnclosed()
	{
		doTest();
	}

	public void testHeredocUnclosedWithEmptyMarker()
	{
		doTest();
	}


	public void testSubAttributes()
	{
		doTest();
	}

	public void testSubSignatures()
	{
		doTest();
	}

	public void testFormat()
	{
		doTest();
	}

	public void testFormatEmpty()
	{
		doTest();
	}

	public void testFormatIncomplete()
	{
		doTest(false);
	}

	public void testFormatEmptyIncomplete()
	{
		doTest(false);
	}

	public void testNamedBlocks()
	{
		doTest();
	}

	public void testVariablesAndElements()
	{
		doTest();
	}

	public void testRegexpTailingBuck()
	{
		doTest();
	}

	public void testAmbiguousSigils()
	{
		doTest();
	}

	public void testTryCatchHybrid()
	{
		doTest();
	}

	public void testHashAcceptors()
	{
		doTest();
	}

	public void testKeywordAsLabel()
	{
		doTest();
	}

	public void testKeywordsWithCore()
	{
		doTest();
	}

	public void testParserRefactoringBugs()
	{
		doTest();
	}

	public void testPostDeref()
	{
		doTest();
	}

	public void testInterpolation()
	{
		doTest();
	}

	public void testCompositeOps()
	{
		doTest();
	}

	public void testCompositeOpsSpaces()
	{
		doTest(false);
	}

	public void testAnnotation()
	{
		doTest(false);
	}

	public void testConstant()
	{
		doTest();
	}

	public void testBareUtfString()
	{
		doTest();
	}

	public void testLazyParsing()
	{
		doTest();
	}

	public void testParserTestSet()
	{
		doTest();
	}

	public void testTrickySyntax()
	{
		doTest();
	}

	public void testVariables()
	{
		doTest();
	}

	public void testMethodSignaturesSimple()
	{
		doTest();
	}

	public void testUtfIdentifiers()
	{
		doTest();
	}

	public void testVarRefactoringBugs()
	{
		doTest();
	}

	public void testPerlSwitch()
	{
		doTest();
	}

	public void testInterpolatedHashArrayElements()
	{
		doTest();
	}

	public void testImplicitRegex()
	{
		doTest();
	}

	public void testCamelcade94()
	{
		doTest(false);
	}

	public void testIssue855()
	{
		doTest();
	}

	public void testIssue867()
	{
		doTest();
	}

	public void testRegexNModifier()
	{
		doTest();
	}

	public void testUtf8PackageName()
	{
		doTest();
	}

	public void testHexBinNumbersParsing()
	{
		doTest();
	}

	public void test28BackrefStyleHeredoc()
	{
		doTest();
	}

	public void testLabelsParsing()
	{
		doTest();
	}

	public void testInterpolatedElements()
	{
		doTest();
	}

	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		LanguageFileViewProviders.INSTANCE.addExplicitExtension(PerlLanguage.INSTANCE, new PerlFileViewProviderFactory());
	}

}

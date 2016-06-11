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

import com.perl5.lang.tt2.TemplateToolkitParserDefinition;

/**
 * Created by hurricup on 06.06.2016.
 */
public class TemplateToolkitParserTest extends PerlParserTestBase
{
	public TemplateToolkitParserTest()
	{
		super("", "tt", new TemplateToolkitParserDefinition());
	}

	@Override
	protected String getTestDataPath()
	{
		return "testData/parser/tt2";
	}

	public void testOperators()
	{
		doTest("operators");
	}

	public void testPrecedence()
	{
		doTest("precedence");
	}

	public void testStrings()
	{
		doTest("strings");
	}

	public void testHash()
	{
		doTest("hash");
	}

	public void testSub()
	{
		doTest("sub");
	}

	public void testArray()
	{
		doTest("array");
	}

	public void testVariables()
	{
		doTest("variables");
	}

	public void testChomp()
	{
		doTest("chomp");
	}

	public void testComments()
	{
		doTest("comments");
	}

	public void testGet()
	{
		doTest("get");
	}

	public void testCall()
	{
		doTest("call");
	}

	public void testSet()
	{
		doTest("set");
	}

	public void testDefault()
	{
		doTest("default");
	}

	public void testInsert()
	{
		doTest("insert");
	}

	public void testInclude()
	{
		doTest("include");
	}

	public void testProcess()
	{
		doTest("process");
	}

	public void testBlockNamed()
	{
		doTest("block_named");
	}

	public void testBlockNamedUnclosed()
	{
		doTest("block_named_unclosed", false);
	}

	public void testBlockAnon()
	{
		doTest("block_anon");
	}

	public void testBlockAnonUnclosed()
	{
		doTest("block_anon_unclosed", false);
	}

	public void testWrapper()
	{
		doTest("wrapper");
	}

	public void testWrapperUnclosed()
	{
		doTest("wrapper_unclosed", false);
	}

	public void testIf()
	{
		doTest("if");
	}

	public void testIfUnclosed()
	{
		doTest("if_unclosed", false);
	}

	public void testSwitch()
	{
		doTest("switch");
	}

	public void testForeach()
	{
		doTest("foreach");
	}

	public void testForeachUnclosed()
	{
		doTest("foreach_unclosed", false);
	}

	public void testWhile()
	{
		doTest("while");
	}

	public void testWhileUnclosed()
	{
		doTest("while_unclosed", false);
	}


	public void testFilter()
	{
		doTest("filter");
	}

	public void testFilterUnclosed()
	{
		doTest("filter_unclosed", false);
	}

	public void testThrow()
	{
		doTest("throw");
	}

	public void testUse()
	{
		doTest("use");
	}

	public void testMacro()
	{
		doTest("macro");
	}

	public void testPerl()
	{
		doTest("perl");
	}

	public void testPerlUnclosed()
	{
		doTest("perl_unclosed", false);
	}

	public void testTryCatch()
	{
		doTest("try_catch");
	}

	public void testTryCatchUnclosed()
	{
		doTest("try_catch_unclosed", false);
	}

}

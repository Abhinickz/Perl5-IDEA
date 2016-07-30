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

package com.perl5.lang.perl.psi.utils;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.RefactoringFactory;
import com.perl5.lang.perl.fileTypes.PerlFileTypePackage;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.impl.PerlFileImpl;
import com.perl5.lang.perl.psi.impl.PerlNamespaceElementImpl;
import com.perl5.lang.perl.psi.impl.PerlStringContentElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerlElementFactory
{
	public static PsiElement createNewLine(Project project)
	{
		PerlFileImpl file = createFile(project, "\n");
		return file.getFirstChild();
	}


	public static PsiPerlDerefExpr createMethodCall(Project project, String packageName, String subName)
	{
		assert packageName != null;
		assert subName != null;

		PerlFileImpl file = createFile(project, String.format("%s->%s;", packageName, subName));
		PsiPerlDerefExpr def = PsiTreeUtil.findChildOfType(file, PsiPerlDerefExpr.class);
		assert def != null;
		return def;
	}

	public static PerlUseStatement createUseStatement(Project project, String packageName)
	{
		assert packageName != null;

		PerlFileImpl file = createFile(project, String.format("use %s;", packageName));
		PerlUseStatement def = PsiTreeUtil.findChildOfType(file, PerlUseStatement.class);
		assert def != null;
		return def;
	}

	// fixme probably we don't need package name and sub. just identifier
	public static PerlNamespaceElementImpl createPackageName(Project project, String name)
	{
		PerlFileImpl file = createFile(project, "package " + name + ";");
		PerlNamespaceDefinition def = PsiTreeUtil.findChildOfType(file, PerlNamespaceDefinition.class);
		assert def != null;
		return (PerlNamespaceElementImpl) def.getNamespaceElement();
	}

	public static List<PsiElement> createHereDocElements(Project project, char quoteSymbol, String markerText, String contentText)
	{
		PerlFileImpl file = createFile(project,
				String.format("<<%c%s%c\n%s\n%s\n;", quoteSymbol, markerText, quoteSymbol, contentText, markerText)
		);

		PsiElement heredocOpener = PsiTreeUtil.findChildOfType(file, PsiPerlHeredocOpener.class);
		@SuppressWarnings("ConstantConditions") PsiElement headingNewLine = heredocOpener.getNextSibling();
		PsiElement tailingNewLine = headingNewLine.getNextSibling().getNextSibling().getNextSibling();

		return new ArrayList<PsiElement>(Arrays.asList(
				heredocOpener,
				headingNewLine,
				tailingNewLine
		));
	}

	public static PerlStringContentElementImpl createStringContent(Project project, String name)
	{
		PerlFileImpl file = createFile(project, "'" + name + "';");
		PsiPerlStringSq string = PsiTreeUtil.findChildOfType(file, PsiPerlStringSq.class);
		assert string != null;
		return (PerlStringContentElementImpl) string.getFirstChild().getNextSibling();
	}

	public static PerlString createBareString(Project project, String content)
	{
		PerlFileImpl file = createFile(project, content + " => 42;");
		PerlString string = PsiTreeUtil.findChildOfType(file, PerlString.class);
		assert string != null : "While creating bare string from: " + content;
		return string;
	}

	public static PerlString createString(Project project, String code)
	{
		PerlFileImpl file = createFile(project, code + ";");
		PerlString string = PsiTreeUtil.findChildOfType(file, PerlString.class);
		assert string != null : "While creating bare string from: " + code;
		return string;
	}

	public static PsiElement createDereference(Project project)
	{
		PerlFileImpl file = createFile(project, "$a->{bla};");
		PerlVariable variable = PsiTreeUtil.findChildOfType(file, PerlVariable.class);
		assert variable != null : "While creating dereference";
		return variable.getNextSibling();
	}

	public static PsiPerlParenthesisedExpr createParenthesisedExpression(Project project)
	{
		PerlFileImpl file = createFile(project, "();");
		PsiPerlParenthesisedExpr result = PsiTreeUtil.findChildOfType(file, PsiPerlParenthesisedExpr.class);
		assert result != null : "While creating PsiPerlParenthesisedExpr";
		return result;
	}

	public static PerlFileImpl createFile(Project project, String text)
	{
		return createFile(project, text, PerlFileTypePackage.INSTANCE);
	}

	public static PerlFileImpl createFile(Project project, String text, FileType fileType)
	{
		return (PerlFileImpl) PsiFileFactory.getInstance(project).
				createFileFromText("file.dummy", fileType, text);
	}

	public static PsiPerlForCompound createIndexedFor(@NotNull Project project,
													  @NotNull PsiPerlExpr lexicalVariableDeclaration,
													  @NotNull PsiPerlForListEpxr listExpr,
													  @NotNull PsiPerlBlock forBlock)
	{
		// check if listExpr is a single array or a list expression
		boolean isSingleArray = listExpr.getFirstChild() instanceof PsiPerlArrayVariable
				&& (listExpr.getChildren().length == 1);

		String arrayName;
		if (isSingleArray) {
			PsiPerlArrayVariable childArray = (PsiPerlArrayVariable) listExpr.getFirstChild();
			arrayName = childArray.getName();
		} else {
			arrayName = "list";
		}

		// Assign iterationVariable = iterationArray[$idx]
		String assignStatementStr = String.format("%s = $%s[$idx];",
				lexicalVariableDeclaration.getText(),
				arrayName);
		PsiPerlStatement assignStatement = createPsiOfTypeFromSyntax(project, assignStatementStr, PsiPerlStatement.class);

		// Define where to insert the new statement
		List<PsiPerlStatement> statementList = forBlock.getStatementList();
		PsiElement anchorPoint = statementList.isEmpty() ? forBlock.getLastChild() : statementList.get(0);
		forBlock.addBefore(assignStatement, anchorPoint);

		String indexedForSyntax = String.format("for (my $idx = 0; $idx < scalar(@%s); $idx++) %s",
				arrayName,
				forBlock.getText());

		PsiPerlForCompound result = createPsiOfTypeFromSyntax(project, indexedForSyntax, PsiPerlForCompound.class);

		if (!isSingleArray) {
			// declare and initialize the new array before the new for
			String newListSyntax = String.format("my @%s = %s;\n", arrayName, listExpr.getText());
			PsiPerlStatement newListStatement = createPsiOfTypeFromSyntax(project, newListSyntax, PsiPerlStatement.class);
			PsiWhiteSpace newLineElement = createPsiOfTypeFromSyntax(project, newListSyntax, PsiWhiteSpace.class);
			result.addBefore(newLineElement, result.getFirstChild());
			result.addBefore(newListStatement, result.getFirstChild());
		}

		assert result != null : "While creating PsiPerlForCompound";
		return result;
	}

	private static <T extends PsiElement> T createPsiOfTypeFromSyntax(Project project, String syntax, Class<T> type)
	{
		return PsiTreeUtil.findChildOfType(createFile(project, syntax), type);
	}
}

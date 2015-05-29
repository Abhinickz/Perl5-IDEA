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
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileMoveEvent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.RefactoringBundle;
import com.intellij.refactoring.RefactoringFactory;
import com.intellij.refactoring.RenameRefactoring;
import com.intellij.refactoring.listeners.impl.RefactoringTransaction;
import com.intellij.refactoring.listeners.impl.impl.RefactoringTransactionImpl;
import com.perl5.lang.perl.idea.refactoring.RenameRefactoringQueue;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.parser.PerlPackage;
import com.perl5.lang.perl.psi.PerlNamespace;
import com.perl5.lang.perl.psi.PerlNamespaceBlock;
import com.perl5.lang.perl.psi.PerlNamespaceDefinition;
import com.perl5.lang.perl.psi.PerlSubDefinition;
import com.perl5.lang.perl.psi.impl.PerlNamespaceDefinitionImpl;
import com.perl5.lang.perl.psi.impl.PerlNamespaceImpl;
import com.perl5.lang.perl.psi.stubs.namespace.definitions.PerlNamespaceDefinitionStubIndex;
import com.perl5.lang.perl.psi.stubs.subs.definitions.PerlSubDefinitionsStubIndex;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by hurricup on 24.04.2015.
 */
public class PerlPackageUtil implements PerlElementTypes, PerlPackageUtilBuiltIn
{
	protected static final HashMap<String,IElementType> BUILT_IN_MAP = new HashMap<String,IElementType>();

	static{
		for( String packageName: BUILT_IN )
		{
			BUILT_IN_MAP.put(packageName, PERL_PACKAGE_BUILT_IN);
		}
		for( String packageName: BUILT_IN_PRAGMA )
		{
			BUILT_IN_MAP.put(packageName, PERL_PACKAGE_PRAGMA);
		}
		for( String packageName: BUILT_IN_DEPRECATED )
		{
			BUILT_IN_MAP.put(packageName, PERL_PACKAGE_DEPRECATED);
		}
	}

	/**
	 * Checks if package is built in
	 * @param variable package name
	 * @return result
	 */
	public static boolean isBuiltIn(String variable)
	{
		return BUILT_IN_MAP.containsKey(canonicalPackageName(variable));
	}

	/**
	 * Returns token type depending on package name
	 * @param variable package name
	 * @return token type
	 */
	public static IElementType getPackageType(String variable)
	{
		IElementType packageType = BUILT_IN_MAP.get(canonicalPackageName(variable));
		return packageType == null ? PERL_PACKAGE : packageType;
	}

	/**
	 * Make canonical package name, atm crude, jut chop off :: from end and begining
	 * @param name package name
	 * @return canonical package name
	 */
	public static String canonicalPackageName(String name)
	{
		if( "::".equals(name))
			return "main";
		else
			return name.replaceFirst("::$", "").replaceFirst("^::", "");
	}

	@NotNull
	public static String getElementPackageName(PsiElement element)
	{
		PerlNamespaceBlock namespaceBlock = PsiTreeUtil.getParentOfType(element, PerlNamespaceBlock.class);

		if (namespaceBlock != null)
		{
			PerlNamespaceDefinition namespaceDefinition = namespaceBlock.getNamespaceDefinition();

			if( namespaceDefinition != null ) // checking that definition is valid and got namespace
			{
				String name = namespaceDefinition.getNamespace().getName();
				assert name != null;
				return name;
			}
		}
		// default value
		return "main";
	}

	/**
	 * Searching project files for namespace definitions by specific package name
	 * @param project	project to search in
	 * @param packageName	canonical package name (without tailing ::)
	 * @return	collection of found definitions
	 */
	public static Collection<PerlNamespaceDefinition> findNamespaceDefinitions(Project project, String packageName)
	{
		assert packageName != null;

		return StubIndex.getElements(PerlNamespaceDefinitionStubIndex.KEY, packageName, project, GlobalSearchScope.projectScope(project), PerlNamespaceDefinition.class);
	}

	/**
	 * Returns list of defined package names
	 * @param project project to search in
	 * @return collection of package names
	 */
	public static Collection<String> getDefinedPackageNames(Project project)
	{
		return StubIndex.getInstance().getAllKeys(PerlSubDefinitionsStubIndex.KEY, project);
	}

	/**
	 * Builds package path from packageName Foo::Bar => Foo/Bar.pm
	 * @param packageName canonical package name
	 * @return package path
	 */
	public static String getPackagePathByName(String packageName)
	{
		return StringUtils.join(packageName.split(":+"), '/') + ".pm";
	}

	/**
	 * Translates package relative name to the package name Foo/Bar.pm => Foo::Bar
	 * @param packagePath
	 * @return
	 */
	public static String getPackageNameByPath(String packagePath)
	{
		packagePath = packagePath.replaceAll("\\\\", "/");

		return StringUtils.join(packagePath.replaceFirst("\\.pm$", "").split("/"), "::");
	}

	/**
	 * Adjusting namespaces according to the new file location
	 * @param file - file has been moved
	 * @param oldPath - previous filename
	 */
	public static void adjustMovedFileNamespaces(@NotNull RenameRefactoringQueue queue, VirtualFile file, String oldPath )
	{
		Project project = queue.getProject();
		VirtualFile newInnermostRoot = PerlUtil.findInnermostSourceRoot(project, file);

		if (newInnermostRoot != null)
		{
			String newRelativePath = VfsUtil.getRelativePath(file, newInnermostRoot);
			String newPackageName = PerlPackageUtil.getPackageNameByPath(newRelativePath);

			VirtualFile oldInnermostRoot = PerlUtil.findInnermostSourceRoot(project, oldPath);

			if( oldInnermostRoot != null )
			{
				String oldRelativePath = Paths.get(oldInnermostRoot.getPath()).relativize(Paths.get(oldPath)).toString();
				String oldPackageName = PerlPackageUtil.getPackageNameByPath(oldRelativePath);

				if( !oldPackageName.equals(newPackageName))
				{
					PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
					if( psiFile != null)
					{
						for( PerlNamespaceDefinition namespaceDefinition: PsiTreeUtil.findChildrenOfType(psiFile, PerlNamespaceDefinition.class) )
						{
							PerlNamespace namespace = namespaceDefinition.getNamespace();
							if( oldPackageName.equals(namespace.getName()))
							{
								queue.addElement(namespace,newPackageName);
							}
						}
					}
				}
			}
		}
	}

	public static void handlePackagePathChange(RenameRefactoringQueue queue, VirtualFile directory, String oldPath)
	{
		Project project = queue.getProject();
		VirtualFile directorySourceRoot = PerlUtil.findInnermostSourceRoot(project, directory);

		if (directorySourceRoot != null)
		{
			for( VirtualFile file: VfsUtil.collectChildrenRecursively(directory))
			{
				if( !file.isDirectory() && "pm".equals(file.getExtension()) && directorySourceRoot.equals(PerlUtil.findInnermostSourceRoot(project, file)) )
				{
					String relativePath = VfsUtil.getRelativePath(file, directory);
					String oldFilePath = oldPath + "/" + relativePath;
					adjustMovedFileNamespaces(queue, file, oldFilePath);
				}
			}
		}
	}
}

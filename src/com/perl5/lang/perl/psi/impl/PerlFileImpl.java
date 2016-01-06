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

package com.perl5.lang.perl.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.ObjectStubTree;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubTreeLoader;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.perl.PerlLanguage;
import com.perl5.lang.perl.extensions.packageprocessor.PerlLibProvider;
import com.perl5.lang.perl.extensions.packageprocessor.PerlPackageProcessor;
import com.perl5.lang.perl.filetypes.PerlFileTypePackage;
import com.perl5.lang.perl.idea.stubs.imports.PerlUseStatementStub;
import com.perl5.lang.perl.idea.stubs.imports.runtime.PerlRuntimeImportStub;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.mro.PerlMro;
import com.perl5.lang.perl.psi.mro.PerlMroC3;
import com.perl5.lang.perl.psi.mro.PerlMroDfs;
import com.perl5.lang.perl.psi.mro.PerlMroType;
import com.perl5.lang.perl.psi.properties.PerlLexicalScope;
import com.perl5.lang.perl.psi.utils.PerlLexicalDeclaration;
import com.perl5.lang.perl.psi.utils.PerlPsiUtil;
import com.perl5.lang.perl.psi.utils.PerlVariableType;
import com.perl5.lang.perl.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hurricup on 26.04.2015.
 */
public class PerlFileImpl extends PsiFileBase implements PerlFile
{
	private static final ArrayList<String> EMPTY_LIST = new ArrayList<String>();
	protected ConcurrentHashMap<PerlVariable, String> VARIABLE_TYPES_CACHE = new ConcurrentHashMap<PerlVariable, String>();
	protected ConcurrentHashMap<PerlMethod, String> METHODS_NAMESAPCES_CACHE = new ConcurrentHashMap<PerlMethod, String>();
	protected GlobalSearchScope myElementsResolveScope;
	List<PerlLexicalDeclaration> declaredScalars = new ArrayList<PerlLexicalDeclaration>();
	List<PerlLexicalDeclaration> declaredArrays = new ArrayList<PerlLexicalDeclaration>();
	List<PerlLexicalDeclaration> declaredHashes = new ArrayList<PerlLexicalDeclaration>();
	List<PerlLexicalDeclaration> declaredVariables = new ArrayList<PerlLexicalDeclaration>();
	boolean lexicalCacheInvalid = true;

	public PerlFileImpl(@NotNull FileViewProvider viewProvider, Language language)
	{
		super(viewProvider, language);
	}

	public PerlFileImpl(@NotNull FileViewProvider viewProvider)
	{
		super(viewProvider, PerlLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public FileType getFileType()
	{
		return getViewProvider().getFileType();
	}

	@Override
	public PerlLexicalScope getLexicalScope()
	{
		return null;
	}

	/**
	 * Returns package name for this psi file. Name built from filename and innermost root.
	 *
	 * @return canonical package name or null if it's not pm file or it's not in source root
	 */
	@Nullable
	public String getFilePackageName()
	{
		VirtualFile containingFile = getVirtualFile();

		if (containingFile.getFileType() == PerlFileTypePackage.INSTANCE)
		{
			VirtualFile innermostSourceRoot = PerlUtil.getFileClassRoot(getProject(), containingFile);
			if (innermostSourceRoot != null)
			{
				String relativePath = VfsUtil.getRelativePath(containingFile, innermostSourceRoot);
				return PerlPackageUtil.getPackageNameByPath(relativePath);
			}
		}
		return null;
	}

	@Override
	public void subtreeChanged()
	{
		super.subtreeChanged();
		lexicalCacheInvalid = true;
		VARIABLE_TYPES_CACHE.clear();
		METHODS_NAMESAPCES_CACHE.clear();
		myElementsResolveScope = null;
	}

	/**
	 * Creates new lexicalVariables scanner or notifies it to restart scanning
	 */
	private synchronized void rescanLexicalVariables()
	{
		if (lexicalCacheInvalid)
		{
//			System.err.println("Started scanning declarations");
			List<PerlLexicalDeclaration> declaredScalars = new ArrayList<PerlLexicalDeclaration>();
			List<PerlLexicalDeclaration> declaredArrays = new ArrayList<PerlLexicalDeclaration>();
			List<PerlLexicalDeclaration> declaredHashes = new ArrayList<PerlLexicalDeclaration>();
			List<PerlLexicalDeclaration> declaredVariables = new ArrayList<PerlLexicalDeclaration>();

			Collection<PerlVariableDeclarationWrapper> declarationWrappers = PsiTreeUtil.findChildrenOfType(this, PerlVariableDeclarationWrapper.class);

			for (PerlVariableDeclarationWrapper declarationWrapper : declarationWrappers)
			{
				PerlVariable variable = declarationWrapper.getVariable();
				assert variable != null;
				// lexically ok
				PerlLexicalScope variableScope = variable.getLexicalScope();
				assert variableScope != null;

				PerlLexicalDeclaration variableDeclaration = new PerlLexicalDeclaration(declarationWrapper, variableScope);
				if (variable instanceof PsiPerlScalarVariable)
				{
					declaredScalars.add(variableDeclaration);
				}
				else if (variable instanceof PsiPerlArrayVariable)
				{
					declaredArrays.add(variableDeclaration);
				}
				else if (variable instanceof PsiPerlHashVariable)
				{
					declaredHashes.add(variableDeclaration);
				}
				else
				{
					throw new RuntimeException("Unknown variable declaration: " + variable);
				}
				declaredVariables.add(variableDeclaration);
			}

			this.declaredScalars = declaredScalars;
			this.declaredArrays = declaredArrays;
			this.declaredHashes = declaredHashes;
			this.declaredVariables = declaredVariables;
			lexicalCacheInvalid = false;
//			System.err.println("Finished scanning declarations");
		}
	}

	/**
	 * Searching for most recent lexically visible variable declaration
	 *
	 * @param currentVariable variable to search declaration for
	 * @return variable in declaration term or null if there is no such one
	 */
	public PerlVariableDeclarationWrapper getLexicalDeclaration(PerlVariable currentVariable)
	{
		if (lexicalCacheInvalid)
			rescanLexicalVariables();

		String currentVariableName = currentVariable.getName();
		if (currentVariableName == null)
			return null;

		PerlLexicalScope currentScope = currentVariable.getLexicalScope();
		PerlVariableType variableType = currentVariable.getActualType();

		int currentStatementOffset;

		PsiElement currentStatement = PerlPsiUtil.getElementStatement(currentVariable);

		if (currentStatement == null)
			return null;
//			throw new RuntimeException("Unable to find current variable statement: " + currentVariableName); // atm happens on bad recovery

		currentStatementOffset = currentStatement.getTextOffset();

		List<PerlLexicalDeclaration> knownDeclarations;

		if (variableType == PerlVariableType.SCALAR)
			knownDeclarations = declaredScalars;
		else if (variableType == PerlVariableType.ARRAY)
			knownDeclarations = declaredArrays;
		else if (variableType == PerlVariableType.HASH)
			knownDeclarations = declaredHashes;
		else
			throw new RuntimeException("Unable to find declarations for variable type " + variableType);

		ListIterator<PerlLexicalDeclaration> iterator = knownDeclarations.listIterator(knownDeclarations.size());
		while (iterator.hasPrevious())
		{
			PerlLexicalDeclaration declaration = iterator.previous();
			if (declaration.getTextOffset() < currentStatementOffset
					&& currentVariableName.equals(declaration.getDeclarationWrapper().getName())
					&& PsiTreeUtil.isAncestor(declaration.getScope(), currentScope, false))
				return declaration.getDeclarationWrapper();
		}

		return null;
	}

	/**
	 * Searches for lexically visible variables declarations relatively to the current element
	 *
	 * @return list of visible variables
	 */
	public Collection<PerlVariableDeclarationWrapper> getVisibleLexicalVariables(PsiElement currentElement)
	{
		if (lexicalCacheInvalid)
			rescanLexicalVariables();

		HashMap<String, PerlVariableDeclarationWrapper> declarationsHash = new HashMap<String, PerlVariableDeclarationWrapper>();

		PerlLexicalScope currentScope = PsiTreeUtil.getParentOfType(currentElement, PerlLexicalScope.class);
		assert currentScope != null;

		PsiElement currentStatement = PerlPsiUtil.getElementStatement(currentElement);

		if (currentStatement == null)
			throw new RuntimeException("Unable to find current element statement");

		int currentStatementOffset = currentStatement.getTextOffset();

		ListIterator<PerlLexicalDeclaration> iterator = declaredVariables.listIterator(declaredVariables.size());
		while (iterator.hasPrevious())
		{
			PerlLexicalDeclaration declaration = iterator.previous();
			if (declaration.getTextOffset() < currentStatementOffset)
			{
				// todo actually, we should use variable as a key or canonical variable name WITHOUT package and possible braces
				String variableName = declaration.getDeclarationWrapper().getName();

				if (declarationsHash.get(variableName) == null
						&& PsiTreeUtil.isAncestor(declaration.getScope(), currentScope, false))
					declarationsHash.put(variableName, declaration.getDeclarationWrapper());
			}
		}

		return declarationsHash.values();
	}

	@Override
	public String getPackageName()
	{
		return PerlPackageUtil.MAIN_PACKAGE;
	}

	@Override
	public List<String> getParentNamespaces()
	{
		return EMPTY_LIST;
	}

	@Override
	public PerlMroType getMroType()
	{
		return PerlMroType.DFS;
	}

	@Override
	public PerlMro getMro()
	{
		if (getMroType() == PerlMroType.DFS)
			return PerlMroDfs.INSTANCE;
		else
			return PerlMroC3.INSTANCE;
	}

	@Override
	public String getVariableType(PerlVariable element)
	{
		if (VARIABLE_TYPES_CACHE.containsKey(element))
		{
			String type = VARIABLE_TYPES_CACHE.get(element);
			return type.isEmpty() ? null : type;
		}

		String type = element.guessVariableTypeHeavy();
		VARIABLE_TYPES_CACHE.put(element, type == null ? "" : type);
		return getVariableType(element);
	}

	@Override
	public String getMethodNamespace(PerlMethod element)
	{
		if (METHODS_NAMESAPCES_CACHE.containsKey(element))
		{
//			System.err.println("Got cached type for method " + element.getText() + " at " + element.getTextOffset());
			String type = METHODS_NAMESAPCES_CACHE.get(element);
			return type.isEmpty() ? null : type;
		}

		String type = element.getContextPackageNameHeavy();
		METHODS_NAMESAPCES_CACHE.put(element, type == null ? "" : type);
		return getMethodNamespace(element);
	}

	@Override
	public Map<String, Set<String>> getImportedSubsNames()
	{
		return PerlSubUtil.getImportedSubs(getProject(), PerlPackageUtil.MAIN_PACKAGE, this);
	}

	@Override
	public Map<String, Set<String>> getImportedScalarNames()
	{
		return PerlScalarUtil.getImportedScalars(getProject(), PerlPackageUtil.MAIN_PACKAGE, this);
	}

	@Override
	public Map<String, Set<String>> getImportedArrayNames()
	{
		return PerlArrayUtil.getImportedArrays(getProject(), PerlPackageUtil.MAIN_PACKAGE, this);
	}

	@Override
	public Map<String, Set<String>> getImportedHashNames()
	{
		return PerlHashUtil.getImportedHashes(getProject(), PerlPackageUtil.MAIN_PACKAGE, this);
	}

	@Override
	public
	@NotNull
	List<VirtualFile> getLibPaths()
	{
		List<VirtualFile> result = new ArrayList<VirtualFile>();

		// libdirs providers
		for (PerlUseStatement useStatement : PsiTreeUtil.findChildrenOfType(this, PerlUseStatement.class))
		{
			PerlPackageProcessor packageProcessor = useStatement.getPackageProcessor();
			if (packageProcessor instanceof PerlLibProvider)
			{
				((PerlLibProvider) packageProcessor).addLibDirs(useStatement, result);
			}
		}

		// classpath
		result.addAll(Arrays.asList(ProjectRootManager.getInstance(getProject()).orderEntries().getClassesRoots()));

		// current dir
		VirtualFile virtualFile = getVirtualFile();
		if (virtualFile != null)
		{
			result.add(virtualFile.getParent());
		}

		return result;
	}

//	@Override
//	@NotNull
//	public GlobalSearchScope getElementsResolveScope()
//	{
//		if (myElementsResolveScope != null)
//			return myElementsResolveScope;
//
//		long t = System.currentTimeMillis();
//		Set<VirtualFile> filesToSearch = new THashSet<VirtualFile>();
//		collectIncludedFiles(filesToSearch);
//		myElementsResolveScope = GlobalSearchScope.filesScope(getProject(), filesToSearch);
//
//		System.err.println("Collected in ms: " + (System.currentTimeMillis() - t) / 1000);
//
//		return myElementsResolveScope;
//	}

	@Override
	public void collectIncludedFiles(Set<VirtualFile> includedVirtualFiles)
	{
		if (!includedVirtualFiles.contains(getVirtualFile()))
		{
			includedVirtualFiles.add(getVirtualFile());

			StubElement fileStub = getStub();

			if (fileStub == null)
			{
				System.err.println("Collecting from psi for " + getVirtualFile());
				collectRequiresFromPsi(this, includedVirtualFiles);
			}
			else
			{
				System.err.println("Collecting from stubs for " + getVirtualFile());
				collectRequiresFromStub(fileStub, includedVirtualFiles);
			}
		}
	}

	protected void collectRequiresFromVirtualFile(VirtualFile virtualFile, Set<VirtualFile> includedVirtualFiles)
	{
		if (!includedVirtualFiles.contains(virtualFile))
		{
			includedVirtualFiles.add(virtualFile);

			ObjectStubTree objectStubTree = StubTreeLoader.getInstance().readOrBuild(getProject(), virtualFile, null);
			if (objectStubTree != null)
			{
				System.err.println("Collecting from stub for " + virtualFile);
				collectRequiresFromStub((PsiFileStub) objectStubTree.getRoot(), includedVirtualFiles);
			}
			else
			{
				System.err.println("Collecting from psi for " + virtualFile);
				PsiFile targetPsiFile = PsiManager.getInstance(getProject()).findFile(virtualFile);
				if (targetPsiFile != null)
					collectRequiresFromPsi(targetPsiFile, includedVirtualFiles);
			}
		}
	}

	protected void collectRequiresFromStub(@NotNull StubElement currentStub, Set<VirtualFile> includedVirtualFiles)
	{
		VirtualFile virtualFile = null;
		if (currentStub instanceof PerlUseStatementStub)
		{
			String packageName = ((PerlUseStatementStub) currentStub).getPackageName();
			if (packageName != null)
			{
				virtualFile = resolvePackageNameToVirtualFile(packageName);
			}

		}
		if (currentStub instanceof PerlRuntimeImportStub)
		{
			String importPath = ((PerlRuntimeImportStub) currentStub).getImportPath();
			if (importPath != null)
			{
				virtualFile = resolveRelativePathToVirtualFile(importPath);
			}
		}

		if (virtualFile != null)
		{
			collectRequiresFromVirtualFile(virtualFile, includedVirtualFiles);
		}

		for (Object childStub : currentStub.getChildrenStubs())
		{
			assert childStub instanceof StubElement : childStub.getClass();
			collectRequiresFromStub((StubElement) childStub, includedVirtualFiles);
		}
	}


	/**
	 * Collects required files from psi structure, used in currently modified document
	 *
	 * @param includedVirtualFiles set of already collected virtual files
	 */
	protected void collectRequiresFromPsi(PsiFile psiFile, Set<VirtualFile> includedVirtualFiles)
	{
		for (PsiElement importStatement : PsiTreeUtil.<PsiElement>findChildrenOfAnyType(psiFile, PerlUseStatement.class, PerlDoExpr.class))
		{
			VirtualFile virtualFile = null;
			if (importStatement instanceof PerlUseStatement)
			{
				String packageName = ((PerlUseStatement) importStatement).getPackageName();
				if (packageName != null)
				{
					virtualFile = resolvePackageNameToVirtualFile(packageName);
				}
			}
			else if (importStatement instanceof PerlDoExpr)
			{
				String importPath = ((PerlDoExpr) importStatement).getImportPath();

				if (importPath != null)
				{
					virtualFile = resolveRelativePathToVirtualFile(((PerlDoExpr) importStatement).getImportPath());
				}
			}

			if (virtualFile != null)
			{
				collectRequiresFromVirtualFile(virtualFile, includedVirtualFiles);
			}
		}
	}

	@Nullable
	@Override
	public PsiFile resolvePackageNameToPsi(String canonicalPackageName)
	{
		// resolves to a psi file
		return resolveRelativePathToPsi(PerlPackageUtil.getPackagePathByName(canonicalPackageName));
	}

	@Nullable
	@Override
	public VirtualFile resolvePackageNameToVirtualFile(String canonicalPackageName)
	{
		// resolves to a psi file
		return resolveRelativePathToVirtualFile(PerlPackageUtil.getPackagePathByName(canonicalPackageName));
	}

	@Nullable
	@Override
	public PsiFile resolveRelativePathToPsi(String relativePath)
	{
		VirtualFile targetFile = resolveRelativePathToVirtualFile(relativePath);

		if (targetFile != null)
		{
			PsiFile targetPsiFile = PsiManager.getInstance(getProject()).findFile(targetFile);
			if (targetPsiFile != null)
				return targetPsiFile;
		}

		return null;
	}

	@Override
	public VirtualFile resolveRelativePathToVirtualFile(String relativePath)
	{
		if (relativePath != null)
		{
			for (VirtualFile classRoot : getLibPaths())
			{
				if (classRoot != null)
				{
					VirtualFile targetFile = classRoot.findFileByRelativePath(relativePath);
					if (targetFile != null)
					{
						return targetFile;
					}
				}
			}
		}

		return null;
	}
}

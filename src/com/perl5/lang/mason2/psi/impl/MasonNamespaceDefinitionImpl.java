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

package com.perl5.lang.mason2.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.Processor;
import com.perl5.lang.mason2.MasonUtils;
import com.perl5.lang.mason2.idea.configuration.MasonSettings;
import com.perl5.lang.mason2.idea.configuration.VariableDescription;
import com.perl5.lang.mason2.psi.MasonNamespaceDefinition;
import com.perl5.lang.mason2.psi.stubs.MasonNamespaceDefitnitionsStubIndex;
import com.perl5.lang.mason2.psi.stubs.MasonParentNamespacesStubIndex;
import com.perl5.lang.perl.PerlLanguage;
import com.perl5.lang.perl.idea.stubs.namespaces.PerlNamespaceDefinitionStub;
import com.perl5.lang.perl.psi.PerlNamespaceDefinition;
import com.perl5.lang.perl.psi.PerlNamespaceElement;
import com.perl5.lang.perl.psi.PerlVariableDeclarationWrapper;
import com.perl5.lang.perl.psi.impl.PerlVariableLightImpl;
import com.perl5.lang.perl.psi.impl.PsiPerlNamespaceDefinitionImpl;
import com.perl5.lang.perl.util.PerlPackageUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hurricup on 05.01.2016.
 */
public class MasonNamespaceDefinitionImpl extends PsiPerlNamespaceDefinitionImpl implements MasonNamespaceDefinition
{
	protected final List<PerlVariableDeclarationWrapper> IMPLICIT_VARIABLES = new ArrayList<PerlVariableDeclarationWrapper>();
	protected int masonSettingsChangeCounter;

	public MasonNamespaceDefinitionImpl(ASTNode node)
	{
		super(node);
		fillImplicitVariables();
	}

	public MasonNamespaceDefinitionImpl(PerlNamespaceDefinitionStub stub, IStubElementType nodeType)
	{
		super(stub, nodeType);
		fillImplicitVariables();
	}

	protected void fillImplicitVariables()
	{
		IMPLICIT_VARIABLES.clear();

		if (isValid())
		{
			MasonSettings masonSettings = MasonSettings.getInstance(getProject());
			for (VariableDescription variableDescription : masonSettings.globalVariables)
			{
				String variableType = variableDescription.variableType;
				if (StringUtil.isEmpty(variableType))
				{
					variableType = null;
				}
				IMPLICIT_VARIABLES.add(
						new PerlVariableLightImpl(
								getManager(),
								PerlLanguage.INSTANCE,
								variableDescription.variableName,
								variableType,
								false,
								false,
								false,
								this
						));
			}
			masonSettingsChangeCounter = masonSettings.getChangeCounter();
		}
	}

	@Override
	public PerlNamespaceElement getNamespaceElement()
	{
		return null;
	}

	@Override
	public String getPackageName()
	{
		String absoluteComponentPath = getAbsoluteComponentPath();
		if (absoluteComponentPath != null)
		{
			return MasonUtils.getClassnameFromPath(absoluteComponentPath);
		}
		return null;
	}

	@Override
	protected String getPackageNameHeavy()
	{
		String packageName = MasonUtils.getVirtualFileClassName(getProject(), getContainingFile().getContainingVirtualFile());
		return packageName == null ? PerlPackageUtil.MAIN_PACKAGE : packageName;
	}

	@Override
	public List<PerlNamespaceDefinition> getParentNamespaceDefinitions()
	{
		List<String> parentsPaths;
		PerlNamespaceDefinitionStub stub = getStub();
		if (stub != null)
		{
			parentsPaths = stub.getParentNamespaces();
		}
		else
		{
			parentsPaths = getParentNamespacesNamesFromPsi();
		}

		VirtualFile containingFile = getContainingFile().getContainingVirtualFile();
		List<PerlNamespaceDefinition> parentsNamespaces;

		if (!parentsPaths.isEmpty() && containingFile != null)
		{
			parentsNamespaces = MasonUtils.collectComponentNamespacesByPaths(getProject(), parentsPaths, containingFile.getParent());
		}
		else
		{
			String autobaseParent = getParentNamespaceFromAutobase();
			if (autobaseParent != null)
			{
				parentsNamespaces = MasonUtils.getMasonNamespacesByAbsolutePath(getProject(), autobaseParent);
			}
			else
			{
				parentsNamespaces = new ArrayList<PerlNamespaceDefinition>();
			}
		}

		if (parentsNamespaces.isEmpty())
		{
			parentsNamespaces.addAll(PerlPackageUtil.getNamespaceDefinitions(getProject(), MASON_DEFAULT_COMPONENT_PARENT));
		}

		return parentsNamespaces;
	}

	@Nullable
	@Override
	public String getAbsoluteComponentPath()
	{
		VirtualFile containingFile = getContainingFile().getContainingVirtualFile();
		if (containingFile != null)
		{
			return VfsUtil.getRelativePath(containingFile, getProject().getBaseDir());
		}

		return null;
	}

	@Nullable
	@Override
	public String getComponentPath()
	{
		VirtualFile containingFile = getContainingFile().getContainingVirtualFile();
		if (containingFile != null)
		{
			VirtualFile containingRoot = MasonUtils.getComponentRoot(getProject(), containingFile);
			if (containingRoot != null)
			{
				return VfsUtil.getRelativePath(containingFile, containingRoot);
			}
		}
		return null;
	}

	@Nullable
	protected String getParentNamespaceFromAutobase()
	{
		// autobase
		VirtualFile componentRoot = getContainingFile().getComponentRoot();
		VirtualFile containingFile = getContainingFile().getContainingVirtualFile();

		if (componentRoot != null && containingFile != null)
		{
			VirtualFile parentComponentFile = getParentComponentFile(componentRoot, containingFile.getParent(), containingFile);
			if (parentComponentFile != null) // found autobase class
			{
				String componentPath = VfsUtil.getRelativePath(parentComponentFile, getProject().getBaseDir());

				if (componentPath != null)
				{
					return componentPath;
				}
			}
		}
		return null;
	}

	/**
	 * Recursively traversing paths and looking for autobase
	 *
	 * @param componentRoot    component root we are search in
	 * @param currentDirectory directory we are currently in	 *
	 * @param childFile        current file (just to speed things up)
	 * @return parent component virtual file or null if not found
	 */
	@Nullable
	private VirtualFile getParentComponentFile(VirtualFile componentRoot, VirtualFile currentDirectory, VirtualFile childFile)
	{
		// check in current dir
		List<String> autobaseNames = new ArrayList<String>(MasonSettings.getInstance(getProject()).autobaseNames);

		if (childFile.getParent().equals(currentDirectory) && autobaseNames.contains(childFile.getName())) // avoid cyclic inheritance
		{
			autobaseNames = autobaseNames.subList(0, autobaseNames.lastIndexOf(childFile.getName()));
		}

		for (int i = autobaseNames.size() - 1; i >= 0; i--)
		{
			VirtualFile potentialParent = VfsUtil.findRelativeFile(currentDirectory, autobaseNames.get(i));
			if (potentialParent != null && potentialParent.exists() && !potentialParent.equals(childFile))
			{
				return potentialParent;
			}
		}

		// move up or exit
		if (!componentRoot.equals(currentDirectory))
		{
			return getParentComponentFile(componentRoot, currentDirectory.getParent(), childFile);
		}
		return null;
	}

	@NotNull
	@Override
	public List<PerlNamespaceDefinition> getChildNamespaceDefinitions()
	{
		MasonSettings masonSettings = MasonSettings.getInstance(getProject());
		final List<PerlNamespaceDefinition> childNamespaces = new ArrayList<PerlNamespaceDefinition>();

		// collect psi children
		final Project project = getProject();
		final GlobalSearchScope projectScope = GlobalSearchScope.projectScope(project);
		final String componentPath = getComponentPath();
		if (componentPath != null)
		{

			StubIndex.getInstance().processAllKeys(MasonParentNamespacesStubIndex.KEY, project, new Processor<String>()
			{
				@Override
				public boolean process(String parentPath)
				{
					if (parentPath.charAt(0) == VfsUtil.VFS_SEPARATOR_CHAR) // absolute path, should be equal
					{
						if (componentPath.equals(parentPath.substring(1)))
						{
							childNamespaces.addAll(StubIndex.getElements(
									MasonParentNamespacesStubIndex.KEY,
									parentPath,
									project,
									projectScope,
									MasonNamespaceDefinition.class
							));
						}
					}
					else if (componentPath.endsWith(parentPath))    // relative path
					{
						for (MasonNamespaceDefinition masonNamespaceDefinition : StubIndex.getElements(
								MasonParentNamespacesStubIndex.KEY,
								parentPath,
								project,
								projectScope,
								MasonNamespaceDefinition.class
						))
						{
							if (masonNamespaceDefinition.getParentNamespaceDefinitions().contains(MasonNamespaceDefinitionImpl.this))
							{
								childNamespaces.add(masonNamespaceDefinition);
							}
						}
					}

					return true;
				}
			});
		}

		// collect autobased children
		if (masonSettings.autobaseNames.contains(getContainingFile().getName()))
		{
			VirtualFile containingFile = getContainingFile().getContainingVirtualFile();
			if (containingFile != null)
			{
				final String basePath = VfsUtil.getRelativePath(containingFile.getParent(), getProject().getBaseDir());

				if (basePath != null)
				{
					StubIndex.getInstance().processAllKeys(
							MasonNamespaceDefitnitionsStubIndex.KEY, getProject(), new Processor<String>()
							{
								@Override
								public boolean process(String componentPath)
								{
									if (componentPath.startsWith(basePath))
									{
										for (MasonNamespaceDefinition namespaceDefinition : StubIndex.getElements(
												MasonNamespaceDefitnitionsStubIndex.KEY,
												componentPath,
												project,
												projectScope,
												MasonNamespaceDefinition.class
										))
										{
											if (namespaceDefinition.getParentNamespaceDefinitions().contains(MasonNamespaceDefinitionImpl.this)
													&& !childNamespaces.contains(namespaceDefinition)
													)
											{
												childNamespaces.add(namespaceDefinition);
											}
										}
									}
									return true;
								}
							}
					);
				}

			}

		}
		return childNamespaces;
	}

	@Override
	public String getPresentableName()
	{
		VirtualFile componentRoot = getContainingFile().getComponentRoot();
		VirtualFile containingFile = getContainingFile().getContainingVirtualFile();

		if (componentRoot != null && containingFile != null)
		{
			String componentPath = VfsUtil.getRelativePath(containingFile, componentRoot);

			if (componentPath != null)
			{
				return VfsUtil.VFS_SEPARATOR_CHAR + componentPath;
			}
		}

		return super.getPresentableName();
	}

	@NotNull
	@Override
	public MasonFileImpl getContainingFile()
	{
		return (MasonFileImpl) super.getContainingFile();
	}

	@NotNull
	@Override
	public List<PerlVariableDeclarationWrapper> getImplicitVariables()
	{
		if (masonSettingsChangeCounter != MasonSettings.getInstance(getProject()).getChangeCounter())
		{
			fillImplicitVariables();
		}
		return IMPLICIT_VARIABLES;
	}

}

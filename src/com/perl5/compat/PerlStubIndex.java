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

package com.perl5.compat;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Created by hurricup on 07.04.2016.
 * Proxy class to implement restrictions from IDEA2016.2
 */
public class PerlStubIndex extends StubIndex implements ApplicationComponent
{
	private final PerlIndexAccessValidator myAccessValidator = new PerlIndexAccessValidator();

	public static PerlStubIndex getInstance()
	{
		return StubIndexHolder.ourInstance;
	}

	@Override
	public void initComponent()
	{

	}

	@Override
	public void disposeComponent()
	{

	}

	public <Key, Psi extends PsiElement> boolean isIndexAvailable(@NotNull StubIndexKey<Key, Psi> indexKey)
	{
		return myAccessValidator.isIndexAvailable(indexKey);
	}

	@NotNull
	@Override
	public String getComponentName()
	{
		return "Stub indexes proxy for Perl5 plugin";
	}

	@Override
	public <Key, Psi extends PsiElement> Collection<Psi> get(@NotNull StubIndexKey<Key, Psi> indexKey, @NotNull Key key, @NotNull Project project, @Nullable GlobalSearchScope scope)
	{
		myAccessValidator.checkAndStartProcessingActivityForIndex(indexKey);
		Collection<Psi> psis = StubIndex.getInstance().get(indexKey, key, project, scope);
		myAccessValidator.stoppedProcessingActivityForIndex(indexKey);
		return psis;
	}

	@Override
	public <Key, Psi extends PsiElement> boolean processElements(@NotNull StubIndexKey<Key, Psi> indexKey, @NotNull Key key, @NotNull Project project, @Nullable GlobalSearchScope scope, Class<Psi> requiredClass, @NotNull Processor<? super Psi> processor)
	{
		myAccessValidator.checkAndStartProcessingActivityForIndex(indexKey);
		boolean b = StubIndex.getInstance().processElements(indexKey, key, project, scope, requiredClass, processor);
		myAccessValidator.stoppedProcessingActivityForIndex(indexKey);
		return b;
	}

	@NotNull
	@Override
	public <Key> Collection<Key> getAllKeys(@NotNull StubIndexKey<Key, ?> indexKey, @NotNull Project project)
	{
		myAccessValidator.checkAndStartProcessingActivityForIndex(indexKey);
		Collection<Key> allKeys = StubIndex.getInstance().getAllKeys(indexKey, project);
		myAccessValidator.stoppedProcessingActivityForIndex(indexKey);
		return allKeys;
	}

	@Override
	public <K> boolean processAllKeys(@NotNull StubIndexKey<K, ?> indexKey, @NotNull Project project, Processor<K> processor)
	{
		myAccessValidator.checkAndStartProcessingActivityForIndex(indexKey);
		boolean b = StubIndex.getInstance().processAllKeys(indexKey, project, processor);
		myAccessValidator.stoppedProcessingActivityForIndex(indexKey);
		return b;
	}

	@Override
	public void forceRebuild(@NotNull Throwable e)
	{
		StubIndex.getInstance().forceRebuild(e);
	}

	private static class StubIndexHolder
	{
		private static final PerlStubIndex ourInstance = ApplicationManager.getApplication().getComponent(PerlStubIndex.class);
	}

}

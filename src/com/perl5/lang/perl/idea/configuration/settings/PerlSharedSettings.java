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

package com.perl5.lang.perl.idea.configuration.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NullableLazyValue;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.HashMap;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Transient;
import com.perl5.lang.perl.idea.PerlPathMacros;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;

/**
 * Created by hurricup on 30.08.2015.
 */
@State(
		name = "Perl5Settings",
		storages = {
				@Storage(id = "default", file = StoragePathMacros.PROJECT_FILE),
				@Storage(id = "dir", file = PerlPathMacros.PERL5_PROJECT_SHARED_SETTINGS_FILE, scheme = StorageScheme.DIRECTORY_BASED)
		}
)

public class PerlSharedSettings implements PersistentStateComponent<PerlSharedSettings>
{
	@Deprecated
	public List<String> libRootUrls = new ArrayList<String>();
	public Map<String, List<String>> libRootUrlsMap = new HashMap<String, List<String>>();

	public List<String> selfNames = new ArrayList<String>(Arrays.asList("self", "this", "class", "proto"));
	public boolean SIMPLE_MAIN_RESOLUTION = true;
	public boolean AUTOMATIC_HEREDOC_INJECTIONS = true;
	public boolean ALLOW_INJECTIONS_WITH_INTERPOLATION = false;
	public boolean PERL_CRITIC_ENABLED = false;
	public boolean PERL_ANNOTATOR_ENABLED = false;
	public boolean PERL_TRY_CATCH_ENABLED = false;
	public String PERL_DEPARSE_ARGUMENTS = "";
	public String PERL_TIDY_ARGS = "";
	public String PERL_CRITIC_ARGS = "";

	private String myAnnotationsPath = "project.annotations";

	@Transient
	private transient NullableLazyValue<VirtualFile> myAnnotationsLazyRoot;

	@Transient
	private Project myProject;
	@Transient
	private Set<String> SELF_NAMES_SET = null;

	public PerlSharedSettings(Project project)
	{
		myProject = project;
	}

	private PerlSharedSettings()
	{
	}

	public static PerlSharedSettings getInstance(@NotNull Project project)
	{
		PerlSharedSettings persisted = ServiceManager.getService(project, PerlSharedSettings.class);
		return persisted != null ? persisted : new PerlSharedSettings(project);
	}

	@Nullable
	@Override
	public PerlSharedSettings getState()
	{
		return this;
	}

	@Override
	public void loadState(PerlSharedSettings state)
	{
		XmlSerializerUtil.copyBean(state, this);
	}

	public void settingsUpdated()
	{
		SELF_NAMES_SET = null;
	}

	public boolean isSelfName(String name)
	{
		if (SELF_NAMES_SET == null)
		{
			SELF_NAMES_SET = new THashSet<String>(selfNames);
		}
		return SELF_NAMES_SET.contains(name);
	}

	public void setDeparseOptions(String optionsString)
	{
		while (optionsString.length() > 0 && optionsString.charAt(0) != '-')
		{
			optionsString = optionsString.substring(1);
		}
		PERL_DEPARSE_ARGUMENTS = optionsString;
	}

	@NotNull
	public List<String> getLibRootUrlsForModule(@NotNull Module module)
	{
		return getLibRootUrlsForModule(module.getName());
	}

	@NotNull
	public List<String> getLibRootUrlsForModule(@NotNull String moduleName)
	{
		List<String> urlsMap = libRootUrlsMap.get(moduleName);

		// migrating to per-module config
		if (urlsMap == null)
		{
			urlsMap = new ArrayList<String>();
			if (libRootUrls != null)
			{
				urlsMap.addAll(libRootUrls);
			}
			setLibRootUrlsForModule(moduleName, urlsMap);
		}

		return urlsMap;
	}

	public void setLibRootUrlsForModule(@NotNull Module module, @NotNull List<String> urlsList)
	{
		setLibRootUrlsForModule(module.getName(), urlsList);
	}

	public void setLibRootUrlsForModule(@NotNull String moduleName, @NotNull List<String> urlsList)
	{
		libRootUrlsMap.put(moduleName, urlsList);
	}

	@Nullable
	public List<String> deleteLibRootUrlsForModule(@NotNull String moduleName)
	{
		return libRootUrlsMap.remove(moduleName);
	}

	public String getAnnotationsPath()
	{
		return myAnnotationsPath;
	}

	public void setAnnotationsPath(String annotationsPath)
	{
		myAnnotationsPath = annotationsPath;

		synchronized (this)
		{
			myAnnotationsLazyRoot = null;
		}
	}

	@Nullable
	public synchronized VirtualFile getAnnotationsRoot()
	{
		if (myAnnotationsLazyRoot == null)
		{
			myAnnotationsLazyRoot = new NullableLazyValue<VirtualFile>()
			{
				@Nullable
				@Override
				protected VirtualFile compute()
				{
					VirtualFile baseDir = myProject.getBaseDir();
					VirtualFile result = baseDir.findFileByRelativePath(myAnnotationsPath);

					if (result != null)
					{
						if (result.isDirectory())
						{
							return result;
						}
					}
					else
					{
						try
						{
							return VfsUtil.createDirectoryIfMissing(baseDir, myAnnotationsPath);
						}
						catch (IOException ignore)
						{
						}
					}
					return null;
				}
			};
		}
		return myAnnotationsLazyRoot.getValue();
	}

}

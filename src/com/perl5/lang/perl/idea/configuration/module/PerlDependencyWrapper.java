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

package com.perl5.lang.perl.idea.configuration.module;

import com.intellij.openapi.roots.ExportableOrderEntry;
import com.intellij.openapi.roots.OrderEntry;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by hurricup on 28.08.2016.
 */
public abstract class PerlDependencyWrapper<Entry extends OrderEntry>
{
	protected Entry myOrderEntry;

	public PerlDependencyWrapper(Entry orderEntry)
	{
		myOrderEntry = orderEntry;
	}

	public String getName()
	{
		return myOrderEntry.getPresentableName();
	}

	public Entry getOrderEntry()
	{
		return myOrderEntry;
	}

	public abstract String getType();

	@Nullable
	Icon getIcon()
	{
		return null;
	}

	public boolean isExportable()
	{
		return myOrderEntry instanceof ExportableOrderEntry;
	}

	public boolean isExported()
	{
		return isExportable() && ((ExportableOrderEntry) myOrderEntry).isExported();
	}

	public void setExported(boolean newValue)
	{
		if (isExportable())
		{
			((ExportableOrderEntry) myOrderEntry).setExported(newValue);
		}
	}

	boolean isConfigurable()
	{
		return true;
	}
}

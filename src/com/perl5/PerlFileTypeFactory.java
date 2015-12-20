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

package com.perl5;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.perl5.lang.perl.filetypes.PerlFileType;
import com.perl5.lang.perl.filetypes.PerlFileTypePackage;
import com.perl5.lang.perl.filetypes.PerlFileTypeTest;
import org.jetbrains.annotations.NotNull;

public class PerlFileTypeFactory extends FileTypeFactory
{
	@Override
	public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer)
	{
		fileTypeConsumer.consume(PerlFileTypePackage.INSTANCE, "pm");
		fileTypeConsumer.consume(PerlFileType.INSTANCE, "cgi");
		fileTypeConsumer.consume(PerlFileType.INSTANCE, "pl");
		fileTypeConsumer.consume(PerlFileType.INSTANCE, "ph");

		fileTypeConsumer.consume(PerlFileTypeTest.INSTANCE, "t");
	}
}

package com.perl5;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.perl5.lang.embedded.EmbeddedPerlFileType;
import com.perl5.lang.perl.files.PerlFileTypePackage;
import com.perl5.lang.perl.files.PerlFileTypeScript;
import com.perl5.lang.pod.PodFileType;
import com.perl5.lang.xs.XSFileType;
import org.jetbrains.annotations.NotNull;

public class PerlTypeFactory extends FileTypeFactory
{
	@Override
	public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
		fileTypeConsumer.consume(PerlFileTypePackage.INSTANCE, "pm");
		fileTypeConsumer.consume(PerlFileTypeScript.INSTANCE, "cgi");
		fileTypeConsumer.consume(PerlFileTypeScript.INSTANCE, "pl");
		fileTypeConsumer.consume(PerlFileTypeScript.INSTANCE, "ph");
		fileTypeConsumer.consume(PodFileType.INSTANCE, "pod");
		fileTypeConsumer.consume(XSFileType.INSTANCE, "xs");
		fileTypeConsumer.consume(EmbeddedPerlFileType.INSTANCE, "thtml");
		fileTypeConsumer.consume(EmbeddedPerlFileType.INSTANCE, "phtml");
	}
}

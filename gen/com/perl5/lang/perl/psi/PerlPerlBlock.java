// This is a generated file. Not intended for manual editing.
package com.perl5.lang.perl.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PerlPerlBlock extends PsiElement {

  @NotNull
  List<PerlCodeLine> getCodeLineList();

  @NotNull
  List<PerlFunctionDefinition> getFunctionDefinitionList();

  @NotNull
  List<PerlIfBlock> getIfBlockList();

  @NotNull
  List<PerlPackageNamespace> getPackageNamespaceList();

  @NotNull
  List<PerlPerlBlock> getPerlBlockList();

}

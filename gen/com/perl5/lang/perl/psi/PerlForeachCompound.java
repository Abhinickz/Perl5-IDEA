// This is a generated file. Not intended for manual editing.
package com.perl5.lang.perl.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PerlForeachCompound extends PsiElement {

  @Nullable
  PerlBlockCompound getBlockCompound();

  @Nullable
  PerlCallable getCallable();

  @NotNull
  List<PerlExpr> getExprList();

  @Nullable
  PerlVariableDeclarationGlobal getVariableDeclarationGlobal();

  @Nullable
  PerlVariableDeclarationLexical getVariableDeclarationLexical();

  @Nullable
  PerlVariableDeclarationLocal getVariableDeclarationLocal();

}

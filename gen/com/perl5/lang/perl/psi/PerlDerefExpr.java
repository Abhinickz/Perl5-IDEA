// This is a generated file. Not intended for manual editing.
package com.perl5.lang.perl.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PerlDerefExpr extends PerlExpr {

  @NotNull
  List<PerlCallable> getCallableList();

  @NotNull
  List<PerlExpr> getExprList();

  @NotNull
  List<PerlRightwardCall> getRightwardCallList();

  @NotNull
  List<PerlScalarCall> getScalarCallList();

}

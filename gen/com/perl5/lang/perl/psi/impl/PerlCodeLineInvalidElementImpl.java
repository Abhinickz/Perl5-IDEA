// This is a generated file. Not intended for manual editing.
package com.perl5.lang.perl.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.perl5.lang.perl.lexer.PerlElementTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.perl5.lang.perl.psi.*;

public class PerlCodeLineInvalidElementImpl extends ASTWrapperPsiElement implements PerlCodeLineInvalidElement {

  public PerlCodeLineInvalidElementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PerlVisitor) ((PerlVisitor)visitor).visitCodeLineInvalidElement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PerlArrayValue getArrayValue() {
    return findChildByClass(PerlArrayValue.class);
  }

  @Override
  @Nullable
  public PerlFunction getFunction() {
    return findChildByClass(PerlFunction.class);
  }

  @Override
  @Nullable
  public PerlFunctionCall getFunctionCall() {
    return findChildByClass(PerlFunctionCall.class);
  }

  @Override
  @Nullable
  public PerlGlob getGlob() {
    return findChildByClass(PerlGlob.class);
  }

  @Override
  @Nullable
  public PerlHashValue getHashValue() {
    return findChildByClass(PerlHashValue.class);
  }

  @Override
  @Nullable
  public PerlMethodCall getMethodCall() {
    return findChildByClass(PerlMethodCall.class);
  }

  @Override
  @Nullable
  public PerlPackageBare getPackageBare() {
    return findChildByClass(PerlPackageBare.class);
  }

  @Override
  @Nullable
  public PerlScalarValue getScalarValue() {
    return findChildByClass(PerlScalarValue.class);
  }

}

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

public class PerlExpressionImpl extends ASTWrapperPsiElement implements PerlExpression {

  public PerlExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PerlVisitor) ((PerlVisitor)visitor).visitExpression(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PerlCodeLineElements> getCodeLineElementsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlCodeLineElements.class);
  }

  @Override
  @NotNull
  public List<PerlSubexpression> getSubexpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlSubexpression.class);
  }

}

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

public class PerlBlockImpl extends ASTWrapperPsiElement implements PerlBlock {

  public PerlBlockImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PerlVisitor) ((PerlVisitor)visitor).visitBlock(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PerlCompoundStatement> getCompoundStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlCompoundStatement.class);
  }

  @Override
  @NotNull
  public List<PerlPackageNamespace> getPackageNamespaceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageNamespace.class);
  }

  @Override
  @NotNull
  public List<PerlStatement> getStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlStatement.class);
  }

  @Override
  @NotNull
  public List<PerlSubDefinition> getSubDefinitionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlSubDefinition.class);
  }

}

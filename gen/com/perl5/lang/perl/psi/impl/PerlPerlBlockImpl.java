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

public class PerlPerlBlockImpl extends ASTWrapperPsiElement implements PerlPerlBlock {

  public PerlPerlBlockImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PerlVisitor) ((PerlVisitor)visitor).visitPerlBlock(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PerlCodeLineValid> getCodeLineValidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlCodeLineValid.class);
  }

  @Override
  @NotNull
  public List<PerlFunctionDefinition> getFunctionDefinitionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlFunctionDefinition.class);
  }

  @Override
  @NotNull
  public List<PerlIfBlock> getIfBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlIfBlock.class);
  }

  @Override
  @NotNull
  public List<PerlPackageDefinitionInvalid> getPackageDefinitionInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageDefinitionInvalid.class);
  }

  @Override
  @NotNull
  public List<PerlPackageNamespace> getPackageNamespaceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageNamespace.class);
  }

  @Override
  @NotNull
  public List<PerlPackageNoInvalid> getPackageNoInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageNoInvalid.class);
  }

  @Override
  @NotNull
  public List<PerlPackageRequireInvalid> getPackageRequireInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageRequireInvalid.class);
  }

  @Override
  @NotNull
  public List<PerlPackageUseInvalid> getPackageUseInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageUseInvalid.class);
  }

  @Override
  @NotNull
  public List<PerlPerlBlock> getPerlBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPerlBlock.class);
  }

}

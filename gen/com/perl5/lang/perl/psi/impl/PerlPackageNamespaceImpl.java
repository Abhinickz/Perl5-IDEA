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

public class PerlPackageNamespaceImpl extends ASTWrapperPsiElement implements PerlPackageNamespace {

  public PerlPackageNamespaceImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PerlVisitor) ((PerlVisitor)visitor).visitPackageNamespace(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PerlBlock> getBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlBlock.class);
  }

  @Override
  @NotNull
  public List<PerlCodeLine> getCodeLineList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlCodeLine.class);
  }

  @Override
  @NotNull
  public List<PerlEval> getEvalList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlEval.class);
  }

  @Override
  @NotNull
  public List<PerlEvalInvalid> getEvalInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlEvalInvalid.class);
  }

  @Override
  @NotNull
  public List<PerlFunctionDefinitionAnon> getFunctionDefinitionAnonList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlFunctionDefinitionAnon.class);
  }

  @Override
  @NotNull
  public List<PerlFunctionDefinitionNamed> getFunctionDefinitionNamedList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlFunctionDefinitionNamed.class);
  }

  @Override
  @NotNull
  public List<PerlIfBlock> getIfBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlIfBlock.class);
  }

  @Override
  @NotNull
  public PerlPackageBare getPackageBare() {
    return findNotNullChildByClass(PerlPackageBare.class);
  }

  @Override
  @NotNull
  public List<PerlPackageNo> getPackageNoList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageNo.class);
  }

  @Override
  @NotNull
  public List<PerlPackageNoInvalid> getPackageNoInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageNoInvalid.class);
  }

  @Override
  @NotNull
  public List<PerlPackageRequire> getPackageRequireList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageRequire.class);
  }

  @Override
  @NotNull
  public List<PerlPackageRequireInvalid> getPackageRequireInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageRequireInvalid.class);
  }

  @Override
  @NotNull
  public List<PerlPackageUse> getPackageUseList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageUse.class);
  }

  @Override
  @NotNull
  public List<PerlPackageUseInvalid> getPackageUseInvalidList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PerlPackageUseInvalid.class);
  }

}

// This is a generated file. Not intended for manual editing.
package com.perl5.lang.perl.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.perl.psi.PsiPerlEqualExpr;
import com.perl5.lang.perl.psi.PsiPerlExpr;
import com.perl5.lang.perl.psi.PsiPerlVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PsiPerlEqualExprImpl extends PsiPerlExprImpl implements PsiPerlEqualExpr
{

	public PsiPerlEqualExprImpl(ASTNode node)
	{
		super(node);
	}

	public void accept(@NotNull PsiElementVisitor visitor)
	{
		if (visitor instanceof PsiPerlVisitor) ((PsiPerlVisitor) visitor).visitEqualExpr(this);
		else super.accept(visitor);
	}

	@Override
	@NotNull
	public List<PsiPerlExpr> getExprList()
	{
		return PsiTreeUtil.getChildrenOfTypeAsList(this, PsiPerlExpr.class);
	}

}

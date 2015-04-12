package com.perl5.lang.lexer;

/**
 * Created by hurricup on 12.04.2015.
 */

import com.intellij.psi.tree.IElementType;
import com.perl5.PerlFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PerlElementType extends IElementType
{
	private String debugName = null;

	public PerlElementType(@NotNull @NonNls String debugName) {
		super(debugName, PerlFileType.PERL_LANGUAGE);
		this.debugName = debugName;
	}

	public String toString() {
		return "PerlTokenType " + super.toString();
	}

//	public static abstract class PsiCreator extends PerlElementType {
//		protected PsiCreator(String debugName) {
//			super(debugName);
//		}

	//	public abstract PerlPsiElement createPsi(@NotNull ASTNode node);
//	}
}

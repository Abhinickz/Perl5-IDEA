/*
 * Copyright 2015 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.psi.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.util.PerlArrayUtil;
import com.perl5.lang.perl.util.PerlHashUtil;
import com.perl5.lang.perl.util.PerlScalarUtil;
import com.perl5.lang.perl.util.PerlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hurricup on 27.05.2015.
 */
public class PerlVariableReference extends PerlReferencePoly
{
	public static enum VariableType{
		SCALAR,
		ARRAY,
		HASH
	};

	private PerlNamespace myNamespace;
	private String myVariableName;
	private VariableType actualType;
	private PerlVariable myVariable;

	public PerlVariableReference(@NotNull PsiElement element, TextRange textRange) {
		super(element, textRange);
		assert element instanceof PerlVariableName;
		myVariableName = ((PerlVariableName) element).getName();

		boolean isScalar;

		if( element.getParent() instanceof PerlVariable )
		{
			myVariable = (PerlVariable) element.getParent();
			myNamespace = myVariable.getNamespace();
			isScalar = myVariable.getScalarSigils() != null;
		}
		else
			throw new RuntimeException("Can't be: got myVariable name without a myVariable");

		PsiElement variableContainer = myVariable.getParent();
		if(
				variableContainer instanceof PerlScalarHashElement
						|| variableContainer instanceof PerlArrayHashSlice
						|| (myVariable instanceof PerlPerlHash && !isScalar)
				)
			actualType = VariableType.HASH;
		else if(
				variableContainer instanceof PerlArrayArraySlice
						|| variableContainer instanceof PerlScalarArrayElement
						|| (myVariable instanceof PerlPerlArrayIndex && !isScalar)
						|| (myVariable instanceof PerlPerlArray && !isScalar)
				)
			actualType = VariableType.ARRAY;
		else if(
				variableContainer instanceof PerlDerefExpr
						|| myVariable instanceof PerlPerlScalar
						|| isScalar
				)
			actualType = VariableType.SCALAR;
		else
			throw new RuntimeException("Can't be: could not detect actual type of myVariable: " + myVariable.getText());

	}

	@NotNull
	@Override
	public Object[] getVariants()
	{
		return new Object[0];
	}

	@NotNull
	@Override
	public ResolveResult[] multiResolve(boolean incompleteCode)
	{
		List<ResolveResult> result;

		if( actualType == VariableType.SCALAR)
			result = resolveScalarName();
		else if( actualType == VariableType.ARRAY)
			result = resolveArrayName();
		else if( actualType == VariableType.HASH)
			result = resolveHashName();
		else
			throw new RuntimeException("Can't be: resolving variable of unknown type");

		return result.toArray(new ResolveResult[result.size()]);
	}

	@Nullable
	@Override
	public PsiElement resolve()
	{
		ResolveResult[] resolveResults = multiResolve(false);
		return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
	}

	/**
	 * Resolving names for scalars, array elements and hash elements
	 * @return declared variable name
	 */
	List<ResolveResult> resolveScalarName()
	{
		List<ResolveResult> result = new ArrayList<>();
		Collection<PerlVariable> variables = PerlUtil.findDeclaredLexicalVariables(myElement.getParent());

		if( myNamespace == null )
		{
			// trying to find lexical variable
			for (PerlVariable variable : variables)
			{
				PerlVariableName variableName = variable.getVariableName();
				if (variable instanceof PerlPerlScalar && variableName != null && myVariableName.equals(variableName.getName()))
				{
					result.add(new PsiElementResolveResult(variableName));
					break;
				}
			}
		}

		if( result.size() == 0 )
		{
			// not found, trying to find global variables
			String packageName = myVariable.getPackageName();

			for( PerlVariable variable: PerlScalarUtil.findGlobalScalarDefinitions(myElement.getProject(), packageName + "::" + myVariableName))
			{
				PerlVariableName variableName = variable.getVariableName();
				if( variableName != null)
					result.add(new PsiElementResolveResult(variableName));
			}
		}

		return result;
	}

	/**
	 * Resolving names for arrays, hash slices and scalar dereference to array
	 * @return declared variable name
	 */
	List<ResolveResult> resolveArrayName()
	{
		List<ResolveResult> result = new ArrayList<>();
		Collection<PerlVariable> variables = PerlUtil.findDeclaredLexicalVariables(myElement.getParent());


		if( myNamespace == null )
		{
			// trying to find lexical variable
			for (PerlVariable variable : variables)
			{
				PerlVariableName variableName = variable.getVariableName();
				if (variable instanceof PerlPerlArray && variableName != null && myVariableName.equals(variableName.getName()))
				{
					result.add(new PsiElementResolveResult(variableName));
					break;
				}
			}
		}

		if( result.size() == 0 )
		{
			// not found, trying to find global variables
			String packageName = myVariable.getPackageName();

			for( PerlVariable variable: PerlArrayUtil.findGlobalArrayDefinitions(myElement.getProject(), packageName + "::" + myVariableName))
			{
				PerlVariableName variableName = variable.getVariableName();
				if( variableName != null)
					result.add(new PsiElementResolveResult(variableName));
			}
		}

		return result;
	}

	/**
	 * Resolving names for hashes and scalar dereference to hash
	 * @return declared variable name
	 */
	List<ResolveResult> resolveHashName()
	{
		List<ResolveResult> result = new ArrayList<>();
		Collection<PerlVariable> variables = PerlUtil.findDeclaredLexicalVariables(myElement.getParent());

		if( myNamespace == null )
		{
			// trying to find lexical variable
			for (PerlVariable variable : variables)
			{
				PerlVariableName variableName = variable.getVariableName();
				if (variable instanceof PerlPerlHash && variableName != null && myVariableName.equals(variableName.getName()))
				{
					result.add(new PsiElementResolveResult(variableName));
					break;
				}
			}
		}

		if( result.size() == 0 )
		{
			// not found, trying to find global variables
			String packageName = myVariable.getPackageName();

			for( PerlVariable variable: PerlHashUtil.findGlobalHashDefinitions(myElement.getProject(), packageName + "::" + myVariableName))
			{
				PerlVariableName variableName = variable.getVariableName();
				if( variableName != null)
					result.add(new PsiElementResolveResult(variableName));
			}
		}

		return result;
	}
}

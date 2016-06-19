/**
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtend.core.macro.declaration;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.core.macro.ConditionUtils;
import org.eclipse.xtend.core.macro.declaration.CompilationUnitImpl;
import org.eclipse.xtend.core.macro.declaration.JvmTypeDeclarationImpl;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MemberDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.ParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.TypeParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class JvmClassDeclarationImpl extends JvmTypeDeclarationImpl<JvmGenericType> implements ClassDeclaration {
  @Override
  public Iterable<? extends TypeReference> getImplementedInterfaces() {
    List<TypeReference> _xblockexpression = null;
    {
      JvmGenericType _delegate = this.getDelegate();
      EList<JvmTypeReference> _superTypes = _delegate.getSuperTypes();
      final Function1<JvmTypeReference, Boolean> _function = (JvmTypeReference it) -> {
        JvmType _type = it.getType();
        return Boolean.valueOf(((JvmGenericType) _type).isInterface());
      };
      final Iterable<JvmTypeReference> filtered = IterableExtensions.<JvmTypeReference>filter(_superTypes, _function);
      final Function1<JvmTypeReference, TypeReference> _function_1 = (JvmTypeReference it) -> {
        CompilationUnitImpl _compilationUnit = this.getCompilationUnit();
        return _compilationUnit.toTypeReference(it);
      };
      Iterable<TypeReference> _map = IterableExtensions.<JvmTypeReference, TypeReference>map(filtered, _function_1);
      _xblockexpression = IterableExtensions.<TypeReference>toList(_map);
    }
    return _xblockexpression;
  }
  
  @Override
  public TypeReference getExtendedClass() {
    CompilationUnitImpl _compilationUnit = this.getCompilationUnit();
    JvmGenericType _delegate = this.getDelegate();
    EList<JvmTypeReference> _superTypes = _delegate.getSuperTypes();
    final Function1<JvmTypeReference, Boolean> _function = (JvmTypeReference it) -> {
      boolean _switchResult = false;
      JvmType _type = it.getType();
      final JvmType it_1 = _type;
      boolean _matched = false;
      if (it_1 instanceof JvmGenericType) {
        boolean _isInterface = ((JvmGenericType)it_1).isInterface();
        boolean _not = (!_isInterface);
        if (_not) {
          _matched=true;
          _switchResult = true;
        }
      }
      if (!_matched) {
        _switchResult = false;
      }
      return Boolean.valueOf(_switchResult);
    };
    JvmTypeReference _findFirst = IterableExtensions.<JvmTypeReference>findFirst(_superTypes, _function);
    return _compilationUnit.toTypeReference(_findFirst);
  }
  
  @Override
  public boolean isAbstract() {
    JvmGenericType _delegate = this.getDelegate();
    return _delegate.isAbstract();
  }
  
  @Override
  public boolean isFinal() {
    JvmGenericType _delegate = this.getDelegate();
    return _delegate.isFinal();
  }
  
  @Override
  public boolean isStatic() {
    JvmGenericType _delegate = this.getDelegate();
    return _delegate.isStatic();
  }
  
  @Override
  public boolean isStrictFloatingPoint() {
    JvmGenericType _delegate = this.getDelegate();
    return _delegate.isStrictFloatingPoint();
  }
  
  @Override
  public Iterable<? extends TypeParameterDeclaration> getTypeParameters() {
    JvmGenericType _delegate = this.getDelegate();
    EList<JvmTypeParameter> _typeParameters = _delegate.getTypeParameters();
    final Function1<JvmTypeParameter, TypeParameterDeclaration> _function = (JvmTypeParameter it) -> {
      CompilationUnitImpl _compilationUnit = this.getCompilationUnit();
      return _compilationUnit.toTypeParameterDeclaration(it);
    };
    return ListExtensions.<JvmTypeParameter, TypeParameterDeclaration>map(_typeParameters, _function);
  }
  
  @Override
  public FieldDeclaration findDeclaredField(final String name) {
    Iterable<? extends MemberDeclaration> _declaredMembers = this.getDeclaredMembers();
    Iterable<FieldDeclaration> _filter = Iterables.<FieldDeclaration>filter(_declaredMembers, FieldDeclaration.class);
    final Function1<FieldDeclaration, Boolean> _function = (FieldDeclaration it) -> {
      String _simpleName = it.getSimpleName();
      return Boolean.valueOf(Objects.equal(_simpleName, name));
    };
    return IterableExtensions.<FieldDeclaration>findFirst(_filter, _function);
  }
  
  @Override
  public MethodDeclaration findDeclaredMethod(final String name, final TypeReference... parameterTypes) {
    MethodDeclaration _xblockexpression = null;
    {
      ConditionUtils.checkIterable(((Iterable<?>)Conversions.doWrapArray(parameterTypes)), "parameterTypes");
      Iterable<? extends MemberDeclaration> _declaredMembers = this.getDeclaredMembers();
      Iterable<MethodDeclaration> _filter = Iterables.<MethodDeclaration>filter(_declaredMembers, MethodDeclaration.class);
      final Function1<MethodDeclaration, Boolean> _function = (MethodDeclaration it) -> {
        return Boolean.valueOf((Objects.equal(it.getSimpleName(), name) && Objects.equal(IterableExtensions.<TypeReference>toList(IterableExtensions.map(it.getParameters(), ((Function1<ParameterDeclaration, TypeReference>) (ParameterDeclaration it_1) -> {
          return it_1.getType();
        }))), IterableExtensions.<TypeReference>toList(((Iterable<TypeReference>)Conversions.doWrapArray(parameterTypes))))));
      };
      _xblockexpression = IterableExtensions.<MethodDeclaration>findFirst(_filter, _function);
    }
    return _xblockexpression;
  }
}

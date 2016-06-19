/**
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtend.core.macro;

import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend.core.macro.declaration.CompilationUnitImpl;
import org.eclipse.xtend.core.xtend.XtendAnnotationTarget;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Sven Efftinge
 */
@Accessors
@SuppressWarnings("all")
public class ActiveAnnotationContext {
  private final List<XtendAnnotationTarget> annotatedSourceElements = CollectionLiterals.<XtendAnnotationTarget>newArrayList();
  
  private Object processorInstance;
  
  private CompilationUnitImpl compilationUnit;
  
  public void handleProcessingError(final Resource resource, final Throwable t) {
    this.compilationUnit.handleProcessingError(this.annotatedSourceElements, resource, t);
  }
  
  @Pure
  public List<XtendAnnotationTarget> getAnnotatedSourceElements() {
    return this.annotatedSourceElements;
  }
  
  @Pure
  public Object getProcessorInstance() {
    return this.processorInstance;
  }
  
  public void setProcessorInstance(final Object processorInstance) {
    this.processorInstance = processorInstance;
  }
  
  @Pure
  public CompilationUnitImpl getCompilationUnit() {
    return this.compilationUnit;
  }
  
  public void setCompilationUnit(final CompilationUnitImpl compilationUnit) {
    this.compilationUnit = compilationUnit;
  }
}

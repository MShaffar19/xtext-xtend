/**
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtend.ide.tests.refactoring;

import org.eclipse.xtend.ide.tests.refactoring.AbstractXtendRenameRefactoringTest;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("all")
public class XtendRenameRefactoringTest extends AbstractXtendRenameRefactoringTest {
  @Ignore("https://github.com/eclipse/xtext-xtend/issues/164")
  @Test
  public void testRenameXtendMember() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package testb;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("import testa.A;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("public class B {");
      _builder.newLine();
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      this.testHelper.createFile("testb/B.java", _builder.toString());
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("package testa");
      _builder_1.newLine();
      _builder_1.append("import testb.B");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("class A {");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("val ARROWHEAD__E = \"\"");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("private interface IAttributeValueParser<T> {");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("val ENUM_PARSER = new IAttributeValueParser<Splines>(){");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("val SPLINES_PARSER = new IAttributeValueParser<Splines>(){");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("@SuppressWarnings(\"unchecked\")");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("def a(IAttributeValueParser<Splines> parser) {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("def b(){");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("a(SPLINES_PARSER)");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("class Splines{");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      final String xtendModel = _builder_1.toString();
      final XtextEditor editor = this.openEditorSafely("A.xtend", xtendModel);
      this.renameXtendElement(editor, xtendModel.indexOf("ARROWHEAD__E"), "arrowhead__e1");
      this.assertDocumentContains(editor, "arrowhead__e1");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}

/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtend.core.tests.compiler;

import org.eclipse.xtend.core.tests.compiler.AbstractXtendCompilerTest;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Test;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@SuppressWarnings("all")
public class CompilerBug457333Test extends AbstractXtendCompilerTest {
  @Test
  public void test_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Map");
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.xbase.lib.Functions.Function0");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class XtendPlugin implements (String)=>void {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("override apply(String project) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#[\'\'].forEach [ sourceSet |");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("create(\'\', Object) [");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("conventionMapping(");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("#{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"classpath\" -> [|sourceSet.charAt(0)],");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"bootClasspath\" -> [|sourceSet.charAt(0)]");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("})");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("static def void conventionMapping(Object task, Map<String, ? extends Function0<?>> mappings) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("def <T> void create(String s, Class<? extends T> c, Action<? super T> a) {}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("def <T> void all(Action<T> action) {}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("interface Action<T> {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("def void exec(T t)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.Collections;");
    _builder_1.newLine();
    _builder_1.append("import java.util.Map;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.CollectionLiterals;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Functions.Function0;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.IterableExtensions;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Pair;");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("@SuppressWarnings(\"all\")");
    _builder_1.newLine();
    _builder_1.append("public class XtendPlugin implements Procedure1<String> {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public interface Action<T extends Object> {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("public abstract void exec(final T t);");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public void apply(final String project) {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("final Procedure1<String> _function = new Procedure1<String>() {");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("public void apply(final String sourceSet) {");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("final XtendPlugin.Action<Object> _function = new XtendPlugin.Action<Object>() {");
    _builder_1.newLine();
    _builder_1.append("          ");
    _builder_1.append("public void exec(final Object it) {");
    _builder_1.newLine();
    _builder_1.append("            ");
    _builder_1.append("final Function0<Character> _function = new Function0<Character>() {");
    _builder_1.newLine();
    _builder_1.append("              ");
    _builder_1.append("public Character apply() {");
    _builder_1.newLine();
    _builder_1.append("                ");
    _builder_1.append("return Character.valueOf(sourceSet.charAt(0));");
    _builder_1.newLine();
    _builder_1.append("              ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("            ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("            ");
    _builder_1.append("Pair<String, Function0<Character>> _mappedTo = Pair.<String, Function0<Character>>of(\"classpath\", _function);");
    _builder_1.newLine();
    _builder_1.append("            ");
    _builder_1.append("final Function0<Character> _function_1 = new Function0<Character>() {");
    _builder_1.newLine();
    _builder_1.append("              ");
    _builder_1.append("public Character apply() {");
    _builder_1.newLine();
    _builder_1.append("                ");
    _builder_1.append("return Character.valueOf(sourceSet.charAt(0));");
    _builder_1.newLine();
    _builder_1.append("              ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("            ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("            ");
    _builder_1.append("Pair<String, Function0<Character>> _mappedTo_1 = Pair.<String, Function0<Character>>of(\"bootClasspath\", _function_1);");
    _builder_1.newLine();
    _builder_1.append("            ");
    _builder_1.append("XtendPlugin.conventionMapping(it, ");
    _builder_1.newLine();
    _builder_1.append("              ");
    _builder_1.append("Collections.<String, Function0<Character>>unmodifiableMap(CollectionLiterals.<String, Function0<Character>>newHashMap(_mappedTo, _mappedTo_1)));");
    _builder_1.newLine();
    _builder_1.append("          ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("XtendPlugin.this.<Object>create(\"\", Object.class, _function);");
    _builder_1.newLine();
    _builder_1.append("      ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("};");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("IterableExtensions.<String>forEach(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(\"\")), _function);");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public static void conventionMapping(final Object task, final Map<String, ? extends Function0<?>> mappings) {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public <T extends Object> void create(final String s, final Class<? extends T> c, final XtendPlugin.Action<? super T> a) {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("public <T extends Object> void all(final XtendPlugin.Action<T> action) {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertCompilesTo(_builder, _builder_1);
  }
}

package org.eclipse.xtend.ide.tests.quickfix;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtend.ide.tests.WorkbenchTestHelper;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.JavaVersion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.annotations.validation.UnresolvedFeatureCallTypeAwareMessageProvider;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.ui.builder.XbaseBuilderPreferenceAccess;
import org.junit.Assert;

@SuppressWarnings("all")
public class QuickfixTestBuilder {
  @Inject
  @Extension
  private WorkbenchTestHelper _workbenchTestHelper;
  
  @Inject
  @Extension
  private IssueResolutionProvider _issueResolutionProvider;
  
  @Inject
  @Extension
  private IResourceValidator _iResourceValidator;
  
  @Inject
  @Extension
  private SyncUtil _syncUtil;
  
  @Inject
  private IPreferenceStoreAccess preferenceStoreAccess;
  
  @Inject
  private XbaseBuilderPreferenceAccess xbaseBuilderPreferenceAccess;
  
  private int caretOffset;
  
  private XtextEditor editor;
  
  private List<Issue> issues;
  
  private Set<String> modifiedIssueCodes;
  
  private IPersistentPreferenceStore getPreferenceStore() {
    IProject _project = this._workbenchTestHelper.getProject();
    IPreferenceStore _writablePreferenceStore = this.preferenceStoreAccess.getWritablePreferenceStore(_project);
    return ((IPersistentPreferenceStore) _writablePreferenceStore);
  }
  
  public void setSeverity(final String issueCode, final String severity) {
    boolean _equals = Objects.equal(this.modifiedIssueCodes, null);
    if (_equals) {
      HashSet<String> _newHashSet = CollectionLiterals.<String>newHashSet();
      this.modifiedIssueCodes = _newHashSet;
    }
    this.modifiedIssueCodes.add(issueCode);
    IPersistentPreferenceStore _preferenceStore = this.getPreferenceStore();
    _preferenceStore.setValue(issueCode, "error");
  }
  
  public void setJavaVersion(final JavaVersion javaVersion) {
    IProject _project = this._workbenchTestHelper.getProject();
    this.xbaseBuilderPreferenceAccess.setJavaVersion(_project, javaVersion);
  }
  
  public QuickfixTestBuilder create(final String fileName, final CharSequence model) {
    try {
      QuickfixTestBuilder _xblockexpression = null;
      {
        String _string = model.toString();
        int _indexOf = _string.indexOf("|");
        Assert.assertNotSame("No position marker | found in model", Integer.valueOf((-1)), Integer.valueOf(_indexOf));
        String _string_1 = model.toString();
        String _replace = _string_1.replace("|", "");
        final IFile file = this._workbenchTestHelper.createFile(fileName, _replace);
        XtextEditor _openEditorSafely = this.openEditorSafely(file);
        this.editor = _openEditorSafely;
        final IXtextDocument document = this.editor.getDocument();
        Assert.assertNotNull("Error getting document from editor", document);
        final IUnitOfWork<List<Issue>, XtextResource> _function = (XtextResource it) -> {
          List<Issue> _validate = this._iResourceValidator.validate(it, CheckMode.NORMAL_AND_FAST, CancelIndicator.NullImpl);
          return this.issues = _validate;
        };
        document.<List<Issue>>readOnly(_function);
        String _string_2 = model.toString();
        int _indexOf_1 = _string_2.indexOf("|");
        this.caretOffset = _indexOf_1;
        _xblockexpression = this;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public QuickfixTestBuilder caretOffset(final int offset) {
    QuickfixTestBuilder _xblockexpression = null;
    {
      this.caretOffset = offset;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertIssueCodes(final String... issueCodes) {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, String> _function = (Issue it) -> {
        return it.getCode();
      };
      Iterable<String> _map = IterableExtensions.<Issue, String>map(_issuesAtCaret, _function);
      this.assertEqual(((List<String>)Conversions.doWrapArray(issueCodes)), _map);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertFeatureCallLinkingIssue() {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, Boolean> _function = (Issue it) -> {
        return Boolean.valueOf((Objects.equal(it.getCode(), Diagnostic.LINKING_DIAGNOSTIC) && 
          ((List<String>)Conversions.doWrapArray(it.getData())).contains(UnresolvedFeatureCallTypeAwareMessageProvider.FEATURE_CALL)));
      };
      boolean _exists = IterableExtensions.<Issue>exists(_issuesAtCaret, _function);
      Assert.assertTrue(_exists);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertTypeLiteralLinkingIssue() {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, Boolean> _function = (Issue it) -> {
        return Boolean.valueOf((Objects.equal(it.getCode(), Diagnostic.LINKING_DIAGNOSTIC) && 
          ((List<String>)Conversions.doWrapArray(it.getData())).contains(UnresolvedFeatureCallTypeAwareMessageProvider.TYPE_LITERAL)));
      };
      boolean _exists = IterableExtensions.<Issue>exists(_issuesAtCaret, _function);
      Assert.assertTrue(_exists);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertResolutionLabels(final String... resolutionLabels) {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, List<IssueResolution>> _function = (Issue it) -> {
        return this._issueResolutionProvider.getResolutions(it);
      };
      Iterable<List<IssueResolution>> _map = IterableExtensions.<Issue, List<IssueResolution>>map(_issuesAtCaret, _function);
      final Iterable<IssueResolution> resolutions = Iterables.<IssueResolution>concat(_map);
      final Function1<IssueResolution, String> _function_1 = (IssueResolution it) -> {
        return it.getLabel();
      };
      Iterable<String> _map_1 = IterableExtensions.<IssueResolution, String>map(resolutions, _function_1);
      this.assertEqual(((List<String>)Conversions.doWrapArray(resolutionLabels)), _map_1);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertResolutionLabelsSubset(final String... expectedLabels) {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, List<IssueResolution>> _function = (Issue it) -> {
        return this._issueResolutionProvider.getResolutions(it);
      };
      Iterable<List<IssueResolution>> _map = IterableExtensions.<Issue, List<IssueResolution>>map(_issuesAtCaret, _function);
      Iterable<IssueResolution> _flatten = Iterables.<IssueResolution>concat(_map);
      final Function1<IssueResolution, String> _function_1 = (IssueResolution it) -> {
        return it.getLabel();
      };
      Iterable<String> _map_1 = IterableExtensions.<IssueResolution, String>map(_flatten, _function_1);
      final Set<String> actualLabels = IterableExtensions.<String>toSet(_map_1);
      final Consumer<String> _function_2 = (String it) -> {
        String _join = IterableExtensions.join(actualLabels, ", ");
        String _plus = ((("Label \'" + it) + "\' missing. Got ") + _join);
        boolean _contains = actualLabels.contains(it);
        Assert.assertTrue(_plus, _contains);
      };
      ((List<String>)Conversions.doWrapArray(expectedLabels)).forEach(_function_2);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertNoResolutionLabels(final String... unExpectedLabels) {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, List<IssueResolution>> _function = (Issue it) -> {
        return this._issueResolutionProvider.getResolutions(it);
      };
      Iterable<List<IssueResolution>> _map = IterableExtensions.<Issue, List<IssueResolution>>map(_issuesAtCaret, _function);
      Iterable<IssueResolution> _flatten = Iterables.<IssueResolution>concat(_map);
      final Function1<IssueResolution, String> _function_1 = (IssueResolution it) -> {
        return it.getLabel();
      };
      Iterable<String> _map_1 = IterableExtensions.<IssueResolution, String>map(_flatten, _function_1);
      final Set<String> actualLabels = IterableExtensions.<String>toSet(_map_1);
      final Consumer<String> _function_2 = (String it) -> {
        String _join = IterableExtensions.join(actualLabels, ", ");
        String _plus = ((("Label \'" + it) + "\' should not appear. Got ") + _join);
        boolean _contains = actualLabels.contains(it);
        Assert.assertFalse(_plus, _contains);
      };
      ((List<String>)Conversions.doWrapArray(unExpectedLabels)).forEach(_function_2);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertModelAfterQuickfix(final CharSequence expectedModel) {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, List<IssueResolution>> _function = (Issue it) -> {
        return this._issueResolutionProvider.getResolutions(it);
      };
      Iterable<List<IssueResolution>> _map = IterableExtensions.<Issue, List<IssueResolution>>map(_issuesAtCaret, _function);
      Iterable<IssueResolution> _flatten = Iterables.<IssueResolution>concat(_map);
      final List<IssueResolution> resolutions = IterableExtensions.<IssueResolution>toList(_flatten);
      IXtextDocument _document = this.editor.getDocument();
      final String originalModel = _document.get();
      final IssueResolution resolution = IterableExtensions.<IssueResolution>head(resolutions);
      Assert.assertNotNull(resolution);
      resolution.apply();
      String _string = expectedModel.toString();
      IXtextDocument _document_1 = this.editor.getDocument();
      String _get = _document_1.get();
      Assert.assertEquals(_string, _get);
      IXtextDocument _document_2 = this.editor.getDocument();
      _document_2.set(originalModel);
      this._syncUtil.waitForReconciler(this.editor);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public QuickfixTestBuilder assertModelAfterQuickfix(final String label, final CharSequence expectedModel) {
    QuickfixTestBuilder _xblockexpression = null;
    {
      Iterable<Issue> _issuesAtCaret = this.getIssuesAtCaret();
      final Function1<Issue, List<IssueResolution>> _function = (Issue it) -> {
        return this._issueResolutionProvider.getResolutions(it);
      };
      Iterable<List<IssueResolution>> _map = IterableExtensions.<Issue, List<IssueResolution>>map(_issuesAtCaret, _function);
      Iterable<IssueResolution> _flatten = Iterables.<IssueResolution>concat(_map);
      final List<IssueResolution> resolutions = IterableExtensions.<IssueResolution>toList(_flatten);
      IXtextDocument _document = this.editor.getDocument();
      final String originalModel = _document.get();
      final Function1<IssueResolution, Boolean> _function_1 = (IssueResolution it) -> {
        String _label = it.getLabel();
        return Boolean.valueOf(Objects.equal(_label, label));
      };
      final IssueResolution matchingResolution = IterableExtensions.<IssueResolution>findFirst(resolutions, _function_1);
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList(label);
      final Function1<IssueResolution, String> _function_2 = (IssueResolution it) -> {
        return label;
      };
      List<String> _map_1 = ListExtensions.<IssueResolution, String>map(resolutions, _function_2);
      String _error = this.error(_newArrayList, _map_1);
      Assert.assertNotNull(_error, matchingResolution);
      matchingResolution.apply();
      String _string = expectedModel.toString();
      IXtextDocument _document_1 = this.editor.getDocument();
      String _get = _document_1.get();
      Assert.assertEquals(_string, _get);
      IXtextDocument _document_2 = this.editor.getDocument();
      _document_2.set(originalModel);
      this._syncUtil.waitForReconciler(this.editor);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  protected XtextEditor getEditor() {
    return this.editor;
  }
  
  protected Iterable<Issue> getIssuesAtCaret() {
    final Function1<Issue, Boolean> _function = (Issue it) -> {
      return Boolean.valueOf((((it.getOffset()).intValue() <= this.caretOffset) && (((it.getOffset()).intValue() + (it.getLength()).intValue()) >= this.caretOffset)));
    };
    return IterableExtensions.<Issue>filter(this.issues, _function);
  }
  
  protected void assertEqual(final List<String> expected, final Iterable<String> actual) {
    List<String> _sort = IterableExtensions.<String>sort(expected);
    String _join = IterableExtensions.join(_sort, "\n");
    List<String> _sort_1 = IterableExtensions.<String>sort(actual);
    String _join_1 = IterableExtensions.join(_sort_1, "\n");
    Assert.assertEquals(_join, _join_1);
  }
  
  protected String error(final Iterable<String> expected, final Iterable<String> actual) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Expected ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\'");
    String _join = IterableExtensions.join(expected, ", ");
    _builder.append(_join, "\t");
    _builder.append("\'  ");
    _builder.newLineIfNotEmpty();
    _builder.append("but got ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\'");
    String _join_1 = IterableExtensions.join(actual, ", ");
    _builder.append(_join_1, "\t");
    _builder.append("\'");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected XtextEditor openEditorSafely(final IFile file) throws Exception {
    XtextEditor _xblockexpression = null;
    {
      final NullProgressMonitor monitor = new NullProgressMonitor();
      this._syncUtil.waitForBuild(monitor);
      this._syncUtil.yieldToQueuedDisplayJobs(monitor);
      XtextEditor editor = this._workbenchTestHelper.openEditor(file);
      this._syncUtil.waitForReconciler(editor);
      this._syncUtil.yieldToQueuedDisplayJobs(monitor);
      _xblockexpression = editor;
    }
    return _xblockexpression;
  }
  
  public void tearDown() {
    this.editor = null;
    this._workbenchTestHelper.closeAllEditors(false);
    Set<IFile> _files = this._workbenchTestHelper.getFiles();
    final Consumer<IFile> _function = (IFile it) -> {
      try {
        NullProgressMonitor _nullProgressMonitor = new NullProgressMonitor();
        it.delete(true, _nullProgressMonitor);
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    _files.forEach(_function);
    Set<IFile> _files_1 = this._workbenchTestHelper.getFiles();
    _files_1.clear();
    boolean _notEquals = (!Objects.equal(this.modifiedIssueCodes, null));
    if (_notEquals) {
      IPersistentPreferenceStore _preferenceStore = this.getPreferenceStore();
      final Procedure1<IPersistentPreferenceStore> _function_1 = (IPersistentPreferenceStore it) -> {
        final Consumer<String> _function_2 = (String code) -> {
          it.setToDefault(code);
        };
        this.modifiedIssueCodes.forEach(_function_2);
        it.setToDefault(XbaseBuilderPreferenceAccess.PREF_USE_COMPILER_SOURCE);
        it.setToDefault(XbaseBuilderPreferenceAccess.PREF_JAVA_VERSION);
      };
      ObjectExtensions.<IPersistentPreferenceStore>operator_doubleArrow(_preferenceStore, _function_1);
      this.modifiedIssueCodes = null;
    }
    NullProgressMonitor _nullProgressMonitor = new NullProgressMonitor();
    this._syncUtil.yieldToQueuedDisplayJobs(_nullProgressMonitor);
  }
  
  public QuickfixTestBuilder removeFile(final String fileName) {
    try {
      QuickfixTestBuilder _xblockexpression = null;
      {
        IFile _file = this._workbenchTestHelper.getFile(fileName);
        NullProgressMonitor _nullProgressMonitor = new NullProgressMonitor();
        _file.delete(true, _nullProgressMonitor);
        _xblockexpression = this;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}

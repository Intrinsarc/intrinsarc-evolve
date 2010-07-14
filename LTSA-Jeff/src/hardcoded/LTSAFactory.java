package hardcoded;

import com.hopstepjump.backbone.runtime.api.*;

/** generated by Evolve */
public class LTSAFactory implements IHardcodedFactory
{
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private Attribute<String> currentDirectory = new Attribute<String>("");
  public void setCurrentDirectory(String currentDirectory) { this.currentDirectory.set(currentDirectory); }
  public String getCurrentDirectory() { return currentDirectory.get(); }
  private Attribute<lts.CompositeState> top = new Attribute<lts.CompositeState>(new lts.CompositeState(null));
  public void setTop(lts.CompositeState top) { this.top.set(top); }
  public lts.CompositeState getTop() { return top.get(); }
  private Attribute<String> name = new Attribute<String>("Parse!");
  private Attribute<String> icon = new Attribute<String>("icon/parse.gif");
  private Attribute<String> name1 = new Attribute<String>("Compile!");
  private Attribute<String> icon1 = new Attribute<String>("icon/compile.gif");
  private Attribute<String> title = new Attribute<String>("LTSA (using Backbone)");
  private Attribute<String> name2 = new Attribute<String>("Set big font!");
  private Attribute<Boolean> selected = new Attribute<Boolean>(false);
  private Attribute<String> name3 = new Attribute<String>("Minimise");
  private Attribute<String> icon2 = new Attribute<String>("icon/minimize.gif");
  private Attribute<String> name4 = new Attribute<String>("Compose!");
  private Attribute<String> icon3 = new Attribute<String>("icon/compose.gif");
  private Attribute<String> name5 = new Attribute<String>("Safety!");
  private Attribute<String> icon4 = new Attribute<String>("icon/safety.gif");
  private Attribute<String> name6 = new Attribute<String>("Progress!");
  private Attribute<String> icon5 = new Attribute<String>("icon/progress.gif");
  private Attribute<String> name7 = new Attribute<String>("Fair choice for LTL check!");
  private Attribute<Boolean> selected1 = new Attribute<Boolean>(true);
  private Attribute<String> icon6 = new Attribute<String>(null);
  private Attribute<String> name8 = new Attribute<String>("Supertrace!");
  private Attribute<String> icon7 = new Attribute<String>(null);
  private Attribute<String> name9 = new Attribute<String>("LTL property!");
  private Attribute<String> name10 = new Attribute<String>("Run DEFAULT!");
  private Attribute<String> icon8 = new Attribute<String>(null);
  private Attribute<String> name11 = new Attribute<String>("Autorun actions in animator!!");
  private Attribute<Boolean> selected2 = new Attribute<Boolean>(false);
  private Attribute<String> name12 = new Attribute<String>("Draw!");
  private Attribute<String> name13 = new Attribute<String>("Use V2.0 label format when drawing LTS!");
  private Attribute<Boolean> selected3 = new Attribute<Boolean>(true);
  private Attribute<String> name14 = new Attribute<String>("Display name when drawing LTS!");
  private Attribute<Boolean> selected4 = new Attribute<Boolean>(true);
  private Attribute<String> name15 = new Attribute<String>("Multiple LTS in Draw window!");
  private Attribute<Boolean> selected5 = new Attribute<Boolean>(false);
  private Attribute<String> name16 = new Attribute<String>("Alphabet!");
  private Attribute<String> name17 = new Attribute<String>("Transitions!");

  // connectors
  private lts.IEventManager c;
  private lts.EventClient c1;
  private lts.IEventManager c2;
  private lts.IEventManager c3;
  private lts.IEventManager c4;
  private lts.IEventManager c5;
  private lts.IEventManager c6;
  private lts.IEventManager c7;
  private lts.IEventManager c8;
  private lts.IEventManager c9;
  private com.hopstepjump.backbone.runtime.api.ICreate c10;
  private com.hopstepjump.backbone.runtime.api.ICreate c11;
  private com.hopstepjump.backbone.runtime.api.ICreate c12;
  private com.hopstepjump.backbone.runtime.api.ICreate c13;
  private com.hopstepjump.backbone.runtime.api.ICreate c14;
  private com.hopstepjump.backbone.runtime.api.ICreate c15;
  private com.hopstepjump.backbone.runtime.api.ICreate c16;
  private com.hopstepjump.backbone.runtime.api.ICreate c17;
  private actions.INameList c18;
  private actions.IAction c19;
  private actions.ICoordinator c20;
  private actions.IAction c21;
  private actions.IAction c22;
  private actions.IAction c23;
  private actions.IAction c24;
  private actions.IAction c25;
  private actions.IAction c26;
  private actions.IAction c27;
  private actions.IAction c28;
  private actions.INameList c29;
  private actions.INameList c30;
  private actions.INameList c31;
  private actions.ICoordinator c32;
  private actions.IAction c33;
  private actions.IAction c34;
  private ui.IWindow c35;
  private ui.IWindow c36;
  private javax.swing.JCheckBoxMenuItem c37;
  private java.awt.event.ActionListener c38;
  private javax.swing.JCheckBoxMenuItem c39;
  private java.awt.event.ActionListener c40;
  private javax.swing.JCheckBoxMenuItem c41;
  private java.awt.event.ActionListener c42;
  private ui.IWindow c43;
  private actions.ICoordinator c44;
  private java.awt.event.ActionListener c45;
  private javax.swing.JCheckBoxMenuItem c46;
  private java.awt.event.ActionListener c47;
  private javax.swing.JCheckBoxMenuItem c48;
  private actions.IAction c49;
  private lts.LTSOutput c50;
  private lts.LTSInput c51;
  private actions.INameList c52;
  private actions.ICoordinator c53;
  private lts.LTSOutput c54;
  private lts.LTSInput c55;
  private actions.IAction c56;
  private actions.ICoordinator c57;
  private lts.LTSOutput c58;
  private lts.LTSInput c59;
  private actions.IAction c60;
  private java.awt.event.ActionListener c61;
  private javax.swing.JCheckBoxMenuItem c62;
  private java.awt.event.ActionListener c63;
  private javax.swing.JCheckBoxMenuItem c64;
  private lts.LTSOutput c65;
  private lts.LTSInput c66;
  private actions.ICoordinator c67;
  private actions.IAction c68;
  private actions.ICoordinator c69;
  private lts.LTSOutput c70;
  private lts.LTSInput c71;
  private actions.IAction c72;
  private actions.ICoordinator c73;
  private lts.LTSOutput c74;
  private lts.LTSInput c75;
  private actions.IAction c76;
  private actions.ICoordinator c77;
  private lts.LTSOutput c78;
  private lts.LTSInput c79;
  private actions.IAction c80;
  private actions.INameListener c81;
  private actions.INameList c82;
  private javax.swing.JCheckBoxMenuItem c83;
  private java.awt.event.ActionListener c84;

 // parts
  private ui.HPWindow x = new ui.HPWindow();
  private lts.EventManager x1 = new lts.EventManager();
  private extend.BooleanOption x87 = new extend.BooleanOption();
  private actions.NameList t = new actions.NameList();
  private actions.NameList check = new actions.NameList();
  private actions.NameList l = new actions.NameList();
  private actions.MinimiseAction x18 = new actions.MinimiseAction();
  private actions.ComposeAction x16 = new actions.ComposeAction();
  private checks.SafetyCheck x14 = new checks.SafetyCheck();
  private checks.ProgressCheck x12 = new checks.ProgressCheck();
  private extend.BooleanOption x72 = new extend.BooleanOption();
  private checks.SupertraceCheck x10 = new checks.SupertraceCheck();
  private checks.LivenessCheck x8 = new checks.LivenessCheck();
  private checks.ExecuteCheck x4 = new checks.ExecuteCheck();
  private extend.BooleanOption x60 = new extend.BooleanOption();
  private extend.WindowManager x27 = new extend.WindowManager();
  private extend.BooleanOption x55 = new extend.BooleanOption();
  private extend.BooleanOption x53 = new extend.BooleanOption();
  private extend.BooleanOption x51 = new extend.BooleanOption();
  private extend.WindowManager x26 = new extend.WindowManager();
  private extend.WindowManager x23 = new extend.WindowManager();
  private actions.ParseManager x21 = new actions.ParseManager();
  private actions.CompileManager x3 = new actions.CompileManager();
  private ICreate factory7 = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      AnalyserFactoryFactory f = new AnalyserFactoryFactory();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  private ICreate factory6 = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      AnimatorFactoryFactory f = new AnimatorFactoryFactory();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  private ICreate factory5 = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      AnalyserFactoryFactory1 f = new AnalyserFactoryFactory1();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  private ICreate factory4 = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      DrawWindowFactoryFactory f = new DrawWindowFactoryFactory();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  private ICreate factory3 = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      AlphabetWindowFactoryFactory f = new AlphabetWindowFactoryFactory();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  private ICreate factory2 = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      TransitionsWindowFactoryFactory f = new TransitionsWindowFactoryFactory();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  private ICreate factory1 = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      CompilerFactoryFactory f = new CompilerFactoryFactory();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  private ICreate factory = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      CompilerFactoryFactory1 f = new CompilerFactoryFactory1();
      f.initialize(LTSAFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  public com.hopstepjump.backbone.runtime.api.IRun getRun() { return x.getRun_IRun(null); }

  public LTSAFactory() {}

  public LTSAFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    if (values != null && values.containsKey("currentDirectory")) currentDirectory = new Attribute<String>((String) values.get("currentDirectory"));
    if (values != null && values.containsKey("top")) top = new Attribute<lts.CompositeState>((lts.CompositeState) values.get("top"));
    x.setCurrentDirectory(currentDirectory);
    x.setTitle(title);
    x.setTop(top);
    x87.setName(name2);
    x87.setSelected(selected);
    x18.setTop(top);
    x18.setName(name3);
    x18.setIcon(icon2);
    x16.setName(name4);
    x16.setTop(top);
    x16.setIcon(icon3);
    x14.setTop(top);
    x14.setName(name5);
    x14.setIcon(icon4);
    x12.setTop(top);
    x12.setName(name6);
    x12.setIcon(icon5);
    x72.setName(name7);
    x72.setSelected(selected1);
    x10.setIcon(icon6);
    x10.setTop(top);
    x10.setName(name8);
    x8.setIcon(icon7);
    x8.setName(name9);
    x8.setTop(top);
    x4.setTop(top);
    x4.setName(name10);
    x4.setIcon(icon8);
    x4.setCurrentDirectory(currentDirectory);
    x60.setName(name11);
    x60.setSelected(selected2);
    x27.setName(name12);
    x55.setName(name13);
    x55.setSelected(selected3);
    x53.setName(name14);
    x53.setSelected(selected4);
    x51.setName(name15);
    x51.setSelected(selected5);
    x26.setName(name16);
    x23.setName(name17);
    x21.setIcon(icon);
    x21.setName(name);
    x21.setTop(top);
    x3.setName(name1);
    x3.setTop(top);
    x3.setIcon(icon1);
    c = x1.getEvents_IEventManager(lts.IEventManager.class);
    c1 = x4.getEventListener_EventClient(lts.EventClient.class);
    c2 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c3 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c4 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c5 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c6 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c7 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c8 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c9 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c10 = factory;
    c11 = factory1;
    c12 = factory2;
    c13 = factory3;
    c14 = factory4;
    c15 = factory5;
    c16 = factory6;
    c17 = factory7;
    c18 = t.getNames_INameList(actions.INameList.class);
    c19 = x21.getAction_IAction(actions.IAction.class);
    c20 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c21 = x3.getAction_IAction(actions.IAction.class);
    c22 = x3.getAction_IAction(actions.IAction.class);
    c23 = x3.getAction_IAction(actions.IAction.class);
    c24 = x3.getAction_IAction(actions.IAction.class);
    c25 = x3.getAction_IAction(actions.IAction.class);
    c26 = x3.getAction_IAction(actions.IAction.class);
    c27 = x3.getAction_IAction(actions.IAction.class);
    c28 = x3.getAction_IAction(actions.IAction.class);
    c29 = l.getNames_INameList(actions.INameList.class);
    c30 = check.getNames_INameList(actions.INameList.class);
    c31 = t.getNames_INameList(actions.INameList.class);
    c32 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c33 = x21.getAction_IAction(actions.IAction.class);
    c34 = x21.getAction_IAction(actions.IAction.class);
    c35 = x23.getProxy_IWindow(ui.IWindow.class);
    c36 = x26.getProxy_IWindow(ui.IWindow.class);
    c37 = x51.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c38 = x.getBigFont_ActionListener(java.awt.event.ActionListener.class);
    c39 = x53.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c40 = x.getBigFont_ActionListener(java.awt.event.ActionListener.class);
    c41 = x55.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c42 = x.getBigFont_ActionListener(java.awt.event.ActionListener.class);
    c43 = x27.getProxy_IWindow(ui.IWindow.class);
    c44 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c45 = x.getOptions_ActionListener(java.awt.event.ActionListener.class, -1);
    c46 = x60.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c47 = x4.getAutorunOption_ActionListener(java.awt.event.ActionListener.class);
    c48 = x60.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c49 = x4.getAction_IAction(actions.IAction.class);
    c50 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c51 = x.getInout_LTSInput(lts.LTSInput.class);
    c52 = l.getNames_INameList(actions.INameList.class);
    c53 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c54 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c55 = x.getInout_LTSInput(lts.LTSInput.class);
    c56 = x8.getAction_IAction(actions.IAction.class);
    c57 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c58 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c59 = x.getInout_LTSInput(lts.LTSInput.class);
    c60 = x10.getAction_IAction(actions.IAction.class);
    c61 = x.getOptions_ActionListener(java.awt.event.ActionListener.class, -1);
    c62 = x72.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c63 = x12.getFairOption_ActionListener(java.awt.event.ActionListener.class);
    c64 = x72.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c65 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c66 = x.getInout_LTSInput(lts.LTSInput.class);
    c67 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c68 = x12.getAction_IAction(actions.IAction.class);
    c69 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c70 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c71 = x.getInout_LTSInput(lts.LTSInput.class);
    c72 = x14.getAction_IAction(actions.IAction.class);
    c73 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c74 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c75 = x.getInout_LTSInput(lts.LTSInput.class);
    c76 = x16.getAction_IAction(actions.IAction.class);
    c77 = x.getCoordinator_ICoordinator(actions.ICoordinator.class);
    c78 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c79 = x.getInout_LTSInput(lts.LTSInput.class);
    c80 = x18.getAction_IAction(actions.IAction.class);
    c81 = x.getTarget_INameListener(actions.INameListener.class);
    c82 = t.getListeners_INameList(actions.INameList.class, -1);
    c83 = x87.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c84 = x.getBigFont_ActionListener(java.awt.event.ActionListener.class);
    x.setActions_IAction(c34, -1);
    x.setWindow_IWindow(c36, -1);
    x.setChecks_IAction(c72, -1);
    x.setActions_IAction(c28, -1);
    x.setWindow_IWindow(c43, -1);
    x.setChecks_IAction(c68, -1);
    x.setWindow_IWindow(c35, -1);
    x.setChecks_IAction(c60, -1);
    x.setActions_IAction(c80, -1);
    x.setChecks_IAction(c56, -1);
    x.setActions_IAction(c76, -1);
    x.setChecks_IAction(c49, -1);
    x3.setEvents_IEventManager(c);
    x1.setClients_EventClient(c1, -1);
    x4.setEvents_IEventManager(c2);
    x8.setEvents_IEventManager(c3);
    x10.setEvents_IEventManager(c4);
    x12.setEvents_IEventManager(c5);
    x14.setEvents_IEventManager(c6);
    x16.setEvents_IEventManager(c7);
    x18.setEvents_IEventManager(c8);
    x.setEvents_IEventManager(c9);
    x3.setCreateCompiler_ICreate(c10);
    x21.setCompilerCreator_ICreate(c11);
    x23.setCreate_ICreate(c12);
    x26.setCreate_ICreate(c13);
    x27.setCreate_ICreate(c14);
    x14.setAnalyserCreator_ICreate(c15);
    x4.setAnimatorCreator_ICreate(c16);
    x10.setAnalyserCreator_ICreate(c17);
    x3.setTarget_INameList(c18);
    x3.setParser_IAction(c19);
    x3.setCoordinator_ICoordinator(c20);
    x4.setCompiler_IAction(c21);
    x8.setCompiler_IAction(c22);
    x10.setCompiler_IAction(c23);
    x12.setCompiler_IAction(c24);
    x14.setCompiler_IAction(c25);
    x16.setCompiler_IAction(c26);
    x18.setCompiler_IAction(c27);
    x21.setLiveness_INameList(c29);
    x21.setCheck_INameList(c30);
    x21.setTarget_INameList(c31);
    x21.setCoordinator_ICoordinator(c32);
    x.setParser_IAction(c33);
    x51.setOptions_ActionListener(c38, -1);
    x.setBigFont_JCheckBoxMenuItem(c37);
    x53.setOptions_ActionListener(c40, -1);
    x.setBigFont_JCheckBoxMenuItem(c39);
    x55.setOptions_ActionListener(c42, -1);
    x.setBigFont_JCheckBoxMenuItem(c41);
    x4.setCoordinator_ICoordinator(c44);
    x.setOptions_JCheckBoxMenuItem(c46, -1);
    x60.setOptions_ActionListener(c45, -1);
    x4.setAutorunOption_JCheckBoxMenuItem(c48);
    x60.setOptions_ActionListener(c47, -1);
    x4.setInout_LTSInput(c51);
    x4.setInout_LTSOutput(c50);
    x8.setAssert__INameList(c52);
    x8.setCoordinator_ICoordinator(c53);
    x8.setInout_LTSInput(c55);
    x8.setInout_LTSOutput(c54);
    x10.setCoordinator_ICoordinator(c57);
    x10.setInout_LTSInput(c59);
    x10.setInout_LTSOutput(c58);
    x.setOptions_JCheckBoxMenuItem(c62, -1);
    x72.setOptions_ActionListener(c61, -1);
    x12.setFairOption_JCheckBoxMenuItem(c64);
    x72.setOptions_ActionListener(c63, -1);
    x12.setInout_LTSInput(c66);
    x12.setInout_LTSOutput(c65);
    x12.setCoordinator_ICoordinator(c67);
    x14.setCoordinator_ICoordinator(c69);
    x14.setInout_LTSInput(c71);
    x14.setInout_LTSOutput(c70);
    x16.setCoordinator_ICoordinator(c73);
    x16.setInout_LTSInput(c75);
    x16.setInout_LTSOutput(c74);
    x18.setCoordinator_ICoordinator(c77);
    x18.setInout_LTSInput(c79);
    x18.setInout_LTSOutput(c78);
    x.setTarget_INameList(c82);
    t.setListeners_INameListener(c81, -1);
    x87.setOptions_ActionListener(c84, -1);
    x.setBigFont_JCheckBoxMenuItem(c83);
    x87.afterInit();
    x72.afterInit();
    x60.afterInit();
    x55.afterInit();
    x53.afterInit();
    x51.afterInit();
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
  }

  static void destroyChildren(IHardcodedFactory parent, IHardcodedFactory me, java.util.List<IHardcodedFactory> children)
  {
    parent.childDestroyed(me);
    if (children != null) {
      java.util.List<IHardcodedFactory> copy = new java.util.ArrayList<IHardcodedFactory>(children);
      java.util.Collections.reverse(copy);
      for (IHardcodedFactory f : copy)
        f.destroy();
    }
  }

// flattened factories
class AnalyserFactoryFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // connectors
  private lts.IEventManager c85;
  private lts.IAnalyser c86;

 // parts
  private lts.Analyser x90 = new lts.Analyser();

  public AnalyserFactoryFactory() {}

  public AnalyserFactoryFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    c85 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c86 = x90.getAnalyser_IAnalyser(lts.IAnalyser.class);
    x90.setEvents_IEventManager(c85);
    x10.setAnalyser_IAnalyser(c86);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x90.setEvents_IEventManager(null);
    x10.setAnalyser_IAnalyser(null);
  }

}
class AnimatorFactoryFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // connectors
  private lts.IEventManager c87;
  private lts.IAnalyser c88;
  private lts.IAnalyser c89;
  private ui.IAnimWindow c90;

 // parts
  private ui.AnimWindow x94 = new ui.AnimWindow();
  private lts.Analyser x93 = new lts.Analyser();

  public AnimatorFactoryFactory() {}

  public AnimatorFactoryFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    c87 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c88 = x93.getAnalyser_IAnalyser(lts.IAnalyser.class);
    c89 = x93.getAnalyser_IAnalyser(lts.IAnalyser.class);
    c90 = x94.getMain_IAnimWindow(ui.IAnimWindow.class);
    x93.setEvents_IEventManager(c87);
    x94.setAnimator_IAnalyser(c88);
    x4.setAnalyser_IAnalyser(c89);
    x4.setAnimator_IAnimWindow(c90);
    x94.afterInit();
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x94.beforeDelete();
    x93.setEvents_IEventManager(null);
    x94.setAnimator_IAnalyser(null);
    x4.setAnalyser_IAnalyser(null);
    x4.setAnimator_IAnimWindow(null);
  }

}
class AnalyserFactoryFactory1 implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // connectors
  private lts.IEventManager c91;
  private lts.IAnalyser c92;

 // parts
  private lts.Analyser x99 = new lts.Analyser();

  public AnalyserFactoryFactory1() {}

  public AnalyserFactoryFactory1 initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    c91 = x1.getEvents_IEventManager(lts.IEventManager.class);
    c92 = x99.getAnalyser_IAnalyser(lts.IAnalyser.class);
    x99.setEvents_IEventManager(c91);
    x14.setAnalyser_IAnalyser(c92);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x99.setEvents_IEventManager(null);
    x14.setAnalyser_IAnalyser(null);
  }

}
class DrawWindowFactoryFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // connectors
  private lts.EventClient c93;
  private java.awt.event.ActionListener c94;
  private javax.swing.JCheckBoxMenuItem c95;
  private java.awt.event.ActionListener c96;
  private javax.swing.JCheckBoxMenuItem c97;
  private java.awt.event.ActionListener c98;
  private javax.swing.JCheckBoxMenuItem c99;
  private java.awt.event.ActionListener c100;
  private javax.swing.JCheckBoxMenuItem c101;
  private ui.IWindow c102;

 // parts
  private draw.LTSDrawWindow x101 = new draw.LTSDrawWindow();

  public DrawWindowFactoryFactory() {}

  public DrawWindowFactoryFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    c93 = x101.getEvents_EventClient(lts.EventClient.class);
    c94 = x101.getDisplayName_ActionListener(java.awt.event.ActionListener.class);
    c95 = x53.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c96 = x101.getMultipleLTS_ActionListener(java.awt.event.ActionListener.class);
    c97 = x51.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c98 = x101.getV2Labels_ActionListener(java.awt.event.ActionListener.class);
    c99 = x55.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c100 = x101.getBigFont_ActionListener(java.awt.event.ActionListener.class);
    c101 = x87.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c102 = x101.getWindow_IWindow(ui.IWindow.class);
    x1.setClients_EventClient(c93, -1);
    x101.setDisplayName_JCheckBoxMenuItem(c95);
    x53.setOptions_ActionListener(c94, -1);
    x101.setMultipleLTS_JCheckBoxMenuItem(c97);
    x51.setOptions_ActionListener(c96, -1);
    x101.setV2Labels_JCheckBoxMenuItem(c99);
    x55.setOptions_ActionListener(c98, -1);
    x101.setBigFont_JCheckBoxMenuItem(c101);
    x87.setOptions_ActionListener(c100, -1);
    x27.setWindow_IWindow(c102);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x1.removeClients_EventClient(c93);
    x101.setDisplayName_JCheckBoxMenuItem(null);
    x53.removeOptions_ActionListener(c94);
    x101.setMultipleLTS_JCheckBoxMenuItem(null);
    x51.removeOptions_ActionListener(c96);
    x101.setV2Labels_JCheckBoxMenuItem(null);
    x55.removeOptions_ActionListener(c98);
    x101.setBigFont_JCheckBoxMenuItem(null);
    x87.removeOptions_ActionListener(c100);
    x27.setWindow_IWindow(null);
  }

}
class AlphabetWindowFactoryFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // connectors
  private lts.EventClient c103;
  private javax.swing.JCheckBoxMenuItem c104;
  private java.awt.event.ActionListener c105;
  private ui.IWindow c106;

 // parts
  private alpha.AlphabetWindow x108 = new alpha.AlphabetWindow();

  public AlphabetWindowFactoryFactory() {}

  public AlphabetWindowFactoryFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    c103 = x108.getEvents_EventClient(lts.EventClient.class);
    c104 = x87.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c105 = x108.getBigFont_ActionListener(java.awt.event.ActionListener.class);
    c106 = x108.getWindow_IWindow(ui.IWindow.class);
    x1.setClients_EventClient(c103, -1);
    x87.setOptions_ActionListener(c105, -1);
    x108.setBigFont_JCheckBoxMenuItem(c104);
    x26.setWindow_IWindow(c106);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x1.removeClients_EventClient(c103);
    x87.removeOptions_ActionListener(c105);
    x108.setBigFont_JCheckBoxMenuItem(null);
    x26.setWindow_IWindow(null);
  }

}
class TransitionsWindowFactoryFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private Attribute<String> extension = new Attribute<String>(".aut");
  public void setExtension(String extension) { this.extension.set(extension); }
  public String getExtension() { return extension.get(); }

  // connectors
  private lts.EventClient c107;
  private javax.swing.JCheckBoxMenuItem c108;
  private java.awt.event.ActionListener c109;
  private ui.IWindow c110;

 // parts
  private transitions.TransitionsWindow x112 = new transitions.TransitionsWindow();

  public TransitionsWindowFactoryFactory() {}

  public TransitionsWindowFactoryFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    if (values != null && values.containsKey("extension")) extension = new Attribute<String>((String) values.get("extension"));
    x112.setExtension(extension);
    c107 = x112.getEvents_EventClient(lts.EventClient.class);
    c108 = x87.getOptions_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem.class, -1);
    c109 = x112.getBigFont_ActionListener(java.awt.event.ActionListener.class);
    c110 = x112.getWindow_IWindow(ui.IWindow.class);
    x1.setClients_EventClient(c107, -1);
    x87.setOptions_ActionListener(c109, -1);
    x112.setBigFont_JCheckBoxMenuItem(c108);
    x23.setWindow_IWindow(c110);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x1.removeClients_EventClient(c107);
    x87.removeOptions_ActionListener(c109);
    x112.setBigFont_JCheckBoxMenuItem(null);
    x23.setWindow_IWindow(null);
  }

}
class CompilerFactoryFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private Attribute<String> currentDirectory1;

  // connectors
  private lts.LTSCompiler c111;
  private lts.LTSOutput c112;
  private lts.LTSInput c113;

 // parts
  private lts.LTSCompiler x116 = new lts.LTSCompiler();

  public CompilerFactoryFactory() {}

  public CompilerFactoryFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    if (values != null && values.containsKey("currentDirectory")) currentDirectory1 = new Attribute<String>((String) values.get("currentDirectory"));
    x116.setCurrentDirectory(currentDirectory1);
    c111 = x116.getMain_LTSCompiler(lts.LTSCompiler.class);
    c112 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c113 = x.getInout_LTSInput(lts.LTSInput.class);
    x21.setCompiler_LTSCompiler(c111);
    x116.setInout_LTSInput(c113);
    x116.setInout_LTSOutput(c112);
    x116.afterInit();
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x116.beforeDelete();
    x21.setCompiler_LTSCompiler(null);
    x116.setInout_LTSInput(null);
    x116.setInout_LTSOutput(null);
  }

}
class CompilerFactoryFactory1 implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private Attribute<String> currentDirectory2;

  // connectors
  private lts.LTSCompiler c114;
  private lts.LTSOutput c115;
  private lts.LTSInput c116;

 // parts
  private lts.LTSCompiler x119 = new lts.LTSCompiler();

  public CompilerFactoryFactory1() {}

  public CompilerFactoryFactory1 initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    if (values != null && values.containsKey("currentDirectory")) currentDirectory2 = new Attribute<String>((String) values.get("currentDirectory"));
    x119.setCurrentDirectory(currentDirectory2);
    c114 = x119.getMain_LTSCompiler(lts.LTSCompiler.class);
    c115 = x.getInout_LTSOutput(lts.LTSOutput.class);
    c116 = x.getInout_LTSInput(lts.LTSInput.class);
    x3.setCompiler_LTSCompiler(c114);
    x119.setInout_LTSInput(c116);
    x119.setInout_LTSOutput(c115);
    x119.afterInit();
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x119.beforeDelete();
    x3.setCompiler_LTSCompiler(null);
    x119.setInout_LTSInput(null);
    x119.setInout_LTSOutput(null);
  }

}
}
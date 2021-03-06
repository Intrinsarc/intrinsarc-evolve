package com.myapplication.client.hardcoded;

import com.hopstepjump.backbone.runtime.api.*;

/** generated by Evolve */
public class GWTCustomerWidgetFactory
{
  private Attribute<String> text = new Attribute<String>("<b>Enter full name</b>");
  private Attribute<String> text1 = new Attribute<String>("<b>Enter normal address</b>");

  private Attribute<String> title = new Attribute<String>("Customer details");
  private Attribute<String> text2 = new Attribute<String>("Click for next of kin...");
  private Attribute<String> text3 = new Attribute<String>("First:");
  private Attribute<String> text4 = new Attribute<String>("Last");
  private Attribute<String> text5 = new Attribute<String>("Street:");
  private Attribute<String> text6 = new Attribute<String>("Town:");

  private com.google.gwt.user.client.ui.HorizontalPanel h = new com.google.gwt.user.client.ui.HorizontalPanel();
  private com.google.gwt.user.client.ui.DecoratorPanel d = new com.google.gwt.user.client.ui.DecoratorPanel();
  private com.google.gwt.user.client.ui.Button x = new com.google.gwt.user.client.ui.Button();
  private com.myapplication.client.widgets.ClickLogic x1 = new com.myapplication.client.widgets.ClickLogic();
  private com.google.gwt.user.client.ui.VerticalPanel x2 = new com.google.gwt.user.client.ui.VerticalPanel();
  private com.google.gwt.user.client.ui.Label x3 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.TextBox x4 = new com.google.gwt.user.client.ui.TextBox();
  private com.google.gwt.user.client.ui.Label x5 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.TextBox x6 = new com.google.gwt.user.client.ui.TextBox();
  private com.google.gwt.user.client.ui.HTML x7 = new com.google.gwt.user.client.ui.HTML();
  private com.google.gwt.user.client.ui.VerticalPanel x8 = new com.google.gwt.user.client.ui.VerticalPanel();
  private com.google.gwt.user.client.ui.Label x9 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.TextBox x10 = new com.google.gwt.user.client.ui.TextBox();
  private com.google.gwt.user.client.ui.Label x11 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.TextBox x12 = new com.google.gwt.user.client.ui.TextBox();
  private com.google.gwt.user.client.ui.HTML x13 = new com.google.gwt.user.client.ui.HTML();
  private ICreate factory = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      GWTNameWidgetFactory f = new GWTNameWidgetFactory();
      f.initialize(values);
      return f;
    }
    public void destroy(Object memento) {}
  };
  public com.google.gwt.user.client.ui.DecoratorPanel getPanel() { return d; }

  public GWTCustomerWidgetFactory() {}

  public GWTCustomerWidgetFactory initialize(java.util.Map<String, Object> values)
  {
    h.setTitle(title.get());
    x.setText(text2.get());
    x3.setText(text3.get());
    x5.setText(text4.get());
    x7.setHTML(text.get());
    x9.setText(text5.get());
    x11.setText(text6.get());
    x13.setHTML(text1.get());
    com.hopstepjump.backbone.runtime.api.ICreate c = factory;
    com.google.gwt.user.client.ui.HTML c1 = x13;
    com.google.gwt.user.client.ui.TextBox c2 = x12;
    com.google.gwt.user.client.ui.Label c3 = x11;
    com.google.gwt.user.client.ui.TextBox c4 = x10;
    com.google.gwt.user.client.ui.Label c5 = x9;
    com.google.gwt.user.client.ui.VerticalPanel c6 = x8;
    com.google.gwt.user.client.ui.HTML c7 = x7;
    com.google.gwt.user.client.ui.TextBox c8 = x6;
    com.google.gwt.user.client.ui.Label c9 = x5;
    com.google.gwt.user.client.ui.TextBox c10 = x4;
    com.google.gwt.user.client.ui.Label c11 = x3;
    com.google.gwt.user.client.ui.VerticalPanel c12 = x2;
    com.google.gwt.user.client.ui.ClickListener c13 = x1.getListener_ClickListener(com.google.gwt.user.client.ui.ClickListener.class);
    com.google.gwt.user.client.ui.ButtonBase c14 = x;
    com.google.gwt.user.client.ui.HorizontalPanel c16 = h;
    x8.add(c1);
    x2.add(c7);
    h.add(c12);
    x8.add(c5);
    h.add(c6);
    x2.add(c11);
    x8.add(c4);
    x2.add(c10);
    h.add(c14);
    x8.add(c3);
    x2.add(c9);
    x8.add(c2);
    x2.add(c8);
    x1.setCreate_ICreate(c);
    x.addClickListener(c13);
    d.add(c16);
    return this;
  }
class GWTNameWidgetFactory
{
  private Attribute<String> text7 = new Attribute<String>("<b>Enter next of kin");
  public void setText7(String text7) { this.text7.set(text7); }
  public String getText7() { return text7.get(); }
  private Attribute<String> text8 = new Attribute<String>("First:");
  private Attribute<String> text9 = new Attribute<String>("Last");

  private com.google.gwt.user.client.ui.VerticalPanel x29 = new com.google.gwt.user.client.ui.VerticalPanel();
  private com.google.gwt.user.client.ui.Label x30 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.TextBox x31 = new com.google.gwt.user.client.ui.TextBox();
  private com.google.gwt.user.client.ui.Label x32 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.TextBox x33 = new com.google.gwt.user.client.ui.TextBox();
  private com.google.gwt.user.client.ui.HTML x34 = new com.google.gwt.user.client.ui.HTML();

  public GWTNameWidgetFactory() {}

  public GWTNameWidgetFactory initialize(java.util.Map<String, Object> values)
  {
    if (values != null && values.containsKey("text")) text7 = new Attribute<String>((String) values.get("text"));
    x30.setText(text8.get());
    x32.setText(text9.get());
    x34.setHTML(text7.get());
    com.google.gwt.user.client.ui.HTML c17 = x34;
    com.google.gwt.user.client.ui.TextBox c18 = x33;
    com.google.gwt.user.client.ui.Label c19 = x32;
    com.google.gwt.user.client.ui.TextBox c20 = x31;
    com.google.gwt.user.client.ui.Label c21 = x30;
    com.google.gwt.user.client.ui.VerticalPanel c22 = x29;
    x29.add(c17);
    x29.add(c21);
    x29.add(c20);
    x29.add(c19);
    h.add(c22);
    x29.add(c18);
    return this;
  }
}
}

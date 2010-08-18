package com.intrinsarc.carrentalgui.client;

import com.intrinsarc.backbone.runtime.api.*;

/** generated by Evolve */
public class CarRentalFormFactory implements IHardcodedFactory
{
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private Attribute<Integer> spacing;
  private Attribute<Integer> spacing1;
  private Attribute<Integer> spacing2;
  private Attribute<Integer> spacing3;
  private Attribute<String> width;
  private Attribute<String> text;

  // connectors
  private com.intrinsarc.carrentalgui.client.IRentalServiceAsync c;
  private com.intrinsarc.backbone.runtime.api.ICreate c1;
  private com.google.gwt.user.client.ui.TextBox c2;
  private com.google.gwt.user.client.ui.Label c3;
  private com.google.gwt.user.client.ui.TextBox c4;
  private com.google.gwt.event.dom.client.ChangeHandler c5;
  private com.google.gwt.user.client.ui.HorizontalPanel c6;
  private com.google.gwt.user.client.ui.VerticalPanel c7;
  private com.google.gwt.user.client.ui.VerticalPanel c8;
  private com.google.gwt.user.client.ui.DecoratorPanel c9;
  private com.google.gwt.user.client.ui.DecoratorPanel c10;

 // parts
  private com.google.gwt.user.client.ui.DecoratorPanel x17 = new com.google.gwt.user.client.ui.DecoratorPanel();
  private com.google.gwt.user.client.ui.VerticalPanel x1 = new com.google.gwt.user.client.ui.VerticalPanel();
  private com.google.gwt.user.client.ui.VerticalPanel x2 = new com.google.gwt.user.client.ui.VerticalPanel();
  private com.google.gwt.user.client.ui.DecoratorPanel x16 = new com.google.gwt.user.client.ui.DecoratorPanel();
  private com.google.gwt.user.client.ui.VerticalPanel x14 = new com.google.gwt.user.client.ui.VerticalPanel();
  private com.intrinsarc.carrentalgui.client.GUILogic x5 = new com.intrinsarc.carrentalgui.client.GUILogic();
  private com.intrinsarc.carrentalgui.client.RentalService x3 = new com.intrinsarc.carrentalgui.client.RentalService();
  private com.google.gwt.user.client.ui.HorizontalPanel x = new com.google.gwt.user.client.ui.HorizontalPanel();
  private com.google.gwt.user.client.ui.Label x9 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.TextBox x7 = new com.google.gwt.user.client.ui.TextBox();
  private ICreate factory = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      CarWidgetFactory f = new CarWidgetFactory();
      f.initialize(CarRentalFormFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  public com.google.gwt.user.client.ui.VerticalPanel getPanel() { return x2; }

  public CarRentalFormFactory() {}

  public CarRentalFormFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    spacing = new Attribute<Integer>(10);
    spacing1 = new Attribute<Integer>(10);
    spacing2 = new Attribute<Integer>(10);
    spacing3 = new Attribute<Integer>(10);
    width = new Attribute<String>("400px");
    text = new Attribute<String>("Add new car:");
    x1.setSpacing(spacing.get());
    x2.setSpacing(spacing1.get());
    x14.setSpacing(spacing2.get());
    x.setSpacing(spacing3.get());
    x.setWidth(width.get());
    x9.setText(text.get());
    c = x3.getService_IRentalServiceAsync(com.intrinsarc.carrentalgui.client.IRentalServiceAsync.class);
    c1 = factory;
    c2 = x7;
    c3 = x9;
    c4 = x7;
    c5 = x5.getChange_ChangeHandler(com.google.gwt.event.dom.client.ChangeHandler.class);
    c6 = x;
    c7 = x14;
    c8 = x1;
    c9 = x16;
    c10 = x17;
    x.add(c3);
    x1.add(c6);
    x2.add(c10);
    x.add(c2);
    x2.add(c9);
    x5.setService_IRentalServiceAsync(c);
    x5.setCreate_ICreate(c1);
    x5.setText_TextBox(c4);
    x7.addChangeHandler(c5);
    x16.setWidget(c7);
    x17.setWidget(c8);
    x5.afterInit();
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
class CarWidgetFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private Attribute<String> renter = new Attribute<String>("");
  public void setRenter(String renter) { this.renter.set(renter); }
  public String getRenter() { return renter.get(); }
  private Attribute<String> model = new Attribute<String>("");
  public void setModel(String model) { this.model.set(model); }
  public String getModel() { return model.get(); }
  private Attribute<Boolean> rentEnabled = new Attribute<Boolean>(true);
  public void setRentEnabled(boolean rentEnabled) { this.rentEnabled.set(rentEnabled); }
  public boolean getRentEnabled() { return rentEnabled.get(); }
  private Attribute<Boolean> returnEnabled = new Attribute<Boolean>(true);
  public void setReturnEnabled(boolean returnEnabled) { this.returnEnabled.set(returnEnabled); }
  public boolean getReturnEnabled() { return returnEnabled.get(); }
  private Attribute<Integer> carNo = new Attribute<Integer>(0);
  public void setCarNo(int carNo) { this.carNo.set(carNo); }
  public int getCarNo() { return carNo.get(); }
  private Attribute<String> model1;
  private Attribute<Integer> spacing4;
  private Attribute<String> width1;
  private Attribute<String> width2;
  private Attribute<String> text1;
  private Attribute<String> text2;
  private Attribute<String> text3;

  // connectors
  private com.intrinsarc.carrentalgui.client.IRentalServiceAsync c11;
  private com.google.gwt.user.client.ui.Button c12;
  private com.google.gwt.user.client.ui.ChangeListener c13;
  private com.google.gwt.user.client.ui.ClickListener c14;
  private com.google.gwt.user.client.ui.TextBox c15;
  private com.google.gwt.user.client.ui.ClickListener c16;
  private com.google.gwt.user.client.ui.TextBox c17;
  private com.google.gwt.user.client.ui.Button c18;
  private com.google.gwt.user.client.ui.Label c19;
  private com.google.gwt.event.dom.client.ChangeHandler c20;
  private com.google.gwt.user.client.ui.HorizontalPanel c21;

 // parts
  private com.google.gwt.user.client.ui.HorizontalPanel x21 = new com.google.gwt.user.client.ui.HorizontalPanel();
  private com.google.gwt.user.client.ui.Label x34 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.Button x31 = new com.google.gwt.user.client.ui.Button();
  private com.google.gwt.user.client.ui.Button x24 = new com.google.gwt.user.client.ui.Button();
  private com.google.gwt.user.client.ui.TextBox x26 = new com.google.gwt.user.client.ui.TextBox();
  private com.intrinsarc.carrentalgui.client.RentalLogic x23 = new com.intrinsarc.carrentalgui.client.RentalLogic();

  public CarWidgetFactory() {}

  public CarWidgetFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    if (values != null && values.containsKey("renter")) renter = new Attribute<String>((String) values.get("renter"));
    if (values != null && values.containsKey("model")) model = new Attribute<String>((String) values.get("model"));
    if (values != null && values.containsKey("rentEnabled")) rentEnabled = new Attribute<Boolean>((Boolean) values.get("rentEnabled"));
    if (values != null && values.containsKey("returnEnabled")) returnEnabled = new Attribute<Boolean>((Boolean) values.get("returnEnabled"));
    if (values != null && values.containsKey("carNo")) carNo = new Attribute<Integer>((Integer) values.get("carNo"));
    model1 = new Attribute<String>(model.get());
    spacing4 = new Attribute<Integer>(10);
    width1 = new Attribute<String>("400px");
    width2 = new Attribute<String>("100px");
    text1 = new Attribute<String>("rent");
    text2 = new Attribute<String>("return");
    text3 = new Attribute<String>(renter.get());
    x21.setSpacing(spacing4.get());
    x21.setWidth(width1.get());
    x34.setText(model1.get());
    x34.setWidth(width2.get());
    x31.setText(text1.get());
    x31.setEnabled(rentEnabled.get());
    x24.setText(text2.get());
    x24.setEnabled(returnEnabled.get());
    x26.setEnabled(returnEnabled.get());
    x26.setText(text3.get());
    x23.setCarNo(carNo);
    c11 = x3.getService_IRentalServiceAsync(com.intrinsarc.carrentalgui.client.IRentalServiceAsync.class);
    c12 = x24;
    c13 = x23.getRenterSet_ChangeListener(com.google.gwt.user.client.ui.ChangeListener.class);
    c14 = x23.getReturn_ClickListener(com.google.gwt.user.client.ui.ClickListener.class);
    c15 = x26;
    c16 = x23.getRent_ClickListener(com.google.gwt.user.client.ui.ClickListener.class);
    c17 = x26;
    c18 = x31;
    c19 = x34;
    c20 = x5.getChange_ChangeHandler(com.google.gwt.event.dom.client.ChangeHandler.class);
    c21 = x21;
    x21.add(c19);
    x21.add(c18);
    x21.add(c17);
    x21.add(c12);
    x23.setService_IRentalServiceAsync(c11);
    x26.addChangeListener(c13);
    x24.addClickListener(c14);
    x23.setRenter_TextBox(c15);
    x31.addClickListener(c16);
    x23.setRefresh_ChangeHandler(c20);
    x14.add(c21);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x21.remove(c19);
    x21.remove(c18);
    x21.remove(c17);
    x21.remove(c12);
    x23.setService_IRentalServiceAsync(null);
    x26.removeChangeListener(c13);
    x24.removeClickListener(c14);
    x23.setRenter_TextBox(null);
    x31.removeClickListener(c16);
    x23.setRefresh_ChangeHandler(null);
    x14.remove(c21);
  }

}
}
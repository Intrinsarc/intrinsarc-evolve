package com.intrinsarc.carrentalgui.client;

import com.intrinsarc.backbone.runtime.api.*;

/** generated by Evolve */
public class CarRentalFormFactory implements IHardcodedFactory
{
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private int spacing;
  private String width;
  private int spacing1;
  private int spacing2;
  private String width1;
  private int spacing3;
  private String text;

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
      CarRentalWidgetFactory f = new CarRentalWidgetFactory();
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
    spacing = 10;
    width = "500px";
    spacing1 = 10;
    spacing2 = 10;
    width1 = "400px";
    spacing3 = 10;
    text = "Add new car:";
    x1.setSpacing(spacing);
    x2.setWidth(width);
    x2.setSpacing(spacing1);
    x14.setSpacing(spacing2);
    x.setWidth(width1);
    x.setSpacing(spacing3);
    x9.setText(text);
    c = x3.getService_Provided();
    c1 = factory;
    c2 = x7;
    c3 = x9;
    c4 = x7;
    c5 = x5;
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
    x5.setService(c);
    x5.setCreate(c1);
    x5.setText(c4);
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
class CarRentalWidgetFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private String model = "";
  public void setModel(String model) { this.model = model; }
  public String getModel() { return model; }
  private boolean rentEnabled = true;
  public void setRentEnabled(boolean rentEnabled) { this.rentEnabled = rentEnabled; }
  public boolean getRentEnabled() { return rentEnabled; }
  private boolean returnEnabled = true;
  public void setReturnEnabled(boolean returnEnabled) { this.returnEnabled = returnEnabled; }
  public boolean getReturnEnabled() { return returnEnabled; }
  private int carNo = 0;
  public void setCarNo(int carNo) { this.carNo = carNo; }
  public int getCarNo() { return carNo; }
  private String renter = "";
  public void setRenter(String renter) { this.renter = renter; }
  public String getRenter() { return renter; }
  private String width2;
  private int spacing4;
  private String width3;
  private String text1;
  private String width4;
  private String text2;

  // connectors
  private com.intrinsarc.carrentalgui.client.IRentalServiceAsync c11;
  private com.google.gwt.user.client.ui.Label c12;
  private com.google.gwt.user.client.ui.Button c13;
  private com.google.gwt.user.client.ui.ChangeListener c14;
  private com.google.gwt.user.client.ui.ClickListener c15;
  private com.google.gwt.user.client.ui.TextBox c16;
  private com.google.gwt.user.client.ui.TextBox c17;
  private com.google.gwt.user.client.ui.Label c18;
  private com.google.gwt.event.dom.client.ChangeHandler c19;
  private com.google.gwt.user.client.ui.HorizontalPanel c20;

 // parts
  private com.google.gwt.user.client.ui.HorizontalPanel x21 = new com.google.gwt.user.client.ui.HorizontalPanel();
  private com.google.gwt.user.client.ui.Label x33 = new com.google.gwt.user.client.ui.Label();
  private com.google.gwt.user.client.ui.Button x26 = new com.google.gwt.user.client.ui.Button();
  private com.google.gwt.user.client.ui.TextBox x28 = new com.google.gwt.user.client.ui.TextBox();
  private com.intrinsarc.carrentalgui.client.RentalLogic x23 = new com.intrinsarc.carrentalgui.client.RentalLogic();
  private com.google.gwt.user.client.ui.Label x24 = new com.google.gwt.user.client.ui.Label();

  public CarRentalWidgetFactory() {}

  public CarRentalWidgetFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    if (values != null && values.containsKey("model")) model = (String) values.get("model");
    if (values != null && values.containsKey("rentEnabled")) rentEnabled = (Boolean) values.get("rentEnabled");
    if (values != null && values.containsKey("returnEnabled")) returnEnabled = (Boolean) values.get("returnEnabled");
    if (values != null && values.containsKey("carNo")) carNo = (Integer) values.get("carNo");
    if (values != null && values.containsKey("renter")) renter = (String) values.get("renter");
    width2 = "400px";
    spacing4 = 10;
    width3 = "150px";
    text1 = "return";
    width4 = "90px";
    text2 = "Enter renter:";
    x21.setWidth(width2);
    x21.setSpacing(spacing4);
    x33.setWidth(width3);
    x33.setText(model);
    x26.setEnabled(returnEnabled);
    x26.setText(text1);
    x28.setEnabled(rentEnabled);
    x28.setText(renter);
    x23.setCarNo(carNo);
    x24.setWidth(width4);
    x24.setText(text2);
    c11 = x3.getService_Provided();
    c12 = x24;
    c13 = x26;
    c14 = x23.getRenterSet_Provided();
    c15 = x23;
    c16 = x28;
    c17 = x28;
    c18 = x33;
    c19 = x5;
    c20 = x21;
    x21.add(c18);
    x21.add(c12);
    x21.add(c17);
    x21.add(c13);
    x23.setService(c11);
    x28.addChangeListener(c14);
    x26.addClickListener(c15);
    x23.setRenter(c16);
    x23.setRefresh(c19);
    x14.add(c20);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x21.remove(c18);
    x21.remove(c12);
    x21.remove(c17);
    x21.remove(c13);
    x23.setService(null);
    x28.removeChangeListener(c14);
    x26.removeClickListener(c15);
    x23.setRenter(null);
    x23.setRefresh(null);
    x14.remove(c20);
  }

}
}

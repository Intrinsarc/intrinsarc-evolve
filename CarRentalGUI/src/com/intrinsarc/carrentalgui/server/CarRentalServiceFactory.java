package com.intrinsarc.carrentalgui.server;

import com.intrinsarc.backbone.runtime.api.*;

/** generated by Evolve */
public class CarRentalServiceFactory implements IHardcodedFactory
{
  private java.util.List<IHardcodedFactory> children;

  // attributes

  // connectors
  private com.intrinsarc.backbone.runtime.api.ICreate c;

 // parts
  private com.intrinsarc.carrentalgui.server.CarRental x1 = new com.intrinsarc.carrentalgui.server.CarRental();
  private ICreate factory = new ICreate() {
    public Object create(java.util.Map<String, Object> values) {
      RentalCarFactory f = new RentalCarFactory();
      f.initialize(CarRentalServiceFactory.this, values);
      if (children == null)
        children = new java.util.ArrayList<IHardcodedFactory>();
      children.add(f);

      return f;
    }
    public void destroy(Object memento) { ((IHardcodedFactory) memento).destroy(); }
  };
  public com.intrinsarc.carrentalgui.client.IRentalService getService_Provided() { return x1; }

  public CarRentalServiceFactory() {}

  public CarRentalServiceFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    c = factory;
    x1.setCreate(c);
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
class RentalCarFactory implements IHardcodedFactory
{
  private IHardcodedFactory parent;
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private String model = new String();
  public void setModel(String model) { this.model = model; }
  public String getModel() { return model; }
  private java.util.Date purchased = new java.util.Date();
  public void setPurchased(java.util.Date purchased) { this.purchased = purchased; }
  public java.util.Date getPurchased() { return purchased; }
  private String renter;

  // connectors
  private com.intrinsarc.base.IRenterDetails c1;
  private com.intrinsarc.base.IRenterDetails c2;
  private com.intrinsarc.base.IRentalCarDetails c3;

 // parts
  private com.intrinsarc.base.RentalCarDetails x2 = new com.intrinsarc.base.RentalCarDetails();
  private com.intrinsarc.base.RenterDetails x3 = new com.intrinsarc.base.RenterDetails();

  public RentalCarFactory() {}

  public RentalCarFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    if (values != null && values.containsKey("model")) model = (String) values.get("model");
    if (values != null && values.containsKey("purchased")) purchased = (java.util.Date) values.get("purchased");
    renter = "";
    x2.setPurchased(purchased);
    x2.setModel(model);
    x3.setRenter(renter);
    c1 = x3;
    c2 = x3;
    c3 = x2;
    x2.setRenter(c1);
    x1.addRenter(c2);
    x1.addCar(c3);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x2.setRenter(null);
    x1.removeRenter(c2);
    x1.removeCar(c3);
  }

}
}

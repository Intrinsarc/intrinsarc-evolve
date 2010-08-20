package com.intrinsarc.carrentalgui.jpaserver;

import com.intrinsarc.backbone.runtime.api.*;

/** generated by Evolve */
public class CarRentalServiceFactory implements IHardcodedFactory
{
  private java.util.List<IHardcodedFactory> children;

  // attributes

  // connectors
  private com.intrinsarc.backbone.runtime.api.ICreate c;

 // parts
  private com.intrinsarc.carrentalgui.server.JPACarRental x1 = new com.intrinsarc.carrentalgui.server.JPACarRental();
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
  public com.intrinsarc.carrentalgui.client.IRentalService getService() { return x1.getService_IRentalService(null); }

  public CarRentalServiceFactory() {}

  public CarRentalServiceFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    c = factory;
    x1.setCreate_ICreate(c);
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
  private Attribute<java.util.Date> purchased = new Attribute<java.util.Date>(new java.util.Date());
  public void setPurchased(java.util.Date purchased) { this.purchased.set(purchased); }
  public java.util.Date getPurchased() { return purchased.get(); }
  private Attribute<String> model = new Attribute<String>(new String());
  public void setModel(String model) { this.model.set(model); }
  public String getModel() { return model.get(); }
  private Attribute<String> renterName;

  // connectors
  private com.intrinsarc.cars.IRenterDetails c1;
  private com.intrinsarc.cars.IRentalCarDetails c2;
  private com.intrinsarc.cars.IRenterDetails c3;

 // parts
  private com.intrinsarc.cars.RentalCarDetails x2 = new com.intrinsarc.cars.RentalCarDetails();
  private com.intrinsarc.cars.RenterDetails x3 = new com.intrinsarc.cars.RenterDetails();

  public RentalCarFactory() {}

  public RentalCarFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    this.parent = parent;
    if (values != null && values.containsKey("purchased")) purchased = new Attribute<java.util.Date>((java.util.Date) values.get("purchased"));
    if (values != null && values.containsKey("model")) model = new Attribute<String>((String) values.get("model"));
    renterName = new Attribute<String>(null);
    x2.setModel(model);
    x2.setPurchased(purchased);
    x3.setRenterName(renterName);
    c1 = x3.getDetails_IRenterDetails(com.intrinsarc.cars.IRenterDetails.class);
    c2 = x2.getDetails_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails.class);
    c3 = x3.getDetails_IRenterDetails(com.intrinsarc.cars.IRenterDetails.class);
    x2.setRenter_IRenterDetails(c1);
    x1.setCars_IRentalCarDetails(c2, -1);
    x1.setRenters_IRenterDetails(c3, -1);
    return this;
  }
  public void childDestroyed(IHardcodedFactory child) { children.remove(child); }

  public void destroy()
  {
    destroyChildren(parent, this, children);
    x2.setRenter_IRenterDetails(null);
    x1.removeCars_IRentalCarDetails(c2);
    x1.removeRenters_IRenterDetails(c3);
  }

}
}

package hardcoded;

import com.intrinsarc.backbone.runtime.api.*;

/** generated by Evolve */
public class CarsExampleFactory implements IHardcodedFactory
{
  private java.util.List<IHardcodedFactory> children;

  // attributes
  private java.util.Date purchasedWhen;
  private java.lang.String model;
  private java.util.Date purchasedWhen1;
  private java.lang.String model1;
  private java.lang.String renterName;
  private java.lang.String renterName1;

  // connectors
  private com.intrinsarc.base.IRenterDetails c;
  private com.intrinsarc.base.IRentalCarDetails c1;
  private com.intrinsarc.base.IRenterDetails c2;
  private com.intrinsarc.base.IRentalCarDetails c3;

 // parts
  private com.intrinsarc.base.CarsExampleRunner x = new com.intrinsarc.base.CarsExampleRunner();
  private com.intrinsarc.base.RentalCarDetails x5 = new com.intrinsarc.base.RentalCarDetails();
  private com.intrinsarc.base.RenterDetails x6 = new com.intrinsarc.base.RenterDetails();
  private com.intrinsarc.base.RentalCarDetails x1 = new com.intrinsarc.base.RentalCarDetails();
  private com.intrinsarc.base.RenterDetails x2 = new com.intrinsarc.base.RenterDetails();
  public com.intrinsarc.backbone.runtime.api.IRun getRun() { return x.getRun_IRun(null); }

  public CarsExampleFactory() {}

  public CarsExampleFactory initialize(IHardcodedFactory parent, java.util.Map<String, Object> values)
  {
    purchasedWhen = new java.util.Date(105, 10, 25);
    model = "Porsche";
    purchasedWhen1 = new java.util.Date(109, 5, 15);
    model1 = "Mini";
    renterName = null;
    renterName1 = null;
    x5.setPurchased(purchasedWhen);
    x5.setModel(model);
    x6.setRenterName(renterName);
    x1.setPurchased(purchasedWhen1);
    x1.setModel(model1);
    x2.setRenterName(renterName1);
    c = x2.getDetails_IRenterDetails(com.intrinsarc.base.IRenterDetails.class);
    c1 = x1.getDetails_IRentalCarDetails(com.intrinsarc.base.IRentalCarDetails.class);
    c2 = x6.getDetails_IRenterDetails(com.intrinsarc.base.IRenterDetails.class);
    c3 = x5.getDetails_IRentalCarDetails(com.intrinsarc.base.IRentalCarDetails.class);
    x.setCars_IRentalCarDetails(c3, -1);
    x.setCars_IRentalCarDetails(c1, -1);
    x1.setRenter_IRenterDetails(c);
    x5.setRenter_IRenterDetails(c2);
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
}

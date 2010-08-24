package com.intrinsarc.base;


public class RentalCarDetails
// start generated code
  // main port
  implements com.intrinsarc.base.IRentalCarDetails
{
  // attributes
	private java.util.Date purchased;
	private String model;

  // attribute setters and getters
	public java.util.Date getPurchased() { return purchased; }
	public void setPurchased(java.util.Date purchased) { this.purchased = purchased;}
	public String getModel() { return model; }
	public void setModel(String model) { this.model = model;}

  // required ports
	private com.intrinsarc.base.IRenterDetails renter;
  // provided ports

  // port setters and getters
	public void setRenter(com.intrinsarc.base.IRenterDetails renter) { this.renter = renter; }
// end generated code
	
	public boolean isRented()
	{
		return renter.isRented();
	}
}

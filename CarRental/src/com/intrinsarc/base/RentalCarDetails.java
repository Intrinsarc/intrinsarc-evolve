package com.intrinsarc.base;

import java.util.*;

public class RentalCarDetails
{
// start generated code
// attributes
	private java.util.Date purchased;
	private String model;
// required ports
	private com.intrinsarc.base.IRenterDetails renter;
// provided ports
	private IRentalCarDetailsDetailsImpl details_IRentalCarDetailsProvided = new IRentalCarDetailsDetailsImpl();
// setters and getters
	public java.util.Date getPurchased() { return purchased; }
	public void setPurchased(java.util.Date purchased) { this.purchased = purchased;}
	public String getModel() { return model; }
	public void setModel(String model) { this.model = model;}
	public void setRenter_IRenterDetails(com.intrinsarc.base.IRenterDetails renter) { this.renter = renter; }
	public com.intrinsarc.base.IRentalCarDetails getDetails_IRentalCarDetails(Class<?> required) { return details_IRentalCarDetailsProvided; }
// end generated code


	private class IRentalCarDetailsDetailsImpl implements com.intrinsarc.base.IRentalCarDetails
	{
		public String getModel()
		{
			return model;
		}

		public Date getPurchased()
		{
			return purchased;
		}

		public boolean isRented()
		{
			return renter.isRented();
		}
	}

}

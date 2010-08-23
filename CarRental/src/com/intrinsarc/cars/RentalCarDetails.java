package com.intrinsarc.cars;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;

public class RentalCarDetails
{
// start generated code
// attributes
	private java.util.Date purchased;
	private String model;
// required ports
	private com.intrinsarc.cars.IRenterDetails renter;
// provided ports
	private IRentalCarDetailsDetailsImpl details_IRentalCarDetailsProvided = new IRentalCarDetailsDetailsImpl();
// setters and getters
	public java.util.Date getPurchased() { return purchased; }
	public void setPurchased(java.util.Date purchased) { this.purchased = purchased;}
	public java.lang.String getModel() { return model; }
	public void setModel(java.lang.String model) { this.model = model;}
	public void setRenter_IRenterDetails(com.intrinsarc.cars.IRenterDetails renter) { this.renter = renter; }
	public com.intrinsarc.cars.IRentalCarDetails getDetails_IRentalCarDetails(Class<?> required) { return details_IRentalCarDetailsProvided; }
// end generated code


	private class IRentalCarDetailsDetailsImpl implements com.intrinsarc.cars.IRentalCarDetails
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

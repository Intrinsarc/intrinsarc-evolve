package com.intrinsarc.cars;

import com.intrinsarc.backbone.runtime.api.*;

public class RenterDetails
{
// start generated code
// attributes
	private Attribute<java.lang.String> renterName = new Attribute<java.lang.String>("");
	private Attribute<java.lang.Boolean> isRented = new Attribute<java.lang.Boolean>(false);
// required ports
// provided ports
	private IRenterDetailsDetailsImpl details_IRenterDetailsProvided = new IRenterDetailsDetailsImpl();
// setters and getters
	public Attribute<java.lang.String> getRenterName() { return renterName; }
	public void setRenterName(Attribute<java.lang.String> renterName) { this.renterName = renterName;}
	public void setRawRenterName(java.lang.String renterName) { this.renterName.set(renterName);}
	public Attribute<java.lang.Boolean> getIsRented() { return isRented; }
	public void setIsRented(Attribute<java.lang.Boolean> isRented) { this.isRented = isRented;}
	public void setRawIsRented(java.lang.Boolean isRented) { this.isRented.set(isRented);}
	public com.intrinsarc.cars.IRenterDetails getDetails_IRenterDetails(Class<?> required) { return details_IRenterDetailsProvided; }
// end generated code


	private class IRenterDetailsDetailsImpl implements com.intrinsarc.cars.IRenterDetails
	{
		public void setRenter(String renter)
		{
			renterName.set(renter);
		}
		
		public String getRenter()
		{
			return renterName.get();
		}

		public boolean isRented()
		{
			return isRented.get();
		}
	}
}

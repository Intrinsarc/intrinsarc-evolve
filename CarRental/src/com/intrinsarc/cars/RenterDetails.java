package com.intrinsarc.cars;

import com.intrinsarc.backbone.runtime.api.*;

public class RenterDetails
{
// start generated code
// attributes
	private String renterName = null;
// required ports
// provided ports
	private IRenterDetailsDetailsImpl details_IRenterDetailsProvided = new IRenterDetailsDetailsImpl();
// setters and getters
	public java.lang.String getRenterName() { return renterName; }
	public void setRenterName(java.lang.String renterName) { this.renterName = renterName;}
	public com.intrinsarc.cars.IRenterDetails getDetails_IRenterDetails(Class<?> required) { return details_IRenterDetailsProvided; }
// end generated code


	private class IRenterDetailsDetailsImpl implements com.intrinsarc.cars.IRenterDetails
	{
		public void setRenter(String renter)
		{
			renterName = renter;
		}
		
		public String getRenter()
		{
			return renterName;
		}

		public boolean isRented()
		{
			return renterName != null && renterName.length() > 0;
		}
	}
}

package com.intrinsarc.base;


public class RenterDetails
// start generated code
  // main port
  implements com.intrinsarc.base.IRenterDetails
{
  // attributes
	private String renter = "";

  // attribute setters and getters
	public String getRenter() { return renter; }
	public void setRenter(String renter) { this.renter = renter;}

  // required ports
  // provided ports

  // port setters and getters
// end generated code
	
	public boolean isRented()
	{
		return renter != null && renter.length() > 0;
	}
}

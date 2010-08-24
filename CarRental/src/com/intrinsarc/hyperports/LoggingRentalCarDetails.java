package com.intrinsarc.hyperports;

import java.util.*;

public class LoggingRentalCarDetails
// start generated code

  // main port
  implements com.intrinsarc.base.IRentalCarDetails
{
  // attributes
	private String model;

  // attribute setters and getters
	public String getModel() { return model; }
	public void setModel(String model) { this.model = model;}

  // required ports
	private com.intrinsarc.base.IRenterDetails renter;
	private com.intrinsarc.hyperports.ILogger logger;
  // provided ports

  // port setters and getters
	public void setRenter(com.intrinsarc.base.IRenterDetails renter) { this.renter = renter; }
	public void setLogger(com.intrinsarc.hyperports.ILogger logger) { this.logger = logger; }
// end generated code

	private Date purchased;
	public void setPurchased(java.util.Date purchased) { this.purchased = purchased;}
	public Date getPurchased()
	{
		if (logger != null)
			logger.log(">> returning purchased field");
		return purchased;
	}

	public boolean isRented()
	{
		return renter.getRenter() != null;
	}
}

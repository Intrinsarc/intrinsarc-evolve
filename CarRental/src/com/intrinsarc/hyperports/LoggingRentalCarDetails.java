package com.intrinsarc.hyperports;

import java.util.*;

public class LoggingRentalCarDetails
// start generated code
	// main port
 extends com.intrinsarc.base.RentalCarDetails implements com.intrinsarc.base.IRentalCarDetails
{
	// required ports
	private com.intrinsarc.hyperports.ILogger logger;

	// port setters and getters
	public void setLogger(com.intrinsarc.hyperports.ILogger logger) { this.logger = logger; }

// end generated code

	@Override
	public Date getPurchased()
	{
		if (logger != null)
			logger.log(">> returning purchased field");
		return super.getPurchased();
	}
}

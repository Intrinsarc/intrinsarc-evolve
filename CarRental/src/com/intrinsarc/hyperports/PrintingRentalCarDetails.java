package com.intrinsarc.hyperports;

import java.util.*;

public class PrintingRentalCarDetails
// start generated code
	// main port
 extends com.intrinsarc.base.RentalCarDetails implements com.intrinsarc.base.IRentalCarDetails
{
	// required ports
	private com.intrinsarc.hyperports.IPrinter printer;

	// port setters and getters
	public void setPrinter(com.intrinsarc.hyperports.IPrinter printer) { this.printer = printer; }

// end generated code

	private Date lastAccess = null;
	
	@Override
	public Date getPurchased()
	{
		if (printer != null)
		{
			String access = ">> purchased field accessed for car " + getModel(); 
			if (lastAccess == null)
				access += ", not previously accessed";
			else
				access += ", previous access was " + lastAccess;
			
			printer.print(access);
			lastAccess = new Date();
		}
		return super.getPurchased();
	}
}

package com.intrinsarc.states;

import com.intrinsarc.backbone.runtime.api.*;

public class Rented
// start generated code
	// main port
 extends com.intrinsarc.backbone.runtime.implementation.State implements com.intrinsarc.states.IRentalEvent
{
	// attributes
	private String renterName = "";

	// attribute setters and getters
	public String getRenterName() { return renterName; }
	public void setRenterName(String renterName) { this.renterName = renterName;}

// end generated code
	
	public boolean isRented()
	{
		return true;
	}

	public void rent()
	{
		throw new IllegalStateException("Already rented!");
	}
	
	public void returnRental()
	{
		current = !out.enter();
		renterName = null;
	}
	
	public String toString()
	{
		return "rented";
	}
}

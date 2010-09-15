package com.intrinsarc.states;


public class Available
// start generated code
	// main port
 extends com.intrinsarc.backbone.runtime.implementation.State implements com.intrinsarc.states.IRentalEvent
{
// end generated code

	public boolean isRented()
	{
		return false;
	}

	public void rent()
	{
		current = !out.enter();
	}

	public void returnRental()
	{
		// does nothing in this state
	}

	public void setRenterName(String name)
	{
		throw new IllegalStateException("Cannot set renter as car is currently available");
	}
	
	public String toString()
	{
		return "available";
	}

	public String getRenterName()
	{
		return "";
	}
}

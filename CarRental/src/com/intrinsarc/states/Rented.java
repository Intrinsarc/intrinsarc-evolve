package com.intrinsarc.states;

import com.intrinsarc.backbone.runtime.api.*;

public class Rented
// start generated code
	// main port
 implements com.intrinsarc.states.IRentalEvent
{
	// attributes
	private String renterName = "";

	// attribute setters and getters
	public String getRenterName() { return renterName; }
	public void setRenterName(String renterName) { this.renterName = renterName;}

	// required ports
	private ITransition out;
	// provided ports
	private ITransitionInImpl in_Provided = new ITransitionInImpl();

	// port setters and getters
	public void setOut(ITransition out) { this.out = out; }
	public ITransition getIn_Provided() { return in_Provided; }

// end generated code
	
	private boolean current;

	public boolean isCurrent()
	{
		return current;
	}

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

	private class ITransitionInImpl implements ITransition
	{
		public boolean enter()
		{
			current = true;
			return current;
		}
	}
}

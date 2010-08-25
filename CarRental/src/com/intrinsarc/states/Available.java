package com.intrinsarc.states;

import com.intrinsarc.backbone.runtime.api.*;

public class Available
// start generated code
  // main port
  implements com.intrinsarc.states.IRentalEvent
{
  // required ports
	private com.intrinsarc.backbone.runtime.api.ITransition out;
  // provided ports
	private ITransitionInImpl in_Provided = new ITransitionInImpl();

  // port setters and getters
	public void setOut(com.intrinsarc.backbone.runtime.api.ITransition out) { this.out = out; }
	public com.intrinsarc.backbone.runtime.api.ITransition getIn_Provided() { return in_Provided; }
// end generated code

	private boolean current;

	public boolean isCurrent()
	{
		return current;
	}

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

	private class ITransitionInImpl implements ITransition
	{
		public boolean enter()
		{
			current = true;
			return current;
		}
	}
}

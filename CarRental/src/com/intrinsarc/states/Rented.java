package com.intrinsarc.states;

import com.intrinsarc.backbone.runtime.api.*;

public class Rented
{
// start generated code
// attributes
	private String renterName = null;
// required ports
	private com.intrinsarc.backbone.runtime.api.ITransition out;
// provided ports
	private ITransitionInImpl in_ITransitionProvided = new ITransitionInImpl();
	private IRentalEventEventsImpl events_IRentalEventProvided = new IRentalEventEventsImpl();
// setters and getters
	public String getRenterName() { return renterName; }
	public void setRenterName(String renterName) { this.renterName = renterName;}
	public void setOut_ITransition(com.intrinsarc.backbone.runtime.api.ITransition out) { this.out = out; }
	public com.intrinsarc.backbone.runtime.api.ITransition getIn_ITransition(Class<?> required) { return in_ITransitionProvided; }
	public com.intrinsarc.states.IRentalEvent getEvents_IRentalEvent(Class<?> required) { return events_IRentalEventProvided; }
// end generated code
	
	private boolean current;

	private class IRentalEventEventsImpl implements com.intrinsarc.states.IRentalEvent
	{
		public boolean isCurrent()
		{
			return current;
		}

		public String getRenter()
		{
			return renterName;
		}

		public boolean isRented()
		{
			return true;
		}

		public void rent()
		{
			throw new IllegalStateException("Already rented!");
		}
		
		public void setRenter(String newRenterName)
		{
			renterName = newRenterName;
		}

		@Override
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

	private class ITransitionInImpl implements ITransition
	{
		public boolean enter()
		{
			current = true;
			return current;
		}
	}
}

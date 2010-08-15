package com.intrinsarc.states;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;

public class Rented
{
// start generated code
// attributes
	private Attribute<java.lang.String> renterName = new Attribute<java.lang.String>(null);
// required ports
	private com.intrinsarc.backbone.runtime.api.ITransition out;
// provided ports
	private ITransitionInImpl in_ITransitionProvided = new ITransitionInImpl();
	private IRentalEventEventsImpl events_IRentalEventProvided = new IRentalEventEventsImpl();
// setters and getters
	public Attribute<java.lang.String> getRenterName() { return renterName; }
	public void setRenterName(Attribute<java.lang.String> renterName) { this.renterName = renterName;}
	public void setRawRenterName(java.lang.String renterName) { this.renterName.set(renterName);}
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
			System.out.println("$$ renterName = " + renterName);
			return renterName.get();
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
			renterName.set(newRenterName);
		}

		@Override
		public void returnRental()
		{
			current = !out.enter();
			renterName.set(null);
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

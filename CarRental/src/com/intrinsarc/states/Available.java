package com.intrinsarc.states;

import com.intrinsarc.backbone.runtime.api.*;

public class Available
{
// start generated code
// attributes
// required ports
	private com.intrinsarc.backbone.runtime.api.ITransition out;
// provided ports
	private ITransitionInImpl in_ITransitionProvided = new ITransitionInImpl();
	private IRentalEventEventsImpl events_IRentalEventProvided = new IRentalEventEventsImpl();
// setters and getters
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

		public void setRenter(String name)
		{
			throw new IllegalStateException("Cannot set renter as car is currently available");
		}
		
		public String toString()
		{
			return "available";
		}

		public String getRenter()
		{
			return "";
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

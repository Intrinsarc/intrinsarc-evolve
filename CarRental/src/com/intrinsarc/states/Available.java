package com.intrinsarc.states;

import java.util.*;

import com.hopstepjump.backbone.runtime.api.*;

public class Available
{
// start generated code
// attributes
// required ports
	private com.hopstepjump.backbone.runtime.api.ITransition out;
// provided ports
	private ITransitionInImpl in_ITransitionProvided = new ITransitionInImpl();
	private IRentalEventEventsImpl events_IRentalEventProvided = new IRentalEventEventsImpl();
// setters and getters
	public void setOut_ITransition(com.hopstepjump.backbone.runtime.api.ITransition out) { this.out = out; }
	public com.hopstepjump.backbone.runtime.api.ITransition getIn_ITransition(Class<?> required) { return in_ITransitionProvided; }
	public com.intrinsarc.states.IRentalEvent getEvents_IRentalEvent(Class<?> required) { return events_IRentalEventProvided; }
// end generated code

	private boolean current;

	private class IRentalEventEventsImpl implements com.intrinsarc.states.IRentalEvent
	{
		public boolean isCurrent()
		{
			return current;
		}

		public String getRenterName()
		{
			return null;
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

		public void setRenter(String name, Date whenTo)
		{
			throw new IllegalStateException("Cannot set renter as car is currently available");
		}
	}

	private class ITransitionInImpl implements ITransition
	{
		public boolean enter()
		{
			current = true;
			return true;
		}
	}
}

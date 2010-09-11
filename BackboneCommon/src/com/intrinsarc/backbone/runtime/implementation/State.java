package com.intrinsarc.backbone.runtime.implementation;

import com.intrinsarc.backbone.runtime.api.*;

public abstract class State
// start generated code
	// main port
 implements IEvent
{
	// required ports
	protected ITransition out;
	// provided ports
	protected ITransitionInImpl in_Provided = new ITransitionInImpl();

	// port setters and getters
	public void setOut(ITransition out) { this.out = out; }
	public ITransition getIn_Provided() { return in_Provided; }

// end generated code
	protected boolean current;

	private class ITransitionInImpl implements ITransition
	{
		public boolean enter()
		{
			current = true;
			return current;
		}
	}
}

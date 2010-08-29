package com.intrinsarc.backbone.runtime.implementation;

import com.intrinsarc.backbone.runtime.api.*;


public class Terminal implements IStateTerminalComponent, ITerminal
{
// start generated code
	private ITransition out_ITransitionRequired;
	public void setOut(ITransition out) { out_ITransitionRequired = out; }
// end generated code
	
	private boolean current;
	
	public boolean isCurrent()
	{
		return current;
	}
	
	public void moveToNextState()
	{
		if (out_ITransitionRequired != null)
			current = !out_ITransitionRequired.enter();
	}

	public ITransition getIn(Class<?> required)
	{
		return new ITransition()
		{
			public boolean enter()
			{
				current = true;
				return true;
			}
		};
	}
}

package com.intrinsarc.backbone.runtime.api;

public interface IStateDispatcher
{
	public void setDDispatch(IEvent event, int index);	
	public void removeDDispatch(IEvent event);
	
	public void setDStart(ITerminal start);
	public void setDEnd(ITerminal end, int index);
	
	public IEvent getDEvents_Provided(Class<?> required);
}


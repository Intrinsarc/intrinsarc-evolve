package com.intrinsarc.backbone.runtime.api;

public interface IStateDispatcher
{
	public void setDDispatch(IEvent event, int index);	
	public void setDStart(ITerminal start);
	public void setDEnd(ITerminal end, int index);
	public IEvent getDEvents(Class<?> required);
	public void removeDDispatch(IEvent event);
}


package com.intrinsarc.backbone.runtime.api;


public interface IStateDispatcher
{
	public void setDDispatch_IEvent(IEvent event, int index);	
	public void setDStart_ITerminal(ITerminal start);
	public void setDEnd_ITerminal(ITerminal end, int index);
	public IEvent getDEvents_IEvent(Class<?> required);
}

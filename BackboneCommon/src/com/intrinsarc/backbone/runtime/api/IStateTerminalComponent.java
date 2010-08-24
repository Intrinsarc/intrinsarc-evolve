package com.intrinsarc.backbone.runtime.api;

public interface IStateTerminalComponent
{
	public ITransition getIn(Class<?> required);
	public void setOut(ITransition out);
}

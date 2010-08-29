package com.intrinsarc.backbone.runtime.api;

public interface IStateTerminalComponent extends ITerminal
{
	public ITransition getIn(Class<?> required);
	public void setOut(ITransition out);
}

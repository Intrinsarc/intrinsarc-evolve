package com.intrinsarc.backbone.runtime.api;

public interface IStateTerminalComponent
{
	public ITransition getIn(Class<?> required);
	public ITerminal getStartTerminal_Provided();
	public ITerminal getEndTerminal_Provided();
	public void setOut(ITransition out);
}

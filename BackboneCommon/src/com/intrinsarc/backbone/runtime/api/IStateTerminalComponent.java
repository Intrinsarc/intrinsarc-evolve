package com.intrinsarc.backbone.runtime.api;

public interface IStateTerminalComponent
{
	public ITerminal getTerminal_ITerminal(Class<?> required);
	public ITransition getIn_ITransition(Class<?> required);
	public void setOut_ITransition(ITransition out);
	public ITerminal getStartTerminal_ITerminal(Class<?> required);
	public ITerminal getEndTerminal_ITerminal(Class<?> required);
}

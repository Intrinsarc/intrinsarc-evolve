package com.hopstepjump.backbone.parser;

public class Match
{
	private IMatcher matcher;
	private IAction action;

	public Match(IMatcher matcher, IAction action)
	{
		this.matcher = matcher;
		this.action = action;
	}

	public IMatcher getMatcher()
	{
		return matcher;
	}

	public IAction getAction()
	{
		return action;
	}
}

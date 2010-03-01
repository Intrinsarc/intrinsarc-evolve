package com.hopstepjump.backbone.parserbase;

public class Match
{
	private IPatternMatcher matcher;
	private IAction action;

	public Match(IPatternMatcher matcher, IAction action)
	{
		this.matcher = matcher;
		this.action = action;
	}

	public IPatternMatcher getMatcher()
	{
		return matcher;
	}

	public IAction getAction()
	{
		return action;
	}
}

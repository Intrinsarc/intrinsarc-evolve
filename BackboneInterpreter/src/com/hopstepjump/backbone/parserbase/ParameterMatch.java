package com.hopstepjump.backbone.parserbase;

public class ParameterMatch extends Match
{
	public ParameterMatch(IAction action)
	{
		super(new ParameterPatternMatcher(), action);
	}
}
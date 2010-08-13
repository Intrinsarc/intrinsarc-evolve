package com.intrinsarc.backbone.parserbase;

public class ParameterMatch extends Match
{
	public ParameterMatch(IAction action)
	{
		super(new ParameterPatternMatcher(), action);
	}
}
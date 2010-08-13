package com.intrinsarc.backbone.parserbase;


public class IntegerMatch extends Match
{
	public IntegerMatch(IAction action)
	{
		super(new IntegerPatternMatcher(), action);
	}
}

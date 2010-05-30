package com.hopstepjump.backbone.parserbase;

public class NonSemiColonOrCommaMatch extends Match
{
	public NonSemiColonOrCommaMatch(IAction action)
	{
		super(new NonSemiColonOrCommaPatternMatcher(), action);
	}
}

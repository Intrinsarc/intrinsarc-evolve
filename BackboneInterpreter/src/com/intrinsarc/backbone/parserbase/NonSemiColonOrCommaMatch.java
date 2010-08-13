package com.intrinsarc.backbone.parserbase;

public class NonSemiColonOrCommaMatch extends Match
{
	private String avoidLiteral;
	
	public NonSemiColonOrCommaMatch(String avoidLiteral, IAction action)
	{
		super(new NonSemiColonOrCommaPatternMatcher(avoidLiteral), action);
	}
}

package com.hopstepjump.backbone.parserbase;


public class LiteralMatch extends Match
{
	public LiteralMatch(IAction action)
	{
		super(new LiteralPatternMatcher(), action);
	}

	public LiteralMatch(String literal, IAction action)
	{
		super(new LiteralPatternMatcher(literal), action);
	}
}

package com.hopstepjump.backbone.parserbase;

public class StringPatternMatcher implements IPatternMatcher
{
	public boolean matches(Token tok)
	{
		return tok.getType().equals(TokenType.STRING);
	}

	public String getDescription()
	{
		return "string";
	}
}

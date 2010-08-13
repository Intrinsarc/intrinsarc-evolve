package com.intrinsarc.backbone.parserbase;

public class IntegerPatternMatcher implements IPatternMatcher
{
	public boolean matches(Token tok)
	{
		return tok.getType().equals(TokenType.INTEGER);
	}

	public String getDescription()
	{
		return "integer";
	}
}

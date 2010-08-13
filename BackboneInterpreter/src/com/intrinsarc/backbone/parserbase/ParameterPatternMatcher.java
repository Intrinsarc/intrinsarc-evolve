package com.intrinsarc.backbone.parserbase;


public class ParameterPatternMatcher implements IPatternMatcher
{
	public ParameterPatternMatcher()
	{
	}

	public boolean matches(Token tok)
	{
		TokenType t = tok.getType();
		return
			t.equals(TokenType.CHAR) ||
			t.equals(TokenType.INTEGER) ||
			t.equals(TokenType.DOUBLE) ||
			t.equals(TokenType.STRING) ||
			t.equals(TokenType.LITERAL);
	}

	public String getDescription()
	{
		return "int, char, double, string or variable parameter";
	}
}

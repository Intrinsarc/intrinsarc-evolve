package com.hopstepjump.backbone.parserbase;

public class NonSemiColonOrCommaPatternMatcher implements IPatternMatcher
{

	public boolean matches(Token tok)
	{
		return tok.getType().equals(TokenType.LITERAL) && !tok.getText().equals(";") && !tok.getText().equals(",");
	}

	public String getDescription()
	{
		return "not a semicolon";
	}
}

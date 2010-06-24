package com.hopstepjump.backbone.parserbase;

public class NonSemiColonOrCommaPatternMatcher implements IPatternMatcher
{
	private String avoidLiteral;
	
	public NonSemiColonOrCommaPatternMatcher(String avoidLiteral)
	{
		this.avoidLiteral = avoidLiteral;
	}
	
	public boolean matches(Token tok)
	{
		return
			tok.getType().equals(TokenType.LITERAL) &&
			!tok.getText().equals(avoidLiteral) &&
			!tok.getText().equals(";") && !tok.getText().equals(",");
	}

	public String getDescription()
	{
		return "not a semicolon";
	}
}

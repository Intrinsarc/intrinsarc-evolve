package com.intrinsarc.backbone.parserbase;

public class LiteralPatternMatcher implements IPatternMatcher
{
	private String literal;
	
	public LiteralPatternMatcher(String literal)
	{
		this.literal = literal;
	}

	public LiteralPatternMatcher()
	{
	}

	public boolean matches(Token tok)
	{
		return tok.getType().equals(TokenType.LITERAL) && (literal == null || tok.getText().equals(literal));
	}

	public String getDescription()
	{
		return new Token(TokenType.LITERAL, literal).toString();
	}
}

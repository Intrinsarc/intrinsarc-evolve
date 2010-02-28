package com.hopstepjump.backbone.parser;

public class LiteralMatcher implements IMatcher
{
	private String literal;
	
	public LiteralMatcher(String literal)
	{
		this.literal = literal;
	}

	public LiteralMatcher()
	{
	}

	@Override
	public boolean matches(Token tok)
	{
		return tok.getType().equals(TokenType.LITERAL) && (literal == null || tok.getText().equals(literal));
	}

	@Override
	public String getDescription()
	{
		return new Token(TokenType.LITERAL, literal).toString();
	}
}

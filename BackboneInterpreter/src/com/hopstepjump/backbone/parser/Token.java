package com.hopstepjump.backbone.parser;

public class Token
{
	private TokenType type;
	private String text;
	
	public Token(TokenType type, String text)
	{
		this.type = type;
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public TokenType getType()
	{
		return type;
	}

	public double interpretAsDouble()
	{
		return Double.parseDouble(text);
	}

	public int interpretAsInt()
	{
		return Integer.parseInt(text);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Token))
			return false;
		Token t = (Token) obj;
		return t.type.equals(type) && t.text.equals(text);
	}

	@Override
	public int hashCode()
	{
		return type.hashCode() ^ text.hashCode();
	}

	@Override
	public String toString()
	{
		return "Token(" + type + ", " + text + ")";
	}
	
}

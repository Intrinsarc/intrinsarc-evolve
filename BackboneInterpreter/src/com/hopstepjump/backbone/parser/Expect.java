package com.hopstepjump.backbone.parser;

public class Expect
{
	private Tokenizer tok;
	
	public Expect(Tokenizer tok)
	{
		this.tok = tok;
	}
	
	public Expect literal(String literal)
	{
		Token t = tok.next();
		if (t == null)
			tok.throwParseException("Expected literal '" + literal + "' but found end of file", false);
		if (!t.equals(new Token(TokenType.LITERAL, literal)))
			tok.throwParseException("Expected literal '" + literal + "' but found " + t, true);
		return this;
	}
	
	public Expect oneOf(Match... matches)
	{
		return optional(true, matches);
	}
	
	public Expect anyOf(Match... matches)
	{
		return optional(false, matches);
	}
	
	public Expect optional(String literal, final boolean toSet[])
	{
		toSet[0] = false;
		return optional(false, new Match(
				new LiteralMatcher(literal),
				new IAction()
				{
					@Override
					public void act(Expect expect, Token tok)
					{
						toSet[0] = true;
					}
				}));
	}
	
	private Expect optional(boolean mustHaveOne, Match... matches)
	{
		Token t = tok.peek();
		if (t == null)
			tok.throwParseException("Expected one of the following " + getDescriptions(matches) + " but found end of file", false);
		if (!t.getType().equals(TokenType.LITERAL) && !mustHaveOne)
			return this;
		if (!t.getType().equals(TokenType.LITERAL))
			tok.throwParseException("Expected one of the following " + getDescriptions(matches) + " but found " + t, false);
		int lp = 0;
		for (Match m : matches)
		{
			if (m.getMatcher().matches(t))
			{
				m.getAction().act(this, t);
				tok.next();
				return this;
			}
			lp++;
		}
		if (mustHaveOne)
			tok.throwParseException("Expected one of the following " + getDescriptions(matches) + " but found " + t, true);
		return this;
	}
	
	public Expect name(String prefixIfNotGlobal, String[] nameToSet)
	{
		Token t = tok.next();
		if (t == null)
			tok.throwParseException("Expected a name but found end of file", false);
		if (!t.getType().equals(TokenType.LITERAL))
			tok.throwParseException("Expected a name but found " + t, true);
		String full = t.getText();
		if (!full.contains("-") && !full.contains("."))
			full = prefixIfNotGlobal + "." + full;
		nameToSet[0] = full;
		return this;
	}
	
	public String makeName(String prefixIfNotGlobal, String text)
	{
		if (!text.contains("-") && !text.contains("."))
			text = prefixIfNotGlobal + "." + text;
		return text;
	}

	public Token parameter()
	{
		Token t = tok.next();
		if (t == null)
			tok.throwParseException("Expected value or variable reference but found end of file", false);
		return t;
	}

	private String getDescriptions(Match[] matches)
	{
		String strs = "(";
		int lp = 0;
		for (Match m : matches)
		{
			if (lp++ != 0)
				strs += ", ";
			strs = m.getMatcher().getDescription();
		}
		return strs;
	}

	public void sequenceOf(LiteralMatcher peekTerminator, Match... matches)
	{
		// TODO Auto-generated method stub
		
	}
}

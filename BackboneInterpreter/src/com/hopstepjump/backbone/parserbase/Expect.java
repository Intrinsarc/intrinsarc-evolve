package com.hopstepjump.backbone.parserbase;

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
	
	public Expect optionalLiteral(String literal, final boolean toSet[])
	{
		toSet[0] = false;
		return optional(false, null, new Match(
				new LiteralPatternMatcher(literal),
				new IAction()
				{
					public void act(Expect expect, Token tok)
					{
						toSet[0] = true;
					}
				}));
	}
	
	public Expect oneOf(Match... matches)
	{
		return optional(true, null, matches);
	}
	
	public Expect anyOf(boolean matched[], Match... matches)
	{
		return optional(false, matched, matches);
	}
	
	public Expect name(String[] uuidToSet, String[] nameToSet)
	{
		return name("", uuidToSet, nameToSet);
	}
	
	public Expect name(String prefixIfNotGlobal, String[] uuidToSet, String[] nameToSet)
	{
		Token t = tok.next();
		if (t == null)
			tok.throwParseException("Expected a name but found end of file", false);
		if (!t.getType().equals(TokenType.LITERAL))
			tok.throwParseException("Expected a name but found " + t, true);
		String full = t.getText();
		if (!full.contains("-") && !full.contains("."))
			full = prefixIfNotGlobal + "." + full;
		uuidToSet[0] = full;
		
		// peek for a string
		Token p = tok.peek();
		if (p != null && p.getType().equals(TokenType.DESCRIPTIVE_NAME))
		{
			nameToSet[0] = tok.next().getText();
		}
		else
			nameToSet[0] = null;
		
		return this;
	}
	
	public String makeName(String prefixIfNotGlobal, String text)
	{
		if (!text.contains("-") && !text.contains("."))
			text = prefixIfNotGlobal + "." + text;
		return text;
	}

	/**
	 * will match one or more of matches 
	 * @param continuer the string to continue when we find it
	 * @param matches
	 */
	public Expect oneOrMore(String continueLiteral, Match... matches)
	{
		Token cnt = new Token(TokenType.LITERAL, continueLiteral);
		for (;;)
		{
			oneOf(matches);
			if (!tok.peek().equals(cnt))
				break;
			tok.next();
		}
		return this;
	}

	/**
	 * will match zero or more of matches.  no continuer string allowed 
	 * @param matches
	 */
	public Expect zeroOrMore(Match... matches)
	{
		boolean matched[] = {false};
		for (;;)
		{
			anyOf(matched, matches);
			if (!matched[0])
				break;
		}
		return this;
	}

	public Expect guard(String literal, IAction action)
	{
		// peek to see if we have a match
		if (tok.peek().equals(new Token(TokenType.LITERAL, literal)))
			action.act(this, tok.next());
		return this;
	}
	
	public Expect inside(IAction action)
	{
		action.act(this, null);
		return this;
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	private Expect optional(boolean mustHaveOne, boolean matched[], Match... matches)
	{
		if (matched != null)
			matched[0] = false;
		Token t = tok.peek();
		if (t == null)
		{
			if (mustHaveOne)
				tok.throwParseException("Expected one of the following " + getDescriptions(matches) + " but found end of file", false);
			return this;
		}
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
				if (matched != null)
					matched[0] = true;
				return this;
			}
			lp++;
		}
		if (mustHaveOne)
			tok.throwParseException("Expected one of the following " + getDescriptions(matches) + " but found " + t, true);
		return this;
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
}

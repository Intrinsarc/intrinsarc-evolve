package com.hopstepjump.backbonetests.parser;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

import com.hopstepjump.backbone.parser.*;

public class ExpectTests
{
	@Test
	public void expectLiteral()
	{
		makeExpect(" hello there").literal("hello").literal("there");
	}
	
	@Test
	public void expectOptionals()
	{
		final String res[] = {""};
		makeExpect(" hello andrew goodbye").literal("hello").
			oneOf(
					new Match(
							new LiteralMatcher("andrew"),
							new IAction()
							{ public void act(Expect expect, Token tok) { res[0] = "optional"; }})).
			literal("goodbye");
		assertEquals("optional", res[0]);
	}
	
	@Test
	public void expectOptionalsMultiple()
	{
		assertEquals("andrew", acceptOptional(makeExpect(" hello andrew goodbye")));
		assertEquals("freddo", acceptOptional(makeExpect(" hello freddo goodbye")));
	}
	
	@Test(expected=ParseException.class)
	public void expectOptionalsBad()
	{
		acceptOptional(makeExpect(" hello foo goodbye"));
	}
	
	private String acceptOptional(Expect e)
	{
		final String res[] = {""};
		e.literal("hello").
			oneOf(
					new Match(
							new LiteralMatcher("andrew"),
							new IAction() { public void act(Expect expect, Token tok) { res[0] = "andrew"; }}),
					new Match(
							new LiteralMatcher("freddo"),
							new IAction() { public void act(Expect expect, Token tok) { res[0] = "freddo"; }}));
		return res[0];
	}
	
	@Test
	public void expectNames()
	{
		String name[] = {""};
		makeExpect("a1-2-3").name("foo", name);
		assertEquals("a1-2-3", name[0]);
		makeExpect("a1.2").name("foo", name);
		assertEquals("a1.2", name[0]);
		makeExpect("Test").name("foo", name);
		assertEquals("foo.Test", name[0]);
	}
	
	
	private Expect makeExpect(String text)
	{
		return new Expect(new Tokenizer(new StringReader(text)));
	}
}

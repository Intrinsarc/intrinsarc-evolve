package com.hopstepjump.backbonetests.parser;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

import com.hopstepjump.backbone.parserbase.*;

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
		final Expect ex = makeExpect(" hello andrew goodbye"); 
		ex.literal("hello").
			oneOf(
					new Match(
							new LiteralPatternMatcher("andrew"),
							new IAction()
							{ public void act() { ex.literal("andrew"); res[0] = "optional"; }})).
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
							new LiteralPatternMatcher("andrew"),
							new IAction() { public void act() { res[0] = "andrew"; }}),
					new Match(
							new LiteralPatternMatcher("freddo"),
							new IAction() { public void act() { res[0] = "freddo"; }}));
		return res[0];
	}
	
	@Test
	public void expectUUIDs()
	{
		UUIDReference ref = new UUIDReference();
		makeExpect("a1-2-3/hello/").uuid(ref);
		assertEquals("a1-2-3", ref.getUUID());
		assertEquals("hello", ref.getName());
		makeExpect("a1.2").uuid(ref);
		assertEquals("a1.2", ref.getUUID());
		makeExpect("Test").uuid(ref);
		assertEquals("Test", ref.getUUID());
	}
	
	
	private Expect makeExpect(String text)
	{
		return new Expect(new Tokenizer("file", new StringReader(text)));
	}
}

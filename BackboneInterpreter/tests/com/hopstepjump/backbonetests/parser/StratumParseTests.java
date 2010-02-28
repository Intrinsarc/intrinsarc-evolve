package com.hopstepjump.backbonetests.parser;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

import com.hopstepjump.backbone.parser.*;

public class StratumParseTests
{
	@Test
	public void TestStratumParse()
	{
		String name[] = {""};
		boolean relaxed[] = {false};
		List<String> dependsOn = new ArrayList<String>();
		parseStratumDeclaration("stratum a is-relaxed;", name, relaxed, dependsOn);
		assertEquals("global.a", name[0]);
		assertTrue(relaxed[0]);
	}
	
	@Test(expected=ParseException.class)
	public void TestStratumParseFail()
	{
		String name[] = {""};
		boolean relaxed[] = {false};
		List<String> dependsOn = new ArrayList<String>();
		parseStratumDeclaration("stratum a is-relaxed test", name, relaxed, dependsOn);
	}
	
	@Test
	public void TestStratumParse2()
	{
		String name[] = {""};
		boolean relaxed[] = {false};
		List<String> dependsOn = new ArrayList<String>();
		parseStratumDeclaration("stratum a is-relaxed depends-on test", name, relaxed, dependsOn);
		assertEquals("global.a", name[0]);
		assertTrue(relaxed[0]);
	}
	
	private void parseStratumDeclaration(String decl, String name[], boolean relaxed[], final List<String> dependsOn)
	{
		final Expect ex = new Expect(new Tokenizer(new StringReader(decl)));
		ex
			.literal("stratum")
			.name("global", name)
			.optional("is-relaxed", relaxed)
			// guard
			.anyOf(
					new Match(
							new LiteralMatcher("depends-on"),
							new IAction()
							{
								public void act(Expect e, Token t)
								{ parseParameters(ex, dependsOn); }
							}))
			.literal(";");
	}
	
	private void parseParameters(final Expect ex, final List<String> dependsOn)
	{
		ex.sequenceOf(
				new LiteralMatcher(";"),
				new Match(
						new LiteralMatcher(),
						new IAction()
						{ public void act(Expect e, Token t)
							{
								dependsOn.add(e.makeName("global", t.getText()));
							}
						}));		
	}
}


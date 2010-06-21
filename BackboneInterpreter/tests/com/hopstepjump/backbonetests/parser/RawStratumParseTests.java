package com.hopstepjump.backbonetests.parser;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

import com.hopstepjump.backbone.parserbase.*;

public class RawStratumParseTests
{
	@Test
	public void TestStratumParse()
	{
		String uuid[] = {""};
		String name[] = {""};
		boolean relaxed[] = {false};
		List<String> dependsOn = new ArrayList<String>();
		parseStratumDeclaration("stratum a is-relaxed;", uuid, name, relaxed, dependsOn);
		assertEquals("a", uuid[0]);
		assertTrue(relaxed[0]);
	}
	
	@Test(expected=ParseException.class)
	public void TestStratumParseFail()
	{
		String uuid[] = {""};
		String name[] = {""};
		boolean relaxed[] = {false};
		List<String> dependsOn = new ArrayList<String>();
		parseStratumDeclaration("stratum a is-relaxed test", uuid, name, relaxed, dependsOn);
	}
	
	@Test
	public void TestStratumParse2()
	{
		String uuid[] = {""};
		String name[] = {""};
		boolean relaxed[] = {false};
		List<String> dependsOn = new ArrayList<String>();
		parseStratumDeclaration("stratum a is-relaxed depends-on test1, test2-a-b;", uuid, name, relaxed, dependsOn);
		assertEquals("a", uuid[0]);
		assertTrue(relaxed[0]);
		assertEquals(2, dependsOn.size());
		assertEquals("test1", dependsOn.get(0));
		assertEquals("test2-a-b", dependsOn.get(1));
	}
	
	private void parseStratumDeclaration(String decl, String uuid[], String name[], boolean relaxed[], final List<String> dependsOn)
	{
		final Expect ex = new Expect(new Tokenizer("file", new StringReader(decl)));
		UUIDReference ref = new UUIDReference();
		ex.
			literal("stratum").
			uuid(ref).
			optionalLiteral("is-relaxed", relaxed).
			guard("depends-on",
					new IAction() { public void act() { parseNames(ex, dependsOn); } }).
			literal(";");
		uuid[0] = ref.getUUID();
		name[0] = ref.getName();
	}
	
	private void parseNames(final Expect ex, final List<String> dependsOn)
	{
		ex.oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{ public void act()
							{
								UUIDReference ref = new UUIDReference();
								ex.uuid(ref);
								dependsOn.add(ref.getUUID());
							}
						}));		
	}
}


package com.intrinsarc.backbonegenerator.tests;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

import com.intrinsarc.backbone.generator.hardcoded.common.*;

public class ClasspathTests
{
	private static final String SEP = File.pathSeparator;
	@Test
	public void testClasspath1()
	{
		compare("a" + SEP + "b" + SEP + "c", "a b c");
	}
	
	@Test
	public void testClasspathQuotes()
	{
		compare(
				"c:\\test\\a.foo" + SEP + "\"hello one two\"" + SEP + "c:test",
				"c:\\test\\a.foo \"hello one two\" c:test");
	}
	
	private void compare(String expected, String actual)
	{
		assertEquals(expected, WriterHelper.replaceSeparators(actual));
	}
}

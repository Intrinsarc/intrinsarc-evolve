package com.hopstepjump.backbonetests.parser;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.parser.*;
import com.hopstepjump.backbone.parserbase.*;

public class LoadListParserTests
{
	@Test
	public void TestSimple() throws FileNotFoundException
	{
			final Expect ex =
				new Expect(
						new Tokenizer(
								new FileReader("tests/com/hopstepjump/backbonetests/parser/simple.loadlist")));
			
			BBLoadList ll = new LoadListParser(ex).parse();
			
			// test the contents
			assertEquals("BB", ll.settable_getVariables().get(0).getName());
			assertEquals("C:/temp/BB", ll.settable_getVariables().get(0).getValue());
			assertEquals("CC", ll.settable_getVariables().get(1).getName());
			assertEquals("C:/temp/BB", ll.settable_getVariables().get(1).getValue());

			assertEquals(7, ll.settable_getStrataDirectories().size());
			assertEquals("backbone", ll.settable_getStrataDirectories().get(1).getStratumName());
			assertEquals("$BB/base/backbone", ll.settable_getStrataDirectories().get(1).getPath());
			assertEquals("backbone :: backbone_profile", ll.settable_getStrataDirectories().get(6).getStratumName());
			assertEquals("$BB/base/backbone/backbone profile", ll.settable_getStrataDirectories().get(6).getPath());
	}
}

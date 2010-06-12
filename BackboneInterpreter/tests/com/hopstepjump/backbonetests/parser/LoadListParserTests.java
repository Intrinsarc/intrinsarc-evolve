package com.hopstepjump.backbonetests.parser;

import java.io.*;

import org.junit.*;

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
			
			new LoadListParser(ex).parse();
			
			// test the contents
			
	}
}

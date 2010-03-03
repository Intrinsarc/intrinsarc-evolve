package com.hopstepjump.backbonetests.parser;

import java.io.*;

import org.junit.*;

import com.hopstepjump.backbone.parser.*;
import com.hopstepjump.backbone.parserbase.*;

public class StratumParserTests
{
	@Test
	public void TestSimple() throws FileNotFoundException
	{
		final Expect ex =
			new Expect(
					new Tokenizer(
							new FileReader("tests/com/hopstepjump/backbonetests/parser/test.bb")));
		
		new StratumParser(ex).parse();
	}
}

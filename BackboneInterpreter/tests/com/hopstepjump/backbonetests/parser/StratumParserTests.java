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
		System.out.println("$$ dir = " + new File("test").getAbsolutePath());
		for (int lp = 0; lp < 10; lp++)
		{
			String file = "tests/com/hopstepjump/backbonetests/parser/test.bb";
			final Expect ex =
				new Expect(
						new Tokenizer(
								file, new FileReader(file)));
			
			new StratumParser(ex).parse();
		}
	}
}

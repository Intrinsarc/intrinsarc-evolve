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
		for (int lp = 0; lp < 10; lp++)
		{
			final Expect ex =
				new Expect(
						new Tokenizer(
								new FileReader("C:/Users/Andrew/Desktop/feature.bb")));
			
			new StratumParser(ex).parse();
		}
	}
}

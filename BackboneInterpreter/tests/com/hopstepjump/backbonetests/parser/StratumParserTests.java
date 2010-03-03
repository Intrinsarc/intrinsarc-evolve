package com.hopstepjump.backbonetests.parser;

import java.io.*;

import org.junit.*;

import com.hopstepjump.backbone.parser.*;
import com.hopstepjump.backbone.parserbase.*;

public class StratumParserTests
{
	@Test
	public void TestSimple()
	{
		String decl = "stratum a-b-c/foo/ is-relaxed is-destructive depends-on a, b { }";
		final Expect ex = new Expect(new Tokenizer(new StringReader(decl)));
		new StratumParser(ex).parse();
	}
}

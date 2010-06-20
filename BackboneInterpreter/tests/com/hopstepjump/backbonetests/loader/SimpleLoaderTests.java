package com.hopstepjump.backbonetests.loader;

import java.io.*;
import java.util.*;

import org.junit.*;

import com.hopstepjump.backbone.*;
import com.hopstepjump.backbone.nodes.*;


public class SimpleLoaderTests
{
	@Test
	public void loadSimple() throws Exception
	{
		// load in a simple program consisting of 2 strata
		System.out.println("$$ dir = " + new File("test").getAbsolutePath());
		List<BBStratum> strata = BackboneInterpreter.loadSystem(new File("./tests/com/hopstepjump/backbonetests/loader/backbone/system.loadlist"), ">>");
	}
}

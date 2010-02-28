package com.hopstepjump.backbonetests.parser;

import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Suite.*;

@SuiteClasses({StratumParseTests.class, ExpectTests.class, TokenizerTests.class})
@RunWith(Suite.class)
public class AllTests
{
}

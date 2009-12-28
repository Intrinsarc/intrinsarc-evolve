package com.hopstepjump.backbonetests;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SimpleTests.class,
        StrataTests.class,
        InferenceTests.class,
        StereotypeTests.class})
public class AllTests
{
}

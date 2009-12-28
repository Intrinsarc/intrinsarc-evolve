/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.unittests;

import junit.framework.*;

/**
 * @version 	1.0
 * @author Andrew
 */
public class AllTests extends TestSuite
{
	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(AllTests.class);
	}

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
	  suite.addTest(new TestSuite(TestWMFWord.class));
	  suite.addTest(new TestSuite(TestWMFDoubleWord.class));
	  suite.addTest(new TestSuite(TestWMFSimple.class));
	  suite.addTest(new TestSuite(TestWMFComplex.class));
	  return suite;
	}}

/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.unittests;

import java.io.*;

import junit.framework.*;

import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class TestWMFDoubleWord extends TestCase
{
	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(TestWMFDoubleWord.class);
	}

	public static Test suite()
	{
		return new TestSuite(TestWMFDoubleWord.class);
	}

	public TestWMFDoubleWord(String name)
	{
		super(name);
	}
	
	public void testHexStringConversion()
	{
		String hexString = HexUtils.int8ToHexString(254);
		assertEquals("fe", hexString);
	}
	
	public void testWMFDoubleWord() throws IOException
	{
		long value = 12345678;
		WMFDoubleWord word = new WMFDoubleWord(value);
		assertEquals(4, word.sizeInBytes());
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		word.writeBytes(bytes);
		byte[] b = bytes.toByteArray();
		assertEquals(HexUtils.int8ToHexString((int)(value & 255)), "" + (char)b[0] + (char)b[1]);
		assertEquals(HexUtils.int8ToHexString((int)((value>>8) & 255)), "" + (char)b[2] + (char)b[3]);
		assertEquals(HexUtils.int8ToHexString((int)((value>>16) & 255)), "" + (char)b[4] + (char)b[5]);
		assertEquals(HexUtils.int8ToHexString((int)((value>>24) & 255)), "" + (char)b[6] + (char)b[7]);
	}
}

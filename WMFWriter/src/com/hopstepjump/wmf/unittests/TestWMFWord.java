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
public class TestWMFWord extends TestCase
{
	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(TestWMFWord.class);
	}

	public static Test suite()
	{
		return new TestSuite(TestWMFWord.class);
	}

	public TestWMFWord(String name)
	{
		super(name);
	}
	
	public void testWMFWord() throws IOException
	{
		WMFWord word = new WMFWord("01cc");
		assertEquals(2, word.sizeInBytes());
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		word.writeBytes(bytes);
		byte[] b = bytes.toByteArray();
		assertEquals(12*16+12, HexUtils.hexStringToInt("cc"));
		assertEquals(1, HexUtils.hexStringToInt("01"));
		assertEquals('c', b[0]);
		assertEquals('c', b[1]);
		assertEquals('0', b[2]);
		assertEquals('1', b[3]);
	}

	public void testWMFWord2() throws IOException
	{
		WMFWord word = new WMFWord(1*256 + (12*16+12)); // 01cc
		assertEquals(2, word.sizeInBytes());
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		word.writeBytes(bytes);
		byte[] b = bytes.toByteArray();
		assertEquals('c', b[0]);
		assertEquals('c', b[1]);
		assertEquals('0', b[2]);
		assertEquals('1', b[3]);
	}


	public void testWMFWord3() throws IOException
	{
		WMFWord word = new WMFWord(1, (12*16+12)); // 01cc
		assertEquals(2, word.sizeInBytes());
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		word.writeBytes(bytes);
		byte[] b = bytes.toByteArray();
		assertEquals('c', b[0]);
		assertEquals('c', b[1]);
		assertEquals('0', b[2]);
		assertEquals('1', b[3]);
	}
}

/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.utility;

import java.io.*;

/**
 * @version 	1.0
 * @author
 */
public class WMFWord implements WMFBytes
{
	private int high;
	private int low;
	
	// reverses the order to little endian, for the intel architecture
	public WMFWord(int high, int low)
	{
		this.high = high;
		this.low = low;
	}
	
	public WMFWord(int data)
	{
    if (data < 0)
      data = 65535 + data;
    
		this.high = data / 256;
		this.low = data % 256;
	}
	
	public WMFWord(String hex)
	{
		// convert the string from hex into bytes
		high = HexUtils.hexStringToInt(hex.substring(0, 2));
    if (high > 255)
      throw new IllegalArgumentException();
		low = HexUtils.hexStringToInt(hex.substring(2, 4));
	}
	
	public int sizeInBytes()
	{
		return 2;
	}
	
	public void writeBytes(OutputStream out) throws IOException
	{
		HexUtils.writeRawString(out, HexUtils.int8ToHexString(low));
		HexUtils.writeRawString(out, HexUtils.int8ToHexString(high));
	}
	
	public int getValue()
	{
		return low + 256 * high;
	}
}

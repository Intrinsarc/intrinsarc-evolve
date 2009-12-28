package com.hopstepjump.wmf.utility;

import java.io.*;

/**
 *
 * (c) Andrew McVeigh 14-Jan-03
 *
 */
public class WMFByte implements WMFBytes
{
	private int value;
	
	public WMFByte(int value)
	{
		this.value = value;
	}
	
	public int sizeInBytes()
	{
		return 1;
	}
	
	public void writeBytes(OutputStream out) throws IOException
	{
		HexUtils.writeRawString(out, HexUtils.int8ToHexString(value & 255));
	}
}

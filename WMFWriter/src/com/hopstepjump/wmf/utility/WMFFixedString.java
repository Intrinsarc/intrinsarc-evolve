package com.hopstepjump.wmf.utility;

import java.io.*;

/**
 *
 * (c) Andrew McVeigh 14-Jan-03
 *
 */
public class WMFFixedString implements WMFBytes
{
	private String str;
	private int size;
	
	// reverses the order to little endian, for the intel architecture
	public WMFFixedString(String str, int size)
	{
		this.str = str;
		this.size = size;
	}
	
	public int sizeInBytes()
	{
		return size;
	}
	
	public void writeBytes(OutputStream out) throws IOException
	{
		int length = str.length();
		for (int lp = 0; lp < size; lp++)
		{
			int b = 0;
			if (lp < length)
				b = str.charAt(lp);
			HexUtils.writeRawString(out, HexUtils.int8ToHexString(b));
		}
	}
}

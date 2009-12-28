package com.hopstepjump.wmf.utility;

import java.io.*;

/**
 *
 * (c) Andrew McVeigh 14-Jan-03
 *
 */
public class WMFBoolean implements WMFBytes
{
	private boolean value;
	
	public WMFBoolean(boolean value)
	{
		this.value = value;
	}
	
	public int sizeInBytes()
	{
		return 1;
	}
	
	public void writeBytes(OutputStream out) throws IOException
	{
		HexUtils.writeRawString(out, HexUtils.int8ToHexString(value ? 1 : 0));
	}
}

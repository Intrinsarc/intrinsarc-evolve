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
public class WMFDoubleWord implements WMFBytes
{
	private long data;
	
	public WMFDoubleWord(long data)
	{
		this.data = data;
	}
	
	public int sizeInBytes()
	{
		return 4;
	}
	
	public void writeBytes(OutputStream out) throws IOException
	{
		// output in little endian order, for the intel architecture
		HexUtils.writeRawString(out, HexUtils.int8ToHexString((int)(data & 255)));
		HexUtils.writeRawString(out, HexUtils.int8ToHexString((int)((data >>8) & 255)));
		HexUtils.writeRawString(out, HexUtils.int8ToHexString((int)((data >>16) & 255)));
		HexUtils.writeRawString(out, HexUtils.int8ToHexString((int)((data >>24) & 255)));
	}
}

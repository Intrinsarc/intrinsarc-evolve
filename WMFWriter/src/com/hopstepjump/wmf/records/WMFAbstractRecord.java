/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import java.io.*;

import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public abstract class WMFAbstractRecord implements WMFRecord
{
	private WMFWord function;
	private String functionName;
	private boolean createsObject;
	private int objectNumber;
	
	public WMFAbstractRecord(String functionWord, String functionName, boolean createsObject)
	{
		this.function = new WMFWord(functionWord);
		this.functionName = functionName;
		this.createsObject = createsObject;
	}
	
	public abstract WMFBytes[] getParameters();
	
	public int sizeInBytes()
	{
		int sum = 0;
		WMFBytes[] params = getParameters();
		for (int lp = 0; lp < params.length; lp++)
			sum += params[lp].sizeInBytes();
		
		return function.sizeInBytes() + sum + 2 * 2 /* the size is a dword */;
	}
	
	public void writeBytes(OutputStream out) throws IOException
	{
		// write the size
		new WMFDoubleWord(sizeInBytes() / 2).writeBytes(out);
		// write the function
		function.writeBytes(out);
		// write the parameters
		WMFBytes[] params = getParameters();
		for (int lp = 0; lp < params.length; lp++)
			params[lp].writeBytes(out);		
	}

	public boolean createsObject()
	{
		return createsObject;
	}
	
	public int setObjectNumber(int number)
	{
		if (createsObject)
			this.objectNumber = number++;
		return number;
	}
	
	public int getObjectNumber()
	{
		return objectNumber;
	}

	public int getNumberOfRecords()
	{
		return 1;
	}

	/**
	 * @see com.hopstepjump.wmf.WMFRecord#getMaxLineSize()
	 */
	public int getMaxLineSize()
	{
		return sizeInBytes();
	}

	public boolean disassemble(long[] records)
	{
		if (records[2] != function.getValue())
			return false;
			
		System.out.println("Function name: " + getFunctionName());
		decodeParameters(records);
		return true;
	}
	
	public String getFunctionName()
	{
		return functionName;
	}
	
	/** override to decode more fully */
	public void decodeParameters(long[] records)
	{
		for (int lp = 3; lp < records.length; lp++)
			System.out.println("  Parameter word " + (lp-3) + " = " + (records[lp]));
	}
}

package com.hopstepjump.wmf.records;

import java.io.*;
import java.util.*;

/**
 *
 * (c) Andrew McVeigh 15-Jan-03
 *
 */
public class CompositeRecord implements WMFRecord
{
	private List<WMFRecord> records = new ArrayList<WMFRecord>();
	
	public CompositeRecord()
	{
	}
	
	public void addRecord(WMFRecord record)
	{
		records.add(record);
	}

	/**
	 * @see com.hopstepjump.wmf.WMFRecord#createsObject()
	 */
	public boolean createsObject()
	{
		return false;
	}

	/**
	 * @see com.hopstepjump.wmf.WMFRecord#setObjectNumber(int)
	 */
	public int setObjectNumber(int startNumber)
	{
		Iterator iter = records.iterator();
		while (iter.hasNext())
		{
			WMFRecord record = (WMFRecord) iter.next();
			startNumber = record.setObjectNumber(startNumber);
		}
		return startNumber;
	}

	/**
	 * @see com.hopstepjump.wmf.WMFRecord#getObjectNumber()
	 */
	public int getObjectNumber()
	{
		throw new IllegalArgumentException("Attempt to use a composite record as a selectObject parameter");
	}

	/**
	 * @see com.hopstepjump.wmf.WMFBytes#size()
	 */
	public int sizeInBytes()
	{
		int sum = 0;
		Iterator iter = records.iterator();
		while (iter.hasNext())
		{
			WMFRecord record = (WMFRecord) iter.next();
			sum += record.sizeInBytes();
		}
		return sum;
	}

	/**
	 * @see com.hopstepjump.wmf.WMFBytes#writeBytes(OutputStream)
	 */
	public void writeBytes(OutputStream out) throws IOException
	{
		Iterator iter = records.iterator();
		while (iter.hasNext())
		{
			WMFRecord record = (WMFRecord) iter.next();
			record.writeBytes(out);
		}
	}

	/**
	 * @see com.hopstepjump.wmf.WMFRecord#getNumberOfRecords()
	 */
	public int getNumberOfRecords()
	{
		int sum = 0;
		Iterator iter = records.iterator();
		while (iter.hasNext())
		{
			WMFRecord record = (WMFRecord) iter.next();
			sum += record.getNumberOfRecords();
		}
		return sum;
	}

	public int getMaxLineSize()
	{
		int max = 0;
		Iterator iter = records.iterator();
		while (iter.hasNext())
		{
			WMFRecord record = (WMFRecord) iter.next();
			max = Math.max(record.getMaxLineSize(), max);
		}
		return max;
	}
	
	/**
	 * @see com.hopstepjump.wmf.records.WMFRecord#disassemble(long[])
	 */
	public boolean disassemble(long[] record)
	{
		return false;
	}

}

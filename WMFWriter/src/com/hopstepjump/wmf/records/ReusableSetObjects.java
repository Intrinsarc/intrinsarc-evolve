package com.hopstepjump.wmf.records;

/**
 *
 * (c) Andrew McVeigh 20-Jan-03
 *
 */
public class ReusableSetObjects
{
	private Object currentObject = new Object();
	
	public ReusableSetObjects()
	{
	}
	
	public void addSetObjectRecords(CompositeRecord records, WMFRecord object)
	{
		// extract it and select it
		if (!currentObject.equals(object))
		{
			records.addRecord(object);
			currentObject = object;
		}
	}
}

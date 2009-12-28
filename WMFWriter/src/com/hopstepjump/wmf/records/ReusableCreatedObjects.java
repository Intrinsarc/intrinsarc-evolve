package com.hopstepjump.wmf.records;

import java.util.*;

/**
 * Allows a pool of reusable objects to be maintained.  Useful for not duplicating creation of pens,
 * brushes etc.  Note: CreateIndirectXXXRecord needs to implements hashCode() and equals()
 * 
 * (c) Andrew McVeigh 20-Jan-03
 */

public class ReusableCreatedObjects
{
	private HashMap<WMFRecord, WMFRecord> objects = new HashMap<WMFRecord, WMFRecord>();
	private Object currentObject = new Object();
	
	public ReusableCreatedObjects()
	{
	}
	
	public void addSelectObjectRecords(CompositeRecord records, WMFRecord object)
	{
		// if we don't have this font already, add it
		if (!objects.containsKey(object))
		{
			records.addRecord(object);
			objects.put(object, object);
		}
		
		// extract it and select it
		if (!currentObject.equals(object))
		{
			WMFRecord savedObject = objects.get(object);
			records.addRecord(new SelectObjectRecord(savedObject));
			currentObject = savedObject;
		}
	}
}

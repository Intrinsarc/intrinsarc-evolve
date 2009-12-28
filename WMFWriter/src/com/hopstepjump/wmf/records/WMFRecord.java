/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public interface WMFRecord extends WMFBytes
{
	public boolean createsObject();
	public int setObjectNumber(int startNumber);
	public int getObjectNumber();
	public int getNumberOfRecords();
	public int getMaxLineSize();
	
	public boolean disassemble(long record[]);
}

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
public class SelectObjectRecord extends WMFAbstractRecord
{
	private WMFRecord selection;
	
	public SelectObjectRecord()
	{
		super("012d", "SelectObject", false);
	}

	public SelectObjectRecord(WMFRecord selection)
	{
		this();
		this.selection = selection;
	}

	/*
	 * @see WMFRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{new WMFWord(selection.getObjectNumber())};
	}

}

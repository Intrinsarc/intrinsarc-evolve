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
public class SetBackgroundModeRecord extends WMFAbstractRecord
{
	private boolean opaque;
	
	public SetBackgroundModeRecord()
	{
		super("0102", "SetBackgroundMode", false);
	}

	public SetBackgroundModeRecord(boolean opaque)
	{
		this();
		this.opaque = opaque;
	}

	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{new WMFWord(opaque ? 2 : 1)};
	}

	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SetBackgroundModeRecord))
			return false;
		SetBackgroundModeRecord mode = (SetBackgroundModeRecord) obj;
		return mode.opaque == opaque;
	}

	@Override
	public int hashCode()
	{
		return opaque ? 1 : 0;
	}

}

/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class SetWindowExtentRecord extends WMFAbstractRecord
{
	private int width;
	private int height;
	
	public SetWindowExtentRecord()
	{
		super("020c", "SetWindowExtent", false);
	}

	public SetWindowExtentRecord(int width, int height)
	{
		this();
		this.width = width;
		this.height = height;
	}

	/*
	 * @see WMFRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{new WMFWord(WMFFormat.scale(height)), new WMFWord(WMFFormat.scale(width))};
	}

}

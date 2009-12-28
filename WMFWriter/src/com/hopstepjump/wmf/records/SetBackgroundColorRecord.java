/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import java.awt.*;

import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class SetBackgroundColorRecord extends WMFAbstractRecord
{
	private long color;
	
	public SetBackgroundColorRecord()
	{
		super("0201", "SetBackgroundColor", false);
	}

	public SetBackgroundColorRecord(Color color)
	{
		this();
		this.color = color.getRed() + color.getGreen() * 256 + color.getBlue() * 65536;
	}

	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{new WMFDoubleWord(color)};
	}

}

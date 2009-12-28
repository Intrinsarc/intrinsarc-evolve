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
public class RectangleRecord extends WMFAbstractRecord
{
	private int x;
	private int y;
	private int width;
	private int height;
	
	public RectangleRecord()
	{
		super("041b", "Rectangle", false);
	}
	
	public RectangleRecord(int x, int y, int width, int height)
	{
		this();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{
			new WMFWord(WMFFormat.scale(y+height)),
			new WMFWord(WMFFormat.scale(x+width)),
			new WMFWord(WMFFormat.scale(y)),
			new WMFWord(WMFFormat.scale(x))};
			
	}

}

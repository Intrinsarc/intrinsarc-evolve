/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import java.awt.*;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class CreateIndirectPenRecord extends WMFAbstractRecord
{
	private int style;
	private int width;
	private long color;
	
	public static final int PS_SOLID = 0;
	public static final int PS_DASH = 1;
	public static final int PS_DOT = 2;
	public static final int PS_DASHDOT = 3;
	public static final int PS_DASHDOTDOT = 4;
	public static final int PS_NULL = 5;
	public static final int PS_INSIDEFRAME = 6;
	public static final int PS_USERSTYLE = 7;
	public static final int PS_ALTERNATE = 8;
	
	public CreateIndirectPenRecord()
	{
		super("02fa", "CreateIndirectPenRecord", true);
	}

	public CreateIndirectPenRecord(int ps_style, int width, Color color)
	{
		this();
		this.style = ps_style;
		this.width = width;
		this.color = color.getRed() + color.getGreen() * 256 + color.getBlue() * 65536;
	}

	/*
	 * @see WMFRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{
				new WMFWord(style),
				new WMFWord(WMFFormat.scale(Math.max(1, width))),
				new WMFWord(0),
				new WMFDoubleWord(color)};
	}


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CreateIndirectPenRecord))
			return false;
		CreateIndirectPenRecord pen = (CreateIndirectPenRecord) obj;
		return pen.style == style && pen.width == width && pen.color == color;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return style ^ width ^ (int) color;
	}

}

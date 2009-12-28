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
public class CreateIndirectBrushRecord extends WMFAbstractRecord
{
	private int style;
	private long color;
	private int hatched;
	
	public static final int BS_SOLID      = 0;
	public static final int BS_NULL       = 1;
	public static final int BS_HATCHED    = 2;

	public static final int HS_HORIZONTAL = 0;
	public static final int HS_VERTICAL   = 1;
	public static final int HS_FDIAGONAL  = 2;
	public static final int HS_BDIAGONAL  = 3;
	public static final int HS_CROSS      = 4;
	public static final int HS_DIAGCROSS  = 5;
	
	public CreateIndirectBrushRecord(int bs_style, Color color, int hs_hatched)
	{
		this();
		this.style = bs_style;
		this.color = color.getRed() + color.getGreen() * 256 + color.getBlue() * 65536;
		this.hatched = hs_hatched;
	}

	public CreateIndirectBrushRecord(int bs_style, Color color)
	{
		this();
		this.style = bs_style;
		this.color = color.getRed() + color.getGreen() * 256 + color.getBlue() * 65536;
		this.hatched = HS_CROSS;
	}
	
	public CreateIndirectBrushRecord()
	{
		super("02fc", "CreateIndirectBrush", true);
	}

	/*
	 * @see WMFRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{new WMFWord(style), new WMFDoubleWord(color), new WMFWord(hatched)};
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CreateIndirectBrushRecord))
			return false;
		CreateIndirectBrushRecord brush = (CreateIndirectBrushRecord) obj;
		return brush.style == style && brush.color == color && brush.hatched == hatched;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return style ^ (int) color ^ hatched;
	}

}

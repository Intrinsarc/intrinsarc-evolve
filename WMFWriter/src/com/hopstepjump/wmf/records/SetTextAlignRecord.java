package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.utility.*;

public class SetTextAlignRecord extends WMFAbstractRecord
{
	public static int ALIGN_LEFT = 0;
	public static int ALIGN_RIGHT = 2;

	public static int ALIGN_TOP = 0;
	public static int ALIGN_BOTTOM = 8;
	public static int ALIGN_BASELINE = 24;
	
	private int alignment;
	
	public SetTextAlignRecord(int alignment)
	{
		super("012E", "SetBackgroundMode", false);
		this.alignment = alignment;
	}
	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{new WMFWord(alignment)};
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SetTextAlignRecord))
			return false;
		SetTextAlignRecord align = (SetTextAlignRecord) obj;
		return align.alignment == alignment;
	}

	@Override
	public int hashCode()
	{
		return alignment;
	}
}

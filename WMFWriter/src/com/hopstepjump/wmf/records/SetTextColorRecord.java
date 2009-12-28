package com.hopstepjump.wmf.records;

import java.awt.*;

import com.hopstepjump.wmf.utility.*;

/**
 *
 * (c) Andrew McVeigh 15-Jan-03
 *
 */
public class SetTextColorRecord extends WMFAbstractRecord
{
	private long color;
	
	public SetTextColorRecord()
	{
		super("0209", "SetTextColor", false);
	}

	public SetTextColorRecord(Color color)
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

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SetTextColorRecord))
			return false;
		SetTextColorRecord colorRec = (SetTextColorRecord) obj;
		return colorRec.color == color;
	}

	@Override
	public int hashCode()
	{
		return (int) color;
	}

}

/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import com.hopstepjump.geometry.*;
import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class EllipseRecord extends WMFAbstractRecord
{
	private UBounds bounds;
	
	public EllipseRecord()
	{
		super("0418", "EllipseRecord", false);
	}

	public EllipseRecord(int x, int y, int width, int height)
	{
		this();
		bounds = new UBounds(new UPoint(x, y), new UDimension(width, height));
	}

	public EllipseRecord(UBounds bounds)
	{
		this();
		this.bounds = bounds;
	}

	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{
			new WMFWord(WMFFormat.scale(bounds.getBottomRightPoint().getY())),
			new WMFWord(WMFFormat.scale(bounds.getBottomRightPoint().getX())),
			new WMFWord(WMFFormat.scale(bounds.getTopLeftPoint().getY())),
			new WMFWord(WMFFormat.scale(bounds.getTopLeftPoint().getX()))};
	}

}

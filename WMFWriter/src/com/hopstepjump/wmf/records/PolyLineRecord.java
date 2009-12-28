package com.hopstepjump.wmf.records;

import java.awt.geom.*;
import java.util.*;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.utility.*;

/**
 *
 * (c) Andrew McVeigh 15-Jan-03
 *
 */
public class PolyLineRecord extends WMFAbstractRecord
{
	List<Point2D> points = new ArrayList<Point2D>();
	
	public PolyLineRecord(Point2D[] pointArray)
	{
		this();
		for (int lp = 0; lp < pointArray.length; lp++)
			points.add(pointArray[lp]);
	}

	public PolyLineRecord()
	{
		super("0325", "PolyLine", false);
	}

	public void addPoint(Point2D point)
	{
		points.add(point);
	}

	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		WMFBytes[] bytes = new WMFBytes[1 + points.size() * 2];
		bytes[0] = new WMFWord(points.size());
		Iterator iter = points.iterator();
		for (int lp = 0; iter.hasNext(); lp++)
		{
			Point2D point = (Point2D) iter.next();
			bytes[lp*2+1] = new WMFWord(WMFFormat.scale(point.getX()));
			bytes[lp*2+2] = new WMFWord(WMFFormat.scale(point.getY()));
		}
		
		return bytes;		
	}
}

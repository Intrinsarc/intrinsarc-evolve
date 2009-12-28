/*
 * Created on Dec 28, 2003 by Andrew McVeigh
 */
package edu.umd.cs.jazz.component;

import java.awt.geom.*;

/**
 * @author Andrew
 */
public class ZQuadPolyLine extends ZPolyline
{

	public ZQuadPolyLine(ZCoordListShape zCoordListShape)
	{
		super(new ZQuadCoordListShape());
	}

	public ZQuadPolyLine(Point2D aPoint)
	{
		super(new ZQuadCoordListShape(), aPoint);
	}

	public ZQuadPolyLine(double x, double y)
	{
		super(new ZQuadCoordListShape(), x, y);
	}
}

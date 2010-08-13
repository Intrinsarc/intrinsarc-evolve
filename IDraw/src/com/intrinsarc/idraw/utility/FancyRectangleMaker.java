package com.intrinsarc.idraw.utility;

import java.awt.*;
import java.awt.geom.*;

import com.intrinsarc.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class FancyRectangleMaker
{
	private UBounds bounds;
	private double roundedRadius;
	private Color primary;
	private boolean gradient;
	private double offset;
	private double width;

	public FancyRectangleMaker(UBounds bounds, double roundedRadius, Color primary, boolean gradient, double offset)
	{
		this(bounds, roundedRadius, primary, gradient, offset, 1.5);
	}
	
	public FancyRectangleMaker(UBounds bounds, double roundedRadius, Color primary, boolean gradient, double offset, double width)
	{
		this.bounds = bounds;
		this.roundedRadius = roundedRadius;
		this.primary = primary;
		this.gradient = gradient;
		this.offset = offset;
		this.width = width;
	}
	
	public ZGroup make()
	{
		ZGroup rect = new ZGroup();
  	{
    	RoundRectangle2D r = new RoundRectangle2D.Double(
          bounds.getX(),
          bounds.getY(),
          bounds.getWidth(),
          bounds.getHeight(),
          roundedRadius,
          roundedRadius);
      ZRoundedRectangle rc = new ZRoundedRectangle(r);
      rc.setFillPaint(Color.WHITE);
      rc.setPenPaint(null);
      rect.addChild(new ZVisualLeaf(rc));
  	}

  	// ring around
  	{
    	RoundRectangle2D r = new RoundRectangle2D.Double(
          bounds.getX(),
          bounds.getY(),
          bounds.getWidth(),
          bounds.getHeight(),
          roundedRadius,
          roundedRadius);
      ZRoundedRectangle rc = new ZRoundedRectangle(r);
      rc.setFillPaint(null);
      rc.setPenPaint(primary.darker());
      rc.setPenWidth(width);
      rect.addChild(new ZVisualLeaf(rc));
  	}

  	// gradient
  	{
      RoundRectangle2D r = new RoundRectangle2D.Double(
          bounds.getX() + offset,
          bounds.getY() + offset,
          bounds.getWidth() - 0.5 - offset,
          bounds.getHeight() - 0.5 - offset,
          roundedRadius,
          roundedRadius);
      ZRoundedRectangle rc = new ZRoundedRectangle(r);
      if (gradient)
      	rc.setFillPaint(new GradientPaint(bounds.getPoint(), primary, bounds.getBottomLeftPoint(), primary.brighter().brighter()));
      else
      	rc.setFillPaint(primary);
      rc.setPenPaint(null);
      rect.addChild(new ZVisualLeaf(rc));
  	}

  	return rect;
	}
}

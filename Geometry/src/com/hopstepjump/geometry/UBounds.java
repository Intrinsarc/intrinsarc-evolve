package com.hopstepjump.geometry;

import java.awt.geom.*;
import java.io.*;

import edu.umd.cs.jazz.util.*;


public final class UBounds extends ZBounds implements Serializable
{
  public static final UBounds ZERO = new UBounds(new UPoint(0, 0), new UDimension(0, 0));
  
  public UBounds(Point2D pt, Dimension2D dim)
  {
    super(pt.getX(), pt.getY(), dim.getWidth(), dim.getHeight());
  }

  public UBounds(Point2D pt1, Point2D pt2)
  {
    double
      x1 = Math.min(pt1.getX(), pt2.getX()),
      x2 = Math.max(pt1.getX(), pt2.getX()),
      y1 = Math.min(pt1.getY(), pt2.getY()),
      y2 = Math.max(pt1.getY(), pt2.getY());

    super.setRect(x1, y1, x2 - x1, y2 - y1);
  }

  public UBounds(Rectangle2D rect)
  {
    super(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
  }

  public UPoint getPoint()
  {
    return new UPoint(getX(), getY());
  }

  public UDimension getDimension()
  {
    return new UDimension(getWidth(), getHeight());
  }

  public UBounds addToExtent(Dimension2D dim)
  {
    // extend out
    return new UBounds(getPoint(), getDimension().add(dim));
  }

  public UBounds addToPoint(Dimension2D dim)
  {
    // move the rectangle
    return new UBounds(getPoint().add(dim), getDimension());
  }

  public UBounds addToPointAndPreserveBottomRightPoint(Dimension2D dim)
  {
    // move the rectangle
    return new UBounds(getPoint().add(dim), getDimension().add(new UDimension(dim.getWidth(), dim.getHeight()).negative()));
  }

  public UBounds setPoint(Point2D pt)
  {
    // translate, keeping dimensions the same
    return new UBounds(pt, getDimension());
  }

  public String toString()
  {
    return "UBounds(" + getPoint() + ", " + getDimension() + ")";
  }

  public UPoint getTopLeftPoint()
  {
    return getPoint();
  }

  public UPoint getTopRightPoint()
  {
    return getPoint().add(new UDimension(getDimension().getWidth(), 0));
  }

  public UPoint getBottomLeftPoint()
  {
    return getPoint().add(new UDimension(0, getDimension().getHeight()));
  }

  public UPoint getBottomRightPoint()
  {
    return getPoint().add(getDimension());
  }

  public UPoint getMiddlePoint()
  {
    return getPoint().add(new UDimension(getDimension().getWidth()/2, getDimension().getHeight()/2));
  }

  public UBounds adjustForJazzBounds()
  {
    return addToPoint(new UDimension(0.5, 0.5)).addToExtent(new UDimension(-1,-1));
  }
  
  public double getDiagonalDistance()
  {
  	return getDimension().distance();
  }
  
  public UBounds round()
  {
  	return new UBounds(getPoint().round(), getDimension().round());
  }
  
  public UBounds union(Rectangle2D.Double bounds)
  {
    if (bounds == null)
      return this;
		return new UBounds(createUnion(bounds));
  }
  
  public UBounds intersect(Rectangle2D.Double bounds)
  {
  	return new UBounds(createIntersection(bounds));
  }
  
	/**
	 * @see java.awt.geom.Rectangle2D#add(double, double)
	 */
	public void add(double newx, double newy)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.Rectangle2D#add(Point2D)
	 */
	public void add(Point2D pt)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.Rectangle2D#add(Rectangle2D)
	 */
	public void add(Rectangle2D r)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see edu.umd.cs.jazz.util.ZBounds#add(ZBounds)
	 */
	public void add(ZBounds r)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

  public UBounds inset(UDimension inset)
  {
    return addToPoint(inset).addToExtent(inset.multiply(2).negative());
  }
  
	/**
	 * @see edu.umd.cs.jazz.util.ZBounds#inset(double, double)
	 */
	public void inset(double dx, double dy)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see edu.umd.cs.jazz.util.ZBounds#reset()
	 */
	public void reset()
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.RectangularShape#setFrame(double, double, double, double)
	 */
	public void setFrame(double x, double y, double w, double h)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.RectangularShape#setFrame(Point2D, Dimension2D)
	 */
	public void setFrame(Point2D loc, Dimension2D size)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.RectangularShape#setFrame(Rectangle2D)
	 */
	public void setFrame(Rectangle2D r)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.RectangularShape#setFrameFromCenter(double, double, double, double)
	 */
	public void setFrameFromCenter(double centerX, double centerY, double cornerX, double cornerY)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.RectangularShape#setFrameFromCenter(Point2D, Point2D)
	 */
	public void setFrameFromCenter(Point2D center, Point2D corner)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.RectangularShape#setFrameFromDiagonal(double, double, double, double)
	 */
	public void setFrameFromDiagonal(double x1, double y1, double x2, double y2)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.RectangularShape#setFrameFromDiagonal(Point2D, Point2D)
	 */
	public void setFrameFromDiagonal(Point2D p1, Point2D p2)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see java.awt.geom.Rectangle2D#setRect(double, double, double, double)
	 */
/*	public void setRect(double x, double y, double w, double h)
	{
		// should be immutable, but this is used by readobject unfortunately
  	//throw new IllegalStateException("UBounds is immutable");
	}*/

	/**
	 * @see java.awt.geom.Rectangle2D#setRect(Rectangle2D)
	 */
	public void setRect(Rectangle2D r)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	/**
	 * @see edu.umd.cs.jazz.util.ZBounds#transform(AffineTransform)
	 */
	public void transform(AffineTransform tf)
	{
  	throw new IllegalStateException("UBounds is immutable");
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof UBounds))
			return false;
		return equals((UBounds) other, 0.001);
	}
	
	public boolean equals(UBounds other, double tolerance)
	{
		if (this == other)
			return true;
		return
			getTopLeftPoint().subtract(other.getTopLeftPoint()).distance() <= tolerance &&
			getBottomRightPoint().subtract(other.getBottomRightPoint()).distance() <= tolerance;
	}
	
	/**
	 * This has been fixed for zero width or height regions
	 * @see java.awt.Shape#contains(Rectangle2D)
	 */
	public boolean contains(Rectangle2D r)
	{
		double x = r.getX();
		double y = r.getY();
		double width = r.getWidth();
		double height = r.getHeight();
		
		if (width == 0 || height == 0)
			return contains(x, y) && contains(x + width, y + height);
		return super.contains(r);
	}

	public Rectangle2D getRectangle2D()
	{
	  return getBounds2D();
	}
	
  public Ellipse2D getEllipse2D()
  {
    return new Ellipse2D.Double(x, y, width, height);
  }

  public UBounds centreToPoint(UPoint middlePoint)
  {
    // work out the difference and add it to the point
    return addToPoint(middlePoint.subtract(getMiddlePoint()));
  }

}

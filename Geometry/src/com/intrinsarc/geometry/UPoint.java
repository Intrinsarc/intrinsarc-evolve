package com.intrinsarc.geometry;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;


public final class UPoint extends Point2D implements Serializable
{
  public static final UPoint ZERO = new UPoint(0, 0);

  private double x;
  private double y;

  public UPoint(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public UPoint(Point2D other)
  {
    this.x = other.getX();
    this.y = other.getY();
  }

  public UPoint(UDimension dim)
  {
    this.x = dim.getWidth();
    this.y = dim.getHeight();
  }

	public void setLocation(Point2D p)
	{
  	throw new IllegalStateException("UPoint is immutable");
	}

  public void setLocation(double x, double y)
  {
  	throw new IllegalStateException("UPoint is immutable");
  }

  public void setLocation(float x, float y)
  {
  	throw new IllegalStateException("UPoint is immutable");
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  public UPoint add(Dimension2D plus)
  {
    return new UPoint(x + plus.getWidth(), y + plus.getHeight());
  }

  public UPoint subtract(Dimension2D take)
  {
    return new UPoint(x - take.getWidth(), y - take.getHeight());
  }

  public UDimension subtract(Point2D take)
  {
    return new UDimension(x - take.getX(), y - take.getY());
  }

  public UPoint negative()
  {
    return new UPoint(-x, -y);
  }

  public UPoint minOfEach(UPoint other)
  {
    return new UPoint(Math.min(x, other.x), Math.min(y, other.y));
  }

  public UPoint maxOfEach(UPoint other)
  {
    return new UPoint(Math.max(x, other.x), Math.max(y, other.y));
  }
  
  public UPoint round()
  {
  	return new UPoint(Math.round(x), Math.round(y));
  }
  
  public UDimension asDimension()
  {
  	return new UDimension(x, y);
  }
  
  public UPoint rotate(double theta)
  {
  	// rotate around (0,0) by theta
  	double fullTheta = Math.atan2(y, x) + theta;
  	double distance = this.distance(new UPoint(0,0));
  	return new UPoint(distance * Math.cos(fullTheta), distance * Math.sin(fullTheta));
  }

  public String toString()
  {
    return "UPoint(" + getX() + ", " + getY() + ")";
  }

  public boolean closeTo(UPoint point, double distance)
  {
    // return true if the distance is observed
    return distance(point) < distance;
  }

  public int getIntX()
  {
    return (int) x;
  }

  public int getIntY()
  {
    return (int) y;
  }
  
  public Point asPoint()
  {
  	return new Point(getIntX(), getIntY());
  }

	public double yDistance(UPoint point)
	{
		return Math.abs(point.y - y);
	}

	public double xDistance(UPoint point)
	{
		return Math.abs(point.x - x);
	}
}


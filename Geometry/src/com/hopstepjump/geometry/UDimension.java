package com.hopstepjump.geometry;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;


public final class UDimension extends Dimension2D implements Serializable
{
  public static final UDimension ZERO = new UDimension(0, 0);

  private double width;
  private double height;

  public UDimension(double width, double height)
  {
    this.width = width;
    this.height = height;
  }

  public UDimension(Dimension dim)
  {
    this.width = dim.getWidth();
    this.height = dim.getHeight();
  }

  public boolean equals(Object other)
  {
    if (!(other instanceof Dimension2D))
      return false;
    Dimension2D otherDim = (Dimension2D) other;
    return otherDim.getWidth() == width && otherDim.getHeight() == height;
  }

  public void setSize(double width, double height)
  {
  	throw new IllegalStateException("UDimensions are immutable");
  }

  public double getHeight()
  {
    return height;
  }

  public double getWidth()
  {
    return width;
  }

  public UDimension add(Dimension2D dim)
  {
    return new UDimension(width + dim.getWidth(), height + dim.getHeight());
  }

  public UDimension subtract(Dimension2D dim)
  {
    return new UDimension(width - dim.getWidth(), height - dim.getHeight());
  }

  public UDimension negative()
  {
    return new UDimension(-width, -height);
  }

  public UDimension multiply(double factor)
  {
    return new UDimension(width * factor, height * factor);
  }

  public String toString()
  {
    return "UDimension(" + getWidth() + ", " + getHeight() + ")";
  }

  public UDimension minOfEach(UDimension other)
  {
    return new UDimension(Math.min(width, other.width), Math.min(height, other.height));
  }

  public UDimension maxOfEach(UDimension other)
  {
    return new UDimension(Math.max(width, other.width), Math.max(height, other.height));
  }

  public double distance()
  {
    double
      width = getWidth(),
      height = getHeight();

    return Math.sqrt(width * width + height * height);
  }

  public double getRadians()
  {
    return Math.atan2(getHeight(), getWidth());
  }
  
  public UDimension round()
  {
  	return new UDimension(Math.round(width), Math.round(height));
  }
	/**
	 * @see java.awt.geom.Dimension2D#setSize(Dimension2D)
	 */
	public void setSize(Dimension2D d)
	{
  	throw new IllegalStateException("UDimension is immutable");
	}
	
	public Dimension getAsAWTDimension()
	{
	  return new Dimension((int) width, (int) height);
	}

  public int getIntWidth()
  {
    return (int) width;
  }

  public int getIntHeight()
  {
    return (int) height;
  }

	@Override
	public int hashCode()
	{
		return (int) width ^ (int) height;
	}
}
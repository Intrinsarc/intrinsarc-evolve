package com.intrinsarc.evolve.umldiagrams.portnode;

import com.intrinsarc.geometry.*;

/**
 * @author Andrew
 */

public class ClosestLine
{
  private UPoint first;
  private UPoint second;
  private double distance;
  private boolean horizontal;
  private int lineNumber;
  
  /**
   * @param first
   * @param second
   * @param horizontal
   */
  public ClosestLine(UPoint point, UPoint first, UPoint second, boolean horizontal, int lineNumber)
  {
    super();
    this.first = first;
    this.second = second;
    this.horizontal = horizontal;
    this.lineNumber = lineNumber;
    
    // calculate the distance to the point
    // if horizontal, return the distance between the y points
    if (horizontal)
    {
      UPoint closest = new UPoint(
          getClosest(point.getX(), first.getX(), second.getX()),
          first.getY());
      distance = point.distance(closest);
    }
    else
    // if vertical, return the distance between the x points
    {
      UPoint closest = new UPoint(
          first.getX(),
          getClosest(point.getY(), first.getY(), second.getY()));
      distance = point.distance(closest);
    }
  }
  
  private double getClosest(double x, double first, double second)
  {
    // possibly switch
    if (first > second)
    {
      double copy = first;
      first = second;
      second = copy;
    }
    
    // if outside the range, truncate
    if (x < first)
      return first;
    if (x > second)
      return second;
    return x;
    
  }

  public double getDistance()
  {
    return distance;
  }
  
  public UPoint getFirst()
  {
    return first;
  }
  
  public boolean isHorizontal()
  {
    return horizontal;
  }
  
  public int getLineNumber()
  {
    return lineNumber;
  }
  
  public UPoint getSecond()
  {
    return second;
  }
  
  public String toString()
  {
    return
    	"first = " + first +
    	", second = " + second +
    	", distance = " + distance +
    	", horizontal = " + horizontal;
  }
}


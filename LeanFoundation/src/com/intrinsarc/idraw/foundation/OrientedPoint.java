package com.intrinsarc.idraw.foundation;

import com.intrinsarc.geometry.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public final class OrientedPoint
{
  public static final int ORIENTED_MIDDLE           = 0;
  /** horizontal default is top to bottom */
  public static final int ORIENTED_HORIZONTAL       = 1;
  /** vertical default is left to right */
  public static final int ORIENTED_VERTICAL         = 2;
  public static final int ORIENTED_UNKNOWN          = 3;

  private UPoint point;
  private int orientation;
  private boolean reversed;

  public OrientedPoint(UPoint point, int orientation, boolean reversed)
  {
    this.point = point;
    this.orientation = orientation;
    this.reversed = reversed;
  }

  public OrientedPoint(UPoint point, int orientation)
  {
    this.point = point;
    this.orientation = orientation;
    this.reversed = false;
  }

  public OrientedPoint(UPoint point)
  {
    this.point = point;
    this.orientation = ORIENTED_UNKNOWN;
    this.reversed = false;
  }

  public UPoint getPoint()
  {
    return point;
  }

  public int getOrientation()
  {
    return orientation;
  }

  public boolean getReversed()
  {
    return reversed;
  }

  public OrientedPoint reverse()
  {
    OrientedPoint pt = new OrientedPoint(point, orientation, !reversed);
    return pt;
  }

  public void toggleReversed()
  {
    reversed = !reversed;
  }

  public void setOrientation(int orientation)
  {
    this.orientation = orientation;
  }

  public String toString()
  {
    return "OrientedPoint: point = " + point + ", orientation = " + orientation;
  }
}

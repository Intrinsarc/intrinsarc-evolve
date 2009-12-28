package com.hopstepjump.idraw.utility;

import java.awt.*;

import com.hopstepjump.geometry.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public final class BoundaryCalculator {

  private UBounds bounds;
  private Shape shape;

  public BoundaryCalculator(UBounds bounds)
  {
    this.bounds = bounds;
  }

  public BoundaryCalculator(Shape shape)
  {
    this.shape = shape;
  }

  public UPoint calculateBoundaryPoint(UPoint offsetPoint, UPoint possibleBoxPoint, UPoint insidePoint)
  {
    // if we have a box point, take this in preference
    if (possibleBoxPoint != null)
      return possibleBoxPoint;

    // make sure that we have a point outside the bounds, using the diagonal length from the middle, along the correct slope
    UBounds bounds = getBounds();
    double theta = Math.atan2(offsetPoint.getY() - insidePoint.getY(), offsetPoint.getX() - insidePoint.getX());
    double maxLength = bounds.getTopLeftPoint().distance(getBounds().getBottomRightPoint()) + 10;  // make sure that this causes the rotated point to be outside
    UPoint definitelyOutside = new UPoint(insidePoint.getX() + maxLength * Math.cos(theta), insidePoint.getY() + maxLength * Math.sin(theta));

    // iterative version that works with any shape...
    // form the bounds, and then intersect the 2 lines using iteration
    UPoint startPoint = insidePoint;

    UDimension difference = definitelyOutside.subtract(startPoint);
    UDimension increment = new UDimension(difference.getWidth()/2, difference.getHeight()/2);

    boolean add = true;
    while (increment.distance() > 0.1)  // accurate to within 0.2 pixels -- sum of 0.1 + 0.05 + 0.025 + ...
    {
      startPoint = startPoint.add(add ? increment : increment.negative());
      increment = new UDimension(increment.getWidth()/2, increment.getHeight()/2);
      add = containsPoint(startPoint);
    }
    return startPoint;
  }

  private UBounds getBounds()
  {
    if (bounds != null)
      return bounds;
    if (shape != null)
      return new UBounds(shape.getBounds());
    return null;  // will never get here
  }

  private boolean containsPoint(UPoint point)
  {
    if (bounds != null)
      return bounds.contains(point);
    if (shape != null)
      return shape.contains(point);

    return false;  // should never get to here
  }
}
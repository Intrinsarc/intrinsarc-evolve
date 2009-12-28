package com.hopstepjump.idraw.foundation;

import java.util.*;

import com.hopstepjump.geometry.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public final class ActualArcPoints
{
  private AnchorFacet node1;
  private AnchorFacet node2;

  private DiagramFacet diagram;
  private UPoint virtualPoint;
  private List<UPoint> internalPoints = new ArrayList<UPoint>();
  private AnchorPreviewFacet preview1;
  private AnchorPreviewFacet preview2;

  public ActualArcPoints(DiagramFacet diagram, CalculatedArcPoints calculated)
  {
    this.diagram = diagram;
    setNode1(calculated.getNode1());
    setNode2(calculated.getNode2());
    virtualPoint = calculated.getVirtualPoint();
    internalPoints.addAll(calculated.getAllPoints());
    internalPoints.remove(0);
    internalPoints.remove(internalPoints.size() - 1);
  }

  /**
   * copy constructor
   */
  public ActualArcPoints(ActualArcPoints actualPoints)
  {
    diagram = actualPoints.diagram;
    setNode1(actualPoints.node1);
    setNode1Preview(actualPoints.preview1);
    setNode2(actualPoints.node2);
    setNode2Preview(actualPoints.preview2);
    this.internalPoints.addAll(actualPoints.internalPoints);
    this.virtualPoint = actualPoints.virtualPoint;
  }

  public ActualArcPoints(ActualArcPoints actualPoints, AnchorFacet node1, AnchorFacet node2)
  {
    this.diagram = actualPoints.diagram;
    setNode1(node1);
    setNode1Preview(actualPoints.preview1);
    setNode2(node2);
    setNode2Preview(actualPoints.preview2);
    this.internalPoints.addAll(actualPoints.internalPoints);
    this.virtualPoint = actualPoints.virtualPoint;
  }

  public ActualArcPoints(DiagramFacet diagram, AnchorFacet node1, AnchorFacet node2, UPoint virtualPoint)
  {
    this.diagram = diagram;
    setNode1(node1);
    setNode2(node2);
    this.virtualPoint = virtualPoint;
  }

  public static boolean isVirtualPointDebuggingOn()
  {
    return false;
  }

  public AnchorFacet getNode1()
  {
    return node1;
  }

  public AnchorFacet getNode2()
  {
    return node2;
  }

  public AnchorPreviewFacet getPreview1()
  {
    return preview1;
  }

  public AnchorPreviewFacet getPreview2()
  {
    return preview2;
  }

  public void setNode1(AnchorFacet node1)
  {
    this.node1 = node1;
  }

  public void setNode2(AnchorFacet node2)
  {
    this.node2 = node2;
  }

  public void setNode1Preview(AnchorPreviewFacet preview)
  {
    this.preview1 = preview;
  }

  public void setNode2Preview(AnchorPreviewFacet preview)
  {
    this.preview2 = preview;
  }

  public List<UPoint> getInternalPoints()
  {
    return internalPoints;
  }

  public void addInternalPoint(UPoint extraPoint)
  {
    internalPoints.add(extraPoint);
  }

  public void addInternalPoint(int index, UPoint extraPoint)
  {
    internalPoints.add(index, extraPoint);
  }

  public void replaceInternalPoint(int index, UPoint newPoint)
  {
    internalPoints.remove(index);
    internalPoints.add(index, newPoint);
  }

  public void removeInternalPoint(int index)
  {
    // possibly use this as a virtual
    if (internalPoints.size() == 1)
      this.virtualPoint = internalPoints.get(0);
    internalPoints.remove(index);
  }

  public UPoint getVirtualPoint()
  {
    return virtualPoint;
  }

  public void setVirtualPoint(UPoint virtualPoint)
  {
    this.virtualPoint = virtualPoint;
  }

  private OrientedPoint getInternalMiddlePoint(OrientedPoint virtualPointToUse, boolean forNode1)
  {
    if (internalPoints.size() == 0)
    {
      if (forNode1)
        return virtualPointToUse;
      else
        return virtualPointToUse.reverse();
    }
    if (forNode1)
      return new OrientedPoint(internalPoints.get(0), OrientedPoint.ORIENTED_UNKNOWN);
    return new OrientedPoint(internalPoints.get(internalPoints.size() - 1), OrientedPoint.ORIENTED_UNKNOWN, true);
  }

  public CalculatedArcPoints calculateAllPoints()
  {
    return calculateAllPoints(virtualPoint);
  }


  public UPoint calculateBetterVirtualPoint(UPoint oldVirtual, AnchorPreviewFacet linkableToFavour)
  {
    OrientedPoint oriented = calculateBetterVirtualFromOld(oldVirtual, linkableToFavour);
    oriented = possiblyUseCorner(oriented, oldVirtual, linkableToFavour);
    oriented = possiblyUseCorner(oriented, oldVirtual,  (linkableToFavour == preview1) ? preview2 : preview1);

    return oriented == null ? this.getSimplestVirtualPoint() : oriented.getPoint();
  }

  private OrientedPoint possiblyUseCorner(OrientedPoint oriented, UPoint oldVirtual, AnchorPreviewFacet linkableToFavour)
  {
    if (oriented == null)
    {
      // a horizontal or vertical line is possible, but not with the current virtual point
      // find a corner on the node to favour that is close to virtual, and set it to that
      UPoint closestCorner = getClosestCorner(oldVirtual, linkableToFavour);
      oriented = calculateBetterVirtualFromOld(closestCorner, linkableToFavour);
    }
    return oriented;
  }

  private CalculatedArcPoints calculateAllPoints(UPoint virtualPointToUse)
  {
    // if we are using a virtual point, then the line must be straight
    // if not, force the issue
    List<UPoint> internalPoints = getInternalPoints();

    // if we are in virtual mode, and there is no overlap, use the simplest possible
    // after this, we are guaranteed to have a straight line, to the middle, horizontal or vertical
    OrientedPoint oriented = null;
    if (isVirtual())
    {
      oriented = calculateBetterVirtualFromOld(virtualPointToUse, null);
      if (oriented == null)
        oriented = new OrientedPoint(getSimplestVirtualPoint(), OrientedPoint.ORIENTED_MIDDLE);
    }

    OrientedPoint starter1 = getInternalMiddlePoint(oriented, true);
    starter1 = determineOrientation(starter1, preview1.getLinkableBounds(starter1));
    UPoint point1 = calculateOnShapePoint(starter1, preview1);

    OrientedPoint starter2 = getInternalMiddlePoint(oriented, false);
    starter2 = determineOrientation(starter2, preview2.getLinkableBounds(starter2));
    UPoint point2 = calculateOnShapePoint(starter2, preview2);

    // make the array of points for the line
    List<UPoint> allPoints = new ArrayList<UPoint>();
    allPoints.add(point1);
    allPoints.addAll(internalPoints);
    allPoints.add(point2);

    return new CalculatedArcPoints(node1, node2, virtualPoint, allPoints);
  }

  private UPoint getClosestCorner(UPoint point, AnchorPreviewFacet linkable)
  {
    UBounds bounds = linkable.getLinkableBounds(determineOrientation(new OrientedPoint(point), linkable.getPreviewFacet().getFullBounds()));
    UPoint[] corners = getCornersUsingGridSpace(bounds);

    double dist = 1e7;
    int index = 0;
    for (int lp = 0; lp < corners.length; lp++)
    {
      if (corners[lp].distance(point) < dist)
      {
        index = lp;
        dist = corners[lp].distance(point);
      }
    }
    return corners[index];
  }

  private UPoint[] getCornersUsingGridSpace(UBounds bounds)
  {
    UDimension gridSpace = Grid.getGridSpace();

    return new UPoint[]
    {
      roundBoxPointToGridSpace(bounds.getTopLeftPoint().add(new UDimension(-gridSpace.getWidth(),0)), true, true),
      roundBoxPointToGridSpace(bounds.getTopRightPoint().add(new UDimension(gridSpace.getWidth(),0)), false, true),
      roundBoxPointToGridSpace(bounds.getBottomLeftPoint().add(new UDimension(-gridSpace.getWidth(),0)), true, false),
      roundBoxPointToGridSpace(bounds.getBottomRightPoint().add(new UDimension(gridSpace.getWidth(),0)), false, false),

      roundBoxPointToGridSpace(bounds.getTopLeftPoint().add(new UDimension(0,-gridSpace.getHeight())), true, true),
      roundBoxPointToGridSpace(bounds.getTopRightPoint().add(new UDimension(0,-gridSpace.getHeight())), false, true),
      roundBoxPointToGridSpace(bounds.getBottomLeftPoint().add(new UDimension(0,gridSpace.getHeight())), true, false),
      roundBoxPointToGridSpace(bounds.getBottomRightPoint().add(new UDimension(0,gridSpace.getHeight())), false, false)
    };
  }

  private UPoint roundBoxPointToGridSpace(UPoint point, boolean xLeftOfCentre, boolean yLeftOfCentre)
  {
    UDimension gridSpace = Grid.getGridSpace();

    double
      newX,
      x = point.getX(),
      gridX = gridSpace.getWidth();

    if (xLeftOfCentre)
      newX = Math.ceil(x / gridX) * gridX;
    else
      newX = Math.floor(x / gridX) * gridX;

    double
      newY,
      y = point.getY(),
      gridY = gridSpace.getHeight();

    if (yLeftOfCentre)
      newY = Math.ceil(y / gridY) * gridY;
    else
      newY = Math.floor(y / gridY) * gridY;

    return new UPoint(newX, newY);
  }

  private OrientedPoint calculateBetterVirtualFromOld(UPoint oldVirtual, AnchorPreviewFacet possibleLinkableToFavour)
  {
    // get the bounds
    UBounds node1Bounds = preview1.getLinkableBounds(new OrientedPoint(oldVirtual));
    UBounds node2Bounds = preview2.getLinkableBounds(new OrientedPoint(oldVirtual));

    // if we are inside, treat differently
    if (node2Bounds.contains(node1Bounds))
    {
      OrientedPoint contained = calculateContainedVirtualPoint(node2Bounds, node1Bounds, oldVirtual);
      if (contained != null)
        contained = contained.reverse();
      return contained;
    }
    if (node1Bounds.contains(node2Bounds))
    {
      OrientedPoint contained = calculateContainedVirtualPoint(node1Bounds, node2Bounds, oldVirtual);
      return contained;
    }
    if (node1Bounds.addToExtent(new UDimension(1,1)).intersects(node2Bounds.addToExtent(new UDimension(1,1))))
      return calculateIntersectedVirtualPoint(node1Bounds, node2Bounds, oldVirtual, possibleLinkableToFavour);


    // we must have disjoint figures, if we have a vertical overlap, then use this
    node1Bounds = preview1.getLinkableBounds(new OrientedPoint(oldVirtual, OrientedPoint.ORIENTED_VERTICAL));
    node2Bounds = preview2.getLinkableBounds(new OrientedPoint(oldVirtual, OrientedPoint.ORIENTED_VERTICAL));
    double virtualX = oldVirtual.getX();
    if (virtualX >= node1Bounds.getBottomLeftPoint().getX() && virtualX <= node1Bounds.getTopRightPoint().getX() &&
        virtualX >= node2Bounds.getBottomLeftPoint().getX() && virtualX <= node2Bounds.getTopRightPoint().getX())
    {
      // calculate virtualY now, using the fact that the rectangles are disjoint
      if (node1Bounds.getBottomLeftPoint().getY() < node2Bounds.getTopRightPoint().getY())
      {
        return new OrientedPoint(
          new UPoint(virtualX, (node1Bounds.getBottomLeftPoint().getY() + node2Bounds.getTopRightPoint().getY())/2),
          OrientedPoint.ORIENTED_VERTICAL, true);
      }
      else
        return new OrientedPoint(
          new UPoint(virtualX, (node2Bounds.getBottomLeftPoint().getY() + node1Bounds.getTopLeftPoint().getY())/2),
          OrientedPoint.ORIENTED_VERTICAL, false);
    }

    // we must have disjoint figures, if we have a horizontal overlap, then use this
    node1Bounds = preview1.getLinkableBounds(new OrientedPoint(oldVirtual, OrientedPoint.ORIENTED_HORIZONTAL));
    node2Bounds = preview2.getLinkableBounds(new OrientedPoint(oldVirtual, OrientedPoint.ORIENTED_HORIZONTAL));
    double virtualY = oldVirtual.getY();
    if (virtualY >= node1Bounds.getTopLeftPoint().getY() && virtualY <= node1Bounds.getBottomRightPoint().getY() &&
        virtualY >= node2Bounds.getTopLeftPoint().getY() && virtualY <= node2Bounds.getBottomRightPoint().getY())
    {
      // calculate virtualX now, using the fact that the rectangles are disjoint
      if (node1Bounds.getBottomRightPoint().getX() < node2Bounds.getTopLeftPoint().getX())
        return new OrientedPoint(
          new UPoint((node1Bounds.getBottomRightPoint().getX() + node2Bounds.getTopLeftPoint().getX())/2, virtualY),
          OrientedPoint.ORIENTED_HORIZONTAL, true);
      else
        return new OrientedPoint(
          new UPoint((node2Bounds.getBottomRightPoint().getX() + node1Bounds.getTopLeftPoint().getX())/2, virtualY),
          OrientedPoint.ORIENTED_HORIZONTAL, false);
    }

    // if we get here, we can't make a horizontal or vertical line using the virtual point
    // even though it is possible to construct such a line between the two figures
    return null;
 }

  private UPoint calculateOnShapePoint(OrientedPoint oriented, AnchorPreviewFacet node)
  {
    UBounds bounds = node.getLinkableBounds(oriented);
    UPoint point = oriented.getPoint();
    AnchorPreviewFacet other = node == preview1 ? preview2 : preview1;
    boolean linkFromContained = node1 != node2 && bounds.contains(other.getLinkableBounds(oriented));

    switch (oriented.getOrientation())
    {
      case OrientedPoint.ORIENTED_MIDDLE:
      case OrientedPoint.ORIENTED_UNKNOWN:
        return node.calculateBoundaryPoint(oriented, linkFromContained, null, node.getMiddlePoint());

      case OrientedPoint.ORIENTED_VERTICAL:
        {
          // get a point on the box
          // NOTE: if the virtual point is inside the box, then we need to reverse
          boolean reversed = oriented.getReversed();

          // reverse this if this is containing the other node
          if (isVirtual())
            if (node == preview1 && bounds.contains(preview2.getLinkableBounds(oriented)) ||
                node == preview2 && bounds.contains(preview1.getLinkableBounds(oriented)))
              reversed = !reversed;

          // ask the node to calculate
          UPoint boxPoint = new UPoint(point.getX(), bounds.getTopRightPoint().getY());
          if (reversed)
            boxPoint = new UPoint(point.getX(), bounds.getBottomLeftPoint().getY());
          return node.calculateBoundaryPoint(oriented, linkFromContained, boxPoint, new UPoint(point.getX(), bounds.getMiddlePoint().getY()));
        }

      case OrientedPoint.ORIENTED_HORIZONTAL:
      default:
        // get a point on the box
        {
          // NOTE: if the virtual point is inside the box, then we need to reverse
          boolean reversed = oriented.getReversed();

          // reverse this if this is containing the other node
          if (isVirtual())
            if (node == preview1 && bounds.contains(preview2.getLinkableBounds(oriented)) ||
                node == preview2 && bounds.contains(preview1.getLinkableBounds(oriented)))
              reversed = !reversed;

          UPoint boxPoint = new UPoint(bounds.getTopLeftPoint().getX(), point.getY());
          if (reversed)
            boxPoint = new UPoint(bounds.getBottomRightPoint().getX(), point.getY());
          // ask the node to calculate
          return node.calculateBoundaryPoint(oriented, linkFromContained, boxPoint, new UPoint(bounds.getMiddlePoint().getX(), point.getY()));
        }
    }
  }

  private OrientedPoint calculateContainedVirtualPoint(UBounds container, UBounds contained, UPoint oldVirtual)
  {
    // if the virtual overlaps the internal horizontal contained bounds, use this
    double virtualX = oldVirtual.getX();
    double virtualY = oldVirtual.getY();
    if (virtualX >= contained.getTopLeftPoint().getX() && virtualX <= contained.getBottomRightPoint().getX())
    {
      if (virtualY >= contained.getMiddlePoint().getY())
        return new OrientedPoint(new UPoint(virtualX, contained.getBottomLeftPoint().getY() + 1000), OrientedPoint.ORIENTED_VERTICAL);
      else
        return new OrientedPoint(new UPoint(virtualX, contained.getTopRightPoint().getY() - 1000), OrientedPoint.ORIENTED_VERTICAL, true);
    }

    // if the virtual overlaps the internal vertical contained bounds, use this
    if (virtualY >= contained.getTopLeftPoint().getY() && virtualY <= contained.getBottomRightPoint().getY())
    {
      if (virtualX >= contained.getMiddlePoint().getX())
        return new OrientedPoint(new UPoint(contained.getTopRightPoint().getX() + 1000, virtualY), OrientedPoint.ORIENTED_HORIZONTAL);
      else
        return new OrientedPoint(new UPoint(contained.getBottomLeftPoint().getX() - 1000, virtualY), OrientedPoint.ORIENTED_HORIZONTAL, true);
    }

    // if we are here, give up again :-)
    return null;
  }

  private OrientedPoint calculateIntersectedVirtualPoint(UBounds one, UBounds two, UPoint oldVirtual, AnchorPreviewFacet possibleLinkableToFavour)
  {
    // get the intersected bounds
    UBounds intersection = new UBounds(new UPoint(0,0), new UDimension(0,0));
    intersection = one.intersect(two);

    // if the old virtual is horizontally ok, leave it
    if (oldVirtual.getX() >= intersection.getTopLeftPoint().getX() && oldVirtual.getX() <= intersection.getBottomRightPoint().getX())
    {
      if (one.getTopRightPoint().getY() < two.getTopRightPoint().getY())
        return new OrientedPoint(oldVirtual, OrientedPoint.ORIENTED_VERTICAL).reverse();
      else
        return new OrientedPoint(oldVirtual, OrientedPoint.ORIENTED_VERTICAL);
    }

    // if the old virtual is vertically ok, leave it
    // but make sure that we add the right polarity and reverse correctly
    // this is quite complicated
    if (oldVirtual.getY() >= intersection.getTopLeftPoint().getY() && oldVirtual.getY() <= intersection.getBottomLeftPoint().getY())
    {
      int offset = 100;
      if (possibleLinkableToFavour != preview1)
        offset = -offset;

      if (one.getBottomLeftPoint().getX() <= two.getBottomLeftPoint().getX() && one.getBottomLeftPoint().getX() <= intersection.getBottomLeftPoint().getX())
        return new OrientedPoint(new UPoint(intersection.getX() - offset, oldVirtual.getY()), OrientedPoint.ORIENTED_HORIZONTAL).reverse();
      else
        return new OrientedPoint(new UPoint(intersection.getTopRightPoint().getX() + offset, oldVirtual.getY()), OrientedPoint.ORIENTED_HORIZONTAL);
    }
    return new OrientedPoint(intersection.getMiddlePoint(), OrientedPoint.ORIENTED_VERTICAL);
  }

  private OrientedPoint determineOrientation(OrientedPoint oriented, UBounds bounds)
  {
    // the point is in one of 9 different parts relative to the box (in the centre)
    //                |    |
    //              0 | 1  | 2
    //            ----+----+----
    //              3 | 4  | 5
    //            ----+----+----
    //              6 | 7  | 8
    //                |    |
    // (share 5 amongst regions 1, 3, 5, 7)

    // need an outside point -- this will be adjusted for region 4
    if (oriented.getOrientation() != OrientedPoint.ORIENTED_UNKNOWN)
      return oriented;

    // work out which region the point is closest to
    UPoint point = oriented.getPoint();
    int yRegion = classifyOneDimensionalRegion(point.getY(), bounds.getTopLeftPoint().getY(), bounds.getBottomLeftPoint().getY());
    int xRegion = classifyOneDimensionalRegion(point.getX(), bounds.getTopLeftPoint().getX(), bounds.getTopRightPoint().getX());
    int regionNumber = yRegion * 3 + xRegion;

    // if the point is inside, we need to be a bit careful
    if (regionNumber == 4)
    {
      int regions[] = {1, 3, 5, 7};
      double distance[] =
        {bounds.getTopLeftPoint().getY() - point.getY(),
         bounds.getTopLeftPoint().getX() - point.getX(),
         bounds.getBottomRightPoint().getX() - point.getX(),
         bounds.getBottomRightPoint().getY() - point.getY()};

      int winner = 1;
      double winningDistance = 1e7;
      for (int lp = 0; lp < 4; lp++)
        if (Math.abs(distance[lp]) < winningDistance)
        {
          winner = regions[lp];
          winningDistance = Math.abs(distance[lp]);
        }
      regionNumber = winner;
    }

    switch (regionNumber)
    {
      case 0:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_MIDDLE);
      case 1:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_VERTICAL);
      case 2:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_MIDDLE);
      case 3:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_HORIZONTAL);
      case 4:  // should never occur...
        return new OrientedPoint(point, OrientedPoint.ORIENTED_HORIZONTAL, true);
      case 5:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_HORIZONTAL, true);
      case 6:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_MIDDLE);
      case 7:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_VERTICAL, true);
      case 8:
        return new OrientedPoint(point, OrientedPoint.ORIENTED_MIDDLE, true);
    }
    return null;  // never get here
  }

  private int classifyOneDimensionalRegion(double toClassify, double left, double right)
  {
    if (toClassify < left)
      return 0;
    if (toClassify > right)
      return 2;
    return 1;
  }

  public UPoint getSimplestVirtualPoint()
  {
    return new UBounds(preview1.getMiddlePoint(), preview2.getMiddlePoint()).getMiddlePoint();
  }

  public void offsetPoints(UDimension offset)
  {
    List<UPoint> newPoints = new ArrayList<UPoint>();
    Iterator iter = internalPoints.iterator();
    while (iter.hasNext())
    {
      UPoint point = (UPoint) iter.next();
      newPoints.add(point.add(offset));
    }
    internalPoints = newPoints;
    virtualPoint = virtualPoint.add(offset);
  }

  public boolean isVirtual()
  {
    return internalPoints.size() == 0;
  }

  public void removeKinks()
  {
    // calculate the points, and see if any kinks can be removed
    // only bother if this is not using a virtual point
    // -- logic is a bit tricky because we are removing points as we scan :-)
    if (!isVirtual())
    {
      CalculatedArcPoints calculated = this.calculateAllPoints();
      List<UPoint> points = calculated.getAllPoints();

      int internalCount = 0;
      int size = points.size();
      for (int lp = 1; lp < size - 1; lp++)
      {
        UPoint first = points.get(lp-1);
        UPoint middle = points.get(lp);
        UPoint last = points.get(lp+1);

        if (insideNodes(middle) || reasonablyStraight(first, middle, last))
          removeInternalPoint(internalCount);  // internal points array doesn't have 2 end pts
        else
          internalCount++;
      }

      // if this is now virtual, recalc the virtual point
      if (isVirtual())
        this.setVirtualPoint(calculateBetterVirtualPoint(virtualPoint, preview1));
    }
  }

  private boolean insideNodes(UPoint middle)
  {
    UBounds preview1Bounds = preview1.getLinkableBounds(new OrientedPoint(virtualPoint));
    UBounds preview2Bounds = preview2.getLinkableBounds(new OrientedPoint(virtualPoint));

    if (preview1.removeKinksOfLinkingIfIntersects() && preview1Bounds.contains(middle) && !preview1Bounds.contains(preview2Bounds))
      return true;
    if (preview2.removeKinksOfLinkingIfIntersects() && preview2Bounds.contains(middle) && !preview2Bounds.contains(preview1Bounds))
      return true;
    return false;
  }

  private boolean reasonablyStraight(UPoint first, UPoint middle, UPoint last)
  {
    // work out the angle
    UDimension
      lastDifference = last.subtract(first),
      middleDifference = middle.subtract(first);

    double
      lastAngle = Math.atan2(lastDifference.getHeight(), lastDifference.getWidth()),
      middleAngle = Math.atan2(middleDifference.getHeight(), middleDifference.getWidth());

    // if these are close enough, then we have a match (1% of 180 degrees)
    if (Math.abs(lastAngle - middleAngle) < 3.1415 / 100)
      return true;
    return false;
  }
}

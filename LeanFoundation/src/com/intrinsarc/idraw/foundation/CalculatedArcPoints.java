package com.intrinsarc.idraw.foundation;

import java.awt.geom.*;
import java.io.*;
import java.util.*;

import com.intrinsarc.geometry.*;

import edu.umd.cs.jazz.component.*;



/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public final class CalculatedArcPoints implements Serializable
{
  public static final int MAJOR_POINT_MIDDLE = 0;
  public static final int MAJOR_POINT_START  = 1;
  public static final int MAJOR_POINT_END    = 2;
  
  private UPoint virtualPoint;
  private List<UPoint> allPoints;
  private AnchorFacet node1;
  private AnchorFacet node2;

  public CalculatedArcPoints(AnchorFacet node1, AnchorFacet node2, UPoint virtualPoint, List<UPoint> allPoints)
  {
    this.node1 = node1;
    this.node2 = node2;
    this.virtualPoint = virtualPoint;
    this.allPoints = new ArrayList<UPoint>();
    this.allPoints.addAll(allPoints);
  }

  public CalculatedArcPoints(CalculatedArcPoints calculated, AnchorFacet node1, AnchorFacet node2)
  {
    this.node1 = node1;
    this.node2 = node2;
    virtualPoint = calculated.virtualPoint;
    allPoints = new ArrayList<UPoint>();
    allPoints.addAll(calculated.getAllPoints());
  }

  public CalculatedArcPoints(CalculatedArcPoints calculated)
  {
    this(calculated, calculated.getNode1(), calculated.getNode2());
  }

  public CalculatedArcPoints(ReferenceCalculatedArcPoints referenceCalculated)
  {
    this(getToolLinkableFromReference(referenceCalculated.getNode1Reference()),
         getToolLinkableFromReference(referenceCalculated.getNode2Reference()),
         referenceCalculated.getVirtualPoint(), referenceCalculated.getAllPoints());
  }

	private static AnchorFacet getToolLinkableFromReference(FigureReference reference)
  {
    return GlobalDiagramRegistry.registry.retrieveFigure(reference).getAnchorFacet();
  }

  public ReferenceCalculatedArcPoints getReferenceCalculatedArcPoints(DiagramFacet diagram)
  {
    return new ReferenceCalculatedArcPoints(virtualPoint, allPoints, node1.getFigureFacet().getFigureReference(), node2.getFigureFacet().getFigureReference());
  }

  public ReferenceCalculatedArcPoints getReferenceCalculatedArcPoints(DiagramFacet diagram, UDimension offset)
  {
    return new ReferenceCalculatedArcPoints(virtualPoint, allPoints, node1.getFigureFacet().getFigureReference(), node2.getFigureFacet().getFigureReference(), offset);
  }

  public AnchorFacet getNode1()
  {
    return node1;
  }

  public AnchorFacet getNode2()
  {
    return node2;
  }

  public UPoint getVirtualPoint()
  {
    return virtualPoint;
  }

  public List<UPoint> getAllPoints()
  {
    return allPoints;
  }

  public UPoint getStartPoint()
  {
    return allPoints.get(0);
  }

  public UPoint getEndPoint()
  {
    return allPoints.get(allPoints.size() - 1);
  }

  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    Iterator iter = allPoints.iterator();
    int index = 0;
    while (iter.hasNext())
    {
      UPoint point = (UPoint) iter.next();
      buffer.append("\tPoint " + index++ + " = " + point + "\n");
    }
    return buffer.toString();
  }
  
  
  public UPoint getMajorPoint(int majorPointType, boolean curved)
  {
    switch (majorPointType)
    {
      case MAJOR_POINT_MIDDLE:
        return calculateMiddlePoint(curved);
      case MAJOR_POINT_START:
        return getStartPoint();
      case MAJOR_POINT_END:
        return getEndPoint();
    }
    // should never get there
    return null;
  }

  public UPoint calculateMiddlePoint(boolean curved)
  {
		// get the list of points
		List points = getAllPointsByFlattening(curved);
	
	  // follow the line, and look for the exact middle
	  Iterator iter = points.iterator();
	  UPoint startPoint = (UPoint) iter.next();
		UPoint start = startPoint;
	  double totalDistance = 0;
	  while (iter.hasNext())
	  {
	    UPoint end = (UPoint) iter.next();
	    totalDistance += end.distance(start);
	    start = end;
	  }
	
	  // if we are here, return the middle, based on distance
	  double middleDistance = totalDistance / 2;
	
	  // iterate over the line, looking for the middle segment
	  start = startPoint;
	  iter = points.iterator();
	  iter.next();
	  double cumulativeDistance = 0;
	  while (iter.hasNext())
	  {
	    UPoint end = (UPoint) iter.next();
	
	    // get the current distance and check to see if within bounds
	    double currentDistance = end.distance(start);
      
	    if (middleDistance >= cumulativeDistance && middleDistance <= cumulativeDistance + currentDistance)
	    {
	      // handle when start and end are the same, or very close
        if (currentDistance < 1e-7)
          return start;

        double fractionAlong = (middleDistance - cumulativeDistance) / currentDistance;
	      double startX = start.getX();
	      double startY = start.getY();
	      double endX = end.getX();
	      double endY = end.getY();
	      return new UPoint(startX + fractionAlong * (endX - startX), startY + fractionAlong * (endY - startY));
	    }
	
	    // old end becomes the new start
	    cumulativeDistance += currentDistance;
	    start = end;
	  }
	
	  // shouldn't ever make it this far
	  return startPoint;
	}

	/**
	 * @param points
	 * @return
	 */
	public List getAllPointsByFlattening(boolean curved)
	{
		List<UPoint> allPoints = getAllPoints();
		if (!curved)
			// no need to flatten
			return allPoints;
			
		// if we are curved, flatten to allow us to work off line segments
		PathIterator flatIter =
			new FlatteningPathIterator(
				makeConnection(allPoints, true).getShape().getPathIterator(null), 2);
	
		List<UPoint> flatPoints = new ArrayList<UPoint>(); 
		for (; !flatIter.isDone(); flatIter.next())
		{
			double coords[] = new double[2];  // should only be a max of 1 point for each segment
			flatIter.currentSegment(coords);  // will be a move_to or a line_to
			flatPoints.add(new UPoint(coords[0], coords[1]));
		}
		return flatPoints;
	}
 
  public static ZShape makeConnection(List<UPoint> allPoints, boolean curved)
  {
    if (curved && allPoints.size() > 2)
      return makeCurvedConnection(allPoints);
    return makeStraightConnection(allPoints);
  }

  private static ZShape makeCurvedConnection(List<UPoint> allPoints)
  {
    UPoint points[] = allPoints.toArray(new UPoint[0]);
    int length = points.length;
    
    // NOTE: length is at least 3
    UPoint startPoint = points[0];
    ZQuadPolyLine connection = new ZQuadPolyLine(startPoint);
    
    for (int lp = 1; lp < length - 1; lp++)
    {
      UPoint controlPoint = points[lp];
      UPoint fullEndPoint = points[lp+1];
      
      UPoint endPoint = partWay(controlPoint, fullEndPoint, (lp == length - 2) ? 1 : 0.5);
      connection.add(controlPoint);
      connection.add(endPoint);
    }
    return connection;
  }
  
  private static ZShape makeStraightConnection(List points)
  {
    Iterator iter = points.iterator();
    UPoint startPoint = (UPoint) iter.next();

    ZPolyline connection = new ZPolyline(startPoint);
    while (iter.hasNext())
    {
      UPoint nextPoint = (UPoint) iter.next();
  
      connection.add(nextPoint);
    }
    return connection;
  }
  
  /**
   * @param fullBeginPoint
   * @param controlPoint
   * @return
   */
  public static UPoint partWay(UPoint pointA, UPoint pointB, double howFar)
  {
    return pointA.add(pointB.subtract(pointA).multiply(howFar));
  }
}

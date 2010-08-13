package com.intrinsarc.idraw.foundation;

import java.io.*;
import java.util.*;

import com.intrinsarc.geometry.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public final class ReferenceCalculatedArcPoints implements Serializable
{
  private UPoint virtualPoint;
  private List<UPoint> allPoints;
  private FigureReference node1Reference;
  private FigureReference node2Reference;

  public ReferenceCalculatedArcPoints(UPoint virtualPoint, List<UPoint> allPoints, FigureReference node1Reference, FigureReference node2Reference)
  {
    this.virtualPoint = virtualPoint;
    this.allPoints = new ArrayList<UPoint>();
    this.allPoints.addAll(allPoints);
    this.node1Reference = node1Reference;
    this.node2Reference = node2Reference;
  }

  public ReferenceCalculatedArcPoints(UPoint virtualPoint, List<UPoint> allPoints, FigureReference node1Reference, FigureReference node2Reference, UDimension offset)
  {
    this.virtualPoint = virtualPoint.add(offset);
    this.allPoints = new ArrayList<UPoint>();
    for (UPoint pt : allPoints)
    	this.allPoints.add(pt.add(offset));
    this.node1Reference = node1Reference;
    this.node2Reference = node2Reference;
  }

  public UPoint getVirtualPoint()
  {
    return virtualPoint;
  }

  public List<UPoint> getAllPoints()
  {
    return allPoints;
  }

  public FigureReference getNode1Reference()
  {
    return node1Reference;
  }

  public FigureReference getNode2Reference()
  {
    return node2Reference;
  }
}
// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * PolyPolygon TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: PolyPolygon.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class PolyPolygon extends EMFTag
{

  private Rectangle bounds;
  private int start, end;
  private int[] numberOfPoints;
  private Point[][] points;

  PolyPolygon()
  {
    super(8, 1);
  }

  public PolyPolygon(Rectangle bounds, int start, int end, int[] numberOfPoints, Point[][] points)
  {
    this();
    this.bounds = bounds;
    this.start = start;
    this.end = end;
    this.numberOfPoints = numberOfPoints;
    this.points = points;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    Rectangle bounds = emf.readRECTL();
    int np = emf.readDWORD();
    int totalNumberOfPoints = emf.readDWORD();
    int[] pc = new int[np];
    Point[][] points = new Point[np][];
    for (int i = 0; i < np; i++)
    {
      pc[i] = emf.readDWORD();
      points[i] = new Point[pc[i]];
    }
    for (int i = 0; i < np; i++)
    {
      points[i] = emf.readPOINTL(pc[i]);
    }
    PolyPolygon tag = new PolyPolygon(bounds, 0, np - 1, pc, points);
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeRECTL(bounds);
    emf.writeDWORD(end - start + 1);
    int c = 0;
    for (int i = start; i < end + 1; i++)
    {
      c += numberOfPoints[i];
    }
    emf.writeDWORD(c);
    for (int i = start; i < end + 1; i++)
    {
      emf.writeDWORD(numberOfPoints[i]);
    }
    for (int i = start; i < end + 1; i++)
    {
      emf.writePOINTL(numberOfPoints[i], points[i]);
    }
  }

  public String toString()
  {
    return super.toString() + "\n" + "  bounds: " + bounds + "\n" + "  #polys: " + (end - start + 1);
  }
}

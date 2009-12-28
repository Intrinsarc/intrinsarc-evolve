// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * PolylineTo16 TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: PolylineTo16.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class PolylineTo16 extends EMFTag
{

  private Rectangle bounds;
  private int numberOfPoints;
  private Point[] points;

  PolylineTo16()
  {
    super(89, 1);
  }

  public PolylineTo16(Rectangle bounds, int numberOfPoints, Point[] points)
  {
    this();
    this.bounds = bounds;
    this.numberOfPoints = numberOfPoints;
    this.points = points;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    Rectangle r = emf.readRECTL();
    int n = emf.readDWORD();
    PolylineTo16 tag = new PolylineTo16(r, n, emf.readPOINTS(n));
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeRECTL(bounds);
    emf.writeDWORD(numberOfPoints);
    emf.writePOINTS(numberOfPoints, points);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  bounds: " + bounds + "\n" + "  #points: " + numberOfPoints;
  }
}

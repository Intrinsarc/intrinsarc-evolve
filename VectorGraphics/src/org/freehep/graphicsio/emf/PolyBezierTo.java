// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * PolyBezierTo TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: PolyBezierTo.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class PolyBezierTo extends EMFTag
{

  private Rectangle bounds;
  private int numberOfPoints;
  private Point[] points;

  PolyBezierTo()
  {
    super(5, 1);
  }

  public PolyBezierTo(Rectangle bounds, int numberOfPoints, Point[] points)
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
    PolyBezierTo tag = new PolyBezierTo(r, n, emf.readPOINTL(n));
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeRECTL(bounds);
    emf.writeDWORD(numberOfPoints);
    emf.writePOINTL(numberOfPoints, points);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  bounds: " + bounds + "\n" + "  #points: " + numberOfPoints;
  }
}

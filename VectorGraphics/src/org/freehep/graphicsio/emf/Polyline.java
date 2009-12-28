// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * Polyline TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: Polyline.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class Polyline extends EMFTag
{

  private Rectangle bounds;
  private int numberOfPoints;
  private Point[] points;

  Polyline()
  {
    super(4, 1);
  }

  public Polyline(Rectangle bounds, int numberOfPoints, Point[] points)
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
    Polyline tag = new Polyline(r, n, emf.readPOINTL(n));
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

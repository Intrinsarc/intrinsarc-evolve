// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;

/**
 * Polygon TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Polygon.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class Polygon extends Polyline
{

  public Polygon()
  {
    super(4, 7, 1);
  }

  public Polygon(Point2D[] p)
  {
    this();
    this.p = p;
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.println("POLYGON");
    writePointList(cgm);
  }
}

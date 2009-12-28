// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;

/**
 * DisjointPolyline TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DisjointPolyline.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class DisjointPolyline extends Polyline
{

  public DisjointPolyline()
  {
    super(4, 2, 1);
  }

  public DisjointPolyline(Point2D[] p)
  {
    this();
    this.p = p;
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.println("DISJTLINE");
    writePointList(cgm);
  }

}

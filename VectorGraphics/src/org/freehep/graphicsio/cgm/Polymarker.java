// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;

/**
 * Polymarker TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Polymarker.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class Polymarker extends Polyline
{

  public Polymarker()
  {
    super(4, 3, 1);
  }

  public Polymarker(Point2D[] p)
  {
    this();
    this.p = p;
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.println("MARKER");
    writePointList(cgm);
  }

}

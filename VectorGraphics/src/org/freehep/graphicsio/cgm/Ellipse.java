// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;

/**
 * Ellipse TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Ellipse.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class Ellipse extends CGMTag
{

  protected Point2D p, c1, c2;

  public Ellipse()
  {
    super(4, 17, 1);
  }

  public Ellipse(Point2D p, Point2D c1, Point2D c2)
  {
    this();
    this.p = p;
    this.c1 = c1;
    this.c2 = c2;
  }

  protected Ellipse(int elementClass, int elementID, int version)
  {
    super(elementClass, elementID, version);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writePoint(p);
    cgm.writePoint(c1);
    cgm.writePoint(c2);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("ELLIPSE ");
    writeEllipseSpec(cgm);
  }

  protected void writeEllipseSpec(CGMWriter cgm) throws IOException
  {
    cgm.writePoint(p);
    cgm.print(", ");
    cgm.writePoint(c1);
    cgm.print(", ");
    cgm.writePoint(c2);
  }
}

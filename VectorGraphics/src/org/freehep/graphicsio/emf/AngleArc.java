// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * AngleArc TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: AngleArc.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class AngleArc extends EMFTag
{

  private Point center;
  private int radius;
  private float startAngle, sweepAngle;

  AngleArc()
  {
    super(41, 1);
  }

  public AngleArc(Point center, int radius, float startAngle, float sweepAngle)
  {
    this();
    this.center = center;
    this.radius = radius;
    this.startAngle = startAngle;
    this.sweepAngle = sweepAngle;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    AngleArc tag = new AngleArc(emf.readPOINTL(), emf.readDWORD(), emf.readFLOAT(), emf.readFLOAT());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writePOINTL(center);
    emf.writeDWORD(radius);
    emf.writeFLOAT(startAngle);
    emf.writeFLOAT(sweepAngle);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  center: " + center + "\n" + "  radius: " + radius + "\n" + "  startAngle: "
        + startAngle + "\n" + "  sweepAngle: " + sweepAngle;
  }
}

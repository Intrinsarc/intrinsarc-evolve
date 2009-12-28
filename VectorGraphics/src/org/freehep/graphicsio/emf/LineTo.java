// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * LineTo TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: LineTo.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class LineTo extends EMFTag
{

  private Point point;

  LineTo()
  {
    super(54, 1);
  }

  public LineTo(Point point)
  {
    this();
    this.point = point;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    LineTo tag = new LineTo(emf.readPOINTL());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writePOINTL(point);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  point: " + point;
  }
  public Point getPoint()
  {
    return point;
  }

}

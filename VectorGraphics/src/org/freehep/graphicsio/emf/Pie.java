// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * Pie TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: Pie.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class Pie extends EMFTag
{

  private Rectangle bounds;
  private Point start, end;

  Pie()
  {
    super(47, 1);
  }

  public Pie(Rectangle bounds, Point start, Point end)
  {
    this();
    this.bounds = bounds;
    this.start = start;
    this.end = end;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    Pie tag = new Pie(emf.readRECTL(), emf.readPOINTL(), emf.readPOINTL());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeRECTL(bounds);
    emf.writePOINTL(start);
    emf.writePOINTL(end);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  bounds: " + bounds + "\n" + "  start: " + start + "\n" + "  end: " + end;
  }
  public Rectangle getBounds()
  {
    return bounds;
  }
  public Point getStart()
  {
    return start;
  }
  public Point getEnd()
  {
    return end;
  }
}

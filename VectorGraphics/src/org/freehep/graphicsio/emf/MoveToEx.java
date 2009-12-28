// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * MoveToEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: MoveToEx.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class MoveToEx extends EMFTag
{

  private Point point;

  MoveToEx()
  {
    super(27, 1);
  }

  public MoveToEx(Point point)
  {
    this();
    this.point = point;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    MoveToEx tag = new MoveToEx(emf.readPOINTL());
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

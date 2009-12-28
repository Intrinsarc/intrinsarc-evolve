// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * SetWindowOrgEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetWindowOrgEx.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetWindowOrgEx extends EMFTag
{

  private Point point;

  SetWindowOrgEx()
  {
    super(10, 1);
  }

  public SetWindowOrgEx(Point point)
  {
    this();
    this.point = point;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetWindowOrgEx tag = new SetWindowOrgEx(emf.readPOINTL());
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
}

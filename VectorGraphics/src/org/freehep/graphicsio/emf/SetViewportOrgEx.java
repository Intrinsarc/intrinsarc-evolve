// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * SetViewportOrgEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetViewportOrgEx.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetViewportOrgEx extends EMFTag
{

  private Point point;

  SetViewportOrgEx()
  {
    super(12, 1);
  }

  public SetViewportOrgEx(Point point)
  {
    this();
    this.point = point;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetViewportOrgEx tag = new SetViewportOrgEx(emf.readPOINTL());
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

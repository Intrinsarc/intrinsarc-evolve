// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * SetViewportExtEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetViewportExtEx.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SetViewportExtEx extends EMFTag
{

  private Dimension size;

  SetViewportExtEx()
  {
    super(11, 1);
  }

  public SetViewportExtEx(Dimension size)
  {
    this();
    this.size = size;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetViewportExtEx tag = new SetViewportExtEx(emf.readSIZEL());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeSIZEL(size);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  size: " + size;
  }
}

// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * SetWindowExtEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetWindowExtEx.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetWindowExtEx extends EMFTag
{

  private Dimension size;

  SetWindowExtEx()
  {
    super(9, 1);
  }

  public SetWindowExtEx(Dimension size)
  {
    this();
    this.size = size;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetWindowExtEx tag = new SetWindowExtEx(emf.readSIZEL());
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

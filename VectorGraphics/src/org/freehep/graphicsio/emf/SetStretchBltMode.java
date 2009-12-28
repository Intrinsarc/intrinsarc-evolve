// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SetStretchBltMode TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetStretchBltMode.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetStretchBltMode extends EMFTag implements EMFConstants
{

  private int mode;

  SetStretchBltMode()
  {
    super(21, 1);
  }

  public SetStretchBltMode(int mode)
  {
    this();
    this.mode = mode;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetStretchBltMode tag = new SetStretchBltMode(emf.readDWORD());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(mode);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  mode: " + mode;
  }
}

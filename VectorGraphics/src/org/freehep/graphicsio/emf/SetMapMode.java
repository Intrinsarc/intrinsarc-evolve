// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SetMapMode TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetMapMode.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetMapMode extends EMFTag implements EMFConstants
{

  private int mode;

  SetMapMode()
  {
    super(17, 1);
  }

  public SetMapMode(int mode)
  {
    this();
    this.mode = mode;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetMapMode tag = new SetMapMode(emf.readDWORD());
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

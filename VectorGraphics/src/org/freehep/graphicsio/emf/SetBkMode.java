// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SetBkMode TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetBkMode.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetBkMode extends EMFTag implements EMFConstants
{

  private int mode;

  SetBkMode()
  {
    super(18, 1);
  }

  public SetBkMode(int mode)
  {
    this();
    this.mode = mode;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetBkMode tag = new SetBkMode(emf.readDWORD());
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

// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SetTextAlign TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetTextAlign.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SetTextAlign extends EMFTag implements EMFConstants
{

  private int mode;

  SetTextAlign()
  {
    super(22, 1);
  }

  public SetTextAlign(int mode)
  {
    this();
    this.mode = mode;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetTextAlign tag = new SetTextAlign(emf.readDWORD());
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

// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SetROP2 TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetROP2.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetROP2 extends EMFTag implements EMFConstants
{

  private int mode;

  SetROP2()
  {
    super(20, 1);
  }

  public SetROP2(int mode)
  {
    this();
    this.mode = mode;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetROP2 tag = new SetROP2(emf.readDWORD());
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

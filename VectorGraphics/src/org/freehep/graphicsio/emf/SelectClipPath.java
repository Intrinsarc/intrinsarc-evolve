// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SelectClipPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SelectClipPath.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SelectClipPath extends EMFTag implements EMFConstants
{

  private int mode;

  SelectClipPath()
  {
    super(67, 1);
  }

  public SelectClipPath(int mode)
  {
    this();
    this.mode = mode;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SelectClipPath tag = new SelectClipPath(emf.readDWORD());
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

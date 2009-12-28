// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SelectPalette TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SelectPalette.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SelectPalette extends EMFTag
{

  private int index;

  SelectPalette()
  {
    super(48, 1);
  }

  public SelectPalette(int index)
  {
    this();
    this.index = index;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SelectPalette tag = new SelectPalette(emf.readDWORD());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(index);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  index: 0x" + Integer.toHexString(index);
  }
}

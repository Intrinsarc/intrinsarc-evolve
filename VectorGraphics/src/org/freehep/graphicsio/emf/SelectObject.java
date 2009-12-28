// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SelectObject TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SelectObject.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SelectObject extends EMFTag
{

  private int index;

  SelectObject()
  {
    super(37, 1);
  }

  public SelectObject(int index)
  {
    this();
    this.index = index;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SelectObject tag = new SelectObject(emf.readDWORD());
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

  public int getIndex()
  {
    return index;
  }
}

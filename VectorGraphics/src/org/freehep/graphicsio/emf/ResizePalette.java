// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * ResizePalette TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ResizePalette.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class ResizePalette extends EMFTag
{

  private int index, entries;

  ResizePalette()
  {
    super(51, 1);
  }

  public ResizePalette(int index, int entries)
  {
    this();
    this.index = index;
    this.entries = entries;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    ResizePalette tag = new ResizePalette(emf.readDWORD(), emf.readDWORD());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(index);
    emf.writeDWORD(entries);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  index: 0x" + Integer.toHexString(index) + "\n" + "  entries: " + entries;
  }
}

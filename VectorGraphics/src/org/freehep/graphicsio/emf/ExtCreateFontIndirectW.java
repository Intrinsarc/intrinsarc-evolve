// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * ExtCreateFontIndirectW TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ExtCreateFontIndirectW.java,v 1.1 2006-07-03 13:32:19 amcveigh
 *          Exp $
 */
public class ExtCreateFontIndirectW extends EMFTag
{

  private int index;
  private ExtLogFontW font;

  ExtCreateFontIndirectW()
  {
    super(82, 1);
  }

  public ExtCreateFontIndirectW(int index, ExtLogFontW font)
  {
    this();
    this.index = index;
    this.font = font;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    ExtCreateFontIndirectW tag = new ExtCreateFontIndirectW(emf.readDWORD(), new ExtLogFontW(emf));
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(index);
    font.write(emf);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  index: 0x" + Integer.toHexString(index) + "\n" + font.toString();
  }
}

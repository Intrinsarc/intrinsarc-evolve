// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * TextFontIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: TextFontIndex.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class TextFontIndex extends CGMTag
{

  private int index;

  public TextFontIndex()
  {
    super(5, 10, 1);
  }

  public TextFontIndex(int index)
  {
    this();
    this.index = index;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeIntegerIndex(index);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("TEXTINDEX ");
    cgm.writeInteger(index);
  }
}

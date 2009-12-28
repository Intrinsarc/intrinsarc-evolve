// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * TextBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: TextBundleIndex.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class TextBundleIndex extends CGMTag
{

  private int index;

  public TextBundleIndex()
  {
    super(5, 9, 1);
  }

  public TextBundleIndex(int index)
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

// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * FillBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: FillBundleIndex.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class FillBundleIndex extends CGMTag
{

  private int index;

  public FillBundleIndex()
  {
    super(5, 21, 1);
  }

  public FillBundleIndex(int index)
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
    cgm.print("FILLINDEX ");
    cgm.writeInteger(index);
  }
}

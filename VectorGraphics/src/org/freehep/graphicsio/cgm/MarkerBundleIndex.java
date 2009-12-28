// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * MarkerBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MarkerBundleIndex.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class MarkerBundleIndex extends CGMTag
{

  private int index;

  public MarkerBundleIndex()
  {
    super(5, 5, 1);
  }

  public MarkerBundleIndex(int index)
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
    cgm.print("MARKERINDEX ");
    cgm.writeInteger(index);
  }
}

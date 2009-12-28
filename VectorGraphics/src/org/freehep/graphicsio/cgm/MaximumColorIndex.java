// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * MaximumColorIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MaximumColorIndex.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class MaximumColorIndex extends CGMTag
{

  private int index;

  public MaximumColorIndex()
  {
    super(1, 9, 1);
  }

  public MaximumColorIndex(int index)
  {
    this();
    this.index = index;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {

    cgm.writeColorIndex(index);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("MAXCOLRINDEX ");
    cgm.writeInteger(127); // FIXME
  }
}

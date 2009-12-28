// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * ClipIndicator TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ClipIndicator.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class ClipIndicator extends CGMTag
{

  private boolean on;

  public ClipIndicator()
  {
    super(3, 4, 1);
  }

  public ClipIndicator(boolean on)
  {
    this();
    this.on = on;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeEnumerate((on) ? 1 : 0);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("CLIP ");
    cgm.print((on) ? "ON" : "OFF");
  }
}

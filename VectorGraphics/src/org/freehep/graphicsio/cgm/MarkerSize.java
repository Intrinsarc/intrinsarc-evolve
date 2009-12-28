// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * MarkerSize TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MarkerSize.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class MarkerSize extends CGMTag
{

  private double size;

  public MarkerSize()
  {
    super(5, 7, 1);
  }

  public MarkerSize(int size)
  {
    this();
    this.size = size;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    if (cgm.getMarkerSizeSpecificationMode() == MarkerSizeSpecificationMode.ABSOLUTE)
    {
      cgm.writeVDC(size);
    }
    else
    {
      cgm.writeReal(size);
    }
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("MARKERSIZE ");
    if (cgm.getMarkerSizeSpecificationMode() == MarkerSizeSpecificationMode.ABSOLUTE)
    {
      cgm.writeVDC(size);
    }
    else
    {
      cgm.writeReal(size);
    }
  }
}

// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * EdgeWidth TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EdgeWidth.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class EdgeWidth extends CGMTag
{

  private double width;

  public EdgeWidth()
  {
    super(5, 28, 1);
  }

  public EdgeWidth(double width)
  {
    this();
    this.width = width;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    if (cgm.getEdgeWidthSpecificationMode() == EdgeWidthSpecificationMode.ABSOLUTE)
    {
      cgm.writeVDC(width);
    }
    else
    {
      cgm.writeReal(width);
    }
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("EDGEWIDTH ");
    if (cgm.getEdgeWidthSpecificationMode() == EdgeWidthSpecificationMode.ABSOLUTE)
    {
      cgm.writeVDC(width);
    }
    else
    {
      cgm.writeReal(width);
    }
  }
}

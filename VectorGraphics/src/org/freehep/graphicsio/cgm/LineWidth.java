// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * LineWidth TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: LineWidth.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class LineWidth extends CGMTag
{

  private double width;

  public LineWidth()
  {
    super(5, 3, 1);
  }

  public LineWidth(double width)
  {
    this();
    this.width = width;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    if (cgm.getLineWidthSpecificationMode() == LineWidthSpecificationMode.ABSOLUTE)
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
    cgm.print("LINEWIDTH ");
    if (cgm.getLineWidthSpecificationMode() == LineWidthSpecificationMode.ABSOLUTE)
    {
      cgm.writeVDC(width);
    }
    else
    {
      cgm.writeReal(width);
    }
  }
}

// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * PatternSize TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: PatternSize.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class PatternSize extends CGMTag
{

  private double hx, hy, wx, wy;

  public PatternSize()
  {
    super(5, 33, 1);
  }

  public PatternSize(double hx, double hy, double wx, double wy)
  {
    this();
    this.hx = hx;
    this.hy = hy;
    this.wx = wx;
    this.wy = wy;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    if (cgm.getInteriorStyleSpecificationMode() == InteriorStyleSpecificationMode.ABSOLUTE)
    {
      cgm.writeVDC(hx);
      cgm.writeVDC(hy);
      cgm.writeVDC(wx);
      cgm.writeVDC(wy);
    }
    else
    {
      cgm.writeReal(hx);
      cgm.writeReal(hy);
      cgm.writeReal(wx);
      cgm.writeReal(wy);
    }
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("PATSIZE ");
    if (cgm.getInteriorStyleSpecificationMode() == InteriorStyleSpecificationMode.ABSOLUTE)
    {
      cgm.writeVDC(hx);
      cgm.print(", ");
      cgm.writeVDC(hy);
      cgm.print(", ");
      cgm.writeVDC(wx);
      cgm.print(", ");
      cgm.writeVDC(wy);
    }
    else
    {
      cgm.writeReal(hx);
      cgm.print(", ");
      cgm.writeReal(hy);
      cgm.print(", ");
      cgm.writeReal(wx);
      cgm.print(", ");
      cgm.writeReal(wy);
    }
  }
}

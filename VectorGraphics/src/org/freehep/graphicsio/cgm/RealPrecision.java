// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * RealPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: RealPrecision.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class RealPrecision extends CGMTag
{

  // FIXME: not complete
  public RealPrecision()
  {
    super(1, 5, 1);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {

    cgm.setRealPrecision(false, true);
    cgm.writeEnumerate(0); // floating
    cgm.writeInteger(12); // double exp
    cgm.writeInteger(52); // double fract
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.setRealPrecision(false, true);
    cgm.print("REALPREC ");
    cgm.writeReal(Double.MIN_VALUE);
    cgm.print(", ");
    cgm.writeReal(Double.MAX_VALUE);
    cgm.print(", ");
    cgm.writeInteger(20); // FIXME: check
  }
}

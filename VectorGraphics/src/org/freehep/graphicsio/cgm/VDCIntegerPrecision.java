// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * VDCIntegerPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: VDCIntegerPrecision.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class VDCIntegerPrecision extends CGMTag
{

  // FIXME: not complete
  public VDCIntegerPrecision()
  {
    super(3, 1, 1);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.setVDCIntegerPrecision(16);
    cgm.writeInteger(16);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("VDCTYPE INTEGER;");
    cgm.setVDCIntegerPrecision(16);
    cgm.print("VDCINTEGERPREC");
    cgm.writeInteger(Short.MIN_VALUE);
    cgm.print(", ");
    cgm.writeInteger(Short.MAX_VALUE);
  }
}

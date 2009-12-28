// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * ColorPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ColorPrecision.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class ColorPrecision extends CGMTag
{

  // FIXME: not complete
  public ColorPrecision()
  {
    super(1, 7, 1);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {

    cgm.setDirectColorPrecision(24);
    cgm.writeInteger(24);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.setDirectColorPrecision(24);
    cgm.print("COLRPREC ");
    cgm.writeInteger(255);
  }
}

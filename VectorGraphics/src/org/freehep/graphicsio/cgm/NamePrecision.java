// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * NamePrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: NamePrecision.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class NamePrecision extends CGMTag
{

  // FIXME: not complete
  public NamePrecision()
  {
    super(1, 16, 2);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.setNamePrecision(16);
    cgm.writeInteger(16);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.setNamePrecision(16);
    cgm.print("NAMEPREC ");
    cgm.writeInteger(Short.MIN_VALUE);
    cgm.print(", ");
    cgm.writeInteger(Short.MAX_VALUE);
  }
}

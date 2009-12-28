// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * IndexPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: IndexPrecision.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class IndexPrecision extends CGMTag
{

  // FIXME: not complete
  public IndexPrecision()
  {
    super(1, 6, 1);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {

    cgm.setIndexPrecision(16);
    cgm.writeInteger(16);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("INDEXPREC ");
    cgm.writeInteger(Short.MIN_VALUE);
    cgm.print(", ");
    cgm.writeInteger(Short.MAX_VALUE);
  }
}

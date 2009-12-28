// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * EdgeWidth TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MitreLimit.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class MitreLimit extends CGMTag
{

  private double limit;

  public MitreLimit()
  {
    super(3, 19, 1);
  }

  public MitreLimit(double limit)
  {
    this();
    this.limit = limit;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeReal(limit);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("MITRELIMIT ");
    cgm.writeReal(limit);
  }
}

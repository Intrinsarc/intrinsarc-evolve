// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * HatchIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: HatchIndex.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class HatchIndex extends CGMTag
{

  public static final int HORIZONTAL = 1;
  public static final int VERTICAL = 2;
  public static final int POSITIVE_SLOPE = 3;
  public static final int NEGATIVE_SLOPE = 4;
  public static final int HV_CROSSHATCH = 5;
  public static final int PN_CROSSHATCH = 6;
  private int index;

  public HatchIndex()
  {
    super(5, 24, 1);
  }

  public HatchIndex(int index)
  {
    this();
    this.index = index;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeIntegerIndex(index);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("HATCHINDEX ");
    cgm.writeInteger(index);
  }
}

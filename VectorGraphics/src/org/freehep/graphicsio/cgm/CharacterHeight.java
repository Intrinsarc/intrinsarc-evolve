// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * CharacterHeight TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterHeight.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class CharacterHeight extends CGMTag
{

  private double height;

  public CharacterHeight()
  {
    super(5, 15, 1);
  }

  public CharacterHeight(double height)
  {
    this();
    this.height = height;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeVDC(height);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("CHARHEIGHT ");
    cgm.writeVDC(height);
  }
}

// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * CharacterSpacing TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterSpacing.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class CharacterSpacing extends CGMTag
{

  private double spacing;

  public CharacterSpacing()
  {
    super(5, 13, 1);
  }

  public CharacterSpacing(double spacing)
  {
    this();
    this.spacing = spacing;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeReal(spacing);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("CHARSPACE ");
    cgm.writeReal(spacing);
  }
}

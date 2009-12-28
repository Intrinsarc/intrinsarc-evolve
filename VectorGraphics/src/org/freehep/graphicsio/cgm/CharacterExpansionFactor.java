// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * CharacterExpansionFactor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterExpansionFactor.java,v 1.1 2006-07-03 13:32:37
 *          amcveigh Exp $
 */
public class CharacterExpansionFactor extends CGMTag
{

  private double factor;

  public CharacterExpansionFactor()
  {
    super(5, 12, 1);
  }

  public CharacterExpansionFactor(double factor)
  {
    this();
    this.factor = factor;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeReal(factor);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("CHAREXPAN ");
    cgm.writeReal(factor);
  }
}

// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * AlternateCharacterSetIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: AlternateCharacterSetIndex.java,v 1.1 2006-07-03 13:32:40
 *          amcveigh Exp $
 */
public class AlternateCharacterSetIndex extends CGMTag
{

  private int index;

  public AlternateCharacterSetIndex()
  {
    super(5, 20, 1);
  }

  public AlternateCharacterSetIndex(int index)
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
    cgm.print("ALTCHARSETINDEX ");
    cgm.writeInteger(index);
  }
}

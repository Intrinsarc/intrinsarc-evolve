// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * CharacterSetIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterSetIndex.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class CharacterSetIndex extends CGMTag
{

  private int index;

  public CharacterSetIndex()
  {
    super(5, 19, 1);
  }

  public CharacterSetIndex(int index)
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
    cgm.print("CHARSETINDEX ");
    cgm.writeInteger(index);
  }
}

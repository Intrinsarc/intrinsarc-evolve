// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * MetafileDefaultsReplacement TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MetafileDefaultsReplacement.java,v 1.1 2006-07-03 13:32:42
 *          amcveigh Exp $
 */
public class MetafileDefaultsReplacement extends CGMTag
{

  private int type;

  public MetafileDefaultsReplacement()
  {
    super(1, 12, 1);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    throw new IOException("Not properly implemented.");
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    throw new IOException("Not properly implemented.");
  }
}

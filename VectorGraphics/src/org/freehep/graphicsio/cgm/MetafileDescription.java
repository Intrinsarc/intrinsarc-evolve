// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * MetafileDescription TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MetafileDescription.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class MetafileDescription extends CGMTag
{

  private String description;

  public MetafileDescription()
  {
    super(1, 2, 1);
  }

  public MetafileDescription(String description)
  {
    this();
    this.description = description;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeString(description);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("MFDESC ");
    cgm.writeString(description);
  }
}

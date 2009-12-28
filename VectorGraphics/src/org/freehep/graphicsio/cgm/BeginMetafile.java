// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * BeginMetafile TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: BeginMetafile.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class BeginMetafile extends CGMTag
{

  private String name;

  public BeginMetafile()
  {
    super(0, 1, 1);
  }

  public BeginMetafile(String name)
  {
    this();
    this.name = name;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeString(name);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("BEGMF ");
    cgm.writeString(name);
    cgm.indent();
  }

}

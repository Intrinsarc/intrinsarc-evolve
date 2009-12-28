// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SetMapperFlags TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetMapperFlags.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SetMapperFlags extends EMFTag
{

  private int flags;

  SetMapperFlags()
  {
    super(16, 1);
  }

  public SetMapperFlags(int flags)
  {
    this();
    this.flags = flags;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetMapperFlags tag = new SetMapperFlags(emf.readDWORD());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(flags);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  flags: " + flags;
  }
}

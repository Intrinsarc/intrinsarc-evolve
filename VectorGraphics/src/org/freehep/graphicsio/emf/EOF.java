// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * Rectangle TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: EOF.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class EOF extends EMFTag
{

  public EOF()
  {
    super(14, 1);
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    int[] bytes = emf.readUnsignedByte(len);
    EOF tag = new EOF();
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(0); // # of palette entries
    emf.writeDWORD(0x10); // offset for palette
    // ... palette
    emf.writeDWORD(0x14); // offset to start of record
  }
}

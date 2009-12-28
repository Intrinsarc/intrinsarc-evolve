// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * SetMiterLimit TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetMiterLimit.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SetMiterLimit extends EMFTag
{

  private int limit;

  SetMiterLimit()
  {
    super(58, 1);
  }

  public SetMiterLimit(int limit)
  {
    this();
    this.limit = limit;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetMiterLimit tag = new SetMiterLimit(emf.readDWORD());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(limit);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  limit: " + limit;
  }
}

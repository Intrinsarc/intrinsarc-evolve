// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * CreatePen TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: CreatePen.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class CreatePen extends EMFTag
{

  private int index;
  private LogPen pen;

  CreatePen()
  {
    super(38, 1);
  }

  public CreatePen(int index, LogPen pen)
  {
    this();
    this.index = index;
    this.pen = pen;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    CreatePen tag = new CreatePen(emf.readDWORD(), new LogPen(emf));
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(index);
    pen.write(emf);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  index: 0x" + Integer.toHexString(index) + "\n" + pen.toString();
  }
  public int getIndex()
  {
    return index;
  }
  public LogPen getPen()
  {
    return pen;
  }


}

// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * CreateBrushIndirect TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: CreateBrushIndirect.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class CreateBrushIndirect extends EMFTag
{

  private int index;
  private LogBrush32 brush;

  CreateBrushIndirect()
  {
    super(39, 1);
  }

  public CreateBrushIndirect(int index, LogBrush32 brush)
  {
    this();
    this.index = index;
    this.brush = brush;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    CreateBrushIndirect tag = new CreateBrushIndirect(emf.readDWORD(), new LogBrush32(emf));
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(index);
    brush.write(emf);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  index: 0x" + Integer.toHexString(index) + "\n" + brush.toString();
  }

  public int getIndex()
  {
    return index;
  }

  public LogBrush32 getBrush()
  {
    return brush;
  }
}
// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * ExcludeClipRect TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ExcludeClipRect.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class ExcludeClipRect extends EMFTag
{

  private Rectangle bounds;

  ExcludeClipRect()
  {
    super(29, 1);
  }

  public ExcludeClipRect(Rectangle bounds)
  {
    this();
    this.bounds = bounds;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    ExcludeClipRect tag = new ExcludeClipRect(emf.readRECTL());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeRECTL(bounds);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  bounds: " + bounds;
  }
}

// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * IntersectClipRect TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: IntersectClipRect.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class IntersectClipRect extends EMFTag
{

  private Rectangle bounds;

  IntersectClipRect()
  {
    super(30, 1);
  }

  public IntersectClipRect(Rectangle bounds)
  {
    this();
    this.bounds = bounds;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    IntersectClipRect tag = new IntersectClipRect(emf.readRECTL());
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

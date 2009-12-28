// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * StrokePath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: StrokePath.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class StrokePath extends EMFTag
{

  private Rectangle bounds;

  StrokePath()
  {
    super(64, 1);
  }

  public StrokePath(Rectangle bounds)
  {
    this();
    this.bounds = bounds;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    StrokePath tag = new StrokePath(emf.readRECTL());
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

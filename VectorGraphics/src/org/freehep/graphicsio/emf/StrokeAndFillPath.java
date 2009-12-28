// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * StrokeAndFillPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: StrokeAndFillPath.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class StrokeAndFillPath extends EMFTag
{

  private Rectangle bounds;

  StrokeAndFillPath()
  {
    super(63, 1);
  }

  public StrokeAndFillPath(Rectangle bounds)
  {
    this();
    this.bounds = bounds;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    StrokeAndFillPath tag = new StrokeAndFillPath(emf.readRECTL());
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

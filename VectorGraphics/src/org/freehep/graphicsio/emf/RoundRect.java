// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * RoundRect TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: RoundRect.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class RoundRect extends EMFTag
{

  private Rectangle bounds;
  private Dimension corner;

  RoundRect()
  {
    super(44, 1);
  }

  public RoundRect(Rectangle bounds, Dimension corner)
  {
    this();
    this.bounds = bounds;
    this.corner = corner;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    RoundRect tag = new RoundRect(emf.readRECTL(), emf.readSIZEL());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeRECTL(bounds);
    emf.writeSIZEL(corner);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  bounds: " + bounds + "\n" + "  corner: " + corner;
  }
}

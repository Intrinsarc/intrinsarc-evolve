// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * Rectangle TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: EMFRectangle.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class EMFRectangle extends EMFTag
{

  private Rectangle bounds;

  EMFRectangle()
  {
    super(43, 1);
  }

  public EMFRectangle(Rectangle bounds)
  {
    this();
    this.bounds = bounds;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    EMFRectangle tag = new EMFRectangle(emf.readRECTL());
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

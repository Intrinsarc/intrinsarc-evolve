// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * SetPixelV TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetPixelV.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SetPixelV extends EMFTag
{

  private Point point;
  private Color color;

  SetPixelV()
  {
    super(15, 1);
  }

  public SetPixelV(Point point, Color color)
  {
    this();
    this.point = point;
    this.color = color;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetPixelV tag = new SetPixelV(emf.readPOINTL(), emf.readCOLORREF());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writePOINTL(point);
    emf.writeCOLORREF(color);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  point: " + point + "\n" + "  color: " + color;
  }
}

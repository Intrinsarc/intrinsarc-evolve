// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * SetTextColor TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetTextColor.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SetTextColor extends EMFTag
{

  private Color color;

  SetTextColor()
  {
    super(24, 1);
  }

  public SetTextColor(Color color)
  {
    this();
    this.color = color;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetTextColor tag = new SetTextColor(emf.readCOLORREF());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeCOLORREF(color);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  color: " + color;
  }
  public Color getColor()
  {
    return color;
  }
}

// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * SetBkColor TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetBkColor.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class SetBkColor extends EMFTag
{

  private Color color;

  SetBkColor()
  {
    super(25, 1);
  }

  public SetBkColor(Color color)
  {
    this();
    this.color = color;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    SetBkColor tag = new SetBkColor(emf.readCOLORREF());
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
}

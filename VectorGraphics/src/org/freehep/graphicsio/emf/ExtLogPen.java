// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * EMF ExtLogPen
 * 
 * @author Mark Donszelmann
 * @version $Id: ExtLogPen.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class ExtLogPen implements EMFConstants
{

  private int penStyle;
  private int width;
  private int brushStyle;
  private Color color;
  private int hatch;
  private int[] style;

  public ExtLogPen(int penStyle, int width, int brushStyle, Color color, int hatch, int[] style)
  {
    this.penStyle = penStyle;
    this.width = width;
    this.brushStyle = brushStyle;
    this.color = color;
    this.hatch = hatch;
    this.style = style;
  }

  ExtLogPen(EMFInputStream emf) throws IOException
  {
    penStyle = emf.readDWORD();
    width = emf.readDWORD();
    brushStyle = emf.readUINT();
    color = emf.readCOLORREF();
    hatch = emf.readULONG();
    int nStyle = emf.readDWORD();
    // it seems we always have to read one!
    if (nStyle == 0)
      emf.readDWORD();
    style = emf.readDWORD(nStyle);
  }

  public void write(EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(penStyle);
    emf.writeDWORD(width);
    emf.writeUINT(brushStyle);
    emf.writeCOLORREF(color);
    emf.writeULONG(hatch);
    emf.writeDWORD(style.length);
    // it seems we always have to write one!
    if (style.length == 0)
      emf.writeDWORD(0);
    emf.writeDWORD(style);
  }

  public String toString()
  {
    StringBuffer s = new StringBuffer();
    s.append("  ExtLogPen\n");
    s.append("    penStyle: " + Integer.toHexString(penStyle) + "\n");
    s.append("    width: " + width + "\n");
    s.append("    brushStyle: " + brushStyle + "\n");
    s.append("    color: " + color + "\n");
    s.append("    hatch: " + hatch + "\n");
    for (int i = 0; i < style.length; i++)
    {
      s.append("      style[" + i + "]: " + style[i] + "\n");
    }
    return s.toString();
  }
}

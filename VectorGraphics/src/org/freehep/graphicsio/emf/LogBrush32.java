// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;

/**
 * EMF LogBrush32
 * 
 * @author Mark Donszelmann
 * @version $Id: LogBrush32.java,v 1.1 2009-03-04 22:46:51 andrew Exp $ see
 *          http://msdn.microsoft.com/library/default.asp?url=/library/en-us/gdi/brushes_8yk2.asp
 */
public class LogBrush32 implements EMFConstants
{

  private int style;
  private Color color;
  private int hatch;

  public LogBrush32(int style, Color color, int hatch)
  {
    this.style = style;
    this.color = color;
    this.hatch = hatch;
  }

  LogBrush32(EMFInputStream emf) throws IOException
  {
    style = emf.readUINT();
    color = emf.readCOLORREF();
    hatch = emf.readULONG();
  }

  public void write(EMFOutputStream emf) throws IOException
  {
    emf.writeUINT(style);
    emf.writeCOLORREF(color);
    emf.writeULONG(hatch);
  }

  public String toString()
  {
    return "  LogBrush32\n" + "    style: " + style + "\n" + "    color: " + color + "\n" + "    hatch: " + hatch;
  }

  public int getStyle()
  {
    return style;
  }
  public Color getColor()
  {
    return color;
  }
  public int getHatch()
  {
    return hatch;
  }
}

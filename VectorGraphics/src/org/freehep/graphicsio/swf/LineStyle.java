// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.awt.*;
import java.io.*;

/**
 * SWF LineStyle.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: LineStyle.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class LineStyle
{

  private int width, endWidth;
  private Color color, endColor;

  public LineStyle(int width, Color color)
  {
    this.width = width;
    this.color = color;
  }

  public LineStyle(int width, int endWidth, Color color, Color endColor)
  {
    this(width, color);
    this.endWidth = endWidth;
    this.endColor = endColor;
  }

  public LineStyle(SWFInputStream input, boolean isMorphStyle, boolean hasAlpha) throws IOException
  {

    if (!isMorphStyle)
    {
      width = input.readUnsignedShort();
      color = input.readColor(hasAlpha);
    }
    else
    {
      width = input.readUnsignedShort();
      endWidth = input.readUnsignedShort();
      color = input.readColor(true);
      endColor = input.readColor(true);
    }
  }

  public void write(SWFOutputStream swf, boolean hasAlpha) throws IOException
  {

    swf.writeUnsignedShort(width);
    if (endColor != null)
      swf.writeUnsignedShort(endWidth);
    swf.writeColor(color, hasAlpha || (endColor != null));
    if (endColor != null)
      swf.writeColor(endColor, true);
  }

  public String toString()
  {
    StringBuffer s = new StringBuffer("LineStyle " + width + ", " + color);
    if (endColor != null)
      s.append("; " + endWidth + ", " + endColor);
    return s.toString();
  }
}

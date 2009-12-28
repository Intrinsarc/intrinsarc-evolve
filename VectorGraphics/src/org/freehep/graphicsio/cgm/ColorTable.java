// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.*;
import java.io.*;

/**
 * ColorTable TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ColorTable.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class ColorTable extends CGMTag
{

  private int index;
  private Color[] colors;

  public ColorTable()
  {
    super(5, 34, 1);
  }

  public ColorTable(int index, Color[] colors)
  {
    this();
    this.index = index;
    this.colors = colors;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeColorIndex(index);
    for (int i = 0; i < colors.length; i++)
    {
      cgm.writeColorDirect(colors[i]);
    }
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("COLRTABLE ");
    cgm.writeColorIndex(index);
    cgm.println(", ");
    cgm.indent();
    for (int i = 0; i < colors.length; i++)
    {
      cgm.writeColorDirect(colors[i]);
      cgm.println();
    }
    cgm.outdent();
  }
}

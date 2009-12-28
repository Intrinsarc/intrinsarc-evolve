// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.*;
import java.io.*;

/**
 * TextColor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: TextColor.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class TextColor extends CGMTag
{

  private Color color;

  public TextColor()
  {
    super(5, 14, 1);
  }

  public TextColor(Color color)
  {
    this();
    this.color = color;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeColor(color);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("TEXTCOLR ");
    cgm.writeColor(color);
  }
}

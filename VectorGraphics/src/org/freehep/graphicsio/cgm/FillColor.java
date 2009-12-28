// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.*;
import java.io.*;

/**
 * FillColor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: FillColor.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class FillColor extends CGMTag
{

  private Color color;

  public FillColor()
  {
    super(5, 23, 1);
  }

  public FillColor(Color color)
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
    cgm.print("FILLCOLR ");
    cgm.writeColor(color);
  }
}

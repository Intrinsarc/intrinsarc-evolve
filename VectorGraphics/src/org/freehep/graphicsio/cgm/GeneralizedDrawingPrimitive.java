// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;

/**
 * GeneralizedDrawingPrimitive TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: GeneralizedDrawingPrimitive.java,v 1.1 2006-07-03 13:32:41
 *          amcveigh Exp $
 */
public class GeneralizedDrawingPrimitive extends CGMTag
{

  private int gdp;
  private Point2D[] p;
  private byte[] data;

  public GeneralizedDrawingPrimitive()
  {
    super(4, 10, 1);
  }

  public GeneralizedDrawingPrimitive(int gdp, Point2D[] p, byte[] data)
  {
    this();
    this.gdp = gdp;
    this.p = p;
    this.data = data;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeInteger(gdp);
    cgm.writeInteger(p.length);
    for (int i = 0; i < p.length; i++)
    {
      cgm.writePoint(p[i]);
    }
    cgm.writeData(data);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("GDP ");
    cgm.indent();
    cgm.writeInteger(gdp);
    cgm.println();
    for (int i = 0; i < p.length; i++)
    {
      cgm.writePoint(p[i]);
      cgm.print(", ");
    }
    cgm.println();
    cgm.writeData(data);
    cgm.println();
    cgm.outdent();
  }
}

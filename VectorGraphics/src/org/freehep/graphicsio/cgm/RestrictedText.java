// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;

/**
 * RestrictedText TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: RestrictedText.java,v 1.1 2009-03-04 22:46:47 andrew Exp $
 */
public class RestrictedText extends Text
{

  protected double deltaWidth, deltaHeight;

  public RestrictedText()
  {
    super(4, 5, 1);
  }

  public RestrictedText(double deltaWidth, double deltaHeight, Point2D p, String text)
  {
    this();
    this.deltaWidth = deltaWidth;
    this.deltaHeight = deltaHeight;
    this.p = p;
    this.text = text;
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writeVDC(deltaWidth);
    cgm.writeVDC(deltaHeight);
    super.write(tagID, cgm);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("RESTRTEXT ");
    cgm.writeVDC(deltaWidth);
    cgm.print(", ");
    cgm.writeVDC(deltaHeight);
    cgm.print(", ");
    cgm.writePoint(p);
    cgm.print(", ");
    writeTextPiece(cgm);
  }
}

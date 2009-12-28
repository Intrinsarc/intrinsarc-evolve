// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;

/**
 * Text TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Text.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class Text extends AppendText
{

  protected Point2D p;

  public Text()
  {
    super(4, 4, 1);
  }

  public Text(Point2D p, String text)
  {
    this();
    this.p = p;
    this.text = text;
  }

  protected Text(int elementClass, int elementID, int version)
  {
    super(elementClass, elementID, version);
  }

  public void write(int tagID, CGMOutputStream cgm) throws IOException
  {
    cgm.writePoint(p);
    super.write(tagID, cgm);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("TEXT ");
    cgm.writePoint(p);
    cgm.print(", ");
    writeTextPiece(cgm);
  }
}

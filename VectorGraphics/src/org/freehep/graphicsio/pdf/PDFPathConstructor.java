// Copyright 2002 FreeHEP.
package org.freehep.graphicsio.pdf;

import java.io.*;

import org.freehep.graphicsio.*;

/**
 * @author Mark Donszelmann
 * @version $Id: PDFPathConstructor.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class PDFPathConstructor extends QuadToCubicPathConstructor
{
  private PDFStream stream;

  public PDFPathConstructor(PDFStream stream)
  {
    super();
    this.stream = stream;
  }

  public void move(double x, double y) throws IOException
  {
    stream.move(x, y);
    super.move(x, y);
  }

  public void line(double x, double y) throws IOException
  {
    stream.line(x, y);
    super.line(x, y);
  }


  public void cubic(double x1, double y1, double x2, double y2, double x3, double y3) throws IOException
  {
    stream.cubic(x1, y1, x2, y2, x3, y3);
    super.cubic(x1, y1, x2, y2, x3, y3);
  }

  public void closePath() throws IOException
  {
    stream.closePath();
    super.closePath();
  }
}

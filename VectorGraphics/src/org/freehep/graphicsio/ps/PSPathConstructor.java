// Copyright 2001 FreeHEP.
package org.freehep.graphicsio.ps;

import java.io.*;

import org.freehep.graphicsio.*;

/**
 * @author Mark Donszelmann
 * @version $Id: PSPathConstructor.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class PSPathConstructor extends QuadToCubicPathConstructor
{
  private PrintStream os;
  private boolean intPrecision;
  private String moveto, lineto, curveto, close;

  public PSPathConstructor(PrintStream os, boolean useProlog, boolean intPrecision)
  {
    super();
    this.os = os;
    this.intPrecision = intPrecision;
    if (useProlog)
    {
      moveto = "m";
      lineto = "l";
      curveto = "c";
      close = "h";
    }
    else
    {
      moveto = "moveto";
      lineto = "lineto";
      curveto = "curveto";
      close = "closepath";
    }
  }

  public void move(double x, double y) throws IOException
  {
    os.println(fixedPrecision(x) + " " + fixedPrecision(y) + " " + moveto);
    super.move(x, y);
  }

  public void line(double x, double y) throws IOException
  {
    os.println(fixedPrecision(x) + " " + fixedPrecision(y) + " " + lineto);
    super.line(x, y);
  }


  public void cubic(double x1, double y1, double x2, double y2, double x3, double y3) throws IOException
  {
    os.println(fixedPrecision(x1) + " " + fixedPrecision(y1) + " " + fixedPrecision(x2) + " " + fixedPrecision(y2)
        + " " + fixedPrecision(x3) + " " + fixedPrecision(y3) + " " + " " + curveto);
    super.cubic(x1, y1, x2, y2, x3, y3);
  }

  public void closePath() throws IOException
  {
    os.println(close);
    super.closePath();
  }

  protected String fixedPrecision(double d)
  {
    if (intPrecision)
    {
      return Integer.toString((int) d);
    }
    else
    {
      String dbl = Double.toString(d);
      int index = dbl.lastIndexOf(".");
      index = Math.min(index + 7, dbl.length());
      return dbl.substring(0, index);
    }
  }
}

// Copyright 2001 FreeHEP.
package org.freehep.graphicsio;

import java.awt.geom.*;
import java.io.*;
import java.util.*;

/**
 * @author Mark Donszelmann
 * @version $Id: PolylinePathConstructor.java,v 1.1 2006-07-03 13:33:09 amcveigh
 *          Exp $
 */
public abstract class PolylinePathConstructor extends CubicToLinePathConstructor
{
  private Vector polyline;

  protected boolean closed;
  protected boolean fill;

  public PolylinePathConstructor(boolean fill)
  {
    super();
    closed = false;
    this.fill = fill;
  }

  public void move(double x, double y) throws IOException
  {
    writePolyline();
    polyline = new Vector();
    polyline.add(new Point2D.Double(x, y));
    super.move(x, y);
  }

  public void line(double x, double y) throws IOException
  {
    // System.out.println("Line "+x+" "+y);
    polyline.add(new Point2D.Double(x, y));
    super.line(x, y);
  }

  public void closePath() throws IOException
  {
    closed = true;
    writePolyline();
    super.closePath();
  }

  public void writePolyline() throws IOException
  {
    if (polyline != null)
      writePolyline(polyline);
    closed = false;
    polyline = null;
  }

  protected abstract void writePolyline(Vector polyline) throws IOException;
}

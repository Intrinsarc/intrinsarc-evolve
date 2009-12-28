// Copyright 2001 FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.freehep.graphicsio.*;
import org.freehep.util.io.*;

/**
 * @author Mark Donszelmann
 * @version $Id: CGMPathConstructor.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class CGMPathConstructor extends PolylinePathConstructor
{
  private TaggedOutput os;
  private AffineTransform matrix;

  public CGMPathConstructor(TaggedOutput os, boolean fill, AffineTransform matrix)
  {
    super(fill);
    this.os = os;
    this.matrix = matrix;
  }

  protected void writePolyline(Vector polyline) throws IOException
  {
    int n = polyline.size();
    Point2D[] src = new Point2D[n];
    polyline.copyInto(src);
    Point2D[] dst = new Point2D[n];
    matrix.transform(src, 0, dst, 0, n);
    if (fill)
    {
      os.writeTag(new Polygon(dst));
    }
    else
      if (closed)
      {
        os.writeTag(new EdgeVisibility(true));
        os.writeTag(new InteriorStyle(InteriorStyle.HOLLOW));
        os.writeTag(new Polygon(dst));
        os.writeTag(new InteriorStyle(InteriorStyle.SOLID));
        os.writeTag(new EdgeVisibility(false));
      }
      else
      {
        os.writeTag(new Polyline(dst));
      }
  }

}

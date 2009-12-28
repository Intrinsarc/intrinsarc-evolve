// Copyright 2001 freehep
package org.freehep.graphics2d;

import java.awt.*;
import java.awt.geom.*;

/**
 * This class can be used to create and render simple shapes quickly and without
 * memory allocation. A common point array is used for all created shapes. The
 * factory methods don't return a new shape, but set the object to the selected
 * shape. Hence, the class is not thread-safe and only one PathIterator can be
 * used at the same time.<br>
 * 
 * @author Simon Fischer
 * @version $Id: SymbolShape.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class SymbolShape implements Shape
{

  private static final double SQRT_2 = Math.sqrt(2.);
  private static final double SQRT_3 = Math.sqrt(3.);

  private class ArrayPathIterator implements PathIterator
  {

    private int currentPoint = 0;
    private double[] points;
    private int[] type;
    private int numberOfPoints;

    private ArrayPathIterator(double[] points, int[] type)
    {
      this.points = points;
      this.type = type;
    }

    public boolean isDone()
    {
      return currentPoint >= numberOfPoints;
    }

    public void next()
    {
      currentPoint++;
    }

    public int currentSegment(double[] coords)
    {
      coords[0] = points[2 * currentPoint];
      coords[1] = points[2 * currentPoint + 1];
      return type[currentPoint];
    }

    public int currentSegment(float[] coords)
    {
      coords[0] = (float) points[2 * currentPoint];
      coords[1] = (float) points[2 * currentPoint + 1];
      return type[currentPoint];
    }

    public int getWindingRule()
    {
      return PathIterator.WIND_NON_ZERO;
    }

    private void reset()
    {
      currentPoint = 0;
    }

    private void done()
    {
      currentPoint = numberOfPoints;
    }
  }

  private double points[];
  private int type[];
  private int numberOfPoints;
  private ArrayPathIterator pathIterator;
  private Rectangle2D.Double bounds;

  public SymbolShape()
  {
    ensureNumberOfPoints(10);
    type[0] = PathIterator.SEG_MOVETO;
    bounds = new Rectangle2D.Double(0, 0, 0, 0);
    for (int i = 1; i < type.length; i++)
    {
      type[i] = PathIterator.SEG_LINETO;
    }
    this.pathIterator = new ArrayPathIterator(points, type);
  }

  public boolean contains(double x, double y)
  {
    return bounds.contains(x, y);
  }

  public boolean contains(double x, double y, double w, double h)
  {
    return contains(x, y) && contains(x + w, y) && contains(x, y + h) && contains(x + w, y + h);
  }

  public boolean contains(Point2D p)
  {
    return contains(p.getX(), p.getY());
  }

  public boolean contains(Rectangle2D r)
  {
    return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
  }

  /** Returns true, if at least one of the points is contained by the shape. */
  public boolean intersects(double x, double y, double w, double h)
  {
    return contains(x, y) || contains(x + w, y) || contains(x, y + h) || contains(x + w, y + h);
  }

  public boolean intersects(Rectangle2D r)
  {
    return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
  }

  public PathIterator getPathIterator(AffineTransform at, double flatness)
  {
    return getPathIterator(at);
  }

  public Rectangle2D getBounds2D()
  {
    return bounds;
  }

  public Rectangle getBounds()
  {
    Rectangle2D bounds = getBounds2D();
    return new Rectangle((int) Math.floor(bounds.getX()), (int) Math.floor(bounds.getY()), (int) Math.ceil(bounds
        .getWidth()), (int) Math.ceil(bounds.getHeight()));
  }

  public PathIterator getPathIterator(AffineTransform t)
  {
    if (t != null)
    {
      t.transform(points, 0, pathIterator.points, 0, points.length / 2);
    }
    // if (!pathIterator.isDone()) {
    // System.err.println("SymbolShape: concurrent PathIterator requested!");
    // }
    pathIterator.reset();
    return pathIterator;
  }

  // -------------------- factory methods --------------------

  private void createNew(int n)
  {
    if (!pathIterator.isDone())
    {
      System.err.println("SymbolShape: concurrent modification!");
    }
    ensureNumberOfPoints(n);
    pathIterator.numberOfPoints = numberOfPoints = n;
    pathIterator.done();
  }

  /**
   * Type must be one of the symbols defined in VectorGraphicsConstants except
   * TYPE_CIRCLE.
   * 
   * @see org.freehep.graphics2d.VectorGraphicsConstants
   */
  public void create(int type, double x, double y, double size)
  {
    switch (type)
    {
      case VectorGraphicsConstants.SYMBOL_VLINE :
        createHLine(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_HLINE :
        createVLine(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_PLUS :
        createPlus(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_CROSS :
        createCross(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_STAR :
        createStar(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_BOX :
        createBox(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_UP_TRIANGLE :
        createUpTriangle(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_DN_TRIANGLE :
        createDownTriangle(x, y, size);
        break;
      case VectorGraphicsConstants.SYMBOL_DIAMOND :
        createDiamond(x, y, size);
        break;
    }
    setBounds(x, y, size);
  }

  public void createHLine(double x, double y, double size)
  {
    createNew(2);

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x - size / 2;
    points[1] = y;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x + size / 2;
    points[3] = y;
  }

  public void createVLine(double x, double y, double size)
  {
    ensureNumberOfPoints(2);
    pathIterator.numberOfPoints = numberOfPoints = 2;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x;
    points[1] = y - size / 2;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x;
    points[3] = y + size / 2;
  }

  public void createPlus(double x, double y, double size)
  {
    ensureNumberOfPoints(4);
    pathIterator.numberOfPoints = numberOfPoints = 4;
    double length = size / 2.;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x + length;
    points[1] = y;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x - length;
    points[3] = y;

    type[2] = PathIterator.SEG_MOVETO;
    points[4] = x;
    points[5] = y + length;

    type[3] = PathIterator.SEG_LINETO;
    points[6] = x;
    points[7] = y - length;
  }

  public void createCross(double x, double y, double size)
  {
    ensureNumberOfPoints(4);
    pathIterator.numberOfPoints = numberOfPoints = 4;
    double side = size / 2. / SQRT_2;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x - side;
    points[1] = y - side;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x + side;
    points[3] = y + side;

    type[2] = PathIterator.SEG_MOVETO;
    points[4] = x + side;
    points[5] = y - side;

    type[3] = PathIterator.SEG_LINETO;
    points[6] = x - side;
    points[7] = y + side;
  }

  public void createStar(double x, double y, double size)
  {
    ensureNumberOfPoints(8);
    pathIterator.numberOfPoints = numberOfPoints = 8;

    double delta = size / 2.;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x;
    points[1] = y - delta;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x;
    points[3] = y + delta;

    type[2] = PathIterator.SEG_MOVETO;
    points[4] = x - delta;
    points[5] = y;

    type[3] = PathIterator.SEG_LINETO;
    points[6] = x + delta;
    points[7] = y;

    delta = size / 2. / SQRT_2;

    type[4] = PathIterator.SEG_MOVETO;
    points[8] = x - delta;
    points[9] = y - delta;

    type[5] = PathIterator.SEG_LINETO;
    points[10] = x + delta;
    points[11] = y + delta;

    type[6] = PathIterator.SEG_MOVETO;
    points[12] = x + delta;
    points[13] = y - delta;

    type[7] = PathIterator.SEG_LINETO;
    points[14] = x - delta;
    points[15] = y + delta;
  }

  public void createUpTriangle(double x, double y, double size)
  {
    ensureNumberOfPoints(4);
    pathIterator.numberOfPoints = numberOfPoints = 4;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x;
    points[1] = y - size / SQRT_3;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x - size / 2.;
    points[3] = y + (-size / SQRT_3 + size * SQRT_3 / 2.);

    type[2] = PathIterator.SEG_LINETO;
    points[4] = x + size / 2.;
    points[5] = y + (-size / SQRT_3 + size * SQRT_3 / 2.);

    type[3] = PathIterator.SEG_CLOSE;
  }

  public void createDownTriangle(double x, double y, double size)
  {
    ensureNumberOfPoints(4);
    pathIterator.numberOfPoints = numberOfPoints = 4;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x;
    points[1] = y + size / SQRT_3;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x - size / 2.;
    points[3] = y - (-size / SQRT_3 + size * SQRT_3 / 2.);

    type[2] = PathIterator.SEG_LINETO;
    points[4] = x + size / 2.;
    points[5] = y - (-size / SQRT_3 + size * SQRT_3 / 2.);

    type[3] = PathIterator.SEG_CLOSE;
  }

  public void createDiamond(double x, double y, double size)
  {
    ensureNumberOfPoints(5);
    pathIterator.numberOfPoints = numberOfPoints = 5;
    double length = size / 2.;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x + length;
    points[1] = y;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x;
    points[3] = y + length;

    type[2] = PathIterator.SEG_LINETO;
    points[4] = x - length;
    points[5] = y;

    type[3] = PathIterator.SEG_LINETO;
    points[6] = x;
    points[7] = y - length;

    type[4] = PathIterator.SEG_CLOSE;
  }

  public void createBox(double x, double y, double size)
  {
    ensureNumberOfPoints(5);
    pathIterator.numberOfPoints = numberOfPoints = 5;
    double side = size / SQRT_2 / 2;

    type[0] = PathIterator.SEG_MOVETO;
    points[0] = x - side;
    points[1] = y - side;

    type[1] = PathIterator.SEG_LINETO;
    points[2] = x + side;
    points[3] = y - side;

    type[2] = PathIterator.SEG_LINETO;
    points[4] = x + side;
    points[5] = y + side;

    type[3] = PathIterator.SEG_LINETO;
    points[6] = x - side;
    points[7] = y + side;

    type[4] = PathIterator.SEG_CLOSE;
  }

  private void setBounds(double x, double y, double size)
  {
    bounds.x = x - size / 2;
    bounds.y = y - size / 2;
    bounds.width = bounds.height = size;
  }

  private void ensureNumberOfPoints(int n)
  {
    if ((type == null) || (type.length < n))
    {
      this.points = new double[n * 2];
      this.type = new int[n];
    }
  }
}

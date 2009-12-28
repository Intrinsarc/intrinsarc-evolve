// Copyright 2001 freehep
package org.freehep.graphicsio;

import java.awt.*;
import java.io.*;

/**
 * Interface for objects that are capable of constructing paths. Path painting
 * (stroking or filling) is not included.
 * 
 * @author Simon Fischer
 * @version $Id: PathConstructor.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public interface PathConstructor
{

  /**
   * Makes (x,y) the current point.
   */
  public void move(double x, double y) throws IOException;

  /**
   * Draws a line from the current point to (x,y) and make (x,y) the current
   * point.
   */
  public void line(double x, double y) throws IOException;

  /**
   * Draws a quadratic bezier curve from the current point to (x2, y2) using the
   * control point (x1, y1) and make (x2, y2) the current point.
   */
  public void quad(double x1, double y1, double x2, double y2) throws IOException;

  /**
   * Draws a cubic bezier curve from the current point to (x3, y3) using the
   * control points (x1, y1) and (x2, y2) and make (x3, y3) the current point.
   */
  public void cubic(double x1, double y1, double x2, double y2, double x3, double y3) throws IOException;

  /**
   * Closes the path by drawing a straight line to the last point which was
   * argument to move.
   */
  public void closePath() throws IOException;

  /**
   * Flushes any cached info to the output file. The path is complete at this
   * point.
   */
  public void flush() throws IOException;

  /**
   * Adds the <i>points</i> of the shape using path <i>construction</i>
   * operators. The path is neither stroked nor filled.
   * 
   * @return true if even-odd winding rule should be used, false if non-zero
   *         winding rule should be used.
   */
  public boolean addPath(Shape s) throws IOException;
}

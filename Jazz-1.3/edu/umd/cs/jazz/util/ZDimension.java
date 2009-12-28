/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.io.*;
import java.awt.geom.*;

/**
 * Implemenation of Dimension2D that uses doubles.
 *
 * @author: Jesse Grosjean
 */
public class ZDimension extends Dimension2D implements Serializable {

    private double fWidth;
    private double fHeight;

    /**
     * ZDimension constructor comment.
     */
    public ZDimension() {
        super();
    }

    /**
     * ZDimension constructor comment.
     */
    public ZDimension(double width, double height) {
        super();
        fWidth = width;
        fHeight = height;
    }

    /**
     * Returns the height of this <code>Dimension</code> in double
     * precision.
     * @return the height of this <code>Dimension</code>.
     */
    public double getHeight() {
        return fHeight;
    }

    /**
     * Returns the width of this <code>Dimension</code> in double
     * precision.
     * @return the width of this <code>Dimension</code>.
     */
    public double getWidth() {
        return fWidth;
    }

    /**
     * Sets the size of this <code>Dimension</code> object to the
     * specified width and height.
     * This method is included for completeness, to parallel the
     * {@link java.awt.Component#getSize getSize} method of
     * {@link java.awt.Component}.
     * @param width  the new width for the <code>Dimension</code>
     * object
     * @param height  the new height for the <code>Dimension</code>
     * object
     */
    public void setSize(double width, double height) {
        fWidth = width;
        fHeight = height;
    }
}
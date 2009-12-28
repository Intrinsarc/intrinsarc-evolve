/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.Stroke;

/**
 * <b>ZStroke</b> represents the "stroke" attribute of a visual component.
 * Any visual components that support a stroke should implement this interface.
 * A stroke describes the rendering details of drawing a path - including
 * details such as pen width, join style, cap style, and dashes.
 *
 * @author  Benjamin B. Bederson
 */
public interface ZStroke extends ZAppearance {

    /**
     * Get the width of the pen used to draw the visual component.
     * If the pen width is absolute (independent of magnification),
     * then this returns 0.
     * @return the pen width.
     * @see #getAbsPenWidth
     */
    public double getPenWidth();

    /**
     * Set the width of the pen used to draw the visual component.
     * If the pen width is set here, then the stroke is set to solid (un-dashed),
     * with a "butt" cap style, and a "bevel" join style.  The pen width
     * will be dependent on the camera magnification.
     * @param width the pen width.
     * @see #setAbsPenWidth
     */
    public void setPenWidth(double width);

    /**
     * Get the absolute width of the pen used to draw the visual component.
     * If the pen width is not absolute (dependent on magnification),
     * then this returns 0.
     * @return the pen width.
     * @see #getPenWidth
     */
    public double getAbsPenWidth();

    /**
     * Set the absolute width of the pen used to draw the visual component.
     * If the pen width is set here, then the stroke is set to solid (un-dashed),
     * with a "butt" cap style, and a "bevel" join style.  The pen width
     * will be independent on the camera magnification.
     * @param width the pen width.
     * @see #setPenWidth
     */
    public void setAbsPenWidth(double width);

    /**
     * Get the stroke used to draw the visual component.
     * @return the stroke.
     */
    public Stroke getStroke();

    /**
     * Set the stroke used to draw the visual component.
     * @param stroke the stroke.
     */
    public void setStroke(Stroke stroke);
}

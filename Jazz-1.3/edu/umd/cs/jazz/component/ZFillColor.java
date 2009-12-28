/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.Color;

/**
 * <b>ZFillColor</b> represents the "fill color" attribute of a visual component.
 * Any visual components that support a fill color should implement this interface.
 *
 * @author  Benjamin B. Bederson
 */
public interface ZFillColor extends ZAppearance {

    /**
     * Set the fill color of this visual component.
     * @param color the fill color, or null if none.
     */
    public void setFillColor(Color color);

    /**
     * Get the fill color of this visual component.
     * @return the fill color, or null if none.
     */
    public Color getFillColor();
}

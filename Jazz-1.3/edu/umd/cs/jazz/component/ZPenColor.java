/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.Color;

/**
 * <b>ZPenColor</b> represents the "pen color" attribute of a visual component.
 * Any visual components that support a pen color should implement this interface.
 *
 * @author  Benjamin B. Bederson
 */
public interface ZPenColor extends ZAppearance {

    /**
     * Set the pen color of this visual component.
     * @param color the pen color, or null if none.
     */
    public void setPenColor(Color color);

    /**
     * Get the pen color of this visual component.
     * @return the pen color, or null if none.
     */
    public Color getPenColor();
}

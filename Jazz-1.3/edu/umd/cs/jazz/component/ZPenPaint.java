/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.Color;
import java.awt.*;

/**
 * <b>ZPenPaint</b> represents the "pen paint" attribute of a visual component.
 * Any visual components that support a pen paint should implement this interface.
 *
 * @author Jesse Grosjean
 */
public interface ZPenPaint extends ZAppearance {

    /**
     * Get the pen paint of this visual component.
     * @return the pen paint, or null if none.
     */
    public Paint getPenPaint();

    /**
     * Set the pen paint of this visual component.
     * @param aPaint the pen paint, or null if none.
     */
    public void setPenPaint(Paint aPaint);
}
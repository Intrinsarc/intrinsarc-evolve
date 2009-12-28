/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.Color;
import java.awt.*;

/**
 * <b>ZFillPaint</b> represents the "fill paint" attribute of a visual component.
 * Any visual components that support a fill paint should implement this interface.
 *
 * @author Jesse Grosjean
 */
public interface ZFillPaint extends ZAppearance {

    /**
     * Get the fill paint of this visual component.
     * @return the fill paint, or null if none.
     */
    public Paint getFillPaint();

    /**
     * Set the fill paint of this visual component.
     * @param aPaint the fill paint, or null if none.
     */
    public void setFillPaint(Paint aPaint);
}
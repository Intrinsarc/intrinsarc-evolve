/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.awt.*;
import java.awt.geom.*;

/**
 * The interface an application can implement to control scrolling in a
 * ZScrollPane->ZViewport->ZCanvas component hierarchy.
 * @see ZDefaultScrollDirector
 * @author Lance Good
 */
public interface ZScrollDirector {

    /**
     * Installs the scroll director
     * @param viewPort The viewport on which this director directs
     * @param view The ZCanvas that the viewport looks at
     */
    public void install(ZViewport viewport, ZCanvas view);

    /**
     * Uninstall the scroll director
     */
    public void unInstall();

    /**
     * Get the View position given the specified camera bounds
     * @param viewBounds The bounds for which the view position will be computed
     * @return The view position
     */
    public Point getViewPosition(Rectangle2D r);


    /**
     * Set the view position
     * @param x The new x position
     * @param y The new y position
     */
    public void setViewPosition(double x, double y);

    /**
     * Get the size of the view based on the specified camera bounds
     * @param viewBounds The view bounds for which the view size will be computed
     * @return The view size
     */
    public Dimension getViewSize(Rectangle2D r);
}

/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.component.ZRectangle;

/**
 * <b>ZSelectionScaleHandler</b> is a selection handler for use with a
 * <b>ZSelectionManager</b>.  <code>ZSelectionScaleHandler</code> allows
 * the user to scale the current selection using the PgUp / PgDn keys.
 *
 * @see edu.umd.cs.jazz.ZSelectionManager
 * @see ZCompositeSelectionHandler
 *
 * @author Antony Courtney, Yale University
 * @author Lance Good, University of Maryland
 * @author Benjamin Bederson, University of Maryland
 * @author Jesse Grosjean, University of Maryland
 */
public class ZSelectionScaleHandler extends ZFilteredEventHandler {

    private double fScaleFactor = 1.1f;

    /**
     * Constructs a new ZSelectionScaleHandler.
     *
     * @param aFilteredKeyEventSouce    the source for filtered KeyEvents. See the ZFilteredEventHandler class comment
     *                                  to customize this behavior.
     */
    public ZSelectionScaleHandler(ZCanvas aFilteredKeyEventSouce) {
        super(null, aFilteredKeyEventSouce);
    }

    /**
     * @deprecated As of Jazz version 1.2,
     * use <code>ZSelectionScaleHandler(ZCanvas aFilteredKeyEventSouce)</code> instead.
     */
    public ZSelectionScaleHandler(ZCamera aIgnoredCamera, ZCanvas aFilteredKeyEventSouce) {
        this(aFilteredKeyEventSouce);
    }

    /**
     * Get the scale factor for incremental scales.
     */
    public double getScaleFactor() {
        return fScaleFactor;
    }

    /**
     * Set the scale factor for incremental scales.
     */
    public void setScaleFactor(double aScaleFactor) {
        fScaleFactor = aScaleFactor;
    }

    /**
     * Invoked when a key is pressed on the key event souce
     * and the event filter accepts the event.
     *
     * @param e the filtered key pressed event accepted by the event filter.
     */
    protected void filteredKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_PAGE_UP:
                scaleSelectionUp();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                scaleSelectionDown();
                break;
        }
    }

    /**
     * Scale the selection up by the current scale factor.
     */
    protected void scaleSelectionUp() {
        scaleSelectionBy(getScaleFactor());
    }

    /**
     * Scale the selection down by the current scale factor.
     */
    protected void scaleSelectionDown() {
        scaleSelectionBy(1.0 / getScaleFactor());
    }

    /**
     * Scale the selection by the given scale factor around
     * the centerpoint of the bounds for the entire selection.
     */
    protected void scaleSelectionBy(double aScaleFactor) {
        ZBounds totalSelectionBounds = new ZBounds();
        Collection currentSelection = getCurrentSelection();

        // Get the total bounds of the selection.
        Iterator i = currentSelection.iterator();
        while (i.hasNext()) {
            ZNode each = (ZNode) i.next();
            totalSelectionBounds.add(each.getGlobalBounds());
        }

        // Then, scale them around a common point
        Point2D selectionCenterPoint = totalSelectionBounds.getCenter2D();
        Point2D temp = new Point2D.Double();

        i = currentSelection.iterator();
        while (i.hasNext()) {
            ZNode each = (ZNode) i.next();

            // Convert point and scale to node's coordinate system
            temp.setLocation(selectionCenterPoint);
            each.globalToLocal(temp);

            // Then, scale in node's local coordinate system
            each.editor().getTransformGroup().scale(aScaleFactor,
                                                    temp.getX(),
                                                    temp.getY());
        }
    }
}
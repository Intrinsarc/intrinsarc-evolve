/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.geom.*;
import java.awt.event.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZPanEventHandler</b> provides event handlers for basic panning
 * of a Jazz camera with the left mouse.  The interaction is that
 * clicking and dragging the mouse translates the camera so that
 * the point on the surface stays under the mouse.
 *
 * <P>
 * This event handler will properly pan within internal cameras if
 * the clicked within an internal camera.
 *
 * @author  Benjamin B. Bederson
 * @author  Jesse Grosjean
 */
public class ZPanEventHandler extends ZDragSequenceEventHandler {

    /**
     * The default distance that the mouse needs to be dragged for a
     * panning action to start. This value can be customized on a per instance
     * basis by using <code>setMinDragStartDistance</code>.
     */
    static public int DEFAULT_MIN_PAN_START_DISTANCE = 5;

    /**
     * Constructs a new ZPanEventHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the ZFilteredEventHandler class comment to customize this behavior.
     */
    public ZPanEventHandler(ZSceneGraphObject aFilteredMouseEventSource) {
        super(aFilteredMouseEventSource);
        setMinDragStartDistance(DEFAULT_MIN_PAN_START_DISTANCE);
                                    // Make is so the we do not start panning until
                                    // the mouse has been dragged 5 pixels.
    }

    /**
     * @deprecated As of Jazz version 1.2,
     * use <code>ZPanEventHandler(ZSceneGraphObject aEventSource)</code> instead.
     */
    public ZPanEventHandler(ZSceneGraphObject aEventSource, ZCamera aIgnoredCamera) {
        this(aEventSource);
    }

    /**
     * Return the event filter. If the filter is null then create
     * the default event filter for ZPanEventHandler, it filters out
     * events that do not have BUTTON1 pressed.
     *
     * @return the filter that is currently in effect.
     */
    public ZMouseFilter getMouseFilter() {
        if (fMouseFilter == null) {
            fMouseFilter = new ZMouseFilter(InputEvent.BUTTON1_MASK);
        }
        return fMouseFilter;
    }

    /**
     * When the dragging action occurs invoke <code>pan</code>.
     *
     * @param e the event causing the drag.
     * @param aScreenDelta the change in mouse position between the current and last mouse events.
     */
    protected void dragInScreenCoords(ZMouseEvent e, Dimension2D aScreenDelta) {
        pan(e, aScreenDelta);
    }

    /**
     * Pan the interaction camera.
     *
     * @param e the event causing the pan.
     * @param aScreenDelta the change in mouse position between the current and last mouse events.
     */
    public void pan(ZMouseEvent e, Dimension2D aScreenDelta) {
        ZSceneGraphPath aPath = e.getPath();
        aPath.screenToCamera(aScreenDelta, getInteractionCamera());
        getInteractionCamera().cameraToLocal(aScreenDelta, null);
        getInteractionCamera().translate(aScreenDelta.getWidth(), aScreenDelta.getHeight());
    }

    /**
     * @deprecated As of Jazz version 1.2,
     * use <code>ZDragSequenceEventHandler.isDragging()</code> instead.
     */
    public boolean isMoved() {
        return isDragging();
    }
}
/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZoomToEventhandler</b> provides the ability to zoom the interaction
 * camera to a target node on the drawing surface.
 * <P>
 * On filtered mouse pressed with the right mouse button this event handler will zoom to
 * the node that was picked by the mouse press. If no node was picked then it will
 * zoom to its default zoom to node that is specified when the event handler is created
 * or by <code>setDefaultZoomToNode</code>. The event handler uses two methods to zoom into a target
 * node. The method to use is set by <code>setZoomToScale</code> and <code>setZoomToScreen</code>.
 *
 * @author  Jesse Grosjean
 */
public class ZoomToEventHandler extends ZFilteredEventHandler {

    private static final double DEFAULT_ZOOM_SCALE = 1.0;
    private static final double DEFAULT_ZOOM_SPACING = 50;
    private static final int DEFAULT_ZOOM_TIME = 500;
    private static final int ZOOM_TO_SCALE = 0;
    private static final int ZOOM_TO_SCREEN = 1;

    private ZNode fDefaultZoomToNode;
    private int fZoomMode = ZOOM_TO_SCREEN;
    private double fZoomScale = DEFAULT_ZOOM_SCALE;
    private double fZoomSpacing = DEFAULT_ZOOM_SPACING;
    private int fZoomTime = DEFAULT_ZOOM_TIME;

    /**
     * Constructs a new ZoomToEventHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the ZFilteredEventHandler class comment to customize this behavior.
     */
    public ZoomToEventHandler(ZSceneGraphObject aFilteredMouseEventSource) {
        super(aFilteredMouseEventSource);
    }

    /**
     * Constructs a new ZoomToEventHandler.

     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the ZFilteredEventHandler class comment to customize this behavior.
     * @param aDefaultZoomToNode        The node that the event handler will zoom to if no other node is picked.
     */
    public ZoomToEventHandler(ZSceneGraphObject aFilteredMouseEventSource, ZNode aDefaultZoomToNode) {
        this(aFilteredMouseEventSource);
        setDefaultZoomToNode(aDefaultZoomToNode);
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
     * Maps filtered mouse pressed onto the <code>zoomTo</code> action.
     *
     * @param e the filtered mouse pressed event.
     */
    public void filteredMousePressed(ZMouseEvent e) {
        zoomTo(e);
    }

    /**
     * Zoom the interaction camera to the node picked by the current mouse event. If no node
     * is picked then zoom to the default zoom to node.
     *
     * @param e the event starting the zoomto action.
     */
    protected void zoomTo(ZMouseEvent e) {
        ZCamera aCamera = getInteractionCamera();
        ZNode aNode = getNodeToZoomTo(e);

        if (aNode == null) return;

        switch (fZoomMode) {
            case ZOOM_TO_SCALE: {
                zoomToScaleWith(aCamera, aNode);
                break;
            }
            case ZOOM_TO_SCREEN: {
                zoomToScreenWith(aCamera, aNode);
                break;
            }
        }
    }

    /**
     * Return the node that should be zoomed to for the given event. The default behavior returns
     * the node picked by the event. If no node is picked then the default zoom to node is returned.
     *
     * @param aEvent the event used to determine the node to zoom to.
     * @return the node that the interaction camera should zoom to.
     */
    protected ZNode getNodeToZoomTo(ZMouseEvent aEvent) {
        ZNode result = fDefaultZoomToNode;
        ZSceneGraphPath aPath = aEvent.getPath();

        if (aPath.getObject() != null) {
            result = aPath.getNode();
        }

        return result;
    }

    /**
     * Return the amount of time that the zoom action should take.
     *
     * @return the zoom animation duration.
     */
    public int getZoomTime() {
        return fZoomTime;
    }

    /**
     * Set the amount of time that the zoom action should take.
     *
     * @param aTime the duration for proceeding zoomto animations.
     */
    public int setZoomTime(int aTime) {
        return fZoomTime = aTime;
    }

    /**
     * Set the default node that the camera should zoom to when no other
     * node is picked for a zoom action.
     *
     * @param aDefaultZoomToNode the default zoom to node.
     */
    public void setDefaultZoomToNode(ZNode aDefaultZoomToNode) {
        fDefaultZoomToNode = aDefaultZoomToNode;
    }

    /**
     * Set the zoom mode to scale. This means the interaction camera will zoom so that
     * the zoom to node appears at the specified scale on the screen.
     *
     * @param aScale the scale that the zoom to node should appear on the screen after a zoom action.
     */
    public void setZoomToScale(double aScale) {
        fZoomMode = ZOOM_TO_SCALE;
        fZoomScale = aScale;
    }

    /**
     * Set the zoom mode to screen. This means the interaction camera will zoom so that
     * the zoom to node fills the screen minus the specified spacing.
     *
     * @param aSpacing the spacing that should be left around the zoom to node after a zoom action.
     */
    public void setZoomToScreen(double aSpacing) {
        fZoomMode = ZOOM_TO_SCREEN;
        fZoomSpacing = aSpacing;
    }

    /**
     * Zoom the specified camera so that the specified node appears in the center
     * of the camera view at the scale specified by <code>setZoomToScale</code>.
     *
     * @param aCamera the camera that will be zoomed.
     * @param aNode the node that will be zoomed to.
     */
    protected void zoomToScaleWith(ZCamera aCamera, ZNode aNode) {
        ZBounds nodeBounds = aNode.getGlobalBounds();
        ZBounds cameraBounds = aCamera.getBoundsReference();
        AffineTransform at = new AffineTransform();
        double scale = ZTransformGroup.computeScale(aNode.getGlobalToLocalTransform()) * (fZoomScale/1);
        double ctrX = (0.5 * cameraBounds.getWidth());
        double ctrY = (0.5 * cameraBounds.getHeight());
        double nodeBoundsX = (nodeBounds.getX() + (0.5 * nodeBounds.getWidth()));
        double nodeBoundsY = (nodeBounds.getY() + (0.5 * nodeBounds.getHeight()));
        at.translate(ctrX + (- nodeBoundsX * scale), ctrY + (- nodeBoundsY * scale));
        at.scale(scale, scale);
        aCamera.animate(at, getZoomTime(), aCamera.getDrawingSurface());
    }

    /**
     * Zoom the specified camera so that the specified node fills the center
     * of the camera view minus the specified spacing set in <code>setZoomToScreen</code>.
     *
     * @param aCamera the camera that will be zoomed.
     * @param aNode the node that will be zoomed to.
     */
    protected void zoomToScreenWith(ZCamera aCamera, ZNode aNode) {
        ZBounds cameraBounds = aCamera.getBounds();
        cameraBounds.x = cameraBounds.x + fZoomSpacing;
        cameraBounds.y = cameraBounds.y + fZoomSpacing;
        cameraBounds.width = cameraBounds.width - (fZoomSpacing * 2);
        cameraBounds.height = cameraBounds.height - (fZoomSpacing * 2);
        aCamera.center(aNode.getGlobalBounds(), cameraBounds, getZoomTime(), aCamera.getDrawingSurface());
    }
}
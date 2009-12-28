/**
 * Copyright 2000-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.geom.*;
import java.awt.event.*;

import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.*;

/**
 * <b>ZHandleEventHandler</b> is normaly used by ZHandleGroups to turn mouse events
 * into calls to ZHandles <code>handleStartDrag</code>, <code>handleDragged<code>,
 * and <code>handleEndDrag</code> methods. It also makes sure handles are highlited
 * and unhighlited when appropriate.
 *
 * Normaly you should not need to use this class directly unless you want to change
 * the way handles are interacted with.
 *
 * @author  Jesse Grosjean
 */
public class ZHandleEventHandler extends ZDragSequenceEventHandler {

    private ZHandle fCurrentHandle;
    private ZHandle fCurrentHighlightedHandle;

    /**
     * Constructs a new ZHandleEventHandler.
     *
     * @param aMouseEventSource the source for ZMouseEvents and ZMouseMotionEvents.
     *                          See the ZFilteredEventHandler class comment to customize this behavior.
     */
    public ZHandleEventHandler(ZSceneGraphObject aEventSource) {
        super(aEventSource);
    }

    /**
     * Return ZHandle that is the target of the current drag sequence. This
     * will be null if a drag sequence is not in progress.
     */
    public ZHandle getCurrentHandle() {
        return fCurrentHandle;
    }

    /**
     * Return ZHandle that is currently highlighted.
     */
    public ZHandle getCurrentHighlightedHandle() {
        return fCurrentHighlightedHandle;
    }

    /**
     * ZHandles are dragged with BUTTON1 by default.
     */
    public ZMouseFilter getMouseFilter() {
        if (fMouseFilter == null) {
            fMouseFilter = new ZMouseFilter(InputEvent.BUTTON1_MASK);
            //fMouseFilter.setIsConsuming(true);
        }
        return fMouseFilter;
    }

    /**
     * Highlite the handle if a handle was just entered.
     */
    public void filteredMouseEntered(ZMouseEvent e) {
        ZCamera aCamera = e.getPath().getCamera();
        ZSceneGraphPath aPath = e.getPath();

        if (aPath.getObject() instanceof ZHandle) {
            fCurrentHighlightedHandle = (ZHandle) aPath.getObject();
            fCurrentHighlightedHandle.setIsHighlighted(true);
        }
    }

    /**
     * Unhighlite the handle if a handle was just exited.
     */
    public void filteredMouseExited(ZMouseEvent e) {
        if (fCurrentHighlightedHandle != null) {
            fCurrentHighlightedHandle.setIsHighlighted(false);
            fCurrentHighlightedHandle = null;
        }
    }

    /**
     * Start dragging the current handle..
     */
    protected void startDrag(ZMouseEvent e) {
        super.startDrag(e);
        fCurrentHandle = (ZHandle) e.getPath().getObject();
        Point2D localPoint = e.getLocalPoint();

        fCurrentHandle.handleStartDrag(localPoint.getX(),
                                       localPoint.getY());

        fCurrentHandle.handleStartDrag(localPoint.getX(),
                                       localPoint.getY(),
                                       e);
        e.consume();
    }

    /**
     * Drag the current handle..
     */
    protected void dragInScreenCoords(ZMouseEvent e, Dimension2D aScreenDelta) {
        e.getPath().screenToLocal(aScreenDelta);

        fCurrentHandle.handleDragged(aScreenDelta.getWidth(),
                                     aScreenDelta.getHeight());

        fCurrentHandle.handleDragged(aScreenDelta.getWidth(),
                                     aScreenDelta.getHeight(),
                                     e);
        e.consume();
    }

    /**
     * Stop dragging the current handle..
     */
    protected void endDrag(ZMouseEvent e) {
        super.endDrag(e);
        Point2D localPoint = e.getLocalPoint();

        fCurrentHandle.handleEndDrag(localPoint.getX(),
                                     localPoint.getY());

        fCurrentHandle.handleEndDrag(localPoint.getX(),
                                     localPoint.getY(),
                                     e);
        fCurrentHandle = null;
        e.consume();
    }

    /**
     * Only start dragging the current handle if we are actualy over a ZHandle
     * object.
     */
    protected boolean shouldStartDragInteraction(ZMouseEvent e) {
        return e.getPath().getObject() instanceof ZHandle;
    }
}
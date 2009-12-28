/**
 * Copyright 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZDragSequenceEventHandler</b> is designed to support drag sequence interactions
 * normally consisting of filtered mouse pressed, filtered mouse dragged, and filtered mouse
 * released events. This class doesn't do anything useful on its own but it helps to support
 * drag sequences in the event handlers that subclass it.
 * <P>
 * ZDragSequenceEventHandler works by setting up three template methods;
 * <ul>
 * <li><code>protected void startDrag(ZMouseEvent e)</code>
 * <li><code>protected void drag(ZMouseEvent e)</code>
 * <li><code>protected void dragInScreenCoords(ZMouseEvent e, Dimension2D aScreenDelta)</code>
 * <li><code>protected void endDrag(ZMouseEvent e)</code>
 * </ul>
 * <P>
 * Event handlers that use drag sequences in their interactions should override these
 * template methods and invoke the appropriate behavior from within those methods.
 * <P>
 * Using this class to implement your drag sequences increases flexibility in a number
 * of ways. Since this class removes the coupling between filtered mouse down events
 * and <code>startDrag</code> it's fairly easy for someone to subclass your event handler
 * and map the drag sequence to a different set of filtered mouse events instead of the
 * standard filtered mouse pressed, filtered mouse dragged, filtered mouse released sequence.
 * <P>
 * This class also supports the notion of a minimum drag start distance. That is the
 * distance that the mouse must be dragged before the drag sequence will start.
 *
 * @author  Jesse Grosjean
 */
public class ZDragSequenceEventHandler extends ZFilteredEventHandler {

    private Point2D fMousePressedScreenPoint;
    private Point2D fDragStartScreenPoint;
    private Point2D fPreviousScreenPoint;
    private Point2D fCurrentScreenPoint;
    private Dimension2D fCurrentScreenDelta;
    private boolean fIsDragging = false;
    private double fMinDragStartDistance = 0;

    /**
     * Constructs a new ZDragSequenceEventHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the ZFilteredEventHandler class comment to customize this behavior.
     */
    public ZDragSequenceEventHandler(ZSceneGraphObject aFilteredMouseEventSource) {
        super(aFilteredMouseEventSource);
    }

    /**
     * Constructs a new ZDragSequenceEventHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the ZFilteredEventHandler class comment to customize this behavior.
     * @param aFilteredKeyEventSouce    the source for filtered KeyEvents. See the ZFilteredEventHandler class comment
     *                                  to customize this behavior.
     */
    public ZDragSequenceEventHandler(ZSceneGraphObject aFilteredMouseEventSource, ZCanvas aFilteredKeyEventSouce) {
        super(aFilteredMouseEventSource, aFilteredKeyEventSouce);
    }

    /**
     * Maps filtered mouse pressed onto the <code>startDrag</code> method if a drag
     * interaction should be started as determined by <code>shouldStartDragInteraction</code>.
     *
     * @param e the filtered mouse pressed event.
     */
    protected void filteredMousePressed(ZMouseEvent e) {
        getMousePressedScreenPoint().setLocation(e.getX(), e.getY());
        if (shouldStartDragInteraction(e))
            startDrag(e);
    }

    /**
     * Maps filtered mouse dragged onto the <code>drag</code> method if a drag sequence is in
     * progress. Or maps it onto the <code>startDrag</code> method if a drag sequence is not
     * in progress but should be started as determined by <code>shouldStartDragInteraction</code>.
     *
     * @param e the filtered mouse dragged event.
     */
    protected void filteredMouseDragged(ZMouseEvent e) {
        if (!isDragging()) {
            if (shouldStartDragInteraction(e))
                startDrag(e);

            return;
        }
        drag(e);
    }

    /**
     * Maps filtered mouse released onto the <code>endDrag</code> method if a drag sequence was
     * in progress when the mouse was released. If a drag sequence was not in progress
     * <code>endDrag</code> will not be called.
     *
     * @param e the filtered mouse released event.
     */
    protected void filteredMouseReleased(ZMouseEvent e) {
        if (isDragging()) {
            endDrag(e);
        }
    }

    /**
     * This method is called when a drag sequence is started. Subclasses that need
     * to know about this should override this method, but also must call
     * <code>super.startDrag</code> since this method sets up the drag sequence state.
     * This method sets the current drawing surface to interacting mode.
     *
     * @param e the event starting the drag sequence.
     */
    protected void startDrag(ZMouseEvent e) {
        setIsDragging(true);
        ZSceneGraphPath aPath = e.getPath();
        getDragStartScreenPoint().setLocation(getMousePressedScreenPoint());
        getPreviousScreenPoint().setLocation(fDragStartScreenPoint);
        getCurrentScreenPoint().setLocation(fDragStartScreenPoint);
        getCurrentScreenDelta().setSize(0, 0);
        getTopCamera().getDrawingSurface().setInteracting(true);
    }

    /**
     * This method is called during a drag sequence. Its default implementation
     * is to figure out the current distance that the mouse has moved and then
     * call <code>dragInScreenCoords</code> with this information. It also updates the current screen delta,
     * and last screen point values.
     *
     * @param e the event causing the drag.
     */
    protected void drag(ZMouseEvent e) {
        double dx = e.getX() - getPreviousScreenPoint().getX();
        double dy = e.getY() - getPreviousScreenPoint().getY();

        if (dx != 0 || dy != 0) {
            getCurrentScreenPoint().setLocation(e.getX(), e.getY());
            getCurrentScreenDelta().setSize(dx, dy);
            dragInScreenCoords(e, getCurrentScreenDelta());
            getPreviousScreenPoint().setLocation(e.getX(), e.getY());
        }
    }

    /**
     * This method is called by <code>drag</code> during at a drag sequence. It can be more useful
     * to override this method then <code>drag<code> since the screen delta is provided for you. The
     * screen delta parameter in this method is the same object as the one returned by
     * <code>getScreenDelta</code>. So if you modify this object (such as transform from one coord
     * system to another) the object returned from <code>getScreenDelta</code> will also be modified. But for most
     * event handlers its still ok to modify it since the screen delta object is refreshed on each new event.
     *
     * @param e the event causing the drag.
     * @param aScreenDelta the change in mouse position between the current and last filtered mouse events.
     */
    protected void dragInScreenCoords(ZMouseEvent e, Dimension2D aScreenDelta) {
    }

    /**
     * This method is called at the end of a drag sequence.
     * This method sets the current drawing surface to not interacting mode.
     *
     * @param e the event causing the end of the drag sequence.
     */
    protected void endDrag(ZMouseEvent e) {
        getTopCamera().getDrawingSurface().setInteracting(false);
        setIsDragging(false);
    }

    /**
     * Invoked to determine if a drag interaction should be started. The normal behavior
     * is to determine if the mouse has moved the <code>getMinDragStartDistance</code> from the
     * <code>getMousePressedScreenPoint</code>.
     *
     * @param e the event containing the current filtered mouse position.
     * @return true if a drag sequence should be started.
     */
    protected boolean shouldStartDragInteraction(ZMouseEvent e) {
        return getMousePressedScreenPoint().distance(e.getX(), e.getY()) >= getMinDragStartDistance();
    }

    /**
     * Returns true if the event handler is currently dragging.
     *
     * @return true if the event handler is dragging.
     */
    public boolean isDragging() {
        return fIsDragging;
    }

    /**
     * Set to true if the event handler is currently dragging. This will normaly only be used internaly.
     *
     * @param aBoolean true if the event handler is dragging..
     */
    protected void setIsDragging(boolean aBoolean) {
        fIsDragging = aBoolean;
    }

    /**
     * Return the distance in screen coords that the mouse needs to move
     * before a drag sequence will be started.
     *
     * @return the minimum distance that mouse must be dragged for a drag sequence to start.
     */
    public double getMinDragStartDistance() {
        return fMinDragStartDistance;
    }

    /**
     * Set the distance (in screen coords) that the mouse needs
     * to move before a drag sequence is started.
     *
     * @param the minimum distance that mouse must be dragged for a drag sequence to start.
     */
    public void setMinDragStartDistance(double aDouble) {
        fMinDragStartDistance = aDouble;
    }

    /**
     * Return the point in screen coords where the mouse was pressed.
     *
     * @return the mouse press point in screen coords.
     */
    public Point2D getMousePressedScreenPoint() {
        if (fMousePressedScreenPoint == null) {
            fMousePressedScreenPoint = new Point2D.Double();
        }
        return fMousePressedScreenPoint;
    }

    /**
     * Return the point in screen coords where the drag sequence started.
     * This will normally be the same as the mouse pressed screen point,
     * but is not set until the drag sequence is started.
     *
     * @return the point where the drag sequence started in screen coords.
     */
    public Point2D getDragStartScreenPoint() {
        if (fDragStartScreenPoint == null) {
            fDragStartScreenPoint = new Point2D.Double();
        }
        return fDragStartScreenPoint;
    }

    /**
     * Returns the screen location of the previous filtered mouse event.
     *
     * @return the previous filtered mouse position in screen coords.
     */
    public Point2D getPreviousScreenPoint() {
        if (fPreviousScreenPoint == null) {
            fPreviousScreenPoint = new Point2D.Double();
        }
        return fPreviousScreenPoint;
    }

    /**
     * Returns the screen point of the current filtered mouse event.
     *
     * @return the current filtered mouse position in screen coords.
     */
    public Point2D getCurrentScreenPoint() {
        if (fCurrentScreenPoint == null) {
            fCurrentScreenPoint = new Point2D.Double();
        }
        return fCurrentScreenPoint;
    }

    /**
     * Returns the current distance that the mouse has moved between the current and previous
     * filtered mouse events in screen coords. This returns the same object that is passed as a paramater into
     * dragInScreenCoords so if you modify (changing from screen to local coords for example) that
     * object it will also be modified when you access it here. But is its value is refreshed on each new
     * call to <code>drag</code> so it is normally safe to modify it if you event handler only accesses
     * it once per event.
     */
    public Dimension2D getCurrentScreenDelta() {
        if (fCurrentScreenDelta == null) {
            fCurrentScreenDelta = new ZDimension();
        }
        return fCurrentScreenDelta;
    }
}
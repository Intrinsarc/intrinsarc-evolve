/**
 * Copyright 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.io.*;
import java.util.*;
import java.awt.event.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZFilteredEventHandler</b> is an abstract implementation of
 * ZEventHandler. This class provides flexible event filtering. This allows one
 * to easily filter the events that this event handler will receive without
 * modifying the event handler source code.
 * <P>
 * This class automatically handles the task of registering and unregistering with
 * its filtered event sources. These sources (a key event source and mouse event source) are
 * specified as arguments to this class's constructor or with <code>setKeyEventSource<code>
 * and <code>setMouseEventSource<code>. The normal behavior when this class is set to
 * active is to register as a KeyListener with the filtered key event souce if it is not null,
 * and to register as a ZMouseListener and a ZMouseMotionListener with the filtered mouse event
 * source if it is not null. This behavior can be customized by overriding these methods:
 * <ul>
 * <li><code>protected boolean wantsKeyEvents()</code>
 * <li><code>protected boolean wantsMouseEvents()</code>
 * <li><code>protected boolean wantsMouseMotionEvents()</code>
 * </ul>
 * <P>
 * Filtering is supported by the ZMouseFilter class together with the following template methods
 * that subclasses should override if they are interested in that event.
 * <ul>
 * <li><code>protected void filteredKeyPressed(KeyEvent e)</code>
 * <li><code>protected void filteredKeyReleased(KeyEvent e)</code>
 * <li><code>protected void filteredKeyTyped(KeyEvent e)</code>
 * <li><code>protected void filteredMouseClicked(ZMouseEvent e)</code>
 * <li><code>protected void filteredMouseDragged(ZMouseEvent e)</code>
 * <li><code>protected void filteredMouseEntered(ZMouseEvent e)</code>
 * <li><code>protected void filteredMouseExited(ZMouseEvent e)</code>
 * <li><code>protected void filteredMouseMoved(ZMouseEvent e)</code>
 * <li><code>protected void filteredMousePressed(ZMouseEvent e)</code>
 * <li><code>protected void filteredMouseReleased(ZMouseEvent e)</code>
 * </ul>
 * <P>
 * These methods will only be called if the the event filter assocaited with this event
 * handler first accepts the incoming event.
 * <P>
 * Filters can be used in two ways. An external application can use them to modify
 * the behavior of a existing event handler. For example an application can tell a
 * ZPanEventHandler to only accept ZMouseEvents that have the shift modifier pressed,
 * and this will cause the panning to occur only when the shift key is down.
 * <p>
 * <code>
 * <pre>
 *      // Example of using a mouse filter externally.
 *      ...
 *      // Get a reference to the pan event handlers filter.
 *      ZMouseFilter panFilter = canvas.getPanEventHandler().getMouseFilter();
 *
 *      // Set the filters andMask to contain button1 and shift. This means that
 *      // an event must have both button1 and shift in its modifiers to be accepted by
 *      // the filter. The panning action will now only occur when those modifiers are both set.
 *      panFilter.setAndMask(InputEvent.BUTTON1_MASK | InputEvent.SHIFT_MASK);
 *      ...
 * </pre>
 * </code>
 * <P>
 * Filters can also be used internally by an event handlers implementation.
 * For example a event handler can determine on a filtered mouse down event that it is not
 * appropriate for it to act in the current context and can then set its filter to
 * accept nothing but the next filtered mouse down event that it sees. This can get rid of
 * testing code in other methods such as mouse moved and mouse released that determines
 * if the event handler should really act in that context or not.
 * <p>
 * <code>
 * <pre>
 *      // Example of using a mouse filter internally.
 *      protected void filteredMousePressed(ZMouseEvent e) {
 *
 *          if (shouldStartHandlingEvents(e)) { // This may test for the type of node that the mouse
 *                                              // is over or any other contextual information.
 *
 *              // Since the event handler wants to handle events for this context it
 *              // must tell its filter to accept all incoming events.
 *              getMouseFilter().acceptAllEventTypes();
 *
 *          } else {
 *
 *              // Since the event handler does not want to handle events for this context it
 *              // must tell its filter to reject all incoming events.
 *              getMouseFilter().rejectAllEventTypes();
 *
 *              // But then it must tell its event handler to accept mouse pressed events so that
 *              // it will have a chance to handle the next filtered mouse down event that will bring a
 *              // new context with it.
 *              getMouseFilter().setIsAcceptingMousePressed(true);
 *          }
 *      }
 * </pre>
 * </code>
 *
 * @see ZMouseFilter
 * @author  Jesse Grosjean
 */
public class ZFilteredEventHandler implements ZEventHandler {

    private boolean fIsActive;
    private ZSceneGraphObject fFilteredMouseEventSource;
    private ZCanvas fFilteredKeyEventSource;
    private ZMouseEvent fCurrentFilteredMouseEvent;
    protected ZFilteredEventDispatcher fFilteredEventDispatcher;
    protected ZMouseFilter fMouseFilter;

    /**
     * ZFilteredEventDispatcher is used internally to register with the filtered event sources.
     * It applies the filter to incoming events. If the event passes the filter then
     * it updates the current filtered mouse event variable (see <code>getCurrentFilteredMouseEvent</code>)
     * and calls the appropriate 'filtered*' template method.
     */
    public class ZFilteredEventDispatcher implements ZMouseListener,
                                                      ZMouseMotionListener,
                                                      KeyListener {

        /**
         * Invoked when a key has is pressed on the key event souce. Forwards the
         * event to <code>filteredKeyPressed</code> if the current event
         * filter accepts the event.
         *
         * @param e the key event.
         */
        public void keyPressed(KeyEvent e) {
            filteredKeyPressed(e); // Currently no filtering is done for key events.
        }

        /**
         * Invoked when a key is released on the key event souce. Forwards the
         * event to <code>filteredKeyReleased</code> if the current event
         * filter accepts the event.
         *
         * @param e the key event.
         */
        public void keyReleased(KeyEvent e) {
            filteredKeyReleased(e); // Currently no filtering is done for key events.
        }

        /**
         * Invoked when a key is typed on the key event souce. Forwards the
         * event to <code>filteredKeyTyped</code> if the current event
         * filter accepts the event. This event occurs when a key press is
         * followed by a key release.
         *
         * @param e the key event.
         */
        public void keyTyped(java.awt.event.KeyEvent e) {
            filteredKeyTyped(e); // Currently no filtering is done for key events.
        }

        /**
         * Invoked when the mouse is clicked the mouse event source. If the current event filter
         * accepts the event then the event is set as the current event and is forwarded to
         * <code>filteredMouseClicked</code>. After <code>filteredMouseClicked</code> returns the
         * current event is set to null.
         *
         * @param e the mouse clicked event.
         */
        public void mouseClicked(ZMouseEvent e) {
            if (getMouseFilter().accept(e)) {
                fCurrentFilteredMouseEvent = e;
                filteredMouseClicked(e);
            }
        }

        /**
         * Invoked when a mouse button is pressed on the mouse event source and then dragged. Mouse drag
         * events will continue to be delivered to the object where the first originated until
         * the mouse button is released (regardless of whether the mouse position is within
         * the bounds of the object). If the current event filter accepts the event then the
         * event is set as the current event and is forwarded to  <code>filteredMouseDragged</code>.
         * After <code>filteredMouseDragged</code> returns the  current event is set to null.
         *
         * @param e the mouse dragged event.
         */
        public void mouseDragged(ZMouseEvent e) {
            if (getMouseFilter().accept(e)) {
                fCurrentFilteredMouseEvent = e;
                filteredMouseDragged(e);
            }
        }

        /**
         * Invoked when the mouse enters the mouse event source. If the current event filter
         * accepts the event then the event is set as the current event and is forwarded to
         * <code>filteredMouseEntered</code>. After <code>filteredMouseEntered</code> returns the
         * current event is set to null.
         *
         * @param e the mouse entered event.
         */
         public void mouseEntered(ZMouseEvent e) {
            if (getMouseFilter().accept(e)) {
                fCurrentFilteredMouseEvent = e;
                filteredMouseEntered(e);
            }
        }

        /**
         * Invoked when the mouse exits the mouse event source. If the current event filter
         * accepts the event then the event is set as the current event and is forwarded to
         * <code>filteredMouseExited</code>. After <code>filteredMouseExited</code> returns the
         * current event is set to null.
         *
         * @param e the mouse exited event.
         */
        public void mouseExited(ZMouseEvent e) {
            if (getMouseFilter().accept(e)) {
                fCurrentFilteredMouseEvent = e;
                filteredMouseExited(e);
            }
        }

        /**
         * Invoked when the mouse button is moved on the mouse event source. (with no buttons down)
         * If the current event filter accepts the event then the event is set as the current
         * event and is forwarded to  <code>filteredMouseMoved</code>. After <code>filteredMouseMovde</code>
         * returns the current event is set to null.
         *
         * @param e the mouse moved event.
         */
        public void mouseMoved(ZMouseEvent e) {
            if (getMouseFilter().accept(e)) {
                fCurrentFilteredMouseEvent = e;
                filteredMouseMoved(e);
            }
        }

        /**
         * Invoked when the mouse is pressed on the mouse event source. If the current event filter
         * accepts the event then the event is set as the current event and is forwarded to
         * <code>filteredMousePressed</code>. After <code>filteredMousePressed</code> returns the
         * current event is set to null.
         *
         * @param e the mouse pressed event.
         */
        public void mousePressed(ZMouseEvent e) {
            if (getMouseFilter().accept(e)) {
                fCurrentFilteredMouseEvent = e;
                filteredMousePressed(e);
            }
        }

        /**
         * Invoked when the mouse is released on the mouse event source. If the current event filter
         * accepts the event then the event is set as the current event and is forwarded to
         * <code>filteredMousePressed</code>. After <code>filteredMousePressed</code> returns the
         * current event is set to null.
         *
         * @param e the mouse pressed event.
         */
        public void mouseReleased(ZMouseEvent e) {
            if (getMouseFilter().accept(e)) {
                fCurrentFilteredMouseEvent = e;
                filteredMouseReleased(e);
            }
        }
    }

    /**
     * Constructs a new ZFilteredEventHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the class comment to customize this behavior.
     */
    public ZFilteredEventHandler(ZSceneGraphObject aFilteredMouseEventSource) {
        setFilteredMouseEventSource(aFilteredMouseEventSource);
    }

    /**
     * Constructs a new ZFilteredEventHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the class comment to customize this behavior.
     * @param aFilteredKeyEventSource    the source for filtered KeyEvents. See the class comment
     *                                  to customize this behavior.
     */
    public ZFilteredEventHandler(ZSceneGraphObject aFilteredMouseEventSource, ZCanvas aFilteredKeyEventSource) {
        this(aFilteredMouseEventSource);
        setFilteredKeyEventSource(aFilteredKeyEventSource);
    }

    /**
     * Set the mouse filter that is used to filter events before they are
     * delivered to this event handler's "filtered*" methods.
     *
     * @param aMouseFilter the new filter for this event handler to use.
     */
    public void setMouseFilter(ZMouseFilter aMouseFilter) {
        fMouseFilter = aMouseFilter;
    }

    /**
     * Returns the filter currently in effect for this event handler.
     * If no filter has been set then a new filter that accepts all events
     * will be created and returned.
     *
     * @return the filter that is currently in effect.
     */
    public ZMouseFilter getMouseFilter() {
        if (fMouseFilter == null) {
            fMouseFilter = new ZMouseFilter();
        }
        return fMouseFilter;
    }

    /**
     * Return true if the event handler should register with
     * the key event source for KeyEvents. The default behavior is to return
     * true if the key event source is not null, false otherwise. This method
     * is called from within <code>setActive</code> to determine if this event
     * handler should register with the key event source.
     *
     * @return true if this event handler wants to receive key events.
     */
    protected boolean wantsKeyEvents() {
        return fFilteredKeyEventSource != null;
    }

    /**
     * Return true if the event handler should register with
     * the mouse event source for ZMouseEvents. The default behavior is to return
     * true if the mouse event source is not null, false otherwise. This method
     * is called from within <code>setActive</code> to determine if this event
     * handler should register for mouse events from the mouse event source.
     *
     * @return true if this event handler wants to receive mouse events.
     */
    protected boolean wantsMouseEvents() {
        return fFilteredMouseEventSource != null;
    }

    /**
     * Return true if the event handler should register with
     * the mouse event source for ZMouseMotionEvents. The default behavior is to return
     * true if the mouse event source is not null, false otherwise. This method
     * is called from within <code>setActive</code> to determine if this event
     * handler should register for mouse motion events from the mouse event source.
     *
     * @return true if this event handler wants to receive mouse motion events.
     */
    protected boolean wantsMouseMotionEvents() {
        return fFilteredMouseEventSource != null;
    }

    /**
     * Return the current source for mouse events.
     *
     * @return the source for mouse events.
     */
    public ZSceneGraphObject getFilteredMouseEventSource() {
        return fFilteredMouseEventSource;
    }

    /**
     * Set the current source for mouse events.
     *
     * @param aMouseEventSource the new source for mouse events, or null if mouse events are not wanted.
     */
    public void setFilteredMouseEventSource(ZSceneGraphObject aFilteredMouseEventSource) {
        if (fFilteredMouseEventSource != null && isActive()) {
            if (wantsMouseEvents()) {
                fFilteredMouseEventSource.removeMouseListener(getFilteredEventDispatcher());
            }

            if (wantsMouseMotionEvents()) {
                fFilteredMouseEventSource.removeMouseMotionListener(getFilteredEventDispatcher());
            }
        }

        fFilteredMouseEventSource = aFilteredMouseEventSource;

        if (fFilteredMouseEventSource != null && isActive()) {
            if (wantsMouseEvents()) {
                fFilteredMouseEventSource.addMouseListener(getFilteredEventDispatcher());
            }

            if (wantsMouseMotionEvents()) {
                fFilteredMouseEventSource.addMouseMotionListener(getFilteredEventDispatcher());
            }
        }
    }

    /**
     * Return the current source for filtered key events.
     *
     * @return the source for filtered key events.
     */
    public ZCanvas getFilteredKeyEventSource() {
        return fFilteredKeyEventSource;
    }

    /**
     * Set the current source for key events.
     *
     * @param aKeyEventSource the new source for key events, or null if key events are not wanted.
     */
    public void setFilteredKeyEventSource(ZCanvas aFilteredKeyEventSource) {
        if (fFilteredKeyEventSource != null && isActive()) {
            if (wantsKeyEvents()) {
                fFilteredKeyEventSource.removeKeyListener(getFilteredEventDispatcher());
            }
        }

        fFilteredKeyEventSource = aFilteredKeyEventSource;

        if (fFilteredKeyEventSource != null && isActive()) {
            if (wantsKeyEvents()) {
                fFilteredKeyEventSource.addKeyListener(getFilteredEventDispatcher());
            }
        }
    }

    /**
     * Determines if this event handler is active. An active event handler is registered with
     * its event sources for events. An inactive event handler is not registered with its event
     * sources and so will not receive events.
     *
     * @return <code>true</code> if active.
     */
    public boolean isActive() {
        return fIsActive;
    }

    /**
     * Specifies whether this event handler is active or not. An active event handler
     * is registered with its event sources for events. An inactive event handler
     * is not registered with its event sources and so will not receive events.
     *
     * @param active <code>true</code> to make this event handler active.
     */
    public void setActive(boolean active) {
        if (fIsActive && !active) {
                                // Turn off event handlers
            fIsActive = false;

            if (wantsMouseEvents()) {
                fFilteredMouseEventSource.removeMouseListener(getFilteredEventDispatcher());
            }

            if (wantsMouseMotionEvents()) {
                fFilteredMouseEventSource.removeMouseMotionListener(getFilteredEventDispatcher());
            }

            if (wantsKeyEvents()) {
                fFilteredKeyEventSource.removeKeyListener(getFilteredEventDispatcher());
            }
        } else if (!fIsActive && active) {
                                // Turn on event handlers
            fIsActive = true;

            if (wantsMouseEvents()) {
                fFilteredMouseEventSource.addMouseListener(getFilteredEventDispatcher());
            }

            if (wantsMouseMotionEvents()) {
                fFilteredMouseEventSource.addMouseMotionListener(getFilteredEventDispatcher());
            }

            if (wantsKeyEvents()) {
                fFilteredKeyEventSource.addKeyListener(getFilteredEventDispatcher());
                fFilteredKeyEventSource.requestFocus();
            }
        }
        fIsActive = active;
    }


    /**
     * Return the current filtered mouse event.
     *
     * @return the current filtered mouse event.
     */
    public ZMouseEvent getCurrentFilteredMouseEvent() {
        return fCurrentFilteredMouseEvent;
    }

    /**
     * Utility method for use by subclasses that operate on the current selection. This
     * implementation returns all selected nodes viewed from the top camera.
     *
     * @return the current selection as viewed from the top camera from the last event, or if no
     *         last event exists return the current selection starting at the
     *         root of the filtered key event souce. If that is also null return an empty collection.
     */
    public Collection getCurrentSelection() {
        if (getInteractionCamera() != null) {
            return ZSelectionManager.getSelectedNodes(getTopCamera());
        }

        if (getFilteredKeyEventSource() != null) {
            return ZSelectionManager.getSelectedNodes(getFilteredKeyEventSource().getRoot());
        }

        return new ArrayList(0);
    }

    /**
     * Return the camera that should be used for camera interactions for the current event. The
     * default implementation returns the bottom camera from the current filtered mouse events scene graph path.
     *
     * @return the camera that should be used for camera interactions such as zooming or panning.
     */
    public ZCamera getInteractionCamera() {
        if (getCurrentFilteredMouseEvent() != null) {
            return getCurrentFilteredMouseEvent().getPath().getCamera();
        }
        return null;
    }

    /**
     * Return the to camera for the current event.
     *
     * @return the camera top camera for the current event..
     */
    public ZCamera getTopCamera() {
        if (getCurrentFilteredMouseEvent() != null) {
            return getCurrentFilteredMouseEvent().getPath().getTopCamera();
        }
        return null;
    }

    /**
     * Return the event dispatcher being used by this filtered event handler. If you need to
     * send an event directly to this event handler but still want it filtered then you should
     * use this.
     */
    public ZFilteredEventDispatcher getFilteredEventDispatcher() {
        if (fFilteredEventDispatcher == null) {
            fFilteredEventDispatcher = new ZFilteredEventDispatcher();
        }
        return fFilteredEventDispatcher;
    }

    /**
     * Invoked when a key is pressed on the key event souce
     * and the event filter accepts the event.
     *
     * @param e the filtered key pressed event accepted by the event filter.
     */
    protected void filteredKeyPressed(KeyEvent e) {
    }

    /**
     * Invoked when a key is released on the key event souce
     * and the event filter accepts the event.
     *
     * @param e the filtered key released event accepted by the event filter.
     */
    protected void filteredKeyReleased(KeyEvent e) {
    }

    /**
     * Invoked when a key is typed on the key event souce
     * and the event filter accepts the event.
     *
     * @param e the filtered key typed event accepted by the event filter.
     */
    protected void filteredKeyTyped(KeyEvent e) {
    }

    /**
     * Invoked when the is clicked the mouse event souce
     * and the current event filter accepts the event.
     *
     * @param e the filtered key typed event accepted by the event filter.
     */
    protected void filteredMouseClicked(ZMouseEvent e) {
    }

    /**
     * Invoked when a mouse button is pressed on the mouse event source and then dragged if
     * the current event filter accepts the event.
     * <P>
     * Mouse drag events will continue to be delivered to the event source until
     * the mouse button is released (regardless of whether the mouse position is within
     * the bounds of the event source).
     *
     * @param e the filtered mouse dragged event.
     */
    protected void filteredMouseDragged(ZMouseEvent e) {
    }

    /**
     * Invoked when the mouse enters the mouse event souce and
     * the current event filter accepts the event.
     *
     * @param e the filtered mouse entered event.
     */
    protected void filteredMouseEntered(ZMouseEvent e) {
    }

    /**
     * Invoked when the mouse exits the mouse event souce and
     * the current event filter accepts the event.
     *
     * @param e the filtered mouse exited event.
     */
    protected void filteredMouseExited(ZMouseEvent e) {
    }

    /**
     * Invoked when the mouse button is moved on the event source
     * (with no buttons no down) and the current event filter accepts the event.
     *
     * @param e the filtered mouse moved event.
     */
    protected void filteredMouseMoved(ZMouseEvent e) {
    }

    /**
     * Invoked when the mouse button is pressed on the event source
     * and the current event filter accepts the event.
     *
     * @param e the filtered mouse pressed event.
     */
    protected void filteredMousePressed(ZMouseEvent e) {
    }

    /**
     * Invoked when the mouse button is released on the event source
     * and the current event filter accepts the event.
     *
     * @param e the filtered mouse released event.
     */
    protected void filteredMouseReleased(ZMouseEvent e) {
    }
}
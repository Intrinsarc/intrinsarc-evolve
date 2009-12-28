/**
 * Copyright 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.event.*;

/**
 * <b>ZMouseFilter</b> is used by ZFilteredEventHandler to filter (accept or reject) mouse events.
 * It is intended to separate event test code from event handlers so that it's easy to change
 * the events that drive a event handlers behavior without needing to subclass or modifying the event handler.
 * <P>
 * An events type, click count, and modifier field can be used to filter the event. The filter can optionally
 * consume any events that it accepts. This consuming behavior is off by default.
 * <p>
 * <code>
 * <pre>
 *      // ZMouseFilter examples.
 *
 *      ZMouseFilter filter = new ZMouseFilter();
 *      // A new filter accepts all events.
 *
 *      filter.setAndMask(InputEvent.BUTTON1_MASK);
 *      // The filter will now only accept events that have the button1 modifier
 *
 *      filter.setNotMask(InputEvent.SHIFT_MASK);
 *      // The filter will now only accept events that have the button1 modifier
 *      // and do not have the shift modifier.
 *
 *      filter.rejectAllEventTypes();
 *      // The filter will now reject all events based on the type of the event. The
 *      // andMask and notMask set previously are still preserved.
 *
 *      filter.setAcceptsMousePressed(true);
 *      // The filter will now only accept mouse pressed events
 *      // that have the button1 modifier
 *      // and do not have the shift modifier.
 *
 *      filter.setAndMask(0); // don't require any modifiers for acceptance.
 *      filter.setNotMask(0); // don't reject on the presence of any modifiers.
 *      filter.acceptAllEventTypes(); // don't reject based on event type.
 *      filter.setOrMask(InputEvent.BUTTON1_MASK | InputEvent.BUTTON2_MASK);
 *      // The filter will now accept any event that has either the button1 modifier or
 *      // the button2 modifier or both.
 *
 *      filter.setOrMask(ALL_MODIFIERS_MASK);
 *      // The filter will now accept all events once again.
 *
 * </pre>
 * </code>
 * @see ZFilteredEventHandler
 * @author  Jesse Grosjean
 */
public class ZMouseFilter {

    /**
     * A mask containing all modifiers. This is most useful for setting the orMask
     * so that it will accept anything.
     */
    public static int ALL_MODIFIERS_MASK = InputEvent.BUTTON1_MASK |
                                           InputEvent.BUTTON2_MASK |
                                           InputEvent.BUTTON3_MASK |
                                           InputEvent.SHIFT_MASK |
                                           InputEvent.CTRL_MASK |
                                           InputEvent.ALT_MASK |
                                           InputEvent.ALT_GRAPH_MASK |
                                           InputEvent.META_MASK;

    private int fAndMask;
    private int fOrMask;
    private int fNotMask;
    private short fClickCount = -1;

    private boolean fConsumes = false;

    private boolean fAcceptsMouseClicked = true;
    private boolean fAcceptsMouseDragged = true;
    private boolean fAcceptsMouseEntered = true;
    private boolean fAcceptsMouseExited = true;
    private boolean fAcceptsMouseMoved = true;
    private boolean fAcceptsMousePressed = true;
    private boolean fAcceptsMouseReleased = true;

    /**
     * Constructs a new ZMouseFilter that accepts all events.
     */
    public ZMouseFilter() {
        acceptEverything();
    }

    /**
     * Constructs a new ZMouseFilter that accepts only events that have all the
     * modifiers that are specified in the AND mask parameter.
     *
     * @param aAndMask a bitmask specifying the flags that must be present for the event to be accepted.
     */
    public ZMouseFilter(int aAndMask) {
        this();
        fAndMask = aAndMask;
    }

    /**
     * Constructs a new ZMouseFilter that accepts only events that have all the
     * modifiers that are specified in the AND mask parameter, and none of the modifiers
     * specified in the NOT mask parameter.
     *
     * @param aAndMask a bitmask specifying the flags that must be present for the event to be accepted.
     * @param aNotMask a bitmask specifying the flags that must not be present for the event to be accepted.
     */
    public ZMouseFilter(int aAndMask, int aNotMask) {
        this(aAndMask);
        fNotMask = aNotMask;
    }

    /**
     * Set the AND bitmask. This mask specifies the modifiers that must be present in a event for the
     * event to be accepted by the filter.
     *
     * @param aAndMask a bitmask specifying the flags that must be present for the event to be accepted.
     */
    public void setAndMask(int aAndMask) {
        fAndMask = aAndMask;
    }

    /**
     * Set the OR bitmask. A event must have at least one of the modifiers specified in the
     * or mask to be accepted by the filter.
     *
     * @param aOrMask a bitmask specifying the OR flags, at least one must be present in an
     *                event for the event to be accepted by the filter.
     */
    public void setOrMask(int aOrMask) {
        fOrMask = aOrMask;
    }

    /**
     * Set the NOT bitmask. This mask specifies the modifiers that must not be present in a event for the
     * event to be accepted by the filter.
     *
     * @param aNotMask a bitmask specifying the flags that must not be present for the event to be accepted.
     */
    public void setNotMask(int aNotMask) {
        fNotMask = aNotMask;
    }

    /**
     * Set the click count to be accepted. The click count of an event must equal this click count
     * for that event to be accepted. A value of -1 for click count will accept all event click counts.
     *
     * @param aClickCount the click count required of an event for it to be accepted.
     */
    public void setAcceptClickCount(short aClickCount) {
        fClickCount = aClickCount;
    }

    /**
     * Make it so that this event filter accepts all click counts.
     */
    public void acceptAllClickCounts() {
        fClickCount = -1;
    }

    /**
     * Make it so that this event filter rejects all click counts.
     */
    public void rejectAllClickCounts() {
        fClickCount = Short.MAX_VALUE;
    }

    /**
     * Make it so that this event filter consumes all events that it accepts.
     * If an event is not accepted it will not be consumed. The default value for
     * this is false.
     *
     * @param aBoolean true if this filter consumes accepted events.
     */
    public void setConsumes(boolean aBoolean) {
        fConsumes = aBoolean;
    }

    /**
     * Returns true if this event filter consumes accepted events.
     *
     * @return true if the filter is consuming events that it accepts.
     */
    public boolean getConsumes() {
        return fConsumes;
    }

    /**
     * Returns true if the filter is accepting mouse clicked events.
     */
    public boolean getAcceptsMouseClicked() {
        return fAcceptsMouseClicked;
    }

    /**
     * Set if the filter is accepting mouse clicked events.
     *
     * @param aBoolean true if the filter is accepting mouse clicked events.
     */
    public void setAcceptsMouseClicked(boolean aBoolean) {
        fAcceptsMouseClicked = aBoolean;
    }

    /**
     * Returns true if the filter is accepting mouse entered events.
     */
    public boolean getAcceptsMouseEntered() {
        return fAcceptsMouseEntered;
    }

    /**
     * Set if the filter is accepting mouse entered events.
     *
     * @param aBoolean true if the filter is accepting mouse entered events.
     */
    public void setAcceptsMouseEntered(boolean aBoolean) {
        fAcceptsMouseEntered = aBoolean;
    }

    /**
     * Returns true if the filter is accepting mouse exited events.
     */
    public boolean getAcceptsMouseExited() {
        return fAcceptsMouseExited;
    }

    /**
     * Set if the filter is accepting mouse exited events.
     *
     * @param aBoolean true if the filter is accepting mouse exited events.
     */
    public void setAcceptsMouseExited(boolean aBoolean) {
        fAcceptsMouseExited = aBoolean;
    }

    /**
     * Returns true if the filter is accepting mouse pressed events.
     */
    public boolean getAcceptsMousePressed() {
        return fAcceptsMousePressed;
    }

    /**
     * Set if the filter is accepting mouse pressed events.
     *
     * @param aBoolean true if the filter is accepting mouse pressed events.
     */
    public void setAcceptsMousePressed(boolean aBoolean) {
        fAcceptsMousePressed = aBoolean;
    }

    /**
     * Returns true if the filter is accepting mouse released events.
     */
    public boolean getAcceptsMouseReleased() {
        return fAcceptsMouseReleased;
    }

    /**
     * Set if the filter is accepting mouse released events.
     *
     * @param aBoolean true if the filter is accepting mouse released events.
     */
    public void setAcceptsMouseReleased(boolean aBoolean) {
        fAcceptsMouseReleased = aBoolean;
    }

    /**
     * Returns true if the filter is accepting mouse dragged events.
     */
    public boolean getAcceptsMouseDragged() {
        return fAcceptsMouseDragged;
    }

    /**
     * Set if the filter is accepting mouse dragged events.
     *
     * @param aBoolean true if the filter is accepting mouse dragged events.
     */
    public void setAcceptsMouseDragged(boolean aBoolean) {
        fAcceptsMouseDragged = aBoolean;
    }

    /**
     * Returns true if the filter is accepting mouse moved events.
     */
    public boolean getAcceptsMouseMoved() {
        return fAcceptsMouseMoved;
    }

    /**
     * Set if the filter is accepting mouse moved events.
     *
     * @param aBoolean true if the filter is accepting mouse moved events.
     */
    public void setAcceptsMouseMoved(boolean aBoolean) {
        fAcceptsMouseMoved = aBoolean;
    }

    /**
     * Make the filter reject all event types. (MOUSE_PRESSED, MOUSE_RELEASED ...)
     */
    public void rejectAllEventTypes() {
        fAcceptsMouseClicked = false;
        fAcceptsMouseDragged = false;
        fAcceptsMouseEntered = false;
        fAcceptsMouseExited = false;
        fAcceptsMouseMoved = false;
        fAcceptsMousePressed = false;
        fAcceptsMouseReleased = false;
    }

    /**
     * Make the filter accept all event types. (MOUSE_PRESSED, MOUSE_RELEASED ...)
     */
    public void acceptAllEventTypes() {
        fAcceptsMouseClicked = true;
        fAcceptsMouseDragged = true;
        fAcceptsMouseEntered = true;
        fAcceptsMouseExited = true;
        fAcceptsMouseMoved = true;
        fAcceptsMousePressed = true;
        fAcceptsMouseReleased = true;
    }

    /**
     * Make the filter accept all events. Accept all types, modifiers, and clickcounts.
     */
    public void acceptEverything() {
        acceptAllEventTypes();
        setAndMask(0);
        setOrMask(ALL_MODIFIERS_MASK);
        setNotMask(0);
        acceptAllClickCounts();
    }

    /**
     * Return true if the event should be accepted, false if it should not. This method takes into
     * consideration:
     * <ul>
     * <li> If the event is already consumed the filter rejects the event.
     * <li> If the events modifiers do not contain all modifiers specified in the filters AND mask the event is rejected.
     * <li> If the events modifiers do not contain at least one of the modifiers specified in the filters OR mask the event is rejected.
     * <li> If the events modifiers contain any of the modifiers specified in the filters NOT mask the event is rejected.
     * <li> If the events click count does not equal the filters click count the event is rejected.
     * <li> If the events type is not being accepted then the event is rejected.
     * <ul>
     * <p>
     * If the event is accepted and this filter is consuming then this method will consume  the event.
     *
     * @param aEvent the event under consideration by the filter.
     */
    public boolean accept(ZMouseEvent aEvent) {
        if (aEvent.isConsumed())
            return false;

        boolean aResult = false;
        int modifiers = aEvent.getModifiers();

        if ((modifiers == 0 ||                     // if no modifiers then ignore modifier constraints, ELSE
            (modifiers & fAndMask) == fAndMask &&  // must have all modifiers from the AND mask and
            (modifiers & fOrMask) != 0 &&          // must have at least one modifier from the OR mask and
            (modifiers & fNotMask) == 0)) {        // can't have any modifiers from the NOT mask

            if (fClickCount != -1 && fClickCount != aEvent.getClickCount()) {
                aResult = false;
            } else {
                switch (aEvent.getID()) {
                    case ZMouseEvent.MOUSE_CLICKED:
                        aResult = getAcceptsMouseClicked();
                        break;

                    case ZMouseEvent.MOUSE_ENTERED:
                        aResult = getAcceptsMouseEntered();
                        break;

                    case ZMouseEvent.MOUSE_EXITED:
                        aResult = getAcceptsMouseExited();
                        break;

                    case ZMouseEvent.MOUSE_PRESSED:
                        aResult = getAcceptsMousePressed();
                        break;

                    case ZMouseEvent.MOUSE_RELEASED:
                        aResult = getAcceptsMouseReleased();
                        break;

                    case ZMouseEvent.MOUSE_DRAGGED:
                        aResult = getAcceptsMouseDragged();
                        break;

                    case ZMouseEvent.MOUSE_MOVED:
                        aResult = getAcceptsMouseMoved();
                        break;

                    default:
                        throw new RuntimeException("ZMouseEvent with bad ID");
                }
            }
        }

        if (aResult && getConsumes()) { // If have accepted this event and we are consuming then
            aEvent.consume();              // consume this event. Don't consume what we dont accept.
        }

        return aResult;
    }
}
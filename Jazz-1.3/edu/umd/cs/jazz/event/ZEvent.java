/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import edu.umd.cs.jazz.*;

/**
 * This interface ensures that the implementing event can dispatch itself
 * without someone else needing to look at the events ID or type. This allows us
 * to get ride of all fireEvent(SpecificEventType) methods and replace them with
 * one fireEvent(ZEvent) method that is located in ZSceneGraphObject.
 *
 * @author: Jesse
 */
public interface ZEvent {
    /**
     * This method should dispatch this event to the listener using this events ID code to
     * determine what method in the listener to call.
     */
    public void dispatchTo(Object listener);

    /**
     * Get the listener type for this event. This is used by fireEvent in ZSceneGraphObject
     * to determine what listeners should receive this event.
     */
    public Class getListenerType();

    /**
     * Returns true if this event has previously been consumed by a listener.
     */

    public boolean isConsumed();

    /**
     * Set the souce of this event. As the event is fired up the tree the source of the
     * event will keep changing to reflect the scenegraph object that is firing the event.
     */
    public void setSource(Object aSource);
}
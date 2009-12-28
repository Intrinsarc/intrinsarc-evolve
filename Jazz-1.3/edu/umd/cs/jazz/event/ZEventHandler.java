/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.Vector;

import edu.umd.cs.jazz.*;

/**
 * <b>ZEventHandler</b> is an interface for defining behaviors in Jazz.
 * It is really just a utility to make it easier to group sets of
 * event handlers that work together to define a behavior.  This defines
 * event listeners for mouse events so that a sub-class can over-ride the
 * ones it wants to define.  Then, the entire set of handlers can be temporarily
 * turned on or off with calls to <code>setActive</code>.
 * <p>
 * The event handler is associated with a specific node in the Jazz scenegraph.
 * If it is attached to the root node, then it will respond to events on all
 * nodes (if the events weren't handled by an event handler on a lower node).
 * If it is attached to any other node, then it will respond only to events
 * on nodes that are in the sub-tree rooted at the event handler node.
 * <p>
 * This functionality is specifically designed for mode-driven applications so that
 * one mode might draw while another follows hyperlinks.  One event handler can
 * be defined for each mode, and then they just need to be activated and deactivated
 * as needed.
 *
 * @author  Benjamin B. Bederson
 */
public interface ZEventHandler {
    /**
     * Specifies whether this event handler is active or not.
     * @param active True to make this event handler active
     */
    public void setActive(boolean active);

    /**
     * Determines if this event handler is active.
     * @return True if active
     */
    public boolean isActive();
}


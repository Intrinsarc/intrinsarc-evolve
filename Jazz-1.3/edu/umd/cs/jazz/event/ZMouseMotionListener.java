/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.util.EventListener;

/**
 * <b>ZMouseMotionListener</b> is a listener interface for receiving mouse motion events on a node.
 * (For clicks and other mouse events, use the ZMouseListener.)
 * <P>
 * The class that is interested in processing a mouse motion event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ZMouseMotionAdapter</code> class
 * (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a
 * node using the component's <code>addMouseMotionListener</code> 
 * method. A mouse motion event is generated when the mouse is moved
 * or dragged. (Many such events will be generated). When a mouse motion event
 * occurs, the relevant method in the listener object is invoked, and 
 * the <code>ZMouseEvent</code> is passed to it.
 *
 * @see ZMouseMotionAdapter
 * @see ZMouseEvent
 */
public interface ZMouseMotionListener extends EventListener {

    /**
     * Invoked when a mouse button is pressed on a node and then 
     * dragged.  Mouse drag events will continue to be delivered to
     * the node where the first originated until the mouse button is
     * released (regardless of whether the mouse position is within the
     * bounds of the node).
     */
    public void mouseDragged(ZMouseEvent e);

    /**
     * Invoked when the mouse button has been moved on a node
     * (with no buttons no down).
     */
    public void mouseMoved(ZMouseEvent e);
}

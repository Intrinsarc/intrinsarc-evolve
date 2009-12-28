/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

/**
 * <b>ZMouseMotionAdapter</b> is an abstract adapter class for receiving mouse motion events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Mouse motion events occur when a mouse is moved or dragged.
 * (Many such events will be generated in a normal program.
 * To track clicks and other mouse events, use the MouseAdapter.)
 * <P>
 * Extend this class to create a <code>ZMouseMotionEvent</code> listener 
 * and override the methods for the events of interest. (If you implement the 
 * <code>ZMouseMotionListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with 
 * a node using the node's <code>addMouseMotionListener</code> 
 * method. When the mouse is moved or dragged, the relevant method in the 
 * listener object is invoked and the <code>ZMouseEvent</code> is passed to it.
 *
 * @see ZMouseEvent
 * @see ZMouseMotionListener
 */
public abstract class ZMouseMotionAdapter implements ZMouseMotionListener {
    /**
     * Invoked when a mouse button is pressed on a node and then 
     * dragged.  Mouse drag events will continue to be delivered to
     * the node where the first originated until the mouse button is
     * released (regardless of whether the mouse position is within the
     * bounds of the node).
     */
    public void mouseDragged(ZMouseEvent e) {}

    /**
     * Invoked when the mouse button has been moved on a node
     * (with no buttons no down).
     */
    public void mouseMoved(ZMouseEvent e) {}
}

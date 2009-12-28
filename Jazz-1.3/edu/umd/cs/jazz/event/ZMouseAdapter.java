/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

/**
 * <b>ZMouseAdapter</b> is an abstract adapter class for receiving mouse events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Mouse events let you track when a mouse is pressed, released, clicked, 
 * when it enters a node, and when it exits.
 * (To track mouse moves and mouse drags, use the ZMouseMotionAdapter.)
 * <P>
 * Extend this class to create a <code>ZMouseEvent</code> listener 
 * and override the methods for the events of interest. (If you implement the 
 * <code>ZMouseListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with 
 * a node using the node's <code>addMouseListener</code> 
 * method. When a mouse button is pressed, released, or clicked (pressed and
 * released), or when the mouse cursor enters or exits the node,
 * the relevant method in the listener object is invoked
 * and the <code>ZMouseEvent</code> is passed to it.
 *
 * @see ZMouseEvent 
 * @see ZMouseListener
 */
public abstract class ZMouseAdapter implements ZMouseListener {
    /**
     * Invoked when the mouse has been clicked on a node.
     */
    public void mouseClicked(ZMouseEvent e) {}

    /**
     * Invoked when a mouse button has been pressed on a node.
     */
    public void mousePressed(ZMouseEvent e) {}

    /**
     * Invoked when a mouse button has been released on a node.
     */
    public void mouseReleased(ZMouseEvent e) {}

    /**
     * Invoked when the mouse enters a node.
     */
    public void mouseEntered(ZMouseEvent e) {}

    /**
     * Invoked when the mouse exits a node.
     */
    public void mouseExited(ZMouseEvent e) {}
}

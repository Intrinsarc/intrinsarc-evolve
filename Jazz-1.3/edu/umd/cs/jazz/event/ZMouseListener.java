/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.util.EventListener;

/**
 * <b>ZMouseListener</b> is a listener interface for receiving "interesting" mouse events 
 * (press, release, click, enter, and exit) on a node.
 * (To track mouse moves and mouse drags, use the ZMouseMotionListener.)
 * <P>
 * The class that is interested in processing a mouse event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ZMouseAdapter</code> class
 * (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a
 * node using the node's <code>addMouseListener</code> 
 * method. A mouse event is generated when the mouse is pressed, released
 * clicked (pressed and released). A mouse event is also generated when
 * the mouse cursor enters or leaves a node. When a mouse event
 * occurs, the relevant method in the listener object is invoked, and 
 * the <code>MouseEvent</code> is passed to it.
 *
 * @see ZMouseAdapter
 * @see ZMouseEvent
 * @author Ben Bederson
 */
public interface ZMouseListener extends EventListener {

    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void mouseClicked(ZMouseEvent e);

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(ZMouseEvent e);

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(ZMouseEvent e);

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(ZMouseEvent e);

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(ZMouseEvent e);
}

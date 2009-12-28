/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

import javax.swing.*;

/**
 * <b>ZGroupListener</b> is an interface to support notification when changes occur to a ZGroup.
 * Based on Swing's ComponentListener.
 * <P>
 * The class that is interested in processing a group event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ZGroupAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * ZGroup using the ZGroup's <code>addGroupListener</code> 
 * method. When the ZGroup's state changes,
 * the relevant method in the listener object is invoked,
 * and the <code>ZGroupEvent</code> is passed to it.
 * <P>
 * ZGroup events are provided for notification purposes ONLY;
 * Jazz will automatically handle ZGroup state changes
 * internally so that everything works properly regardless of
 * whether a program registers a <code>ZGroupListener</code> or not.
 *
 * @see ZGroupAdapter
 * @see ZGroupEvent
 * @author Ben Bederson
 */
public interface ZGroupListener extends EventListener {
    /**
     * Invoked when a node has been added to this group.
     * @param e The group event.
     */
    public void nodeAdded(ZGroupEvent e);

    /**
     * Invoked when a node has been removed from this group.
     * @param e The group event.
     */    
    public void nodeRemoved(ZGroupEvent e);
}

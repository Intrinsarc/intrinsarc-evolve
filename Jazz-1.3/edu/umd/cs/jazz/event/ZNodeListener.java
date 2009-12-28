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
 * <b>ZNodeListener</b> is an interface to support notification when changes occur to a ZNode.
 * Based on Swing's ComponentListener.
 * <P>
 * The class that is interested in processing a node event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ZNodeAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * ZNode using the ZNode's <code>addNodeListener</code> 
 * method. When the ZNode's state changes,
 * the relevant method in the listener object is invoked,
 * and the <code>ZNodeEvent</code> is passed to it.
 * <P>
 * ZNode events are provided for notification purposes ONLY;
 * Jazz will automatically handle ZNode state changes
 * internally so that everything works properly regardless of
 * whether a program registers a <code>ZNodeListener</code> or not.
 *
 * @see ZNodeAdapter
 * @see ZNodeEvent
 * @author Ben Bederson
 */
public interface ZNodeListener extends EventListener {
    /**
     * Invoked when a node has its bounds changed.
     */
    public void boundsChanged(ZNodeEvent e);

    /**
     * Invoked when a node has its global bounds changed.
     */
    public void globalBoundsChanged(ZNodeEvent e);
}

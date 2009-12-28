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
 * <b>ZTransformListener</b> is an interface to support notification when changes occur to a ZTransformGroup.
 * Based on Swing's ComponentListener.
 * <P>
 * The class that is interested in processing a transform event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ZTransformAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * ZTransform using the ZTransform's <code>addTransformListener</code> 
 * method. When the ZTransform's state changes,
 * the relevant method in the listener object is invoked,
 * and the <code>ZTransformEvent</code> is passed to it.
 * <P>
 * ZTransform events are provided for notification purposes ONLY;
 * Jazz will automatically handle ZTransform state changes
 * internally so that everything works properly regardless of
 * whether a program registers a <code>ZTransformListener</code> or not.
 *
 * @see ZTransformAdapter
 * @see ZTransformEvent
 * @author Ben Bederson
 */
public interface ZTransformListener extends EventListener {
    /**
     * Invoked when the transform changes.
     */
    public void transformChanged(ZTransformEvent e);
}

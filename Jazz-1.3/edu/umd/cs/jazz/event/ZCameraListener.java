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
 * <b>ZCameraListener</b> is an interface to support notification when changes occur to a ZCamera.
 * Based on Swing's ComponentListener.
 * <P>
 * The class that is interested in processing a camera event
 * either implements this interface (and all the methods it
 * contains) or extends the abstract <code>ZCameraAdapter</code> class
 * (overriding only the methods of interest).
 * The listener object created from that class is then registered with a
 * ZCamera using the ZCamera's <code>addCameraListener</code> 
 * method. When the ZCamera's state changes,
 * the relevant method in the listener object is invoked,
 * and the <code>ZCameraEvent</code> is passed to it.
 * <P>
 * ZCamera events are provided for notification purposes ONLY;
 * Jazz will automatically handle ZCamera state changes
 * internally so that everything works properly regardless of
 * whether a program registers a <code>ZCameraListener</code> or not.
 *
 * @see ZCameraAdapter
 * @see ZCameraEvent
 * @author Ben Bederson
 */
public interface ZCameraListener extends EventListener {
    /**
     * Invoked when the camera's view transform changes.
     */
    public void viewChanged(ZCameraEvent e);
}

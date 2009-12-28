/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.util.*;

import javax.swing.*;

/**
 * <b>ZCameraAdapater</b> is an abstract adapter class for receiving camera events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * Based on Swing's ComponentListener.
 * <P>
 * Extend this class to create a <code>ZCameraEvent</code> listener 
 * and override the methods for the events of interest. (If you implement the 
 * <code>ZCameraListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines null methods for them
 * all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using your class and then register it with a
 * camera using the camera's <code>addCameraListener</code> 
 * method. When the camera's state changes,
 * the relevant method in the listener object is invoked,
 * and the <code>ZCameraEvent</code> is passed to it.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see ZCameraAdapter
 * @see ZCameraEvent
 * @author Ben Bederson
 */
public class ZCameraAdapter implements ZCameraListener, Serializable {
    /**
     * Invoked when the camera's view transform changes.
     */
    public void viewChanged(ZCameraEvent e) {
    }
}

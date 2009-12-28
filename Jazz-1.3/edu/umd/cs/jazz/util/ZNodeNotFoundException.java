/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.io.*;

/** 
 * <b>ZNodeNotFoundException</b> is an exception that is thrown
 * to indicate that a node that was being searched
 * for in the scenegraph was not found.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 */

public class ZNodeNotFoundException extends RuntimeException implements Serializable {
    /**
     * Constructs a new ZNodeNotFoundException.
     */
    public ZNodeNotFoundException() {
    }

    /**
     * Constructs a new ZNodeNotFoundException with the given message.
     */
    public ZNodeNotFoundException(String msg) {
	super(msg);
    }
}

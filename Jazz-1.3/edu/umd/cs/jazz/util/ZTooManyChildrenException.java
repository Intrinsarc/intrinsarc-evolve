/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import edu.umd.cs.jazz.ZGroup;
import java.io.*;

/** 
 * <b>ZTooManyChildrenException</b> is an exception that is thrown
 * to indicate that an operation was attempted that would
 * have resulted in a decorator node having more than one child.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 */

public class ZTooManyChildrenException extends RuntimeException implements Serializable {
    ZGroup decorator = null;

    /**
     * Construct a ZTooManyChildrenException object.
     * @param decorator group node that the operation to add more than one child to was attempted on.
     */
    public ZTooManyChildrenException(ZGroup decorator) {
	this.decorator = decorator;
    }

    /**
     * Construct a ZTooManyChildrenException object.
     * @param decorator group node that the operation to add more than one child to was attempted on.
     * @param msg a message associated with this exception.
     */
    public ZTooManyChildrenException(ZGroup decorator, String msg) {
	super(msg);
	this.decorator = decorator;
    }

    /**
     * Get the decorator that the operation to add more than one child to was attempted on.
     * @return the decorator.
     */
    public ZGroup getDecorator() {
	return decorator;
    }
}

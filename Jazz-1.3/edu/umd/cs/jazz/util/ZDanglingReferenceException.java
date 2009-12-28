/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import edu.umd.cs.jazz.ZSceneGraphObject;
import java.io.*;

/** 
 * <b>Thrown</b> to indicate that a node that was being searched
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

public class ZDanglingReferenceException extends RuntimeException implements Serializable {
    ZSceneGraphObject origObj;

    /**
     * Constructs a new ZDanglingReferenceException, specifying the object being searched for.
     * @param origObj the scenegraph object being searched for.
     */
    public ZDanglingReferenceException(ZSceneGraphObject origObj) {
	this.origObj = origObj;
    }

    /**
     * Constructs a new ZDanglingReferenceException, specifying the node being searched for,
     * and a text message.
     * @param origObj the scenegraph object being searched for.
     * @param msg a text message.
     */
    public ZDanglingReferenceException(ZSceneGraphObject origObj, String msg) {
	super(msg);
	this.origObj = origObj;
    }

    /**
     * Returns the original object being searched for.
     */
    public ZSceneGraphObject getOriginalObject() {
	return origObj;
    }
}

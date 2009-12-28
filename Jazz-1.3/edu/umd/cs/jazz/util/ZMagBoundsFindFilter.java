/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.util;

import edu.umd.cs.jazz.*;
import java.io.*;

/**
 * <b>ZMagBoundsFindFilter</b> is a filter that accepts "terminal" nodes that overlap
 * the specified bounds in global coordinates, but only if the object is within
 * its visible magnification range.  
 * Terminal nodes are leaf nodes and group
 * nodes that do not return true for "childrenFindable()".
 * 
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 */
public class ZMagBoundsFindFilter extends ZBoundsFindFilter implements Serializable {
    ZBounds bounds = null;
    double mag = 1.0;

    /**
     * Create a new magnification bounds filter.  This filter accepts "terminal" nodes whose
     * bounds intersect the specified bounds, but only if the object
     * is within its visible magnification range.
     * Terminal nodes are leaf nodes and group
     * nodes that do not "childrenFindable".
     * @param bounds The bounds in global coordinates to search within.
     * @param mag The magnification to use for filtering
     */
    public ZMagBoundsFindFilter(ZBounds bounds, double mag) {
	super(bounds);

	this.bounds = bounds;
	this.mag = mag;
    }

    /**
     * Determine if the specified node is accepted by this filter.
     * @param node The node that is to be examined by this filter
     * @return True if the node is accepted by the filter
     */
    public boolean accept(ZNode node) {
	boolean visible = true;

	if (node instanceof ZFadeGroup) {
	    visible = ((ZFadeGroup)node).isVisible(mag);
	}
	if (visible) {
	    return super.accept(node);
	} else {
	    return false;
	}
    }

    /**
     * This method determines if the children of the specified node should be
     * searched.
     * @param node The node that is to be examined by this filter
     * @return True if this node's children should be searched, or false otherwise.
     */
    public boolean childrenFindable(ZNode node) {
	boolean visible = true;

	if (node instanceof ZFadeGroup) {
	    visible = ((ZFadeGroup)node).isVisible(mag);
	}
	if (visible) {
	    return super.childrenFindable(node);
	} else {
	    return false;
	}
    }
}

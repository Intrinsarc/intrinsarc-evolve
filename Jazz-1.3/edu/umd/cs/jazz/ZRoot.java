/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.geom.AffineTransform;
import java.io.*;
import edu.umd.cs.jazz.util.*;

/** 
 * <b>ZRoot</b> extends ZNode overiding several methods of ZNode to ensure that ZRoot is
 * always in the root position of a Scenegraph. Every scenegraph begins with a ZRoot 
 * which serves as the root of the entire scenegraph tree. Each scenegraph has exactly 
 * one root.  
 * 
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 * @author Britt McAlister
 */
public class ZRoot extends ZGroup implements Serializable {
    // The current render context for this tree
    private transient ZRenderContext currentRenderContext;

    /**
     * Set the current render context.  This is meant to be used
     * by context-sensitive objects to determine the render context
     * while computing bounds, etc.
     * @param renderContext The new render context.
     */
    public void setCurrentRenderContext(ZRenderContext renderContext) {
	currentRenderContext = renderContext;
    }

    /**
     * Return current render context.  During a render, this contains
     * the active render context.  During other times, it contains
     * the render context to be used for computing bounds by
     * context-sensitive objects.
     * @return the render context
     */
    public ZRenderContext getCurrentRenderContext() {
	return currentRenderContext;
    }

    /**
     * Overrides ZNode.setParent() to throw an exception if an
     * attempt to set the parent of a ZRoot is made.
     * @param parent parameter is not used.
     */
    protected void setParent(ZNode parent) throws RuntimeException {
	throw new RuntimeException("Can't set parent of ZRoot");
    }
}

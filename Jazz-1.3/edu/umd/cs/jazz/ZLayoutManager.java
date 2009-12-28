/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import edu.umd.cs.jazz.io.*;

/**
 * <b>ZLayoutManager</b> represents an object that can layout the
 * children of a node.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Benjamin B. Bederson
 * @see     ZNode
 */
public interface ZLayoutManager extends ZSerializable {
    /**
     * Apply this manager's layout algorithm to the specified node's children.
     * @param The node to apply this layout algorithm to.
     */
    public void doLayout(ZGroup node);

    /**
     * Apply this manager's layout algorithm to the specified node's children,
     * and animate the changes over time.
     * @param node The node to apply this layout algorithm to.
     * @param millis The number of milliseconds over which to animate layout changes.
     */
    public void doLayout(ZGroup node, int millis);

    /**
     * Notify the layout manager that a potentially recursive layout is starting.
     * This is called before any children are layed out.
     * @param node The node to apply this layout algorithm to.
     */
    public void preLayout(ZGroup node);

    /**
     * Notify the layout manager that the layout for this node has finished
     * This is called after all children and the node itself are layed out.
     * @param node The node to apply this layout algorithm to.
     */
    public void postLayout(ZGroup node);

    /**
     * Layout manager objects must provide a public clone method
     */
    public Object clone();
}

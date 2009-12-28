/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import edu.umd.cs.jazz.ZNode;

/** 
 * <b>ZFindFilter</b> provides a general interface that is used to determine if a specified
 * node should be accepted.
 * 
 * @author Ben Bederson
 * @see edu.umd.cs.jazz.ZDrawingSurface#findNodes
 */
public interface ZFindFilter {
    /**
     * This method determines if the specified node should be accepted by
     * the filter.  Users of this filter determine the semantics of how
     * the filter is applied, but generally if a node is not accepted, then
     * its children are still examined.
     * @param node The node that is to be examined by this filter
     * @return True if the node is accepted by the filter
     * @see edu.umd.cs.jazz.ZDrawingSurface#findNodes
     */
    public boolean accept(ZNode node);

    /**
     * This method determines if the children of the specified node should be
     * searched.
     * @param node The node that is to be examined by this filter
     * @return True if this node's children should be searched, or false otherwise.
     */
    public boolean childrenFindable(ZNode node);
}

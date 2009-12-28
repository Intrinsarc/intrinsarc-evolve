/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.*;
import javax.swing.event.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;

/**
 * <b>ZSpatialIndexGroup</b> is a group node that supports R-tree indexing
 * for a group of visual components. This indexing can provide faster rendering
 * when a large number of visual components are being displayed. Currently, significant
 * speed improvements become apparent when a few thousand or more nodes are being
 * rendered. Using a ZSpatialIndexGroup group node with a small number of nodes
 * can actually slow down rendering, due to the indexing overhead.<P>
 *
 * To use spatial indexing, first create a large number of nodes grouped under a single
 * ZGroup node. A scenegraph editor can then be used to add the ZSpatialIndexGroup
 * node: <code>groupNode.editor().getSpatialIndexGroup()</code> <P>An r-tree index
 * is created, and the spatial location information of the nodes in the group is indexed.
 * The Jazz render methods will detect the
 * ZSpatialIndexGroup node and use the appropriate rendering calls. When any
 * indexed node's bounds are changed, Jazz automatically updates the index.
 * To return to regular rendering, simply remove the ZSpatialIndexNode:
 * <code> groupNode.editor().removeSpatialIndexGroup(); </code><P>
 *
 * Note: Any ZSpatialIndexGroup node must
 * appear at the bottom of the edit node chain: its only child must be
 * a ZGroup node. The ZSceneGraphEditor (groupNode.editor()) supports this contraint.<P>
 *
 * R-tree indexing works by building a data structure containing spatial location
 * information for a group of nodes. During rendering, this structure is used
 * to decide which nodes are currently visible. These nodes are then rendered.<P>
 *
 * If ZDebug.debugSpatialIndexing is set to true, the number of nodes being rendered
 * will be reported.
 *
 * <P>
 * For a description of the algorithm used here,
 * see "The Design and Analysis of Spatial Data Structures" by Hanan Samet, pp 219-224.
 * Addison-Wesley, 1989.  Or source paper in "R-trees: A dynamic index structure for
 * spatial searching" by A. Guttman, in Proceedings of SIGMOD Conference, 1984, 47-57.
 *
 * {@link ZSceneGraphEditor} provides a convenience mechanism to locate, create
 * and manage nodes of this type.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see ZSpatialIndex
 * @see ZNode#editor()
 * @author James Mokwa
 */

/////////////////////////////////////////////////////////////
// ZSpatialIndexGroup catches ZGroupEvents: ADD_NODE and REMOVE_NODE,
// adding and removing nodes from the rtree index.
//
// Example: a new editGroup node T is to be added above editGroup2 in the
// following scenegraph:
// ZSpatialIndexGroup -> Group -> editGroup1 -> editGroup2 -> VisualLeaf
//
// VisualLeaf is currently indexed.
// New editGroup node T added above editGroup2:
// T.insertAbove(editGroup2)
//
// 1. if T has a parent, then T.getParent().removeChild(T)
//                       REMOVE_NODE event: child=T, node=T.parent
//                       ZSpatialIndexGroup.nodeRemoved catches event
//                         event.child.primary NOT a ZVisualLeaf
//                         event.child.primary.topGroup != Group
//                         ==> No index action
//
// 2. Remove editGroup2 from editGroup1
//    REMOVE_NODE event: child=eg2, node=eg1
//    ZSpatialIndexGroup.nodeRemoved catches event
//      event.child.primary IS a ZVisualLeaf
//      event.child.primary.topGroup == Group
//      ==> UNINDEX event.child.primary  (ie unindex VisualLeaf)
//
// 3. Add T to eg1
//    ADD_NODE event: child=T, node=eg1
//    ZSpatialIndexGroup.nodeAdded catches event
//    T.primary NOT a ZVisualLeaf
//    ==> No index action
//      note: for tests to work, event must happen before node actually
//            removed? ZGroup.insertAbove FireEvent(NODE_REMOVED)
//            must be moved up 2 lines? evidently not.
//
// 4. Add eg2 to T
//    ADD_NODE event: child=eg2, node=T
//    ZSpatialIndexGroup.nodeAdded catches event
//    eg2.primary IS a ZVisualLeaf
//    eg2.primary.topNode == Group
//    ==> INDEX eg2.primary (ie index VisualLeaf)
//
/////////////////////////////////////////////////////////////
//
// An indexed node must be re-indexed (removed then re-added to the rtree)
// whenever it's bounds change.  When a node's bounds is changed,
// updateBounds() is called on the node's ZTransformGroup edit group node.
// When this happens, a ZNode event BOUNDS_CHANGED is fired on the
// primary node.
//
// Indexed nodes register listeners on this event. When BOUNDS_CHANGED event is caught,
// the node is re-indexed.

public class ZSpatialIndexGroup extends ZGroup implements ZGroupListener, ZSerializable, Serializable {

    /**
     * The r-tree index tree.
     */
    private ZSpatialIndex rIndex;

    /**
     * HashTable of current nodeListeners indexed by node.
     */
    private Hashtable nodeListenerHT;

    /**
     * List to hold queryWindow results.
     */
    private ArrayList queryResults = new ArrayList();

    /**
     * Constructs a new ZSpatialIndexGroup node.
     */
    public ZSpatialIndexGroup () {
        rIndex = new ZSpatialIndex();
        initialize();
    }

    /**
     * Constructs a new ZSpatialIndexGroup node using a given camera.
     */
    public ZSpatialIndexGroup (ZCamera camera) {
        rIndex = new ZSpatialIndex(camera);
        initialize();
    }

    /**
     * Initialize this ZSpatialIndexGroup. Add listeners to detect if a group
     * node added, so it's children can be indexed. Also detect global bounds
     * changes, so the index can use local bounds.
     */
    private void initialize() {
        rIndex.setGroupNodeTransform(getLocalToGlobalTransform());
        nodeListenerHT = new Hashtable();
        ZGroupListener indexGroupListener = new ZGroupListener() {
            public void nodeAdded(ZGroupEvent e) {
                indexChildren((ZGroup)e.getChild());
            }
            public void nodeRemoved(ZGroupEvent e) {
                unIndexChildren((ZGroup)e.getChild());
            }
        };
        addGroupListener(indexGroupListener);

        ZNodeListener nodeListener = new ZNodeListener() {
            public void boundsChanged(ZNodeEvent e) {
            }
            public void globalBoundsChanged(ZNodeEvent e) {
                ZNode pnode = e.getNode();
                rIndex.setGroupNodeTransform(getLocalToGlobalTransform());
            }
        };
        addNodeListener(nodeListener);
    }

    /**
     * Constructs a new ZSpatialIndex group node with the specified node as a child of the
     * new group.
     * @param child Child of the new group node.
     * @param camera the camera.
     */
    public ZSpatialIndexGroup(ZNode child, ZCamera camera) {
        rIndex = new ZSpatialIndex(camera);
        rIndex.setGroupNodeTransform(getLocalToGlobalTransform());
        insertAbove(child);
    }

    /**
     * Displays the spatial index tree, for debugging.
     * @param treeName string displayed when tree is printed.
     */
    public void displayTree(String treeName) {
        rIndex.displayTree(treeName);
    }

    /**
     * internal method: add a node to the rtree index.
     * Add a nodeListener to it's transformGroup node, if it has one.
     * @param node The node to be indexed.
     * @return true if the node was added to the index, false otherwise.
     */
    private boolean indexNode(ZNode node) {
        if (getNumChildren() == 0) {
            return false;
        }

        if (! rIndex.getStatus()) {
            return false;
        }

                                // don't index editGroup nodes
        if ((node instanceof ZGroup) && (((ZGroup)node).hasOneChild())) {
            return false;
        }

        if (node instanceof ZSelectionGroup) {
            return false;
        }
                                // if node is a child of this group
        if (node.editor().getTop().getParent() == getChild(0)) {
                                // removeNode does nothing if node is not already indexed
            rIndex.removeNode(node);
            rIndex.addNode(node);
                                // if the indexed node has a transformGroup,
                                // add a bounds-change listener to that transformGroup.
            if (node.editor().hasTransformGroup()) {
                ZTransformGroup tg = node.editor().getTransformGroup();
                addListener(tg);
            }
            return true;
        }
        return false;
    }

    /**
     * Add a BOUNDS_CHANGED node listener to a transformGroup node, if it does not
     * already have one. If a indexed node's bounds change, it needs to be re-indexed.
     * @param tg the transformGroup node.
     */
    public void addListener(ZTransformGroup tg) {
        if (! nodeListenerHT.containsKey(tg)) {
            ZNodeListener nodeListener = new ZNodeListener() {
                public void boundsChanged(ZNodeEvent e) {
                    ZNode pnode = e.getNode();
                    if (pnode instanceof ZTransformGroup) {
                        ZTransformGroup ptg = (ZTransformGroup)pnode;
                        if (ptg.getNumChildren() > 0) {
                            ZNode primary = pnode.editor().getNode();
                            indexNode(primary);
                        }
                    }
                }
                public void globalBoundsChanged(ZNodeEvent e) {
                }
            };

            tg.addNodeListener(nodeListener);
            nodeListenerHT.put(tg, nodeListener);
        }
    }

    /**
     * internal method: remove a node from the rtree index.
     * @param node The node to be un-indexed.
     * @return true if the node was actually removed from the index, false otherwise.
     */
    private boolean unIndexNode(ZNode node) {
        if (getNumChildren() == 0) {
            return false;
        }

        if (! rIndex.getStatus()) {
            return false;
        }

                                // if node is succesfully removed from rtree,
                                // remove our nodeListener from it's transformGroup
        if (rIndex.removeNode(node)) {
            if (node.editor().hasTransformGroup()) {
                ZTransformGroup tg = node.editor().getTransformGroup();
                ZNodeListener nodeListener = (ZNodeListener)(nodeListenerHT.get(tg));
                if (nodeListener != null) {
                    node.removeNodeListener(nodeListener);
                }
                nodeListenerHT.remove(node);
            }
            return true;
        }
        return false;
    }

    /**
     * Catches NODE_ADDED event, and tries to index the new node.
     * @param e the event.
     */
    public void nodeAdded(ZGroupEvent e) {
        ZNode node = e.getChild().editor().getNode();
        indexNode(node);
    }

    /**
     * Catches ZGroupEvent NODE_REMOVED event, and tries to unIndex the node.
     * @param e the event.
     */
    public void nodeRemoved(ZGroupEvent e) {
        ZNode node = e.getChild().editor().getNode();
        unIndexNode(node);
    }

    /**
     * Stop listening to nodeEvents from any indexed children. Do this prior to
     * deleting this index group.
     * @param group the group node whose children are indexed.
     */
    public void unregisterAllListeners(ZGroup group) {
        for (int i = 0; i < group.getNumChildren(); i++) {
            ZNode node = group.getChild(i).editor().getNode();
            ZNodeListener nodeListener = (ZNodeListener) (nodeListenerHT.get(node));
            if (nodeListener != null) {
                node.removeNodeListener(nodeListener);
            }
            nodeListenerHT.remove(node);
        }
    }

    /**
     * Stop listening to nodeEvents from any indexed children. Do this prior to
     * deleting this index group.
     */
    public void unregisterAllListeners() {
        ZGroup group = (ZGroup)editor().getNode();
        unregisterAllListeners(group);
    }

    /**
     * Returns the first object under the specified rectangle (if there is one)
     * in the subtree rooted with this ZSpatialIndexGroup node, as searched in
     * reverse (front-to-back) order.
     * This performs a depth-first search, first picking children.
     * Only returns a node if this is "pickable".
     * <P>
     * If childrenPickable is false, then this will never return a child as the picked node.
     * Instead, this node will be returned if any children are picked.  If no children
     * are picked, then this will return null.
     * @param rect Coordinates of pick rectangle in local coordinates
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return The picked node, or null if none
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        boolean picked = false;

        if (isPickable()) {
            path.push(this);

            if (rIndex.getStatus()) {

                // find the children who should be picked
                // this skips over the edit group nodes
              queryResults.clear();

                rIndex.queryWindow(queryResults, rect);
                Object result[] = queryResults.toArray();
                for (int i=(result.length - 1); i >=0; i--) {
                        // call pick on group node above the top edit group node
                        // this will pick thru the 'hasOneChild' edit
                        // groups, and land on our result node
                        if (((ZNode)result[i]).editor().getTop().getParent().pick(rect, path)) {
                            if (!getChildrenPickable()) {
                                // Can't pick children - set the picked object to the group
                                path.pop(this);
                                path.setObject(this);
                            }
                            return true;
                        }
                }

                // Remove me from the path if the pick failed
                path.pop(this);

            } else { // not using rTree indexing
                return super.pick(rect, path);
            }
        }
        return false;
    }

    /**
     * Renders this node which results in its children getting painted.
     * <p>
     * The transform, clip, and composite will be set appropriately when this object
     * is rendered.  It is up to this object to restore the transform, clip, and composite of
     * the Graphics2D if this node changes any of them. However, the color, font, and stroke are
     * unspecified by Jazz.  This object should set those things if they are used, but
     * they do not need to be restored.
     *
     * @param renderContext The graphics context to use for rendering.
     */
    public void render(ZRenderContext renderContext) {
        if (children.size() == 0) {
            return;
        }
                                // Paint children
        if (rIndex.getStatus()) {

            ZCamera camera = renderContext.getRenderingCamera();
            Rectangle2D viewBounds = camera.getViewBounds();
            rIndex.queryWindow(queryResults, camera.getViewBounds());
            Object result[] = queryResults.toArray();
            if (ZDebug.debugSpatialIndexing) {
                System.out.println("ZSpatialIndexGroup: Number of objects rendered: "+result.length);
            }
            for (int i=0; i<result.length; i++) {
              ((ZNode)result[i]).editor().getTop().render(renderContext);
            }

        } else { // not using RTree indexing
            super.render(renderContext);
        }
    }

    /**
     * Removes the ZGroup child from this ZSpatialIndexGroup. Stops listening to
     * NodeEvents from indexed nodes, and re-initializes the rtree index.
     * @param child the ZGroup node whose children are indexed.
     */
    public void removeChild(ZNode child) {
        super.removeChild(child);
        unregisterAllListeners((ZGroup)child);
        rIndex = new ZSpatialIndex();
    }

    /**
     * Index the children of the group node.
     * @param group a group node.
     */
    private void indexChildren(ZGroup group) {
        ZNode node;
        ZNode[] children = group.getChildren();
        for (int i=0; i<children.length; i++) {
            node = children[i].editor().getNode();
            indexNode(node);
        }
    }

    /**
     * Remove all index nodes, and their attached node listeners.
     * @param group The group node whose children should be unindexed.
     */
    private void unIndexChildren(ZGroup group) {
        ZNode node;
        ZNode[] children = group.getChildren();

        for (int i=0; i<children.length; i++) {
            node = children[i].editor().getNode();
            unIndexNode(node);
        }
    }

    /**
     * Returns true if the node intersects the bounds.
     */
    protected boolean childrenFindable(ZNode node, ZBounds bounds) {
        ZBounds nodeBounds = node.getGlobalBounds();
        if (nodeBounds.intersects(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * internal method for findNodes.
     */
    private int find(ZFindFilter filter, ZBounds bounds, ArrayList nodes) {
        int nodesSearched = 1;

                                // Only search if node is findable.
        if (isFindable()) {

            if (rIndex.getStatus()) {
                                // Check if this node is accepted by the filter
                queryResults.clear();
                rIndex.queryWindow(queryResults, bounds);
                Object result[] = queryResults.toArray();
                for (int i=0; i<result.length; i++) {
                    if ((ZNode)this == (ZNode)result[i]) {
                        nodes.add(this);
                        break;
                    }
                }

                                // Check node's children
                if (getChildrenFindable() && childrenFindable(this, bounds)) {
                    ZNode[] childrenRef = getChildrenReference();
                    for (int i=0; i<children.size(); i++) {
                        nodesSearched += childrenRef[i].findNodes(filter, nodes);
                    }
                }

            } else { // not using RTree indexing
                                // Check if this node is accepted by the filter
                if (filter.accept(this)) {
                    nodes.add(this);
                }

                                // Check node's children
                if (getChildrenFindable() && filter.childrenFindable(this)) {
                    ZNode[] childrenRef = getChildrenReference();
                    for (int i=0; i<children.size(); i++) {
                        nodesSearched += childrenRef[i].findNodes(filter, nodes);
                    }
                }
            }
        }
        return nodesSearched;
    }

    /**
     * Search from this spatialIndexGroup node down, return a list of nodes that match filter bounds.
     */
    public int findNodes(ZFindFilter filter, ArrayList nodes) {
        if (filter instanceof ZBoundsFindFilter) {
            ZBounds bounds = ((ZBoundsFindFilter)filter).getBounds();
            return(find(filter, bounds, nodes));
        } else {
            return(super.findNodes(filter, nodes));
        }
    }
}
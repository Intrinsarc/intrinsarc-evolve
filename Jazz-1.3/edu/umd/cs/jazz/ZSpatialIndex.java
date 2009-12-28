/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import edu.umd.cs.jazz.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZSpatialIndex</b> is a jazz utility class, supporting ZSpatialIndexGroup.
 * See {@link ZSpatialIndexGroup} for details.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 * @see ZSpatialIndexGroup
 */

public class ZSpatialIndex implements Serializable {
    /**
     * Minimum number of children per node
     */
    private int minChildren = 5;

    /**
     * Maximum number of children per node
     */
    private int maxChildren = 10;

    /**
     * number of nodes searched for most recent query
     */
    private int nodesSearched;

    /**
     * Surface this r-tree indexes.
     */
    protected ZCamera camera;

    /**
     * Controls if r-tree indexing is on or off.
     */
    private boolean status;

    /**
     * Root of the r-tree.
     */
    protected RTreeNode root;

    /**
     * List of r-tree nodes in draw-order order.
     */
    protected Hashtable drawOrder;

    /**
     * Comparator for sort.
     */
    private DrawOrderComp doc;

    /**
     * The localToGlobal transform for the associated group node.
     */
    private AffineTransform groupNodeTransform = null;

    //****************************************************************************
    //
    //                 Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new ZSpatialIndex.
     */
    public ZSpatialIndex() {
        camera = new ZCamera();
        status = true;
        root = new RTreeNodeLeaf();
        drawOrder = new Hashtable();
        doc = new DrawOrderComp();
     }

    /**
     * Constructs a new ZSpatialIndex with the given camera.
     * @param aCamera A camera.
     */
    public ZSpatialIndex(ZCamera aCamera) {
        camera = aCamera;
        status = true;
        root = new RTreeNodeLeaf();
        drawOrder = new Hashtable();
        doc = new DrawOrderComp();
    }

    /**
     * Increment the number of nodes searched.
     */
    private void incrementNodesSearched() {
        nodesSearched++;
    }

    /**
     * Get number of nodes searched for most recent query.
     */
    public int getNodesSearched() {
        return(nodesSearched);
    }

    /**
     * Turn RTree indexing on and off.
     * @param aStatus true to turn indexing on, false otherwise.
     */
    public void setStatus(boolean aStatus) {
        if (aStatus != status) {
            if (aStatus == true) {
                                // Turning indexing on
                status = true;
                reIndex();
            } else {
                                // Turning indexing off
                status = false;
                root = null;
            }
        }
    }

    /**
     * Return true if indexing is on, otherwise false.
     * @return status true or false.
     */
    public boolean getStatus() {
        return(status);
    }

    /**
     * Set the localToGlobal transform for the ZSpatialIndexGroup node associated with this index.
     */
    public void setGroupNodeTransform(AffineTransform at) {
        getGroupNodeTransform().setTransform(at);
    }

    /**
     * Return the localToGlobal transform for the ZSpatialIndexGroup node associated with this index.
     */
    public AffineTransform getGroupNodeTransform() {
        if (groupNodeTransform == null) {
            groupNodeTransform = new AffineTransform();
        }
        return groupNodeTransform;
    }

    /**
     * Completely re-index all objects
     */
    public void reIndex() {
        root = new RTreeNodeLeaf();
        ZFindFilter filter = new ZMagBoundsFindFilter(camera.getViewBounds(), camera.getMagnification());
        ZNode node;
        ArrayList nodes = camera.findNodes(filter);
        for (Iterator i=nodes.iterator(); i.hasNext();) {
            node = (ZNode)i.next();
            add(node);
        }
    }

    /**
     * Add a node to the rtree index.
     * @param obj the object.
     */
  public void addNode(ZNode node) {
    add(node);
    drawOrder.put(node, new Integer(drawOrder.size()));
  }

    /**
     * internal method: Add a node to the rtree index.
     * @param obj the object.
     */
    protected void add(ZNode obj) {
        boolean split;

        if (status == false) {
            return;
        }

        RTreeNode nodeArray[] = new RTreeNode[2];
        split = root.add(obj, nodeArray);
        if (split) {
                                // Root was split, need to add a new root
            root = new RTreeNodeInternal();
            root.add(nodeArray[0]);
            root.add(nodeArray[1]);
        }
    }

    /**
     * Remove an object from the index.
     * Return true if object was found, and thus removed.
     * @param obj the object to be removed.
     */
  public boolean removeNode(ZNode node) {
    boolean result = remove(node);
    if (result) {
      drawOrder.remove(node);
    }
    return(result);
  }

    /**
     * internal method: Remove an object from the index.
     * Return true if object was found, and thus removed.
     * @param obj the object to be removed.
     */
    protected boolean remove(ZNode obj) {
        boolean rc;
        RTreeNode node;
        ArrayList reinsertList = new ArrayList();
        ArrayList objList = new ArrayList();

        if (status == false) {
            return(false);
        }

        rc = root.remove(obj, reinsertList);
        if (rc) {
                                // Object was removed.
                                // Check if root has now just one child.
                                // If so, make child the new root
            if ((root.getNumRegions() == 1) && (!root.isLeaf())) {
                RTreeNode oldRoot;
                oldRoot = root;
                root = ((RTreeNodeInternal)root).getChildNode(0);
            }

                                // Now, reinsert nodes that were removed
                                // because they had two few members.
                                // Start by extracting objects
            Object reinsertListArray[] = reinsertList.toArray();
            for (int i=0; i<reinsertListArray.length; i++) {
                ((RTreeNode)reinsertListArray[i]).extractObjs(objList);
            }

                                // Finally, reinsert objects
            Object objListArray[] = objList.toArray();
            for (int i=0; i<objListArray.length; i++) {
                add((ZNode)objListArray[i]);
            }

        }

        return(rc);
    }

    public class DrawOrderComp implements Comparator {
        public int compare(Object a, Object b) {
            ZNode nodeA = (ZNode)a;
            ZNode nodeB = (ZNode)b;
            Integer valA = (Integer)drawOrder.get(nodeA);
            Integer valB = (Integer)drawOrder.get(nodeB);
            return valA.compareTo(valB);
        }
    }

    /**
     * Return all objects that overlap a point. Objects are returned
     * sorted by the order in which they were drawn.
     * @param result the objects found.
     * @param pt the point.
     */
    public void queryPoint(ArrayList result, Point2D pt) {
        queryPoint(result, pt, 0, 1);
    }

    /**
     * Return objects larger than a minimum size that overlap a point. Objects are returned
     * sorted by the order in which they were drawn.
     * @param result the objects found.
     * @param pt the point.
     * @param minSize miminum size of the objects to be found.
     * @param currentMag the current magnification.
     */
    public void queryPoint(ArrayList result, Point2D pt, double minSize, double currentMag) {
        if (status == false) {
            return;
        }
        result.clear();

        nodesSearched = 0;
        root.queryPoint(result, pt, minSize, currentMag);

        Object resultArray[] = result.toArray();
        Arrays.sort(resultArray, doc);

        result.clear();
        for (int i=0; i<resultArray.length; i++) {
            result.add(resultArray[i]);
        }
    }

    /**
     * Return all objects that overlap a bounding rectangle. Objects are returned
     * sorted by the order in which they were drawn.
     * @param result the objects found.
     * @param queryBBox the bounding box.
     */
    public void queryWindow(ArrayList result, Rectangle2D queryBBox) {
        queryWindow(result, queryBBox, 0, 1);
    }

    /**
     * Return all objects larger than a minimum size that overlap a bounding rectangle.
     * @param result the objects found.
     * @param queryBBox the bounding box.
     * @param minSize miminum size of the objects to be found.
     * @param currentMag the current magnification.
     */
    public void queryWindow(ArrayList result, Rectangle2D queryBBox, double minSize, double currentMag) {
        if (status == false) {
            return;
        }

        result.clear();
        nodesSearched = 0;
        root.queryWindow(result, queryBBox, minSize, currentMag);

        Object resultArray[] = result.toArray();
        Arrays.sort(resultArray, doc);

        result.clear();
        for (int i=0; i<resultArray.length; i++) {
            result.add(resultArray[i]);
        }
    }

    /**
     * For debugging, print a depiction of the RTree index tree.
     * @param tree a name for this tree.
     */
    public void displayTree(String tree) {
        if (status == false) {
            return;
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        root.displayTree(tree, 0);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * Get the minimum number of children per node.
     */
    public int getMinChildren() {
        return (minChildren);
    }
    /**
     * Get the maximum number of children per node.
     */
    public int getMaxChildren() {
        return (maxChildren);
    }

    /**
     * Set the minimum number of children per node.
     * @param aMinChildren the minimum number of children.
     */
    public void setMinChildren(int aMinChildren) {
        minChildren = aMinChildren;
        reIndex();
    }

    /**
     * Set the maximum number of children per node.
     * @param aMaxChildren the maximum number of children.
     */
    public void setMaxChildren(int aMaxChildren) {
        maxChildren = aMaxChildren;
        reIndex();
    }

    /**
     * Return local bounds for an indexed object, modified by any transform
     * apearing between the node and it's group node.
     */
    private ZBounds getCurrentBounds(ZNode obj) {
        ZBounds bbox;
        bbox = obj.getBounds();
        if (obj.editor().hasTransformGroup()) {
            bbox.transform(obj.editor().getTransformGroup().getTransform());
        }
        bbox.transform(groupNodeTransform);
        return(bbox);
    }

    //****************************************************************************
    //
    //       Inner Classes: RTreeNode, RTreeNodeInternal, RTreeNodeLeaf
    //
    //***************************************************************************

    class RTreeNodeInternal extends RTreeNode {
        protected RTreeNode[] child;
        protected int numChildren;

        RTreeNodeInternal() {
            int i;
            int aMaxChildren;

            aMaxChildren = getMaxChildren();
            numChildren = 0;
            child = new RTreeNode[aMaxChildren];
            for (i=0; i<aMaxChildren; i++) {
                child[i] = null;
            }
        }

        RTreeNodeInternal(RTreeNode node) {
            int i;
            int aMaxChildren;

            aMaxChildren = getMaxChildren();
            numChildren = 0;
            child = new RTreeNode[aMaxChildren];
            for (i=0; i<aMaxChildren; i++) {
                child[i] = null;
            }

            add(node);
        }

        /**
         * Internal nodes are not leaves
         * @return false.
         */
        boolean isLeaf() {
            return(false);
        }

        /**
         * Return number of nodes pointed to by this node
         * @return the number
         */
        int getNumRegions()
        {
            return(numChildren);
        }

        /**
         * Return specified region
         * @return a rectangle specifying the region.
         */
        Rectangle2D getChildRegion(int regionNum) {
            if (regionNum < numChildren) {
                return(child[regionNum].getRegion());
            } else {
                return(null);
            }
        }

        /**
         * Returns the specified child node
         * @return the node.
         */
        RTreeNode getChildNode(int nodeNum) {
            if (nodeNum < numChildren) {
                return(child[nodeNum]);
            } else {
                return(null);
            }
        }

        /**
         * Objects can not be added to internal nodes.
         * @param the object
         */
        void add(ZNode obj) {
            System.err.println("add(): ERROR: Can't add object directly to internal node");
            System.exit(1);
        }

        /**
         * Add the specified node to be a child of the current node.
         * If this node is already full, this will result in a fatal error.
         * @param node A node
         */
        void add(RTreeNode node) {
            if (numChildren == getMaxChildren()) {
                System.err.println("add(): ERROR: Node is full, can't add child");
                System.exit(1);
            }
            child[numChildren] = node;
            numChildren++;
            updateRegion();
        }

        /**
         * Add object to the sub-tree starting at this node
         * If that results in a split, this will return TRUE,
         * and two new nodes will be generated that contain
         * the existing children plus the new one.
         * NOTE: It is up to the caller to replace this node
         * with the two new ones in the case of a split.
         * @param obj the object
         * @param nodeArray sub-tree
         */
        boolean add(ZNode obj, RTreeNode[] nodeArray) {
            int i;
            boolean split = false;
            boolean first = true;

                                // Child to add object to
            int aChild=0;

                                // Amount region would be expanded if object is added
            double expansion;

                                // Min expansion of all regions
            double minExpansion=0;

            double expansion1, expansion2;
            Rectangle2D bbox;
            RTreeNode nodeToAdd;
            RTreeNode node1, node2;

            node1 = nodeArray[0];
            node2 = nodeArray[1];

                                // Determine appropriate child and descend tree
            bbox = getCurrentBounds(obj);
            for (i=0; i<numChildren; i++) {
                expansion = child[i].computeExpansion(bbox);
                if (first) {
                    minExpansion = expansion;
                    aChild = i;
                    first = false;
                } else {
                    if (expansion < minExpansion) {
                        minExpansion = expansion;
                        aChild = i;
                    }
                }
            }
                                // Best child to add obj to has been found, so
                                // recursively add object to that region

            if (child[aChild].add(obj, nodeArray)) {
                                // Adding caused a split, so add new nodes to self
                child[aChild] = nodeArray[0];
                if (numChildren == getMaxChildren()) {
                                // Self is full, so split self, and add extra node
                                // to appropriate new node
                    nodeToAdd = nodeArray[1];
                    split(nodeArray);
                    node1 = nodeArray[0];
                    node2 = nodeArray[1];

                    expansion1 = node1.computeExpansion(nodeToAdd.getRegion());
                    expansion2 = node2.computeExpansion(nodeToAdd.getRegion());
                    if (expansion1 < expansion2) {
                        node1.add(nodeToAdd);
                    } else {
                        node2.add(nodeToAdd);
                    }
                    nodeArray[0] = node1;
                    nodeArray[1] = node2;
                    split = true;
                } else {
                    add(nodeArray[1]);
                    split = false;
                }
            } else {
                updateRegion();
            }
            return(split);
        }

        /**
         * Split this node and redistribute children.
         * Return two newly allocated nodes that replace self.
         * NOTE: It is up to the caller of this function to delete
         * this node and replace it with the two newly generated nodes.
         * @param nodeArray the tree
         */
        void split(RTreeNode[] nodeArray) {
            int i;
            int region1 = 0;
            int region2 = 0;
            int aMinChildren;
            int remainingObjs;
            double expansion1, expansion2;

                                // Use simple linear splitting algorithm.
                                // It starts by finding the two rectangles
                                // with furthest apart corners, and then
                                // adding the rest to the region that
                                // requires the least expansion.
            int[] regions = computeMostDistantRegions();
            region1 = regions[0];
            region2 = regions[1];

                                // Create two regions representing these two new rectangles
            RTreeNodeInternal node1 = new RTreeNodeInternal(child[region1]);
            RTreeNodeInternal node2 = new RTreeNodeInternal(child[region2]);

                                // Now, add the rest of the nodes to
                                // one of the two new nodes
            aMinChildren = getMinChildren();

                                // number of objs to add doesn't include objs already in new nodes
            remainingObjs = numChildren - 2;
            for (i=0; i<numChildren; i++) {
                if ((i == region1) || (i == region2)) {
                    continue;
                }
                                // If one node isn't getting enough children,
                                // then put the rest of the objects there to
                                // insure that minChildren is maintained.
                if ((node1.getNumRegions() + remainingObjs) <= aMinChildren) {
                    node1.add(child[i]);
                } else if ((node2.getNumRegions() + remainingObjs) <= aMinChildren) {
                    node2.add(child[i]);
                } else {
                    expansion1 = node1.computeExpansion(child[i].getRegion());
                    expansion2 = node2.computeExpansion(child[i].getRegion());
                    if (expansion1 < expansion2) {
                        node1.add(child[i]);
                    } else {
                        node2.add(child[i]);
                    }
                }
                remainingObjs--;
            }
            nodeArray[0] = node1;
            nodeArray[1] = node2;
        }

        /**
         * Remove object from the subtree starting at this node
         * Return true if object was found, and thus removed.
         * @param obj the object
         * @param reinsertList
         * @return true if object was removed, false otherwise.
         */
        protected boolean remove(ZNode obj, ArrayList reinsertList) {
            int i, j;
            int aMinChildren;
            boolean rc = false;

                                // Try to remove object from each child region
                                // that overlaps object
            aMinChildren = getMinChildren();
            for (i=0; i<numChildren; i++) {

                Rectangle2D reg = child[i].getRegion();
                Rectangle2D glob = getCurrentBounds(obj);
                boolean overlaps = ! (reg.createUnion(glob)).isEmpty();
                if (overlaps) {
                    rc = child[i].remove(obj, reinsertList);
                    if (rc) {
                        if (child[i].getNumRegions() < aMinChildren) {
                                // Child is now too small, so remove it and reinsert afterwards
                            reinsertList.add(child[i]);
                            for (j=i; j<(numChildren - 1); j++) {
                                child[j] = child[j + 1];
                            }
                            numChildren--;
                        }
                        updateRegion();
                        break;
                    }
                }
            }

            return(rc);
        }

        /**
         * Update region's bounding box to match members
         */
        void updateRegion() {
            int i;

            if (numChildren == 0) {
                region.setRect(0, 0, 0, 0);
            }

            region = child[0].getRegion();
            for (i=1; i<numChildren; i++) {
                region = (Rectangle2D)region.createUnion(child[i].getRegion());
            }
        }

        /**
         * Extract objects from node and add to list
         * @param objList the objects
         */
        void extractObjs(ArrayList objList) {
            int i;

            for (i=0; i<numChildren; i++) {
                child[i].extractObjs(objList);
            }
        }

        /**
         * For debugging, print a depiction of this node
         * @param tree a name for this tree.
         */
        public void displaySelf(String tree) {
            System.out.println(tree + "internal node: x: " + region.getX() + " y: " + region.getY() + "w: " + region.getWidth() + " h: " + region.getHeight());
        }

        /**
         * For debugging, print a depiction of the index tree
         * rooted here.
         * @param tree a name for this tree
         * @level the current print level.
         */
        public void displayTree(String tree, int level) {
            int i, j;

            for (j=0; j<level; j++) {
                tree += "  ";
            }
            displaySelf(tree);
            System.out.println("children: "+numChildren);
            for (i=0; i<numChildren; i++) {
                child[i].displayTree(tree, level + 1);
            }
        }

        /**
         * Return all objects that overlap a point.
         * @param result a list of the objects.
         * @param pt the point
         */
        void queryPoint(ArrayList result, Point2D pt) {
            queryPoint(result, pt, 0, 1);
        }

        /**
         * Return objects larger than a minimum size that overlap a point.
         * @param result a list of the objects.
         * @param pt the point
         * @param minSize the minimum dimension of objects found.
         * @param currentMag the current magnification.
         */
        void queryPoint(ArrayList result, Point2D pt, double minSize, double currentMag) {
            int i;
            Rectangle2D bbox;

                                // Search children in each region that overlaps query
            for (i=0; i<numChildren; i++) {
                incrementNodesSearched();
                bbox = child[i].getRegion();
                double maxDim = Math.max(bbox.getHeight(), bbox.getWidth()) * currentMag;

                if (bbox.contains(pt.getX(), pt.getY()) &&
                    (maxDim >= minSize)) {
                    child[i].queryPoint(result, pt, minSize, currentMag);
                }
            }
        }

        /**
         * Return all objects that overlap a bounding rectangle.
         * @param result the objects
         * @param queryBBox the bounding box
         */
        void queryWindow(ArrayList result, Rectangle2D queryBBox) {
            queryWindow(result, queryBBox, 0, 1);
        }

        /**
         * Return objects overlapping a bounding rectangle.
         * @param result the objects
         * @param queryBBox the bounding box
         * @param minSize the minimum dimension of objects found.
         */
        void queryWindow(ArrayList result, Rectangle2D queryBBox, double minSize, double currentMag) {
            int i;
            Rectangle2D bbox;

                                // Search children in each region that overlaps query
            for (i=0; i<numChildren; i++) {
                incrementNodesSearched();
                bbox = child[i].getRegion();
                double maxDim = Math.max(bbox.getHeight(), bbox.getWidth()) * currentMag;
                boolean overlaps = bbox.intersects(queryBBox.getX(), queryBBox.getY(), queryBBox.getWidth(), queryBBox.getHeight());

                if (overlaps && (maxDim >= minSize)) {
                    child[i].queryWindow(result, queryBBox, minSize, currentMag);
                }
            }
        }
        }

class RTreeNodeLeaf extends RTreeNode {
    int numObjs;
    ZNode[] object;

    RTreeNodeLeaf() {
        int i;
        int aMaxChildren;

        aMaxChildren = getMaxChildren();
        numObjs = 0;
        object = new ZNode[aMaxChildren];
        for (i=0; i<aMaxChildren; i++) {
            object[i] = new ZNode();
        }
    }

    RTreeNodeLeaf(ZNode obj) {
        int i;
        int aMaxChildren;

        aMaxChildren = getMaxChildren();
        numObjs = 0;
        object = new ZNode[aMaxChildren];
        for (i=0; i<aMaxChildren; i++) {
            object[i] = new ZNode();
        }

        add(obj);
    }

    /**
     * Leaf nodes are leaves
     * @return true
     */
    boolean isLeaf() {
        return(true);
    }

    /**
     * Return number of objects to by this node
     * @return the number
     */
    int getNumRegions() {
        return(numObjs);
    }

    /**
     * Return the specified region
     * @param regionNum the index of the region
     * @return the region
     */
    Rectangle2D getChildRegion(int regionNum) {
        if (regionNum < numObjs) {
            return(getCurrentBounds(object[regionNum]));
        } else {
            return(null);
        }
    }

    /**
     * Add the specified object to be a member of the current node.
     * If this node is already full, this will result in a fatal error.
     * @param obj the object
     */
    void add(ZNode obj) {
        if (numObjs == getMaxChildren()) {
            System.err.println("add(): ERROR: Node is full, can't add object");
            System.exit(1);
        }

        object[numObjs] = obj;
        numObjs++;
        updateRegion();
    }

    /**
     * Add the specified node to be a child of the current node.
     * If this node is already full, this will result in a fatal error.
     * @param node the node
     */
    void add(RTreeNode node) {
        System.err.println("add(): ERROR: Can't add node directly to leaf node");
        System.exit(1);
    }

    /**
     * Add object to the sub-tree starting at this node
     * If that results in a split, this will return true,
     * and two new nodes will be generated that contain
     * the existing children plus the new one.
     * NOTE: It is up to the caller to replace this node
     * with the two new ones in the case of a split.
     * @param obj the object to add
     * @param nodeArray the sub-tree
     */
    boolean add(ZNode obj, RTreeNode[] nodeArray) {
        double expansion1, expansion2;
        boolean split;
        RTreeNode node1, node2;
                                // Determine if node is full
        if (numObjs == getMaxChildren()) {
                                // Node is full, so split
            split(nodeArray);
            node1 = nodeArray[0];
            node2 = nodeArray[1];
            expansion1 = node1.computeExpansion(getCurrentBounds(obj));
            expansion2 = node2.computeExpansion(getCurrentBounds(obj));
            if (expansion1 < expansion2) {
                node1.add(obj);
            } else {
                node2.add(obj);
            }
            split = true;
            nodeArray[0] = node1;
            nodeArray[1] = node2;

        } else {
                                // Node not full, so add it
            add(obj);
            split = false;
        }

        return(split);
    }

    /**
     * Split this node and redistribute children.
     * Return two newly allocated nodes that replace self.
     * NOTE: It is up to the caller of this function to delete
     * this node and replace it with the two newly generated nodes.
     * @param nodeArray the tree
     */
    void split(RTreeNode[] nodeArray) {
        int i;
        int region1 = 0;
        int region2 = 0;
        int aMinChildren;
        int remainingObjs;
        double expansion1, expansion2;

                                // Use simple linear splitting algorithm.
                                // It starts by finding the two rectangles
                                // with furthest apart corners, and then
                                // adding the rest to the region that
                                // requires the least expansion.

        int[] regions = computeMostDistantRegions();
        region1 = regions[0];
        region2 = regions[1];


                                // Create two regions representing these two new rectangles
        RTreeNodeLeaf node1 = new RTreeNodeLeaf(object[region1]);
        RTreeNodeLeaf node2 = new RTreeNodeLeaf(object[region2]);

                                // Now, add the rest of the objects to
                                // one of the two new nodes
        aMinChildren = getMinChildren();

                                // number of objs to add doesn't include objs already in new nodes
        remainingObjs = numObjs - 2;

        for (i=0; i<numObjs; i++) {
            if ((i == region1) || (i == region2)) {
                continue;
            }
                                // If one node isn't getting enough children,
                                // then put the rest of the objects there to
                                // insure that minChildren is maintained.
            if ((node1.getNumRegions() + remainingObjs) <= aMinChildren) {
                node1.add(object[i]);
            } else if ((node2.getNumRegions() + remainingObjs) <= aMinChildren) {
                node2.add(object[i]);
            } else {
                                // Else, compute the best place for this object
                expansion1 = node1.computeExpansion(getCurrentBounds(object[i]));
                expansion2 = node2.computeExpansion(getCurrentBounds(object[i]));
                if (expansion1 < expansion2) {
                    node1.add(object[i]);
                } else {
                    node2.add(object[i]);
                }
            }
            remainingObjs--;
        }
        nodeArray[0] = node1;
        nodeArray[1] = node2;
    }

    /**
     * Remove object from this node.
     * Return true if object was found, and thus removed.
     * @param obj the object
     * @param list not used.
     */
    boolean remove(ZNode obj, ArrayList list) {
        int i, j;
        boolean rc = false;

                                // Check if object is here
        for (i=0; i<numObjs; i++) {
            if (object[i] == obj) {
                                // Object found - remove it
                for (j=i; j<(numObjs - 1); j++) {
                    object[j] = object[j + 1];
                }
                numObjs--;
                updateRegion();
                rc = true;
                break;
            }
        }

        return(rc);
    }

    /**
     * Update region's bounding box to match members
     */
    void updateRegion() {
        int i;

        if (numObjs == 0) {
            region.setRect(0, 0, 0, 0);
        }

        region = getCurrentBounds(object[0]);
        for (i=1; i<numObjs; i++) {
            region = (Rectangle2D)region.createUnion(getCurrentBounds(object[i]));
        }
    }

    /**
     * Extract objects from node and add to list
     * @param objList result list
     */
    void extractObjs(ArrayList objList) {
        int i;

        for (i=0; i<numObjs; i++) {
            objList.add(object[i]);
        }
    }

    /**
     * For debugging, print a depiction of this node
     * @param tree a name for this tree
     */
    public void displaySelf(String tree) {
        int i;
        String buf = null;

        System.out.println(tree + "leaf node x: " + region.getX() + " y: " + region.getY() + "w: " + region.getWidth() + " h: " + region.getHeight());
        tree += buf;

        System.out.println("");
        System.out.println("{");
        for (i=0; i<numObjs; i++) {
            System.out.println(object[i].toString());
            System.out.println("  "+getCurrentBounds(object[i]));
        }
        System.out.println("}\n");
    }

    /**
     * For debugging, print a depiction of the index tree
     * rooted here.
     * @param tree a name for this tree
     * @param level the current print level
     */
    public void displayTree(String tree, int level) {
        int i;

        for (i=0; i<level; i++) {
            tree += "  ";
        }
        displaySelf(tree);
    }

    /**
     * Return all objects that overlap a point.
     * @param result return list of the objects found.
     * @param pt the point
     */
    void queryPoint(ArrayList result, Point2D pt) {
        queryPoint(result, pt, 0, 1);
    }

    /**
     * Return objects larger than a minimum size that overlap a point.
     * @param result return list of the objects found.
     * @param pt the point
     * @param minSize return only objects larger than this size
     * @param currentMag the current magnification.
     */
    void queryPoint(ArrayList result, Point2D pt, double minSize, double currentMag) {
        int i;
        Rectangle2D bbox;

                                // Check each object
        for (i=0; i<numObjs; i++) {
            incrementNodesSearched();
            bbox = getCurrentBounds(object[i]);
            double maxDim = (Math.max(bbox.getHeight(), bbox.getWidth())) * currentMag;
            if (bbox.contains(pt.getX(), pt.getY()) &&
                (maxDim >= minSize)) {
                result.add(object[i]);
            }
        }
    }

    /**
     * Return all objects that overlap a bounding rectangle.
     * @param result the objects returned.
     * @param queryBBox the bounding box to search
     */
    void queryWindow(ArrayList result, Rectangle2D queryBBox) {
        queryWindow(result, queryBBox, 0, 1);
    }

    /**
     * Return objects overlapping bounding rectangle.
     * @param result the objects returned.
     * @param queryBBox the bounding box to search
     * @param minSize return only objects larger than this size adjusted for magnification
     * @param currentMag the current magnification
     */
    void queryWindow(ArrayList result, Rectangle2D queryBBox, double minSize, double currentMag) {
        int i;
        Rectangle2D bbox;
                                // Check each object
        for (i=0; i<numObjs; i++) {
            incrementNodesSearched();
            bbox = getCurrentBounds(object[i]);

            double maxDim = Math.max(bbox.getHeight(), bbox.getWidth()) * currentMag;

            boolean overlaps = bbox.intersects(queryBBox.getX(), queryBBox.getY(), queryBBox.getWidth(), queryBBox.getHeight());

            if (overlaps && (maxDim >= minSize)) {
                result.add(object[i]);
            }
        }
    }
}
    abstract class RTreeNode {

        Rectangle2D region;

        RTreeNode() {
            region = new Rectangle2D.Double();
        }

                                // Add object to be a member of this node
        abstract void      add(ZNode obj);

                                // Add node to be a child of this node
        abstract void      add(RTreeNode node);

                                // Add object to the sub-tree starting at this node
        abstract boolean  add(ZNode obj, RTreeNode[] nodeArray);

                                // Extract objects from node and add to list
        abstract void      extractObjs(ArrayList objList);

        abstract int       getNumRegions();

        abstract Rectangle2D getChildRegion(int regionNum);

                                // Returns true if node is a leaf node
        abstract boolean  isLeaf();

                                // Remove object from the subtree starting at this node
        abstract boolean  remove(ZNode obj, ArrayList reinsertList);

                                // Update region to match members
        abstract void      updateRegion();

                                // Query index to find objects within specified area
                                // no smaller than the minimum dimension.
        abstract void      queryPoint(ArrayList result, Point2D pt, double minSize, double currentMag);

                                // no minimum dimension
        abstract void      queryPoint(ArrayList result, Point2D pt);

        abstract void      queryWindow(ArrayList result, Rectangle2D bbox, double minSize, double currentMag);

        abstract void      queryWindow(ArrayList result, Rectangle2D bbox);

        abstract void      displaySelf(String tree);

        abstract void      displayTree(String tree, int level);

        /**
         * Compute how much the specified region would have to expand
         * to accomodate the specified object.
         * @param aRegion the region.
         */
        double computeExpansion(Rectangle2D aRegion) {
            Rectangle2D bbox;
            double expansion;

            bbox = region;
            bbox.union(bbox, aRegion, bbox);
            expansion = ((bbox.getHeight() * bbox.getWidth()) - (region.getHeight() * region.getWidth()));

            return(expansion);
        }

        /**
         * This finds the two regions that are members of this node that
         * have furthest apart corners.
         * It fills in the index parameters to the two regions.
         * @return two ints, the two regions.
         */
        int[] computeMostDistantRegions() {
            int i;
            int regions;
            int llrect, urrect;
            double val, llval, urval;

            regions = getNumRegions();

            if (regions < 2) {
                System.err.println("RTreeNode::ComputeMostDistantRegions: ERROR: Less than 2 regions");
                System.exit(1);
            }

                                // First, find the two regions with
                                // most extreme corners.
            llval = (getChildRegion(0).getX() + getChildRegion(0).getY());
            urval = ((getChildRegion(0).getX() + getChildRegion(0).getWidth())+ (getChildRegion(0).getY() + getChildRegion(0).getHeight()));
            llrect = 0;
            urrect = 0;
            for (i=1; i<regions; i++) {
                val = (getChildRegion(i).getX() + getChildRegion(i).getY());
                if (val < llval) {
                    llval = val;
                    llrect = i;
                }
                val = ((getChildRegion(i).getX() + getChildRegion(i).getHeight() ) + (getChildRegion(i).getY() + getChildRegion(i).getWidth()));
                if (val > urval) {
                    urval = val;
                    urrect = i;
                }
            }
                                // If the result is a single rectangle with the most extreme corners,
                                // then this means that it encloses all the others, so find
                                // next most extreme rectangle.
            if (llrect == urrect) {
                int rect;
                double minllval, maxurval;
                boolean first = true;

                                // First, store values of max rect
                rect = llrect;
                minllval = llval;
                maxurval = urval;

                                // Then, compute values for all other rects
                for (i=0; i<regions; i++) {
                    if (i == rect) {
                        continue;
                    }
                    if (first) {
                        llval = (getChildRegion(i).getX() + getChildRegion(i).getY());
                        urval = ((getChildRegion(i).getX() + getChildRegion(i).getWidth() ) + (getChildRegion(i).getY() + getChildRegion(i).getHeight()));
                        llrect = i;
                        urrect = i;
                        first = false;
                        continue;
                    }
                    val = (getChildRegion(i).getX() + getChildRegion(i).getY());
                    if (val < llval) {
                        llval = val;
                        llrect = i;
                    }
                    val = ((getChildRegion(i).getX() + getChildRegion(i).getWidth()) + (getChildRegion(i).getY() + getChildRegion(i).getHeight()));
                    if (val > urval) {
                        urval = val;
                        urrect = i;
                    }
                }
                                // Then, decide which should be second rect
                if ((llval - minllval) > (maxurval - urval)) {
                    llrect = rect;
                } else {
                    urrect = rect;
                }
            }

            int aRegion[] = new int[2];
            aRegion[0] = llrect;
            aRegion[1] = urrect;
            return(aRegion);
        }

        /**
         * Return region of this node.
         * @return the rectangular region.
         */
        Rectangle2D getRegion() {
            return(region);
        }
    }
}
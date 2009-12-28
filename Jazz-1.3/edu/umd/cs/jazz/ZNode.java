/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.event.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;

/**
 * <b>ZNode</b> is the common superclass of all objects in a
 * Jazz scenegraph. It has very limited functionality, and
 * primarily exists to support sub-classes.
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
public class ZNode extends ZSceneGraphObject implements ZSerializable, Serializable {
                                // Default values
    static public final boolean savable_DEFAULT = true;      // True if this node gets saved
    static public final boolean pickable_DEFAULT = true;     // True if this node is pickable
    static public final boolean findable_DEFAULT = true;     // True if this node is findable
    static public final boolean selectable_DEFAULT = true;     // True if this node is selectable
    static final boolean hasNodeListener_DEFAULT = false; // True if this node has a global bounds listener

                                // Create the default editor factory
    static public ZSceneGraphEditorFactory editorFactory = new ZSceneGraphEditorFactory() {
        public ZSceneGraphEditor createEditor(ZNode node) {
            return new ZSceneGraphEditor(node);
        }
    };

    /**
     * This node's parent.
     */
    public ZGroup parent;

    /**
     *  True if this node should be saved
     */
    private boolean savable = savable_DEFAULT;

    /**
     *  True if this node is pickable
     */
    private boolean pickable = pickable_DEFAULT;

    /**
     *  True if this node is findable
     */
    private boolean findable = findable_DEFAULT;

    /**
     *  True if this node is selectable
     */
    private boolean selectable = selectable_DEFAULT;

    /**
     * True if this node has a global bounds listener
     * (package private for access in ZGroup)
     */
    boolean hasNodeListener = hasNodeListener_DEFAULT;

    //****************************************************************************
    //
    //                Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new ZNode.  The node must be attached to a live scenegraph (a scenegraph that is
     * currently visible) order for it to be visible.
     */
    public ZNode () {
        parent = null;
    }

    /**
     * Adds the specified node listener to receive node events from this node.
     * Also updates the hasNodeListener bit.
     *
     * @param l the node listener
     */
    public void addNodeListener(ZNodeListener l) {
        getListenerList().add(ZNodeListener.class, l);

        if (!hasNodeListener) {
            hasNodeListener = true;
            if (parent != null) {
                parent.updateHasNodeListener();
            }
        }
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZNode newNode = (ZNode)super.duplicateObject();

        newNode.parent = null;  // No parent - but see ZGroup.duplicateObject.

        return newNode;
    }

    /**
     * This returns a new instance of a ZSceneGraphEditor for this node.
     * ZSceneGraphEditor provides a convenience mechanism used to locate
     * and create instances of several node types.  The specific nodes
     * supported are defined by the particular editor being used.
     *
     * @see edu.umd.cs.jazz.util.ZSceneGraphEditor
     * @see edu.umd.cs.jazz.util.ZSceneGraphEditorFactory
     */
    public ZSceneGraphEditor editor() {
        return editorFactory.createEditor(this);
    }

    /**
     * Extract this node from the tree, merging back in any children. As ZNode's
     * do not have any children, ZNode.extract() has the same function as ZNode.remove().
     * However, extract() on subclasses such as ZGroup truly extract a node, merging
     * any children back into the scenegraph.
     * @see ZGroup#extract()
     */
    public void extract() {
        remove();
    }

    /**
     * Internal method used to return the list of nodes that are accepted by the specified filter in the
     * subtree rooted with this.  If this node is not "findable", then neither
     * this node, nor any of its descendants will be included.
     * The filter specifies whether or not this node should be accepted by the
     * search, and whether the node's children should be searched.
     * @param filter The filter that decides whether or not to include individual nodes in the find list
     * @param nodes the accumulation list (results will be place here).
     * @return the number of nodes searched
     * @see #isFindable()
     * @see ZFindFilter
     */
    protected int findNodes(ZFindFilter filter, ArrayList nodes) {
        int nodesSearched = 1;
                                // Only search if node is findable.
        if (findable) {
                                // Check if this node is accepted by the filter
            if (filter.accept(this)) {
                nodes.add(this);
            }
        }
        return nodesSearched;
    }

    /**
     * Return a copy of the bounds of the subtree rooted at this node in global coordinates.
     * Note that global bounds are not cached, and this method involves some computation.
     * @return The bounds of the subtree rooted at this node in global coordinates.
     */
    public ZBounds getGlobalBounds() {
        ZBounds globalBounds = getBounds();
        if (parent != null) {
            AffineTransform at = parent.getLocalToGlobalTransform();
            globalBounds.transform(at);
        }

        return globalBounds;
    }

    /**
     * Return the transform that converts global coordinates at the root node
     * to local coordinates at this node.
     * @return The inverse of the concatenation of transforms from the root down to this node.
     */
    public AffineTransform getGlobalToLocalTransform() {
        AffineTransform at = getLocalToGlobalTransform();
        AffineTransform globalToLocal = null;

        try {
            globalToLocal = at.createInverse();
        } catch (NoninvertibleTransformException exc) {
            throw new ZNoninvertibleTransformException(exc);
        }

        return globalToLocal;
    }

    /**
     * Return the transform that converts local coordinates at this node to global coordinates
     * at the root node.
     * @return The concatenation of transforms from the root down to this node.
     */
    public AffineTransform getLocalToGlobalTransform() {
        AffineTransform result = null;

        if (parent != null) {
            result = parent.getLocalToGlobalTransform();
        } else {
            result = new AffineTransform();
        }

        return result;
    }

    /**
     * Get the node's parent.
     */
    public final ZGroup getParent() {
        return parent;
    }

    /**
     * Traverse the tree, find the root node, and return it.
     * @return The root node of this scenegraph
     * @see ZRoot
     */
    public ZRoot getRoot() {
        ZNode node = this;

        do {
            if (node instanceof ZRoot) {
                return (ZRoot)node;
            }
            node = node.getParent();
        } while (node != null);

        return null;
    }

    /**
     * Transform the specified dimension (in global coordinates) to local coordinates
     * in this node's coordinate system.
     * The input dimension is modified by this method.
     * <P>
     * NOTE: Dimension2D's are abstract. When creating a new Dimension2D for use with Jazz
     * we recoment that you use edu.umd.cs.util.ZDimension instead of java.awt.Dimension.
     * ZDimension uses doubles internally, while java.awt.Dimension uses integers.
     * <p>
     * @return dz The change in scale from global coordinates to the node's local coordinate system.
     * @see #localToGlobal
     */
    public double globalToLocal(Dimension2D aDimension) {
        AffineTransform at = getLocalToGlobalTransform();

        try {
            return ZUtil.inverseTransformDimension(aDimension, at);
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }
    }

    /**
     * Transform the specified point (in global coordinates) to local coordinates
     * in this node's coordinate system.
     * The input point is modified by this method.
     * It also returns the change in scale from the global coordinate system
     * to the node's local coordinate system.
     * @return dz The change in scale from global coordinates to the node's local coordinate system.
     * @see #localToGlobal
     */
    public double globalToLocal(Point2D pt) {
        double dz = 1.0;
        AffineTransform at = getLocalToGlobalTransform();
        try {
            at.inverseTransform(pt, pt);
            dz = 1 / Math.max(at.getScaleX(), at.getScaleY());
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }

        return dz;
    }

    /**
     * Transform the specified rectangle (in global coordinates) to local coordinates
     * in this node's coordinate system.
     * The input rectangle is modified by this method.
     * It also returns the change in scale from the global coordinate system
     * to the node's local coordinate system.
     * @return dz The change in scale from global coordinates to the node's local coordinate system.
     * @see #localToGlobal
     */
    public double globalToLocal(Rectangle2D rect) {
        AffineTransform at = getLocalToGlobalTransform();
        try {
            return ZUtil.inverseTransformRectangle(rect, at);
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }
    }

    /**
     * Determines if this node has a node listener.
     * If this node does not have a node listener, it will not
     * receive global bounds events.
     */
    public final boolean hasNodeListener() {
        return hasNodeListener;
    }

    /**
     * Method to determine if this is an ancenstor of queryNode.
     * @param queryNode a possible descendent of node
     * @return true of queryNode is an descendent of node.
     */
    public boolean isAncestorOf(ZNode queryNode) {
        return queryNode.isDescendentOf(this);
    }

    /**
     * Method to determine if this is a descendent of queryNode.
     * @param queryNode a possible ancenstor of node
     * @return true of queryNode is an ancestor of node.
     */
    public boolean isDescendentOf(ZNode queryNode) {
        ZNode node = parent;
        boolean found = false;

        while (node != null) {
            if (node == queryNode) {
                found = true;
                break;
            }
            node = node.getParent();
        }
        return found;
    }

    /**
     * Determines if this node is findable.
     * When a node is not findable, it will be ignored by
     * the ZNode find methods.
     * @return True if this node is findable
     */
    public final boolean isFindable() {
        return findable;
    }

    /**
     * Determines if this node is pickable.
     * When a node is not pickable, it will be ignored by
     * the ZNode pick methods that determine which object is under a point.
     * @return True if this node is pickable
     */
    public final boolean isPickable() {
        return pickable;
    }

    /**
     * Determine if this node gets saved when written out.
     * @return true if this node gets saved.
     */
    public final boolean isSavable() {
        return savable;
    }

    /**
     * Determines if this node is selectable.
     * When a node is not selectable, it will be ignored by
     * the ZSelectionManager.
     * @return True if this node is selectable
     */
    public final boolean isSelectable() {
        return selectable;
    }
    /**
     * Transform the specified dimension (in this node's local coordinates) to global coordinates.
     * The input dimension is modified by this method.
     * <P>
     * NOTE: Dimension2D's are abstract. When creating a new Dimension2D for use with Jazz
     * we recoment that you use edu.umd.cs.util.ZDimension instead of java.awt.Dimension.
     * ZDimension uses doubles internally, while java.awt.Dimension uses integers.
     * <p>
     * @return dz The change in scale from global coordinates to the node's local coordinate system.
     * @see #globalToLocal
     */
    public double localToGlobal(Dimension2D aDimension) {
        AffineTransform at = getLocalToGlobalTransform();
        return ZUtil.transformDimension(aDimension, at);
    }
    /**
     * Transform the specified point (in this node's local coordinates) to global coordinates.
     * The input point is modified by this method.
     * It also returns the change in scale from the local coordinate system
     * to global coordinates.
     * @return dz The change in scale from global coordinates to the node's local coordinate system.
     * @see #globalToLocal
     */
    public double localToGlobal(Point2D pt) {
        double dz;
        AffineTransform at = getLocalToGlobalTransform();
        at.transform(pt, pt);
        dz = Math.max(at.getScaleX(), at.getScaleY());

        return dz;
    }
    /**
     * Transform the specified rectangle (in this node's local coordinates) to global coordinates.
     * The input rectangle is modified by this method.
     * It also returns the change in scale from the local coordinate system
     * to global coordinates.
     * @return dz The change in scale from global coordinates to the node's local coordinate system.
     * @see #globalToLocal
     */
    public double localToGlobal(Rectangle2D rect) {
        double dz;
        AffineTransform at = getLocalToGlobalTransform();
        ZTransformGroup.transform(rect, at);
        dz = Math.max(at.getScaleX(), at.getScaleY());
        return dz;
    }
    /**
     * Lowers this node within the drawing order of its siblings,
     * so it gets rendered below (before) all of its siblings.
     * This is done by moving this node to the beginning of its parent's child list.
     */
    public void lower() {
        if (parent != null) {
            parent.lower(this);
        }
    }
    /**
     * Lowers this node within the drawing order of its siblings,
     * so it gets rendered below (before) the specified node.
     * This is done by moving this node just before the specified node in its parent's child list.
     * If the specified node is not a sibling of this, then this call does nothing.
     * <p>
     * If the specified node is null, then this node is lowered to be the
     * first node rendered of its siblings (i.e., equivalent to calling {@link #lower}
     *
     * @param beforeNode The node to lower this node before.
     */
     public void lowerTo(ZNode beforeNode) {
         if (parent != null) {
             if (beforeNode == null) {
                 parent.lowerTo(this);
             } else {
                 parent.lowerTo(this, beforeNode);
             }
         }
    }
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type, percolate up the scenegraph
     * looking for listeners. Stop when the event is consumed. The event
     * instance is lazily created using the parameters passed into
     * the fire method.  The listener list is processed in last to
     * first order.
     * @param id The event id (BOUNDS_CHANGED or GLOBAL_BOUNDS_CHANGED)
     * @param node The node being affected.
     * @see EventListenerList
     */
    protected void percolateEventUpSceneGraph(ZEvent anEvent) {
        ZNode node = this;
        do {
            if (node.hasNodeListener) {
                node.fireEvent(anEvent);
            }
            node = node.getParent();
        } while ((node != null) && (!anEvent.isConsumed()));
    }
    //****************************************************************************
    //
    //                  Other Methods
    //
    //****************************************************************************

    /**
     * Returns the first object under the specified rectangle (if there is one)
     * in the subtree rooted with this as searched in reverse (front-to-back) order.
     * Only returns "pickable" nodes.
     * @param rect Coordinates of pick rectangle in local coordinates
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return The picked node, or null if none
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        if (pickable) {
            if (getBoundsReference().intersects(rect.getX(),
                                                rect.getY(),
                                                rect.getWidth(),
                                                rect.getHeight())) {
                path.setObject(this);
                return true;
            }
        }
        return false;
    }
    /**
     * Raises this node within the drawing order of its siblings,
     * so it gets rendered above (after) all of its siblings.
     * This is done by moving this node to the end of its parent's child list.
     */
    public void raise() {
        if (parent != null) {
            parent.raise(this);
        }
    }
    /**
     * Raises this node within the drawing order of its siblings,
     * so it gets rendered above (after) the specified node.
     * This is done by moving this node just after the specified node in its parent's child list.
     * If the specified node is not a sibling of this, then this call does nothing.
     * <p>
     * If the specified node is null, then this node is raised to be the
     * last node rendered of its siblings (i.e., equivalent to calling {@link #raise}
     *
     * @param afterNode The node to raise this node after.
     */
     public void raiseTo(ZNode afterNode) {
         if (parent != null) {
             if (afterNode == null) {
                 parent.raise(this);
             } else {
                 parent.raiseTo(this, afterNode);
             }
         }
    }
    //****************************************************************************
    //
    //                  Other Methods
    //
    //****************************************************************************

    /**
     * Remove this node, and any subtree, from the scenegraph.
     */
    public void remove() {
        if (parent != null) {
            getParent().removeChild(this);
        }
    }
    /**
     * Removes the specified node listener so that it no longer
     * receives node events from this node. Also updates the hasNodeListener
     * bit and notifies its parent of the change, if necessary.
     *
     * @param l the node listener
     */
    public void removeNodeListener(ZNodeListener l) {
        removeEventListener(ZNodeListener.class, l);

        if (listenerList == null ||
            listenerList.getListenerCount(ZNodeListener.class) == 0) {

            hasNodeListener = false;
            if (parent != null) {
                parent.updateHasNodeListener();
            }
        }
    }
    //****************************************************************************
    //
    // Painting related methods
    //
    //***************************************************************************

    /**
     * Renders this node.
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
    }
    /**
     * Repaint causes the portions of the surfaces that this object
     * appears in to be marked as needing painting, and queues events to cause
     * those areas to be painted. The painting does not actually
     * occur until those events are handled.
     * If this object is visible in multiple places because more than one
     * camera can see this object, then all of those places are marked as needing
     * painting.
     * <p>
     * Scenegraph objects should call repaint when their internal
     * state has changed and they need to be redrawn on the screen.
     * <p>
     * Important note : Scenegraph objects should call reshape() instead
     * of repaint() if the internal state change effects the bounds of the
     * shape in any way (e.g. changing penwidth, selection, transform, adding
     * points to a line, etc.)
     *
     * @see #reshape()
     */
    public void repaint() {
        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println("ZNode.repaint: this = " + this);
            if (parent != null) {
                System.out.println("ZNode.repaint: bounds = " + getBounds());
            }
        }

        if (!inTransaction && parent != null) {
            parent.repaint(getBounds());
        }
    }
    /**
     * Method to pass repaint methods up the tree.  Repaints only the
     * sub-portion of this object specified by the given ZBounds.
     * Note that the input parameter may be modified as a result of this call.
     * @param repaintBounds The bounds to repaint
     */
    public void repaint(ZBounds repaintBounds) {
        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println("ZNode.repaint(ZBounds): this = " + this);
            System.out.println("ZNode.repaint(ZBounds): bounds = " + repaintBounds);
        }

        if (!inTransaction && parent != null) {
            parent.repaint(repaintBounds);
        }
    }
    /**
     * Set the parent of this node, and transform the node in such a way that it
     * doesn't move in global coordinates.  If the node does not already have a
     * transform node associated with it, then one will be created.
     * This method operates on the handle (top) node of a decorator chain.
     * <P>
     * This method may fire NODE_ADDED or NODE_REMOVED ZGroupEvents.
     * ZGroupEvents now contain a method <code>isModificationEvent()</code> to
     * distinguish a modification event from a <bold>true</bold> node addition
     * or removal.  A modification event is one in which a node changes
     * position in a single scenegraph or between two different scenegraphs.
     * A true addition or removal event is one in which a node is first
     * added to or removed from a scenegraph.
     * @param newParent The new parent of this node.
     * @see ZGroupEvent
     */
    public void reparent(ZGroup newParent) {
        AffineTransform origAT;
        AffineTransform newParentAT;
        AffineTransform newAT = null;
        ZNode node = editor().getNode();
        ZTransformGroup transform;

        origAT = node.getLocalToGlobalTransform();
        newParentAT = newParent.getLocalToGlobalTransform();

        try {
            newAT = newParentAT.createInverse();
        } catch (NoninvertibleTransformException exc) {
            throw new ZNoninvertibleTransformException(exc);
        }
        newAT.concatenate(origAT);

        ZGroup origParent = parent;
        if (parent != null) {
            parent.removeChild(this,false);
        }
        if (newParent != null) {
            newParent.addChildImpl(this,false);
        }

        // If the new parent is null - then this was just a normal
        // removeChild and not a modification
        if (origParent != null &&
            newParent == null) {
            origParent.childRemovedNotification(this,false);
        }
        else if (origParent != null) {
            origParent.childRemovedNotification(this,true);
        }

        // If the original parent was null - then this was just a normal
        // addChild and not a modification
        if (newParent != null &&
            origParent == null) {
            parent.childAddedNotification(this,false);
        }
        else if (newParent != null) {
            parent.childAddedNotification(this,true);
        }

        transform = node.editor().getTransformGroup();
        transform.setTransform(newAT);
    }
    /**
     * Swaps this node out of the scenegraph tree, and replaces it with the specified
     * replacement node.  This node is left dangling, and it is up to the caller to
     * manage it.  The replacement node will be added to this node's parent in the same
     * position as this was.  That is, if this was the 3rd child of its parent, then
     * after calling replaceWith(), the replacement node will also be the 3rd child of its parent.
     * If this node has no parent when replace is called, then nothing will be done at all.
     *
     * @param replacement the new node that replaces the current node in the scenegraph tree.
     */
    public void replaceWith(ZNode replacement) {
        ZGroup parent = getParent();

        if (parent != null) {
        	int index = parent.indexOf(this);
        	parent.removeChild(index);   
        	parent.addChild(replacement);
        	
        	// fix up index  	
        	parent.children.remove(replacement);
        	parent.children.add(index, replacement);
        }
    }
    //****************************************************************************
    //
    // Convenience methods to manage node decorators
    //
    //***************************************************************************

    /**
     * Define how editors should be created.  This specifies a factory
     * that is used whenever an editor needs to be created.
     * @param factory The new factory to create editors with.
     * @see #editor
     */
    static public void setEditorFactory(ZSceneGraphEditorFactory factory) {
        editorFactory = factory;
    }
    /**
     * Specifies whether this node is findable.
     * When a node is not findable, it will be ignored by
     * the ZNode find methods that search the scenegraph for nodes that satisfy
     * some criteria.
     * @param findable True if this node should be findable.
     */
    public void setFindable(boolean findable) {
        this.findable = findable;
    }
    //****************************************************************************
    //
    // Get/Set pairs
    //
    //***************************************************************************

    /**
     * Set the parent of this node.  If it already was in the tree, then
     * this moves the node to a new place in the tree specified by the new parent.
     * This is equivalent to removing this from its original parent, and adding
     * it to its new parent.
     * @param newParent The new parent of this node.
     */
    public void setParent(ZGroup newParent) {
        if (parent != null) {
            parent.removeChild(this);
        }
        if (newParent != null) {
            newParent.addChild(this);
        }
    }
    /**
     * Specifies whether this node is pickable.
     * When a node is not pickable, it will be ignored by
     * the ZNode pick methods that determine which object is under a point.
     * @param pickable True if this node should be pickable.
     */
    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }
    /**
     * Specify if this node should be saved.  If not, then all references to this
     * will be skipped in saved files.
     * @param s true if node should be saved
     */
    public void setSavable(boolean s) {
        savable = s;
    }
    /**
     * Specifies whether this node is selectable.
     * When a node is not selectable, it will be ignored by
     * the ZSelectionManager.
     * @param findable True if this node should be selectable.
     */
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
    /**
     * Set some state of this object as it gets read back in.
     * After the object is created with its default no-arg constructor,
     * this method will be called on the object once for each bit of state
     * that was written out through calls to ZObjectOutputStream.writeState()
     * within the writeObject method.
     * @param fieldType The fully qualified type of the field
     * @param fieldName The name of the field
     * @param fieldValue The value of the field
     */
    public void setState(String fieldType, String fieldName, Object fieldValue) {
        super.setState(fieldType, fieldName, fieldValue);

        if (fieldName.compareTo("pickable") == 0) {
            setPickable(((Boolean)fieldValue).booleanValue());
        } else if (fieldName.compareTo("findable") == 0) {
            setFindable(((Boolean)fieldValue).booleanValue());
        } else if (fieldName.compareTo("selectable") == 0) {
            setSelectable(((Boolean)fieldValue).booleanValue());
        }
    }
    /**
     * Internal method that causes this node and all of its ancestors
     * to invalidate their bounds. The bounds will get recomputed on the
     * next call to getBoundsReference();
     */
    protected void updateBounds() {
        super.updateBounds();

        if (parent != null && !parent.invalidBounds) {
            parent.updateBounds();
        }
    }

    /**
     * Internal method that causes this node and all of its ancestors
     * to invalidate their volatileBounds. The volatileBounds will get recomputed on the
     * next call to getVolatileBounds();
     * All parents of this node are also volatile when this is volatile.
     * @see #setVolatileBounds(boolean)
     * @see #getVolatileBounds()
     */
    protected void updateVolatility() {
        super.updateVolatility();

        if (parent != null && !parent.invalidVolatileBounds) {
            parent.updateVolatility();
        }
    }

    /**
     * Specifies whether or not this node is volatile.
     * All parents of this node are also volatile when this is volatile.
     * <p>
     * Volatile objects are those objects that change regularly, such as an object
     * that is animated, or one whose rendering depends on its context.
     * @param v the new specification of whether this node is volatile.
     * @see #getVolatileBounds()
     */
    public void setVolatileBounds(boolean v) {
        super.setVolatileBounds(v);

        if (parent != null) {
            parent.updateVolatility();
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //
    // Saving
    //
    /////////////////////////////////////////////////////////////////////////

    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException {
        super.writeObject(out);

        if (pickable != pickable_DEFAULT) {
            out.writeState("boolean", "pickable", pickable);
        }
        if (findable != findable_DEFAULT) {
            out.writeState("boolean", "findable", findable);
        }
        if (selectable != selectable_DEFAULT) {
            out.writeState("boolean", "selectable", selectable);
        }
    }
    /**
     * Node doesn't get written out if save property is false
     */
    public ZSerializable writeReplace() {
        if (savable) {
            return this;
        } else {
            return null;
        }
    }
}

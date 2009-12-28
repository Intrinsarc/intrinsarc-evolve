/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZVisualComponent</b> is the base class for objects that actually get rendered.
 * A visual component primarily implements three methods: paint(), pick(), and computeBounds().
 * New sub-classes must override at least paint() and computeBounds(), and will often
 * choose to override pick() as well.
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
public class ZVisualComponent extends ZSceneGraphObject implements ZSerializable, Serializable {

    /**
     * The parents of this visual component.
     * This is guaranteed to point to a valid array,
     * even if this group does not have any parents.
     */
    ZList.ZNodeList parents = ZListImpl.NullList;

    //****************************************************************************
    //
    //               Constructors
    //
    //***************************************************************************

    /**
     * Default constructor for visual component.
     */
    public ZVisualComponent() {
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZVisualComponent newComponent = (ZVisualComponent)super.duplicateObject();

        if (!parents.isNull()) {
            // Perform a shallow-copy of the parents array. The
            // updateObjectReferences table modifies this array. See below.
            newComponent.parents = (ZList.ZNodeList) parents.clone();
        }
        return newComponent;
    }

    /**
     * Called to update internal object references after a clone operation
     * by {@link ZSceneGraphObject#clone}.
     *
     * @see ZSceneGraphObject#updateObjectReferences
     */
    protected void updateObjectReferences(ZObjectReferenceTable objRefTable) {
        super.updateObjectReferences(objRefTable);
        if (!parents.isNull()) {
            int n = 0;
            ZNode[] parentsRef = parents.getNodesReference();
            for (int i = 0; i < parents.size(); i++) {
                ZNode newParent = (ZNode) objRefTable.getNewObjectReference(parentsRef[i]);
                if (newParent == null) {
                    // Cloned a visual component, but did not clone its parent.
                    // Drop the parent from the list of parents.
                } else {
                    // Cloned a visual component and its parent. Add the newly cloned
                    // parent to the parents list
                    parentsRef[n++] = newParent;
                }
            }
            parents.setSize(n);
        }
    }

    /**
     * Trims the capacity of the array that stores the parents list points to
     * the actual number of points.  Normally, the parents list arrays can be
     * slightly larger than the number of points in the parents list.
     * An application can use this operation to minimize the storage of a
     * parents list.
     */
    public void trimToSize() {
        parents.trimToSize();
    }

    //****************************************************************************
    //
    //                  Get/Set  pairs
    //
    //***************************************************************************

    /**
     * Returns the root of the scene graph that this component is in.
     * Actually returns the root of the first node this is a child of.
     */
    public ZRoot getRoot() {
        return (parents.size() > 0) ? parents.getNodesReference()[0].getRoot() : null;
    }

    /**
     * Return a copy of the array of parents of this node.
     * This method always returns an array, even when there
     * are no children.
     * @return the parents of this node.
     */
    public ZNode[] getParents() {
        return (ZNode[]) parents.toArray();
    }

    /**
     * Return the number of parents of this visual component.
     * @return the number of parents.
     */
    public int getNumParents() {
        return parents.size();
    }

    /**
     * Returns a reference to the parents of this component.
     * It should not be modified by the caller.  Note that the actual number
     * of parents could be less than the size of the array.  Determine
     * the actual number of parents with {@link #getNumParents}.
     * <P>
     * <b>Warning:</b> This method returns a reference to an internal array. Any modification
     * of this array will result in undefined behavior.
     * <P>
     * @return the parents of this visual component.
     */
    public ZNode[] getParentsReference() {
        return parents.getNodesReference();
    }

    /**
     * Method to add a node to be a new parent of this component.  The new node
     * is added to the end of the list of this node's parents;
     *
     * These methods are used primarily by the implementation of node objects
     * that need to update the internal scenegraph hierarchy, and should be
     * used with caution.  For example, instead consider using
     * {@link ZVisualLeaf#addVisualComponent} instead.
     * @param parent The new parent node.
     */
    public void addParent(ZNode parent) {
        if (parents.isNull()) {
            parents = new ZListImpl.ZNodeListImpl(1);
        }
        parents.add(parent);
    }

    /**
     * Method to remove the specified parent node from this visual component.
     * If the specified node wasn't a parent of this node,
     * then nothing happens.
     *
     * These methods are used primarily by the implementation of node objects
     * that need to update the internal scenegraph hierarchy, and should be
     * used with caution.  For example, instead consider using
     * {@link ZVisualLeaf#addVisualComponent} instead.
     * @param parent The parent to be removed.
     */
    public void removeParent(ZNode parent) {
        parents.remove(parent);
        if (parents.size() == 0) {
            parents = ZListImpl.NullList;
        }
    }

    /**
     * This is a utility function to determine if the specified rectangle
     * intersects the bounds of this visual component.
     * @param rect the rectangle that this method tests for intersection with
     * @return true if this component's local bounds intersects the specified rectangle
     */
    public boolean pickBounds(Rectangle2D rect) {
        if (getBoundsReference().intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight())) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether the specified rectangle (in local coordinates) should "pick" this visual component.
     * Picking is typically used to determine if a pointer is over an object, and
     * thus pick should be implemented to retur true if the rectangle intersects the object.
     * <p>
     * The implementation of this pick method for the base visual component returns
     * true if the rectangle intersects the bounds of the component.  If a sub-class
     * wants more detailed picking, then it must extend this class.  For example,
     * a circle may only consider itself to be picked if the pointer is within
     * the circle - rather than within the rectangular bounds.
     * @param rect The rectangle that is picking this visual component in local coordinates.
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return true if the rectangle picks this visual component
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        return pickBounds(rect);
    }

    /**
     * Paints this component. This method is called when the contents of the
     * visual component should be painted, either when the component is being
     * shown for the first time, or when repaint() has been called.<p>
     *
     * The clip rectangle, composite mode and transform of the Graphics2D parameter
     * are set by Jazz to reflect the context in which the component is being painted.
     * However, the color, font and stroke of the Graphics2D parameter are
     * left undefined by Jazz, and each visual component must set these attributes explicitly
     * to ensure that they are painted correctly.<p>
     *
     * The paint method is called by ZVisualComponent.render. Some visual components
     * may need to override render() instead of paint().<p>
     *
     * @param Graphics2D The graphics context to use for painting.
     * @see #render(ZRenderContext)
     */
    public void paint(Graphics2D g2) {
    }

    /**
     * Renders this visual component.<p>
     *
     * This method is called by Jazz when the component needs to be
     * redrawn on the screen. The default implementation of render simply
     * calls paint(), passing it the graphics object stored in the renderContext:<p>
     *
     * <code>    paint(renderContext.getGraphics2D()); </code><p>
     *
     * Sophisticated visual components may need access to the state information
     * stored in the ZRenderContext to draw themselves. Such components should override
     * render() rather than paint().<p>
     *
     * @param renderContext The graphics context to use for rendering.
     * @see #paint(Graphics2D)
     */
    public void render(ZRenderContext renderContext) {
        paint(renderContext.getGraphics2D());
    }

    /*
     * Repaint causes the portions of the surfaces that this object
     * appears in to be marked as needing painting, and queues events to cause
     * those areas to be painted. The painting does not actually
     * occur until those events are handled.
     * If this object is visible in multiple places because more than one
     * camera can see it, then all of those places are marked as needing
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
        if (!inTransaction) {
            parents.collectiveRepaint(getBounds());
        }
    }

    /**
     * This causes just the specified bounds of this visual component to be repainted.
     * Note that the input parameter may be modified as a result of this call.
     * @param repaintBounds The bounds to repaint
     * @see #repaint()
     */
    public void repaint(ZBounds repaintBounds) {
        if (!inTransaction) {
            parents.collectiveRepaint(repaintBounds);
        }
    }

    /**
     * Internal method that causes this node and all of its ancestors
     * to invalidate their bounds.
     */
    protected void updateBounds() {
        super.updateBounds();

        ZNode[] parentsRef = parents.getNodesReference();
        for (int i = 0; i < parents.size(); i++) {
            parentsRef[i].updateBounds();
        }
    }

    /**
     * Internal method invalidate the volatility of a component. This will also
     * invalidate the volatility of all the parents of this component.
     * All parents of this component are also volatile when this is volatile.
     * @see #setVolatileBounds(boolean)
     * @see #getVolatileBounds()
     */
    protected void updateVolatility() {
        super.updateVolatility();
                                // Update parent's volatility
        ZNode[] parentsRef = parents.getNodesReference();
        for (int i = 0; i < parents.size(); i++) {
            parentsRef[i].updateVolatility();
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
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        super.writeObjectRecurse(out);
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
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        trimToSize();   // Remove extra unused array elements
        out.defaultWriteObject();
    }
}
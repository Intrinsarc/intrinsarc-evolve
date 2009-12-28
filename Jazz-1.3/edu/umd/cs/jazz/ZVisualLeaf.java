package edu.umd.cs.jazz;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

public class ZVisualLeaf extends ZLeaf implements ZSerializable, Serializable {
    /**
     * The visual components associated with this leaf.
     */
    protected ZList.ZVisualComponentList visualComponents = ZListImpl.NullList;

    /**
     * Cached volatility computation.
     */
    private transient boolean cacheVolatile = false;

    //****************************************************************************
    //
    //                Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new empty visual leaf node.
     */
    public ZVisualLeaf() {
    }

    /**
     * Constructs a new visual leaf node with the specified visual component.
     * @param visualComponent The new visual component that this leaf displays.
     */
    public ZVisualLeaf(ZVisualComponent visualComponent) {
        setVisualComponent(visualComponent);
    }

    /**
     * Add a new visual component to this leaf node.
     * If this leaf already contains this component, then nothing happens.
     * @param visualComponent The visual component to be added.
     */
    public void addVisualComponent(ZVisualComponent visualComponent) {
        if (visualComponent == null) return;

        if (visualComponents.isNull()) {
            visualComponents = new ZListImpl.ZVisualComponentListImpl(1);
        }

        if (visualComponents.contains(visualComponent)) return;

        if (inTransaction != visualComponent.inTransaction) {
            if (inTransaction) {
                visualComponent.markInTransaction();
            } else {
                visualComponent.markNotInTransaction();
            }
        }

        visualComponents.add(visualComponent);
        visualComponent.addParent(this);

        updateBounds();
        updateVolatility();
        visualComponent.repaint();
    }
    /**
     * Add a collection of new visual components to this leaf node. If you have a
     * large group of visualComponents to add to a ZVisualLeaf this method will be
     * much faster then repeatedly calling addVisualComponent.
     *
     * @param aVisualComponentCollection The collection to be added.
     */
    public void addVisualComponents(Collection aVisualComponentCollection) {
        if (visualComponents.isNull()) {
            visualComponents = new ZListImpl.ZVisualComponentListImpl(1);
        }

        Iterator i = aVisualComponentCollection.iterator();
        while (i.hasNext()) {
            ZVisualComponent each = (ZVisualComponent) i.next();

            if (inTransaction != each.inTransaction) {
                if (inTransaction) {
                    each.markInTransaction();
                } else {
                    each.markNotInTransaction();
                }
            }

            visualComponents.add(each);
            each.addParent(this);
        }

        updateVolatility();
        reshape();
    }
    /**
     * Remove all visual components from this visual leaf.
     */
    public void clearVisualComponents() {
        ZVisualComponent[] visualComponentsRef = visualComponents.getVisualComponentsReference();
        for (int i = 0; i < visualComponents.size(); i++) {
            visualComponentsRef[i].removeParent(this);
        }
        visualComponents.clear();
        visualComponents = ZListImpl.NullList;

        repaint();
        updateBounds();
        updateVolatility();
    }

    /**
     * Recomputes and caches the bounds for this node.  Generally this method is
     * called by reshape when the bounds have changed, and it should rarely
     * directly elsewhere.  A ZVisualLeaf bounds is the bounds of the union
     * of its visual components.
     */
    protected void computeBounds() {
        bounds.reset();
        bounds = visualComponents.collectiveBoundsReference(bounds);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZVisualLeaf newObject = (ZVisualLeaf)super.duplicateObject();

        if (!visualComponents.isNull()) {
            // Perform a shallow-copy of the parents array. The
            // updateObjectReferences table modifies this array. See below.
            newObject.visualComponents = (ZList.ZVisualComponentList) visualComponents.clone();
            ZVisualComponent[] visualComponentsRef = visualComponents.getVisualComponentsReference();
            for (int i = 0; i < visualComponents.size(); i++) {
                visualComponentsRef[i].clone();
            }
        }

        return newObject;
    }

    /**
     * Return the first visual component associated with this leaf,
     * or null if there are none.
     */
    public final ZVisualComponent getFirstVisualComponent() {
        return (ZVisualComponent) visualComponents.get(0);
    }

    /**
     * Return the handles associated with this leaf.
     */
    public Collection getHandles() {
        ArrayList result = new ArrayList();

        ZVisualComponent[] visualComponentsRef = getVisualComponents();
        for (int i=0; i<visualComponents.size(); i++) {
            result.addAll(visualComponentsRef[i].getHandles());
        }
        return result;
    }

    /**
     * Return the number of visual components of this visual leaf.
     * @return the number of visual components.
     */
    public int getNumVisualComponents() {
        return visualComponents.size();
    }

    /**
     * Returns the i'th visual component of this node.
     * @return the i'th visual component of this node.
     */
    public ZVisualComponent getVisualComponent(int i) {
        return (ZVisualComponent) visualComponents.get(i);
    }

    /**
     * Return a copy of the bounds of this node's visual components in local coordinates.
     * If this node does not have any visual components, then this returns null.
     * @return The union of this node's visual component's bounds in local coordinates
     * (or null if there are no visual components).
     */
    public ZBounds getVisualComponentBounds() {
        return visualComponents.collectiveBoundsReference(new ZBounds());
    }

    /**
     * Return a copy of the bounds of this node's visual components in global coordinates.
     * If this node does not have any visual components, then this returns null.
     * Note that global bounds are not cached, and this method involves some computation.
     * @return The visual component's bounds in global coordinates
     * (or null if there are no visual components).
     */
    public ZBounds getVisualComponentGlobalBounds() {
        if (visualComponents.isNull()) return null;

        ZBounds result = getVisualComponentBounds();
        localToGlobal(result);
        return result;
    }

    /**
     * Return the visual components associated with this visual leaf.
     */
    public final ZVisualComponent[] getVisualComponents() {
        return visualComponents.getVisualComponentsReference();
    }

    /**
     * Determines if this node is volatile.
     * A node is considered to be volatile if it is specifically set
     * to be volatile with {@link ZNode#setVolatileBounds}.
     * All parents of this node are also volatile when this is volatile.
     * <p>
     * Volatile objects are those objects that change regularly, such as an object
     * that is animated, or one whose rendering depends on its context.
     * @return true if this node is volatile
     * @see #setVolatileBounds(boolean)
     */
    public boolean getVolatileBounds() {
        return super.getVolatileBounds() || cacheVolatile;
    }

    /**
     * Compute volatileBoundsCache for visualComponents. getVolatileBounds()
     * returns this cache ored with this objects volatileBounds varriable.
     */
    protected void computeVolatileBounds() {
        super.computeVolatileBounds();

        if (!visualComponents.isNull()) {
            cacheVolatile = visualComponents.collectiveHasVolatileBounds();
        } else {
            cacheVolatile = false;
        }
    }

    /**
     * Returns the index of the specified visual component or -1 if
     * the visual component has not been added to this leaf
     * @return The index of the specified visual component or -1
     */
    public int indexOf(ZVisualComponent vis) {
        return visualComponents.indexOf(vis);
    }

    //****************************************************************************
    //
    //                  Other Methods
    //
    //****************************************************************************

    /**
     * Returns true if any of this node's visual components
     * are under the specified rectangle, and builds a ZSceneGraphPath to the node.
     * Only returns "pickable" nodes.
     * @param rect Coordinates of pick rectangle in local coordinates
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return The picked node, or null if none
     * @see ZDrawingSurface#pick(int, int)
     */
     public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        if (isPickable() && (!visualComponents.isNull())) {
            path.push(this);
            ZVisualComponent picked = (ZVisualComponent) visualComponents.collectivePick(rect, path);
            if (picked != null) {
                if (!(picked instanceof ZCamera)) {
                                // Set object here rather than in component so components don't
                                // have to worry about implementation of paths.
                    path.setObject(picked);
                }
                return true;
            }
            path.pop(this);
        }

        return false;
    }

    /**
     * Remove a visual component from this leaf node.
     * If this leaf didn't already contains this component, then nothing happens.
     * @param visualComponent The visual component to be removed.
     */
    public void removeVisualComponent(ZVisualComponent visualComponent) {
        if (visualComponent == null) return;

                                // Check if visualComponent already exists
        int index = visualComponents.indexOf(visualComponent);
        if (index == -1) return;

        visualComponents.remove(index);
        visualComponent.removeParent(this);

        repaint();
        updateBounds();
        updateVolatility();
    }

    //****************************************************************************
    //
    // Painting related methods
    //
    //***************************************************************************

    /**
     * Renders this node which results its visual components getting painted.
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

                                // Paint intersecting visual components
        ZBounds visibleBounds = renderContext.getVisibleBounds();
        ZVisualComponent[] visualComponentsRef = getVisualComponents();
        for (int i=0; i<visualComponents.size(); i++) {
        	if (visibleBounds.intersects(visualComponentsRef[i].getBoundsReference())) {
	            visualComponentsRef[i].render(renderContext);
        	}
        }

        if (ZDebug.debug) {
            ZDebug.incPaintCount();     // Keep a count of how many things have been rendered
                                        // Draw bounding box if requested for debugging
            if (ZDebug.showBounds) {
                Graphics2D g2 = renderContext.getGraphics2D();
                g2.setColor(new Color(60, 60, 60));
                g2.setStroke(new BasicStroke((float)(1.0 / renderContext.getCompositeMagnification()),
                                             BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                g2.draw(getBoundsReference());
            }
        }
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

        if (fieldName.compareTo("visualComponents") == 0) {
            addVisualComponents((Vector) fieldValue);
                                // For backwards compatability, we read in this value
                                // for a single visual component
        } else if (fieldName.compareTo("visualComponent") == 0) {
            ZVisualComponent visualComponent = (ZVisualComponent)fieldValue;
            setVisualComponent(visualComponent);
        }
    }

    /**
     * Replace the i'th visual component associated with this leaf node.
     * If this node does not already have an i'th visual component,
     * then an IndexOutOfBoundsException is thrown.
     * @param i The index of the visual component to replace
     * @param visualComponent The new visual component for this node.
     */
    public void setVisualComponent(int i, ZVisualComponent visualComponent) {
        ZVisualComponent old = (ZVisualComponent) visualComponents.get(i);
        old.removeParent(this);

        visualComponents.set(i, visualComponent);
        visualComponent.addParent(this);

        if (inTransaction != visualComponent.inTransaction) {
            if (inTransaction) {
                visualComponent.markInTransaction();
            } else {
                visualComponent.markNotInTransaction();
            }
        }

        updateVolatility();
        reshape();
    }

    /**
     * Set the visual component associated with this leaf node.
     * If this node previously had any visual components associated with it,
     * then those components will be replaced with the new one.
     * @param visualComponent The new visual component for this node.
     */
    public void setVisualComponent(ZVisualComponent visualComponent) {
        clearVisualComponents();
        addVisualComponent(visualComponent);
    }

    /**
     * Trims the capacity of the array that stores the visual components list to
     * the actual number of points.  Normally, the visual components list array can be
     * slightly larger than the number of points in the visual components list.
     * An application can use this operation to minimize the storage of a
     * visual components list.
     */
    public void trimToSize() {
        super.trimToSize();
        visualComponents.trimToSize();
    }

    /**
     * Called to update internal object references after a clone operation
     * by {@link ZSceneGraphObject#clone}.
     *
     * @see ZSceneGraphObject#updateObjectReferences
     */
    protected void updateObjectReferences(ZObjectReferenceTable objRefTable) {
        super.updateObjectReferences(objRefTable);
        if (!visualComponents.isNull()) {
            int n = 0;
            ZVisualComponent[] visualComponentsRef = visualComponents.getVisualComponentsReference();
            for (int i = 0; i < visualComponents.size(); i++) {
                ZVisualComponent newComponent = (ZVisualComponent)
                                                objRefTable.getNewObjectReference(visualComponentsRef[i]);
                if (newComponent == null) {
                    // Cloned a visual component, but did not clone its parent.
                    // Drop the parent from the list of parents.
                } else {
                    // Cloned a visual component and its parent. Add the newly cloned
                    // parent to the parents list
                    visualComponentsRef[n++] = newComponent;
                }
            }
            visualComponents.setSize(n);
        }
    }

    /**
     * Mark this object and all its visualComponents as being part of a transaction.
     */
    protected void markInTransaction() {
        super.markInTransaction();

        ZVisualComponent[] components = visualComponents.getVisualComponentsReference();
        for (int i = 0; i < visualComponents.size(); i++) {
            components[i].markInTransaction();
        }
    }

    /**
     * Mark this object and all its visualComponents as not being part of a transaction.
     */
    protected void markNotInTransaction() {
        super.markNotInTransaction();

        ZVisualComponent[] components = visualComponents.getVisualComponentsReference();
        for (int i = 0; i < visualComponents.size(); i++) {
            components[i].markNotInTransaction();
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
        visualComponents.writeObject("visualComponents", out);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        trimToSize();   // Remove extra unused array elements
        out.defaultWriteObject();
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        super.writeObjectRecurse(out);

        ZVisualComponent[] visualComponentsRef = getVisualComponents();
        for (int i=0; i<visualComponents.size(); i++) {
            out.addObject(visualComponentsRef[i]);
        }
    }
}

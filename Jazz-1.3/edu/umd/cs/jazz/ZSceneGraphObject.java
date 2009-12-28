package edu.umd.cs.jazz;

import java.io.*;
import java.io.IOException;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;

import javax.swing.event.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;

/**
 * <b>ZSceneGraphObject</b> is the base class for all objects in the Jazz scenegraph.
 * It provides support for the basic shared methods between all nodes and visual components.
 * <P>
 * <B> Coordinate Systems </B><br>
 * Application developers must understand the basic coordinate systems used in Jazz.
 * The basic coordinate system has its origin at the upper-left.  The X-axis increases positively
 * to the right, and the Y-axis increase positively down.
 * <P>
 * Because certain node types define transforms which define a new relative coordinate system,
 * it is important to realize that typically, objects are not placed in "global" coordinates.
 * Rather, every object is defined in their own "local" coordinate system.  The relationship
 * of the local coordinate system to the global coordinate system is determined by the
 * series of transforms between that object, and the root of the scenegraph.
 * <P>
 * All Jazz operations occur in local coordinates.  For instance, coordinates of rectangles
 * are specified in local coordinates.  In addition, objects maintain a bounding box which is
 * stored in local coordinates.
 * <P> See the Jazz Tutorial for a more complete description of the scene graph.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Ben Bederson
 * @see     ZNode
 * @see     ZVisualComponent
 */
public abstract class ZSceneGraphObject implements ZSerializable, Serializable, Cloneable {
                                // Default values
    static public final boolean volatileBounds_DEFAULT = false;    // True if this node has volatile bounds (shouldn't be cached)

    /**
     * The single instance of the object reference table used for cloning scenegraph trees.
     */
    private static ZObjectReferenceTable objRefTable = ZObjectReferenceTable.getInstance();

    /**
     * Used to detect recursive calls to clone.
     */
    private static boolean inClone;

    /**
     * The bounding rectangle occupied by this object in its own local coordinate system.
     * Conceptually, the bounding rectangle is defined as the minimum rectangle that
     * would surround all of the geometry drawn by the node and its children. The bounding
     * rectangle's coordinates are in the node's local coordinates. That is, they are
     * independant of any viewing transforms, or of transforms performed by parents
     * of the node.
     */
    protected ZBounds bounds;

    /**
     * True if this nodes bounds need to be recomputed.
     */
    protected transient boolean invalidBounds = true;

    /**
     *  True if this node is specifically set to have volatile bounds
     */
    protected boolean volatileBounds = volatileBounds_DEFAULT;

    /**
     * True if this nodes volatileBounds need to be recomputed.
     */
    protected transient boolean invalidVolatileBounds = true;

    /**
     * True if the scenegraph rooted by this object is in a transaction.
     */
    protected transient boolean inTransaction = false;

    /**
     * A list of event listeners for this node.
     */
    protected transient EventListenerList listenerList = null;

    /**
     * Set of client-specified properties for this node.
     */
    private ZList.ZPropertyList clientProperties = ZListImpl.NullList;

    //****************************************************************************
    //
    //                 Constructors
    //
    //***************************************************************************

    /**
     * Constructs an empty scenegraph object.
     * <P>
     * Most objects will want to store their
     * bounds, and so we allocate bounds here.
     * However, if a particular object is implemented by
     * computing its bounds every time it is asked instead of allocating
     * it, then it can free up the bounds allocated here.
     */
    protected ZSceneGraphObject() {
        bounds = new ZBounds();
    }
    /**
     * Internal method to add the specified property.
     * @param prop The new property.
     */
    protected void addClientProperty(ZProperty prop) {
        if (clientProperties.isNull()) {
            clientProperties = new ZListImpl.ZPropertyListImpl(1);
        }
        clientProperties.add(prop);
    }
    /**
     * Adds the specified mouse listener to receive mouse events from this object
     *
     * @param l the mouse listener
     */
    public void addMouseListener(ZMouseListener l) {
        getListenerList().add(ZMouseListener.class, l);
    }
    /**
     * Adds the specified mouse motion listener to receive mouse motion events from this object
     *
     * @param l the mouse motion listener
     */
    public void addMouseMotionListener(ZMouseMotionListener l) {
        getListenerList().add(ZMouseMotionListener.class, l);
    }
    /**
     * Clones this scene graph object and all its children and returns the newly
     * cloned sub-tree. Applications must then add the sub-tree to the scene graph
     * for it to become visible.
     *
     * @return A cloned copy of this object.
     */
    public Object clone() {
        Object newObject;

        if (inClone) {

            // Recursive call of clone (e.g. by a group to copy its children)
            newObject = duplicateObject();

        } else {
            try {
                inClone = true;
                objRefTable.reset();
                newObject = duplicateObject();

                // Updates cloned objects. This iterates through all the cloned
                // objects in the reference table, notifying them to update their
                // internal references, passing in a reference
                // to objRefTable so it can be queried for original/new object mappings.

                for (Iterator iter = objRefTable.iterator() ; iter.hasNext() ;) {
                    ZSceneGraphObject clonedObject = (ZSceneGraphObject) iter.next();
                    clonedObject.updateObjectReferences(objRefTable);
                }

            } finally {
                inClone = false;
            }
        }
        return newObject;
    }
    /**
     * Recomputes and caches the bounds for this node.  Generally this method is
     * called by reshape when the bounds have changed, and it should rarely
     * directly elsewhere. ZGroup and ZVisualLeaf overide this method to compute
     * the cached volatile bounds of their children.
     */
    protected void computeBounds() {
    }
    /**
     * Recomputes and caches the volatile bounds for this node.  Generally this method is
     * called by getVolatileBounds when the volatileBounds are invalid, and it should rarely
     * get called directly elsewhere.
     */
    protected void computeVolatileBounds() {
    }
    /**
     * Generate a string that represents this object for debugging.
     * @return the string that represents this object for debugging
     * @see ZDebug#dump
     */
    public String dump() {
        String str = toString();
        ZBounds b = getBounds();
        if (b.isEmpty()) {
            str += ": Bounds = [Empty]";
        } else {
            str += ": Bounds = [x=" + b.getX() + ", y=" + b.getY() +
                ", w=" + b.getWidth() + ", h=" + b.getHeight() + "]";
        }
        if (getVolatileBounds()) {
            str += "\n Volatile";
        }

        Iterator i = clientProperties.iterator();
        while (i.hasNext()) {
            ZProperty each = (ZProperty) i.next();
            str += "\n Property '" + each.getKey() + "': " + each.getValue();
        }

        return str;
    }
    /**
     * Creates a copy of this scene graph object and all its children.<p>
     *
     * ZSceneGraphObject.duplicateObject() calls Object.clone() on this object, and
     * returns the newly cloned object. This results in a shallow copy of the object.<p>
     *
     * Subclasses override this method to modify the cloning behavior
     * for nodes in the scene graph. Typically, subclasses first invoke super.duplicateObject()
     * to get the default cloning behavior, and then take additional actions after this.
     * In particular, ZGroup.duplicateObject() first invokes super.duplicateObject(),
     * and then calls duplicateObject() on all of the children in the group, so that the whole
     * tree beneath the group is cloned.
     *
     * Applications do not call duplicateObject directly. Instead,
     * ZSceneGraphObject.clone() is used clone a scene graph object.
     */
    protected Object duplicateObject() {
        ZSceneGraphObject newObject;
        try {
            newObject = (ZSceneGraphObject)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Object.clone() failed: " + e);
        }

        if (bounds != null) {
            newObject.bounds = (ZBounds)(bounds.clone());
        }

        objRefTable.addObject(this, newObject);

        newObject.listenerList = null;    // Listeners on objects are de-referenced on node clones

                                // Do a deep copy of client properties (if some)
        if (!clientProperties.isNull()) {
            newObject.clientProperties = new ZListImpl.ZPropertyListImpl(clientProperties.size());

            ZProperty[] propertiesRef = clientProperties.getPropertiesReference();
            for (int i = 0; i < clientProperties.size(); i++) {
                newObject.clientProperties.add((ZProperty)propertiesRef[i].clone());
            }
        }

        return newObject;
    }
    /**
     * End the transaction for the scenegraph rooted at this object. This ends
     * the transaction making sure that the volatilily, and bounds of the scenegraph
     * rooted at this object are up to date. This object is also repainted.
     *
     * @see #startTransaction()
     */
    public void endTransaction() {
        markNotInTransaction();
        updateVolatility();
        updateBounds();
        repaint();
    }
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type. The listener list is processed
     * in last to first order.
     * <p>
     * If the event is consumed, then the event will not be passed to any more
     * listeners in the list.
     * @param anEvent The ZEvent
     * @see ZEvent
     */
    protected void fireEvent(ZEvent anEvent) {
        if (listenerList == null) {
            return;
        }

        anEvent.setSource(this);

        Object[] listeners = getListenerList().getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i] == anEvent.getListenerType()) {
                anEvent.dispatchTo(listeners[i+1]);
            }
            if (anEvent.isConsumed())
                return;
        }
    }
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type. The listener list is processed
     * in last to first order.
     * <p>
     * If the event is consumed, then the event will not be passed
     * event listeners on the Component that the event came through.
     * @param e The mouse event
     * @see EventListenerList
     * @deprecated as of Jazz 1.1
     */
    public void fireMouseEvent(ZMouseEvent e) {
        if (listenerList == null) {
            return;
        }
                                // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

                                // Process the listeners last to first, notifying
                                // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ZMouseListener.class) {
                switch (e.getID()) {

                case MouseEvent.MOUSE_PRESSED:
                    ((ZMouseListener)listeners[i+1]).mousePressed(e);
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    ((ZMouseListener)listeners[i+1]).mouseReleased(e);
                    break;
                case MouseEvent.MOUSE_ENTERED:
                    ((ZMouseListener)listeners[i+1]).mouseEntered(e);
                    break;
                case MouseEvent.MOUSE_EXITED:
                    ((ZMouseListener)listeners[i+1]).mouseExited(e);
                    break;
                case MouseEvent.MOUSE_CLICKED:
                    ((ZMouseListener)listeners[i+1]).mouseClicked(e);
                    break;
                }
            }
                                // Don't process any more listeners if event was consumed
            if (e.isConsumed()) {
                break;
            }

            if (listeners[i]==ZMouseMotionListener.class) {
                switch (e.getID()) {
                case MouseEvent.MOUSE_DRAGGED:
                    ((ZMouseMotionListener)listeners[i+1]).mouseDragged(e);
                    break;
                case MouseEvent.MOUSE_MOVED:
                    ((ZMouseMotionListener)listeners[i+1]).mouseMoved(e);
                    break;
                }
            }
                                // Don't process any more listeners if event was consumed
            if (e.isConsumed()) {
                break;
            }
        }
    }
    /**
     * Return a copy of the bounds of the subtree rooted at this node in local coordinates.
     * If a valid cached value is available, this method returns it.  If a
     * valid cache is not available (i.e. the object is volatile) then the bounds are
     * recomputed, cached and then returned to the caller.
     * <P>
     * If the object is a context-sensitive object, then it may compute the bounds
     * based on the current render context.
     * @return The bounds of the subtree rooted at this in local coordinates.
     * @see ZRoot#getCurrentRenderContext
     */
    public ZBounds getBounds() {
        return (ZBounds) getBoundsReference().clone();
    }
    /**
     * Return a reference to the bounds of the subtree rooted at this node in local coordinates.
     * If a valid cached value is available, this method returns it.  If a
     * valid cache is not available (i.e. the object is volatile) then the bounds are
     * recomputed, cached and then returned to the caller. The ZBounds returned should not be modified.
     * <p>
     * <b>Warning:</b> This method returns a reference to an internal ZBounds object. Any modification
     * of this ZBounds object will result in undefined behavior.
     * <P>
     * If the object is a context-sensitive object, then it may compute the bounds
     * based on the current render context.
     * @return The bounds of the subtree rooted at this in local coordinates.
     * @see ZRoot#getCurrentRenderContext
     */
    public ZBounds getBoundsReference() {
        if (getVolatileBounds() || invalidBounds) {

            double oldX = 0;
            double oldY = 0;
            double oldWidth = 0;
            double oldHeight = 0;
            boolean oldIsEmpty = true;
            boolean hasOldBound = false;

            if (bounds != null) {
                oldX = bounds.getX();
                oldY = bounds.getY();
                oldWidth = bounds.getWidth();
                oldHeight = bounds.getHeight();
                oldIsEmpty = bounds.isEmpty();
                hasOldBound = true;
            }

            computeBounds();
            invalidBounds = false;

            // if bounds did not change, return without event percolation
            if (hasOldBound) {
                if (oldX == bounds.getX() &&
                    oldY == bounds.getY() &&
                    oldWidth == bounds.getWidth() &&
                    oldHeight == bounds.getHeight() &&
                    oldIsEmpty == bounds.isEmpty()) {

                    return bounds;
                }
            }

            if (this instanceof ZNode) {
                ZNode thisNode = (ZNode) this;
                thisNode.percolateEventUpSceneGraph(ZNodeEvent.createBoundsChangedEvent(thisNode));
                thisNode.percolateEventUpSceneGraph(ZNodeEvent.createGlobalBoundsChangedEvent(thisNode));
            }
        }
        return bounds;
    }
    /**
     * Returns the value of the property with the specified key.  Only
     * properties added with <code>putClientProperty</code> will return
     * a non-null value.
     *
     * @return the value of this property or null
     * @see #putClientProperty
     */
    public Object getClientProperty(Object key) {
        return clientProperties.getMatchingProperty(key);
    }
    /**
     * Return the collection of handles associated with this object.
     */
    public Collection getHandles() {
        return new ArrayList(0);
    }
    /**
     * Return this objects current event listener list. If the list is currently
     * null then create a new listener list and return that one.
     */
    protected EventListenerList getListenerList() {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        return listenerList;
    }
    /**
     * Determines if this node is volatile.
     * A node is considered to be volatile if it is specifically set
     * to be volatile with {@link ZNode#setVolatileBounds}.
     * All parents of this node are also considered volatile when a child is volatile
     * and should return true when responding to this message.
     * <p>
     * Volatile objects are those objects that change regularly, such as an object
     * that is animated, or one whose rendering depends on its context.
     * @return true if this node is volatile
     * @see #setVolatileBounds(boolean)
     */
    public boolean getVolatileBounds() {
        if (invalidVolatileBounds) {
            computeVolatileBounds();
            invalidVolatileBounds = false;
        }
        return volatileBounds;
    }
    /**
     * @deprecated as of Jazz 1.2 replaced with hasListenerOfType (proper spelling)
     */
    public boolean hasLisenerOfType(Class aType) {
        if (listenerList == null) return false;

        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==aType) {
                return true;
            }
        }

        return false;
    }
    /**
     * Determines if this Object has a registered listener of the type
     * specified in its listener list.
     * @param aType The type of listener to search for.
     * @return ture if it does have such a listener.
     */
    public boolean hasListenerOfType(Class aType) {
        if (listenerList == null) return false;

        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==aType) {
                return true;
            }
        }

        return false;
    }
    /**
     * Determines if this object has any kind of mouse listener (i.e., mouse or mouse motion listener.)
     * @return true if this object does have at least one mouse listener
     */
    public boolean hasMouseListener() {
        if (listenerList != null) {
                                // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            for (int i = listeners.length-2; i>=0; i-=2) {
                if ((listeners[i]==ZMouseListener.class) || (listeners[i]==ZMouseMotionListener.class)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Mark this object and any of its children as part of a transaction.
     */
    protected void markInTransaction() {
        inTransaction = true;
    }
    /**
     * Mark this object and any of its children as not part of a transaction.
     */
    protected void markNotInTransaction() {
        inTransaction = false;
    }
    /**
     * Forwards event to fireEvent(ZMouseEvent e);
     */
    public void processMouseEvent(ZMouseEvent e) {
        fireEvent(e);
    }
    /**
     * Add an arbitrary key/value "client property" to this component.
     * <p>
     * The <code>get/putClientProperty<code> methods provide access to
     * a small per-instance hashtable. Callers can use get/putClientProperty
     * to annotate components that were created by another module.
     * <p>
     * If value is null this method will remove the property.
     *
     * @see #getClientProperty
     */
    public void putClientProperty(Object key, Object value) {
        int index = clientProperties.indexOfPropertyWithKey(key);

                                // If value == null and the property exists remove it.
        if (value == null) {
            if (index != -1) {
                clientProperties.remove(index);
            }
        } else {                // Else add a new property or update the current one.
            if (index == -1) {
                addClientProperty(new ZProperty(key, value));
            } else {
                ZProperty found = (ZProperty) clientProperties.get(index);
                found.set(key, value);
            }
        }                       // If no properties exist replace list with nullList.
        if (clientProperties.size() == 0) {
            clientProperties = ZListImpl.NullList;
        }
    }
    /**
     * Removes the specified mouse listener so that it no longer
     * receives mouse events from this object.
     *
     * @param l the mouse listener
     */
    protected void removeEventListener(Class listenerType, EventListener listener) {
        getListenerList().remove(listenerType, listener);
        if (listenerList.getListenerCount() == 0) {
            listenerList = null;
        }
    }
    /**
     * Removes the specified mouse listener so that it no longer
     * receives mouse events from this object.
     *
     * @param l the mouse listener
     */
    public void removeMouseListener(ZMouseListener l) {
        removeEventListener(ZMouseListener.class, l);
    }
    /**
     * Removes the specified mouse motion listener so that it no longer
     * receives mouse motion events from this object.
     *
     * @param l the mouse motion listener
     */
    public void removeMouseMotionListener(ZMouseMotionListener l) {
        removeEventListener(ZMouseMotionListener.class, l);
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
            System.out.println("ZSceneGraphObject.repaint: this = " + this);
        }
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
     * of repaint(bounds) if the internal state change effects the bounds of
     * the shape in any way (e.g. changing penwidth, selection, transform,
     * adding points to a line, etc.)
     *
     * @see #reshape()
     */
    public void repaint(ZBounds bounds) {
        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println("ZSceneGraphObject.repaint(ZBounds): this = " + this);
            System.out.println("ZSceneGraphObject.repaint(ZBounds): bounds = " + bounds);
        }
    }
    /**
     * Reshape causes the portion of the surface that this object
     * appears in before the bounds are changed to be marked as needing
     * painting, and queues events to cause those areas to be painted.
     * Then, the bounds are updated, and finally, the portion of the
     * screen corresponding to the newly computed bounds are marked
     * for repainting.
     * If this object is visible in multiple places because more than one
     * camera can see this object, then all of those places are marked as needing
     * painting.
     * <p>
     * Scenegraph objects should call reshape when their internal
     * state has changed in such a way that their bounds have changed.
     * <p>
     * Important note : Scenegraph objects should call repaint() instead
     * of reshape() if the bounds of the shape have not changed.
     *
     * @see #repaint()
     */
    public void reshape() {
        // Fix suggested by David Wang.
        // Directly repaints the bounds when volatile
        if (!inTransaction && getVolatileBounds()) {
            repaint((ZBounds)bounds.clone());
        } else {
            repaint();
        }

        updateBounds();
        repaint();
    }
    /**
     * Internal method to specify the bounds of this object.
     */
    protected void setBounds(ZBounds newBounds) {
        bounds.setRect(newBounds);
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
        if (fieldName.compareTo("volatileBounds") == 0) {
            setVolatileBounds(((Boolean)fieldValue).booleanValue());
        } else if (fieldName.compareTo("properties") == 0) {
            ZProperty prop;
            for (Iterator i=((Vector)fieldValue).iterator(); i.hasNext();) {
                prop = (ZProperty)i.next();
                addClientProperty(prop);
            }
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
        volatileBounds = v;
        updateVolatility();
    }
    /**
     * Start a transaction for the scenegraph rooted at this object. Transactions
     * allow for efficient manipulation of the scenegraph. While a transaction is
     * in effect many of the updating methodes (such as repaint) will not get called,
     * this can result in signifigant performance improvments when a large number of
     * objects are involved. endTransaction() should be be called after the modifications
     * have been made so that the scenegraph will get properly updated.
     *
     * @see #endTransaction()
     */
    public void startTransaction() {
        repaint();
        markInTransaction();
    }
    /////////////////////////////////////////////////////////////////////////
    //
    // Saving
    //
    /////////////////////////////////////////////////////////////////////////

    /**
     * Trims the capacity of the array that stores the clientProperties list points to
     * the actual number of points. Normally, the clientProperties list arrays can be
     * slightly larger than the number of points in the clientProperties list.
     * An application can use this operation to minimize the storage of a
     * clientProperties list.
     */
    public void trimToSize() {
        clientProperties.trimToSize();
    }
    /**
     * Internal method that causes this node and all of its ancestors
     * to invalidate their bounds. If the bounds are invalid this will get
     * recomputed on the next call to getBoundsReference();
     */
    protected void updateBounds() {
        invalidBounds = true;
    }
    /**
     * Updates references to scene graph nodes after a clone operation.<p>
     *
     * This method is invoked on cloned objects after the clone operation has been
     * completed. The objRefTable parameter is a table mapping from the original
     * uncloned objects to their newly cloned versions. Subclasses override this
     * method to update any internal references they have to scene graph nodes.
     * For example, ZNode's updateObjectReferences does:
     *
     * <pre>
     *      super.updateObjectReferences(objRefTable);
     *
     *      // Set parent to point to the newly cloned parent, or to
     *      // null if the parent object was not cloned.
     *      parent = (ZNode)objRefTable.getNewObjectReference(parent);
     * </pre>
     *
     * @param objRefTable Table mapping from uncloned objects to their cloned versions.
     */
    protected void updateObjectReferences(ZObjectReferenceTable objRefTable) {
        if (!inClone) {
            throw new RuntimeException("ZSceneGraphObject.updateObjectReferences: Called outside of a clone");
        }

        // Update client properties
        ZProperty[] propertiesRef = clientProperties.getPropertiesReference();
        for (int i = 0; i < clientProperties.size(); i++) {
            propertiesRef[i].updateObjectReferences(objRefTable);
        }
    }
    /**
     * Internal method to invalidate the volatility of a node,
     * The next call to getVolatileBounds() will cause the volatileBounds to get
     * recomputed.
     * @see #setVolatileBounds(boolean)
     * @see #getVolatileBounds()
     */
    protected void updateVolatility() {
        invalidVolatileBounds = true;
    }
    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException {
        if (volatileBounds != volatileBounds_DEFAULT) {
            out.writeState("boolean", "volatileBounds", volatileBounds);
        }
        clientProperties.writeObject("properties", out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        invalidBounds = true;
        invalidVolatileBounds = true;
        in.defaultReadObject();
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

                                // Add properties
        ZProperty[] propertiesRef = clientProperties.getPropertiesReference();
        for (int i=0; i<clientProperties.size(); i++) {
            out.addObject(propertiesRef[i]);
        }
    }
}

/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.util.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZHandleGroup</b> is a group node that manages a collection of {@link edu.umd.cs.jazz.ZHandle}s.
 * This collection is created by calling <code>getHandles</code> on the bottom most node of the
 * edit group that the handle group is part of. Generally to create custom handles
 * you should just override the <code>getHandles</code> method in your custom scene graph object. See
 * {@link edu.umd.cs.jazz.component.ZRectangle} for an example of how to do this.
 * <P>
 * {@link edu.umd.cs.jazz.util.ZSceneGraphEditor} provides a convenience mechanism to locate, create
 * and manage nodes of this type.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see ZHandle
 * @author Jesse Grosjean
 */
public class ZHandleGroup extends ZGroup {

    private static ZHandleEventHandler DEFAULT_EVENT_HANDLER = new ZHandleEventHandler(null);

    private transient ArrayList fHandles;
    private transient ZHandleEventHandler fHandleInteractor;

    /**
     * Set the default event handler used by ZHandleGroups to dispatch events
     * to their ZHandles. This can be set on an individual ZHandleGroup basis
     * by using ZHandleGroup.setEventHandler().
     */
    public static void setDefaultEventHandler(ZHandleEventHandler aEventHandler) {
        DEFAULT_EVENT_HANDLER = aEventHandler;
    }

    /**
     * Constructs an empty ZHandleGroup.
     */
    public ZHandleGroup() {
        super();
    }

    /**
     * Constructs a new handle group node with the specified node as a child of the
     * new group.  If the specified child was already a member of a tree (i.e., had a parent),
     * then this new node is inserted in the tree above the child so that the original
     * child is still in that tree, but with this node inserted in the middle of the tree.
     * If the specified child does not have a parent, then it is just made a child of this node.
     *
     * @param child Child of the new group node.
     */
    public ZHandleGroup(ZNode child) {
        super(child);
    }

    /**
     * Set the event handler used internally by handle groups to dispatch
     * events to their handles.
     */
    public void setEventHandler(ZHandleEventHandler aEventHandler) {
        fHandleInteractor = aEventHandler;
    }

    /**
     * Get the event handler used internally by handle groups to dispatch
     * events to their handles.
     */
    public ZHandleEventHandler getEventHandler() {
        if (fHandleInteractor == null) {
            fHandleInteractor = DEFAULT_EVENT_HANDLER;
        }
        return fHandleInteractor;
    }

    /**
     * Add a new handle to this group. Normally this should not be called directly. If
     * you want custom handles to appear in a handle group override the <code>getHandles</code>
     * method in the scene graph object that the handle group is decorating.
     */
    protected void addHandle(ZHandle aHandle) {
        getHandles().add(aHandle);
        aHandle.setHandleGroup(this);
        aHandle.computeBounds();
    }

    /**
     * Remove all handles from this handle group. To redisplay the handles use <code>refreshHandles</code>.
     */
    public void clearHandles() {
        Iterator i = getHandles().iterator();
        while (i.hasNext()) {
            ZHandle each = (ZHandle) i.next();
        }
        getHandles().clear();
    }

    /**
     * Recomputes and caches the bounds for this handle group, including the bounds
     * of the handles that it is managing. Generally this method is
     * called by reshape when the bounds have changed, and it should rarely
     * directly elsewhere.
     */
    public void computeBounds() {
        super.computeBounds();

        Iterator i = getHandles().iterator();
        while (i.hasNext()) {
            ZHandle each = (ZHandle) i.next();
            bounds.add(each.getBoundsReference());
        }
    }

    /**
     * Return the collection of handles that this handle group is managing. This should be
     * the same collection as returned by the edit nodes <code>getHandles</code> method.
     */
    public Collection getHandles() {
        if (fHandles == null) {
            fHandles = new ArrayList(0);
        }
        return fHandles;
    }

    /**
     * Returns the first object under the specified rectangle (if there is one)
     * in the subtree rooted with this as searched in reverse (front-to-back) order.
     * Only returns "pickable" nodes. If the pick rect intersects one of this handle groups
     * handles then that handle is set as the path object. The handle event handler looks
     * at the path object to determine if a handle was picked.
     *
     * @param rect Coordinates of pick rectangle in local coordinates
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return The picked node, or null if none
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath aPath) {
        aPath.push(this);
        Iterator i = getHandles().iterator();
        while (i.hasNext()) {
            ZHandle each = (ZHandle) i.next();
            if (rect.intersects(each.getBoundsReference())) {
                aPath.setObject(each);
                return true;
            }
        }
        aPath.pop(this);

        return super.pick(rect, aPath);
    }

    /**
     * The location of handles depends on the geometry of the underlying scene graph object
     * that they are operating on. This method is used to tell all the handles managed by
     * this handle group that something may have changed about that geometry and that they
     * should update their locations.
     */
    public void relocateHandles() {
        Iterator i = getHandles().iterator();
        while (i.hasNext()) {
            ((ZHandle)i.next()).computeBounds();
        }
        reshape();
    }

    /**
     * Clears all the handles from this handle group and then adds a new set of handles
     * created by the underlying node that this handle group is decorating.
     */
    public void refreshHandles() {
        clearHandles();

        Iterator i = editor().getNode().getHandles().iterator();
        while (i.hasNext()) {
            addHandle((ZHandle)i.next());
        }

        reshape();
    }

    /**
     * Removes a handle from this group. Normally this should not be called directly. If
     * you want to remove handles use <code>clearHandles</code>.
     */
    protected void removeHandle(ZHandle aHandle) {
        getHandles().remove(aHandle);
        aHandle.setHandleGroup(null);
    }

    /**
     * Renders this handle group along with its handles.
     */
    public void render(ZRenderContext aRenderContext) {
        super.render(aRenderContext);

        Iterator i = getHandles().iterator();
        while (i.hasNext()) {
            ZHandle each = (ZHandle) i.next();
            each.render(aRenderContext);
        }
    }

    /**
     * Dispatch all events received by the handler group to the handle event handler. This
     * event handler is responsible for interpreting these events and sending mouse
     * dragged messages to the handle under the mouse when appropriate.
     */
    public void processMouseEvent(ZMouseEvent e) {
        e.dispatchTo(getEventHandler().getFilteredEventDispatcher());
        super.processMouseEvent(e);
    }
}
/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZVisualGroup</b> is a group node that has a visual components that can be rendered.
 * It has two visual components (either or both of which could be null) which get rendered
 * before and after the node's children, respectively.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 */
public class ZVisualGroup extends ZGroup implements ZSerializable, Serializable {
                                // Default values
    static public final boolean visualComponentPickable_DEFAULT = true;     // True if this node's visual component is pickable

    /**
     * The front visual component associated with this group.
     */
    private ZVisualComponent frontVisualComponent = null;

    /**
     * The back visual component associated with this group.
     */
    private ZVisualComponent backVisualComponent = null;

    /**
     *  True if this node's front visual component is pickable
     */
    private boolean frontVisualComponentPickable = visualComponentPickable_DEFAULT;

    /**
     *  True if this node's back visual component is pickable
     */
    private boolean backVisualComponentPickable = visualComponentPickable_DEFAULT;

    //****************************************************************************
    //
    //                Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new empty visual group node.
     */
    public ZVisualGroup() {
    }

    /**
     * Constructs a new visual group node with the specified node as a child of the
     * new group.
     * @param child Child of the new group node.
     */
    public ZVisualGroup(ZNode child) {
        super(child);
    }

    /**
     * Constructs a new ZVisualGroup with the specified visual components.
     * @param newFrontVisualComponent The new front visual component that this group displays.
     * @param newBackVisualComponent The new back visual component that this group displays.
     */
    public ZVisualGroup(ZVisualComponent newFrontVisualComponent, ZVisualComponent newBackVisualComponent) {
        setFrontVisualComponent(newFrontVisualComponent);
        setBackVisualComponent(newBackVisualComponent);
    }

    /**
     * Constructs a new ZVisualGroup with the specified visual components, and
     * specified node as a child of the new group.
     * @param child Child of the new group node.
     * @param newFrontVisualComponent The new front visual component that this group displays.
     * @param newBackVisualComponent The new back visual component that this group displays.
     */
    public ZVisualGroup(ZNode child, ZVisualComponent newFrontVisualComponent, ZVisualComponent newBackVisualComponent) {
        super(child);
        setFrontVisualComponent(newFrontVisualComponent);
        setBackVisualComponent(newBackVisualComponent);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZVisualGroup newGroup = (ZVisualGroup)super.duplicateObject();

        if (frontVisualComponent != null) {
            newGroup.frontVisualComponent = (ZVisualComponent)frontVisualComponent.clone();
        }

        if (backVisualComponent != null) {
            newGroup.backVisualComponent = (ZVisualComponent)backVisualComponent.clone();
        }

        return newGroup;
    }

    /**
     * Set the front visual component associated with this group node.
     * This visual component gets rendered after the group's children,
     * thus appears visually in front.
     * If this node previously had a front visual component associated with it,
     * than that component will be replaced with the new one.
     * @param newFrontVisualComponent The new front visual component for this node.
     * @see #setBackVisualComponent
     */
    public void setFrontVisualComponent(ZVisualComponent newFrontVisualComponent) {
                                // First remove old visual component if there was one
        if (frontVisualComponent != null) {
            repaint();
            frontVisualComponent.removeParent(this);
        }
                                // Now, add new visual component
        frontVisualComponent = newFrontVisualComponent;
        if (frontVisualComponent != null) {
            frontVisualComponent.addParent(this);
        }
        updateVolatility();
        reshape();
    }

    /**
     * Return the front visual component associated with this leaf,
     * or null if none.
     */
    public final ZVisualComponent getFrontVisualComponent() {
        return frontVisualComponent;
    }

    /**
     * Set the back visual component associated with this group node.
     * This visual component gets rendered before the group's children,
     * thus appears visually in back.
     * If this node previously had a back visual component associated with it,
     * than that component will be replaced with the new one.
     * @param newBackVisualComponent The new back visual component for this node.
     * @see #setFrontVisualComponent
     */
    public void setBackVisualComponent(ZVisualComponent newBackVisualComponent) {
                                // First remove old visual component if there was one
        if (backVisualComponent != null) {
            repaint();
            backVisualComponent.removeParent(this);
        }
                                // Now, add new visual component
        backVisualComponent = newBackVisualComponent;
        if (backVisualComponent != null) {
            backVisualComponent.addParent(this);
        }
        updateVolatility();
        reshape();
    }

    /**
     * Return the back visual component associated with this leaf,
     * or null if none.
     */
    public final ZVisualComponent getBackVisualComponent() {
        return backVisualComponent;
    }

    /**
     * Compute the volatileBoundsCache, taking into consideration the front and
     * back visual components.
     */
    protected void computeVolatileBounds() {
        super.computeVolatileBounds();

        if (!childrenVolatileBoundsCache) {
            if (frontVisualComponent != null) {
                childrenVolatileBoundsCache = frontVisualComponent.getVolatileBounds();
            }

            if (!childrenVolatileBoundsCache && backVisualComponent != null) {
                childrenVolatileBoundsCache = backVisualComponent.getVolatileBounds();
            }
        }
    }

    /**
     * Specifies whether this node's front visual component is pickable.
     * If false, then the pick methods will never pick this node based on its front visual component.
     * @param pickable True if this node's front visual component should be pickable.
     */
    public void setFrontVisualComponentPickable(boolean frontVisualComponentPickable) {
        this.frontVisualComponentPickable = frontVisualComponentPickable;
    }

    /**
     * Determines if this node's front visual component is pickable.
     * If false, then the pick methods will never pick this node based on its front visual component.
     * @return True if this node's front visual component is pickable.
     */
    public final boolean isFrontVisualComponentPickable() {
        return frontVisualComponentPickable;
    }

    /**
     * Specifies whether this node's back visual component is pickable.
     * If false, then the pick methods will never pick this node based on its back visual component.
     * @param pickable True if this node's back visual component should be pickable.
     */
    public void setBackVisualComponentPickable(boolean backVisualComponentPickable) {
        this.backVisualComponentPickable = backVisualComponentPickable;
    }

    /**
     * Determines if this node's back visual component is pickable.
     * If false, then the pick methods will never pick this node based on its back visual component.
     * @return True if this node's back visual component is pickable.
     */
    public final boolean isBackVisualComponentPickable() {
        return backVisualComponentPickable;
    }

    //****************************************************************************
    //
    // Painting related methods
    //
    //***************************************************************************

    /**
     * Renders this node which results in the node's visual component getting rendered,
     * followed by its children getting rendered.
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
                                // Paint back visual component
        if (backVisualComponent != null) {
            backVisualComponent.render(renderContext);
        }

        super.render(renderContext);

                                // Paint front visual component
        if (frontVisualComponent != null) {
            frontVisualComponent.render(renderContext);
        }

        if (ZDebug.debug) {
            if (frontVisualComponent != null) {
                ZDebug.incPaintCount(); // Keep a count of how many things have been rendered
            }
            if (backVisualComponent != null) {
                ZDebug.incPaintCount(); // Keep a count of how many things have been rendered
            }
                                        // Draw bounding box if requested for debugging
            if (ZDebug.showBounds) {
                Graphics2D g2 = renderContext.getGraphics2D();
                g2.setColor(new Color(60, 60, 60));
                g2.setStroke(new BasicStroke((float)(1.0 / renderContext.getCompositeMagnification()),
                                             BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                if (frontVisualComponent != null) {
                    g2.draw(frontVisualComponent.getBoundsReference());
                }
                if (backVisualComponent != null) {
                    g2.draw(backVisualComponent.getBoundsReference());
                }
            }
        }
    }

    /**
     * Recomputes and caches the bounds for this node.  Generally this method is
     * called by reshape when the bounds have changed, and it should rarely
     * directly elsewhere.  A ZVisualGroup bounds is the bounds of its visual components
     * and its children.
     */
    protected void computeBounds() {
        super.computeBounds();
        if (frontVisualComponent != null) {
            bounds.add(frontVisualComponent.getBoundsReference());
        }
        if (backVisualComponent != null) {
            bounds.add(backVisualComponent.getBoundsReference());
        }
    }

    /**
     * Mark the scenegraph rooted at this node as being in a transaction.
     */
    protected void markInTransaction() {
        super.markInTransaction();

        if (frontVisualComponent != null) {
            frontVisualComponent.markInTransaction();
        }
        if (backVisualComponent != null) {
            backVisualComponent.markInTransaction();
        }
    }

    /**
     * Mark the scenegraph rooted at this node as not being in a transaction.
     */
    protected void markNotInTransaction() {
        super.markNotInTransaction();

        if (frontVisualComponent != null) {
            frontVisualComponent.markNotInTransaction();
        }
        if (backVisualComponent != null) {
            backVisualComponent.markNotInTransaction();
        }
    }

    //****************************************************************************
    //
    //                  Other Methods
    //
    //****************************************************************************

    /**
     * Returns the first object under the specified rectangle (if there is one)
     * in the subtree rooted with this as searched in reverse (front-to-back) order.
     * This performs a depth-first search, first picking children.
     * Only returns a node if this is "pickable".
     * If no nodes in the sub-tree are picked, then this node's visual
     * component is picked.
     * <P>
     * This first checks the front visual component for picking, then the children,
     * and then the back visual component.  However, the visual components can
     * be ignored for picking if they are set to not be pickable.
     * <p>
     * If childrenPickable is false, then this will never return a child as the picked node.
     * Instead, this node will be returned if any children are picked, or one of this node's
     * visual components is picked.  Else, it will return null.
     * @param rect Coordinates of pick rectangle in local coordinates
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return The picked node, or null if none
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {

        // Check front visual component
        if (isFrontVisualComponentPickable() && (frontVisualComponent != null)) {
            path.push(this);
            if (frontVisualComponent.pick(rect, path)) {
                if (!(frontVisualComponent instanceof ZCamera)) {
                                // Set object here rather than in component so components don't
                                // have to worry about implementation of paths.
                    path.setObject(frontVisualComponent);
                }
                return true;
            }
            path.pop(this);
        }

        // Try picking a child of the group
        if (super.pick(rect, path))
            return true;

        if (isBackVisualComponentPickable() && (backVisualComponent != null)) {
            path.push(this);
            if (backVisualComponent.pick(rect, path)) {
                if (!(backVisualComponent instanceof ZCamera)) {
                                // Set object here rather than in component so components don't
                                // have to worry about implementation of paths.
                    path.setObject(backVisualComponent);
                }
                return true;
            }
            path.pop(this);
        }

         return false;
    }

    /**
     * Return a copy of the bounds of this node's front visual component in local coordinates.
     * If this node does not have a front visual component, then this returns null.
     * @return The front visual component's bounds in local coordinates (or null if no front visual component).
     */
    public ZBounds getFrontVisualComponentBounds() {
        ZBounds bounds = null;
        if (frontVisualComponent != null) {
            bounds = frontVisualComponent.getBounds();
        }

        return bounds;
    }

    /**
     * Return a copy of the bounds of this node's front visual component in global coordinates.
     * If this node does not have a front visual component, then this returns null.
     * Note that global bounds are not cached, and this method involves some computation.
     * @return The front visual component's bounds in global coordinates (or null if no front visual component).
     */
    public ZBounds getFrontVisualComponentGlobalBounds() {
        ZBounds globalBounds = null;
        if (frontVisualComponent != null) {
            globalBounds = frontVisualComponent.getBounds();
            AffineTransform at = getLocalToGlobalTransform();
            globalBounds.transform(at);
        }

        return globalBounds;
    }

    /**
     * Return a copy of the bounds of this node's back visual component in local coordinates.
     * If this node does not have a back visual component, then this returns null.
     * @return The back visual component's bounds in local coordinates (or null if no back visual component).
     */
    public ZBounds getBackVisualComponentBounds() {
        ZBounds bounds = null;
        if (backVisualComponent != null) {
            bounds = backVisualComponent.getBounds();
        }

        return bounds;
    }

    /**
     * Return a copy of the bounds of this node's back visual component in global coordinates.
     * If this node does not have a back visual component, then this returns null.
     * Note that global bounds are not cached, and this method involves some computation.
     * @return The back visual component's bounds in global coordinates (or null if no back visual component).
     */
    public ZBounds getBackVisualComponentGlobalBounds() {
        ZBounds globalBounds = null;
        if (backVisualComponent != null) {
            globalBounds = backVisualComponent.getBounds();
            AffineTransform at = getLocalToGlobalTransform();
            globalBounds.transform(at);
        }

        return globalBounds;
    }

    /**
     * Return the bounds of this ZGroup without taking the groups children into
     * consideration. For the class ZGroup this will always return an empty bounds. But for
     * sublclasses such as ZVisualGroup it may return a non-empty bounds.
     */
    public ZBounds getShallowBounds() {
        ZBounds result = super.getShallowBounds();

        ZBounds backVisualComponentBounds = getBackVisualComponentBounds();
        if (backVisualComponentBounds != null) {
            result.add(backVisualComponentBounds);
        }

        ZBounds frontVisualComponentBounds = getFrontVisualComponentBounds();
        if (frontVisualComponentBounds != null) {
            result.add(frontVisualComponentBounds);
        }

        return result;
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

        if (frontVisualComponent != null) {
            out.writeState("ZVisualComponent", "frontVisualComponent", frontVisualComponent);
        }
        if (frontVisualComponentPickable != visualComponentPickable_DEFAULT) {
            out.writeState("boolean", "frontVisualComponentPickable", frontVisualComponentPickable);
        }
        if (backVisualComponent != null) {
            out.writeState("ZVisualComponent", "backVisualComponent", backVisualComponent);
        }
        if (backVisualComponentPickable != visualComponentPickable_DEFAULT) {
            out.writeState("boolean", "backVisualComponentPickable", backVisualComponentPickable);
        }
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        super.writeObjectRecurse(out);

                                // Add front visual component if there is one
        if (frontVisualComponent != null) {
            out.addObject(frontVisualComponent);
        }
                                // Add back visual component if there is one
        if (backVisualComponent != null) {
            out.addObject(backVisualComponent);
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

        if (fieldName.compareTo("frontVisualComponent") == 0) {
            setFrontVisualComponent((ZVisualComponent)fieldValue);
        } else if (fieldName.compareTo("frontVisualComponentPickable") == 0) {
            setFrontVisualComponentPickable(((Boolean)fieldValue).booleanValue());
        } else if (fieldName.compareTo("backVisualComponent") == 0) {
            setBackVisualComponent((ZVisualComponent)fieldValue);
        } else if (fieldName.compareTo("backVisualComponentPickable") == 0) {
            setBackVisualComponentPickable(((Boolean)fieldValue).booleanValue());
        }
    }
}
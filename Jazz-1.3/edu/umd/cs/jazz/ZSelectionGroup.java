/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.component.*;

/**
 * <b>ZSelectionGroup</b> is a visual group node that provides functionality for specifying
 * selection. Inserting a selection group in the scenegraph will visually
 * select its subtree. It has utility methods for selecting and unselecting nodes,
 * and for determining the selected nodes in a sub-tree.
 * It manages a visual component that actually represents the selection, and shows
 * a selected node by surrounding its children with a 1 pixel wide line.
 * To give selection a new visual look, an application can set the
 * SelectionComponentFactory {@link #setSelectionComponentFactory}
 * <P>
 * The primary visual component generation has changed to use a factory.  This
 * allows a developer to more easily change the look of all selected
 * components.  The following code demonstrates changing the default selection
 * to be an ellipse, rather than a rectangle:
 *
 * <pre>
 *      ZSelectionGroup.setSelectionComponentFactory(new
 *                             ZSelectionGroup.SelectionComponentFactory() {
 *          public ZVisualComponent createSelectionComponent() {
 *              return new SelectionEllipse();
 *          }
 *      });
 * </pre>
 *
 * with the following example implementation of SelectionEllipse:
 *
 * <pre>
 *      public class SelectionEllipse extends ZVisualComponent {
 *
 *          public SelectionEllipse() { }
 *
 *          public boolean pick(Rectangle2D pickRect, ZSceneGraphPath path) {
 *              return false; // pick handled by SelectionGroup
 *          }
 *
 *          public void render(ZRenderContext ctx) {
 *              Graphics2D g2 = ctx.getGraphics2D();
 *              double mag = ctx.getCompositeMagnification();
 *              double sz = 1.0 / mag;
 *              ZNode p = getParents()[0];
 *              if (p instanceof ZSelectionGroup) {
 *                  ZSelectionGroup g = (ZSelectionGroup)p;
 *                  Ellipse2D e = new Ellipse2D.Double();
 *                  e.setFrame(g.getBounds());
 *                  double x = e.getX();
 *                  double y = e.getY();
 *                  double w = e.getWidth();
 *                  double h = e.getHeight();
 *
 *                  // don't draw very small selection objects
 *                  if (w * mag < 2 || h * mag < 2) return;
 *
 *                  // shrink bounds by 1 pixel to ensure I am
 *                  // inside them
 *                  e.setFrame(x + sz, y + sz, w - sz*2, h - sz*2);
 *
 *                  g2.setStroke(new BasicStroke((float)sz));
 *                  g2.setColor(g.getPenColor());
 *                  g2.draw(e);
 *              }
 *          }
 *
 *          // SelectionEllipse's have no logical bounds.
 *          protected void computeBounds() {
 *      }
 * </pre>
 *
 * <P>
 * {@link edu.umd.cs.jazz.util.ZSceneGraphEditor} provides a convenience mechanism to locate, create
 * and manage nodes of this type.
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
public class ZSelectionGroup extends ZVisualGroup implements ZSerializable, Serializable {
                                // Default values
    static public Color penColor_DEFAULT = Color.magenta;

    static private SelectionComponentFactory componentFactory = new SelectionComponentFactory() {
        public ZVisualComponent createSelectionComponent() {
            return new SelectionRect();
        }
    };

    /**
     * Pen color for rendering of selection
     */
    private Color penColor  = penColor_DEFAULT;

    /**
     * The visual components associated with this selection group.
     */
    private ZList.ZVisualComponentList visualComponents = ZListImpl.NullList;

    //************************************************************************
    //
    //                 Inner Classes and Interfaces
    //
    //************************************************************************

    public interface SelectionComponentFactory {
        public ZVisualComponent createSelectionComponent();
    }

    //
    // Internal class used to render selection as a rectangle.
    //
    static class SelectionRect extends ZVisualComponent {

        public SelectionRect() { }

        public boolean pick(Rectangle2D pickRect, ZSceneGraphPath path) {
            return false; // pick handled by SelectionGroup
        }

        public void render(ZRenderContext ctx) {
            Graphics2D g2 = ctx.getGraphics2D();
            double mag = ctx.getCompositeMagnification();
            double sz = 1.0 / mag;
            ZNode p = (ZNode) parents.get(0);
            if (p instanceof ZSelectionGroup) {
                ZSelectionGroup g = (ZSelectionGroup)p;
                Rectangle2D r = g.getBounds();
                double x = r.getX();
                double y = r.getY();
                double w = r.getWidth();
                double h = r.getHeight();

                // don't draw very small selection objects
                if (w * mag < 2 || h * mag < 2) return;

                // shrink bounds by 1 pixel to ensure I am
                // inside them
                r.setRect(x + sz, y + sz, w - sz*2, h - sz*2);

                g2.setStroke(new BasicStroke((float)sz));
                g2.setColor(g.getPenColor());
                g2.draw(r);
            }
        }

        // SelectionRect's have no logical bounds.
        protected void computeBounds() {
        }
    }

    //************************************************************************
    //
    //                 Constructors
    //
    //************************************************************************

    /**
     * Constructs a new ZSelectionGroup.  The node must be attached to a live scenegraph (a scenegraph that is
     * currently visible) in order for it to be visible.
     */
    public ZSelectionGroup () {
        setFrontVisualComponentPickable(false);
        setFrontVisualComponent(componentFactory.createSelectionComponent());
    }

    /**
     * Constructs a new select group node with the specified node as a child of the
     * new group.
     * @param child Child of the new group node.
     */
    public ZSelectionGroup(ZNode child) {
        this();
        addChild(child);
    }

    /**
     * Adds an auxiliary visual component to this selection group
     * @param visualComponent The auxiliary visual component to be added
     */
    public void addAuxiliaryVisualComponent(ZVisualComponent visualComponent) {
        if (visualComponent == null) return;

        if (visualComponents.isNull()) {
            visualComponents = new ZListImpl.ZVisualComponentListImpl(1);
        }

        if (visualComponents.contains(visualComponent)) return;

        visualComponents.add(visualComponent);
        visualComponent.addParent(this);
        updateVolatility();
        reshape();
    }

    /**
     * Remove an auxiliary visual component from this selection group.
     * If this group didn't already contains this component, then nothing happens.
     * @param visualComponent The visual component to be removed.
     */
    public void removeAuxiliaryVisualComponent(ZVisualComponent visualComponent) {
        if (visualComponent == null) return;

                                // Check if visualComponent already exists
        int index = visualComponents.indexOf(visualComponent);
        if (index == -1) return;

        visualComponents.remove(visualComponent);
        visualComponent.removeParent(this);
        updateVolatility();
        reshape();
    }

    /**
     * Set the auxiliary visual component associated with this selection group.
     * If this node previously had any auxiliary visual components associated
     * with it, then those components will be replaced with the new one.
     * @param visualComponent The new visual component for this group.
     */
    public void setAuxiliaryVisualComponent(ZVisualComponent visualComponent) {
        clearAuxiliaryVisualComponents();
        addAuxiliaryVisualComponent(visualComponent);
    }

    /**
     * Return the auxiliary visual components associated with this selection
     * group.
     * @return The auxiliary visual components
     */
    public final ZVisualComponent[] getAuxiliaryVisualComponents() {
        return visualComponents.getVisualComponentsReference();
    }

    /**
     * Remove all auxiliary visual components from this selection group.
     */
    public void clearAuxiliaryVisualComponents() {
        ZVisualComponent[] visualComponentsRef = visualComponents.getVisualComponentsReference();
        for (int i=0; i<visualComponents.size(); i++) {
            repaint();
            visualComponentsRef[i].removeParent(this);
        }
        visualComponents.clear();
        updateVolatility();
    }

    /**
     * Internal method to compute and cache the volatility of a node,
     * to recursively call the parents to compute volatility.
     * All parents of this node are also volatile when this is volatile.
     * A leaf is volatile if either the node or any of its visual components
     * are volatile.
     * @see #setVolatileBounds(boolean)
     * @see #getVolatileBounds()
     */
    /*protected void updateVolatility() {
        ZVisualComponent frontVisualComponent = getFrontVisualComponent();
        ZVisualComponent backVisualComponent = getBackVisualComponent();

                                // If this node set to volatile, then it is volatile
        cacheVolatile = volatileBounds;

                                // Else, if either visual component is volatile, then it is volatile
        if (!cacheVolatile && frontVisualComponent != null) {
            cacheVolatile = frontVisualComponent.getVolatileBounds();
        }
        if (!cacheVolatile) {
                                // Else, if any of its visual components are volatile, then it is volatile
            cacheVolatile = visualComponents.collectiveHasVolatileBounds();
        }
        if (!cacheVolatile && backVisualComponent != null) {
            cacheVolatile = backVisualComponent.getVolatileBounds();
        }
        if (!cacheVolatile) {
                                // Else, if any of its children are volatile, then it is volatile
            cacheVolatile = children.collectiveHasVolatileBounds();
        }

                                // Update parent's volatility
        if (parent != null) {
            parent.updateVolatility();
        }
    }*/

    protected void computeVolatileBounds() {
        super.computeVolatileBounds();

        if (!volatileBounds) {
            volatileBounds = visualComponents.collectiveHasVolatileBounds();
        }
    }

    //************************************************************************
    //
    // Static convenience methods to manage selection
    //
    //************************************************************************

    /**
     * @deprecated as of Jazz 1.1,
     * replaced by <code>ZSelectionManager.getSelectedNodes(ZNode)</code>
     */
    static public ArrayList getSelectedNodes(ZNode node) {
        return ZSelectionManager.getSelectedNodes(node);
    }

    /**
     * @deprecated as of Jazz 1.1,
     * replaced by <code>ZSelectionManager.getSelectedNodes(ZCamera)</code>
     */
    static public ArrayList getSelectedNodes(ZCamera camera) {
        return ZSelectionManager.getSelectedNodes(camera);
    }

    /**
     * @deprecated as of Jazz 1.1,
     * replaced by <code>ZSelectionManager.select(ZNode)</code>
     */
    static public ZSelectionGroup select(ZNode node) {
        return ZSelectionManager.select(node);
    }

    /**
     * @deprecated as of Jazz 1.1,
     * replaced by <code>ZSelectionManager.unselect(ZNode)</code>
     */
    static public void unselect(ZNode node) {
        ZSelectionManager.unselect(node);
    }

    /**
     * @deprecated as of Jazz 1.1,
     * replaced by <code>ZSelectionManager.unselectAll(ZNode)</code>
     */
    static public void unselectAll(ZNode node) {
        ZSelectionManager.unselectAll(node);
    }

    /**
     * @deprecated as of Jazz 1.1,
     * replaced by <code>ZSelectionManager.unselectAll(ZCamera)</code>
     */
    static public void unselectAll(ZCamera camera) {
        ZSelectionManager.unselectAll(camera);
    }

    /**
     * @deprecated as of Jazz 1.1,
     * replaced by <code>ZSelectionManager.isSelected(ZNode)</code>
     */
    static public boolean isSelected(ZNode node) {
        return ZSelectionManager.isSelected(node);
    }

    //************************************************************************
    //
    //                  Get/Set and pairs
    //
    //************************************************************************

    /**
     * Get the pen color that is used to render the selection.
     * @return the pen color.
     */
    public Color getPenColor() {
        return penColor;
    }

    /**
     * Set the pen color that is used to render the selection.
     * @param color the pen color, or null if none.
     */
    public void setPenColor(Color color) {
        penColor = color;
        repaint();
    }

    //************************************************************************
    //
    // Painting related methods
    //
    //************************************************************************

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
        ZVisualComponent frontVisualComponent = getFrontVisualComponent();
        ZVisualComponent backVisualComponent = getBackVisualComponent();

                                // Paint back visual component
        if (backVisualComponent != null) {
            backVisualComponent.render(renderContext);
        }

        super.render(renderContext);


                                // Paint auxiliary visual components
        ZVisualComponent[] visualComponentsRef = visualComponents.getVisualComponentsReference();
        for (int i = visualComponents.size()-1; i >= 0; i--) {
            visualComponentsRef[i].render(renderContext);
        }

                                // Paint front visual component
        if (frontVisualComponent != null) {
            frontVisualComponent.render(renderContext);
        }


        if (ZDebug.debug) {
                                // Keep a count of how many things have been rendered
            if (frontVisualComponent != null) {
                ZDebug.incPaintCount();
            }
            if (backVisualComponent != null) {
                ZDebug.incPaintCount();
            }
            for(int i=0; i<visualComponents.size(); i++) {
                ZDebug.incPaintCount();
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

                for(int i=0; i<visualComponents.size(); i++) {
                    g2.draw(visualComponentsRef[i].getBoundsReference());
                }
            }
        }
    }

    //************************************************************************
    //
    //                 Other Methods
    //
    //************************************************************************

    /**
     * ZSelectionGroup overrides this method to check whether any of the
     * auxiliary visual components have been picked.
     * @param rect The picking rectangle
     * @param path The picking path up to this node
     * @return True if this node has been picked
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        // Check the auxiliary visual components
        if (isPickable() && (visualComponents.size() > 0)) {
            ZNode node = editor().getNode();
            ZNode group = this;

            // We have to push all the nodes down to the visual node
            // so we fire the event on the proper node
            while (group != node) {
                path.push(group);
                group = ((ZGroup)group).getChildrenReference()[0];
            }
            path.push(node);

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

        // Try picking a child of the group
        return super.pick(rect, path);
    }

    /**
     * Set the SelectionComponentFactory used to generate visual components
     * for selected objects
     * @param componentFactory The new SelectionComponentFactory
     */
    public static void setSelectionComponentFactory(SelectionComponentFactory newFactory) {
        componentFactory = newFactory;
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
                                // Selection is tricky to write out because, it defines
                                // a custom visual component which is a private class -
                                // and private classes are not ZSerializable.  Because
                                // the class is referenced from this class's superclass
                                // (ZVisualComponent), we can not just make the reference
                                // transient so it doesn't get written out.  Instead,
                                // we do this trick here of setting that object reference
                                // to null before we write out this selection, and then
                                // restore it when we are finished.
        ZVisualComponent vc = getFrontVisualComponent();
        setFrontVisualComponent(null);
        super.writeObject(out);
        setFrontVisualComponent(vc);
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        ZVisualComponent vc = getFrontVisualComponent();
        setFrontVisualComponent(null);
        super.writeObjectRecurse(out);
        setFrontVisualComponent(vc);
    }

    /**
     * Trims the capacity of the array that stores the parents list points to
     * the actual number of points.  Normally, the parents list arrays can be
     * slightly larger than the number of points in the parents list.
     * An application can use this operation to minimize the storage of a
     * parents list.
     */
    public void trimToSize() {
        super.trimToSize();
        visualComponents.trimToSize();
    }
}

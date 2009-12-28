/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;

/**
 * <b>ZLayoutGroup</b> is a visual group that wraps a layout manager that can position
 * the node's children. The layout manager may also include a visual
 * component that aids the layout. For instance, the tree layout manager adds links
 * connecting the tree nodes.
 * <P>
 * {@link edu.umd.cs.jazz.util.ZSceneGraphEditor} provides a convenience mechanism to locate, create
 * and manage nodes of this type.
 * <P>
 * <b>BUG 6/28/2001:</b> JAG - ZLayoutGroup will not invalidate the layout when one of the
 * layoutChilds bounds changes. This means that you must manually invalidate
 * ZlayoutGroups in those cases.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 */
public class ZLayoutGroup extends ZVisualGroup implements ZSerializable, Serializable {

    static private ArrayList invalidatedNodes = new ArrayList();      // The list of layout nodes that need to be revalidated
    static private ArrayList validatedInvalidNodes = new ArrayList(); // The list of layout nodes that have been revalidated and are ready to be removed

    static private boolean revalidating = false;                      // True during revalidation

    /**
     * The layout manager that lays out the children of this node,
     * or null if none.
     */
    private ZLayoutManager layoutManager = null;

    /**
     * The node whose children this layout should be applied to.
     */
    private ZGroup layoutChild = null;

    /**
     * True if this layout group is currently validated.
     * False if it needs to be revalidated.
     */
    private boolean validated = true;

    /**
     * Listens to the layoutChild and invalidates the layout when
     * a child is added or removed.
     */
    private transient ZGroupListener layoutUpdateManager;

    //****************************************************************************
    //
    // Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new empty layout group node.
     */
    public ZLayoutGroup() {
        setLayoutChild(this);
    }

    /**
     * Constructs a new layout group node with the specified node as a child of the
     * new group.  This group will apply its layout manager (if there is one) to
     * the children of its layoutChild node (or itself if there isn't one).
     * @param child Child of the new group node.
     * @see #setLayoutChild
     */
    public ZLayoutGroup(ZNode child) {
        super(child);
    }

    /**
     * Constructs a new layout group node with the specified node as a child of the
     * new group.  This group will apply its layout manager (if there is one) to
     * the children of its layoutChild node (or itself if there isn't one).
     * @param child Child of the new group node.
     */
    public ZLayoutGroup(ZNode child, ZGroup layoutChild) {
        super(child);
        setLayoutChild(layoutChild);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZLayoutGroup newGroup = (ZLayoutGroup)super.duplicateObject();
        if (layoutManager != null) {
            newGroup.layoutManager = (ZLayoutManager)layoutManager.clone();
            newGroup.layoutUpdateManager = null;
        }
        newGroup.validated = false;
        return newGroup;
    }

    /**
     * Called to update internal object references after a clone operation
     * by {@link ZSceneGraphObject#clone}.
     *
     * @see ZSceneGraphObject#updateObjectReferences
     */
    protected void updateObjectReferences(ZObjectReferenceTable objRefTable) {
        super.updateObjectReferences(objRefTable);

        if (layoutChild != null) {

            ZGroup duplicatedLayoutChild = (ZGroup)objRefTable.getNewObjectReference(layoutChild);
            setLayoutChild(duplicatedLayoutChild);
            if (layoutChild == null) {
                // Cloning this object also clones the children - so we can
                // should be certain to get a layout child.
                throw new RuntimeException("Problem in updateObjectReferences: " + this);
            }

        }
    }

    //****************************************************************************
    //
    // Get/Set pairs
    //
    //***************************************************************************

    /**
     * Specifies the layout manager for this node.
     * @param manager The new layout manager.
     */
    public void setLayoutManager(ZLayoutManager manager) {
        layoutManager = manager;
        invalidate();
    }

    /**
     * Returns the current layout manager for this node.
     * @return The current layout manager.
     */
    public final ZLayoutManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * Specifies the layout child for this node.
     * This node's layout manager will apply to the children of the specified child.
     * If no layout child is specified, then the layout will apply to this node's children.
     * @param child The new layout child.
     */
    public void setLayoutChild(ZGroup child) {
        if (child != this && !child.isDescendentOf(this)) {
            throw new ZNodeNotFoundException("Node " + child + " is not a descendant of " + this);
        }

        if (layoutChild != null) {
            layoutChild.removeGroupListener(layoutUpdateManager);
        }

        layoutChild = child;

        if (layoutChild != null) {
            // Listen for group events on the layout child.
            // When a node is added or removed invalidate
            // the current layout.
            layoutUpdateManager = new ZGroupListener() {
                public void nodeAdded(ZGroupEvent e) {
                    invalidate();
                }

                public void nodeRemoved(ZGroupEvent e) {
                    invalidate();
                }
            };
            layoutChild.addGroupListener(layoutUpdateManager);
        }
    }

    /**
     * Returns the current layout child for this node.
     * @return The current layout child.
     */
    public final ZGroup getLayoutChild() {
        return layoutChild;
    }

    //****************************************************************************
    //
    //                  Other Methods
    //
    //***************************************************************************

    /**
     * Specify that this layout group is out of date, and needs to be revalidated.
     * A request will be made to apply the group's layout.
     */
    public void invalidate() {
                                // We don't need to queue requests while we are revalidating
        if (revalidating) {
            return;
        }
        validated = false;

                                // Add layout to list of invalid nodes, but merge with ancestors
        ArrayList removeList = new ArrayList();
        ZLayoutGroup layout;

        for (Iterator i=invalidatedNodes.iterator(); i.hasNext();) {
            layout = (ZLayoutGroup)i.next();
            if (layout == this) {
                                // We are already on the list - no further need to do anything here
                return;
            } else if (layout.isAncestorOf(this)) {
                                // Do nothing if an ancestor is already invalidated
                return;
            } else if (isAncestorOf(layout)) {
                                // This is an ancestor of an invalidated node, so mark this one instead
                removeList.add(layout);
            }
        }
        invalidatedNodes.add(this);

                                // Now, update the remove list
        for (Iterator i=removeList.iterator(); i.hasNext();) {
            layout = (ZLayoutGroup)i.next();
            invalidatedNodes.remove(layout);
        }
                                // Finally, queue a layout revalidation
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                revalidating = true;
                ZLayoutGroup l;
                for (Iterator i=invalidatedNodes.iterator(); i.hasNext();) {
                    l = (ZLayoutGroup)i.next();
                    l.doLayout();
                    validatedInvalidNodes.add(l);
                }
                revalidating = false;

                // Remove the validated nodes in the layout queue so repaint
                // requests on the event thread will not cause an infinite
                // loop if volatileBounds are set
                SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            ZLayoutGroup layout;
                            for(Iterator i=validatedInvalidNodes.iterator(); i.hasNext();) {
                                layout = (ZLayoutGroup)i.next();
                                invalidatedNodes.remove(layout);
                            }
                        }
                    });
            }
        });
    }

    /**
     * A helper method to recursively descend the subtree rooted at
     * <code>aNode</code> in a depth-first order and mark the entire
     * subtree as needing validation
     * @param aNode The node whose subtree is to be marked invalid
     */
    static void invalidateChildren(ZNode aNode) {
        if (aNode instanceof ZGroup) {
            ZNode[] children = ((ZGroup)aNode).getChildren();
            for(int i=0; i<children.length; i++) {
                invalidateChildren(children[i]);
            }
            if (aNode instanceof ZLayoutGroup) {
                ((ZLayoutGroup)aNode).validated = false;
            }
        }
    }

    /**
     * Force an immediate validation of this layout node (if it was invalidated).
     * This will result in this node's layout being applied immediately if it was
     * out of date, and any queued requests to layout this node will be removed.
     */
    public void validate() {
        invalidatedNodes.remove(this);
        doLayout();
    }

    /**
     * Causes the children of the specified layout node to be laid out.
     * It performs a depth-first layout, doing children first, and parents last,
     * recursively checking to see if the children have layout groups, and if
     * so, laying them out.
     * If there is no layout manager for this node, then nothing happens.
     * @see #getLayoutManager
     * @see #setLayoutChild
     */
    public void doLayout() {
        ZLayoutManager manager = getLayoutManager();
                                // If there is a layout manager
        if (!validated) {
            ZGroup layoutNode = layoutChild;
            if (layoutNode == null) {
                layoutNode = this;
            }

            if (manager != null) {
                manager.preLayout(layoutNode);       // Notify layout manager that recursive layout is starting
            }

            ZNode[] children = layoutNode.getChildren();
            for (int i=0; i<children.length; i++) {
                doLayoutInternal(children[i]);
            }

            if (manager != null) {
                manager.doLayout(layoutNode);        // Do the layout
                manager.postLayout(layoutNode);      // Notify layout manager that recursive layout is ending
            }

            validated = true;
        }
    }

    /**
     * Internal method to recursively search children to look for layout nodes,
     * and apply the layout if found.
     * @param node The node to check for layout
     */
    protected void doLayoutInternal(ZNode node) {
        if (node instanceof ZLayoutGroup) {
            ZLayoutGroup layout = (ZLayoutGroup)node;
            if (!layout.validated) {
                layout.doLayout();
            }
        } else if (node instanceof ZGroup) {
            ZNode[] children = ((ZGroup)node).getChildren();
            for (int i=0; i<children.length; i++) {
                doLayoutInternal(children[i]);
            }
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

        if (layoutManager != null) {
            out.writeState("ZLayoutManager", "layoutManager", layoutManager);
        }
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        super.writeObjectRecurse(out);

                                // Add layout manager if there is one
        if (layoutManager != null) {
            out.addObject(layoutManager);
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

        if (fieldName.compareTo("layoutManager") == 0) {
            setLayoutManager((ZLayoutManager)fieldValue);
        }

        // Reconnect to the layoutChild.
        if (layoutChild != null) {
            setLayoutChild(layoutChild);
        } else {
            setLayoutChild(this);
        }
    }

    /**
     * Read in all of this object's state.
     * @param in The stream that this object reads from.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        // Reconnect to the layoutChild.
        if (layoutChild != null) {
            setLayoutChild(layoutChild);
        } else {
            setLayoutChild(this);
        }
    }
}
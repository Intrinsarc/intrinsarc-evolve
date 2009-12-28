/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.io.*;
import edu.umd.cs.jazz.*;
import java.util.*;

/**
 * <b>ZSceneGraphEditor</b> provides a convenience mechanism used to locate
 * and create instances of the following types of group nodes:
 * <p>
 * <ul>
 * <li>Custom (i.e., any group that the editor does not have ordering information, see below)
 * <li>ZNameGroup
 * <li>ZInvisibleGroup
 * <li>ZLayoutGroup
 * <li>ZAnchorGroup
 * <li>ZTransformGroup
 * <li>ZStickyGroup
 * <li>ZSelectionGroup
 * <li>ZClipGroup
 * <li>ZFadeGroup
 * <li>ZHandleGroup
 * <li>ZSpatialIndexGroup
 * </ul>
 *
 * ZSceneGraphEditor uses lazy evaluation to automatically create these
 * group nodes in a scene graph as they are needed.<p>
 *
 * For example, you can use a graph editor to obtain a ZTransformGroup
 * for a leaf node. The first time you do this, a new ZTransformGroup
 * is inserted above the leaf. Subsequently, that ZTransformGroup
 * is reused. e.g.<p>
 *
 * <pre>
 *    ZTransformGroup t = node.editor().getTransformGroup();
 *    t.translate(1, 0);
 * </pre>
 *
 * will translate <i>node</i> by 1, 0. Repeatedly executing
 * this code will cause the node to move horizontally across the
 * screen.<p>
 *
 * If multiple group types are created for a given node in the scene graph,
 * they are ordered as shown in the list above - so custom groups
 * will be inserted at the top (closest to the root), whereas spatialIndex groups will be
 * inserted at the bottom (closest to the node being edited.)<p>
 *
 * Group nodes inserted into the scene graph by ZSceneGraphEditor have
 * their hasOneChild flag set to true - such group nodes are referred to
 * as "edit groups", and can only act on the single node immediately
 * beneath them in the scene graph. This guarantees that
 * translations or scalings applied to a ZTransformGroup
 * node created by ZSceneGraphEditor effect only the node being edited.
 * ZSceneGraphEditor uses the hasOneChild flag to identify edit
 * groups that it has created in the scene graph, to avoid creating the
 * same group nodes twice.<p>
 *
 * Customization:
 *
 * You can also use the scene graph editor to manage your own custom edit
 * groups. This is done with the following methods;<p>
 *
 * <pre>
 *      getEditGroup(ZMyCustomGroup.class);
 *      hasEditGroup(ZMyCustomGroup.class);
 *      removeEditGroup(ZMyCustomGroup.class);
 * </pre>
 *
 * If ZMyCustomGroup.class is a class that the scene graph editor does not
 * have ordering information for then the edit group will be inserted at
 * the top of the edit groups.<p>
 *
 * If you need to have your edit group inserted somewhere else (for example
 * below the ZTransformGroup editor) then you need to give the
 * ZSceneGraphEditor ordering information for this that type. This is done
 * by calling<p>
 *
 * <pre>
 *      ZSceneGraphEditor.setEditorOrder(ZMyCustomGroup.class, ZSceneGraphEditor.TRANSFORM_GROUP_ORDER - 1);
 * </pre>
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see edu.umd.cs.jazz.ZNode#editor
 * @author Jonathan Meyer, 25 Aug 99
 * @author Ben Bederson
 * @author Jesse Grosjean
 */
public class ZSceneGraphEditor implements Serializable {
    // Stores Class->Order associations.
    private static HashMap editorOrderingMap;

    /**
     * Defines the ordering of the edit goups above the edit node.
     */
    public static final int NAME_GROUP_ORDER = 90;
    public static final int INVISIBLE_GROUP_ORDER = 80;
    public static final int LAYOUT_GROUP_ORDER = 70;
    public static final int ANCHOR_GROUP_ORDER = 60;
    public static final int TRANSFORM_GROUP_ORDER = 50;
    public static final int STICKY_GROUP_ORDER = 40;
    public static final int SELECTION_GROUP_ORDER = 30;
    public static final int CLIP_GROUP_ORDER = 20;
    public static final int FADE_GROUP_ORDER = 10;
    public static final int HANDLE_GROUP_ORDER = 9;
    public static final int SPATIAL_INDEX_GROUP_ORDER = 0;

    /**
     * The edit node. This node is found in the ZSceneGraphEditor constructor.
     */
    protected ZNode editNode;

    // Iterates up from the first edit group above the edit node. The last
    // object returned by the iterator will be the "top" edit group.
    public class ZEditGroupIterator implements Iterator {
        protected ZNode currentEditor = editNode;

        public boolean hasNext() {
            return isEditGroup(currentEditor.getParent());
        }
        public Object next() {
            currentEditor = currentEditor.getParent();
            return currentEditor;
        }
        public void remove() {
            currentEditor.extract();
        }
        public ZNode last() {
            return currentEditor;
        }
    }

    // Initialize editor orderings.
    static {
        ZSceneGraphEditor.setEditorOrder(ZSpatialIndexGroup.class, SPATIAL_INDEX_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZHandleGroup.class, HANDLE_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZFadeGroup.class, FADE_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZClipGroup.class, CLIP_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZSelectionGroup.class, SELECTION_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZStickyGroup.class, STICKY_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZTransformGroup.class, TRANSFORM_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZAnchorGroup.class, ANCHOR_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZLayoutGroup.class, LAYOUT_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZInvisibleGroup.class, INVISIBLE_GROUP_ORDER);
        ZSceneGraphEditor.setEditorOrder(ZNameGroup.class, NAME_GROUP_ORDER);
    }

    /**
     * Create a new editor for aNode.
     */
    public ZSceneGraphEditor(ZNode aNode) {
        super();

        while (isEditGroup(aNode)) {
            aNode = ((ZGroup)aNode).getChild(0);
        }
        editNode = aNode;
    }

    /**
     * Set the ordering information for aType of edit group.
     */
    public static void setEditorOrder(Class aType, int aOrder) {
        if (editorOrderingMap == null) {
            editorOrderingMap = new HashMap();
        }
        editorOrderingMap.put(aType, new Integer(aOrder));
    }

    /**
     * If ZSceneGraphEditor has inserted groups above a node, this
     * returns the topmost of those groups (the group nearest the root of the
     * scene graph). If no edit groups have been inserted above a node,
     * this returns the node itself. This method is useful if you want
     * to remove a node and also its associated edit groups
     * from a scene graph.
     *
     * @return The top edit group, or the edit node of there are no edit groups.
     */
    public ZNode getTop() {
        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) { i.next(); }
        return i.last();
    }

    /**
     * Returns the node being edited.  This is the node that is the bottom-most
     * node of an edit group.  It is defined as being this node, or the first
     * descendant that does not have 'hasOneChild' set.
     *
     * @return The node being edited.
     */
    public ZNode getNode() {
        return editNode;
    }

    /**
     * Returns an array representing the list of "edit" groups
     * above the node - groups above the node whose hasOneChild
     * flag is set to true.  The groups are listed in bottom-top order.
     *
     * @deprecated as of Jazz 1.1 editorIterator() instead.
     */
    public ArrayList getGroups() {
        ArrayList result = new ArrayList();
        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) {
            result.add(i.next());
        }
        return result;
    }

    /**
     * Returns an iterator over the list of "edit" groups
     * above the node - groups above the node whose hasOneChild
     * flag is set to true. The groups are listed in bottom-top order.
     *
     * @return An iterator over the edit groups being managed.
     */
    public Iterator editorIterator() {
        return new ZEditGroupIterator();
    }

    /**
     * Checks to see if an editor of this type already exists, if so it returns that editor
     * if not it creates a new editor, inserts that editor above the edit node, and then
     * returns that new editor.
     *
     * @param type The type of edit group to return
     * @return The edit group
     */
    public ZGroup getEditGroup(Class aType) {
        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) {
            ZGroup each = (ZGroup) i.next();
            if (each.getClass() == aType) {
                return each;
            }
        }

        ZGroup result = createEditGroupInstance(aType);
        insertEditGroup(result);
        return result;
    }

    /**
     * Returns a ZTransformGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZTransformGroup getTransformGroup() {
        // XXX this is ugly because ZIndexGroups need so much maintanence... should fix.
        ZTransformGroup result = null;

        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) {
            ZGroup each = (ZGroup) i.next();
            if (each.getClass() == ZTransformGroup.class) {
                result = (ZTransformGroup) each;
                break;
            }
        }

        if (result == null) {
            result = (ZTransformGroup) createEditGroupInstance(ZTransformGroup.class);
            insertEditGroup(result);
                                // If the node getting this transformGroup is spatial-indexed,
                                // add a node listener so it will be re-indexed when it's
                                // bounds change.
            ZNode top = getTop().getParent();
            if ((top != null) && (top.editor().hasSpatialIndexGroup())) {
                ZSpatialIndexGroup indexGroup = top.editor().getSpatialIndexGroup();
                indexGroup.addListener(result);
            }
        }
        return result;
    }

    /**
     * Returns a ZHandleGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZHandleGroup getHandleGroup() {
        return (ZHandleGroup) getEditGroup(ZHandleGroup.class);
    }

    /**
     * Returns a ZFadeGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZFadeGroup getFadeGroup() {
        return (ZFadeGroup) getEditGroup(ZFadeGroup.class);
    }

    /**
     * Returns a ZStickyGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZStickyGroup getStickyGroup() {
        return (ZStickyGroup) getEditGroup(ZStickyGroup.class);
    }

    /**
     * Returns a ZSelectionGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZSelectionGroup getSelectionGroup() {
        return (ZSelectionGroup) getEditGroup(ZSelectionGroup.class);
    }

    /**
     * Returns a ZAnchorGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZAnchorGroup getAnchorGroup() {
        return (ZAnchorGroup) getEditGroup(ZAnchorGroup.class);
    }

    /**
     * Returns a ZLayoutGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZLayoutGroup getLayoutGroup() {
        return (ZLayoutGroup) getEditGroup(ZLayoutGroup.class);
    }

    /**
     * Returns a ZNameGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZNameGroup getNameGroup() {
        return (ZNameGroup) getEditGroup(ZNameGroup.class);
    }

    /**
     * Returns a ZInvisibleGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZInvisibleGroup getInvisibleGroup() {
        return (ZInvisibleGroup) getEditGroup(ZInvisibleGroup.class);
    }

    /**
     * Returns a ZSpatialIndexGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZSpatialIndexGroup getSpatialIndexGroup() {
        return (ZSpatialIndexGroup) getEditGroup(ZSpatialIndexGroup.class);
    }

    /**
     * Returns a ZClipGroup to use for a node,
     * inserting one above the edited node if none exists.
     */
    public ZClipGroup getClipGroup() {
        return (ZClipGroup) getEditGroup(ZClipGroup.class);
    }

    /**
     * Searches to see if the edit node has an edit group of a matching type.
     *
     * @param type The type of edit group to search for.
     * @return True if such an edit group was found.
     */
    public boolean hasEditGroup(Class aType) {
        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) {
            ZGroup each = (ZGroup) i.next();
            if (each.getClass() == aType) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this node has a ZTransformGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasTransformGroup() {
        return hasEditGroup(ZTransformGroup.class);
    }

    /**
     * Returns true if this node has a ZHandleGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasHandleGroup() {
        return hasEditGroup(ZHandleGroup.class);
    }

    /**
     * Returns true if this node has a ZFadeGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasFadeGroup() {
        return hasEditGroup(ZFadeGroup.class);
    }

    /**
     * Returns true if this node has a ZStickyGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasStickyGroup() {
        return hasEditGroup(ZStickyGroup.class);
    }

    /**
     * Returns true if this node has a ZSelectionGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasSelectionGroup() {
        return hasEditGroup(ZSelectionGroup.class);
    }

    /**
     * Returns true if this node has a ZAnchorGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasAnchorGroup() {
        return hasEditGroup(ZAnchorGroup.class);
    }

    /**
     * Returns true if this node has a ZLayoutGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasLayoutGroup() {
        return hasEditGroup(ZLayoutGroup.class);
    }

    /**
     * Returns true if this node has a ZNameGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasNameGroup() {
        return hasEditGroup(ZNameGroup.class);
    }

    /**
     * Returns true if this node has a ZInvisibleGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasInvisibleGroup() {
        return hasEditGroup(ZInvisibleGroup.class);
    }

    /**
     * Returns true if this node has a ZSpatialIndexGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasSpatialIndexGroup() {
        return hasEditGroup(ZSpatialIndexGroup.class);
    }

    /**
     * Returns true if this node has a ZClipGroup above it as
     * an edit group, false otherwise.
     */
    public boolean hasClipGroup() {
        return hasEditGroup(ZClipGroup.class);
    }

    /**
     * Looks for an edit group of matching type, and if found removes that edit group.
     *
     * @param type The type of edit group to remove
     * @return True if the edit group was found and removed.
     */
    public boolean removeEditGroup(Class aType) {
        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) {
            ZGroup each = (ZGroup) i.next();
            if (each.getClass() == aType) {
                i.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a TransformGroup edit group from above a node. Returns true
     * if the ZTransformGroup could be removed, false otherwise.
     */
    public boolean removeTransformGroup() {
        return removeEditGroup(ZTransformGroup.class);
    }

    /**
     * Removes a ZHandleGroup edit group from above a node. Returns true
     * if the ZHandleGroup could be removed, false otherwise.
     */
    public boolean removeHandleGroup() {
        return removeEditGroup(ZHandleGroup.class);
    }

    /**
     * Removes a ZFadeGroup edit group from above a node. Returns true
     * if the ZFadeGroup could be removed, false otherwise.
     */
    public boolean removeFadeGroup() {
        return removeEditGroup(ZFadeGroup.class);
    }

    /**
     * Removes a ZStickyGroup edit group from above a node. Returns true
     * if the ZStickyGroup could be removed, false otherwise.
     */
    public boolean removeStickyGroup() {
        return removeEditGroup(ZStickyGroup.class);
    }

    /**
     * Removes a ZSelectionGroup edit group from above a node. Returns true
     * if the ZSelectionGroup could be removed, false otherwise.
     */
    public boolean removeSelectionGroup() {
        return removeEditGroup(ZSelectionGroup.class);
    }

    /**
     * Removes a ZAnchorGroup edit group from above a node. Returns true
     * if the ZAnchorGroup could be removed, false otherwise.
     */
    public boolean removeAnchorGroup() {
        return removeEditGroup(ZAnchorGroup.class);
    }

    /**
     * Removes a ZLayoutGroup edit group from above a node. Returns true
     * if the ZLayoutGroup could be removed, false otherwise.
     */
    public boolean removeLayoutGroup() {
        return removeEditGroup(ZLayoutGroup.class);
    }

    /**
     * Removes a ZNameGroup edit group from above a node. Returns true
     * if the ZNameGroup could be removed, false otherwise.
     */
    public boolean removeNameGroup() {
        return removeEditGroup(ZNameGroup.class);
    }

    /**
     * Removes a ZInvisibleGroup edit group from above a node. Returns true
     * if the ZInvisibleGroup could be removed, false otherwise.
     */
    public boolean removeInvisibleGroup() {
        return removeEditGroup(ZInvisibleGroup.class);
    }

    /**
     * Removes a ZClipGroup edit group from above a node. Returns true
     * if the ZClipGroup could be removed, false otherwise.
     */
    public boolean removeClipGroup() {
        return removeEditGroup(ZClipGroup.class);
    }

    /**
     * Removes a ZSpatialIndexGroup edit group from above a node. Returns true
     * if the ZSpatialIndexGroup could be removed, false otherwise.
     */
    public boolean removeSpatialIndexGroup() {
        // XXX this is ulgy, need to do something about index groups to make them easier
        // to work with.
        ZSpatialIndexGroup indexGroup = null;

        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) {
            ZGroup each = (ZGroup) i.next();
            if (each.getClass() == ZSpatialIndexGroup.class) {
                indexGroup = (ZSpatialIndexGroup) each;
                break;
            }
        }

        if (indexGroup == null) {
            return false;
        } else {
            ZGroup g = null;
            for (Iterator iter = editorIterator(); iter.hasNext();) {
                g = (ZGroup)iter.next();
                if (g.getClass() == ZSpatialIndexGroup.class) {
                    ZSpatialIndexGroup spatialIndexGroup = (ZSpatialIndexGroup)g;
                    spatialIndexGroup.unregisterAllListeners();
                }
            }
            return removeEditGroup(ZSpatialIndexGroup.class);
        }
    }

    /**
     * Returns true if aNode is an edit group;
     */
    protected boolean isEditGroup(ZNode aNode) {
        if (aNode == null) return false;
        return (aNode instanceof ZGroup && ((ZGroup)aNode).hasOneChild() && ((ZGroup)aNode).getNumChildren() == 1);
    }

    /**
     * Insert aGroup above the edit node.
     */
    protected void insertEditGroup(ZGroup aGroup) {
        if (hasEditGroup(aGroup.getClass())) return;

        ZNode aChild = null;
        int order = getOrderFor(aGroup.getClass());
        ZEditGroupIterator i = new ZEditGroupIterator();
        while (i.hasNext()) {
            ZGroup each = (ZGroup) i.next();
            if (getOrderFor(each.getClass()) >= order) {
                break;
            }
            aChild = each;
        }

        if (aChild == null) {
            aChild = editNode;
        }

        aGroup.insertAbove(aChild);
    }

    /**
     * Returns the order where aType of editor group should be inserted above the
     * edit node. If no order is specified then it returns MAX_VALUE, meaning that the
     * edit group will be inserted at the top of the edit groups.
     */
    protected int getOrderFor(Class aType) {
        Integer anInteger = (Integer) editorOrderingMap.get(aType);
        if (anInteger == null) {
            return Integer.MAX_VALUE;
        }
        return anInteger.intValue();
    }

    protected ZGroup createEditGroupInstance(Class aType) {
        ZGroup result = null;

        try {
            result = (ZGroup) aType.newInstance();
            result.setHasOneChild(true);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }
}
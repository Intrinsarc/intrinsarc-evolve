/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * <code>ZSelectionManager</code> allows for registration of
 * <b>ZGroupListener</b> instances with <code>addGroupListener()</code>.
 * Registered <code>ZGroupListener</code> instances will be notified when
 * items are added or removed from the current selection for a given camera.
 * <P>
 * Note that this class should not be instantiated.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see edu.umd.cs.jazz.event.ZSelectionModifyHandler
 * @see edu.umd.cs.jazz.event.ZSelectionDeleteHandler
 * @see edu.umd.cs.jazz.event.ZSelectionMoveHandler
 * @see edu.umd.cs.jazz.event.ZSelectionScaleHandler
 * @author Antony Courtney, Yale University
 * @author Lance Good, University of Maryland
 * @author Benjamin Bederson, University of Maryland
 */
public class ZSelectionManager {

    private static final Object ALL_CAMERA_LISTENERS = new Object();

    /**
     * Listeners to be notified when the selection changes for a given camera
     */
    private static transient HashMap listenerListMap = new HashMap();

    /**
     * This class should not be instantiated
     */
    private ZSelectionManager() {
    }

    /**
     * Add a ZGroupListener, registered for group selection events on
     * all cameras
     * @param l The listener to add
     */
    public static void addGroupListener(ZGroupListener l) {
        EventListenerList listenerList = (EventListenerList)listenerListMap.get(ALL_CAMERA_LISTENERS);

        if (listenerList == null) {
            listenerList = new EventListenerList();
            listenerListMap.put(ALL_CAMERA_LISTENERS,listenerList);
        }
        listenerList.add(ZGroupListener.class, l);
    }

    /**
     * Remove a ZGroupListener, registered for group selection events on
     * all cameras
     * @param l The listener to remove
     */
    public static void removeGroupListener(ZGroupListener l) {
        EventListenerList listenerList = (EventListenerList)listenerListMap.get(ALL_CAMERA_LISTENERS);

        if (listenerList != null) {
            listenerList.remove(ZGroupListener.class, l);
        }
    }

    /**
     * Add a ZGroupListener, registered for group selection events on
     * the given camera
     * @param l The listener to add
     * @param camera The camera on which to listen for selection events
     */
    public static void addGroupListener(ZGroupListener l, ZCamera camera) {
        EventListenerList listenerList = (EventListenerList)listenerListMap.get(camera);
        if (listenerList == null) {
            listenerList = new EventListenerList();
            listenerListMap.put(camera,listenerList);
        }
        listenerList.add(ZGroupListener.class, l);
    }

    /*
     * Remove a ZGroupListener, registered for group selection events
     * on the given camera
     * @param l The listener to remove
     * @param camera The camera to which the listener listened
     */
    public static void removeGroupListener(ZGroupListener l, ZCamera camera) {
        EventListenerList listenerList = (EventListenerList)listenerListMap.get(camera);
        if (listenerList != null) {
            listenerList.remove(ZGroupListener.class, l);
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.  The listener list is processed in last to
     * first order.
     * @param id The event id (NODE_ADDED, NODE_REMOVED)
     * @param child The child being added or removed from this node
     * @see EventListenerList
     */
    protected static void fireGroupEvent(int id, ZGroup group, ZNode child) {

        // First we fire events on listeners that are listening to all events

        EventListenerList globalListenerList = (EventListenerList)listenerListMap.get(ALL_CAMERA_LISTENERS);

        if (globalListenerList != null) {
            Object[] listeners = globalListenerList.getListenerList();


            ZGroupEvent e = null;
            if (id == ZGroupEvent.NODE_ADDED) {
                e = ZGroupEvent.createNodeAddedEvent(group, child, false);
            } else if (id == ZGroupEvent.NODE_REMOVED) {
                e = ZGroupEvent.createNodeRemovedEvent(group, child, false);
            }

            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int j = listeners.length-2; j>=0; j-=2) {
                if (listeners[j]==e.getListenerType()) {
                    e.dispatchTo(listeners[j+1]);
                }
            }
        }

        // Then we fire events on listeners listening to specific cameras

        ZGroup ancestor = child.getParent();
        while (ancestor != null) {
            if (ancestor instanceof ZLayerGroup) {
                ZCamera[] cameras = ((ZLayerGroup)ancestor).getCamerasReference();

                for(int i=0; i<cameras.length; i++) {

                    EventListenerList listenerList = (EventListenerList)listenerListMap.get(cameras[i]);
                    if (listenerList == null) {
                        continue;
                    }

                    Object[] listeners = listenerList.getListenerList();


                    ZGroupEvent e = null;
                    if (id == ZGroupEvent.NODE_ADDED) {
                        e = ZGroupEvent.createNodeAddedEvent(group, child, false);
                    } else if (id == ZGroupEvent.NODE_REMOVED) {
                        e = ZGroupEvent.createNodeRemovedEvent(group, child, false);
                    }

                    // Process the listeners last to first, notifying
                    // those that are interested in this event
                    for (int j = listeners.length-2; j>=0; j-=2) {
                        if (listeners[j]==e.getListenerType()) {
                            e.dispatchTo(listeners[j+1]);
                        }
                    }
                }
            }

            ancestor = ancestor.getParent();
        }
    }

    //************************************************************************
    //
    // Static convenience methods to manage selection
    //
    //************************************************************************

    /**
     * Return a list of the selected nodes in the subtree rooted
     * at the specified node (including the root if it is selected).
     * Note that the nodes returned are the nodes at the bottom of
     * the "decorator chain".  See
     * {@link edu.umd.cs.jazz.util.ZSceneGraphEditor} for more information.
     *
     * You can also obtain all the selected nodes for a given camera,
     * (for example, {@link edu.umd.cs.jazz.util.ZCanvas#getCamera()})
     * using {@link #getSelectedNodes(ZCamera)}
     *
     * @param node The subtree to check for selection
     * @return The list of selected nodes.
     */
    public static ArrayList getSelectedNodes(ZNode node) {
        ArrayList selection = new ArrayList();
        node.findNodes(new SelectionFilter(), selection);

        return selection;
    }

    /**
     * Return a list of the selected nodes in the portion of the
     * scenegraph visible from the specified camera.
     * Note that the nodes returned are the nodes at the bottom of
     * the "decorator chain".  See @link{ZSceneGraphEditor} for
     * more information.
     *
     * You can also obtain all the selected nodes in the subtree of
     * rooted at given ZNode using, {@link #getSelectedNodes(ZNode)}
     *
     * @param camera The camera to look through for selected nodes.
     * @return The list of selected nodes.
     */
    public static ArrayList getSelectedNodes(ZCamera camera) {
        ArrayList selection = new ArrayList();
        ZLayerGroup[] layers = camera.getLayersReference();
        for (int i=0; i<camera.getNumLayers(); i++) {
            layers[i].findNodes(new SelectionFilter(), selection);
        }

        return selection;
    }

    /**
     * Select the specified node.
     * If the node is already selected, then do nothing.
     * This manages the selection as a decorator as described in @link{ZSceneGraphEditor}.
     * @param node the node to select
     * @return the ZSelectionGroup that represents the selection
     */
    public static ZSelectionGroup select(ZNode node) {
        if (node != null && node.isSelectable()) {
            ZSelectionGroup group = node.editor().getSelectionGroup();
            fireGroupEvent(ZGroupEvent.NODE_ADDED,group,node);
            return group;
        }
        else {
            return null;
        }
    }

    /**
     * Unselect the specified node.
     * If the node is not already selected, then do nothing.
     * This manages the selection as a decorator as described in @link{ZNode}.
     * @param node the node to unselect
     */
    public static void unselect(ZNode node) {
        ZSceneGraphEditor editor = node.editor();
        if (editor.hasSelectionGroup()) {
	    ZSelectionGroup selectGroup = editor.getSelectionGroup();
            editor.removeSelectionGroup();
            fireGroupEvent(ZGroupEvent.NODE_REMOVED,selectGroup,node);
        }
    }

    /**
     * Unselect all currently selected nodes in the subtree rooted
     * at the specified node (including the root if it is selected).
     * This manages the selection as a decorator as described in @link{ZNode}.
     * @param node The subtree to check for selection
     */
    public static void unselectAll(ZNode node) {
        ArrayList selection = getSelectedNodes(node);
        for (Iterator i=selection.iterator(); i.hasNext();) {
            node = (ZNode)i.next();
            unselect(node);
        }
    }

    /**
     * Unselect all currently selected nodes in the portion of the
     * scenegraph visible from the specified camera.
     * This manages the selection as a decorator as described in @link{ZNode}.
     * @param camera The camera to look through for selected nodes.
     */
    public static void unselectAll(ZCamera camera) {
        if (camera == null) {
            return;
        }

        ArrayList selection = getSelectedNodes(camera);
        for (Iterator i=selection.iterator(); i.hasNext();) {
            ZNode node = (ZNode)i.next();
            unselect(node);
        }
    }

    /**
     * Determine if the specified node is selected.
     * @return true if the node is selected.
     */
    public static boolean isSelected(ZNode node) {
        if (node == null)
            return false;

        if (node.editor().hasSelectionGroup()) {
            return true;
        } else {
            return false;
        }
    }

    //************************************************************************
    //
    //                 Inner Classes
    //
    //************************************************************************

    /**
     * Internal class used to find the children of selection nodes.
     */
    private static class SelectionFilter implements ZFindFilter {
        public boolean accept(ZNode node) {
            if ((node instanceof ZGroup) && (((ZGroup)node).hasOneChild())) {
                return false;
            } else {
                return ZSelectionManager.isSelected(node);
            }
        }

        public boolean childrenFindable(ZNode node) {
            return true;
        }
    }
}

/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.io.*;
import java.util.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZLayerGroup</b> is used exclusively to specify the portion of the scenegraph
 * that a camera can see. It has no other function.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see ZCamera
 * @author Ben Bederson
 */
public class ZLayerGroup extends ZGroup implements ZSerializable, Serializable {
    /**
     * All the cameras explicitly looking onto the scene graph at this node.  Other cameras
     * may actually see this node *indirectly* (some ancestor may have a camera looking at it.)
     */
    private ZList.ZCameraList cameras = new ZListImpl.ZCameraListImpl(1);

    //****************************************************************************
    //
    //                 Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new ZLayerGroup.  The node must be attached to a live scenegraph (a scenegraph that is
     * currently visible) order for it to be visible.
     */
    public ZLayerGroup () {
    }

    /**
     * Constructs a new layer group node with the specified node as a child of the
     * new group.
     * @param child Child of the new group node.
     */
    public ZLayerGroup(ZNode child) {
        this();         // XXX This should really be calling super, but this causes
                        // problems since that call will cause a repaint on this object
                        // before this objects instance variables have a chance to be
                        // initialized.
        insertAbove(child);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZLayerGroup newLayer = (ZLayerGroup)super.duplicateObject();

            // Shallow-Copy the cameras array.
            // Note that updateObjectReferences modifies this array. See below.
        if (!cameras.isNull()) {
            newLayer.cameras = (ZList.ZCameraList) cameras.clone();
        }
        return newLayer;
    }

    /**
     * Called to update internal object references after a clone operation
     * by {@link ZSceneGraphObject#clone}.
     *
     * @see ZSceneGraphObject#updateObjectReferences
     */
     protected void updateObjectReferences(ZObjectReferenceTable objRefTable) {
        super.updateObjectReferences(objRefTable);

        if (cameras.size() > 0) {
            int n = 0;

            ZCamera[] camerasRef = cameras.getCamerasReference();
            for (int i = 0; i < cameras.size(); i++) {
                ZCamera newCamera = (ZCamera) objRefTable.getNewObjectReference(camerasRef[i]);
                if (newCamera == null) {
                    // Cloned a ZLayerGroup, but did not clone a camera looking at the layer.
                    // Drop the camera from the list of cameras.
                } else {
                    // Cloned a ZLayerGroup and also the camera. Use the new camera.
                    camerasRef[n++] = newCamera;
                }
            }
            cameras.setSize(n);
        }
    }

    /**
     * Trims the capacity of the array that stores the cameras list points to
     * the actual number of points.  Normally, the cameras list arrays can be
     * slightly larger than the number of points in the cameras list.
     * An application can use this operation to minimize the storage of a
     * cameras list.
     */
    public void trimToSize() {
        super.trimToSize();
        cameras.trimToSize();
    }

    /**
     * Internal method to add the camera to the list of cameras that this node is visible within.
     * If camera is already listed, it will not be added again.
     *
     * @param camera The camera this node should be visible within
     */
    void addCamera(ZCamera camera) {
        if (cameras.contains(camera)) {
            return;
        }
        cameras.add(camera);
        repaint();
    }

    /**
     * Internal method to remove camera from the list of cameras that this node is visible within.
     *
     * @param camera The camera this node is no longer visible within
     */
    void removeCamera(ZCamera camera) {
        repaint();
        cameras.remove(camera);
    }

    /**
     * Return a copy of the array of cameras looking onto this layer.
     * This method always returns an array, even when there
     * are no cameras.
     * @return the cameras looking onto this layer
     */
    public ZCamera[] getCameras() {
        return (ZCamera[]) cameras.toArray();
    }

    /**
     * Internal method to return a reference to the actual cameras looking onto this layer.
     * It should not be modified by the caller.  Note that the actual number
     * of cameras could be less than the size of the array.  Determine
     * the actual number of cameras with @link{#getNumCameras}.
     * @return the cameras looking onto this layer.
     */
    protected ZCamera[] getCamerasReference() {
        return cameras.getCamerasReference();
    }

    /**
     * Return the number of cameras of this group node.
     * @return the number of cameras.
     */
    public int getNumCameras() {
        return cameras.size();
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
        }

                                // ZLayerGroup needs to override the base repaint method
                                // so it can pass on the repaint call to the cameras that
                                // look at this layer.
        if (!inTransaction) {
            repaint(getBounds());
        }
    }

    /**
     * Method to pass repaint methods up the tree,
     * and to any cameras looking here.  Repaints only the sub-portion of
     * this object specified by the given ZBounds.
     * Note that the input parameter may be modified as a result of this call.
     * @param repaintBounds The bounds to repaint
     */
    public void repaint(ZBounds repaintBounds) {
        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println("ZLayerGroup.repaint(ZBounds): this = " + this);
            System.out.println("ZLayerGroup.repaint(ZBounds): repaintBounds = " + repaintBounds);
        }

        if (inTransaction) {
            return;
        }
                                // The camera could modify the repaint bounds,
                                // so make a copy of them,
                                // and use the copies for each other camera.
        ZBounds origRepaintBounds = (ZBounds) repaintBounds.clone();

        ZCamera[] camerasRef = getCamerasReference();
        for (int i=0; i<cameras.size(); i++) {
            repaintBounds.setRect(origRepaintBounds);
            camerasRef[i].repaint(repaintBounds);
        }

        super.repaint(origRepaintBounds);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        trimToSize();   // Remove extra unused array elements
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (cameras == null) {
            cameras = new ZListImpl.ZCameraListImpl(1);
        }
    }
}

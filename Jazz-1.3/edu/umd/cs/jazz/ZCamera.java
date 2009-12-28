/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.event.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.component.*;

/**
 * <b>ZCamera</b> represents a viewport onto a list of nodes.
 * A camera can look at any number of ZLayerGroups, and can specify
 * where in space it looks as specified by
 * an arbitrary affine transformation.
 * <p>
 * When a ZCanvas is created, it automatically creates a top-level
 * camera attached to that canvas that is the same size as the canvas.
 * Thus, the whole scenegraph is rendered within that canvas.
 * <p>
 * It is also possible to create an "internal camera" that acts as
 * a portal, or internal window.  That is, it is an object within
 * the scene that looks onto the scene.  To do this, create a scenegraph
 * where the top-level camera sees both the regular layer, and a new layer
 * that contains the internal camera.  Then, make the new internal camera
 * look at the regular layer.  The following code demonstrates this:
 *
 * <pre>
 *      ZCamera portal = new ZCamera();
 *      portal.setBounds(100, 100, 200, 200);
 *      portal.setFillColor(Color.red);
 *      ZVisualLeaf leaf = new ZVisualLeaf(portal);
 *      leaf.setFindable(false);
 *      ZVisualGroup border = new ZVisualGroup(leaf);
 *      ZRectangle rect = new ZRectangle(100, 100, 200, 200);
 *      rect.setPenColor(Color.blue);
 *      rect.setFillColor(null);
 *      rect.setPenWidth(5.0);
 *      border.setFrontVisualComponent(rect);
 *      ZLayerGroup layer = new ZLayerGroup(border);
 *      canvas.getRoot().addChild(layer);
 *      canvas.getCamera().addLayer(layer);
 *      portal.addLayer(canvas.getLayer());
 * </pre>
 *
 * <h2>Context-sensitive rendering</h2>
 * It is possible to create visual components that appear differently in
 * different cameras, or are dependent on camera paramters (or other context).
 * An example of such context-sensitive rendering is a polyline with a
 * constant-thickness pen.  As the camera view zooms in and out, the position
 * of the points changes, but the pen thickness does not.
 * <P>
 * At first glance, it might seem that all that is necessary for these special
 * objects is to make the render method depend on camera parameters.  However,
 * it is a bit more complex because the effective bounds of the object changes
 * if the pen width of the object changes.  Thus, the bounds of the object
 * must be computed depending on the properties of the camera it is being
 * rendered within.
 * <P>
 * In order to compute bounds for different camera views, two things must be
 * done specially.  First, the object must be set to have 'volatile bounds'
 * (with the setVolatileBounds() method) which tells Jazz not to cache the
 * bounds of the object, but to recompute them every time they are needed.
 * The second thing is that the relevant camera must be consulted to get
 * the camera state (such as view magnification).  This can be done through
 * the 'current render context' which is maintained in the root of the
 * scenegraph.  This can be found with getRoot().getCurrentRenderContext().
 * Finally, certain objects may want to also redefine the pick method using
 * the render context if the picking is dependent on some context-sensitive
 * attribute that is used for rendering.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Benjamin B. Bederson
 * @author  Britt McAlister
 * @author  Maria E. Jump
 */

public class ZCamera extends ZVisualComponent implements ZFillColor, ZTransformable, Serializable {
    /**
     * The default fill color. For top level cameras the preferred way of setting
     * the ZCanvas background color is to set it directly with ZCanvas.setBackground().
     * This is why the default fill for cameras is set to null.
     */
    static public final Color  fillColor_DEFAULT = null;

                                // The transform that specifies where in space this camera looks.
    private AffineTransform viewTransform;

                                // Used to notify transform listeners. This way we can directly edit
                                // the current view transform.
    private AffineTransform originalViewTransform = null;

                                // The inverse of the view transform.
    private AffineTransform inverseViewTransform = null;

                                // A dirty bit specifying if the inverse view transform is up to date.
    private transient boolean inverseViewTransformDirty = true;

                                // The list of ZLayerGroups that this camera looks onto.
    private ZList.ZLayerGroupList layers = ZListImpl.NullList;



                                // The fill color used to paint this camera's background.
    private Color            fillColor = fillColor_DEFAULT;

                                // The optional surface this camera is associated with.  If this surface
                                // is non-null, then the camera will render directly onto this surface.
                                // Else, the camera will only be visible within other cameras that looks
                                // at it.
    private transient ZDrawingSurface surface = null;

                                // Debugging variable used for debugging region management
    private transient int debugRenderCount = 0;

                                // Render context for context-sensitive objects
    private transient ZRenderContext renderContext = null;

                                // Some things that gets reused within render()
                                // Define here for efficiency
    private  ZBounds  paintBounds;
    private transient ZBounds  tmpBounds = new ZBounds();

                                // Create the default render context factory
    static private ZRenderContextFactory renderContextFactory = new ZRenderContextFactory() {
        public ZRenderContext createRenderContext(ZCamera camera) {
            return new ZRenderContext(camera);
        }
        public ZRenderContext createRenderContext(Graphics2D aG2, ZBounds visibleBounds,
                                                  ZDrawingSurface aSurface, int qualityRequested) {
            return new ZRenderContext(aG2, visibleBounds, aSurface, qualityRequested);
        }
    };

    //****************************************************************************
    //
    //                Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new ZCamera.  Cameras are always associated with a scenegraph,
     * but are not attached to any output device (such as a window or a portal) to start.
     */
    public ZCamera() {
        this((ZLayerGroup)null, (ZDrawingSurface)null);
    }

    /**
     * Constructs a new ZCamera.  Cameras are always associated with a scenegraph,
     * but are not attached to any output device (such as a window or a portal) to start.
     * @param node The part of the scenegraph this camera sees.
     * @param surf The surface this top-level camera projects onto
     */
    public ZCamera(ZLayerGroup layer, ZDrawingSurface aSurface) {
        surface = aSurface;
        viewTransform = new AffineTransform();
        originalViewTransform = new AffineTransform();
        paintBounds = new ZBounds();
        renderContext = createRenderContext(this);
        addLayer(layer);
    }

    /**
     * Clones this object.
     *
     * <p>The cloned camera will continue to see the same
     * layers in the original scenegraph (the layers that the camera looks at
     * are not cloned).
     *
     * <p>The cloned camera will not be attached to any surface, and must
     * be made visible by attaching to a new surface (or by being visible
     * through another camera.)
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZCamera newCamera = (ZCamera)super.duplicateObject();

        newCamera.viewTransform = getViewTransform();
        newCamera.originalViewTransform = new AffineTransform();
        newCamera.paintBounds = new ZBounds();
        newCamera.tmpBounds = new ZBounds();
        newCamera.surface = null;
        newCamera.debugRenderCount = 0;
        newCamera.inverseViewTransform = null;
        newCamera.inverseViewTransformDirty = true;

        if (!layers.isNull()) {
            newCamera.layers = (ZList.ZLayerGroupList) layers.clone();
        }

        return newCamera;
    }

    /**
     * Called to update internal object references after a clone operation
     * by {@link ZSceneGraphObject#clone}.
     *
     * @see ZSceneGraphObject#updateObjectReferences
     */
    protected void updateObjectReferences(ZObjectReferenceTable objRefTable) {
        super.updateObjectReferences(objRefTable);
// XXX this is working differently then other update referecne things.
        ZLayerGroup[] layersRef = layers.getLayersReference();
        for (int i=0; i<layers.size(); i++) {
            ZLayerGroup newLayer = (ZLayerGroup) objRefTable.getNewObjectReference(layersRef[i]);
            if (newLayer == null) {
                // Cloned a camera, but did not clone a layer that it was looking at.
                // Continue to look at the old layer.
                layersRef[i].addCamera(this);
            } else {
                // Cloned a camera and the layer - use the new layer
                layersRef[i] = newLayer;
            }
        }
    }

    /**
     * Define how render contexts should be created.  This specifies a factory
     * that is used whenever a render context needs to be created.
     * @param factory The new factory to create render contexts with.
     * @see #createRenderContext
     */
    static public void setRenderContextFactory(ZRenderContextFactory factory) {
        renderContextFactory = factory;
    }

    /**
     * This returns a new instance of a ZRenderContext for this node.
     * @see #setRenderContextFactory
     */
    public ZRenderContext createRenderContext(ZCamera camera) {
        return renderContextFactory.createRenderContext(camera);
    }

    /**
     * This returns a new instance of a ZRenderContext for this node.
     * @see #setRenderContextFactory
     */
    public ZRenderContext createRenderContext(Graphics2D aG2, ZBounds visibleBounds,
                                              ZDrawingSurface aSurface, int qualityRequested) {
        return renderContextFactory.createRenderContext(aG2, visibleBounds, aSurface, qualityRequested);
    }

    /**
     * Trims the capacity of the array that stores the layers list points to
     * the actual number of points.  Normally, the layers list arrays can be
     * slightly larger than the number of points in the layers list.
     * An application can use this operation to minimize the storage of a
     * layers list.
     */
    public void trimToSize() {
        super.trimToSize();
        layers.trimToSize();
    }

    /**
     * Add a portion of the scenegraph that what this camera sees.
     * If the layer is already visible by this camera, then nothing happens.
     * @param layer The part of the scenegraph added to what this camera sees.
     */
    public void addLayer(ZLayerGroup layer) {
        if (layer == null) return;

        if (layers.isNull()) {
            layers = new ZListImpl.ZLayerGroupListImpl(1);
        }

        if (layers.contains(layer)) return;

        layers.add(layer);
        layer.addCamera(this);
    }

    /**
     * Removes a portion of the scenegrpah from what this camera sees
     * @param layer The part of the scenegraph removed from what this camera sees.
     */
    public void removeLayer(ZLayerGroup layer) {
        if (layers.remove(layer)) {
            layer.removeCamera(this);
        }
    }

    /**
     * Replaces the specified node out of the list of layers of this
     * camera, and replaces it with the specified node.
     * The replacement node will be added to layer list in the same
     * position as the original was.
     *
     * @param original is the old node that is being swapped out as a layer
     * @param replacement is the new node that is being swapped in as a layer
     */
    public void replaceLayer(ZLayerGroup original, ZLayerGroup replacement) {
        if (layers.replaceWith(original, replacement)) {
            original.removeCamera(this);
            replacement.addCamera(this);
        }
    }

    /**
     * Returns a copy of the list of layers that this camera looks onto.
     * @return Portion of scenegraph that is visible from this camera.
     */
    public ZLayerGroup[] getLayers() {
        return (ZLayerGroup[]) layers.toArray();
    }

    /**
     * Returns a reference to the actual layers array of this camera.
     * It should not be modified by the caller.  Note that the actual number
     * of layers could be less than the size of the array.  Determine
     * the actual number of layers with {@link #getNumLayers}.
     * <P>
     * <b>Warning:</b> This method returns a reference to an internal array. Any modification
     * of this array will result in undefined behavior.
     * <P>
     * @return the children of this node.
     */
    public ZLayerGroup[] getLayersReference() {
        return layers.getLayersReference();
    }

    /**
     * Returns the number of layers of this camera.
     * @return the number of layers.
     */
    public int getNumLayers() {
        return layers.size();
    }

    /**
     * Get the value of surface.
     * @return Value of surface.
     */
    public ZDrawingSurface getDrawingSurface() {
        return surface;
    }

    /**
     * Set the value of surface.
     * @param v  Value to assign to surface.
     */
    public void setDrawingSurface(ZDrawingSurface aSurface) {
        surface = aSurface;
        repaint();
    }

    /**
     * Get the value of fillColor.
     * @return Value of fillColor.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Set the value of fillColor.
     * For top level cameras the preferred way of setting the ZCanvas background 
     * color is to set it directly with ZCanvas.setBackground() instead of setting
     * the ZCameras fill color.
     * @param v  Value to assign to fillColor.
     */
    public void setFillColor(Color aColor) {
        fillColor = aColor;
        repaint();
    }

    /**
     * Returns the bounds that this Camera sees in global scene coordinates.
     * @return The bounds.
     */
    public ZBounds getViewBounds() {
        ZBounds result = getBounds();
        cameraToLocal(result, null);
        return result;
    }

    /**
     * Sets the bounds of this camera.
     * @param x,y,w,h The new bounds of this camera
     */
    public void setBounds(int x, int y, int w, int h) {
        repaint();
        super.setBounds(new ZBounds(x, y, w, h));
        reshape();
    }

    /**
     * Sets the bounds of this camera.
     * @param newBounds The new bounds of this camera
     */
    public void setBounds(Rectangle2D newBounds) {
		repaint();
        super.setBounds(new ZBounds(newBounds));
        reshape();
    }

    /**
     * Returns the current magnification of this camera.
     * @return The magnification factor.
     */
    public double getMagnification() {
        return (Math.max(viewTransform.getScaleX(), viewTransform.getScaleY()));
    }

    /*
     * Repaint causes the portions of the surfaces that this object
     * appears in to be marked as needing painting, and queues events to cause
     * those bounds to be painted. The painting does not actually
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
        super.repaint();        // First pass repaint request up the tree

                                // And then pass it on to the surface looking at this if there is one.
        if (!inTransaction && surface != null) {
            surface.repaint(getBoundsReference());
        }
    }

    /**
     * This is an internal form of repaint that is only intended to be
     * used by calls from within Jazz.  It passes repaint requests
     * up through the camera to other interested camera, and the surface (if there is one).
     * Note that the input parameter may be modified as a result of this call.
     *
     * @param repaintBounds The bounds that need to be repainted (in global coordinates)
     */
    public void repaint(ZBounds repaintBounds) {
        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println("ZCamera.repaint(bounds): this = " + this);
        }

        if (inTransaction || repaintBounds.isEmpty()) {
            return;
        }

                                // Set render context based on this camera so that
                                // context-sensitive objects can compute bounds dependent on it
        ZRoot root = getRoot();

        if (root != null) {
            root.setCurrentRenderContext(renderContext);
        }
                                // First, map global coords backwards through the camera's view
        tmpBounds.setRect(repaintBounds);
        ZTransformGroup.transform(tmpBounds, viewTransform);

                                // Now, intersect those bounds with the camera's bounds
                                // Note that Rectangle2D.intersect doesn't handle empty intersections properly
        ZBounds bounds = getBoundsReference();
        float x1 = Math.max((float)tmpBounds.getMinX(), (float)bounds.getMinX());
        float y1 = Math.max((float)tmpBounds.getMinY(), (float)bounds.getMinY());
        float x2 = Math.min((float)tmpBounds.getMaxX(), (float)bounds.getMaxX());
        float y2 = Math.min((float)tmpBounds.getMaxY(), (float)bounds.getMaxY());
        if ((x1 >= x2) || (y1 >= y2)) {
            return;
        }
        tmpBounds.setRect(x1, y1, x2 - x1, y2 - y1);
        repaintBounds.setRect(tmpBounds);
                                // Then, pass repaint up the tree
        //tmpBounds.setRect(repaintBounds);
        parents.collectiveRepaint(repaintBounds);

                                // Finally, if this camera is attached to a surface, repaint it.
        if (surface != null) {
            surface.repaint(tmpBounds);
        }

        if (root != null) {
            root.setCurrentRenderContext(null);
        }
    }

    /**
     * Renders the view this camera sees.
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
        Graphics2D      g2 = renderContext.getGraphics2D();
        ZBounds         visibleBounds = renderContext.getVisibleBounds();
        ZBounds         cameraBounds = getBoundsReference();
        boolean         paintingWholeCamera = false;
        AffineTransform saveTransform = g2.getTransform();
        Shape           saveClip = g2.getClip();

                                // Determine if we are painting the entire camera, or a sub-region
								// Blocked to show changes even for painting the entire camera
//        if (visibleBounds.contains(cameraBounds)) {
//            paintingWholeCamera = true;
//        }

        renderContext.pushCamera(this);

                                // Want to avoid clipping if possible since it slows things down
                                // So, don't clip to camera if this is a top-level camera,
        if (renderContext.getDrawingSurface().getCamera() != renderContext.getRenderingCamera()) {
            if (ZDebug.debug && ZDebug.debugRender) {
                System.out.println("ZCamera.render: clipping to camera bounds = " + cameraBounds);
            }
            g2.clip(cameraBounds);
        }

                                // Compute the visible bounds as transformed by the camera's view transform
        paintBounds.reset();
        paintBounds.add(visibleBounds);
        paintBounds.transform(getInverseViewTransformReference());

                                // Added to fix repaint bug in JDK1.4beta 3 when hardware
                                // acceleration is left on. JG
        paintBounds.inset(-1, -1);

        renderContext.pushVisibleBounds(paintBounds);

                                // Apply the camera view transform
        g2.transform(viewTransform);

                                // Clip to the transformed visible bounds
                                // (unless painting the whole camera in which case we don't need to
                                // since we've already clipped to the camera's bounds)
        if (!paintingWholeCamera) {
            if (ZDebug.debug && ZDebug.debugRender) {
                System.out.println("ZCamera.render: clipping to paint bounds = " + paintBounds);
            }
            g2.clip(paintBounds);
        }

                                // Draw fill (background) color if specified
        if (fillColor != null) {
            g2.setColor(fillColor);
            g2.fill(paintBounds);
        }

        if (ZDebug.debug && ZDebug.debugRender) {
            System.out.println("ZCamera.render");
            System.out.println("ZCamera.render: xformed visible bounds = " + paintBounds);
            System.out.println("ZCamera.render: camera bounds = " + cameraBounds);
            System.out.println("ZCamera.render: transform = " + g2.getTransform());
            System.out.println("ZCamera.render: clip = " + g2.getClip().getBounds2D());
        }

                                // Finally, paint all the scenegraph objects this camera looks onto
        AffineTransform origTransform = g2.getTransform();
        ZLayerGroup[] layersRef = getLayersReference();
        ZLayerGroup layer;
        for (int i = 0; i < getNumLayers(); i++) {
            layer = layersRef[i];
            g2.transform(layer.getParent().getLocalToGlobalTransform());
            layer.render(renderContext);
            g2.setTransform(origTransform);
        }
                                // Restore render context
        renderContext.popVisibleBounds();
        renderContext.popCamera();
        g2.setTransform(saveTransform);
        g2.setClip(saveClip);

                                // For debugging, render a gray transparent bounds over the actual portion of
                                // the screen that was rendered.  Cycle through several different gray colors
                                // so one render can be distinguished from another.
        if (ZDebug.debug && ZDebug.debugRegionMgmt) {
            if (!paintingWholeCamera) {
                paintBounds.reset();
                paintBounds.add(visibleBounds);
                int color = 100 + (debugRenderCount % 10) * 10;
                g2.setColor(new Color(color, color, color, 150));
                g2.fill(paintBounds);
                debugRenderCount++;
            }
        }
    }

    /**
     * Returns the root of the scene graph that this camera is looking at.
     * Actually returns the root of the first layer this camera sees.
     */
    public ZRoot getRoot() {
        return (layers.size() > 0) ? ((ZLayerGroup)layers.get(0)).getRoot() : null;
    }

    /**
     * Picks the first object under the
     * specified rectangle (if there is one) as searched in reverse
     * (front-to-back) order. The picked object is returned via the
     * ZSceneGraphPath. Only nodes with "pickable" visual components
     * are returned.
     * @param rect Coordinates of pick rectangle in camera coordinates.
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return true if pick succeeds.
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        Rectangle2D viewRect = (Rectangle2D)(rect.clone());
        Rectangle2D transformedRect = new Rectangle2D.Double();
        ZLayerGroup layer;
        AffineTransform localToGlobal, globalToLocal;

                                // First check if pick rectangle intersects this camera's bounds
        if (!getBoundsReference().intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight())) {
            return false;
        }

        //
        // Setup the path
        //
        path.push(this);        // Add this camera to the path
        path.pushCamera(this);
        path.pushTransformer(this);

                                // Concatenate the camera's transform with the one stored in the path
        AffineTransform origTm = path.getTransform();
        AffineTransform tm = new AffineTransform(origTm);
        tm.concatenate(viewTransform);
        path.setTransform(tm);
        path.setCameraTransform(origTm);

                                // Convert the rect from parent's coordinate system to local coordinates
        AffineTransform inverse = getInverseViewTransformReference();
        ZTransformGroup.transform(viewRect, inverse);

                                // Search nodes in reverse (front-to-back) order.
        ZLayerGroup[] layersRef = getLayersReference();
        for (int i=(layers.size() - 1); i>=0; i--) {
            layer = layersRef[i];
            localToGlobal = layer.getLocalToGlobalTransform();

            try {
                globalToLocal = localToGlobal.createInverse();
                                // Convert view rectangle to layer's coordinate system
                transformedRect.setRect(viewRect);
                ZTransformGroup.transform(transformedRect, globalToLocal);
                if (layer.pick(transformedRect, path)) {
                    return true;
                }
            } catch (NoninvertibleTransformException e) {
                throw new ZNoninvertibleTransformException(e);
            }
        }

        return true;
    }

    /**
     * Return the list of nodes that are accepted by the specified filter in the
     * portion of the scenegraph visible through this camera.
     * If a node is not "findable", then neither
     * that node, nor any of its descendants will be included.
     * The filter specifies whether or not a given node should be accepted by this
     * search, and whether the children of a node should be searched.
     * @param filter The filter that decides whether or not to include individual nodes in the find list
     * @return The nodes found
     * @see ZNode#isFindable()
     * @see ZFindFilter
     */
    public ArrayList findNodes(ZFindFilter filter) {
        ArrayList result = new ArrayList();
        findNodes(filter, result);
        return result;
    }

    /**
     * Internal method to assist findNodes.
     * @param filter The filter that decides whether or not to include individual nodes in the find list
     * @param nodes the accumulation list (results will be place here).
     * @return the number of nodes searched
     */
    int findNodes(ZFindFilter filter, ArrayList nodes) {
        int nodesSearched = 0;
                                // Search scenegraph nodes
        ZLayerGroup[] layersRef = getLayersReference();
        for (int i = 0; i < layers.size(); i++) {
            nodesSearched += layersRef[i].findNodes(filter, nodes);
        }

        return nodesSearched;
    }

    /**
     * Generate a string that represents this object for debugging.
     * @return the string that represents this object for debugging
     * @see ZDebug#dump
     */
    public String dump() {
        String str = super.dump();

        str += "\n View Bounds:   " + getViewBounds()
            + "\n View Transform: " + getViewTransform();
        for (int i=0; i<layers.size(); i++) {
            str += "\n Layer: " + layers.get(i);
        }

        return str;
    }

    //****************************************************************************
    //
    // Event methods
    //
    //***************************************************************************

    /**
     * Adds the specified camera listener to receive camera events from this camera
     *
     * @param l the camera listener
     */
    public void addCameraListener(ZCameraListener l) {
        getListenerList().add(ZCameraListener.class, l);
    }

    /**
     * Removes the specified camera listener so that it no longer
     * receives camera events from this camera.
     *
     * @param l the camera listener
     */
    public void removeCameraListener(ZCameraListener l) {
        removeEventListener(ZCameraListener.class, l);
    }

    //****************************************************************************
    //
    // View Transform methods
    //
    //***************************************************************************

    /**
     * Returns the view transform matrix.  This is necessary to implement
     * the ZTransformable interface.
     * @param matrix The matrix which needs to be set
     */
    public void getMatrix(double[] matrix) {
        viewTransform.getMatrix(matrix);
    }

    /**
     * Sets the view transform via the matrix values.  This is necessary
     * to implement the ZTransformable interface
     * @param m00 The 00 element of the matrix
     * @param m10 The 10 element of the matrix
     * @param m01 The 01 element of the matrix
     * @param m11 The 11 element of the matrix
     * @param m02 The 02 element of the matrix
     * @param m12 The 12 element of the matrix
     */
    public void setTransform(double m00, double m10,
                             double m01, double m11,
                             double m02, double m12) {
        setViewTransform(m00, m10, m01, m11, m02, m12);
    }

    /**
     * Returns a copy of the view transform that specifes where in
     * space this camera looks.
     * @return The current camera view transform.
     */
    public AffineTransform getViewTransform() {
        return (AffineTransform) viewTransform.clone();
    }

    /**
     * Internal method to compute the inverse camera transform based on the camera transform.
     * This gets called from within ZCamera
     * whenever the inverse camera transform cache has been invalidated,
     * and it is needed.
     */
    protected void computeInverseViewTransform() {
        try {
            inverseViewTransform = viewTransform.createInverse();
            inverseViewTransformDirty = false;
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }
    }

    /**
     * Returns a copy of the inverse view transform associated with this camera.
     * @return The current inverse camera transform.
     */
    public AffineTransform getInverseViewTransform() {
        return (AffineTransform) getInverseViewTransformReference().clone();
    }

    /**
     * Returns a reference to the inverse view transform associated with this camera.
     * @return The current inverse camera transform.
     */
    public AffineTransform getInverseViewTransformReference() {
        if (inverseViewTransformDirty) {
            computeInverseViewTransform();
        }
        return inverseViewTransform;
    }

    /**
     * Sets the view transform associated with this camera.
     * This controls where in space this camera looks.
     * @param transform the new transform.
     */
    public void setViewTransform(AffineTransform transform) {
        originalViewTransform.setTransform(viewTransform);
        viewTransform.setTransform(transform);
        inverseViewTransformDirty = true;
        if (hasListenerOfType(ZCameraListener.class)) {
            fireEvent(ZCameraEvent.createViewChangedEvent(this, originalViewTransform));
        }
        repaint();
    }

    /**
     * Sets the view transform associated with this camera.
     * This controls where in space this camera looks.
     * @param m00,&nbsp;m01,&nbsp;m02,&nbsp;m10,&nbsp;m11,&nbsp;m12 the
     * 6 values that compose the 3x3 transformation matrix
     */
    public void setViewTransform(double m00, double m10,
                                 double m01, double m11,
                                 double m02, double m12) {
        originalViewTransform.setTransform(viewTransform);

        viewTransform.setTransform(m00, m10, m01, m11, m02, m12);
        inverseViewTransformDirty = true;

        if (hasListenerOfType(ZCameraListener.class)) {
            fireEvent(ZCameraEvent.createViewChangedEvent(this, originalViewTransform));
        }
        repaint();
    }

    /**
     * Transform a point in the camera's coordinate system through the camera down the tree
     * to the specified node's local coordinate system.
     * In the typical case where this is a top-level camera, and the point
     * is in screen coordinates, this will transform the
     * point to the local coordinate system of the specified node. The input point is modified
     * by this method.  It also returns the change in scale from the camera coordinate system
     * to the node coordinate system.
     * <P>
     * If the node is specified as null, then the point is transformed through the
     * camera, but no further - thus transforming the point from window to global coordinates.
     * <P>
     * If the specified node is not on the portion of the scenegraph that is visible
     * through the camera, then a ZNodeNotFoundException is thrown.
     * @param pt The point to be transformed
     * @param node The node to transform to
     * @return dz The change in scale from the camera coordinate system to the node coordinate system.
     * @exception ZNodeNotFoundException if the node is not in the subtree of the scenegraph
     *            under one of the camera's layers.
     * @see #localToCamera(Point2D, ZNode)
     */
    public double cameraToLocal(Point2D pt, ZNode node) {
                                // First transform point through camera's view
        double dz;
        AffineTransform inverse;
        inverse = getInverseViewTransformReference();
        inverse.transform(pt, pt);
        dz = Math.max(inverse.getScaleX(), inverse.getScaleY());
        if (node != null) {
            ZLayerGroup layer = getAncestorLayerFor(node);

            if (layer == null) {
                                // Oops - this node can't be seen through the camera
                throw new ZNodeNotFoundException("Node " + node + " is not accessible from camera " + this);
            } else {
                dz *= node.globalToLocal(pt);
            }
        }

        return dz;
    }

    /**
     * Transform a dimension in the camera's coordinate system through the camera down the tree
     * to the specified node's local coordinate system.
     * In the typical case where this is a top-level camera, and the dimension
     * is in screen coordinates, this will transform the
     * dimension to the local coordinate system of the specified node.
     * The input dimension is modified by this method.
     * <P>
     * If the node is specified as null, then the dimension is transformed through the
     * camera, but no further - thus transforming the point from window to global coordinates.
     * <P>
     * NOTE: Dimension2D's are abstract. When creating a new Dimension2D for use with Jazz
     * we recoment that you use edu.umd.cs.util.ZDimension instead of java.awt.Dimension.
     * ZDimension uses doubles internally, while java.awt.Dimension uses integers.
     * <p>
     * If the specified node is not on the portion of the scenegraph that is visible
     * through the camera, then a ZNodeNotFoundException is thrown.
     * @param aDimension The dimension to be transformed
     * @param node The node to transform to
     * @return dz The change in scale from the camera coordinate system to the node coordinate system.
     * @exception ZNodeNotFoundException if the node is not in the subtree of the scenegraph
     *            under one of the camera's layers.
     * @see #localToCamera(Dimension2D, ZNode)
     */
    public double cameraToLocal(Dimension2D aDimension, ZNode node) {
                                // First transform dimension through camera's view
        double dz = 0;

        try {
            dz = ZUtil.inverseTransformDimension(aDimension, viewTransform);
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }

        if (node != null) {
                                // Then, find the layer that is the ancestor of the specified node
            ZLayerGroup layer = getAncestorLayerFor(node);

            if (layer == null) {
                                // Oops - this node can't be seen through the camera
                throw new ZNodeNotFoundException("Node " + node + " is not accessible from camera " + this);
            } else {
                dz *= node.globalToLocal(aDimension);
            }
        }

        return dz;
    }

    /**
     * Transform a rectangle in the camera's coordinate system through the camera down the tree
     * to the specified node's local coordinate system.
     * In the typical case where this is a top-level camera, and the rectangle
     * is in screen coordinates, this will transform the
     * rectangle to the local coordinate system of the specified node. The input rectangle is modified
     * by this method.  It also returns the change in scale from the camera coordinate system
     * to the node coordinate system.
     * <P>
     * If the node is specified as null, then the rectangle is transformed through the
     * camera, but no further - thus transforming the rectangle from window to global coordinates.
     * <P>
     * If the specified node is not on the portion of the scenegraph that is visible
     * through the camera, then a ZNodeNotFoundException is thrown.
     * @param rect The rectangle to be transformed
     * @param node The node to transform to
     * @return dz The change in scale from the camera coordinate system to the node coordinate system.
     * @exception ZNodeNotFoundException if the node is not in the subtree of the scenegraph
     *            under one of the camera's layers.
     * @see #localToCamera(Rectangle2D, ZNode)
     */
    public double cameraToLocal(Rectangle2D rect, ZNode node) {
                                // First transform rectangle through camera's view
        double dz;
        AffineTransform inverse;
        inverse = getInverseViewTransformReference();
        ZTransformGroup.transform(rect, inverse);
        dz = Math.max(inverse.getScaleX(), inverse.getScaleY());
        if (node != null) {
                                // Then, find the layer that is the ancestor of the specified node
            ZLayerGroup layer = getAncestorLayerFor(node);

            if (layer == null) {
                                // Oops - this node can't be seen through the camera
                throw new ZNodeNotFoundException("Node " + node + " is not accessible from camera " + this);
            } else {
                dz *= node.globalToLocal(rect);
            }
        }

        return dz;
    }

    /**
     * Transform a point in a node's local coordinate system up the scenegraph backwards through the camera
     * to the camera's coordinate system.
     * In the typical case where this is a top-level camera,
     * and the point represents a coordinate in the local coordinate system
     * of a node, this will transform the point to screen coordinates.
     * The input point is modified by this method.
     * It also returns the change in scale from the node coordinate system
     * to the camera coordinate system.
     * <P>
     * If the node is specified as null, then the point is transformed from global
     * coordinates through the camera, thus transforming the point from global to window coordinates.
     * <P>
     * If the specified node is not on the portion of the scenegraph that is visible
     * through the camera, then a ZNodeNotFoundException is thrown.
     * @param pt The point to be transformed
     * @param node The node that represents the local coordinates to transform from
     * @return dz The change in scale from the node coordinate system to the camera coordinate system.
     * @exception ZNodeNotFoundException if the node is not in the subtree of the scenegraph
     *            under one of the camera's layers.
     * @see #cameraToLocal(Point2D, ZNode)
     */
    public double localToCamera(Point2D pt, ZNode node) {
        double dz = 1.0;
        if (node != null) {
            ZLayerGroup layer = getAncestorLayerFor(node);

            if (layer == null) {
                                // Oops - this node can't be seen through the camera
                throw new ZNodeNotFoundException("Node " + node + " is not accessible from camera " + this);
            } else {
                dz *= node.localToGlobal(pt);
            }
        }

        viewTransform.transform(pt, pt);
        dz *= Math.max(viewTransform.getScaleX(), viewTransform.getScaleY());

        return dz;
    }

    /**
     * Transform a dimension in a node's local coordinate system up the scenegraph backwards through the camera
     * to the camera's coordinate system.
     * In the typical case where this is a top-level camera,
     * and the dimension represents a dimension in the local coordinate system
     * of a node, this will transform the dimension to screen coordinates.
     * The input dimension is modified by this method.
     * <P>
     * If the node is specified as null, then the dimension is transformed from global
     * coordinates through the camera, thus transforming the size from global to window coordinates.
     * <P>
     * NOTE: Dimension2D's are abstract. When creating a new Dimension2D for use with Jazz
     * we recoment that you use edu.umd.cs.util.ZDimension instead of java.awt.Dimension.
     * ZDimension uses doubles internally, while java.awt.Dimension uses integers.
     * <p>
     * If the specified node is not on the portion of the scenegraph that is visible
     * through the camera, then a ZNodeNotFoundException is thrown.
     * @param aDimension The dimension to be transformed
     * @param node The node that represents the local coordinates to transform from
     * @return dz The change in scale from the node coordinate system to the camera coordinate system.
     * @exception ZNodeNotFoundException if the node is not in the subtree of the scenegraph
     *            under one of the camera's layers.
     * @see #cameraToLocal(Dimension2D, ZNode)
     */
    public double localToCamera(Dimension2D aDimension, ZNode node) {
        double dz = 1.0;

        if (node != null) {
            ZLayerGroup layer = getAncestorLayerFor(node);

            if (layer == null) {
                                // Oops - this node can't be seen through the camera
                throw new ZNodeNotFoundException("Node " + node + " is not accessible from camera " + this);
            } else {
                dz *= node.localToGlobal(aDimension);
            }
        }

        dz *= ZUtil.transformDimension(aDimension, viewTransform);

        return dz;
    }

    /**
     * Transform a rectangle in a node's local coordinate system up the scenegraph backwards through the camera
     * to the camera's coordinate system.
     * In the typical case where this is a top-level camera,
     * and the rectangle is in the local coordinate system
     * of a node, this will transform the rectangle to screen coordinates.
     * The input rectangle is modified by this method.
     * It also returns the change in scale from the node coordinate system
     * to the camera coordinate system.
     * <P>
     * If the node is specified as null, then the rectangle is transformed from global
     * coordinates through the camera, thus transforming the rectangle from global to window coordinates.
     * <P>
     * If the specified node is not on the portion of the scenegraph that is visible
     * through the camera, then a ZNodeNotFoundException is thrown.
     * @param rect The rectangle to be transformed
     * @param node The node that represents the local coordinates to transform from
     * @return dz The change in scale from the node coordinate system to the camera coordinate system.
     * @exception ZNodeNotFoundException if the node is not in the subtree of the scenegraph
     *            under one of the camera's layers.
     * @see #cameraToLocal(Rectangle2D, ZNode)
     */
    public double localToCamera(Rectangle2D rect, ZNode node) {
        double dz = 1.0;
        if (node != null) {
            ZLayerGroup layer = getAncestorLayerFor(node);

            if (layer == null) {
                                // Oops - this node can't be seen through the camera
                throw new ZNodeNotFoundException("Node " + node + " is not accessible from camera " + this);
            } else {
                dz *= node.localToGlobal(rect);
            }
        }

        ZTransformGroup.transform(rect, viewTransform);
        dz *= Math.max(viewTransform.getScaleX(), viewTransform.getScaleY());

        return dz;
    }

    /**
     * Animates the camera view so that the specified bounds (in global coordinates)
     * is centered within the view of the camera.
     * @param <code>refBounds</code> The bounds (in global coordinates) to be centered.
     * @param <code>millis</code> The time in milliseconds to perform the animation
     * @param <code>surface</code> The surface to be updated during the animation
     */
    public void center(Rectangle2D refBounds, int millis, ZDrawingSurface aSurface) {
        AffineTransform at = new AffineTransform();
        ZBounds bounds = getBoundsReference();
                                // First compute transform that will result in bounds being centered
        double dx = (bounds.getWidth() / refBounds.getWidth());
        double dy = (bounds.getHeight() / refBounds.getHeight());
        double scale = (dx < dy) ? dx : dy;
        double ctrX = (0.5 * bounds.getWidth());
        double ctrY = (0.5 * bounds.getHeight());
        double refBoundsX = (refBounds.getX() + (0.5 * refBounds.getWidth()));
        double refBoundsY = (refBounds.getY() + (0.5 * refBounds.getHeight()));

        at.translate(ctrX + (- refBoundsX * scale), ctrY + (- refBoundsY * scale));
        at.scale(scale, scale);

                                // Then, change camera to new transform
        animate(at, millis, aSurface);
    }

    /**
     * Animates the camera view so that a given global bounds appear
     * within a specified screen bounds given in camera coordinates. The
     * transformation is made in such a way that the width/height ratio
     * of the source bounds is kept.
     *
     * @param millis Number of milliseconds over which to perform the animation.
     * @param surface The surface to be updated during animation.
     * @param srcBounds A bounds in global coordinates.
     * @param destBounds Bounds of the region in camera coordinates that <code>bounds</code> should apperar in.
     *
     */
    public void center(Rectangle2D srcBounds, Rectangle2D destBounds, int millis, ZDrawingSurface surface) {
                                // Modify the reference bounds so it has the same aspect ratio as
                                // the destination bounds.
        double dx = srcBounds.getWidth() / destBounds.getWidth();
        double dy = srcBounds.getHeight() / destBounds.getHeight();
        double bx, by, bw, bh;

        if (dx < dy) {
            bw = (destBounds.getWidth() / destBounds.getHeight()) * srcBounds.getHeight();
            bh = srcBounds.getHeight();
            bx = srcBounds.getX() + (0.5 * srcBounds.getWidth()) - (0.5 * bw);
            by = srcBounds.getY();
        } else {
            bw = srcBounds.getWidth();
            bh = (destBounds.getHeight() / destBounds.getWidth()) * srcBounds.getWidth();
            bx = srcBounds.getX();
            by = srcBounds.getY() + (0.5 * srcBounds.getHeight()) - (0.5 * bh);
        }

        Rectangle2D.Double rect = new Rectangle2D.Double(bx, by, bw, bh);
        centerWithAspectChange(rect, destBounds, millis, surface);
    }

    /**
     * Animates the given camera view so that a given global bounds appear
     * within a specified screen bounds given in camera coordinates. The
     * source bounds is made to fit exactly inside the destination bounds,
     * which may result in a change in width/height ratio.
     *
     * @param millis Number of milliseconds over which to perform the animation.
     * @param surface The surface to be updated during animation.
     * @param srcBounds A bounds in global coordinates.
     * @param destBounds Bounds of the region in camera coordinates that <code>bounds</code> should apperar in.
     *
     */
    public void centerWithAspectChange(Rectangle2D srcBounds, Rectangle2D destBounds, int millis, ZDrawingSurface surface) {
        ZBounds  cameraBounds = getBounds();

                                // Calculate the width and height (in global coords) of the
                                // resulting camera view bounds. The ratio between the
                                // width of the resulting bounds and the reference bounds should
                                // be equal to the ratio between the camera bounds and the destination
                                // bounds. Solving for the width of the resulting bounds:

        double   gcw = (cameraBounds.getWidth() * srcBounds.getWidth()) / destBounds.getWidth();
        double   gch = (cameraBounds.getHeight() * srcBounds.getHeight()) / destBounds.getHeight();

                                // Now calculate the x and y coordinates from the following
                                // identities:
                                //
                                // (destBoundsX - cameraBoundsX) / cameraBoundsWidth = (refBoundsX - resultX) / resultWidth
                                // (destBoundsY - cameraBoundsY) / cameraBoundsHeight = (refBoundsY - resultY) / resultHeight

        double   gcx = srcBounds.getX() - (gcw * ((destBounds.getX() - cameraBounds.getX()) / cameraBounds.getWidth()));
        double   gcy = srcBounds.getY() - (gch * ((destBounds.getY() - cameraBounds.getY()) / cameraBounds.getHeight()));

        Rectangle2D rect = new Rectangle2D.Double(gcx, gcy, gcw, gch);
        centerWithAspectChange(rect, millis, surface);
    }

    /**
     * Animates the camera view so that the camera view
     * will match the given bounds exactly. This may result in a
     * change in width/height ratio.
     *
     * @param millis Number of milliseconds over which to perform the animation.
     * @param surface The surface to be updated during animation.
     * @param refBounds A bounds in global coordinates.
     *
     */
    public void centerWithAspectChange(Rectangle2D refBounds, int millis, ZDrawingSurface aSurface) {
        AffineTransform at = new AffineTransform();
        ZBounds bounds = getBounds();
                                // First compute transform that will result in bounds being centered
        double dx = (bounds.getWidth() / refBounds.getWidth());
        double dy = (bounds.getHeight() / refBounds.getHeight());
        double scaleX = dx;
        double scaleY = dy;
        double ctrX = (0.5 * bounds.getWidth());
        double ctrY = (0.5 * bounds.getHeight());
        double refBoundsX = (refBounds.getX() + (0.5 * refBounds.getWidth()));
        double refBoundsY = (refBounds.getY() + (0.5 * refBounds.getHeight()));

        at.translate(ctrX + (- refBoundsX * scaleX), ctrY + (- refBoundsY * scaleY));
        at.scale(scaleX, scaleY);

                                // Then, change camera to new transform
        animate(at, millis, aSurface);
    }

    /**
     * Returns the current translation of this object
     * @return the translation
     */
    public Point2D getTranslation() {
        return new Point2D.Double(viewTransform.getTranslateX(), viewTransform.getTranslateY());
    }

    /**
     * Returns the current X translation of this object
     * @return the X translation
     */
    public double getTranslateX() {
        return viewTransform.getTranslateX();
    }
    /**
     * Sets the current X translation of this object
     */
    public void setTranslateX(double x) {
        setTranslation(x, getTranslateY());
    }
    /**
     * Returns the current Y translation of this object
     * @return the Y translation
     */
    public double getTranslateY() {
        return viewTransform.getTranslateY();
    }
    /**
     * Sets the current Y translation of this object
     */
    public void setTranslateY(double y) {
        setTranslation(getTranslateX(), y);
    }

    /**
     * Translate the object by the specified deltaX and deltaY
     * @param dx X-coord of translation
     * @param dy Y-coord of translation
     */
    public void translate(double dx, double dy) {
        originalViewTransform.setTransform(viewTransform);
        viewTransform.translate(dx, dy);
        updateViewTransform();
    }

    /**
     * Animate the object from its current position by the specified deltaX and deltaY
     * @param dx X-coord of translation
     * @param dy Y-coord of translation
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void translate(double dx, double dy, int millis, ZDrawingSurface surface) {
        AffineTransform at = new AffineTransform(viewTransform);
        at.translate(dx, dy);
        ZTransformGroup.animate(this, at, millis, surface);
    }

    /**
     * Translate the object to the specified position
     * @param x X-coord of translation
     * @param y Y-coord of translation
     */
    public void setTranslation(double x, double y) {
        double[] mat = new double[6];
        viewTransform.getMatrix(mat);
        mat[4] = x;
        mat[5] = y;
        setViewTransform(mat[0], mat[1], mat[2], mat[3], mat[4], mat[5]);
    }

    /**
     * Animate the object from its current position to the position specified
     * by x, y
     * @param x X-coord of translation
     * @param y Y-coord of translation
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void setTranslation(double x, double y, int millis, ZDrawingSurface surface) {
        AffineTransform at = new AffineTransform(viewTransform);
        double[] mat = new double[6];

        at.translate(x, y);
        at.getMatrix(mat);
        mat[4] = x;
        mat[5] = y;
        at.setTransform(mat[0], mat[1], mat[2], mat[3], mat[4], mat[5]);
        ZTransformGroup.animate(this, at, millis, surface);
    }

    /**
     * Returns the current scale of this transform.
     * Note that this is implemented by applying the transform to a diagonal
     * line and returning the length of the resulting line.  If the transform
     * is sheared, or has a non-uniform scaling in X and Y, the results of
     * this method will be ill-defined.
     * @return the scale
     */
    public double getScale() {
        return getMagnification();
    }

    /**
     * Scale the object from its current scale to the scale specified
     * by muliplying the current scale and dz.
     * @param dz scale factor
     */
    public void scale(double dz) {
        originalViewTransform.setTransform(viewTransform);
        viewTransform.scale(dz, dz);
        updateViewTransform();
    }

    /**
     * Scale the object around the specified point (x, y)
     * from its current scale to the scale specified
     * by muliplying the current scale and dz.
     * @param dz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     */
    public void scale(double dz, double x, double y) {
        originalViewTransform.setTransform(viewTransform);
        viewTransform.translate(x, y);
        viewTransform.scale(dz, dz);
        viewTransform.translate(-x, -y);
        updateViewTransform();
    }

    /**
     * Animate the object from its current scale to the scale specified
     * by muliplying the current scale and deltaZ
     * @param dz scale factor
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void scale(double dz, int millis, ZDrawingSurface surface) {
        AffineTransform at = new AffineTransform(viewTransform);
        at.scale(dz, dz);
        ZTransformGroup.animate(this, at, millis, surface);
    }

    /**
     * Animate the object around the specified point (x, y)
     * from its current scale to the scale specified
     * by muliplying the current scale and dz
     * @param dz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void scale(double dz, double x, double y, int millis, ZDrawingSurface surface) {
        AffineTransform at = new AffineTransform(viewTransform);
        at.translate(x, y);
        at.scale(dz, dz);
        at.translate(-x, -y);
        ZTransformGroup.animate(this, at, millis, surface);
    }

    /**
     * Sets the scale of the view transform
     * @param the new scale
     */
    public void setScale(double finalz) {
        double dz = finalz / getScale();
        scale(dz);
    }

    /**
     * Set the scale of the object to the specified target scale,
     * scaling the object around the specified point (x, y).
     * @param finalz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     */
    public void setScale(double finalz, double x, double y) {
        double dz = finalz / getScale();
        scale(dz, x, y);
    }

    /**
     * Animate the object from its current scale to the specified target scale.
     * @param finalz scale factor
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void setScale(double finalz, int millis, ZDrawingSurface surface) {
        double dz = finalz / getScale();
        scale(dz, millis, surface);
    }

    /**
     * Animate the object around the specified point (x, y)
     * to the specified target scale.
     * @param finalz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void setScale(double finalz, double x, double y, int millis, ZDrawingSurface surface) {
        double dz = finalz / getScale();
        scale(dz, x, y, millis, surface);
    }

    /**
     * Set the transform of this camera to the specified transform,
     * and animate the change from its current transformation over the specified
     * number of milliseconds using a slow-in slow-out animation.
     * The surface specifies which surface should be updated during the animation.
     * <p>
     * If millis is 0, then the transform is updated once, and the scene
     * is not repainted immediately, but rather a repaint request is queued,
     * and will be processed by an event handler.
     * <p>
     * @param at Final transformation
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void animate(AffineTransform at, int millis, ZDrawingSurface surface) {
        ZTransformGroup.animate(this, at, millis, surface);
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

        if (!viewTransform.isIdentity()) {
            out.writeState("java.awt.geom.AffineTransform", "viewTransform", viewTransform);
        }
        if (fillColor != fillColor_DEFAULT) {
            out.writeState("java.awt.Color", "fillColor", fillColor);
        }
        layers.writeObject("layers", out);

        // camera bounds
        Vector cameraBoundsNumbers = new Vector();
        cameraBoundsNumbers.addElement(new Double(getBounds().getX()));
        cameraBoundsNumbers.addElement(new Double(getBounds().getY()));
        cameraBoundsNumbers.addElement(new Double(getBounds().getWidth()));
        cameraBoundsNumbers.addElement(new Double(getBounds().getHeight()));

        out.writeState("Vector", "cameraBounds", cameraBoundsNumbers);

    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        super.writeObjectRecurse(out);

                                // Add layers
        ZLayerGroup[] layersRef = getLayersReference();
        for (int i=0; i<layers.size(); i++) {
            out.addObject(layersRef[i]);
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

        if (fieldName.compareTo("layers") == 0) {
            ZLayerGroup layer;
            for (Iterator i=((Vector)fieldValue).iterator(); i.hasNext();) {
                layer = (ZLayerGroup)i.next();
                addLayer(layer);
            }
        } else if (fieldName.compareTo("viewTransform") == 0) {
            setViewTransform((AffineTransform)fieldValue);
        } else if (fieldName.compareTo("fillColor") == 0) {
            setFillColor((Color)fieldValue);
        } else if (fieldName.compareTo("cameraBounds") == 0) {
            Vector cameraBoundsNumbers = (Vector)fieldValue;
            Rectangle2D.Double cameraBounds = new
                Rectangle2D.Double((double)((Double)cameraBoundsNumbers.elementAt(0)).doubleValue(),
                                   (double)((Double)cameraBoundsNumbers.elementAt(1)).doubleValue(),
                                   (double)((Double)cameraBoundsNumbers.elementAt(2)).doubleValue(),
                                   (double)((Double)cameraBoundsNumbers.elementAt(3)).doubleValue());
            setBounds(cameraBounds);
        }
    }

    /**
     * Return the camera layer associated with this camera that is the
     * ancestor of the supplied node.
     *
     * @param node The node whos layer ancestor is needed.
     * @return The ancenstor ZLayerGroup or null if no ancestor was found.
     */
    protected ZLayerGroup getAncestorLayerFor(ZNode node) {
        ZLayerGroup[] layersRef = getLayersReference();
        for (int i=0; i<layers.size(); i++) {
            if (node == layersRef[i] || node.isDescendentOf(layersRef[i])) {
                return layersRef[i];
            }
        }
        return null;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        tmpBounds = new ZBounds();
        inverseViewTransformDirty = true;
        debugRenderCount = 0;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        trimToSize();   // Remove extra unused array elements
        out.defaultWriteObject();
    }

    /**
     * Fires events to camera listeners then updates origialViewTransform.
     * This method needs to be called when the viewTransform is updated
     * directly, ie not through setViewTransform().
     *
     */
    private void updateViewTransform() {
        inverseViewTransformDirty = true;
        if (hasListenerOfType(ZCameraListener.class)) {
            fireEvent(ZCameraEvent.createViewChangedEvent(this, originalViewTransform));
        }

        originalViewTransform.setTransform(viewTransform);
        repaint();
    }
}
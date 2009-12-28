/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.component.*;

/**
 * <b>ZCanvas</b> is a simple Swing component that can be used to render
 * onto for Jazz.  It extends JComponent, and overrides the appropriate
 * methods so that whenever Java requests that this widget gets redrawn,
 * the requests are forwarded on to Jazz to render appropriately.  It also
 * defines a very simple Jazz scenegraph consisting of a root, a camera,
 * and one node.  It supports capturing the current camera view
 * onto an Image (i.e., a screengrab).  It also supports the use of
 * Swing components within Jazz by forwarding mouse, repaint, and revalidate
 * events.
 * <P>
 * To make a ZCanvas scrollable, you can add it to a ZScrollPane in the same
 * way a normal JComponent is added to a JScrollPane.  Note that manipulating
 * the scrollbars changes the camera view, not the canvas view.  By default,
 * the scrollbars adjust to accomodate the objects in the camera view,
 * incorporating the magnification. There can be weird effects when the camera
 * is panned manually, as the scrollBars are adjusted to include all the
 * objects in the scenegraph, plus the current camera view. Panning the camera
 * in space that includes no objects will cause the scrollBars to change size,
 * as they adapt to the changing space that is the union of the objects and
 * the camera.
 * <P>
 * To get different scrolling behavior from the default, you can set the
 * ZScrollDirector on the ZViewport.  See the ScrollingExample in the jazz
 * examples package for an example of a modified ZScrollDirector.
 * <P>
 * ZCanvas defines basic event handlers for panning and zooming with the keyboard and mouse
 * which can be enabled and disabled with {@link #setNavEventHandlersActive}.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Benjamin B. Bederson
 * @author Lance E. Good
 */
public class ZCanvas extends JComponent implements Serializable {
    /**
     * Used as a hashtable key to indicate that a JComponent is
     * functioning as a place holder for Swing components displayed in
     * a ZCanvas
     */
    static final String SWING_WRAPPER_KEY = "Swing Wrapper";

    /**
     * A fake mouse event used by setToolTipText to force immediate updates
     * of the ToolTipText
     */
    final MouseEvent FAKE_MOUSE_EVENT = new MouseEvent(this,MouseEvent.MOUSE_ENTERED,0,0,0,0,0,false);

                                // The root of the scenegraph
    private ZRoot           root;
                                // The camera in the scenegraph
    private ZCamera         camera;
                                // The camera node in the scenegraph
    private ZNode           cameraNode;
                                // The surface associated with the component
    private ZDrawingSurface surface;
                                // The single node that camera looks onto.  It is considered to
                                // be the "layer" because many applications will put content
                                // under this node which can then be hidden or revealed like a layer.
    private ZLayerGroup     layer;

    private Cursor cursor = getCursor();
                                // A visible though not rendered JComponent to which Swing components are
                                // added to function properly in the Jazz Scenegraph
    private JComponent swingWrapper;

    /**
     * Mouse Listener for ZNodes that have visual components.
     */
    protected ZNodeEventHandler nodeListener;

    /**
     * The event handler that supports events for Swing Visual Components.
     */
    protected ZSwingEventHandler          swingEventHandler;

    /**
     * The event handler that supports panning.
     */
    protected ZPanEventHandler            panEventHandler;

    /**
     * The event handler that supports zooming.
     */
    protected ZoomEventHandler            zoomEventHandler;

    /**
     * True if any ZMouseEvents are being sent to nodes on the canvas.
     */
    protected boolean enableNodeEvents = false;

    /**
     * True if ZMouseEvents of type ZMouseEvent.MOUSE_MOVED,
     * ZMouseEvent.MOUSE_ENTERED, and ZMouseEvent.MOUSE_EXITED are being
     * excluded and not sent to nodes on the canvas. If a Jazz application
     * does not need these event types then enabling this flag can increase
     * performance since Jazz will not need to call ZDrawingSurface.pick()
     * on every mouse movement.
     */
    protected boolean excludeMouseMoveEvents = false;

    /**
     * The default constructor for a ZCanvas.  This creates a simple
     * scenegraph with a root, camera, surface, and layer.  These 4 scenegraph
     * elements are accessible to the application through get methods.
     * Also adds the necessary structure to facilitate the focus, repaint,
     * and event handling for Swing components within Jazz
     * @see #getRoot()
     * @see #getDrawingSurface()
     * @see #getCamera()
     * @see #getLayer()
     */
    public ZCanvas() {
        root = new ZRoot();
        camera = new ZCamera();
        cameraNode = new ZVisualLeaf(camera);
        surface = new ZDrawingSurface(camera, cameraNode, this);
        layer = new ZLayerGroup();
        root.addChild(layer);
        root.addChild(cameraNode);
        camera.addLayer(layer);
        init();
    }

    /**
     * A constructor for a ZCanvas that uses an existing scenegraph.
     * This creates a new camera and surface.  The camera is inserted into
     * the scenegraph under the root, and the specified layer is added to
     * the camera's paint start point list.  The scenegraph
     * elements are accessible to the application through get methods.
     * Also adds the necessary structure to facilitate the focus, repaint,
     * and event handling for Swing components within Jazz
     * @param aRoot The existing root of the scenegraph this component is attached to
     * @param layer The existing layer node of the scenegraph that this component's camera looks onto
     * @see #getRoot()
     * @see #getDrawingSurface()
     * @see #getCamera()
     * @see #getLayer()
     */
    public ZCanvas(ZRoot aRoot, ZLayerGroup layer) {
        root = aRoot;
        camera = new ZCamera();
        cameraNode = new ZVisualLeaf(camera);
        surface = new ZDrawingSurface(camera, cameraNode, this);
        this.layer = layer;
        root.addChild(cameraNode);
        camera.addLayer(layer);

        init();
    }

    /**
     * Internal method to support initialization of a ZCanvas.
     */
    protected void init() {
		setBackground(Color.WHITE);
    	
                                // Add support for Swing widgets
        swingWrapper = new JComponent() {
            public boolean isValidateRoot() {
                return true;
            }
        };
        swingWrapper.putClientProperty(SWING_WRAPPER_KEY, new Object());
        swingWrapper.setSize(0, 0);
        swingWrapper.setVisible(true);
        add(swingWrapper);

        setEnableNodeEvents(true);

        if (!(RepaintManager.currentManager(this) instanceof ZBasicRepaintManager)) {
            RepaintManager.setCurrentManager(new ZBasicRepaintManager());
        }

        setNavEventHandlersActive(true);    // Create some basic event handlers for panning and zooming
        setSwingEventHandlersActive(true);  // Create the event handler for swing events
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   // Create the Swing event handler

    }

    /**
     * This renders the Jazz scene attached to this component by passing on the Swing paint request
     * to the underlying Jazz surface.
     * @param g The graphics to be painted onto
     */
    public void paintComponent(Graphics g) {    	
        try {
			ZDebug.startProcessingOutput();
			Graphics2D g2 = (Graphics2D) g.create();		
		    g2.setColor(getBackground());
		    g2.fillRect(0, 0, getWidth(), getHeight());			
            surface.paint(g2);
			ZDebug.endProcessingOutput();				
        } catch (ZNoninvertibleTransformException e) {
            e.printStackTrace();
        }
    }

    /**
     * This captures changes in the component's bounds so the underlying Jazz camera can
     * be updated to mirror bounds change.
     * @param x The X-coord of the top-left corner of the component
     * @param y The Y-coord of the top-left corner of the component
     * @param width The width of the component
     * @param height The Height of the component
     */
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        Rectangle bounds = getBounds();
        camera.setBounds(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
        getCameraNode().getBoundsReference();
    }

    /**
     * Sets the surface.
     * @param surface the surface
     */
    public void setDrawingSurface(ZDrawingSurface aSurface) {
        surface = aSurface;
        surface.setCamera(camera,cameraNode);
        surface.setComponent(this);
        camera.repaint();
    }

    /**
     * Return the surface.
     * @return the surface
     */
    public ZDrawingSurface getDrawingSurface() {
        return surface;
    }

    /**
     * Sets the camera.
     * @param camera the camera
     */
    public void setCamera(ZCamera aCamera) {
        camera = aCamera;
        Rectangle bounds = getBounds();
        camera.setBounds(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
        camera.addLayer(layer);
        surface.setCamera(camera,cameraNode);

        boolean zoomActive = zoomEventHandler.isActive();
        boolean panActive = panEventHandler.isActive();
        boolean swingActive = swingEventHandler.isActive();

	zoomEventHandler.setActive(false);
	panEventHandler.setActive(false);
	swingEventHandler.setActive(false);

        zoomEventHandler = null;
        panEventHandler = null;
        swingEventHandler = null;

        if (zoomActive) {
            zoomEventHandler = new ZoomEventHandler(cameraNode);
            zoomEventHandler.setActive(true);
        }
        if (panActive) {
            panEventHandler = new ZPanEventHandler(cameraNode);
            panEventHandler.setActive(true);
        }
        if (swingActive) {
            swingEventHandler = new ZSwingEventHandler(this,cameraNode);
            swingEventHandler.setActive(true);
        }

        camera.repaint();
    }

    /**
     * Sets the camera.
     * @param aCamera the camera
     * @param aCameraNode the camera node
     */
    public void setCamera(ZCamera aCamera, ZNode aCameraNode) {
        camera = aCamera;
        cameraNode = aCameraNode;
        Rectangle bounds = getBounds();
        camera.setBounds(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
        camera.addLayer(layer);
        surface.setCamera(camera,cameraNode);
        root.addChild(cameraNode);

        boolean zoomActive = zoomEventHandler.isActive();
        boolean panActive = panEventHandler.isActive();
        boolean swingActive = swingEventHandler.isActive();

        zoomEventHandler = null;
        panEventHandler = null;
        swingEventHandler = null;

        if (zoomActive) {
            zoomEventHandler = new ZoomEventHandler(cameraNode);
            zoomEventHandler.setActive(true);
        }
        if (panActive) {
            panEventHandler = new ZPanEventHandler(cameraNode);
            panEventHandler.setActive(true);
        }
        if (swingActive) {
            swingEventHandler = new ZSwingEventHandler(this,cameraNode);
            swingEventHandler.setActive(true);
        }

        camera.repaint();
    }

    /**
     * Return the camera associated with the primary surface.
     * @return the camera
     */
    public ZCamera getCamera() {
        return camera;
    }

    /**
     * Return the camera's node associated with the primary surface.
     * @return the camera's node
     */
    public ZNode getCameraNode() {
        return cameraNode;
    }

    /**
     * Sets the root of the scenegraph.
     * @param root the root
     */
    public void setRoot(ZRoot aRoot) {
        root = aRoot;

        root.addChild(layer);
        root.addChild(cameraNode);
    }

    /**
     * Return the root of the scenegraph.
     * @return the root
     */
    public ZRoot getRoot() {
        return root;
    }

    /**
     * Sets the layer of the scenegraph.
     * @param layer the layer
     */
    public void setLayer(ZLayerGroup aLayer) {
        if (layer != aLayer) {
            if (root.indexOf(aLayer) == -1) {
                root.addChild(aLayer);
            }
            camera.replaceLayer(layer,aLayer);

            layer = aLayer;
            camera.repaint();
        }
    }

    /**
     * Return the "layer".  That is, the single node that
     * the camera looks onto to start.
     * @return the node
     */
    public ZLayerGroup getLayer() {
        return layer;
    }

    /**
     * Identifies whether or not this component can receive the focus.
     * @return true if this ZCanvas can receive the focus, otherwise false.
     */
    public boolean isFocusTraversable() {
        return true;
    }

    /**
     * @see ZScrollPane
     * @see ZViewport
     * @see ZScrollDirector
     * @see ZDefaultScrollDirector
     * @see javax.swing.JScrollPane#setHorizontalScrollBarPolicy
     * @see javax.swing.JScrollPane#setVerticalScrollBarPolicy
     * @deprecated As of Jazz version 1.2
     */
    public void setAutoScrollingEnabled(boolean autoScroll) {}

    /**
     * Generate a copy of the view in the current camera scaled so that the aspect ratio
     * of the screen is maintained, and the larger dimension is scaled to
     * match the specified parameter.
     * @return An image of the camera
     */
    public Image getScreenImage(int maxDim) {
        int w, h;

        if (getSize().getWidth() > getSize().getHeight()) {
            w = maxDim;
            h = (int)(maxDim * getSize().getHeight() / getSize().getWidth());
        } else {
            h = maxDim;
            w = (int)(maxDim * getSize().getWidth() / getSize().getHeight());
        }
        return getScreenImage(w, h);
    }

    /**
     * Generate a copy of the current camera scaled to the specified dimensions.
     * @param w  Width of the image
     * @param h  Height of the image
     * @return An image of the camera
     */
    public Image getScreenImage(int w, int h) {
                                // We create an image of the right size and get its graphics
        Image screenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)screenImage.getGraphics();
                                // Then, we compute the transform that will map the component into the image
        double dsx = (w / getSize().getWidth());
        double dsy = (h / getSize().getHeight());
        AffineTransform at = AffineTransform.getScaleInstance(dsx, dsy);
        g2.setTransform(at);
                                // Finally, we paint onto the image
        surface.paint(g2);
                                // And we're done
        return screenImage;
    }

    /**
     * Converts the specified point from screen coordinates of this canvas to
     * global coordinates. This method modifies the pt parameter.
     *
     * @param pt the point in screen coords that will be converted to global coords.
     */
    public void screenToGlobal(Point2D pt) {
        try {
            getCamera().getViewTransform().inverseTransform(pt, pt);
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }
    }

    /**
     * Converts the specified dimension from screen coordinates of this canvas to
     * global coordinates. This method modifies the dimension parameter.
     *
     * @param dimension the point in screen coords that will be converted to global coords.
     */
    public void screenToGlobal(Dimension2D dimension) {
        try {
            ZUtil.inverseTransformDimension(dimension, getCamera().getViewTransform());
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }
    }

    /**
     * Converts the specified rectangle from screen coordinates of this canvas to
     * global coordinates. This method modifies the rectangle parameter.
     *
     * @param rectangle the rectangle in screen coords that will be converted to global coords.
     */
    public void screenToGlobal(Rectangle2D rectangle) {
        try {
            ZUtil.inverseTransformRectangle(rectangle, getCamera().getViewTransform());
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }
    }

    /**
     * Return the pan event handler.
     * @return the pan event handler.
     */
    public ZEventHandler getPanEventHandler() {
        return panEventHandler;
    }

    /**
     * Return the zoom event handler.
     * @return the zoom event handler.
     */
    public ZEventHandler getZoomEventHandler() {
        return zoomEventHandler;
    }

    /**
     * Control whether swing event handlers are active or not for this ZCanvas.
     * This controls basic mouse events on Swing components within a ZCanvas.
     * @param active - a boolean: true to enable the swing event handlers, false to disable them.
     */
    public void setSwingEventHandlersActive(boolean active) {
        if (active) {
            if (swingEventHandler == null) {
                swingEventHandler = new ZSwingEventHandler(this, cameraNode);
            }
            swingEventHandler.setActive(true);
        }
        else {
            if (swingEventHandler != null) {
                swingEventHandler.setActive(false);
            }
        }
    }

    /**
     * Control whether event handlers are active or not for this ZCanvas.
     * This controls basic panning and zooming event handlers for the mouse,
     * so that the left button pans, and the right button zooms.
     */
    public void setNavEventHandlersActive(boolean active) {
        if (active) {
            boolean swingActive = false;

            // Activate event handlers
            if (panEventHandler == null) {
                panEventHandler = new ZPanEventHandler(cameraNode);
            }
            if (zoomEventHandler == null) {
                zoomEventHandler = new ZoomEventHandler(cameraNode);
            }

            if (swingEventHandler != null && swingEventHandler.isActive()) {
                swingEventHandler.setActive(false);
                swingActive = true;
            }

            panEventHandler.setActive(true);
           zoomEventHandler.setActive(true);

            if (swingEventHandler != null && swingActive) {
                swingEventHandler.setActive(true);
            }

        } else {
                                // Deactivate event handlers
            if (panEventHandler != null) {
                panEventHandler.setActive(false);
            }
            if (zoomEventHandler != null) {
                zoomEventHandler.setActive(false);
            }
        }
    }

    /**
     * Returns the component to which Swing components are added to function
     * properly in Jazz.  This method is public only to allow access to ZSwing.
     * It should not be used otherwise.
     * @return The component to which Swing components are added to function in Jazz
     */
    public JComponent getSwingWrapper() {
        return swingWrapper;
    }

    /**
     * Returns the event handler that supports events for Swing Visual Components.
     * @return the event handler that supports events for Swing Visual Components.
     */
    public ZSwingEventHandler getSwingEventHandler() {
        return swingEventHandler;
    }

    /**
     * Specify if Jazz node event handlers should be invoked.
     * NOTE:  This should only be called if Jazz Events are not needed
     * @param enable True if node event handlers should be invoked.
     */
    public void setEnableNodeEvents(boolean enable) {
        if (enable && !enableNodeEvents) {
            if (nodeListener == null) {
                nodeListener = new ZNodeEventHandler();
            }
            addMouseListener(nodeListener);
            addMouseMotionListener(nodeListener);
            enableNodeEvents = true;
        } else if (!enable && enableNodeEvents) {
            removeMouseListener(nodeListener);
            removeMouseMotionListener(nodeListener);
            enableNodeEvents = false;
            nodeListener = null;
        }
    }

    /**
     * Determine if Jazz node event handlers should be invoked.
     * @return True if Node event handlers should be invoked.
     */
    public final boolean getEnableNodeEvents() {
        return enableNodeEvents;
    }

    /**
     * Returns true if ZMouseEvents of type ZMouseEvent.MOUSE_MOVED,
     * ZMouseEvent.MOUSE_ENTERED, and ZMouseEvent.MOUSE_EXITED are being
     * excluded and not sent to nodes on the canvas. If a Jazz application
     * does not need these event types then enabling this flag can increase
     * performance since Jazz will not need to call ZDrawingSurface.pick()
     * on every mouse movement.
     */
    public boolean getExcludeMouseMoveEvents() {
        return excludeMouseMoveEvents;
    }

    /**
     * If the parameter aBoolean is true then ZMouseEvents of type
     * ZMouseEvent.MOUSE_MOVED, ZMouseEvent.MOUSE_ENTERED, and
     * ZMouseEvent.MOUSE_EXITED are being excluded and not sent to nodes
     * on the canvas. If a Jazz application does not need these event types
     * then enabling this flag can increase performance since Jazz will not
     * need to call ZDrawingSurface.pick() on every mouse movement.
     */
    public void setExcludeMouseMoveEvents(boolean aBoolean) {
        excludeMouseMoveEvents = aBoolean;
    }

    /**
     * Sets the cursor for this ZCanvas
     * @param c The new cursor
     */
    public void setCursor(Cursor c) {
        setCursor(c,true);
    }

    /**
     * Sets the cursor for this ZCanvas.  If realSet is
     * true then the cursor that displays when the mouse is over the
     * ZCanvas is set as well as the currently displayed cursor.
     * If realSet is false then only the currently displayed cursor is changed
     * to indicate that the mouse is over a deeper component within the
     * ZCanvas.
     * @param c The new cursor
     * @param realSet true - The ZCanvas cursor and current cursor set
     *                false - Only the current cursor set
     */
    public void setCursor(Cursor c, boolean realSet) {
        if (realSet) {
            cursor = c;
        }
        super.setCursor(c);
    }

    /**
     * Sets the current cursor to the ZCanvas's cursor.
     */
    public void resetCursor() {
        setCursor(cursor, false);
    }

    /**
     * Sets the ToolTip Text for this ZCanvas
     * LEG: HACK - this includes a workaround to update the ToolTip as
     *             as soon as it changes by forwarding fake mouse events
     *             to the tooltip manager
     * @param s The new tooltip text
     */
    public void setToolTipText(String s) {
        super.setToolTipText(s);

        if (s != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ToolTipManager.sharedInstance().mouseEntered(FAKE_MOUSE_EVENT);
                }
            });
        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ToolTipManager.sharedInstance().mouseEntered(FAKE_MOUSE_EVENT);
                    ToolTipManager.sharedInstance().mouseExited(FAKE_MOUSE_EVENT);
                }
            });
        }
    }

    /**
     * Internal class to distribute mouse events to our listeners first, and
     * then on to the other listeners.  This allows Jazz to support consuming
     * the events, and only passing the events on if they are not consumed.
     * Mouse events get dispatched with the following priority (assuming
     * swing and node events are enabled.)
     * <ol>
     * <li> If there is a Swing widget, then that gets the mouse event.
     * <li> If the event is not consumed, and there is a node event listener, then that gets the event.
     * <li> If the event is not consumed, then any other component event listeners are processed.
     * </ol>
     */
    class ZNodeEventHandler implements MouseListener, MouseMotionListener {

        protected ZSceneGraphPath targetPath = null;
        protected ZSceneGraphPath mouseOverPath = null;
        protected ZSceneGraphPath previousPath = null;
        protected ZSceneGraphObject targetObject = null;
        protected ZSceneGraphObject currentObject = null;
        protected ZSceneGraphObject previousObject = null;

        ZNodeEventHandler() {
        }

        protected void updateMouseOverPath(MouseEvent e) {
            // Needed for objects with volatile bounds over multiple cameras.
            ZCamera camera = surface.getCamera();
            ZRenderContext rc = camera.createRenderContext(camera);
            camera.getRoot().setCurrentRenderContext(rc);

            try {
                mouseOverPath = getDrawingSurface().pick(e.getX(), e.getY());
            } catch (ZNoninvertibleTransformException ex) {
                ex.printStackTrace();
            }
            currentObject = mouseOverPath.getObject();

            camera.getRoot().setCurrentRenderContext(null);
        }

        protected void updateTargetPath() {
            targetPath = mouseOverPath;
            if (targetPath == null) {
                targetObject = null;
            } else {
                targetObject = targetPath.getObject();
            }
        }

        protected void checkForMouseEnteredOrExited(MouseEvent e) {
            if (getExcludeMouseMoveEvents())
                return;

            if (currentObject != previousObject) {
                if (previousObject != null) {
                    try {
                        dispatchEventToPath(MouseEvent.MOUSE_EXITED, e, previousPath);
                    } catch (ZNodeNotFoundException exc) {
                                // The current node was probably deleted in an event handler,
                                // so we won't give it any more events
                    }
                }
                if (currentObject != null) {
                    dispatchEventToPath(MouseEvent.MOUSE_ENTERED, e, mouseOverPath);
                }
            }
            previousPath = mouseOverPath;
            previousObject = currentObject;
        }

        public void mouseMoved(MouseEvent e) {
			ZDebug.startProcessingInput();        	
            if (getExcludeMouseMoveEvents())
                return;

            updateMouseOverPath(e);
            updateTargetPath();
            checkForMouseEnteredOrExited(e);
            dispatchEventToPath(MouseEvent.MOUSE_MOVED, e, targetPath);            
			ZDebug.endProcessingInput();
        }

        public void mousePressed(MouseEvent e) {
			ZDebug.startProcessingInput();        	
            updateMouseOverPath(e);
            updateTargetPath();
            dispatchEventToPath(MouseEvent.MOUSE_PRESSED, e, targetPath);
			ZDebug.endProcessingInput();
        }

        public void mouseDragged(MouseEvent e) {
			ZDebug.startProcessingInput();        	
                                // We need to set the transform using the objects on the grab path.
                                // We can't use the current transform from the current path because the path may be different.
                                // So instead, we recompute the transform using the objects along the path.
            updateMouseOverPath(e);
            targetPath.updateTransform();
            dispatchEventToPath(MouseEvent.MOUSE_DRAGGED, e, targetPath);
			ZDebug.endProcessingInput();
        }

        public void mouseReleased(MouseEvent e) {
			ZDebug.startProcessingInput();        	
            updateMouseOverPath(e);
            targetPath.updateTransform();
            dispatchEventToPath(MouseEvent.MOUSE_RELEASED, e, targetPath);
			ZDebug.endProcessingInput();
        }

        public void mouseClicked(MouseEvent e) {
			ZDebug.startProcessingInput();        	
            updateMouseOverPath(e);
            updateTargetPath();
            dispatchEventToPath(MouseEvent.MOUSE_CLICKED, e, targetPath);
			ZDebug.endProcessingInput();
        }

        public void mouseExited(MouseEvent e) {
			ZDebug.startProcessingInput();        	
            //updateMouseOverPath(e);
            mouseOverPath = null;
            currentObject = null;
            checkForMouseEnteredOrExited(e);
            //mouseOverPath = null;
            //dispatchEventToPath(MouseEvent.MOUSE_EXITED, e, targetPath);
            //targetPath = null;
            //previousObject = null;
            //targetObject = null;
			ZDebug.endProcessingInput();
        }

        public void mouseEntered(MouseEvent e) {
			ZDebug.startProcessingInput();        	
            updateMouseOverPath(e);
            checkForMouseEnteredOrExited(e);
			ZDebug.endProcessingInput();
        }

        protected void dispatchEventToPath(int id, MouseEvent e, ZSceneGraphPath aPath) {
            ZMouseEvent aNewEvent = ZMouseEvent.createMouseEvent(id,
                                                                 e,
                                                                 aPath,
                                                                 mouseOverPath);
            try {
                aPath.processMouseEvent(aNewEvent);
            } catch (ZNoninvertibleTransformException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This is an internal class used by Jazz to support Swing components
     * in Jazz.  This should not be instantiated, though all the public
     * methods of javax.swing.RepaintManager may still be called and
     * perform in the expected manner.
     *
     * ZBasicRepaint Manager is an extension of RepaintManager that traps
     * those repaints called by the Swing components that have been added
     * to the ZCanvas and passes these repaints to the
     * SwingVisualComponent rather than up the component hierarchy as
     * usually happens.
     *
     * Also traps revalidate calls made by the Swing components added
     * to the ZCanvas to reshape the applicable Visual Component.
     *
     * Also keeps a list of ZSwings that are painting.  This
     * disables repaint until the component has finished painting.  This is
     * to address a problem introduced by Swing's CellRendererPane which is
     * itself a work-around.  The problem is that JTable's, JTree's, and
     * JList's cell renderers need to be validated before repaint.  Since
     * we have to repaint the entire Swing component hierarchy (in the case
     * of a Swing component group used as a Jazz visual component).  This
     * causes an infinite loop.  So we introduce the restriction that no
     * repaints can be triggered by a call to paint.
     */
    public class ZBasicRepaintManager extends RepaintManager {
        // The components that are currently painting
        // This needs to be a vector for thread safety
        Vector paintingComponents = new Vector();

        /**
         * Locks repaint for a particular (Swing) component displayed by
         * ZCanvas
         * @param c The component for which the repaint is to be locked
         */
        public void lockRepaint(JComponent c) {
            paintingComponents.addElement(c);
        }

        /**
         * Unlocks repaint for a particular (Swing) component displayed by
         * ZCanvas
         * @param c The component for which the repaint is to be unlocked
         */
        public void unlockRepaint(JComponent c) {
            synchronized (paintingComponents) {
                paintingComponents.removeElementAt(paintingComponents.lastIndexOf(c));
            }
        }

        /**
         * Returns true if repaint is currently locked for a component and
         * false otherwise
         * @param c The component for which the repaint status is desired
         * @return Whether the component is currently painting
         */
        public boolean isPainting(JComponent c) {
            return paintingComponents.contains(c);
        }

        /**
         * This is the method "repaint" now calls in the Swing components.
         * Overridden to capture repaint calls from those Swing components
         * which are being used as Jazz visual components and to call the Jazz
         * repaint mechanism rather than the traditional Component hierarchy
         * repaint mechanism.  Otherwise, behaves like the superclass.
         * @param c Component to be repainted
         * @param x X coordinate of the dirty region in the component
         * @param y Y coordinate of the dirty region in the component
         * @param w Width of the dirty region in the component
         * @param h Height of the dirty region in the component
         */
        public synchronized void addDirtyRegion(JComponent c, int x, int y, final int w, final int h) {
            boolean captureRepaint = false;
            JComponent capturedComponent = null;
            int captureX = x, captureY = y;

            // We have to check to see if the ZCanvas
            // (ie. the SwingWrapper) is in the components ancestry.  If so,
            // we will want to capture that repaint.  However, we also will
            // need to translate the repaint request since the component may
            // be offset inside another component.
            for(Component comp = c; comp != null && comp.isLightweight() && !captureRepaint; comp = comp.getParent()) {

                if (comp.getParent() != null &&
                    comp.getParent() instanceof JComponent &&
                    ((JComponent)comp.getParent()).getClientProperty(SWING_WRAPPER_KEY) != null) {
                    if (comp instanceof JComponent) {
                        captureRepaint = true;
                        capturedComponent = (JComponent)comp;
                    }
                }
                else {
                    // Adds to the offset since the component is nested
                    captureX += comp.getLocation().getX();
                    captureY += comp.getLocation().getY();
                }

            }

            // Now we check to see if we should capture the repaint and act
            // accordingly
            if (captureRepaint) {
                if (!isPainting(capturedComponent)) {

                    final ZSwing vis = (ZSwing)capturedComponent.getClientProperty(ZSwing.VISUAL_COMPONENT_KEY);

                    if (vis != null) {
			final int repaintX = captureX;
			final int repaintY = captureY;			
			Runnable repainter = new Runnable() {
				public void run() {
				    vis.repaint(new ZBounds((double)repaintX,(double)repaintY,(double)w,(double)h));
				}
			    };
			SwingUtilities.invokeLater(repainter);
                    }

                }
            }
            else {
                super.addDirtyRegion(c,x,y,w,h);
            }
        }

        /**
         * This is the method "revalidate" calls in the Swing components.
         * Overridden to capture revalidate calls from those Swing components
         * being used as Jazz visual components and to update Jazz's visual
         * component wrapper bounds (these are stored separately from the
         * Swing component). Otherwise, behaves like the superclass.
         * @param invalidComponent The Swing component that needs validation
         */
        public synchronized void addInvalidComponent(JComponent invalidComponent) {
            final JComponent capturedComponent = invalidComponent;

            if (capturedComponent.getParent() != null &&
                capturedComponent.getParent() instanceof JComponent &&
                ((JComponent)capturedComponent.getParent()).getClientProperty(SWING_WRAPPER_KEY) != null) {

                Runnable validater = new Runnable() {
                    public void run() {
                        capturedComponent.validate();
                        ZSwing swing = (ZSwing)capturedComponent.getClientProperty(ZSwing.VISUAL_COMPONENT_KEY);
                        swing.reshape();
                    }
                };
                SwingUtilities.invokeLater(validater);
            }
            else {
                super.addInvalidComponent(invalidComponent);
            }
        }
    }
}

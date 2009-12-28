/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.awt.image.ImageObserver;
import java.util.*;
import java.io.*;
import javax.swing.JComponent;
import javax.swing.RepaintManager;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZDrawingSurface</b>  represents the thing the camera renders onto. Typically, a
 * drawing surface will be associated with a ZCanvas window. However,
 * a drawing surface can also represent a printer, and thus rendering to a window
 * and printer is implemented in the same way. The drawing surface is
 * associated with a window or printer by specifying which Graphics2D to render onto.
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
 * @see     ZNode
 */

public class ZDrawingSurface implements Printable, Serializable {
                                // Default values
    static public final int RENDER_QUALITY_LOW = 1;
    static public final int RENDER_QUALITY_MEDIUM = 2;
    static public final int RENDER_QUALITY_MEDIUM_JAGGED_TEXT = 4;
    static public final int RENDER_QUALITY_HIGH = 3;

    /**
     * Number of pixels a point can miss an object and still pick it.
     */
    static public final int DEFAULT_HALO = 2;

                                // The camera this surface is associated with.
    private transient ZCamera camera = null;

                                // The camera this surface is associated with.
    private transient ZNode   cameraNode = null;

                                // The optional component this surface is associated with.
    private JComponent            component = null;

                                // True when user interacting with surface
    private boolean               interacting;

                                // True when items within this view are being animated
    private boolean               animating;

                                // True if fractional font metrics should be used
    private boolean               useFractionalMetrics = true;

                                // The render quality to use when painting this surface.
    private int                   currentRenderQuality = RENDER_QUALITY_LOW;

                                // The render quality when not interacting or animating
    private int                   normalRenderQuality = RENDER_QUALITY_LOW;

                                // The render quality when interacting with surface.
    private int                   interactingRenderQuality = RENDER_QUALITY_LOW;

                                // Rectangle used for calculating repaint region.
                                // Defined once per surface, and reused for efficiency.
    private Rectangle             tmpRepaintRect = null;

                                // Only allow paintingImmediately to be called once at a time
    private boolean               paintingImmediately = false;

    //****************************************************************************
    //
    //                 Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new Surface. Surfaces are associated with a top-level camera and serve
     * as the surface onto which the camera projects.
     */
    public ZDrawingSurface() {
        this(null, null);
    }

    /**
     * Constructs a new Surface, containing the given camera and associated camera node.
     * Surfaces are always associated with a scenegraph,
     * but are not attached to any output device (such as a window or a portal) to start.
     * If this surface is attached to a window, then its component must be set
     * with {@link #setComponent}
     * @param node The part of the scenegraph this camera sees.
     * @param cameraNode The node the camera is attached to
     */
    public ZDrawingSurface(ZCamera camera, ZNode cameraNode) {
        tmpRepaintRect = new Rectangle();
        setCamera(camera, cameraNode);
    }

    /**
     * Constructs a new Surface, containing the given camera and associated camera node,
     * along with a JComponent this surface is connected to.
     * Surfaces are always associated with a scenegraph,
     * but are not attached to any output device (such as a window or a portal) to start.
     * @param node The part of the scenegraph this camera sees.
     * @param cameraNode The node the camera is attached to
     * @param aComponent The component this surface is connected to
     */
    public ZDrawingSurface(ZCamera camera, ZNode cameraNode, JComponent aComponent) {
        tmpRepaintRect = new Rectangle();
        component = aComponent;
        setCamera(camera, cameraNode);
    }

    /**
     * Specify that future rendering should occur at the specified quality.
     * All rendering occurs at this quality except for when the surface
     * is being interacted with or animated in which case it uses the
     * interactingRenderQuality.
     * @see #setInteractingRenderQuality
     * @param qualityRequested Can be <code>RENDER_QUALITY_LOW</code>,
     * <code>RENDER_QUALITY_MEDIUM</code> or <code>RENDER_QUALITY_HIGH</code>.
     */
    public void setRenderQuality(int qualityRequested) {
        normalRenderQuality = qualityRequested;
        if (!isInteracting()) {
            if (qualityRequested > currentRenderQuality) {
                currentRenderQuality = qualityRequested;
                repaint();
            } else {
                currentRenderQuality = qualityRequested;
            }
        }
    }

    /**
     * Determine the render quality that is used when not interacting or animating.
     * @return the normal render quality. Can be <code>RENDER_QUALITY_LOW</code>,
     * <code>RENDER_QUALITY_MEDIUM</code> or <code>RENDER_QUALITY_HIGH</code>.
     */
    public int getRenderQuality() {
        return normalRenderQuality;
    }

    /**
     * Specify the render quality to be used during interaction
     * and animation.
     * @param qualityRequested Can be <code>RENDER_QUALITY_LOW</code>,
     * <code>RENDER_QUALITY_MEDIUM</code> or <code>RENDER_QUALITY_HIGH</code>.
     */
    public void setInteractingRenderQuality(int qualityRequested) {
        interactingRenderQuality = qualityRequested;
        if (isInteracting()) {
            if (qualityRequested > currentRenderQuality) {
                currentRenderQuality = qualityRequested;
                repaint();
            } else {
                currentRenderQuality = qualityRequested;
            }
        }
    }

    /**
     * Determine the render quality that is used during interaction and animation.
     */
    public int getInteractingRenderQuality() {
        return interactingRenderQuality;
    }

    /**
     * If set to true, then the fractional font metrics rendering hint is
     * turned on.  Note that this causes problems with editing Swing text
     * components on the jazz surface since these components rely on integer
     * math (as of JDK 1.4.0)
     * @param useFractionalMetrics Are fractional metrics requested
     */
    public void setUseFractionalMetrics(boolean useFractionalMetrics) {
	if (this.useFractionalMetrics != useFractionalMetrics) {
	    this.useFractionalMetrics = useFractionalMetrics; 
	    repaint();
	}
    }


    /**
     * Get whether fraction metrics will be used
     * @return Get whether fractional metrics will be used
     */
    public boolean getUseFractionalMetrics() {
	return useFractionalMetrics;
    }

    /**
     * Get the component that this surface is attached to, or null if none.
     * @return The component this surface is attached to, or null if none.
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * Set the component that this surface is attached to, or null if none.
     * @param aComponent The component this surface is attached to, or null if none.
     */
    public void setComponent(JComponent aComponent) {
        component = aComponent;
    }

    /**
     * Sets the camera that this surface is rendered with.
     * @param cam The new camera
     * @param camNode The camera's node
     */
    public void setCamera(ZCamera cam, ZNode camNode) {
        if (camera != null) {
            camera.setDrawingSurface(null);
        }
        camera = cam;
        cameraNode = camNode;
        if (camera != null) {
            camera.setDrawingSurface(this);
        }
    }

    /**
     * Get the camera this surface is associated with.
     * @return the camera this surface is associated with.
     */
    public ZCamera getCamera() {
        return camera;
    }

    /**
     * Get the camera node this surface is associated with.
     * @return the camera node this surface is associated with.
     */
    public ZNode getCameraNode() {
        return cameraNode;
    }

    /**
     * Determine if the user interacting with the surface
     * @return true if the user is interacting with the surface, false otherwise.
     */
    public boolean isInteracting() {
        return interacting;
    }

    /**
     * Specify if the user is interacting with the surface or not.
     * Typically, event handlers will set this to true on a button press event,
     * and to false on a button release event.  This allows Jazz to change the
     * render quality to favor speed during interaction, and quality when not
     * interacting.
     *
     * @param v true if the user is interacting with the surface, false otherwise.
     */
    public void setInteracting(boolean v) {
        if (v && !interacting) {
            interacting = true;
            if (interactingRenderQuality > currentRenderQuality) {
                currentRenderQuality = interactingRenderQuality;
                repaint();
            } else {
                currentRenderQuality = interactingRenderQuality;
            }
        } else if (!v && interacting) {
            interacting = false;
            if (normalRenderQuality >= currentRenderQuality) {
                currentRenderQuality = normalRenderQuality;
                repaint();
            } else {
                currentRenderQuality = normalRenderQuality;
            }
        }
    }

    /**
     * Internal method to specify whether items within this view are currently
     * being animated.
     * @param v true when animating
     */
    void setAnimating(boolean v) {
        if (v && !animating) {
            animating = true;
            if (interactingRenderQuality > currentRenderQuality) {
                currentRenderQuality = interactingRenderQuality;
                repaint();
            } else {
                currentRenderQuality = interactingRenderQuality;
            }
        } else if (!v && animating) {
            animating = false;
            if (normalRenderQuality > currentRenderQuality) {
                currentRenderQuality = normalRenderQuality;
                repaint();
            } else {
                currentRenderQuality = normalRenderQuality;
            }
        }
    }

    /**
     * Determine if view is currently being animated.
     */
    boolean getAnimating() {
        return animating;
    }

    /**
     * A utility function to repaint the entire component.
     * This results in request being queued for the entire component to be painted.
     * The paint does not happen immediately, but instead occurs when the queued
     * request comes to the head of the Swing event queue and is fired.
     */
    public void repaint() {
        if (camera != null) {
            camera.repaint();
        }
    }

    /**
     * Internal method to notify the surface that the specified bounds should be repainted.
     * This queues an event requesting the repaint to happen.
     * Note that the input parameter may be modified as a result of this call.
     * @param repaintBounds The bounds that need to be redrawn (in global coordinates).
     */
    void repaint(ZBounds repaintBounds) {
        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println("ZDrawingSurface.repaint: repaintBounds = " + repaintBounds);
        }

        if (component != null) {
                                // We need to round conservatively so the repainted area is big enough
            tmpRepaintRect.setRect((int)(repaintBounds.getX() - 1.0),
                                   (int)(repaintBounds.getY() - 1.0),
                                   (int)(repaintBounds.getWidth() + 3.0),
                                   (int)(repaintBounds.getHeight() + 3.0));
            component.repaint(tmpRepaintRect);
        }

        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println();
        }
    }

    /**
     * Paints the camera this surface sees.
     * @param Graphics The graphics to use for rendering.
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        Rectangle rectSrc = g2.getClipBounds();
        ZBounds paintBounds;
        if (rectSrc == null) {
            paintBounds = camera.getBounds();
        } else {
            paintBounds = new ZBounds(rectSrc);
        }

        if (ZDebug.debug && ZDebug.debugRender) {
            System.out.println("ZDrawingSurface.paint(ZRenderContext): transform   = " + g2.getTransform());
            System.out.println("ZDrawingSurface.paint(ZRenderContext): clip bounds = " + g2.getClipBounds());
            System.out.println("ZDrawingSurface.paint(ZRenderContext): paint bounds = " + paintBounds);
            ZDebug.clearPaintCount();
        }

        ZRenderContext rc = camera.createRenderContext(g2, paintBounds, this, currentRenderQuality);
	rc.setAccurateSpacing(useFractionalMetrics);
        camera.getRoot().setCurrentRenderContext(rc);
        camera.render(rc);
        camera.getRoot().setCurrentRenderContext(null);

        if (ZDebug.debug && ZDebug.debugRender) {
            System.out.println("ZDrawingSurface.paint: Rendered " + ZDebug.getPaintCount() + " objects this pass");
            System.out.println("");
        }
    }

    /**
     * Force this surface to immediately paint any regions that are out of date
     * and marked for future repainting.
     */
    public void paintImmediately() {
        if (paintingImmediately) {
                                // Stop recursion or paintDirtyRegions will throw an exception
            return;
        }

        paintingImmediately = true;
        if (component != null) {
            RepaintManager.currentManager(component).paintDirtyRegions();
        }
        paintingImmediately = false;
    }

    /**
     * Returns the path to the first object intersecting the specified rectangle within DEFAULT_HALO pixels
     * as searched in reverse (front-to-back) order, or null if no objects satisfy criteria.
     * If no object is picked, then the path contains just the top-level camera as a terminal object.
     * @param x X-coord of pick point in window coordinates.
     * @param y Y-coord of pick point in window coordinates.
     * @return The ZSceneGraphPath to the picked object.
     */
    public ZSceneGraphPath pick(int x, int y) {
        return pick(x, y, DEFAULT_HALO);
    }

    /**
     * Returns the path to the first object intersecting the specified rectangle within halo pixels
     * as searched in reverse (front-to-back) order, or null if no objects satisfy criteria.
     * If no object is picked, then the path contains just the top-level camera as a terminal object.
     * @param x X-coord of pick point in window coordinates.
     * @param y Y-coord of pick point in window coordinates.
     * @param halo The amount the point can miss an object and still pick it
     * @return The ZSceneGraphPath to the picked object.
     */
    public ZSceneGraphPath pick(int x, int y, int halo) {
        ZSceneGraphPath path = new ZSceneGraphPath();

        if (camera != null) {
            long startTime = 0, pickTime = 0;
            if (ZDebug.debug && ZDebug.debugTiming) {
                startTime = System.currentTimeMillis();
            }

            ZBounds rect = new ZBounds(x-halo, y-halo, halo+halo, halo+halo);
            path.setRoot(camera.getRoot());
            path.setTopCamera(camera);
            path.setTopCameraNode(cameraNode);
            camera.pick(rect, path);

            if (ZDebug.debug && ZDebug.debugTiming) {
                pickTime = System.currentTimeMillis();
                System.out.println("ZDrawingSurface.pick: pickTime = " + (pickTime - startTime));
            }
        }
        if (ZDebug.debug && ZDebug.debugPick) {
            System.out.println("ZDrawingSurface.pick: " + path);
        }

        return path;
    }

    /**
     * Return the list of nodes that are accepted by the specified filter in the
     * portion of the scenegraph visible within the camera attached to this surface.
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
        ArrayList nodes = new ArrayList();
        if (camera != null) {
            camera.findNodes(filter, nodes);
        }
        return nodes;
    }

    /**
     * Constructs a new PrinterJob, allows the user to select which printer
     * to print to, and prints the surface.
     */
    public void printSurface() {
        PrinterJob printJob = PrinterJob.getPrinterJob();

                                // Set up a new book so we can specify the numbe of pages (1)
        PageFormat pageFormat = printJob.defaultPage();
        Book book = new Book();
        book.append(this, pageFormat);
        printJob.setPageable(book);

        if (printJob.printDialog()) {   // Open up a print dialog
            try {
                printJob.print();       // Then, start the print
            } catch (Exception e) {
                System.out.println("Error Printing");
                e.printStackTrace();
            }
        }
    }

    /**
     * Prints the surface into the specified Graphics context in the specified format. A PrinterJob
     * calls the printable interface to request that a surface be rendered into the context specified
     * by the graphics. The format of the page to be drawn is specified by PageFormat. The zero based
     * index of the requested page is specified by pageIndex. If the requested page does not exist then
     * this method returns NO_SUCH_PAGE; otherwise PAGE_EXISTS is returned. If the printable object aborts
     * the print job then it throws a PrinterException
     * @param graphics    the context into which the page is drawn
     * @param pageFormat  the size and orientation of the page being drawn
     * @param pageIndex   the zero based index of the page to be drawn
     */
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D)graphics;

                                // translate the graphics to the printable bounds on the page
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                                // scale the graphics to the printable bounds on the page
        ZBounds cameraBounds = camera.getBounds();
        double scaleFactor = pageFormat.getImageableWidth()/cameraBounds.getWidth();
        if (pageFormat.getImageableHeight()/cameraBounds.getHeight() < scaleFactor) {
            scaleFactor = pageFormat.getImageableHeight()/cameraBounds.getHeight();
        }
        g2.scale(scaleFactor, scaleFactor);

                                // paint onto the printer graphics
        ZRenderContext rc = camera.createRenderContext(g2, new ZBounds(cameraBounds), this, RENDER_QUALITY_HIGH);
        camera.getRoot().setCurrentRenderContext(rc);
        camera.render(rc);
        camera.getRoot().setCurrentRenderContext(null);

        return PAGE_EXISTS;
    }

    /**
     * Generate a string that represents this object for debugging.
     * @return the string that represents this object for debugging
     * @see ZDebug#dump
     */
    public String dump() {
        String str = toString();
        str += "\n Camera: " + camera;
        return str;
    }
}

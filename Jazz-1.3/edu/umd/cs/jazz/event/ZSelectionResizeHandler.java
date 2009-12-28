package edu.umd.cs.jazz.event;

/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import javax.swing.event.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.component.ZRectangle;

/**
 * <b>ZSelectionResizeHandler</b> is a selection handler for use with
 * <b>ZSelectionManager</b>.  <code>ZSelectionResizeHandler</code> renders
 * resize "handles" over the current selection, and allows the user to
 * resize the selection by dragging these handles.
 * <P>
 * Resizing works as expected from drawing applications such as PowerPoint.
 * Dragging a resize handle allows for unconstrained scaling while shift-drag
 * allows constrained proportion scaling.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see ZSelectionManager
 * @see ZSelectionModifyHandler
 * @see ZSelectionMoveHandler
 * @see ZSelectionDeleteHandler
 * @see ZSelectionScaleHandler
 * @author Antony Courtney, Yale University
 * @author Lance Good, University of Maryland
 * @author Benjamin Bederson, University of Maryland
 */
public class ZSelectionResizeHandler implements ZEventHandler, ZGroupListener, Serializable {

    /** The default highlight color */
    private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.red.darker();

    /** The resize handle size */
    protected static double RESIZE_HANDLE_SIZE = 6.0;

    /** true when event handler is active */
    private boolean active = false;

    /** rrue if this event handler is globally active rather than per-camera */
    private boolean globallyActive = false;

    /** node this event handler attaches to */
    private ZNode node = null;

    /** The active camera if this is a per-camera event handler */
    private ZCamera activeCamera = null;

    /** camera found in latest interaction */
    private ZCamera interactionCamera = null;

    /** highlight color for highlighted nodes */
    private Color highLightColor = DEFAULT_HIGHLIGHT_COLOR;

    /** active resize handles.  Maps from ZNode -> ResizeHandle */
    protected transient HashMap activeHandles;

    /** registered event listeners */
    protected transient EventListenerList listenerList;

    /** the current resizer - handles the actual resizing */
    private ResizeListener defaultResizeListener;

    private int allButton1ButShiftMask   = (MouseEvent.BUTTON1_MASK |
                                            MouseEvent.ALT_GRAPH_MASK |
                                            MouseEvent.CTRL_MASK);

    /**
     * Interface that a class must implement to handle the resizing
     * of the selected nodes for a given ZSelectionResizeHandler.
     * A default resizer is provided by ZSelectionResizeHandler so
     * applications need not implement their own for the default behavior.
     *
     * @see ZSelectionResizeHandler.ResizeEvent
     */
    public interface ResizeListener extends EventListener {

        /**
         * Fired when a node is resized by the current resize handler
         * @param re The resize event for the current resize
         */
        public void nodeResized(ResizeEvent re);

        /**
         * Signals the end of a resize for a group of nodes
         */
        public void resizeComplete();
    }

    /**
     * An event to signal that a node has been resized by this event handler.
     * This event provides several scale values to allow for various resizing
     * behaviors other than the default.
     * <p>
     * The boolean value <code>constrained</code> signals that this resize
     * was considered constrained by the default standard, namely that the
     * shift key was depressed during the resize.
     * <p>
     * The values returned by <code>getScaleX</code> and <code>getScaleY<code>
     * represent the default scale values based on the value of
     * <code>constrained</code>.  The constrained values are provided by
     * the <code>getConstrainScaleX</code> and <code>getConstrainScaleY<code>.
     * <p>
     * The scale values for no flip scaling are also provided.  The term
     * "no flip" does not indicate that the values will always be positive
     * but rather indicates that the point from which the scale is computed
     * remains constant.  For example, if the bottom right resize handle is
     * grabbed, the new size will always be computed from the upper right
     * hand corner. In the previous case (ie. when flipping is employed) when
     * the scale  becomes negative, it is necessary to change the point from
     * which the new width is computed.  This is necessary for resizing in
     * which flipping occurs because of the representation of rectangles.
     * Rectangles can not have negative width or height, hence when the
     * object's scale becomes negative the point from which the new width
     * or height is computed must change, eg. from the x point to the
     * x point plus the width.
     */
    public static class ResizeEvent {

        /**
         * The resized node
         */
        ZNode node;

        /**
         * The mouse event that caused the resize
         */
        ZMouseEvent mouseEvent;

        /**
         * The default X scale
         */
        double xScale;

        /**
         * The default Y scale
         */
        double yScale;

        /**
         * The alternate X scale - eg. a constrained scale if the default
         * scale was unconstrained
         */
        double constrainXScale;

        /**
         * The alternate Y scale - eg. a constrained scale if the default
         * scale was unconstrained
         */
        double constrainYScale;

        /**
         * The default X scale in which the point from which the scale is
         * computed doesn't change
         */
        double noFlipXScale;

        /**
         * The default Y scale in which the point from which the scale is
         * computed doesn't change
         */
        double noFlipYScale;

        /**
         * The alternate X scale in which the point from which the scale is
         * computed doesn't change
         */
        double constrainNoFlipXScale;

        /**
         * The alternate Y scale in which the point from which the scale is
         * computed doesn't change
         */
        double constrainNoFlipYScale;

        /**
         * The X coordinate around which the resizing occurs
         */
        double x;

        /**
         * The Y coordinate around which the resizing occurs
         */
        double y;

        /**
         * @param node The node that is being resized
         * @param mouseEvent The mouse event that caused the resize
         * @param xScale The percentage of the current width
         * @param yScale The percentage of the current height
         * @param constrainXScale The constrained x scale percentage
         * @param constrainYScale The constrained y scale percentage
         * @param noFlipXScale The non-flipping x scale percentage
         * @param noFlipYScale The non-flipping y scale percentage
         * @param constrainNoFlipXScale The constrained non-flip x scale
         * @param constrainNoFlipYScale The constrained non-flip y scale
         * @param x The x point around which resizing occurs
         * @param y The y point around which resizing occurs
         */
        public ResizeEvent(ZNode node, ZMouseEvent mouseEvent,
                           double xScale, double yScale,
                           double constrainXScale, double constrainYScale,
                           double noFlipXScale, double noFlipYScale,
                           double constrainNoFlipXScale, double constrainNoFlipYScale,
                           double x, double y) {
            this.node = node;
            this.mouseEvent = mouseEvent;
            this.xScale = xScale;
            this.yScale = yScale;
            this.constrainXScale = constrainXScale;
            this.constrainYScale = constrainYScale;
            this.noFlipXScale = noFlipXScale;
            this.noFlipYScale = noFlipYScale;
            this.constrainNoFlipXScale = constrainNoFlipXScale;
            this.constrainNoFlipYScale = constrainNoFlipYScale;
            this.x = x;
            this.y = y;
        }

        /**
         * @return The node that was resized
         */
        public ZNode getNode() {
            return node;
        }

        /**
         * @return The mouse event that cause the resize
         */
        public ZMouseEvent getMouseEvent() {
            return mouseEvent;
        }

        /**
         * @return The default x scale
         */
        public double getScaleX() {
            return xScale;
        }

        /**
         * @return The default y scale
         */
        public double getScaleY() {
            return yScale;
        }

        /**
         * @return The constrained x scale
         */
        public double getConstrainScaleX() {
            return constrainXScale;
        }

        /**
         * @return The constrained y scale
         */
        public double getConstrainScaleY() {
            return constrainYScale;
        }

        /**
         * @return The default no-flip x scale
         */
        public double getNoFlipScaleX() {
            return noFlipXScale;
        }

        /**
         * @return The default no-flip y scale
         */
        public double getNoFlipScaleY() {
            return noFlipYScale;
        }

        /**
         * @return The constrained no-flip x scale
         */
        public double getNoFlipConstrainScaleX() {
            return constrainNoFlipXScale;
        }

        /**
         * @return The constrained no-flip y scale
         */
        public double getNoFlipConstrainScaleY() {
            return constrainNoFlipYScale;
        }

        /**
         * @return The x coordinate around which the object is resized
         */
        public double getX() {
            return x;
        }

        /**
         * @return The y coordinate around which the object is resized
         */
        public double getY() {
            return y;
        }
    }

    /**
     * Inner class used to render resize handles over a single component.
     * <P>
     * Terminology:  A "compass position" is a pair of integers (x,y) drawn
     * from the set {-1,0,1} that identifies a particular resize handle
     * relative to a 3x3 square whose origin is the center.
     * The upper-left resize handle is identified as (-1,-1), upper-right
     * as (1,-1), etc.
     */
    protected class ResizeHandles extends ZVisualComponent implements ZMouseListener, ZMouseMotionListener {
        /** Default pen color */
        public final Color penColor_DEFAULT = ZSelectionGroup.penColor_DEFAULT;

	// The set of PROTECTED variables which might be generally useful

        /** Pen color for rendering of selection */
        protected Color penColor  = penColor_DEFAULT;

        /** actual rectangles rendered for the handles */
        protected Rectangle2D[] handles = null;

        /** location of most recent mouse press on a resize handle,
         *  in global coordinates. */
        protected Point2D pressPt;

        /** the node that was pressed */
        protected ZNode pressNode = null;

        /** relative compass location of resize handle of last press */
        protected Point pressHandle;

        /** the handle to highlight */
        protected Point highLightHandle;

        /** location of most recent drag, in global coordinates */
        protected Point2D dragPt;

        /** The current selected list */
        protected ArrayList selection = null;

	// The set of PRIVATE variables which are more dependent on this
	// particular implementation

        /** have we rendered the handles yet? */
        private boolean rendered = false;

        /** The width of the selected object at mouse press */
        private double pressWidth;

        /** The height of the selected object at mouse press */
        private double pressHeight;

        /** The x scale of the selected object at mouse press */
        private double pressScaleX;

        /** The y scale of the selected object at mouse press */
        private double pressScaleY;

        /** The x scale of the selected object at previous drag */
        private double prevScaleX;

        /** The y scale of the selected object at previous drag */
        private double prevScaleY;

        /** The x constrained scale on the previous drag */
        private double prevConstrainScaleX;

        /** The y constrained scale on the previous drag */
        private double prevConstrainScaleY;

        /** The x scale of the selected object at previous drag */
        private double prevNoFlipScaleX;

        /** The y scale of the selected object at previous drag */
        private double prevNoFlipScaleY;

        /** The x constrained scale on the previous drag */
        private double prevNoFlipConstrainScaleX;

        /** The y constrained scale on the previous drag */
        private double prevNoFlipConstrainScaleY;

        /**
         * A list of boolean objects to indicate whether each of
         * the selected objects have negative x scale
         */
        private ArrayList selectedInverseX = new ArrayList();

        /**
         * A list of boolean objects to indicate whether each of
         * the selected objects have negative y scale
         */
        private ArrayList selectedInverseY = new ArrayList();

        /** true if we are performing a drag operation */
        private boolean dragging = false;

        /** A temporary storage point */
        private Point2D tmpPoint = new Point2D.Double();

        /**
         * Default constructor
         */
        public ResizeHandles() {
            super();
            handles = new Rectangle2D[9];
            pressPt = new Point2D.Double();
            dragPt = new Point2D.Double();
        }

        /**
         * Compute index of resize handle from relative compass position.
         */
        private final int mapIndex(int i,int j) {
            return (j+1)*3+(i+1);
        }

        /**
         * Compute compass position of resize handle from an index.
         */
        private final Point unMapIndex(int index) {
            Point cp = new Point((index % 3) - 1,(index / 3) - 1);
            return cp;
        }

        /**
         * Indicates a pick in case the underlying visual components
         * aren't picked
         */
        public boolean pick(Rectangle2D pickRect, ZSceneGraphPath path) {
            return contains(pickRect);
        }

        /**
         * Render a single resize handle, centered at the given x,y position.
         * @return the newly allocated rectangle that represents the geometry
         *         of the handle.
         */
        protected Rectangle2D getHandle(double cx,
                                        double cy,
                                        double xLen,
                                        double yLen) {
            Rectangle2D r = new Rectangle2D.Double(cx - (xLen/2),
                                                   cy - (yLen/2),
                                                   xLen,
                                                   yLen);
            return r;
        }

        /**
         * Renders the given ResizeHandles
         * @param ctx The current render context
         */
        public void render(ZRenderContext ctx) {
            Graphics2D g2 = ctx.getGraphics2D();
            AffineTransform transform = g2.getTransform();
            double xScale = Math.abs(transform.getScaleX());
            double yScale = Math.abs(transform.getScaleY());
            double xSz = 1.0 / xScale;
            double ySz = 1.0 / yScale;
            double xLen = RESIZE_HANDLE_SIZE*xSz;
            double yLen = RESIZE_HANDLE_SIZE*ySz;

            ZNode p = getParents()[0];

            if (p instanceof ZSelectionGroup) {
                ZSelectionGroup g = (ZSelectionGroup)p;
                Rectangle2D r = g.getBounds();
                double x = r.getX();
                double y = r.getY();
                double w = r.getWidth();
                double h = r.getHeight();

                // don't draw very small selection objects
                if (w < 3.0*xLen ||
                    h < 3.0*yLen) {
                    rendered = false;
                    return;
                }

                double cx = x + (w/2);
                double cy = y + (h/2);
                w -= xLen + 2*xSz;
                h -= yLen + 2*ySz;

                g2.setColor(penColor);

                Rectangle2D handle;

                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if ((i != 0) || (j != 0)) {
                            handle = getHandle(cx+i*(w/2),
                                               cy+j*(h/2),
                                               xLen,
                                               yLen);
                            if (highLightHandle != null &&
                                ((int)highLightHandle.getX()) == i &&
                                ((int)highLightHandle.getY()) == j) {
                                g2.setColor(highLightColor);
                                handles[mapIndex(i,j)] = handle;
                                g2.fill(handle);
                                g2.setColor(penColor);
                            }
                            else {
                                handles[mapIndex(i,j)] = handle;
                                g2.fill(handle);
                            }
                        }
                    }
                }

                rendered = true;
            }
        }

        /**
         * The resize handles don't have logical bounds
         */
        protected void computeBounds() {
        }

        /**
         * Used by ZSelectionResizeHandler to determine whether or not
         * the mouse is over a resize handle.  Tests the given (x,y)
         * position to determine if it is over a resize handle.  If so,
         * sets <code>cp</code> to the compass position of the handle,
         * and returns <code>true</code>.  Otherwise returns false.
         *
         * @param x x position, in local coordinates
         * @param y y position, in local coordinates
         * @return cp out parameter: compass point of handle, if found.
         */
        public Point contains(double x,double y) {
            if (!rendered) {
                return null;
            }

            for (int i=0; i < 9; i++) {
                Rectangle2D r = handles[i];

                if (r != null && r.contains(x,y)) {
                    return unMapIndex(i);
                }
            }

            return null;
        }

        /**
         * Used by ZSelectionResizeHandler to determine whether or not
         * the mouse is over a resize handle.  Tests the given rectangle
         * to determine if it is contained by a resize handle.
         *
         * @param x x position, in local coordinates
         * @param y y position, in local coordinates
         * @return True if one of the handles contains the point
         */
        public boolean contains(Rectangle2D rect) {
            if (!rendered) {
                return false;
            }

            for (int i=0; i < 9; i++) {
                Rectangle2D r = handles[i];

                if (r != null && r.contains(rect.getX()+rect.getWidth()/2.0,
                                            rect.getY()+rect.getHeight()/2.0)) {
                    return true;
                }
            }

            return false;
        }


        /**
         * @param bounds The current global bounds of the scaled node
         * @param oldPt The press pt from which the old dimensions are computed
         * @param newPt The drag pt from which the new dimensions are computed
         */
        private void scaleSelection(ZBounds bounds,
                                    Point2D oldPt,
                                    Point2D newPt,
                                    ZMouseEvent mouseEvent) {

            Point2D scalePoint = new Point2D.Double();
            double scaleX;
            double scaleY;
            double noFlipScaleX;
            double noFlipScaleY;
            double baseX;
            double baseY;
            double noFlipBaseX;
            double noFlipBaseY;
            double oldWidth, oldHeight;
            double newWidth, newHeight;
            double oldNoFlipWidth, oldNoFlipHeight;
            double newNoFlipWidth, newNoFlipHeight;
            double offX, offY;
            double noFlipOffX, noFlipOffY;

            // ***************************************************
            // Compute the base point from which the width and height
            // will be computed.  This depends on the previous scale
            // and the handle pressed.

            // Compute the base X
            if (pressHandle.getX() >= 0) {
                if (prevScaleX >= 0) {
                    baseX = bounds.getX();
                }
                else {
                    baseX = bounds.getX()+bounds.getWidth();
                }
                noFlipBaseX = bounds.getX();
            }
            else {
                if (prevScaleX >= 0) {
                    baseX = bounds.getX()+bounds.getWidth();
                }
                else {
                    baseX = bounds.getX();
                }
                noFlipBaseX = bounds.getX()+bounds.getWidth();
            }

            // Compute the base Y
            if (pressHandle.getY() >= 0) {
                if (prevScaleY >= 0) {
                    baseY = bounds.getY();
                }
                else {
                    baseY = bounds.getY()+bounds.getHeight();
                }
                noFlipBaseY = bounds.getY();
            }
            else {
                if (prevScaleY >= 0) {
                    baseY = bounds.getY()+bounds.getHeight();
                }
                else {
                    baseY = bounds.getY();
                }
                noFlipBaseY = bounds.getY()+bounds.getHeight();
            }
            // ***************************************************


            // ***************************************************
            // Now compute the old and new widths and heights to
            // later compute X and Y scales

            oldWidth = Math.abs(oldPt.getX()-baseX);
            oldHeight = Math.abs(oldPt.getY()-baseY);

            oldNoFlipWidth = Math.abs(oldPt.getX()-noFlipBaseX);
            oldNoFlipHeight = Math.abs(oldPt.getY()-noFlipBaseY);

            offX = pressWidth-oldWidth;
            offY = pressHeight-oldHeight;

            noFlipOffX = pressWidth-oldNoFlipWidth;
            noFlipOffY = pressHeight-oldNoFlipHeight;

            if (pressHandle.getX() >= 0) {
                newWidth = newPt.getX()-baseX;
                newNoFlipWidth = newPt.getX()-noFlipBaseX;
            }
            else {
                newWidth = baseX-newPt.getX();
                newNoFlipWidth = noFlipBaseX-newPt.getX();
            }

            if (pressHandle.getY() >= 0) {
                newHeight = newPt.getY()-baseY;
                newNoFlipHeight = newPt.getY()-noFlipBaseY;
            }
            else {
                newHeight = baseY-newPt.getY();
                newNoFlipHeight = noFlipBaseY-newPt.getY();
            }

            // Adjust the new width based on the pressed offset
            // from the handle edge
            if (newWidth > 0.0) {
                newWidth += offX;
            }
            else {
                newWidth -= offX;
            }

            // Adjust the new no flip width based on the pressed
            // offset from the handle edge
            if (newNoFlipWidth > 0.0) {
                newNoFlipWidth += noFlipOffX;
            }
            else {
                newNoFlipWidth -= noFlipOffX;
            }

            // Adjust the new height based on the pressed offset
            // from the handle edge
            if (newHeight > 0.0) {
                newHeight += offY;
            }
            else {
                newHeight -= offY;
            }

            // Adjust the new no flip height based on the pressed
            // offset from the handle edge
            if (newNoFlipHeight > 0.0) {
                newNoFlipHeight += noFlipOffY;
            }
            else {
                newNoFlipHeight -= noFlipOffY;
            }

            // ***************************************************


            // ***************************************************
            // Compute the X and Y scales

            // Compute the X scale
            if (pressHandle.getX() != 0) {
                if (oldWidth == 0.0 || newWidth == 0.0) {
                    scaleX = 1.0;
                }
                else {
                    scaleX = newWidth/pressWidth;
                }

                if (oldNoFlipWidth == 0.0 || newNoFlipWidth == 0.0) {
                    noFlipScaleX = 1.0;
                }
                else {
                    noFlipScaleX = newNoFlipWidth/pressWidth;
                }
            }
            else {
                scaleX = 1.0;
                noFlipScaleX = 1.0;
            }

            // Compute the Y scale
            if (pressHandle.getY() != 0) {
                if (oldHeight == 0.0 || newHeight == 0.0) {
                    scaleY = 1.0;
                }
                else {
                    scaleY = newHeight/pressHeight;
                }

                if (oldNoFlipHeight == 0.0 || newNoFlipHeight == 0.0) {
                    noFlipScaleY = 1.0;
                }
                else {
                    noFlipScaleY = newNoFlipHeight/pressHeight;
                }
            }
            else {
                scaleY = 1.0;
                noFlipScaleY = 1.0;
            }
            // ***************************************************

            // ***************************************************
            // Now scale each of the selected nodes around a specific point

            double xSign = (scaleX)*Math.abs(scaleX) / Math.pow(scaleX,2);
            double ySign = (scaleY)*Math.abs(scaleY) / Math.pow(scaleY,2);
            double scale = 1.0;

            double noFlipXSign = (noFlipScaleX)*Math.abs(noFlipScaleX) / Math.pow(noFlipScaleX,2);
            double noFlipYSign = (noFlipScaleY)*Math.abs(noFlipScaleY) / Math.pow(noFlipScaleY,2);
            double noFlipScale = 1.0;

            if (pressHandle.getX() != 0 && pressHandle.getY() != 0) {
                scale = Math.max(Math.abs(scaleX),Math.abs(scaleY));
                noFlipScale = Math.max(Math.abs(noFlipScaleX),Math.abs(noFlipScaleY));
            }
            else if (pressHandle.getX() == 0) {
                scale = Math.abs(scaleY);
                noFlipScale = Math.abs(noFlipScaleY);
            }
            else if (pressHandle.getY() == 0) {
                scale = Math.abs(scaleX);
                noFlipScale = Math.abs(noFlipScaleX);
            }

            for (int i=0; i<selection.size(); i++) {
                ZNode selNode = (ZNode)selection.get(i);
                bounds = selNode.getBounds();

                boolean inverseX = ((Boolean)selectedInverseX.get(i)).booleanValue();
                boolean inverseY = ((Boolean)selectedInverseY.get(i)).booleanValue();

                // First set the x scale coordinate
                if (inverseX) {
                    if (pressHandle.getX() > 0) {
                        scalePoint.setLocation(bounds.getX()+bounds.getWidth(),
                                               bounds.getY()+bounds.getHeight());
                    }
                    else if (pressHandle.getX() == 0) {
                        scalePoint.setLocation(bounds.getX()+bounds.getWidth()/2.0,
                                               scalePoint.getY());
                    }
                    else {
                        scalePoint.setLocation(bounds.getX(),
                                               scalePoint.getY());
                    }
                }
                else {
                    if (pressHandle.getX() > 0) {
                        scalePoint.setLocation(bounds.getX(),
                                               scalePoint.getY());
                    }
                    else if (pressHandle.getX() == 0) {
                        scalePoint.setLocation(bounds.getX()+bounds.getWidth()/2.0,
                                               scalePoint.getY());
                    }
                    else {
                        scalePoint.setLocation(bounds.getX()+bounds.getWidth(),
                                               bounds.getY()+bounds.getHeight());
                    }
                }

                // Now set the y scale coordinate
                if (inverseY) {
                    if (pressHandle.getY() > 0) {
                        scalePoint.setLocation(scalePoint.getX(),
                                               bounds.getY()+bounds.getHeight());
                    }
                    else if (pressHandle.getY() == 0) {
                        scalePoint.setLocation(scalePoint.getX(),
                                               bounds.getY()+bounds.getHeight()/2.0);
                    }
                    else {
                        scalePoint.setLocation(scalePoint.getX(),
                                               bounds.getY());
                    }
                }
                else {
                    if (pressHandle.getY() > 0) {
                        scalePoint.setLocation(scalePoint.getX(),
                                               bounds.getY());
                    }
                    else if (pressHandle.getY() == 0) {
                        scalePoint.setLocation(scalePoint.getX(),
                                               bounds.getY()+bounds.getHeight()/2.0);
                    }
                    else {
                        scalePoint.setLocation(scalePoint.getX(),
                                               bounds.getY()+bounds.getHeight());
                    }
                }

                ResizeEvent re = new ResizeEvent(selNode, mouseEvent,
                                                 xSign*Math.abs(scaleX)/prevScaleX,
                                                 ySign*Math.abs(scaleY)/prevScaleY,
                                                 xSign*scale/prevConstrainScaleX,
                                                 ySign*scale/prevConstrainScaleY,
                                                 noFlipXSign*Math.abs(noFlipScaleX)/prevNoFlipScaleX,
                                                 noFlipYSign*Math.abs(noFlipScaleY)/prevNoFlipScaleY,
                                                 noFlipXSign*noFlipScale/prevNoFlipConstrainScaleX,
                                                 noFlipYSign*noFlipScale/prevNoFlipConstrainScaleY,
                                                 scalePoint.getX(),
                                                 scalePoint.getY());

                fireResizeEvent(re);
            }

            prevScaleX = scaleX;
            prevScaleY = scaleY;
            prevConstrainScaleX = xSign*scale;
            prevConstrainScaleY = ySign*scale;
            prevNoFlipScaleX = noFlipScaleX;
            prevNoFlipScaleY = noFlipScaleY;
            prevNoFlipConstrainScaleX = noFlipXSign*noFlipScale;
            prevNoFlipConstrainScaleY = noFlipYSign*noFlipScale;
        }


        /**
         * Mouse press event handler
         * @param <code>e</code> The event.
         */
        public void mousePressed(ZMouseEvent e) {
            if (((e.getModifiers() & allButton1ButShiftMask) == MouseEvent.BUTTON1_MASK)) {
                ZSceneGraphPath path = e.getPath();

                if (globallyActive) {
                    interactionCamera = path.getCamera();
                }
                else {
                    interactionCamera = activeCamera;
                }

                // If the node isn't accessible through the specified camera
                // then just return - this means we likely pressed on an
                // empty region in an internal camera
                Point2D testPt = new Point2D.Double(e.getX(),e.getY());
                try {
                    path.screenToCamera(testPt,interactionCamera);
                    interactionCamera.cameraToLocal(testPt,path.getNode());
                }
                catch (ZNodeNotFoundException znnfe) {
                    return;
                }

                pressPt.setLocation(e.getX(), e.getY());
                path.screenToCamera(pressPt,interactionCamera);
                interactionCamera.cameraToLocal(pressPt,null);

                pressHandle = contains(testPt.getX(),testPt.getY());

                if (pressHandle != null) {
                    // consume this event so no other handler sees it...
                    e.consume();
                    pressNode = path.getNode();

                    ZBounds pressBounds = pressNode.getGlobalBounds();

                    pressWidth = pressBounds.getWidth();
                    pressHeight = pressBounds.getHeight();

                    AffineTransform globalToLocal = pressNode.getGlobalToLocalTransform();

                    if (globalToLocal.getScaleX() < 0.0) {
                        pressHandle.setLocation(-pressHandle.getX(),pressHandle.getY());
                    }
                    if (globalToLocal.getScaleY() < 0.0) {
                        pressHandle.setLocation(pressHandle.getX(),-pressHandle.getY());
                    }

                    selection = ZSelectionManager.getSelectedNodes(interactionCamera);
                    selectedInverseX.clear();
                    selectedInverseY.clear();

                    for (int i=0; i<selection.size(); i++) {
                        ZNode selNode = (ZNode)selection.get(i);

                        globalToLocal = selNode.getGlobalToLocalTransform();

                        if (globalToLocal.getScaleX() < 0.0) {
                            selectedInverseX.add(new Boolean(true));
                        }
                        else {
                            selectedInverseX.add(new Boolean(false));
                        }

                        if (globalToLocal.getScaleY() < 0.0) {
                            selectedInverseY.add(new Boolean(true));
                        }
                        else {
                            selectedInverseY.add(new Boolean(false));
                        }
                    }

                    prevScaleX = 1.0;
                    prevScaleY = 1.0;
                    prevConstrainScaleX = 1.0;
                    prevConstrainScaleY = 1.0;
                    prevNoFlipScaleX = 1.0;
                    prevNoFlipScaleY = 1.0;
                    prevNoFlipConstrainScaleX = 1.0;
                    prevNoFlipConstrainScaleY = 1.0;

                    dragging = true;
                }
            }
        }


        /**
         * Mouse drag event handler
         * @param <code>e</code> The event.
         */
        public void mouseDragged(ZMouseEvent e) {
            if (((e.getModifiers() & allButton1ButShiftMask) == MouseEvent.BUTTON1_MASK) && dragging) { // left button only:
                if (interactionCamera != null) {

                    ZSceneGraphPath path = e.getPath();

                    dragPt.setLocation(e.getX(), e.getY());

                    path.screenToCamera(dragPt,interactionCamera);
                    interactionCamera.cameraToLocal(dragPt,null);

                    scaleSelection(pressNode.getGlobalBounds(),
                                   pressPt,
                                   dragPt,
                                   e);

                    // finally: consume the event:
                    e.consume();
                }
            }
        }

        /**
         * Mouse release event handler
         * @param <code>e</code> The event.
         */
        public void mouseReleased(ZMouseEvent e) {
            if (((e.getModifiers() & allButton1ButShiftMask) == MouseEvent.BUTTON1_MASK)) {
		if (dragging) {
		    mouseMoved(e);
		    fireResizeEvent(null);
		}
            }
            dragging = false;
        }

        /**
         * Invoked when the mouse enters a component.
         */
        public void mouseEntered(ZMouseEvent e) {
        }

        /**
         * Invoked when the mouse exits a component.
         */
        public void mouseExited(ZMouseEvent e) {
            if (highLightHandle != null && !dragging) {
                highLightHandle = null;
                repaint();
            }
        }

        /**
         * Invoked when the mouse has been clicked on a component.
         */
        public void mouseClicked(ZMouseEvent e) {
        }

        /**
         * Invoked when the mouse button has been moved on a node
         * (with no buttons no down).
         */
        public void mouseMoved(ZMouseEvent e) {
            ZSceneGraphPath path = e.getPath();
            ZCamera camera;

            if (globallyActive) {
                camera = path.getCamera();
            }
            else {
                camera = activeCamera;
            }

            // If the node isn't accessible through the specified camera
            // then just return - this means we likely moused over an
            // empty region in an internal camera
            Point2D testPt = new Point2D.Double(e.getX(),e.getY());
            try {
                path.screenToCamera(testPt,camera);
                camera.cameraToLocal(testPt,path.getNode());
            }
            catch (ZNodeNotFoundException znnfe) {
                return;
            }

            Point newHighLightHandle = contains(testPt.getX(),testPt.getY());

            if ((highLightHandle == null && newHighLightHandle != null) ||
                (highLightHandle != null && newHighLightHandle == null)) {
                highLightHandle = newHighLightHandle;
                repaint();
            }
            else if (highLightHandle != null && newHighLightHandle != null &&
                     (highLightHandle.getX() != newHighLightHandle.getX() ||
                      highLightHandle.getY() != newHighLightHandle.getY())) {
                highLightHandle = newHighLightHandle;
                repaint();
            }
        }
    }

    /**
     * Construct a new ZSelectionResizeHandler which will be active across
     * all cameras
     * @param node The node to which this handler attaches
     */
    public ZSelectionResizeHandler(ZNode node) {
        this.node = node;
        globallyActive = true;
        init();
    }

    /**
     * Construct a new ZSelectionResizeHandler.
     * @param node The node to which this handler attaches
     * @param camera The camera to which this handler attaches
     */
    public ZSelectionResizeHandler(ZNode node, ZCamera camera) {
        this.node = node;
        this.activeCamera = camera;
        init();
    }

    /**
     * Add a ResizeListener, registered for nodeResized events.
     * @param l The resize listener to register
     */
    public void addResizeListener(ResizeListener l) {
        listenerList.add(ResizeListener.class, l);
    }

    /**
     * Remove a ResizeListener, registered for nodeResized events.
     * @param l The resize listener to un-register
     */
    public void removeResizeListener(ResizeListener l) {
        listenerList.remove(ResizeListener.class, l);
    }

    /**
     * Creates the default resizer which just allows unconstrained
     * scale resizing.
     */
    protected ResizeListener createResizeListener() {
        return new ResizeListener() {
            public void nodeResized(ResizeEvent re) {
                if (re.getMouseEvent().isShiftDown()) {
                    scale(re.getNode().editor().getTransformGroup(),
                          re.getConstrainScaleX(),re.getConstrainScaleY(),re.getX(),re.getY());
                }
                else {
                    scale(re.getNode().editor().getTransformGroup(),
                          re.getScaleX(),re.getScaleY(),re.getX(),re.getY());
                }
            }

            public void resizeComplete() {
            }
        };
    }

    /**
     * render the resize handles on a selected node
     */
    protected ResizeHandles createResizeHandles() {
        return new ResizeHandles();
    }

    /**
     * Fires the resize event on the appropriate listeners
     * @param re The resize event to be fired
     */
    protected void fireResizeEvent(ResizeEvent re) {
        if (listenerList == null) {
            return;
        }

        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ResizeListener.class) {
                if (re == null) {
                    ((ResizeListener)listeners[i+1]).resizeComplete();
                }
                else {
                    ((ResizeListener)listeners[i+1]).nodeResized(re);
                }
            }
        }
    }

    /**
     * Gets the current highlight color
     * @return The current highlight color
     */
    public Color getHighLightColor() {
        return highLightColor;
    }

    /**
     * Initializes this event handler
     */
    protected void init() {
        listenerList = new EventListenerList();
        defaultResizeListener = createResizeListener();
        addResizeListener(defaultResizeListener);
        activeHandles = new HashMap();
    }

    /**
     * Determines if this event handler is active.
     * @return True if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * required method of ZGroupListener:
     */
    public void nodeAdded(ZGroupEvent e) {
        ZNode node = e.getChild();
        ZSceneGraphEditor editor = node.editor();

        ResizeHandles rh = createResizeHandles();
        editor.getNode().addMouseListener(rh);
        editor.getNode().addMouseMotionListener(rh);
        editor.getSelectionGroup().addAuxiliaryVisualComponent(rh);

        activeHandles.put(node,rh);
    }

    /**
     * required method of ZGroupListener:
     */
    public void nodeRemoved(ZGroupEvent e) {
        ZNode node = e.getChild();
        ZSceneGraphEditor editor = node.editor();
        ResizeHandles handles = (ResizeHandles)activeHandles.get(node);

        if (handles != null) {
            editor.getNode().removeMouseListener(handles);
            editor.getNode().removeMouseMotionListener(handles);
        }

        activeHandles.remove(node);
    }

    /**
     * Scales the given ZTransformGroup by the given (unconstrained)
     * scale factors around the specified point
     * @param zTransform The transform group to scale
     * @param dxz The x scale
     * @param dyz The y scale
     * @param x   The x coordinate around which to scale
     * @param y   The y coordinate around which to scale
     */
    protected void scale(ZTransformGroup zTransform,
                       double dxz,
                       double dyz,
                       double x,
                       double y) {
        AffineTransform transform = zTransform.getTransform();
        transform.translate(x,y);
        transform.scale(dxz,dyz);
        transform.translate(-x,-y);
        zTransform.setTransform(transform);
    }

    /**
     * Specifies whether this event handler is active
     * @param active True to make this event handler active
     */
    public void setActive(boolean active) {
        if (this.active && !active) {
            // turn off this handler
            this.active = false;
            // un-register as a group listener for notification of changes to
            // the selection:
            if (globallyActive) {
                ZSelectionManager.removeGroupListener(this);
            }
            else {
                ZSelectionManager.removeGroupListener(this,activeCamera);
            }
        } else if (!this.active && active) {
            // turn on handler:
            this.active = true;
            // register as a group listener for notification of changes to the
            // selection:
            if (globallyActive) {
                ZSelectionManager.addGroupListener(this);
            }
            else {
                ZSelectionManager.addGroupListener(this,activeCamera);
            }
        }
    }

    /**
     * Remove the default resize listener
     */
    public void setDefaultResizeListenerActive(boolean active) {
        if (active && defaultResizeListener == null) {
            defaultResizeListener = createResizeListener();
            addResizeListener(defaultResizeListener);
        }
        else if (!active && defaultResizeListener != null) {
            removeResizeListener(defaultResizeListener);
            defaultResizeListener = null;
        }
    }

    /**
     * Sets the highlight color for this resize handler.
     * @param color The new highlight color
     */
    public void setHighLightColor(Color color) {
        highLightColor = color;
    }
}

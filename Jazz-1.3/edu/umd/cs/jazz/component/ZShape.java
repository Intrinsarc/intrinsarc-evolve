/**
 * Copyright 1998-1999 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZShape</b> is an abstract object, meant to be extended by visual components
 * that wrap standard java.awt.Shapes.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  James Mokwa
 * @author  Jesse Grosjean
 */
public abstract class ZShape extends ZBasicVisualComponent {

    private static AffineTransform sharedTempTransform = new AffineTransform();

    /**
     * Constructs a new visual component based on a java.awt.Shape
     * @param aShape a pre-defined shape.
     */
    public ZShape() {
        super();
    }

    /**
     * Return the current shape.
     */
    public abstract Shape getShape();

    /**
     * Returns the x coordinate of the upper left corner of
     * the framing rectangle in <code>double</code> precision.
     * @return the x coordinate of the upper left corner of
     * the framing rectangle.
     */
    public double getX() {
        return getFrame().getX();
    }

    /**
     * Returns the Y coordinate of the upper left corner of
     * the framing rectangle in <code>double</code> precision.
     * @return the y coordinate of the upper left corner of
     * the framing rectangle.
     */
    public double getY() {
        return getFrame().getY();
    }

    /**
     * Return the width of this ellipse.
     * @return the width.
     */
    public double getWidth() {
        return getFrame().getWidth();
    }

    /**
     * Return the height of this ellipse.
     * @return the height.
     */
    public double getHeight() {
        return getFrame().getHeight();
    }

    /**
     * Return the bounds of the internal java.awt.Shape structure. This
     * is different then getBounds() because it does not take the lineWidth
     * into consideration.
     * @return the height.
     */
    public Rectangle2D getFrame() {
        Shape shape = getShape();
        if (shape != null) {
            return shape.getBounds2D();
        } else {
            return new Rectangle2D.Double();
        }
    }

    /**
     * Returns true if the specified rectangle is on the polygon.
     * @param rect Pick rectangle of object coordinates.
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return True if rectangle overlaps object.
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        if (pickBounds(rect)) {
            double currentPenWidth = getPenWidthForCurrentContext();
            if (fillPaint != null && getShape().intersects(rect)) {
                return true;
            } else {
                return (penPaint != null && pickStroke(rect, currentPenWidth));
            }
        }
        return false;
    }

    /**
     * Returns true if the specified rectangle intersects the shapes stroke.
     * @param rect Pick rectangle of object coordinates.
     * @param aPenWidth the current pen width.
     */
    protected boolean pickStroke(Rectangle2D aRect, double aPenWidth) {
        if (penPaint == null)
            return false;

        double px = (aRect.getX() + (0.5 * aRect.getWidth()));
        double py = (aRect.getY() + (0.5 * aRect.getHeight()));
        double squareDist;
        double minSquareDist = ((0.5 * aPenWidth) + (0.5 * ((0.5 * aRect.getWidth()) + (0.5 * aRect.getHeight()))));

        boolean isFirstSegment = true;
        double firstX = 0;
        double firstY = 0;
        double currentX = 0;
        double currentY = 0;
        double lastX = 0;
        double lastY = 0;

        double[] cs = new double[6];

        AffineTransform aTransform = null;

        if (absPenWidth) {
            aTransform = getStrokeTransformForAbsPenWidth(aPenWidth);
        }

        FlatteningPathIterator i = new FlatteningPathIterator(getShape().getPathIterator(aTransform), 1);
        while (!i.isDone()) {
            switch (i.currentSegment(cs)) {
                case PathIterator.SEG_LINETO: {
                    lastX = currentX;
                    lastY = currentY;
                    currentX = cs[0];
                    currentY = cs[1];
                    squareDist = Line2D.ptSegDist(lastX, lastY, currentX, currentY, px, py);
                    if (squareDist <= minSquareDist) {
                        return true;
                    }
                    break;
                }
                case PathIterator.SEG_MOVETO: {
                    currentX = cs[0];
                    currentY = cs[1];
                    break;
                }
                case PathIterator.SEG_CLOSE: {
                    lastX = currentX;
                    lastY = currentY;
                    currentX = firstX;
                    currentY = firstY;
                    squareDist = Line2D.ptSegDist(lastX, lastY, currentX, currentY, px, py);
                    if (squareDist <= minSquareDist) {
                        return true;
                    }
                    break;
                }
            }

            if (isFirstSegment) {
                firstX = cs[0];
                firstY = cs[1];
                isFirstSegment = false;
            }

            i.next();
        }
        return false;
    }

    /**
     * Paints this object.
     * <p>
     * The transform, clip, and composite will be set appropriately when this object
     * is rendered.  It is up to this object to restore the transform, clip, and composite of
     * the Graphics2D if this node changes any of them. However, the color, font, and stroke are
     * unspecified by Jazz.  This object should set those things if they are used, but
     * they do not need to be restored.
     *
     * @param <code>renderContext</code> The graphics context to paint into.
     */
    public void render(ZRenderContext renderContext) {
        Graphics2D g2 = renderContext.getGraphics2D();

        if (fillPaint != null) {
            g2.setPaint(fillPaint);
            g2.fill(getShape());
        }
        if (penPaint != null) {
            if (absPenWidth) {
                double pw = penWidth / renderContext.getCompositeMagnification();

                AffineTransform saveTransform = g2.getTransform();
                AffineTransform newTransform = getStrokeTransformForAbsPenWidth(pw);

                double scale = newTransform.getScaleX();

                // If the abs line is growing so much that its overtaking the bounds
                // of the object then just fill the bounds with the line paint.
                if (scale <= 0) {
                    g2.setPaint(penPaint);
                    g2.fill(getBoundsReference());
                    return;
                }

                pw *= 1 / scale;
                g2.transform(newTransform);

                if (stroke != null) {
                    stroke = new BasicStroke((float)pw, stroke.getEndCap(), stroke.getLineJoin());
                } else {
                    stroke = new BasicStroke((float)pw, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
                }

                g2.setStroke(stroke);
                g2.setPaint(penPaint);
                g2.draw(getShape());

                g2.setTransform(saveTransform);
            } else {
                g2.setStroke(stroke);
                g2.setPaint(penPaint);
                g2.draw(getShape());
            }
        }
    }

    /**
     * This method returns a transform used for the implementation of abs pen width.
     */
    protected AffineTransform getStrokeTransformForAbsPenWidth(double aPenWidth) {
        double scale = (bounds.getWidth()-aPenWidth)/bounds.getWidth();
        Point2D center = bounds.getCenter2D();

        sharedTempTransform.setToIdentity();
        sharedTempTransform.translate(center.getX(), center.getY());
        sharedTempTransform.scale(scale, scale);
        sharedTempTransform.translate(-center.getX(), -center.getY());

        return sharedTempTransform;
    }

    /**
     * Notifies this object that it has changed and that it should update
     * its notion of its bounding box.  Note that this should not be called
     * directly.  Instead, it is called by <code>updateBounds</code> when needed.
     */
    protected void computeBounds() {
        bounds.reset();
        Rectangle2D shapeBounds = getFrame();

        double aPenWidth = getPenWidth();
        double aHalfPenWidth = aPenWidth * 0.5;

                            // Expand the bounds to accomodate the pen width
        bounds.setRect(shapeBounds.getX() - aHalfPenWidth, shapeBounds.getY() - aHalfPenWidth,
                       shapeBounds.getWidth() + aPenWidth, shapeBounds.getHeight() + aPenWidth);
    }


}
/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import edu.umd.cs.jazz.*;

/**
 * <b>ZUtil</b> provides some generic, useful routines.
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

public class ZUtil implements Serializable {

    private static double[] scratchDoubleArray = new double[8];

    /**
     * Returns a BufferedImage of <code>thumbnailSize</code> with
     * the <code>node</code> rendered into the image. The node is scaled to fit
     * within the image without changing its aspect ratio.
     * <p>
     * NOTE: ZSceneGraphObjects that access the ZDrawingSurface or ZCamera in their
     * render method without checking for null values will throw exceptions when
     * passed to this method.
     * <p>
     * Contributed by Philippe Converset.
     * <p>
     * @param node The node to render.
     * @param thumbnailSize The size of the resulting BufferedImage.
     * @return BufferedImage The image with the node rendered into it.
     */
    static public BufferedImage createThumbnailImage(ZNode node, Dimension thumbnailSize) {
        GraphicsConfiguration graphicsConfiguration;
        BufferedImage image;
        Graphics2D g2;
        ZBounds nodeBounds;
        ZRenderContext context;
        double translateX,translateY,scale;

        graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        image = (BufferedImage)graphicsConfiguration.createCompatibleImage((int)thumbnailSize.getWidth(), (int)thumbnailSize.getHeight(), Transparency.TRANSLUCENT);
        g2 = image.createGraphics();

        nodeBounds = node.getGlobalBounds();

        // Compute the scale and translate values in order to render
        // the node centered in the image without altering the aspect ratio.
        if(thumbnailSize.getWidth()/nodeBounds.getWidth()<thumbnailSize.getHeight()/nodeBounds.getHeight()) {
            scale = thumbnailSize.getWidth()/nodeBounds.getWidth();
            translateX = - nodeBounds.getX();
            translateY = (thumbnailSize.getHeight()/scale - nodeBounds.getHeight())/2.0 - nodeBounds.getY();
        } else {
            scale = thumbnailSize.getHeight()/nodeBounds.getHeight();
            translateX = (thumbnailSize.getWidth()/scale - nodeBounds.getWidth())/2.0 - nodeBounds.getX();
            translateY = - nodeBounds.getY();
        }

        g2.scale(scale,scale);
        g2.translate(translateX,translateY);
        context = new ZRenderContext(g2,nodeBounds,null,ZDrawingSurface.RENDER_QUALITY_HIGH);
        node.render(context);

        return image;
    }


    /**
     * Tests if the specified line segment intersects the interior of the specified
     * <code>Rectangle2D</code>.
     * @param x1,&nbsp;y1 the first endpoint of the specified
     * line segment
     * @param x2,&nbsp;y2 the second endpoint of the specified
     * line segment
     * @return <code>true</code> if the specified line segment intersects
     * the interior of this <code>Rectangle2D</code>; <code>false</code>
     * otherwise.
     */
    static public boolean rectIntersectsLine(Rectangle2D rect, double x1, double y1, double x2, double y2) {
        int out1 = outcode(rect, x1, y1);
        int out2 = outcode(rect, x2, y2);

        if ((out1 & out2) != 0) {
                   // Both are to one side of the rectangle
            return false;
        }
        if ((out1 == 0) || (out2 == 0)) {
                // One is inside, line intersects
            return true;
        }

                                // Both are outside, interpolate to find if intersecting
        if ((out1 & (OUT_LEFT | OUT_RIGHT)) != 0) {
            double x = rect.getX();
            if ((out1 & OUT_RIGHT) != 0) {
                x += rect.getWidth();
            }
            y1 = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
            x1 = x;
        }
        if ((out1 & (OUT_TOP | OUT_BOTTOM)) != 0) {
            double y = rect.getY();
            if ((out1 & OUT_BOTTOM) != 0) {
                y += rect.getHeight();
            }
            x1 = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
            y1 = y;
        }

        out1 = outcode(rect, x1, y1);
        out2 = outcode(rect, x2, y2);

        if ((out1 & out2) != 0) {
                   // Both are to one side of the rectangle
            return false;
        }

        return true;

    }

    private static final int OUT_LEFT = 1;
    private static final int OUT_TOP = 2;
    private static final int OUT_RIGHT = 4;
    private static final int OUT_BOTTOM = 8;
    private static int outcode(Rectangle2D rect, double x, double y) {
        int out = 0;
        double rx = rect.getX();
        double ry = rect.getY();
        double rw = rect.getWidth();
        double rh = rect.getHeight();

        if (rw <= 0) {
            out |= OUT_LEFT | OUT_RIGHT;
        } else if (x < rx) {
            out |= OUT_LEFT;
        } else if (x > rx + rw) {
            out |= OUT_RIGHT;
        }
        if (rh <= 0) {
            out |= OUT_TOP | OUT_BOTTOM;
        } else if (y < ry) {
            out |= OUT_TOP;
        } else if (y > ry + rh) {
            out |= OUT_BOTTOM;
        }

        return out;
    }

    /**
     * Determine if the specified rectangle intersects the specified polyline.
     * @param rect The rectangle that is being tested for intersection
     * @param xp The array of X-coordinates that determines the polyline
     * @param yp The array of Y-coordinates that determines the polyline
     * @param penWidth The width of the polyline
     * @return true if the rectangle intersects the polyline.
     */
    static public boolean rectIntersectsPolyline(Rectangle2D rect, double[] xp, double[] yp, double penWidth) {
        boolean picked = false;
        double width = ((0.5 * penWidth) + (0.5 * ((0.5 * rect.getWidth()) + (0.5 * rect.getHeight()))));
        double px = (rect.getX() + (0.5 * rect.getWidth()));
        double py = (rect.getY() + (0.5 * rect.getHeight()));
        double squareDist;
        double minSquareDist;
        int i;
        int np = xp.length;

        if (np > 0) {
            if (np == 1) {
                minSquareDist = Line2D.ptSegDistSq(xp[0], yp[0], xp[0], yp[0], px, py);
            } else {
                minSquareDist = Line2D.ptSegDistSq(xp[0], yp[0], xp[1], yp[1], px, py);
            }
            for (i=1; i<np-1; i++) {
                squareDist = Line2D.ptSegDistSq(xp[i], yp[i], xp[i+1], yp[i+1], px, py);
                if (squareDist < minSquareDist) {
                    minSquareDist = squareDist;
                }
            }
            if (minSquareDist <= (width*width)) {
                picked = true;
            }
        }

        return picked;
    }

    /**
     * Returns the angle in radians between point a and point b from point pt,
     * that is the angle between a-pt-b.
     * @param pt,a,b The points that specify the angle
     * @return the angle
     */
    static public double angleBetweenPoints(Point2D pt, Point2D a, Point2D b) {
        return angleBetweenPoints(pt.getX(), pt.getY(), a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * Returns the angle in radians between point a and point b from point pt,
     * that is the angle between a-pt-b.
     * @param x,y The central point
     * @param ax,ay The point a
     * @param bx,by The point b
     * @return the angle
     */
    static public double angleBetweenPoints(double x, double y, double ax, double ay, double bx, double by) {
        Point2D v1, v2;
        double z, s;
        double theta;
        double t1, t2;

        // vector from pt to a
        v1 = new Point2D.Double(ax - x, ay - y);

        // vector from pt to b
        v2 = new Point2D.Double(bx - x, by - y);

        z = (v1.getX() * v2.getY()) - (v1.getY() * v2.getX());

        // s is the sign of z
        if (z >= 0) {
            s = 1;
        }
        else {
            s = -1;
        }

        // so we can calculate the angle from the cosine through the dot product
        t1 = Math.sqrt((x-ax)*(x-ax) + (y-ay)*(y-ay)) * Math.sqrt((x-bx)*(x-bx) + (y-by)*(y-by));

        if (t1 == 0.0) {
            theta = 0.0;
        } else {
            // Calculate angle between vectors by doing dot product and dividing by
            // the product of the length of the vectors.
            t2 = (v1.getX() * v2.getX() + v1.getY() * v2.getY()) / t1;
            if ((t2 < -1) || (t2 > 1)) {
                theta = 0.0;
            }
            else {
                theta = s * java.lang.Math.acos(t2);
            }
        }

        return theta;
    }

    /**
     * Determines if any part of the rectangle is inside this polygon.
     * @param rect The rectangle being tested for intersecting this polygon
     * @return true if the rectangle intersects the polygon
     */
    static public boolean intersectsPolygon(Rectangle2D rect, double[] xp, double[] yp) {
        boolean inside = false;
        int np = xp.length;

        // we check each vertix of the rectangle to see if it's inside the polygon

        // check upper left corner
        inside = isInsidePolygon(rect.getX(), rect.getY(), np, xp, yp);
        if (!inside) {
            // check upper right corner
            inside = isInsidePolygon(rect.getX() + rect.getWidth(), rect.getY(), np, xp, yp);
            if (!inside) {
                // check lower right corner
                inside = isInsidePolygon(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), np, xp, yp);
                if (!inside) {
                    // check lower left corner
                    inside = isInsidePolygon(rect.getX(), rect.getY() + rect.getHeight(), np, xp, yp);
                    if (!inside) {
                        // if none of the corners are inside of the polygon
                        // we check to see if any of the edges go through the rectangle
                        int i = 0;
                        while (!inside && (i < (np - 1))) {
                            // for each edge
                            inside = ZUtil.rectIntersectsLine(rect, xp[i], yp[i], xp[i + 1], yp[i + 1]);

                            i++;
                        }
                        if (!inside) {
                            inside = ZUtil.rectIntersectsLine(rect, xp[np - 1], yp[np - 1], xp[0], yp[0]);
                        }
                    }
                }
            }
        }

        return inside;
    }

    /**
     * Determines if the point is inside the polygon.
     * @param x,y The point being tested for containment within a polygon
     * @return true if the point is inside the polygon
     */
    static public boolean isInsidePolygon(double x, double y, int np, double[] xp, double[] yp) {
        int i;
        double angle = 0.0;
        boolean inside = false;

        for (i = 0; i < (np - 1); i++) {
            angle += ZUtil.angleBetweenPoints(x, y, xp[i], yp[i], xp[i+1], yp[i+1]);
        }
        angle += ZUtil.angleBetweenPoints(x, y, xp[np-1], yp[np-1], xp[0], yp[0]);

                                // Allow for a bit of rounding
                                // Ideally, angle should be 2*pi.
        if (java.lang.Math.abs(angle) > 6.2) {
            inside = true;
        } else {
            inside = false;
        }

        return inside;
    }

    /**
     * Determines if a rectangle is inside a polygon.
     * Warning: this code only works for simple convex polygons.
     * @param rect The rectangle being tested for containment within a polygon
     * @return true if the rectangle is inside the polygon
     */
    static public boolean isInsidePolygon(Rectangle2D rect, int np, double[] xp, double[] yp) {
        boolean inside;
        double x = rect.getX();
        double y = rect.getY();
        double width = rect.getWidth();
        double height = rect.getHeight();

        if (isInsidePolygon(x, y, np, xp, yp) &&
            isInsidePolygon(x+width, y, np, xp, yp) &&
            isInsidePolygon(x, y+height, np, xp, yp) &&
            isInsidePolygon(x+width, y+height, np, xp, yp)) {
            inside = true;
        } else {
            inside = false;
        }

        return inside;
    }

    /**
     * Modify the dimension paramater by applying the the given transform.
     * @return dz The change in scale from applying aTransform.
     * @param aDimension the dimension to which the transform will be applied.
     * @param aTransform the transform that will be applied to the dimension.
     */
    public static double transformDimension(Dimension2D aDimension, AffineTransform aTransform) {
        double[] pts = scratchDoubleArray;
        pts[0] = aDimension.getWidth();
        pts[1] = aDimension.getHeight();
        aTransform.deltaTransform(pts, 0, pts, 0, 1);
        aDimension.setSize(pts[0], pts[1]);
        return Math.max(aTransform.getScaleX(), aTransform.getScaleX());
    }


    /**
     * Modify the dimension paramater by applying the inverse of the given transform.
     * @return dz The change in scale from applying aTransform.
     * @param aDimension the dimension to which the inverse transform will be applied.
     * @param aTransform the transform who's inverse will be applied to the dimension.
     */
    public static double inverseTransformDimension(Dimension2D aDimension, AffineTransform aTransform) throws NoninvertibleTransformException {
        double width = aDimension.getWidth();
        double height = aDimension.getHeight();
        double m00 = aTransform.getScaleX();
        double m11 = aTransform.getScaleY();
        double m01 = aTransform.getShearX();
        double m10 = aTransform.getShearY();
        double det = m00 * m11 - m01 * m10;

        if (Math.abs(det) <= Double.MIN_VALUE) {
            throw new NoninvertibleTransformException("Determinant is "+ det);
        }
        aDimension.setSize((width * m11 - height * m01) / det, (height * m00 - width * m10) / det);

        return 1 / Math.max(aTransform.getScaleX(), aTransform.getScaleX());
    }

    /**
     * Modify the aRectangle paramater by applying the inverse of the given transform.
     * This method works without creating a new transform object so it should be more
     * efficient then calling aTransform.createInverseTransform() and applying that result
     * to the rectangle.
     * @return dz The change in scale from applying aTransform.
     * @param aRectangle the rectangle to which the inverse transform will be applied.
     * @param aTransform the transform who's inverse will be applied to the size.
     */
    public static double inverseTransformRectangle(Rectangle2D aRectangle, AffineTransform aTransform) throws NoninvertibleTransformException {
        double[] pts = getRectPointsAsArray(aRectangle);
        aTransform.inverseTransform(pts, 0, pts, 0, 4);
        setRectFromPointsArray(aRectangle, pts);
        return 1 / Math.max(aTransform.getScaleX(), aTransform.getScaleX());
    }

    private static double[] getRectPointsAsArray(Rectangle2D aRectangle) {
        double[] pts = scratchDoubleArray;
        pts[0] = aRectangle.getX();
        pts[1] = aRectangle.getY();
        pts[2] = aRectangle.getX() + aRectangle.getWidth();
        pts[3] = aRectangle.getY();
        pts[4] = aRectangle.getX() + aRectangle.getWidth();
        pts[5] = aRectangle.getY() + aRectangle.getHeight();
        pts[6] = aRectangle.getX();
        pts[7] = aRectangle.getY() + aRectangle.getHeight();
        return pts;
    }

    private static void setRectFromPointsArray(Rectangle2D aRectangle, double[] pts) {
        double minX = pts[0];
        double minY = pts[1];
        double maxX = pts[0];
        double maxY = pts[1];
        int i;
        for (i=1; i<4; i++) {
            if (pts[2*i] < minX) {
                minX = pts[2*i];
            }
            if (pts[2*i+1] < minY) {
                minY = pts[2*i+1];
            }
            if (pts[2*i] > maxX) {
                maxX = pts[2*i];
            }
            if (pts[2*i+1] > maxY) {
                maxY = pts[2*i+1];
            }
        }
        aRectangle.setRect(minX, minY, maxX - minX, maxY - minY);
    }
}
/**
 * Copyright (C) 2000-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.io.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.Vector;
import java.util.Iterator;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZCoordList</b> is a Shape implemenation that uses a list of coordinates as its
 * underlying model. This class is use by ZPolyline and ZPolygon to manage their vertices.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Jesse Grosjean
 */
class ZCoordListShape implements Shape, Cloneable {

    public static int DEFAULT_WINDING_RULE = PathIterator.WIND_EVEN_ODD;

    protected int coordCount = 0;
    protected double xCoords[] = new double[2];
    protected double yCoords[] = new double[2];
    protected boolean isClosed = false;
    protected int windingRule = DEFAULT_WINDING_RULE;

    /**
     * Constructs a new ZCoordList with no points.
     */
    public ZCoordListShape() {
        xCoords = new double[2];
        yCoords = new double[2];
        coordCount = 0;
    }

    /**
     * Constructs a new ZCoordList with no points.
     */
    public ZCoordListShape(boolean isClosed) {
        this();
        this.isClosed = isClosed;
    }

    /**
     * Determine if this coordinate list is closed.  A closed coordinate list means
     * that the last point is always the same as the first point in the path that
     * is used for painting.  The actual coordinates are not affected by this.
     * @return true if the coodinate list is closed, false otherwise.
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Specify that this coordinate list is closed.  A closed coordinate list means
     * that the last point is always the same as the first point in the path that
     * is used for painting.  The actual coordinates are not affected by this.
     * @param closed true if the coodinate list is closed, false otherwise.
     */
    public void setClosed(boolean aBoolean) {
        isClosed = aBoolean;
    }

    /**
     * Return the winding rule used to fill this shape. See PathIterator for what
     * a winding rule is.
     */
    public int getWindingRule() {
        return windingRule;
    }

    /**
     * Set the winding rule used to fill this shape. See PathIterator for what
     * a winding rule is.
     */
    public void setWindingRule(int aWindingRule) {
        windingRule = aWindingRule;
    }

    /**
     * Get the number of points in this coordinate list.
     * @return the number of points in this coordinate list
     */
    public int getNumberPoints() {
        return getVertexCount();
    }

    /**
     * Clones this object.
     */
    public Object clone() {
        try {
            trimToSize();
            ZCoordListShape copy = (ZCoordListShape) super.clone();
            copy.xCoords = (double[]) xCoords.clone();
            copy.yCoords = (double[]) yCoords.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    /**
     * Tests if the specified coordinates are inside the boundary of the
     * <code>Shape</code>.
     * @param x,&nbsp;y the specified coordinates
     * @return <code>true</code> if the specified coordinates are inside
     *         the <code>Shape</code> boundary; <code>false</code>
     *         otherwise.
     */
    public boolean contains(double x, double y) {
        if (!isClosed) return false;
        return ZUtil.isInsidePolygon(x, y, getVertexCount(), xCoords, yCoords);
    }

    /**
     * Tests if the interior of the <code>Shape</code> entirely contains
     * the specified rectangular area.  All coordinates that lie inside
     * the rectangular area must lie within the <code>Shape</code> for the
     * entire rectanglar area to be considered contained within the
     * <code>Shape</code>.
     * <p>
     * This method might conservatively return <code>false</code> when:
     * <ul>
     * <li>
     * the <code>intersect</code> method returns <code>true</code> and
     * <li>
     * the calculations to determine whether or not the
     * <code>Shape</code> entirely contains the rectangular area are
     * prohibitively expensive.
     * </ul>
     * This means that this method might return <code>false</code> even
     * though the <code>Shape</code> contains the rectangular area.
     * The <code>Area</code> class can be used to perform more accurate
     * computations of geometric intersection for any <code>Shape</code>
     * object if a more precise answer is required.
     * @param x,&nbsp;y the coordinates of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return <code>true</code> if the interior of the <code>Shape</code>
     *      entirely contains the specified rectangular area;
     *      <code>false</code> otherwise or, if the <code>Shape</code>
     *      contains the rectangular area and the
     *      <code>intersects</code> method returns <code>true</code>
     *      and the containment calculations would be too expensive to
     *      perform.
     * @see java.awt.geom.Area
     * @see #intersects
     */
    public boolean contains(double x1, double y1, double w, double h) {
        double x2 = x1 + w;
        double y2 = y1 + h;
        return contains(x1,y1) && contains (x1,y2)
            && contains(x2,y1) && contains(x2,y2);
    }

    /**
     * Tests if a specified {@link Point2D} is inside the boundary
     * of the <code>Shape</code>.
     * @param p a specified <code>Point2D</code>
     * @return <code>true</code> if the specified <code>Point2D</code> is
     *          inside the boundary of the <code>Shape</code>;
     *      <code>false</code> otherwise.
     */
    public boolean contains(Point2D p) {
        return contains(p.getX(), p.getY());
    }

    /**
     * Tests if the interior of the <code>Shape</code> entirely contains the
     * specified <code>Rectangle2D</code>.
     * This method might conservatively return <code>false</code> when:
     * <ul>
     * <li>
     * the <code>intersect</code> method returns <code>true</code> and
     * <li>
     * the calculations to determine whether or not the
     * <code>Shape</code> entirely contains the <code>Rectangle2D</code>
     * are prohibitively expensive.
     * </ul>
     * This means that this method might return <code>false</code> even
     * though the <code>Shape</code> contains the
     * <code>Rectangle2D</code>.
     * The <code>Area</code> class can be used to perform more accurate
     * computations of geometric intersection for any <code>Shape</code>
     * object if a more precise answer is required.
     * @param r The specified <code>Rectangle2D</code>
     * @return <code>true</code> if the interior of the <code>Shape</code>
     *          entirely contains the <code>Rectangle2D</code>;
     *          <code>false</code> otherwise or, if the <code>Shape</code>
     *          contains the <code>Rectangle2D</code> and the
     *          <code>intersects</code> method returns <code>true</code>
     *          and the containment calculations would be too expensive to
     *          perform.
     * @see #contains(double, double, double, double)
     */
    public boolean contains(Rectangle2D r) {
        return ZUtil.isInsidePolygon(r, getVertexCount(), xCoords, yCoords);
    }

    /**
     * Returns an integer {@link Rectangle} that completely encloses the
     * <code>Shape</code>.  Note that there is no guarantee that the
     * returned <code>Rectangle</code> is the smallest bounding box that
     * encloses the <code>Shape</code>, only that the <code>Shape</code>
     * lies entirely within the indicated  <code>Rectangle</code>.  The
     * returned <code>Rectangle</code> might also fail to completely
     * enclose the <code>Shape</code> if the <code>Shape</code> overflows
     * the limited range of the integer data type.  The
     * <code>getBounds2D</code> method generally returns a
     * tighter bounding box due to its greater flexibility in
     * representation.
     * @return an integer <code>Rectangle</code> that completely encloses
     *                 the <code>Shape</code>.
     * @see #getBounds2D
     */
    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    /**
     * Returns a high precision and more accurate bounding box of
     * the <code>Shape</code> than the <code>getBounds</code> method.
     * Note that there is no guarantee that the returned
     * {@link Rectangle2D} is the smallest bounding box that encloses
     * the <code>Shape</code>, only that the <code>Shape</code> lies
     * entirely within the indicated <code>Rectangle2D</code>.  The
     * bounding box returned by this method is usually tighter than that
     * returned by the <code>getBounds</code> method and never fails due
     * to overflow problems since the return value can be an instance of
     * the <code>Rectangle2D</code> that uses double precision values to
     * store the dimensions.
     * @return an instance of <code>Rectangle2D</code> that is a
     *                 high-precision bounding box of the <code>Shape</code>.
     * @see #getBounds
     */
    public Rectangle2D getBounds2D() {
        if (coordCount <= 0) {
            return new Rectangle2D.Double();
        }
        double x1 = xCoords[0];
        double y1 = yCoords[0];
        double x2 = x1;
        double y2 = y1;
        for (int i = 1; i < getVertexCount(); i++) {
            if (xCoords[i] < x1) {
                x1 = xCoords[i];
            } else if (xCoords[i] > x2) {
                x2 = xCoords[i];
            }

            if (yCoords[i] < y1) {
                y1 = yCoords[i];
            } else if (yCoords[i] > y2) {
                y2 = yCoords[i];
            }
        }
        return new Rectangle2D.Double(x1,y1,x2-x1,y2-y1);
    }

    /**
     * Returns an iterator object that iterates along the
     * <code>Shape</code> boundary and provides access to the geometry of the
     * <code>Shape</code> outline.  If an optional {@link AffineTransform}
     * is specified, the coordinates returned in the iteration are
     * transformed accordingly.
     * <p>
     * Each call to this method returns a fresh <code>PathIterator</code>
     * object that traverses the geometry of the <code>Shape</code> object
     * independently from any other <code>PathIterator</code> objects in use
     * at the same time.
     * <p>
     * It is recommended, but not guaranteed, that objects
     * implementing the <code>Shape</code> interface isolate iterations
     * that are in process from any changes that might occur to the original
     * object's geometry during such iterations.
     * <p>
     * Before using a particular implementation of the <code>Shape</code>
     * interface in more than one thread simultaneously, refer to its
     * documentation to verify that it guarantees that iterations are isolated
     * from modifications.
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *      coordinates as they are returned in the iteration, or
     *      <code>null</code> if untransformed coordinates are desired
     * @return a new <code>PathIterator</code> object, which independently
     *      traverses the geometry of the <code>Shape</code>.
     */
    public PathIterator getPathIterator(AffineTransform at) {
                                                // Bug fix, an empty list that is closed throws exception.
        return new ZCoordListIterator(this, at, isClosed && getNumberPoints() > 0);
    }

    /**
     * Returns an iterator object that iterates along the <code>Shape</code>
     * boundary and provides access to a flattened view of the
     * <code>Shape</code> outline geometry.
     * <p>
     * Only SEG_MOVETO, SEG_LINETO, and SEG_CLOSE point types are
     * returned by the iterator.
     * <p>
     * If an optional <code>AffineTransform</code> is specified,
     * the coordinates returned in the iteration are transformed
     * accordingly.
     * <p>
     * The amount of subdivision of the curved segments is controlled
     * by the <code>flatness</code> parameter, which specifies the
     * maximum distance that any point on the unflattened transformed
     * curve can deviate from the returned flattened path segments.
     * Note that a limit on the accuracy of the flattened path might be
     * silently imposed, causing very small flattening parameters to be
     * treated as larger values.  This limit, if there is one, is
     * defined by the particular implementation that is used.
     * <p>
     * Each call to this method returns a fresh <code>PathIterator</code>
     * object that traverses the <code>Shape</code> object geometry
     * independently from any other <code>PathIterator</code> objects in use at
     * the same time.
     * <p>
     * It is recommended, but not guaranteed, that objects
     * implementing the <code>Shape</code> interface isolate iterations
     * that are in process from any changes that might occur to the original
     * object's geometry during such iterations.
     * <p>
     * Before using a particular implementation of this interface in more
     * than one thread simultaneously, refer to its documentation to
     * verify that it guarantees that iterations are isolated from
     * modifications.
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *      coordinates as they are returned in the iteration, or
     *      <code>null</code> if untransformed coordinates are desired
     * @param flatness the maximum distance that the line segments used to
     *          approximate the curved segments are allowed to deviate
     *          from any point on the original curve
     * @return a new <code>PathIterator</code> that independently traverses
     *      the <code>Shape</code> geometry.
    */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return getPathIterator(at);
    }

    /**
     * Return the number of vertices stored in this list.
     */
    public int getVertexCount () {
        return coordCount;
    }

    /**
     * Return the X-coordinate located at the given index.
     */
    public double getX(int index) {
        return xCoords[index];
    }

    /**
     * Return the Y-coordinate located at the given index.
     */
    public double getY(int index) {
        return yCoords[index];
    }

    /**
     * Inserts the given point at the given index.
     */
    public void insertPoint(int index, double x, double y) {
        if (index > getVertexCount()) {
            index = getVertexCount();
        }

        lineTo(x, y); // Create extra space.

        for (int i = getVertexCount() - 1; i > index; i--) {
            setX(i, getX(i-1));
            setY(i, getY(i-1));
        }
        setX(index, x);
        setY(index, y);
    }

    /**
     * Tests if the interior of the <code>Shape</code> intersects the
     * interior of a specified rectangular area.
     * The rectangular area is considered to intersect the <code>Shape</code>
     * if any point is contained in both the interior of the
     * <code>Shape</code> and the specified rectangular area.
     * <p>
     * This method might conservatively return <code>true</code> when:
     * <ul>
     * <li>
     * there is a high probability that the rectangular area and the
     * <code>Shape</code> intersect, but
     * <li>
     * the calculations to accurately determine this intersection
     * are prohibitively expensive.
     * </ul>
     * This means that this method might return <code>true</code> even
     * though the rectangular area does not intersect the <code>Shape</code>.
     * The {@link java.awt.geom.Area Area} class can be used to perform
     * more accurate computations of geometric intersection for any
     * <code>Shape</code> object if a more precise answer is required.
     * @param x,&nbsp;y the coordinates of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return <code>true</code> if the interior of the <code>Shape</code> and
     *      the interior of the rectangular area intersect, or are
     *      both highly likely to intersect and intersection calculations
     *      would be too expensive to perform; <code>false</code> otherwise.
     * @see java.awt.geom.Area
     */
    public boolean intersects(double x1, double y1, double w, double h) {
        if (isClosed) {
            double x2 = x1 + w;
            double y2 = y1 + h;
            return contains(x1, y1) || contains(x1, y2)
                || contains(x2, y1) || contains(x2, y2);
        } else {
            return intersects(new Rectangle2D.Double(x1, y1, w, h));
        }
    }

    /**
     * Tests if the interior of the <code>Shape</code> intersects the
     * interior of a specified <code>Rectangle2D</code>.
     * This method might conservatively return <code>true</code> when:
     * <ul>
     * <li>
     * there is a high probability that the <code>Rectangle2D</code> and the
     * <code>Shape</code> intersect, but
     * <li>
     * the calculations to accurately determine this intersection
     * are prohibitively expensive.
     * </ul>
     * This means that this method might return <code>true</code> even
     * though the <code>Rectangle2D</code> does not intersect the
     * <code>Shape</code>.
     * @param r the specified <code>Rectangle2D</code>
     * @return <code>true</code> if the interior of the <code>Shape</code> and
     *      the interior of the specified <code>Rectangle2D</code>
     *      intersect, or are both highly likely to intersect and intersection
     *      calculations would be too expensive to perform; <code>false</code>
     *      otherwise.
     * @see #intersects(double, double, double, double)
     */
    public boolean intersects (Rectangle2D r) {
        if (isClosed) {
            return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        } else {
            if (coordCount == 0) {
                return false;
            } else if (coordCount == 1) {
                return r.contains(getX(0), getY(0));
            }
            int count = getVertexCount();
            double x1;
            double y1;
            double x2 = getX(0);
            double y2 = getY(0);
            for (int i = 1; i < count; i++) {
                x1 = x2;
                y1 = y2;
                x2 = getX(i);
                y2 = getY(i);
                if (r.intersectsLine(x1,y1,x2,y2)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Adds a new pair of points to the end of hte list.
     */
    public void lineTo(double x, double y) {
        if (coordCount == xCoords.length) {
            double temp[] = new double[coordCount*2];
            System.arraycopy(xCoords, 0, temp, 0, coordCount);
            xCoords = temp;
            temp = new double[coordCount*2];
            System.arraycopy(yCoords, 0, temp, 0, coordCount);
            yCoords = temp;
        }
        xCoords[coordCount] = x;
        yCoords[coordCount] = y;
        coordCount++;
    }

    /**
     * Move the starting coords to the given position.
     */
    public void moveTo(double x, double y) {
        if (coordCount > 0) {
            throw new UnsupportedOperationException(
                    "This coordlist already has vertices");
        }
        xCoords[0] = x;
        yCoords[0] = y;
        coordCount = 1;
    }

    /**
     * Reset the list to empty.
     */
    public void reset() {
        coordCount = 0;
    }

    /**
     * Set the given X-coordinate to the given index.
     */
    public void setX(int index, double x) {
        xCoords[index] = x;
    }

    /**
     * Set the given Y-coordinate to the given index.
     */
    public void setY(int index, double y) {
        yCoords[index] = y;
    }

    /**
     * Get an array of the X coordinates of the points in this coordinate list.
     * These are the original coordinates of this list, and must not be
     * modified by the caller.
     * @return Array of X coordinates of points.
     */
    public double[]getXCoords() {
        trimToSize();
        return xCoords;
    }

    /**
     * Get an array of the Y coordinates of the points in this coordinate list.
     * These are the original coordinates of this list, and must not be
     * modified by the caller.
     * @return Array of Y coordinates of points.
     */
    public double[]getYCoords() {
        trimToSize();
        return yCoords;
    }

    public void trimToSize() {
        double[] newXP = new double[coordCount];
        double[] newYP = new double[coordCount];
        for (int i=0; i<coordCount; i++) {
            newXP[i] = xCoords[i];
            newYP[i] = yCoords[i];
        }
        xCoords = newXP;
        yCoords = newYP;
    }

    /**
     * This is the path iterator used to iterate over the ZCoordList. If the ZCoordList
     * is marked as closed then this iterator will return a PathIterator.SEG_CLOSE as its
     * last segment.
     */
    protected static class ZCoordListIterator implements PathIterator {

        private ZCoordListShape coordList;
        private AffineTransform transform;
        private int index = 0;
        private boolean isClosed;

        public ZCoordListIterator (ZCoordListShape pl, AffineTransform at, boolean isClosed) {
            transform = at;
            index = 0;
            coordList = pl;
            this.isClosed = isClosed;
        }

        public int currentSegment (double coords[]) {
            int result = -1;

            if (index == coordList.getVertexCount()) {
                return PathIterator.SEG_CLOSE;
            } else {
                coords[0] = coordList.getX(index);
                coords[1] = coordList.getY(index);
                if (index == 0) {
                    result = PathIterator.SEG_MOVETO;
                } else {
                    result = PathIterator.SEG_LINETO;
                }
            }

            if (transform != null) {
                transform.transform(coords, 0, coords, 0, 1);
            }

            return result;
        }

        public int currentSegment (float coords[]) {
            int result = -1;

            if (index == coordList.getVertexCount()) {
                return PathIterator.SEG_CLOSE;
            } else {
                coords[0] = (float) coordList.getX(index);
                coords[1] = (float) coordList.getY(index);
                if (index == 0) {
                    result = PathIterator.SEG_MOVETO;
                } else {
                    result = PathIterator.SEG_LINETO;
                }
            }

            if (transform != null) {
                transform.transform(coords, 0, coords, 0, 1);
            }

            return result;
        }

        public int getWindingRule () {
            return coordList.getWindingRule();
        }

        public boolean isDone () {
            if (isClosed) {
                return index > coordList.getVertexCount();
            } else {
                return (index == coordList.getVertexCount());
            }
        }

        public void next () {
            index++;
        }
    }
}
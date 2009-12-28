/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
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
 * <b>ZCoordList</b> is an abstract visual component that stores a sequence
 * of coordinates, and the corresponding general path.  This is intended to
 * be sub-classed for specific objects that use coordinate lists.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Benjamin B. Bederson
 * @author  Jesse Grosjean
 */
public class ZCoordList extends ZShape {

    /**
     * The shape representing this coordlist.
     */
    protected transient ZCoordListShape coordListShape;

    /**
     * Constructs a new ZCoordList with no points.
     */
    public ZCoordList() {
    }
    
    public ZCoordList(ZCoordListShape coordListShape)
    {
    	this.coordListShape = coordListShape;
    }

    /**
     * Constructs a new ZCoordList with a single point.
     * @param <code>pt</code> Initial point
     */
    public ZCoordList(Point2D pt) {
        this(pt.getX(), pt.getY());
    }

    /**
     * Constructs a new ZCoordList with two points.
     * @param <code>pt1</code> First point
     * @param <code>pt2</code> Second point
     */
    public ZCoordList(Point2D pt1, Point2D pt2) {
        this(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
    }

    /**
     * Constructs a new ZCoordList with a single point.
     * @param <code>x,y</code> Initial point
     */
    public ZCoordList(double x, double y) {
        this();
        add(x, y);
    }

    /**
     * Constructs a new ZCoordList with a two points
     * @param <code>x,y</code> First point
     * @param <code>x,y</code> Second point
     */
    public ZCoordList(double x1, double y1, double x2, double y2) {
        this();
        add(x1, y1);
        add(x2, y2);
    }

    /**
     * Constructs a new ZCoordList.
     * The xp, yp parameters are stored within this coordinate list, so the caller
     * must not modify them after passing them in.
     * @param <code>xp</code> Array of X points
     * @param <code>yp</code> Array of Y points
     */
    public ZCoordList(double[] xp, double[] yp) {
        this();
        setCoords(xp, yp);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZCoordList newCoordList = (ZCoordList) super.duplicateObject();
        newCoordList.coordListShape = (ZCoordListShape) coordListShape.clone();
        return newCoordList;
    }

    //****************************************************************************
    //
    //          Get/Set and pairs
    //
    //***************************************************************************

    /**
     * Get the shape defined by the coords.
     */
    protected ZCoordListShape getCoordListShape() {
        if (coordListShape == null) {
            coordListShape = new ZCoordListShape();
        }
        return coordListShape;
    }

    /**
     * Determine if this coordinate list is closed.  A closed coordinate list means
     * that the last point is always the same as the first point in the path that
     * is used for painting.  The actual coordinates are not affected by this.
     * @return true if the coodinate list is closed, false otherwise.
     */
    public boolean isClosed() {
        return getCoordListShape().isClosed();
    }

    /**
     * Specify that this coordinate list is closed.  A closed coordinate list means
     * that the last point is always the same as the first point in the path that
     * is used for painting.  The actual coordinates are not affected by this.
     * @param closed true if the coodinate list is closed, false otherwise.
     */
    public void setClosed(boolean closed) {
        getCoordListShape().setClosed(closed);
        repaint();
    }

    //****************************************************************************
    //
    // Modify and retrieve the coordinates
    //
    //***************************************************************************

    /**
     * Add a point to the end of this coordinate list.
     * @param pt The new point
     */
    public void add(Point2D pt) {
        add(pt.getX(), pt.getY());
    }

    /**
     * Add a point to the end of this coordinate list.
     * @param x,y The new point
     */
    public void add(double x, double y) {
        if (getNumberPoints() == 0) {
            moveTo(x, y);
        } else {
            lineTo(x, y);
        }
    }

    /**
     * Add a point to the specified part of this coordinate list.
     * Specifying an index of 0 puts the point at the beginning of the list.
     * Specifying an index greater than the number of points in the coordinate list puts the point
     * at the end of the list of points.
     * @param pt The new point
     * @param index The index of the new point.
     */
    public void add(Point2D pt, int index) {
        add(pt.getX(), pt.getY(), index);
    }

    /**
     * Add a point to the specified part of this coordinate list.
     * Specifying an index of 0 puts the point at the beginning of the list.
     * Specifying an index greater than the number of points in the coordinate list puts the point
     * at the end of the list of points.
     * @param x,y The new point
     * @param index The index of the new point.
     */
    public void add(double x, double y, int index) {
        getCoordListShape().insertPoint(index, x, y);
        reshape();
    }

    /**
     * Get the number of points in this coordinate list.
     * @return the number of points in this coordinate list
     */
    public int getNumberPoints() {
        return getCoordListShape().getNumberPoints();
    }

    /**
     * Return the current shape.
     */
    public Shape getShape() {
        return getCoordListShape();
    }

    /**
     * Return the X-coord of the vertex located in the specified index.
     */
    public double getX(int index) {
        return getCoordListShape().getX(index);
    }

    /**
     * Return the Y-coord of the vertex located in the specified index.
     */
    public double getY(int index) {
        return getCoordListShape().getY(index);
    }

    /**
     * Get an array of the X coordinates of the points in this coordinate list.
     * These are the original coordinates of this list, and must not be
     * modified by the caller.
     * @return Array of X coordinates of points.
     */
    public double[] getXCoords() {
        return getCoordListShape().getXCoords();
    }

    /**
     * Get an array of the Y coordinates of the points in this coordinate list.
     * These are the original coordinates of this list, and must not be
     * modified by the caller.
     * @return Array of Y coordinates of points.
     */
    public double[] getYCoords() {
        return getCoordListShape().getYCoords();
    }

    /**
     * Return the append the given vertex onto the end of the list of points
     * that define this polygon.
     */
    public void lineTo(double x, double y) {
        getCoordListShape().lineTo(x, y);

        double p2 = 0.5 * getPenWidth();
        if (!bounds.contains(x - p2, y - p2) ||
            !bounds.contains(x + p2, y + p2)) {
                                // Need to expand bounds to accomodate point.
                                // Do it incrementally for efficiency instead of calling reshape()
            bounds.add(x - p2, y - p2);
            bounds.add(x + p2, y + p2);

            updateBounds();
        }
                                // Only refresh the portion of the shape that has changed - which is just
                                // the area between the current and previous point
        ZCoordListShape pl = getCoordListShape();
        if (pl.getVertexCount() > 2) {
            int previousIndex = getNumberPoints() - 2;
            ZBounds tmpBounds = new ZBounds();
            tmpBounds.add(x - p2, y - p2);
            tmpBounds.add(x + p2, y + p2);
            tmpBounds.add(getX(previousIndex), getY(previousIndex));

            repaint(tmpBounds);

            if (isClosed()) {
                reshape();
            }

        } else {
            reshape();
        }
    }

    /**
     * Set the first vertex in the polygon list to the specified point. This will
     * throw an error of the polygon already has some points defined.
     */
    public void moveTo(double x, double y) {
        getCoordListShape().moveTo(x, y);
        reshape();
    }

    /**
     * Set the coords of this polygon.
     */
    public void setCoords(Point2D pt1, Point2D pt2) {
        getCoordListShape().reset();
        getCoordListShape().moveTo(pt1.getX(), pt1.getY());
        getCoordListShape().lineTo(pt2.getX(), pt2.getY());
        reshape();
    }

    /**
     * Set the coords of this polygon.
     */
    public void setCoords(double[] xp, double[] yp) {
        reset();

        if (xp.length > 0) {
            getCoordListShape().moveTo(xp[0], yp[0]);
        }

        for (int i = 1; i < xp.length; i++) {
            getCoordListShape().lineTo(xp[i], yp[i]);
        }

        reshape();
    }

    /**
     * Set the coords of this polygon.
     */
    public void setCoords(Point2D[] points) {
        reset();

        if (points.length > 0) {
            getCoordListShape().moveTo(points[0].getX(),
                                       points[0].getY());
        }

        for (int i = 1; i < points.length; i++) {
            getCoordListShape().lineTo(points[i].getX(),
                                       points[i].getY());
        }

        reshape();
    }

    /**
     * Remove all coordinates from the list.
     */
    public void reset() {
        getCoordListShape().reset();
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

        PathIterator i = getCoordListShape().getPathIterator(null);
        double[] pathcoords = new double[6];
        Vector coords = new Vector();

        while (!i.isDone()) {
            switch (i.currentSegment(pathcoords)) {
                case PathIterator.SEG_CLOSE: {
                    break;
                }
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO: {
                    coords.add(new Double(pathcoords[0]));
                    coords.add(new Double(pathcoords[1]));
                }
            }
            i.next();
        }

        out.writeState("Vector", "coords", coords);
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

        if (fieldName.compareTo("coords") == 0) {
            ZCoordListShape aCoordListShape = getCoordListShape();

            for (Iterator i=((Vector)fieldValue).iterator(); i.hasNext();) {
                aCoordListShape.lineTo(((Double)i.next()).doubleValue(),
                                       ((Double)i.next()).doubleValue());
            }
        }
        reshape();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        ZCoordListShape aCoordListShape = getCoordListShape();
        int vertexCount = aCoordListShape.getVertexCount();
        out.writeInt(vertexCount);

        for (int i = 0; i < vertexCount; i++) {
            out.writeDouble(aCoordListShape.getX(i));
            out.writeDouble(aCoordListShape.getY(i));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        ZCoordListShape aCoordListShape = getCoordListShape();
        int vertexCount = in.readInt();

        for (int i = 0; i < vertexCount; i++) {
            aCoordListShape.lineTo(in.readDouble(), in.readDouble());
        }
    }
}
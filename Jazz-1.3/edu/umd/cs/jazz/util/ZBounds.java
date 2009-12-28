/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.awt.geom.*;
import java.io.*;
import java.util.*;

import edu.umd.cs.jazz.io.*;


/**
 * <b>ZBounds</b> is simply a Rectangle2D.Double with extra methods that more
 * properly deal with the case when the rectangle is "empty".  A ZBounds
 * has an extra bit to store emptiness.  In this state, adding new geometry
 * replaces the current geometry.
 * <p>
 * This is intended for use by visual objects that store their dimensions, and
 * which may be empty.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 */
public class ZBounds extends Rectangle2D.Double implements Serializable {
    private boolean empty = true;

    /**
     * Constructs a new ZBounds object.
     */
    public ZBounds() {
    }

    /**
     * Constructs a new ZBounds object with the given dimensions.
     * @param x The X coordinate.
     * @param x The Y coordinate.
     * @param w The width.
     * @param h The height.
     */
    public ZBounds(double x, double y, double w, double h) {
        super(x, y, w, h);
        empty = false;
    }

    /**
     * Constructs a new ZBounds object with the dimensions of a given Rectangle2D.
     * @param rect The rectangle2D.
     */
    public ZBounds(Rectangle2D rect) {
        super(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        empty = false;
    }

    /**
     * Constructs a new ZBounds object with the dimensions of another ZBounds object.
     * @param bounds The ZBounds object.
     */
    public ZBounds(ZBounds bounds) {
        super(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        empty = bounds.isEmpty();
    }

    /**
     * Returns a clone of this object.
     */
    public Object clone() {
        ZBounds bounds = new ZBounds();
        bounds.add(this);

        return bounds;
    }

    /**
     * Sets the dimensions of this object be empty.
     */
    public void reset() {
        empty = true;
    }

    /**
     * Modify the object by applying the given transform.
     * @param tf the AffineTransform to apply.
     */
    public void transform(AffineTransform tf) {
    	if (isEmpty()) {
    		return;
    	}
    	
                    // First, transform all 4 corners of the rectangle
        double[] pts = new double[8];
        pts[0] = x;          // top left corner
        pts[1] = y;
        pts[2] = x + width;  // top right corner
        pts[3] = y;
        pts[4] = x + width;  // bottom right corner
        pts[5] = y + height;
        pts[6] = x;          // bottom left corner
        pts[7] = y + height;
        tf.transform(pts, 0, pts, 0, 4);

                    // Then, find the bounds of those 4 transformed points.
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
        setRect(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Returns the center point of the bounds.
     */
    public Point2D getCenter2D() {
        return new Point2D.Double(x + 0.5*width, y + 0.5*height);
    }

    /**
     * Determines if this ZBounds object is empty.
     * @return true if the bounds are empty, false otherwise.
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Sets the dimensions of this object.
     * @param x The X coordinate.
     * @param x The Y coordinate.
     * @param w The width.
     * @param h The height.
     */
    public void setRect(double x, double y, double w, double h) {
        super.setRect(x, y, w, h);
        empty = false;
    }

    /**
     * Sets the dimensions of this object to be the same as the given Rectangle2D.
     * @param r the Rectangle2D
     */
    public void setRect(Rectangle2D r) {
        super.setRect(r);
        empty = false;
    }

    /**
     * Adds a point, specified by the double precision arguments newx and newy,
     * to this ZBounds object. The resulting bounds is the smallest Rectangle2D
     * that contains both the original bounds and the specified point.
     * @param newx The X coordinate of the new point.
     * @param newy The Y coordinate of the new point.
     */
    public void add(double newx, double newy) {
        if (empty) {
            setRect(newx, newy, 0, 0);
            empty = false;
        } else {
            super.add(newx, newy);
        }
    }

    /**
     * Adds a Rectangle2D object to this ZBounds object. The resulting bounds is
     * the union of the current bounds and the given Rectangle2D object.
     * @param r The Rectangle2D to add.
     */
    public void add(Rectangle2D r) {
        if (r.isEmpty()) {
            return;
        } else if (empty) {
            setRect(r);
            empty = false;
        } else {
            super.add(r);
        }
    }

    /**
     * Adds a ZBounds object to this ZBounds object. The resulting bounds is
     * the union of the current bounds and the given ZBounds object.
     * @param r The ZBounds to add.
     */
    public void add(ZBounds r) {
        if (r.isEmpty()) {
            return;
        }

        if (empty) {
            setRect(r);
            empty = false;
        } else {
            super.add(r);
        }
    }

    /**
     * This method insets the bounds by the specified amount.
     */
    public void inset(double dx, double dy) {
        setRect(x + dx,
                y + dy,
                width - (dx*2),
                height - (dy*2));
    }

    /**
     * Generate a string that represents this object for debugging.
     * @return the string that represents this object for debugging
     */
    public String toString() {
        String str;
        if (isEmpty()) {
            str = "jazz.util.ZBounds[Empty]";
        } else {
            str = super.toString();
        }
        return str;
    }

    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
                    // write local class
        out.defaultWriteObject();

                    // write rectangle2d
        out.writeDouble(getX());
        out.writeDouble(getY());
        out.writeDouble(getWidth());
        out.writeDouble(getHeight());
    }

    /**
     * Read in all of this object's state.
     * @param in The stream that this object reads from.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
                    // read local class
        in.defaultReadObject();

                    // read rectangle2d
        double x, y, w, h;
        x = in.readDouble();
        y = in.readDouble();
        w = in.readDouble();
        h = in.readDouble();
        if (! isEmpty()) {
            setRect(x, y, w, h);
        }
    }
}
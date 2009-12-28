/**
 * Copyright 1998-1999 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.Vector;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZEllipse</b> is a graphic object that represents a hard-cornered
 * or rounded ellipse.
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
 * @author  adapted from ZRectangle by Wayne Westerman, University of Delaware
 */
public class ZEllipse extends ZShape {

    transient protected Ellipse2D ellipse;

    //****************************************************************************
    //
    //                Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new Ellipse, initialized to location (0, 0) and size (0, 0).
     */
    public ZEllipse() {
        setEllipse(new Ellipse2D.Double());
    }

    /**
     * Constructs an Ellipse2D at the specified location, initialized to size (0, 0).
     * @param <code>x</code> X-coord of top-left corner
     * @param <code>y</code> Y-coord of top-left corner
     */
    public ZEllipse(double x, double y) {
        setEllipse(new Ellipse2D.Double(x, y, 0.0, 0.0));
    }

    /**
     * Constructs and initializes an Ellipse2D from the specified coordinates.
     * @param <code>x</code> X-coord of top-left corner
     * @param <code>y</code> Y-coord of top-left corner
     * @param <code>width</code> Width of ellipse
     * @param <code>height</code> Height of ellipse
     */
    public ZEllipse(double x, double y, double width, double height) {
        setEllipse(new Ellipse2D.Double(x, y, width, height));
    }

    /**
     * Constructs a new Ellipse based on the geometry of the one passed in.
     * @param <code>r</code> A ellipse to get the geometry from
     */
    public ZEllipse(Ellipse2D r) {
        setEllipse((Ellipse2D)r.clone());
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZEllipse newEllipse = (ZEllipse)super.duplicateObject();
        newEllipse.ellipse = (Ellipse2D) getEllipse().clone();
        return newEllipse;
    }

    /**
     * Return the ellipse.
     * @return the Ellipse2D.
     */
    public Ellipse2D getEllipse() {
        if (ellipse == null) {
            ellipse = new Ellipse2D.Double();
        }
        return ellipse;
    }

    /**
     * Sets the coordinates of this ellipse.
     * @param <code>x</code> X coordinate of top-left corner.
     * @param <code>y</code> Y coordinate of top-left corner.
     * @param <code>width</code> Width of ellipse.
     * @param <code>height</code> Height of ellipse.
     */
    public void setFrame(double x, double y, double width, double height) {
        ellipse.setFrame(x, y, width, height);
        reshape();
    }

    /**
     * Sets coords of ellipse
     * @param <code>r</code> The new ellipse coordinates
     */
    public void setFrame(Ellipse2D r) {
        setEllipse(r);
    }

    /**
     * Return the shape.
     * @return the Shape.
     */
    public Shape getShape() {
        return ellipse;
    }

    /**
     * Set the ellipse.
     * @param <code>aEllipse</code> The new ellipse.
     */
    public void setEllipse(Ellipse2D aEllipse) {
        getEllipse().setFrame(aEllipse.getX(),
                              aEllipse.getY(),
                              aEllipse.getWidth(),
                              aEllipse.getHeight());
        reshape();
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

        Vector dimensions = new Vector();
        Ellipse2D ellipse = getEllipse();
        dimensions.add(new Double(ellipse.getX()));
        dimensions.add(new Double(ellipse.getY()));
        dimensions.add(new Double(ellipse.getWidth()));
        dimensions.add(new Double(ellipse.getHeight()));
        out.writeState("ellipse", "ellipse", dimensions);
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

        if (fieldName.compareTo("ellipse") == 0) {
            Vector dim = (Vector)fieldValue;
            double xpos   = ((Double)dim.get(0)).doubleValue();
            double ypos   = ((Double)dim.get(1)).doubleValue();
            double width  = ((Double)dim.get(2)).doubleValue();
            double height = ((Double)dim.get(3)).doubleValue();
            setFrame(xpos, ypos, width, height);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        Ellipse2D ellipse = getEllipse();
        out.writeDouble(ellipse.getX());
        out.writeDouble(ellipse.getY());
        out.writeDouble(ellipse.getWidth());
        out.writeDouble(ellipse.getHeight());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        double x, y, w, h;
        x = in.readDouble();
        y = in.readDouble();
        w = in.readDouble();
        h = in.readDouble();
        ellipse = new Ellipse2D.Double(x, y, w, h);
    }
}
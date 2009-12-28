/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;

/**
 * <b>ZRoundedRectangle</b> is a graphic object that represents a rounded rectangle.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Jesse Grosjean
 */
public class ZRoundedRectangle extends ZShape {

    protected transient RoundRectangle2D roundedRect;

    /**
     * ZRoundedRectangle constructor.
     */
    public ZRoundedRectangle() {
        super();
    }

    /**
     * ZRoundedRectangle constructor.
     */
    public ZRoundedRectangle(double x, double y, double w, double h,
                          double arcWidth, double arcHeight) {
        super();
        setRoundRect(x, y, w, h, arcWidth, arcHeight);
    }

    /**
     * ZRoundedRectangle constructor.
     */
    public ZRoundedRectangle(RoundRectangle2D aRoundedRectangle) {
        super();
        setRoundRect(aRoundedRectangle);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZRoundedRectangle newRoundedRect = (ZRoundedRectangle) super.duplicateObject();
        newRoundedRect.roundedRect = (RoundRectangle2D) getRounedRect().clone();
        return newRoundedRect;
    }

    /**
     * Gets the height of the arc that rounds off the corners.
     * @return the height of the arc that rounds off the corners
     * of this <code>RoundRectangle2D</code>.
     */
    public double getArcHeight() {
        return getRounedRect().getArcHeight();
    }

    /**
     * Gets the width of the arc that rounds off the corners.
     * @return the width of the arc that rounds off the corners
     * of this <code>RoundRectangle2D</code>.
     */
    public double getArcWidth() {
        return getRounedRect().getArcWidth();
    }

    /**
     * Return the current shape.
     */
    public RoundRectangle2D getRounedRect() {
        if (roundedRect == null) {
            roundedRect = new RoundRectangle2D.Double();
        }
        return roundedRect;
    }

    /**
     * Return the current shape.
     */
    public Shape getShape() {
        return getRounedRect();
    }

    /**
     * Sets the location and size of the outer bounds of this
     * <code>RoundRectangle2D</code> to the specified rectangular values.
     * @param x,&nbsp;y the coordinates to which to set the location
     * of this <code>RoundRectangle2D</code>
     * @param w the width to which to set this
     * <code>RoundRectangle2D</code>
     * @param h the height to which to set this
     * <code>RoundRectangle2D</code>
     */
    public void setFrame(double x, double y, double w, double h) {
        setRoundRect(x, y, w, h, getArcWidth(), getArcHeight());
    }

    /**
     * Sets the location, size, and corner radii of this
     * <code>ZRoundRectangle</code> to the specified
     * <code>double</code> values.
     * @param x,&nbsp;y the coordinates to which to set the
     * location of this <code>ZRoundRectangle</code>
     * @param w the width to which to set this
     * <code>ZRoundRectangle</code>
     * @param h the height to which to set this
     * <code>ZRoundRectangle</code>
     * @param arcWidth the width to which to set the arc of this
     * <code>ZRoundRectangle</code>
     * @param arcHeight the height to which to set the arc of this
     * <code>ZRoundRectangle</code>
     */
    public void setRoundRect(double x, double y, double w, double h,
                      double arcWidth, double arcHeight) {
        getRounedRect().setRoundRect(x, y, w, h, arcWidth, arcHeight);
        reshape();
    }

    /**
     * Sets this <code>RoundRectangle2D</code> to be the same as the
     * specified <code>RoundRectangle2D</code>.
     * @param rr the specified <code>RoundRectangle2D</code>
     */
    public void setRoundRect(RoundRectangle2D rr) {
        getRounedRect().setRoundRect(rr);
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

        RoundRectangle2D rect = getRounedRect();
        Vector dimensions = new Vector();
        dimensions.add(new Double(rect.getX()));
        dimensions.add(new Double(rect.getY()));
        dimensions.add(new Double(rect.getWidth()));
        dimensions.add(new Double(rect.getHeight()));
        dimensions.add(new Double(rect.getArcWidth()));
        dimensions.add(new Double(rect.getArcHeight()));
        out.writeState("roundedRectangle", "roundedRect", dimensions);
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

        if (fieldName.compareTo("roundedRect") == 0) {
            Vector dim = (Vector)fieldValue;
            double x   = ((Double)dim.get(0)).doubleValue();
            double y   = ((Double)dim.get(1)).doubleValue();
            double w  = ((Double)dim.get(2)).doubleValue();
            double h = ((Double)dim.get(3)).doubleValue();
            double arcWidth  = ((Double)dim.get(4)).doubleValue();
            double arcHeight = ((Double)dim.get(5)).doubleValue();

            setRoundRect(x, y, w, h, arcWidth, arcHeight);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        RoundRectangle2D rect = getRounedRect();
        out.writeDouble(rect.getX());
        out.writeDouble(rect.getY());
        out.writeDouble(rect.getWidth());
        out.writeDouble(rect.getHeight());
        out.writeDouble(rect.getArcWidth());
        out.writeDouble(rect.getArcHeight());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        double x, y, w, h, arcWidth, arcHeight;
        x = in.readDouble();
        y = in.readDouble();
        w = in.readDouble();
        h = in.readDouble();
        arcWidth = in.readDouble();
        arcHeight = in.readDouble();

        getRounedRect().setRoundRect(x, y, w, h, arcWidth, arcHeight);
    }
}
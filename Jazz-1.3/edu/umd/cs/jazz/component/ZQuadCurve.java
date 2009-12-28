/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;

/**
 * <b>ZQuadCurve</b> is a graphic object that represents a quad curve.
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
public class ZQuadCurve extends ZShape {

    /**
     * The wrapped QuadCurve2D.
     */
    protected transient QuadCurve2D quadCurve;

    /**
     * ZArc constructor.
     */
    public ZQuadCurve() {
    }

    /**
     * ZArc constructor.
     */
    public ZQuadCurve(double x1, double y1,
                      double ctrlx, double ctrly,
                      double x2, double y2) {
        super();
        setCurve(x1, y1, ctrlx, ctrly, x2, y2);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZQuadCurve newCurve = (ZQuadCurve) super.duplicateObject();
        newCurve.quadCurve = (QuadCurve2D) getQuadCurve().clone();
        return newCurve;
    }

    /**
     * Return the current curve.
     */
    public QuadCurve2D getQuadCurve() {
        if (quadCurve == null) {
            quadCurve = new QuadCurve2D.Double();
        }
        return quadCurve;
    }

    /**
     * Return the current shape.
     */
    public Shape getShape() {
        return getQuadCurve();
    }

    /**
     * Sets the curve.
     */
    public void setCurve(double x1, double y1,
                         double ctrlx, double ctrly,
                         double x2, double y2)
    {
        getQuadCurve().setCurve(x1, y1, ctrlx, ctrly, x2, y2);
        reshape();
    }

    /**
     * Sets the location of the endpoints and controlpoints of this curve
     * to the same as those in the specified <code>CubicCurve2D</code>.
     * @param c the specified <code>CubicCurve2D</code>
     */
    public void setCurve(QuadCurve2D c) {
        getQuadCurve().setCurve(c);
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

        QuadCurve2D quad = getQuadCurve();
        Vector dimensions = new Vector();
        dimensions.add(new Double(quad.getX1()));
        dimensions.add(new Double(quad.getY1()));
        dimensions.add(new Double(quad.getCtrlX()));
        dimensions.add(new Double(quad.getCtrlY()));
        dimensions.add(new Double(quad.getX2()));
        dimensions.add(new Double(quad.getY2()));
        out.writeState("quadCurve", "curve", dimensions);
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

        if (fieldName.compareTo("curve") == 0) {
            Vector dim = (Vector)fieldValue;
            double x1   = ((Double)dim.get(0)).doubleValue();
            double y1   = ((Double)dim.get(1)).doubleValue();
            double ctrlx  = ((Double)dim.get(2)).doubleValue();
            double ctrly = ((Double)dim.get(3)).doubleValue();
            double x2  = ((Double)dim.get(4)).doubleValue();
            double y2 = ((Double)dim.get(5)).doubleValue();

            setCurve(x1, y1, ctrlx, ctrly, x2, y2);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        QuadCurve2D quad = getQuadCurve();
        out.writeDouble(quad.getX1());
        out.writeDouble(quad.getY1());
        out.writeDouble(quad.getCtrlX());
        out.writeDouble(quad.getCtrlY());
        out.writeDouble(quad.getX2());
        out.writeDouble(quad.getY2());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        double x1, y1, ctrlx, ctrly, x2, y2;
        x1 = in.readDouble();
        y1 = in.readDouble();
        ctrlx = in.readDouble();
        ctrly = in.readDouble();
        x2 = in.readDouble();
        y2 = in.readDouble();

        getQuadCurve().setCurve(x1, y1, ctrlx, ctrly, x2, y2);
    }
}
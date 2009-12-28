package edu.umd.cs.jazz.component;
/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;

/**
 * <b>ZCubicCurve</b> is a simple ZShape implementation that uses a CubicCurve2D
 * as the underlying shape model.
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
public class ZCubicCurve extends ZShape {

    /**
     * The wrapped CubicCurve2D.
     */
    protected CubicCurve2D cubicCurve;

    /**
     * ZArc constructor.
     */
    public ZCubicCurve() {
    }

    /**
     * ZArc constructor.
     */
    public ZCubicCurve(double x1, double y1,
                         double ctrlx1, double ctrly1,
                         double ctrlx2, double ctrly2,
                         double x2, double y2) {
        super();
        setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZCubicCurve newCurve = (ZCubicCurve) super.duplicateObject();
        newCurve.cubicCurve = (CubicCurve2D) getCubicCurve().clone();
        return newCurve;
    }

    /**
     * Return the current curve.
     */
    public CubicCurve2D getCubicCurve() {
        if (cubicCurve == null) {
            cubicCurve = new CubicCurve2D.Double();
        }
        return cubicCurve;
    }

    /**
     * Return the current shape.
     */
    public Shape getShape() {
        return getCubicCurve();
    }

    /**
     * Set the current curve.
     */
    public void setCurve(double x1, double y1,
                         double ctrlx1, double ctrly1,
                         double ctrlx2, double ctrly2,
                         double x2, double y2)
    {
        getCubicCurve().setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
        reshape();
    }

    /**
     * Sets the location of the endpoints and controlpoints of this curve
     * to the same as those in the specified <code>CubicCurve2D</code>.
     * @param c the specified <code>CubicCurve2D</code>
     */
    public void setCurve(CubicCurve2D c) {
        setCurve(c.getX1(), c.getY1(), c.getCtrlX1(), c.getCtrlY1(),
                 c.getCtrlX2(), c.getCtrlY2(), c.getX2(), c.getY2());
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

        CubicCurve2D cubic = getCubicCurve();
        Vector dimensions = new Vector();
        dimensions.add(new Double(cubic.getX1()));
        dimensions.add(new Double(cubic.getY1()));
        dimensions.add(new Double(cubic.getCtrlX1()));
        dimensions.add(new Double(cubic.getCtrlY1()));
        dimensions.add(new Double(cubic.getCtrlX2()));
        dimensions.add(new Double(cubic.getCtrlY2()));
        dimensions.add(new Double(cubic.getX2()));
        dimensions.add(new Double(cubic.getY2()));
        out.writeState("cubicCurve", "curve", dimensions);
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
            double ctrlx1  = ((Double)dim.get(2)).doubleValue();
            double ctrly1 = ((Double)dim.get(3)).doubleValue();
            double ctrlx2  = ((Double)dim.get(2)).doubleValue();
            double ctrly2 = ((Double)dim.get(3)).doubleValue();
            double x2  = ((Double)dim.get(4)).doubleValue();
            double y2 = ((Double)dim.get(5)).doubleValue();

            setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        CubicCurve2D cubic = getCubicCurve();
        out.writeDouble(cubic.getX1());
        out.writeDouble(cubic.getY1());
        out.writeDouble(cubic.getCtrlX1());
        out.writeDouble(cubic.getCtrlY1());
        out.writeDouble(cubic.getCtrlX2());
        out.writeDouble(cubic.getCtrlY2());
        out.writeDouble(cubic.getX2());
        out.writeDouble(cubic.getY2());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        double x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2;
        x1 = in.readDouble();
        y1 = in.readDouble();
        ctrlx1 = in.readDouble();
        ctrly1 = in.readDouble();
        ctrlx2 = in.readDouble();
        ctrly2 = in.readDouble();
        x2 = in.readDouble();
        y2 = in.readDouble();

        setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
    }
}
/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZArc</b> is a simple ZShape implementation that uses a Arc2D as the underlying
 * shape model.
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
public class ZArc extends ZShape {

    /**
     * The wrapped Arc2D.
     */
    protected transient Arc2D arc;

    /**
     * ZArc constructor.
     */
    public ZArc() {
        super();
    }

    /**
     * ZArc constructor.
     */
    public ZArc(Arc2D aArch) {
        super();
        setArc(aArch);
    }

    /**
     * ZArc constructor.
     */
    public ZArc(double x, double y, double w, double h, double angSt, double angExt, int closure) {
        super();
        setArc(x, y, w, h, angSt, angExt, closure);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZArc newArc = (ZArc) super.duplicateObject();
        newArc.arc = (Arc2D) getArc().clone();
        return newArc;
    }

    /**
     * Return the current arc.
     */
    public Arc2D getArc() {
        if (arc == null) {
            arc = new Arc2D.Double();
        }
        return arc;
    }

    /**
     * Return the current shape.
     */
    public Shape getShape() {
        return arc;
    }

    /**
     * Sets this arc to be the same as the specified arc.
     *
     * @param a The <CODE>Arc2D</CODE> to use to set the arc's values.
     */
    public void setArc(Arc2D a) {
        getArc().setArc(a);
        reshape();
    }

    /**
     * Sets this arc to be the same as the specified arc.
     */
    public void setArc(double x, double y, double w, double h, double angSt, double angExt, int closure) {
        getArc().setArc(x, y, w, h, angSt, angExt, closure);
        reshape();
    }

    /////////////////////////////////////////////////////////////////////////
    //
    // Saving
    //
    /////////////////////////////////////////////////////////////////////////

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

        if (fieldName.compareTo("arc") == 0) {
            Vector dim = (Vector) fieldValue;
            double x   = ((Double)dim.get(0)).doubleValue();
            double y   = ((Double)dim.get(1)).doubleValue();
            double w  = ((Double)dim.get(2)).doubleValue();
            double h = ((Double)dim.get(3)).doubleValue();
            double angSt = ((Double)dim.get(4)).doubleValue();
            double angExt = ((Double)dim.get(5)).doubleValue();
            int closure = ((Integer)dim.get(6)).intValue();
            setArc(x, y, w, h, angSt, angExt, closure);
        }
    }

    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException {
        super.writeObject(out);

        Arc2D arc = getArc();
        Vector dimensions = new Vector();
        dimensions.add(new Double(arc.getX()));
        dimensions.add(new Double(arc.getY()));
        dimensions.add(new Double(arc.getWidth()));
        dimensions.add(new Double(arc.getHeight()));
        dimensions.add(new Double(arc.getAngleStart()));
        dimensions.add(new Double(arc.getAngleExtent()));
        dimensions.add(new Integer(arc.getArcType()));
        out.writeState("arc", "arc", dimensions);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        double x, y, w, h, angSt, angExt;
        int closure;

        x = in.readDouble();
        y = in.readDouble();
        w = in.readDouble();
        h = in.readDouble();
        angSt = in.readDouble();
        angExt = in.readDouble();
        closure = in.readInt();

        getArc().setArc(x ,y, w, h, angSt, angExt, closure);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        Arc2D arc = getArc();
        out.writeDouble(arc.getX());
        out.writeDouble(arc.getY());
        out.writeDouble(arc.getWidth());
        out.writeDouble(arc.getHeight());
        out.writeDouble(arc.getAngleStart());
        out.writeDouble(arc.getAngleExtent());
        out.writeInt(arc.getArcType());
    }
}
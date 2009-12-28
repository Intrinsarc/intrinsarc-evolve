/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.component;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.Vector;
import java.util.Iterator;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZPath</b> is a graphic object that represents combination of lines and curves that
 * can be specified <b>GeneralPath</b>.
 * Please see java.awt.geon.GeneralPath for detail
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
public class ZPath extends ZShape {

    transient protected GeneralPath path;

    //****************************************************************************
    //
    //                Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new Path
     */
    public ZPath() {
    }

    /**
     * Constructs a new Path
     */
    public ZPath(GeneralPath path) {
        setPath(path);
    }

    /**
     * Set the path.
     */
    public void setPath(GeneralPath aPath) {
        getPath().reset();
        getPath().append(aPath, false);
        reshape();
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZPath newPath = (ZPath)super.duplicateObject();
        newPath.path = (GeneralPath) getPath().clone();
        return newPath;
    }

    //****************************************************************************
    //
    // Geometry
    //
    //***************************************************************************

    /**
     * Return x-coord of rectangle.
     * @return x-coord.
     */
    public double getX() {
        return getRect().getX();
    }

    /**
     * Return y-coord of rectangle.
     * @return y-coord.
     */
    public double getY() {
        return getRect().getY();
    }

    /**
     * Return width of rectangle.
     * @return width.
     */
    public double getWidth() {
        return getRect().getWidth();
    }

    /**
     * Return height of rectangle.
     * @return height.
     */
    public double getHeight() {
        return getRect().getHeight();
    }

    /**
     * Return rectangle.
     * @return rectangle.
     */
    public Rectangle2D getRect() {
        return getShape().getBounds2D();
    }

    /**
     * Return the shape.
     * @return the Shape.
     */
    public Shape getShape() {
        return path;
    }

    /**
     * Return the path.
     * @return the GeneralPath.
     */
    public GeneralPath getPath() {
        if (path == null) {
            path = new GeneralPath();
        }
        return path;
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

        Vector pathVector = flattenPath(getPath());
        out.writeState("java.util.Vector", "path", pathVector);
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

        if (fieldName.compareTo("path") == 0) {
            Vector pathVector = (Vector)fieldValue;
            setPath(buildPathFrom(pathVector));
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        Vector pathVector = flattenPath(getPath());
        out.writeObject(pathVector);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        Vector pathVector = (Vector)in.readObject();
        path = buildPathFrom(pathVector);
    }

    /**
     * Flatten the Path
     */
    private Vector flattenPath(GeneralPath path) {
        Vector result = new Vector();

        AffineTransform at = new AffineTransform();
        PathIterator i = path.getPathIterator(at);
        while(!i.isDone()) {
            float[] data = new float[6];
            switch(i.currentSegment(data)) {
                case PathIterator.SEG_MOVETO :
                    result.addElement(new Integer(PathIterator.SEG_MOVETO));
                    result.addElement(new Float(data[0]));
                    result.addElement(new Float(data[1]));
                    break;
                case PathIterator.SEG_LINETO :
                    result.addElement(new Integer(PathIterator.SEG_LINETO));
                    result.addElement(new Float(data[0]));
                    result.addElement(new Float(data[1]));
                    break;
                case PathIterator.SEG_QUADTO :
                    result.addElement(new Integer(PathIterator.SEG_QUADTO));
                    result.addElement(new Float(data[0]));
                    result.addElement(new Float(data[1]));
                    result.addElement(new Float(data[2]));
                    result.addElement(new Float(data[3]));
                    break;
                case PathIterator.SEG_CUBICTO :
                    result.addElement(new Integer(PathIterator.SEG_CUBICTO));
                    result.addElement(new Float(data[0]));
                    result.addElement(new Float(data[1]));
                    result.addElement(new Float(data[2]));
                    result.addElement(new Float(data[3]));
                    result.addElement(new Float(data[4]));
                    result.addElement(new Float(data[5]));
                    break;
                case PathIterator.SEG_CLOSE :
                    result.addElement(new Integer(PathIterator.SEG_CLOSE));
                    break;
                default :
                    break;
            }

            i.next();
        }
        return result;
    }

    private GeneralPath buildPathFrom(Vector v) {
        GeneralPath path = new GeneralPath();

        Iterator i = v.iterator();

        while(i.hasNext()) {
            int segType = ((Integer)i.next()).intValue();
            switch(segType) {
                case PathIterator.SEG_MOVETO :
                    path.moveTo(((Float)i.next()).floatValue(),((Float)i.next()).floatValue());
                    break;
                case PathIterator.SEG_LINETO :
                    path.lineTo(((Float)i.next()).floatValue(),((Float)i.next()).floatValue());
                    break;
                case PathIterator.SEG_QUADTO :
                    path.quadTo(((Float)i.next()).floatValue(),((Float)i.next()).floatValue(),
                        ((Float)i.next()).floatValue(),((Float)i.next()).floatValue());
                    break;
                case PathIterator.SEG_CUBICTO :
                    path.curveTo(((Float)i.next()).floatValue(),((Float)i.next()).floatValue(),
                        ((Float)i.next()).floatValue(),((Float)i.next()).floatValue(),
                        ((Float)i.next()).floatValue(),((Float)i.next()).floatValue());
                    break;
                case PathIterator.SEG_CLOSE :
                    path.closePath();
                    break;
                default :
                    break;
            }
        }
        return path;
    }
}
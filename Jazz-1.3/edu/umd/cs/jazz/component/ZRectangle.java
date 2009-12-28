/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZRectangle</b> is a graphic object that represents a hard-cornered
 * rectangle.
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
public class ZRectangle extends ZShape {

    /**
     * Default stroke for rectangles.
     */
    static public final Stroke      DEFAULT_RECT_STROKE = new BasicStroke((float)DEFAULT_PEN_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    transient protected Rectangle2D rectangle;

    //****************************************************************************
    //
    //                Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new Rectangle.
     */
    public ZRectangle() {
        this(new Rectangle2D.Double());
    }

    /**
     * Constructs a new Rectangle at the specified location, with dimensions of zero.
     * @param <code>x</code> X-coord of top-left corner
     * @param <code>y</code> Y-coord of top-left corner
     */
    public ZRectangle(double x, double y) {
        this(new Rectangle2D.Double(x, y, 0.0, 0.0));
    }

    /**
     * Constructs a new Rectangle at the specified location, with the given dimensions.
     * @param <code>x</code> X-coord of top-left corner
     * @param <code>y</code> Y-coord of top-left corner
     * @param <code>width</code> Width of rectangle
     * @param <code>height</code> Height of rectangle
     */
    public ZRectangle(double x, double y, double width, double height) {
        this(new Rectangle2D.Double(x, y, width, height));
    }

    /**
     * Constructs a new Rectangle based on the geometry of the one passed in.
     * @param <code>r</code> A rectangle to get the geometry from
     */
    public ZRectangle(Rectangle2D r) {
        setRect((Rectangle2D)r.clone());
        stroke = (BasicStroke) DEFAULT_RECT_STROKE;
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZRectangle newRectangle = (ZRectangle)super.duplicateObject();
        newRectangle.rectangle = (Rectangle2D) getRect().clone();
        return newRectangle;
    }

    public Collection getHandles() {
        Collection result = new ArrayList(8);

        // North
        result.add(new ZHandle(ZBoundsLocator.createNorthLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                if (rectangle.getHeight() - dy < 0) {
                    dy = dy - Math.abs(rectangle.getHeight() - dy);
                }
                setRect(rectangle.getX(),
                        rectangle.getY() + dy,
                        rectangle.getWidth(),
                        rectangle.getHeight() - dy);

                super.handleDragged(dx, dy, e);
            }
        });

        // East
        result.add(new ZHandle(ZBoundsLocator.createEastLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                setRect(rectangle.getX(),
                        rectangle.getY(),
                        rectangle.getWidth() + dx,
                        rectangle.getHeight());

                super.handleDragged(dx, dy, e);
            }
        });

        // West
        result.add(new ZHandle(ZBoundsLocator.createWestLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                if (rectangle.getWidth() - dx < 0) {
                    dx = dx - Math.abs(rectangle.getWidth() - dx);
                }
                setRect(rectangle.getX() + dx,
                        rectangle.getY(),
                        rectangle.getWidth() - dx,
                        rectangle.getHeight());

                super.handleDragged(dx, dy, e);
            }
        });

        // South
        result.add(new ZHandle(ZBoundsLocator.createSouthLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                setRect(rectangle.getX(),
                        rectangle.getY(),
                        rectangle.getWidth(),
                        rectangle.getHeight() + dy);

                super.handleDragged(dx, dy, e);
            }
        });

        // North West
        result.add(new ZHandle(ZBoundsLocator.createNorthWestLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                if (rectangle.getWidth() - dx < 0) {
                    dx = dx - Math.abs(rectangle.getWidth() - dx);
                }
                if (rectangle.getHeight() - dy < 0) {
                    dy = dy - Math.abs(rectangle.getHeight() - dy);
                }
                setRect(rectangle.getX() + dx,
                        rectangle.getY() + dy,
                        rectangle.getWidth() - dx,
                        rectangle.getHeight() - dy);

                super.handleDragged(dx, dy, e);
            }
        });

        // South West
        result.add(new ZHandle(ZBoundsLocator.createSouthWestLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                if (rectangle.getWidth() - dx < 0) {
                    dx = dx - Math.abs(rectangle.getWidth() - dx);
                }
                setRect(rectangle.getX() + dx,
                        rectangle.getY(),
                        rectangle.getWidth() - dx,
                        rectangle.getHeight() + dy);

                super.handleDragged(dx, dy, e);
            }
        });

        // North East
        result.add(new ZHandle(ZBoundsLocator.createNorthEastLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                if (rectangle.getHeight() - dy < 0) {
                    dy = dy - Math.abs(rectangle.getHeight() - dy);
                }
                setRect(rectangle.getX(),
                        rectangle.getY() + dy,
                        rectangle.getWidth() + dx,
                        rectangle.getHeight() - dy);

                super.handleDragged(dx, dy, e);
            }
        });

        // South East
        result.add(new ZHandle(ZBoundsLocator.createSouthEastLocator(this)) {
            public void handleDragged(double dx, double dy, ZMouseEvent e) {
                setRect(rectangle.getX(),
                        rectangle.getY(),
                        rectangle.getWidth() + dx,
                        rectangle.getHeight() + dy);

                super.handleDragged(dx, dy, e);
            }
        });

        return result;
    }

    /**
     * Return the rectangle.
     * @return rectangle.
     */
    public Rectangle2D getRect() {
        if (rectangle == null) {
            rectangle = new Rectangle2D.Double();
        }
        return rectangle;
    }

    /**
     * Return the shape.
     * @return Shape.
     */
    public Shape getShape() {
        return getRect();
    }

    /**
     * Sets location and size of the rectangle.
     * @param <code>x</code> X-coord of top-left corner
     * @param <code>y</code> Y-coord of top-left corner
     * @param <code>width</code> Width of rectangle
     * @param <code>height</code> Height of rectangle
     */
    public void setRect(double x, double y, double width, double height) {
        if (width < 0) {
            width = 0;
        }

        if (height < 0) {
            height = 0;
        }

        getRect().setRect(x, y, width, height);
        reshape();
    }

    /**
     * Sets coordinates of rectangle.
     * @param <code>r</code> The new rectangle coordinates
     */
    public void setRect(Rectangle2D r) {
        getRect().setRect(r);
        reshape();
    }

    /**
     * Set the width of the pen used to draw the visual component.
     * If the pen width is set here, then the stroke is set to solid (un-dashed),
     * with a "butt" cap style, and a "bevel" join style.  The pen width
     * will be dependent on the camera magnification.
     * @param width the pen width.
     * @see #setAbsPenWidth
     */
    public void setPenWidth(double width) {
        penWidth = width;
        absPenWidth = false;
        setVolatileBounds(false);
        stroke = new BasicStroke((float)penWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
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

        Rectangle2D rect = getRect();
        Vector dimensions = new Vector();
        dimensions.add(new Double(rect.getX()));
        dimensions.add(new Double(rect.getY()));
        dimensions.add(new Double(rect.getWidth()));
        dimensions.add(new Double(rect.getHeight()));
        out.writeState("rectangle", "rect", dimensions);
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

        if (fieldName.compareTo("rect") == 0) {
            Vector dim = (Vector)fieldValue;
            double xpos   = ((Double)dim.get(0)).doubleValue();
            double ypos   = ((Double)dim.get(1)).doubleValue();
            double width  = ((Double)dim.get(2)).doubleValue();
            double height = ((Double)dim.get(3)).doubleValue();
            setRect(xpos, ypos, width, height);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

                                // write Rectangle2D rect
        Rectangle2D rect = getRect();
        out.writeDouble(rect.getX());
        out.writeDouble(rect.getY());
        out.writeDouble(rect.getWidth());
        out.writeDouble(rect.getHeight());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
                                // read Rectangle2D rect
        double x, y, w, h;
        x = in.readDouble();
        y = in.readDouble();
        w = in.readDouble();
        h = in.readDouble();

        rectangle = new Rectangle2D.Double(x, y, w, h);
    }
}
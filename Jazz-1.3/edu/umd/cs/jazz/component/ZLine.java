/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZLine</b> is a simple ZShape implementation that uses a Line2D as the underlying
 * shape model.
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
public class ZLine extends ZShape {

    protected transient Line2D line;

    /**
     * ZLine constructor.
     */
    public ZLine() {
        super();
    }

    /**
     * ZLine constructor.
     */
    public ZLine(Line2D aLine) {
        this(aLine.getP1(), aLine.getP2());
    }

    /**
     * ZLine constructor.
     */
    public ZLine(Point2D pt1, Point2D pt2) {
        this(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
    }

    /**
     * ZLine constructor.
     */
    public ZLine(double x1, double y1, double x2, double y2) {
        super();
        setLine(x1, y1, x2, y2);
        fillPaint = null;
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZLine newLine = (ZLine) super.duplicateObject();
        newLine.line = (Line2D) getLine().clone();
        return newLine;
    }

    /**
     * Return the line.
     */
    public Line2D getLine() {
        if (line == null) {
            line = new Line2D.Double();
        }
        return line;
    }

    /**
     * Set the absolute width of the pen used to draw the perimeter of this shape.
     * Absolute pen width means that the stroke of this shape will always be drawn to the
     * screen at the same size no matter what the camera magnification is.
     * <p>
     * When drawing with an abs pen width the component is stroked on the inside of its bounds,
     * not along the center as is the standard method for Java and Jazz. This is done so
     * that the global bounds of the shape do not need to change when the camera zooms.
     * <p>
     * A consequence of stroking on the inside of the component is that components with zero width
     * or height will not get drawn even if they have a non zero pen width, since there is no interior space
     * for the pen width to expand into. This could happen for instance if a ZPolyline is drawn with
     * all its points in a line along the same axis. SO BE WARNED!
     * <p>
     * ZLine presents an extreme case of this, since whenever it is drawn along the x or y axis it
     * will not get drawn. Because of this ZLine implements abs pen width differently. ZLines are
     * always stroked along the center of the line, so as the camera moves a ZLines global
     * bounds will change if it has abs pen width.
     *
     * @param width the pen width.
     */
    public void setAbsPenWidth(double width) {
        setVolatileBounds(true);
        super.setAbsPenWidth(width);
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
        setVolatileBounds(false);
        super.setPenWidth(width);
    }

    /**
     * Notifies this object that it has changed and that it should update
     * its notion of its bounding box.  Note that this should not be called
     * directly.  Instead, it is called by <code>updateBounds</code> when needed.
     */
    protected void computeBounds() {
        bounds.reset();
        Rectangle2D shapeBounds = getFrame();

        double aPenWidth = getPenWidthForCurrentContext();
        double aHalfPenWidth = aPenWidth * 0.5;

                            // Expand the bounds to accomodate the pen width
        bounds.setRect(shapeBounds.getX() - aHalfPenWidth, shapeBounds.getY() - aHalfPenWidth,
                       shapeBounds.getWidth() + aPenWidth, shapeBounds.getHeight() + aPenWidth);
    }

    /**
     * Paints this object.
     * <p>
     * The transform, clip, and composite will be set appropriately when this object
     * is rendered.  It is up to this object to restore the transform, clip, and composite of
     * the Graphics2D if this node changes any of them. However, the color, font, and stroke are
     * unspecified by Jazz.  This object should set those things if they are used, but
     * they do not need to be restored.
     *
     * @param <code>renderContext</code> The graphics context to paint into.
     */
    public void render(ZRenderContext renderContext) {
        if (absPenWidth) {
            if (penPaint != null) {
                Graphics2D g2 = renderContext.getGraphics2D();
                double pw = penWidth / renderContext.getCompositeMagnification();
                stroke = new BasicStroke((float)pw, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
                g2.setStroke(stroke);
                g2.setPaint(penPaint);
                g2.draw(getShape());
            }
        } else {
            super.render(renderContext);
        }
    }

    /**
     * Return the current shape.
     */
    public Shape getShape() {
        return getLine();
    }

    /**
     * Set the line.
     */
    public void setLine(Line2D aLine) {
        setLine(aLine.getP1(), aLine.getP2());
    }

    /**
     * Set the line.
     */
    public void setLine(Point2D pt1, Point2D pt2) {
        setLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
    }

    /**
     * Set the line.
     */
    public void setLine(double x1, double y1, double x2, double y2) {
        getLine().setLine(x1, y1, x2, y2);
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

        Line2D line = getLine();
        Vector dimensions = new Vector();
        dimensions.add(new Double(line.getX1()));
        dimensions.add(new Double(line.getY1()));
        dimensions.add(new Double(line.getX2()));
        dimensions.add(new Double(line.getY2()));
        out.writeState("line", "line", dimensions);
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

        if (fieldName.compareTo("line") == 0) {
            Vector dim = (Vector)fieldValue;
            double x1   = ((Double)dim.get(0)).doubleValue();
            double y1   = ((Double)dim.get(1)).doubleValue();
            double x2  = ((Double)dim.get(2)).doubleValue();
            double y2 = ((Double)dim.get(3)).doubleValue();

            setLine(x1, y1, x2, y2);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        Line2D line = getLine();
        out.writeDouble(line.getX1());
        out.writeDouble(line.getY1());
        out.writeDouble(line.getX2());
        out.writeDouble(line.getY2());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        double x1, y1, x2, y2;
        x1 = in.readDouble();
        y1 = in.readDouble();
        x2 = in.readDouble();
        y2 = in.readDouble();

        getLine().setLine(x1, y1, x2, y2);
    }
}
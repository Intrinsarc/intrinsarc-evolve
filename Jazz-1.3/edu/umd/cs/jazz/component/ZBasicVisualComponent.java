/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;
import java.util.*;
import edu.umd.cs.jazz.io.*;
import java.io.*;

/**
 * This class is designed to be extended. It takes care of managing a fillPaint,
 * a penPaint, and a stroke, but leaves picking, and rendering to its subclasses.
 *
 * @author Jesse Grosjean
 */
public class ZBasicVisualComponent extends ZVisualComponent implements ZStroke,
                                                                       ZPenPaint,
                                                                       ZFillPaint,
                                                                       ZPenColor,
                                                                       ZFillColor {
    /**
     * Default pen width.
     */
    static public final double      DEFAULT_PEN_WIDTH = 1.0;

    /**
     * Default absolute pen width.
     */
    static public final boolean     DEFAULT_ABS_PEN_WIDTH = false;

    /**
     * Default pen paint.
     */
    static public final Paint       DEFAULT_PEN_PAINT = Color.black;

    /**
     * Default fill paint.
     */
    static public final Paint       DEFAULT_FILL_PAINT = Color.white;

    /**
     * Default stroke.
     */
    static public final Stroke      DEFAULT_STROKE = new BasicStroke((float)DEFAULT_PEN_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    //
    // Instance variables.
    //

    /**
     * Current fill paint.
     */
    protected transient Paint       fillPaint  = DEFAULT_FILL_PAINT;

    /**
     * Current pen paint.
     */
    protected transient Paint       penPaint  = DEFAULT_PEN_PAINT;

    /**
     * Current stroke.
     */
    protected transient BasicStroke stroke = (BasicStroke) DEFAULT_STROKE;

    /**
     * Current pen width.
     */
    protected double                penWidth  = DEFAULT_PEN_WIDTH;

    /**
     * Current absolute pen width.
     */
    protected boolean               absPenWidth = DEFAULT_ABS_PEN_WIDTH;

    /**
     * ZBasicVisualComponent constructor comment.
     */
    public ZBasicVisualComponent() {
        super();
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZBasicVisualComponent newBasicVisualComponent = (ZBasicVisualComponent) super.duplicateObject();
        newBasicVisualComponent.penPaint = penPaint;
        newBasicVisualComponent.fillPaint = fillPaint;
        newBasicVisualComponent.stroke = new BasicStroke(stroke.getLineWidth(),
                                                         stroke.getEndCap(),
                                                         stroke.getLineJoin(),
                                                         stroke.getMiterLimit(),
                                                         stroke.getDashArray(),
                                                         stroke.getDashPhase());

        newBasicVisualComponent.penWidth = penWidth;
        newBasicVisualComponent.absPenWidth = absPenWidth;

        return newBasicVisualComponent;
    }

    /**
     * Get the absolute width of the pen used to draw the visual component.
     * If the pen width is not absolute (dependent on magnification),
     * then this returns 0.
     * @return the pen width.
     * @see #getPenWidth
     */
    public double getAbsPenWidth() {
        if (absPenWidth) {
            return penWidth;
        } else {
            return 0.0d;
        }
    }

    /**
     * Get the fill color of this visual component.
     * @return the fill color, or null if none.
     *
     * @deprecated As of Jazz version 1.1,
     * replaced by <code>ZFillPaint.getFillPaint()</code>.
     */
    public Color getFillColor() {
        return (Color) getFillPaint();
    }

    /**
     * Get the fill paint of this visual component.
     * @return the fill paint, or null if none.
     */
    public Paint getFillPaint() {
        return fillPaint;
    }

    /**
     * Get the pen color of this visual component.
     * @return the pen color, or null if none.
     *
     * @deprecated As of Jazz version 1.1,
     * replaced by <code>ZPenPaint.getPenPaint()</code>.
     */
    public Color getPenColor() {
        return (Color) getPenPaint();
    }

    /**
     * Get the pen paint of this visual component.
     * @return the pen paint, or null if none.
     */
    public Paint getPenPaint() {
        return penPaint;
    }

    /**
     * Get the width of the pen used to draw the visual component.
     * If the pen width is absolute (independent of magnification),
     * then this returns 0.
     * @return the pen width.
     * @see #getAbsPenWidth
     */
    public double getPenWidth() {
        if (absPenWidth || penPaint == null) {
            return 0.0d;
        } else {
            return penWidth;
        }
    }

    /**
     * Get the width of the pen used to draw the visual component while
     * taking the current render context into consideration. If the pen
     * width is absolute (independent of magnification) then the resulting
     * width will be scaled appropriately for the current context.
     * @return the pen width scale for current context.
     */
    protected double getPenWidthForCurrentContext() {
        double result = 0.0;
        if (penPaint != null) {
            if (absPenWidth) {
                ZRoot aRoot = getRoot();
                if (aRoot != null) {
                    ZRenderContext rc = aRoot.getCurrentRenderContext();
                    double mag = (rc == null) ? 1.0f : rc.getCameraMagnification();
                    result = penWidth / mag;
                } else {
                    result = penWidth;
                }
            } else {
                result = penWidth;
            }
        }
        return result;
    }

    /**
     * Get the stroke used to draw the visual component.
     * @return the stroke.
     */
    public Stroke getStroke() {
        return stroke;
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
        penWidth = width;
        absPenWidth = true;
        reshape();
    }

    /**
     * Set the fill color of this visual component.
     * @param color the fill color, or null if none.
     *
     * @deprecated As of Jazz version 1.1,
     * replaced by <code>ZFillPaint.setFillPaint(Paint aPaint)</code>.
     */
    public void setFillColor(Color color) {
        setFillPaint(color);
    }

    /**
     * Set the fill paint of this visual component.
     * @param aPaint the fill paint, or null if none.
     */
    public void setFillPaint(Paint aPaint) {
        fillPaint = aPaint;
        repaint();
    }

    /**
     * Set the pen color of this visual component.
     * @param color the pen color, or null if none.
     *
     * @deprecated As of Jazz version 1.1,
     * replaced by <code>ZPenPaint.setPenPaint(Paint aPaint)</code>.
     */
    public void setPenColor(Color color) {
        setPenPaint(color);
    }

    /**
     * Set the pen paint of this visual component.
     * @param aPaint the pen paint, or null if none.
     */
    public void setPenPaint(Paint aPaint) {
        boolean boundsChanged = false;

                                // If turned pen color on or off, then need to recompute bounds
        if (((penPaint == null) && (aPaint != null)) ||
            ((penPaint != null) && (aPaint == null))) {
            boundsChanged = true;
        }
        penPaint = aPaint;

        if (boundsChanged) {
            reshape();
        } else {
            repaint();
        }
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

        if (volatileBounds) {
            setVolatileBounds(false);
        }

        stroke = new BasicStroke((float)penWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        reshape();
    }

    /**
     * Set the stroke used to draw the visual component.
     * @param stroke the stroke.
     */
    public void setStroke(Stroke stroke) {
        this.stroke = (BasicStroke) stroke;
        penWidth = this.stroke.getLineWidth();
        absPenWidth = false;
        setVolatileBounds(false);
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

        float[] dash = null;

        if (fieldName.compareTo("penPaint") == 0) {
            if (fieldValue.equals("nopaint")) {
                penPaint = null;
            } else {
                setPenPaint((Paint)fieldValue);
            }
        } else if (fieldName.compareTo("fillPaint") == 0) {
            if (fieldValue.equals("nopaint")) {
                fillPaint = null;
            } else {
                setFillPaint((Paint)fieldValue);
            }
        } else if (fieldName.compareTo("penWidth") == 0) {
            if (absPenWidth) {
                setAbsPenWidth(((Double)fieldValue).doubleValue());
            } else {
                setPenWidth(((Double)fieldValue).doubleValue());
            }
        } else if (fieldName.compareTo("dashArray") == 0) {
            Vector dashArray = (Vector)fieldValue;

            if(dashArray.size() > 0) {
                dash = new float[dashArray.size()];
                for(int i=0;i<dashArray.size();i++) {
                    dash[i] = ((Float)dashArray.elementAt(i)).floatValue();
                }
            }
        } else if (fieldName.compareTo("stroke") == 0) {
            Vector strokeVector = (Vector)fieldValue;
            float width = ((Float)strokeVector.elementAt(0)).floatValue();
            int cap = ((Integer)strokeVector.elementAt(1)).intValue();
            int join = ((Integer)strokeVector.elementAt(2)).intValue();
            float miterlimit = ((Float)strokeVector.elementAt(3)).floatValue();
            float dash_phase = ((Float)strokeVector.elementAt(4)).floatValue();
            if(dash == null) {
                setStroke(new BasicStroke(width, cap, join, miterlimit));
            } else {
                setStroke(new BasicStroke(width, cap, join, miterlimit, dash, dash_phase));
            }

        // XXX this is to support legacy files. These field names no longer get
        // written out.
        } else if (fieldName.compareTo("fillColor") == 0) {
            setFillPaint((Paint)fieldValue);
        } else if (fieldName.compareTo("penColor") == 0) {
            setPenPaint((Paint)fieldValue);
        }
    }

    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException {
        super.writeObject(out);

        if (penPaint != DEFAULT_PEN_PAINT) {
            if (penPaint != null) {
                out.writeState("java.awt.Color", "penPaint", penPaint);
            } else {
                out.writeState("java.lang.String", "penPaint", "nopaint");
            }
        }
        if (fillPaint != DEFAULT_FILL_PAINT) {
            if (fillPaint != null) {
                out.writeState("java.awt.Color", "fillPaint", fillPaint);
            } else {
                out.writeState("java.lang.String", "fillPaint", "nopaint");
            }
        }
        if (absPenWidth != DEFAULT_ABS_PEN_WIDTH) {
            out.writeState("boolean", "absPenWidth", absPenWidth);
        }
        if (getPenWidth() != DEFAULT_PEN_WIDTH) {
            out.writeState("double", "penWidth", getPenWidth());
        }
        if ((stroke != null) && (stroke != DEFAULT_STROKE)) {
            float[] dash = stroke.getDashArray();
            Vector dashArray = new Vector();
            if(dash != null && dash.length > 0) {
                for(int i=0;i<dash.length;i++) {
                    dashArray.addElement(new Float(dash[i]));
                }
            }
            out.writeState("java.util.Vector", "dashArray", dashArray);

            Vector strokeVector = new Vector();
            strokeVector.addElement(new Float(stroke.getLineWidth()));
            strokeVector.addElement(new Integer(stroke.getEndCap()));
            strokeVector.addElement(new Integer(stroke.getLineJoin()));
            strokeVector.addElement(new Float(stroke.getMiterLimit()));
            strokeVector.addElement(new Float(stroke.getDashPhase()));

            out.writeState("java.util.Vector", "stroke", strokeVector);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        // Write out colors. This ignors gradients, and patterns since they are
        // not serializable.
        if(penPaint == null || (penPaint instanceof Color)) {
            out.writeBoolean(true);
            out.writeObject(penPaint);
        } else {
            out.writeBoolean(false);
        }

        if(fillPaint == null || (fillPaint instanceof Color)) {
            out.writeBoolean(true);
            out.writeObject(fillPaint);
        } else {
            out.writeBoolean(false);
        }

        // Write out stroke.
        float[] dash = stroke.getDashArray();
        Vector dashArray = new Vector();
        if(dash != null && dash.length > 0) {
            for(int i=0;i<dash.length;i++) {
                dashArray.addElement(new Float(dash[i]));
            }
        }
        out.writeObject(dashArray);

        Vector strokeVector = new Vector();
        strokeVector.addElement(new Float(stroke.getLineWidth()));
        strokeVector.addElement(new Integer(stroke.getEndCap()));
        strokeVector.addElement(new Integer(stroke.getLineJoin()));
        strokeVector.addElement(new Float(stroke.getMiterLimit()));
        strokeVector.addElement(new Float(stroke.getDashPhase()));

        out.writeObject(strokeVector);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        // Read in colors...
        boolean exist = (boolean)in.readBoolean();
        if(exist)
            penPaint = (Paint)in.readObject();

        exist = (boolean)in.readBoolean();
        if(exist)
            fillPaint = (Paint)in.readObject();

        // Read in stroke...
        float[] dash = null;
        Vector dashArray = (Vector)in.readObject();
        if(dashArray.size() > 0) {
            dash = new float[dashArray.size()];
            for(int i=0;i<dashArray.size();i++) {
                dash[i] = ((Float)dashArray.elementAt(i)).floatValue();
            }
        }

        Vector strokeVector = (Vector)in.readObject();
        float width = ((Float)strokeVector.elementAt(0)).floatValue();
        int cap = ((Integer)strokeVector.elementAt(1)).intValue();
        int join = ((Integer)strokeVector.elementAt(2)).intValue();
        float miterlimit = ((Float)strokeVector.elementAt(3)).floatValue();
        float dash_phase = ((Float)strokeVector.elementAt(4)).floatValue();

        if(dash == null) {
            this.stroke = new BasicStroke(width, cap, join, miterlimit);
        } else {
            this.stroke = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
        }
    }
}
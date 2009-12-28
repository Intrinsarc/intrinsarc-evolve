/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.io.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.Vector;
import java.util.Iterator;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZPolygon</b> is a visual component for displaying a polygonal
 * shape.
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
public class ZPolygon extends ZCoordList {

    /**
     * Constructs a new ZPolygon with no points.
     */
    public ZPolygon() {
        super();
    }

    /**
     * Constructs a new ZPolygon with a single point.
     * @param <code>pt</code> Initial point
     */
    public ZPolygon(Point2D pt) {
        this(pt.getX(), pt.getY());
    }

    /**
     * Constructs a new ZPolygon with two points.
     * @param <code>pt1</code> First point
     * @param <code>pt2</code> Second point
     */
    public ZPolygon(Point2D pt1, Point2D pt2) {
        this(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
    }

    /**
     * Constructs a new ZPolygon with a single point.
     * @param <code>x,y</code> Initial point
     */
    public ZPolygon(double x, double y) {
        super();
        moveTo(x, y);
    }

    /**
     * Constructs a new ZPolygon with a two points
     * @param <code>x,y</code> First point
     * @param <code>x,y</code> Second point
     */
    public ZPolygon(double x1, double y1, double x2, double y2) {
        super();
        moveTo(x1, y1);
        lineTo(x2, y2);
    }

    /**
     * Constructs a new ZPolygon from an array of points.
     * @param <code>xp</code> Array of X points
     * @param <code>yp</code> Array of Y points
     */
    public ZPolygon(double[] xp, double[] yp) {
        super();
        setCoords(xp, yp);
    }

    /**
     * Constructs a new ZPolygon with no points.
     */
    public ZPolygon(ZPolyline aPolyline) {
        super();
        if (aPolyline.getNumberPoints() == 0) return;

        ZCoordListShape l = aPolyline.getCoordListShape();
        getCoordListShape().moveTo(l.getX(0), l.getY(0));

        for (int i = 1; i < aPolyline.getNumberPoints(); i++) {
            getCoordListShape().lineTo(l.getX(i), l.getY(i));
        }
        reshape();
    }

    /**
     * Get the shape defined by the coords.
     */
    protected ZCoordListShape getCoordListShape() {
        if (coordListShape == null) {
            coordListShape = new ZCoordListShape();
            coordListShape.setClosed(true);
        }
        return coordListShape;
    }
}
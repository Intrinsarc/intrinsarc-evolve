/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.geom.*;

/**
 * <b>ZLocator</b> is used to locate a point on a scene graph object in the coordinate system of
 * that scene graph object.
 *
 * @author Jesse Grosjean
 */
public class ZLocator {

    private ZSceneGraphObject sceneGraphObject;
    private double normal = 0.0f;

    /**
     * Create a new ZLocator that will locate points on aSceneGraphObject.
     */
    public ZLocator(ZSceneGraphObject aSceneGraphObject) {
        sceneGraphObject = aSceneGraphObject;
    }

    /**
     * Return the x coord of the located point.
     */
    public double getX() {
        return getSceneGraphObject().getBoundsReference().getCenterX();
    }

    /**
     * Return the y coord of the located point.
     */
    public double getY() {
        return getSceneGraphObject().getBoundsReference().getCenterY();
    }

    /**
     * Return the normal of the location.
     */
    public double getNormal() {
        return normal;
    }

    /**
     * Return the scene graph object that the locator is locating points on.
     */
    public ZSceneGraphObject getSceneGraphObject() {
        return sceneGraphObject;
    }

    /**
     * Get the located point. The located point will be stored in the aPoint parameter. If
     * that parameter is null a new point will be created and returned with the location.
     */
    public Point2D getPoint(Point2D aPoint) {
        if (aPoint == null) {
            aPoint = new Point2D.Double();
        }

        aPoint.setLocation(getX(), getY());
        return aPoint;
    }
}
/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import javax.swing.*;
import java.awt.geom.*;

/**
 * <b>ZBoundsLocator</b> is used to locate a points on the bounds of a scene graph
 * object in the coordinate system of that scene graph object.
 *
 * @author Jesse Grosjean
 */
public class ZBoundsLocator extends ZLocator {

    private double fOffset;
    private int fSide;

    /**
     * ZBoundsLocators should be created using the provided static factory methodes.
     */
    protected ZBoundsLocator(ZSceneGraphObject aSceneGraphObject, int aSide) {
        super(aSceneGraphObject);
        fSide = aSide;
    }

    /**
     * Return the x coord of the located point.
     */
    public double getX() {
        Rectangle2D aBounds = getSceneGraphObject().getBoundsReference();

        switch (fSide) {
            case SwingConstants.NORTH_WEST :
            case SwingConstants.SOUTH_WEST :
            case SwingConstants.WEST :
                return aBounds.getX();

            case SwingConstants.NORTH_EAST :
            case SwingConstants.SOUTH_EAST :
            case SwingConstants.EAST :
                return aBounds.getX() + aBounds.getWidth();

            case SwingConstants.NORTH :
            case SwingConstants.SOUTH :
                return aBounds.getX() + (aBounds.getWidth() / 2);
        }
        return -1;
    }

    /**
     * Return the y coord of the located point.
     */
    public double getY() {
        Rectangle2D aBounds = getSceneGraphObject().getBoundsReference();

        switch (fSide) {
            case SwingConstants.EAST :
            case SwingConstants.WEST :
                return aBounds.getY() + (aBounds.getHeight() / 2);

            case SwingConstants.SOUTH :
            case SwingConstants.SOUTH_WEST :
            case SwingConstants.SOUTH_EAST :
                return aBounds.getY() + aBounds.getHeight();

            case SwingConstants.NORTH_WEST :
            case SwingConstants.NORTH_EAST :
            case SwingConstants.NORTH :
                return aBounds.getY();
        }
        return -1;
    }

    /**
     * Create and return a new locator on the east side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createEastLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.EAST);
    }

    /**
     * Create and return a new locator on the north east side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createNorthEastLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.NORTH_EAST);
    }

    /**
     * Create and return a new locator on the north west side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createNorthWestLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.NORTH_WEST);
    }

    /**
     * Create and return a new locator on the north side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createNorthLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.NORTH);
    }

    /**
     * Create and return a new locator on the south side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createSouthLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.SOUTH);
    }

    /**
     * Create and return a new locator on the west side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createWestLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.WEST);
    }

    /**
     * Create and return a new locator on the south west side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createSouthWestLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.SOUTH_WEST);
    }

    /**
     * Create and return a new locator on the south east side of the aSceneGraphObject parameter.
     */
    public static ZBoundsLocator createSouthEastLocator(ZSceneGraphObject aSceneGraphObject) {
        return new ZBoundsLocator(aSceneGraphObject, SwingConstants.SOUTH_EAST);
    }
}
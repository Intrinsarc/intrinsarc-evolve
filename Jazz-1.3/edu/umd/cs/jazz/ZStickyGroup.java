/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;

/**
 * <b>ZStickyGroup</b>  is a constraint group that moves its children inversely to the
 * camera view, so that the children stay visually on the same place on
 * the screen, even as the camera view changes. There are two types of sticky constraints:
 * <ul>
 * <li><b>Sticky</b> constrains the children to be at the same place and size,
 * independent of the camera view.  Thus, as a particular
 * camera that looks at the component changes its magnification, the node changes
 * its transform by the inverse of the camera's transform  so it's position is not changed.
 * <li><b>Sticky Z</b> constrains the children to be rendered at a scale independent
 * of the camera scale. Thus, as a particular camera that looks at the component changes its
 * magnification, the component changes its size by the inverse of the camera's magnification
 * so it's magnification is not changed.  This is intended to be useful for map labels which
 * should stay over the relevant portion of the map, but should not change magnification.
 * </ul>
 * <p>
 * The simplest way to make an object sticky is to use the static makeSticky() method.
 * Alternatively, an application can make one manually by creating
 * a constraint group over a sub-tree that should be sticky.
 * The following code creates a sticky rectangle using this node.
 * <pre>
 *     ZRectangle rect;
 *     ZVisualLeaf leaf;
 *     ZStickyGroup sticky;
 *
 *     rect = new ZRectangle(0, 0, 50, 50);
 *     leaf = new ZVisualLeaf(rect);
 *     sticky = new ZStickyGroup(camera, leaf);
 *     layer.addChild(sticky);
 * </pre>
 *
 * <P>
 * {@link edu.umd.cs.jazz.util.ZSceneGraphEditor} provides a convenience mechanism to locate, create
 * and manage nodes of this type.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Benjamin B. Bederson
 */
public class ZStickyGroup extends ZConstraintGroup implements Serializable {
    /**
     * The type for a Sticky constraint
     */
    static public final int STICKY        = 1;

    /**
     * The type for a Sticky Z constraint
     */
    static public final int STICKYZ       = 2;

                                // Default values
    static public double stickyPointX_DEFAULT = 0.5;
    static public double stickyPointY_DEFAULT = 0.5;
    static public int constraintType_DEFAULT = STICKY;

    /**
     * The type of constraint that this node implements.
     */
    private int constraintType = constraintType_DEFAULT;

    /**
     * The X coord of the point of the child that will be fixed.
     */
    private double stickyPointX = stickyPointX_DEFAULT;

    /**
     * The Y coord of the point of the child that will be fixed.
     */
    private double stickyPointY = stickyPointY_DEFAULT;

    /**
     * Internal point used for temporary storage.
     */
    private transient Point2D pt = new Point2D.Double();

    /**
     * Constructs a new sticky group.
     */
    public ZStickyGroup() {
    }

    /**
     * Constructs a new sticky group with a specified camera.
     * @param camera The camera the node is related to.
     */
    public ZStickyGroup(ZCamera camera) {
        super();
        setCamera(camera);   // Need to set the camera after initialization so that internal defaults are set
    }

    /**
     * Constructs a new sticky group that decorates the specified child.
     * @param child The child that should go directly below this node.
     */
    public ZStickyGroup(ZNode child) {
        super(child);
    }

    /**
     * Constructs a new sticky group with a specified camera that decorates the specified child.
     * @param camera The camera the node is related to.
     * @param child The child that should go directly below this node.
     */
    public ZStickyGroup(ZCamera camera, ZNode child) {
        super(child);
        setCamera(camera);   // Need to set the camera after initialization so that internal defaults are set
    }

    //****************************************************************************
    //
    // Static methods
    //
    //***************************************************************************

    /**
     * Make the specified node sticky by adding a sticky node above the specified node.
     * It also inserts a transform node between the primary node and the sticky node,
     * since a sticky node's transform needs to be under the sticky node.
     * If the node is already sticky, then do nothing.
     * This manages the sticky node as a decorator as described in {@link ZGroup#setHasOneChild}.
     * <p>
     * If the node has a transform decorator, then the sticky node is added above the transform node.
     * @param node The node to make sticky
     * @param camera The camera that the node is sticky relative to
     * @param constraintType The constraint type for this node (STICKY or STICKYZ).
     * @return The ZStickyGroup that represents the sticky constraint
     */
    static public ZStickyGroup makeSticky(ZNode node, ZCamera camera, int constraintType) {
        ZGroup parent = node.getParent();
        ZStickyGroup sticky = null;
        ZSceneGraphEditor editor = node.editor();

                                // Check to see if this node already has a sticky node
        if (editor.hasStickyGroup()) {
            sticky = editor.getStickyGroup();
        }

                                // If there is one, and it is the wrong type, then remove it
        if ((sticky != null) && (sticky.getConstraintType() != constraintType)) {
            makeUnSticky(node);
            sticky = null;
        }
                                // Now we can make a new one
        if (sticky == null) {
                                // Then, apply the new constraint
            sticky = editor.getStickyGroup();
            sticky.setCamera(camera);
            sticky.setConstraintType(constraintType);
            ZTransformGroup transform = editor.getTransformGroup();
            transform.concatenate(sticky.getInverseTransform());
        }

        return sticky;
    }

    /**
     * Make the specified node unsticky.
     * If the node is not already sticky, then do nothing.
     * This manages the sticky node as a decorator as described in {@link ZNode}.
     * @param node The node to make unsticky
     */
    static public void makeUnSticky(ZNode node) {
        ZSceneGraphEditor editor = node.editor();
        ZStickyGroup sticky = null;

                                // First look for current sticky decorator
        if (editor.hasStickyGroup()) {
            sticky = editor.getStickyGroup();
                                // Then, if it is sticky, make it unsticky
            ZNode primary;
            ZNode[] children = sticky.getChildren();
            ZGroup newParent  = sticky.getParent();
            AffineTransform stickyTransform = sticky.getTransform();

            newParent.removeChild(sticky);
            for (int i=0; i<children.length; i++) {
                children[i].setParent(newParent);
                editor = children[i].editor();
                primary = editor.getNode();
                editor.getTransformGroup().concatenate(stickyTransform);
            }
        }
    }

    //****************************************************************************
    //
    //                  Get/Set and pairs
    //
    //***************************************************************************

    /**
     * Specifies a point on the unit square of the
     * sticky object that will remain fixed when the scene is zoomed
     * (for StickyZ only).
     * The coordinates range from upper left hand corner (0,0) of
     * the sticky object, to bottom right hand corner (1,1).
     * @param x X coordinate of the sticky point of the sticky object.
     * @param y Y coordinate of the sticky point of the sticky object.
     */
    public void setStickyPoint(double x, double y) {
        stickyPointX = x;
        stickyPointY = y;
        updateTransform();
    }

    /**
     * Returns a Dimension specifying a point on the sticky object
     * that remains fixed as the scene is zoomed (for StickyZ only).
     * @return the coordinates of the fixed point of the sticky object.
     */
    public Dimension getStickyPoint() {
        Dimension d = new Dimension();
        d.setSize(stickyPointX, stickyPointY);
        return d;
    }

    /**
     * Set the type of constraint that this node implements.
     * Must be STICKY or STICKYZ.
     * @param constraintType The constraint type for this node.
     */
    public void setConstraintType(int constraintType) {
        this.constraintType = constraintType;
        updateTransform();
    }

    /**
     * Determine the type of constraint that this node implements.
     * @return They constraint type.
     */
    public int getConstraintType() {
        return constraintType;
    }

    //****************************************************************************
    //
    //                  Other Methods
    //
    //****************************************************************************

    /**
     * Computes the constraint that defines the child to not move
     * even as the camera view changes.  This is called whenever
     * the camera view changes.  This should be overrided
     * by sub-classes defining constraints.
     * @return The new transform
     */
    public AffineTransform computeTransform() {
        AffineTransform at = null;

        switch (constraintType) {
        case STICKY:
            at = computeStickyTransform();
            break;
        case STICKYZ:
            at = computeStickyZTransform();
            break;
        }

        return at;
    }

    /**
     * Computes the Sticky constraint that defines the child to not move
     * even as the camera view changes.  This is called whenever
     * the camera view changes.  This should be overrided
     * by sub-classes defining constraints.
     * @return The new transform
     */
    public AffineTransform computeStickyTransform() {
        AffineTransform at = new AffineTransform();

        if (camera != null) {
            try {
                AffineTransform globalCoordFrame;
                if (parent == null) {
                    globalCoordFrame = new AffineTransform();
                } else {
                    globalCoordFrame = parent.getLocalToGlobalTransform();
                }
                at = globalCoordFrame.createInverse();
                at.concatenate(camera.getInverseViewTransformReference());
                at.concatenate(globalCoordFrame);
            } catch (NoninvertibleTransformException e) {
                                // Couldn't invert transform - not much we can do here.
                throw new ZNoninvertibleTransformException(e);
//              System.out.println("ZStickyDecorator.computeTransform: Can't compute transform inverse");
//              at = new AffineTransform();
            }
        }

        return at;
    }

    /**
     * Computes the Sticky Z constraint that defines the child to keep a constant magnification
     * even as the camera magnification changes.
     * @return the affine transform the defines the constraint.
     */
    protected AffineTransform computeStickyZTransform() {
        AffineTransform at = new AffineTransform();

        if (camera != null) {
            double iscale = 1.0 / camera.getMagnification();
            ZBounds childrenBounds = new ZBounds();

            ZNode[] childrenRef = getChildrenReference();
            for (int i=0; i<children.size(); i++) {
                childrenBounds.add(childrenRef[i].getBoundsReference());
            }

                                // Compute the position of the child so that its center point
                                // stays fixed, and it is scaled by the inverse of the camera
                                // magnification.

                                // This is computed by getting the "fixed" point that is not
                                // supposed to move, and creating a transform that scales
                                // around that point by the inverse of the current camera magnification.
            pt.setLocation((childrenBounds.getX() + (stickyPointX * childrenBounds.getWidth())),
                            (childrenBounds.getY() + (stickyPointY * childrenBounds.getHeight())));

            at.translate(pt.getX(), pt.getY());
            at.scale(iscale, iscale);
            at.translate(-pt.getX(), -pt.getY());
        }

        return at;
    }

    /**
     * Generate a string that represents this object for debugging.
     * @return the string that represents this object for debugging
     * @see ZDebug#dump
     */
    public String dump() {
        String str = super.dump();

        switch (constraintType) {
        case STICKY:
            str += "\n Sticky constraint";
            break;
        case STICKYZ:
            str += "\n Sticky-Z constraint";
            break;
        }

        return str;
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

        if (constraintType != constraintType_DEFAULT) {
            out.writeState("int", "constraintType", constraintType);
        }
        if (stickyPointX != stickyPointX_DEFAULT) {
            out.writeState("double", "stickyPointX", stickyPointX);
        }
        if (stickyPointY != stickyPointY_DEFAULT) {
            out.writeState("double", "stickyPointY", stickyPointY);
        }
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

        if (fieldName.compareTo("constraintType") == 0) {
            constraintType = ((Integer)fieldValue).intValue();
        } else if (fieldName.compareTo("stickyPointX") == 0) {
            stickyPointX = ((Double)fieldValue).doubleValue();
        } else if (fieldName.compareTo("stickyPointY") == 0) {
            stickyPointY = ((Double)fieldValue).doubleValue();
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        pt = new Point2D.Double();
    }
}
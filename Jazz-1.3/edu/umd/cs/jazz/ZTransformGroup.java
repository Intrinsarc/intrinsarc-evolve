/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import edu.umd.cs.jazz.animation.ZAlpha;
import edu.umd.cs.jazz.animation.ZTransformAnimation;
import edu.umd.cs.jazz.event.ZNodeEvent;
import edu.umd.cs.jazz.event.ZNodeListener;
import edu.umd.cs.jazz.event.ZTransformEvent;
import edu.umd.cs.jazz.event.ZTransformListener;
import edu.umd.cs.jazz.io.ZObjectOutputStream;
import edu.umd.cs.jazz.io.ZSerializable;
import edu.umd.cs.jazz.util.ZBounds;
import edu.umd.cs.jazz.util.ZDebug;
import edu.umd.cs.jazz.util.ZLerp;
import edu.umd.cs.jazz.util.ZNoninvertibleTransformException;
import edu.umd.cs.jazz.util.ZRenderContext;
import edu.umd.cs.jazz.util.ZSceneGraphPath;

/**
 * <b>ZTransformGroup</b> is a group node that specifies an arbitrary affine transform.
 * The transform applies to all the children of this node.
 * In addition, it provides some extra manipulators
 * on the transform.  This provides the ability to relatively or absolutely change
 * the translation, scale, rotation and shear of the transform - either immediately,
 * or animated over time.
 * <p>
 * In order to use transforms effectively, it is important to have a solid understanding
 * of affine transforms, matrix multiplication, and the standard use of matrices for
 * graphics coordinate systems.  The best place to start is by reading the documentation
 * on {@link java.awt.geom.AffineTransform}.  After that, a good next bit is to read
 * a standard 3D graphics text such as "Interactive Computer Graphics: a Top-Down Approach
 * with OpenGL" 2nd Edition by Angel (Addison-Wesley), or "Computer Graphics: Principles
 * and Practice, Second Edition in C" by Foley, van Dam, Feiner, Hughes, and Phillips
 * (Addison-Wesley).
 * <p>
 * Here is a very short lesson on matrices and graphics.  A 2D affine transform is typically
 * represented by a 3x3 matrix which we typically call M, and sometimes denote [M].  An
 * affine transform can represent any 2D translation, scale, rotation, shear or any combination
 * of these 4 operators.  Matrices which are pure translations, scales, and rotations are
 * typically denoted T, S, or R ([T], [S], or [R]), respectively.  The identity matrix
 * is typically denoted I or [I].
 * <p>
 * The reason these transforms are so powerful is because they can be combined in a
 * semantically straightforward way by simply concatenating the matrices.  Creating
 * a transform results in I.  Calling one of the transformation methods on a transform is exactly equivalent to
 * concatenating the transform with a new transform that specifies the transformation.
 * Thus, transform.translate(dx, dy) is equivalent to [M][T] where M represents the original
 * transform, and T represents the translation matrix of dx, dy.  Similarly, transform.scale(ds)
 * is equivalent to [M][S].  And, these build up, so
 * <pre>
 *    ZTransformGroup t = new ZTransformGroup();
 *    t.scale(ds);
 *    t.translate(dx, dy);
 *    t.rotate(Math.PI * 0.5);
 * </pre>
 * is equivalent to generating a node with  transform of these four matrices concatenated together with standard
 * matrix multiplication [I][S][T][R]
 * <p>
 * However, a crucial place for confusion with these transforms is that the transforms
 * get applied in the <em>reverse</em> order of how you placed the calls to the transforms
 * in your code.  To understand this, you must realize that the object paint methods
 * get called <em>after</em> the transformations are applied.  Your paint methods specify
 * geometry (such as points) which get transformed by the current transformation before
 * being painted.  For a simple example, think of a point P.  Well, the point
 * post-multiplies the current transformation.  In the example above, that works out
 * to a new point P' being computed as P' = [S][T][R]P.  You can think about this
 * as the original point P first getting multiplied (on the left) by R, then the result
 * gets multipled (on the left) by T, and that gets multiplied (on the left) by S.
 * <p>
 * Let's go through a simple example with actual numbers.  Suppose you want to take a
 * rectangle at (0, 0) with width 50 and height 50, and first translate it 50 units to the right,
 * and then scale the whole thing by 2 about the origin.
 * The result should be that the rectangle actually gets rendered at (100, 0) with dimensions
 * of (100x100).  The following code in Jazz implements this example.
 * <pre>
 *    ZRectangle rect = new ZRectangle(0, 0, 50, 50);
 *    ZVisualLeaf leaf = new ZVisualLeaf(rect);
 *    ZTransformGroup node = new ZTransformGroup();
 *    node.addChild(leaf);
 *    layer.addChild(node);
 *
 *                              // Note how we call scale first even though
 *                              // the translation will actually be applied before the scale.
 *    node.scale(2);
 *    node.translate(50, 0);
 * </pre>
 * <p>
 * Sometimes it is useful to transform an object in global coordinates - even though
 * that object has a transform of its own.  For instance, suppose you are implementing an
 * event handler for selection, and want to move an object so that it follows the pointer.
 * In order to do this, you need to translate the object in <em>global</em> coordinates.
 * Since the node you want to move may have a transform already (for instance, it may be scaled),
 * if you simply translate the object, that transform will be applied <em>after</em> the
 * scale, and thus the translation will be modified by the scale.  That is, the object
 * may have the matrix [M].  Calling translate will generate [M][T].  If M represents a scale of 2,
 * then the translation will actually translate twice as much as you intended.
 * <p>
 * The solution is to convert your translation into the local coordinate system of the object.
 * Since there this node is actually part of a tree, there coul be other transforms as
 * well, not to mention the camera transform.  So, the goal is to take the amount you want
 * to translate the object in the original coordinate system (in this case the window coords),
 * and convert it into the object's local coordinate system.  We do this by building up the
 * concatenation of all the transforms from the window to the node, taking the inverse of
 * that matrix, and finally transform the translation by the resulting inverse matrix.
 * Jazz provides a utility method to make this easier.  So, the resulting code would
 * look like this.  It takes a desired translation in window coordinates, and uses
 * the utility method ZCamera.cameraToLocal to convert it to the local coordinate
 * system of the specified node, and finally translates the node by the resulting amount.
 * <pre>
 *    Point2D pt = new Point2D.Double(x, y);
 *    camera.cameraToLocal(pt, node);
 *    node.translate(pt.getX(), pt.getY());
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
 * @see java.awt.geom.AffineTransform
 * @author  Benjamin B. Bederson
 */
public class ZTransformGroup extends ZGroup implements ZSerializable, ZTransformable, Serializable {
	
	/**
	 * Jazz animation framework was added after ZTransformGroups animate methods
	 * were coded. This flag lets you specify that the animate methods should
	 * use the animation framework, by default they will still use the original
	 * implementation. These two methods differ in a significant way, the old
	 * implementation animates in a hard loop that blocks until the animation
	 * finished, while the animation framework is timer based so it is non-
	 * blocking.
	 */
	public static boolean ANIMATE_METHODS_USE_ANIMATION_FRAMEWORK = false;
	
                                // The transform that this node represents.
    private AffineTransform transform;
                                // The inverse of the transform
    private transient AffineTransform inverseTransform = null;
                                // A dirty bit specifying if the inverse transform is up to date.
    private boolean inverseTransformDirty = true;

                                // Some thangs that get reused.
                                // Define here for efficiency.
    private transient AffineTransform tmpTransform;
    private transient ZBounds   paintBounds;
    private transient Rectangle2D tmpRect;
    static private double[] pts = new double[8];

    //****************************************************************************
    //
    //                 Constructors
    //
    //***************************************************************************

    /**
     * Constructs an empty ZTransformGroup.
     */
    public ZTransformGroup() {
        tmpTransform = new AffineTransform();
        transform = new AffineTransform();
        paintBounds = new ZBounds();
        tmpRect = new Rectangle2D.Double();
    }

    /**
     * Constructs a new transform group node with the specified node as a child of the
     * new group.  If the specified child was already a member of a tree (i.e., had a parent),
     * then this new node is inserted in the tree above the child so that the original
     * child is still in that tree, but with this node inserted in the middle of the tree.
     * If the specified child does not have a parent, then it is just made a child of this node.
     *
     * @param child Child of the new group node.
     */
    public ZTransformGroup(ZNode child) {
        tmpTransform = new AffineTransform();
        transform = new AffineTransform();
        paintBounds = new ZBounds();
        tmpRect = new Rectangle2D.Double();
        insertAbove(child);
    }

    /**
     * Returns a clone of this object.
     *
     * @see ZSceneGraphObject#duplicateObject
     */
    protected Object duplicateObject() {
        ZTransformGroup newTransform = (ZTransformGroup)super.duplicateObject();

        newTransform.transform = getTransform(); // Get a copy
        newTransform.tmpTransform = new AffineTransform();
        newTransform.inverseTransform = new AffineTransform();
        newTransform.inverseTransformDirty = true;
        newTransform.paintBounds = new ZBounds();
        newTransform.tmpRect = new Rectangle2D.Double();

        return newTransform;
    }

    //****************************************************************************
    //
    // Painting and Bounds related methods
    //
    //***************************************************************************

    /**
     * Renders this node which results in its children getting painted.
     * <p>
     * The transform, clip, and composite will be set appropriately when this object
     * is rendered.  It is up to this object to restore the transform, clip, and composite of
     * the Graphics2D if this node changes any of them. However, the color, font, and stroke are
     * unspecified by Jazz.  This object should set those things if they are used, but
     * they do not need to be restored.
     * <P>
     * This transform node applies its transform before painting its children.
     *
     * @param renderContext The graphics context to use for rendering.
     */
    public void render(ZRenderContext renderContext) {
        Graphics2D      g2 = renderContext.getGraphics2D();
        ZBounds         visibleBounds = renderContext.getVisibleBounds();
        AffineTransform saveTransform = g2.getTransform();

                                // Modify the current visible bounds by the inverse transform
        paintBounds.reset();
        paintBounds.add(visibleBounds);
        paintBounds.transform(getInverseTransform());
        renderContext.pushVisibleBounds(paintBounds);

                                // Apply this transform to the transform
        g2.transform(transform);

                                // Paint children
        super.render(renderContext);

                                // Restore state
        g2.setTransform(saveTransform);
        renderContext.popVisibleBounds();
    }

    /**
     * Recomputes and caches the bounds for this node.  Generally this method is
     * called by reshape when the bounds have changed, and it should rarely
     * directly elsewhere.  A ZTransformGroup bounds is the union
     * of its children's bounds, transformed by this node's transform.
     */
    protected void computeBounds() {
        super.computeBounds();
        transform(bounds, transform);
        fireGlobalBoundsEventForSubTree(this);
    }

    /**
     * Recursively descends the subtree rooted at <code>node</code> to fire
     * global bounds change events.  This uses a depth first search.
     * @param node The top node whose subtree is to be examined
     */
    private void fireGlobalBoundsEventForSubTree(ZNode node) {
        // For the transform group that originated this event, the
        // ZNode.updateBounds method will fire the event for this node
        // so just send it on to the children
        if (node == this) {
            ZNode[] childrenRef = getChildrenReference();
            for(int i=0; i<children.size(); i++) {
                if (childrenRef[i].hasNodeListener()) {
                    fireGlobalBoundsEventForSubTree(childrenRef[i]);
                }
            }
        }
        // This is any other group so we need to fire the event for this node
        // and its children
        else if (node instanceof ZGroup) {
            ZGroup group = (ZGroup) node;

            if (group.hasListenerOfType(ZNodeListener.class)) {
                group.fireEvent(ZNodeEvent.createGlobalBoundsChangedEvent(group));
            }
            ZNode[] childrenRef = group.getChildrenReference();
            for(int i = 0; i< group.getNumChildren(); i++) {
                if (childrenRef[i].hasNodeListener()) {
                    fireGlobalBoundsEventForSubTree(childrenRef[i]);
                }
            }
        }
        // Any other non-group nodes - just fire the event for the node
        else {
            if (node.hasListenerOfType(ZNodeListener.class)) {
                node.fireEvent(ZNodeEvent.createGlobalBoundsChangedEvent(node));
            }
        }
    }

    /**
     * Method to pass repaint methods up the tree.  Repaints only the sub-
     * portion of this object specified by the given ZBounds.
     * Note that the input parameter may be modified as a result of this call.
     * @param repaintBounds The bounds to repaint
     */
    public void repaint(ZBounds repaintBounds) {
        if (ZDebug.debug && ZDebug.debugRepaint) {
            System.out.println("ZNode.repaint(ZBounds): this = " + this);
            System.out.println("ZNode.repaint(ZBounds): bounds = " + repaintBounds);
        }

        if (!inTransaction && parent != null) {
            transform(repaintBounds, transform);
            parent.repaint(repaintBounds);
        }
    }

    /**
     * Overrides ZSceneGraphObject.reshape to invert the transform on the
     * when the this ZTransformGroup is volatile
     */
    public void reshape() {
        // Fix suggested by David Wang.
        // Directly repaints the bounds when volatile
        if (!inTransaction && getVolatileBounds()) {
            ZBounds tBounds = (ZBounds)bounds.clone();
            transform(tBounds,getInverseTransform());
            repaint(tBounds);
        } else {
            repaint();
        }

        updateBounds();
        repaint();
    }

    //****************************************************************************
    //
    //                  Other Methods
    //
    //****************************************************************************

    /**
     * Returns the first object under the specified rectangle (if there is one)
     * in the subtree rooted with this as searched in reverse (front-to-back) order.
     * This performs a depth-first search, first picking children.
     * Only returns a node if this is "pickable".
     * @param rect Coordinates of pick rectangle in local coordinates
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return The picked node, or null if none
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
        if (isPickable()) {
            path.pushTransformer(this);

                // Concatenate this object's transform with the one stored in the path
            AffineTransform origTm = path.getTransform();
            AffineTransform tm = new AffineTransform(origTm);
            tm.concatenate(transform);
            path.setTransform(tm);

                // Convert the rect from parent's coordinate system to local coordinates
            tmpRect.setRect(rect);
            transform(tmpRect, getInverseTransform());

                // Use super's pick method
            if (super.pick(tmpRect, path)) {
                if (!getChildrenPickable()) {
                        // Subtle point:
                        // the path's transform may be reflecting the transform to the child that
                        // was picked, rather than the transform to this node. Reset the
                        // transform.
                    path.setTransform(tm);
                }
                return true;
            }

                // Restore transform
            if (!path.getCameraFound()) {
                path.setTransform(origTm);
                path.popTransformer(this);
            }
        }

        return false;
    }

    //****************************************************************************
    //
    // Event methods
    //
    //***************************************************************************

    /**
     * Adds the specified transform listener to receive transform events from this node
     *
     * @param l the transform listener
     */
    public void addTransformListener(ZTransformListener l) {
        getListenerList().add(ZTransformListener.class, l);
    }

    /**
     * Removes the specified transform listener so that it no longer
     * receives transform events from this transform.
     *
     * @param l the transform listener
     */
    public void removeTransformListener(ZTransformListener l) {
        removeEventListener(ZTransformListener.class, l);
    }

    //****************************************************************************
    //
    // Transform methods
    //
    //***************************************************************************

    /**
     * Returns a copy of the transform that that this node specifies.
     * @return The current transform.
     */
    public AffineTransform getTransform() {
        return (AffineTransform) getTransformReference().clone();
    }

    /**
     * Returns a reference to the transform that that this node specifies.
     * @return A reference to the current transform.
     */
    public AffineTransform getTransformReference() {
        return transform;
    }

    /**
     * Retrieves the 6 specifiable values in the affine transformation,
     * and places them into an array of double precisions values.
     * The values are stored in the array as
     * {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;m02&nbsp;m12&nbsp;}.
     * An array of 4 doubles can also be specified, in which case only the
     * first four elements representing the non-transform
     * parts of the array are retrieved and the values are stored into
     * the array as {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;}
     * @param flatmatrix the double array used to store the returned
     * values.
     */
    public void getMatrix(double[] flatmatrix) {
        transform.getMatrix(flatmatrix);
    }

    /**
     * Sets the transform associated with this node.
     * This transform applies to the sub-tree rooted at this node.
     * @param newTransform
     */
    public void setTransform(AffineTransform newTransform) {
        AffineTransform origTransform = transform;

        if (transform == null) {
            transform = new AffineTransform();
        }

        transform.setTransform(newTransform);
        inverseTransformDirty = true;

        if (hasListenerOfType(ZTransformListener.class)) {
            fireEvent(ZTransformEvent.createTransformChangedEvent(this, origTransform));
        }

        tmpTransform = origTransform;    // Reuse previous transform for internal temporary transform
        reshape();
    }

    /**
     * Sets the transform associated with this node.
     * This transform applies to the sub-tree rooted at this node.
     * @param m00,&nbsp;m01,&nbsp;m02,&nbsp;m10,&nbsp;m11,&nbsp;m12 the
     * 6 values that compose the 3x3 transformation matrix
     */
    public void setTransform(double m00, double m10,
                             double m01, double m11,
                             double m02, double m12) {
        tmpTransform.setTransform(m00, m10, m01, m11, m02, m12);
        setTransform(tmpTransform);
    }

	/**
	 * Sets the transform reference associated with this node. This is
	 * different then the other setTransform methods because they set the
	 * transform using this.transform.setTransform(newTransform). This
	 * method uses this.transform = newTransform, and can be useful if
	 * you want the ZTransformGroup to use your own subclass of AffineTransform
	 * internally.
	 */
	public void setTransformReference(AffineTransform newTransform) {
		transform = newTransform;
		setTransform(newTransform);
	}

    /**
     * Concatenates an AffineTransform <code>at</code> to this node's transform in
     * the standard way.  This has the affect of applying <code>at</code> in this
     * node's local coordinate system.
     * @param at The transform to concatenate
     * @see #preConcatenate
     */
    public void concatenate(AffineTransform at) {
        tmpTransform.setTransform(transform);
        tmpTransform.concatenate(at);
        setTransform(tmpTransform);
    }

    /**
     * Pre-Concatenates an AffineTransform <code>at</code> to this node's transform in a less commonly
     * used way such that <code>at</code> gets pre-multipled with the existing transform rather
     * than the more normal post-multiplication.  This has the affect of applying the transform <code>at</code>
     * in the coordinate system above this node, rather than within the local coordinate system
     * of this node.
     * @param at The transform to pre-concatenate
     * @see #concatenate
     */
    public void preConcatenate(AffineTransform at) {
        tmpTransform.setTransform(transform);
        tmpTransform.preConcatenate(at);
        setTransform(tmpTransform);
    }

    /**
     * Return the transform that converts local coordinates at this node to global coordinates
     * at the root node.
     * @return The concatenation of transforms from the root down to this node.
     */
    public AffineTransform getLocalToGlobalTransform() {
        AffineTransform result = null;

        if (parent != null) {
            result = parent.getLocalToGlobalTransform();
            result.concatenate(transform);
        } else {
            result = (AffineTransform) transform.clone();
        }

        return result;
    }

    /**
     * Internal method to compute the inverse transform based on the transform.
     * This gets called from within ZTransformGroup
     * whenever the inverse transform cache has been invalidated,
     * and it is needed.
     */
    protected void computeInverseTransform() {
        try {
            inverseTransform = transform.createInverse();
            inverseTransformDirty = false;
        } catch (NoninvertibleTransformException e) {
            throw new ZNoninvertibleTransformException(e);
        }
    }

    /**
     * Returns the inverse of the transform associated with this node.
     * @return The current inverse transform.
     */
    public AffineTransform getInverseTransform() {
        if (inverseTransformDirty) {
            computeInverseTransform();
        }
        return inverseTransform;
    }

    /**
     * Returns the current translation of this node
     * @return the translation
     */
    public Point2D getTranslation() {
        return new Point2D.Double(transform.getTranslateX(), transform.getTranslateY());
    }

    /**
     * Returns the current X translation of this node
     * @return the translation
     */
    public double getTranslateX() {
        return transform.getTranslateX();
    }

    /**
     * Sets the current X translation of this node
     */
    public void setTranslateX(double x) {
        setTranslation(x, getTranslateY());
    }

    /**
     * Returns the current Y translation of this node
     * @return the translation
     */
    public double getTranslateY() {
        return transform.getTranslateY();
    }

    /**
     * Sets the current Y translation of this node
     */
    public void setTranslateY(double y) {
        setTranslation(getTranslateX(), y);
    }

    /**
     * Translate the node by the specified deltaX and deltaY
     * @param dx X-coord of translation
     * @param dy Y-coord of translation
     */
    public void translate(double dx, double dy) {
        tmpTransform.setTransform(transform);
        tmpTransform.translate(dx, dy);
        setTransform(tmpTransform);
    }

    /**
     * Animate the node from its current position by the specified deltaX and deltaY
     * @param dx X-coord of translation
     * @param dy Y-coord of translation
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void translate(double dx, double dy, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        tmpTransform.translate(dx, dy);
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * Translate the node to the specified position
     * @param x X-coord of translation
     * @param y Y-coord of translation
     */
    public void setTranslation(double x, double y) {
        double[] mat = new double[6];
        transform.getMatrix(mat);
        mat[4] = x;
        mat[5] = y;
        setTransform(mat[0], mat[1], mat[2], mat[3], mat[4], mat[5]);
    }

    /**
     * Animate the node from its current position to the position specified
     * by x, y
     * @param x X-coord of translation
     * @param y Y-coord of translation
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void setTranslation(double x, double y, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        double[] mat = new double[6];

        tmpTransform.translate(x, y);
        tmpTransform.getMatrix(mat);
        mat[4] = x;
        mat[5] = y;
        tmpTransform.setTransform(mat[0], mat[1], mat[2], mat[3], mat[4], mat[5]);
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * Returns the current scale of this transform.
     * Note that this is implemented by applying the transform to a diagonal
     * line and returning the length of the resulting line.  If the transform
     * is sheared, or has a non-uniform scaling in X and Y, the results of
     * this method will be ill-defined.
     * @return the scale
     */
    public double getScale() {
        return computeScale(transform);
    }

    /**
     * Scale the node from its current scale to the scale specified
     * by muliplying the current scale and dz.
     * @param dz scale factor
     */
    public void scale(double dz) {
        tmpTransform.setTransform(transform);
        tmpTransform.scale(dz, dz);
        setTransform(tmpTransform);
    }

    /**
     * Scale the node around the specified point (x, y)
     * from its current scale to the scale specified
     * by muliplying the current scale and dz.
     * @param dz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     */
    public void scale(double dz, double x, double y) {
        tmpTransform.setTransform(transform);
        tmpTransform.translate(x, y);
        tmpTransform.scale(dz, dz);
        tmpTransform.translate(-x, -y);
        setTransform(tmpTransform);
    }

    /**
     * Animate the node from its current scale to the scale specified
     * by muliplying the current scale and deltaZ
     * @param dz scale factor
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void scale(double dz, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        tmpTransform.scale(dz, dz);
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * Animate the node around the specified point (x, y)
     * from its current scale to the scale specified
     * by muliplying the current scale and dz
     * @param dz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void scale(double dz, double x, double y, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        tmpTransform.translate(x, y);
        tmpTransform.scale(dz, dz);
        tmpTransform.translate(-x, -y);
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * Sets the scale of the transform
     * @param the new scale
     */
    public void setScale(double finalz) {
        double dz = finalz / getScale();
        scale(dz);
    }

    /**
     * Set the scale of the node to the specified target scale,
     * scaling the node around the specified point (x, y).
     * @param finalz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     */
    public void setScale(double finalz, double x, double y) {
        double dz = finalz / getScale();
        scale(dz, x, y);
    }

    /**
     * Animate the node from its current scale to the specified target scale.
     * @param finalz scale factor
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void setScale(double finalz, int millis, ZDrawingSurface surface) {
        double dz = finalz / getScale();
        scale(dz, millis, surface);
    }

    /**
     * Animate the node around the specified point (x, y)
     * to the specified target scale.
     * @param finalz scale factor
     * @param x X coordinate of the point to scale around
     * @param y Y coordinate of the point to scale around
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void setScale(double finalz, double x, double y, int millis, ZDrawingSurface surface) {
        double dz = finalz / getScale();
        scale(dz, x, y, millis, surface);
    }

    /**
     * Returns the current rotation of this node
     * @return the rotation angle in radians.
     */
    public double getRotation() {
        Point2D p1 = new Point2D.Double(0.0, 0.0);
        Point2D p2 = new Point2D.Double(1.0, 0.0);
        transform.transform(p1, p1);
        transform.transform(p2, p2);

        double dy = (p2.getY() - p1.getY());
        double l = (p1.distance(p2));

        double rotation = Math.asin(dy / l);

        return rotation;
    }

    /**
     * Set the absolute rotation of this node.
     * @param theta angle to rotate (in radians)
     */
    public void setRotation(double theta) {
        rotate(theta - getRotation());
    }

    /**
     * Set the absolute rotation of this node, animating the change over time.
     * @param theta angle to rotate (in radians)
     * @param millis Time to animate scale in milliseconds
     * @param surface The surface to updated during animation.
     */
    public void setRotation(double theta, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        tmpTransform.rotate(theta - getRotation());
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * Set the absolute rotation of this node, rotating around the specified anchor point.
     * @param theta angle to rotate (in radians)
     * @param xctr X-coord of anchor point
     * @param yctr Y-coord of anchor point
     */
    public void setRotation(double theta, double xctr, double yctr) {
        rotate(theta - getRotation(), xctr, yctr);
    }

    /**
     * Set the absolute rotation of this node, via animation, theta radians about the specified anchor point.
     * @param theta angle to rotate (in radians)
     * @param xctr X-coord of anchor point
     * @param yctr Y-coord of anchor point
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
     public void setRotation(double theta, double xctr, double yctr, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        tmpTransform.rotate(theta - getRotation(), xctr, yctr);
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * Rotate the node by the specified amount
     * @param theta angle to rotate (in radians)
     */
    public void rotate(double theta) {
        tmpTransform.setTransform(transform);
        tmpTransform.rotate(theta);
        setTransform(tmpTransform);
    }

    /**
     * Rotate the node by the specified amount around the specified anchor point
     * @param theta angle to rotate (in radians)
     * @param xctr X-coord of anchor point
     * @param yctr Y-coord of anchor point
     */
    public void rotate(double theta, double xctr, double yctr) {
        tmpTransform.setTransform(transform);
        tmpTransform.rotate(theta, xctr, yctr);
        setTransform(tmpTransform);
    }

    /**
     * Rotate the node, via animation, theta radians
     * @param theta angle to rotate (in radians)
     * @param millis Time to animate scale in milliseconds
     * @param surface The surface to updated during animation.
     */
    public void rotate(double theta, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        tmpTransform.rotate(theta);
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * Rotate the node, via animation, theta radians about the specified anchor point
     * @param theta angle to rotate (in radians)
     * @param xctr X-coord of anchor point
     * @param yctr Y-coord of anchor point
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
     public void rotate(double theta, double xctr, double yctr, int millis, ZDrawingSurface surface) {
        tmpTransform.setTransform(transform);
        tmpTransform.rotate(theta, xctr, yctr);
        animate(this, tmpTransform, millis, surface);
    }

    /**
     * This will calculate the necessary transform in order to make this
     * node appear at a particular position relative to the
     * specified node.  The source point specifies a point in the
     * unit square (0, 0) - (1, 1) that represents an anchor point on the
     * corresponding node to this transform.  The destination point specifies
     * an anchor point on the reference node.  The position method then
     * computes the transform that results in transforming this node so that
     * the source anchor point coincides with the reference anchor
     * point. This can be useful for layout algorithms as it is
     * straightforward to position one object relative to another.
     * <p>
     * For example, If you have two nodes, A and B, and you call
     * <PRE>
     *     Point2D srcPt = new Point2D.Double(1.0, 0.0);
     *     Point2D destPt = new Point2D.Double(0.0, 0.0);
     *     A.position(srcPt, destPt, B, 750, null);
     * </PRE>
     * The result is that A will move so that its upper-right corner is at
     * the same place as the upper-left corner of B, and the transition will
     * be smoothly animated over a period of 750 milliseconds.
     * @param srcPt The anchor point on this transform's node (normalized to a unit square)
     * @param destPt The anchor point on destination bounds (normalized to a unit square)
     * @param destBounds The bounds used to calculate this transform's node
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to be updated during animation.
     */
    public void position(Point2D srcPt, Point2D destPt, ZNode refNode, int millis, ZDrawingSurface surface) {
        position(srcPt, destPt, refNode.getGlobalBounds(), millis, surface);
    }

    /**
     * This will calculate the necessary transform in order to make this
     * node appear at a particular position relative to the
     * specified bounding box.  The source point specifies a point in the
     * unit square (0, 0) - (1, 1) that represents an anchor point on the
     * corresponding node to this transform.  The destination point specifies
     * an anchor point on the reference node.  The position method then
     * computes the transform that results in transforming this node so that
     * the source anchor point coincides with the reference anchor
     * point. This can be useful for layout algorithms as it is
     * straightforward to position one object relative to another.
     * <p>
     * For example, If you have two nodes, A and B, and you call
     * <PRE>
     *     Point2D srcPt = new Point2D.Double(1.0, 0.0);
     *     Point2D destPt = new Point2D.Double(0.0, 0.0);
     *     A.position(srcPt, destPt, B.getGlobalBounds(), 750, null);
     * </PRE>
     * The result is that A will move so that its upper-right corner is at
     * the same place as the upper-left corner of B, and the transition will
     * be smoothly animated over a period of 750 milliseconds.
     * @param srcPt The anchor point on this transform's node (normalized to a unit square)
     * @param destPt The anchor point on destination bounds (normalized to a unit square)
     * @param destBounds The bounds (in global coordinates) used to calculate this transform's node
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    public void position(Point2D srcPt, Point2D destPt, Rectangle2D destBounds, int millis, ZDrawingSurface surface) {
        double srcx, srcy;
        double destx, desty;
        double dx, dy;
        Point2D pt1, pt2;

        if (parent != null) {
                                // First compute translation amount in global coordinates
            Rectangle2D srcBounds = getGlobalBounds();
            srcx  = lerp(srcPt.getX(),  srcBounds.getX(),  srcBounds.getX() +  srcBounds.getWidth());
            srcy  = lerp(srcPt.getY(),  srcBounds.getY(),  srcBounds.getY() +  srcBounds.getHeight());
            destx = lerp(destPt.getX(), destBounds.getX(), destBounds.getX() + destBounds.getWidth());
            desty = lerp(destPt.getY(), destBounds.getY(), destBounds.getY() + destBounds.getHeight());

                                // Convert vector to local coordinates
            pt1 = new Point2D.Double(srcx, srcy);
            globalToLocal(pt1);
            pt2 = new Point2D.Double(destx, desty);
            globalToLocal(pt2);
            dx = (pt2.getX() - pt1.getX());
            dy = (pt2.getY() - pt1.getY());

                                // Finally, animate change
            tmpTransform.setTransform(transform);
            tmpTransform.translate(dx, dy);
            animate(this, tmpTransform, millis, surface);
        }
    }

    //****************************************************************************
    //
    // Static methods
    //
    //***************************************************************************

    /**
     * Given an AffineTransform, this returns the
     * "scale" of the transform. The scale of an affine transform
     * is computed by transforming a vector and seeing how its length changes.
     */
    static public double computeScale(AffineTransform at) {
        Point2D pt1 = new Point2D.Double(0.0, 0.0);
        Point2D pt2 = new Point2D.Double(1.0, 1.0);
        double origDist = pt1.distance(pt2);
        at.transform(pt1, pt1);
        at.transform(pt2, pt2);
        double mag = (pt1.distance(pt2) / origDist);
        return mag;
    }

    /**
     * Linearly interpolates between a and b, based on t.
     * Specifically, it computes lerp(a, b, t) = a + t*(b - a).
     * This produces a result that changes from a (when t = 0) to b (when t = 1).
     *
     * @param a from point
     * @param b to Point
     * @param t variable 'time' parameter
     */
    static public double lerp(double t, double a, double b) {
        return (a + t * (b - a));
    }

    /**
     * Apply the specified transform to the specified bounds, modifying the bounds.
     * @param bounds The bounds to be transformed
     * @param at The transform to use to transform the rectangle
     */
    static public void transform(ZBounds bounds, AffineTransform at) {
        if (!bounds.isEmpty()) {
            transform((Rectangle2D)bounds, at);
        }
    }

    /**
     * Apply the specified transform to the specified rectangle, modifying the rect.
     * @param rect The rectangle to be transformed
     * @param at The transform to use to transform the rectangle
     */
    static public void transform(Rectangle2D rect, AffineTransform at) {
                                // First, transform all 4 corners of the rectangle
        pts[0] = rect.getX();          // top left corner
        pts[1] = rect.getY();
        pts[2] = rect.getX() + rect.getWidth();  // top right corner
        pts[3] = rect.getY();
        pts[4] = rect.getX() + rect.getWidth();  // bottom right corner
        pts[5] = rect.getY() + rect.getHeight();
        pts[6] = rect.getX();          // bottom left corner
        pts[7] = rect.getY() + rect.getHeight();
        at.transform(pts, 0, pts, 0, 4);

                                // Then, find the bounds of those 4 transformed points.
        double minX = pts[0];
        double minY = pts[1];
        double maxX = pts[0];
        double maxY = pts[1];
        int i;
        for (i=1; i<4; i++) {
            if (pts[2*i] < minX) {
                minX = pts[2*i];
            }
            if (pts[2*i+1] < minY) {
                minY = pts[2*i+1];
            }
            if (pts[2*i] > maxX) {
                maxX = pts[2*i];
            }
            if (pts[2*i+1] > maxY) {
                maxY = pts[2*i+1];
            }
        }
        rect.setRect(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Set the transform of the specified node to the specified transform,
     * and animate the change from its current transformation over the specified
     * number of milliseconds using a slow-in slow-out animation.
     * The surface specifies which surface should be updated during the animation.
     * Note that another version of animate lets you specify the timing of
     * the animation so you can avoid the slow-in slow-out animation if you prefer.
     * <p>
     * If millis is 0, then the transform is updated once, and the scene
     * is not repainted immediately, but rather a repaint request is queued,
     * and will be processed by an event handler.
     * @param node The node to be animated
     * @param tx Final transformation
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    static public void animate(ZTransformable node, AffineTransform tx, int millis, ZDrawingSurface surface) {
        animate(node, tx, millis, surface, new ZSISOLerp());
    }

    /**
     * Set the transform of the specified node to the specified transform,
     * and animate the change from its current transformation over the specified
     * number of milliseconds using a slow-in slow-out animation.
     * The surface specifies which surface should be updated during the animation.
     * Note that another version of animate lets you specify the timing of
     * the animation so you can avoid the slow-in slow-out animation if you prefer.
     * <p>
     * If millis is 0, then the transform is updated once, and the scene
     * is not repainted immediately, but rather a repaint request is queued,
     * and will be processed by an event handler.
     * <p>
     * The timing of the animation is controlled by the <tt>lerpTimeFunction</tt>.
     * This is used to specify the rate the animation occurs over time.  If this
     * parameter is specified as null, then a standard linear interpolation is
     * used, and the animation goes at a constant speed from beginning to end.
     * The caller can specify alternate functions, however, which can do things
     * like perform a slow-in, slow-out animation.
     * @param node The node to be animated
     * @param at Final transformation
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     * @param lerpTimeFunction The function that determines how the timing of the animation should be calculated
     */
    static public void animate(ZTransformable node, AffineTransform at, int millis, ZDrawingSurface surface, ZLerp lerpTimeFunction) {
        ZTransformable[] nodes = new ZTransformable[1];
        AffineTransform[] ats = new AffineTransform[1];
        nodes[0] = node;
        ats[0] = at;
        animate(nodes, ats, millis, surface, lerpTimeFunction);
    }

    /**
     * Set the transforms of the specified array of nodes to the specified
     * array of transforms,
     * and animate the change over the specified
     * number of milliseconds using a slow-in slow-out animation.
     * The surface specifies which surface should be updated during the animation.
     * (Note that another version of animate lets you specify the timing of
     * the animation so you can avoid the slow-in slow-out animation if you prefer.)
     * <p>
     * If the size of the nodes and txs arrays are not equal, then only those
     * nodes for which transforms are specified will be animated.  That is,
     * the smaller of the two array sizes will be used.
     * <p>
     * If millis is 0, then the transform is updated once, and the scene
     * is not repainted immediately, but rather a repaint request is queued,
     * and will be processed by an event handler.
     * <p>
     * The following code fragment demonstrates the use of this animate
     * method.  It creates three rectangles, and animates two of them
     * simultaneously.
     * <pre>
     *     ZRectangle rect1, rect2, rect3;
     *     ZVisualLeaf leaf1, leaf2, leaf3;
     *     ZTransformable node1, node2, node3;
     *
     *     rect1 = new ZRectangle(0, 0, 50, 50);
     *     rect1.setFillColor(Color.red);
     *     leaf1 = new ZVisualLeaf(rect1);
     *     node1 = new ZTransformable();
     *     node1.addChild(leaf1);
     *     layer.addChild(node1);
     *
     *     rect2 = new ZRectangle(25, 25, 50, 50);
     *     rect2.setFillColor(Color.blue);
     *     leaf2 = new ZVisualLeaf(rect2);
     *     node2 = new ZNode();
     *     node2.addChild(leaf2);
     *     layer.addChild(node2);
     *
     *     rect3 = new ZRectangle(100, 100, 50, 50);
     *     rect3.setFillColor(Color.orange);
     *     leaf3 = new ZVisualLeaf(rect3);
     *     node3 = new ZNode(rect3);
     *     node3.addChild(leaf3);
     *     layer.addChild(node3);
     *
     *     ZTransformable[] nodes = new ZTransformable[2];
     *     nodes[0] = node1;
     *     nodes[1] = node2;
     *     AffineTransform[] txs = new AffineTransform[2];
     *     txs[0] = new AffineTransform();
     *     txs[0].scale(2.0, 2.0);
     *     txs[1] = new AffineTransform();
     *     txs[1].translate(100.0, 25.0);
     *     txs[1].scale(0.5, 0.5);
     *
     *     ZTransform.animate(nodes, txs, 1000, surface);
     * </pre>
     *
     * @param nodes The array of nodes to be animated
     * @param txs The array of final transformations of the nodes
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     */
    static public void animate(ZTransformable[] nodes, AffineTransform[] txs, int millis, ZDrawingSurface surface) {
        animate(nodes, txs, millis, surface, new ZSISOLerp());
    }

    /**
     * Set the transforms of the specified array of nodes to the specified
     * array of transforms,
     * and animate the change over the specified
     * number of milliseconds using a slow-in slow-out animation.
     * The surface specifies which surface should be updated during the animation.
     * (Note that another version of animate lets you specify the timing of
     * the animation so you can avoid the slow-in slow-out animation if you prefer.)
     * <p>
     * If the size of the nodes and txs arrays are not equal, then only those
     * nodes for which transforms are specified will be animated.  That is,
     * the smaller of the two array sizes will be used.
     * <p>
     * If millis is 0, then the transform is updated once, and the scene
     * is not repainted immediately, but rather a repaint request is queued,
     * and will be processed by an event handler.
     * <p>
     * The timing of the animation is controlled by the <tt>lerpTimeFunction</tt>.
     * This is used to specify the rate the animation occurs over time.  If this
     * parameter is specified as null, then a standard linear interpolation is
     * used, and the animation goes at a constant speed from beginning to end.
     * The caller can specify alternate functions, however, which can do things
     * like perform a slow-in, slow-out animation.
     * <p>
     * The following code fragment demonstrates the use of this animate
     * method.  It creates three rectangles, and animates two of them
     * simultaneously.
     * <pre>
     *     ZRectangle rect1, rect2, rect3;
     *     ZVisualLeaf leaf1, leaf2, leaf3;
     *     ZTransformable node1, node2, node3;
     *
     *     rect1 = new ZRectangle(0, 0, 50, 50);
     *     rect1.setFillColor(Color.red);
     *     leaf1 = new ZVisualLeaf(rect1);
     *     node1 = new ZTransformable();
     *     node1.addChild(leaf1);
     *     layer.addChild(node1);
     *
     *     rect2 = new ZRectangle(25, 25, 50, 50);
     *     rect2.setFillColor(Color.blue);
     *     leaf2 = new ZVisualLeaf(rect2);
     *     node2 = new ZNode();
     *     node2.addChild(leaf2);
     *     layer.addChild(node2);
     *
     *     rect3 = new ZRectangle(100, 100, 50, 50);
     *     rect3.setFillColor(Color.orange);
     *     leaf3 = new ZVisualLeaf(rect3);
     *     node3 = new ZNode(rect3);
     *     node3.addChild(leaf3);
     *     layer.addChild(node3);
     *
     *     ZTransformable[] nodes = new ZTransformable[2];
     *     nodes[0] = node1;
     *     nodes[1] = node2;
     *     AffineTransform[] txs = new AffineTransform[2];
     *     txs[0] = new AffineTransform();
     *     txs[0].scale(2.0, 2.0);
     *     txs[1] = new AffineTransform();
     *     txs[1].translate(100.0, 25.0);
     *     txs[1].scale(0.5, 0.5);
     *
     *     ZTransform.animate(nodes, txs, 1000, surface);
     * </pre>
     *
     * @param nodes The array of nodes to be animated
     * @param txs The array of final transformations of the nodes
     * @param millis Number of milliseconds over which to perform the animation
     * @param surface The surface to updated during animation.
     * @param lerpTimeFunction The function that determines how the timing of the animation should be calculated
     */
    static public void animate(ZTransformable[] nodes, AffineTransform[] txs, int millis, ZDrawingSurface surface, ZLerp lerpTimeFunction) {
    	
        int i;
        int len = Math.min(nodes.length, txs.length);
        double[][] srcTx = new double[len][6];
        double[][] currTx = new double[len][6];
        double[][] destTx = new double[len][6];

		if (millis != 0) {
        	surface.setAnimating(true);
		}
                                // Extract initial transforms
        for (i=0; i<len; i++) {
            nodes[i].getMatrix(srcTx[i]);
            txs[i].getMatrix(destTx[i]);
        }

		// hack for the mac, might be useful in other cases as well. This 
    	if (millis > 0 && ANIMATE_METHODS_USE_ANIMATION_FRAMEWORK) {
    		for (i = 0; i < len; i++) {
    			ZTransformAnimation animation = new ZTransformAnimation(srcTx[i], destTx[i]);
    			animation.setTransformTarget(nodes[i]);
    			animation.setAlpha(ZAlpha.createStandardSlowInSlowOutAlpha(millis));    
    			animation.play();			
    		}
    		return;
    	}

        if (millis > 0) {
            double lerp;
            double straightLerp = millis / (1000.0 * 30.0);   // Estimate first transition at 30 frames per second
            double sisoLerp;
            long startTime;
            long elapsedTime;
            if (lerpTimeFunction == null) {
                lerp = straightLerp;
            } else {
                lerp = lerpTimeFunction.lerpTime(straightLerp);
            }

            startTime = System.currentTimeMillis();

                                // Loop until animation time has completed
            do {
                for (i=0; i<len; i++) {
                                // Computer new transform representing new step of animation
                    currTx[i][0] = lerp(lerp, srcTx[i][0], destTx[i][0]);
                    currTx[i][1] = lerp(lerp, srcTx[i][1], destTx[i][1]);
                    currTx[i][2] = lerp(lerp, srcTx[i][2], destTx[i][2]);
                    currTx[i][3] = lerp(lerp, srcTx[i][3], destTx[i][3]);
                    currTx[i][4] = lerp(lerp, srcTx[i][4], destTx[i][4]);
                    currTx[i][5] = lerp(lerp, srcTx[i][5], destTx[i][5]);

                                // Modify transform to reflect this step
                    nodes[i].setTransform(currTx[i][0], currTx[i][1], currTx[i][2],
                                          currTx[i][3], currTx[i][4], currTx[i][5]);
                }

                                // Force immediate render
                surface.paintImmediately();

                                // Calculate total elapsed time
                elapsedTime = System.currentTimeMillis() - startTime;

                                // Calculate next step
                straightLerp = (double)elapsedTime / millis;
                if (lerpTimeFunction == null) {
                    sisoLerp = straightLerp;
                } else {
                    sisoLerp = lerpTimeFunction.lerpTime(straightLerp);
                }
                lerp = (sisoLerp > lerp) ? sisoLerp : lerp;  // Don't allow animation to move backwards
            } while (elapsedTime < millis);
        }

                                // When finished animating, put objects at exact final point in case of rounding error
        for (i=0; i<len; i++) {
            nodes[i].setTransform(destTx[i][0], destTx[i][1], destTx[i][2],
                                  destTx[i][3], destTx[i][4], destTx[i][5]);
        }
                                // Only paint surface immediately if non-zero animation time specified
        if (millis != 0) {
            surface.paintImmediately();
	        surface.setAnimating(false);
        }	
    }

    /**
     * Generate a string that represents this object for debugging.
     * @return the string that represents this object for debugging
     * @see ZDebug#dump
     */
    public String dump() {
        String str = super.dump();

        str += "\n Transform = " + transform;

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

        out.writeState("java.awt.geom.AffineTransform", "transform", transform);
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
        super.writeObjectRecurse(out);
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

        if (fieldName.compareTo("transform") == 0) {
            setTransform((AffineTransform)fieldValue);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        tmpTransform = new AffineTransform();
        paintBounds = new ZBounds();
        tmpRect = new Rectangle2D.Double();
        inverseTransformDirty = true;
    }
}

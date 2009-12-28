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

/**
 * <b>ZFadeGroup</b> is a group node that controls transparency and fading of its
 * sub-tree. Inserting a fade node into the tree lets you control transparency
 * and minimum/maximum magnification of all its descendants. If this node is rendered
 * below its minimum magnification, or above its maximum magnification, it and its
 * children will not be rendered. The node and its subtree will be smoothly faded
 * out as the minimum or maximum magnification is approached.
 * <P>
 * Four types of fading have been implemented,
 * selected with the setFadeType method:<P>
 * <b>CAMERA_MAG</b> fades a node on camera magnification.<BR>
 * <b>COMPOSITE_MAG</b> fades on composite camera magnification.<BR>
 * <b>ABSOLUTE_SCREEN_SIZE</b> fades on absolute screen size in pixels.<BR>
 * <b>PERCENT_OF_CAMERA</b> fades on percentage of the camera size.
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
 * @author Ben Bederson
 */
public class ZFadeGroup extends ZGroup implements ZSerializable, Serializable {
    /**
     * FadeType: Fade on camera magnification.
     */
    static public final short CAMERA_MAG = 0;

    /**
     * FadeType: Fade on composite magnification.
     */
    static public final short COMPOSITE_MAG = 1;

    /**
     * FadeType: Fade on absolute screen size in pixels.
     */
    static public final short ABSOLUTE_SCREEN_SIZE = 2;

    /**
     * FadeType: Fade on percentage of camera size.
     */
    static public final short PERCENT_OF_CAMERA = 3;

                                // Default values
    static public final short   fadeType_DEFAULT = CAMERA_MAG;
    static public final double   alpha_DEFAULT = 1;
    static public final double   minMag_DEFAULT = 0.0;
    static public final double   maxMag_DEFAULT = 1000.0;
    static public final double   fadeRange_DEFAULT = 0.3f;

    static private final int    NUM_ALPHA_LEVELS = 16;    // The number of pre-allocated alpha levels

    static private Composite alphas[] = null;             // The pre-allocated alpha levels

    /**
     * Type of fading: can be CAMERA_MAG, COMPOSITE_MAG, ABSOLUTE_SCREEN_SIZE
     * or PERCENT_OF_CAMERA
     */
    private short fadeType = fadeType_DEFAULT;

    /**
     * The alpha value that will be applied to the transparency (multiplicitively) of the graphics
     * context during render.
     */
    private double alpha = alpha_DEFAULT;

    /**
     * The minimum magnification that this node gets rendered at.
     */
    private double minMag = minMag_DEFAULT;

    /**
     * The maximum magnification that this node gets rendered at.
     */
    private double maxMag = maxMag_DEFAULT;

    /**
     * The percentage of magnification change over which an object is faded in or out
     * as it reaches its minimum or maximum magnification.
     * A value of 0 means there is no fading at all and the object just jumps in
     * and out of visibility.  A value of 0.3 is reasonable, meaning it fades
     * in and out over 30% of its size.
     */
    private double fadeRange = fadeRange_DEFAULT;

    private AffineTransform tmpTransform = new AffineTransform();


    static {
                                // Allocate the pre-computed alpha composites the first time a fade group is used.
        double value;
        alphas = new Composite[NUM_ALPHA_LEVELS];

        for (int i=0; i<NUM_ALPHA_LEVELS; i++) {
            value = (double)i / (NUM_ALPHA_LEVELS - 1);
            alphas[i] = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)value);
        }
    }

    //****************************************************************************
    //
    //                  Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new empty fade group node.
     */
    public ZFadeGroup() { }

    /**
     * Constructs a new fade group node with the specified node as a child of the
     * new group.
     * @param child Child of the new group node.
     */
    public ZFadeGroup(ZNode child) {
        super(child);
    }

    //****************************************************************************
    //
    // Get/Set pairs
    //
    //***************************************************************************

    /**
     * Get the alpha value (opacity) for this node.  Alpha values are applied
     * multiplicitively with the current alpha values at render time.
     * @return The alpha value for this node.
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Set the alpha value (opacity) for this node.  Alpha values are applied
     * multiplicitively with the current alpha values at render time.
     * Alpha valus range from 0 to 1 where 0 represents a node being completely
     * transparent, and 1 represents a node being completely opaque.  Alpha
     * is clamped to the range [0, 1].
     *
     * @param alpha The new alpha value for this node.
     */
    public void setAlpha(double alpha) {
        if (this.alpha != alpha) {
            if (alpha < 0.0) {
                alpha = 0.0;
            } else if (alpha > 1.0) {
                alpha = 1.0;
            }
            this.alpha = alpha;
            repaint();
        }
    }

    /**
     * Get the minimumn magnification for this node, as defined by the fade type.
     * @return The minimum magnification of this node
     */
    public double getMinMag() {
        return minMag;
    }

    /**
     * Set the minimumn magnification for this node. <BR> This node will not be rendered
     * when the current rendering camera's magnification is less than then specified
     * minimum magnification for this node.  The node with smoothly fade from its
     * regular opacity to completely transparent during a small magnification range
     * just before the minimum magnification is reached.  This transparency control
     * affects the entire sub-tree rooted at this node.  The minimum magnification
     * has a minimum value of 0, and is clamped to that value.<P>
     * If fade type is CAMERA_MAG, then minMag is the minimum magnification for this
     * node.<BR>
     * If fade type is COMPOSITE_MAG, then minMag is the minimum composite
     * magnification for this node.<BR>
     * if fade type is ABSOLUTE_SCREEN_SIZE, then minMag is the minimum size of the
     * node in pixels.<BR>
     * if fade type is PERCENT_OF_CAMERA, then minMag is the minimum size, as a
     * percentage of the camera size.<BR>
     *
     * @param minMag The new minimumn magnification for this node.
     */
    public void setMinMag(double minMag) {
        if (minMag < 0.0) {
            minMag = 0.0;
        }
        this.minMag = minMag;
        repaint();
    }

    /**
     * Get the maximum magnification for this node, as defined by the fade type.
     * @return The maximum magnification of this node
     */
    public double getMaxMag() {
        return maxMag;
    }

    /**
     * Set the maximumn magnification for this node. <BR> This node will not be rendered
     * when the current rendering camera's magnification is greater than then specified
     * maximum magnification for this node.  The node with smoothly fade from its
     * regular opacity to completely transparent during a small magnification range
     * just before the maximum magnification is reached.  This transparency control
     * affects the entire sub-tree rooted at this node.  To disable the maximum magnification
     * feature, set the value to any negative value.<P>
     * If fade type is CAMERA_MAG, then maxMag is the maximum magnification for this
     * node.<BR>
     * If fade type is COMPOSITE_MAG, then maxMag is the maximum composite
     * magnification for this node.<BR>
     * if fade type is ABSOLUTE_SCREEN_SIZE, then maxMag is the maximum size of the
     * node in pixels.<BR>
     * if fade type is PERCENT_OF_CAMERA, then maxMag is the maximum size, as a
     * percentage of the camera size.<BR>
     *
     * @param maxMag The new maximumn magnification for this node.
     */
    public void setMaxMag(double maxMag) {
        this.maxMag = maxMag;
        repaint();
    }

    /**
     * Sets the percentage of magnification change over which an object is faded in or out
     * as it reaches its minimum or maximum magnification.
     * A value of 0 means there is no fading at all and the object just jumps in
     * and out of visibility.  A value of 0.3 is reasonable, meaning it fades
     * in and out over 30% of its size.  The input is clamped between 0 and 1.
     * @param fadeRange the new fade range [0, 1]
     */
    public void setFadeRange(double fadeRange) {
        if (fadeRange < 0.0) {
            fadeRange = 0.0;
        }
        if (fadeRange > 1.0) {
            fadeRange = 1.0;
        }
        this.fadeRange = fadeRange;
    }

    /**
     * Returns the current fade range of this node.
     * @return the current fade range
     * @see #setFadeRange
     */
    public double getFadeRange() {
        return fadeRange;
    }

    /**
     * Set the type of fading that this node implements.
     * Must be CAMERA_MAG, COMPOSITE_MAG, ABSOLUTE_SCREEN_SIZE or PERCENT_OF_CAMERA.<BR>
     * CAMERA_MAG fades a node on camera magnification.<BR>
     * COMPOSITE_MAG fades on composite camera magnification.<BR>
     * ABSOLUTE_SCREEN_SIZE fades on absolute screen size in pixels.<BR>
     * PERCENT_OF_CAMERA fades on percentage of the camera size.
     * @param fadeType The fade type for this node.
     */
    public void setFadeType(short aFadeType) {
        if ((aFadeType != CAMERA_MAG) &&
            (aFadeType != COMPOSITE_MAG) &&
            (aFadeType != ABSOLUTE_SCREEN_SIZE) &&
            (aFadeType != PERCENT_OF_CAMERA)) {
            throw new Error("Fade type must be: CAMERA_MAG, COMPOSITE_MAG, ABSOLUTE_SCREEN_SIZE or PERCENT_OF_CAMERA");
        } else {
            fadeType = aFadeType;
        }
    }

    /**
     * Determine the type of fading that this node implements.
     * @return They fade type.
     */
    public int getFadeType() {
        return fadeType;
    }

    //****************************************************************************
    //
    // Painting related methods
    //
    //***************************************************************************

    /**
     * Computes the composite magnification by climbing the scenegraph path.
     * @param path A scenegraph path.
     */
    private double getCompositeMag(ZSceneGraphPath path) {
        tmpTransform.setToIdentity();
        int n = path.getNumParents();
        ZSceneGraphObject obj;
        for (int i=0; i<n; i++) {
            obj = path.getParent(i);
            if (obj instanceof ZCamera) {
                tmpTransform.concatenate(((ZCamera)obj).getViewTransform());
            } else if (obj instanceof ZTransformGroup) {
                tmpTransform.concatenate(((ZTransformGroup)obj).getTransformReference());
            }
        }
        return ZTransformGroup.computeScale(tmpTransform);
    }

    /**
     * Determines if this fade node is visible.
     * @param path A scenegraph path.
     * @return True if this node is visible at the current magnification.
     */
    public boolean isVisible(ZSceneGraphPath path) {
        if (path == null) {
            return false;
        }

        ZCamera camera = path.getCamera();
        // small fix by Andrew McVeigh, 1st February 2005
        double currentMag = camera == null ? 1.0 : camera.getMagnification();
        double metric = currentMag;
        switch(fadeType) {

        case CAMERA_MAG:
            metric = currentMag;
            break;

        case COMPOSITE_MAG:
            metric = getCompositeMag(path);
            break;

        case ABSOLUTE_SCREEN_SIZE:
            double objSize = Math.max(getBounds().getWidth(),
                                            getBounds().getHeight());
            metric = objSize * getCompositeMag(path);
            break;

        case PERCENT_OF_CAMERA:
            Rectangle2D cameraView = camera.getViewBounds();
            Rectangle2D nodeBounds = editor().getNode().getGlobalBounds();
            double objWidth = nodeBounds.getWidth();
            double objHeight = nodeBounds.getHeight();
            if (objWidth > objHeight) {
                metric =  objWidth / cameraView.getWidth();
            } else {
                metric = objHeight / cameraView.getHeight();
            }
            break;
        }
        return isVisible(metric);
    }

    /**
     * Internal method: determines if this fade node is visible at the specified
     * magnification.
     * @param metric The magnification, as defined by fade type.
     * @return True if this node is visible at the specified magnification.
     */
    public boolean isVisible(double metric) {
        boolean visible;

        if ((alpha == 0.0) ||
            (metric < minMag) ||
            ((maxMag >= 0) && (metric > maxMag))) {

            visible = false;
        } else {
            visible = true;
        }

        return visible;
    }

    /**
     * Internal method to compute and access an alpha Composite given the current
     * rendering composite, and the current metric. The metric can be magnification,
     * composite magnification, absolute size in pixels, or percentage of camera
     * size in pixels, depending on fadeType. This method determines the alpha based
     * on this node's alpha value, minimum metric, and maximum metric.
     * @param currentComposite The composite in the current render context
     * @param currentMag The metric of the current rendering camera
     * @return Composite The Composite to use to render this node
     */
    protected Composite getComposite(Composite currentComposite, double metric) {
        double newAlpha = alpha;
        double maxMagFadeStart;
        double minMagFadeStart;

                                // Beware the assignment inside the first clause of both if conditions
        if ((maxMag >= 0) && (metric > (maxMagFadeStart = maxMag * (1.0 - fadeRange)))) {
            newAlpha *= (maxMag - metric)  / (maxMag - maxMagFadeStart);

        } 
	if ((metric < (minMagFadeStart = minMag * (1.0 + fadeRange)))) {
            newAlpha *= (metric - minMag)  / (minMagFadeStart - minMag);
        }

        if ((currentComposite != null) &&
            (currentComposite instanceof AlphaComposite)) {
            newAlpha *= ((AlphaComposite)currentComposite).getAlpha();
        }

        if (newAlpha == 1.0) {
            return currentComposite;
        } else {
            return alphas[(int)(newAlpha * NUM_ALPHA_LEVELS)];
        }
    }

    /**
     * Renders this node which results in the node's visual component getting rendered,
     * followed by its children getting rendered.
     * <p>
     * The transform, clip, and composite will be set appropriately when this object
     * is rendered.  It is up to this object to restore the transform, clip, and composite of
     * the Graphics2D if this node changes any of them. However, the color, font, and stroke are
     * unspecified by Jazz.  This object should set those things if they are used, but
     * they do not need to be restored.
     *
     * @param renderContext The graphics context to use for rendering.
     */
    public void render(ZRenderContext renderContext) {
        Graphics2D      g2 = renderContext.getGraphics2D();
        Composite       saveComposite = null;
        double           metric = 1;

                                // Apply transparency for this node (if fading enabled)
        switch(fadeType) {

        case CAMERA_MAG:
            metric = renderContext.getCameraMagnification();
            break;

        case COMPOSITE_MAG:
            metric = renderContext.getCompositeMagnification();
            break;

        case ABSOLUTE_SCREEN_SIZE:
            double objSize = Math.max(getBounds().getWidth(),
                                            getBounds().getHeight());
            metric = objSize * renderContext.getCompositeMagnification();
            break;

        case PERCENT_OF_CAMERA:
            Rectangle2D cameraView = renderContext.getRenderingCamera().getViewBounds();
            Rectangle2D nodeBounds = editor().getNode().getGlobalBounds();
            double objWidth = nodeBounds.getWidth();
            double objHeight = nodeBounds.getHeight();
            if (objWidth > objHeight) {
                metric =  objWidth / cameraView.getWidth();
            } else {
                metric = objHeight / cameraView.getHeight();
            }
            break;
        }
                                // Don't paint nodes that are invisible, too big or too small
        if (!isVisible(metric)) {
            return;
        }
                                // Apply transparency for this node (if fading enabled)
        if (fadeRange > 0.0) {
            saveComposite = g2.getComposite();
            g2.setComposite(getComposite(saveComposite, metric));
        }

                                // Render node
        super.render(renderContext);
                                // Restore graphics state
        if (fadeRange > 0.0) {
            g2.setComposite(saveComposite);
        }
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
     * If no nodes in the sub-tree are picked, then this node's visual
     * component is picked.
     * <P>
     * If childrenPickable is false, then this will never return a child as the picked node.
     * Instead, this node will be returned if any children are picked, or if this node's
     * visual component is picked.  Else, it will return null.
     * @param rect Coordinates of pick rectangle in local coordinates
     * @param path The path through the scenegraph to the picked node. Modified by this call.
     * @return The picked node, or null if none
     * @see ZDrawingSurface#pick(int, int)
     */
    public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
                                // Don't pick nodes that are invisible, too big or too small
        if (!isVisible(path)) {
            return false;
        }
                                // If not faded out, use super's pick method
        return super.pick(rect, path);
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

        if (alpha != alpha_DEFAULT) {
            out.writeState("double", "alpha", alpha);
        }
        if (minMag != minMag_DEFAULT) {
            out.writeState("double", "minMag", minMag);
        }
        if (maxMag != maxMag_DEFAULT) {
            out.writeState("double", "maxMag", maxMag);
        }
        if (fadeRange != fadeRange_DEFAULT) {
            out.writeState("double", "fadeRange", fadeRange);
        }
        if (fadeType != fadeType_DEFAULT) {
            out.writeState("short", "fadeType", fadeType);
        }
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

        if (fieldName.compareTo("alpha") == 0) {
            alpha = ((Double)fieldValue).doubleValue();
        } else if (fieldName.compareTo("minMag") == 0) {
            minMag = ((Double)fieldValue).doubleValue();
        } else if (fieldName.compareTo("maxMag") == 0) {
            maxMag = ((Double)fieldValue).doubleValue();
        } else if (fieldName.compareTo("fadeRange") == 0) {
            fadeRange = ((Double)fieldValue).doubleValue();
        } else if (fieldName.compareTo("fadeType") == 0) {
            fadeType = ((Short)fieldValue).shortValue();
        }
    }
}

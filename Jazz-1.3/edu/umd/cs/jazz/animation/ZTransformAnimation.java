/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.animation;

import java.util.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;

/**
 * <b>ZTransformAnimation</b> animates an object conforming to the ZTransformable
 * interface from a source transform to a destination transform over time. A ZAlpha
 * class is used to specify the start time, duration, and acceleration time for this
 * animation.
 * <p>
 * This code demonstrates how to animate a rectangle from position 0, 0 to 300, 300.
 * <p>
 * <code>
 * <pre>
 * ZRectangle aRect = new ZRectangle(0, 0, 100, 100);
 * ZVisualLeaf aLeaf = new ZVisualLeaf(aRect);
 *
 * // The animation will apply to the transform group decorating the aLeaf node.
 * ZTransformGroup aTarget = aLeaf.editor().getTransformGroup();
 *
 * canvas.getLayer().addChild(aTarget);
 *
 * // Create a new ZAlpha that will run from the current time for 1.5 seconds.
 * // This alpha will change linearly; see the ZAlpha class to learn how to create
 * // slow in slow out animation effects.
 * ZAlpha alpha = new ZAlpha(1, 1500);
 *
 * // Create the ZTransformAnimation with its source and destination values. Here
 * // we choose to animate the target from its current transform, to the identity transform
 * // translated by 300, 300.
 * AffineTransform souceTransform = aTarget.getTransform();
 * AffineTransform destinationTransform = AffineTransform.getTranslateInstance(300, 300);
 * ZTransformAnimation aTransformAnimation = new ZTransformAnimation(souceTransform, destinationTransform);
 *
 * // Set the target of the animation.
 * aTransformAnimation.setTransformTarget(aTarget);
 *
 * // Set the alpha value for the animation. This animation will start immediately,
 * // and run for 1.5 seconds.
 * aTransformAnimation.setAlpha(alpha);
 *
 * // Start the animation by registering it with the ZAnimationScheduler.
 * aTransformAnimation.play();
 * </pre>
 * </code>
 * <p>
 * @see ZAlpha
 * @author Jesse Grosjean
 */
public class ZTransformAnimation extends ZAnimation {

    private double[] fSource;
    private double[] fDestination;
    private ZTransformable fTransformTarget;

    /**
     * Construct a new ZTransformAnimation.
     *
     * @param aSource      the source transform matrix that the animation will start at.
     * @param aDestination the destination transform matrix that the animation will end at.
     */
    public ZTransformAnimation(double[] aSource, double[] aDestination) {
        super();
        setSourceTransform(aSource);
        setDestinationTransform(aDestination);
    }

    /**
     * Construct a new ZTransformAnimation.
     *
     * @param aSource      the source transform that the animation will start at.
     * @param aDestination the destination transform that the animation will end at.
     */
    public ZTransformAnimation(AffineTransform aSource, AffineTransform aDestination) {
        fSource = new double[6];
        fDestination = new double[6];

        aSource.getMatrix(fSource);
        aDestination.getMatrix(fDestination);
    }

    /**
     * Return the source transform matrix, the transform matrix that the animation will start at.
     */
    public double[] getSourceTransform() {
        return fSource;
    }

    /**
     * Set the source transform matrix, the transform matrix that the animation will start at.
     */
    public void setSourceTransform(double[] aSource) {
        fSource = aSource;
    }

    /**
     * Return the destination transform matrix , the transform matrix that the animation will end up at.
     */
    public double[] getDestinationTransform() {
        return fDestination;
    }

    /**
     * Set the destination transform matrix, the transform matrix that the animation will end up at.
     */
    public void setDestinationTransform(double[] aDestination) {
        fDestination = aDestination;
    }

    /**
     * Return the target of this transform animation. This is the object who's
     * <code>setTransform</code> method will be called for each frame of the
     * animation.
     */
    public ZTransformable getTransformTarget() {
        return fTransformTarget;
    }

    /**
     * Set the target of this transform animation. This is the object who's
     * <code>setTransform</code> method will be called for each frame of the
     * animation.
     */
    public void setTransformTarget(ZTransformable aTarget) {
        fTransformTarget = aTarget;
    }

    /**
     * Animate one frame of this animation for the given time. The time parameter is
     * used to generate an alpha value, which is then used to create a new transform
     * interpolated between the source and destination transforms. The transform target
     * is then set to this interpolated value.
     */
    protected void animateFrameForTime(long aTime) {
        super.animateFrameForTime(aTime);

        float ratio = getAlpha().value(aTime);
        if (fTransformTarget != null) {
            fTransformTarget.setTransform(fSource[0] + (ratio * (fDestination[0] - fSource[0])),
                                          fSource[1] + (ratio * (fDestination[1] - fSource[1])),
                                          fSource[2] + (ratio * (fDestination[2] - fSource[2])),
                                          fSource[3] + (ratio * (fDestination[3] - fSource[3])),
                                          fSource[4] + (ratio * (fDestination[4] - fSource[4])),
                                          fSource[5] + (ratio * (fDestination[5] - fSource[5])));
        }
    }
}
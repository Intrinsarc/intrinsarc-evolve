/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.animation;

import java.awt.*;
import java.util.*;

import edu.umd.cs.jazz.component.*;

/**
 * <b>ZStrokeAnimation</b> animates an object conforming to the ZStroke
 * interface from a source stroke to a destination stroke over time. A ZAlpha
 * class is used to specify the start time, duration, and acceleration time for this
 * animation.
 * <p>
 * This code demonstrates how to animate the stroke of a rectangle from width 0 to width 5.
 * <p>
 * <code>
 * <pre>
 * ZRectangle aRect = new ZRectangle(0, 0, 100, 100);
 * ZVisualLeaf aLeaf = new ZVisualLeaf(aRect);
 *
 * canvas.getLayer().addChild(aLeaf);
 *
 * // Create a new ZAlpha that will run from the current time for 1.5 seconds.
 * // This alpha will change linearly; see the ZAlpha class to learn how to create
 * // slow in slow out animation effects.
 * ZAlpha alpha = new ZAlpha(1, 1500);
 *
 * // Create the ZTransformAnimation with its source and destination values. Here
 * // we choose to animate the target from a stroke with width 0 to a stroke with width 5.
 * ZStrokeAnimation aStrokeAnimation = new ZStrokeAnimation(new BasicStroke(0), new BasicStroke(5));
 *
 * // Set the target of the animation.
 * aStrokeAnimation.setStrokeTarget(aRect);
 *
 * // Set the alpha value for the animation. This animation will start immediately,
 * // and run for 1.5 seconds.
 * aStrokeAnimation.setAlpha(alpha);
 *
 * // Start the animation by registering it with the ZAnimationScheduler.
 * aStrokeAnimation.play();
 * </pre>
 * </code>
 * <p>
 * @see ZAlpha
 * @author Jesse Grosjean
 */
public class ZStrokeAnimation extends ZAnimation {

    private BasicStroke fSource;
    private BasicStroke fDestination;
    private ZStroke fTargetStroke;

    /**
     * Construct a new ZStrokeAnimation.
     *
     * @param aSource      the source stroke that the animation will start at.
     * @param aDestination the destination stroke that the animation will end at.
     */
    public ZStrokeAnimation(BasicStroke aSource, BasicStroke aDestination) {
        super();
        setSourceStroke(aSource);
        setDestinationStroke(aDestination);
    }

    /**
     * Return the source stroke, the stroke that the animation will start at.
     */
    public BasicStroke getSourceStroke() {
        return fSource;
    }

    /**
     * Set the source stroke, the stroke that the animation will start at.
     */
    public void setSourceStroke(BasicStroke aStroke) {
        fSource = aStroke;
    }

    /**
     * Return the destination stroke, the stroke that the animation will end at.
     */
    public BasicStroke getDestinationStroke() {
        return fDestination;
    }

    /**
     * Set the destination stroke, the stroke that the animation will end at.
     */
    public void setDestinationStroke(BasicStroke aStroke) {
        fDestination = aStroke;
    }

    /**
     * Return the target of this stroke animation. This is the object who's
     * <code>setStroke</code> method will be called for each frame of the
     * animation.
     */
    public ZStroke getStrokeTarget() {
        return fTargetStroke;
    }

    /**
     * Set the target of this stroke animation. This is the object who's
     * <code>setStroke</code> method will be called for each frame of the
     * animation.
     */
    public void setStrokeTarget(ZStroke aTarget) {
        fTargetStroke = aTarget;
    }

    /**
     * Animate one frame of this animation for the given time. The time paramater is
     * used to generate an alpha value, which is then used to create a new stroke
     * interpolated between the source and destination strokes. The stroke target
     * is then set to this interpolated value.
     */
    protected void animateFrameForTime(long aTime) {
        super.animateFrameForTime(aTime);

        if (fTargetStroke != null) {
            float ratio = getAlpha().value(aTime);
            float lineWidth = fSource.getLineWidth() + (ratio * (fDestination.getLineWidth() - fSource.getLineWidth()));
            fTargetStroke.setStroke(new BasicStroke(lineWidth));
        }
    }
}
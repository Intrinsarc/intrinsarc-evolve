/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.animation;

import java.awt.*;
import java.util.*;

import edu.umd.cs.jazz.component.*;

/**
 * <b>ZColorAnimation</b> animates an object conforming to the ZFillPaint or ZPenPaint
 * interface from a source color to a destination color over time. A ZAlpha
 * class is used to specify the start time, duration, and acceleration time for this
 * animation.
 * <p>
 * This example code demonstrates how to animate the fill color of a rectangle from color
 * red to color yellow.
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
 * // Create the ZColorAnimation with its source and destination values. Here
 * // we choose to animate the target from color red to color yellow.
 * ZColorAnimation aColorAnimation = new ZColorAnimation(Color.red, Color.yellow);
 *
 * // Set the target of the animation.
 * aColorAnimation.setFillPaintTarget(aRect);
 *
 * // Set the alpha value for the animation. This animation will start immediately,
 * // and run for 1.5 seconds.
 * aColorAnimation.setAlpha(alpha);
 *
 * // Start the animation by registering it with the ZAnimationScheduler.
 * aColorAnimation.play();
 * </pre>
 * </code>
 * <p>
 * @see ZAlpha
 * @author Jesse Grosjean
 */
public class ZColorAnimation extends ZAnimation {

    private Color fSource;
    private Color fDestination;
    private ZFillPaint fFillPaintTarget;
    private ZPenPaint fPenPaintTarget;

    /**
     * Construct a new ZColorAnimation.
     *
     * @param aSource      the source color that the animation will start at.
     * @param aDestination the destination color that the animation will end at.
     */
    public ZColorAnimation(Color aSource, Color aDestination) {
        super();
        fSource = aSource;
        fDestination = aDestination;
    }

    /**
     * Return the source color, the color that the animation will start at.
     */
    public Color getSourceColor() {
        return fSource;
    }

    /**
     * Set the source color, the color that the animation will start at.
     */
    public void setSourceColor(Color aSource) {
        fSource = aSource;
    }

    /**
     * Return the destination color, the color that the animation will end at.
     */
    public Color getDestinationColor() {
        return fDestination;
    }

    /**
     * Set the destination color, the color that the animation will end at.
     */
    public void setDestinationColor(Color aDestination) {
        fDestination = aDestination;
    }

    /**
     * Return the pen paint target of this color animation. This is the object who's
     * <code>setPenPaint</code> method will be called for each frame of the
     * animation.
     */
    public ZPenPaint getPenPaintTarget() {
        return fPenPaintTarget;
    }

    /**
     * Set the pen paint target of this color animation. This is the object who's
     * <code>setPenPaint</code> method will be called for each frame of the
     * animation.
     */
    public void setPenPaintTarget(ZPenPaint aPenPaintTarget) {
        fPenPaintTarget = aPenPaintTarget;
    }

    /**
     * Return the fill paint target of this color animation. This is the object whos
     * <code>setFillPaint</code> method will be called for each frame of the
     * animation.
     */
    public ZFillPaint getFillPaintTarget() {
        return fFillPaintTarget;
    }

    /**
     * Set the fill paint target of this color animation. This is the object who's
     * <code>setFillPaint</code> method will be called for each frame of the
     * animation.
     */
    public void setFillPaintTarget(ZFillPaint aFillPaintTarget) {
        fFillPaintTarget = aFillPaintTarget;
    }

    /**
     * Animate one frame of this animation for the given time. The time parameter is
     * used to generate an alpha value, which is then used to create a color
     * interpolated between the source and destination colors. The color is then applied to
     * both the pen paint and fill paint targets. If the animation should only apply to one of
     * these targets the other can safely be left as null.
     */
    protected void animateFrameForTime(long aTime) {
        super.animateFrameForTime(aTime);

        float ratio = getAlpha().value(aTime);
        float red = (float) (fSource.getRed() + (ratio * (fDestination.getRed() - fSource.getRed())));
        float green = (float) (fSource.getGreen() + (ratio * (fDestination.getGreen() - fSource.getGreen())));
        float blue = (float) (fSource.getBlue() + (ratio * (fDestination.getBlue() - fSource.getBlue())));
        float alpha = (float) (fSource.getAlpha() + (ratio * (fDestination.getAlpha() - fSource.getAlpha())));

        Color interpolation = new Color(red/255, green/255, blue/255, alpha/255);

        if (fFillPaintTarget != null) {
            fFillPaintTarget.setFillPaint(interpolation);
        }

        if (fPenPaintTarget != null) {
            fPenPaintTarget.setPenPaint(interpolation);
        }
    }
}
/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.animation;

import java.awt.*;

/**
 * <b>ZNextFrameOnElapsedFrames</b> allows an animation to schedule its animation rate by
 * frame count instead of by time. That condition is how an animation that wants to be animated
 * on every third frame would communicate that request to the ZAnimationScheduler.
 * <p>
 * This class will is used internally to support <code>ZAnimation.animationRateByElapsedFrames</code>,
 * you should not normally need to use it directly.
 *
 * @author Jesse Grosjean
 */
public class ZNextFrameOnElapsedFrames extends ZNextFrameCondition {

    private long fTriggerFrame;

    public ZNextFrameOnElapsedFrames(ZAlpha aAlpha, long aFrameCount) {
        super(aAlpha);
        fTriggerFrame = aFrameCount + ZAnimationScheduler.instance().getCurrentFrame();
    }

    public boolean isReadyToAnimate() {
        return super.isReadyToAnimate() &&
               ZAnimationScheduler.instance().getCurrentFrame() >= fTriggerFrame;
    }

    public int compareTo(Object o) {
        int result = super.compareTo(o);
        if (result == 0) {
            ZNextFrameOnElapsedFrames condition = (ZNextFrameOnElapsedFrames) o;

            if (fTriggerFrame > condition.fTriggerFrame) {
                result = -1;                                    // that frame needs to be scheduled first
                                                                // so we get put in the queue after it.

            } else if (fTriggerFrame < condition.fTriggerFrame) {
                result = 1;                                     // this frame needs to be schedule first
                                                                // so put it earlier in the queue.
            }
        }

        return result;
    }
}
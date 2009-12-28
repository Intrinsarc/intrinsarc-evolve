/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.animation;

import java.awt.*;

/**
 * <b>ZNextFrameOnElapsedTime</b> allows an animation to schedule its frame rate based on time.
 * This condition is how an animation that wants to be animated every 500 milliseconds
 * would communicate that request to the ZAnimationScheduler.
 * <p>
 * This class is used internally to support <code>ZAnimation.animationRateByElapsedTime</code>,
 * it should not normally be used directly.
 *
 * @author Jesse Grosjean
 */
public class ZNextFrameOnElapsedTime extends ZNextFrameCondition {

    private long fTriggerTime;

    public ZNextFrameOnElapsedTime(ZAlpha aAlpha, long aTimeDurration) {
        super(aAlpha);
        fTriggerTime = aTimeDurration + ZAnimationScheduler.instance().getCurrentTime();
    }

    public boolean isReadyToAnimate() {
        return super.isReadyToAnimate() &&
               ZAnimationScheduler.instance().getCurrentTime() >= fTriggerTime;
    }

    public int compareTo(Object o) {
        int result = super.compareTo(o);
        if (result == 0) {
            ZNextFrameOnElapsedTime condition = (ZNextFrameOnElapsedTime) o;

            if (fTriggerTime < condition.fTriggerTime) {
                result = 1;
            } else if (fTriggerTime > condition.fTriggerTime) {
                result = -1;
            }
        }

        return result;
    }
}
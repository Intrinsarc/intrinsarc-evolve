/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.animation;

import java.awt.*;

/**
 * <b>ZNextFrameCondition</b> is used by ZAnimations to communicate to the ZAnimationScheduler when
 * they want their next frame to be animated. Only when this condition is met, as
 * determined by <code>isReadyToAnimate</code>, will the animation scheduler animate the next frame of
 * the animation.
 * <p>
 * This class uses a alpha object to determine when the next frame should be animated. Its concrete subclasses
 * ZNextFrameOnElapsedFrames and ZNextFrameOnElapsedTime use the alpha object together with the current frame
 * number or current time to determine when the next frame should be animated.
 * <p>
 * For normal use of the animation system you will not need to use ZNextFrameCondition's directly. ZAnimation
 * will do everything behind the scenes for you why you press <code>ZAnimation.play</code>.
 *
 * @author Jesse Grosjean
 */
public abstract class ZNextFrameCondition implements Comparable {

    private ZAlpha fAlpha;

    /**
     * Create a new ZNextFrameCondition.
     *
     * @param aAlpha the alpha that will be used to determine if the next frame from that
     * associated animation should be animated yet.
     */
    public ZNextFrameCondition(ZAlpha aAlpha) {
        super();
        fAlpha = aAlpha;
    }

    /**
     * Return true if this condition has been met, and the next frame of its associated
     * animation is ready to animate. This implementation asks the alpha object if it
     * is started for the current time provided by the ZAnimationScheduler.
     */
    public boolean isReadyToAnimate() {
        if (fAlpha != null) {
            return fAlpha.isStarted(ZAnimationScheduler.instance().getCurrentTime());
        }
        return true;
    }

    /**
     * This is used to compare two frame conditions. The frame condition whos associated animation
     * should be animated first is the greater of the two. The ZAlpha associated
     * with each frame condition is used to determine this.
     */
    public int compareTo(Object o) {
        long aTime = ZAnimationScheduler.instance().getCurrentTime();
        ZNextFrameCondition aFrameCondition = (ZNextFrameCondition) o;

        ZAlpha thisAlpha = fAlpha;
        ZAlpha thatAlpha = aFrameCondition.fAlpha;

        // Conditions with a null alpha always come first.
        if (thisAlpha == null && thatAlpha != null) return 1;
        if (thisAlpha != null && thatAlpha == null) return -1;
        if (thisAlpha == null && thatAlpha == null) return 0;

        boolean isThisStarted = thisAlpha.isStarted(aTime);
        boolean isThatStarted = thatAlpha.isStarted(aTime);

        if (isThisStarted && !isThatStarted) {          // if this one is started and that one isn't
            return 1;                                   // then this is greater then that.

        } else if (!isThisStarted && isThatStarted) {   // if this one is not started and that one is
            return -1;                                  // started then that one is greater the this.

        } else if (!isThatStarted && !isThatStarted) {  // if neither has been started then compare by alpha start time.
            long thisStart = fAlpha.getTriggerTime() + fAlpha.getPhaseDelayDuration();
            long thatStart = aFrameCondition.fAlpha.getTriggerTime() + aFrameCondition.fAlpha.getPhaseDelayDuration();

            if (thisStart < thatStart) {                // if this start time is less then that start time then this
                return 1;                               // is greater.

            } else if (thisStart > thatStart) {
                return -1;                              // if this start time is greater then that start time then
                                                        // that is greater.
            }
        }

        // they are equal.
        return 0;
    }
}
/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.animation;

import java.util.*;
import java.awt.event.*;

import edu.umd.cs.jazz.util.*;

/**
 * <b>ZAnimationScheduler</b> schedules the frames of ZAnimations. It uses a timer firing from
 * the event dispatch thread, each time the timer fires it animates one frame from all animations
 * that have been scheduled and are ready to animate at that time.
 * <p>
 * Normally you will not need to use this class directly, unless you are creating your own custom
 * subclass of ZAnimation. This class is a singleton, only one instance should ever exist at the same
 * time. This instance is accessed through the <code>instance</code> method.
 *
 * @author Jesse Grosjean
 */
public class ZAnimationScheduler implements ActionListener {

    private static ZAnimationScheduler theAnimationScheduler;

    private javax.swing.Timer fTimer;
    private int fFrameDelay = 20;
    private long fCurrentTime = 0;
    private long fCurrentFrame = 0;
    private ArrayList fProcessList = new ArrayList();
    private Runnable fFrameEndedRunnable;

    private ZPriorityQueue fTimeConditionQueue = new ZPriorityQueue();
    private ZPriorityQueue fFrameConditionQueue = new ZPriorityQueue();

    /**
     * ZScheduledAnimation is used internally to group a ZAnimations together with
     * the ZNextFrameCondition that they were scheduled with.
     */
    private class ZScheduledAnimation implements Comparable {

        private ZAnimation fAnimation;
        private ZNextFrameCondition fFrameCondition;

        public ZScheduledAnimation(ZAnimation aAnimation, ZNextFrameCondition aCondition) {
            fAnimation = aAnimation;
            fFrameCondition = aCondition;
        }

        public boolean isReadyToAnimate() {
            return fFrameCondition.isReadyToAnimate();
        }

        public ZAnimation getAnimation() {
            return fAnimation;
        }

        public int compareTo(Object o) {
            ZScheduledAnimation scheduledAnimation = (ZScheduledAnimation) o;
            return fFrameCondition.compareTo(scheduledAnimation.fFrameCondition);
        }
    }

    /**
     * Return a reference to the singleton animation scheduler.
     */
    public static ZAnimationScheduler instance() {
        if (theAnimationScheduler == null) {
            theAnimationScheduler = new ZAnimationScheduler();
        }
        return theAnimationScheduler;
    }

    /**
     * Construct a new ZAnimationScheduler. This is protected
     * because <code>instance</code> should be used instead.
     */
    protected ZAnimationScheduler() {
        super();
    }

    /**
     * Return the current time for the animation scheduler. All animations should use this instead of
     * System.currentTimeMillis(). This will make sure that all animations in the system are synchronized.
     */
    public long getCurrentTime() {
        return fCurrentTime;
    }

    /**
     * Return the current frame this the animation scheduler is or will soon be drawing.
     */
    public long getCurrentFrame() {
        return fCurrentFrame;
    }

    /**
     * Return the delay between each frame that gets animated. If the animations take a long time
     * to animate the actual frame delay may be greater then this value.
     */
    public int getFrameDelay() {
        return fFrameDelay;
    }

    /**
     * Set the delay between each frame that gets animated. If the animations take a long time
     * to animate the actual frame delay may be greater then this value.
     */
    public void setFrameDelay(int aDelay) {
        stopAnimationScheduler();
        fFrameDelay = aDelay;
        startAnimationScheduler();
    }

    /**
     * Schedule an animation together with its frame condition. Normally this will be done
     * automatically by </code>ZAnimation.play</code>. NOTE the current implementation requires that
     * the aCondition parameter inherits from either ZNextFrameOnElapsedTime or ZNextFrameOnElapsedFrames.
     */
    public void scheduleAnimation(ZAnimation aAnimation, ZNextFrameCondition aCondition) {
        if (aCondition instanceof ZNextFrameOnElapsedTime) {
            fTimeConditionQueue.insert(new ZAnimationScheduler.ZScheduledAnimation(aAnimation, aCondition));
        } else if (aCondition instanceof ZNextFrameOnElapsedFrames) {
            fFrameConditionQueue.insert(new ZAnimationScheduler.ZScheduledAnimation(aAnimation, aCondition));
        }

        if (!isAnimationSchedulerRunning()) {
            startAnimationScheduler();
        }
    }

    /**
     * This method first sets the current time (returned by <code>getCurrentTime</code>) to
     * the aTime parameter. Next it gets all animations that are ready to animate at that time
     * or for the current frame and animates one frame for each one. Last of all it increments the
     * current frame.
     */
    public void processAnimations(long aTime) {
        fCurrentTime = aTime;

        Iterator i = getAnimationsToProcessForCurrentFrame();
        while (i.hasNext()) {
            ZAnimation each = (ZAnimation) i.next();

            if (!each.isStopped())
                each.animateFrameForTime(fCurrentTime);
        }

        if (fTimeConditionQueue.isEmpty() &&
            fFrameConditionQueue.isEmpty()) {
            stopAnimationScheduler();
        }

        if (fFrameEndedRunnable != null) {
            fFrameEndedRunnable.run();
        }

        fCurrentFrame++;
    }

    /**
     * Return an iterator for all animations that should be animated for the current frame.
     * The values of <code>getCurrentFrame</code> and <code>getCurrentTime</code> are used
     * to determine if an animation is ready to be animated for the current frame. Since all
     * animations use these common values animates that are scheduled to start at the same time
     * will always start animating in the same frame.
     */
    protected Iterator getAnimationsToProcessForCurrentFrame() {
        fProcessList.clear();

        while (!fTimeConditionQueue.isEmpty()) {
            ZScheduledAnimation scheduledAnimation = (ZScheduledAnimation) fTimeConditionQueue.first();
            if (scheduledAnimation.isReadyToAnimate()) {
                fTimeConditionQueue.extractFirst();
                fProcessList.add(scheduledAnimation.getAnimation());
            } else {
                break;
            }
        }

        while (!fFrameConditionQueue.isEmpty()) {
            ZScheduledAnimation scheduledAnimation = (ZScheduledAnimation) fFrameConditionQueue.first();
            if (scheduledAnimation.isReadyToAnimate()) {
                fFrameConditionQueue.extractFirst();
                fProcessList.add(scheduledAnimation.getAnimation());
            } else {
                break;
            }
        }

        return fProcessList.iterator();
    }

    /**
     * Return true if the animation scheduler's timer is running.
     */
    public boolean isAnimationSchedulerRunning() {
        return getTimer().isRunning();
    }

    /**
     * Forces all animations that are in the queue's to be stopped.
     */
    public void stopPendingAnimations() {
        while (!fTimeConditionQueue.isEmpty()) {
            ZScheduledAnimation scheduledAnimation = (ZScheduledAnimation) fTimeConditionQueue.extractFirst();
            scheduledAnimation.getAnimation().stop();
        }

        while (!fFrameConditionQueue.isEmpty()) {
            ZScheduledAnimation scheduledAnimation = (ZScheduledAnimation) fFrameConditionQueue.extractFirst();
            scheduledAnimation.getAnimation().stop();
        }
    }

    /**
     * Start the timer that drives this animation scheduler.
     */
    protected synchronized void startAnimationScheduler() {
        getTimer().start();
    }

    /**
     * Start the timer that drives this animation scheduler.
     */
    protected synchronized void stopAnimationScheduler() {
        getTimer().stop();
    }

    /**
     * This method is called when the timer fires. This method
     * in turn calls <code>processAnimations</code> with the current time.
     */
    public void actionPerformed(ActionEvent e) {
        processAnimations(System.currentTimeMillis());
    }

    /**
     * Return the animation schedulers timer.
     */
    protected javax.swing.Timer getTimer() {
        if (fTimer == null) {
            fTimer = new javax.swing.Timer(getFrameDelay(), this);
        }
        return fTimer;
    }

    /**
     * Set a runnable that will be run when all the animations
     * for one frame have finished.
     */
    public void setFrameEndedRunnable(Runnable aRunnable) {
        fFrameEndedRunnable = aRunnable;
    }
}
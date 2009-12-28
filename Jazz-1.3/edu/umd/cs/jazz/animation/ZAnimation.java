package edu.umd.cs.jazz.animation;

import java.util.*;

/**
 * <b>ZAnimation</b> and its subclasses provide the central programming interface
 * to the animation system. Most animations consist of a source property, a destination
 * property and a target object that is normally something in the Jazz scene graph. The
 * animation interpolates between its source and destination property over time,
 * applying the intermediate values to its target. For a concrete look at this behavior
 * see ZColorAnimation.
 * <p>
 * Each ZAnimation has a ZAlpha object that it uses to determine when it should
 * start, when it should finish, and how it should interpolate between its source and
 * destination values.
 * <p>
 * When creating most animations three decisions need to be made.
 * <ol>
 * <li>What object should this animation apply to? This determines the target of the animation.
 * <li>What property on the target do i want to change. This will determine the ZAnimation subclass
 * that you use, and its source and destination values. (if the property is a color then use ZColorAnimation)
 * <li>How should this property be set over time, this determines the parameters used
 * to set up the animations ZAlpha.
 * </ol>
 * <p>
 * Once an animation has been constructed the method <code>play</code> must be called so that
 * the animation gets scheduled with the current ZAnimationScheduler. Once the scheduler determines
 * (by using the animations ZAlpha and ZNextFrameCondition) that the next frame of the animation should be
 * animated it calls <code>animateFrameForTime</code> on the animation. In this method the
 * animation performs all animation activities, finishing that method implementation by scheduling its
 * next frame if it wishes to continue animating.
 * <p>
 * Here is a simple animation example. It uses a transform animation to animate a square across
 * the screen.
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
public abstract class ZAnimation {

    private static final int ANIMATION_RATE_BY_NEXT_FRAME = 0;
    private static final int ANIMATION_RATE_BY_ELAPSED_TIME = 1;
    private static final int ANIMATION_RATE_BY_ELAPSED_FRAMES = 2;

    private ZAlpha fAlpha;
    private boolean isStopped = true;
    private boolean hasSeenFirstFrameOfPlaySequence = false;
    private int fAnimationRateMode = ANIMATION_RATE_BY_NEXT_FRAME;
    private long fAnimationRateValue = 0;
    private Runnable fStartedRunnable;
    private Runnable fStoppedRunnable;

    /**
     * Construct a new ZAnimation.
     */
    public ZAnimation() {
        super();
    }
    /**
     * Construct a new ZAnimation.
     *
     * @param aAlpha the alpha parameter determines the animations start and finish time, and
     *               generates any alpha values needed by the animation to interpolate between values.
     */
    public ZAnimation(ZAlpha aAlpha) {
        super();
    }
    /**
     * Animate one frame of this animation at the specified time, and
     * schedule a new frame to be animated if the alpha object that is
     * scheduling this animation has not yet finished. This method is
     * called by the ZAnimation scheduler.
     * <p>
     * ZAnimation subclasses should override this method and perform all
     * animation code from with it.
     */
    protected void animateFrameForTime(long aTime) {
        if (!hasSeenFirstFrameOfPlaySequence) {
            animationStarted();
            hasSeenFirstFrameOfPlaySequence = true;
        }

        if (getAlpha() != null &&
            getAlpha().isFinished(aTime)) {
                stop();
                return; // don't schedule any more frames.
        }

        scheduleNextFrame();
    }
    /**
     * This method makes the animation schedule its frames by elapsed frames. For example this
     * code would make the animation animate on every 5th frame.
     * <code>animation.animationRateByElapsedFrames(5)</code>.
     *
     * @param aFramesCount the number of frames to wait until the next frame of this animation is animated.
     */
    public void animationRateByElapsedFrames(long aFramesCount) {
        fAnimationRateMode = ANIMATION_RATE_BY_ELAPSED_FRAMES;
        fAnimationRateValue = aFramesCount;
    }
    /**
     * This method makes the animation schedule its frames by elapsed time. For example this
     * code would make the animation animate one frame per seconds.
     * <code>animation.animationRateByElapsedTime(1000)</code>.
     *
     * @param aElapsedTime the amount of time to wait before animating the next frame of the animation,
     *                     specified in milliseconds.
     */
    public void animationRateByElapsedTime(long aElapsedTime) {
        fAnimationRateMode = ANIMATION_RATE_BY_ELAPSED_TIME;
        fAnimationRateValue = aElapsedTime;
    }
    /**
     * Make it so that that this animation will be animated on every frame that
     * the ZAnimationScheduler runs.
     */
    public void animationRateByNextFrame() {
        fAnimationRateMode = ANIMATION_RATE_BY_NEXT_FRAME;
    }
    /**
     * Template method that is called when the first frame of the animation is
     * animated after <code>play</code> has been called.
     */
    protected void animationStarted() {
        if (fStartedRunnable != null) {
            fStartedRunnable.run();
        }
    }
    /**
     * Template method that is called when the animation is temporarily stopped with
     * the <code>stop</code> method or when the animation finishes.
     */
    protected void animationStopped() {
        if (fStoppedRunnable != null) {
            fStoppedRunnable.run();
        }
    }
    /**
     * Return the alpha object used to determine the animations start and finish time,
     * and to generate any alpha values that it needs when interpolating between values.
     *
     * @return the animations alpha object, the default value is null.
     */
    public ZAlpha getAlpha() {
        return fAlpha;
    }
    /**
     * Return true if the animation has been stopped. It can be restarted with
     * the <code>play</code> method.
     */
    public boolean isStopped() {
        return isStopped;
    }
    /**
     * Schedule this animation with the ZAnimationScheduler. Once an animation is constructed it
     * is necessary to call this method to so that the animation is scheduled.
     */
    public void play() {
        if (isStopped()) {
            isStopped = false;
            hasSeenFirstFrameOfPlaySequence = false;
            scheduleNextFrame();
        }
    }
    /**
     * Schedule the next frame of the animation. This is normaly called as the last statement in
     * <code>animateFrameForTime</code>. It creates a new ZNextFrameCondition and schedules this
     * animation with that condition on the ZAnimationScheduler.
     */
    protected void scheduleNextFrame() {
        ZNextFrameCondition aCondition = null;

        switch (fAnimationRateMode) {
            case ANIMATION_RATE_BY_NEXT_FRAME: {
                aCondition = new ZNextFrameOnElapsedTime(fAlpha, 0);
                break;
            }
            case ANIMATION_RATE_BY_ELAPSED_TIME: {
                aCondition = new ZNextFrameOnElapsedTime(fAlpha, fAnimationRateValue);
                break;
            }
            case ANIMATION_RATE_BY_ELAPSED_FRAMES: {
                aCondition = new ZNextFrameOnElapsedFrames(fAlpha, fAnimationRateValue);
                break;
            }
        }
        scheduleNextFrame(aCondition);
    }
    /**
     * Schedules this animation with the given condition with the ZAnimationScheduler.
     */
    protected void scheduleNextFrame(ZNextFrameCondition aCondition) {
        ZAnimationScheduler.instance().scheduleAnimation(this, aCondition);
    }
    /**
     * Set the alpha object used to determine the animations start and finish time,
     * and to generate alpha values that it needs when interpolating between values.
     *
     * @param aAlpha the alpha
     */
    public void setAlpha(ZAlpha aAlpha) {
        fAlpha = aAlpha;
    }
    /**
     * Temporarily or permanently stop the animation. This will stop any animation frames
     * that have been scheduled with the ZAnimationScheduler from playing. The animation can
     * be restarted with <code>play</code> method.
     */
    public void stop() {
        if (!isStopped()) {
            isStopped = true;
            animationStopped();
        }
    }

    /**
     * Specify a Runnable that will be executed when the animation is started..
     */
    public void setStartedRunnable(Runnable aRunnable) {
        fStartedRunnable = aRunnable;
    }

    /**
     * Specify a Runnable that will be executed when the animation is stopped..
     */
    public void setStoppedRunnable(Runnable aRunnable) {
        fStoppedRunnable = aRunnable;
    }
}

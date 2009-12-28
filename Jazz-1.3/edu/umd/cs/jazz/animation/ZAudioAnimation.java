/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.animation;

import java.applet.*;

/**
 * <b>ZAudioAnimation</b> is designed to make it easy to synchronize an audio clip
 * with another animation. ZAudioAnimation does not really animate but it overrides
 * the <code>animationStarted</code> and <code>animationStarted</code> methods and starts
 * and stops it audio clip from within those methods.
 * <p>
 * To synchronize audio with an animation just create a new ZAudioAnimation with the audio
 * clip that is wanted, and schedule the audio animation with the same ZAlpha object that is
 * used for the other animation. This will cause the audio to start and end at the exact same
 * time as the animation it is being synchronized to.
 * <p>
 * @see ZAlpha
 * @author Jesse Grosjean
 */
public class ZAudioAnimation extends ZAnimation {

    private AudioClip fAudioClip;

    /**
     * Construct a new ZAudioAnimation.
     *
     * @param aAudioClip the audio clip that will be looped when the animation is started.
     */
    public ZAudioAnimation(AudioClip aAudioClip) {
        super();
        setAudioClip(aAudioClip);
    }

    /**
     * Return the audio clip that will be looped when the animation is started, and
     * stopped when the animation is stopped.
     */
    public AudioClip getAudioClip() {
        return fAudioClip;
    }

    /**
     * Set the audio clip that will be looped when the animation is started, and
     * stopped when the animation is stopped.
     */
    public void setAudioClip(AudioClip aAudioClip) {
        fAudioClip = aAudioClip;
    }

    /**
     * Start looping the audio clip.
     */
    protected void animationStarted() {
        getAudioClip().loop();
        super.animationStarted();
    }

    /**
     * Stop looping the audio clip.
     */
    protected void animationStopped() {
        getAudioClip().stop();
        super.animationStopped();
    }
}
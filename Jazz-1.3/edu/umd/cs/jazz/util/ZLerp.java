/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

/** 
 * <b>ZLerp</b> is an interface for specifying how to interpolate a double over an interval.
 * 
 * @author Ben Bederson
 * @see edu.umd.cs.jazz.ZDrawingSurface#findNodes
 */
public interface ZLerp {
    /**
     * This method specifies how to interpolate a double over the interval [0, 1].
     * Traditionally, a lerp (linear interpolation) smoothly varies a variable
     * from one point to another over time.  The standard lerp function follows
     * as t varies from 0 to 1.
     * <ul> <li>
     * x = x1 + t(x2 - x1)
     * </ul>
     * However, for animation, it is often desired to have animation functions
     * that have various properties instead of a pure smooth movement.  For instance,
     * a standard alternative is Slow-In-Slow-Out (SISO) where the animation starts
     * slowly, picks up speed in the middle, and slows down at the end.  This can
     * be implemented by modifying the standard lerp so that t is replaced with
     * a function that replaces t with a SISO version of t.  siso(t) might return
     * sub-linear values for small t, super-linear values for middle t, and sub-linear
     * values again for large t.
     * <p>
     * This function is meant to define a function that modifies t in a way
     * suitable for use by a lerp function.
     * @see edu.umd.cs.jazz.ZTransformGroup#animate
     */
    public double lerpTime(double t);
}

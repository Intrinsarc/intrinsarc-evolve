/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

/**
 * <b>ZTransformable</b> is an interface that represents an object
 * that has a transform that supports get and set.
 *
 * @author  Benjamin B. Bederson
 */
public interface ZTransformable {
    /**
     * Retrieves the 6 specifiable values of the affine transformation,
     * and places them into an array of double precisions values.
     * The values are stored in the array as
     * {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;m02&nbsp;m12&nbsp;}.
     * An array of 4 doubles can also be specified, in which case only the
     * first four elements representing the non-transform
     * parts of the array are retrieved and the values are stored into
     * the array as {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;}
     * @param matrix the double array used to store the returned
     * values.
     */
    public void getMatrix(double[] matrix);

    /**
     * Sets the affine transform.
     * @param m00,&nbsp;m01,&nbsp;m02,&nbsp;m10,&nbsp;m11,&nbsp;m12 the
     * 6 values that compose the 3x3 transformation matrix
     */
    public void setTransform(double m00, double m10,
			     double m01, double m11,
			     double m02, double m12);
}

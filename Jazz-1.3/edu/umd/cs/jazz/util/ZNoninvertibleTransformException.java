package edu.umd.cs.jazz.util;

/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.geom.*;

/**
 * <b>Thrown</b> to indicate that a AffineTransform could not be inverted. This
 * exception is thrown by Jazz when Jazz internaly catches a NoninvertibleTransformException.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 */
public class ZNoninvertibleTransformException extends RuntimeException {

    /**
     * The original exception that caused this one.
     */
    private NoninvertibleTransformException originalException;

    /**
     * ZNoninvertibleTransformException constructor comment.
     */
    public ZNoninvertibleTransformException(NoninvertibleTransformException e) {
        super(e.toString());
        originalException = e;
    }

    /**
     * Returns the original ZNoninvertibleTransformException that caused Jazz
     * to throw this exception.
     */
    public NoninvertibleTransformException getOriginalException() {
        return originalException;
    }
}
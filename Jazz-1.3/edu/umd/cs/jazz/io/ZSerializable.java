/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.io;

import java.io.*;
import java.util.Arrays;

/** 
 * <b>ZSerializable</b> indicates that a class can be read/written with
 * {@link ZObjectOutputStream}
 * <p>
 * If the object declares the <tt>ZSerializable writeReplace()</tt> method, then 
 * the object returned by writeReplace will be written out instead of this one.
 * If writeReplace() is specified, and returns null, then this object
 * is not written out at all.
 * <p>
 * If the object declares the <tt>ZSerializable readResolve()</tt> method, then 
 * the object returned by readResolve will be read in instead of this one.
 * In this case, the setState of the new object will be called instead of the
 * original one.  The new object can just handle whatever subset of the original
 * object's state as it wants - and ignore the rest.
 * If readResolve() is specified, and returns null, then this object
 * is not read in at all - and any references to this object will be replaced
 * with null references.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 * @author Britt McAlister
 */
public interface ZSerializable {
    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException;

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException;

    /**
     * Set some state of this object as it gets read back in.
     * After the object is created with its default no-arg constructor,
     * this method will be called on the object once for each bit of state
     * that was written out through calls to ZObjectOutputStream.writeState()
     * within the writeObject method.
     * @param fieldType The fully qualified type of the field
     * @param fieldName The name of the field
     * @param fieldValue The value of the field
     */
    public void setState(String fieldType, String fieldName, Object fieldValue);
}

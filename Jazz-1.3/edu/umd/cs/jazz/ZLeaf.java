/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.io.*;

import edu.umd.cs.jazz.io.*;

/** 
 * <b>ZLeaf</b> is a basic leaf node that doesn't have any children.
 * ZLeaf serves as a tag, identifying all sub-classes as being leaves. 
 * ZLeaf provides no other function, and there is no reason to instantiate
 * a ZLeaf object.  
 * 
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 */
public class ZLeaf extends ZNode implements ZSerializable, Serializable {

    //****************************************************************************
    //
    //              Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new empty leaf node.
     */
    public ZLeaf() {
    }
}

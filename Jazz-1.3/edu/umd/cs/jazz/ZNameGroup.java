/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.util.*;
import java.io.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZNameGroup</b> is a group node that names a portion of the scenegraph.
 * A name group node can be inserted into the tree when an application wants to 
 * assign a name to that section of the scenegraph. A static method 
 * ZNameGroup.getNameGroup(String name) returns the name group node associated with
 * the specified name.
 * <P>
 * {@link edu.umd.cs.jazz.util.ZSceneGraphEditor} provides a convenience mechanism to locate, create 
 * and manage nodes of this type.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Jim Mokwa
 * @see ZNameGroup#getNameGroup(String)
 */
public class ZNameGroup extends ZGroup implements ZSerializable, Serializable {
    /**
     * Hashtable whose key is String name, value is reference to ZNameGroup node. 
     */
    protected static Hashtable nameHash = new Hashtable();

    /**
     * The name of this ZNameGroup node.
     */
    protected String name;

    //****************************************************************************
    //
    //                  Constructors
    //
    //***************************************************************************

    /**
     * Constructs a new empty name group node.
     */
    public ZNameGroup() { }

    /**
     * Constructs a new name group node with the given name.
     * @param name The name of this group node.
     */
    public ZNameGroup(String name) {
	this.name = name;
	nameHash.put(name, this);
    }

    /**
     * Constructs a new name group node with the specified node as a child of the
     * new group.
     * @param child Child of the new group node.
     */
    public ZNameGroup(ZNode child) {
	super(child);
    }

    /**
     * Constructs a new name group node with the specified node as a child of the
     * new group, and with the specified name.
     * @param child Child of the new group node.
     * @param name The name of this group node.
     */
    public ZNameGroup(ZNode child, String name) {
	super(child);
	this.name = name;
	nameHash.put(name, this);
    }


    //****************************************************************************
    //
    // static nameGroup hashtable access methods
    //
    //****************************************************************************

    /**
     * Returns the ZNameGroup node associated with the specified name.
     * @param name The name associated with some ZNameGroup node.
     * @return the ZNameGroup node associated with the specified name.
     */
    static public ZNameGroup getNameGroup(String name) {
	ZNameGroup nameGroupNode = (ZNameGroup)nameHash.get(name);
	return nameGroupNode;
    }

    //****************************************************************************
    //
    // get/set methods
    //
    //****************************************************************************

    /**
     * Set the name of this nameGroup node. This name replaces any old name for this node.
     * If this name had previously been assigned to another node, it will now be
     * associated with this node.
     * @param aName The new name of this nameGroup node.
     */
    public void setName(String aName) {
	if (name != null) {
	    nameHash.remove(name);
	}
	name = aName;
	if (name != null) {
	    nameHash.put(name, this);
	}
    }

    /**
     * Get the name associated with this name group node.
     * @return a String containing the name of this name group node.
     */
    public String getName() {
	return name;
    }


    /////////////////////////////////////////////////////////////////////////
    //
    // Saving
    //
    /////////////////////////////////////////////////////////////////////////


    /**
     * Write out all of this object's state.
     * @param out The stream that this object writes into
     */
    public void writeObject(ZObjectOutputStream out) throws IOException {
	super.writeObject(out);

	out.writeState("String", "name", name);
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
	super.writeObjectRecurse(out);
    }

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
    public void setState(String fieldType, String fieldName, Object fieldValue) {
	super.setState(fieldType, fieldName, fieldValue);

	if (fieldName.compareTo("name") == 0) {
	    setName((String)fieldValue);
	}
    }

}

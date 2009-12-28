/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.util.HashMap;
import java.util.Iterator;
import java.io.*;

import edu.umd.cs.jazz.*;

/**
 * <b>ZObjectReferenceTable</b> helps to manage the references between objects within the
 * scenegraph when a portion of the scenegraph is duplicated with clone().
 * It maintains the relationship between all cloned objects, and the original
 * objects they were cloned from.  Then, after all objects have been duplicated, each
 * object's updateObjectReferences method is called. This method takes a
 * ZObjectReferenceTable object as a parameter. The object's
 * updateObjectReferences method can then use the getNewObjectReference
 * method from this object to get updated references to objects that have
 * been duplicated in the new sub-graph. If a match is found, a
 * reference to the corresponding object in the newly cloned sub-graph is
 * returned. If no corresponding reference is found, a
 * ZDanglingReferenceException is thrown.
 * <P>
 * ZObjectReferenceTable is a singleton, which means that there can only be
 * one instance of it in the entire run-time system.  Rather than call the
 * constructor, use the getInstance() method to access the single instance.
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
public class ZObjectReferenceTable implements Serializable {

    private HashMap table;

    /**
     * The single instance of this table.
     */
    static private ZObjectReferenceTable instance = null;

    
    /**
       Implements singleton for this class.  Always returns the same instance of ZObjectReferenceTable.
     */
    static public ZObjectReferenceTable getInstance() {
	if (instance == null) {
				// If this is the first time getInstance is called
	    instance = new ZObjectReferenceTable();
	}
	return instance;
    }

    /**
     * Constructor for new empty table.
     */
    protected ZObjectReferenceTable() { table = new HashMap(); }

    /**
     * Adds an original/cloned object pair to the table.
     * @param orig The original object
     * @param copy The copy of the original object
     */
    public void addObject(ZSceneGraphObject orig, ZSceneGraphObject copy) {
	table.put(orig, copy);
    }

    /**
     * Resets the table, removing all entries from it.
     */
    public void reset() {
	table.clear();
    }

    /**
     * Return an iterator for this table.
     */
    public Iterator iterator() {
        return table.values().iterator();
    }

    /**
     * This method is called to test if a node that is referenced by the object 
     * has been duplicated in the new cloned sub-graph. 
     * updateObjectReferences() calls this method with a reference to an old 
     * scene graph object (i.e. one that existed before the clone operation). If the 
     * object was cloned during the clone operation, the cloned version of the object
     * is returned. Otherwise, null is returned.
     * @param origObj The reference to the object in the original sub-graph.
     * @return The cloned version of the Object, or null if it was not cloned.
     */
    public ZSceneGraphObject getNewObjectReference(ZSceneGraphObject origObj) {
	return (ZSceneGraphObject)table.get(origObj);
    }
}

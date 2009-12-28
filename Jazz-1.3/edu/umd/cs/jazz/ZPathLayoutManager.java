/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import java.io.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZPathLayoutManager</b> positions a set of nodes along a path.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Lance Good
 * @author  Benjamin B. Bederson
 * @see     ZNode
 * @see     edu.umd.cs.jazz.util.ZLayout
 */

public class ZPathLayoutManager implements ZLayoutManager, ZSerializable {
				// Still need to add support for: 
				//    scale down at each level
    // The shape of the path
    private transient Shape shape = null;

    // The layout path
    private ArrayList path = null;

    // Should this layout manager layout its children in inverse order
    boolean invertChildren = false;
    
    // Says whether path is closed
    private boolean closed = false;

    // The flatness of the FlattingPathIterator
    private double flatness = 1.0;

    // Should the distribute function iterate or use the specified space
    private boolean exact = true;

    // The specified spacing
    private double space = -1.0;

    // The tolerance allowed if exact is specified
    private double tolerance = -1.0;
    
    /**
     * Default Constructor - uses default values unless specifically set
     */
    public ZPathLayoutManager() {
	this(new Line2D.Double(0.0,0.0,1.0,1.0));
    }

    /**
     * Constructor that accepts a shape
     * @param s The shape used for layout
     */
    public ZPathLayoutManager(Shape s) {
	path = new ArrayList();
	setShape(s);
    }

    /** 
    * Returns a clone of this layout manager. 
    */
    public Object clone() {
	ZPathLayoutManager newObject;
	try {
	    newObject = (ZPathLayoutManager)super.clone();
	} catch (CloneNotSupportedException e) {
	    throw new RuntimeException("Object.clone() failed: " + e);
	}

	if (path != null) {
	    newObject.path = (ArrayList)path.clone();
	}

	if (shape != null) {
	    // JM - not done yet: The shape interface  doesn't include a clone() method,
	    // so to clone a shape we either must cast it to a specific shape class
	    // and call clone() on that, or use reflection to invoke the clone()
	    // method. For now, we just continue referencing the old shape.	
	}

	return newObject;
    }

    /**
     * Notify the layout manager that a potentially recursive layout is starting.
     * This is called before any children are layed out.
     * @param The node to apply this layout algorithm to.
     */
    public void preLayout(ZGroup node) {
    }

    /**
     * Notify the layout manager that the layout for this node has finished
     * This is called after all children and the node itself are layed out.
     * @param node The node to apply this layout algorithm to.
     */
    public void postLayout(ZGroup node) {
    }

    /**
     * Apply this manager's layout algorithm to the specified node's children,
     * and animate the changes over time.
     * @param node The node to apply this layout algorithm to.
     * @param millis The number of milliseconds over which to animate layout changes.
     */
    public void doLayout(ZGroup node, int millis) {
	System.out.println("WARNING: Layout animation not implemented yet - layout being applied without animation.");
	doLayout(node);
    }

    /**
     * Apply this manager's layout algorithm to the specified node's children.
     * @param node The node to apply this layout algorithm to.
     */
    public void doLayout(ZGroup node) {
	ZNode primary = node.editor().getNode();
	if (primary instanceof ZGroup) {
	    ZNode[] children = ((ZGroup)primary).getChildren();
	    if (invertChildren) {
		ZNode tmp;
		for(int i=0; i<(children.length/2); i++) {
		    tmp = children[i];
		    children[i] = children[children.length-i-1];
		    children[children.length-i-1] = tmp;
		}
	    }
	    
	    if (exact) {
		if (space >= 0) {
		    ZLayout.distribute(children, path, space, tolerance, true, closed);
		}
		else {
		    ZLayout.distribute(children, path, tolerance, closed);
		}
	    }
	    else {
		if (space >= 0) {
		    ZLayout.distribute(children, path, space, tolerance, false, closed);
		}
		else {
		    ZLayout.distribute(children, path, false, closed);
		}
	    }
	}
    }


    /**
     * Sets whether this layout manager should invert its children before
     * laying them out.  That is, should it lay out the children from front
     * to back rather than back to front
     */
    public void setInvertChildren(boolean invertChildren) {
	this.invertChildren = invertChildren;
    }

    /**
     * Gets whether this layout manager inverts its children before
     * laying them out.  That is, does it lay out the children from front
     * to back rather than back to front     
     */
    public boolean getInvertChildren() {
	return invertChildren;
    }
    
    /**
     * Determine whether the path is closed.
     * @return Is the path closed?
     */
    public boolean getClosed() {
	return this.closed;
    }

    /**
     * Sets the path open or closed.
     * @param closed True to make this path closed.
     */
    public void setClosed(boolean closed) {
	this.closed = closed;
    }

    /**
     * 
     * Return the current flatness of the FlatteningPathIterator used to convert the Shape to points.
     * @return the current flatness.
     */
    public double getFlatness() {
	return flatness;
    }

    /**
     * Sets the flatness of the FlatteninPathIterator used to convert the Shape to points.
     * @param flatness the flatness.
     */
    public void setFlatness(double flatness) {
	this.flatness = flatness;
	setShape(shape);
    }

    /**
     * Determine if exact spacing was specified.
     * @return true if exact spacing was specified, false otherwise.
     */
    public boolean getExact() {
	return exact;
    }

    /**
     * Sets whether the algorithm should iterate to get exact spacing
     * or should only run once.
     * @param exact Whether exact spacing is specified.
     */
    public void setExact(boolean exact) {
	this.exact = exact;
    }

    /**
     * Get the tolerance allowed if exact spacing is specified.
     * @return The tolerance.
     */
    public double getTolerance() {
	return tolerance;
    }

    /**
     * Sets the tolerance, ie the error allowed, in laying out objects
     * on the path.
     * @param tolerance The tolerance allowed if exact spacing is specified.
     */
    public void setTolerance(double tolerance) {
	this.tolerance = tolerance;
    }

    /**
     * Get the shape currently in use by this object.
     * @return The shape.
     */
    public Shape getShape() {
	return shape;
    }
    
    /**
     * Sets the shape that this layout manager will use.  Gets the points
     * from this shape using a FlatteningPathIterator with flatness constant
     * of 1.0 by default.
     * @param s The desired layout shape
     */      
    public void setShape(Shape shape) {
	this.shape = shape;

	PathIterator p = shape.getPathIterator(new AffineTransform());
		
	FlatteningPathIterator fp = new FlatteningPathIterator(p, flatness);
		
	path.clear();
	while(!fp.isDone()) {
	    double[] farr = new double[6];
	    int type = fp.currentSegment(farr);
		    
	    if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO) {
		path.add(new Point2D.Double(farr[0],farr[1]));
	    }
	    if (type == PathIterator.SEG_QUADTO) {
		for(int i=0; i<2; i++) {
		    path.add(new Point2D.Double(farr[0],farr[1]));
		}
	    }
	    if (type == PathIterator.SEG_CUBICTO) {
		for(int i=0; i<3; i++) {
		    path.add(new Point2D.Double(farr[2*i],farr[2*i+1]));
		}
	    }
	    fp.next();
	}
    }

    /**
     * The current spacing used by the layout algorithm during its first iteration.
     * @return The current spacing.
     */
    public double getSpace() {
	return space;
    }
    
    /**
     * Set the spacing used by the layout algorithm during its first iteration.
     * @param space The spacing.
     */
    public void setSpacing(double space) {
	this.space = space;
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
    }

    /**
     * Specify which objects this object references in order to write out the scenegraph properly
     * @param out The stream that this object writes into
     */
    public void writeObjectRecurse(ZObjectOutputStream out) throws IOException {
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
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	in.defaultReadObject();
	setShape(new Line2D.Double(0.0,0.0,1.0,1.0));
    }
}

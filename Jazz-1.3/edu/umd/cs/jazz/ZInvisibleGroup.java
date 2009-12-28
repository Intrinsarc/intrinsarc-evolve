/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.util.*;
import java.io.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZInvisibleGroup</b> is a group node that completely hides its descendents.
 * It does not render anything, nor does it pick or find any children.  In addition,
 * an invisible group always has empty bounds.  An invisible group can be inserted
 * into a scenegraph when a portion of the tree needs to be temporarily hidden.
 * <P>
 * {@link ZSceneGraphEditor} provides a convenience mechanism to locate, create 
 * and manage nodes of this type.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 */
public class ZInvisibleGroup extends ZGroup implements ZSerializable, Serializable {
	//****************************************************************************
	//
	//                  Constructors
	//
	//***************************************************************************

	/**
	 * Constructs a new empty invisible group node.
	 */
	public ZInvisibleGroup() {
	}

	/**
	 * Constructs a new invisible group node with the specified node as a child of the
	 * new group.
	 * @param child Child of the new group node.
	 */
	public ZInvisibleGroup(ZNode child) {
		super(child);
	}

	//****************************************************************************
	//
	// Static convenience methods to make a sub-tree in/visible
	//
	//***************************************************************************

	/**
	 * Make the sub-tree rooted at the specified node invisible or visible.
	 * If the node is being made invisible, this inserts an invisible node above that node
	 * (if there isn't already one there.)
	 * If it is being made visible, then an invisible nodes directly above
	 * the specified node is removed if there is one.
	 * @param node the node to make invisible
	 */
	static public void setVisible(ZNode node, boolean visible) {
		if (visible) {
			node.editor().removeInvisibleGroup();
		}
		else {
			node.editor().getInvisibleGroup();
		}
	}

	//****************************************************************************
	//
	// Painting related methods
	//
	//***************************************************************************

	/**
	 * An invisible node does not get rendered at all, nor do any of its children.
	 * @param renderContext The graphics context to use for rendering.
	 */
	public void render(ZRenderContext renderContext) {
	}

	/**
	 * Because repaints are irrelevant - they are not passed up the scenegraph
	 */
	public void repaint() {
	}

	/**
	 * Because repaints are irrelevant - they are not passed up the scenegraph
	 */
	public void repaint(ZBounds bounds) {
	}

	/**
	 * An invisible group always has empty bounds.
	 */
	protected void computeBounds() {
		bounds.reset();
	}

	//****************************************************************************
	//
	//			Other Methods
	//
	//****************************************************************************

	/**
	 * An invisible node never gets picked, nor does it pick any of its children.
	 *
	 * @param rect Coordinates of pick rectangle in local coordinates
	 * @param path The path through the scenegraph to the picked node. Modified by this call.
	 * @return The picked node, or null if none
	 */
	public boolean pick(Rectangle2D rect, ZSceneGraphPath path) {
		return false;
	}

	/**
	 * In invisible node never is found, nor are any of its children.
	 *
	 * @param filter The filter that decides whether or not to include individual nodes in the find list
	 * @param nodes the accumulation list (results will be place here).
	 * @return the number of nodes searched
	 */
	protected int findNodes(ZFindFilter filter, ArrayList nodes) {
		return 0;
	}
}

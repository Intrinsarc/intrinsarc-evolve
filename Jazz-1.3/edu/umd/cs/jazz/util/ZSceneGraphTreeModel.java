/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.util;

import edu.umd.cs.jazz.*;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;

/**
 * <B>ZSceneGraphTreeModel</B> implements a TreeModel used by ZSceneGraphTreeView
 * to implement a jazz sceneGraph browser.
 * @see ZSceneGraphTreeView
 */
public class ZSceneGraphTreeModel implements TreeModel {
    private ArrayList treeModelListeners = new ArrayList();
    private TreePart root;

    class TreePart {
	ZSceneGraphObject obj;
	
	TreePart(ZSceneGraphObject obj) { this.obj = obj; }

	TreePart getChild(int index) {
	    if (obj instanceof ZVisualGroup) {
		ZVisualGroup vg = (ZVisualGroup)obj;
		if (vg.getBackVisualComponent() != null) {		  
		    if (index == 0) {
			return getTreePart(vg.getBackVisualComponent());
		    } else {
			index--;
		    }
		}
		if (vg.getFrontVisualComponent() != null) {		  
		    if (index == 0) {
			return getTreePart(vg.getFrontVisualComponent());
		    } else {
			index--;
		    }
		} 
	    }

	    if (obj instanceof ZGroup) {
		ZGroup g = (ZGroup)obj;
		if (index >= 0 && index < g.getNumChildren()) {
		    return getTreePart(g.getChild(index));
		}		
	    }
	    
	    if (obj instanceof ZVisualLeaf) {
		ZVisualLeaf vl = (ZVisualLeaf)obj;
		if (index < vl.getNumVisualComponents()) {
		    return getTreePart(vl.getVisualComponent(index));
		}
	    }
	    
	    return null;
	}

	int getChildCount() {
	    int count = 0;
	    if (obj instanceof ZGroup) count = ((ZGroup)obj).getNumChildren();
	    if (obj instanceof ZVisualLeaf) count += ((ZVisualLeaf)obj).getNumVisualComponents();
	    if (obj instanceof ZVisualGroup) {
		ZVisualGroup vg = (ZVisualGroup)obj;
		if (vg.getBackVisualComponent() != null) count++;
		if (vg.getFrontVisualComponent() != null) count++;
	    }
	    
	    return count;
	}

	int getIndexOfChild(TreePart child) {
	    int offs = 0;
	    ZSceneGraphObject childNode = child.obj;
	    
	    if (obj instanceof ZVisualGroup) {
		ZVisualGroup vg = (ZVisualGroup)obj;
		if (vg.getFrontVisualComponent() != null) {
		    if (childNode == vg.getBackVisualComponent()) {
			return 0;
		    } else {
			offs ++;
		    }
		} 
		if (vg.getFrontVisualComponent() != null) {
		    if (childNode == vg.getFrontVisualComponent()) {
			return offs;
		    } else {
			offs++;
		    }
		} 
	    }
	    
	    if (obj instanceof ZGroup) {
		ZGroup g = (ZGroup)obj;
		for (int i = 0; i < g.getNumChildren(); i++) {
		    if (g.getChild(i) == childNode) 
			return i + offs;	    
		}
	    }

	    if (obj instanceof ZVisualLeaf) {
		if (((ZVisualLeaf)obj).getFirstVisualComponent() == childNode)
		    return 0;
	    }

	    return -1;
	}

	boolean isLeaf() {
	    return !(obj instanceof ZGroup || obj instanceof ZVisualLeaf);
	}

	public String toString() {
	    if (obj == null) {
		return "Empty";
	    } else {
		if (obj instanceof ZVisualLeaf) {
		    ZVisualLeaf lf = (ZVisualLeaf)obj;
		    String name = "ZVisualLeaf [";
		    for (int i=0; i<lf.getNumVisualComponents(); i++) {
			if (i > 0) {
			    name += ", ";
			}
			TreePart tp = getTreePart(((ZVisualLeaf)obj).getVisualComponent(i));
			name += tp.toString();
		    }
		    name += "]";
		    return name;
		} else if (obj instanceof ZNameGroup) {
		    String name = "ZNameGroup";
		    ZNameGroup nameGroup = (ZNameGroup)obj;
		    if (nameGroup.getName() != null) {
			name += " [";
			name += ((ZNameGroup)obj).getName();
			name += "]";
		    }
		    return name;
		} else {
		    String name = obj.getClass().getName();
		    int dot = name.lastIndexOf('.');
		    return ((dot > 0) ? name.substring(dot + 1) : name);
		}
	    }
    	}
    }

    private HashMap table = new HashMap();
    private TreePart nullTreePart = new TreePart(null);

    TreePart getTreePart(ZSceneGraphObject obj) {
	if (obj == null) {
	    return nullTreePart;
	} else {
	    TreePart tp = (TreePart)table.get(obj);
	    if (tp == null) { tp = new TreePart(obj); table.put(obj, tp); }
	    return tp;
	}
    }

    TreePath getTreePath(ZNode object) {
	ArrayList list = new ArrayList();
	while (object != null) {
	    list.add(getTreePart(object));
	    object = object.getParent();
	}
	
	Object[] path = new Object[list.size()];
	for (int i = 0; i < path.length; i++) {
	    path[i] = list.get(path.length - i - 1);
	}
	return new TreePath(path);
    }


    public ZSceneGraphTreeModel(ZRoot root) {
        this.root = getTreePart(root);
    }

    //////////////// Fire events //////////////////////////////////////////////

    /**
     * The only event raised by this model is TreeStructureChanged with the
     * root as path, i.e. the whole tree has changed.
     */
    void fireTreeStructureChanged() {
        int len = treeModelListeners.size();
        TreeModelEvent e = new TreeModelEvent(this, 
                                              new Object[] {root});
        for (int i = 0; i < len; i++) {
            ((TreeModelListener)treeModelListeners.get(i)).
                    treeStructureChanged(e);
        }
    }


    //////////////// TreeModel interface implementation ///////////////////////


    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.add(l);
    }

    /**
     * Removes a listener previously added with addTreeModelListener().
     */
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.remove(l);
    }


    /**
     * Returns the child of parent at index index in the parent's child array.
     */
    public Object getChild(Object parent, int index) {
        return ((TreePart)parent).getChild(index);
    }

    /**
     * Returns the number of children of parent.
     */
    public int getChildCount(Object parent) {
        return ((TreePart)parent).getChildCount();
    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object parent, Object child) {
        return ((TreePart)parent).getIndexOfChild((TreePart)child);
    }

    /**
     * Returns the root of the tree.
     */
    public Object getRoot() {
        return root;
    }


    /**
     * Returns the object as a jazz ZSceneGraphObject.
     * @param object the object.
     */
    public ZSceneGraphObject getJazzObject(Object object) {
	if (object instanceof TreePart) 
	    return ((TreePart)object).obj;
	else
	    return null;
    }

    /**
     * Returns true if obj is a leaf.
     */
    public boolean isLeaf(Object obj) {
        return ((TreePart)obj).isLeaf();
    }

    /**
     * Messaged when the user has altered the value for the item
     * identified by path to newValue.  Not used by this model.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
                           + path + " --> " + newValue);
    }
}

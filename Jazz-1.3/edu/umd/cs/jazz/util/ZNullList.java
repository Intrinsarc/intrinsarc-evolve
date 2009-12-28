package edu.umd.cs.jazz.util;

/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.awt.geom.*;
import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;

/**
 * This class provides a stand in for ZLists that have no elements. Instead of setting an empty ZList
 * to null we set it to the static instance of ZNullList that is located in ZListImpl. Doing this enables
 * us to avoid == null checks everytime we need to use a ZList.
 *
 * @author Jesse Grosjean
 */
public class ZNullList extends ZListImpl implements ZList.ZPropertyList,
                                                    ZList.ZNodeList,
                                                    ZList.ZVisualComponentList,
                                                    ZList.ZLayerGroupList,
                                                    ZList.ZBoundsList,
                                                    ZList.ZCameraList {
    public void clear() {}
    public ZBounds collectiveBoundsReference(ZBounds bounds) { return bounds; }
    public boolean collectiveHasVolatileBounds() { return false; }
    public ZSceneGraphObject collectivePick(Rectangle2D rect, ZSceneGraphPath path) { return null; }
    public void collectiveRender(ZRenderContext renderContext, ZBounds visibleBounds) {}
    public void collectiveRepaint(ZBounds bounds) {}
    public Object[] createElementData(int capacity) { return null; }
    // ZBoundsList Implementation
    public ZBounds[] getBoundsReference() { return null; }
    public Object[] getElementData() { return null; }
    // ZLayerGroupList Implementation
    public ZLayerGroup[] getLayersReference() { return null; }
    public Object getMatchingProperty(Object key) { return null; }
    // ZNodeList Implementation.
    public ZNode[] getNodesReference() { return null; }
    // ZPropertyList Implementation.
    public ZProperty[] getPropertiesReference() { return null; }
    // ZVisualComponentList Implementation.
    public ZVisualComponent[] getVisualComponentsReference() { return null; }
    public int indexOfPropertyWithKey(Object key) { return -1; }
    public boolean isNull() { return true; }
    public void setElementData(Object[] data) {}
    public int size() { return 0; }
    public Object[] toArray() { return null; }
    public void trimToSize() {}
    public void writeObject(ZObjectOutputStream out) throws IOException {}
    //public ZCamera[] getCamerasReference() { return null; }
    public ZCamera[] getCamerasReference() { return null; }
}
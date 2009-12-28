package edu.umd.cs.jazz.util;

/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.io.*;
import edu.umd.cs.jazz.io.*;
import java.util.*;
import java.awt.geom.*;
import edu.umd.cs.jazz.*;

/**
 * Collections of objects in the Jazz framework are usual contained in ZLists, as implemented by
 * ZListImpl.
 *
 * @author: Jesse Grosjean
 */
public interface ZList extends List, Cloneable {

    public static interface ZNodeList extends ZList {
        public ZNode[] getNodesReference();
        public ZBounds collectiveBoundsReference(ZBounds bounds);
        public void collectiveRender(ZRenderContext renderContext, ZBounds visibleBounds);
        public boolean collectiveHasVolatileBounds();
        public ZSceneGraphObject collectivePick(Rectangle2D rect, ZSceneGraphPath path);
        public void collectiveRepaint(ZBounds bounds);
    }

    public static interface ZPropertyList extends ZList {
        public ZProperty[] getPropertiesReference();
        public Object getMatchingProperty(Object key);
        public int indexOfPropertyWithKey(Object key);
    }

    public static interface ZCameraList extends ZList {
        public ZCamera[] getCamerasReference();
    }

    public static interface ZVisualComponentList extends ZList {
        public ZVisualComponent[] getVisualComponentsReference();
        public ZBounds collectiveBoundsReference(ZBounds bounds);
        public ZSceneGraphObject collectivePick(Rectangle2D rect, ZSceneGraphPath path);
        public boolean collectiveHasVolatileBounds();
    }
    public static interface ZLayerGroupList extends ZList {
        public ZLayerGroup[] getLayersReference();
    }
    public static interface ZBoundsList extends ZList {
        public ZBounds[] getBoundsReference();
    }
    public static interface ZSceneGraphObjectList extends ZList {
        public ZSceneGraphObject[] getSceneGraphObjectsReference();
    }
    public static interface ZTransformableList extends ZList {
        public ZTransformable[] getTransformablesReference();
        public AffineTransform collectiveCatTransformUntil(ZCamera camera);
    }
    public Object clone();
    public Object[] getElementData();
    public boolean isNull();
    public void moveElementToIndex(Object elem, int newIndex);
    public void pop();
    public void pop(Object element);
    public boolean replaceWith(Object oldElement, Object newElement);
    public void setSize(int size);
    public void trimToSize();
    public void writeObject(String name, ZObjectOutputStream out) throws IOException;
}

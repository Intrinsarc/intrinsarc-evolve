/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.util;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;

/**
 * <b>ZListImpl</b> Implementation of ZList. This class is designed to provide a thin wrapper
 * around an array data structure containing objects. In this respect it is similar to
 * java.util.ArrayList. Unlike java.util.ArrayList ZListImpl this class is abstract, and has a subclass
 * for each type of object that we want to store in a ZList.
 *
 * This is done for two reasons. First it allows us to deal with collections of objects without having
 * to cast the objects every time we wish to iterate over them. It also allows subclasses to implement
 * type specific collection methods so that that code is not spread throughout the Jazz framework.
 *
 * @author Jesse Grosjean
 */
public abstract class ZListImpl extends AbstractList implements ZList, Serializable {

    public static ZNullList NullList = new ZNullList();

    protected int size;

    /**
     * For each type of array that we had we should have a subclass that
     * suplies its own element data of that type. NOTE ZArrayList should become abstract
     * each subclass being forced to provide an array to store the data in.
     *
     * this way subclasses can also provide type specific methods. So the ZNodeList could
     * have a agragate getBoundsReference() method that gets the bounds of all objects in the list.
     */
    public static class ZNodeListImpl extends ZListImpl implements ZList.ZNodeList {
        private transient ZNode[] nodes;

        public ZNodeListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return nodes;
        }
        public ZNode[] getNodesReference() {
            return nodes;
        }
        public Object[] createElementData(int capacity) {
            return new ZNode[capacity];
        }
        public void setElementData(Object[] data) {
            nodes = (ZNode[]) data;
        }
        public ZBounds collectiveBoundsReference(ZBounds bounds) {
            for (int i=0; i<this.size; i++) {
                bounds.add(nodes[i].getBoundsReference());
            }
            return bounds;
        }
        public void collectiveRender(ZRenderContext renderContext, ZBounds visibleBounds) {
            for (int i = 0; i < this.size; i++) {
                if (visibleBounds.intersects(nodes[i].getBoundsReference())) {
                    nodes[i].render(renderContext);
                }
            }
        }
        public boolean collectiveHasVolatileBounds() {
            for (int i=0; i<this.size; i++) {
                if (nodes[i].getVolatileBounds()) {
                    return true;
                }
            }
            return false;
        }
        public ZSceneGraphObject collectivePick(Rectangle2D rect, ZSceneGraphPath path) {
            for (int i = (this.size - 1); i >= 0; i--) {
                if (nodes[i].getBoundsReference().intersects(rect)) {
                    if (nodes[i].pick(rect, path)) {
                        return nodes[i];
                    }
                }
            }
            return null;
        }
        public void collectiveRepaint(ZBounds bounds) {
            if (bounds == null) {
                for (int i = 0; i < this.size; i++) {
                    nodes[i].repaint();
                }
            } else {
                for (int i = 0; i < this.size; i++) {
                    nodes[i].repaint(bounds);
                }
            }
        }
    }

    public static class ZPropertyListImpl extends ZListImpl implements ZList.ZPropertyList {
        private transient ZProperty[] properties;

        public ZPropertyListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return properties;
        }
        public Object[] createElementData(int capacity) {
            return new ZProperty[capacity];
        }
        public void setElementData(Object[] data) {
            properties = (ZProperty[]) data;
        }
        public ZProperty[] getPropertiesReference() {
            return properties;
        }
        public Object getMatchingProperty(Object key) {
            for (int i = 0; i < this.size; i++) {
                if (properties[i].getKey().equals(key)) {
                    return properties[i].getValue();
                }
            }
            return null;
        }
        public int indexOfPropertyWithKey(Object key) {
            for (int i = 0; i < this.size; i++) {
                if (properties[i].getKey().equals(key)) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static class ZCameraListImpl extends ZListImpl implements ZList.ZCameraList {
        private transient ZCamera[] cameras;

        public ZCameraListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return cameras;
        }
        public Object[] createElementData(int capacity) {
            return new ZCamera[capacity];
        }
        public void setElementData(Object[] data) {
            cameras = (ZCamera[]) data;
        }
        public ZCamera[] getCamerasReference() {
            return cameras;
        }
    }

    public static class ZVisualComponentListImpl extends ZListImpl implements ZList.ZVisualComponentList {
        private transient ZVisualComponent[] visualComponents;

        public ZVisualComponentListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return visualComponents;
        }
        public Object[] createElementData(int capacity) {
            return new ZVisualComponent[capacity];
        }
        public void setElementData(Object[] data) {
            visualComponents = (ZVisualComponent[]) data;
        }
        public ZVisualComponent[] getVisualComponentsReference() {
            return visualComponents;
        }
        public ZBounds collectiveBoundsReference(ZBounds bounds) {
            for (int i=0; i<this.size; i++) {
                bounds.add(visualComponents[i].getBoundsReference());
            }
            return bounds;
        }
        public ZSceneGraphObject collectivePick(Rectangle2D rect, ZSceneGraphPath path) {
            for (int i = (this.size - 1); i >= 0; i--) {
                if (visualComponents[i].pick(rect, path)) {
                    return visualComponents[i];
                }
            }
            return null;
        }
        public boolean collectiveHasVolatileBounds() {
            for (int i=0; i<this.size; i++) {
                if (visualComponents[i].getVolatileBounds()) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class ZLayerGroupListImpl extends ZListImpl implements ZList.ZLayerGroupList {
        private transient ZLayerGroup[] layers;

        public ZLayerGroupListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return layers;
        }
        public Object[] createElementData(int capacity) {
            return new ZLayerGroup[capacity];
        }
        public void setElementData(Object[] data) {
            layers = (ZLayerGroup[]) data;
        }
        public ZLayerGroup[] getLayersReference() {
            return layers;
        }
    }

    public static class ZBoundsListImpl extends ZListImpl implements ZList.ZBoundsList {
        private transient ZBounds[] bounds;

        public ZBoundsListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return bounds;
        }
        public Object[] createElementData(int capacity) {
            return new ZBounds[capacity];
        }
        public void setElementData(Object[] data) {
            bounds = (ZBounds[]) data;
        }
        public ZBounds[] getBoundsReference() {
            return bounds;
        }
    }

    public static class ZSceneGraphObjectListImpl extends ZListImpl implements ZList.ZSceneGraphObjectList {
        private transient ZSceneGraphObject[] sceneGraphObjects;

        public ZSceneGraphObjectListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return sceneGraphObjects;
        }
        public Object[] createElementData(int capacity) {
            return new ZSceneGraphObject[capacity];
        }
        public void setElementData(Object[] data) {
            sceneGraphObjects = (ZSceneGraphObject[]) data;
        }
        public ZSceneGraphObject[] getSceneGraphObjectsReference() {
            return sceneGraphObjects;
        }
    }

    public static class ZObjectListImpl extends ZListImpl {
        private transient Object[] objects;

        public ZObjectListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return objects;
        }
        public Object[] createElementData(int capacity) {
            return new Object[capacity];
        }
        public void setElementData(Object[] data) {
            objects = (Object[]) data;
        }
    }

    public static class ZTransformableListImpl extends ZListImpl implements ZList.ZTransformableList {
        private transient ZTransformable[] transformables;

        public ZTransformableListImpl(int capacity) {
            super(capacity);
        }
        public Object[] getElementData() {
            return transformables;
        }
        public Object[] createElementData(int capacity) {
            return new ZTransformable[capacity];
        }
        public void setElementData(Object[] data) {
            transformables = (ZTransformable[]) data;
        }
        public ZTransformable[] getTransformablesReference() {
            return transformables;
        }
        public AffineTransform collectiveCatTransformUntil(ZCamera camera) {
            AffineTransform tmpTransform = new AffineTransform();
            AffineTransform catTransform = new AffineTransform();

            for (int i = 0; i < this.size; i++) {
                if (transformables[i] == camera) {
                    break;
                } else {
                    double[] matrix = new double[6];
                    transformables[i].getMatrix(matrix);
                    catTransform.setTransform(matrix[0],matrix[1],matrix[2],
                                              matrix[3],matrix[4],matrix[5]);
                    tmpTransform.concatenate(catTransform);
                }
            }
            return tmpTransform;
        }
    }


    public ZListImpl() {
        this(10);
    }
    public ZListImpl(int capacity) {
        setElementData(createElementData(capacity));
    }



    public void add(int index, Object element) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(
            "Index: "+index+", Size: "+size);

        ensureCapacity(size+1);
        System.arraycopy(getElementData(), index,
                         getElementData(), index + 1, size - index);

        getElementData()[index] = element;
        size++;
    }

    public boolean add(Object o) {
        ensureCapacity(size + 1);
        getElementData()[size++] = o;
        return true;
    }

    public boolean addAll(int index, Collection c) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(
            "Index: "+index+", Size: "+size);

        int numNew = c.size();
        ensureCapacity(size + numNew);

        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(getElementData(), index, getElementData(), index + numNew,
                     numMoved);

        Iterator e = c.iterator();
        Object[] elementData = getElementData();
        for (int i=0; i<numNew; i++)
            elementData[index++] = e.next();

        size += numNew;
        return numNew != 0;
    }

    public boolean addAll(Collection c) {
        return addAll(size, c);
    }

    public void clear() {
        modCount++;

        Object[] elementData = getElementData();
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }

    public Object clone() {
        try {
            ZListImpl v = (ZListImpl) super.clone();
            v.setElementData(createElementData(size));
            System.arraycopy(getElementData(), 0, v.getElementData(), 0, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public boolean contains(Object elem) {
        return indexOf(elem) >= 0;
    }

    public abstract Object[] createElementData(int size);

    public void ensureCapacity(int minCapacity) {
        modCount++;

        int oldCapacity = getElementData().length;

        if (minCapacity > oldCapacity) {
            Object oldData[] = getElementData();
            int newCapacity = (oldCapacity * 3)/2 + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            setElementData(createElementData(newCapacity));
            System.arraycopy(oldData, 0, getElementData(), 0, size);
        }
    }

    public Object get(int index) {
        return getElementData()[index];
    }



    public abstract Object[] getElementData();

    public int indexOf(Object elem) {
        Object[] elementData = getElementData();
        if (elem == null) {
            for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
        } else {
            for (int i = 0; i < size; i++)
            if (elem.equals(elementData[i]))
                return i;
        }
        return -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isNull() {
        return false;
    }

    public int lastIndexOf(Object elem) {
        Object[] elementData = getElementData();
        if (elem == null) {
            for (int i = size-1; i >= 0; i--)
            if (elementData[i]==null)
                return i;
        } else {
            for (int i = size-1; i >= 0; i--)
            if (elem.equals(elementData[i]))
                return i;
        }
        return -1;
    }

    public void moveElementToIndex(Object elem, int newIndex) {
        // XXX could make this faster.
        int oldIndex = indexOf(elem);
        remove(oldIndex);
        add(newIndex, elem);
    }

    public void pop() {
        getElementData()[size-1] = null;
        size--;
    }

    public void pop(Object element) {
        Object[] elementData = getElementData();
        for (int i = size - 1; i >= 0; i--) {
            if (elementData[i] == element) {
                size = i;
                return;
            }
        }
        throw new IllegalArgumentException(element + " is not in list.");
    }

    private void rangeCheck(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(
            "Index: "+index+", Size: "+size);
    }

    private synchronized void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        int arrayLength = s.readInt();
        setElementData(createElementData(arrayLength));

        Object[] elementData = getElementData();
        for (int i=0; i<size; i++)
            elementData[i] = s.readObject();
    }

    public Object remove(int index) {
        rangeCheck(index);

        modCount++;
        Object oldValue = getElementData()[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(getElementData(), index+1, getElementData(), index,
                     numMoved);
        getElementData()[--size] = null; // Let gc do its work

        return oldValue;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = size - toIndex;
            System.arraycopy(getElementData(), toIndex,
                             getElementData(), fromIndex, numMoved);

        int newSize = size - (toIndex-fromIndex);
        Object[] elementData = getElementData();
        while (size != newSize)
            elementData[--size] = null;
    }

    // return true if successful, else false.
    public boolean replaceWith(Object oldElement, Object newElement) {
        Object[] elementData = getElementData();
        for (int i = 0; i < size; i++) {
            if (elementData[i] == oldElement) {
                elementData[i] = newElement;
                return true;
            }
        }
        return false;
    }

    public Object set(int index, Object element) {
        rangeCheck(index);

        Object oldValue = getElementData()[index];
        getElementData()[index] = element;
        return oldValue;
    }

    public abstract void setElementData(Object[] elementData);

    public void setSize(int aSize) {
        size = aSize; // XXX mabye should do some checks and cleanup here.
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        Object[] result = createElementData(size);
        System.arraycopy(getElementData(), 0, result, 0, size);
        return result;
    }

    public Object[] toArray(Object a[]) {
        if (a.length < size)
            a = (Object[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size);

        System.arraycopy(getElementData(), 0, a, 0, size);

        if (a.length > size)
            a[size] = null;

        return a;
    }

    public void trimToSize() {
        modCount++;
        int oldCapacity = getElementData().length;
        if (size < oldCapacity) {
            Object oldData[] = getElementData();
            setElementData(createElementData(size));
            System.arraycopy(oldData, 0, getElementData(), 0, size);
        }
    }

    private synchronized void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
        s.defaultWriteObject();

        Object[] elementData = getElementData();
        if (elementData == null) {
            s.writeInt(0);
        } else {
            s.writeInt(size);
        }

        for (int i=0; i<size; i++)
            s.writeObject(elementData[i]);
    }

    public void writeObject(String name, ZObjectOutputStream out) throws IOException {
        trimToSize();
        out.writeState("List", name, this);
    }
    public void writeObjectd(String name, ZObjectOutputStream out) throws IOException {
        trimToSize();
        out.writeState("List", name, this);
    }
}
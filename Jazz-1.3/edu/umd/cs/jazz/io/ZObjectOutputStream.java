/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.io;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

import com.sun.image.codec.jpeg.*;

import edu.umd.cs.jazz.util.*;

/** 
 * <b>ZObjectOutputStream</b> writes Jazz objects that are ZSerializable.
 * The process of writing out Jazz scenes is to make an instance of this
 * class, and then call writeObject() on a ZSerializable object.
 * <p>
 * Every object that gets written out must implement two methods that
 * control the writing process. The first method, writeObjectRecurse()
 * specifies (recursively) the objects related to this one that should be
 * written out by calling addObject() for those objects.  The second method, 
 * writeObject gets called on each object so it can specify how to write
 * out its state.
 * <p>
 * An object can specify a replacment object that should be written out
 * instead of itself by declaring the method:
 * <tt>ZSerializable writeReplace()</tt>.
 * If the replacement object is null, then the original object will
 * note be written out at all.  If it specifies a ZSerializable object,
 * then that object will be written out instead, and any references to
 * the original object will be replaced with references to the replacement
 * object.
 * <p>
 * Note that there is a potential for
 * conflict where an object can define that it should not be written out,
 * and yet a second object can reference it in its state.  Because no 
 * object can be allowed to reference an object that wasn't written out,
 * this io code will skip any such references, and instead insert a comment
 * into the file specifying that the reference was skipped.
 * <p>
 * ZObjectOutputStream knows how to write out the following types:
 * <ul>
 * <li>short
 * <li>integer
 * <li>long
 * <li>float
 * <li>double
 * <li>boolean
 * <li>byte
 * <li>String
 * <li>ZSerializable
 * <li>Rectangle2D
 * <li>AffineTransform
 * <li>Color
 * <li>Font
 * <li>Image
 * </ul>
 * 
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Ben Bederson
 * @author Britt McAlister
 */
public class ZObjectOutputStream extends OutputStream implements Serializable {
    static protected final Class[] NULL_ARGS = {};

    protected DataOutputStream dos;   // Stream to write data to
    protected int id;
    protected Hashtable objs;
    protected Hashtable unsavedObjs;
    protected Hashtable replacedObjs;
    protected boolean writingState;
    protected boolean stateWritten;

    public ZObjectOutputStream(OutputStream out) {
	dos = new DataOutputStream(out);
	objs = new Hashtable();
	unsavedObjs = new Hashtable();
	replacedObjs = new Hashtable();
    }

    /**
     * Determine if the specified object is savable.
     * @param obj Object to check
     * @return true if object is savable.
     */
    static public boolean isSavable(Object obj) {
	boolean rc = false;

	if ((obj instanceof Short) ||
	    (obj instanceof Integer) ||
	    (obj instanceof Long) ||
	    (obj instanceof Float) ||
	    (obj instanceof Double) ||
	    (obj instanceof Boolean) ||
	    (obj instanceof String) ||
	    (obj instanceof Byte) ||
	    (obj instanceof Color) ||
	    (obj instanceof Font) ||
	    (obj instanceof Rectangle2D) ||
	    (obj instanceof AffineTransform) ||
	    (obj instanceof Image) ||
	    (obj instanceof ZSerializable)) {
	    rc = true;
	}

	return rc;
    }

    public void writeObject(ZSerializable obj) throws IOException {
	int i = 0;
	int objId;
	ZSerializable zobj;

	id = 1;
	writingState = false;
	objs.clear();
	unsavedObjs.clear();
	replacedObjs.clear();

				// Write header
	dos.writeBytes("#JAZZ1.0");
	dos.writeBytes("\n");
				// Create table of all referenced objects.
	addObject(obj);

				// First sort them so they get written out in the order
				// they were added to the list.
	ZSerializable[] zobjs = new ZSerializable[objs.size()];
	for (Enumeration e = objs.keys(); e.hasMoreElements() ;) {
	    zobj = (ZSerializable)e.nextElement();
	    zobjs[i++] = zobj;
	}
	Arrays.sort(zobjs, new Comparator() {
	    public int compare(Object o1, Object o2) {
		Integer i1 = ((Integer)ZObjectOutputStream.this.objs.get(o1));
		Integer i2 = ((Integer)ZObjectOutputStream.this.objs.get(o2));
		
		return i1.compareTo(i2);
	    }
	});

				// Now, Write line defining each object
	dos.writeBytes("[\n");
	for (i=0; i<zobjs.length; i++) {
	    zobj = zobjs[i];
	    objId = ((Integer)objs.get(zobj)).intValue();
	    dos.writeBytes("  Z" + objId + " " + zobj.getClass().getName() + "\n");
	}	
	for (Enumeration e = unsavedObjs.keys(); e.hasMoreElements() ;) {
	    zobj = (ZSerializable)e.nextElement();
	    objId = ((Integer)unsavedObjs.get(zobj)).intValue();
	    dos.writeBytes("  # Unsaved object skipped: Z" + objId + " " + zobj.getClass().getName() + "\n");
	}
	dos.writeBytes("]\n");

				// Finally, write out each object's state
	for (i=0; i<zobjs.length; i++) {
	    zobj = zobjs[i];
	    objId = ((Integer)objs.get(zobj)).intValue();
	    dos.writeBytes("[OBJECT @Z" + objId + " # " + zobj.getClass().getName() + "\n");
	    dos.writeBytes("  {");
	    writingState = true;
	    stateWritten = false;
	    zobj.writeObject(this);
	    writingState = false;
	    if (stateWritten) {
		dos.writeBytes("  ");
	    }
	    dos.writeBytes("}\n");
	    dos.writeBytes("]\n");
	}
	dos.writeBytes("[RETURN @Z" + ((Integer)objs.get(obj)).intValue() + "]\n");
				// Finish by returning the object to return when reading in the file
				// Hard code return the first object for now
    }

    /**
     * Add this ZSerializable object to the list of objects to be written out.
     * Each object will only be written out once - so calling this method
     * multiple times on the same object will have no effect.
     * <p>
     * If the object declares the <tt>ZSerializable writeReplace()</tt> method, then 
     * the specified object will be written out instead of the one passed in.
     * If writeReplace() is specified, and returns null, then this object
     * is not written out at all.
     * @param obj The object to be written out
     */
    public void addObject(ZSerializable obj) throws IOException {
				// Check for class provided writeReplace substitution method.
				// Method can not be static.
	try {
	    Method writeReplaceMethod = obj.getClass().getDeclaredMethod("writeReplace", NULL_ARGS);
	    int mods = writeReplaceMethod.getModifiers();
	    if ((mods & Modifier.STATIC) == 0) {
				// Determine replacement object
		Object replacementObj = null;
		try {
		    replacementObj = writeReplaceMethod.invoke(obj, NULL_ARGS);

				// Keep list of unsaved objects so we can skip any references to original
		    if (replacementObj == null) {
			if (!unsavedObjs.containsKey(obj)) {
			    unsavedObjs.put(obj, new Integer(id));
			    id++;
			}
			obj = null;
		    } else {
			if (replacementObj != obj) {
			    if (!replacedObjs.containsKey(obj)) {
				replacedObjs.put(obj, replacementObj);
			    }
			    if (replacementObj instanceof ZSerializable) {
				obj = (ZSerializable)replacementObj;
			    } else {
				throw new IOException("ZObjectOutputStream.addObject: Error saving: " + obj + 
						      ", Replacement is not ZSerializable: " + replacementObj);
			    }
			}
		    }

		} catch (IllegalAccessException e) {
		    throw new IOException("ZObjectOutputStream.addObject: Error saving: " + obj + 
					  ", Can't access writeReplace method: " + e);
		} catch (InvocationTargetException e) {
		    throw new IOException("ZObjectOutputStream.addObject: Error saving: " + obj + ", " + e);
		}
	    }
	} catch (NoSuchMethodException e) {
				// If no replacement method - then just continue along without replacing object
	}

	if (obj != null) {
	    if (!objs.containsKey(obj)) {
		objs.put(obj, new Integer(id));
		id++;
		obj.writeObjectRecurse(this);
	    }
	}
    }

    public void writeState(String type, String name, java.util.List value) throws IOException {
	Object obj;
	boolean first = true;
	Vector unsavedObjsList = null;

	if (writingState == false) {
	    throw new IOException("ZObjectOutputStream.writeState: Error: can't call writeState unless within a writeObject method");
	}
	if (stateWritten == false) {
	    stateWritten = true;
	    dos.writeBytes("\n");
	}

				// First, go through vector of objects, and make list of unsavable ones.
				// Write out comment saying that unsavable ones are skipped.
	for (Iterator i=value.iterator(); i.hasNext();) {
	    obj = i.next();
	    if (!isSaved(obj)) {
		if (unsavedObjsList == null) {
		    unsavedObjsList = new Vector();
		}
		unsavedObjsList.add(obj);
	    }
	}
	if (unsavedObjsList != null) {
	    dos.writeBytes("    # Reference to unsaved objects skipped:");
	    for (Iterator i=unsavedObjsList.iterator(); i.hasNext();) {
		obj = i.next();
		int objId = ((Integer)unsavedObjs.get(obj)).intValue();
		dos.writeBytes(" @Z" + objId);
	    }
	    dos.writeBytes("\n");
	}

				// Now, if there are any objects left, write them out
	if ((value.size() > 0) &&
	    ((unsavedObjsList == null) ||
	     (value.size() > unsavedObjsList.size()))) {
	    dos.writeBytes("    " + type + " " + name + " [");
	    for (Iterator i=value.iterator(); i.hasNext();) {
		obj = i.next();
		if (isSaved(obj)) {
		    if (first) {
			first = false;
		    } else {
			dos.writeBytes(" ");
		    }
		    writeState(obj);
		}
	    }
	    dos.writeBytes("]\n");
	}
    }

    public void writeState(String type, String name, short s) throws IOException {
	writeState(type, name, new Short(s));
    }

    public void writeState(String type, String name, int i) throws IOException {
	writeState(type, name, new Integer(i));
    }

    public void writeState(String type, String name, long l) throws IOException {
	writeState(type, name, new Long(l));
    }

    public void writeState(String type, String name, float f) throws IOException {
	writeState(type, name, new Float(f));
    }

    public void writeState(String type, String name, double d) throws IOException {
	writeState(type, name, new Double(d));
    }

    public void writeState(String type, String name, boolean b) throws IOException {
	writeState(type, name, new Boolean(b));
    }

    public void writeState(String type, String name, byte b) throws IOException {
	writeState(type, name, new Byte(b));
    }

    public void writeState(String type, String name, Object obj) throws IOException {
	if (writingState == false) {
	    throw new IOException("ZObjectOutputStream.writeState: Error: can't call writeState unless within a writeObject method");
	}

	if (stateWritten == false) {
	    stateWritten = true;
	    dos.writeBytes("\n");
	}

	if (isSaved(obj)) {
	    dos.writeBytes("    " + type + " " + name + " ");
	    writeState(obj);
	    dos.writeBytes("\n");
	} else {
	    int objId = ((Integer)unsavedObjs.get(obj)).intValue();
	    dos.writeBytes("    # Reference to unsaved object skipped: @Z" + objId + "\n");
	}
    }

    public boolean isSaved(Object obj) {
	boolean rc;

	if ((obj instanceof ZSerializable) &&
	    (unsavedObjs.get(obj) != null)) {
	    rc = false;
	} else {
	    rc = true;
	}

	return rc;
    }

    public void writeState(Object obj) throws IOException {
	if (obj instanceof Short) {
	    dos.writeBytes(((Short)obj).toString());
	} else if (obj instanceof Integer) {
	    dos.writeBytes(((Integer)obj).toString());
	} else if (obj instanceof Long) {
	    dos.writeBytes(((Long)obj).toString());
	} else if (obj instanceof Float) {
	    dos.writeBytes(((Float)obj).toString() + "f");
	} else if (obj instanceof Double) {
	    dos.writeBytes(((Double)obj).toString() + "d");
	} else if (obj instanceof Boolean) {
	    dos.writeBytes(((Boolean)obj).toString());
	} else if (obj instanceof String) {
	    dos.writeBytes("\"" + ZParser.translateJavaToJazz((String)obj) + "\"");
	} else if (obj instanceof Byte) {
	    dos.writeBytes(((Byte)obj).toString());
	} else if (obj instanceof Color) {
	    
	    float[] colors = ((Color)obj).getComponents(null);
	    dos.writeBytes("[");
	    for (int i=0; i<colors.length; i++) {
		if (i > 0) {
		    dos.writeBytes(" ");
		}
		dos.writeBytes(Float.toString(colors[i]));
	    }
	    dos.writeBytes("]");
	} else if (obj instanceof Font) {
	    Font font = (Font)obj;
	    dos.writeBytes("[\"" + font.getName() + "\" " + font.getStyle() + " " + font.getSize() + "]");
	} else if (obj instanceof Rectangle2D) {
	    Rectangle2D rect = (Rectangle2D)obj;
	    dos.writeBytes("[" + rect.getX() + " " + rect.getY() + " " 
			   + rect.getWidth() + " " + rect.getHeight() + "]");
	} else if (obj instanceof AffineTransform) {
	    AffineTransform at = (AffineTransform)obj;
	    double[] matrix = new double[6];
	    at.getMatrix(matrix);
	    dos.writeBytes("[" + matrix[0] + " " + matrix[1] + " " + matrix[2] + " " + 
			   matrix[3] + " " + matrix[4] + " " + matrix[5] + "]");
	} else if (obj instanceof Image) {
	    Image image = (Image)obj;
	    BufferedImage bufferedImage;
	    if (image instanceof BufferedImage) {
		bufferedImage = (BufferedImage)image;
	    } else {
				// Create a buffered image out of the regular image in order to write it out
		bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 
						  BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, null, null);
		image = bufferedImage;
	    }

				// Write the image to a temporary stream so we can get its length.
	    ByteArrayOutputStream tmpOut = new ByteArrayOutputStream();
	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tmpOut);
	    encoder.encode(bufferedImage);
	    try {
		tmpOut.close();
	    } catch(IOException e) {}
				// Finally, write out the size of the image, and the image itself
	    dos.writeBytes(" " + Long.toString(tmpOut.size()) + "\n");
	    tmpOut.writeTo(dos);
	} else if (obj instanceof ZSerializable) {
	    int objId;
				// See if the object was replaced with another
	    Object replacementObj = replacedObjs.get(obj);
	    if (replacementObj != null) {
		obj = replacementObj;
	    }

				// Lookup id of referenced object
	    Object value = objs.get(obj);
	    if (value == null) {
		throw new IOException("ZObjectOutputStream.writeState: Error writing obj that is referenced, but not added to write list: " + obj);
	    } else {
		objId = ((Integer)value).intValue();
		dos.writeBytes("@Z" + objId);
	    }
	} else {
	    throw new IOException("ZObjectOutputStream.writeState: Error: can't write out object of type: " + obj.getClass());
	}
    }
    
    public void write(int b) {
    }

    public void flush() {
	try {
	    dos.flush();
	} 
	catch (Exception e) {
	}
    }
}

/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;

import edu.umd.cs.jazz.ZRoot;
import edu.umd.cs.jazz.ZSceneGraphObject;
import edu.umd.cs.jazz.util.ZDebug;

import java.util.ArrayList;
import java.util.HashMap;
import java.beans.*;
import java.lang.reflect.*;

/**
 * 
 * <B>ZSceneGraphPropertyPanel</B> displays a list of properties for a number of Java Bean objects, 
 * and allows the user to edit the properties.
 *
 * The component uses the Java Beans Introspection mechanism to discover what
 * properties are available, and the Java Beans Editor mechanism to determine what
 * editors to build.
 *
 * The code was originally based on the BDK 1.0 code from Sun. It has been updated
 * to work using inner classes, to use Swing rather than JDK, and to support editing
 * the properties of multiple objects at once.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @see ZSceneGraphTreeView
 */ 

public class ZSceneGraphPropertyPanel extends JPanel implements PropertyChangeListener {
    private Object targets[];
    private ObjectInfo objectInfo[];
    private PropertyDisplay props[];
    
    private boolean processEvents;    

    /**
     * Create a new sceneGraph property panel.
     */
    public ZSceneGraphPropertyPanel() {
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /*
     * Inner class used to popup a dialog box displaying a custom editor.
     */
    private static class PopupProp extends JDialog implements ActionListener {
	private JButton doneButton;
	private Component body;
	private final static int vPad = 5;
	private final static int hPad = 4;

	PopupProp(Frame frame, PropertyEditor pe, int x, int y) {
	    super(frame, pe.getClass().getName(), true);
	    getContentPane().setLayout(new BorderLayout());

	    body = pe.getCustomEditor();
	    getContentPane().add(BorderLayout.CENTER, body);

	    doneButton = new JButton("OK");
	    doneButton.addActionListener(this);
	    getContentPane().add(BorderLayout.SOUTH, doneButton);
	    setLocation(x, y);
	    pack();
	    show();
	}

	public void actionPerformed(ActionEvent evt) {
	    // Button down.
	    dispose();
	}
    }

    /*
     * Inner class used for properties that paint themselves. 
     */
    private static class PaintedProp extends JComponent implements MouseListener {
	private PropertyDisplay display;
	 
	PaintedProp(PropertyDisplay pd) {
	    display = pd;
	    setBorder(BorderFactory.createEtchedBorder());
	    addMouseListener(this);
	}

	// Returns the frame this component is in
	Frame getFrame() {
	    Component p = getTopLevelAncestor();
	    while (p != null) {
		if (p instanceof Frame) 
		    return (Frame)p;
		p = p.getParent();
	    }
	    return null;
	}

	// Sadly, the Java Beans API doesn't provide a way to discover how
	// big a paint area is needed. We just use something 30 pixels high.
	//
	public Dimension getPreferredSize() {
	    int h = display.getRowHeight();
	    return new Dimension(h, h);
	}

	public Dimension getMaximumSize() {
	    int h = display.getRowHeight();
	    return new Dimension(10000, h);
	}

	public void paint(Graphics g) {
	    Dimension sz = getSize();
	    
	    if (display.hasSingleValue()) {
		Rectangle box = new Rectangle(4, 2, sz.width - 6, sz.height - 4);
	    	display.getPropertyEditor(0).paintValue(g, box);
	    }
	    getBorder().paintBorder(this, g, 2, 0, sz.width - 2, sz.height);
	}

	private static boolean ignoreClick = false;

	public void mouseClicked(MouseEvent evt) {
	    if (! ignoreClick) {
		try {
		    ignoreClick = true;
		    Frame frame = getFrame();
		    int x = frame.getLocation().x - 30;
		    int y = frame.getLocation().y + 50;
		    new PopupProp(frame, display.getPropertyEditor(0), x, y);
		} finally {
		    ignoreClick = false;
		}
	    }
	}

	public void mousePressed(MouseEvent evt) { }
	public void mouseReleased(MouseEvent evt) { }
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
    }

    /*
     * Inner class used for text-based properties. 
     */
    private static class TextProp extends JTextField implements KeyListener, FocusListener {
	private PropertyDisplay display;

	TextProp(PropertyDisplay pd) {
	    super(pd.getAsText());
	    display = pd;
	    addKeyListener(this);
	    addFocusListener(this);
	}

	public Dimension getMaximumSize() {
	    Dimension sz = getPreferredSize();
	    sz.width = 10000;
	    return sz;
	}

	protected void updateEditor() {
	    try {
		if (display != null) display.setAsText(getText());
	    } catch (IllegalArgumentException ex) {
		// Quietly ignore.
	    }
	}
    
	// Focus listener methods.

	
	public void focusLost(FocusEvent e) { 
	    updateEditor(); 
	}
	public void focusGained(FocusEvent e) { 
	    if (display != null) {
		setText(display.getAsText());
	    }
	}

	// Keyboard listener methods.

	public void keyReleased(KeyEvent e) {
 	    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		updateEditor();
	    }
	}
	public void keyPressed(KeyEvent e) { }
	public void keyTyped(KeyEvent e) { }
    }

    /*
     * Inner class used for enumerated properties. 
     */
    private static class ComboProp extends JComboBox implements ItemListener {
	PropertyDisplay display;
	
	ComboProp(PropertyDisplay pd) {
	    display = pd;
	    String tags[] = pd.getPropertyEditor(0).getTags();
	    if (display.multipleObjects()) 
		addItem("");
	    for (int i = 0; i < tags.length; i++) {
		addItem(tags[i]);
	    }
	    //setSelectedItem(tags[0]);
	    
	    // This is a noop if the getAsText is not a tag.
	    if (pd.getAsText() != null) 
		setSelectedItem(pd.getAsText());
	    
	    addItemListener(this);
	}

	public void itemStateChanged(ItemEvent evt) {
	    String s = (String)getSelectedItem();
	    if (s != null && display != null && !s.equals("")) {
		display.setAsText(s);
	    }
	}	
    }

    // Obtaining the properties and editors to use for a given object from the 
    // Java Beans introspector is slow. So we divide the process into two steps:
    // First, we cache information about the properties/editors available for a 
    // particular class in a hash table. Then we use this cached information as
    // the basis (or "prototype") for constructing ObjectInfo records for specific 
    // objects.
    
    //
    // Maps from an object's class to an ObjectInfo record that contains
    // the properties/editors for the class.
    //
    private static HashMap infoMap = new HashMap();
    
    /**
     * Contains information about the properties/editors/values of a given object.
     */
    private static class ObjectInfo {
	Object target;
	PropertyDescriptor  properties[];
	PropertyEditor	    editors[];
	Object		    values[];
	int numProperties;

	ObjectInfo(Object target) {	
	    this.target = target;

	    // Look in the infoMap cache for an ObjectInfo prototype containing the
	    // properties/editors for the target's class.

	    ObjectInfo prototype = (ObjectInfo)infoMap.get(target.getClass());

	    if (prototype == null) {
		// Make a new prototype.
		prototype = new ObjectInfo(target.getClass());
		infoMap.put(target.getClass(), prototype);
	    }

	    // Construct this ObjectInfo from the prototype ObjectInfo
	    // by copying the properties/editors, and then filling in the values
	    // array.

	    numProperties = prototype.numProperties;
	    properties    = new PropertyDescriptor[numProperties];
	    editors       = new PropertyEditor[numProperties];
	    values        = new Object[numProperties];
	    
	    int n = 0;    	    
	    for (int i = 0; i < numProperties; i++) {
		PropertyDescriptor property = prototype.properties[i];
		PropertyEditor editor = prototype.editors[i];

		String name = property.getDisplayName();
		Method getter = property.getReadMethod();

		try {
		    // Make a new instance of the editor
		    editor = (PropertyEditor)editor.getClass().newInstance();
		    
		    // Get the current property value
		    Object args[] = { };
		    Object value = getter.invoke(target, args);
		    
		    // Don't try to set null values:
		    if (value == null) {
			// If it's a user-defined property we give a warning.
			String getterClass = getter.getDeclaringClass().getName();
			if (getterClass.indexOf("java.") != 0) {
			    System.err.println("Warning: Property \"" + name 
				    + "\" has null initial value.  Skipping.");	
			}
			continue;
		    }

		    properties[n] = property;
		    values[n] = value;
		    editors[n] = editor;
		    editor.setValue(value);
		    n++;

		} catch (InvocationTargetException ex) {
		    System.err.println("Skipping property " + name + " ; exception on target: " + ex.getTargetException());
		    //ex.getTargetException().printStackTrace();
		    continue;
		} catch (Exception ex) {
		    System.err.println("Skipping property " + name + " ; exception: " + ex);
		    //ex.printStackTrace();
		    continue;
		}

	    }
	    numProperties = n;
	}

	/** 
	 * Determines the properties/editors for a given class.
	 */
	ObjectInfo(Class targetClass) {	
	   
	    // First use the Bean Introspector to find the property descriptors 
	    //	    
	    try {
		BeanInfo bi = Introspector.getBeanInfo(targetClass);
		properties = bi.getPropertyDescriptors();
	    } catch (IntrospectionException ex) {
		error("PropertySheet: Couldn't introspect ", ex);
		return;
	    }

	    // Now figure out what PropertyEditors to use
	    //	    
	    editors = new PropertyEditor[properties.length];
	    
	    int n = 0;    	    
	    for (int i = 0; i < properties.length; i++) {
		PropertyDescriptor property = properties[i];
		// Don't display hidden or expert properties.
		if (property.isHidden() || property.isExpert()) {
		    continue;
		}

		String name = property.getDisplayName();
		Class type = property.getPropertyType();
		Method getter = property.getReadMethod();
		Method setter = property.getWriteMethod();

		// Only display read/write properties.
		if (getter == null || setter == null) {
		    continue;
		}
	    	
		try {		    
		    // Find an editor for the property
		    PropertyEditor editor = null;
		    Class pec = property.getPropertyEditorClass();
		    if (pec != null) {
			try {
			    editor = (PropertyEditor)pec.newInstance();
			} catch (Exception ex) {
			    // Drop through.
			}
		    }
		    if (editor == null) {
			editor = PropertyEditorManager.findEditor(type);
		    }
		    
		    // If we can't edit this component, skip it.
		    if (editor == null) {
			// If it's a user-defined property we give a warning.
			String getterClass = getter.getDeclaringClass().getName();
			if (getterClass.indexOf("java.") != 0) {
			    System.err.println("Warning: Can't find public property editor for property \""
				     + name + "\".  Skipping.");
			}
			continue;
		    }
		   
		    properties[n] = property;
		    editors[n] = editor;
		    n++;

		} catch (Exception ex) {
		    System.err.println("Skipping property " + name + " ; exception: " + ex);
		    //ex.printStackTrace();
		    continue;
		}

	    }
	    numProperties = n;
	}	

	int getNumProperties() {
	    return numProperties;
	}

	String getPropertyName(int i) {
	    return properties[i].getDisplayName();
	}

	Class getEditorType(int i) {
	    return editors[i].getClass();
	}

	PropertyEditor getPropertyEditor(int i) {
	    return editors[i];
	}

	void setPropertyValue(int i, Object value) {	   
	    values[i] = value;
	    Method setter = properties[i].getWriteMethod();
	    try {
		Object args[] = { value };
		args[0] = value;
		setter.invoke(target, args);					
	    } catch (InvocationTargetException ex) {
	      if (ex.getTargetException()
			instanceof PropertyVetoException) {
		//warning("Vetoed; reason is: " 
		//        + ex.getTargetException().getMessage());
		// temp dealock fix...I need to remove the deadlock.
		System.err.println("WARNING: Vetoed; reason is: " 
				+ ex.getTargetException().getMessage());
	      }
	      else
		error("InvocationTargetException while updating " 
			+ properties[i].getName(), ex.getTargetException());
	    } catch (Exception ex) {
		error("Unexpected exception while updating " 
			+ properties[i].getName(), ex);
	    }
	}

	// Checks to see if the properties value has changed. Returns true
	// if the property was changed, false otherwise.
	boolean refreshPropertyValue(int i) {
	    Object o;
	    try {
	        Method getter = properties[i].getReadMethod();
	        Object args[] = { };
	        o = getter.invoke(target, args);
	    } catch (Exception ex) {
		o = null;
	    }
	    if (o == values[i] || (o != null && o.equals(values[i]))) {
	        // The property is equal to its old value.
		return false; 
	    }
	    // The property has changed!  Update the editor.
	    editors[i].setValue(o);
	    values[i] = o;
	    return true;
	}

	/**
	 * Returns an index indicating where a property 
	 * with a given name/type occurs in this ObjectInfo record,
	 * or -1 if the property doesn't exist.
	 */
	int findProperty(String name, Class type) {
	    for (int i = 0; i < numProperties; i++) {
		if (editors[i].getClass() == type 
			&& properties[i].getDisplayName().equals(name))
		    return i;
	    }
	    return -1;
	}


    }

    /**
     * A PropertyDisplay identifies a particular property that is displayed. 
     * Multiple objects can share a single property display for one of their properties.
     * For example, if several selected objects have a property "fooBaz" of type boolean, then
     * a single property display can be used to present that property to the user. Each
     * property display contains a propertyIndex, which is an array of indices 
     * indicating where that property is located in each target object's ObjectInfo 
     * record. Each property display also contains a "view" component that is used to 
     * display the property.
     */
    private class PropertyDisplay {
	int propertyIndex[]; // Index where this property is located in each object
	JLabel label;
	JComponent view;
	
	PropertyDisplay(int propertyIndex[]) {
	    this.propertyIndex = propertyIndex;

	    // We use the first target object to determine the name and editor,
	    // and how to display the property.
	    //
	    PropertyEditor editor = getPropertyEditor(0);
	    String name = getPropertyName(0);
	    
	    label = new JLabel(name);
	    
	    // Now figure out how to display it...
	    if (editor.isPaintable() && editor.supportsCustomEditor()) {
		view = new PaintedProp(this);
	    } else if (editor.getTags() != null) {
		view = new ComboProp(this);
	    } else if (editor.getAsText() != null) {
		String init = editor.getAsText();
		view = new TextProp(this);
	    } else {
		System.err.println("Warning: Property \"" + name 
			    + "\" has non-displayabale editor.  Skipping.");
		return;
	    }

	    ZSceneGraphPropertyPanel.this.add(label);
	    ZSceneGraphPropertyPanel.this.add(view);	    

	    // Listen to the first editor only - when its value changes we
	    // propagate this to the other editors
	    editor.addPropertyChangeListener(ZSceneGraphPropertyPanel.this);
	}	    

	int getRowHeight() {
	    return (int)(label.getPreferredSize().height * 1.5);
	}

	/**
	 * Returns true if all the objects sharing this property display have a
	 * consistent value for the property. If this returns true, the underlying
	 * view component shows the value, otherwise it shows a blank.
	 */
	boolean hasSingleValue() {
	    Object value = getPropertyEditor(0).getValue();
	    for (int i = 1; i < objectInfo.length; i++) {
		PropertyEditor editor = getPropertyEditor(i);
		if (!editor.getValue().equals(value))
		    return false;
	    }
	    return true;
	}

	/**
	 * Returns true if this property display is being shared by multiple objects.
	 */
	boolean multipleObjects() {
	    return (objectInfo.length > 1);
	}


	/**
	 * Returns a textual representation of the property. If all the objects
	 * have a consistent value for the property, this returns the value 
	 * held in the first object's editor. Otherwise this returns "".
	 */
	String getAsText() {
	    if (hasSingleValue()) {
		return getPropertyEditor(0).getAsText();
	    } else {
		return "";
	    }
	}

	/**
	 * Sets this property.
	 */
	void setAsText(String value) {
	    // The ZSceneGraphPropertyPanel's propertyChange listener will notice this
	    // change and call editorValueChanged to propagate it to the other objects.
	    getPropertyEditor(0).setAsText(value);
	}

	/**
	 * Called when the first editor's value changes. This propagates the value
	 * to the other objects.
	 */
	void editorValueChanged(Object value) {
	    // For each object, set the property value to the specified value 
	    for (int i = 0; i < objectInfo.length; i++) {
		objectInfo[i].setPropertyValue(propertyIndex[i], value);		
	    }
	    
	    // Must also update the editors (except for the first editor)
	    for (int i = 1; i < objectInfo.length; i++) {
		objectInfo[i].getPropertyEditor(propertyIndex[i]).setValue(value);		
	    }

	    if (view instanceof PaintedProp) {
		view.repaint();
	    }
	}


	/**
	 * Called if one of the objects has changed - this refreshes the value
	 * displayed in the editor.
	 */
	void refresh() { 
	    for (int i = 0; i < objectInfo.length; i++) {
		ObjectInfo info = objectInfo[i];
		if (info.refreshPropertyValue(propertyIndex[i])) {
		    if (view instanceof TextProp) {
			((TextProp)view).setText(getAsText());
		    } else if (view instanceof ComboProp) {
			((ComboProp)view).setSelectedItem(getAsText());
		    } else if (view instanceof PaintedProp) {
			view.repaint();
		    }
		}
	    }
	}
	

	/**
	 * Returns the property editor for the i'th object in this display.
	 */
	PropertyEditor getPropertyEditor(int i) {
	    return objectInfo[i].getPropertyEditor(propertyIndex[i]);
	}		
	
	/**
	 * Returns the name of this property from the i'th object in the display. 
	 */
	String getPropertyName(int i) {
	    return objectInfo[i].getPropertyName(propertyIndex[i]);
	}		
    }

     
    synchronized void setCustomizer(Customizer c) {
	if (c != null) {
	    c.addPropertyChangeListener(this);
	}
    }

    /**
     * Changes the object(s) whose properties are displayed in this panel.
     */
    synchronized void setTarget(Object targets[]) {
	
	processEvents = false;	
	setVisible(false);
	removeAll();

	this.targets = targets;

	if (targets == null || targets.length == 0) {
	    setVisible(true);
	    return;
	}
	 
	// Get all the property information for each target
	   
	objectInfo = new ObjectInfo[targets.length];

	for (int i = 0; i < targets.length; i++) {
	    objectInfo[i] = new ObjectInfo(targets[i]);
	}

	// Now "unify" all the object infos to determine 
	// which properties are common to all the objects.
	unify();

	add(new JLabel("")); // used to hold blank space at bottom	
	setVisible(true);
	revalidate();
	processEvents = true;	
    }

    private void unify() {
	ArrayList props = new ArrayList();
	
	// We need to extract the properties that are common to all
	// the objects that are selected as targets. We do this by
	// iterating over the properties of the first target object, 
	// only displaying each property if it is also found in all
	// the other targets...

	ObjectInfo info = objectInfo[0];
	
	for (int i = 0; i < info.getNumProperties(); i++) {
	    String name = info.getPropertyName(i);
	    Class type = info.getEditorType(i);

	    // We construct a propertyIndex array indicating where this
	    // property is found in each object.

	    int propertyIndex[] = new int[objectInfo.length];
    
	    propertyIndex[0] = i;
		    
	    for (int j = 1; j < objectInfo.length; j++) {
		int index = objectInfo[j].findProperty(name, type);
		if (index == -1) { // Not a common property - skip this property
		    propertyIndex = null;
		    break;
		}
		propertyIndex[j] = index; 
	    }
	    if (propertyIndex != null) {  // Found a common property - now display it.
		PropertyDisplay p = new PropertyDisplay(propertyIndex);
		props.add(p);						
	    }
	}

	// Store the property display array as an array
	this.props = (PropertyDisplay[])props.toArray(new PropertyDisplay[props.size()]);
    }


    /**
     * Used to listen to changes to properties that are on display.
     */
    public synchronized void propertyChange(PropertyChangeEvent evt) {
	if (!processEvents) {
	    return;
	}

	// If the change took place because of a property editor, propagate the
	// change to the Beans.
	if (evt.getSource() instanceof PropertyEditor) {
	    PropertyEditor editor = (PropertyEditor) evt.getSource();
	    for (int i = 0 ; i < props.length; i++) {
	        if (props[i].getPropertyEditor(0) == editor) {
		    props[i].editorValueChanged(editor.getValue());
		    break;
		}
	    }
	}

	// If the change took place because a bean was changed, propagate the
	// change to the editors.
	for (int i = 0; i < props.length; i++) {
	    props[i].refresh();
	}

    }

    private static void error(String message, Throwable th) {
	String mess = message + ":\n" + th;
	System.err.println(message);
	//th.printStackTrace();
	// Popup an ErrorDialog with the given error message.
	//new ErrorDialog(frame, mess);

    }
}

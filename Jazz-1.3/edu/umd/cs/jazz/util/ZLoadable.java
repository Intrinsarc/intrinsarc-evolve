/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import javax.swing.*;

import edu.umd.cs.jazz.*;

/**
 * <b>ZLoadable</b> is an interface that defines objects that can be dynamically loaded into Jazz, and be
 * made aware of the basic scenegraph structure so that it can add itself
 * to the application and do something useful.  When a ZLoadable object
 * is loaded into Jazz, all these methods are guaranteed to be called which
 * specify the primary menubar of the application, and the basic elements
 * of the scenegraph (camera, surface, and top node).
 * <P>
 * The following code is a sample stand-alone code segment that implements 
 * ZLoadable and can be imported directly into the demo HiNote program.
 * It adds a 'Layout' option to the menu bar, and when the 'doLayout' option is selected, it
 * lays out all the objects in the world in an ellipse.
 * <PRE>
 *    import java.awt.geom.*;
 *    import java.awt.event.*;
 *    import javax.swing.*;
 *    
 *    import edu.umd.cs.jazz.*;
 *    import edu.umd.cs.jazz.util.*;
 *    
 *    public class Foo implements Runnable, ZLoadable {
 *        JMenuBar menubar = null;
 *        ZCamera camera = null;
 *        ZDrawingSurface surface = null;
 *        ZLayerGroup layer = null;
 *        ZLayoutGroup layoutGroup = null;
 *        
 *        public Foo() {
 *        }
 *        
 *        public void run() {
 *    	JMenu layoutMenu = new JMenu("Layout");
 *    	JMenuItem menuItem = new JMenuItem("doLayout");
 *    	menuItem.addActionListener(new ActionListener() {
 *    	    public void actionPerformed(ActionEvent e) {
 *    				// Make a new layout group with a path layout manager
 *    		layoutGroup = new ZLayoutGroup();
 *    		ZPathLayoutManager layout = new ZPathLayoutManager();
 *    		layout.setShape(new Ellipse2D.Double(0, 0, 200, 200));
 *    		
 *    				// Move all the scene's nodes under the layout group and lay them out
 *    		ZNode[] children = layer.getChildren();
 *    		for (int i=0; i<LT>children.length; i++) {
 *    		    children[i].reparent(layoutGroup);
 *    		}
 *    		layer.addChild(layoutGroup);
 *    		layoutGroup.setLayoutManager(layout);
 *    		layoutGroup.doLayout();
 *    
 *    				// Remove the layout manager
 *    		layoutGroup.remove();
 *    	    }
 *    	});
 *    	layoutMenu.add(menuItem);
 *        
 *    	menubar.add(layoutMenu);
 *    	menubar.revalidate();
 *        }
 *        
 *        public void setMenubar(JMenuBar aMenubar) {
 *    	menubar = aMenubar;
 *        }
 *        
 *        public void setCamera(ZCamera aCamera) {
 *    	camera = aCamera;
 *        }
 *        
 *        public void setDrawingSurface(ZDrawingSurface aSurface) {
 *    	surface = aSurface;
 *        }
 *        
 *        public void setLayer(ZLayerGroup aLayer) {
 *    	layer = aLayer;
 *        }
 *    }
 * </PRE>
 * @author Ben Bederson
 */
public interface ZLoadable {
    /**
     * Set the primary menubar for the application.
     */
    public void setMenubar(JMenuBar menubar);

    /**
     * Set the camera of the scenegraph.
     */
    public void setCamera(ZCamera camera);

    /**
     * Set the drawing surface of the scenegraph.
     */
    public void setDrawingSurface(ZDrawingSurface surface);

    /**
     * Set the layer of the scenegraph.
     */
    public void setLayer(ZLayerGroup layer);
}

/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

package edu.umd.cs.jazz.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;


/**
 * <b>ZSceneGraphTreeView</b> displays a tree structure
 * representing the Jazz scene graph that is useful for debugging.
 * Below the tree view is a property panel that shows the properties
 * of the node(s) that are selected in the tree. Properties are obtained 
 * using the Java Beans Introspector. The mechanism supports editing the
 * properties of multiple objects at once.<P>
 * 
 * The SceneGraphTreeView browser has a menubar as follows:<P>
 * 
 * <b>Item</b>
 * <ul>
 * <li> <b>Info</b> - Shows you the dump output for a node
 * 
 * <li> <b>Select</b> In Main View   - nodes that are selected in the tree view 
 *                             become selected in the main Jazz window.
 * 
 * <li> <b>Center</b> In Main View   - nodes that are selected in the tree view 
 *                             become centered in the main Jazz window.
 * </ul>
 * <b>View</b>
 * <ul>
 * <li> <b>Refresh</b>- Causes the scene graph tree to be rebuilt
 * 
 * <li> <b>Update Selection</b> - Any notes that are selected in the main
 *                        Jazz window are selected in the tree view.
 * </ul>
 * <p>
 * To use this, simply create an instance of this class for a specific canvas:
 * <pre>
 *    ZSceneGraphTreeView treeView = new ZSceneGraphTreeView(canvas);
 *    treeView.pack();
 *    treeView.show();
 * </pre>
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Jon Meyer
**/
public class ZSceneGraphTreeView extends JFrame implements ActionListener, TreeSelectionListener {
    ZCanvas canvas;
    ZCamera camera;
    ZDrawingSurface surface;

    JTree tree;
    JTextArea text;
    JPanel scrollPanel;

    ZSceneGraphPropertyPanel propertyPanel;
    ZSceneGraphTreeModel model;
        
    /**
     * Create a new scenegraph browser window.
     * @param aCanvas The canvas to be browsed.
     */
    public ZSceneGraphTreeView(ZCanvas aCanvas) {
	super("Scenegraph Tree View");
	canvas = aCanvas;
	camera = canvas.getCamera();
	surface = canvas.getDrawingSurface();

	JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	JScrollPane scroll;

	tree = new JTree();
	tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
	tree.addTreeSelectionListener(this);
  
	pane.add(scroll = new JScrollPane(tree));
	scroll.setPreferredSize(new Dimension(300, 300));
	
	propertyPanel = new ZSceneGraphPropertyPanel();
	pane.add(scroll = new JScrollPane(propertyPanel));
	scroll.setPreferredSize(new Dimension(300, 300));

	Container p = getContentPane();
	p.setLayout(new BorderLayout());
	p.add(BorderLayout.CENTER, pane);
		
	JMenuBar mb = new JMenuBar();

	JMenu m = new JMenu("File");
	m.setMnemonic('F');
	add(m,"Close", 'x');
	mb.add(m);

	m = new JMenu("Item");
	m.setMnemonic('I');
	add(m, "Info", 'I');
	add(m, "Select In Main View", 'S');
	add(m, "Center In Main View", 'O');
	mb.add(m);
	
	m = new JMenu("View");
	m.setMnemonic('V');
	add(m, "Refresh", 'R');
	add(m, "Update Selection", 'S');
	mb.add(m);
	
	setJMenuBar(mb);
	setRoot(canvas.getRoot());
    }

    /**
     * Internal method that overrides java.awt.event.actionPerformed.
     * Responds to menubar selections.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand().intern();
        if (cmd == "Close") dispose();
        else if (cmd == "Info") showInfo();
	else if (cmd == "Select In Main View") selectNodes();
	else if (cmd == "Center In Main View") showNodes();	
	else if (cmd == "Refresh") model.fireTreeStructureChanged();
	else if (cmd == "Update Selection") updateSelection();
    }

    /**
     * Internal method: add items a JMenu.
     * @param m the JMenu
     * @param command the text for the item
     * @param mnemonic the menu mnemonic 
     */
    public void add(JMenu m, String command, char mnemonic) {
        JMenuItem mc = m.add(command);
        mc.addActionListener(this);
        mc.setMnemonic(mnemonic);
    }

    /**
     * Internal method: Get selected items from Tree.
     */
    public Object[] getSelectedObjects() {
	TreePath paths[] = tree.getSelectionPaths();
	if (paths == null || paths.length == 0) return null;
	Object objects[] = new Object[paths.length];
	for (int i = 0; i < paths.length; i++) {
	    objects[i] = model.getJazzObject(paths[i].getLastPathComponent());
	}
	return objects;
    }

    /**
     * Internal method that overrides avax.swing.event.valueChanged.
     * Responds to changes in browser selections.
     * @param e a TreeSelectionEvent from the browser.
     */
    public void valueChanged(TreeSelectionEvent e) {
	Object objects[] = getSelectedObjects();	
	if (objects != null) {
	    try {
		propertyPanel.setTarget(objects);
	    } catch (Throwable t) {
		System.err.println("Property Panel: " + t);		
	    }
	}
    }

    /**
     * Set the root node of the scenegraph browser.
     * @param root the root node.
     */
    public void setRoot(ZRoot root) {
	model = new ZSceneGraphTreeModel(root);
	tree.setModel(model);
    }

    /**
     * Internal method: pop up a window with info about selected node.
     */
    public void showInfo() {
	Object selected = tree.getLastSelectedPathComponent();
	if (selected != null) {
	    ZSceneGraphObject obj = model.getJazzObject(tree.getLastSelectedPathComponent());
	    new InfoPopup(this, obj);
	}
    }

    /**
     * Internal method: finds nodes selected in the browser, and centers
     * them in the jazz application window.
     */
    public void showNodes() {
	Object selected[] = getSelectedObjects();
	if (selected != null) {
	    ZBounds b = new ZBounds();
	    for (int i = 0; i < selected.length; i++) {
		ZSceneGraphObject obj = (ZSceneGraphObject)selected[i];
		if (obj instanceof ZVisualComponent) {
		    obj = ((ZVisualComponent)obj).getParents()[0];
		}
		if (obj instanceof ZNode) {
    		    b.add(((ZNode)obj).getGlobalBounds());
		}
	    }
	    if (!b.isEmpty()) {
		camera.center(b, 1000, surface);	
	    }
	}
    }

    /** Internal method: selects in browser any nodes selected in the
     * jazz application.
     */
    public void selectNodes() {
	ZSelectionManager.unselectAll(camera);
	Object selected[] = getSelectedObjects();
	if (selected != null) {
	    for (int i = 0; i < selected.length; i++) {
		ZSceneGraphObject obj = (ZSceneGraphObject)selected[i];
		if (obj instanceof ZVisualComponent) {
		    obj = ((ZVisualComponent)obj).getParents()[0];
		}
		if (obj instanceof ZNode) {
		    ZSelectionManager.select((ZNode)obj);
		}
	    }
	}
	showNodes();
    }

    /** Internal method: selects in jazz application any nodes selected in
     * the browser.
     */
    public void updateSelection() {	
	model.fireTreeStructureChanged();

        ArrayList selected = ZSelectionManager.getSelectedNodes(canvas.getRoot());
	tree.clearSelection();
	if (selected.size() > 0) {
	    for (int i = 0; i < selected.size(); i++) {
		ZNode n = (ZNode)selected.get(i);
		TreePath tp = model.getTreePath(n);
		tree.addSelectionPath(tp);
		//tree.makeVisible(tp);
	    }
	}
	propertyPanel.setTarget(selected.toArray());
    }

    static class InfoPopup extends JDialog implements ActionListener {	
	InfoPopup(Frame frame, ZSceneGraphObject node) {
	    
	    super(frame, "Node Info", true);
	    
	    JButton doneButton;
	    JTextArea text;
	    
	    getContentPane().setLayout(new BorderLayout());

	    text = new JTextArea();
	    text.setText(ZDebug.dumpString(node, false));
	    
	    getContentPane().add(BorderLayout.CENTER, new JScrollPane(text));

	    doneButton = new JButton("OK");
	    doneButton.addActionListener(this);	  
	    getContentPane().add(BorderLayout.SOUTH, doneButton);
	    
	    pack();
	    show();
	}

	public void actionPerformed(ActionEvent evt) {
	    // Button down.
	    dispose();
	}
    }


}

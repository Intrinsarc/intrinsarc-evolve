/**
 * Copyright 2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * Test of the ZTreeLayoutManager
 */
public class TreeLayoutExample extends AbstractExample {
    ZLayoutGroup startNode    = null;
    ZTreeLayoutManager manager = new ZTreeLayoutManager();
    protected ZCanvas             canvas;

    public TreeLayoutExample() {
            super("Tree Layout example");
    }
    public void addSomeChildren() {
        if (startNode == null) {
            return;
        }

        ZVisualComponent vis = null;
        ZVisualGroup child = null;
        ZNode node = null;
        ZGroup group;

        ArrayList selection = ZSelectionManager.getSelectedNodes(canvas.getLayer());
        for (Iterator i = selection.iterator() ; i.hasNext() ;) {
            node = (ZNode)i.next();
            if (node instanceof ZGroup) {
                group = (ZGroup)node;

                vis  = new ZRectangle(0.0, 0.0, 40.0, 40.0);
                child = new ZVisualGroup(vis, null);
                ZLayoutGroup layout = child.editor().getLayoutGroup();
                layout.setLayoutChild(child);
                layout.setLayoutManager(manager);
                group.addChild(child.editor().getTop());
            }
        }

        if (node != null) {
            ZSelectionManager.unselectAll(canvas.getLayer());
            ZSelectionManager.select(node);
        }
    }

    public JMenuBar buildMenu() {
        JMenuBar retVal = new JMenuBar();

        // File menu
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        JMenuItem menuItem;

        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic('X');
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(menuItem);

        retVal.add(file);

        // Action menu
        JMenu action = new JMenu("Action");
        file.setMnemonic('A');

        menuItem = new JMenuItem("Add Node");
        menuItem.setMnemonic('C');
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSomeChildren();
            }
        });
        action.add(menuItem);


        menuItem = new JMenuItem("Remove Node");
        menuItem.setMnemonic('R');
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSomeChildren();
            }
        });
        action.add(menuItem);

        action.addSeparator();

        menuItem = new JMenuItem("Clear");
        menuItem.setMnemonic('f');
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        action.add(menuItem);

        menuItem = new JMenuItem("Dump Scenegraph");
        menuItem.setMnemonic('D');
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ZDebug.dump(canvas.getRoot());
            }
        });
        action.add(menuItem);

        retVal.add(action);

        return retVal;
    }

    public JToolBar buildToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton button;

        button = new JButton("Add");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSomeChildren();
            }
        });

        button.setToolTipText("Add a node");
        toolBar.add(button);

        button = new JButton("Remove");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSomeChildren();
            }
        });
        button.setToolTipText("Remove a node");
        toolBar.add(button);
        toolBar.addSeparator();

        // Link style choices
        JToggleButton angleLinkButton = new JToggleButton("Angled");
        angleLinkButton.setToolTipText("Angled Link Style");
        angleLinkButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                setLinkStyle(ZTreeLayoutManager.LINK_ANGLEDLINE);
            }
        });

        JToggleButton straighLinkButton = new JToggleButton("Straight");
        straighLinkButton.setSelected(true);
        straighLinkButton.setToolTipText("Straight Line Link Style");
        straighLinkButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                setLinkStyle(ZTreeLayoutManager.LINK_STRAIGHTLINE);
            }
        });

        ButtonGroup linkGroup = new ButtonGroup();
        linkGroup.add(straighLinkButton);
        linkGroup.add(angleLinkButton);

        toolBar.add(angleLinkButton);
        toolBar.add(straighLinkButton);

        toolBar.addSeparator();

        // Head alignment choices
        JToggleButton alignmentButton = new JToggleButton("Head In");
        alignmentButton.setSelected(true);
        alignmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setParentAlign(ZTreeLayoutManager.HEAD_IN);
            }
        });
        alignmentButton.setToolTipText("Center node over its immediate children");

        ButtonGroup alignGroup = new ButtonGroup();
        alignGroup.add(alignmentButton);
        toolBar.add(alignmentButton);

        alignmentButton = new JToggleButton("Head Out");
        alignmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setParentAlign(ZTreeLayoutManager.HEAD_OUT);
            }
        });
        alignmentButton.setToolTipText("Center node over all of its children");
        alignGroup.add(alignmentButton);
        toolBar.add(alignmentButton);


        alignmentButton = new JToggleButton("Head Side");
        alignmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setParentAlign(ZTreeLayoutManager.HEAD_SIDE);
            }
        });
        alignmentButton.setToolTipText("Put parent node on the side");
        alignGroup.add(alignmentButton);
        toolBar.add(alignmentButton);

        toolBar.addSeparator();

        // Head orientation choices
        JToggleButton orientationButton = new JToggleButton("Horizontal");
        orientationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setOrientation(ZTreeLayoutManager.ORIENT_HORIZONTAL);
            }
        });
        orientationButton.setToolTipText("Tree from Left to Right");

        ButtonGroup orientGroup = new ButtonGroup();
        orientGroup.add(orientationButton);
        toolBar.add(orientationButton);

        orientationButton = new JToggleButton("Vertical");
        orientationButton.setSelected(true);
        orientationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setOrientation(ZTreeLayoutManager.ORIENT_VERTICAL);
            }
        });
        orientationButton.setToolTipText("Tree from Top Down");
        orientGroup.add(orientationButton);
        toolBar.add(orientationButton);

        return toolBar;
    }

    public java.lang.String getExampleDescription() {
        return "This example shows how the ZTreeLayoutManager works.";
    }
    public void initializeExample() {
        super.initializeExample();
                        // Set up basic frame
        setBounds(100, 100, 800, 400);
        setResizable(true);
        setBackground(null);
        setVisible(true);
        canvas = new ZCanvas();
        canvas.setNavEventHandlersActive(false);
        getContentPane().add(canvas);

                                // Add menu bar and toolbar
        JMenuBar menubar = buildMenu();
        setJMenuBar(menubar);
        JToolBar toolBar = buildToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);
        validate();

                                // Set up event handlers
        ZPanEventHandler panHandler = new ZPanEventHandler(canvas.getCameraNode());
        ZCompositeSelectionHandler selectionHandler = new ZCompositeSelectionHandler(canvas.getCameraNode(),canvas,canvas.getLayer(),ZCompositeSelectionHandler.MODIFY);

        panHandler.setActive(true);
        selectionHandler.setActive(true);

        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                canvas.requestFocus();
            }
        });

        initScreen();
    }

    public void initScreen() {
        ZRectangle rect = new ZRectangle(0f, 0f, 40f, 40f);

        ZVisualGroup group = new ZVisualGroup(rect, null);
        ZLayoutGroup layout = group.editor().getLayoutGroup();
        layout.setLayoutChild(group);
        layout.setLayoutManager(manager);
        canvas.getLayer().addChild(group.editor().getTop());
        startNode = layout;
        ZSelectionManager.select(group);
    }

    public void removeSomeChildren() {
        ZNode node = null;
        ZNode handle = null;
        ArrayList selection = ZSelectionManager.getSelectedNodes(canvas.getLayer());

        for (Iterator i = selection.iterator(); i.hasNext();) {
                        node = (ZNode)i.next();
            handle = node.editor().getTop();

                                // Always keep start node
            if (handle != startNode) {
                handle.getParent().removeChild(handle);
            }
        }

        ZSelectionManager.unselectAll(canvas.getLayer());
    }
    void setLinkStyle(int wanted) {
        if (wanted != manager.getLinkStyle()) {
            manager.setLinkStyle(startNode, wanted);
            canvas.getDrawingSurface().repaint();
        }
    }
    public void setOrientation(int wanted) {
        if (wanted != manager.getCurrentOrientation()) {
            manager.setCurrentOrientation(startNode, wanted);
            canvas.getDrawingSurface().repaint();
        }
    }
    void setParentAlign(int wanted) {
        if (wanted != manager.getCurrentHeadStyle()) {
            manager.setCurrentHeadingStyle(startNode, wanted);
            canvas.getDrawingSurface().repaint();
        }
    }
    void clear() {
        if (startNode != null) {
            canvas.getLayer().removeChild(startNode);
        }
        initScreen();
        canvas.getDrawingSurface().repaint();
    }
}
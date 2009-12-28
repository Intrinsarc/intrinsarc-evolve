/**
 * Copyright 2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * Basic example of RTrees indexing of a group node's children.
 * set ZDebug.debugSpatialIndexing = true to print out number of objects
 * being rendered. Non-indexed rendering always tries to render all objects.
 *
 * @author Jim Mokwa
 */
public class RTreeExample extends AbstractExample {

    int sq = 10;
    JToggleButton button;
    JTextField textField;
    ZGroup groupNode;
    ZLayerGroup layer;
    ZDrawingSurface surface;
    ZSceneGraphTreeView treeView;

    public RTreeExample() {
        super("RTree example");
    }
    public void addChildren(int sq) {
        for (int x=0; x< (sq * 10); x+=10) {
            System.out.println("built row: "+x+" of "+(sq*10));
            for (int y=0; y< (sq * 10); y+=10) {
                ZRectangle rect = new ZRectangle((double)x, (double)y, 8.0, 8.0);
                ZVisualLeaf leaf = new ZVisualLeaf(rect);
                groupNode.addChild(leaf);
            }
        }
        System.out.println("done adding children.");
        ZTransformGroup tf = groupNode.editor().getTransformGroup();
        tf.setScale(10);
    }

    public JToolBar buildToolBar() {
        JToolBar toolBar = new JToolBar();

        button = new JToggleButton("Press to turn indexing on");
        Integer sqi = new Integer(sq);
        final JTextField textField = new JTextField(sqi.toString());
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {

                    // When new textfield value entered, create new
                    // scenegraph tree:
                    sq = Integer.parseInt(textField.getText());
                    layer.removeChild(groupNode);
                    layer.removeChild(layer.getChild(0));
                    groupNode = new ZGroup();
                    layer.addChild(groupNode);
                    addChildren(sq);
                    if (button.isSelected()) {
                        System.out.println("Indexing...");
                        ZSpatialIndexGroup index = groupNode.editor().getSpatialIndexGroup();
                    }

                } catch (NumberFormatException e) {
                    System.out.println("error: not a number: " + textField.getText());
                    textField.setText("10");
                    sq =10;
                }
            }
        });

        button.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                int s = ev.getStateChange();
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    indexingOn();
                } else {
                    indexingOff();
                }
            }
        });

        JLabel label = new JLabel("size of square:");
        toolBar.add(button);
        toolBar.add(label);
        toolBar.add(textField);
        toolBar.addSeparator();

        return toolBar;
    }

    public java.lang.String getExampleDescription() {
        return "Basic example of RTrees indexing of a group node's children. " +
                   "set ZDebug.debugSpatialIndexing = true to print out number of objects " +
                   "being rendered. Non-indexed rendering always tries to render all objects.";
    }

    public void indexingOff() {
        groupNode.editor().removeSpatialIndexGroup();
        button.setText("Press to turn indexing on");

        long t1 = System.currentTimeMillis();
        surface.paintImmediately();
        long t2 = System.currentTimeMillis();
        System.out.println("non-rtree rendered " + (sq*sq) + " objects in " + (t2 - t1) + " milliseconds\n");

        //ZDebug.dump(layer);
        }
        public void indexingOn() {
        System.out.println("indexing...");
        ZSpatialIndexGroup index = groupNode.editor().getSpatialIndexGroup();
        //ZDebug.debugSpatialIndexing = true;
        button.setText("Press to turn indexing off");

        long t1 = System.currentTimeMillis();
        surface.paintImmediately();
        long t2 = System.currentTimeMillis();
        System.out.println("   r-tree rendered " + (sq*sq) + " objects in " + (t2 - t1) + " milliseconds");

        //ZDebug.dump(layer);
        //index.displayTree("Index Tree:");
    }

    public void initializeExample() {
        super.initializeExample();

        // Set up basic frame
        setBounds(100, 100, 400, 400);
        setVisible(true);
        ZCanvas canvas = new ZCanvas();
        surface = canvas.getDrawingSurface();
        getContentPane().add(canvas);
        layer = canvas.getLayer();

        JToolBar toolBar = buildToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);
        validate();


        groupNode = new ZGroup();
        layer.addChild(groupNode); // 320 * 320 = 1024
        addChildren(sq);
    }
}
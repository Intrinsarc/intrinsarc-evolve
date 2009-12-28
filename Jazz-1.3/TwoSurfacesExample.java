/**
 * Copyright 1998-1999 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;

import javax.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * Basic test of two surfaces each attached to a JPanel.
 * @author Maria Jump
 * @author Benjamin B. Bederson
 */
public class TwoSurfacesExample extends AbstractExample {

    protected JPanel panel1, panel2;
    protected ZRoot root;
    protected ZLayerGroup layer, selectionLayer;
    protected ZCanvas canvas1, canvas2;

    public TwoSurfacesExample() {
        super("Two Surfaces example");
    }

    public java.lang.String getExampleDescription() {
        return "Basic example of two surfaces each attached to a JPanel. They and both drawing the same scene graph so both views stay synchronized.";
    }

    public void initializeExample() {
        super.initializeExample();
        setSize(800, 400);   // Give this window some dimensions
        getContentPane().setLayout(null);

                            // Setup first internal frame
        panel1 = new JPanel();
        getContentPane().add(panel1);
        panel1.setLocation(new Point(10, 10));
        panel1.setSize(370, 350);
        panel1.setLayout(null);

                            // Setup second internal frame
        panel2 = new JPanel();
        getContentPane().add(panel2);
        panel2.setLocation(new Point(410, 10));
        panel2.setSize(370, 350);
        panel2.setLayout(null);

                            // Make the basic scenegraph
        root = new ZRoot();
        layer = new ZLayerGroup();
        root.addChild(layer);
                            // Create the first surface
        canvas1 = new ZCanvas(root, layer);
        canvas1.setNavEventHandlersActive(false);
        canvas1.setSize(panel1.getSize());
        panel1.add(canvas1);
        panel1.repaint();

                            // Create the second surface
        canvas2 = new ZCanvas(root, layer);
        canvas2.setNavEventHandlersActive(false);
        canvas2.setSize(panel2.getSize());
        panel2.add(canvas2);
        panel2.repaint();
                            // Add a little content to the scene
        ZText text1 = new ZText("Left-button in the left window to select");
        ZVisualLeaf leaf1 = new ZVisualLeaf(text1);
        leaf1.editor().getTransformGroup().translate(100, 100);
        layer.addChild(leaf1.editor().getTop());

        ZText text2 = new ZText("Left-button in the right window to pan");
        ZVisualLeaf leaf2 = new ZVisualLeaf(text2);
        leaf2.editor().getTransformGroup().translate(100, 115);
        layer.addChild(leaf2.editor().getTop());

        ZText text3 = new ZText("Right-button (and drag left or right) in either window to zoom");
        ZVisualLeaf leaf3 = new ZVisualLeaf(text3);
        leaf3.editor().getTransformGroup().translate(100, 200);
        layer.addChild(leaf3.editor().getTop());

        ZRectangle rect = new ZRectangle(50, 50, 50, 100);
        rect.setFillPaint(Color.blue);
        rect.setPenPaint(Color.black);
        ZVisualLeaf leaf4 = new ZVisualLeaf(rect);
        layer.addChild(leaf4.editor().getTop());

        double[] xp = new double[2];
        double[] yp = new double[2];
        xp[0] = 0;   yp[0] = 0;
        xp[1] = 50;  yp[1] = 50;
        ZPolyline poly = new ZPolyline(xp, yp);
        poly.setPenPaint(Color.red);
        ZVisualLeaf leaf5 = new ZVisualLeaf(poly);
        layer.addChild(leaf5.editor().getTop());

                        // Add selection event handler to left window
        selectionLayer = new ZLayerGroup();
        root.addChild(selectionLayer);
        canvas1.getCamera().addLayer(selectionLayer);
        canvas2.getCamera().addLayer(selectionLayer);

        ZEventHandler selectionHandler = new ZCompositeSelectionHandler(canvas1.getCameraNode(), canvas1, selectionLayer, ZCompositeSelectionHandler.MOVE | ZCompositeSelectionHandler.MODIFY | ZCompositeSelectionHandler.SCALE);
        selectionHandler.setActive(true);

                        // Add panning event handler to right window
        ZEventHandler panHandler2 = new ZPanEventHandler(canvas2.getCameraNode());
        panHandler2.setActive(true);

                        // Add zooming event handler to both windows
        ZEventHandler zoomHandler1 = new ZoomEventHandler(canvas1.getCameraNode());
        ZEventHandler zoomHandler2 = new ZoomEventHandler(canvas2.getCameraNode());
        zoomHandler2.setActive(true);
        zoomHandler1.setActive(true);
    }

    public void twoSurfacesExample() {
        setSize(800, 400);      // Give this window some dimensions
        getContentPane().setLayout(null);

                        // Setup first internal frame
        panel1 = new JPanel();
        getContentPane().add(panel1);
        panel1.setLocation(new Point(10, 10));
        panel1.setSize(370, 350);

                        // Setup second internal frame
        panel2 = new JPanel();
        getContentPane().add(panel2);
        panel2.setLocation(new Point(410, 10));
        panel2.setSize(370, 350);

        setVisible(true);// Make this window visible

                        // Make the basic scenegraph
        root = new ZRoot();
        layer = new ZLayerGroup();
        root.addChild(layer);
                        // Create the first surface
        canvas1 = new ZCanvas(root, layer);
        canvas1.setNavEventHandlersActive(false);
        canvas1.setSize(panel1.getSize());
        panel1.add(canvas1);
        panel1.repaint();

                        // Create the second surface
        canvas2 = new ZCanvas(root, layer);
        canvas2.setNavEventHandlersActive(false);
        canvas2.setSize(panel2.getSize());
        panel2.add(canvas2);
        panel2.repaint();
                        // Watch for the user closing the window so we can exit gracefully

                        // Add a little content to the scene
        ZText text1 = new ZText("Left-button in the left window to select");
        ZVisualLeaf leaf1 = new ZVisualLeaf(text1);
        leaf1.editor().getTransformGroup().translate(100, 100);
        layer.addChild(leaf1.editor().getTop());

        ZText text2 = new ZText("Left-button in the right window to pan");
        ZVisualLeaf leaf2 = new ZVisualLeaf(text2);
        leaf2.editor().getTransformGroup().translate(100, 115);
        layer.addChild(leaf2.editor().getTop());

        ZText text3 = new ZText("Right-button (and drag left or right) in either window to zoom");
        ZVisualLeaf leaf3 = new ZVisualLeaf(text3);
        leaf3.editor().getTransformGroup().translate(100, 200);
        layer.addChild(leaf3.editor().getTop());

        ZRectangle rect = new ZRectangle(50, 50, 50, 100);
        rect.setFillPaint(Color.blue);
        rect.setPenPaint(Color.black);
        ZVisualLeaf leaf4 = new ZVisualLeaf(rect);
        layer.addChild(leaf4.editor().getTop());

        double[] xp = new double[2];
        double[] yp = new double[2];
        xp[0] = 0;   yp[0] = 0;
        xp[1] = 50;  yp[1] = 50;
        ZPolyline poly = new ZPolyline(xp, yp);
        poly.setPenPaint(Color.red);
        ZVisualLeaf leaf5 = new ZVisualLeaf(poly);
        layer.addChild(leaf5.editor().getTop());

                        // Add selection event handler to left window
        selectionLayer = new ZLayerGroup();
        root.addChild(selectionLayer);
        canvas1.getCamera().addLayer(selectionLayer);
        canvas2.getCamera().addLayer(selectionLayer);

        ZEventHandler selectionHandler = new ZCompositeSelectionHandler(canvas1.getCameraNode(), canvas1, selectionLayer, ZCompositeSelectionHandler.MOVE | ZCompositeSelectionHandler.MODIFY | ZCompositeSelectionHandler.SCALE);
        selectionHandler.setActive(true);

                        // Add panning event handler to right window
        ZEventHandler panHandler2 = new ZPanEventHandler(canvas2.getCameraNode());
        panHandler2.setActive(true);

                        // Add zooming event handler to both windows
        ZEventHandler zoomHandler1 = new ZoomEventHandler(canvas1.getCameraNode());
        ZEventHandler zoomHandler2 = new ZoomEventHandler(canvas2.getCameraNode());
        zoomHandler2.setActive(true);
        zoomHandler1.setActive(true);
    }
}
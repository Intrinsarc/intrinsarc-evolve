/**
 * Copyright 1998-1999 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * This code creates a simple scene and then adds an internal camera
 * (or "portal") that looks at the scene also.  The scene can be
 * panned and zoomed as a whole, and the view within the portal
 * can be panned and zoomed as well.
 *
 * Toggle buttons are now provided to toggle between global pan and zoom
 * event handlers (ie. that work on both the canvas' camera and the portal)
 * and pan and zoom handlers for only the portal.
 *
 * @author Lance Good
 * @author Ben Bederson
 */
public class PortalExample extends AbstractExample {
    public PortalExample() {
        super("Portal example");
    }
    public String getExampleDescription() {
        return "This creates a simple scene and then adds an internal camera " +
                   "(or portal) that looks at the scene also.  The scene can be " +
                   "panned and zoomed as a whole, and the view within the portal " +
                   "can be panned and zoomed as well. " +
                   "Toggle buttons are now provided to toggle between global pan and zoom " +
                   "event handlers (ie. that work on both the canvas' camera and the portal) " +
                   "and pan and zoom handlers for only the portal.";
    }
    public void initializeExample() {
        super.initializeExample();

        final ZCanvas canvas;
        final ZEventHandler panHandler;
        final ZEventHandler zoomHandler;

        setBounds(100, 100, 400, 400);
        setResizable(true);
        setBackground(null);
        setVisible(true);
        canvas = new ZCanvas();
        getContentPane().add(canvas);

                                // Make some rectangles on the surface so we can see where we are
        ZRectangle rect;
        for (int x=0; x<5; x++) {
            for (int y=0; y<5; y++) {
                rect = new ZRectangle(50*x, 50*y, 40, 40);
                rect.setFillPaint(Color.blue);
                rect.setPenPaint(Color.black);
                canvas.getLayer().addChild(new ZVisualLeaf(rect));
            }
        }
                                // Now, create a portal (i.e., internal camera) on the surface
                                // that looks at all of the rectangles.
        Portal portal = new Portal(canvas, 100, 100, 200, 200);
        canvas.getRoot().addChild(portal);
        canvas.getCamera().addLayer(portal);
        panHandler = new ZPanEventHandler(portal.getPortalNode());
        zoomHandler = new ZoomEventHandler(portal.getPortalNode());

                                // Now, create the toolbar
        JToolBar toolBar = new JToolBar();
        JToggleButton global = new JToggleButton("Global");
        JToggleButton internal = new JToggleButton("Portal");
        ButtonGroup bg = new ButtonGroup();
        bg.add(global);
        bg.add(internal);
        toolBar.add(global);
        toolBar.add(internal);
        toolBar.setFloatable(false);
        global.setSelected(true);
        global.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                panHandler.setActive(false);
                zoomHandler.setActive(false);
                canvas.setNavEventHandlersActive(true);
            }
        });
        internal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                canvas.setNavEventHandlersActive(false);
                panHandler.setActive(true);
                zoomHandler.setActive(true);
            }
        });
        getContentPane().add(toolBar,BorderLayout.NORTH);
    }
}
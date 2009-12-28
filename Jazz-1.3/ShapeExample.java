/**
 * Copyright 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;
import java.util.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * Shows how to add a variety of shapes to a ZCanvas.
 *
 * @author Jesse Grosjean
 */
public class ShapeExample extends AbstractExample {
    protected ZCanvas             canvas;

    public ShapeExample() {
        super("Shape example");
    }
    public String getExampleDescription() {
        return "This example shows how to add some shapes to a canvas.";
    }
    public void initializeExample() {
        super.initializeExample();

        // Set up basic frame
        setBounds(100, 100, 400, 400);
        setResizable(true);
        setBackground(null);
        setVisible(true);
        canvas = new ZCanvas();
        getContentPane().add(canvas);
        validate();

        // Rects
        ZRectangle rect = new ZRectangle(0, 0, 50, 50);
        rect.setFillPaint(Color.yellow);
        rect.setPenWidth(0);
        canvas.getLayer().addChild(new ZVisualLeaf(rect));

        rect = new ZRectangle(20, 20, 50, 50);
        rect.setFillPaint(null);
        rect.setPenPaint(Color.red);
        rect.setPenWidth(15);
        canvas.getLayer().addChild(new ZVisualLeaf(rect));

        // Rounded Rect
        ZRoundedRectangle roundedRect = new ZRoundedRectangle(20, 50, 30, 60, 10, 10);
        roundedRect.setFillPaint(Color.gray);
        canvas.getLayer().addChild(new ZVisualLeaf(roundedRect));

        // Ellipses
        ZEllipse ellipse = new ZEllipse(100, 100, 60, 60);
        ellipse.setFillPaint(Color.green);
        ellipse.setPenWidth(10);
        canvas.getLayer().addChild(new ZVisualLeaf(ellipse));

        ellipse = new ZEllipse(110, 130, 60, 60);
        ellipse.setFillPaint(Color.blue);
        ellipse.setPenPaint(null);
        canvas.getLayer().addChild(new ZVisualLeaf(ellipse));

        ellipse = new ZEllipse(160, 200, 30, 60);
        ellipse.setFillPaint(null);
        ellipse.setPenPaint(Color.pink);
        ellipse.setPenWidth(10);
        canvas.getLayer().addChild(new ZVisualLeaf(ellipse));

        // Arcs
        ZArc arc = new ZArc(300, 300, 100, 100, 90, 200, java.awt.geom.Arc2D.PIE);
        arc.setFillPaint(Color.cyan);
        canvas.getLayer().addChild(new ZVisualLeaf(arc));

        // CubicCurve
        ZCubicCurve cubic = new ZCubicCurve(200, 300, 300, 300, 300, 400, 400, 500);
        cubic.setPenPaint(Color.orange);
        cubic.setPenWidth(7);
        cubic.setFillPaint(null);
        canvas.getLayer().addChild(new ZVisualLeaf(cubic));

        // QuadCurve
        ZQuadCurve quad = new ZQuadCurve(0, 0, 400, 0, 400, 400);
        quad.setPenPaint(Color.cyan);
        quad.setPenWidth(7);
        quad.setFillPaint(null);
        canvas.getLayer().addChild(new ZVisualLeaf(quad));

        // Line
        ZLine line = new ZLine(0, 300, 300, 0);
        line.setPenWidth(2);
        line.setFillPaint(null);
        line.setPenPaint(Color.green);
        canvas.getLayer().addChild(new ZVisualLeaf(line));

        // Polyline
        ZPolyline polyline = new ZPolyline();
        polyline.lineTo(50, 50);
        polyline.lineTo(300, 30);
        polyline.lineTo(500, 500);
        polyline.setPenWidth(50);
        canvas.getLayer().addChild(new ZVisualLeaf(polyline));

        polyline = new ZPolyline();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            polyline.lineTo(r.nextDouble() * 100, r.nextDouble() * 100);
        }
        polyline.setFillPaint(null);
        canvas.getLayer().addChild(new ZVisualLeaf(polyline));

        polyline = new ZPolyline(300, 100, 200, 100);
        polyline.setArrowHead(ZPolyline.ARROW_BOTH);
        canvas.getLayer().addChild(new ZVisualLeaf(polyline));

        polyline = new ZPolyline(100, 300, 200, 100);
        polyline.setArrowHead(ZPolyline.ARROW_BOTH);
        polyline.setPenWidth(2);
        canvas.getLayer().addChild(new ZVisualLeaf(polyline));

        // Polygon
        ZPolygon polygon = new ZPolygon();
        polygon.moveTo(0, 0);
        polygon.lineTo(10, 0);
        polygon.lineTo(50, 50);
        polygon.lineTo(0, 30);
        polygon.lineTo(10, 15);
        polygon.setFillPaint(Color.yellow);
        canvas.getLayer().addChild(new ZVisualLeaf(polygon));

        // Add selection event handler
        ZLayerGroup selectionLayer = new ZLayerGroup();
        canvas.getRoot().addChild(selectionLayer);
        canvas.getCamera().addLayer(selectionLayer);
        ZEventHandler selectionHandler = new ZCompositeSelectionHandler(canvas.getCameraNode(), canvas, selectionLayer, ZCompositeSelectionHandler.MOVE | ZCompositeSelectionHandler.MODIFY | ZCompositeSelectionHandler.SCALE);
        selectionHandler.setActive(true);

        canvas.getPanEventHandler().setActive(false);
    }
}
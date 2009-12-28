/**
 * Copyright 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public class ClipExample extends AbstractExample {
    protected ZCanvas             canvas;

    public ClipExample() {
        super("Clip example");
    }
    public String getExampleDescription() {
        return "This example shows how to use a clip group.";
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

        ZLayerGroup selectionLayer = new ZLayerGroup();
        canvas.getRoot().addChild(selectionLayer);
        canvas.getCamera().addLayer(selectionLayer);
        ZEventHandler selectionHandler = new ZCompositeSelectionHandler(canvas.getCameraNode(), canvas, selectionLayer, ZCompositeSelectionHandler.MOVE | ZCompositeSelectionHandler.MODIFY | ZCompositeSelectionHandler.SCALE);
        selectionHandler.setActive(true);

        ZGroup aGroup = new ZGroup();

        ZRectangle rect = new ZRectangle(50, 50, 60, 70);
        rect.setFillPaint(Color.blue);
        aGroup.addChild(new ZVisualLeaf(rect));
        rect = new ZRectangle(100, 100, 50, 50);
        rect.setFillPaint(Color.red);
        aGroup.addChild(new ZVisualLeaf(rect));
        rect = new ZRectangle(120, 130, 40, 25);
        rect.setFillPaint(Color.green);
        aGroup.addChild(new ZVisualLeaf(rect));

        canvas.getLayer().addChild(aGroup);

        Area clipShape = new Area(new Ellipse2D.Double(0, 0, 200, 200));
        clipShape.add(new Area(new Ellipse2D.Double(150, 100, 200, 200)));
        ZPath aPath = new ZPath(new GeneralPath(clipShape));
        aPath.setFillPaint(Color.yellow);
        aPath.setPenPaint(null);

        aGroup.editor().getClipGroup().setClip(aPath);

        canvas.getPanEventHandler().setActive(false);
    }
}
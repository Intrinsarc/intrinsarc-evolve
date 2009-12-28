/**
 * Copyright 1998-1999 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
import java.awt.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * The portal itself actually consists of a few elements.  There is a layer which
 * can be looked at by the application camera.  The layer has a ZVisualGroup which
 * acts as a border around the camera (it contains a rectangle).  Then, the border
 * contains a ZVisualLeaf which contains the internal camera.  The internal camera
 * is the actual element that acts like a portal - looking onto the scenegraph.
 */
class Portal extends ZLayerGroup {
    ZVisualLeaf leaf;
    ZCamera camera;

    public Portal(ZCanvas canvas, int x, int y, int w, int h) {
        camera = new ZCamera(canvas.getLayer(), canvas.getDrawingSurface());

                                // Fill in the details of the camera - its bounds, color, border
        camera.setBounds(x, y, w, h);
        camera.setFillColor(Color.gray);
        leaf = new ZVisualLeaf(camera);
        ZVisualGroup border = new ZVisualGroup(leaf);
        ZRectangle borderRect = new ZRectangle(x, y, w, h);
        borderRect.setPenPaint(Color.black);
        borderRect.setFillPaint(null);
        borderRect.setPenWidth(5.0);
        border.setFrontVisualComponent(borderRect);

                                // Now, build up the portal connections.  The camera gets added to the
                                // border, and the border gets added to this layer.
        this.addChild(border);
        border.addChild(leaf);
    }
    public ZCamera getPortal() {
        return camera;
    }
    public ZNode getPortalNode() {
        return leaf;
    }
}
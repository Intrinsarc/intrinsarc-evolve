/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.util.*;
import java.awt.geom.*;
import java.awt.event.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZSelectionMoveHandler</b> is a selection handler for use with
 * <b>ZSelectionManager</b> and <code>ZSelectionMoveHandler</code> It allows the user to
 * translate the current selection in two ways:
 * <ul>
 * <li>Any item in the current selection may be dragged with the mouse.  All selected items will be translated.
 * <li>The arrow keys may be used to apply a small translation (or "nudge") to the selected items.
 * </ul>
 * <P>
 * It should be noted that this event handler may not have the desired effect
 * if internal cameras are used. This cannot be fixed in a general
 * purpose way for two reasons: the possibility of multiple representations
 * and the possibility of multiple cameras viewing the same nodes.
 * First, an internal camera is a normal visual component and as
 * such can appear in multiple places in a jazz scenegraph.
 * Consequently, if items in an internal camera are selected and the
 * internal camera appears in multiple places, moving these items
 * becomes arbitrary (ie. for which representation do they move
 * appropriately).  Second, if two distinct cameras are viewing the same
 * set of selected nodes it is again an arbitrary decision as for which
 * camera the nodes are moved appropriately.  As a result, in the case when
 * the event handler is globally active and internal or multiple cameras
 * appear, the nodes are moved first according to the most recently
 * interacted camera or, if none has been interacted, according to the
 * top camera (of the ZCanvas).
 * <P>
 * @see edu.umd.cs.jazz.ZSelectionManager
 * @see ZCompositeSelectionHandler
 * @see ZSelectionModifyHandler
 * @see ZSelectionDeleteHandler
 * @see ZSelectionScaleHandler
 *
 * @author Antony Courtney, Yale University
 * @author Lance Good, University of Maryland
 * @author Benjamin Bederson, University of Maryland
 * @author Jesse Grosjean, University of Maryland
 */
public class ZSelectionMoveHandler extends ZDragSequenceEventHandler {

    /**
     * Construct a new ZSelectionMoveHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the ZFilteredEventHandler class comment to customize this behavior.
     * @param aFilteredKeyEventSouce    the source for filtered KeyEvents. See the ZFilteredEventHandler class comment
     *                                  to customize this behavior.
     */
    public ZSelectionMoveHandler(ZSceneGraphObject aFilteredMouseEventSource, ZCanvas aFilteredKeyEventSouce) {
        super(aFilteredMouseEventSource, aFilteredKeyEventSouce);
    }

    /**
     * Moving works with BUTTON1 by default.
     */
    public ZMouseFilter getMouseFilter() {
        if (fMouseFilter == null) {
            fMouseFilter = new ZMouseFilter(InputEvent.BUTTON1_MASK);
        }
        return fMouseFilter;
    }

    /**
     * When an arrow key is pressed move the selection one pixel in the direction
     * of the arrow.
     */
    protected void filteredKeyPressed(KeyEvent e) {
        double dx = 0.0;
        double dy = 0.0;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                dx = -1.0;
                break;
            case KeyEvent.VK_RIGHT:
                dx = 1;
                break;
            case KeyEvent.VK_UP:
                dy = -1.0;
                break;
            case KeyEvent.VK_DOWN:
                dy = 1;
                break;

            default:
                return;
        }
        dragSelectionBy(new ZDimension(dx, dy));
    }

    /**
     * At the start of a drag sequence see if we are over a node,
     * or over the camera background. If we are over a node then continue with
     * the drag sequence, else reject all events until we get the next mousePressed event.
     */
    protected void startDrag(ZMouseEvent e) {
        ZSceneGraphPath aPath = e.getPath();

        if (aPath.getObject() == null) {
            getMouseFilter().rejectAllEventTypes();
            getMouseFilter().setAcceptsMousePressed(true);
        } else {
            getMouseFilter().acceptAllEventTypes();
            super.startDrag(e);
        }
    }

    /**
     * Drag all the selected visible in the interaction current camera by the
     * given screen delta.
     */
    protected void dragInScreenCoords(ZMouseEvent e, Dimension2D aScreenDelta) {
        dragSelectionBy(aScreenDelta);
    }

    /**
     * Drag selection implementation used for both key and mouse drags.
     */
    protected void dragSelectionBy(Dimension2D aScreenDelta) {
        ZDimension aDimension = new ZDimension();
        ZSceneGraphPath aPath = getCurrentFilteredMouseEvent().getCurrentPath();

        // Convert the screen delta into a global delta.
        aPath.screenToCamera(aScreenDelta, getInteractionCamera());
        getInteractionCamera().cameraToLocal(aScreenDelta, null);

        // Translate selection by global delta.
        Iterator i = getCurrentSelection().iterator();
        while (i.hasNext()) {
            ZNode each = (ZNode) i.next();
            ZTransformGroup eachTransform = each.editor().getTransformGroup();
            aDimension.setSize(aScreenDelta);
            each.globalToLocal(aDimension);
            eachTransform.translate(aDimension.getWidth(),
                                    aDimension.getHeight());
        }
    }
}
/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZSelectionModifyHandler</b> is a <b>ZEventHandler</b> that allows items
 * to be selected with the mouse, and maintains the current selection.
 *
 * As an event handler, <b>ZSelectionManager</b> directly supports the
 * following actions:
 * <ul>
 * <li> Click to select/unselect an item.
 * <li> Click-and-drag on the background to marquee select.
 * </ul>
 * Holding down the <b>SHIFT</b> key while performing the above actions
 * will extend the current selection with the newly selected items, otherwise
 * the previous selection (if any) will be replaced by the new selection.
 * <P>
 * @see edu.umd.cs.jazz.ZSelectionManager
 * @see ZCompositeSelectionHandler
 * @see ZSelectionDeleteHandler
 * @see ZSelectionMoveHandler
 * @see ZSelectionScaleHandler
 *
 * @author Antony Courtney, Yale University
 * @author Lance Good, University of Maryland
 * @author Benjamin Bederson, University of Maryland
 * @author Jesse Grosjean, University of Maryland
 */
public class ZSelectionModifyHandler extends ZDragSequenceEventHandler {

    private ZGroup fMarqueeLayer;
    private ZVisualLeaf fMarqueeLeaf;
    private ZVisualComponent fMarquee;
    private Collection fLastMarqueeSelection;

    /**
     * Construct a new ZSelectionModifyHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the class comment to customize this behavior.
     * @param marqueeLayer              The layer to draw the marquee on
     */
    public ZSelectionModifyHandler(ZSceneGraphObject aFilteredMouseEventSource, ZGroup aMarqueeLayer) {
        super(aFilteredMouseEventSource, null);
        fMarqueeLayer = aMarqueeLayer;
    }

    /**
     * @deprecated As of Jazz version 1.2,
     * use <code>ZSelectionModifyHandler(ZSceneGraphObject aFilteredMouseEventSource, ZGroup aMarqueeLayer)</code> instead.
     */
    public ZSelectionModifyHandler(ZSceneGraphObject aFilteredMouseEventSource, ZCanvas aIgnoredCanvas, ZGroup aMarqueeLayer) {
        super(aFilteredMouseEventSource, null);
        fMarqueeLayer = aMarqueeLayer;
    }

    /**
     * Modifying the selection works with BUTTON1 by default.
     */
    public ZMouseFilter getMouseFilter() {
        if (fMouseFilter == null) {
            fMouseFilter = new ZMouseFilter(InputEvent.BUTTON1_MASK);
        }
        return fMouseFilter;
    }

    /**
     * On mouse pressed try to select the node under the cursor.
     */
    protected void filteredMousePressed(ZMouseEvent e) {
        super.filteredMousePressed(e);
        makeSelection(getNodeToSelect(e), e);
    }

    /**
     * Forward the message to <code>startMarqueeSelection</code> if
     * <code>shouldStartMarqueeSelection</code> returns true. Otherwise make the mouse filter
     * reject all events until the next mouse pressed that it sees.
     */
    protected void startDrag(ZMouseEvent e) {
        super.startDrag(e);

        if (shouldStartMarqueeSelection(e)) {
            startMarqueeSelection(e);
            getMouseFilter().acceptAllEventTypes();
        } else {
            getMouseFilter().rejectAllEventTypes();
            getMouseFilter().setAcceptsMousePressed(true);
        }
    }

    /**
     * Forward the message to <code>modifyMarqueeSelection</code>.
     */
    protected void dragInScreenCoords(ZMouseEvent e, Dimension2D aScreenDelta) {
        modifyMarqueeSelection(e, aScreenDelta);
    }

    /**
     * Forward the message to <code>endMarqueeSelection</code>.
     */
    protected void endDrag(ZMouseEvent e) {
        super.endDrag(e);
        endMarqueeSelection(e);
    }

    // Point-click selections.

    /**
     * Select aNode. If the event <code>isOptionSelection</code> then option select
     * the node, otherwise unselect all nodes and then select aNode.
     */
    protected void makeSelection(ZNode aNode, ZMouseEvent e) {
        if (isOptionSelection(e)) {
            if (aNode == null)
                return;

            if (ZSelectionManager.isSelected(aNode)) {
                ZSelectionManager.unselect(aNode);
            } else {
                ZSelectionManager.select(aNode);
            }
        } else {
            if (!ZSelectionManager.isSelected(aNode)) {
                unselectAll();
                ZSelectionManager.select(aNode);
            }
        }
    }

    /**
     * Return the node that should be selected for the given event, null
     * if no node should be selected.
     */
    protected ZNode getNodeToSelect(ZMouseEvent e) {
        if (e.getPath().getObject() == null)
            return null;

        return e.getNode();
    }

    /**
     * Unselect all selected nodes seen from the current interaction camera.
     */
    protected void unselectAll() {
        ZSelectionManager.unselectAll(getTopCamera());
    }

    // Marquee selections.

    /**
     * Start a new marquee selection. This creates a new marquee visual component
     * and adds it to the marquee layer.
     */
    protected void startMarqueeSelection(ZMouseEvent e) {
        ZRectangle aRectangle = (ZRectangle) getMarqueeVisualComponent();
        Point2D aPoint = new Point2D.Double(e.getX(), e.getY());
        e.getPath().screenToGlobal(aPoint);
        getMarqueeLayer().globalToLocal(aPoint);
        aRectangle.setRect(aPoint.getX(), aPoint.getY(), 0, 0);
    }

    /**
     * Modify the current marquee selection by aScreenDelta. This will update
     * the current nodes that are selected and the current shape of the marquee.
     */
    protected void modifyMarqueeSelection(ZMouseEvent e, Dimension2D aScreenDelta) {
        ZBounds aBounds = getResizedMarqueeBounds(e, aScreenDelta);
        setMarqueeBounds(aBounds);

        Collection aNewNewMarqueeSelection = getNewMarqueeSelection(aBounds);
        Collection aNewNewMarqueeSelectionCopy = new ArrayList(aNewNewMarqueeSelection);

        // this leaves us with just the nodes that need to be selected.
        aNewNewMarqueeSelectionCopy.removeAll(getLastMarqueeSelection());
        Iterator i = aNewNewMarqueeSelectionCopy.iterator();
        while (i.hasNext()) {
            ZNode each = (ZNode) i.next();

            if (isOptionSelection(e) && ZSelectionManager.isSelected(each)) {
                ZSelectionManager.unselect(each);
            } else {
                ZSelectionManager.select(each);
            }
        }

        // this leaves us with just the nodes that need to be unselected.
        getLastMarqueeSelection().removeAll(aNewNewMarqueeSelection);
        i = getLastMarqueeSelection().iterator();
        while (i.hasNext()) {
            ZNode each = (ZNode) i.next();

            if (isOptionSelection(e) && !ZSelectionManager.isSelected(each)) {
                ZSelectionManager.select(each);
            } else {
                ZSelectionManager.unselect(each);
            }
        }

        setLastMarqueeSelection(aNewNewMarqueeSelection);
    }

    /**
     * Remove the marquee visual leaf and set the last marquee selection to null.
     */
    protected void endMarqueeSelection(ZMouseEvent e) {
        setMarqueeLeaf(null);
        setLastMarqueeSelection(null);
    }

    /**
     * Return true if no node is currently under the cursor that should be
     * selected.
     */
    protected boolean shouldStartMarqueeSelection(ZMouseEvent e) {
        return getNodeToSelect(e) == null;
    }

    /**
     * Resize the marquee by aScreenDelta and return the resulting bounds.
     */
    protected ZBounds getResizedMarqueeBounds(ZMouseEvent e, Dimension2D aScreenDelta) {
        ZSceneGraphPath aPath = e.getPath();

        ZBounds aNewMarqueeBounds = new ZBounds();
        aNewMarqueeBounds.setFrameFromDiagonal(getDragStartScreenPoint(),
                                               getCurrentScreenPoint());
        aPath.screenToGlobal(aNewMarqueeBounds);
        getMarqueeLayer().globalToLocal(aNewMarqueeBounds);

        return aNewMarqueeBounds;
    }

    /** THIS CODE CLIPS THE MARQUEE TO THE INTERACTION CAMERAS BOUNDS.
    protected ZBounds getResizedMarqueeBounds(ZMouseEvent e, Dimension2D aScreenDelta) {
        ZSceneGraphPath aPath = e.getPath();

        ZBounds aNewMarqueeBounds = new ZBounds();
        aNewMarqueeBounds.setFrameFromDiagonal(getDragStartScreenPoint(),
                                               getCurrentScreenPoint());

        aPath.screenToGlobal(aNewMarqueeBounds);
        getMarqueeLayer().globalToLocal(aNewMarqueeBounds);
        getTopCamera().cameraToLocal(aNewMarqueeBounds,null);

        ZCamera currentCamera = null;
        for(int i=aPath.getNumCameras()-1; i>=0; i--) {
            currentCamera = aPath.getCamera(i);
            intersectWithCamera(currentCamera, aPath, aNewMarqueeBounds);
        }

        return aNewMarqueeBounds;
    }

    protected void intersectWithCamera(ZCamera iCamera, ZSceneGraphPath path, ZBounds bounds) {
        ZBounds aCameraBounds = iCamera.getBounds();
        path.cameraToScreen(aCameraBounds,iCamera);
        getTopCamera().cameraToLocal(aCameraBounds,null);
        Rectangle2D.intersect(bounds, aCameraBounds, bounds);
    }*/

    /**
     * Return the collection of nodes that intersect aBounds from the interaction
     * cameras perspective.
     */
    protected Collection getNewMarqueeSelection(ZBounds aBounds) {
        ZFindFilter aFilter = new ZMagBoundsFindFilter(aBounds,
                                                       getInteractionCamera().getMagnification());

        return getTopCamera().findNodes(aFilter);
    }

    /**
     * Get the collection of nodes that were in the last marquee
     * selection.
     */
    protected Collection getLastMarqueeSelection() {
        if (fLastMarqueeSelection == null) {
            setLastMarqueeSelection(new ArrayList());
        }
        return fLastMarqueeSelection;
    }

    /**
     * Set the collection of nodes that were in the last marquee
     * selection.
     */
    protected void setLastMarqueeSelection(Collection aCollection) {
        fLastMarqueeSelection = aCollection;
    }

    /**
     * Get the layer that the marquee upon which the marquee should be drawn.
     */
    public ZGroup getMarqueeLayer() {
        return fMarqueeLayer;
    }

    /**
     * Set the layer tha the marquee upon which the marquee should be drawn.
     */
    public void setMarqueeLayer(ZGroup aGroup) {
        fMarqueeLayer = aGroup;
    }

    /**
     * Return the leaf from which the marquee visual component should hang.
     */
    public ZVisualLeaf getMarqueeLeaf() {
        if (fMarqueeLeaf == null) {
            ZVisualLeaf aLeaf = new ZVisualLeaf();
            aLeaf.setPickable(false);
            aLeaf.setFindable(false);
            setMarqueeLeaf(aLeaf);
        }
        return fMarqueeLeaf;
    }

    /**
     * Set the leaf from which the marquee visual component should hang.
     */
    public void setMarqueeLeaf(ZVisualLeaf aMarquee) {
        if (fMarqueeLeaf != null) {
            getMarqueeLayer().removeChild(fMarqueeLeaf);
            fMarquee = null;
        }

        fMarqueeLeaf = aMarquee;

        if (fMarqueeLeaf != null) {
            getMarqueeLayer().addChild(fMarqueeLeaf);
        }
    }

    /**
     * Return the visual component used to draw the marquee.
     */
    public ZVisualComponent getMarqueeVisualComponent() {
        if (fMarquee == null) {
            ZRectangle aRectangle = new ZRectangle();
            aRectangle.setFillPaint(null);
            aRectangle.setStroke(new BasicStroke(0.0f));

            /** This has problems because the mitter limit must be greater then one.
            float dz = 1 / (float) getInteractionCamera().getMagnification();
            float dash[] = { 5 * dz };
            aRectangle.setStroke(new BasicStroke(0.0f,
                                     BasicStroke.CAP_BUTT,
                                     BasicStroke.JOIN_MITER,
                                     10.0f * dz,
                                     dash,
                                     0.0f));*/

            setMarqueeVisualComponent(aRectangle);
        }
        return fMarquee;
    }

    /**
     * Set the visual component used to draw the marquee.
     */
    public void setMarqueeVisualComponent(ZVisualComponent aMarquee) {
        if (fMarquee != null) {
            getMarqueeLeaf().removeVisualComponent(fMarquee);
        }

        fMarquee = aMarquee;

        if (fMarquee != null) {
            getMarqueeLeaf().setVisualComponent(fMarquee);
        }
    }

    /**
     * Set the bounds of the marquee visual component to the new bounds.
     */
    public void setMarqueeBounds(ZBounds aBounds) {
        ZRectangle aMarqee = (ZRectangle) getMarqueeVisualComponent();
        aMarqee.setRect(aBounds);
    }

    /**
     * Return true if the event is an option selection. Option selection events select
     * unselected nodes, and deselect selected nodes.
     */
    protected boolean isOptionSelection(ZMouseEvent e) {
        return e.isShiftDown();
    }

    /**
     * Specifies whether this event handler is active or not. An active event handler
     * is registered with its event sources for events. An inactive event handler
     * is not registered with its event sources and so will not receive events.
     *
     * @param active <code>true</code> to make this event handler active.
     */
    public void setActive(boolean active) {
        if (isActive() && !active) {
            unselectAll();
        }
        super.setActive(active);
    }
}
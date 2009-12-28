/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import edu.umd.cs.jazz.component.ZSwing;
import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Event handler to send MousePressed, MouseReleased, MouseMoved,
 * MouseClicked, and MouseDragged events on Swing components within
 * a ZCanvas
 *
 * @see ZMouseAdapter
 * @see ZMouseEvent
 * @see ZMouseListener
 * @see ZMouseMotionListener
 * @author Ben Bederson
 * @author Lance Good
 */
public class ZSwingEventHandler implements ZEventHandler, ZMouseListener, ZMouseMotionListener {

    /**
     * The node to listen to for events.
     */
    protected ZNode listenNode = null;

    /**
     * True when event handlers are set active.
     */
    protected boolean active = false;

    // The previous component - used to generate mouseEntered and
    // mouseExited events
    Component prevComponent = null;

    // The components whose cursor is on the screen
    Component cursorComponent = null;

    // Previous points used in generating mouseEntered and mouseExited
    // events
    Point2D prevPoint = null;
    Point2D prevOff = null;

    // The focused ZSwing for the left button
    ZSwing focusZSwingLeft = null;

    // The focused node for the left button
    ZNode focusNodeLeft = null;

    // The focused component for the left button
    Component focusComponentLeft = null;

    // Offsets for the focused node for the left button
    int focusOffXLeft = 0;
    int focusOffYLeft = 0;

    // The focused ZSwing for the middle button
    ZSwing focusZSwingMiddle = null;

    // The focused node for the middle button
    ZNode focusNodeMiddle = null;

    // The focused component for the middle button
    Component focusComponentMiddle = null;

    // Offsets for the focused node for the middle button
    int focusOffXMiddle = 0;
    int focusOffYMiddle = 0;

    // The focused ZSwing for the right button
    ZSwing focusZSwingRight = null;

    // The focused node for the right button
    ZNode focusNodeRight = null;

    // The focused component for the right button
    Component focusComponentRight = null;

    // Offsets for the focused node for the right button
    int focusOffXRight = 0;
    int focusOffYRight = 0;

    // The canvas
    ZCanvas canvas;

    // Constructor that adds the mouse listeners to a
    /**
     * Constructs a new ZSwingEventHandler for the given canvas,
     * and a node that will recieve the mouse events.
     * @param canvas the canvas associated with this ZSwingEventHandler.
     * @param node the node the mouse listeners will be attached to.
     */
    public ZSwingEventHandler(ZCanvas canvas, ZNode node) {
        this.canvas = canvas;
        listenNode = node;
    }

    /**
     * Constructs a new ZSwingEventHandler for the given canvas.
     */
    public ZSwingEventHandler(ZCanvas canvas) {
        this.canvas = canvas;
    }

    public void setActive(boolean active) {
        if (this.active && !active) {
            if (listenNode != null) {
                this.active = false;
                listenNode.removeMouseListener(this);
                listenNode.removeMouseMotionListener(this);
            }
        }
        else if (!this.active && active) {
            if (listenNode != null) {
                this.active = true;
                listenNode.addMouseListener(this);
                listenNode.addMouseMotionListener(this);
            }
        }
    }

    /**
     * Determines if this event handler is active.
     * @return True if active
     */
    public boolean isActive() {
        return active;
    }

    // Forwards mousePressed events to Swing components used in Jazz,
    // if any should receive the event
    public void mousePressed(ZMouseEvent e1) {
        dispatchEvent(e1);
    }

    // Forwards mouseReleased events to Swing components used in Jazz,
    // if any should receive the event
    public void mouseReleased(ZMouseEvent e1) {
        dispatchEvent(e1);
    }

    // Forwards mouseClicked events to Swing components used in Jazz,
    // if any should receive the event
    public void mouseClicked(ZMouseEvent e1) {
        dispatchEvent(e1);
    }

    // We'll have to implement this ourselves later
    // since it only tells us when we exit the
    // ZNode
    public void mouseExited(ZMouseEvent e1) {
        dispatchEvent(e1);
    }

    // We'll have to implement this ourselves later
    // since it only tells us when we enter the
    // ZNode
    public void mouseEntered(ZMouseEvent e1) {
        dispatchEvent(e1);
    }

    // Forwards mouseMoved events to Swing components used in Jazz,
    // if any should receive the event
    public void mouseMoved(ZMouseEvent e1) {
        dispatchEvent(e1);
    }

    // Forwards mouseDragged events to Swing components used in Jazz,
    // if any should receive the event
    public void mouseDragged(ZMouseEvent e1) {
        dispatchEvent(e1);
    }

    // A re-implementation of Container.findComponentAt that ensures
    // that the returned component is *SHOWING* not just visible
    Component findComponentAt(Component c, int x, int y) {
        if (!c.contains(x,y)) {
            return null;
        }

        if (c instanceof Container) {
            Container contain = ((Container)c);
            int ncomponents = contain.getComponentCount();
            Component component[] = contain.getComponents();

            for (int i = 0; i < ncomponents; i++) {
                Component comp = component[i];
                if (comp != null) {
                    Point p = comp.getLocation();
                    if (comp instanceof Container) {
                        comp = findComponentAt(comp, x - (int)p.getX(), y - (int)p.getY());
                    }
                    else {
                        comp = comp.getComponentAt(x - (int)p.getX(), y - (int)p.getY());
                    }
                    if (comp != null && comp.isShowing()) {
                        return comp;
                    }
                }
            }
        }
        return c;
    }

    // Determines if any Swing components being used in Jazz should
    // receive the given MouseEvent and forwards the event to that
    // component. However, mouseEntered
    // and mouseExited are independent of the buttons
    // Also, notice the notes on mouseEntered and mouseExited
    void dispatchEvent(ZMouseEvent e1) {
        ZNode grabNode = null;
        Component comp = null;
        Point2D pt = null;
        ZNode currentNode = e1.getPath().getNode();

        // The offsets to put the event in the correct context
        int offX = 0;
        int offY = 0;

        if (currentNode instanceof ZVisualLeaf || currentNode instanceof ZVisualGroup) {
            ZVisualComponent vc = null;
            ZNode visualNode = currentNode;
            if (visualNode instanceof ZVisualLeaf) {
		if (e1.getPath().getObject() instanceof ZVisualComponent) {
		    if (((ZVisualLeaf)visualNode).indexOf((ZVisualComponent)e1.getPath().getObject()) != -1) {
			vc = (ZVisualComponent)e1.getPath().getObject();
		    }
		}
            }
            else {
		if (e1.getPath().getObject() == ((ZVisualGroup)visualNode).getFrontVisualComponent()) {
		    vc = ((ZVisualGroup)visualNode).getFrontVisualComponent();
		}
		else if (e1.getPath().getObject() == ((ZVisualGroup)visualNode).getBackVisualComponent()) {
		    vc = ((ZVisualGroup)visualNode).getBackVisualComponent();
		}
            }


            if (vc instanceof ZSwing) {

                ZSwing swing = (ZSwing)vc;
                grabNode = visualNode;

                if (grabNode.isDescendentOf(canvas.getRoot())) {
                    pt = new Point2D.Double(e1.getX(), e1.getY());
                    e1.getPath().getTopCamera().cameraToLocal(pt, grabNode);
                    prevPoint = (Point2D)pt.clone();

                    // This is only partially fixed to find the deepest
                    // component at pt.  It needs to do something like
                    // package private method:
                    // Container.getMouseEventTarget(int,int,boolean)
                    comp = findComponentAt(swing.getComponent(),(int)pt.getX(),(int)pt.getY());

                    // We found the right component - but we need to
                    // get the offset to put the event in the component's
                    // coordinates
                    if (comp != null && comp != swing.getComponent()) {
                        for(Component c = comp; c != swing.getComponent(); c = c.getParent()) {
                            offX += c.getLocation().getX();
                            offY += c.getLocation().getY();
                        }
                    }

                    // Mouse Pressed gives focus - effects Mouse Drags and
                    // Mouse Releases
                    if (comp != null && e1.getID() == MouseEvent.MOUSE_PRESSED) {
                        if (SwingUtilities.isLeftMouseButton(e1)) {
                            focusZSwingLeft = swing;
                            focusComponentLeft = comp;
                            focusNodeLeft = visualNode;
                            focusOffXLeft = offX;
                            focusOffYLeft = offY;
                        }
                        else if (SwingUtilities.isMiddleMouseButton(e1)) {
                            focusZSwingMiddle = swing;
                            focusComponentMiddle = comp;
                            focusNodeMiddle = visualNode;
                            focusOffXMiddle = offX;
                            focusOffYMiddle = offY;
                        }
                        else if (SwingUtilities.isRightMouseButton(e1)) {
                            focusZSwingRight = swing;
                            focusComponentRight = comp;
                            focusNodeRight = visualNode;
                            focusOffXRight = offX;
                            focusOffYRight = offY;
                        }
                    }
                }
            }
        }

        // This first case we don't want to give events to just
        // any Swing component - but to the one that got the
        // original mousePressed
        if (e1.getID() == MouseEvent.MOUSE_DRAGGED ||
            e1.getID() == MouseEvent.MOUSE_RELEASED) {

            // LEFT MOUSE BUTTON
            if (SwingUtilities.isLeftMouseButton(e1) &&
                focusComponentLeft != null) {

                if (focusNodeLeft.isDescendentOf(canvas.getRoot())) {
                    pt = new Point2D.Double(e1.getX(), e1.getY());
                    e1.getPath().getTopCamera().cameraToLocal(pt, focusNodeLeft);
                    MouseEvent e_temp = new MouseEvent(focusComponentLeft,
                                                       e1.getID(),
                                                       e1.getWhen(),
                                                       e1.getModifiers(),
                                                       (int)pt.getX() - focusOffXLeft,
                                                       (int)pt.getY() - focusOffYLeft,
                                                       e1.getClickCount(),
                                                       e1.isPopupTrigger());

                    ZMouseEvent e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

                    focusComponentLeft.dispatchEvent(e2);
                }
                else {
                    focusComponentLeft.dispatchEvent(e1);
                }

                focusZSwingLeft.repaint();

                e1.consume();

                if (e1.getID() == MouseEvent.MOUSE_RELEASED) {
                    focusComponentLeft = null;
                    focusNodeLeft = null;
                }

            }

            // MIDDLE MOUSE BUTTON
            if (SwingUtilities.isMiddleMouseButton(e1) &&
                focusComponentMiddle != null) {

                if (focusNodeMiddle.isDescendentOf(canvas.getRoot())) {
                    pt = new Point2D.Double(e1.getX(), e1.getY());
                    e1.getPath().getTopCamera().cameraToLocal(pt, focusNodeMiddle);
                    MouseEvent e_temp = new MouseEvent(focusComponentMiddle,
                                                       e1.getID(),
                                                       e1.getWhen(),
                                                       e1.getModifiers(),
                                                       (int)pt.getX() - focusOffXMiddle,
                                                       (int)pt.getY() - focusOffYMiddle,
                                                       e1.getClickCount(),
                                                       e1.isPopupTrigger());

                    ZMouseEvent e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

                    focusComponentMiddle.dispatchEvent(e2);
                }
                else {
                    focusComponentMiddle.dispatchEvent(e1);
                }

                focusZSwingMiddle.repaint();

                e1.consume();

                if (e1.getID() == MouseEvent.MOUSE_RELEASED) {
                    focusComponentMiddle = null;
                    focusNodeMiddle = null;
                }

            }

            // RIGHT MOUSE BUTTON
            if (SwingUtilities.isRightMouseButton(e1) &&
                focusComponentRight != null) {

                if (focusNodeRight.isDescendentOf(canvas.getRoot())) {
                    pt = new Point2D.Double(e1.getX(), e1.getY());
                    e1.getPath().getTopCamera().cameraToLocal(pt, focusNodeRight);
                    MouseEvent e_temp = new MouseEvent(focusComponentRight,
                                                       e1.getID(),
                                                       e1.getWhen(),
                                                       e1.getModifiers(),
                                                       (int)pt.getX() - focusOffXRight,
                                                       (int)pt.getY() - focusOffYRight,
                                                       e1.getClickCount(),
                                                       e1.isPopupTrigger());

                    ZMouseEvent e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

                    focusComponentRight.dispatchEvent(e2);
                }
                else {
                    focusComponentRight.dispatchEvent(e1);
                }

                focusZSwingRight.repaint();

                e1.consume();

                if (e1.getID() == MouseEvent.MOUSE_RELEASED) {
                    focusComponentRight = null;
                    focusNodeRight = null;
                }

            }
        }
        // This case covers the cases mousePressed, mouseClicked,
        // and mouseMoved events
        else if ((e1.getID() == MouseEvent.MOUSE_PRESSED ||
                  e1.getID() == MouseEvent.MOUSE_CLICKED ||
                  e1.getID() == MouseEvent.MOUSE_MOVED) &&
                 (comp != null)) {

            MouseEvent e_temp = new MouseEvent(comp,
                                               e1.getID(),
                                               e1.getWhen(),
                                               e1.getModifiers(),
                                               (int)pt.getX() - offX,
                                               (int)pt.getY() - offY,
                                               e1.getClickCount(),
                                               e1.isPopupTrigger());

            ZMouseEvent e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

            comp.dispatchEvent(e2);

            e1.consume();
        }

        // Now we need to check if an exit or enter event needs to
        // be dispatched - this code is independent of the mouseButtons.
        // I tested in normal Swing to see the correct behavior.
        if (prevComponent != null) {
            // This means mouseExited

            // This shouldn't happen - since we're only getting node events
            if (comp == null || e1.getID() == MouseEvent.MOUSE_EXITED) {
                MouseEvent e_temp = new MouseEvent(prevComponent,
                                                   MouseEvent.MOUSE_EXITED,
                                                   e1.getWhen(),
                                                   0,
                                                   (int)prevPoint.getX() - (int)prevOff.getX(),
                                                   (int)prevPoint.getY() - (int)prevOff.getY(),
                                                   e1.getClickCount(),
                                                   e1.isPopupTrigger());

                ZMouseEvent e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

                prevComponent.dispatchEvent(e2);
                prevComponent = null;

                if (e1.getID() == MouseEvent.MOUSE_EXITED) {
                    e1.consume();
                }
            }


            // This means mouseExited prevComponent and mouseEntered comp
            else if (prevComponent != comp) {
                MouseEvent e_temp = new MouseEvent(prevComponent,
                                                   MouseEvent.MOUSE_EXITED,
                                                   e1.getWhen(),
                                                   0,
                                                   (int)prevPoint.getX() - (int)prevOff.getX(),
                                                   (int)prevPoint.getY() - (int)prevOff.getY(),
                                                   e1.getClickCount(),
                                                   e1.isPopupTrigger());

                ZMouseEvent e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

                prevComponent.dispatchEvent(e2);
                e_temp = new MouseEvent(comp,
                                        MouseEvent.MOUSE_ENTERED,
                                        e1.getWhen(),
                                        0,
                                        (int)prevPoint.getX() - offX,
                                        (int)prevPoint.getY() - offY,
                                        e1.getClickCount(),
                                        e1.isPopupTrigger());

                e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

                comp.dispatchEvent(e2);
            }
        }
        else {
            // This means mouseEntered
            if (comp != null) {
                MouseEvent e_temp = new MouseEvent(comp,
                                                   MouseEvent.MOUSE_ENTERED,
                                                   e1.getWhen(),
                                                   0,
                                                   (int)prevPoint.getX() - offX,
                                                   (int)prevPoint.getY() - offY,
                                                   e1.getClickCount(),
                                                   e1.isPopupTrigger());

                ZMouseEvent e2 = ZMouseEvent.createMouseEvent(e_temp.getID(), e_temp, e1.getPath(), e1.getPath());

                comp.dispatchEvent(e2);
            }
        }

        // We have to manager our own Cursors since this is normally
        // done on the native side
        if (comp != cursorComponent &&
            focusNodeLeft == null &&
            focusNodeMiddle == null &&
            focusNodeRight == null) {
            if (comp != null) {
                cursorComponent = comp;
                canvas.setCursor(comp.getCursor(),false);
            }
            else {
                cursorComponent = null;
                canvas.resetCursor();
            }
        }

        // Set the previous variables for next time
        prevComponent = comp;

        if (comp != null) {
            prevOff = new Point2D.Double(offX,offY);
        }
    }
}

/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZLinkEventHandler</b> is a simple event handler for interactively creating hyperlinks.
 * This supports clicking on an object to define a link from that object, and
 * then click on another object to define a link to that second object as a destination.
 * This inserts a ZAnchorGroup which can then be used to follow the link. Links are also
 * created by pressing on one object, dragging, then releasing on another object.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Benjamin B. Bederson
 */
public class ZLinkEventHandler implements ZEventHandler, ZMouseListener, ZMouseMotionListener, KeyListener, Serializable {
    private boolean        active = false;          // True when event handlers are attached to a node
    private ZNode          node = null;             // The node the event handlers are attached to
    private ZCanvas        canvas = null;           // The canvas this event handler is associated with

    private ZNode          currentNode = null;      // The node the pointer is over
    private ZAnchorGroup   currentLink = null;      // The link currently being defined
    private ZAnchorGroup   hiliteLink = null;       // The link currently being hilited because of a mouse-over
    private Vector         links = null;            // The list of currently visible links
                                                    // Mask out mouse and mouse/key chords
    private int            allButton1Mask   = (MouseEvent.BUTTON1_MASK |
					       MouseEvent.ALT_GRAPH_MASK |
					       MouseEvent.CTRL_MASK |
					       MouseEvent.SHIFT_MASK);

    /**
     * This method creates a new link event handler.
     * It handles the interaction to interactively create a hyperlink.
     * @param <code>node</code> The node this event handler attaches to.
     * @param <code>canvas</code> The canvas this event handler attaches to
     */
    public ZLinkEventHandler(ZNode node, ZCanvas canvas) {
        this.node = node;
        this.canvas = canvas;
    }

    /**
     * Specifies whether this event handler is active or not.
     * @param active True to make this event handler active
     */
    public void setActive(boolean active) {
        if (this.active && !active) {
                                // Turn off event handlers
            this.active = false;
            node.removeMouseListener(this);
            node.removeMouseMotionListener(this);
            canvas.removeKeyListener(this);
            hideVisibleLinksAndHighlight();
            links = null;
        } else if (!this.active && active) {
                                // Turn on event handlers
            this.active = true;
            node.addMouseListener(this);
            node.addMouseMotionListener(this);
            canvas.addKeyListener(this);
            canvas.requestFocus();

                                // Initialize links
            links = new Vector();
        }
    }

    /**
     * Determines if this event handler is active.
     * @return True if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Key press event handler
     * @param <code>e</code> The event.
     */
    public void keyPressed(KeyEvent e) {
        ZCamera camera = canvas.getCamera();
        ZAnchorGroup link;
                                // Press on 'space' to define a link to the current camera
        if (e.getKeyChar() == ' ') {
            if (currentLink != null) {
                if (currentNode != null) {
                    ZSelectionManager.unselect(currentNode);
                }
                currentLink.setDestBounds(camera.getViewBounds(), camera);
                currentNode = null;
                currentLink = null;
            }
        }
    }

    /**
     * Key release event handler
     * @param <code>e</code> The event.
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Key typed event handler
     * @param <code>e</code> The event.
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * When the mouse is moved the current node under the mouse cursor
     * is updated and the curent link if one is being created is updated.
     */
    public void mouseMoved(ZMouseEvent e) {
        updateCurrentNode(e);
        if (isDefiningLink()) {
            updateCurrentLink(e);
        }
    }

    /**
     * When the mouse is pressed the event handler tries to start creating a new
     * link.
     */
    public void mousePressed(ZMouseEvent e) {
        if ((e.getModifiers() & allButton1Mask) == MouseEvent.BUTTON1_MASK) {   // Left button only
            if (isDefiningLink()) {
                finishLink(e);
            } else {
                startLink(e);
            }
        }
    }

    /**
     * When the mouse is dragged the current node under the mouse cursor
     * is updated and the curent link if one is being created is updated.
     */
    public void mouseDragged(ZMouseEvent e) {
        updateCurrentNode(e);
        if (isDefiningLink()) {
            updateCurrentLink(e);
        }
    }

    /**
     * When the mouse is released the event handler checks to see if a link is
     * being created and if so it finishes that link.
     */
    public void mouseReleased(ZMouseEvent e) {
        if ((e.getModifiers() & allButton1Mask) == MouseEvent.BUTTON1_MASK) {   // Left button only

            if (isDefiningLink() && (currentNode == currentLink.editor().getNode())) {
                return;
            } else {
                finishLink(e);
            }
        }
    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(ZMouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(ZMouseEvent e) {
    }

    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void mouseClicked(ZMouseEvent e) {
    }

    /**
     * This method finishes the process of link creating a link. If the current node is
     * null then the link will be removed. If the current node is not null then
     * it will be set as the links destination node.
     *
     * @param <code>e</code> The event that finished the link
     */
    protected void finishLink(ZMouseEvent e) {
        ZSceneGraphPath aPath = e.getPath();
        ZCamera aCamera = aPath.getCamera();

        if (currentNode == null) {
            hideLink(currentLink);
        } else if (currentLink != null) {
            currentLink.setDestNode(currentNode, aCamera);
        }

        currentNode = null;
        currentLink = null;
    }

    /**
     * This method returns the current pickable node that is under the mouse cursor
     * even in the case when the mouse is being dragged.
     *
     * @param <code>e</code> The current event.
     */
    protected ZNode getNodeUnderMouse(ZMouseEvent e) {
        ZSceneGraphPath aPath = e.getPath();
        ZCamera aCamera = aPath.getCamera();
        ZDrawingSurface aSurface = aCamera.getDrawingSurface();

        aPath = aSurface.pick(e.getX(), e.getY());
        if (aPath.getObject() != null) {
            return aPath.getNode();
        }
        return null;
    }

    /**
     * This method will hilite the passed in node selecting it. It will also
     * set the anchor group for that node to visible if the node has an
     * anchor group.
     *
     * @param <code>aNode</code> The node to hilite.
     * @param <code>aCamera</code> The camera used to primarily view the anchor group.
     */
    protected void hilite(ZNode aNode, ZCamera aCamera) {
        if (aNode == null) return;

        ZSelectionManager.select(aNode);

        if (isDefiningLink()) return;

        ZSceneGraphEditor editor = aNode.editor();
        if (editor.hasAnchorGroup()) {
            ZAnchorGroup anchor = editor.getAnchorGroup();
            anchor.setVisible(true, aCamera);
            hiliteLink = anchor;
        }
    }

    /**
     * Determines if this event handler is currently defining a new link.
     * @return boolean
     */
    public boolean isDefiningLink() {
        return currentLink != null;
    }

    /**
     * This will start a link, anchored at the current node under the cursor.
     */
    protected void startLink(ZMouseEvent e) {
        ZSceneGraphPath aPath = e.getPath();
        ZCamera aCamera = aPath.getTopCamera();

        // XXX when we save nodes i'm not sure what is happening to the anchor group listeners
        // that are registered with their destination node. The listener list is transient so it
        // just goes away i guess. But below exposes some error.
        // When i said currentLink.setDestNode(null, aCamera) it caused an exception because when it
        // tried to unregister its transform listener from the old destination node that node had
        // a null listner list. This is fixed no since listernlist is not gotton with a null checking
        // method instead of referenced directly. BUT
        // even without the listener list the link was getting updated correctly. So i'm confused.

        if (currentNode != null) {
            currentLink = currentNode.editor().getAnchorGroup();
            Point2D pt = new Point2D.Double(e.getX(), e.getY());
            aPath.screenToGlobal(pt);
            currentLink.setSrcPt(pt);
            currentLink.setDestPt(pt);
            currentLink.setDestNode(null, aCamera);
            currentLink.setDestBounds(null, aCamera);
            currentLink.setVisible(true, aCamera);
            links.addElement(currentLink);
        }
    }

    /**
     * This method will unhilite the passed in node by deselecting it. It will also
     * check to see if the anchor group for that node has a link. If the the anchor
     * group does not have a link it will be removed.
     *
     * @param <code>aNode</code> The node to unhilite.
     * @param <code>aCamera</code> The camera used to primarily view the anchor group.
     */
    protected void unhilite(ZNode aNode, ZCamera aCamera) {
        if (aNode == null) return;

        ZSelectionManager.unselect(aNode);

        if (isDefiningLink()) return;

        ZSceneGraphEditor editor = aNode.editor();
        if (editor.hasAnchorGroup()) {
            ZAnchorGroup anchor = editor.getAnchorGroup();
            if (anchor.hasDestination()) {
                if (links.contains(anchor)) return;
                anchor.setVisible(false, aCamera);
            } else {
                anchor.setVisible(false, aCamera);
                anchor.extract();
                links.remove(anchor);
            }
        }
        hiliteLink = null;
    }

    /**
     * This method updates the link that is currently being defined so
     * that it follows the mouse cursor.
     */
    protected void updateCurrentLink(ZMouseEvent e) {
        if (!isDefiningLink()) return;

        ZSceneGraphPath path = e.getPath();
        Point2D pt = new Point2D.Double(e.getX(), e.getY());
        path.screenToGlobal(pt);
        currentLink.setDestPt(pt);
        currentLink.updateLinkComponent(path.getCamera());
    }

    /**
     * This method updates the current node so that it is always the node under
     * the cursor. If the current node changes the old on is unhilted and the
     * new one hilited.
     */
    protected void updateCurrentNode(ZMouseEvent e) {
        ZSceneGraphPath path = e.getPath();
        ZCamera camera = path.getTopCamera();
        ZNode nodeUnderMouse = getNodeUnderMouse(e);

        if (currentNode != nodeUnderMouse) {
            unhilite(currentNode, camera);
            currentNode = nodeUnderMouse;
            hilite(currentNode, camera);
        }
    }

    /**
     * This method finishes the process of link creating a link. If the current node is
     * null then the link will be removed. If the current node is not null then
     * it will be set as the links destination node.
     *
     * @param <code>e</code> The event that finished the link
     */
    protected void hideLink(ZAnchorGroup aLink) {
        if (aLink == null) return;

        aLink.setVisible(false, null);

        if (links != null) {
            links.remove(currentLink);
        }
        if (!aLink.hasDestination()) {
            aLink.extract();
        }
    }

    /**
     * Hides all links that have been made visible by this event handler.
     */
    public void hideVisibleLinksAndHighlight() {
        ZAnchorGroup link;

        if (currentNode != null) {
            currentNode.editor().removeSelectionGroup();
        }

        if (links != null) {
            Iterator i = links.iterator();
            while (i.hasNext()) {
                ZAnchorGroup each = (ZAnchorGroup) i.next();
                each.setVisible(false, null);
            }
        }
        if (hiliteLink != null) {
            hideLink(hiliteLink);
        }
    }
}

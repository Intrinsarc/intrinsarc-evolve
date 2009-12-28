/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * <code>ZCompositeSelectionHandler</code> is a convenience class that allows
 * applications to use a single event handler to manage the multiple jazz
 * selection event handlers.
 *
 * @see edu.umd.cs.jazz.ZSelectionManager
 * @see ZSelectionModifyHandler
 * @see ZSelectionMoveHandler
 * @see ZSelectionDeleteHandler
 * @see ZSelectionScaleHandler
 * @see ZSelectionResizeHandler
 *
 * @author Lance Good
 */
public class ZCompositeSelectionHandler implements ZEventHandler, Serializable {
    /** Flag used to indicate selection modification */
    public static final int MODIFY        = 1;

    /** Flag used to indicate selection movement */
    public static final int MOVE          = 2;

    /** Flag used to indicate selection keyboard scaling */
    public static final int SCALE         = 4;

    /** Flag used to indicate selection deletion */
    public static final int DELETE        = 8;

    /** Flag used to indicate selection resizing */
    public static final int RESIZE        = 16;

    /** Flag used to indicate all available selection behaviors */
    public static final int ALL_AVAILABLE = MODIFY | MOVE | SCALE | DELETE | RESIZE;

    /** The current enabled event handlers */
    private int enabledFlag = 0;

    /** true when event handler is active */
    protected boolean active = false;

    /** node this event handler attaches to */
    protected ZNode node = null;

    /** canvas this event handler attaches to */
    protected ZCanvas canvas = null;

    /** Marquee layer */
    protected ZLayerGroup layer = null;

    /** Selection modify handler */
    private ZSelectionModifyHandler modifyHandler = null;

    /** Selection move handler */
    private ZSelectionMoveHandler moveHandler = null;

    /** Selection scale handler */
    private ZSelectionScaleHandler scaleHandler = null;

    /** Selection delete handler */
    private ZSelectionDeleteHandler deleteHandler = null;

    /** Selection resize handler */
    private ZSelectionResizeHandler resizeHandler = null;

    /**
     * Creates a composite selection handler with all available selection
     * event handler types enabled.  This event handler will operate across
     * all cameras.
     *
     * @param node The node to which this event hander attaches
     * @param canvas The canvas for which this event handler is active
     * @param marqueeLayer The layer on which marquee selection is drawn
     */
    public ZCompositeSelectionHandler(ZNode node, ZCanvas canvas, ZLayerGroup marqueeLayer) {
        this(node,canvas,marqueeLayer,ALL_AVAILABLE);
    }

    /**
     * Creates a composite selection handler with the specified enabled
     * selection event handler types.  This event handler will operate across
     * all cameras.
     *
     * Possible values for <code>flags</code> include boolean combinations of
     * <code>MOVE</code>, <code>MODIFY</code>, <code>SCALE</code>,
     * <code>DELETE</code>, and <code>ALL_AVAILABLE</code>.
     * @param node The node to which this event hander attaches
     * @param canvas The canvas for which this event handler is active
     * @param marqueeLayer The layer on which marquee selection is drawn
     * @param flags The event handlers to enable
     */
    public ZCompositeSelectionHandler(ZNode node, ZCanvas canvas, ZLayerGroup marqueeLayer, int flags) {
        this.node = node;
        this.canvas = canvas;
        this.layer = marqueeLayer;

        setEnabled(flags,true);
    }

    /**
     * Specifies whether this event handler is active
     * @param active True to make this event handler active
     */
    public void setActive(boolean active) {
        if (this.active && !active) {
            // turn off this handler
            if (moveHandler != null) {
                moveHandler.setActive(false);
            }
            if (resizeHandler != null) {
                resizeHandler.setActive(false);
            }
            if (modifyHandler != null) {
                modifyHandler.setActive(false);
            }
            if (scaleHandler != null) {
                scaleHandler.setActive(false);
            }
            if (deleteHandler != null) {
                deleteHandler.setActive(false);
            }

            this.active = false;
        } else if (!this.active && active) {
            // turn on handler:
            if (moveHandler != null) {
                moveHandler.setActive(true);
            }
            if (resizeHandler != null) {
                resizeHandler.setActive(true);
            }
            if (modifyHandler != null) {
                modifyHandler.setActive(true);
            }
            if (scaleHandler != null) {
                scaleHandler.setActive(true);
            }
            if (deleteHandler != null) {
                deleteHandler.setActive(true);
            }

            this.active = true;
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
     * @return The current marquee layer
     */
    public ZLayerGroup getMarqueeLayer() {
        return layer;
    }

    /**
     * Sets the marquee layer for this event handler
     * @param layer The new marquee layer
     */
    public void setMarqueeLayer(ZLayerGroup layer) {
        this.layer = layer;
        if (modifyHandler != null &&
            modifyHandler instanceof ZSelectionModifyHandler) {
            ((ZSelectionModifyHandler)modifyHandler).setMarqueeLayer(layer);
        }
    }

    /**
     * Sets whether the specified event handlers are enabled. Possible values
     * for <code>flags</code> include boolean combinations of
     * <code>MOVE</code>, <code>MODIFY</code>, <code>SCALE</code>,
     * <code>DELETE</code>, and <code>ALL_AVAILABLE</code>.
     * @param flags The event handlers to enable or disable
     * @param enable Should the specified event handlers be enabled or disabled
     */
    public void setEnabled(int flags, boolean enable) {
        if (enable) {
            if ((flags & MOVE) == MOVE) {
                if (moveHandler == null) {
                    moveHandler = createSelectionMoveHandler();

                    if (active && modifyHandler != null && resizeHandler != null) {
                        resizeHandler.setActive(false);
                        modifyHandler.setActive(false);
                        moveHandler.setActive(true);
                        resizeHandler.setActive(true);
                        modifyHandler.setActive(true);
                    }
                    else if (active && resizeHandler != null) {
                        resizeHandler.setActive(false);
                        moveHandler.setActive(true);
                        resizeHandler.setActive(true);
                    }
                    else if (active && modifyHandler != null) {
                        modifyHandler.setActive(false);
                        moveHandler.setActive(true);
                        modifyHandler.setActive(true);
                    }
                    else if (active) {
                        moveHandler.setActive(true);
                    }
                }
            }
            if ((flags & RESIZE) == RESIZE) {
                if (resizeHandler == null) {
                    resizeHandler = createSelectionResizeHandler();

                    if (active && modifyHandler != null) {
                        modifyHandler.setActive(false);
                        resizeHandler.setActive(true);
                        modifyHandler.setActive(true);
                    }
                    else if (active) {
                        resizeHandler.setActive(true);
                    }
                }
            }
            if ((flags & MODIFY) == MODIFY) {
                if (modifyHandler == null) {
                    modifyHandler = createSelectionModifyHandler();

                    if (active) {
                        modifyHandler.setActive(true);
                    }
                }
            }
            if ((flags & SCALE) == SCALE) {
                if (scaleHandler == null) {
                    scaleHandler = createSelectionScaleHandler();

                    if (active) {
                        scaleHandler.setActive(true);
                    }
                }
            }
            if ((flags & DELETE) == DELETE) {
                if (deleteHandler == null) {
                    deleteHandler = createSelectionDeleteHandler();

                    if (active) {
                        deleteHandler.setActive(true);
                    }
                }
            }
        }
        else {
            if ((flags & MOVE) == MOVE) {
                if (moveHandler != null) {
                    if (active) {
                        moveHandler.setActive(false);
                    }
                    moveHandler = null;
                }
            }
            if ((flags & MODIFY) == MODIFY) {
                if (modifyHandler != null) {
                    if (active) {
                        modifyHandler.setActive(false);
                    }
                    modifyHandler = null;
                }
            }
            if ((flags & SCALE) == SCALE) {
                if (scaleHandler != null) {
                    if (active) {
                        scaleHandler.setActive(false);
                    }
                    scaleHandler = null;
                }
            }
            if ((flags & DELETE) == DELETE) {
                if (deleteHandler != null) {
                    if (active) {
                        deleteHandler.setActive(false);
                    }
                    deleteHandler = null;
                }
            }
        }
    }

    /**
     * This method returns the current selection modify handler if
     * <code>ZCompositeSelectionHandler.MODIFY</code> is enabled.  Otherwise
     * it returns null.
     * @return The selection modify handler associated with this event handler
     */
    public ZSelectionModifyHandler getSelectionModifyHandler() {
        return modifyHandler;
    }

    /**
     * This method returns the current selection move handler if
     * <code>ZCompositeSelectionHandler.MOVE</code> is enabled.  Otherwise
     * it returns null.
     * @return The selection move handler associated with this event handler
     */
    public ZSelectionMoveHandler getSelectionMoveHandler() {
        return moveHandler;
    }

    /**
     * This method returns the current selection scale handler if
     * <code>ZCompositeSelectionHandler.SCALE</code> is enabled.  Otherwise
     * it returns null.
     * @return The selection scale handler associated with this event handler
     */
    public ZSelectionScaleHandler getSelectionScaleHandler() {
        return scaleHandler;
    }

    /**
     * This method returns the current selection delete handler if
     * <code>ZCompositeSelectionHandler.DELETE</code> is enabled.  Otherwise
     * it returns null.
     * @return The selection delete handler associated with this event handler
     */
    public ZSelectionDeleteHandler getSelectionDeleteHandler() {
        return deleteHandler;
    }

    /**
     * This method returns the current selection resize handler if
     * <code>ZCompositeSelectionHandler.RESIZE</code> is enabled.  Otherwise
     * it returns null.
     * @return The selection delete handler associated with this event handler
     */
    public ZSelectionResizeHandler getSelectionResizeHandler() {
        return resizeHandler;
    }

    /**
     * @return a new instance of a ZSelectionMoveHandler
     */
    protected ZSelectionMoveHandler createSelectionMoveHandler() {
	return new ZSelectionMoveHandler(node,canvas);
    }

    /**
     * @return a new instance of a ZSelectionResizeHandler
     */
    protected ZSelectionResizeHandler createSelectionResizeHandler() {
	return new ZSelectionResizeHandler(node);
    }

    /**
     * @return a new instance of a ZSelectionModifyHandler
     */
    protected ZSelectionModifyHandler createSelectionModifyHandler() {
	return new ZSelectionModifyHandler(node,layer);
    }

    /**
     * @return a new instance of a ZSelectionScaleHandler
     */
    protected ZSelectionScaleHandler createSelectionScaleHandler() {
	return new ZSelectionScaleHandler(canvas);
    }

    /**
     * @return a new instance of a ZSelectionDeleteHandler
     */
    protected ZSelectionDeleteHandler createSelectionDeleteHandler() {
	return new ZSelectionDeleteHandler(canvas);
    }

}

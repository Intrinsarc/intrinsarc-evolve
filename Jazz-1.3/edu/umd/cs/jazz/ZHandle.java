/**
 * Copyright (C) 2001-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz;

import java.awt.*;
import java.awt.geom.*;

import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZHandle</b>s are used to modify jazz object using direct manipulation. A scene graph
 * object that wants to supply a set of custom handles should override the method <code>getHandles</code>.
 * See the class ZRectangle for an example of providing a set of custom handles.
 * <p>
 * ZHandles should be located on the scene graph object that they manipulate. For example a handle
 * to resize a rectangle might be located on the bottom right corner of that rectangle. Each time a
 * handle is asked to compute its bounds it is expected to relocate itself using the coordinate system
 * of the scene graph object that it is manipulating. To do this it uses a ZLocator object. ZLocators
 * return a point on a scene graph object.
 *
 * @author Jesse Grosjean
 */
public class ZHandle extends ZSceneGraphObject {

    public static Paint DEFAULT_FILL_PAINT = Color.gray;
    public static Paint DEFAULT_HIGHLIGHT_FILL_PAINT = Color.lightGray;
    public static Paint DEFAULT_PEN_PAINT = Color.black;
    public static Paint DEFAULT_HIGHLIGHT_PEN_PAINT = Color.black;
    public static double DEFAULT_HANDLE_SIZE = 8;
    public static Stroke DEFAULT_STROKE = new BasicStroke(0f);

    private ZLocator locator;
    private ZHandleGroup handleGroup;
    private boolean isHighlighted = false;

    /**
     * Create a new handle that will use the aLocator parameter to locate itself.
     */
    public ZHandle(ZLocator aLocator) {
        super();
        locator = aLocator;
    }

    /**
     * Set the default fill paint for handles. All handles in the system will be filled
     * with this paint the next time they are repainted.
     */
    public static void setDefaultFillPaint(Paint aFillPaint) {
        DEFAULT_FILL_PAINT = aFillPaint;
    }

    /**
     * Set the default highlight paint for handles. All handles in the system will be filled
     * with this paint next time they are highlighted.
     */
    public static void setDefaultHighlightFillPaint(Paint aHighlightFillPaint) {
        DEFAULT_HIGHLIGHT_FILL_PAINT = aHighlightFillPaint;
    }

    /**
     * Set the default pen paint for handles. All handles in the system will be drawn
     * with this paint the next time they are repainted.
     */
    public static void setDefaultPenPaint(Paint aPenPaint) {
        DEFAULT_PEN_PAINT = aPenPaint;
    }

    /**
     * Set the default highlight pen paint for handles. All handles in the system will be drawn
     * with this paint next time they are highlighted.
     */
    public static void setDefaultHighlightPenPaint(Paint aHighlightPenPaint) {
        DEFAULT_HIGHLIGHT_PEN_PAINT = aHighlightPenPaint;
    }

    /**
     * Set the default size for all handles.
     */
    public static void setDefaultHandleSize(double aHandleSize) {
        DEFAULT_HANDLE_SIZE = aHandleSize;
    }

    /**
     * Set the default stroke for all handles.
     */
    public static void setDefaultStroke(Stroke aStroke) {
        DEFAULT_STROKE = aStroke;
    }

    /**
     * Compute the bounds of this handle using the handles locator to determine
     * the center point of the bounds and the DEFAULT_HANDLE_SIZE to determine the extent.
     */
    public void computeBounds() {
        Point2D aPoint = getLocator().getPoint(null);

        bounds.reset();
        bounds.setRect(aPoint.getX() - (DEFAULT_HANDLE_SIZE / 2),
                       aPoint.getY() - (DEFAULT_HANDLE_SIZE / 2),
                       DEFAULT_HANDLE_SIZE,
                       DEFAULT_HANDLE_SIZE);
    }

    /**
     * Return true if the handle is highlighted.
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Mark this handles as highlighted if true, not highlighted if false.
     */
    public void setIsHighlighted(boolean aBoolean) {
        isHighlighted = aBoolean;

        if (handleGroup != null) {
            handleGroup.repaint(getBounds());
        }
    }

    /**
     * Set the locator that the handle uses to locate itself when it computes its bounds.
     */
    public void setLocator(ZLocator aLocator) {
        locator = aLocator;
        computeBounds();
    }

    /**
     * Return the locator that the handle uses to locate itself when it computes its bounds.
     */
    public ZLocator getLocator() {
        return locator;
    }

    /**
     * Return the handle group that is managing this handle in the scene graph.
     */
    public ZHandleGroup getHandleGroup() {
        return handleGroup;
    }

    /**
     * Set the handle group that is managing this handle in the scene graph.
     */
    public void setHandleGroup(ZHandleGroup aHandleGroup) {
        handleGroup = aHandleGroup;
    }

    /**
     * Render the handle using the default values for fill paint, pen paint, size
     * and stroke.
     */
    public void render(ZRenderContext aRenderContext) {
        Graphics2D g2 = aRenderContext.getGraphics2D();

        Paint fill = null;
        Paint pen = null;

        if (isHighlighted()) {
            fill = DEFAULT_HIGHLIGHT_FILL_PAINT;
            pen = DEFAULT_HIGHLIGHT_PEN_PAINT;
        } else {
            fill = DEFAULT_FILL_PAINT;
            pen = DEFAULT_PEN_PAINT;
        }

        if (fill != null) {
            g2.setPaint(fill);
            g2.fill(getBoundsReference());
        }

        if (pen != null) {
            g2.setStroke(DEFAULT_STROKE);
            g2.setPaint(pen);
            g2.draw(getBoundsReference());
        }
    }

    /**
     * Called by the managing ZHandleGroup when appropriate. Customs handles can
     * override this method to learn when a drag has started.
     *
     * @deprecated As of Jazz version 1.3,
     * replaced by <code>ZHandle.handleStartDrag(double x, double y, ZMouseEvent aEvent)</code>.
     */
    public void handleStartDrag(double x, double y) {
    }

    /**
     * Called by the managing ZHandleGroup when appropriate. Customs handles can
     * override this method to learn when a drag has occurred.
     *
     * @deprecated As of Jazz version 1.3,
     * replaced by <code>ZHandle.handleDragged(double x, double y, ZMouseEvent aEvent)</code>.
     */
    public void handleDragged(double dx, double dy) {
        getHandleGroup().relocateHandles();
    }

    /**
     * Called by the managing ZHandleGroup when appropriate. Customs handles can
     * override this method to learn when a drag has ended.
     *
     * @deprecated As of Jazz version 1.3,
     * replaced by <code>ZHandle.handleEndDrag(double x, double y, ZMouseEvent aEvent)</code>.
     */
    public void handleEndDrag(double x, double y) {
    }

    /**
     * Called by the managing ZHandleGroup when appropriate. Customs handles can
     * override this method to learn when a drag has started.
     *
     * @param x The x coord of the drag start in local coords of the ZHandleGroup.
     * @param y The y coord of the drag start in local coords of the ZHandleGroup.
     * @param aEvent The source mouse event that caused the start drag.
     */
    public void handleStartDrag(double x, double y, ZMouseEvent aEvent) {
    }

    /**
     * Called by the managing ZHandleGroup when appropriate. Customs handles can
     * override this method to learn when a drag has occurred.
     *
     * @param dx The change in the x coord of the drag in local coords of the ZHandleGroup.
     * @param dy The change in the y coord of the drag in local coords of the ZHandleGroup.
     * @param aEvent The source mouse event that caused the drag.
     */
    public void handleDragged(double dx, double dy, ZMouseEvent aEvent) {
        getHandleGroup().relocateHandles();
    }

    /**
     * Called by the managing ZHandleGroup when appropriate. Customs handles can
     * override this method to learn when a drag has ended.
     *
     * @param x The x coord of the end drag in local coords of the ZHandleGroup.
     * @param y The y coord of the end drag in local coords of the ZHandleGroup.
     * @param aEvent The source mouse event that caused the end drag.
     */
    public void handleEndDrag(double x, double y, ZMouseEvent aEvent) {
    }
}
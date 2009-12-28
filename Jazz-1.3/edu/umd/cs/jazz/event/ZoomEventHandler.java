package edu.umd.cs.jazz.event;

import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZoomEventhandler</b> provides event handlers for basic zooming
 * of a Jazz camera with the right button.  The interaction is that
 * the initial mouse press defines the zoom anchor point, and then
 * moving the mouse to the right zooms with a speed proportional
 * to the amount the mouse is moved to the right of the anchor point.
 * Similarly, if the mouse is moved to the left, the the camera is
 * zoomed out.
 * <P>
 * On a Mac with its single mouse button one may wish to change the
 * standard right mouse button zooming behavior. This can be easily done
 * with the ZMouseFilter. For example to zoom with button one and shift you
 * would do this:
 * <P>
 * <code>
 * <pre>
 * zoomEventHandler.getMouseFilter().setAndMask(InputEvent.BUTTON1_MASK |
 *                                              InputEvent.SHIFT_MASK);
 * </pre>
 * </code>
 * <P>
 * This event handler will properly pan within internal cameras if
 * the clicked within an internal camera.
 *
 * @author  Benjamin B. Bederson
 * @author  Jesse Grosjean
 */
public class ZoomEventHandler extends ZDragSequenceEventHandler {

    private boolean fIsZooming;
    private double fMinMagnification;
    private double fMaxMagnification;
    private Point2D fGlobalPressPoint;

    /**
     * Constructs a new ZoomEventHandler.
     *
     * @param aFilteredMouseEventSource the source for filtered ZMouseEvents and ZMouseMotionEvents.
     *                                  See the ZFilteredEventHandler class comment to customize this behavior.
     */
    public ZoomEventHandler(ZSceneGraphObject aFilteredMouseEventSource) {
        super(aFilteredMouseEventSource);
        fIsZooming = false;
        fMinMagnification = 0.0;
        fMaxMagnification = -1.0;
    }
    /**
     * When the dragging action ends invoke <code>stopZooming</code>.
     *
     * @param the event ending the drag.
     */
    protected void endDrag(ZMouseEvent e) {
        super.endDrag(e);
        stopZooming(e);
    }
    /**
     * Return the next view transform for the current cameras zooming
     * sequence.
     *
     * @return the next view transform for the zooming camera.
     */
    protected AffineTransform generateNextViewTransform() {
        AffineTransform result = getInteractionCamera().getViewTransform();

        double dx = getCurrentScreenPoint().getX() - getDragStartScreenPoint().getX();
        double scaleDelta = (1.0 + (0.001 * dx));

        double currentMag = getInteractionCamera().getMagnification();
        double newMag = currentMag * scaleDelta;

        if (newMag < fMinMagnification) {
            scaleDelta = fMinMagnification / currentMag;
        }
        if ((fMaxMagnification > 0) && (newMag > fMaxMagnification)) {
            scaleDelta = fMaxMagnification / currentMag;
        }

        result.translate(fGlobalPressPoint.getX(), fGlobalPressPoint.getY());
        result.scale(scaleDelta, scaleDelta);
        result.translate(-fGlobalPressPoint.getX(), -fGlobalPressPoint.getY());

        return result;
    }
    /**
     * Return the max magnification that the zooming action
     * is bound by. If the value is <= 0 then this feature is disabled. The default
     * value is -1.
     *
     * @return the max camera magnification.
     */
    public double getMaxMagnification() {
        return fMaxMagnification;
    }
    /**
     * Return the min magnification that the zooming action
     * is bound by. The default value is 0.
     *
     * @return the min camera magnification.
     */
    public double getMinMagnification() {
        return fMinMagnification;
    }
    /**
     * Return the current event filter. If no filter is specified then return a filter that only
     * accepts BUTTON3 by default.
     *
     * @return the filter that is currently in effect.
     */
    public ZMouseFilter getMouseFilter() {
        if (fMouseFilter == null) {
            fMouseFilter = new ZMouseFilter(InputEvent.BUTTON3_MASK);
        }
        return fMouseFilter;
    }
    /**
     * Return if is zooming.
     *
     * @return true if new zoom steps are currently being scheduled.
     */
    protected boolean isZooming() {
        return fIsZooming;
    }
    /**
     * Set if is zooming.
     */
    protected void isZooming(boolean aBoolean) {
        fIsZooming = aBoolean;
    }
    /**
     * Set the maximum magnification that the camera can be set to
     * with this event handler.  Setting the max mag to <= 0 disables
     * this feature.  If the max mag if set to a value which is less
     * than the current camera magnification, then the camera is left
     * at its current magnification.
     *
     * @param newMaxMag the new maximum magnification
     */
    public void setMaxMagnification(double aMagnification) {
        fMaxMagnification = aMagnification;
    }
    /**
     * Set the minimum magnification that the camera can be set to
     * with this event handler.  Setting the min mag to <= 0 disables
     * this feature.  If the min mag is set to a value which is greater
     * than the current camera magnification, then the camera is left
     * at its current magnification.
     *
     * @param newMinMag the new minimum magnification
     */
    public void setMinMagnification(double aMagnification) {
        fMinMagnification = aMagnification;
    }
    /**
     * When the dragging action starts invoke <code>startZooming</code>.
     *
     * @param the event starting the drag.
     */
    protected void startDrag(ZMouseEvent e) {
        super.startDrag(e);
        startZooming(e);
    }
    /**
     * Start zooming around the given mouse point with
     * the interaction camera.
     *
     * @param the event starting the zoom.
     */
    protected void startZooming(ZMouseEvent e) {
        isZooming(true);

        fGlobalPressPoint = new Point2D.Double(e.getX(), e.getY());
        e.getPath().screenToCamera(fGlobalPressPoint, getInteractionCamera());
        getInteractionCamera().cameraToLocal(fGlobalPressPoint, null);

        zoomOneStep();
    }
    /**
     * Stop animated zooming.
     *
     * @param the event stopping the zoom.
     */
    protected void stopZooming(ZMouseEvent e) {
        isZooming(false);
    }
    /**
     * Do one zooming step, sleep a short amount, and schedule the next zooming step.
     * This effectively continuously zooms while still accepting input events so
     * that the zoom center point can be changed, and zooming can be stopped.
     */
    protected void zoomOneStep() {
        if (isZooming()) {
            getInteractionCamera().setViewTransform(generateNextViewTransform());

            try {
                Thread.sleep(20);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ZoomEventHandler.this.zoomOneStep();
                    }
                });
            } catch (InterruptedException e) {
                isZooming(false);
            }
        }
    }
}

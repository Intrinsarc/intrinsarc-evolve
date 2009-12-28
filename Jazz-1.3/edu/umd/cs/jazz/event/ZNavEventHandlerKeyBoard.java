/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZNavEventHandlerKeyBoard</b> provides event handlers for basic zooming
 * and panning of a Jazz camera with the keyboard.  Applications can define which keys
 * are used for navigation, and how much each key moves the camera.
 * <p>
 * The camera is changed a little bit with each keypress.
 * If a key is held down so it auto-repeats, that is detected, and the camera
 * will then be continuously moved in until the key is released, or another
 * key is pressed at which point it will return to the original behavior
 * of one increment per key press.
 * <p>
 * The default parameters are:
 *   PageUp zooms in
 *   PageDown zooms out
 *   Arrow keys pan
 *   Each keypress zooms in 25%, or pans 25%
 *   Each camera change is animated over 250 milliseconds
 *   The camera is zoomed around the cursor
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author  Benjamin B. Bederson
 */
public class ZNavEventHandlerKeyBoard implements ZEventHandler, ZMouseMotionListener, KeyListener, Serializable {
    private boolean active = false;       // True when event handlers are attached to a node
    private ZNode   node = null;          // The node the event handlers are attached to
    private ZCanvas canvas = null;        // The canvas this event handler is associated with

    private boolean autoNav = false;      // True while this autonav is on
    private double   scaleDelta = 1.25f;   // Magnification factor by for incremental zooms
    private double   panDelta = 25;        // Number of pixels to pan for incremental pans
    private double   autoPanXDelta;        // Amount to pan in X by for auto-nav
    private double   autoPanYDelta;        // Amount to pan in Y by for auto-nav
    private double   autoZoomDelta;        // Amount to zoom by for auto-nav
    private Point2D pointerPosition;      // Event coords of current mouse position (in screen coordinates)
    private int     animTime = 250;       // Time for animated zooms in milliseconds
    private int     zoomInKey   = KeyEvent.VK_PAGE_UP;    // Key that zooms in a bit
    private int     zoomOutKey  = KeyEvent.VK_PAGE_DOWN;  // Key that zooms out a bit
    private int     panLeftKey  = KeyEvent.VK_LEFT;       // Key that pans to the right
    private int     panRightKey = KeyEvent.VK_RIGHT;      // Key that pans to the left
    private int     panUpKey    = KeyEvent.VK_UP;         // Key that pans to the down
    private int     panDownKey  = KeyEvent.VK_DOWN;       // Key that pans to the up
    private int     homeKey     = KeyEvent.VK_HOME;       // Key that navigates to home
    private int     prevKeyPress = 0;     // The previous key pressed (or 0 if previous key event not a press)
    private int     delay = 20;           // Delay (in milliseconds) between auto-nav increments
    private double   minMag = 0.0;        // The minimum allowed magnification
    private double   maxMag = -1.0;       // The maximum allowed magnification (or disabled if less than 0)
    
    /**
     * Constructs a new ZNavEventHandlerKeyBoard.
     * @param <code>node</code> The node this event handler attaches to.
     * @param <code>canvas</code> The canvas this event handler attaches to
     */
    public ZNavEventHandlerKeyBoard(ZNode node, ZCanvas canvas) {
	this.node = node;
	this.canvas = canvas;
        canvas.requestFocus();
    
	pointerPosition = new Point2D.Double();
    }

    /**
     * Specifies whether this event handler is active or not.
     * @param active True to make this event handler active
     */
    public void setActive(boolean active) {
	if (this.active && !active) {
				// Turn off event handlers
	    this.active = false;
	    node.removeMouseMotionListener(this);
	    canvas.removeKeyListener(this);
	} else if (active) {
				// Turn on event handlers
	    canvas.requestFocus();
	    if (!this.active) {
		this.active = true;
		node.addMouseMotionListener(this);
		canvas.addKeyListener(this);
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

    /**
     * Define the keys that are used to zoom.  
     * A key can be set to 0 to disable that function.
     * @param <code>inKey</code> The keycode of the key that should trigger zoom in events.
     * @param <code>outKey</code> The keycode of the key that should trigger zoom out events.
     */
    public void setZoomKeys(int inKey, int outKey) {
	zoomInKey = inKey;
	zoomOutKey = outKey;
    }

    /**
     * Define the keys that are used to pan.
     * A key can be set to 0 to disable that function.
     * @param <code>leftKey</code> The keycode of the key that should trigger pan left events.
     * @param <code>rightKey</code> The keycode of the key that should trigger pan right events.
     * @param <code>upKey</code> The keycode of the key that should trigger pan up events.
     * @param <code>downKey</code> The keycode of the key that should trigger pan down events.
     */
    public void setPanKeys(int leftKey, int rightKey, int upKey, int downKey) {
	panLeftKey = leftKey;
	panRightKey = rightKey;
	panUpKey = upKey;
	panDownKey = downKey;
    }

    /**
     * Define the key that is used to home.  
     * A key can be set to 0 to disable that function.
     * @param <code>homeKey</code> The keycode of the key that should trigger the home event.
     */
    public void setHomeKey(int homeKey) {
	this.homeKey = homeKey;
    }

    /**
     * Key press event handler
     * @param <code>e</code> The event.
     */
    public void keyPressed(KeyEvent e) {
	// System.out.println("Key press: " + e);

	double delta = 0.0;
	double panX = 0.0;
	double panY = 0.0;
	boolean pan  = false;
	boolean zoom = false;
	int keyCode = e.getKeyCode();
	ZCamera camera = canvas.getCamera();
	ZDrawingSurface surface = canvas.getDrawingSurface();

				// Detect auto key-repeat
				// If we get two of the same key presses in a row without
				// an intervening key release, then that means the keyboard
				// auto-repeat function is activated and we should queue
				// an event for continuous navigation which we will stop as soon
				// the key release arrives.
	if (keyCode == prevKeyPress) {
	    if (!isAutoNav()) {
				// Set amount to zoom for this increment
		if (keyCode == zoomInKey) {
		    autoZoomDelta = 1.0 + 0.3f * (scaleDelta - 1.0);
		    zoom = true;
		} else if (keyCode == zoomOutKey) {
		    autoZoomDelta = 1.0 / (1.0 + 0.3f * (scaleDelta - 1.0));
		    zoom = true;
		} else if (keyCode == panLeftKey) {
		    autoPanXDelta = (1.0 * panDelta) / camera.getMagnification();
		    autoPanYDelta = 0.0;
		    pan = true;
		} else if (keyCode == panRightKey) {
		    autoPanXDelta = (-1.0 * panDelta) / camera.getMagnification();
		    autoPanYDelta = 0.0;
		    pan = true;
		} else if (keyCode == panUpKey) {
		    autoPanXDelta = 0.0;
		    autoPanYDelta = (1.0 * panDelta) / camera.getMagnification();
		    pan = true;
		} else if (keyCode == panDownKey) {
		    autoPanXDelta = 0.0;
		    autoPanYDelta = (-1.0 * panDelta) / camera.getMagnification();
		    pan = true;
		}
		if (zoom || pan) {
		    surface.setInteracting(true);
		    startAutoNav();
		}
	    }
	} else {
				// No auto key-repeat, just zoom a bit
				// Stop auto-nav if it was going
	    if (isAutoNav()) {
		stopAutoNav();
	    }

				// Set amount to zoom for this increment
	    if (keyCode == zoomInKey) {
		delta = scaleDelta;
		zoom = true;
	    } else if (keyCode == zoomOutKey) {
		delta = 1.0 / scaleDelta;
		zoom = true;
	    } else if (keyCode == panLeftKey) {
		panX = (1.0 * panDelta) / camera.getMagnification();
		panY = 0.0;
		pan = true;
	    } else if (keyCode == panRightKey) {
		panX = (-1.0 * panDelta) / camera.getMagnification();
		panY = 0.0;
		pan = true;
	    } else if (keyCode == panUpKey) {
		panX = 0.0;
		panY = (1.0 * panDelta) / camera.getMagnification();
		pan = true;
	    } else if (keyCode == panDownKey) {
		panX = 0.0;
		panY = (-1.0 * panDelta) / camera.getMagnification();
		pan = true;
	    } else if (keyCode == homeKey) {
		AffineTransform at = new AffineTransform();
		camera.animate(at, animTime, surface);
	    }

	    if (zoom || pan) {
		surface.setInteracting(true);
		if (zoom) {
				// Check for magnification bounds
		    double currentMag = camera.getMagnification();
		    double newMag = currentMag * delta;
		    if (newMag < minMag) {
			delta = minMag / currentMag;
		    }
		    if ((maxMag > 0) && (newMag > maxMag)) {
			delta = maxMag / currentMag;
		    }

		    Point2D pt = new Point2D.Double(pointerPosition.getX(), pointerPosition.getY());
		    camera.cameraToLocal(pt, null);
		    camera.scale(delta, pt.getX(), pt.getY(), animTime, surface);
		} else {
		    camera.translate(panX, panY, animTime, surface);
		}
		surface.setInteracting(false);
	    }
	}	    
	prevKeyPress = keyCode;
    }

    /**
     * Key release event handler
     * @param <code>e</code> The event.
     */
    public void keyReleased(KeyEvent e) {
	// System.out.println("Key release: " + e);

	prevKeyPress = 0;
	autoZoomDelta = 0.0;
	autoPanXDelta = 0;
	autoPanYDelta = 0;
				// Stop auto-zoom if it was going
	if (isAutoNav()) {
	    stopAutoNav();
	    canvas.getDrawingSurface().setInteracting(false);
	}
    }

    /**
     * Key typed event handler
     * @param <code>e</code> The event.
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Watch mouse motion so we always know where the mouse is.
     * We can use this info to zoom around the mouse position.
     */
    public void mouseMoved(ZMouseEvent e) {
	setZoomCenter(e.getX(), e.getY());
    }

    /**
     * Watch mouse motion so we always know where the mouse is.
     * We can use this info to zoom around the mouse position.
     */
    public void mouseDragged(ZMouseEvent e) {
	setZoomCenter(e.getX(), e.getY());
    }

    /**
     * Specify the point (in screen coordinates) that the camera
     * will be zoomed about.
     * @param x,y Screen coordinates of zoom center
     */
    public void setZoomCenter(int x, int y) {
	pointerPosition.setLocation(x, y);
    }

    /**
     * Return true if currently auto-zooming
     */
    public boolean isAutoNav() {
	return autoNav;
    }

    /**
     * Start the auto navigation
     */
    public void startAutoNav() {
	if (!autoNav) {
	    autoNav = true;
	    navOneStep();
	}
    }

    /**
     * Stops the auto navigation
     */
    public void stopAutoNav() {
	autoNav = false;
    }

    /**
     * Set the minimum magnification that the camera can be set to
     * with this event handler.  Setting the min mag to <= 0 disables
     * this feature.  If the min mag if set to a value which is greater
     * than the current camera magnification, then the camera is left
     * at its current magnification.
     * @param newMinMag the new minimum magnification
     */
    public void setMinMagnification(double newMinMag) {
	minMag = newMinMag;
    }

    /**
     * Set the maximum magnification that the camera can be set to
     * with this event handler.  Setting the max mag to <= 0 disables
     * this feature.  If the max mag if set to a value which is less
     * than the current camera magnification, then the camera is left
     * at its current magnification.
     * @param newMaxMag the new maximum magnification
     */
    public void setMaxMagnification(double newMaxMag) {
	maxMag = newMaxMag;
    }

    /**
     * Implements auto-navigation
     */
    public void navOneStep() {
	ZCamera camera = canvas.getCamera();

	if (autoNav) {
	    if (autoZoomDelta > 0) {
				// Check for magnification bounds
		double delta = autoZoomDelta;
		double currentMag = camera.getMagnification();
		double newMag = currentMag * autoZoomDelta;
		if (newMag < minMag) {
		    delta = minMag / currentMag;
		}
		if ((maxMag > 0) && (newMag > maxMag)) {
		    delta = maxMag / currentMag;
		}

		Point2D pt = new Point2D.Double(pointerPosition.getX(), pointerPosition.getY());
		camera.cameraToLocal(pt, null);
		camera.scale(delta, pt.getX(), pt.getY());
	    }
	    if ((autoPanXDelta != 0) || (autoPanYDelta != 0)) {
		camera.translate(autoPanXDelta, autoPanYDelta);
	    }

	    try {
				// The sleep here is necessary.  Otherwise, there won't be
				// time for the primary event thread to get and respond to
				// input events.
		Thread.sleep(20);

				// If the sleep was interrupted, then cancel the zooming,
				// so don't do the next zooming step
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			ZNavEventHandlerKeyBoard.this.navOneStep();
		    }
		});
	    } catch (InterruptedException e) {
		autoNav = false;
	    }
	}
    }
}

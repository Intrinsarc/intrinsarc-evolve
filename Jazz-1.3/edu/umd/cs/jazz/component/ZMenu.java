/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

import edu.umd.cs.jazz.util.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.*;


/**
 * A Menu for use in Jazz.  This still has an associated JPopupMenu
 * (which is always potentially heavyweight depending on component location
 * relative to containing window borders.)  However, this Menu places
 * the PopupMenu component of the Menu in the appropriate position
 * relative to the permanent button on the Menu.  The PopupMenu is never
 * transformed.
 *
 * This class was not designed for subclassing.  If different behavior
 * is required, it seems more appropriate to subclass JMenu directly
 * using this class as a model.
 *
 * NOTE: There is currently a known bug, namely, if the Menu is activated
 * via keyboard shortcuts without an actual mouse press on the Menu, there
 * may be unexpected results.
 *
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 *
 * @author Lance Good
 */
public class ZMenu extends JMenu implements Serializable {

    ZMouseEvent currentEvent = null;

    /**
     * Creates an empty ZMenu
     */
    public ZMenu() {
	super();
	init();
    }

    /**
     * Creates a new menu with the supplied text
     * @param s The Text for the Menu
     */
    public ZMenu (String s) {
	super(s);
	init();
    }

    /**
     * Creates a new menu with the supplied test and specified as
     * tear-off menu or not.
     * @param s The Text for the Menu
     * @param b can the menu be torn off (not yet implemented)
     */
    public ZMenu (String s, boolean b) {
	super(s,b);
	init();
    }

    /**
     * Substitute our UI for the default
     */
    private void init() {
	setUI(new ZBasicMenuUI());
    }

    /**
     * Put the Popup Menu in the appropriate spot for Jazz if a ZMouseEvent
     * has been received. Otherwise, use the default algorithm.
     * @param b should the popup be visible
     */
    public void setPopupMenuVisible(boolean b) {
	ZMouseEvent event = getCurrentEvent();

	if (event != null) {
	    
	    if (!isEnabled())
		return;
	    boolean isVisible = isPopupMenuVisible();
	    if (b != isVisible) {
		// ensurePopupMenuCreated() is already called in
		// isPopupMenuVisible so it isn't needed here
		// NOTE - Even if it was needed we couldn't get to
		// it because its private!
		
		// Set location of popupMenu (pulldown or pullright)
		//  Perhaps this should be dictated by L&F
		if ((b==true) && isShowing()) {
		    Point swing = getTruePopupMenuOrigin();
		    Point jazz = getJazzOffset();
		    getPopupMenu().show(this, swing.x+jazz.x, swing.y+jazz.y);
		} else {
		    getPopupMenu().setVisible(false);
		}
	    }

	}
	else {
	    super.setPopupMenuVisible(b);
	}
    }

    /**
     * @param event Set the most recent ZMouseEvent
     */
    protected void setCurrentEvent(ZMouseEvent event) {
	currentEvent = event;
    }

    /**
     * @return Get the most recent ZMouseEvent
     */
    protected ZMouseEvent getCurrentEvent() {
	return currentEvent;
    }

    /**
     * Get the true origin for the PopupMenu, ie. where it should
     * actually be placed relative to its parent taking into account
     * the Jazz tranformation
     */
    protected Point getTruePopupMenuOrigin() {
	int x = 0;
	int y = 0;
	JPopupMenu pm = getPopupMenu();
	// Figure out the sizes needed to calculate the menu position
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	Dimension s = getSize();

	// Transform the bounds for the Jazz transformation
	Rectangle2D bounds = new Rectangle2D.Double(0.0, 0.0, (double)s.width, (double)s.height);
	currentEvent.getPath().getCamera().localToCamera(bounds, currentEvent.getNode());
	s.width = (int)(bounds.getWidth()+0.5);
	s.height = (int)(bounds.getHeight()+0.5);

	Dimension pmSize = pm.getSize();
	// For the first time the menu is popped up, 
	// the size has not yet been initiated
	if (pmSize.width==0) {
	    pmSize = pm.getPreferredSize();
	}

	// This is the *true* location on screen - ie. within the ZCanvas
	Point position = getTrueLocationOnScreen();
	
	Container parent = getParent();
	if (parent instanceof JPopupMenu) {
	    // We are a submenu (pull-right)

            if(getComponentOrientation().isLeftToRight()) {
                // First determine x:
                if (position.x+s.width + pmSize.width < screenSize.width) {
                    x = s.width;         // Prefer placement to the right
                } else {
                    x = 0-pmSize.width;  // Otherwise place to the left
                }
            } else {
                // First determine x:
                if (position.x < pmSize.width) {
                    x = s.width;         // Prefer placement to the right
                } else {
                    x = 0-pmSize.width;  // Otherwise place to the left
                }
            }
            // Then the y:
            if (position.y+pmSize.height < screenSize.height) {
                y = 0;                       // Prefer dropping down
            } else {
                y = s.height-pmSize.height;  // Otherwise drop 'up'
            }
	} else {
	    // We are a toplevel menu (pull-down)

            if(getComponentOrientation().isLeftToRight()) {
                // First determine the x:
                if (position.x+pmSize.width < screenSize.width) {
                    x = 0;                     // Prefer extending to right 
                } else {
                    x = s.width-pmSize.width;  // Otherwise extend to left
                }
            } else {
                // First determine the x:
                if (position.x+s.width < pmSize.width) {
                    x = 0;                     // Prefer extending to right 
                } else {
                    x = s.width-pmSize.width;  // Otherwise extend to left
                }
            }
	    // Then the y:
	    if (position.y+s.height+pmSize.height < screenSize.height) {
		y = s.height;          // Prefer dropping down
	    } else {
		y = 0-pmSize.height;   // Otherwise drop 'up'
	    }
	}
	return new Point(x,y);
    }

    /**
     * @return Point The *true* location of the Menu on the screen, that is,
     *         including the Jazz transformation
     */
    protected Point getTrueLocationOnScreen() {
	Point position = null;

	if (isShowing()) {
	    Point2D pt = new Point2D.Double(0.0, 0.0);
	    Component c;

	    // We don't want to get the offset of the Swing Component
	    // from the SwingWrapper (in ZCanvas) so we stop when we get
	    // to the top Swing component below the SwingWrapper
	    for (c = this; !(c.getParent().getParent() instanceof ZCanvas); c = c.getParent()) {
		Point location = c.getLocation();
		pt.setLocation(pt.getX()+(double)location.getX(), pt.getY()+(double)location.getY());
		
	    }
	    ZCamera camera = currentEvent.getPath().getTopCamera();
	    camera.localToCamera(pt,currentEvent.getNode());	    
	    position = new Point((int)(pt.getX()+0.5),(int)(pt.getY()+0.5));

	    Point canvasOffset = c.getParent().getParent().getLocationOnScreen();

	    position.setLocation(position.getX()+canvasOffset.getX(), position.getY()+canvasOffset.getY());
	}
   
	return position;
    }

    /**
     * @return The offset of the Menu within the ZCanvas
     */
    protected Point getJazzOffset() {
	Point swing = getLocationOnScreen();
	Point jazz = getTrueLocationOnScreen();
	jazz.setLocation(jazz.getLocation().getX()-swing.getLocation().getX(),
			 jazz.getLocation().getY()-swing.getLocation().getY());
	return jazz;
    }
    
    /**
     * A substitute UI that adds our MouseListener to the front of the list.
     */
    class ZBasicMenuUI extends BasicMenuUI {

	protected void installListeners() {
	    menuItem.addMouseListener(new EventGrabber());
	    super.installListeners();
	}

    }

    /**
     * MouseAdapter to grab the ZMouseEvents and give them to this Menu
     */
    class EventGrabber extends MouseAdapter {
	public void mousePressed(MouseEvent me) {
	    if (me instanceof ZMouseEvent) {
		setCurrentEvent((ZMouseEvent)me);
	    }
	}
	
    }
    
}

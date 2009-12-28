/**
 * Copyright (C) 1998-@year@ by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.event;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZSelectionDeleteHandler</b> is a selection handler for use with a
 * <b>ZSelectionManager</b>.  <code>ZSelectionDeleteHandler</code> allows
 * the user to delete the current selection by pressing the DEL key.
 * <P>
 * <b>ZSelectionDeleteHandler</b> also allows registration of
 * <b>ZGroupListener</b> instances to be notified when items are deleted.
 * <P>
 * @see edu.umd.cs.jazz.ZSelectionManager
 * @see ZCompositeSelectionHandler
 *
 * @author Antony Courtney, Yale University
 * @author Lance Good, Univesity of Maryland
 * @author Benjamin Bederson, University of Maryland
 * @author Jesse Grosjean, University of Maryland
 */
public class ZSelectionDeleteHandler extends ZFilteredEventHandler {

    private EventListenerList fListenerList;

    /**
     * Constructs a new ZSelectionDeleteHandler.
     *
     * @param aFilteredKeyEventSouce    the source for filtered KeyEvents. See the ZFilteredEventHandler class comment
     *                                  to customize this behavior.
     */
    public ZSelectionDeleteHandler(ZCanvas aFilteredKeyEventSouce) {
        super(null, aFilteredKeyEventSouce);
        fListenerList = new EventListenerList();
    }

    /**
     * @deprecated As of Jazz version 1.2,
     * use <code>ZSelectionDeleteHandler(ZSceneGraphObject aEventSource)</code> instead.
     */
    public ZSelectionDeleteHandler(ZNode aIgnoredNode, ZCanvas aFilteredKeyEventSouce) {
        this(aFilteredKeyEventSouce);
    }

    /**
     * @deprecated As of Jazz version 1.2,
     * use <code>ZSelectionDeleteHandler(ZSceneGraphObject aEventSource)</code> instead.
     */
    public ZSelectionDeleteHandler(ZNode aIgnoredNode, ZCamera aIgnoredCamera, ZCanvas aFilteredKeyEventSouce) {
        this(aFilteredKeyEventSouce);
    }

    /**
     * Invoked when a key is pressed on the key event souce
     * and the event filter accepts the event.
     *
     * @param e the filtered key pressed event accepted by the event filter.
     */
    public void filteredKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            deleteSelection();
        }
    }

    /**
     * Delete the current selection and fire ZGroupEvent.NODE_REMOVED events
     * to each registered group listener.
     */
    protected void deleteSelection() {
        Iterator i = getCurrentSelection().iterator();
        while (i.hasNext()) {
            ZNode each = (ZNode) i.next();
            ZNode top = each.editor().getTop();
            ZGroup topParent = top.getParent();
            topParent.removeChild(top);

            fireEvent(ZGroupEvent.createNodeRemovedEvent(topParent, each, false));
        }
    }

    /**
     * Add a ZGroupListener, registered for nodeRemoved events.
     */
    public void addGroupListener(ZGroupListener l) {
        fListenerList.add(ZGroupListener.class, l);
    }

    /**
     * Remove a ZGroupListener, registered for nodeRemoved events.
     */
    public void removeGroupListener(ZGroupListener l) {
        fListenerList.remove(ZGroupListener.class, l);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     * @see EventListenerList
     */
    protected void fireEvent(ZEvent aEvent) {
        if (fListenerList == null) {
            return;
        }

        aEvent.setSource(this);

        Object[] listeners = fListenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == aEvent.getListenerType()) {
                aEvent.dispatchTo(listeners[i + 1]);
            }
            if (aEvent.isConsumed())
                return;
        }
    }
}
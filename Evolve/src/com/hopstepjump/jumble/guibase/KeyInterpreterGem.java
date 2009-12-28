package com.hopstepjump.jumble.guibase;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 10-Jan-03
 *
 */
public class KeyInterpreterGem
{
	private KeyInterpreterFacet interpreterFacet = new KeyInterpreterFacetImpl();
	private List<KeyActionFacet> actions = new ArrayList<KeyActionFacet>();
	
	public KeyInterpreterGem()
	{
	}
	
	public KeyInterpreterFacet getKeyInterpreterFacet()
	{
		return interpreterFacet;
	}
	
	
	private class KeyInterpreterFacetImpl implements KeyInterpreterFacet
	{
		public void addAction(KeyActionFacet action)
		{
			actions.add(action);
		}
	
		/**
		 * @see com.hopstepjump.jumble.guibase.KeyInterpreterFacet#keyReleased(KeyEvent, ToolCoordinatorFacet, DiagramViewFacet)
		 */
		public void keyReleased(KeyEvent event, ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView)
		{
			// find the key action that matched the key event
			KeyActionFacet matchedAction = findMatchingKeyAction(event);
			if (matchedAction != null)
			{
				Cursor current = coordinator.displayWaitCursor();
				matchedAction.actOnKeyRelease(coordinator, diagramView);
				coordinator.restoreCursor(current);
			}
	  }
	  
	  private KeyActionFacet findMatchingKeyAction(KeyEvent event)
	  {
	  	Iterator iter = actions.iterator();
			while (iter.hasNext())
	  	{
	  		KeyActionFacet action = (KeyActionFacet) iter.next();
	  		if (action.wantsKey(event))
	  			return action;
	  	}
	  	return null;
	  }
	}
}

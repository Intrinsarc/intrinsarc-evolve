// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.util.*;

/**
 * Class to keep registered Actions, which should be used by the
 * TaggedIn/OutputStream.
 * 
 * A set of recognized Actions can be added to this class. A concrete
 * implementation of this stream should install all allowed actions.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ActionSet.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class ActionSet
{

  /**
   * This holds the individual actions.
   */
  protected Map actions;
  protected Action defaultAction;

  public ActionSet()
  {
    actions = new HashMap();
    defaultAction = new Action.Unknown();
  }

  public void addAction(Action action)
  {
    actions.put(new Integer(action.getCode()), action);
  }

  public Action get(int actionCode)
  {
    Action action = (Action) actions.get(new Integer(actionCode));
    if (action == null)
      action = defaultAction;
    return action;
  }

  public boolean exists(int actionCode)
  {
    return (actions.get(new Integer(actionCode)) != null);
  }
}

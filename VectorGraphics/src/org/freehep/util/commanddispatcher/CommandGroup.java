package org.freehep.util.commanddispatcher;

import java.util.*;

/**
 * A CommandGroup represents a collection of CommandTargets. The CommandGroup is
 * Observable, and is normally Observed by the CommandTargetManager. When the
 * CommandGroup calls its notifies its observers, the CommandTargetManager
 * prompts each CommandSource currently attached to CommandTargets within the
 * CommandGroup to update their enabled/disabled status.
 * 
 * @author tonyj
 * @version $Id: CommandGroup.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public interface CommandGroup
{
  CommandTarget acceptCommand(String command);
  void addObserver(Observer observer);
  void deleteObserver(Observer observer);
  void setManager(CommandTargetManager manager);
}

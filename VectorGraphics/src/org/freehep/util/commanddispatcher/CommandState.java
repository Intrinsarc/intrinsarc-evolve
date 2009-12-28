package org.freehep.util.commanddispatcher;


/**
 * An interface representing the state of a CommandTarget (enabled or disabled,
 * text and tooltip text)
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: CommandState.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public interface CommandState
{
  public void setEnabled(boolean state);

  public void setText(String text);

  public void setToolTipText(String text);
}

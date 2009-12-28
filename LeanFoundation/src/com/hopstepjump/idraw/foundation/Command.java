package com.hopstepjump.idraw.foundation;

import java.io.*;


/**
 * a command is a non-interactive, undoable change to a diagram or to subject(s)
 */

public interface Command extends Serializable
{
	/** execute the command 
	 * @param isTop TODO*/
	public void execute(boolean isTop);
	public String getExecutionName();

	/** undo the command, and clean up */
	public void unExecute();
	public String getUnExecutionName();

  /** commit the entire command */
  public void afterCommit();
  
	/** clean up due to truncation i.e. fell off the start of the buffer */
	public void cleanUpBeforeTruncation();
	/** clean up due to replacement i.e. replaced when undo and then a new command comes along */
	public void cleanUpBeforeReplacement();
	
	public boolean isEmpty();
}
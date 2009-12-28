package com.hopstepjump.idraw.foundation;


/**
 *
 * (c) Andrew McVeigh 04-Jan-03
 *
 */
public abstract class AbstractCommand implements Command
{
	private String executeDescription;
	private String unExecuteDescription;
	
	public AbstractCommand(String executeDescription, String unExecuteDescription)
	{
		this.executeDescription = executeDescription;
		this.unExecuteDescription = unExecuteDescription;
	}

	public AbstractCommand()
	{
		this.executeDescription = "";
		this.unExecuteDescription = "";
	}

	public void setDescriptions(String executeDescription, String unExecuteDescription)
	{
		this.executeDescription = executeDescription;
		this.unExecuteDescription = unExecuteDescription;
	}

	public final String getExecutionName()
	{
		return executeDescription;
	}

	public final String getUnExecutionName()
	{
		return unExecuteDescription;
	}

  public void afterCommit()
  {
  }
  
	public void cleanUpBeforeTruncation()
	{
	}

	public void cleanUpBeforeReplacement()
	{
	}
	
	public boolean isEmpty()
	{
		return false;
	}

}

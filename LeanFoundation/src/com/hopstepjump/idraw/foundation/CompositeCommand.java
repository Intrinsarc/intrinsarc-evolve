package com.hopstepjump.idraw.foundation;


import java.util.*;


public class CompositeCommand extends AbstractCommand
{
  private List<Command> commands = new ArrayList<Command>();
  private String name;

  public CompositeCommand(String executeDescription, String unExecuteDescription)
	{
		super(executeDescription, unExecuteDescription);
  }

  public CompositeCommand(String name, String executeDescription, String unExecuteDescription)
	{
		super(executeDescription, unExecuteDescription);
		this.name = name;
  }

  public CompositeCommand(Command leadCommand)
	{
		super(leadCommand.getExecutionName(), leadCommand.getUnExecutionName());
		addCommand(leadCommand);
  }
  
  public void takeExecutionNamesFromLeadCommand()
  {
  	if (!commands.isEmpty())
  	{
  		Command leadCommand = commands.get(0);
  		setDescriptions(leadCommand.getExecutionName(), leadCommand.getUnExecutionName());
  	}
  }

  public void addCommand(Command command)
  {
  	if (command != null && !command.isEmpty())
	    commands.add(command);
  }

  public void execute(boolean isTop)
  {
    Iterator iter = commands.iterator();
    while (iter.hasNext())
    {
      Command command = (Command) iter.next();
      command.execute(isTop);
    }
  }

  public void unExecute()
  {
    // perform the commands in reverse just in case there were dependencies
    Collections.reverse(commands);
    Iterator iter = commands.iterator();
    while (iter.hasNext())
    {
      Command command = (Command) iter.next();
      command.unExecute();
    }
    Collections.reverse(commands);
  }
  
	public void cleanUpBeforeTruncation()
	{
		super.cleanUpBeforeTruncation();
		Collections.reverse(commands);
    Iterator iter = commands.iterator();
    while (iter.hasNext())
    {
      Command command = (Command) iter.next();
      command.cleanUpBeforeTruncation();
    }
    Collections.reverse(commands);
	}

	public void cleanUpBeforeReplacement()
	{
		super.cleanUpBeforeReplacement();
		Collections.reverse(commands);
    Iterator iter = commands.iterator();
    while (iter.hasNext())
    {
      Command command = (Command) iter.next();
      command.cleanUpBeforeReplacement();
    }
    Collections.reverse(commands);
	}
	
	
	/**
	 * @see com.hopstepjump.idraw.foundation.Command#isEmpty()
	 */
	public boolean isEmpty()
	{
		for (Iterator iter = commands.iterator(); iter.hasNext();)
		{
			Command cmd = (Command) iter.next();
			if (!cmd.isEmpty())
				return false;
		}
		return true;
	}

	public String toString()
	{
		StringBuffer buffer = new StringBuffer("CompositeCommand (name = " + name + ", id = " + hashCode() + ")\n");
		int lp = 0;
		for (Iterator iter = commands.iterator(); iter.hasNext();)
		{
			Command cmd = (Command) iter.next();
			buffer.append("   " + lp++ + ") " + cmd + "\n");
		}
		return buffer.toString();
	}
}
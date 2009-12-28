package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;

public class BasicCommandManagerGem implements Gem
{
	private CommandManagerFacetImpl commandManagerFacet = new CommandManagerFacetImpl();
	private CommandWrapperFacet commandWrapperFacet;

	// fields for implementing undo mgr functionality
	private static final int COMMAND_UNDO_LIMIT = 100;
	private int actualCommandUndoLimit = COMMAND_UNDO_LIMIT;
	private int index = 0;
  private List<Command> commands;
  private CommandManagerListenerFacet listener;
  private boolean suspendChanges;

  public BasicCommandManagerGem()
  {
    commands = new ArrayList<Command>();
  }

  public BasicCommandManagerGem(int actualCommandUndoLimit)
  {
    commands = new ArrayList<Command>();
    this.actualCommandUndoLimit = actualCommandUndoLimit;
  }

	public CommandManagerFacet getCommandManagerFacet()
	{
		return commandManagerFacet;
	}
	
	public void connectCommandWrapperFacet(CommandWrapperFacet commandWrapperFacet)
	{
		this.commandWrapperFacet = commandWrapperFacet;
	}
	
	public void connectCommandManagerListenerFacet(CommandManagerListenerFacet listener)
	{
		this.listener = listener;
	}

	private class CommandManagerFacetImpl implements CommandManagerFacet
	{
		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#canRedo()
		 */
		public boolean canRedo()
		{
			return getRedoCommand() != null;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#canUndo()
		 */
		public boolean canUndo()
		{
			return getUndoCommand() != null;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#executedCommandAndUpdateViews(Command)
		 */
		public void executeCommandAndUpdateViews(Command command)
		{
			if (command != null)
			{
				if (commandWrapperFacet != null)
					command = commandWrapperFacet.wrapCommand(command);

				// try the command first, as it may generate a read-only exception and we want
				// to preserve the redo history if this happens
//				long start = System.currentTimeMillis();
        command.execute(true);
//        long end = System.currentTimeMillis();
//        System.out.println("$$ took " + (end - start) + "ms to execute");
        
		    // cut off the commands at the index, and add to the end
		    while (commands.size() > index)
		      commands.remove(commands.size() - 1).cleanUpBeforeReplacement();
		
		    // if we have too many commands, truncate
		    if (commands.size() >= actualCommandUndoLimit)
		    {
		      // lose the first command, and subtract one from index
		      commands.remove(0).cleanUpBeforeTruncation();  // give the command a chance to clean up
		      index--;
		    }
		
				// add a composite command instead of the normal one, as
				// we can append to it later, when we are adding view updates
		    commands.add(new CompositeCommand(command));
		    index++;
		    
				// tell the diagrams to send the changes now...
				tellDiagramsToSendChanges();
			}
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getCommandIndex()
		 */
		public int getCommandIndex()
		{
			return index;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getCommandMax()
		 */
		public int getCommandMax()
		{
			return commands.size();
		}

	  private Command getUndoCommand()
	  {
	    if (index > 0)
	      return commands.get(index-1);
	    return null;
	  }
	
	  private Command getRedoCommand()
	  {
	    if (index < commands.size())
	      return commands.get(index);
	    return null;
	  }

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getRedoPresentationName()
		 */
		public String getRedoPresentationName()
		{
			Command redoCommand = getRedoCommand();
			return redoCommand != null ? redoCommand.getExecutionName() : "";
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getUndoPresentationName()
		 */
		public String getUndoPresentationName()
		{
			Command undoCommand = getUndoCommand();
			return undoCommand != null ? undoCommand.getUnExecutionName() : "";
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#redo()
		 */
		public void redo()
		{
	    // move back a command, and execute
      int size = commands.size();
	    if (index < size)
	    {
	      Command command = commands.get(index);
	      index++;
        command.execute(index == size);
				tellDiagramsToSendChanges();
	    }
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#undo()
		 */
		public void undo()
		{
	    // move back a command, and execute
	    if (index > 0)
	    {
	      index--;
	      Command command = commands.get(index);
	      command.unExecute();
				tellDiagramsToSendChanges();
	    }
		}


		public void tellDiagramsToSendChanges()
		{
			if (!suspendChanges)
			{
				for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
					diagram.sendChangesToListeners();
				if (listener != null)
					listener.commandExecuted();
			}
		}
		
		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#executeForPreview(Command)
		 */
		public Command executeForPreview(Command command, boolean sendDiagramChangesToListeners, boolean returnCommand)
		{
			if (command == null)
				return null;

			command.execute(false);
			if (sendDiagramChangesToListeners)
				tellDiagramsToSendChanges();
				
			if (!returnCommand)
				return null;
			return command;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#undoForPreview(Command)
		 */
		public void undoForPreview(Command command)
		{
			command.unExecute();
		}
		
    public void clearCommandHistory()
    {
      // lose the first command, and subtract one from index
      for (Command command : commands)
        command.cleanUpBeforeTruncation();  // give the command a chance to clean up
      index = 0;
      commands = new ArrayList<Command>();
    }

    public void switchListener(CommandManagerListenerFacet facet)
    {
      clearCommandHistory();
      listener = facet;
    }

		public void suspendChanges(boolean suspend)
		{
			suspendChanges = suspend;
			tellDiagramsToSendChanges();
		}
	}
}
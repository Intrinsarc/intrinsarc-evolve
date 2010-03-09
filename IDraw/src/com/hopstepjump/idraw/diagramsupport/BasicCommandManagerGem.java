package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;

public class BasicCommandManagerGem implements Gem
{
	private CommandManagerFacetImpl commandManagerFacet = new CommandManagerFacetImpl();
	private CommandWrapperFacet commandWrapperFacet;

	// fields for implementing undo mgr functionality
	private CommandManagerListenerFacet listener;

  public BasicCommandManagerGem()
  {
  }

  public BasicCommandManagerGem(int actualCommandUndoLimit)
  {
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
			return true;
			//return getRedoCommand() != null;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#canUndo()
		 */
		public boolean canUndo()
		{
			return true;
			//return getUndoCommand() != null;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#executedCommandAndUpdateViews(Command)
		 */
		public void executeCommandAndUpdateViews(Command command)
		{
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getCommandIndex()
		 */
		public int getCommandIndex()
		{
			return 0;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getCommandMax()
		 */
		public int getCommandMax()
		{
			return 1;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getRedoPresentationName()
		 */
		public String getRedoPresentationName()
		{
			return "redo";
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#getUndoPresentationName()
		 */
		public String getUndoPresentationName()
		{
			return "undo";
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#redo()
		 */
		public void redo()
		{
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#undo()
		 */
		public void undo()
		{
		}


		public void tellDiagramsToSendChanges()
		{
		}
		
		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#executeForPreview(Command)
		 */
		public Command executeForPreview(Command command, boolean sendDiagramChangesToListeners, boolean returnCommand)
		{
			return null;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.CommandManagerFacet#undoForPreview(Command)
		 */
		public void undoForPreview(Command command)
		{
		}
		
    public void clearCommandHistory()
    {
    }

    public void switchListener(CommandManagerListenerFacet facet)
    {
      listener = facet;
    }
	}
}
package com.hopstepjump.repository;

import javax.swing.*;

import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.repositorybase.*;

/**
 *
 * (c) Andrew McVeigh 08-Oct-02
 *
 */
public class ViewUpdateWrapperCommand
{
//	public static final int MSEC_VIEW_UPDATE_DELAY = 100;
//	public static Preference BACKGROUND_VIEW_UPDATES = new Preference(
//			"Advanced",
//			"Perform slow view updates in the background",
//			new PersistentProperty(true));
//
//  private Command viewCommand[] = new Command[ViewUpdatePassEnum.values().length];
//  private Command slowViewCommand[] = new Command[ViewUpdatePassEnum.values().length];
//  private Command commandToWrapper;
//  private long executionTime;
//
//	public ViewUpdateWrapperCommand(Command commandToWrapper)
//	{
//		this.commandToWrapper = commandToWrapper;
//	}
//
//  public void execute(final boolean isTop)
//  {
//		boolean background = GlobalPreferences.preferences.getRawPreference(BACKGROUND_VIEW_UPDATES).asBoolean();
//    final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
//
//    // start a transaction
//    repository.startTransaction("updated view", "restored view");
//    
//    clearCaches();
//    
//    commandToWrapper.execute(isTop);
//		
//    clearCaches();
//
//		// ask for another view command, in case there are newly opened diagrams in the interim
//    executionTime = System.currentTimeMillis();
//    
//    for (ViewUpdatePassEnum pass : ViewUpdatePassEnum.values())
//    {
//      int index = pass.ordinal();
//      if (viewCommand[index] == null)
//        viewCommand[index] = repository.formUpdateDiagramsCommandAfterSubjectChanges(
//        		executionTime,
//        		pass,
//        		background);
//      viewCommand[index].execute(isTop);
//    }
//		
//    if (background)
//    	processViewUpdatesInBackground(repository, isTop);
//    
//		// commit the transaction
//    repository.commitTransaction();
//  }
//
//	private void processViewUpdatesInBackground(final SubjectRepositoryFacet repository, final boolean isTop)
//	{
//		new Thread(new Runnable()
//		{
//			public void run()
//			{
//				try
//				{
//					Thread.sleep(MSEC_VIEW_UPDATE_DELAY);
//				}
//				catch (InterruptedException e)
//				{
//				}
//
//				SwingUtilities.invokeLater(new Runnable()
//		    {
//		    	public void run()
//		    	{
//		        clearCaches();
//		
//		        for (ViewUpdatePassEnum pass : ViewUpdatePassEnum.values())
//		        {
//		          int index = pass.ordinal();
//		          if (slowViewCommand[index] == null)
//		            slowViewCommand[index] = repository.formUpdateDiagramsCommandAfterSubjectChanges(executionTime, pass, false);
//		          slowViewCommand[index].execute(isTop);
//		        }
//						for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
//							diagram.sendChangesToListeners();
//		    	}
//		    });    			
//			}
//		}).start();
//	}
//  
//  public void unExecute()
//  {
//  }
//  
//  public void clearCaches()
//  {
//  }
//
//  public void cleanUpBeforeTruncation()
//	{
//		commandToWrapper.cleanUpBeforeTruncation();
//	}
//
//	/**
//	 * @see com.hopstepjump.idraw.foundation.Command#getExecutionName()
//	 */
//	public String getExecutionName()
//	{
//		return commandToWrapper.getExecutionName();
//	}
//
//	/**
//	 * @see com.hopstepjump.idraw.foundation.Command#getUnExecutionName()
//	 */
//	public String getUnExecutionName()
//	{
//		return commandToWrapper.getUnExecutionName();
//	}
//
//	/**
//	 * @see com.hopstepjump.idraw.foundation.Command#cleanupBeforeReplacement()
//	 */
//	public void cleanUpBeforeReplacement()
//	{
//		commandToWrapper.cleanUpBeforeReplacement();
//	}
//
//	/**
//	 * @see com.hopstepjump.idraw.foundation.Command#isEmpty()
//	 */
//	public boolean isEmpty()
//	{
//	  boolean empty = true;
//	  for (ViewUpdatePassEnum pass : ViewUpdatePassEnum.values())
//	    if (viewCommand[pass.ordinal()] != null && !viewCommand[pass.ordinal()].isEmpty())
//	      empty = false;
//		return commandToWrapper.isEmpty() && empty;
//	}
//
//	public String toString()
//	{
//		return
//		  "ViewUpdateWrapperCommand(commandToWrapper = " +
//		  commandToWrapper + ", viewCommand = " + viewCommand;
//	}
//
//  public void afterCommit()
//  {
//  }
}

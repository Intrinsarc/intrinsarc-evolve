package com.hopstepjump.idraw.foundation;

import com.hopstepjump.gem.*;

/**
 *
 * (c) Andrew McVeigh 10-Aug-02
 *
 */
public interface CommandManagerFacet extends Facet
{
	public void executeCommandAndUpdateViews(Command command);
	public Command executeForPreview(Command command, boolean sendDiagramChangesToListeners, boolean returnCommand);
	public void undoForPreview(Command command);
  public void tellDiagramsToSendChanges();  

  public boolean canRedo();
  public String getRedoPresentationName();
	public void redo();
	
  public boolean canUndo();
	public String getUndoPresentationName();
	public void undo();

	public int getCommandIndex();
  public int getCommandMax();
  public void clearCommandHistory();
  public void switchListener(CommandManagerListenerFacet facet);
}

package com.hopstepjump.idraw.figurefacilities.selectionbase;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 04-Oct-02
 *
 */
public final class SetLocationCommand extends AbstractCommand
{
  private FigureReference locatable;
  private Object memento;
  private String packageUUID;

  public SetLocationCommand(FigureReference locatable, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
		this.locatable = locatable;
  }

	/**
	 * @see com.hopstepjump.jumble.repository.SubjectAffectingCommand#executeAfterRepositoryCheckPoint()
	 */
	public void execute(boolean isTop)
	{
	  LocationFacet locatable = getLocatable();
	  if (locatable != null)
	    memento = locatable.setLocation();
	}

	/**
	 * @see com.hopstepjump.jumble.repository.SubjectAffectingCommand#unExecuteAfterRepositoryUndo()
	 */
	public void unExecute()
	{
	  if (memento != null)
	    getLocatable().unSetLocation(memento);
	}

  private LocationFacet getLocatable()
  {
    return (LocationFacet) GlobalDiagramRegistry.registry.retrieveFigure(locatable).getDynamicFacet(LocationFacet.class);
  }
}


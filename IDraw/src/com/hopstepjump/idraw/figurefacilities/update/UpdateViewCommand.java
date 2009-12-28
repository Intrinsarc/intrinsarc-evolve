package com.hopstepjump.idraw.figurefacilities.update;

import com.hopstepjump.idraw.foundation.*;

public class UpdateViewCommand extends AbstractCommand
{
  private FigureReference updateable;
  private Object memento;

  public UpdateViewCommand(FigureReference updateable, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.updateable = updateable;
  }

  /**
   * @see com.hopstepjump.jumble.repository.SubjectAffectingCommand#executeAfterRepositoryCheckPoint()
   */
  public void execute(boolean isTop)
  {
    UpdateViewFacet toUpdate= getUpdateable();
    if (toUpdate != null)
      memento = toUpdate.updateViewAfterSubjectChanged(isTop);
  }

  /**
   * @see com.hopstepjump.jumble.repository.SubjectAffectingCommand#unExecuteAfterRepositoryUndo()
   */
  public void unExecute()
  {
    if (memento != null && getUpdateable() != null)
      getUpdateable().unUpdateViewAfterSubjectChanged(memento);
  }

  private UpdateViewFacet getUpdateable()
  {
    return (UpdateViewFacet) GlobalDiagramRegistry.registry.retrieveFigure(updateable).getDynamicFacet(UpdateViewFacet.class);
  }
}


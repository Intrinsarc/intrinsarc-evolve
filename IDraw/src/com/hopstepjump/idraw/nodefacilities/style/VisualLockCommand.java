package com.hopstepjump.idraw.nodefacilities.style;

import com.hopstepjump.idraw.foundation.*;

public class VisualLockCommand extends AbstractCommand
{
  private FigureReference lockable;
  private boolean lock;
  private Object memento;

  public VisualLockCommand(FigureReference lockable, boolean lock, String executeDescription, String unExecuteDescription)
  {
  	super(executeDescription, unExecuteDescription);
  	this.lockable = lockable;
  	this.lock = lock;
  }

  public void execute(boolean isTop)
  {
    memento = getVisualLockFacet().lock(lock);
  }

  public void unExecute()
  {
    getVisualLockFacet().unLock(memento);
  }

  private VisualLockFacet getVisualLockFacet()
  {
    return (VisualLockFacet) GlobalDiagramRegistry.registry.retrieveFigure(lockable).getDynamicFacet(VisualLockFacet.class);
  }
}
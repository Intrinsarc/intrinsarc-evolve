package com.hopstepjump.idraw.nodefacilities.previewsupport;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;


public final class MoveNodeFigureCommand extends AbstractCommand
{
  private FigureReference moveable;
  private UDimension offset;
  private Object memento;

  public MoveNodeFigureCommand(FigureReference moveable, UDimension offset, String executeDescription, String unExecuteDescription) {
		super(executeDescription, unExecuteDescription);
    this.moveable = moveable;
    this.offset = offset;
  }

  public void execute(boolean isTop)
  {
    MoveFacet moveable = getMoveable();
    memento = moveable.move(offset);
  }

  public void unExecute()
  {
    getMoveable().unMove(memento);
  }

  private MoveFacet getMoveable()
  {
    // necessary cast
    return (MoveFacet) GlobalDiagramRegistry.registry.retrieveFigure(moveable).getDynamicFacet(MoveFacet.class);
  }
}
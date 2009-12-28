package com.hopstepjump.idraw.nodefacilities.resize;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;


public final class NodeResizeCommand extends AbstractCommand
{
  private FigureReference resizeable;
  private Object memento;
  private UBounds resizedBounds;

  public NodeResizeCommand(FigureReference resizeable, UBounds resizedBounds, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.resizeable = resizeable;
    this.resizedBounds = resizedBounds;
  }

  public void execute(boolean isTop)
  {
    memento = getResizeable().resize(resizedBounds);
  }

  public void unExecute()
  {
    getResizeable().unResize(memento);
  }

  private ResizeFacet getResizeable()
  {
    return (ResizeFacet) GlobalDiagramRegistry.registry.retrieveFigure(resizeable).getDynamicFacet(ResizeFacet.class);
  }
}
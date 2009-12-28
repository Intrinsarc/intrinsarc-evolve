package com.hopstepjump.idraw.nodefacilities.style;

import java.awt.*;

import com.hopstepjump.idraw.foundation.*;

public final class ChangeColorCommand extends AbstractCommand
{
  private FigureReference stylable;
  private Object memento;
  private Color fill;

  public ChangeColorCommand(FigureReference stylable, Color fill, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.stylable = stylable;
    this.fill = fill;
  }

  public void execute(boolean isTop)
  {
    memento = getStylable().setFill(fill);
  }

  public void unExecute()
  {
    getStylable().unSetFill(memento);
  }

  private StylableFacet getStylable()
  {
    return (StylableFacet) GlobalDiagramRegistry.registry.retrieveFigure(stylable).getDynamicFacet(StylableFacet.class);
  }
}
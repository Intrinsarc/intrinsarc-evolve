package com.hopstepjump.idraw.nodefacilities.style;

import java.awt.*;

import com.hopstepjump.idraw.foundation.*;

public final class ChangeColorCommand extends AbstractCommand
{
  private FigureReference stylable;
  private Color fill;

  public ChangeColorCommand(FigureReference stylable, Color fill, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.stylable = stylable;
    this.fill = fill;
  }

  public void execute(boolean isTop)
  {
    getStylable().setFill(fill);
  }

  public void unExecute()
  {
  }

  private StylableFacet getStylable()
  {
    return (StylableFacet) GlobalDiagramRegistry.registry.retrieveFigure(stylable).getDynamicFacet(StylableFacet.class);
  }
}
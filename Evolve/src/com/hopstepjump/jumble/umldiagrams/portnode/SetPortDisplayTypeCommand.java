package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.idraw.foundation.*;

public class SetPortDisplayTypeCommand extends AbstractCommand
{
  private FigureReference toElide;
  private Object memento;
  private int displayType;

  public SetPortDisplayTypeCommand(FigureReference toElide, int displayType, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.toElide = toElide;
    this.displayType = displayType;
  }

  public void execute(boolean isTop)
  {
    memento = getElided().setDisplayType(displayType);
  }

  public void unExecute()
  {
    getElided().unSetDisplayType(memento);
  }

  private PortDisplayTypeFacet getElided()
  {
    return (PortDisplayTypeFacet) GlobalDiagramRegistry.registry.retrieveFigure(toElide).getDynamicFacet(PortDisplayTypeFacet.class);
  }
}
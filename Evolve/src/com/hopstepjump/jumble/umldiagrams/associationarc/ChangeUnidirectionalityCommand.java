package com.hopstepjump.jumble.umldiagrams.associationarc;

import com.hopstepjump.idraw.foundation.*;

public class ChangeUnidirectionalityCommand extends AbstractCommand
{
  private FigureReference changeable;
  private Object memento;
  private boolean unidirectional;

  public ChangeUnidirectionalityCommand(FigureReference changeable, boolean unidirectional, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.changeable = changeable;
    this.unidirectional = unidirectional;
  }

  public void execute(boolean isTop)
  {
    memento = getChangeable().setUnidirectionality(unidirectional);
  }

  public void unExecute()
  {
    getChangeable().unSetUnidirectionality(memento);
  }

  private ChangeUnidirectionalityFacet getChangeable()
  {
    return (ChangeUnidirectionalityFacet) GlobalDiagramRegistry.registry.retrieveFigure(changeable).getDynamicFacet(ChangeUnidirectionalityFacet.class);
  }

}

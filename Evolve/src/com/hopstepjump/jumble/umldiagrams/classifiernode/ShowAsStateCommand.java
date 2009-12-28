package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.idraw.foundation.*;

public class ShowAsStateCommand extends AbstractCommand
{
  private FigureReference stateable;
  private Object memento;
  private boolean showAsState;

  public ShowAsStateCommand(FigureReference stateable, boolean displayAsIcon, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.stateable = stateable;
    this.showAsState = displayAsIcon;
  }

  public void execute(boolean isTop)
  {
    memento = getIconifiable().showAsState(showAsState);
  }

  public void unExecute()
  {
    getIconifiable().unShowAsState(memento);
  }

  private ShowAsStateFacet getIconifiable()
  {
    return (ShowAsStateFacet) GlobalDiagramRegistry.registry.retrieveFigure(stateable).getDynamicFacet(ShowAsStateFacet.class);
  }
}

package com.hopstepjump.jumble.umldiagrams.base;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 22-Aug-02
 *
 */
public final class DisplayAsIconCommand extends AbstractCommand
{
  private FigureReference iconifiable;
  private Object memento;
  private boolean displayAsIcon;

  public DisplayAsIconCommand(FigureReference iconifiable, boolean displayAsIcon, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.iconifiable = iconifiable;
    this.displayAsIcon = displayAsIcon;
  }

  public void execute(boolean isTop)
  {
    memento = getIconifiable().displayAsIcon(displayAsIcon);
  }

  public void unExecute()
  {
    getIconifiable().unDisplayAsIcon(memento);
  }

  private DisplayAsIconFacet getIconifiable()
  {
    return (DisplayAsIconFacet) GlobalDiagramRegistry.registry.retrieveFigure(iconifiable).getDynamicFacet(DisplayAsIconFacet.class);
  }
}

package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 22-Aug-02
 *
 */
public final class NamespaceChangeDisplayTypeCommand extends AbstractCommand
{
  private FigureReference iconifiable;
  private Object memento;
  private NamespaceDisplayType displayAsIcon;

  public NamespaceChangeDisplayTypeCommand(FigureReference iconifiable, NamespaceDisplayType displayAsIcon, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.iconifiable = iconifiable;
    this.displayAsIcon = displayAsIcon;
  }

  public void execute(boolean isTop)
  {
    memento = getIconifiable().setDisplayType(displayAsIcon);
  }

  public void unExecute()
  {
    getIconifiable().unSetDisplayType(memento);
  }

  private NamespaceDisplayTypeFacet getIconifiable()
  {
    return (NamespaceDisplayTypeFacet) GlobalDiagramRegistry.registry.retrieveFigure(iconifiable).getDynamicFacet(NamespaceDisplayTypeFacet.class);
  }
}

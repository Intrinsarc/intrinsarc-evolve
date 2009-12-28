package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public class HideContentsCommand extends AbstractCommand
{
  private FigureReference hideable;
  private boolean hideContents;
  private Object memento;

  public HideContentsCommand(FigureReference hideable, boolean hideContents, String executeDescription, String unExecuteDescription)
  {
  	super(executeDescription, unExecuteDescription);
    this.hideable = hideable;
    this.hideContents = hideContents;
  }

  public void execute(boolean isTop)
  {
    memento = getHideContentsFacet().hideContents(hideContents);
  }

  public void unExecute()
  {
    getHideContentsFacet().unHideContents(memento);
  }

  private HideContentsFacet getHideContentsFacet()
  {
    return (HideContentsFacet) GlobalDiagramRegistry.registry.retrieveFigure(hideable).getDynamicFacet(HideContentsFacet.class);
  }
}

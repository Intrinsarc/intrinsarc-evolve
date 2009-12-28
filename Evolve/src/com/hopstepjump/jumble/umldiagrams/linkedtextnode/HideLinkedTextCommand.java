package com.hopstepjump.jumble.umldiagrams.linkedtextnode;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public class HideLinkedTextCommand extends AbstractCommand
{
  private FigureReference hideable;
  private boolean hide;
  private Object memento;

  public HideLinkedTextCommand(FigureReference hideable, boolean hide, String executeDescription, String unExecuteDescription)
  {
  	super(executeDescription, unExecuteDescription);
    this.hideable = hideable;
    this.hide = hide;
  }

  public void execute(boolean isTop)
  {
    memento = getHideable().hideLinkedText(hide);
  }

  public void unExecute()
  {
    getHideable().unHideLinkedText(memento);
  }

  private HideLinkedTextFacet getHideable()
  {
    return (HideLinkedTextFacet) GlobalDiagramRegistry.registry.retrieveFigure(hideable).getDynamicFacet(HideLinkedTextFacet.class);
  }
}

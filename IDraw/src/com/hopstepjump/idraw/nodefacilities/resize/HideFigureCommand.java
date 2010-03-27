package com.hopstepjump.idraw.nodefacilities.resize;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author andrew
 */
public class HideFigureCommand extends AbstractCommand
{
  private FigureReference hideable;
  private boolean hide;

  public HideFigureCommand(
      FigureReference hidingFigure,
      FigureReference hideable,
      boolean hide,
      String executeDescription,
      String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.hideable = hideable;
    this.hide = hide;
  }

  public void execute(boolean isTop)
  {
    getHideable().setShowing(!hide);
  }

  public void unExecute()
  {
    getHideable().setShowing(hide);
  }

  private FigureFacet getHideable()
  {
    return GlobalDiagramRegistry.registry.retrieveFigure(hideable);
  }
}
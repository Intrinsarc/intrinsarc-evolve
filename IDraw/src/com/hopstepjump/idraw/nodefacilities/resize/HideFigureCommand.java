package com.hopstepjump.idraw.nodefacilities.resize;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author andrew
 */
public class HideFigureCommand extends AbstractCommand
{
  private FigureReference hideable;
  private FigureReference hidingFigure;
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
    this.hidingFigure = hidingFigure;
    this.hide = hide;
  }

  public void execute(boolean isTop)
  {
    getHideable().setShowing(!hide);
    if (hidingFigure != null)
      getHidingFigure().getDiagram().adjusted(getHidingFigure());
  }

  public void unExecute()
  {
    getHideable().setShowing(hide);
    if (hidingFigure != null)
      getHidingFigure().getDiagram().adjusted(getHidingFigure());
  }

  private FigureFacet getHidingFigure()
  {
    return GlobalDiagramRegistry.registry.retrieveFigure(hidingFigure);
  }

  private FigureFacet getHideable()
  {
    return GlobalDiagramRegistry.registry.retrieveFigure(hideable);
  }
}
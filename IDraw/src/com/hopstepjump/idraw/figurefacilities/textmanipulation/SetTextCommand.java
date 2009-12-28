package com.hopstepjump.idraw.figurefacilities.textmanipulation;

import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;


public final class SetTextCommand extends AbstractCommand{
  private FigureReference textable;
  private Object memento;
  private String text;
  private Object listSelection;
  private boolean unsuppress;

  public SetTextCommand(FigureReference textable, String text, Object listSelection, boolean unsuppress, String executeDescription, String unExecuteDescription)
  {
		super(executeDescription, unExecuteDescription);
    this.textable = textable;
    this.text = text;
    this.listSelection = listSelection;
    this.unsuppress = unsuppress;
  }

  public void execute(boolean isTop)
  {
    memento = getTextable().setText(text, listSelection, unsuppress, memento);
    GlobalDiagramRegistry.registry.retrieveFigure(textable).adjusted();
  }

  public void unExecute()
  {
    getTextable().unSetText(memento);
  }

  private TextableFacet getTextable()
  {
    // necessary cast
    return (TextableFacet) GlobalDiagramRegistry.registry.retrieveFigure(textable).getDynamicFacet(TextableFacet.class);
  }
}
package com.hopstepjump.idraw.nodefacilities.resize;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;


public final class NodeAutoSizeCommand extends AbstractCommand
{
  private FigureReference autoSizeable;
  private boolean autoSized;
  private Object memento;

  public NodeAutoSizeCommand(FigureReference resizeable, boolean autoSized, String executeDescription, String unExecuteDescription)
  {
  	super(executeDescription, unExecuteDescription);
    this.autoSizeable = resizeable;
    this.autoSized = autoSized;
  }

  public void execute(boolean isTop)
  {
    memento = getAutoSizeable().autoSize(autoSized);
  }

  public void unExecute()
  {
    getAutoSizeable().unAutoSize(memento);
  }

  private AutoSizedFacet getAutoSizeable()
  {
    return (AutoSizedFacet) GlobalDiagramRegistry.registry.retrieveFigure(autoSizeable).getDynamicFacet(AutoSizedFacet.class);
  }
}

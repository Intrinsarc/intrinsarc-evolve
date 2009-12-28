package com.hopstepjump.jumble.freeform.image;

import com.hopstepjump.idraw.foundation.*;

public class LoadImageCommand extends AbstractCommand
{
  private FigureReference imagable;
  private Object memento;
  private byte[] imageData;
  private String type;
  
  public LoadImageCommand(FigureReference imagable, String type, byte[] imageData, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.imagable = imagable;
    this.imageData = imageData;
    this.type = type;
  }
  
  public void execute(boolean isTop)
  {
    memento = getStylable().setImage(type, imageData);
  }
  
  public void unExecute()
  {
    getStylable().unSetImage(memento);
  }
  
  private ImagableFacet getStylable()
  {
    return (ImagableFacet) GlobalDiagramRegistry.registry.retrieveFigure(imagable).getDynamicFacet(ImagableFacet.class);
  }
}
package com.hopstepjump.jumble.umldiagrams.associationarc;

import com.hopstepjump.idraw.foundation.*;

public class ChangeAssociationTypeCommand extends AbstractCommand
{
  private FigureReference changeable;
  private Object memento;
  private int associationType;

  public ChangeAssociationTypeCommand(FigureReference changeable, int associationType, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.changeable = changeable;
    this.associationType = associationType;
  }

  public void execute(boolean isTop)
  {
    memento = getChangeable().setAssociationType(associationType);
  }

  public void unExecute()
  {
    getChangeable().unSetAssociationType(memento);
  }

  private ChangeAssociationTypeFacet getChangeable()
  {
    return (ChangeAssociationTypeFacet) GlobalDiagramRegistry.registry.retrieveFigure(changeable).getDynamicFacet(ChangeAssociationTypeFacet.class);
  }

}

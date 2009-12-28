package com.hopstepjump.idraw.arcfacilities.previewsupport;

import com.hopstepjump.idraw.foundation.*;


public final class MoveArcFigureCommand extends AbstractCommand
{
  private FigureReference moveable;
  private ReferenceCalculatedArcPoints memento;
  private ReferenceCalculatedArcPoints referencePoints;

  public MoveArcFigureCommand(FigureReference moveable, ReferenceCalculatedArcPoints referencePointsToCopy, String executeDescription, String unExecuteDescription)
  {
  	super(executeDescription, unExecuteDescription);
    this.moveable = moveable;
    referencePoints = referencePointsToCopy;
  }

  public void execute(boolean isTop)
  {
    DiagramFacet diagram = getDiagram();
    // bad cast
    memento = ((CalculatedArcPoints) getLinking().move(new CalculatedArcPoints(referencePoints))).getReferenceCalculatedArcPoints(diagram);
  }

  public void unExecute()
  {
    getLinking().unMove(new CalculatedArcPoints(memento));
  }

  private DiagramFacet getDiagram()
  {
    return GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(moveable.getDiagramReference());
  }

  private LinkingFacet getLinking()
  {
    return GlobalDiagramRegistry.registry.retrieveFigure(moveable).getLinkingFacet();
  }
}
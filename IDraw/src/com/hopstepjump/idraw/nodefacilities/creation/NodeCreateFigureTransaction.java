package com.hopstepjump.idraw.nodefacilities.creation;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;


public class NodeCreateFigureTransaction implements TransactionFacet
{
  public static void create(
  		DiagramFacet diagram,
  		Object useSubject,
  		FigureReference reference,
  		FigureReference containingReference,
      NodeCreateFacet factory,
      UPoint location,
      PersistentProperties properties,
      Object relatedSubject)
  {
    // if we have no subject, we must create one
    if (useSubject == null)
      useSubject = factory.createNewSubject(null, diagram, containingReference, relatedSubject, properties);
      
    // make the figure
    factory.createFigure(
        useSubject,
        diagram,
        reference.getId(),
        location,
        properties);

    // resize to ensure that any children are correctly located and all internals are correctly positioned
    FigureFacet figure = diagram.retrieveFigure(reference.getId());
    ResizingFiguresFacet resizings = new ResizingFiguresGem(null, diagram).getResizingFiguresFacet();
    resizings.markForResizing(figure);
    resizings.setFocusBounds(figure.getRecalculatedFullBoundsForDiagramResize(false));
    resizings.end();
  }
}
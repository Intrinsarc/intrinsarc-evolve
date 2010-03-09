package com.hopstepjump.idraw.nodefacilities.creation;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;


public final class NodeCreateFigureCommand extends AbstractCommand
{
  private Object useSubject;
  private Object createdSubject;
  private FigureReference reference;
  private FigureReference containingReference;
  private String factoryName;
  private Object memento;
  private UPoint location;
  private PersistentProperties properties;
  private boolean previewingOnly;
  private Object relatedSubject;

  public NodeCreateFigureCommand(Object useSubject, FigureReference reference, FigureReference containingReference,
      NodeCreateFacet factory, UPoint location, boolean previewingOnly, PersistentProperties properties,
      Object relatedSubject,
      String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.useSubject = useSubject;
    this.reference = reference;
    this.containingReference = containingReference;
    this.factoryName = factory.getRecreatorName();
    this.location = location;
    this.previewingOnly = previewingOnly;
    this.properties = properties;
    this.relatedSubject = relatedSubject;
    execute(true);
  }

  public void execute(boolean isTop)
  {
    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(reference.getDiagramReference());

    // if we have no subject, we must create one
    if (useSubject == null && !previewingOnly)
      createdSubject = retrieveFactory().createNewSubject(createdSubject, diagram, containingReference, relatedSubject, properties);
      
    // no id for non-subject related items
    memento = retrieveFactory().createFigure(
        getSubject(),
        diagram,
        reference.getId(),
        location,
        properties);

    // resize to ensure that any children are correctly located
    // resize now -- so that all internals will be correctly resized also
    FigureFacet figure = diagram.retrieveFigure(reference.getId());
    ResizingFiguresFacet resizings = new ResizingFiguresGem(null, diagram).getResizingFiguresFacet();
    resizings.markForResizing(figure);
    resizings.setFocusBounds(figure.getRecalculatedFullBoundsForDiagramResize(false));
    resizings.end("", "").execute(false);
  }
  
  public Object getSubject()
  {
    return useSubject != null ? useSubject : createdSubject;
  }

  public void unExecute()
  {
    retrieveFactory().unCreateFigure(memento);
    if (createdSubject != null)
      retrieveFactory().uncreateNewSubject(createdSubject);
  }

  private NodeCreateFacet retrieveFactory()
  {
    return (NodeCreateFacet) PersistentFigureRecreatorRegistry.registry.retrieveRecreator(factoryName);
  }
}
package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

public class AddPortCommand extends AbstractCommand
{
  private FigureReference addable;
  private FigureReference featureReference;
  private String factoryName;
  private Object memento;
  private PersistentProperties properties;
  private Object useSubject;
  private Object relatedSubject;
  private UPoint location;

  public AddPortCommand(FigureReference addable, FigureReference featureReference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, String executeDescription, String unExecuteDescription, UPoint location)
  {
    super(executeDescription, unExecuteDescription);
    this.addable = addable;
    this.featureReference = featureReference;
    this.factoryName = factory.getRecreatorName();
    this.properties = properties;
    this.useSubject = useSubject;
    this.relatedSubject = relatedSubject;
    this.location = location;
  }

  public void execute(boolean isTop)
  {
    memento = getAddable().addPort(
        memento,
        featureReference,
        retrieveFactory(),
        properties,
        useSubject,
        relatedSubject,
        location);
  }

  public void unExecute()
  {
    getAddable().unAddPort(memento);
  }
  
  private NodeCreateFacet retrieveFactory()
  {
    return (NodeCreateFacet) PersistentFigureRecreatorRegistry.registry.retrieveRecreator(factoryName);
  }

  private PortAddFacet getAddable()
  {
    return (PortAddFacet) GlobalDiagramRegistry.registry.retrieveFigure(addable).getDynamicFacet(PortAddFacet.class);
  }
}

package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

public class ReplacePortCommand extends AbstractCommand
{
  private FigureReference replaceable;
  private FigureReference replacement;
  private FigureReference replaced;
  private String factoryName;
  private Object memento;
  private PersistentProperties properties;
  private Object useSubject;
  private Object relatedSubject;

  public ReplacePortCommand(FigureReference addable, FigureReference featureReference, FigureReference replaced, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, String executeDescription, String unExecuteDescription)
  {
    super(executeDescription, unExecuteDescription);
    this.replaceable = addable;
    this.replacement = featureReference;
    this.replaced = replaced;
    this.factoryName = factory.getRecreatorName();
    this.properties = properties;
    this.useSubject = useSubject;
    this.relatedSubject = relatedSubject;
  }

  public void execute(boolean isTop)
  {
    memento = getAddable().replacePort(
        memento,
        replacement,
        replaced,
        retrieveFactory(),
        properties,
        useSubject,
        relatedSubject);
  }

  public void unExecute()
  {
    getAddable().unReplacePort(memento);
  }
  
  private NodeCreateFacet retrieveFactory()
  {
    return (NodeCreateFacet) PersistentFigureRecreatorRegistry.registry.retrieveRecreator(factoryName);
  }

  private PortAddFacet getAddable()
  {
    return (PortAddFacet) GlobalDiagramRegistry.registry.retrieveFigure(replaceable).getDynamicFacet(PortAddFacet.class);
  }
}

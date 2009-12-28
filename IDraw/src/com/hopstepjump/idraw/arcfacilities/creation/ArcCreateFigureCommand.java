package com.hopstepjump.idraw.arcfacilities.creation;

import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;


public final class ArcCreateFigureCommand extends AbstractCommand
{
	private Object useSubject;
	private Object createdSubject;
	private FigureReference reference;
	private String factoryName;
	private ReferenceCalculatedArcPoints referencePoints;
	private Object memento;
  private PersistentProperties properties;

	public ArcCreateFigureCommand(Object useSubject, FigureReference reference, ArcCreateFacet factory, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties, String executeDescription, String unExecuteDescription)
	{
		super(executeDescription, unExecuteDescription);
		this.useSubject = useSubject;
		this.reference = reference;
		this.factoryName = factory.getRecreatorName();
		this.referencePoints = referencePoints;
    this.properties = properties;
	}

	public void execute(boolean isTop)
	{
		DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(reference.getDiagramReference());
		
		// possibly create a subject if we haven't been given one
		if (useSubject == null)
			createdSubject = retrieveFactory().createNewSubject(createdSubject, diagram, referencePoints, properties);
		
		memento = retrieveFactory().create(getSubject(), diagram, reference.getId(), referencePoints, properties);
	}

  public Object getSubject()
  {
    return useSubject != null ? useSubject : retrieveFactory().extractRawSubject(createdSubject);
  }
  
	public void unExecute()
	{
		retrieveFactory().unCreate(memento);
    if (createdSubject != null)
      retrieveFactory().uncreateNewSubject(createdSubject);
	}
  
	private ArcCreateFacet retrieveFactory()
	{
		return (ArcCreateFacet) PersistentFigureRecreatorRegistry.registry.retrieveRecreator(factoryName);
	}
}
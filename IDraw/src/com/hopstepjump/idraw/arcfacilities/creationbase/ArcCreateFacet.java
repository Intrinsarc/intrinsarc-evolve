package com.hopstepjump.idraw.arcfacilities.creationbase;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;


public interface ArcCreateFacet extends PersistentFigureRecreatorFacet, ArcAcceptanceFacet
{
  public String getFigureName();

  public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties);
  public Object extractRawSubject(Object previouslyCreated);
  
  public Object create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties);
  public void initialiseExtraProperties(PersistentProperties properties);
	public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator);
}
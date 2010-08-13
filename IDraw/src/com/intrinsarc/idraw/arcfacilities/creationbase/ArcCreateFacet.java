package com.intrinsarc.idraw.arcfacilities.creationbase;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;


public interface ArcCreateFacet extends PersistentFigureRecreatorFacet, ArcAcceptanceFacet
{
  public String getFigureName();

  public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties);
  public void create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties);
  public void initialiseExtraProperties(PersistentProperties properties);
	public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator);
}
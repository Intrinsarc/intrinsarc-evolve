package com.intrinsarc.idraw.nodefacilities.creationbase;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;


public interface NodeCreateFacet extends PersistentFigureRecreatorFacet
{
  public String getFigureName();
  public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties);  
  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties);
  public void initialiseExtraProperties(PersistentProperties properties);
}
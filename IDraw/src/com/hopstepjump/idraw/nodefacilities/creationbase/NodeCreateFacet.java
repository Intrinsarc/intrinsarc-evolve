package com.hopstepjump.idraw.nodefacilities.creationbase;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;


public interface NodeCreateFacet extends PersistentFigureRecreatorFacet
{
  public String getFigureName();

  public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties);
  public void uncreateNewSubject(Object previouslyCreated);
  
  public Object createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties);
  public void unCreateFigure(Object memento);
  public void initialiseExtraProperties(PersistentProperties properties);
}
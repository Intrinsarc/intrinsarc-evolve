package com.intrinsarc.idraw.diagramsupport;

import java.util.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

public interface BasicDiagramRecreatorFacet extends Facet
{
  public DiagramFacet recreateAndRegisterDiagram(
  		DiagramReference reference,
  		Map<DiagramReference, DiagramFacet> diagrams,
  		DiagramPostProcessor postProcessor) throws DiagramRecreationException;
  
  public DiagramFacet recreateAndRegisterDiagram(
  		DiagramReference reference,
  		Map<DiagramReference, DiagramFacet> diagrams,
  		DiagramFacet chainedSource,
  		Object perspective,
  		DiagramPostProcessor postProcessor) throws DiagramRecreationException;
  
  public PersistentDiagram retrievePersistentDiagram(
  		DiagramReference diagramReference) throws DiagramRecreationException;

	public void setSaveDetails(DiagramFacet diagram);
}

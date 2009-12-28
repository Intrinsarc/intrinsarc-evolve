package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

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
}

package com.intrinsarc.idraw.foundation;

import java.util.*;

import com.intrinsarc.gem.*;

public interface DiagramRegistryFacet extends Facet
{
	public List<DiagramFacet> getDiagrams();
  public DiagramFacet retrieveOrMakeDiagram(DiagramReference reference);
  public DiagramFacet retrieveOrMakeDiagram(DiagramReference reference, boolean forceToNotModified);
  public DiagramFacet retrieveOrMakeDiagram(DiagramReference reference, DiagramFacet source, Object perspective, DiagramPostProcessor postProcessor);
  public DiagramFacet retrieveOrMakeClipboardDiagram(DiagramReference reference);
  
  public FigureFacet retrieveFigure(FigureReference figureReference);
  public List<DiagramFacet> refreshAllDiagrams();
  public void enforceMaxUnmodifiedUnviewedDiagramsLimit();
  
  public void reset();
}
package com.hopstepjump.jumble.umldiagrams.baseline;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;

public class RegionCreatorGem implements Gem
{
  public static final String NAME = "Region";
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  
  public RegionCreatorGem()
  {
  }

  public NodeCreateFacet getNodeCreateFacet()
  {
    return nodeCreateFacet;
  }

  private class NodeCreateFacetImpl implements NodeCreateFacet
  {
    public String getFigureName()
    {
      return NAME;
    }
  
    public Object createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
      BaselineNodeGem baselineGem = new BaselineNodeGem(NAME, true);
      basicGem.connectBasicNodeAppearanceFacet(baselineGem.getBasicNodeAppearanceFacet());
      baselineGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      
      diagram.add(basicGem.getBasicNodeFigureFacet());
      return new FigureReference(diagram, figureId);
    }
    
    public void unCreateFigure(Object memento)
    {
      FigureReference figureReference = (FigureReference) memento;
      DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(figureReference.getDiagramReference());
      FigureFacet figure = GlobalDiagramRegistry.registry.retrieveFigure(figureReference);
      diagram.remove(figure);
    }
    
    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return NAME;
    }

    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
     */
    public FigureFacet createFigure(
      DiagramFacet diagram, PersistentFigure figure)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
      BaselineNodeGem baselineGem = new BaselineNodeGem(NAME, true, figure.getProperties());
      basicGem.connectBasicNodeAppearanceFacet(baselineGem.getBasicNodeAppearanceFacet());
      baselineGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      return basicGem.getBasicNodeFigureFacet();      
    }

    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      return null;
    }

    public void uncreateNewSubject(Object previouslyCreated)
    {
    }

		public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
  }
}
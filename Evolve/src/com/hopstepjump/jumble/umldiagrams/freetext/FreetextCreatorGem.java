package com.hopstepjump.jumble.umldiagrams.freetext;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;


public final class FreetextCreatorGem implements Gem
{
  public static final String NAME = "Freetext";
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  
  public FreetextCreatorGem()
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
      return FreetextNodeGem.FIGURE_NAME;
    }
   
    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, false, true);
      FreetextNodeGem freetextNodeGem = new FreetextNodeGem();
      basicGem.connectBasicNodeAppearanceFacet(freetextNodeGem.getBasicNodeAppearanceFacet());
      freetextNodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      
      diagram.add(basicGem.getBasicNodeFigureFacet());
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
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, true);
      FreetextNodeGem freetextNodeGem = new FreetextNodeGem(figure.getProperties());
      basicGem.connectBasicNodeAppearanceFacet(freetextNodeGem.getBasicNodeAppearanceFacet());
      freetextNodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      return basicGem.getBasicNodeFigureFacet();
    }

    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      return null;
    }

		public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
  }

}
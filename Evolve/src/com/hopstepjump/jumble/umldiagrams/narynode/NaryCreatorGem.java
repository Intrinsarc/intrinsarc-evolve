package com.hopstepjump.jumble.umldiagrams.narynode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;


public final class NaryCreatorGem implements Gem
{
	public static final String NAME = "Nary";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	
  public NaryCreatorGem()
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
	    return NaryNodeGem.FIGURE_NAME;
	  }
	
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
	  	NaryNodeGem naryGem = new NaryNodeGem();
			basicGem.connectBasicNodeAppearanceFacet(naryGem.getBasicNodeAppearanceFacet());
			naryGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			
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
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
	  	NaryNodeGem naryGem = new NaryNodeGem(figure.getProperties());
			basicGem.connectBasicNodeAppearanceFacet(naryGem.getBasicNodeAppearanceFacet());
			naryGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			return basicGem.getBasicNodeFigureFacet();			
		}

    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      return null;
    }

		public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
	}
}
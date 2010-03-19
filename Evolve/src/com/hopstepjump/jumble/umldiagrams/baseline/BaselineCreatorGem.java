package com.hopstepjump.jumble.umldiagrams.baseline;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;


public final class BaselineCreatorGem implements Gem
{
	public static final String NAME = "Baseline";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	
  public BaselineCreatorGem()
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
	
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
	  	BaselineNodeGem baselineGem = new BaselineNodeGem(NAME, false);
			basicGem.connectBasicNodeAppearanceFacet(baselineGem.getBasicNodeAppearanceFacet());
			baselineGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			
	    diagram.add(basicGem.getBasicNodeFigureFacet());
	  }
    
    public Object getSubject(Object memento)
    {
      return null;
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
	  	BaselineNodeGem baselineGem = new BaselineNodeGem(NAME, false, figure.getProperties());
			basicGem.connectBasicNodeAppearanceFacet(baselineGem.getBasicNodeAppearanceFacet());
			baselineGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
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
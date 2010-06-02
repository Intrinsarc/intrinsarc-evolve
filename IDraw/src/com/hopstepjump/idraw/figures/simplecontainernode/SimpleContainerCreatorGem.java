package com.hopstepjump.idraw.figures.simplecontainernode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;

/**
 *
 * (c) Andrew McVeigh 03-Sep-02
 *
 */
public class SimpleContainerCreatorGem implements Gem
{
  public static final String RECREATOR_NAME = "SimpleContainer";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	
  public SimpleContainerCreatorGem()
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
	    return SimpleContainerGem.FIGURE_NAME;
	  }

	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, new UPoint(0,0), true, false);
			SimpleContainerGem simpleGem = new SimpleContainerGem(new UDimension(0,0), new UDimension(4,4), true);
			basicGem.connectBasicNodeAppearanceFacet(simpleGem.getBasicNodeAppearanceFacet());
			
			// a simple container cannot act as an anchor
			basicGem.connectAnchorFacet(null);
			simpleGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeContainerFacet(simpleGem.getBasicNodeContainerFacet());
	  }
    
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getName()
		 */
		public String getRecreatorName()
		{
			return RECREATOR_NAME;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
			SimpleContainerGem simpleGem = new SimpleContainerGem(figure);
			basicGem.connectBasicNodeAppearanceFacet(simpleGem.getBasicNodeAppearanceFacet());
			
			// a simple container cannot act as an anchor
			basicGem.connectAnchorFacet(null);
			simpleGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeContainerFacet(simpleGem.getBasicNodeContainerFacet());
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
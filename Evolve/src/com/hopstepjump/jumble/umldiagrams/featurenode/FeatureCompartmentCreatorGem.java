package com.hopstepjump.jumble.umldiagrams.featurenode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;

/**
 *
 * (c) Andrew McVeigh 04-Sep-02
 *
 */
public class FeatureCompartmentCreatorGem implements Gem
{
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private int featureType;
	private String recreatorName;
	private boolean contentsCanMoveContainers;
	
  public FeatureCompartmentCreatorGem(int featureType, String recreatorName, boolean contentsCanMoveContainers)
  {
  	this.featureType = featureType;
  	this.recreatorName = recreatorName;
  	this.contentsCanMoveContainers = contentsCanMoveContainers;
  }

	public NodeCreateFacet getNodeCreateFacet()
	{
		return nodeCreateFacet;
	}

	private class NodeCreateFacetImpl implements NodeCreateFacet
	{
	  public String getFigureName()
	  {
	    return FeatureCompartmentGem.FIGURE_NAME;
	  }

	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	    BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, new UPoint(0,0), true, false);
			FeatureCompartmentGem featureGem = new FeatureCompartmentGem(featureType);
			featureGem.setContentsCanMoveContainers(contentsCanMoveContainers);
			basicGem.connectBasicNodeAppearanceFacet(featureGem.getBasicNodeAppearanceFacet());
			// a compartment cannot act as an anchor
			basicGem.connectAnchorFacet(null);
			featureGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeContainerFacet(featureGem.getBasicNodeContainerFacet());
	  }
	
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
		  return recreatorName;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	    BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
			FeatureCompartmentGem featureGem = new FeatureCompartmentGem(figure.getProperties(), featureType);
			featureGem.setContentsCanMoveContainers(contentsCanMoveContainers);
			basicGem.connectBasicNodeAppearanceFacet(featureGem.getBasicNodeAppearanceFacet());
			// a compartment cannot act as an anchor
			basicGem.connectAnchorFacet(null);
			featureGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeContainerFacet(featureGem.getBasicNodeContainerFacet());
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

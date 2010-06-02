package com.hopstepjump.jumble.umldiagrams.lifeline;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public final class LifelineCreatorGem implements Gem
{
	public static final String NAME = "Lifeline";
	private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();

  public LifelineCreatorGem()
  {
  }

	public ArcCreateFacet getArcCreateFacet()
	{
		return arcCreateFacet;
	}
	
	private class ArcCreateFacetImpl implements ArcCreateFacet
	{
	  public String getFigureName()
	  {
	    return LifelineArcAppearanceFacetImpl.FIGURE_NAME;
	  }
	
	  public void create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
	  {
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
	    gem.connectBasicArcAppearanceFacet(new LifelineArcAppearanceFacetImpl());	    																					 
	    diagram.add(gem.getFigureFacet());
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
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figure);
	    gem.connectBasicArcAppearanceFacet(new LifelineArcAppearanceFacetImpl());
	    return gem.getFigureFacet();
		}

    public void initialiseExtraProperties(PersistentProperties properties)
    {
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
      return true;
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
    	return null;
    }

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{
		}
	}
}
package com.intrinsarc.evolve.freeform.measurebox;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class MeasureBoxCreatorGem implements Gem
{
	public static final String NAME = "MeasureBox";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	
	public MeasureBoxCreatorGem()
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
	    return MeasureBoxGem.FIGURE_NAME;
	  }
	 
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, false, true);
	  	MeasureBoxGem measureBoxGem = new MeasureBoxGem();
			basicGem.connectBasicNodeAppearanceFacet(measureBoxGem.getBasicNodeAppearanceFacet());
			measureBoxGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			
	    diagram.add(basicGem.getBasicNodeFigureFacet());
	  }
    
		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}

		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(
			DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, true);
	  	MeasureBoxGem measureBoxGem = new MeasureBoxGem(figure);
			basicGem.connectBasicNodeAppearanceFacet(measureBoxGem.getBasicNodeAppearanceFacet());
			measureBoxGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
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

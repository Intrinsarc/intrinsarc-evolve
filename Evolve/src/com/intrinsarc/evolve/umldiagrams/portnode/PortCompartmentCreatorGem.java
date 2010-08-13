package com.intrinsarc.evolve.umldiagrams.portnode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;

/**
 * @author andrew
 */
public class PortCompartmentCreatorGem implements Gem
{
  public static final String RECREATOR_NAME = "PortCompartment";
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private boolean isPortForClasses;

  public PortCompartmentCreatorGem(boolean isPortForClasses)
  {
    this.isPortForClasses = isPortForClasses;
  }

  public NodeCreateFacet getNodeCreateFacet()
  {
    return nodeCreateFacet;
  }

  private class NodeCreateFacetImpl implements NodeCreateFacet
  {
    public String getFigureName()
    {
      return PortCompartmentGem.FIGURE_NAME;
    }

    public void createFigure(
        Object subject, DiagramFacet diagram, String figureId,
        UPoint location, PersistentProperties properties)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, new UPoint(0, 0), true, false);
      PortCompartmentGem portGem = new PortCompartmentGem(isPortForClasses);
      basicGem.connectBasicNodeAppearanceFacet(portGem.getBasicNodeAppearanceFacet());

      // a port container cannot act as an anchor
      basicGem.connectAnchorFacet(null);
      portGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      basicGem.connectBasicNodeContainerFacet(portGem.getBasicNodeContainerFacet());
    }

    /**
     * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return RECREATOR_NAME;
    }

    /**
     * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference,
     *      PersistentProperties)
     */
    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
      PortCompartmentGem portGem = new PortCompartmentGem(figure);
      basicGem.connectBasicNodeAppearanceFacet(portGem.getBasicNodeAppearanceFacet());

      // a port container cannot act as an anchor
      basicGem.connectAnchorFacet(null);
      portGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      basicGem.connectBasicNodeContainerFacet(portGem.getBasicNodeContainerFacet());
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
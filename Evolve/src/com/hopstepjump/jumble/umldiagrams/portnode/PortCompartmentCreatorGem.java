package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;

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
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return RECREATOR_NAME;
    }

    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference,
     *      PersistentProperties)
     */
    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
      PortCompartmentGem portGem = new PortCompartmentGem(figure.getProperties());
      basicGem.connectBasicNodeAppearanceFacet(portGem.getBasicNodeAppearanceFacet());

      // a port container cannot act as an anchor
      basicGem.connectAnchorFacet(null);
      portGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      basicGem.connectBasicNodeContainerFacet(portGem.getBasicNodeContainerFacet());
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
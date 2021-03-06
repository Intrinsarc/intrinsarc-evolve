package com.intrinsarc.evolve.umldiagrams.portnode;

import org.eclipse.uml2.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;

public class PortInstanceCreatorGem implements Gem
{
  public static final String NAME = "PortInstance";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  // extra text is used to show the port remapping details
  private String extraText;
	
	public PortInstanceCreatorGem()
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
	    return PortNodeGem.FIGURE_NAME;
	  }
	 
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, false, true);

      PersistentProperties actualProperties = new PersistentProperties(properties);
      initialiseExtraProperties(actualProperties);

	  	UDimension def = new UDimension(0, -PortNodeGem.CREATION_EXTENT.getHeight());
      PortNodeGem portNodeGem = new PortNodeGem(
      		diagram,
      		location,
      		false,
      		new PersistentFigure(figureId, null, subject, actualProperties),
      		true,
  				properties != null && properties.retrieve(">suppressLinkedText", false).asBoolean(),
  				properties != null ? properties.retrieve(">linkedTextOffset", def).asUDimension() : def);
			basicGem.connectBasicNodeAppearanceFacet(portNodeGem.getBasicNodeAppearanceFacet());
			portNodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	    portNodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	    basicGem.connectClipboardCommandsFacet(portNodeGem.getClipboardCommandsFacet());
			basicGem.connectBasicNodeContainerFacet(portNodeGem.getBasicNodeContainerFacet());
			
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
	  	PortNodeGem portNodeGem = new PortNodeGem(figure, true);
			basicGem.connectBasicNodeAppearanceFacet(portNodeGem.getBasicNodeAppearanceFacet());
			portNodeGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	    basicGem.connectClipboardCommandsFacet(portNodeGem.getClipboardCommandsFacet());
			basicGem.connectBasicNodeContainerFacet(portNodeGem.getBasicNodeContainerFacet());

			return basicGem.getBasicNodeFigureFacet();
		}

    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
	    return relatedSubject;
	  }
	
		public void initialiseExtraProperties(PersistentProperties properties)
		{
      if (extraText != null)
        properties.addIfNotThere(new PersistentProperty("extraText", (String) null));
		}
	}

  public void setExtraText(String extraText)
  {
    this.extraText = extraText;
  }
}

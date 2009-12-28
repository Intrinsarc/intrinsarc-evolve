package com.hopstepjump.jumble.umldiagrams.linkedtextnode;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;

/**
 * @author andrew
 */
public class LinkedTextCreatorGem
{
  public static final String RECREATOR_NAME = "Linked Text";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	
	public LinkedTextCreatorGem()
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
	    return LinkedTextGem.FIGURE_NAME;
	  }
	 
	  public Object createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	    // never create on its own
	    return null;
	  }
	
	  public void unCreateFigure(Object memento)
	  {
	    FigureReference figureReference = (FigureReference) memento;
	    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(figureReference.getDiagramReference());
	    FigureFacet figure = GlobalDiagramRegistry.registry.retrieveFigure(figureReference);
	    diagram.remove(figure);
	  }
	  
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return RECREATOR_NAME;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(
			DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, true);
	  	LinkedTextGem linkedTextGem = new LinkedTextGem(figure.getProperties());
			basicGem.connectBasicNodeAppearanceFacet(linkedTextGem.getBasicNodeAppearanceFacet());
			linkedTextGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      // decorate the clipboard facet
      basicGem.connectClipboardCommandsFacet(linkedTextGem.getClipboardCommandsFacet());

      return basicGem.getBasicNodeFigureFacet();
		}

    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      return null;
    }

    public void uncreateNewSubject(Object previouslyCreated)
    {
    }
    
		public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
	}
}

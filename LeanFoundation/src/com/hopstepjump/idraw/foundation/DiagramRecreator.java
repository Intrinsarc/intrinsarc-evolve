package com.hopstepjump.idraw.foundation;

import java.util.*;

import com.hopstepjump.idraw.foundation.persistence.*;

public class DiagramRecreator
{
  public static void recreateFigures(
      DiagramFacet diagram, 
      Collection<PersistentFigure> persistentFigures,
      Map<String, FigureFacet> /* id -> FigureFacet */ existingFigures,
      boolean add,
      RecreatorListener listener)
  {
    for (PersistentFigure pfig : persistentFigures)
    {
    	if (add)
    	{
	    	PersistentFigureRecreatorFacet recreatorFacet = PersistentFigureRecreatorRegistry.registry.retrieveRecreator(pfig.getRecreator());
	  		FigureFacet figure = recreatorFacet.createFigure(diagram, pfig);
	  		Validator.validateFigure(figure);
	  
	  		String id = figure.getId();
	  	  existingFigures.put(id, figure);
	  	  if (listener != null)
	  	  	listener.addedFigure(figure);
    	}
    	else
    	{
      	FigureFacet figure = diagram.retrieveFigure(pfig.getId());
      	figure.acceptPersistentFigure(pfig);
    	}
    }
    
    // set up any links or containment hierarchies
    for (PersistentFigure pfig : persistentFigures)
    {
    	FigureFacet figure = retrieveFigure(existingFigures, pfig.getId());
    	
    	// set up possible anchors for a link
    	if (pfig.getAnchor1Id() != null)
    	{
  			FigureFacet anchor1 = retrieveFigure(existingFigures, pfig.getAnchor1Id());
  			FigureFacet anchor2 = retrieveFigure(existingFigures, pfig.getAnchor2Id());
    		figure.getLinkingFacet().persistence_setAnchors(anchor1.getAnchorFacet(), anchor2.getAnchorFacet());
    		if (listener != null)
    			listener.addedLink(figure.getLinkingFacet());
    	}
    	
  		// set up possible containments
  		if (pfig.getContainerId() != null)
  		{
  			FigureFacet containerFigure = retrieveFigure(existingFigures, pfig.getContainerId());
  			if (containerFigure != null)
  			{
  				containerFigure.getContainerFacet().persistence_addContained(figure);
  				if (listener != null)
  					listener.addedToContainer(containerFigure.getContainerFacet());
  			}
  		}
  
    	// set up possible links for an anchor, via the links stored in the figure
  		for (String linkId : pfig.getLinkIds())
    	{
    		FigureFacet link = retrieveFigure(existingFigures, linkId);
    		// NOTE: a link may be null if it is not included in the persistent graph
    		// for example, when a copy is done, and the link is not chosen    		
    		if (link != null)
  	  		figure.getAnchorFacet().persistence_addLinked(link.getLinkingFacet());
    	}
    	
    	// set up possible containment via the contents stored in the figure
  		for (String contentId : pfig.getContentIds())
    	{
    		FigureFacet content = retrieveFigure(existingFigures, contentId);
  			figure.getContainerFacet().persistence_addContained(content);
    	}
    }
  }

  private static FigureFacet retrieveFigure(Map<String, FigureFacet> existingFigures, String id)
  {
    return existingFigures.get(id);
  }
}

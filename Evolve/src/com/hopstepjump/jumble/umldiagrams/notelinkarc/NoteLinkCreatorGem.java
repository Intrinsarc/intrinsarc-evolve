package com.hopstepjump.jumble.umldiagrams.notelinkarc;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public final class NoteLinkCreatorGem implements Gem
{
	public static final String NAME = "NoteLink";
	private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();

  public NoteLinkCreatorGem()
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
	    return NoteLinkArcAppearanceFacetImpl.figureName;
	  }
	
	  public Object create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
	  {
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
	    gem.connectBasicArcAppearanceFacet(new NoteLinkArcAppearanceFacetImpl());
	    																					 
	    diagram.add(gem.getFigureFacet());
	    return new FigureReference(diagram, figureId);
	  }
	
	  public void unCreate(Object memento)
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
			return NAME;
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figure);
	    gem.connectBasicArcAppearanceFacet(new NoteLinkArcAppearanceFacetImpl());
	    return gem.getFigureFacet();
		}

    public void initialiseExtraProperties(PersistentProperties properties)
    {
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
      return true;
    }
    
    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
    	return null;
    }

    public void uncreateNewSubject(Object previouslyCreated)
    {
    }

		public void aboutToMakeCommand(ToolCoordinatorFacet coordinator)
		{
		}
		
		public Object extractRawSubject(Object previouslyCreated)
		{
			return previouslyCreated;
		}
	}
}
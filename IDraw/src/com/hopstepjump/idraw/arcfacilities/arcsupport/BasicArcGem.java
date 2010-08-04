package com.hopstepjump.idraw.arcfacilities.arcsupport;


import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public final class BasicArcGem implements Gem
{
	BasicArcState state;
	
  public BasicArcGem(PersistentFigureRecreatorFacet recreatorFacet, DiagramFacet diagram, String figureId, CalculatedArcPoints calculatedPoints)
  {
    String id = figureId;
  	state = new BasicArcState();
  	state.recreatorFacet = recreatorFacet;
  	state.showing = true;
  	state.calculatedPoints = calculatedPoints;
  	state.diagram = diagram;
  	state.diagramReference = diagram.getDiagramReference();
  	state.id = id;
  	state.linked = new HashSet<LinkingFacet>();
  	useDefaultFacets();
  }
  
  public BasicArcGem(PersistentFigureRecreatorFacet recreatorFacet, DiagramFacet diagram, String figureId, CalculatedArcPoints calculatedPoints, boolean curved)
  {
    String id = figureId;
  	state = new BasicArcState();
  	state.recreatorFacet = recreatorFacet;
  	state.showing = true;
  	state.calculatedPoints = calculatedPoints;
  	state.diagram = diagram;
  	state.diagramReference = diagram.getDiagramReference();
  	state.id = id;
  	state.linked = new HashSet<LinkingFacet>();
  	state.curved = curved;
  	useDefaultFacets();
  }
  
  public BasicArcGem(PersistentFigureRecreatorFacet recreatorFacet, DiagramFacet diagram, String figureId, BasicArcState state, CalculatedArcPoints calculatedPoints)
  {
  	this.state = state;
  	state.recreatorFacet = recreatorFacet;
  	state.showing = true;
  	state.calculatedPoints = calculatedPoints;
  	state.diagram = diagram;
  	state.diagramReference = diagram.getDiagramReference();
  	state.id = figureId;
  	state.linked = new HashSet<LinkingFacet>();
		// need to set up facets manually
  }
  
  public BasicArcGem(PersistentFigureRecreatorFacet recreatorFacet, DiagramFacet diagram, PersistentFigure figure)
  {
  	this.state = new BasicArcState();
  	state.recreatorFacet = recreatorFacet;
  	state.diagram = diagram;
  	state.diagramReference = diagram.getDiagramReference();
  	state.id = figure.getId();
  	state.linked = new HashSet<LinkingFacet>();
  	useDefaultFacets(figure.getProperties());
  }
  
  private void useDefaultFacets()
  {
		// must be done in the correct order
  	state.figureFacet = new BasicArcFigureGem(state).getFigureFacet();
  	state.anchorFacet = new BasicArcAnchorFacetImpl(state);
  	state.linkingFacet = new BasicArcLinkingFacetImpl(state);
  }  
  
  private void useDefaultFacets(PersistentProperties properties)
  {
		// must be done in the correct order
  	state.figureFacet = new BasicArcFigureGem(properties, state).getFigureFacet();
  	state.anchorFacet = new BasicArcAnchorFacetImpl(state);
  	state.linkingFacet = new BasicArcLinkingFacetImpl(properties, state);
  }  
  
  public FigureFacet getFigureFacet()
  {
  	return state.figureFacet;
  }
  
  public void connectBasicArcAppearanceFacet(BasicArcAppearanceFacet appearanceFacet)
  {
  	state.appearanceFacet = appearanceFacet;
  }

  public void connectAdvancedArcFacet(AdvancedArcFacet advancedFacet)
  {
  	state.advancedFacet = advancedFacet;
  }

	public void connectFigureFacet(FigureFacet figureFacet)
	{
		state.figureFacet = figureFacet;
	}
	
	public void connectAnchorFacet(AnchorFacet anchorFacet)
	{
		state.anchorFacet = anchorFacet;
	}
	
	public void connectLinkingFacet(LinkingFacet linkingFacet)
	{
		state.linkingFacet = linkingFacet;
	}
	
	public void connectContainerFacet(ContainerFacet containerFacet)
	{
	  state.containerFacet = containerFacet;
	}
	
  public void connectClipboardActionsFacet(ClipboardActionsFacet clipboardCommandsFacet)
  {
    state.clipboardCommandsFacet = clipboardCommandsFacet;
  }
}

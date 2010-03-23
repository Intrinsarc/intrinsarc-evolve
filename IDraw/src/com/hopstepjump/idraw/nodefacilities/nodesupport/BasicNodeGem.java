package com.hopstepjump.idraw.nodefacilities.nodesupport;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public final class BasicNodeGem implements Gem
{
	private BasicNodeState state;
	
  public BasicNodeGem(String recreatorName, DiagramFacet diagram, String figureId, UPoint location, boolean autoSized, boolean useGlobalLayer)
  {
  	state = new BasicNodeState();
  	state.recreatorName = recreatorName;
    state.id = figureId;
    state.diagram = diagram;
		state.diagramReference = diagram.getDiagramReference();

    state.pt = location;
		useDefaultFacets(autoSized, null, useGlobalLayer);    
  }

  public BasicNodeGem(String recreatorName, DiagramFacet diagram, String figureId, UPoint location, boolean autoSized, String containedName, boolean useGlobalLayer)
  {
  	state = new BasicNodeState();
  	state.recreatorName = recreatorName;
    state.id = figureId;
    state.diagram = diagram;
		state.diagramReference = diagram.getDiagramReference();

    state.pt = location;
		useDefaultFacets(autoSized, containedName, useGlobalLayer);    
  }

  public BasicNodeGem(String recreatorName, DiagramFacet diagram, String figureId, BasicNodeState state, UPoint location, boolean autoSized)
  {
  	this.state = state;
  	state.recreatorName = recreatorName;
    state.id = figureId;
    state.diagram = diagram;
    state.pt = location;
		state.diagramReference = diagram.getDiagramReference();
    // facets must be manually connected
  }

  public BasicNodeGem(String recreatorName, DiagramFacet diagram, PersistentFigure figure, boolean useGlobalLayer)
  {
  	state = new BasicNodeState();
  	state.recreatorName = recreatorName;
    state.id = figure.getId();
    state.diagram = diagram;
		state.diagramReference = state.diagram.getDiagramReference();
		useDefaultFacets(figure, true, useGlobalLayer);
  }

	private void useDefaultFacets(boolean autoSized, String containedName, boolean useGlobalLayer)
	{
		connectAnchorFacet(new BasicNodeAnchorFacetImpl(state));
		connectContainedFacet(new BasicNodeContainedFacetImpl(state, containedName));
		connectBasicNodeFigureFacet(new BasicNodeFigureFacetImpl(state, useGlobalLayer));
		connectBasicNodeAutoSizedFacet(new BasicNodeAutoSizedFacetImpl(state, autoSized));
    connectClipboardFacet(new BasicNodeClipboardFacetImpl(state));
	}
	
	private void useDefaultFacets(PersistentFigure pfig, boolean autoSized, boolean useGlobalLayer)
	{
		connectAnchorFacet(new BasicNodeAnchorFacetImpl(state));
		PersistentProperties properties = pfig.getProperties();
		connectContainedFacet(new BasicNodeContainedFacetImpl(properties, state));
		connectBasicNodeFigureFacet(new BasicNodeFigureFacetImpl(pfig, state, useGlobalLayer));
		connectBasicNodeAutoSizedFacet(new BasicNodeAutoSizedFacetImpl(properties, state));
    connectClipboardFacet(new BasicNodeClipboardFacetImpl(state));
	}
	
	public void connectAnchorFacet(AnchorFacet anchorFacet)
	{
		state.anchorFacet = anchorFacet;
	}
	
	public void connectBasicNodeAutoSizedFacet(BasicNodeAutoSizedFacet autoSizedFacet)
	{
		state.autoSizedFacet = autoSizedFacet;
		state.figureFacet.registerDynamicFacet(autoSizedFacet, AutoSizedFacet.class);
	}
	
	public void connectContainedFacet(ContainedFacet containedFacet)
	{
		state.containedFacet = containedFacet;
	}
	
	public void connectBasicNodeContainerFacet(BasicNodeContainerFacet containerFacet)
	{
		state.containerFacet = containerFacet;
	}
	
	public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
	{
		state.figureFacet = figureFacet;
	}
	
	public void connectBasicNodeAppearanceFacet(BasicNodeAppearanceFacet appearanceFacet)
	{
		state.appearanceFacet = appearanceFacet;
		// resized extent may have been set by a persistent recreation
		if (state.resizedExtent == null)
			state.resizedExtent = appearanceFacet.getCreationExtent();
	}
  
	public BasicNodeFigureFacet getBasicNodeFigureFacet()
	{
		return state.figureFacet;
	}

  /**
   * @param clipboardFacet
   */
  public void connectClipboardFacet(ClipboardFacet clipboardFacet)
  {
    state.clipboardFacet = clipboardFacet;
  }

  public void connectClipboardCommandsFacet(ClipboardCommandsFacet clipboardCommandsFacet)
  {
    state.clipboardCommandsFacet = clipboardCommandsFacet;
  }

  /**
   * @return
   */
  public ClipboardFacet getClipboardFacet()
  {
    return state.clipboardFacet;
  }
}
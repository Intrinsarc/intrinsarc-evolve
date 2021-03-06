package com.intrinsarc.idraw.nodefacilities.nodesupport;

import java.util.*;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.resizebase.*;

import edu.umd.cs.jazz.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public final class BasicNodeFigureFacetImpl implements BasicNodeFigureFacet, MoveFacet, ResizeFacet
{
	private BasicNodeState state;
	private Map<Class, Facet> dynamicFacets = new HashMap<Class, Facet>();
	private boolean useGlobalLayer;
	
	public BasicNodeFigureFacetImpl(BasicNodeState state, boolean useGlobalLayer)
	{
		this.state = state;
		this.useGlobalLayer = useGlobalLayer;
		
		registerDynamicFacet(this, MoveFacet.class);
		registerDynamicFacet(this, ResizeFacet.class);
	}
	
	public BasicNodeFigureFacetImpl(PersistentFigure pfig, BasicNodeState state, boolean useGlobalLayer)
	{
		this(state, useGlobalLayer);
		PersistentProperties properties = pfig.getProperties();
		state.pt = properties.retrieve("pt").asUPoint();
		state.resizedExtent = properties.retrieve("dim", new UDimension(0,0)).asUDimension();
		state.showing = properties.retrieve("show", true).asBoolean();
	}

	public void acceptPersistentFigure(PersistentFigure pfig)
	{
		PersistentProperties properties = pfig.getProperties();
		state.pt = properties.retrieve("pt").asUPoint();
		state.resizedExtent = properties.retrieve("dim", new UDimension(0,0)).asUDimension();
		state.showing = properties.retrieve("show", true).asBoolean();
		state.appearanceFacet.acceptPersistentFigure(pfig);
	}

  public String getId()
  {
    return state.id;
  }

	public int getType()
	{
		return state.containerFacet != null ? CONTAINER_TYPE : NODE_TYPE;
	}

  public void setShowing(boolean showing)
	{
		state.showing = showing;

		if (state.containerFacet != null)
			state.containerFacet.setShowingForChildren(showing);

		// adjust each linking figure, so that it will be asked again if it is showing
		for (Iterator iter = state.linked.iterator(); iter.hasNext();)
		{
			LinkingFacet linkingFacet = (LinkingFacet) iter.next();
			linkingFacet.getFigureFacet().setShowing(showing);
		}
	}

	public boolean isShowing()
	{
		return state.showing;
	}

  public UBounds getFullBounds()
  {
    return new UBounds(state.pt, state.resizedExtent);
  }

  public UBounds getFullBoundsForContainment()
  {
    return state.appearanceFacet.getFullBoundsForContainment();
  }

  public UBounds getRecalculatedFullBoundsForDiagramResize(boolean diagramResize)
  {
    return state.appearanceFacet.getRecalculatedFullBounds(diagramResize);
  }

  public FigureFacet getActualFigureForSelection()
  {
  	return state.appearanceFacet.getActualFigureForSelection();
  }

  public void cleanUp()
  {
  	ContainerFacet container = state.containedFacet.getContainer();
  	if (container != null)
  		container.removeContents(new ContainedFacet[]{state.containedFacet});
  	
  	// remove any contained and linking objects
  	if (state.containerFacet != null)
  		state.containerFacet.cleanUp();
  	if (state.anchorFacet != null)
  		state.anchorFacet.cleanUp();
  }
  
  public void addPreviewToCache(DiagramFacet diagram, PreviewCacheFacet previewFigures, UPoint start, boolean addMyself)
  {
  	addPreviewToCache(diagram, previewFigures, start, addMyself, true);
  }

  private PreviewFacet addPreviewToCache(DiagramFacet diagram, PreviewCacheFacet previewFigures, UPoint start, boolean addMyself, boolean addLinked)
  {
		PreviewFacet previewFigure = null;
		
    // very important to add this first if we are asked
    boolean isFocus = previewFigures.nodeIsInFocusOfPreview(this);
    if (addMyself)
    {
      // make the preview figure
      previewFigure = state.appearanceFacet.makeNodePreviewFigure(previewFigures, diagram, start, isFocus);

      // need to add this early, as others may ask for it
      previewFigures.addPreviewToCache(this, previewFigure);
    }

    // add each linked
    if (isFocus && addLinked)
    {
      Iterator iter = state.linked.iterator();
      while (iter.hasNext())
      {
        LinkingFacet linking = (LinkingFacet) iter.next();
        previewFigures.getCachedPreviewOrMakeOne(linking.getFigureFacet());
      }
    }

    // add each contained, if this is a container
    ContainerFacet asContainer = getContainerFacet();
    if (asContainer != null)
    	asContainer.addChildPreviewsToCache(previewFigures);
    return previewFigure;
  }

  public PreviewFacet getSinglePreview(DiagramFacet diagram)
  {
  	ResizingFiguresFacet resizings = new ResizingFiguresGem(null, diagram).getResizingFiguresFacet();
  	return addPreviewToCache(diagram, resizings, new UPoint(0,0), true, true);
  }

	public ContainedFacet getContainedFacet()
	{
		return state.containedFacet;
	}

	public ContainerFacet getContainerFacet()
	{
		return state.containerFacet; // optional
	}

	public AnchorFacet getAnchorFacet()
	{
		return state.anchorFacet;
	}

	public LinkingFacet getLinkingFacet()
	{
		return null;
	}
	
	public ZNode formView()
	{
		return state.appearanceFacet.formView();
	}
	
  public String getFigureName()
  {
  	return state.appearanceFacet.getFigureName();
  }

  public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
  {
  	return state.appearanceFacet.getSelectionManipulators(coordinator, diagramView, favoured, firstSelected, allowTYPE0Manipulators);
  }
  
	public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
	{
		return state.appearanceFacet.makeContextMenu(diagramView, coordinator);
	}

	public boolean isAutoSized()
	{
		return state.autoSizedFacet.isAutoSized();
	}

  public Object move(UDimension offset)
  {
    state.pt = state.pt.add(offset);
    return null;
  }

  public Object resize(UBounds bounds)
  {
    state.pt = bounds.getTopLeftPoint();
    state.resizedExtent = bounds.getDimension();
    return null;
  }

	/**
	 * @see com.intrinsarc.jumble.foundation.FigureFacet#getDynamicFacet(Class)
	 */
	public <T extends Facet> T getDynamicFacet(Class<T> facetClass)
	{
		T facet = (T) dynamicFacets.get(facetClass);
		if (facet == null)
			throw new FacetNotFoundException("Cannot find facet corresponding to " + facetClass + " in " + state.appearanceFacet);
		return facet;
	}

	/**
	 * @see com.intrinsarc.jumble.foundation.FigureFacet#getDiagram()
	 */
	public DiagramFacet getDiagram()
	{
		return state.diagram;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer buffer = new StringBuffer("BasicNode figure facet, with an appearance facet of " + state.appearanceFacet + "::: ");
		Iterator iter = state.linked.iterator();
		while (iter.hasNext())
		{
			LinkingFacet facet = (LinkingFacet) iter.next();
			buffer.append("link = " + facet + ":::");
		}
		return buffer.toString();
	}

	/**
	 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeFigureFacet#registerFacet(Facet, Class)
	 */
	public void registerDynamicFacet(Facet facet, Class facetInterface)
	{
		dynamicFacets.put(facetInterface, facet);
	}

	/**
	 * @see com.intrinsarc.jumble.foundation.FigureFacet#hasDynamicFacet(Class)
	 */
	public boolean hasDynamicFacet(Class facetClass)
	{
		return dynamicFacets.containsKey(facetClass);
	}
		
	/**
	 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeFigureFacet#getBasicNodeAutoSizedFacet()
	 */
	public BasicNodeAutoSizedFacet getBasicNodeAutoSizedFacet()
	{
		return state.autoSizedFacet;
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof FigureFacet))
			return false;
			
		FigureFacet other = (FigureFacet) obj;
		return other.getId().equals(state.id);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		if (state == null || state.id == null)
			return super.hashCode();
		return state.id.hashCode();
	}
	
	/**
	 * @see com.intrinsarc.idraw.foundation.FigureFacet#getDiagramReference()
	 */
	public FigureReference getFigureReference()
	{
		return new FigureReference(state.diagramReference, state.id);
	}
	
	/**
	 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeFigureFacet#makeAndExecuteResizingTransaction()
	 */
	public void performResizingTransaction(UBounds newBounds)
	{
		// this method should only be used inside a command's execution
		ResizingFiguresGem gem = new ResizingFiguresGem(null, state.diagram);
		ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
		facet.markForResizing(this);
		facet.setFocusBounds(newBounds);
		facet.end();
	}

	public PersistentFigure makePersistentFigure()
	{
		PersistentFigure pFigure = new PersistentFigure(state.id, state.recreatorName);
    pFigure.setSubject(getSubject());
		PersistentProperties properties = pFigure.getProperties();
		properties.add(new PersistentProperty("pt", state.pt));
		properties.add(new PersistentProperty("dim", state.resizedExtent, new UDimension(0,0)));
		
		// add the autosized property for this figure
		properties.add(new PersistentProperty("auto", state.autoSizedFacet.isAutoSized(), true));
		properties.add(new PersistentProperty("show", state.showing, true));

		// possibly add a contained name and id
		if (state.containedFacet != null)
		{
			properties.add(new PersistentProperty("contName", state.containedFacet.persistence_getContainedName()));
			ContainerFacet container = state.containedFacet.getContainer();
			if (container != null)
				pFigure.setContainerId(container.getFigureFacet().getId());
		}
		
		// possibly store container information
		if (state.containerFacet != null)
			for (Iterator iter = state.containerFacet.getContents(); iter.hasNext();)
			{
				FigureFacet child = (FigureFacet) iter.next();
				pFigure.getContentIds().add(child.getId());
			}

		// possibly store link information
		if (state.linked != null)
			for (Iterator iter = state.linked.iterator(); iter.hasNext();)
			{
				LinkingFacet link = (LinkingFacet) iter.next();
				pFigure.getLinkIds().add(link.getFigureFacet().getId());
			}

		state.appearanceFacet.addToPersistentProperties(properties);
		return pFigure;
	}

	/**
	 * @see com.intrinsarc.idraw.foundation.FigureFacet#subjectChanged()
	 */
	public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
	{
		state.appearanceFacet.updateViewAfterSubjectChanged(pass);
	}

	/**
	 * @see com.intrinsarc.idraw.foundation.FigureFacet#getMiddleButtonCommand()
	 */
	public void middleButtonPressed(ToolCoordinatorFacet coordinator)
	{
		state.appearanceFacet.middleButtonPressed(coordinator);
	}

	/**
	 * @see com.intrinsarc.idraw.foundation.FigureFacet#getSubject()
	 */
	public Object getSubject()
	{
		return state.appearanceFacet.getSubject();
	}

	/**
	 * @see com.intrinsarc.idraw.foundation.FigureFacet#hasSubjectBeenDeleted()
	 */
	public boolean hasSubjectBeenDeleted()
	{
		return state.appearanceFacet.hasSubjectBeenDeleted();
	}

  public boolean useGlobalLayer()
  {
    return useGlobalLayer;
  }

  public ClipboardFacet getClipboardFacet()
  {
    return state.clipboardFacet;
  }

  public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
  {
    // pass on to a possible appearance facet
    if (state.anchorFacet != null)
      state.appearanceFacet.produceEffect(coordinator, effect, parameters);
  }

  public void formDeleteTransaction()
  {
    // form a complete set of all figures to delete, including children
    ChosenFiguresFacet chosenFigures = new ChosenFiguresFacet()
    {
      public boolean isChosen(FigureFacet figure)
      {
        return figure == BasicNodeFigureFacetImpl.this;
      }
    };
    List<FigureFacet> toDelete = new ArrayList<FigureFacet>();
    toDelete.add(this);

    // remove the views with deleted subjects
    Set<String> deletionFigureIds = DeleteFromDiagramTransaction.getFigureIdsIncludedInDelete(toDelete, chosenFigures, false);
    DeleteFromDiagramTransaction.delete(getDiagram(), deletionFigureIds, false);
  }

  public ClipboardActionsFacet getClipboardCommandsFacet()
  {
    return state.clipboardCommandsFacet;
  }

  public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
  {
  	// if this is on a chained diagram, then it is readonly
  	if (state.diagram.getSource() != null)
  		return true;
  	
    return state.appearanceFacet.isSubjectReadOnlyInDiagramContext(kill);
  }

	public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
	{
		return state.appearanceFacet.getToolClassification(point, diagramView, coordinator);
	}
}


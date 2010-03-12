package com.hopstepjump.idraw.arcfacilities.arcsupport;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.adjust.*;
import com.hopstepjump.idraw.arcfacilities.previewsupport.*;
import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public final class BasicArcFigureGem implements Gem
{
	private BasicArcState state;
	private Map<Class<?>, Facet> dynamicFacets = new HashMap<Class<?>, Facet>();
	private ClipboardFacet clipFacet = new ClipboardFacetImpl();
	private CurvableFacet curvableFacet = new CurvableFacetImpl();
  private FigureFacet figureFacet = new BasicArcFigureFacetImpl();
	
	public BasicArcFigureGem(BasicArcState state)
	{
		this.state = state;
		figureFacet.registerDynamicFacet(curvableFacet, CurvableFacet.class);
    state.curvableFacet = curvableFacet;
	}
  
  public FigureFacet getFigureFacet()
  {
    return figureFacet;
  }
  
  public CurvableFacet getCurvableFacet()
  {
    return curvableFacet;
  }
  
  public BasicArcFigureGem(PersistentProperties properties, BasicArcState state)
  {
    this(state);
    state.showing = properties.retrieve("show", true).asBoolean();
    state.curved = properties.retrieve("curved", false).asBoolean();

    UPoint virtualPoint = properties.retrieve("vtl").asUPoint();

    UPoint[] allPoints = properties.retrieve("pts").asUPointArray();
    state.calculatedPoints = new CalculatedArcPoints(null, null, virtualPoint, Arrays.asList(allPoints));
  }

  private class CurvableFacetImpl implements CurvableFacet
  {
    public Object curve(boolean curved)
    {
      makeStyle(curved);
      makeAndExecuteResizingCommand();
      return null;
    }

    public void unCurve(Object memento)
    {
    }

    private void makeStyle(boolean curved)
    {
      state.curved = curved;
      figureFacet.adjusted();
    }
    
    public boolean isCurved()
    {
      return state.curved;
    }
  }

  public void makeAndExecuteResizingCommand()
  {
    // this method should only be used inside a command's execution
    ResizingFiguresGem gem = new ResizingFiguresGem(null, state.diagram);
    ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
    facet.markForResizing(figureFacet);
    facet.setFocusBounds(figureFacet.getFullBounds());
    facet.end();
  }

  private class ClipboardFacetImpl implements ClipboardFacet
  {
    /**
     * @see com.hopstepjump.idraw.foundation.ClipboardFacet#addIncludedInDelete(Set, ChosenFiguresFacet, boolean)
     */
    public boolean addIncludedInDelete(Set<FigureFacet> figures, ChosenFiguresFacet focussedFigures, boolean includedViaParent)
    {
      // don't add this if it is there already
      if (figures.contains(figureFacet))
        return true;
      
      // we want to include this always 
      figures.add(figureFacet);

      // handle any linked items
      for (Iterator links = state.linked.iterator(); links.hasNext();)
      {
        FigureFacet link = ((LinkingFacet) links.next()).getFigureFacet();
        
        ClipboardFacet linkClipFacet = link.getClipboardFacet();
        if (linkClipFacet != null)
          linkClipFacet.addIncludedInDelete(figures, focussedFigures, false);
      }
      
      // handle any contained items
      if (state.containerFacet != null)
      {
	      for (Iterator<FigureFacet> contents = state.containerFacet.getContents(); contents.hasNext();)
	      {
	        ClipboardFacet linkClipFacet = contents.next().getClipboardFacet();
	        if (linkClipFacet != null)
	          linkClipFacet.addIncludedInDelete(figures, focussedFigures, true);
	      }
      }
      return true;
    }
    
    /**
     * @see com.hopstepjump.idraw.foundation.ClipboardFacet#addIncludedInCopy(Set, FigureChosenFacet, boolean)
     */
    public boolean addIncludedInCopy(Set<FigureFacet> figures, ChosenFiguresFacet focussedFigures, boolean includedViaParent)
    {
      // don't add this if it is there already
      if (figures.contains(figureFacet))
        return true;
        
      // if both anchors are included, then also include this
      FigureFacet anchor1 = state.calculatedPoints.getNode1().getFigureFacet();
      ClipboardFacet anchor1ClipFacet = anchor1.getClipboardFacet();
      boolean anchor1Included = anchor1ClipFacet == null ? false :
                                  anchor1ClipFacet.addIncludedInCopy(figures, focussedFigures, false);
      
      FigureFacet anchor2 = state.calculatedPoints.getNode2().getFigureFacet();
      ClipboardFacet anchor2ClipFacet = anchor2.getClipboardFacet();
      boolean anchor2Included = anchor2ClipFacet == null ? false :
                                  anchor2ClipFacet.addIncludedInCopy(figures, focussedFigures, false);
      
      if (anchor1Included && anchor2Included)
      {
        figures.add(figureFacet);

        // handle any linked items
        for (Iterator links = state.linked.iterator(); links.hasNext();)
        {
          FigureFacet link = ((LinkingFacet) links.next()).getFigureFacet();
          
          ClipboardFacet linkClipFacet = link.getClipboardFacet();
          if (linkClipFacet != null)
            linkClipFacet.addIncludedInCopy(figures, focussedFigures, false);
        }

        // handle any contained items
        if (state.containerFacet != null)
        {
  	      for (Iterator<FigureFacet> contents = state.containerFacet.getContents(); contents.hasNext();)
  	      {
  	        ClipboardFacet linkClipFacet = contents.next().getClipboardFacet();
  	        if (linkClipFacet != null)
  	          linkClipFacet.addIncludedInDelete(figures, focussedFigures, true);
  	      }
        }

        return true;
      }

      return false;
    }

    /**
     * @see com.hopstepjump.idraw.foundation.ClipboardFacet#removeFromDiagram(FigureChosenFacet)
     */
    public Command removeFromDiagram(ChosenFiguresFacet focussedFigures)
    {
      return null;
    }
  }

  private class BasicArcFigureFacetImpl implements FigureFacet
  {    
    public ZNode formView()
    {	
      // make the thick lines
      ZGroup group = new ZGroup();
  		List<UPoint> points = state.calculatedPoints.getAllPoints();
  		
  		// get the start, second, secondLast and last points
  		UPoint all[] = points.toArray(new UPoint[0]);
  		int length = all.length;
  		UPoint start = all[0];
  		UPoint second = all[1];  // guaranteed to have at least 2 pts
  		UPoint last = all[length-1];
  		UPoint secondLast = all[length-2];
  
  		// get the line shape, with a little help from the intended path of the line
  		ZShape mainArc = CalculatedArcPoints.makeConnection(points, state.curved);
  		group.addChild(state.appearanceFacet.formAppearance(
  			mainArc, start, second, secondLast, last, state.calculatedPoints, state.curved));
  
  		// add the thick line
  		ZShape grabLine = CalculatedArcPoints.makeConnection(points, state.curved);
  		grabLine.setPenWidth(7);
  		grabLine.setPenPaint(ScreenProperties.getTransparentColor());
  		ZVisualLeaf grabLeaf = new ZVisualLeaf(grabLine);
  		grabLeaf.putClientProperty("figure", this);
  		group.addChild(grabLeaf);
  
      group.setChildrenFindable(false);
      group.setChildrenPickable(true);
      group.putClientProperty("figure", this);
  
      return group;
    }
  
  
    public String getId()
    {
      return state.id;
    }
  
  	public String getFigureName()
  	{
  		return state.appearanceFacet.getFigureName();
  	}
  
  	public int getType()
  	{
  		return ARC_TYPE;
  	}
  
  	/**
  	 * @see com.giroway.jumble.foundation.interfaces.SelectableFigure#hide()
  	 */
  	public void setShowing(boolean showing)
  	{
  		state.showing = showing;
  		
  		// adjust each linking figure, so that it will be asked again if it is showing
  		for (Iterator iter = state.linked.iterator(); iter.hasNext();)
  		{
  			LinkingFacet linkingFacet = (LinkingFacet) iter.next();
  			linkingFacet.getFigureFacet().setShowing(showing);
  		}
  		
  		// adjust any possible children also
  		if (state.containerFacet != null)
  			state.containerFacet.setShowingForChildren(showing);
  		
  		// adjust any anchors
  		state.linkingFacet.getAnchor1().getFigureFacet().adjusted();
      state.linkingFacet.getAnchor2().getFigureFacet().adjusted();
  		
  		adjusted();
  	}
  
  	/**
  	 * @see com.giroway.jumble.foundation.interfaces.SelectableFigure#isShowing()
  	 */
  	public boolean isShowing()
  	{
  		return state.showing && state.calculatedPoints.getNode1().getFigureFacet().isShowing() && state.calculatedPoints.getNode2().getFigureFacet().isShowing();
  	}
  
  	public UBounds getFullBounds()
  	{
  		return new UBounds(formView().getBounds()).adjustForJazzBounds();
  	}
  
  	public UBounds getRecalculatedFullBoundsForDiagramResize(boolean diagramResize)
  	{
  		return getFullBounds();
  	}
  
  	public UBounds getFullBoundsForContainment()
  	{
  		return getFullBounds();
  	}
  
    public Manipulators getSelectionManipulators(
        ToolCoordinatorFacet coordinator,
        DiagramViewFacet diagramView,
        boolean favoured,
        boolean firstSelected, boolean allowTYPE0Manipulators)
    {
      if (state.advancedFacet == null)
      {
	      return new Manipulators(
	      	null,
	      	new ArcAdjustManipulatorGem(coordinator, getLinkingFacet(), diagramView, state.calculatedPoints, state.curved, firstSelected).getManipulatorFacet());
      }
      else
      {
        return state.advancedFacet.getSelectionManipulators(
        		coordinator,
            diagramView,
            favoured,
            firstSelected,
            allowTYPE0Manipulators,
            state.calculatedPoints,
            state.curved);
      }
    }
  
    public FigureFacet getActualFigureForSelection()
    {
    	return this;
    }
    
  	/**
  	 * @see com.giroway.jumble.foundation.interfaces.SelectableFigure#getContextMenu()
  	 */
  	public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
  	{
  		JPopupMenu popup = new JPopupMenu();
  		
  		// add any manu entries
  		state.appearanceFacet.addToContextMenu(popup, diagramView, coordinator);
  		
  		// possibly add an entry to allow curved lines
  		if (!diagramView.getDiagram().isReadOnly())
  		{
        Utilities.addSeparator(popup);
        popup.add(makeCurvedItem(coordinator));
  		}
      
  		return popup;
  	}
  	
    /**
  	 * @return
  	 */
  	private JCheckBoxMenuItem makeCurvedItem(final ToolCoordinatorFacet coordinator)
  	{
  		JCheckBoxMenuItem curvedItem = new JCheckBoxMenuItem("Curved");
  		curvedItem.setSelected(state.curved);
  		
  		curvedItem.addActionListener(new ActionListener()
  		{
  			public void actionPerformed(ActionEvent e)
  			{
  				// toggle the autosized flag (as a command)
  				String figureName = state.figureFacet.getFigureName();
  				boolean curved = state.curved;
  				Command curveCommand =
  					new ArcCurveCommand(
  						state.figureFacet.getFigureReference(),
  						!curved,
  						(curved ? "uncurved " : "curved ") + figureName,
  						(curved ? "curved " : "uncurved ") + figureName);
  				coordinator.executeCommandAndUpdateViews(curveCommand);
  			}
  		});
  		return curvedItem;
  	}
  
  	public void addPreviewToCache(DiagramFacet diagram, PreviewCacheFacet previewFigures, UPoint start, boolean addMyself)
    {
      // if node1 and node2 are selected, then tell the moving figure to also adjust internal (and virtual points)
      boolean offsetPointsWhenMoving =
        state.calculatedPoints.getNode1().isInvolvedInMoving(previewFigures) &&
        state.calculatedPoints.getNode2().isInvolvedInMoving(previewFigures);
      ActualArcPoints actualArcPoints = new ActualArcPoints(diagram, state.calculatedPoints);
      
      // if this is a standard arc, use the default preview
      if (state.advancedFacet == null)
      {
	      BasicArcPreviewGem moving = new BasicArcPreviewGem(
	      	diagram, getLinkingFacet(), actualArcPoints, start, offsetPointsWhenMoving, state.curved);
	      PreviewFacet previewFacet = moving.getPreviewFacet();
	  
	      // important to add this as soon as possible to terminate any recursion looking for it from nodes
	      previewFigures.addPreviewToCache(this, previewFacet);
	  
	      // now we can set the outgoings
	      previewFacet.setOutgoingsToPeripheral(getLinkingFacet().hasOutgoingsToPeripheral(previewFigures));
	  
	      AnchorFacet node1 = state.calculatedPoints.getNode1();
	      AnchorFacet node2 = state.calculatedPoints.getNode2();
	      moving.getBasicArcPreviewFacet().setLinkablePreviews(
	          getPreviewFigureForToolLinkable(previewFigures, node1),
	          getPreviewFigureForToolLinkable(previewFigures, node2));
      }
      else
      {
        state.advancedFacet.addOnePreviewFigure(
            previewFigures, diagram, actualArcPoints, start, addMyself, state.curved, offsetPointsWhenMoving);
      }
      
      // add each contained, if this is a container
      ContainerFacet asContainer = state.containerFacet;
      if (asContainer != null)
      {
      	asContainer.addChildPreviewsToCache(previewFigures);
      }
    }
  
    public PreviewFacet getSinglePreview(DiagramFacet diagram)
    {
      // construct a figure, and any linkables it is attached to
      ActualArcPoints actualPoints = new ActualArcPoints(diagram, state.calculatedPoints);
      actualPoints.setNode1Preview(actualPoints.getNode1().getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
      actualPoints.setNode2Preview(actualPoints.getNode2().getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
      
      BasicArcPreviewGem gem = new BasicArcPreviewGem(
      	diagram, getLinkingFacet(), actualPoints, new UPoint(0,0), false, state.curved);
      return gem.getPreviewFacet();
    }
  
  	public ContainedFacet getContainedFacet()
  	{
  		return null;  // an arc cannot be contained
  	}
  
  	public ContainerFacet getContainerFacet()
  	{
  		return state.containerFacet;
  	}
  	
  	public AnchorFacet getAnchorFacet()
  	{
  		return state.anchorFacet;
  	}
  
  	public LinkingFacet getLinkingFacet()
  	{
  		return state.linkingFacet;
  	}
  	
    private AnchorPreviewFacet getPreviewFigureForToolLinkable(PreviewCacheFacet previewFigures, AnchorFacet initial)
    {
      return previewFigures.getCachedPreviewOrMakeOne(initial.getFigureFacet()).getAnchorPreviewFacet();
    }
    
  	/**
  	 * @see com.hopstepjump.jumble.foundation.FigureFacet#cleanUp()
  	 */
  	public void cleanUp()
  	{
  		state.linkingFacet.detachFromAnchors();
  	}
  
  	/**
  	 * @see com.hopstepjump.jumble.foundation.FigureFacet#getDynamicFacet(Class)
  	 */
  	public Facet getDynamicFacet(Class facetClass)
  	{
  		Facet facet = dynamicFacets.get(facetClass);
  		if (facet == null)
  			throw new FacetNotFoundException("Cannot find facet corresponding to " + facetClass + " in " + state.appearanceFacet);
  		return facet;
  	}
  	
  	/**
  	 * @see com.hopstepjump.jumble.foundation.FigureFacet#getDiagram()
  	 */
  	public DiagramFacet getDiagram()
  	{
  		return state.diagram;
  	}
  
  	public void registerDynamicFacet(Facet facet, Class facetInterface)
  	{
  		dynamicFacets.put(facetInterface, facet);
  	}
  	
  	/**
  	 * @see com.hopstepjump.jumble.foundation.FigureFacet#hasDynamicFacet(Class)
  	 */
  	public boolean hasDynamicFacet(Class facetClass)
  	{
  		return dynamicFacets.containsKey(facetClass);
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
  			return super.hashCode();  // to solve a bug in the serialization of objects which override hashCode()
  		return state.id.hashCode();
  	}
  	
  	/**
  	 * @see com.hopstepjump.idraw.foundation.FigureFacet#getDiagramReference()
  	 */
  	public FigureReference getFigureReference()
  	{
  		return new FigureReference(state.diagramReference, state.id);
  	}
  
  	public PersistentFigure makePersistentFigure()
  	{
  		PersistentFigure pFigure = new PersistentFigure(state.id, state.recreatorFacet.getRecreatorName());
      pFigure.setSubject(getSubject());
  		PersistentProperties properties = pFigure.getProperties();
  		properties.add(new PersistentProperty("show", state.showing, true));
  		properties.add(new PersistentProperty("curved", state.curved, false));
  		properties.add(new PersistentProperty("vtl", state.calculatedPoints.getVirtualPoint()));
  
  		List<UPoint> allPoints = state.calculatedPoints.getAllPoints();
  		properties.add(new PersistentProperty("pts", allPoints.toArray(new UPoint[0])));
  		
  		state.appearanceFacet.addToPersistentProperties(properties);
  		
  		// set any anchors
  		AnchorFacet anchor1 = state.calculatedPoints.getNode1();
  		if (anchor1 != null)
  			pFigure.setAnchor1Id(anchor1.getFigureFacet().getId());
  		AnchorFacet anchor2 = state.calculatedPoints.getNode2();
  		if (anchor2 != null)
  			pFigure.setAnchor2Id(anchor2.getFigureFacet().getId());
  			
  		// set any links
  		for (Iterator iter = state.linked.iterator(); iter.hasNext();)
  		{
  			LinkingFacet linkingFacet = (LinkingFacet) iter.next();
  			pFigure.getLinkIds().add(linkingFacet.getFigureFacet().getId());
  		}
  		
  		return pFigure;
  	}
  
  	/**
  	 * @see com.hopstepjump.idraw.foundation.FigureFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
  	 */
  	public Command updateViewAfterSubjectChanged(boolean isTop, ViewUpdatePassEnum pass)
  	{
  		return state.appearanceFacet.formViewUpdateCommandAfterSubjectChanged(isTop, pass);
  	}
  
  	/**
  	 * @see com.hopstepjump.idraw.foundation.FigureFacet#middleButtonPressed(ToolCoordinatorFacet)
  	 */
  	public Command middleButtonPressed(ToolCoordinatorFacet coordinator)
  	{
  	  return null;
  	}
  	
  	/**
  	 * @see com.hopstepjump.idraw.foundation.FigureFacet#getSubject()
  	 */
  	public Object getSubject()
  	{
  		return state.appearanceFacet.getSubject();
  	}
  
  	/**
  	 * @see com.hopstepjump.idraw.foundation.FigureFacet#getClipboardFacet()
  	 */
  	public ClipboardFacet getClipboardFacet()
  	{
  		return clipFacet;
  	}
  
  	/**
  	 * @see com.hopstepjump.idraw.foundation.FigureFacet#hasSubjectBeenDeleted()
  	 */
  	public boolean hasSubjectBeenDeleted()
  	{
  		return state.appearanceFacet.hasSubjectBeenDeleted();
  	}
  
  	/*
  	 * @see com.hopstepjump.idraw.arcfacilities.arcsupport.BasicArcFigureFacet#adjusted()
  	 */
  	public void adjusted()
  	{
  		state.diagram.adjusted(state.figureFacet);
  	}
  
    public boolean useGlobalLayer()
    {
      return true;
    }


    public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
    {
      // ignore for now
    }


		public Command formDeleteCommand()
		{
	    // form a complete set of all figures to delete, including children
	    ChosenFiguresFacet chosenFigures = new ChosenFiguresFacet()
	    {
	      public boolean isChosen(FigureFacet figure)
	      {
	        return figure == BasicArcFigureFacetImpl.this;
	      }
	    };
	    List<FigureFacet> toDelete = new ArrayList<FigureFacet>();
	    toDelete.add(this);
	    Set deletionFigureIds = DeleteFromDiagramHelper.getFigureIdsIncludedInDelete(toDelete, chosenFigures, false);
	    

	    // make a delete command to remove the views with deleted subjects
	    return
	      DeleteFromDiagramHelper.makeDeleteCommand(
	          "BasicArcFigureGem deletion", "", "", getDiagram(), deletionFigureIds, false);
		}


    public ClipboardCommandsFacet getClipboardCommandsFacet()
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
			return new ToolFigureClassification(figureFacet.getFigureName(), null);
		}

		public void aboutToAdjust()
		{
			state.diagram.aboutToAdjust(figureFacet);
		}
  }
}

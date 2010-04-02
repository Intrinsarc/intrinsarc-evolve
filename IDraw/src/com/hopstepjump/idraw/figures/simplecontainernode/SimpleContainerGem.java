package com.hopstepjump.idraw.figures.simplecontainernode;

import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class SimpleContainerGem implements Gem
{
	static final String FIGURE_NAME = "simple container";
  private Set<FigureFacet> contents = new HashSet<FigureFacet>();
  private UDimension minExtent;
  private UDimension offset;
  private BasicNodeContainerFacet containerFacet = new ContainerFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private SimpleContainerFacet simpleFacet = new SimpleContainerFacetImpl();
  private boolean isWillingToActAsBackdrop;
  private Set<String> addedUuids = new HashSet<String>();
  private Set<String> deletedUuids = new HashSet<String>();
  
  private class ContainerFacetImpl implements BasicNodeContainerFacet
  {
	 	public boolean insideContainer(UPoint point)
	  {
	    return figureFacet.getFullBounds().contains(point);
	  }
	
	  public Iterator<FigureFacet> getContents()
	  {
			return getContentsList().iterator();
	  }
	  
	  public void removeContents(ContainedFacet[] containable)
	  {
	    // tell each containable that they are now contained by this
	    for (int lp = 0; lp < containable.length; lp++)
	    {
	    	contents.remove(containable[lp].getFigureFacet());
	      containable[lp].setContainer(null);
	    }
	  }
	
	  public void addContents(ContainedFacet[] containable)
	  {
	    // tell each containable that they are now contained by this
	    for (int lp = 0; lp < containable.length; lp++)
			{
				contents.add(containable[lp].getFigureFacet());
	      containable[lp].setContainer(this);
			}
	  }

	  public boolean isWillingToActAsBackdrop()
	  {
	    return isWillingToActAsBackdrop && !contents.isEmpty();
	  }
	  
	  public boolean directlyAcceptsItems()
		{
			return true;
		}
		
		/**
		 * @see com.giroway.jumble.figurefacilities.containmentbase.IContainerable#getAcceptingSubcontainer(CmdContainable[])
		 */
		public ContainerFacet getAcceptingSubcontainer(ContainedFacet[] containables)
		{
		  return this;
		}
	
	  public FigureFacet getFigureFacet()
	  {
	  	return figureFacet;
	  }
	  
	  public ContainedFacet getContainedFacet()
	  {
	  	return figureFacet.getContainedFacet();
	  }

		/**
		 * @see com.hopstepjump.jumble.foundation.ContainerFacet#addChildPreviewsToCache(PreviewCacheFacet)
		 */
		public void addChildPreviewsToCache(PreviewCacheFacet previewCache)
		{						
			Iterator iter = getContents();
	    {
	      while (iter.hasNext())
	      {
	        FigureFacet contained = (FigureFacet) iter.next();
	        previewCache.getCachedPreviewOrMakeOne(contained);
	      }
	    }
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
		 */
		public void setShowingForChildren(boolean showing)
		{
			for (Iterator iter = getContents(); iter.hasNext();)
			{
				FigureFacet figure = (FigureFacet) iter.next();
				figure.setShowing(showing);
			}
		}
		
		/**
		 * @see com.hopstepjump.idraw.foundation.ContainerFacet#persistence_addContained( FigureFacet)
		 */
		public void persistence_addContained(FigureFacet contained)
		{
			// add this to the list, and set the container
			contents.add(contained);
			contained.getContainedFacet().persistence_setContainer(containerFacet);
		}

		public void cleanUp()
		{
			contents.clear();
		}
  }
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {
	  public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
	  {
			return null; // not needed -- this is not directly selectable
	  }
	  
    public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification(FIGURE_NAME, null);
		}
	
	  public ZNode formView()
	  {
	  	// add a transparent cover onto the diagram -- still must be "findable"
			ZGroup group = new ZGroup();
			ZRectangle rect = new ZRectangle(figureFacet.getFullBounds());
			rect.setPenPaint(ScreenProperties.getTransparentColor());
			rect.setFillPaint(ScreenProperties.getTransparentColor());
			group.addChild(new ZVisualLeaf(rect));
			
			// add the interpretable properties
	    group.setChildrenPickable(false);
	    group.setChildrenFindable(false);
	    group.putClientProperty("figure", figureFacet);
			
			return group;
	  }
	  
		/**
		 * @see com.hopstepjump.jumble.foundation.interfaces.SelectableFigure#getActualFigureForSelection()
		 */
		public FigureFacet getActualFigureForSelection()
		{
			// eek!  we want this containers actual figure for selection
			return figureFacet.getContainedFacet().getContainer().getFigureFacet().getActualFigureForSelection();
		}
	
		/**
		 * @see com.hopstepjump.jumble.foundation.interfaces.SelectableFigure#getContextMenu(DiagramView, ToolCoordinator)
		 */
		public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return null;
		}
	
		/**
		 * @see com.hopstepjump.jumble.foundation.interfaces.Figure#getFigureName()
		 */
		public String getFigureName()
		{
			ContainerFacet container = figureFacet.getContainedFacet().getContainer();
			if (container == null)
				return "contents space";
			return "contents space for " + container.getFigureFacet().getFigureName();
		}
	
		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
	  {
	  	SimpleContainerPreviewGem containerPreviewGem = new SimpleContainerPreviewGem();
	  	BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, false);
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			basicGem.connectContainerPreviewFacet(containerPreviewGem.getContainerPreviewFacet());
			containerPreviewGem.connectPreviewFacet(basicGem.getPreviewFacet());
			containerPreviewGem.connectSimpleContainerFacet(simpleFacet);
			containerPreviewGem.connectPreviewCacheFacet(previews);

			return basicGem.getPreviewFacet();
	  }
	  
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getAutoSizedBounds(IDiagram)
		 */
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return null;
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			return new UDimension(0, 0);
		}
		
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
		 */
		public boolean acceptsContainer(ContainerFacet container)
		{
			return true;
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return figureFacet.getFullBounds();
		}
		
		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			return makeCurrentSizeInfo().calculateSizes().getEntireBounds();
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#setPersistentProperties(MDiagramDefinitionsPackage, MFigure)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			// want to save min extent and offset
			properties.add(new PersistentProperty("min", minExtent, new UDimension(0,0)));
			properties.add(new PersistentProperty("off", offset, new UDimension(0,0)));
      properties.add(new PersistentProperty("backdrop", isWillingToActAsBackdrop, true));
      properties.add(new PersistentProperty("addedUuids", addedUuids));
      properties.add(new PersistentProperty("deletedUuids", deletedUuids));
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
		}
	
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
		 */
		public void middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
		 */
		public Object getSubject()
		{
			return null;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
		 */
		public boolean hasSubjectBeenDeleted()
		{
			return false;
		}

    public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
    {
    }

    public void performPostContainerDropTransaction()
    {
    }

		public boolean canMoveContainers()
		{
			return false;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {      
      ContainerFacet container = figureFacet.getContainedFacet().getContainer();
      if (container == null)
        return true;
      return container.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }
  
  private class SimpleContainerFacetImpl implements SimpleContainerFacet
  {
		public UBounds getMinimumBounds()
		{
			SimpleContainerSizeInfo info = SimpleContainerGem.this.makeCurrentSizeInfo();
			return info.calculateSizes().getContained();
		}
		
		public void addDeleted(String uuid)
		{
			if (!addedUuids.remove(uuid))
				deletedUuids.add(uuid);
		}

		public void removeDeleted(String uuid)
		{
			if (!deletedUuids.remove(uuid))
				addedUuids.add(uuid);
		}

		public void clean(Set<String> visuallySuppressedUuids, Set<String> uuids)
		{
			addedUuids.retainAll(visuallySuppressedUuids);
			deletedUuids.removeAll(visuallySuppressedUuids);
			deletedUuids.retainAll(uuids);
		}

		public Set<String>[] getAddedAndDeleted()
		{
			return (Set<String>[]) new Set[]{new HashSet<String>(addedUuids), new HashSet<String>(deletedUuids)};
		}

		public boolean isDeleted(Set<String> visuallySuppressedUuids, String uuid)
		{
			if (addedUuids.contains(uuid))
				return false;
			return visuallySuppressedUuids.contains(uuid) || deletedUuids.contains(uuid);
		}

		public void resetToDefaults()
		{
			addedUuids.clear();
			deletedUuids.clear();
		}

		public void setAddedAndDeleted(Set<String>[] addedAndDeletedUuids)
		{
			if (addedAndDeletedUuids == null)
				resetToDefaults();
			else
			{
				addedUuids = addedAndDeletedUuids[0];
				deletedUuids = addedAndDeletedUuids[1];
			}
		}

		public void setToShowAll(Set<String> visuallySuppressedUuids)
		{
			addedUuids = visuallySuppressedUuids;
			deletedUuids.clear();
		}

		public boolean isEmpty()
		{
			return contents.isEmpty();
		}
	
		/**
		 * 
		 * 
		 * useful, and complex geomety calculations
		 * 
		 * 
		 * 
		 */
	
		private UPoint getTopLeftKeepingCloseToCorner(int corner, UBounds contained, UBounds full)
		{
			UPoint topLeft = full.getTopLeftPoint();
			
	  	switch (corner)
	  	{
	  		case ResizingManipulatorGem.TOP_LEFT_CORNER:
	  			return topLeft;
	  		case ResizingManipulatorGem.TOP_RIGHT_CORNER:
	  		{
	  			double xDifference = full.getTopRightPoint().getX() - contained.getTopRightPoint().getX();
	  			return topLeft.subtract(new UDimension(xDifference, 0));
	  		}
	  		case ResizingManipulatorGem.BOTTOM_LEFT_CORNER:
	  		{
	  			double yDifference = full.getBottomLeftPoint().getY() - contained.getBottomLeftPoint().getY();
	  			return topLeft.subtract(new UDimension(0, yDifference));
	  		}
	
	  		case ResizingManipulatorGem.BOTTOM_RIGHT_CORNER:
	  		{
	  			UDimension difference = full.getBottomRightPoint().subtract(contained.getBottomRightPoint());
	  			return topLeft.subtract(difference);
	  		}
	  	}
	  	return topLeft;  // should never get here
		}
	
		private UPoint stopOppositeCornerFromBeingPushedOut(int corner, UBounds bounds, UBounds desired /* wanted */)
		{
			UPoint currentTopLeft = bounds.getTopLeftPoint();
			
	  	switch (corner)
	  	{
	  		case ResizingManipulatorGem.TOP_LEFT_CORNER:
	  		{
	  			UPoint bottomRight = bounds.getBottomRightPoint().minOfEach(desired.getBottomRightPoint());
	  			
	  			return currentTopLeft.subtract(bounds.getBottomRightPoint().subtract(bottomRight));
	  		}
	  		case ResizingManipulatorGem.TOP_RIGHT_CORNER:
	  		{
	  			UPoint bottomLeftOfBounds = bounds.getBottomLeftPoint();
	  			UPoint bottomLeftOfDesired = desired.getBottomLeftPoint();
	  			UPoint bottomLeft = new UPoint(Math.max(bottomLeftOfBounds.getX(), bottomLeftOfDesired.getX()),
	  																		 Math.min(bottomLeftOfBounds.getY(), bottomLeftOfDesired.getY()));
	  			
	  			return currentTopLeft.subtract(bounds.getBottomLeftPoint().subtract(bottomLeft));
	  		}
	  		case ResizingManipulatorGem.BOTTOM_LEFT_CORNER:
	  		{
	  			UPoint topRightOfBounds = bounds.getTopRightPoint();
	  			UPoint topRightOfDesired = desired.getTopRightPoint();
	  			UPoint topRight = new UPoint(Math.min(topRightOfBounds.getX(), topRightOfDesired.getX()),
	  																	 Math.max(topRightOfBounds.getY(), topRightOfDesired.getY()));
	  			
	  			return currentTopLeft.subtract(bounds.getTopRightPoint().subtract(topRight));
	  		}
	
	  		case ResizingManipulatorGem.BOTTOM_RIGHT_CORNER:
	  		{
	  			UPoint topLeft = bounds.getTopLeftPoint().maxOfEach(desired.getTopLeftPoint());
	  			
	  			return currentTopLeft.subtract(bounds.getTopLeftPoint().subtract(topLeft));
	  		}
	  	}		
	  	return currentTopLeft;
		}
	
		public UBounds getMinimumResizeBounds(ContainerSizeCalculator calculator, int corner, UBounds bounds, UPoint topLeftPoint, boolean fromCentre)
		{
	  	// get the minimum extent for rebounding purposes
	  	ContainerSizeInfo dummy = calculator.makeInfo(new UPoint(0,0), new UDimension(0,0), true);
	  	UBounds minBounds = dummy.getMinContentBounds();
			ContainerSizeInfo info = calculator.makeInfo(minBounds.getTopLeftPoint(), minBounds.getDimension(), false);
			info.setTopLeft(bounds.getTopLeftPoint());
			ContainerCalculatedSizes sizes = info.makeSizes(true);
			UDimension minExtent = sizes.getOuter().getDimension();
	
			// if we are resizing from the centre, choose topleft from the middle point
			UPoint lockedTopLeft;
			if (fromCentre)
			{
				UPoint centredTopLeft = sizes.getContents().getMiddlePoint().subtract(minExtent.multiply(0.5));
				info.setTopLeft(centredTopLeft);
				sizes = info.makeSizes(false);
			}
	
			// now, work out the top left point given that we want to preserve the position
			// of the opposite corner to the one that is being manipulated	
			lockedTopLeft = getTopLeftKeepingCloseToCorner(corner, info.getMinContentBounds(), sizes.getOuter());
			
			ContainerSizeInfo info2 = calculator.makeInfo(minBounds.getTopLeftPoint(), minBounds.getDimension(), false);
			info2.setTopLeft(lockedTopLeft);
			ContainerCalculatedSizes sizes2 = info2.makeSizes(false);
			//
			// sizes2 is the minimum rectangle, pushed against the corner that is being moved!
			//
			
			// we need to make sure that the top left corner is not moving outwards further than bounds specifies
			// make sure that the bounds ensure that the opposite corner is not pushed out
			UBounds newBounds = new UBounds(topLeftPoint, bounds.getDimension().maxOfEach(minExtent));
			lockedTopLeft = stopOppositeCornerFromBeingPushedOut(corner, sizes2.getOuter(), newBounds);
			
			ContainerSizeInfo info3 = calculator.makeInfo(minBounds.getTopLeftPoint(), minBounds.getDimension(), false);
			info3.setTopLeft(lockedTopLeft);
			ContainerCalculatedSizes sizes3 = info3.makeSizes(false);
			return sizes3.getOuter();
		}
		
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classdiagram.simplecontainernode.SimpleContainerFacet#getFigureFacet()
		 */
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classdiagram.simplecontainernode.SimpleContainerFacet#makeCurrentSizeInfo(UBounds)
		 */
		public SimpleContainerSizeInfo makeCurrentSizeInfo(UBounds minContentsBoundsFromPreviews)
		{
			UBounds fullBounds = figureFacet.getFullBounds();
			SimpleContainerSizeInfo info = new SimpleContainerSizeInfo(fullBounds.getTopLeftPoint(), fullBounds.getDimension(), minContentsBoundsFromPreviews, minExtent, offset);
			return info;
		}
  }

	public static SimpleContainerGem createAndWireUp(DiagramFacet diagram, String figureId, ContainerFacet owner, UDimension minSize, UDimension offset, String containedName, boolean isWillingToActAsBackdrop)
	{
		// need to find a better way to do this...
  	BasicNodeGem basicGem = new BasicNodeGem(SimpleContainerCreatorGem.RECREATOR_NAME, diagram, figureId, new UPoint(0,0), true, containedName, false);
		SimpleContainerGem simpleGem = new SimpleContainerGem(minSize, offset, isWillingToActAsBackdrop);
		basicGem.connectBasicNodeAppearanceFacet(simpleGem.getBasicNodeAppearanceFacet());
		basicGem.getBasicNodeFigureFacet().getContainedFacet().setContainer(owner);
		
		// a simple container cannot act as an anchor
		basicGem.connectAnchorFacet(null);
		simpleGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
		basicGem.connectBasicNodeContainerFacet(simpleGem.getBasicNodeContainerFacet());
		return simpleGem;
	}

  public SimpleContainerGem(UDimension minExtent, UDimension offset, boolean isWillingToActAsBackdrop)
  {
    this.minExtent = minExtent;
    this.offset = offset;
    this.isWillingToActAsBackdrop = isWillingToActAsBackdrop;
  }
  
  public SimpleContainerGem(PersistentFigure pfig)
  {
  	interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
  {
		PersistentProperties properties = pfig.getProperties();
		minExtent = properties.retrieve("min", new UDimension(0,0)).asUDimension();
		offset = properties.retrieve("off", new UDimension(0,0)).asUDimension();
		addedUuids = new HashSet<String>(properties.retrieve("addedUuids", "").asStringCollection());		
		deletedUuids = new HashSet<String>(properties.retrieve("deletedUuids", "").asStringCollection());
  }
  
  public SimpleContainerFacet getSimpleContainerFacet()
  {
  	return simpleFacet;
  }
  
  public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
  	return appearanceFacet;
  }
  
	private SimpleContainerSizeInfo makeCurrentSizeInfo()
	{
		UBounds fullBounds = figureFacet.getFullBounds();
		SimpleContainerSizeInfo info = new SimpleContainerSizeInfo(fullBounds.getTopLeftPoint(), fullBounds.getDimension(), getMinimumContainedBounds(), minExtent, offset);
		return info;
	}
	
	Set<FigureFacet> getUnderlyings(ContainedPreviewFacet[] add, ContainedPreviewFacet[] remove)
	{
		Set<FigureFacet> toAdd = new HashSet<FigureFacet>();
		if (add != null)
			for (int lp = 0; lp < add.length; lp++)
				toAdd.add(add[lp].getPreviewFacet().isPreviewFor());

		Set<FigureFacet> toRemove = new HashSet<FigureFacet>();
		if (remove != null)
			for (int lp = 0; lp < remove.length; lp++)
				toRemove.add(remove[lp].getPreviewFacet().isPreviewFor());
		
		Set<FigureFacet> all = new HashSet<FigureFacet>(getContentsList());
		all.addAll(toAdd);
		all.removeAll(toRemove);
		return all;
	}
	
	private UBounds getMinimumContainedBounds()
	{
		UBounds bounds = null;
		Iterator iter = containerFacet.getContents();
		while (iter.hasNext())
		{
			FigureFacet contained = (FigureFacet) iter.next();
			UBounds containedBounds = contained.getFullBoundsForContainment();
			if (bounds == null)
				bounds = containedBounds;
			else
				bounds = bounds.union(containedBounds);
		}
		return bounds;
	}
	
	private List<FigureFacet> getContentsList()
	{
		List<FigureFacet> list = new ArrayList<FigureFacet>();
		for (FigureFacet content : contents)
		  list.add(content);
		return list;
	}

	/**
	 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#setBasicNodeFigureFacet(BasicNodeFigureFacet)
	 */
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
		figureFacet.registerDynamicFacet(simpleFacet, SimpleContainerFacet.class);
  }
  
  public BasicNodeContainerFacet getBasicNodeContainerFacet()
  {
  	return containerFacet;
  }
}

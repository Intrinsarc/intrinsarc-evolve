/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.featurenode;


import java.util.*;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.containment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creation.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @version 	1.0
 * @author
 */
public final class FeatureCompartmentGem implements Gem
{
	public static final String FIGURE_NAME = "feature compartment";
	private static final double NULL_COMPARTMENT_HEIGHT = 8;
	private static final UDimension NULL_COMPARTMENT_EXTENT = new UDimension(40, NULL_COMPARTMENT_HEIGHT);
	private static final UDimension CORNER_OFFSET = new UDimension(3, 2);
	private Set<FigureFacet> features = new LinkedHashSet<FigureFacet>();
	private int containsFeatureType;
  private BasicNodeContainerFacet containerFacet = new ContainerFacetImpl();
  private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private FeatureAddFacet addFacet = new FeatureAddFacetImpl();
  private FeatureCompartmentFacet compartmentFacet = new FeatureCompartmentFacetImpl();
  private FeatureAcceptorFacet acceptorFacet = new FeatureAcceptorFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  private boolean contentsCanMoveContainers = true;
  private Set<String> addedUuids = new HashSet<String>();
  private Set<String> deletedUuids = new HashSet<String>();
    
  public static FeatureCompartmentGem createAndWireUp(
      DiagramFacet diagram,
      String figureId,
      ContainerFacet owner,
      int featureType,
      String recreatorName,
      String containedName,
      boolean contentsCanMoveContainers)
	{
		// need to find a better way to do this
    BasicNodeGem basicGem =
      new BasicNodeGem(
          recreatorName,
          diagram,
          figureId,
          new UPoint(0,0),
          true,
          containedName,
          false);
		FeatureCompartmentGem featureGem = new FeatureCompartmentGem(featureType);
		featureGem.setContentsCanMoveContainers(contentsCanMoveContainers);
		basicGem.connectBasicNodeAppearanceFacet(featureGem.getBasicNodeAppearanceFacet());
		// a compartment cannot act as an anchor
		basicGem.connectAnchorFacet(null);
		featureGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
		basicGem.getBasicNodeFigureFacet().getContainedFacet().setContainer(owner);
		basicGem.connectBasicNodeContainerFacet(featureGem.getBasicNodeContainerFacet());
		return featureGem;
	}
	
	private class FeatureCompartmentFacetImpl implements FeatureCompartmentFacet
	{
    /**
		 * @see com.intrinsarc.jumble.umldiagrams.classdiagram.featurenode.FeatureCompartmentFacet#getFigureFacet()
		 */
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}

	  /**
	   * invoked by the container to determine how far we can resize down
	   */
	  public UDimension getMinimumExtent()
	  {
	  	double height = 0;
	  	double width = 0;
	  	boolean haveAtLeastOneOp = false;

	  	for (FigureFacet figure : features)
	  	{
	  		UDimension opDim = figure.getFullBounds().getDimension();
				height += opDim.getHeight();
				width = Math.max(width, opDim.getWidth());			
				haveAtLeastOneOp = true;
	   	}
	   	if (!haveAtLeastOneOp)
	   		return NULL_COMPARTMENT_EXTENT;
			return new UDimension(width, height).add(CORNER_OFFSET.multiply(2));
	  }

	  public List<PreviewFacet> getSortedOperations(
	      PreviewCacheFacet previews,
	      ContainedPreviewFacet[] add,
	      ContainedPreviewFacet[] remove,
	      UPoint movePoint)
	  {
	  	// sort the add ops, and give them a sortPosition based on their position relative to the rest real operations
	  	if (add != null && movePoint != null)
	  	{
				// see which operation this goes in front of
				// the sorted ops are FigureFacets
				Iterator sortedOps = FeatureCompartmentGem.this.getSortedOperations();
				
				Double addSortPosition = null;
				double lastY = 0;
				while (sortedOps.hasNext())
				{
					FigureFacet sortedOp = (FigureFacet) sortedOps.next();
					
					if (!coveredAlready(sortedOp, add))
					{
  					PreviewFacet previewOp = previews.getCachedPreview(sortedOp);
  					FeaturePreviewComparableFacet comparableFacet =
  					  previewOp.getDynamicFacet(FeaturePreviewComparableFacet.class);
  					
						double sortedY = sortedOp.getFullBounds().getMiddlePoint().getY();
						if (movePoint.getY() < sortedY)
						{
							addSortPosition = new Double(comparableFacet.getOriginalSortPosition() - 0.5);
							break;
						}
						lastY = previewOp.getTopLeftPoint().getY();
					}
				}
				if (addSortPosition == null)
					addSortPosition = new Double(lastY);
				
				// assign sort numbers to each added preview
				// the sortedAddPreviews are ContainedPreviewFacets
				List<PreviewFacet> sortedAddPreviews = new ArrayList<PreviewFacet>();
				for (int lp = 0; lp < add.length; lp++)
					sortedAddPreviews.add(add[lp].getPreviewFacet());

				sortPreviews(sortedAddPreviews);
				Iterator sortedAdds = sortedAddPreviews.iterator();
				double primitiveAddSortPosition = addSortPosition.doubleValue();
				while (sortedAdds.hasNext())
				{
					PreviewFacet addPreview = (PreviewFacet) sortedAdds.next();
					FeaturePreviewComparableFacet addOpComparable = addPreview.getDynamicFacet(FeaturePreviewComparableFacet.class);
					addOpComparable.setSortPosition(primitiveAddSortPosition);
					primitiveAddSortPosition += 0.00001;
				}
	  	}
	  	
	  	// now we have found a sort position for the preview, we can put all the previews into 1 collection, and resort!
	  	Set<PreviewFacet> ops = new HashSet<PreviewFacet>();
	  	
	  	// add the current operations
	  	for (FigureFacet feature : features)
	  	{
	  	  if (!coveredAlready(feature, add))
	  	  {
  	  		PreviewFacet preview = previews.getCachedPreview(feature);
  	  		if (preview != null)
  	  			ops.add(preview);
	  	  }
	  	}
	  	
	  	// add the add preview figures
			if (add != null)
			  for (ContainedPreviewFacet addTo : add)
		  		ops.add(addTo.getPreviewFacet());
	
	  	// remove the remove figures
	  	if (remove != null)
	  	  for (ContainedPreviewFacet takeAway : remove)
		  		ops.remove(takeAway.getPreviewFacet());
	  	
	  	// sort the operations
	  	List<PreviewFacet> allSortedOpPreviews = new ArrayList<PreviewFacet>(ops);
	  	sortPreviews(allSortedOpPreviews);

			return allSortedOpPreviews;
	  }
	  
	  /**
	   * is this feature covered already by one of the adds?  allows it to be discounted.
	   * @param feature
	   * @param add
	   * @return
	   */
	  private boolean coveredAlready(FigureFacet feature, ContainedPreviewFacet[] add)
    {
	    if (add == null)
	      return false;
      for (ContainedPreviewFacet one : add)
      {
        if (one.getPreviewFacet().isPreviewFor().getSubject() == feature.getSubject())
          return true;
      }
      return false;
    }

    public UDimension formNewDimensions(PreviewCacheFacet previews, List<PreviewFacet> sortedOps, UPoint reference)
	  {
			UBounds bounds = null;
	  	UPoint start = reference;
	  	for (PreviewFacet preview : sortedOps)
	  	{
	  		UDimension opDim = preview.getFullBounds().getDimension();
	  		UBounds opBounds = new UBounds(start.add(CORNER_OFFSET), opDim);
	  		
	  		UPoint newPoint = start.add(CORNER_OFFSET);
	  		UDimension previewDimension = preview.getFullBounds().getDimension();
	  		preview.setFullBounds(new UBounds(newPoint, previewDimension), true);
	  		start = start.add(new UDimension(0, opDim.getHeight()));
	  		
	  		// set the full bounds
	  		if (bounds == null)
	  			bounds = opBounds;
	  		else
	  			bounds = bounds.union(opBounds);
	  	}
			return bounds == null ? NULL_COMPARTMENT_EXTENT : bounds.getDimension().add(CORNER_OFFSET.multiply(2));  	
	  }

			/**
			 * @see com.intrinsarc.evolve.umldiagrams.featurenode.FeatureCompartmentFacet#isEmpty()
			 */
			public boolean isEmpty()
			{
				return features.isEmpty();
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

			public boolean isDeleted(Set<String> visuallySuppressedUuids, String uuid)
			{
				if (addedUuids.contains(uuid))
					return false;
				return visuallySuppressedUuids.contains(uuid) || deletedUuids.contains(uuid);
			}

			public void setToShowAll(Set<String> visuallySuppressedUuids)
			{
				addedUuids = visuallySuppressedUuids;
				deletedUuids.clear();
			}
	}

	private class FeatureAcceptorFacetImpl implements FeatureAcceptorFacet
	{
	  /**
		 * @see com.giroway.jumble.umldiagrams.classdiagram.featurenode.FeatureFigureAcceptor#acceptsFeatureType(int)
		 */
		public boolean acceptsFeatureType(int type)
		{
			return type == containsFeatureType;
		}	
	}

  private class ContainerFacetImpl implements BasicNodeContainerFacet
  {
    /**
		 * @see com.giroway.jumble.figurefacilities.containmentbase.IContainerable#getAcceptingSubcontainer(CmdContainable[])
		 */
		public ContainerFacet getAcceptingSubcontainer(ContainedFacet[] containables)
		{
			for (int lp = 0; lp < containables.length; lp++)
			{
				// only accept feature types
				if (!containables[lp].getFigureFacet().hasDynamicFacet(FeatureTypeFacet.class))
					return null;
					
				FeatureTypeFacet typeFacet = containables[lp].getFigureFacet().getDynamicFacet(FeatureTypeFacet.class);
				if (typeFacet.getFeatureType() != containsFeatureType)
					return null;
			}
			return this;
		}	

	  public boolean directlyAcceptsItems()
		{
			return false;
		}
		
		/**
		 * @see com.giroway.jumble.figurefacilities.containmentbase.IContainerable#getContainables()
		 */
		public Iterator<FigureFacet> getContents()
		{
		  // copy for immutability??
		  return new ArrayList<FigureFacet>(features).iterator();
		}
		
		/*
		 * @see IContainerable#insideContainer(UPoint)
		 */
		public boolean insideContainer(UPoint point)
		{
			return figureFacet.getFullBounds().contains(point);
		}
	
		/*
		 * @see IContainerable#isWillingToActAsBackdrop()
		 */
		public boolean isWillingToActAsBackdrop()
		{
			return false;
		}
	
		/*
		 * @see CmdContainerable#addContainables(CmdContainable[])
		 */
		public void addContents(ContainedFacet[] containables)
		{
			// add the operations to the list -- acceptsContained() makes sure that these are the correct type			
			for (int lp = 0; lp < containables.length; lp++)
			{
				features.add(containables[lp].getFigureFacet());
				containables[lp].setContainer(this);
			}
		}
		
		/*
		 * @see CmdContainerable#removeContainables(CmdContainable[])
		 */
		public void removeContents(ContainedFacet[] containables)
		{
			// remove the operations from the list
			for (int lp = 0; lp < containables.length; lp++)
			{
				containables[lp].setContainer(null);
				features.remove(containables[lp].getFigureFacet());
			}
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
		 * @see com.intrinsarc.jumble.foundation.ContainerFacet#addChildPreviewsToCache(PreviewCacheFacet)
		 */
		public void addChildPreviewsToCache(PreviewCacheFacet previewCache)
		{
			Iterator iter = getSortedOperations();
	    {
	    	int sortPosition = 0;
	      while (iter.hasNext())
	      {
	        FigureFacet contained = (FigureFacet) iter.next();
	        PreviewFacet preview = previewCache.getCachedPreviewOrMakeOne(contained);
	        preview.getDynamicFacet(FeaturePreviewComparableFacet.class).setOriginalSortPosition(sortPosition++);
	      }
	    }
		}
		
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
		 */
		public void setShowingForChildren(boolean showing)
		{
			// if we are a container, tell each contained to show or hide
			for (Iterator iter = containerFacet.getContents(); iter.hasNext();)
			{
				FigureFacet figure = (FigureFacet) iter.next();
				figure.setShowing(showing);
			}
		}

		public void persistence_addContained(FigureFacet contained)
		{
			features.add(contained);
			contained.getContainedFacet().persistence_setContainer(this);
		}

		public void cleanUp()
		{
			features.clear();
		}
  }
	
	private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
	{
		public Manipulators getSelectionManipulators(
		    ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
	  {
	    return null;
	  }
		
		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet.getContainedFacet().getContainer().getFigureFacet();
		}
	
	  public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
	  {
	  	BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, false);
	  	FeatureCompartmentPreviewGem previewGem = new FeatureCompartmentPreviewGem();
	  	previewGem.connectPreviewFacet(basicGem.getPreviewFacet());
	  	previewGem.connectFeatureCompartmentFacet(compartmentFacet);
	  	previewGem.connectPreviewCacheFacet(previews);
	  	basicGem.connectContainerPreviewFacet(previewGem.getContainerPreviewFacet());
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
	  	
	  	return basicGem.getPreviewFacet();	  	
	  }
	
		/*
		 * @see Figure#formView()
		 */
		public ZNode formView()
		{
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
	
		public String getFigureName()
		{
			return FIGURE_NAME;
		}
		
			/**
		 * @see com.giroway.jumble.foundation.interfaces.SelectableFigure#getContextMenu()
	 	 */
		public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return null;
		}
		
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getAutoSizedBounds(IDiagram)
		 */
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return null;
		}

		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			return NULL_COMPARTMENT_EXTENT;
		}
		
		/**
		 * @see com.intrinsarc.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
		 */
		public boolean acceptsContainer(ContainerFacet container)
		{
			return true;
		}
		
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return figureFacet.getFullBounds();
		}

		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			return new UBounds(figureFacet.getFullBounds().getTopLeftPoint(), compartmentFacet.getMinimumExtent());
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("addedUuids", addedUuids));
		  properties.add(new PersistentProperty("deletedUuids", deletedUuids));
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
		 */
		public void middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
		 */
		public Object getSubject()
		{
			return null;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
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
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }

    public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification(FIGURE_NAME, null);
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
	}
	
	private class FeatureAddFacetImpl implements FeatureAddFacet
	{
		public void addFeature(FigureReference reference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location)
		{
			// set the location so that the operation will be inserted last
	    if (location == null)
	      location = figureFacet.getFullBounds().getBottomLeftPoint();
			
	    // adjust resizings to accommodate operation preview
	    NodeCreateFigureTransaction.create(
	    		figureFacet.getDiagram(),
	        useSubject,
	        reference,
	        figureFacet.getFigureReference(),
	        factory,
	        location,
	        properties,
	        relatedSubject);
	    
	    ContainerAddTransaction.add(figureFacet.getContainerFacet(), new FigureReference[]{reference});
		}
	}

	public FeatureCompartmentGem(int containsFeatureType)
	{
		this.containsFeatureType = containsFeatureType;
		this.deletedUuids.addAll(deletedUuids);
	}
	
	public void setContentsCanMoveContainers(boolean contentsCanMoveContainers)
	{
	  this.contentsCanMoveContainers = contentsCanMoveContainers;
	}
	
	public FeatureCompartmentGem(PersistentFigure pfig, int featureType)
	{
		containsFeatureType = featureType;
		interpretPersistentFigure(pfig);
	}
	
	private void interpretPersistentFigure(PersistentFigure pfig)
	{
		PersistentProperties properties = pfig.getProperties();
		addedUuids = new HashSet<String>(properties.retrieve("addedUuids", "").asStringCollection());		
		deletedUuids = new HashSet<String>(properties.retrieve("deletedUuids", "").asStringCollection());
	}
	
	public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
	{
		this.figureFacet = figureFacet;
		figureFacet.registerDynamicFacet(addFacet, FeatureAddFacet.class);
		figureFacet.registerDynamicFacet(acceptorFacet, FeatureAcceptorFacet.class);
		figureFacet.registerDynamicFacet(compartmentFacet, FeatureCompartmentFacet.class);
	}
	
	public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
	{
		return appearanceFacet;
	}
	
	public BasicNodeContainerFacet getBasicNodeContainerFacet()
	{
		return containerFacet;
	}
	
	public FeatureCompartmentFacet getFeatureCompartmentFacet()
	{
		return compartmentFacet;
	}
	
  private Iterator getSortedOperations()
  {
		ArrayList<FigureFacet> sortedFeatures = new ArrayList<FigureFacet>(features);
  	sortFeatures(sortedFeatures);  	
  	return sortedFeatures.iterator();
  }
  
  private void sortFeatures(List<FigureFacet> features)
  {
    Collections.sort(features, new Comparator<FigureFacet>()
  	{
  		public int compare(FigureFacet f1, FigureFacet f2)
  		{
  			FeatureComparableFacet fc1 = f1.getDynamicFacet(FeatureComparableFacet.class);
  			FeatureComparableFacet fc2 = f2.getDynamicFacet(FeatureComparableFacet.class);
  			return fc1.compareTo(fc2);
  		}
  		
	    public boolean equals(Object obj)
  		{
				return false;
  		}
	    
	    @Override
	    public int hashCode()
	    {
	    	return super.hashCode();
	    }
  	});  // sort based on the y height parameter
  }

  private void sortPreviews(List<PreviewFacet> previews)
  {
    Collections.sort(previews, new Comparator<PreviewFacet>()
  	{
  		public int compare(PreviewFacet f1, PreviewFacet f2)
  		{
  			FeaturePreviewComparableFacet fc1 = f1.getDynamicFacet(FeaturePreviewComparableFacet.class);
  			FeaturePreviewComparableFacet fc2 = f2.getDynamicFacet(FeaturePreviewComparableFacet.class);
  			return fc1.compareTo(fc2);
  		}
  		
	    public boolean equals(Object obj)
  		{
				return false;
  		}
	    	    
	    @Override
	    public int hashCode()
	    {
	    	return super.hashCode();
	    }
  	});  // sort based on the y height parameter
  }
}

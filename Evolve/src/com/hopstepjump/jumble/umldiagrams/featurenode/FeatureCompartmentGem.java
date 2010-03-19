/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.jumble.umldiagrams.featurenode;


import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.containment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creation.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.repositorybase.*;
import com.sun.corba.se.spi.ior.*;

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
		 * @see com.hopstepjump.jumble.umldiagrams.classdiagram.featurenode.FeatureCompartmentFacet#getFigureFacet()
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
	  	Iterator iter = features.iterator();
	  	double height = 0;
	  	double width = 0;
	  	boolean haveAtLeastOneOp = false;
	  	while (iter.hasNext())
	  	{
	  		FigureFacet figure = (FigureFacet) iter.next();
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
  					  (FeaturePreviewComparableFacet) previewOp.getDynamicFacet(FeaturePreviewComparableFacet.class);
  					
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
					FeaturePreviewComparableFacet addOpComparable = (FeaturePreviewComparableFacet) addPreview.getDynamicFacet(FeaturePreviewComparableFacet.class);
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
			 * @see com.hopstepjump.jumble.umldiagrams.featurenode.FeatureCompartmentFacet#isEmpty()
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
					
				FeatureTypeFacet typeFacet = (FeatureTypeFacet) containables[lp].getFigureFacet().getDynamicFacet(FeatureTypeFacet.class);
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
		public Object addContents(ContainedFacet[] containables)
		{
			// make a list of the current containables
			List current = makeCurrentFeatures();
	
			// add the operations to the list -- acceptsContained() makes sure that these are the correct type
			for (int lp = 0; lp < containables.length; lp++)
			{
				features.add(containables[lp].getFigureFacet());
				containables[lp].setContainer(this);
			}
				
			// return the old list
			return current;
		}
	
		private List makeCurrentFeatures()
		{
			List<FigureReference> current = new ArrayList<FigureReference>();
			for (FigureFacet f : features)
				current.add(f.getFigureReference());
			return current;
		}
		
		private void restoreOldFeatures(List oldOps)
		{
			features = new HashSet<FigureFacet>();
			Iterator iter = oldOps.iterator();
			while (iter.hasNext())
			{
				FigureReference ref = (FigureReference) iter.next();

				FigureFacet figure = GlobalDiagramRegistry.registry.retrieveFigure(ref);
				ContainedFacet port = figure.getContainedFacet();
				if (port != null)
				{
					port.setContainer(this);
					features.add(figure);
				}
			}
		}

		/*
		 * @see CmdContainerable#removeContainables(CmdContainable[])
		 */
		public Object removeContents(ContainedFacet[] containables)
		{
			// make a list of the current containables
			List current = makeCurrentFeatures();
	
			// remove the operations from the list
			for (int lp = 0; lp < containables.length; lp++)
			{
				containables[lp].setContainer(null);
				features.remove(containables[lp].getFigureFacet());
			}
			
			// return the old list
			return current;
		}
	
		/*
		 * @see CmdContainerable#unAddContainables(Object)
		 */
		public void unAddContents(Object memento)
		{
			restoreOldFeatures((List) memento);
		}
	
		/*
		 * @see CmdContainerable#unRemoveContainables(Object)
		 */
		public void unRemoveContents(Object memento)
		{
			restoreOldFeatures((List) memento);
			// set the positions of the operations
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
			Iterator iter = getSortedOperations();
	    {
	    	int sortPosition = 0;
	      while (iter.hasNext())
	      {
	        FigureFacet contained = (FigureFacet) iter.next();
	        PreviewFacet preview = previewCache.getCachedPreviewOrMakeOne(contained);
	        ((FeaturePreviewComparableFacet) preview.getDynamicFacet(FeaturePreviewComparableFacet.class)).setOriginalSortPosition(sortPosition++);
	      }
	    }
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
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
		 * @see com.hopstepjump.jumble.foundation.FigureFacet#cleanUp()
		 */
		public void cleanUp()
		{
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
			return NULL_COMPARTMENT_EXTENT;
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
			return new UBounds(figureFacet.getFullBounds().getTopLeftPoint(), compartmentFacet.getMinimumExtent());
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
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
		public Command middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
		  return null;
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

    public Command getPostContainerDropCommand()
    {
      return null;
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
	    figureFacet.performResizingTransaction(figureFacet.getFullBounds());
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
	
	public FeatureCompartmentGem(PersistentProperties properties, int featureType)
	{
		containsFeatureType = featureType;
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
  			FeatureComparableFacet fc1 = (FeatureComparableFacet) f1.getDynamicFacet(FeatureComparableFacet.class);
  			FeatureComparableFacet fc2 = (FeatureComparableFacet) f2.getDynamicFacet(FeatureComparableFacet.class);
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
  			FeaturePreviewComparableFacet fc1 = (FeaturePreviewComparableFacet) f1.getDynamicFacet(FeaturePreviewComparableFacet.class);
  			FeaturePreviewComparableFacet fc2 = (FeaturePreviewComparableFacet) f2.getDynamicFacet(FeaturePreviewComparableFacet.class);
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

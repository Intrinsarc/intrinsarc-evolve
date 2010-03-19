package com.hopstepjump.jumble.umldiagrams.connectorarc;

import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.adjust.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.previewsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;
import com.hopstepjump.jumble.umldiagrams.linkedtextnode.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * to fix:
 * 1. finshed --> text is not hidden correctly, or reinstated correctly after deletion
 * 2. finished --> text does not move when line moves because 2 anchors move
 * 3. finshed --> text does not move when line changes shape
 * 4. text should be hidden initially, and be located at a point away from the line initially
 * 5. not all previews are shown...
 * 
 * @author andrew
 */
public class OldConnectorArcAppearanceGem implements Gem
{
  final static String figureName = "connector link";
  private BasicArcAppearanceFacet appearanceFacet = new BasicArcAppearanceFacetImpl();
  private AdvancedArcFacet advancedFacet = new AdvancedArcFacetImpl();
  private ContainerFacet containerFacet = new ContainerFacetImpl();
  private LinkedTextOriginFacet linkedTextOriginFacet = new LinkedTextOriginFacetImpl();
  private BasicArcAppearanceFacet delegatingAppearanceFacet;
  private FigureFacet figureFacet;
  private String name;
  private String startMultiplicity;
  private String endMultiplicity;

  private LinkedTextFacet linkedTextFacet;
  private FigureFacet text;
  private LinkedTextFacet startLinkedTextFacet;
  private FigureFacet startText;
  private LinkedTextFacet endLinkedTextFacet;
  private FigureFacet endText;
  
  public OldConnectorArcAppearanceGem(PersistentProperties properties)
  {
    if (properties == null)
      properties = new PersistentProperties();

    properties.addIfNotThere(new PersistentProperty("name", ""));
    name = properties.retrieve("name").asString();

    properties.addIfNotThere(new PersistentProperty("start", "start"));
    startMultiplicity = properties.retrieve("start").asString();

    properties.addIfNotThere(new PersistentProperty("end", "end"));
    endMultiplicity = properties.retrieve("end").asString();
  }
  
  public void connectDelegatingBasicArcAppearanceFacet(BasicArcAppearanceFacet facet)
  {
    this.delegatingAppearanceFacet = facet;
  }
  
  
  class LinkedTextOriginFacetImpl implements LinkedTextOriginFacet
  {
    public UPoint getMajorPoint(int majorPointType)
    {
      return figureFacet.getLinkingFacet().getMajorPoint(majorPointType);
    }

		public String textChanged(String newText, int majorPointType)
		{
			return newText;
		}    
  }
  
  class AdvancedArcFacetImpl implements AdvancedArcFacet
  {
    public void addOnePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, ActualArcPoints actualArcPoints, UPoint start, boolean focus, boolean curved, boolean offsetPointsWhenMoving)
    {
      BasicArcPreviewGem moving = new BasicArcPreviewGem(
	      	diagram, figureFacet.getLinkingFacet(), actualArcPoints, start, offsetPointsWhenMoving, curved);
      DependencyArcContainerPreviewGem connectorGem =
        new DependencyArcContainerPreviewGem();
      connectorGem.connectPreviewCacheFacet(previews);
      connectorGem.connectPreviewFacet(moving.getPreviewFacet());
      moving.connectBasicContainerPreviewFacet(connectorGem.getBasicArcContainerPreviewFacet());
	    PreviewFacet previewFacet = moving.getPreviewFacet();
	  
	      // important to add this as soon as possible to terminate any recursion looking for it from nodes
	      previews.addPreviewToCache(figureFacet, previewFacet);
	  
	      // now we can set the outgoings
	      previewFacet.setOutgoingsToPeripheral(
	          figureFacet.getLinkingFacet().hasOutgoingsToPeripheral(previews));
	  
	      AnchorFacet node1 = actualArcPoints.getNode1();
	      AnchorFacet node2 = actualArcPoints.getNode2();
	      moving.getBasicArcPreviewFacet().setLinkablePreviews(
	          getPreviewFigureForToolLinkable(previews, node1),
	          getPreviewFigureForToolLinkable(previews, node2));
    }

    private AnchorPreviewFacet getPreviewFigureForToolLinkable(PreviewCacheFacet previewFigures, AnchorFacet initial)
    {
      return previewFigures.getCachedPreviewOrMakeOne(initial.getFigureFacet()).getAnchorPreviewFacet();
    }
    
    public Manipulators getSelectionManipulators(
    		ToolCoordinatorFacet coordinator, 
        DiagramViewFacet diagramView,
        boolean favoured,
        boolean firstSelected,
        boolean allowTYPE0Manipulators,
        CalculatedArcPoints calculatedPoints,
        boolean curved)
    {
		  ManipulatorFacet keyFocus = null;
		  if (favoured)
      {
        keyFocus = linkedTextFacet.getTextEntryManipulator(coordinator, diagramView);
      }
		    
      Manipulators manips = new Manipulators(
	      	keyFocus,
	      	new ArcAdjustManipulatorGem(
	      			coordinator,
	      	    figureFacet.getLinkingFacet(),
	      	    diagramView,
	      	    calculatedPoints,
	      	    curved,
	      	    firstSelected).getManipulatorFacet());

      return manips;
    }
  }
  
  class ContainerFacetImpl implements ContainerFacet
  {
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
		 */
		public void setShowingForChildren(boolean showing)
		{
			// if we are showing, don't show any suppressed children
			text.setShowing(showing && !linkedTextFacet.isHidden());
      startText.setShowing(showing && !startLinkedTextFacet.isHidden());
      endText.setShowing(showing && !endLinkedTextFacet.isHidden());
		}
		
		/**
		 * container related code
		 */
		public boolean insideContainer(UPoint point)
		{
			return false;
		}
		
		/** returns true if area sweep in the container bounds is supported */
		public boolean isWillingToActAsBackdrop()
		{
			return false;
		}
		
		public void removeContents(ContainedFacet[] containables)
		{
		}
		
		public void addContents(ContainedFacet[] containables)
		{
		}
		
		public Iterator<FigureFacet> getContents()
		{
			List<FigureFacet> cont = new ArrayList<FigureFacet>();
			cont.add(text);
      cont.add(startText);
      cont.add(endText);
			return cont.iterator();
		}
		
		/**
		 * @see com.giroway.jumble.figurefacilities.containmentbase.IContainerable#getAcceptingSubcontainer(CmdContainable[])
		 */
		public ContainerFacet getAcceptingSubcontainer(ContainedFacet[] containables)
		{
			return null;
		}
		
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}
		
		/** many containers can also be contained */
		public ContainedFacet getContainedFacet()
		{
			return figureFacet.getContainedFacet();
		}
		
		/**
		 * @see com.hopstepjump.jumble.foundation.ContainerFacet#addChildPreviewsToCache(PreviewCacheFacet)
		 */
		public void addChildPreviewsToCache(PreviewCacheFacet previewCache)
		{
		  previewCache.getCachedPreviewOrMakeOne(text);
      previewCache.getCachedPreviewOrMakeOne(startText);
      previewCache.getCachedPreviewOrMakeOne(endText);
		}
		
		public void persistence_addContained(FigureFacet contained)
		{
			String containedName = contained.getContainedFacet().persistence_getContainedName();
			
			// set up the linked texts
			if (containedName.equals("text"))
			{
				// make the attribute compartment
				text = contained;
				text.getContainedFacet().persistence_setContainer(this);
				linkedTextFacet = (LinkedTextFacet) contained.getDynamicFacet(LinkedTextFacet.class);
      }
      if (containedName.equals("startText"))
      {
        // make the attribute compartment
        startText = contained;
        startText.getContainedFacet().persistence_setContainer(this);
        startLinkedTextFacet = (LinkedTextFacet) contained.getDynamicFacet(LinkedTextFacet.class);
      }
      if (containedName.equals("endText"))
      {
        // make the attribute compartment
        endText = contained;
        endText.getContainedFacet().persistence_setContainer(this);
        endLinkedTextFacet = (LinkedTextFacet) contained.getDynamicFacet(LinkedTextFacet.class);
      }
		}
		
		/**
		 * @see com.giroway.jumble.figurefacilities.containmentbase.ToolContainerFigure#directlyAcceptsContainables()
		 */
		public boolean directlyAcceptsItems()
		{
			return false;
		}

		public void cleanUp()
		{
		}
	}
  
  
  private class BasicArcAppearanceFacetImpl implements BasicArcAppearanceFacet
  {
    public BasicArcAppearanceFacetImpl()
    {
    }

  	public ZNode formAppearance(
  		ZShape mainArc,
  		UPoint start,
  		UPoint second,
  		UPoint secondLast,
  		UPoint last,
  		CalculatedArcPoints calculated, boolean curved)
    {
      if (delegatingAppearanceFacet != null)
        return
          delegatingAppearanceFacet.formAppearance(mainArc, start, second, secondLast, last, calculated, false);
      else
        return new ZVisualLeaf(mainArc);
    }
    
  	/**
  	 * @see com.hopstepjump.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
  	 */
  	public String getFigureName()
  	{
      if (delegatingAppearanceFacet != null)
        return delegatingAppearanceFacet.getFigureName();
      else
        return figureName;
  	}

  	/**
  	 * @see com.hopstepjump.idraw.arcfacilities.arcsupport.BasicArcAppearanceFacet#addToPersistentProperties(PersistentProperties)
  	 */
  	public void addToPersistentProperties(PersistentProperties properties)
  	{
      if (delegatingAppearanceFacet != null)
        delegatingAppearanceFacet.addToPersistentProperties(properties);
  	}

//    public boolean wantsAnchorDisplayStyle(String style, AnchorFacet anchor)
//    {
//      if (delegatingAppearanceFacet != null)
//      return false;
//    }
    
    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
      if (!figureFacet.isSubjectReadOnlyInDiagramContext(false))
      {
        menu.add(linkedTextFacet.getViewLabelMenuItem(coordinator, "connector"));      
        menu.add(startLinkedTextFacet.getViewLabelMenuItem(coordinator, "start multiplicity"));  
        menu.add(endLinkedTextFacet.getViewLabelMenuItem(coordinator, "end multiplicity"));
      }
      if (delegatingAppearanceFacet != null)
        delegatingAppearanceFacet.addToContextMenu(menu, diagramView, coordinator);
    }
    
		public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
		{
			return true;
		}

		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
		}

		public Object getSubject()
		{
			return null;
		}

		public boolean hasSubjectBeenDeleted()
		{
			return false;
		}

		public Command makeReanchorCommand(AnchorFacet start, AnchorFacet end)
		{
			return null;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return false;
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
      return null;
    }
  }

  /**
   * @return
   */
  public BasicArcAppearanceFacet getBasicArcAppearanceFacet()
  {
    return appearanceFacet;
  }
  
  public void connectFigureFacet(FigureFacet figureFacet, PersistentProperties properties)
  {
    this.figureFacet = figureFacet;
    figureFacet.registerDynamicFacet(linkedTextOriginFacet, LinkedTextOriginFacet.class);
    createLinkedText();
  }
  
  /**
   * 
   */
  private static final UDimension LINKED_TEXT_OFFSET = new UDimension(-16, -16);
  private void createLinkedText()
  {
    DiagramFacet diagram = figureFacet.getDiagram();
    
    // make the text
    makeStartText(diagram);
    makeMiddleText(diagram);
    makeEndText(diagram);
  }

  private void makeEndText(DiagramFacet diagram)
  {
    String reference = figureFacet.getFigureReference().getId();
    // make the end text
    {
      UPoint location = figureFacet.getLinkingFacet().getMajorPoint(CalculatedArcPoints.MAJOR_POINT_END);
      BasicNodeGem basicLinkedTextGem = new BasicNodeGem(
          LinkedTextCreatorGem.RECREATOR_NAME,
          diagram,
          reference + "E",
          Grid.roundToGrid(location.add(LINKED_TEXT_OFFSET)),
          true,
          "endText",
          true);
      LinkedTextGem linkedTextGem = new LinkedTextGem(endMultiplicity, true, CalculatedArcPoints.MAJOR_POINT_END);
      basicLinkedTextGem.connectBasicNodeAppearanceFacet(linkedTextGem.getBasicNodeAppearanceFacet());
      linkedTextGem.connectBasicNodeFigureFacet(basicLinkedTextGem.getBasicNodeFigureFacet());
      
      // decorate the clipboard facet
      basicLinkedTextGem.connectClipboardCommandsFacet(linkedTextGem.getClipboardCommandsFacet());
  
      endText = basicLinkedTextGem.getBasicNodeFigureFacet();
      endText.getContainedFacet().persistence_setContainer(containerFacet);
      endLinkedTextFacet = linkedTextGem.getLinkedTextFacet();
    }
  }

  private void makeStartText(DiagramFacet diagram)
  {
    String reference = figureFacet.getFigureReference().getId();
    // make the start text
    {
      UPoint location = figureFacet.getLinkingFacet().getMajorPoint(CalculatedArcPoints.MAJOR_POINT_START);
      BasicNodeGem basicLinkedTextGem = new BasicNodeGem(
          LinkedTextCreatorGem.RECREATOR_NAME,
          diagram,
          reference + "S",
          Grid.roundToGrid(location.add(LINKED_TEXT_OFFSET)),
          true,
          "startText",
          true);
      LinkedTextGem linkedTextGem = new LinkedTextGem(startMultiplicity, true, CalculatedArcPoints.MAJOR_POINT_START);
      basicLinkedTextGem.connectBasicNodeAppearanceFacet(linkedTextGem.getBasicNodeAppearanceFacet());
      linkedTextGem.connectBasicNodeFigureFacet(basicLinkedTextGem.getBasicNodeFigureFacet());
      
      // decorate the clipboard facet
      basicLinkedTextGem.connectClipboardCommandsFacet(linkedTextGem.getClipboardCommandsFacet());
  
      startText = basicLinkedTextGem.getBasicNodeFigureFacet();
      startText.getContainedFacet().persistence_setContainer(containerFacet);
      startLinkedTextFacet = linkedTextGem.getLinkedTextFacet();
    }
  }

  private void makeMiddleText(DiagramFacet diagram)
  {
    String reference = figureFacet.getFigureReference().getId();

    {
      UPoint location = figureFacet.getLinkingFacet().getMajorPoint(CalculatedArcPoints.MAJOR_POINT_MIDDLE);
      BasicNodeGem basicLinkedTextGem = new BasicNodeGem(
          LinkedTextCreatorGem.RECREATOR_NAME,
          diagram,
          reference + "_M",
          Grid.roundToGrid(location.add(LINKED_TEXT_OFFSET)),
          true,
          "text",
          true);
    	LinkedTextGem linkedTextGem = new LinkedTextGem(name, true, CalculatedArcPoints.MAJOR_POINT_MIDDLE);
  		basicLinkedTextGem.connectBasicNodeAppearanceFacet(linkedTextGem.getBasicNodeAppearanceFacet());
  		linkedTextGem.connectBasicNodeFigureFacet(basicLinkedTextGem.getBasicNodeFigureFacet());
      
      // decorate the clipboard facet
      basicLinkedTextGem.connectClipboardCommandsFacet(linkedTextGem.getClipboardCommandsFacet());
  
  		text = basicLinkedTextGem.getBasicNodeFigureFacet();
  		text.getContainedFacet().persistence_setContainer(containerFacet);
  		linkedTextFacet = linkedTextGem.getLinkedTextFacet();
    }
  }

  public ContainerFacet getContainerFacet()
  {
    return containerFacet;
  }
  
  public AdvancedArcFacet getAdvancedArcFacet()
  {
    return advancedFacet;
  }
}

package com.hopstepjump.jumble.umldiagrams.tracearc;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.adjust.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.previewsupport.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;
import com.hopstepjump.jumble.umldiagrams.linkedtextnode.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author andrew
 */
public class TraceArcGem implements Gem
{
  public static final String LEFT_GUILLEMET = "\u00AB";
  public static final String RIGHT_GUILLEMET = "\u00BB";
  static final String FIGURE_NAME = "trace";
  private BasicArcAppearanceFacet basicArcAppearanceFacet = new BasicArcAppearanceFacetImpl();
  private AdvancedArcFacet advancedFacet = new AdvancedArcFacetImpl();
  private LinkedTextOriginFacet linkedTextOriginFacet = new LinkedTextOriginFacetImpl();
  private ContainerFacet containerFacet = new ContainerFacetImpl();
  private FigureFacet figureFacet;
  private Dependency subject;
  private String name = "";
  private LinkedTextFacet linkedTextFacet;
  private FigureFacet text;
  private Color color = Color.BLACK;


  public TraceArcGem(PersistentFigure pfig)
  {
  	interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
    subject = (Dependency) pfig.getSubject();
    PersistentProperties properties = pfig.getProperties();
    color = properties.retrieve("color", Color.BLACK).asColor();
	}

	public BasicArcAppearanceFacet getBasicArcAppearanceFacet()
  {
    return basicArcAppearanceFacet;
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
    figureFacet.registerDynamicFacet(linkedTextOriginFacet, LinkedTextOriginFacet.class);
    createLinkedText();
  }
  
  public ContainerFacet getContainerFacet()
  {
    return containerFacet;
  }
	
  public AdvancedArcFacet getAdvancedArcFacet()
  {
    return advancedFacet;
  }
  
  /**
   * 
   */
  private static final UDimension LINKED_TEXT_OFFSET = new UDimension(-16, -16);
  private void createLinkedText()
  {
    DiagramFacet diagram = figureFacet.getDiagram();
    
    // make the text
    makeMiddleText(diagram);
  }
  
  private void makeMiddleText(DiagramFacet diagram)
  {
    String reference = figureFacet.getFigureReference().getId();
    UPoint location = figureFacet.getLinkingFacet().getMajorPoint(CalculatedArcPoints.MAJOR_POINT_MIDDLE);
    BasicNodeGem basicLinkedTextGem = new BasicNodeGem(
        LinkedTextCreatorGem.RECREATOR_NAME,
        diagram,
        reference + "_M",
        Grid.roundToGrid(location.add(LINKED_TEXT_OFFSET)),
        true,
        "text",
        true);
  	LinkedTextGem linkedTextGem = new LinkedTextGem(name, false, CalculatedArcPoints.MAJOR_POINT_MIDDLE);
		linkedTextGem.addPrefix(LEFT_GUILLEMET + "trace" + RIGHT_GUILLEMET);
		basicLinkedTextGem.connectBasicNodeAppearanceFacet(linkedTextGem.getBasicNodeAppearanceFacet());
		linkedTextGem.connectBasicNodeFigureFacet(basicLinkedTextGem.getBasicNodeFigureFacet());
    
    // decorate the clipboard facet
    basicLinkedTextGem.connectClipboardCommandsFacet(linkedTextGem.getClipboardCommandsFacet());

		text = basicLinkedTextGem.getBasicNodeFigureFacet();
		text.getContainedFacet().persistence_setContainer(containerFacet);
		linkedTextFacet = linkedTextGem.getLinkedTextFacet();
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

  
  class LinkedTextOriginFacetImpl implements LinkedTextOriginFacet
  {
    public UPoint getMajorPoint(int majorPointType)
    {
      return figureFacet.getLinkingFacet().getMajorPoint(majorPointType);
    }

		public String textChanged(String newText, int majorPointType)
		{
			// if the text has changed, possibly look at changing the model
			String stripped = newText.trim();
			if (subject != null && !stripped.equals(subject.getName()))
				subject.setName(stripped);
			return stripped;
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
  
  class BasicArcAppearanceFacetImpl implements BasicArcAppearanceFacet
  {
    public ZNode formAppearance(
      ZShape mainArc,
      UPoint start,
      UPoint second,
      UPoint secondLast,
      UPoint last,
      CalculatedArcPoints calculated, boolean curved)
    {
      return DependencyArcGem.formDependencyAppearance(mainArc, start, second, secondLast, last, calculated, color);
    }

    /**
     * @see com.hopstepjump.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
     */
    public String getFigureName()
    {
      return FIGURE_NAME;
    }

    /**
     * @see com.hopstepjump.idraw.arcfacilities.arcsupport.BasicArcAppearanceFacet#addToPersistentProperties(PersistentProperties)
     */
    public void addToPersistentProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("color", color, Color.BLACK));
    }

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
      menu.add(linkedTextFacet.getViewLabelMenuItem(coordinator, "dependency"));

      Utilities.addSeparator(menu);
      // only add a replace if this is not visually at home
    	if (getVisualOwner(figureFacet) != getOwner(subject))
      {
        JMenuItem replaceItem = getReplaceItem(diagramView, coordinator);
        menu.add(replaceItem);
        Utilities.addSeparator(menu);
      }
    }
    
    private JMenuItem getReplaceItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      // for adding operations
      JMenuItem replace = new JMenuItem("Replace");
      replace.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
        	Dependency replace = (Dependency) figureFacet.getSubject();
          coordinator.startTransaction("replaced trace", "removed replaced trace");
          Dependency replacement = createDeltaReplacedTrace(getVisualOwner(figureFacet), replace);
          
          // change the subject so we don't lose the graphical view
          TraceCreatorGem gem = new TraceCreatorGem();
          PersistentFigure pfig = figureFacet.makePersistentFigure();
          pfig.setSubject(replacement);
          DiagramFacet diagram = figureFacet.getDiagram();
          FigureReference reference = diagram.makeNewFigureReference();
          gem.getArcCreateFacet().create(
          		replacement,
          		diagram,
          		reference.getId(),
          		figureFacet.getLinkingFacet().getCalculated().getReferenceCalculatedArcPoints(diagram),
          		figureFacet.makePersistentFigure().getProperties());
          coordinator.commitTransaction(true);
          
          // select the replaced figure
          diagramView.getSelection().clearAllSelection();
          diagramView.getSelection().addToSelection(diagram.retrieveFigure(reference.getId()));
        }
      });

      return replace;
    }
    
    private Dependency createDeltaReplacedTrace(Class owner, Dependency replace)
    { 
    	DeltaReplacedTrace replacement = owner.createDeltaReplacedTraces();
      replacement.setReplaced(replace);
      
      Dependency next = (Dependency) replacement.createReplacement(UML2Package.eINSTANCE.getDependency());
      next.setTrace(true);
      next.settable_getClients().add(owner);
      next.setDependencyTarget(replace.getDependencyTarget());
      replacement.setReplacement(next);
      
      return next;
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
    	// ensure we are even authorised...
			if (getOwner(subject) != getVisualOwner(figureFacet))
				return false;

      return start.getFigureFacet() == figureFacet.getLinkingFacet().getAnchor1().getFigureFacet() && TraceCreatorGem.acceptsOneOrBothAnchors(start, end);
    }
    
    private Class getOwner(Dependency subject)
    {
    	if (subject.getOwner() instanceof DeltaReplacedTrace)
    		return (Class) ((DeltaReplacedTrace) subject.getOwner()).getOwner();
    	return (Class) subject.getOwner();
    }
    
    private Class getVisualOwner(FigureFacet figure)
    {
    	FigureFacet owner = figure.getLinkingFacet().getAnchor1().getFigureFacet();
    	return (Class) owner.getSubject();
    }
    
    private DEElement getVisualTarget()
    {
    	FigureFacet target = figureFacet.getLinkingFacet().getAnchor2().getFigureFacet();
    	DEElement de = GlobalDeltaEngine.engine.locateObject(target.getSubject()).asElement();
    	return de.getSubstitutesOrSelf().iterator().next().asElement();
    }

    private FigureFacet getOwnerFigure()
    {
    	return figureFacet.getLinkingFacet().getAnchor1().getFigureFacet();
    }
    
    public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
    {
      if (pass != ViewUpdatePassEnum.LAST)
        return;
      
			// is this in the correct place?
			// first, ensure that the element is included
			IDeltaEngine engine = GlobalDeltaEngine.engine;
			DEComponent decomp = engine.locateObject(getVisualOwner(figureFacet)).asComponent();
      Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), getOwnerFigure().getContainerFacet());
      DEStratum perspective = engine.locateObject(visualHome).asStratum();
      
      boolean found = false;
      DEElement visualTarget = getVisualTarget();
      for (DeltaPair pair : decomp.getDeltas(ConstituentTypeEnum.DELTA_TRACE).getConstituents(perspective))
      {
      	if (pair.getConstituent().getUuid().equals(subject.getUuid()))
      	{
      		// ensure this links to the correct element
      		if (pair.getConstituent().asTrace().getTarget().getSubstitutesOrSelf().iterator().next() == visualTarget)
      			found = true;
      		break;
      	}
      }
			
      if (!found)
      	figureFacet.formDeleteTransaction();

      // possibly update the name
      if (!name.equals(linkedTextFacet.getText()))
      {
  			SetTextTransaction.set(
  			    linkedTextFacet.getFigureFacet(),
  			    subject.getName(),
  			    null,
  			    false);
      }
    }

    public Object getSubject()
    {
      return subject;
    }

    public boolean hasSubjectBeenDeleted()
    {
      return subject.isThisDeleted();
    }

    public void makeReanchorAction(AnchorFacet start, AnchorFacet end)
    {
      NamedElement newSupplier = (NamedElement) end.getFigureFacet().getSubject();
      setTarget(newSupplier);
    }

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
    	return null;
    }

		public void acceptPersistentProperties(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }  
  
  private void setTarget(NamedElement element)
  {
    // change the owner, but make sure we take the original
    DEElement elem = GlobalDeltaEngine.engine.locateObject(element).asElement().getSubstitutesOrSelf().iterator().next();
    subject.setDependencyTarget((NamedElement) elem.getRepositoryObject());  	
  }
}


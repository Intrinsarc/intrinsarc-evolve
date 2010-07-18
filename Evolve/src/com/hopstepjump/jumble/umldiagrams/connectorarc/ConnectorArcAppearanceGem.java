package com.hopstepjump.jumble.umldiagrams.connectorarc;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.adjust.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.previewsupport.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.jumble.umldiagrams.linkedtextnode.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

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
public class ConnectorArcAppearanceGem implements Gem
{
  private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

  final static String figureName = "connector link";
  private BasicArcAppearanceFacet appearanceFacet = new BasicArcAppearanceFacetImpl();
  private AdvancedArcFacet advancedFacet = new AdvancedArcFacetImpl();
  private ContainerFacet containerFacet = new ContainerFacetImpl();
  private LinkedTextOriginFacet linkedTextOriginFacet = new LinkedTextOriginFacetImpl();
  private FigureFacet figureFacet;
  private Connector subject;
  private LinkedTextFacet linkedTextFacet;
  private FigureFacet text;
  private LinkedTextFacet startLinkedTextFacet;
  private FigureFacet startText;
  private LinkedTextFacet endLinkedTextFacet;
  private FigureFacet endText;
  boolean delegate;
  boolean directed;
  boolean portLink;
  private ClipboardActionsFacet clipboardCommandsFacet = new ClipboardActionsFacetImpl();  
  
  public ConnectorArcAppearanceGem(PersistentFigure pfig)
  {
  	interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
		subject = (Connector) pfig.getSubject();
    directed = pfig.getProperties().retrieve("directed", false).asBoolean();
    portLink = pfig.getProperties().retrieve("portLink", false).asBoolean();
    delegate = pfig.getProperties().retrieve("delegate", false).asBoolean();
	}

	public ClipboardActionsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
  }
  
  private class ClipboardActionsFacetImpl implements ClipboardActionsFacet
  {
    public boolean hasSpecificDeleteAction()
    {
      return false;
    }

    public void makeSpecificDeleteAction()
    {
    }
    
    public void performPostDeleteAction()
    {
      // important to use the reference rather than the figure, which gets recreated...
      String uuid = FeatureNodeGem.getOriginalSubject(subject).getUuid();      
      getSimpleDeletedUuidsFacet().addDeleted(uuid);
    }

	  private SimpleDeletedUuidsFacet getSimpleDeletedUuidsFacet()
	  {
	    // follow one anchor up until we find the classifier, and then look for the simple deleted uuids facet 
	    FigureFacet clsFigure = ClassifierConstituentHelper.extractVisualClassifierFigureFromConnector(figureFacet);
	    return clsFigure.getDynamicFacet(SimpleDeletedUuidsFacet.class);
	  }

    public boolean hasSpecificKillAction()
    {
      return isOutOfPlace() || !atHome();
    }

    /** returns true if the element is out of place */
    private boolean isOutOfPlace()
    {
      return ClassifierConstituentHelper.extractVisualClassifierFromConnector(figureFacet) != getSubjectAsElement().getOwner();
    }

    public void makeSpecificKillAction(ToolCoordinatorFacet coordinator)
    {
      // only allow changes in the home stratum
      if (!atHome())
      {
        coordinator.displayPopup(ERROR_ICON, "Delta error",
            new JLabel("Must be in home stratum to delete subjects!", DELTA_ICON, JLabel.LEFT),
            ScreenProperties.getUndoPopupColor(),
            Color.black,
            3000);
      }

      // if this is a replace, kill the replace delta
      Element feature = getSubjectAsElement();
      if (feature.getOwner() instanceof DeltaReplacedConstituent &&
          feature.getOwner().getOwner() == ClassifierConstituentHelper.extractVisualClassifierFromConnector(figureFacet))
        generateReplaceDeltaKill(coordinator);
      else
        generateDeleteDelta(coordinator);
    }

    private void generateReplaceDeltaKill(ToolCoordinatorFacet coordinator)
    {
      // generate a delete delta for the replace
      coordinator.displayPopup(null, null,
          new JLabel("Removed replace delta", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      final Element feature = getSubjectAsElement();      
      GlobalSubjectRepository.repository.incrementPersistentDelete(feature.getOwner());            
    }

    private void generateDeleteDelta(ToolCoordinatorFacet coordinator)
    {
      // generate a delete delta
      coordinator.displayPopup(null, null,
          new JLabel("Delta delete", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      generateDeleteDelta(coordinator, ClassifierConstituentHelper.extractVisualClassifierFromConnector(figureFacet));      
    }
    
    public void generateDeleteDelta(ToolCoordinatorFacet coordinator, final Classifier owner)
    {
      // add this to the classifier as a delete delta
      if (owner instanceof ClassImpl)
      {
        final Element feature = FeatureNodeGem.getOriginalSubject(figureFacet.getSubject());
      	DeltaDeletedConstituent delete;
        delete = ((Class) owner).createDeltaDeletedConnectors();
        delete.setDeleted(feature);
      }
    }

    private boolean atHome()
    {
      // are we at home?
      Package home = GlobalSubjectRepository.repository.findOwningStratum(ClassifierConstituentHelper.extractVisualClassifierFromConnector(figureFacet));
      Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), ClassifierConstituentHelper.extractVisualClassifierFigureFromConnector(figureFacet).getContainerFacet());
      
      return home == visualHome;
    }
  }
  
  private static Pattern match = Pattern.compile("\\s*\\[\\s*(\\d*|\\+)\\s*\\]\\s*");
  private static Pattern matchAlpha = Pattern.compile("\\s*\\[\\s*(\\w*)\\s*\\]\\s*");
  class LinkedTextOriginFacetImpl implements LinkedTextOriginFacet
  {
    public UPoint getMajorPoint(int majorPointType)
    {
      return figureFacet.getLinkingFacet().getMajorPoint(majorPointType);
    }

		public String textChanged(String newText, int majorPointType)
		{
			switch (majorPointType)
			{
			case CalculatedArcPoints.MAJOR_POINT_MIDDLE:
				// if the text has changed, possibly look at changing the model
				String stripped = newText.trim();
				// causes outside of transaction error if we don't suppress when equal
				if (!stripped.equals(subject.getName()))
					subject.setName(stripped);
				return stripped;
				
			case CalculatedArcPoints.MAJOR_POINT_START:
				return handleEndChange(newText, 0, startLinkedTextFacet);

			case CalculatedArcPoints.MAJOR_POINT_END:
				return handleEndChange(newText, 1, endLinkedTextFacet);
			}
			return newText;
		}

		/**
		 * set the end lower multiplicity as the connection index
		 * @param newText
		 * @param index
		 * @param linkedTextFacet
		 * @return
		 */
		private String handleEndChange(String newText, int index, LinkedTextFacet linkedTextFacet)
		{
			// don't change if we don't have to
			if (newText.equals(getConnectionIndexString(index)))
				return newText;
			
			ConnectorEnd end = (ConnectorEnd) subject.getEnds().get(index);
			Matcher matcher = match.matcher(newText);
			Matcher matcherAlpha = matchAlpha.matcher(newText);
			if (matcher.matches())
			{
				LiteralInteger lower = (LiteralInteger) end.createLowerValue(UML2Package.eINSTANCE.getLiteralInteger());
				lower.setValue(new Integer(matcher.group(1)));
			}
			else
			if (matcherAlpha.matches())
			{
				Expression lower = (Expression) end.createLowerValue(UML2Package.eINSTANCE.getExpression());
				lower.setBody(matcherAlpha.group(1));				
			}
			else
				end.setLowerValue(null);
			
			return getConnectionIndexString(index);
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
        keyFocus = linkedTextFacet.getTextEntryManipulator(coordinator, diagramView);
		    
      Manipulators manips = new Manipulators(
	      	keyFocus,
	      	new ArcAdjustManipulatorGem(
	      			coordinator,
	      	    figureFacet.getLinkingFacet(),
	      	    diagramView,
	      	    calculatedPoints,
	      	    curved,
	      	    firstSelected).getManipulatorFacet());
      if (favoured)
      {
      	manips.addOther(startLinkedTextFacet.getTextEntryManipulator(coordinator, diagramView));
      	manips.addOther(endLinkedTextFacet.getTextEntryManipulator(coordinator, diagramView));
      }
      

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
				// make the middle text
				text = contained;
				text.getContainedFacet().persistence_setContainer(this);
				linkedTextFacet = contained.getDynamicFacet(LinkedTextFacet.class);
      }
      if (containedName.equals("startText"))
      {
        // make the start text
        startText = contained;
        startText.getContainedFacet().persistence_setContainer(this);
        startLinkedTextFacet = contained.getDynamicFacet(LinkedTextFacet.class);
      }
      if (containedName.equals("endText"))
      {
        // make the end text
        endText = contained;
        endText.getContainedFacet().persistence_setContainer(this);
        endLinkedTextFacet = contained.getDynamicFacet(LinkedTextFacet.class);
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
      if (!delegate)
      {
      	ZGroup group = new ZGroup();
      	group.addChild(new ZVisualLeaf(mainArc));
      	
      	if (portLink)
      		return formPortLinkAppearance(mainArc, start, second, secondLast, last, calculated);
      	else
      	if (directed)
      	{
	  	    // make the arrowhead
	  	    ZPolygon poly = new ZPolygon(last);
	  	    poly.setClosed(false);
	  	    poly.add(last.add(new UDimension(-5, -12)));
	  	    poly.add(last);
	  	    poly.add(last.add(new UDimension(5, -12)));
	  	    poly.setPenPaint(Color.black);
	  	    poly.setFillPaint(null);
	  	    poly.setStroke(new BasicStroke(0.7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
	  	    UDimension dimension = secondLast.subtract(last);
	  	    ZTransformGroup arrowGroup = new ZTransformGroup(new ZVisualLeaf(poly));
	  	    arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());
	
	  	    // add the thin line
	  	    group.addChild(new ZVisualLeaf(mainArc));
	
	  	    // add the arrow
	  	    arrowGroup.setChildrenFindable(false);
	  	    arrowGroup.setChildrenPickable(false);
	  	    group.addChild(arrowGroup);
      	}
        return group;        
      }
      else
      	return DependencyArcGem.formDependencyAppearance(
            mainArc, start, second, secondLast, last, calculated, Color.BLACK);
    }
    
    private ZNode formPortLinkAppearance(
        ZShape mainArc,
        UPoint start,
        UPoint second,
        UPoint secondLast,
        UPoint last,
        CalculatedArcPoints calculated)
    {
      // make the thin and thick lines
      ZGroup group = new ZGroup();

      mainArc.setStroke(
        new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));

      // make the arrowhead
      ZPolygon poly = new ZPolygon(last);
      poly.setClosed(false);
      poly.add(last.add(new UDimension(-5, -12)));
      poly.add(last);
      poly.add(last.add(new UDimension(5, -12)));
      poly.setFillPaint(null);
      poly.setStroke(new BasicStroke(0.7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
      UDimension dimension = secondLast.subtract(last);
      ZTransformGroup arrowGroup = new ZTransformGroup(new ZVisualLeaf(poly));
      arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());

      // make the arrowhead
      ZPolygon poly2 = new ZPolygon(start);
      poly2.setClosed(false);
      poly2.add(start.add(new UDimension(-5, -12)));
      poly2.add(start);
      poly2.add(start.add(new UDimension(5, -12)));
      poly2.setFillPaint(null);
      poly2.setStroke(new BasicStroke(0.7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
      UDimension dimension2 = second.subtract(start);
      ZTransformGroup arrowGroup2 = new ZTransformGroup(new ZVisualLeaf(poly2));
      arrowGroup2.rotate(dimension2.getRadians() + Math.PI/2, start.getX(), start.getY());

      // add the thin line
      group.addChild(new ZVisualLeaf(mainArc));

      // add the arrow
      arrowGroup.setChildrenFindable(false);
      arrowGroup.setChildrenPickable(false);
      group.addChild(arrowGroup);
      arrowGroup2.setChildrenFindable(false);
      arrowGroup2.setChildrenPickable(false);
      group.addChild(arrowGroup2);

      group.setChildrenFindable(false);
      group.setChildrenPickable(true);
      return group;
    }  

  	
  	/**
  	 * @see com.hopstepjump.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
  	 */
  	public String getFigureName()
  	{
      return figureName;
  	}

  	/**
  	 * @see com.hopstepjump.idraw.arcfacilities.arcsupport.BasicArcAppearanceFacet#addToPersistentProperties(PersistentProperties)
  	 */
  	public void addToPersistentProperties(PersistentProperties properties)
  	{
  		properties.add(new PersistentProperty("directed", directed, false));
  		properties.add(new PersistentProperty("portLink", portLink, false));
  		properties.add(new PersistentProperty("delegate", delegate, false));
  	}

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
      // only add a replace if this is not visually at home
      FigureFacet clsFigure = ClassifierConstituentHelper.extractVisualClassifierFigureFromConnector(figureFacet);
      Namespace visual = (Namespace) clsFigure.getSubject();
      Namespace real = (Namespace)
        GlobalSubjectRepository.repository.findOwningElement(
            getSubjectAsElement(),
            ClassifierImpl.class); 
          
      boolean enable =
      	visual != real &&
        !clsFigure.isSubjectReadOnlyInDiagramContext(false);

      JMenuItem replaceItem = getReplaceItem(diagramView, coordinator);
      menu.add(replaceItem);
      replaceItem.setEnabled(enable);
      Utilities.addSeparator(menu);
      
      menu.add(linkedTextFacet.getViewLabelMenuItem(coordinator, "connector"));      
      menu.add(startLinkedTextFacet.getViewLabelMenuItem(coordinator, "start multiplicity"));  
      menu.add(endLinkedTextFacet.getViewLabelMenuItem(coordinator, "end multiplicity"));
      if (!figureFacet.isSubjectReadOnlyInDiagramContext(false))
      {
        Utilities.addSeparator(menu);
        menu.add(makeDelegateVersusAssemblyMenuItem(coordinator));  
      }
    }
    
    public JMenuItem getReplaceItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      // for adding operations
      JMenuItem replaceConnectorItem = new JMenuItem("Replace");
      replaceConnectorItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          Connector replaced = (Connector) figureFacet.getSubject();
          Connector original = (Connector) ClassifierConstituentHelper.getOriginalSubject(replaced);
          final FigureFacet clsFigure = ClassifierConstituentHelper.extractVisualClassifierFigureFromConnector(figureFacet);
          final Class cls = (Class) clsFigure.getSubject();
          
          coordinator.startTransaction("replaced connector", "removed replaced connector");
          final DeltaReplacedConnector replacement = createDeltaReplacedConnector(cls, replaced, original);
          coordinator.commitTransaction(true);
          
          FigureFacet createdFeature = ClassifierConstituentHelper.findSubfigure(clsFigure, replacement.getReplacement());
          diagramView.getSelection().clearAllSelection();
          diagramView.getSelection().addToSelection(createdFeature, true);
        }
      });

      return replaceConnectorItem;
    }    
    
		private JMenuItem makeDelegateVersusAssemblyMenuItem(final ToolCoordinatorFacet coordinator)
		{
			JCheckBoxMenuItem item = new JCheckBoxMenuItem("Delegate");
			item.setSelected(delegate);
			item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							coordinator.startTransaction("changed connector kind", "restored connector kind");
							subject.setKind(delegate ?
									ConnectorKind.ASSEMBLY_LITERAL : ConnectorKind.DELEGATION_LITERAL);
							coordinator.commitTransaction();
						}
					});
			return item;
		}

		public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
		{
			return end != null && ConnectorCreatorGem.acceptsOneOrBothAnchors(start, end);
		}

		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
		  if (pass != ViewUpdatePassEnum.LAST)
		    return;
		  
      // possibly update the name
      if (!subject.getName().equals(linkedTextFacet.getText()))
  			SetTextTransaction.set(linkedTextFacet.getFigureFacet(), subject.getName(), null, false);
      
      // see if the connection ends are correct for the start
      if (!getConnectionIndexString(0).equals(startLinkedTextFacet.getText()))
      	SetTextTransaction.set(startLinkedTextFacet.getFigureFacet(), getConnectionIndexString(0), null, false);
      
      // see if the connection ends are correct for the end
      if (!getConnectionIndexString(1).equals(endLinkedTextFacet.getText()))
      	SetTextTransaction.set(endLinkedTextFacet.getFigureFacet(), getConnectionIndexString(1), null, false);
      
      // is this a different type of connector now?
      final boolean subjectIsDelegate = subject.getKind().equals(ConnectorKind.DELEGATION_LITERAL);
      final boolean subjectIsPortLink = subject.getKind().equals(ConnectorKind.PORT_LINK_LITERAL);
      final boolean isDirected = StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.DIRECTED);
      if (subjectIsDelegate != delegate || subjectIsPortLink != portLink || isDirected != directed)
      {
				delegate = subjectIsDelegate;
				portLink = subjectIsPortLink;
				directed = isDirected;
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

		public void makeReanchorAction(final AnchorFacet start, final AnchorFacet end)
		{
			// we are either reattaching to a new start or a new end
			// clear out the ends and regenerate
			SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
			
			ConnectorEnd oldStart;
			ConnectorEnd oldEnd;
			ConnectorEnd newStart;
			ConnectorEnd newEnd;

			oldStart = (ConnectorEnd) subject.undeleted_getEnds().get(0);
			oldEnd = (ConnectorEnd) subject.undeleted_getEnds().get(1);
			newStart = ConnectorCreatorGem.makeConnectorEnd(subject, start.getFigureFacet());
			newEnd = ConnectorCreatorGem.makeConnectorEnd(subject, end.getFigureFacet());
			subject.getEnds().add(newStart);
			subject.getEnds().add(newEnd);
			repository.incrementPersistentDelete(oldEnd);
			repository.incrementPersistentDelete(oldStart);

			newStart.setLowerValue(oldStart.getLowerValue());
			newEnd.setLowerValue(oldEnd.getLowerValue());
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      // look for the container classifier, which is more complex for connectors as arcs aren't "contained" by containers
      ContainerFacet container = ClassifierConstituentHelper.extractVisualClassifierFigureFromConnector(figureFacet).getContainerFacet();
      if (container == null)
        return true;
      
      // only truly writeable/moveable if this is owned by the same visual classifier
      // however, for a kill, this is fine
      if (!kill)
      {
        if (container.getFigureFacet().getSubject() !=
            FeatureNodeGem.getPossibleDeltaSubject(figureFacet.getSubject()).getOwner())
          return true;
      }
      
      // only writeable if the class is located correctly
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
      Set<String> styles = new HashSet<String>();
      styles.add("hideConnector");
      return styles;
    }

		public void acceptPersistentProperties(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }

  /**
   * @return
   */
  public BasicArcAppearanceFacet getBasicArcAppearanceFacet()
  {
    return appearanceFacet;
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
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
      LinkedTextGem linkedTextGem = new LinkedTextGem("", false, CalculatedArcPoints.MAJOR_POINT_END);
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
      LinkedTextGem linkedTextGem = new LinkedTextGem("", false, CalculatedArcPoints.MAJOR_POINT_START);
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
    	LinkedTextGem linkedTextGem = new LinkedTextGem("", false, CalculatedArcPoints.MAJOR_POINT_MIDDLE);
  		basicLinkedTextGem.connectBasicNodeAppearanceFacet(linkedTextGem.getBasicNodeAppearanceFacet());
  		linkedTextGem.connectBasicNodeFigureFacet(basicLinkedTextGem.getBasicNodeFigureFacet());
      
      // decorate the clipboard facet
      basicLinkedTextGem.connectClipboardCommandsFacet(linkedTextGem.getClipboardCommandsFacet());
  
  		text = basicLinkedTextGem.getBasicNodeFigureFacet();
  		text.getContainedFacet().persistence_setContainer(containerFacet);
  		linkedTextFacet = linkedTextGem.getLinkedTextFacet();
    }
  }
  
  public DeltaReplacedConnector createDeltaReplacedConnector(Class owner, Connector replaced, Connector original)
  { 
    DeltaReplacedConnector replacement = owner.createDeltaReplacedConnectors();
    replacement.setReplaced(original);
    Connector next = (Connector) replacement.createReplacement(UML2Package.eINSTANCE.getConnector());
    next.setKind(replaced.getKind());
    next.setName(replaced.getName());
    
    // copy the ends
    for (Object end : replaced.undeleted_getEnds())
    {
      ConnectorEnd connEnd = (ConnectorEnd) end;
      // make a new end and copy over the details
      ConnectorEnd nextEnd = next.createEnd();
      nextEnd.setRole(connEnd.undeleted_getRole());
      nextEnd.setPartWithPort(connEnd.getPartWithPort());
      cloneLowerValue(nextEnd, connEnd);
      cloneUpperValue(nextEnd, connEnd);
    }              
    
    return replacement;
  }

  private void cloneLowerValue(ConnectorEnd src, ConnectorEnd dest)
	{
  	ValueSpecification spec = dest.getLowerValue();
  	if (spec instanceof LiteralInteger)
  	{
			LiteralInteger lower = (LiteralInteger) src.createLowerValue(UML2Package.eINSTANCE.getLiteralInteger());
			lower.setValue(((LiteralInteger) spec).getValue());
  	}
  	else
  	if (spec instanceof Expression)
  	{
  		Expression lower = (Expression) src.createLowerValue(UML2Package.eINSTANCE.getExpression());
  		lower.setBody(((Expression) spec).getBody());
  	}
	}

  private void cloneUpperValue(ConnectorEnd src, ConnectorEnd dest)
	{
  	ValueSpecification spec = dest.getUpperValue();
  	if (spec instanceof LiteralInteger)
  	{
			LiteralInteger upper = (LiteralInteger) src.createUpperValue(UML2Package.eINSTANCE.getLiteralInteger());
			upper.setValue(((LiteralInteger) spec).getValue());
  	}
  	else
  	if (spec instanceof Expression)
  	{
  		Expression upper = (Expression) src.createUpperValue(UML2Package.eINSTANCE.getExpression());
  		upper.setBody(((Expression) spec).getBody());
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

	/**
	 * turn the connection index into a multiplicity string
	 * @param index
	 * @return
	 */
	private String getConnectionIndexString(int index)
	{
		ConnectorEnd end = (ConnectorEnd) subject.getEnds().get(index);
		ValueSpecification spec = end.getLowerValue();
		
		if (spec instanceof Expression)
			return "[" + ((Expression) spec).getBody() + "]";
		
		if (spec instanceof LiteralInteger)
		{
			int lower = end.getLower();
			return
				"[" +
				(lower == -1 ? "+" : ("" + end.getLower()))
				+ "]";
		}
		return "";
	}
	
  private Element getSubjectAsElement()
  {
    return (Element) figureFacet.getSubject();
  }
}

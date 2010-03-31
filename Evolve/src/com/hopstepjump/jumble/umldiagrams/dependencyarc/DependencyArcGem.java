package com.hopstepjump.jumble.umldiagrams.dependencyarc;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.adjust.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.previewsupport.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.jumble.umldiagrams.linkedtextnode.*;
import com.hopstepjump.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author andrew
 */
public class DependencyArcGem implements Gem
{
  static final String FIGURE_NAME = "dependency";
  private BasicArcAppearanceFacet basicArcAppearanceFacet = new BasicArcAppearanceFacetImpl();
  private AdvancedArcFacet advancedFacet = new AdvancedArcFacetImpl();
  private LinkedTextOriginFacet linkedTextOriginFacet = new LinkedTextOriginFacetImpl();
  private ContainerFacet containerFacet = new ContainerFacetImpl();
  private FigureFacet figureFacet;
  private Dependency subject;
  private String name = "";
  private LinkedTextFacet linkedTextFacet;
  private FigureFacet text;
  private int stereotypeHash;
  private Color color = Color.BLACK;
  private boolean substitution;
  private boolean resemblance;
  private boolean socketStyle;
  private boolean largeSocketStyle;


  public DependencyArcGem(PersistentFigure pfig)
  {
  	interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
    subject = (Dependency) pfig.getSubject();
    PersistentProperties properties = pfig.getProperties();
    color = properties.retrieve("color", Color.BLACK).asColor();
    substitution = properties.retrieve("substitution", false).asBoolean();
    resemblance = properties.retrieve("resemblance", false).asBoolean();
    socketStyle = properties.retrieve("socket", false).asBoolean();
    largeSocketStyle = properties.retrieve("largeSocket", false).asBoolean();
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
  	LinkedTextGem linkedTextGem = new LinkedTextGem(name, true, CalculatedArcPoints.MAJOR_POINT_MIDDLE);
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
      Dependency subject = (Dependency) figureFacet.getSubject();
      boolean resemblance = subject.isResemblance();
      boolean substitution = subject.isReplacement();
      if (resemblance || substitution)
      	return formResemblanceAppearance(mainArc, start, second, secondLast, last, calculated, resemblance, substitution);
    	
      Set<String> styles = calculated.getNode2().getDisplayStyles(true);
      if (styles == null)
        styles = new HashSet<String>();
    	boolean socketStyle = styles.contains(InterfaceCreatorGem.LINK_STYLE_DIRECT);
      boolean largeSocketStyle = styles.contains(InterfaceCreatorGem.LINK_STYLE_DIRECT_LARGE);
      
      if (socketStyle || largeSocketStyle)
        return formSocketAppearance(mainArc, start, second, secondLast, last, calculated, largeSocketStyle, curved);
      else
        return formDependencyAppearance(mainArc, start, second, secondLast, last, calculated, color);
    }
    
		public ZNode formSocketAppearance(
        ZShape mainArc,
        UPoint start,
        UPoint second,
        UPoint secondLast,
        UPoint last,
        CalculatedArcPoints calculated,
        boolean largeSocketStyle,
        boolean curved)
    {
      ZGroup group = new ZGroup();
      UDimension offset = new UDimension(4, 4);
      if (!largeSocketStyle)
        offset = UDimension.ZERO;

      // make the curve around the interface
      UDimension dimension = secondLast.subtract(last);
      UBounds fullTarget = 
        calculated.getNode2().getFigureFacet().getFullBounds();
      UPoint middle = fullTarget.getMiddlePoint(); 
      UBounds targetBounds =
        fullTarget.inset(offset.negative());
      double radius = fullTarget.getWidth() / 2;
            
      double startAngle = dimension.getRadians();
      double startDegrees = -startAngle / Math.PI * 180;
      double degreesMoreThanHalf = 0;
      boolean isDarwinStyle =
        GlobalPreferences.preferences.getRawPreference(
            new Preference("Appearance", "Interface display type", new PersistentProperty("UML2"))).asString().equals("Darwin");
      int openClosed = isDarwinStyle ? Arc2D.CHORD : Arc2D.OPEN;
      double startingAngle = startDegrees - 90 - degreesMoreThanHalf;
      
      ZArc socket = new ZArc(
          targetBounds.getX(),
          targetBounds.getY(),
          targetBounds.getWidth(),
          targetBounds.getHeight(),
          startingAngle,
          isDarwinStyle ? 360 : 180 + 2 * degreesMoreThanHalf,
          openClosed);
      socket.setPenPaint(color);
      socket.setFillPaint(null);

      // make a main arc with an altered last segment, to intersect with the circle
      UPoint lastPoint = null;
      List<UPoint> points = calculated.getAllPoints();
      List<UPoint> changedPoints = new ArrayList<UPoint>();
      int totalPoints = points.size();
      int index = 0;
      
      for (UPoint point : calculated.getAllPoints())
      {
        index++;
        
        // if the segment is last, then truncate
        if (index == totalPoints && point.distance(middle) <= radius + offset.getWidth() + 1)
        {
          // bring the last point back by offset.width pixels
          double distance = point.distance(lastPoint) - offset.getWidth();
          double angle = point.subtract(lastPoint).getRadians();
          
          point =
            lastPoint.add(
              new UDimension(distance * Math.cos(angle), distance * Math.sin(angle)));
        }
        
        // add the point to the polyline
        changedPoints.add(point);
        
        // keep a record of the last point so we can have deal with a line segment
        lastPoint = point;
      }
      
      // make a (possibly curved) line
      ZShape mainLine = CalculatedArcPoints.makeConnection(changedPoints, curved);

      // add the thin line
      group.addChild(new ZVisualLeaf(mainLine));

      // add the arc
      group.addChild(new ZVisualLeaf(socket));

      group.setChildrenFindable(false);
      group.setChildrenPickable(true);
      return group;
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
      properties.add(new PersistentProperty("substitution", substitution, false));
      properties.add(new PersistentProperty("resemblance", resemblance, false));
      properties.add(new PersistentProperty("socket", socketStyle, false));
      properties.add(new PersistentProperty("largeSocket", largeSocketStyle, false));
    }

    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
      menu.add(linkedTextFacet.getViewLabelMenuItem(coordinator, "dependency"));
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
      return end != null && DependencyCreatorGem.acceptsOneOrBothAnchors(start, end);
    }

    public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
    {
      if (pass != ViewUpdatePassEnum.LAST)
        return;
      
      // if this is top and the anchors we are attached to are not the same as the ones that the
      // model element is attached to, then delete
      final Dependency dependency = subject;
      NamedElement owner = (NamedElement) subject.getClients().get(0);
      NamedElement supplier = subject.undeleted_getDependencyTarget();
      final NamedElement viewOwner = DependencyCreatorGem.extractDependentClient(figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject());
      final NamedElement viewSupplier = (NamedElement) figureFacet.getLinkingFacet().getAnchor2().getFigureFacet().getSubject();
      
      if (owner != viewOwner || supplier != viewSupplier)
      {
        figureFacet.formDeleteTransaction();
        return;
      }
      
      // possibly update the name
      if (!name.equals(linkedTextFacet.getText()))
      {
  			SetTextTransaction.set(
  			    linkedTextFacet.getFigureFacet(),
  			    subject.getName(),
  			    null,
  			    false);
      }
      
      // if the stereotypes have changed, force a redraw
      final int newHash = StereotypeUtilities.calculateStereotypeHash(null, subject);
			stereotypeHash = newHash;
			substitution = dependency.isReplacement();
			resemblance = dependency.isResemblance();
      
      CalculatedArcPoints calculated  = figureFacet.getLinkingFacet().getCalculated();
      Set<String> styles = calculated.getNode2().getDisplayStyles(true);
      if (styles == null)
        styles = new HashSet<String>();
    	socketStyle = styles.contains(InterfaceCreatorGem.LINK_STYLE_DIRECT);
      largeSocketStyle = styles.contains(InterfaceCreatorGem.LINK_STYLE_DIRECT_LARGE);
    }

    public Object getSubject()
    {
      return subject;
    }

    public boolean hasSubjectBeenDeleted()
    {
      return subject.isThisDeleted();
    }

    public Command makeReanchorCommand(AnchorFacet start, AnchorFacet end)
    {
      NamedElement oldOwner = (NamedElement) subject.getClients().get(0);      
      NamedElement newOwner = DependencyCreatorGem.extractDependentClient(start.getFigureFacet().getSubject());
      NamedElement newSupplier = (NamedElement) end.getFigureFacet().getSubject();

      // change the owner
      newOwner.getOwnedAnonymousDependencies().add(subject);
      subject.getClients().remove(oldOwner);
      subject.getClients().add(newOwner);
      subject.setDependencyTarget(newSupplier);
      return null;
    }

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
      Set<String> styles = new HashSet<String>();
      if (anchor == figureFacet.getLinkingFacet().getAnchor1())
        return styles;
      
      // if we are not resemblance or substitution, we may require to change style
      boolean resemblance = subject.isResemblance();
      boolean substitution = subject.isReplacement();
      if (!resemblance && !substitution)
      {
        // check the other node -- if this has interface style, then just default to ordinary
        Set<String> other = figureFacet.getLinkingFacet().getAnchor1().getDisplayStyles(false);
        if (other == null || !other.contains("interface-style"))
        {
          styles.add(InterfaceCreatorGem.LINK_STYLE_DIRECT);
          styles.add(InterfaceCreatorGem.LINK_STYLE_DIRECT_LARGE);
        }
      }
      
      return styles;
    }

		public void acceptPersistentProperties(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
  }
  
	public static ZNode formDependencyAppearance(
			ZShape mainArc,
			UPoint start,
			UPoint second,
			UPoint secondLast,
			UPoint last,
			CalculatedArcPoints calculated,
      Color color)
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
      poly.setPenPaint(color);
	    poly.setStroke(new BasicStroke(0.7f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0));
	    UDimension dimension = secondLast.subtract(last);
	    ZTransformGroup arrowGroup = new ZTransformGroup(new ZVisualLeaf(poly));
	    arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());

	    // add the thin line
      mainArc.setPenPaint(color);
	    group.addChild(new ZVisualLeaf(mainArc));

	    // add the arrow
	    arrowGroup.setChildrenFindable(false);
	    arrowGroup.setChildrenPickable(false);
	    group.addChild(arrowGroup);

	    group.setChildrenFindable(false);
	    group.setChildrenPickable(true);
	    return group;
	  }
	
	
  private ZNode formResemblanceAppearance(
  		ZShape mainArc,
  		UPoint start,
  		UPoint second,
  		UPoint secondLast,
  		UPoint last,
  		CalculatedArcPoints calculated,
  		boolean resemblance,
  		boolean substitution)
	{
  	UPoint current = last;
  	
  	// make the thin and thick lines
    ZGroup group = new ZGroup();
    
    // make the arrowhead
    ZTransformGroup arrowGroup = new ZTransformGroup();
    // if substitution, place 2 arrows
    if (substitution)
    {
	    ZPolygon poly = new ZPolygon(last);
      poly.setClosed(false);
	    poly.add(current.add(new UDimension(-6, -15)));
	    poly.add(current);
	    poly.add(current.add(new UDimension(6, -15)));
	    arrowGroup.addChild(new ZVisualLeaf(poly));
	    current = current.subtract(new UDimension(0, 8));

      poly = new ZPolygon(last);
	    if (resemblance)
      {
	    	poly.setFillPaint(color);
        poly.add(current);
        poly.add(current.add(new UDimension(-4, -10)).getX(), current.add(new UDimension(-4, -10)).getY());
        poly.add(current.add(new UDimension(4, -10)));
        poly.add(current);
      }
	    else
      {
	    	poly.setClosed(false);
        poly.add(current);
        poly.add(current.add(new UDimension(-4, -10)).getX(), current.add(new UDimension(-4, -10)).getY());
        poly.add(current);
        poly.add(current.add(new UDimension(4, -10)));
      }
      
	    arrowGroup.addChild(new ZVisualLeaf(poly));
    }
    else
    if (resemblance)
    {
	    ZPolygon poly = new ZPolygon(last);
	    poly.add(current.add(new UDimension(-8, -20)).getX(), current.add(new UDimension(-8, -20)).getY());
	    poly.add(current.add(new UDimension(8, -20)));
	    poly.add(current);
      poly.add(last);
	    poly.setPenPaint(color);
	    poly.setFillPaint(color);
	    arrowGroup.addChild(new ZVisualLeaf(poly));
	    current = current.subtract(new UDimension(0, 4));
    }      

	  UDimension dimension = secondLast.subtract(last);
    arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());

    // add the thin line
    group.addChild(new ZVisualLeaf(mainArc));

    // add the arrow
    arrowGroup.setChildrenFindable(false);
    arrowGroup.setChildrenPickable(false);
    group.addChild(arrowGroup);

    group.setChildrenFindable(false);
    group.setChildrenPickable(true);
    return group;
	}
  
}

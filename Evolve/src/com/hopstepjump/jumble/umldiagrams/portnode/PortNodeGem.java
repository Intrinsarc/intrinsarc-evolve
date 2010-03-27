package com.hopstepjump.jumble.umldiagrams.portnode;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.expander.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.jumble.umldiagrams.implementationarc.*;
import com.hopstepjump.jumble.umldiagrams.linkedtextnode.*;
import com.hopstepjump.layout.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public final class PortNodeGem implements Gem
{
  private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

	private static final UDimension CREATION_EXTENT = new UDimension(16, 16);

  private Color FILL_COLOR = new Color(240, 240, 240);
	private Color lineColor = Color.black;
	public static final String FIGURE_NAME = "port";
	private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
	private ResizeVetterFacet resizeVetterFacet = new ResizeVetterFacetImpl();
	private LinkedTextOriginFacet linkedTextOriginFacet = new LinkedTextOriginFacetImpl();
	private BasicNodeFigureFacet figureFacet;
	private FigureFacet text;
	private boolean classScope;
  private String extraText;
	private BasicNodeContainerFacet containerFacet = new ContainerFacetImpl();
	private PortNodeFacet portNode = new PortFacetImpl();
	private LinkedTextFacet linkedTextFacet;
	private VisibilityFacet visibilityFacet = new VisibilityFacetImpl();
  private PortDisplayTypeFacet portDisplayTypeFacet = new PortDisplayTypeFacetImpl();
	private VisibilityKind accessType = VisibilityKind.PUBLIC_LITERAL;
  private int displayType = PortDisplayTypeFacet.NORMAL_TYPE;
  private Port subject;
  private ClipboardCommandsFacet clipboardCommandsFacet = new ClipboardCommandsFacetImpl();
  private boolean instance;
  private boolean drawInferred;
  private int portKind;
  private Set<String> inferredReqNames = new HashSet<String>();
  private Set<String> inferredProvNames = new HashSet<String>();
  
	public PortNodeGem(DiagramFacet diagram, UPoint location, boolean isForClasses, PersistentFigure pfig, boolean instance)
  {
		this.subject = (Port) pfig.getSubject();
		String figureId = pfig.getId();
  	BasicNodeGem basicLinkedTextGem = new BasicNodeGem(
  	    LinkedTextCreatorGem.RECREATOR_NAME,
  	    diagram,
  	    figureId + "_T",
  	    location.subtract(new UDimension(0, CREATION_EXTENT.getHeight())),
  	    true,
  	    "text",
  	    true);
    
    // possibly seed persistent properties
  	PersistentProperties properties = pfig.getProperties();
  	if (properties == null)
  	{
  		properties = new PersistentProperties();
  	  pfig.setProperties(properties);
  	}
    properties.addIfNotThere(new PersistentProperty("classScope", isForClasses, true));
    String name = subject == null ? "" : subject.getName();
    LinkedTextGem linkedTextGem = new LinkedTextGem(name, false, CalculatedArcPoints.MAJOR_POINT_MIDDLE);
		basicLinkedTextGem.connectBasicNodeAppearanceFacet(linkedTextGem.getBasicNodeAppearanceFacet());
		linkedTextGem.connectBasicNodeFigureFacet(basicLinkedTextGem.getBasicNodeFigureFacet());
    
    // decorate the clipboard facet
    basicLinkedTextGem.connectClipboardCommandsFacet(linkedTextGem.getClipboardCommandsFacet());

		text = basicLinkedTextGem.getBasicNodeFigureFacet();
		text.getContainedFacet().setContainer(containerFacet);
		linkedTextFacet = linkedTextGem.getLinkedTextFacet();
		
		// start with the text showing
    interpretOptionalProperties(pfig);
    this.instance = instance;
  }
  
	public PortNodeGem(PersistentFigure figure, boolean instance)
  {
		interpretOptionalProperties(figure);
		this.instance = instance;
  }

  public ClipboardCommandsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
  }

  private class ClipboardCommandsFacetImpl implements ClipboardCommandsFacet
  {
    public boolean hasSpecificDeleteCommand()
    {
      return false;
    }

    public Command makeSpecificDeleteCommand()
    {
      return null;
    }
    
    public Command performPostDeleteTransaction()
    {
      String uuid = getOriginalSubjectAsPort(subject).getUuid();
      getPortCompartment().addDeleted(uuid);
      return null;
    }
    
    private PortCompartmentFacet getPortCompartment()
    {
      FigureFacet parent = figureFacet.getContainedFacet().getContainer().getFigureFacet();
      return (PortCompartmentFacet)
        parent.getDynamicFacet(PortCompartmentFacet.class);
    }

    public boolean hasSpecificKillCommand()
    {
      return isOutOfPlace() || !atHome();
    }

    /** returns true if the element is out of place */
    private boolean isOutOfPlace()
    {
      return extractVisualClassifier() != getSubjectAsPort().getOwner();
    }

    public Command makeSpecificKillCommand(ToolCoordinatorFacet coordinator)
    {
      // be defensive
      if (figureFacet.getContainedFacet().getContainer() == null)
        return null;
      
      // only allow changes in the home stratum
      if (!atHome())
      {
        coordinator.displayPopup(ERROR_ICON, "Delta error",
            new JLabel("Must be in home stratum to delete subjects!", DELTA_ICON, JLabel.RIGHT),
            ScreenProperties.getUndoPopupColor(),
            Color.black,
            3000);
        return null;
      }

      // if this is a replace, kill the replace delta
      Port port = getSubjectAsPort();
      if (port.getOwner() instanceof DeltaReplacedConstituent && port.getOwner().getOwner() == extractVisualClassifier())
        return generateReplaceDeltaKill(coordinator);
      else
        return generateDeleteDelta(coordinator);
    }

    private Command generateReplaceDeltaKill(ToolCoordinatorFacet coordinator)
    {
      // generate a delete delta
      coordinator.displayPopup(null, null,
          new JLabel("Removed replace delta", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      final Port port = getSubjectAsPort();
      
      return new AbstractCommand("Removed replace delta", "Restored replace delta")
      {
        public void execute(boolean isTop)
        {
          GlobalSubjectRepository.repository.incrementPersistentDelete(port.getOwner());            
        }

        public void unExecute()
        {
          GlobalSubjectRepository.repository.decrementPersistentDelete(port.getOwner());
        } 
      };
    }

    private Command generateDeleteDelta(ToolCoordinatorFacet coordinator)
    {
      // generate a delete delta
      coordinator.displayPopup(null, null,
          new JLabel("Delta delete", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      final Classifier classifier = extractVisualClassifier();
      
      // add this to the classifier as a delete delta
      final Port port = getOriginalSubjectAsPort(figureFacet.getSubject());
      
      return new AbstractCommand("Added delete delta", "Removed delete delta")
      {
        private DeltaDeletedConstituent delete;
        
        public void execute(boolean isTop)
        {
          SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
          
          // possibly resurrect
          if (delete != null)
          {
            repository.decrementPersistentDelete(delete);
          }
          else
          {
            delete = ((Class) classifier).createDeltaDeletedPorts();
            delete.setDeleted(port);
          }
        }

        public void unExecute()
        {
          GlobalSubjectRepository.repository.incrementPersistentDelete(delete);
        } 
      };
    }

    private boolean atHome()
    {
      // are we at home?
      Package home = GlobalSubjectRepository.repository.findOwningStratum(extractVisualClassifier());
      Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
      
      return home == visualHome;
    }

    private Classifier extractVisualClassifier()
    {
      return ClassifierConstituentHelper.extractVisualClassifierFromConstituent(figureFacet);
    }
  }

  /**
   * @param properties
   */
  private void interpretOptionalProperties(PersistentFigure pfig)
  {
  	subject = (Port) pfig.getSubject();
  	PersistentProperties properties = pfig.getProperties();
    classScope = properties.retrieve("classScope", false).asBoolean();
    displayType = properties.retrieve("dispType", PortDisplayTypeFacet.NORMAL_TYPE).asInteger();
    extraText = properties.retrieve("extraText", "").asString();
    drawInferred = properties.retrieve("drawInferred", false).asBoolean();
  }
  
  public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
  	return appearanceFacet;
  }
  
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
  	figureFacet.registerDynamicFacet(portNode, PortNodeFacet.class);
  	figureFacet.registerDynamicFacet(visibilityFacet, VisibilityFacet.class);
    figureFacet.registerDynamicFacet(portDisplayTypeFacet, PortDisplayTypeFacet.class);
  	figureFacet.registerDynamicFacet(linkedTextOriginFacet, LinkedTextOriginFacet.class);
	}

  // regexp for interpreting port multiplicity
  private static Pattern pattern = Pattern.compile(
  		"\\s*(\\w*)\\s*(?:\\[(?:([0-9]+)\\s*\\.\\.\\s*)?([0-9]+|\\*)\\s*\\])?");
  private class LinkedTextOriginFacetImpl implements LinkedTextOriginFacet
  {
    public UPoint getMajorPoint(int majorPointType)
    {
      return figureFacet.getFullBounds().getMiddlePoint();
    }

		public String textChanged(String newText, int majorPointType)
		{
			// if the text has changed, possibly look at changing the model
			String oldName = formName();
			if (subject != null && !newText.equals(oldName))
			{
				setNameAndMultiplicity(subject, newText);
			}
			return formName();
		}
  }
  
  private class VisibilityFacetImpl implements VisibilityFacet
  {
    public Object setVisibility(VisibilityKind newAccessType)
    {
      VisibilityKind oldAccessType = subject.getVisibility();
      subject.setVisibility(newAccessType);
      return null;
    }
    
    public void unSetVisibility(Object memento)
    {
    }
  }
  
  private class PortDisplayTypeFacetImpl implements PortDisplayTypeFacet
  {
    public Object setDisplayType(int type)
    {
      displayType = type;

      // we are about to autosize, so need to make a resizings command
      figureFacet.performResizingTransaction(figureFacet.getFullBounds());
      return null;
    }

    public void unSetDisplayType(Object memento)
    {
    }    
  }
  
  private class PortFacetImpl implements PortNodeFacet
  {
    public boolean isClassScope()
    {
      return classScope;
    }
    
    public boolean isPrivate()
    {
      return accessType.equals(VisibilityKind.PRIVATE_LITERAL);
    }
  }
  
	private class ResizeVetterFacetImpl implements ResizeVetterFacet
	{
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.resizebase.ResizeVetter#startResizeVet()
		 */
		public void startResizeVet()
		{
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.resizebase.ResizeVetter#vetResizedBounds(DiagramView, int, UBounds, boolean)
		 */
		public UBounds vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
		{
			return bounds;
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.resizebase.ResizeVetter#vetResizedExtent(UBounds)
		 */
		public UDimension vetResizedExtent(UBounds bounds)
		{
			if (figureFacet.isAutoSized())
				return figureFacet.getFullBounds().getDimension();
			return bounds.getDimension().maxOfEach(CREATION_EXTENT);
		}
	}
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
  {	
    /**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BaseNodeFigure#getAutoSizedBounds()
		 */
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), getCreationExtent());
		}
		
		/**
		 * @see com.hopstepjump.jumble.foundation.interfaces.Figure#formView()
		 */
		public ZNode formView()
		{
			UBounds bounds = figureFacet.getFullBounds();
      ZGroup group = new ZGroup();

			// should we display the inferred interfaces?
			if (drawInferred && figureFacet.getContainedFacet().getContainer() != null)
			{
		    List<String> provided = new ArrayList<String>(getInferredProvidedNames());
		    List<String> required = new ArrayList<String>(getInferredRequiredNames());
		    Collections.sort(provided);
		    Collections.sort(required);
		    int lineNumber[] = {0};
		    OuterBox outer = computeInferredInterfaceBounds(
		    		figureFacet.getContainedFacet().getContainer().getFigureFacet().getFullBounds(),
		    		bounds,
		    		provided,
		    		required,
		    		lineNumber);
				
				UBounds fill = outer.getLayoutBounds("fill");
				ZRoundedRectangle rect = new ZRoundedRectangle(fill.getX(), fill.getY(), fill.getWidth(), fill.getHeight(), 20, 20);
				Color color = Color.WHITE;
				rect.setFillPaint(color);
				rect.setPenPaint(new Color(235, 200, 200));
		    rect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));
				group.addChild(new ZVisualLeaf(rect));
		    
		    // place the provided interfaces first
		    for (String name : provided)
		    {
		    	UBounds circle = outer.getLayoutBounds("circleP" + name);
		    	UBounds text = outer.getLayoutBounds("textP" + name);
		    	group.addChild(drawInterface(name, true, bounds, lineNumber[0], circle, text, color));
		    }
		    for (String name : required)
		    {
		    	UBounds circle = outer.getLayoutBounds("circleR" + name);
		    	UBounds text = outer.getLayoutBounds("textR" + name);
		    	group.addChild(drawInterface(name, false, bounds, lineNumber[0], circle, text, color));
		    }				
			}
			
      if (displayType != PortDisplayTypeFacet.ELIDED_TYPE)
      {
        ZRectangle rect = new ZRectangle(bounds);
        rect.setPenPaint(lineColor);
				rect.setFillPaint(FILL_COLOR);
        // a possible shortcut?
        if (displayType == PortDisplayTypeFacet.SHORTCUT_TYPE)
        {
          rect.setPenPaint(new Color(210, 210, 210));
          rect.setFillPaint(new Color(250, 250, 250));
          rect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));
        }
        rect.setPenWidth(1);
        // place some text indicating the dimensions
        group.addChild(new ZVisualLeaf(rect));     
      }
      else
      {
        UDimension offset = new UDimension(2, 2);

        bounds = bounds.addToPoint(offset.negative()).addToExtent(offset);
        ZRectangle rect = new ZRectangle(bounds);
        rect.setPenPaint(new Color(140, 140, 140, 0));
        rect.setFillPaint(new Color(240, 240, 240, 0));
        rect.setPenWidth(2);
                
        // place some text indicating the dimensions
        group.addChild(new ZVisualLeaf(rect));
      }
      
      // is this a specialised type of port?  if so, put something in the middle to show this
      PortKind kind = subject.getKind();
      if (kind.equals(PortKind.CREATE_LITERAL))
      {
      	int offset = 5;
      	UPoint middle = bounds.getMiddlePoint().subtract(new UDimension(offset, offset));
      	ZRectangle ell = new ZRectangle(middle.getX(), middle.getY(), offset * 2, offset * 2);
      	ell.setPenWidth(3);
      	ell.setFillPaint(null);
      	group.addChild(new ZVisualLeaf(ell));
      }
      else
      {
      	// only add the hyperport or autoconnect information if there are no connectors to the port, or if it is in a classifier
      	// as soon as a hyperport or autoconnect has a connector, it becomes a normal port and we must show this visually
      	boolean joined = false;
      	boolean isPart = false;
      	Object obj = figureFacet.getContainedFacet().getContainer() == null ? 
      			null : figureFacet.getContainedFacet().getContainer().getContainedFacet().getContainer().getFigureFacet().getSubject();
      	IDeltaEngine engine = GlobalDeltaEngine.engine;
      	Package pkg = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
      	DEStratum perspective = engine.locateObject(pkg).asStratum();
      	DEPort port = engine.locateObject(subject).asConstituent().asPort();
      	if (obj instanceof Property)
      	{
      		isPart = true;
	      	Classifier cls = ClassifierConstituentHelper.extractVisualClassifierFromConstituent(figureFacet);
	      	DEComponent comp = engine.locateObject(cls).asComponent();
      		DEPart part = engine.locateObject(obj).asConstituent().asPart();	      	
	      	if (comp != null && part != null)
	      	{
	      		// only draw if we have empty links to this port on this part 
	      		joined = !comp.getCompositeConnectorsAsLinks(perspective, true).getEnds(new DELinkEnd(port, part)).isEmpty();
	      	}
      	}
	
      	if (!joined && kind.equals(PortKind.HYPERPORT_START_LITERAL))
	  		{
	      	ZPolygon poly = new ZPolygon();
	      	poly.add(bounds.getTopLeftPoint());
	      	poly.add(bounds.getBottomLeftPoint());
	      	poly.add(bounds.getBottomRightPoint());
	      	poly.setFillPaint(Color.BLACK);      	
	      	group.addChild(new ZVisualLeaf(poly));
	  		}
	      else
	      if (!joined && kind.equals(PortKind.HYPERPORT_END_LITERAL))
	      {
	      	ZPolygon poly = new ZPolygon();
	      	poly.add(bounds.getTopLeftPoint());
	      	poly.add(bounds.getTopRightPoint());
	      	poly.add(bounds.getBottomRightPoint());
	      	poly.setFillPaint(Color.BLACK);
	      	group.addChild(new ZVisualLeaf(poly));
	      }
	      else
	      if (!isPart && kind.equals(PortKind.AUTOCONNECT_LITERAL))
	      {
	      	Classifier cls = (Classifier) ClassifierConstituentHelper.getPossibleDeltaSubject(subject).getOwner();
	      	DEComponent comp = engine.locateObject(cls).asComponent();
      		if (comp.getCompositeConnectorsAsLinks(perspective, true).getEnds(new DELinkEnd(port, null)).isEmpty())
      		{
		      	int coords[][] = {
		      			{-4, -4}, {4, -4}, {0, 4},
		      			{-4, -4}, {4, 0}, {-4, 4},
		      			{0, -4}, {-4, 4}, {4, 4},
		      			{4, -4}, {4, 4}, {-4, 0}};
		      	UPoint pt = bounds.getMiddlePoint();
		      	UBounds enclosing = figureFacet.getContainedFacet().getContainer() == null ?
		      			UBounds.ZERO : figureFacet.getContainedFacet().getContainer().getFigureFacet().getFullBounds();
		      	ZPolygon poly = new ZPolygon();		      	
		      	int closest = PortNodeContainerPreviewGem.classifyPoint(pt, enclosing).getLineNumber();
	      		for (int lp = 0; lp < 3; lp++)
	      		{
	      			int point[] = coords[closest * 3 + lp];
	      			poly.add(pt.add(new UDimension(point[0], point[1])));
	      		}
		      	poly.setFillPaint(Color.BLACK);
		      	group.addChild(new ZVisualLeaf(poly));
      		}
	      }
      }
              
      // draw a "hiding" square if there are hidden connectors
      if (connectorVisibility(false))
      {
        UDimension offset = new UDimension(1, 1);
        UPoint middle = bounds.getMiddlePoint();
        UBounds smallBounds = new UBounds(middle.subtract(offset), middle.add(offset));
        ZRectangle rect = new ZRectangle(smallBounds.getRectangle2D());
        rect.setFillPaint(Color.GRAY);
        group.addChild(new ZVisualLeaf(rect));
      }
      
			// indicate that these visual items are linked to this figure
	    group.setChildrenPickable(false);
	    group.setChildrenFindable(false);
	    group.putClientProperty("figure", figureFacet);
			return group;
		}
		
		/** draw an interface (required or provided) including the link to the port, and advance the point 
		 * @param fsize 
		 * @param color */
		private ZNode drawInterface(
				String name,
				boolean provided,
				UBounds portBounds,
				int lineNumber,
				UBounds circle,
				UBounds text,
				Color background)
		{
			UPoint start = portBounds.getMiddlePoint();
			UPoint last = circle.getMiddlePoint();
			double xs = start.getX();
			double ys = start.getY();
			double xl = last.getX();
			double yl = last.getY();
			switch (lineNumber)
			{
				case 0:
				case 2:
					if (xl >= portBounds.getX() && xl <= portBounds.getMaxX())
						start = new UPoint(xl, ys);
					break;
				case 1:
				case 3:
				default:
					if (yl >= portBounds.getY() && yl <= portBounds.getMaxY())
						start = new UPoint(xs, yl);
					break;
			}	
			
			ZGroup group = new ZGroup();

			// add the line
      double angle = start.subtract(last).getRadians();
			ZLine line = new ZLine(start, last);
			
			// add the text
			ZText atext = new ZText(name);
			atext.setTranslateX(text.getX());
			atext.setTranslateY(text.getY());

			// add a fairly large boundary
			UBounds nameCover = new UBounds(atext.getBounds()).addToExtent(new UDimension(10, 4)).addToPoint(new UDimension(-5, -2));
			ZRectangle nameRect = new ZRectangle(nameCover);
			nameRect.setFillPaint(background);
			nameRect.setPenPaint(background);
			
			group.addChild(new ZVisualLeaf(line));
			group.addChild(new ZVisualLeaf(nameRect));
			group.addChild(new ZVisualLeaf(atext));

			// draw the circle
			double w = circle.getWidth();
			if (provided)
			{
				ZEllipse ellipse = new ZEllipse(circle.getX(), circle.getY(), w, w);
				ellipse.setFillPaint(background);
				group.addChild(new ZVisualLeaf(ellipse));
			}
			else
			{
	      double startDegrees = -angle / Math.PI * 180;
	      double degreesMoreThanHalf = 5;
	      double startingAngle = startDegrees - 90 - degreesMoreThanHalf;

				ZArc arc = new ZArc(
						circle.getX(),
						circle.getY(),
						circle.getWidth(),
						circle.getHeight(),
						startingAngle,
						180 + degreesMoreThanHalf * 2,
						Arc2D.OPEN);
				arc.setFillPaint(background);
				group.addChild(new ZVisualLeaf(arc));				
			}
			
			return group;
		}

		/**
		 * @see com.hopstepjump.jumble.foundation.interfaces.Figure#getFigureName()
		 */
		public String getFigureName()
		{
			return FIGURE_NAME;
		}
	
		/**
		 * @see com.hopstepjump.jumble.foundation.interfaces.SelectableFigure#getActualFigureForSelection()
		 */
		public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
		{
		  ManipulatorFacet keyFocus = null;
		  if (favoured)
		    keyFocus = linkedTextFacet.getTextEntryManipulator(coordinator, diagramView);
		    
	    Manipulators manipulators =
	      new Manipulators(
	          keyFocus,
	          new ResizingManipulatorGem(
	          		coordinator,
	              figureFacet,
	              diagramView,
	              figureFacet.getFullBounds(),
	              resizeVetterFacet,
	              firstSelected).getManipulatorFacet());

	    return manipulators;
		}

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getActualFigureForSelection()
		 */
		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getContextMenu(IDiagramView, IToolCoordinator)
		 */
		public JPopupMenu makeContextMenu(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
			JPopupMenu popup = new JPopupMenu();
			
			final Object ppart = figureFacet.getContainedFacet().getContainer().getContainedFacet().getContainer().getFigureFacet().getSubject();
			boolean portInstance = ppart instanceof Property;
			if (portInstance)
			{
				final Class cls = (Class) GlobalSubjectRepository.repository.findVisuallyOwningNamespace(
						diagramView.getDiagram(), figureFacet.getContainerFacet());
				JMenuItem links = new JMenuItem("Display port instance info");
				links.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// ask the component for any port links
						IDeltaEngine engine = GlobalDeltaEngine.engine;
						DEPort port = engine.locateObject(subject).asConstituent().asPort();
						DEComponent comp = engine.locateObject(cls).asComponent();
						DEStratum perspective =
							engine.locateObject(
									GlobalSubjectRepository.repository.findVisuallyOwningStratum(
											diagramView.getDiagram(), figureFacet.getContainerFacet())).asStratum();
						DEPart part = engine.locateObject(ppart).asConstituent().asPart();
						
						comp.getInferredLinks(perspective, port, part);
						System.out.println("$$ --------------- visited into parts");
						for (DELinkEnd end : comp.getCachedVisitedIntoParts(perspective, port, part))
							System.out.println("   $$ visits port instance " + end);
						for (DEPort end : comp.getCachedVisitedPorts(perspective, port, part))
							System.out.println("   $$ visits port " + end);
						for (DELinkEnd end : comp.getCachedVisitedTerminals(perspective, port, part))
							System.out.println("   $$ visits terminals " + end);
					}
				});
				popup.add(links);				
			}
			
			JMenuItem links = new JMenuItem("Display links");
			links.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// ask the component for any port links
					IDeltaEngine engine = GlobalDeltaEngine.engine;
					DEPort port = engine.locateObject(subject).asConstituent().asPort();
					DEStratum perspective =
						engine.locateObject(
								GlobalSubjectRepository.repository.findVisuallyOwningStratum(
										diagramView.getDiagram(), figureFacet.getContainerFacet())).asStratum();
					DEComponent owner = engine.locateObject(subject.getOwner()).asComponent();
					
					DELinks links = owner.getInferredLinks(perspective, port);
					System.out.println("$$ ---------------");
					for (DELinkEnd end : links.getEnds(new DELinkEnd(port)))
						System.out.println("Links to " + end);
				}
			});
			popup.add(links);
			
			JMenuItem ifaces = new JMenuItem("Display Provided + required interfaces");
			ifaces.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// ask the component for any port links
					IDeltaEngine engine = GlobalDeltaEngine.engine;
					DEPort port = engine.locateObject(subject).asConstituent().asPort();
					DEStratum perspective =
						engine.locateObject(
								GlobalSubjectRepository.repository.findVisuallyOwningStratum(
										diagramView.getDiagram(), figureFacet.getContainerFacet())).asStratum();
					DEComponent owner = engine.locateObject(ClassifierConstituentHelper.getPossibleDeltaSubject(subject).getOwner()).asComponent();
					
					System.out.println("$$ ----------- Interfaces for " + owner + " from perspective " + perspective);
					Set<? extends DEInterface> provides = owner.getProvidedInterfaces(perspective, port);
					for (DEInterface iface : provides)
						System.out.println("Provides " + iface);
					Set<? extends DEInterface> requires = owner.getRequiredInterfaces(perspective, port);
					for (DEInterface iface : requires)
						System.out.println("Requires " + iface);
				}
			});
			popup.add(ifaces);
			
			boolean diagramReadOnly = diagramView.getDiagram().isReadOnly();
			boolean subjectReadOnly = figureFacet.isSubjectReadOnlyInDiagramContext(false);
			
			if (!portInstance && !diagramReadOnly)
			{
				// add expansions
				popup.addSeparator();
				JMenu expand = new JMenu("Expand");
				expand.setIcon(Expander.EXPAND_ICON);
				JMenuItem deps = new JMenuItem("provided and required interface links");
				expand.add(deps);
				deps.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Type type = ((Port) figureFacet.getSubject()).undeleted_getType();
						new Expander().expand(
								figureFacet,
								type,
								UML2Package.eINSTANCE.getNamedElement_OwnedAnonymousDependencies(),
								new DependencyCreatorGem().getArcCreateFacet(),
								coordinator);
						new Expander().expand(
								figureFacet,
								type,
								UML2Package.eINSTANCE.getBehavioredClassifier_Implementation(),
								new ImplementationCreatorGem().getArcCreateFacet(),
								coordinator);
	
					}
				});
				popup.add(expand);
			}
			
	     // only add a replace if this is not visually at home
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      Namespace visual = repository.findVisuallyOwningNamespace(diagramView.getDiagram(), figureFacet.getContainedFacet().getContainer());
      Namespace real = (Namespace)
        GlobalSubjectRepository.repository.findOwningElement(
            getSubjectAsPort(),
            ClassifierImpl.class); 
          
      if (!portInstance && visual != real &&
          !figureFacet.getContainedFacet().getContainer().getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
      {
        Utilities.addSeparator(popup);			
        JMenuItem replaceItem = getReplaceItem(diagramView, coordinator);
        if (replaceItem != null)
        {
          popup.add(replaceItem);
        }
      }
      
      if (!diagramReadOnly)
      {
        Utilities.addSeparator(popup);
        popup.add(figureFacet.getBasicNodeAutoSizedFacet().getAutoSizedMenuItem(coordinator));
      }
      
      if (!subjectReadOnly)
      {
        Utilities.addSeparator(popup);
  			JMenu visibility = new JMenu("Visibility");
  			visibility.add(getAccessMenuItem(coordinator, VisibilityKind.PUBLIC_LITERAL,    "Public"));
  			visibility.add(getAccessMenuItem(coordinator, VisibilityKind.PROTECTED_LITERAL, "Protected"));
  			visibility.add(getAccessMenuItem(coordinator, VisibilityKind.PACKAGE_LITERAL,   "Package"));
  			visibility.add(getAccessMenuItem(coordinator, VisibilityKind.PRIVATE_LITERAL,   "Private"));
  			popup.add(visibility);
      }
      
      if (!diagramReadOnly)
      {
        Utilities.addSeparator(popup);
        popup.add(linkedTextFacet.getViewLabelMenuItem(coordinator, "port"));
        Utilities.addSeparator(popup);
        JMenu displayType = new JMenu("Display type");
        displayType.add(getPortDisplayTypeMenuItem(coordinator, PortDisplayTypeFacet.NORMAL_TYPE, "normal"));
        displayType.add(getPortDisplayTypeMenuItem(coordinator, PortDisplayTypeFacet.ELIDED_TYPE, "elided"));
        displayType.add(getPortDisplayTypeMenuItem(coordinator, PortDisplayTypeFacet.SHORTCUT_TYPE, "shortcut"));
        popup.add(displayType);
      }
      
			return popup;
		}

		private JMenuItem getAccessMenuItem(final ToolCoordinatorFacet coordinator, final VisibilityKind newAccessType, String accessTypeName)
		{
			JCheckBoxMenuItem makePrivateItem = new JCheckBoxMenuItem(accessTypeName);
			
			makePrivateItem.setState(accessType.equals(newAccessType));
						
			makePrivateItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// toggle the autosized flag (as a command)
					Command privateCommand =
						new SetVisibilityCommand(
						    figureFacet.getFigureReference(),
						    newAccessType,
						    "changed visibility of " + getFigureName() + " to " + newAccessType.getName(),
						    "restored visibility of " + getFigureName() + " to " + accessType.getName());
					coordinator.executeCommandAndUpdateViews(privateCommand);
				}
			});
			return makePrivateItem;
		}
		
    private JMenuItem getPortDisplayTypeMenuItem(final ToolCoordinatorFacet coordinator, final int newDisplayType, String type)
    {
      JCheckBoxMenuItem elidedItem = new JCheckBoxMenuItem(type);
      
      elidedItem.setState(displayType == newDisplayType);
            
      elidedItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // toggle the autosized flag (as a command)
          Command elideCommand =
            new SetPortDisplayTypeCommand(
                figureFacet.getFigureReference(),
                newDisplayType,
                "changed display type of " + getFigureName(),
                "restored display type of " + getFigureName());
          coordinator.executeCommandAndUpdateViews(elideCommand);
        }
      });
      return elidedItem;
    }
    
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			return CREATION_EXTENT;
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#makeNodePreviewFigure(PreviewCacheFacet, IDiagram, UPoint, boolean)
		 */
		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
		{
      // need the bounds of the container, for horizontal / vertical classification
      // however, the basic node will use this call also for a single preview, so must account for this also
      UBounds containerBounds = null;

      // get the bounds of the container for this
      ContainerFacet container = figureFacet.getContainedFacet().getContainer();
      if (container != null)
        containerBounds = container.getFigureFacet().getFullBounds();

      BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
      basicGem.setCentreToEdge(CREATION_EXTENT);
      basicGem.connectPreviewCacheFacet(previews);
      basicGem.connectFigureFacet(figureFacet);
      
	    List<String> provided = new ArrayList<String>(inferredProvNames);
	    List<String> required = new ArrayList<String>(inferredReqNames);
	    Collections.sort(provided);
	    Collections.sort(required);

      PortNodeContainerPreviewGem portNode =
        new PortNodeContainerPreviewGem(
              containerBounds,
              displayType == PortDisplayTypeFacet.ELIDED_TYPE,
              basicGem.getPreviewFacet(),
              previews,
              provided,
              required);
      basicGem.connectContainerPreviewFacet(
          portNode.getContainerPreviewFacet());
      basicGem.connectBasicNodePreviewUnusualSizingFacet(
          portNode.getBasicNodePreviewUnsualSizingFacet());

      return basicGem.getPreviewFacet();
		}

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
		 */
		public boolean acceptsContainer(ContainerFacet container)
		{
		  return 
		  	container != null &&
		  	container.getFigureFacet().hasDynamicFacet(PortCompartmentFacet.class);
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			if (shouldDrawInferred())
			{
		    List<String> provided = new ArrayList<String>(inferredProvNames);
		    List<String> required = new ArrayList<String>(inferredReqNames);
		    Collections.sort(provided);
		    Collections.sort(required);
		    int lineNumber[] = {0};
	
				OuterBox outer = computeInferredInterfaceBounds(
						figureFacet.getContainedFacet().getContainer().getFigureFacet().getFullBounds(),
						figureFacet.getFullBounds(),
						provided,
						required,
						lineNumber);
				UBounds inferred = outer.getLayoutBounds();
			  return
	        figureFacet.getFullBounds().union(text.getFullBoundsForContainment()).union(inferred);
			}
			else
				return figureFacet.getFullBounds().union(text.getFullBoundsForContainment());
		}
		
		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			return figureFacet.getFullBounds();
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
		  properties.add(new PersistentProperty("classScope", classScope, false));
      properties.add(new PersistentProperty("dispType", displayType, PortDisplayTypeFacet.NORMAL_TYPE));
      properties.add(new PersistentProperty("extraText", extraText, null));
      properties.add(new PersistentProperty("drawInferred", drawInferred, false));
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
			if (subject == null || pass != ViewUpdatePassEnum.LAST)
				return;

			// don't bother if the text is still the same
			String subjectName = formName();
			boolean sameName = linkedTextFacet.getText().equals(subjectName);
			boolean sameVisibility = subject.getVisibility().equals(accessType);
			final boolean shouldDrawInferred = shouldDrawInferred();
			final Set<String> provNames = getInferredProvidedNames();
			final Set<String> reqNames = getInferredRequiredNames();
			final int nextKind = subject.getKind().getValue();

			boolean update = false;
			if (shouldDrawInferred != drawInferred)
				update = true;
			if (!provNames.equals(inferredProvNames) || !reqNames.equals(inferredReqNames))
			{
				update = true;
				figureFacet.getDiagram().forceAdjust(figureFacet);
			}
      if (!sameName || !sameVisibility)
        update = true;
      if (nextKind != portKind)
      	update = true;
      
      // don't bother if there are no changes
      if (!update)
      	return;

			if (!sameName)
          SetTextTransaction.set(
              linkedTextFacet.getFigureFacet(),
              subjectName,
              null,
              false);

      accessType = subject.getVisibility();
      // resize, using a text utility
      figureFacet.performResizingTransaction(figureFacet.getFullBounds());
      inferredProvNames = provNames;
      inferredReqNames = reqNames;
      portKind = nextKind;
      drawInferred = shouldDrawInferred;
		}
		
		private Set<String> getInferredRequiredNames()
		{
			Classifier cls = (Classifier) GlobalSubjectRepository.repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), figureFacet.getContainerFacet());
			ElementProperties props = new ElementProperties(figureFacet, cls);
			Set<String> names = new HashSet<String>();
			DEPort port = GlobalDeltaEngine.engine.locateObject(subject).asConstituent().asPort();
			for (DEInterface iface : props.getElement().asComponent().getRequiredInterfaces(props.getPerspective(), port))
				names.add(iface.getName());
			return names;
		}

		private Set<String> getInferredProvidedNames()
		{
			Classifier cls = (Classifier) GlobalSubjectRepository.repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), figureFacet.getContainerFacet());
			ElementProperties props = new ElementProperties(figureFacet, cls);
			Set<String> names = new HashSet<String>();
			DEPort port = GlobalDeltaEngine.engine.locateObject(subject).asConstituent().asPort();
			for (DEInterface iface : props.getElement().asComponent().getProvidedInterfaces(props.getPerspective(), port))
				names.add(iface.getName());
			return names;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
		 */
		public Command middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
		  // show or hide all the connectors attached
		  // if we have some showing and some hidden, show all
		  boolean someHidden = connectorVisibility(false);
		  boolean someShown = connectorVisibility(true);
		  
		  if (!someHidden && !someShown)
		    return null; // no connectors
		  
		  // show if we have some hidden
		  boolean show = someHidden;
		  
		  // make a command to hide or show...
		  CompositeCommand hide = new CompositeCommand(show ? "Show all connections" : "Hide all connections",
		                                               show ? "Hide all the connections" : "Show all the connections");
		  for (Iterator iter = figureFacet.getAnchorFacet().getLinks(); iter.hasNext();)
      {
        LinkingFacet link = (LinkingFacet) iter.next();
        FigureFacet figure = link.getFigureFacet();

        if (isConnector(link))
          hide.addCommand(new HideFigureCommand(null, figure.getFigureReference(), !show, "", ""));
      }
      return hide;
		}
		
    /**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
		 */
		public Object getSubject()
		{
			return subject;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
		 */
		public boolean hasSubjectBeenDeleted()
		{
		  // cannot delete something with no subject
		  if (subject == null)
		    return false;
		  
			return subject.isThisDeleted();
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
      // port instances are always read only
      if (instance)
        return true;
      
      // pass this on up to the container, as we may not be in the place where we are defined
      ContainerFacet container = figureFacet.getContainedFacet().getContainer();
      if (container == null)
        return true;
      
      // only truly writeable/moveable if this is owned by the same visual classifier
      // however, for a kill, this is fine
      if (!kill)
      {
        SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
        if (repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer()) !=
            getPossibleDeltaSubject(figureFacet.getSubject()).getOwner())
          return true;
      }
      
      // only writeable if the class is located correctly
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }

		public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			if (instance)
				return new ToolFigureClassification("port,element", "instance");
			else
				return new ToolFigureClassification("port,element", "class");
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretOptionalProperties(pfig);
		}
  }
  
	private class ContainerFacetImpl implements BasicNodeContainerFacet
	{
		/**
		 * container related code
		 */
		public boolean insideContainer(UPoint point)
		{
			return figureFacet.getFullBoundsForContainment().contains(point);
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
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
		 */
		public void setShowingForChildren(boolean showing)
		{
			// if we are showing, don't show any suppressed children
			text.setShowing(showing && !linkedTextFacet.isHidden());
		}
		
		public void persistence_addContained(FigureFacet contained)
		{
			String containedName = contained.getContainedFacet().persistence_getContainedName();
			
			// set up the operations, attributes, and simple container
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
  
	/**
	 * @see com.hopstepjump.jumble.foundation.IFigure#getContainerablePort()
	 */
	public BasicNodeContainerFacet getBasicNodeContainerFacet()
	{
		return containerFacet;
	}
	
	private String formName()
	{
    String extra = extraText != null ? extraText : "";
		return UMLNodeText.makeNameFromPort(subject) + extra;
	}
  
  // extract the name and multiplicity from a name string
  public static void setNameAndMultiplicity(Port subject, String newName)
  {
    if (newName == null)
      return;
    
    Matcher matcher = pattern.matcher(newName);
    if (matcher.matches())
    {
      subject.setName(matcher.group(1));
      if (matcher.group(2) == null)
        subject.setLowerValue(null);
      else
      {
        LiteralInteger lower = (LiteralInteger) subject.createLowerValue(UML2Package.eINSTANCE.getLiteralInteger());
        lower.setValue(new Integer(matcher.group(2)));
        subject.setLowerValue(lower);
      }
      String upper = matcher.group(3);
      if (upper == null)
        subject.setUpperValue(null);
      else
        subject.setUpperBound(upper.equals("*") ? -1 : new Integer(upper));
    }
    else
    {
      subject.setName(newName);
      subject.setLowerValue(null);
      subject.setUpperValue(null);
    }
  }
  
  private boolean connectorVisibility(boolean shown)
  {
    for (Iterator iter = figureFacet.getAnchorFacet().getLinks(); iter.hasNext();)
    {
      LinkingFacet link = (LinkingFacet) iter.next();
      if (isConnector(link) && link.getFigureFacet().isShowing() == shown)
        return true;
    }
    return false;
  }
  
  private boolean isConnector(LinkingFacet link)
  {
    Set<String> possibleStyles = link.getPossibleDisplayStyles(figureFacet.getAnchorFacet());
    return possibleStyles != null && possibleStyles.contains("hideConnector");
  }
  
  /** return the replaced port if this is a replacement, otherwise just return the port */
  public static Port getOriginalSubjectAsPort(Object subject)
  {
    Port port = (Port) subject;
    if (port.getOwner() instanceof DeltaReplacedConstituent)
      return (Port)((DeltaReplacedConstituent) port.getOwner()).getReplaced();
    return port;
  }
  
  public static Element getPossibleDeltaSubject(Object subject)
  {
    Element element = (Element) subject;
    if (element.getOwner() instanceof DeltaReplacedConstituent)
      return element.getOwner();
    return element;
  }

  private Port getSubjectAsPort()
  {
    return subject;
  }
  
  public JMenuItem getReplaceItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
  {
    // for adding operations
    JMenuItem replaceAttributeItem = new JMenuItem("Replace");
    replaceAttributeItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        final Port replaced = (Port) figureFacet.getSubject();
        final Port original = (Port) ClassifierConstituentHelper.getOriginalSubject(replaced);
        final FigureFacet clsFigure = ClassifierConstituentHelper.extractVisualClassifierFigureFromConstituent(figureFacet);
        final Classifier cls = (Classifier) clsFigure.getSubject();
        
        Command cmd = new AbstractCommand("replaced port", "removed replaced port")
        {          
          private DeltaReplacedPort replacement;
          public void execute(boolean isTop)
          {
          	if (replacement == null)
          		replacement = createDeltaReplacedPort(cls, replaced, original);
            GlobalSubjectRepository.repository.decrementPersistentDelete(replacement);
          }

          public void unExecute()
          {
            GlobalSubjectRepository.repository.incrementPersistentDelete(replacement);
          }            
        };
        coordinator.executeCommandAndUpdateViews(cmd);
        
        diagramView.runWhenModificationsHaveBeenProcessed(new Runnable()
        {
          public void run()
          {
//            FigureFacet createdFeature = ClassifierConstituentHelper.findSubfigure(clsFigure, replacement.getReplacement());
            diagramView.getSelection().clearAllSelection();
//            diagramView.getSelection().addToSelection(createdFeature, true);
          }
        });
      }
    });

    return replaceAttributeItem;
  }
  
  
  public DeltaReplacedPort createDeltaReplacedPort(Classifier owner, Port replaced, Port original)
  {
    DeltaReplacedPort replacement = null;
    Port port = null;
    replacement = ((StructuredClassifier) owner).createDeltaReplacedPorts();
    replacement.setReplaced(original);
    port = (Port) replacement.createReplacement(UML2Package.eINSTANCE.getPort());

    port.setName(replaced.getName());
    port.setVisibility(replaced.getVisibility());

    // create a new type, don't copy over the set provides and requires
    Type type = port.createOwnedAnonymousType(UML2Package.eINSTANCE.getClass_());
    type.setName("(port type)");
    port.setType(type);

    // set upper and lower bounds
    if (replaced.getUpperValue() != null)
      port.setUpperBound(new Integer(replaced.getUpper()));
    if (replaced.getLowerValue() != null)
      port.setLowerBound(new Integer(replaced.getLower()));
    
    // delete it so it can be brought back as part of the redo
    GlobalSubjectRepository.repository.incrementPersistentDelete(replacement);
    
    return replacement;
  }
  
  private boolean shouldDrawInferred()
  {  	
  	if (instance)
  		return false;
  	
  	// if we have no other links (dependencies or implementations), then we are free to draw inferred
  	for (Iterator<LinkingFacet> iter = figureFacet.getAnchorFacet().getLinks(); iter.hasNext();)
  	{
  		LinkingFacet link = iter.next();
  		Object subject = link.getFigureFacet().getSubject();
  		if (subject instanceof Dependency || subject instanceof Implementation)
  			return false;
  	}

  	return true;
  }
  
	private Port getOriginalPort()
	{
		if (subject.getOwner() instanceof DeltaReplacedPort)
			return (Port) ((DeltaReplacedPort) subject.getOwner()).undeleted_getReplaced();
		return subject;
	}
	
	/** work out the bounds of the inferred interfaces 
	 * @param lineNumber */
	static OuterBox computeInferredInterfaceBounds(UBounds containerBounds, UBounds bounds, List<String> provided, List<String> required, int[] lineNumber)
	{
		UDimension isize = new UDimension(24, 24);
		UPoint middle = bounds.getMiddlePoint();
		ClosestLine closest = PortNodeContainerPreviewGem.classifyPoint(middle, containerBounds);
		lineNumber[0] = closest.getLineNumber();
		boolean horiz = closest.isHorizontal();
		int num = closest.getLineNumber();
		ContainerBox container = new ContainerBox(null, horiz ? new HorizontalLayout() : new VerticalLayout());
		
    // sort the names
    Collections.sort(provided);
    Collections.sort(required);
    
		// add each provided interface in turn
    boolean nothing = true;
		for (String prov : provided)
		{
			ContainerBox overall = new ContainerBox("totalP" + prov, new VerticalLayout());
			BorderBox border = new BorderBox(null, new UDimension(4, 4), overall);
			
			overall.addBox(new ExpandingBox("circleP" + prov, isize, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE));
			overall.addBox(new ExpandingBox(null, new UDimension(0, 2), ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE));
			overall.addBox(new ExpandingBox("textP" + prov, new UBounds(new ZText(prov).getBounds()).getDimension(), ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE));
			container.addBox(border);
			nothing = false;
		}
		for (String prov : required)
		{
			ContainerBox overall = new ContainerBox("totalR" + prov, new VerticalLayout());
			BorderBox border = new BorderBox(null, new UDimension(4, 4), overall);
			
			overall.addBox(new ExpandingBox("circleR" + prov, isize, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE));
			overall.addBox(new ExpandingBox(null, new UDimension(0, 2), ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE));
			overall.addBox(new ExpandingBox("textR" + prov, new UBounds(new ZText(prov).getBounds()).getDimension(), ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE));
			container.addBox(border);
			nothing = false;
		}
		BorderBox border;
		BorderBox inner = new BorderBox(null, new UDimension(8, 8), container);
		String n = "fill";
		int sideOffset = nothing ? 10 : 24;
		switch (num)
		{
			case 0:
				border = new BorderBox(n, new UDimension(0, 0), new UDimension(0, sideOffset), inner);
				break;
			case 1:
				border = new BorderBox(n, new UDimension(0, 0), new UDimension(sideOffset, 0), inner);
				break;
			case 2:
				border = new BorderBox(n, new UDimension(0, sideOffset), new UDimension(0, 0), inner);
				break;
			case 3:
			default:
				border = new BorderBox(n, new UDimension(sideOffset, 0), new UDimension(0, 0), inner);
				break;
		}
		OuterBox outer = new OuterBox(null, UBounds.ZERO, true, true, false, border);

		try
		{
			outer.computeLayoutBounds();
		} catch (LayoutException e)
		{
			// shouldn't happen, the layout is quite simple
		}
		
		// work out where the top left point is
		UPoint top;
		UDimension full = outer.getLayoutBounds().getDimension();
		double w = full.getWidth();
		double h = full.getHeight();
		
		// work out the offset so we can make a single interface align with the port
		double offset = nothing ? 0 : new ZText("H").getBounds().height / 2 + 1;
		
		switch (num)
		{
			case 0:
				top = middle.subtract(new UDimension(w/2, h + 2));
				break;
			case 1:
				top = middle.subtract(new UDimension(w + 2, h/2 - offset));
				break;
			case 2:
				top = middle.subtract(new UDimension(w/2, -2));
				break;
			case 3:
			default:
				top = middle.subtract(new UDimension(-2, h/2 - offset));
				break;
		}
		outer.offsetLayoutBounds(top.asDimension());
		return outer;
	}
}

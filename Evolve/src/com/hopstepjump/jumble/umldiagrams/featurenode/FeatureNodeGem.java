package com.hopstepjump.jumble.umldiagrams.featurenode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @todo
 * 	add a cmd like language to allow non-text properties to be specified e.g. [ac-] == abstract classifier private
 */


public final class FeatureNodeGem implements Gem
{
  private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

	private static final Color RED = new Color(255, 160, 160);
	private static final Color GREEN = new Color(160, 255, 160);
	private static final Color ORANGE = Color.ORANGE;

	private Color fillColor = Color.white;
	private Font font = ScreenProperties.getPrimaryFont();
	private boolean selectParentWhenTextSelected = false;
  private String name = "";
  private VisibilityKind accessType = VisibilityKind.PUBLIC_LITERAL;
  private boolean classifierScope;
  private boolean displayingLinkMark;
  
  private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private TextableFacet textableFacet = new TextableFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  private FeatureTypeFacet featureTypeFacet;
  private FeatureComparableFacet comparableFacet = new FeatureComparableFacetImpl();
  private Element subject;
  private int stereotypeHashcode;
  private ClipboardActionsFacet clipboardCommandsFacet = new ClipboardActionsFacetImpl();
    
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
      final String uuid = getOriginalSubject(subject).getUuid();      
      getFeatureCompartment().addDeleted(uuid);
    }

    private FeatureCompartmentFacet getFeatureCompartment()
    {
      FigureFacet parent = figureFacet.getContainedFacet().getContainer().getFigureFacet();
      return (FeatureCompartmentFacet)
        parent.getDynamicFacet(FeatureCompartmentFacet.class);
    }
    
    public boolean hasSpecificKillAction()
    {
      return isSubjectAFeature() && (isOutOfPlace() || !atHome());
    }

    /** returns true if the element is out of place */
    private boolean isOutOfPlace()
    {
      return extractVisualClassifier() != getSubjectAsFeature().getOwner();
    }

    public void makeSpecificKillAction(ToolCoordinatorFacet coordinator)
    {
      // be defensive
      if (figureFacet.getContainedFacet().getContainer() == null)
        return;
      
      // only allow changes in the home stratum
      if (!atHome())
      {
        coordinator.displayPopup(ERROR_ICON, "Delta error",
            new JLabel("Must be in home stratum to delete subjects!", DELTA_ICON, JLabel.LEFT),
            ScreenProperties.getUndoPopupColor(),
            Color.black,
            3000);
        return;
      }

      // if this is a replace, kill the replace delta
      Feature feature = getSubjectAsFeature();
      if (feature.getOwner() instanceof DeltaReplacedConstituent && feature.getOwner().getOwner() == extractVisualClassifier())
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
      
      Feature feature = getSubjectAsFeature();
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
      
      Classifier classifier = extractVisualClassifier();      
      featureTypeFacet.generateDeleteDelta(coordinator, classifier);      
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
  
  private class FeatureComparableFacetImpl implements FeatureComparableFacet
  {
  	public int compareTo(FeatureComparableFacet other)
  	{
	  	return new Double(getY()).compareTo(new Double(other.getY()));
	  }
	  
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classdiagram.featurenode.FeatureComparableFacet#getY()
		 */
		public double getY()
		{
			return figureFacet.getFullBounds().getTopLeftPoint().getY();
		}
  }
  
	private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
	{
		public Manipulators getSelectionManipulators(
		    ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
	  {
			FeatureSizeInfo info = makeCurrentInfo();
			FeatureSizes sizes = info.makeSizes();
	
	    ManipulatorFacet keyFocus = null;
	    ManipulatorFacet iconManipulator =
		      new VisibilityManipulatorGem(
		          accessType,
		          sizes.getIconPoint(),
		          sizes.getText().getBounds().getHeight(),
		          featureTypeFacet.getFeatureType(),
		          firstSelected).getManipulatorFacet();
	
	    if (favoured)
			{
	      TextManipulatorGem textGem = new TextManipulatorGem(
	      		coordinator,
            "change " + getFigureName() + " details",
            "restore " + getFigureName() + " details",
            name,
            featureTypeFacet.makeShortName(name),
            font,
            Color.black,
            fillColor,
            TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE_DISPLAY_AS_IF_EDITING,
            allowTYPE0Manipulators ? ManipulatorFacet.TYPE0: ManipulatorFacet.TYPE2);
	      textGem.connectTextableFacet(textableFacet);
	      keyFocus = textGem.getManipulatorFacet();
			}  
			
	    Manipulators manipulators = new Manipulators(keyFocus, iconManipulator);
	    return manipulators;
	  }
	
	  public ZNode formView()
	  {
			// make and return a group that will draw this operation
			FeatureSizeInfo info = makeCurrentInfo();
			FeatureSizes sizes = info.makeSizes();
	    
	    // group them:
	    // 	 the parent group pretends to be like the owner -- if we have a parent group that is...
	    ZGroup parentGroup = new ZGroup();
	    parentGroup.setChildrenPickable(false);
	    parentGroup.setChildrenFindable(false);
	    FigureFacet reference = figureFacet;
	    ContainerFacet container = figureFacet.getContainedFacet().getContainer();
	    if (selectParentWhenTextSelected && container != null)
	    	reference = container.getFigureFacet();
	    parentGroup.putClientProperty("figure", reference);		  
	    parentGroup.addChild(new ZVisualLeaf(sizes.getText()));
	    
	    // add a possible underline for a static operation
	    UBounds textBounds = new UBounds(sizes.getText().getBounds());
	    if (classifierScope)
	    {
	    	parentGroup.addChild(new ZVisualLeaf(new ZLine(textBounds.getBottomLeftPoint(), textBounds.getBottomRightPoint())));
	    }
	    
			// the icon is always in a group which is managed by this figure
	    ZGroup myGroup = new ZGroup();
	    myGroup.setChildrenPickable(false);
	    myGroup.setChildrenFindable(false);
		  myGroup.putClientProperty("figure", figureFacet);
      boolean isEnvironment = false;
      // the next check is ugly and should go elsewhere
      // also, we shouldn't need the deletion check...
      if (subject instanceof Slot && !hasSubjectBeenDeleted())
      {
        isEnvironment =
          StereotypeUtilities.extractBooleanProperty(getSubjectAsSlot(), CommonRepositoryFunctions.OVERRIDDEN_SLOT_ALIAS);
      	Slot slot = (Slot) subject;
      	if (slot.undeleted_getValues().size() > 0)
      	{
      		ValueSpecification spec = (ValueSpecification) slot.undeleted_getValues().get(0);
      		if (spec instanceof PropertyValueSpecification)
      			isEnvironment = ((PropertyValueSpecification) spec).isAliased();
      	}
      }
      
      ZNode icon =
        makeIcon(
            accessType,
            true,
            sizes.getIconPoint(),
            sizes.getText().getBounds().getHeight(),
            null,
            featureTypeFacet.getFeatureType(),
            isEnvironment);
      myGroup.addChild(icon);

      if (sizes.getLinkMark() != null)
		    myGroup.addChild(sizes.getLinkMark());
	
			ZGroup group = new ZGroup();
			group.addChild(myGroup);
			group.addChild(parentGroup);
	    return group;
	  }

	  public String getFigureName()
	  {
	  	return featureTypeFacet.getFigureName();
	  }
	  
	  public UBounds getAutoSizedBounds(boolean autoSized)
	  {
	  	return null;
	  }
	  
	  public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
	  {
	  	BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
			FeaturePreviewGem previewGem = new FeaturePreviewGem();
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			previewGem.connectPreviewFacet(basicGem.getPreviewFacet());
      basicGem.setCentreToEdge(true);
			
			return basicGem.getPreviewFacet();
	  }
  
	  public JPopupMenu makeContextMenu(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
	  	// slots have no menu
	  	if (!isSubjectAFeature())
	  		return null;
	  	
			// populate the menu
			JPopupMenu popup = new JPopupMenu();
			
		  // only add a replace if this is not visually at home
		  SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
		  Namespace visual = repository.findVisuallyOwningNamespace(diagramView.getDiagram(), figureFacet.getContainedFacet().getContainer());
		  Namespace real = (Namespace)
		    GlobalSubjectRepository.repository.findOwningElement(
		        getSubjectAsFeature(),
		        ClassifierImpl.class); 
		      
		  boolean enabled =
		  	visual != real &&
		    !figureFacet.getContainedFacet().getContainer().getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
	    JMenuItem replaceItem = featureTypeFacet.getReplaceItem(diagramView, coordinator);
	    if (replaceItem != null)
	    {
	    	replaceItem.setEnabled(enabled);
	      popup.add(replaceItem);
	    }
		  
      Utilities.addSeparator(popup);
			JMenu visibility = new JMenu("Visibility");
			visibility.setEnabled(!figureFacet.isSubjectReadOnlyInDiagramContext(false));
			visibility.add(getAccessItem(coordinator, VisibilityKind.PUBLIC_LITERAL,      "Public"));
			visibility.add(getAccessItem(coordinator, VisibilityKind.PROTECTED_LITERAL,   "Protected"));
			visibility.add(getAccessItem(coordinator, VisibilityKind.PACKAGE_LITERAL,     "Package"));
			visibility.add(getAccessItem(coordinator, VisibilityKind.PRIVATE_LITERAL,     "Private"));
      visibility.add(getAccessItem(coordinator, VisibilityKind.PUBLIC_LITERAL,      "Environment"));
			popup.add(visibility);
      Utilities.addSeparator(popup);
		  popup.add(getClassifierScopeItem(coordinator));
	
			return popup;
		}
	  
		private JCheckBoxMenuItem getClassifierScopeItem(final ToolCoordinatorFacet coordinator)
		{
			JCheckBoxMenuItem scopeItem = new JCheckBoxMenuItem("Classifier scope");
			scopeItem.setState(classifierScope);
	
			scopeItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// adjust the visibility
					coordinator.startTransaction("changed scope of " + getFigureName() + " to " + (classifierScope ? "instance" : "classifier"), "restored scope of " + getFigureName() + " to " + (classifierScope ? "classifier" : "instance"));
					Feature feature = getSubjectAsFeature();
		      feature.setIsStatic(!classifierScope);
					coordinator.commitTransaction();
				}
			});
			return scopeItem;
		}
	
		private JCheckBoxMenuItem getAccessItem(final ToolCoordinatorFacet coordinator, final VisibilityKind newAccessType, final String name)
		{
			JCheckBoxMenuItem accessItem = new JCheckBoxMenuItem(name);
			accessItem.setState(getSubjectAsFeature().getVisibility().equals(newAccessType));
	
			accessItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// adjust the visibility
					coordinator.startTransaction(
							"changed visibility of " + getFigureName() + " to " + newAccessType.getName(),
							"restored visibility of " + getFigureName() + " to " + accessType.getName());
					Feature feature = getSubjectAsFeature();
			  	feature.setVisibility(newAccessType);
					coordinator.commitTransaction();
				}
			});
			return accessItem;
		}
		
		public UDimension getCreationExtent()
		{
			String shortName = featureTypeFacet.makeShortName(name);
			FeatureSizeInfo info =
			  new FeatureSizeInfo(
			      new UPoint(0,0),
			      font,
			      shortName,
			      displayingLinkMark);

			return info.makeSizes().getEntireBounds().getDimension();
		}

		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
		}
		
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#acceptsContainer(ContainerFacet)
		 */
		public boolean acceptsContainer(ContainerFacet container)
		{
	  	// can't check any more here, as the compartment is never explicitly identified
	  	// -- the acceptance of this item is checked in the container also
	  	if (container == null)
	  		return false;
	  		
	  	// check if the container has a dynamic facet of 
	  	if (!container.getFigureFacet().hasDynamicFacet(FeatureAcceptorFacet.class))
	  		return false;

			FeatureAcceptorFacet acceptor = (FeatureAcceptorFacet) container.getFigureFacet().getDynamicFacet(FeatureAcceptorFacet.class);	  		
	  	return acceptor.acceptsFeatureType(featureTypeFacet.getFeatureType());
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
			return makeCurrentInfo().makeSizes().getEntireBounds();
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("name", name, ""));
			properties.add(new PersistentProperty("stereoHash", stereotypeHashcode, 0));
			properties.add(new PersistentProperty("access", accessType.getValue(), 0));
			properties.add(new PersistentProperty("classifierScope", classifierScope, false));
		}

    /**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
    public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
    {
      if (pass != ViewUpdatePassEnum.LAST)
        return;
      
      int actualStereotypeHashcode = StereotypeUtilities.calculateStereotypeHash(null, subject);
    	if (isSubjectAFeature())
    		formFeatureViewUpdateCommandAfterSubjectChanged(actualStereotypeHashcode);
    	else
    		formSlotViewUpdateCommandAfterSubjectChanged(actualStereotypeHashcode);
    		
    }

		public void formSlotViewUpdateCommandAfterSubjectChanged(int actualStereotypeHashcode)
		{
			// if neither the name or the namespace has changed, or the in-placeness, suppress any command
			final boolean shouldBeDisplayingLinkMark = figureFacet.getAnchorFacet().hasLinks();
      final String newName = featureTypeFacet.makeNameFromSubject();
			if (displayingLinkMark == shouldBeDisplayingLinkMark &&
          name.equals(newName) &&
          stereotypeHashcode == actualStereotypeHashcode)
				return;			
      
			updateSlotViewAfterSubjectChanged(true);
		}


		public void formFeatureViewUpdateCommandAfterSubjectChanged(int actualStereotypeHashCode)
		{
			final Feature feature = getSubjectAsFeature();
			
			// if neither the name or the namespace has changed, or the in-placeness, suppress any command
			final boolean shouldBeDisplayingLinkMark = figureFacet.getAnchorFacet().hasLinks();
      final String newName = featureTypeFacet.makeNameFromSubject();
      
      // if nothing has changed, don't worry
			if (displayingLinkMark == shouldBeDisplayingLinkMark &&
          accessType.equals(feature.getVisibility()) &&
          classifierScope == feature.isStatic() &&
          name.equals(newName) &&
          stereotypeHashcode == actualStereotypeHashCode)
				return;			

			updateFeatureViewAfterSubjectChanged();
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
			return subject;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#hasSubjectBeenDeleted()
		 */
		public boolean hasSubjectBeenDeleted()
		{
			return subject.isThisDeleted();
		}

    public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters)
    {
    }

    public void performPostContainerDropTransaction()
    {
      featureTypeFacet.performPostContainerDropTransaction();
    }

		public boolean canMoveContainers()
		{
			// slots cannot move containers...
			return isSubjectAFeature() ? true : false;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return featureTypeFacet.isSubjectReadOnlyInDiagramContext(kill);
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }
    
    public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return new ToolFigureClassification("feature", null);
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
	}
	
	private class TextableFacetImpl implements TextableFacet
	{
	  public UBounds getTextBounds(String name)
	  {
	  	FeatureSizeInfo info = makeCurrentInfo();
	  	info.setName(name);
	  	FeatureSizes sizes = info.makeSizes();
	  	return new UBounds(sizes.getText().getBounds()).addToPoint(new UDimension(0, -1));
	  }
	
	  public UBounds vetTextResizedExtent(String name)
	  {
	  	FeatureSizeInfo info = makeCurrentInfo();
	  	info.setName(featureTypeFacet.makeShortName(featureTypeFacet.makeNameFromSubject()));
	  	FeatureSizes sizes = info.makeSizes();
	  	return sizes.getEntireBounds();
	  }
	
	  public FigureFacet getFigureFacet()
	  {
	  	return figureFacet;
	  }

    public void setText(String text, Object listSelection, boolean unsuppress)
    {
      name = featureTypeFacet.setText(text, listSelection);
    }

    public JList formSelectionList(String textSoFar)
    {
      return featureTypeFacet.formSelectionList(textSoFar);
    }
	}
	
  private void updateFeatureViewAfterSubjectChanged()
  {
    final boolean shouldBeDisplayingLinkMark = figureFacet.getAnchorFacet().hasLinks();
    final String newName = featureTypeFacet.makeNameFromSubject();

    // set the new variables
    Feature feature = getSubjectAsFeature();
    displayingLinkMark = shouldBeDisplayingLinkMark;
    accessType = feature.getVisibility();
    classifierScope = feature.isStatic();
    name = newName;
    stereotypeHashcode = StereotypeUtilities.calculateStereotypeHash(null, subject);
    
    // resize, using a text utility
    figureFacet.performResizingTransaction(makeCurrentInfo().makeSizes().getEntireBounds());
  }

  public void updateSlotViewAfterSubjectChanged(boolean isTop)
  {
    boolean shouldBeDisplayingLinkMark = figureFacet.getAnchorFacet().hasLinks();
    String newName = featureTypeFacet.makeNameFromSubject();

    // set the new variables
    displayingLinkMark = shouldBeDisplayingLinkMark;
    name = newName;
    stereotypeHashcode = StereotypeUtilities.calculateStereotypeHash(null, subject);
    
    // resize, using a text utility
    figureFacet.performResizingTransaction(makeCurrentInfo().makeSizes().getEntireBounds());
  }

  public FeatureNodeGem(Element subject)
  {
    this.subject = subject;
  }
  
  public void setSelectParentWhenTextSelected(boolean selectParentWhenTextSelected)
  {
    this.selectParentWhenTextSelected = selectParentWhenTextSelected;
  }
  
  public FeatureNodeGem(PersistentFigure pfig)
  {
    // retrieve the subject
  	interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
    subject = (Element) pfig.getSubject();
    name = pfig.getProperties().retrieve("name", "").asString();
    stereotypeHashcode = pfig.getProperties().retrieve("stereoHash", 0).asInteger();
    accessType = VisibilityKind.get(pfig.getProperties().retrieve("access", 0).asInteger());
    classifierScope = pfig.getProperties().retrieve("classifierScope", false).asBoolean();
	}

	public String getName()
  {
  	return name;
  }

  private FeatureSizeInfo makeCurrentInfo()
  {
  	String shortName = featureTypeFacet.makeShortName(name);
		FeatureSizeInfo info =
		  new FeatureSizeInfo(
		      figureFacet.getFullBounds().getTopLeftPoint(),
		      font,
		      shortName,
		      displayingLinkMark);
		return info;
  }
  
  public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
  	figureFacet.registerDynamicFacet(textableFacet, TextableFacet.class);
  	figureFacet.registerDynamicFacet(comparableFacet, FeatureComparableFacet.class);
  	figureFacet.registerDynamicFacet(featureTypeFacet, FeatureTypeFacet.class);
  }
  
  public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
  {
  	return appearanceFacet;
  }
  
  public TextableFacet getTextableFacet()
  {
    return textableFacet;
  }
  
  public void connectFeatureTypeFacet(FeatureTypeFacet featureTypeFacet)
  {
  	this.featureTypeFacet = featureTypeFacet;
  }
  
  public FigureFacet getFigureFacet()
  {
  	return figureFacet;
  }

	public static ZNode makeIcon(VisibilityKind accessType, boolean paintBackground, UPoint point, double textHeight,
      Color reverseColor, int featureType, boolean isEnvironment)
  {
    // determine the fill paint and the pen paint
    Color fillPaint = Color.white;
    if (featureType == 2)
    {
    	if (!isEnvironment)
    		fillPaint = Color.CYAN; 
    }
    else
    if (paintBackground && featureType != 2)
    {
      if (accessType.equals(VisibilityKind.PUBLIC_LITERAL))
        fillPaint = GREEN;
      else
      if (accessType.equals(VisibilityKind.PROTECTED_LITERAL))
        fillPaint = ORANGE;
      else
      if (accessType.equals(VisibilityKind.PACKAGE_LITERAL))
        fillPaint = ORANGE;
      else
      if (accessType.equals(VisibilityKind.PRIVATE_LITERAL))
        fillPaint = RED;
    }
    if (reverseColor != null)
    	fillPaint = reverseColor;
    
    return new FancyRectangleMaker(new UBounds(point.add(new UDimension(0, 4)), new UDimension(textHeight-4, textHeight-5)), 2, fillPaint.darker().darker(), true, 2).make();
  }

	private Feature getSubjectAsFeature()
	{
		return (Feature) subject;
	}
	
	/** return the replaced feature is this is a replacement, otherwise just return the feature */
  public static Element getOriginalSubject(Object subject)
  {
    Element element = (Element) subject;
    if (element.getOwner() instanceof DeltaReplacedConstituent)
      return ((DeltaReplacedConstituent) element.getOwner()).getReplaced();
    return element;
  }
  
  public static Element getPossibleDeltaSubject(Object subject)
  {
    Element element = (Element) subject;
    if (element.getOwner() instanceof DeltaReplacedConstituent)
      return element.getOwner();
    return element;
  }
  
	private Slot getSubjectAsSlot()
	{
		return (Slot) subject;
	}
	
	private boolean isSubjectAFeature()
	{
		return subject instanceof Feature;
	}
}
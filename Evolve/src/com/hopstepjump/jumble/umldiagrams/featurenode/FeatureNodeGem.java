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

	private static final UDimension ICON_OFFSET = new UDimension(1, 1);
	private static final Color RED = new Color(255, 160, 160);
	private static final Color GREEN = new Color(160, 255, 160);
	private static final Color ORANGE = Color.ORANGE;
	private static final boolean USE_ELLIPSE_FOR_ALL_ICONS = false;

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
  private ScopeFacet scopeFacet = new ScopeFacetImpl();
  private VisibilityFacet visibilityFacet = new VisibilityFacetImpl();
  private FeatureComparableFacet comparableFacet = new FeatureComparableFacetImpl();
  private Element subject;
  private int stereotypeHashcode;
  private ClipboardCommandsFacet clipboardCommandsFacet = new ClipboardCommandsFacetImpl();
    
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
      // important to use the reference rather than the figure, which gets recreated...
      final String uuid = getOriginalSubject(subject).getUuid();      
      getFeatureCompartment().addDeleted(uuid);
      return null;
    }

    private FeatureCompartmentFacet getFeatureCompartment()
    {
      FigureFacet parent = figureFacet.getContainedFacet().getContainer().getFigureFacet();
      return (FeatureCompartmentFacet)
        parent.getDynamicFacet(FeatureCompartmentFacet.class);
    }
    
    public boolean hasSpecificKillCommand()
    {
      return isSubjectAFeature() && (isOutOfPlace() || !atHome());
    }

    /** returns true if the element is out of place */
    private boolean isOutOfPlace()
    {
      return extractVisualClassifier() != getSubjectAsFeature().getOwner();
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
      Feature feature = getSubjectAsFeature();
      if (feature.getOwner() instanceof DeltaReplacedConstituent && feature.getOwner().getOwner() == extractVisualClassifier())
        return generateReplaceDeltaKill(coordinator);
      else
        return generateDeleteDelta(coordinator);
    }

    private Command generateReplaceDeltaKill(ToolCoordinatorFacet coordinator)
    {
      // generate a delete delta for the replace
      coordinator.displayPopup(null, null,
          new JLabel("Removed replace delta", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      final Feature feature = getSubjectAsFeature();
      
      return new AbstractCommand("Removed replace delta", "Restored replace delta")
      {
        public void execute(boolean isTop)
        {
          GlobalSubjectRepository.repository.incrementPersistentDelete(feature.getOwner());            
        }

        public void unExecute()
        {
          GlobalSubjectRepository.repository.decrementPersistentDelete(feature.getOwner());
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
      
      return featureTypeFacet.generateDeleteDelta(coordinator, classifier);      
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
  
  private class VisibilityFacetImpl implements VisibilityFacet
  {
		public Object setVisibility(VisibilityKind newAccessType)
	  {
			Feature feature = getSubjectAsFeature();
	  	VisibilityKind oldAccessType = feature.getVisibility();
	  	feature.setVisibility(newAccessType);
	  	figureFacet.adjusted();
	  	return oldAccessType;
	  }
	  
	  public void unSetVisibility(Object memento)
	  {
	  	getSubjectAsFeature().setVisibility((VisibilityKind) memento);
			figureFacet.adjusted();
	  }
  }
  
  private class ScopeFacetImpl implements ScopeFacet
  {
	 	/**
		 * @see com.hopstepjump.jumble.umldiagrams.classdiagram.featurenode.CmdScopeable#setScope(boolean)
		 */
		public Object setScope(boolean newClassifierScope)
		{
			Feature feature = getSubjectAsFeature();
      boolean oldScope = feature.isStatic();
      feature.setIsStatic(newClassifierScope);
			figureFacet.adjusted();
			
			return oldScope;
		}
	
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classdiagram.featurenode.CmdScopeable#unSetScope(Object)
		 */
		public void unSetScope(Object memento)
		{
			Boolean oldClassifierScope = (Boolean) memento;
      getSubjectAsFeature().setIsStatic(oldClassifierScope);
			figureFacet.adjusted();
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
		      
		  if (visual != real &&
		      !figureFacet.getContainedFacet().getContainer().getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
		  {
		    JMenuItem replaceItem = featureTypeFacet.getReplaceItem(diagramView, coordinator);
		    if (replaceItem != null)
		      popup.add(replaceItem);
		  }
		  
      if (!figureFacet.isSubjectReadOnlyInDiagramContext(false))
      {
        Utilities.addSeparator(popup);
  			JMenu visibility = new JMenu("Visibility");
  			visibility.add(getAccessItem(coordinator, VisibilityKind.PUBLIC_LITERAL,      "Public"));
  			visibility.add(getAccessItem(coordinator, VisibilityKind.PROTECTED_LITERAL,   "Protected"));
  			visibility.add(getAccessItem(coordinator, VisibilityKind.PACKAGE_LITERAL,     "Package"));
  			visibility.add(getAccessItem(coordinator, VisibilityKind.PRIVATE_LITERAL,     "Private"));
        visibility.add(getAccessItem(coordinator, VisibilityKind.PUBLIC_LITERAL,      "Environment"));
  			popup.add(visibility);
        Utilities.addSeparator(popup);
  		  popup.add(getClassifierScopeItem(coordinator));
			}
	
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
					SetScopeCommand scopeCommand = new SetScopeCommand(figureFacet.getFigureReference(), !classifierScope, "changed scope of " + getFigureName() + " to " + (classifierScope ? "instance" : "classifier"), "restored scope of " + getFigureName() + " to " + (classifierScope ? "classifier" : "instance"));
					coordinator.executeCommandAndUpdateViews(scopeCommand);
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
					SetVisibilityCommand visibilityCommand = new SetVisibilityCommand(figureFacet.getFigureReference(), newAccessType, "changed visibility of " + getFigureName() + " to " + newAccessType.getName(), "restored visibility of " + getFigureName() + " to " + accessType.getName());
					coordinator.executeCommandAndUpdateViews(visibilityCommand);
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

			updateFeatureViewAfterSubjectChanged(true);
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
	    name = pfig.getProperties().retrieve("name", "").asString();
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
      featureTypeFacet.setText(text, listSelection);
    }

    public JList formSelectionList(String textSoFar)
    {
      return featureTypeFacet.formSelectionList(textSoFar);
    }
	}
	
  private void updateFeatureViewAfterSubjectChanged(boolean isTop)
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
  
  public FeatureNodeGem(PersistentFigure figure)
  {
    // retrieve the subject
    subject = (Element) figure.getSubject();
    name = figure.getProperties().retrieve("name", "").asString();
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
  	figureFacet.registerDynamicFacet(visibilityFacet, VisibilityFacet.class);
  	figureFacet.registerDynamicFacet(scopeFacet, ScopeFacet.class);
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
		// handle slot icons differently
		if (featureType == 2)
			return makeSlotIcon(point, textHeight, reverseColor, isEnvironment);
		
    double scaler = 0.8;
    double fullSize = ((int) (textHeight * scaler / 2)) * 2;
    ZGroup group = new ZGroup();

    // make up the access icon
    UDimension iconExtent = new UDimension(fullSize, fullSize);
    UBounds ellBounds = new UBounds(point.add(new UDimension(0, (textHeight - iconExtent.getHeight()) / 2)), iconExtent);
    ellBounds = ellBounds.addToPoint(ICON_OFFSET);
    ZShape ell;
    if (featureType == 1 || USE_ELLIPSE_FOR_ALL_ICONS)
      ell = new ZEllipse(ellBounds.getPoint().getX(), ellBounds.getPoint().getY(), ellBounds.getWidth(), ellBounds
          .getHeight());
    else
      ell = new ZRectangle(ellBounds);

    group.addChild(new ZVisualLeaf(ell));

    // draw some nice lines indicating that this is a ray traced ball :-)
    double size = (int) (fullSize * 0.2); // important to round to integer, or
                                          // lines look asymmetrical
    if (reverseColor != null)
      size++;

    // determine the fill paint and the pen paint
    Color fillPaint = Color.white;
    Color paint = Color.black;
    if (paintBackground)
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
    {
      paint = Color.black;
      fillPaint = reverseColor;
    }
    ell.setFillPaint(fillPaint);

    // draw public icon
    UPoint middle = ellBounds.getMiddlePoint();
    if (accessType.equals(VisibilityKind.PUBLIC_LITERAL))
    {
      ZLine line1 = new ZLine(middle.subtract(new UDimension(0, size)), middle.add(new UDimension(0, size)));
      line1.setPenPaint(paint);
      ZLine line2 = new ZLine(middle.subtract(new UDimension(size, 0)), middle.add(new UDimension(size, 0)));
      line2.setPenPaint(paint);
      group.addChild(new ZVisualLeaf(line1));
      group.addChild(new ZVisualLeaf(line2));
    }
    else
    if (accessType.equals(VisibilityKind.PROTECTED_LITERAL))
    {
      double offset = (int) (fullSize * 0.1);
      ZLine linev1 = new ZLine(middle.subtract(new UDimension(offset, size)), middle
          .add(new UDimension(-offset, size)));
      linev1.setPenPaint(paint);
      ZLine linev2 = new ZLine(middle.subtract(new UDimension(-offset, size)), middle
          .add(new UDimension(offset, size)));
      linev2.setPenPaint(paint);
      ZLine lineh1 = new ZLine(middle.subtract(new UDimension(size, offset)), middle
          .add(new UDimension(size, -offset)));
      lineh1.setPenPaint(paint);
      ZLine lineh2 = new ZLine(middle.subtract(new UDimension(size, -offset)), middle
          .add(new UDimension(size, offset)));
      lineh2.setPenPaint(paint);
      group.addChild(new ZVisualLeaf(linev1));
      group.addChild(new ZVisualLeaf(linev2));
      group.addChild(new ZVisualLeaf(lineh1));
      group.addChild(new ZVisualLeaf(lineh2));
    }
    else
    if (accessType.equals(VisibilityKind.PACKAGE_LITERAL))
    {
      double offset = (int) (fullSize * 0.1);
      ZPolyline line = new ZPolyline();
      line.add(middle.add(new UDimension(-offset * 3, offset)));
      line.add(middle.add(new UDimension(-offset, -offset)));
      line.add(middle.add(new UDimension(offset, offset)));
      line.add(middle.add(new UDimension(offset * 3, -offset)));
      line.setPenPaint(paint);
      group.addChild(new ZVisualLeaf(line));
    }
    else
    if (accessType.equals(VisibilityKind.PRIVATE_LITERAL))
    {
      ZLine line = new ZLine(middle.subtract(new UDimension(size, 0)), middle.add(new UDimension(size, 0)));
      line.setPenPaint(paint);
      group.addChild(new ZVisualLeaf(line));
    }

    double rayOffset = (int) (fullSize * 0.4);
    ZLine rayLine = new ZLine(middle.subtract(new UDimension(rayOffset, 0)), middle.subtract(new UDimension(0,
        rayOffset)));
    rayLine.setPenPaint(Color.white);
    group.addChild(new ZVisualLeaf(rayLine));

    return group;
  }
  
	public static ZNode makeSlotIcon(UPoint point, double textHeight, Color reverseColor, boolean isEnvironment)
  {
	  // get the middle point, and move out from there
	  UBounds bounds = new UBounds(point.add(new UDimension(0, 2)), new UDimension(textHeight, textHeight));
	  UPoint middle = bounds.getMiddlePoint();
	  UDimension offset = new UDimension(2, 2);
	  if (reverseColor != null)
	    offset = new UDimension(4, 4);
    if (isEnvironment)
      offset = new UDimension(5, 5);
	  UBounds smallSquare = new UBounds(middle.subtract(offset), offset.multiply(2)).addToPoint(new UDimension(-1, -1));

	  ZShape shape =
	    new ZRectangle(
          smallSquare.getTopLeftPoint().getX(),
          smallSquare.getTopLeftPoint().getY(),
          smallSquare.getWidth(),
          smallSquare.getHeight());
    if (isEnvironment)
      shape.setFillPaint(Color.WHITE);
    else
      shape.setFillPaint(Color.GRAY);
    
    if (reverseColor != null)
      shape.setFillPaint(reverseColor);
    
    ZGroup group = new ZGroup(new ZVisualLeaf(shape));
    
    // place a transparent cover over
    ZShape cover =
      new ZRectangle(
          bounds.getTopLeftPoint().getX(),
          bounds.getTopLeftPoint().getY(),
          bounds.getWidth(),
          bounds.getHeight());
    cover.setFillPaint(ScreenProperties.getTransparentColor());
    cover.setPenPaint(null);
    group.addChild(new ZVisualLeaf(cover));

    return group;
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
package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.figurefacilities.selectionbase.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.idraw.nodefacilities.style.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.deltaview.*;
import com.hopstepjump.jumble.expander.*;
import com.hopstepjump.jumble.gui.lookandfeel.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.basicnamespacenode.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;
import com.hopstepjump.jumble.umldiagrams.tracearc.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


// TODO:
// (done) 1. prevent retargeting of non-replaced link
// (done) 3. delta view for reqfeatures + links
// (done) 1b. fix retargeting of start 
// (done) 1a. fix ellipsis
// (done) 2. cleanuuids in reqfeaturenode?
// (done) 4. expand mechanism for full subfeatures (+ others?)
// (done) 1c. fix subject deletion of link when type is deleted + immutable trace flag + traces on classifier
// (done) 5. traces (from classifier + ellipsis)
// 6. requirements composition explorer

public final class RequirementsFeatureNodeGem implements Gem
{
  private static final ImageIcon COMPOSITION_ICON = IconLoader.loadIcon("composition.png");

	private Font font = ScreenProperties.getTitleFont();
	private Font packageFont = ScreenProperties.getSecondaryFont();
	
	private RequirementsFeature subject;
	
	private Color fillColor;
  private Color initialFillColor;
	private Color lineColor = Color.BLACK;
	
	/** persistent attributes */
	private boolean autoSized = true;
	private boolean displayOnlyIcon = false;
	private String name = "";
	private UDimension rememberedTLOffset = new UDimension(0, 0);
	private UDimension rememberedBROffset = new UDimension(0, 0);
	
	private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
	private TextableFacet textableFacet = new TextableFacetImpl();
	private ResizeVetterFacet resizeVetterFacet = new ResizeVetterFacetImpl();
	private ClassifierNodeFacetImpl classifierFacet = new ClassifierNodeFacetImpl();
	private DisplayAsIconFacet displayAsIconFacet = new DisplayAsIconFacetImpl();
	private LocationFacet locationFacet = new LocationFacetImpl();
	private AutoSizedFacetImpl autosizedFacet = new AutoSizedFacetImpl();
	private BasicNodeFigureFacet figureFacet;
	private RequirementsFeatureMiniAppearanceFacet miniAppearanceFacet;
  private boolean showStereotype = true;
	
  private String owner = "";
  private boolean showOwningPackage;
  private boolean forceSuppressOwningPackage = false;
  private int stereotypeHashcode;
  private SimpleDeletedUuidsFacetImpl deletedConnectorUuidsFacet = new SimpleDeletedUuidsFacetImpl();
  private Set<String> addedUuids = new HashSet<String>();
  private Set<String> deletedUuids = new HashSet<String>();
  private boolean bodyEllipsis = false;
  private boolean retired = false;
	private SwitchSubjectFacet switchableFacet = new SwitchSubjectFacetImpl();
	private UBounds arc2;
	private double arc2Start;
	private double arc2End;
	private UBounds arc3;
	private double arc3Start;
	private double arc3End;
	
	private class SwitchSubjectFacetImpl implements SwitchSubjectFacet
	{
		public void switchSubject(Object newSubject)
		{
			subject = (RequirementsFeature) newSubject;
			figureFacet.updateViewAfterSubjectChanged(ViewUpdatePassEnum.LAST);
		}
	};
  
  private class SimpleDeletedUuidsFacetImpl implements SimpleDeletedUuidsFacet
  {
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

  private boolean isEllipsisForBody()
  {
		// add ellipsis if the full set of links are not expanded
		IDeltaEngine engine = GlobalDeltaEngine.engine;
		DERequirementsFeature dereq = engine.locateObject(subject).asRequirementsFeature();
    Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainerFacet());
    Set<String> uuids = new HashSet<String>();
    for (DeltaPair pair : dereq.getDeltas(ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK).getConstituents(engine.locateObject(visualHome).asStratum()))
    	uuids.add(pair.getConstituent().getUuid());
    
    // draw an ellipsis if the full set of subfeatures are not shown
    for (Iterator<LinkingFacet> iter = figureFacet.getAnchorFacet().getLinks(); iter.hasNext();)
    {
    	LinkingFacet link = iter.next();
    	if (link.getAnchor1().getFigureFacet() == figureFacet)
    		uuids.remove(UUID(link.getFigureFacet().getSubject()));
    }
		
    return !uuids.isEmpty();
  }
  
  
	private String UUID(Object subject)
	{
		return ((Element) subject).getUuid();
	}

  private void updateFeatureViewAfterSubjectChanged(int actualStereotypeHashcode, boolean ellipsisForBody)
  {
    // should we be displaying the owner?
    RequirementsFeatureProperties props = new RequirementsFeatureProperties(figureFacet);
    boolean sub = props.getElement().isSubstitution();
    boolean locatedInCorrectView = props.getPerspective() == props.getElement().getHomeStratum();

    final boolean shouldBeDisplayingOwningPackage =
    	!locatedInCorrectView && !forceSuppressOwningPackage || sub;

    // get a possible name for a substituted element
    String newName = new RequirementsFeatureProperties(figureFacet, subject).getPerspectiveName();
    
    this.bodyEllipsis = ellipsisForBody;
    
    // set the variables
    name = newName;
    retired = isElementRetired();
    showOwningPackage = shouldBeDisplayingOwningPackage;
    stereotypeHashcode = actualStereotypeHashcode;
    autoSized = shouldDisplayOnlyIcon() ? true : autoSized;
    
    // get the new stratum owner (or set to the original name in case of evolution)
    final String newOwner = sub ?
    	"(" + (retired ? "retires " : "replaces ") + props.getSubstitutesForName() + ")" :
    	"(from " + GlobalSubjectRepository.repository.getFullStratumNames((Element) props.getHomePackage().getRepositoryObject()) + ")";
    owner = newOwner;
    
    // resize, using a text utility
    DisplayAsIconTransaction.display(figureFacet, shouldDisplayOnlyIcon());
    figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(name));
  }

  private class DisplayAsIconFacetImpl implements DisplayAsIconFacet
  {
    /**
     * @see com.hopstepjump.jumble.umldiagrams.base.DisplayAsIconFacet#displayAsIcon(boolean)
     */
    public void displayAsIcon(boolean displayAsIcon)
    {
      // make the change
      displayOnlyIcon = displayAsIcon;
      
      // we are about to autosize, so need to make a resizing
      ResizingFiguresFacet resizings = new ResizingFiguresGem(null, figureFacet.getDiagram()).getResizingFiguresFacet();
      resizings.markForResizing(figureFacet);
      
      RequirementsFeatureSizeInfo info = makeCurrentInfo();
      UBounds newBounds = info.makeActualSizes().getOuter();
      UBounds centredBounds = ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), newBounds.getDimension());
      resizings.setFocusBounds(centredBounds);
      
      resizings.end();
    }
  }
  
	private class LocationFacetImpl implements LocationFacet
	{
    public void setLocation()
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      
      // locate to the diagram, or a possible nesting package
      // look upwards, until we find one that has a PackageFacet registered
      Namespace newOwner = (Namespace) figureFacet.getDiagram().getLinkedObject();
      Namespace currentOwner = (Namespace) subject.getOwner();
      Namespace containerOwner = repository.findVisuallyOwningNamespace(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
      if (containerOwner != null)
        newOwner = containerOwner;
      
      // make sure that the package is not set to be owned by itself somehow
      for (Element owner = newOwner; owner != null; owner = owner.getOwner())
        if (owner == subject)
          return;
      
      if (currentOwner instanceof Class)
        ((Class) currentOwner).getNestedClassifiers().remove(subject);
      else
        ((Package) currentOwner).getOwnedMembers().remove(subject);
      
      if (newOwner instanceof Class)
        ((Class) newOwner).getNestedClassifiers().add(subject); 
      else
        ((Package) newOwner).getOwnedMembers().add(subject); 
    }
	}
	
	private class ClassifierNodeFacetImpl implements RequirementsFeatureNodeFacet
	{
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classifiernode.ClassifierNodeFacet#getShapeForPreview(UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds)
		{
			return formShape(bounds, true);
		}
		
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classifiernode.ClassifierNodeFacet#formShapeForBoundaryCalculation(UBounds)
		 */
		public Shape formShapeForBoundaryCalculation(UBounds bounds)
		{
			return formShape(bounds, false);
		}
		
		private Shape formShape(UBounds bounds, boolean onlyIcon)
		{
			if (miniAppearanceFacet != null && displayOnlyIcon)
			{
				RequirementsFeatureSizeInfo info = makeCurrentInfo(bounds.getTopLeftPoint(), bounds.getDimension(), autoSized);
				RequirementsFeatureSizes sizes = info.makeActualSizes();
				Shape miniShape = miniAppearanceFacet.formShapeForPreview(sizes.getIcon());
				
				UBounds bodyBounds = null;
				
				if (!onlyIcon)
				{
					if (name.length() != 0)
						bodyBounds = new UBounds(sizes.getName());
					if (!forceSuppressOwningPackage && showOwningPackage)
					{
						if (bodyBounds == null)
							bodyBounds = sizes.getOwner();
						else
							bodyBounds = bodyBounds.union(sizes.getOwner());
					}
					
					// if the body bounds are not null, make sure they extend to the circle
					if (bodyBounds != null)
					{
						UDimension offset = new UDimension(0, 6);
						UBounds topBounds = new UBounds(sizes.getName().getPoint().subtract(offset), new UDimension(0, 0));
						bodyBounds = bodyBounds.union(topBounds);
					}
				}
				
				Area area = new Area(miniShape);
				if (bodyBounds != null)
					area.add(new Area(new ZRectangle(bodyBounds).getShape()));
				
				return area;
			}
			else
			{
				return new ZRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()).getShape();
			}
		}
		
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classifiernode.ClassifierNodeFacet#isDisplayIconOnly()
		 */
		public boolean isDisplayOnlyIcon()
		{
			return displayOnlyIcon;
		}
		
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classifiernode.ClassifierNodeFacet#getMiddlePointForPreview(UBounds)
		 */
		public UPoint getMiddlePointForPreview(UBounds bounds)
		{
			RequirementsFeatureSizeInfo info = makeCurrentInfo(bounds.getTopLeftPoint(), bounds.getDimension(), autoSized);
			RequirementsFeatureSizes sizes = info.makeActualSizes();
			
			if (miniAppearanceFacet != null && displayOnlyIcon)
			{
				return sizes.getIcon().getMiddlePoint();
			}
			else
			{
				return sizes.getOuter().getMiddlePoint();
			}
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.classifiernode.ClassifierNodeFacet#getContainmentBounds(UBounds)
		 */
		public UBounds getContainmentBounds(UBounds newBounds)
		{
			if (!displayOnlyIcon)
      {
				return newBounds;
      }
			
			RequirementsFeatureSizeInfo info = makeCurrentInfo(newBounds.getTopLeftPoint(), newBounds.getDimension(), false);
			return getBoundsForContainment(info.makeActualSizes());
		}
	}
	
	private class AutoSizedFacetImpl implements AutoSizedFacet
	{
		public void autoSize(boolean newAutoSized)
		{
			// make the change
			autoSized = newAutoSized;
			
			// we are about to autosize, so need to make a resizings
			ResizingFiguresFacet resizings = new ResizingFiguresGem(null, figureFacet.getDiagram()).getResizingFiguresFacet();
			resizings.markForResizing(figureFacet);
			
			UBounds autoBounds = getAutoSizedBounds(newAutoSized);
			resizings.setFocusBounds(autoBounds);		
			resizings.end();
		}
	}
	
	public JMenuItem getAutoSizedMenuItem(final ToolCoordinatorFacet coordinator)
	{
		// for autosizing
		JCheckBoxMenuItem toggleAutoSizeItem = new JCheckBoxMenuItem("Autosized");
		toggleAutoSizeItem.setState(autoSized);
		toggleAutoSizeItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// toggle the autosized flag
				coordinator.startTransaction(
				  (autoSized ? "unautosized " : "autosized ") + " feature",
				  (!autoSized ? "unautosized " : "autosized ") + " feature");
				autosizedFacet.autoSize(!autoSized);
				coordinator.commitTransaction();
			}
		});
		return toggleAutoSizeItem;
	}
	
	private class ResizeVetterFacetImpl implements ResizeVetterFacet
	{
		public void startResizeVet()
		{
		}
		
		public UDimension vetResizedExtent(UBounds bounds)
		{
			if (displayOnlyIcon)
			{
				UDimension extent = bounds.getDimension();
				
				// don't allow the extent to go less than the height or width
				UDimension minExtent = miniAppearanceFacet.getMinimumDisplayOnlyAsIconExtent();
				if (autoSized)
					return minExtent;
				
				double vettedWidth = Math.max(extent.getWidth(), minExtent.getWidth());
				return new UDimension(vettedWidth, minExtent.getHeight() / minExtent.getWidth() * vettedWidth);
				// preserve ratio
			}
			
			UDimension extent = bounds.getDimension();
			// don't allow the extent to go less than the height or width
			RequirementsFeatureSizeInfo info = makeCurrentInfo();
			info.setAutoSized(true);
			RequirementsFeatureSizes sizes = info.makeActualSizes();
			
			return sizes.getOuter().getDimension().maxOfEach(extent);
		}
		
		public UBounds vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
		{
			if (autoSized)
				return figureFacet.getFullBounds();
			
			return bounds;
		}
	}
	
  
	private class TextableFacetImpl implements TextableFacet
	{
		/*
		 * text resize vetting
		 */
		
		public UBounds getTextBounds(String name)
		{
			UBounds textBounds = new UBounds(vetTextChange(name).getName());
			return textBounds.addToPoint(new UDimension(0, -1));
		}
		
		public UBounds vetTextResizedExtent(String name)
		{
			return vetTextChange(name).getOuter();
		}
		
		private RequirementsFeatureSizes vetTextChange(String name)
		{
			// get the old sizes, the new sizes and then get the preferred topleft.  Then, get the sizes with this as the topleft point!!
			RequirementsFeatureSizeInfo info = makeCurrentInfo();
			UBounds contentBoundsToPreserve = info.makeActualSizes().getContents();
			info.setName(name);
			
			RequirementsFeatureSizes ret = formCentredBounds(info, contentBoundsToPreserve);
			return ret;
		}
		
    public void setText(String text, Object listSelection, boolean unsuppress)
    {
    	subject = (RequirementsFeature) miniAppearanceFacet.setText(null, text, listSelection, unsuppress);
    	figureFacet.updateViewAfterSubjectChanged(ViewUpdatePassEnum.LAST);      
    }
		
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}

    public JList formSelectionList(String textSoFar)
    {
      return miniAppearanceFacet.formSelectionList(textSoFar);
    }
	}
	
	private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
	{
    public String getFigureName()
		{
			return "feature";
		}
		
		public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
		{
			BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
			RequirementsFeaturePreviewGem previewGem = new RequirementsFeaturePreviewGem(figureFacet.getFullBounds());
      basicGem.setCentreToEdge(true);
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			basicGem.connectBasicNodePreviewAppearanceFacet(previewGem.getBasicNodePreviewAppearanceFacet());
			previewGem.connectClassifierNodeFacet(classifierFacet);
			
			return basicGem.getPreviewFacet();
		}
		
		public Manipulators getSelectionManipulators(final ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
		{
			ManipulatorFacet keyFocus = null;
			if (favoured)
			{
				TextManipulatorGem textGem =
					new TextManipulatorGem(
							coordinator,
							"changed feature name",
							"reverted feature name",
							subject.getName(),
							font,
							Color.black,
							fillColor,
							TextManipulatorGem.TEXT_AREA_ONE_LINE_TYPE);
				textGem.connectTextableFacet(textableFacet);
				keyFocus = textGem.getManipulatorFacet();
			}
			return
				new Manipulators(
				    keyFocus,
				    new ResizingManipulatorGem(
				    		coordinator,
				        figureFacet,
				        diagramView,
								figureFacet.getFullBounds(),
								resizeVetterFacet,
								firstSelected).getManipulatorFacet());
		}
		
		public ZNode formView()
		{
			RequirementsFeatureSizeInfo info = makeCurrentInfo();
			RequirementsFeatureSizes sizes = info.makeActualSizes();
			
			ZText zName = sizes.getZtext();
			if (!displayOnlyIcon)
				zName.setBackgroundColor(null);
			zName.setPenColor(lineColor);
			
			ZText zOwner = sizes.getZOwningText();
			if (zOwner != null)
			{
				if (!displayOnlyIcon)
					zOwner.setBackgroundColor(null);
				zOwner.setPenColor(lineColor);
			}
			
			// a possible miniappearance
			ZNode miniAppearance = null;      
			if (sizes.getIcon() != null)
				miniAppearance = miniAppearanceFacet.formView(displayOnlyIcon, sizes.getIcon());
			
			// draw the rectangle
			UBounds entireBounds = sizes.getOuter();
      ZShape rect = new ZRoundedRectangle(
      		entireBounds.getX(), entireBounds.getY(),
      		entireBounds.getWidth(), entireBounds.getHeight(),
      		10, 10);
      rect.setFillPaint(fillColor);
      rect.setPenPaint(lineColor);
			
			
			// group them
			ZGroup group = new ZGroup();

      if (!displayOnlyIcon)
      {
        ZShape shadow = new ZRoundedRectangle(
        		entireBounds.getX() + 3, entireBounds.getY() + 3,
        		entireBounds.getWidth(), entireBounds.getHeight(),
        		10, 10);
        shadow.setFillPaint(Color.LIGHT_GRAY);
        shadow.setPenPaint(null);
        group.addChild(new ZVisualLeaf(shadow));
      }
      			
			if (!displayOnlyIcon)
			  group.addChild(new ZVisualLeaf(rect));
			
			if (name.length() > 0)
				group.addChild(new ZVisualLeaf(zName));
			if (zOwner != null)
				group.addChild(new ZVisualLeaf(zOwner));
			if (miniAppearance != null)
				group.addChild(miniAppearance);
			
      // possibly add in a ... for missing display attributes
      if (bodyEllipsis)
      {
        UPoint pt = sizes.getFull().getBottomRightPoint();
        UPoint start = pt.subtract(new UDimension(14, 6));
        for (int lp = 0; lp < 3; lp++)
        {
          ZRectangle dot = new ZRectangle(new UBounds(start, new UDimension(1, 1)));
          group.addChild(new ZVisualLeaf(dot));
          start = start.add(new UDimension(4, 0));
        }
      }
      
      // is this a retirement?
      if (isElementRetired())
    		addCross(sizes, group, 12, Color.RED);

			// possibly draw the arcs
			if (arc2 != null)
			{
				ZArc arc = new ZArc(new Arc2D.Double(arc2, arc2Start, arc2End - arc2Start, Arc2D.PIE));
				arc.setFillPaint(Color.BLACK);
				group.addChild(new ZVisualLeaf(arc));
			}
			if (arc3 != null)
			{
				ZArc arc = new ZArc(new Arc2D.Double(arc3, arc3Start, arc3End - arc3Start, Arc2D.OPEN));
				arc.setFillPaint(null);
				group.addChild(new ZVisualLeaf(arc));
			}
			
      // add the interpretable properties
			group.setChildrenPickable(false);
			group.setChildrenFindable(false);
			group.putClientProperty("figure", figureFacet);
			
			return group;
		}

		private void addCross(RequirementsFeatureSizes sizes, ZGroup group, int s, Color color)
		{
			UPoint top = sizes.getName().getTopLeftPoint().subtract(new UDimension(16, 0));
			UDimension size = new UDimension(s, s);
			UDimension size2 = new UDimension(s, -s);
			ZLine line1 = new ZLine(top.subtract(size), top.add(size));
			line1.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			line1.setPenPaint(color);
			ZLine line2 = new ZLine(top.subtract(size2), top.add(size2));
			line2.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			line2.setPenPaint(color);
			group.addChild(new ZVisualLeaf(line1));
			group.addChild(new ZVisualLeaf(line2));
		}
		
    /**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			return new UDimension(0, 0);
		}
		
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getActualFigureForSelection()
		 */
		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
		}
		
		/**
		 * @see com.giroway.jumble.foundation.interfaces.SelectableFigure#getContextMenu()
		 */
		public JPopupMenu makeContextMenu(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
			JPopupMenu popup = new JPopupMenu();
			
			boolean diagramReadOnly = diagramView.getDiagram().isReadOnly();
			
			if (!diagramReadOnly)
			{
  			popup.add(getAutoSizedMenuItem(coordinator));
        Utilities.addSeparator(popup);        
			}
			
      if (miniAppearanceFacet != null)
        miniAppearanceFacet.addToContextMenu(popup, diagramView, coordinator);
			
			if (!diagramReadOnly)
			{
        Utilities.addSeparator(popup);
        
				// add expansions
				JMenu expand = new JMenu("Expand");
				expand.setIcon(Expander.EXPAND_ICON);
				JMenuItem deps = new JMenuItem("dependencies");
				expand.add(deps);
				deps.addActionListener(new DependencyExpander(figureFacet, coordinator, new DependencyCreatorGem().getArcCreateFacet(), subject.undeleted_getOwnedAnonymousDependencies()));
				
				JMenuItem subs = new JMenuItem("subfeatures");
				expand.add(subs);

				subs.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{						
						UBounds bounds = figureFacet.getFullBounds();
						final UPoint loc = new UPoint(bounds.getPoint().getX(), bounds.getBottomRightPoint().getY());
						ITargetResolver resolver = new ITargetResolver()
						{
							public List<Element> resolveTargets(Element relationship)
							{
								List<Element> targets = new ArrayList<Element>();
								targets.add(((RequirementsFeatureLink) relationship).undeleted_getType());
								return targets;
							}
							
							public UPoint determineTargetLocation(Element target, int index)
							{
								return loc.add(new UDimension(-50 + 40 * index, 100 + index * 40));
							}
							
							public NodeCreateFacet getNodeCreator(Element target)
							{
								if (target instanceof RequirementsFeature)
									return new RequirementsFeatureCreatorGem().getNodeCreateFacet();
								return null;
							}
						};
						
						// work out the links that should be present
						List<Element> links = new ArrayList<Element>();
						IDeltaEngine engine = GlobalDeltaEngine.engine;
						DERequirementsFeature dereq = engine.locateObject(subject).asRequirementsFeature();
				    Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainerFacet());
				    for (DeltaPair pair : dereq.getDeltas(ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK).getConstituents(engine.locateObject(visualHome).asStratum()))
				    	links.add((Element) pair.getConstituent().getRepositoryObject());
				    
				    // expand now
						new Expander(
								coordinator,
								figureFacet,
								links,
								resolver,
								new RequirementsFeatureLinkCreatorGem(RequirementsLinkKind.MANDATORY_LITERAL).getArcCreateFacet()).expand();
					}
				});
				popup.add(expand);
			}
			
      Utilities.addSeparator(popup);
      popup.add(SubstitutionsMenuMaker.getSubstitutionsMenuItem(figureFacet, coordinator));
      popup.add(ResemblancePerspectiveTree.makeMenuItem(diagramView.getDiagram(), figureFacet, coordinator));

      final Package pkg = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainerFacet()); 
      final Type feature = (Type) figureFacet.getSubject();
      final DERequirementsFeature me = GlobalDeltaEngine.engine.locateObject(feature).asRequirementsFeature();
      
      JMenuItem comp = new JMenuItem("Show compositional hierarchy");
      comp.setIcon(COMPOSITION_ICON);
      popup.add(comp);
      if (me != null)
      {
      	comp.addActionListener(new ActionListener()
      	{
					public void actionPerformed(ActionEvent e)
					{
						JPanel panel = new JPanel(new BorderLayout());
		      	RequirementsFeatureHierarchyViewer viewer = new RequirementsFeatureHierarchyViewer(pkg, feature, panel);

		        int x = coordinator.getFrameXPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_X_POS);
		        int y = coordinator.getFrameYPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_Y_POS);
		        int width = coordinator.getIntegerPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_WIDTH);
		        int height = coordinator.getIntegerPreference(RegisteredGraphicalThemes.INITIAL_EDITOR_HEIGHT);

		        viewer.constructComponent();
		        coordinator.getDock().createExternalPaletteDockable(
		            "Compositional hierarchy",
		            COMPOSITION_ICON,
		            new Point(x, y), new Dimension(width, height),
		            true,
		            true,
		            new JScrollPane(panel));
					}
      	});	      	
      }
      
      if (!diagramReadOnly)
      {
        Utilities.addSeparator(popup);
				popup.add(getDisplayAsIconItem(diagramView, coordinator));
        popup.add(BasicNamespaceNodeGem.getChangeColorItem(diagramView, coordinator, figureFacet, fillColor,
        		new SetFillCallback()
						{
							public void setFill(Color fill)
							{
								fillColor = fill;
							}
						}));
      }
      
			return popup;
		}
		
    private JMenuItem getShowStereotypeItem(DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      JMenuItem item = new JCheckBoxMenuItem("Stereotype");
      item.setSelected(showStereotype);
      item.addItemListener(new ItemListener()
          {
            public void itemStateChanged(ItemEvent e)
            {
              showStereotype = !showStereotype;
              RequirementsFeatureSizeInfo info = makeCurrentInfo();
              figureFacet.performResizingTransaction(
                  formCentredBounds(info, null).getOuter());                  
            }
          });
      return item;
    }

		private JMenuItem getSuppressOwnerItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
			// for adding operations
			JCheckBoxMenuItem showVisibilityItem = new JCheckBoxMenuItem("Suppress owner");
			showVisibilityItem.setState(forceSuppressOwningPackage);
			
			showVisibilityItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// toggle the suppress operations flag
					coordinator.startTransaction(
							forceSuppressOwningPackage ? "unsuppressed owner package" : "suppressed owner package",
							forceSuppressOwningPackage ? "suppressed owner package" : "unsuppressed owner package");
					forceSuppressOwningPackage = !forceSuppressOwningPackage;
					coordinator.commitTransaction();
				}
			});
			return showVisibilityItem;
		}
		
		private JMenuItem getDisplayAsIconItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
			// for adding operations
			JCheckBoxMenuItem displayAsIconItem = new JCheckBoxMenuItem("Display as icon");
			displayAsIconItem.setState(displayOnlyIcon);
			displayAsIconItem.setEnabled(!hasSubstitutions());
			
			displayAsIconItem.addActionListener(new ActionListener()
																					{
						public void actionPerformed(ActionEvent e)
						{
							// toggle the suppress operations flag
							coordinator.startTransaction(
									"displayed " + getFigureName() + (displayOnlyIcon ? " as box" : " as icon"),
									"displayed " + getFigureName() + (!displayOnlyIcon ? " as box" : " as icon"));
							DisplayAsIconTransaction.display(figureFacet, !displayOnlyIcon);
							coordinator.commitTransaction();
						}
					});
			return displayAsIconItem;
		}				
    
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			return null; // only used for figures with default auto-sizing
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
			if (!displayOnlyIcon)
        return figureFacet.getFullBounds();
			
			// must do this, because the size info structure doesn't know if the attributes or operations is empty
			RequirementsFeatureSizeInfo info = makeCurrentInfo();
			return getBoundsForContainment(info.makeActualSizes());
		}
		
		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			RequirementsFeatureSizeInfo info = makeCurrentInfo();
      if (!diagramResize)
        return info.makeActualSizes().getOuter();
			return formCentredBounds(info, info.makeActualSizes().getContents()).getOuter();
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("name", name));
			properties.add(new PersistentProperty("owner", owner));
			properties.add(new PersistentProperty("auto", autoSized, true));
			properties.add(new PersistentProperty("icon", displayOnlyIcon, false));
			properties.add(new PersistentProperty("tlOff", rememberedTLOffset, new UDimension(0, 0)));
			properties.add(new PersistentProperty("brOff", rememberedBROffset, new UDimension(0, 0)));
			properties.add(new PersistentProperty("showVis", showOwningPackage, false));
      properties.add(new PersistentProperty("showStereo", showStereotype, true));
			properties.add(new PersistentProperty("suppVis", forceSuppressOwningPackage, false));
      properties.add(new PersistentProperty("fill", fillColor, initialFillColor));
      properties.add(new PersistentProperty("addedUuids", deletedUuids));
      properties.add(new PersistentProperty("deletedUuids", deletedUuids));
      properties.add(new PersistentProperty("stereoHash", stereotypeHashcode, 0));
      properties.add(new PersistentProperty("ellipsis", bodyEllipsis, false));
      if (arc2 != null)
      {
      	properties.add(new PersistentProperty("arc2P", arc2.getPoint()));
      	properties.add(new PersistentProperty("arc2D", arc2.getDimension(), new UDimension(0, 0)));
      	properties.add(new PersistentProperty("arc2Start", arc2Start));
      	properties.add(new PersistentProperty("arc2End", arc2End));
      }
      if (arc3 != null)
      {
      	properties.add(new PersistentProperty("arc3P", arc3.getPoint()));
      	properties.add(new PersistentProperty("arc3D", arc3.getDimension(), new UDimension(0, 0)));
      	properties.add(new PersistentProperty("arc3Start", arc3Start));
      	properties.add(new PersistentProperty("arc3End", arc3End));
      }
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#formViewUpdateCommandAfterSubjectChanged(boolean)
		 */
		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
    	formViewUpdateCommandAfterFeatureChanged(pass);
		}
		
		/**
		 * form a view update command for a classifier
		 * @param isTop
		 * @param pass = ViewUpdatePassEnum.FIRST 
		 * @return
		 */
		private void formViewUpdateCommandAfterFeatureChanged(ViewUpdatePassEnum pass)
		{
		  if (pass != ViewUpdatePassEnum.LAST)
		    return;
		  
      int actualStereotypeHashcode = calculateStereotypeHashcode();

			// should we be displaying the owner?
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      Package visualStratum = repository.findVisuallyOwningStratum(
          figureFacet.getDiagram(),
          figureFacet.getContainedFacet().getContainer());
      Package owningStratum = repository.findOwningStratum(subject);
      boolean locatedInCorrectView = visualStratum == owningStratum;

			RequirementsFeatureProperties props = new RequirementsFeatureProperties(figureFacet, subject); 
			final boolean shouldBeDisplayingOwningPackage =
				!locatedInCorrectView && !forceSuppressOwningPackage || props.getElement().isSubstitution(); 

			String newName = props.getPerspectiveName();
			
			// get the new stratum owner
			String newOwner = props.getElement().isSubstitution() ?
					"(replaces " + props.getSubstitutesForName() + ")" :
					"(from " + repository.getFullStratumNames((Element) props.getHomePackage().getRepositoryObject()) + ")";
			
			// possibly remake the arc for 1..* (type 2)
			UPoint middle = Grid.roundToGrid(new UPoint(figureFacet.getFullBounds().getCenterX(), 0));
			middle = new UPoint(middle.getX(), figureFacet.getFullBounds().getBottomLeftPoint().getY());
			UDimension dim = new UDimension(16, 16);
			UBounds arc = new UBounds(middle, new UDimension(0, 0)).addToPoint(dim.negative()).addToExtent(dim.multiply(2));
			Double startAngle = getAngle(RequirementsLinkKind.ONE_OR_MORE_LITERAL, true);
			if (startAngle != null)
			{
				arc2 = arc;
				arc2Start = startAngle;
				arc2End = getAngle(RequirementsLinkKind.ONE_OR_MORE_LITERAL, false);
				if (arc2Start == arc2End)
					arc2End += 15;  // degrees
			}

			UDimension dimN = new UDimension(19, 19);
			UBounds arcN = new UBounds(middle, new UDimension(0, 0)).addToPoint(dimN.negative()).addToExtent(dimN.multiply(2));
			Double startAngleN = getAngle(RequirementsLinkKind.ONE_OF_LITERAL, true);
			if (startAngleN != null)
			{
				arc3 = arcN;
				arc3Start = startAngleN;
				arc3End = getAngle(RequirementsLinkKind.ONE_OF_LITERAL, false);
				if (arc3Start == arc3End)
					arc3End += 15;  // degrees
			}

			// if neither the name or the namespace has changed, or the in-placeness, suppress any command
			boolean ellipsis = isEllipsisForBody();
			if (shouldBeDisplayingOwningPackage == showOwningPackage
			      && newName.equals(name) && owner.equals(newOwner)
            && stereotypeHashcode == actualStereotypeHashcode
            && bodyEllipsis == ellipsis 
            && displayOnlyIcon == shouldDisplayOnlyIcon()
            && isElementRetired() == retired)
      {
				return;
      }

      updateFeatureViewAfterSubjectChanged(actualStereotypeHashcode, ellipsis);
		}
		
		private Double getAngle(RequirementsLinkKind kind, boolean start)
		{
			Double ret = null;
			
			AnchorFacet anchor = figureFacet.getAnchorFacet();
			for (Iterator<LinkingFacet> iter = figureFacet.getAnchorFacet().getLinks(); iter.hasNext();)
			{
				// is this one to use?
				LinkingFacet link = iter.next();
				Object subject = link.getFigureFacet().getSubject(); 
				if (subject instanceof RequirementsFeatureLink)
				{
					RequirementsLinkKind def = ((RequirementsFeatureLink) subject).getKind();
					if (kind.equals(def) && link.getAnchor1() == anchor)
					{
						double angle = computeAngle(link);
						if (ret == null)
							ret = angle;
						else
						if (start)
						{
							if (angle < ret)
								ret = angle;
						}
						else
						{
							if (angle > ret)
								ret = angle;							
						}
					}
				}
			}
			return ret;
		}

		private double computeAngle(LinkingFacet link)
		{
			List<UPoint> pts = link.getCalculated().getAllPoints();
			UDimension dim = pts.get(0).subtract(pts.get(1));
			return (dim.getRadians() + Math.PI) / Math.PI * -180;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#middleButtonPressed(ToolCoordinatorFacet)
		 */
		public void middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
		  // look up through the owning namespaces, until we find a possible package
      if (subject != null)
      {
      	Element possiblePackage = subject;
      	
  		  while (possiblePackage != null && !(possiblePackage instanceof Package))
  		    possiblePackage = possiblePackage.getOwner();
  		  
  		  if (possiblePackage != null)
  		  {
  				// want to open the diagram for this package
  				GlobalPackageViewRegistry.activeRegistry.open(
  				    (Package) possiblePackage,
  				    true,
  				    false,
  				    figureFacet.getFullBounds(),
  				    GlobalPackageViewRegistry.activeRegistry.getFocussedView().getFixedPerspective(),
  				    true);
  		  }
      }
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getSubject()
		 */
		public NamedElement getSubject()
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
			return true;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      Package visualStratum = repository.findVisuallyOwningStratum(
          figureFacet.getDiagram(),
          figureFacet.getContainedFacet().getContainer());
      Package owningStratum = repository.findOwningStratum(subject);
      boolean locatedInCorrectView = visualStratum == owningStratum;

      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet) || !locatedInCorrectView;
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      if (miniAppearanceFacet != null)
        return miniAppearanceFacet.getDisplayStyles(displayOnlyIcon, anchorIsTarget);
      return null;
    }

		public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			ToolFigureClassification tool =
				miniAppearanceFacet.getToolClassification(
					makeCurrentInfo().makeActualSizes(),
					displayOnlyIcon,
					point);
			if (tool == null)
				tool = new ToolFigureClassification("?", "?");			
			return tool;
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}
	}
	
	public RequirementsFeatureNodeGem(
		DiagramFacet diagram,
		Color initialFillColor,
		PersistentFigure pfig)
	{
    this.initialFillColor = initialFillColor;
    interpretPersistentFigure(pfig);    
	}
	
	private void interpretPersistentFigure(PersistentFigure pfig)
	{
		subject = (RequirementsFeature) pfig.getSubject();
		PersistentProperties properties = pfig.getProperties();
  	name = properties.retrieve("name", "").asString();
  	owner = properties.retrieve("owner", "").asString();
    autoSized = properties.retrieve("auto", true).asBoolean();
    displayOnlyIcon = properties.retrieve("icon", false).asBoolean();
    
    rememberedTLOffset = properties.retrieve("tlOff", new UDimension(0, 0)).asUDimension();
    rememberedBROffset = properties.retrieve("brOff", new UDimension(0, 0)).asUDimension();
    
    showOwningPackage = properties.retrieve("showVis", false).asBoolean();
    showStereotype = properties.retrieve("showStereo", true).asBoolean();
    forceSuppressOwningPackage = properties.retrieve("suppVis", false).asBoolean();

    fillColor = properties.retrieve("fill", initialFillColor).asColor();
    addedUuids = new HashSet<String>(properties.retrieve("addedUuids", "").asStringCollection());
    deletedUuids = new HashSet<String>(properties.retrieve("deletedUuids", "").asStringCollection());
    stereotypeHashcode = properties.retrieve("stereoHash", 0).asInteger();
    bodyEllipsis = properties.retrieve("ellipsis", false).asBoolean();

    if (properties.contains("arc2P"))
    {
    	UPoint pt = properties.retrieve("arc2P").asUPoint();
    	UDimension dim = properties.retrieve("arc2D").asUDimension();
    	arc2 = new UBounds(pt, dim);
    	arc2Start = properties.retrieve("arc2Start").asDouble();
    	arc2End = properties.retrieve("arc2End").asDouble();
    }
    else
    {
    	arc2 = null;
    }	
    if (properties.contains("arc3P"))
    {
    	UPoint pt = properties.retrieve("arc3P").asUPoint();
    	UDimension dim = properties.retrieve("arc3D").asUDimension();
    	arc3 = new UBounds(pt, dim);
    	arc3Start = properties.retrieve("arc3Start").asDouble();
    	arc3End = properties.retrieve("arc3End").asDouble();
    }
    else
    {
    	arc3 = null;
    }	
	}
  
  public RequirementsFeatureNodeGem(Color initialFillColor, PersistentFigure pfig)
	{
    this.initialFillColor = initialFillColor;    
    interpretPersistentFigure(pfig);
  }
  
  private int calculateStereotypeHashcode()
  {
    return StereotypeUtilities.calculateStereotypeHashAndComponentHash(figureFacet, subject);
  }
	
	public BasicNodeAppearanceFacet getBasicNodeAppearanceFacet()
	{
		return appearanceFacet;
	}
	
	public void connectBasicNodeFigureFacet(BasicNodeFigureFacet figureFacet)
	{
		this.figureFacet = figureFacet;
		figureFacet.registerDynamicFacet(textableFacet, TextableFacet.class);
		figureFacet.registerDynamicFacet(displayAsIconFacet, DisplayAsIconFacet.class);
		figureFacet.registerDynamicFacet(locationFacet, LocationFacet.class);
		figureFacet.registerDynamicFacet(switchableFacet, SwitchSubjectFacet.class);
		// override the default autosizing mechanism, which doesn't work for this
    figureFacet.registerDynamicFacet(deletedConnectorUuidsFacet, SimpleDeletedUuidsFacet.class);
    figureFacet.registerDynamicFacet(autosizedFacet, AutoSizedFacet.class);
    registerAdorner();
	}
	
	private void registerAdorner()
	{
    RequirementsFeatureDelegatedAdornerGem adorner =
    	new RequirementsFeatureDelegatedAdornerGem(figureFacet);
    figureFacet.registerDynamicFacet(adorner.getDelegatedDeltaAdornerFacet(), DelegatedDeltaAdornerFacet.class);
	}
	
	public void connectClassifierMiniAppearanceFacet(RequirementsFeatureMiniAppearanceFacet miniAppearanceFacet)
	{
		this.miniAppearanceFacet = miniAppearanceFacet;
	}
	
	public ResizeVetterFacet getResizeVetterFacet()
	{
		return resizeVetterFacet;
	}
	
	private RequirementsFeatureSizeInfo makeCurrentInfo()
	{
		UBounds bounds = figureFacet.getFullBounds();
		return makeCurrentInfo(bounds.getTopLeftPoint(), bounds.getDimension(), autoSized);
	}
	
	private RequirementsFeatureSizeInfo makeCurrentInfo(UPoint topLeft, UDimension extent, boolean autoSized)
	{
		RequirementsFeatureSizeInfo info;
		boolean haveIcon = (showStereotype || displayOnlyIcon) && miniAppearanceFacet != null && miniAppearanceFacet.haveMiniAppearance(displayOnlyIcon);
		UDimension minimumIconExtent = (miniAppearanceFacet == null ? null : miniAppearanceFacet.getMinimumDisplayOnlyAsIconExtent());
		
		// work out the package name for visibility calcs
		String owningPackageString = null;
		if (showOwningPackage && owner != null)
			owningPackageString = owner;
		
		// see if we need to turn the compartment ellipsis on
/*    boolean partsEllipsis = false;
    if (subject != null)
    {
      if (!displayOnlyIcon && !autoSized)
      {
        partsEllipsis =
          !new ClassPartHelper(
          		null,
              figureFacet,
              primitiveContents,
              contents).isShowingAllConstituents();

      }
    }
*/    
		info =
			new RequirementsFeatureSizeInfo(
			topLeft,
			extent,
			autoSized,
			name,
			font,
			packageFont,
			haveIcon,
			displayOnlyIcon,
			minimumIconExtent,
			owningPackageString);
		return info;
	}
	
	private RequirementsFeatureSizes formCentredBounds(RequirementsFeatureSizeInfo info, UBounds contentBoundsToPreserve)
	{
		RequirementsFeatureSizes sizes = info.makeActualSizes();
		
		UBounds centred = ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), sizes.getOuter().getDimension());
		info.setTopLeft(centred.getTopLeftPoint());
		return info.makeActualSizes();
	}
	
	private UBounds getAutoSizedBounds(boolean autoSized)
	{
		RequirementsFeatureSizeInfo info = makeCurrentInfo();
		
		info.setAutoSized(autoSized);
		UBounds bounds = formCentredBounds(info, info.makeActualSizes().getContents()).getOuter();
		
		// if this is to be autosized, remember the content bounds and the top left bounds
		if (autoSized)
		{
			UBounds fullBounds = figureFacet.getFullBounds();
			info = makeCurrentInfo(fullBounds.getTopLeftPoint(), fullBounds.getDimension(), false);
			
			rememberedTLOffset = new UDimension(0, 0);
			rememberedBROffset = new UDimension(0, 0);
		}
		
		return bounds;
	}
	
	private UBounds getBoundsForContainment(RequirementsFeatureSizes sizes)
	{
		if (!displayOnlyIcon)
			return sizes.getOuter();
		
		UBounds bounds = sizes.getOuter();
		if (name.length() != 0)
			bounds = bounds.union(sizes.getName());
		if (sizes.getZOwningText() != null)
			bounds = bounds.union(sizes.getOwner());
		return bounds;
	}	
  
	private boolean shouldDisplayOnlyIcon()
	{
		return hasSubstitutions() ? true : displayOnlyIcon;
	}
	
	public boolean hasSubstitutions()
	{
		// do we have any redefinitions pointing at us in this diagram?
		for (Iterator<LinkingFacet> iter = figureFacet.getAnchorFacet().getLinks(); iter.hasNext();)
		{
			FigureFacet link = iter.next().getFigureFacet(); 
			Object subj = link.getSubject();
			if (subj instanceof Dependency)
			{
				Dependency dep = (Dependency) subj;
				if (dep.isReplacement() && dep.undeleted_getDependencyTarget() == subject)
					return true;
			}
		}
		return false;
	}

  private boolean isElementRetired()
	{
		return subject.isRetired();
	}

	/**
	 * @see com.hopstepjump.jumble.foundation.IFigure#getContainerablePort()
	 */
	public BasicNodeContainerFacet getBasicNodeContainerFacet()
	{
		return null;
	}
}



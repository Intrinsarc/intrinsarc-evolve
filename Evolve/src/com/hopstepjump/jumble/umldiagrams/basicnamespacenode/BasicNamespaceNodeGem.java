package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.xoetrope.editor.color.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.figurefacilities.selectionbase.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulation.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.idraw.nodefacilities.style.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.expander.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.uml2deltaengine.*;

import edu.umd.cs.jazz.*;


/**
 * BasicNamespaceNodeGem represents a namespace element and the container it holds...
 *
 */

public final class BasicNamespaceNodeGem implements Gem
{
  public static final ImageIcon ERROR_ICON = IconLoader.loadIcon("warning.png");
  public static final ImageIcon COLOR_WHEEL_ICON = IconLoader.loadIcon("color_wheel.png");
  
	private Font font = ScreenProperties.getPrimaryFont();
	private Font packageFont = ScreenProperties.getSecondaryFont();

	private Namespace subject;
  private Color lineColor = Color.black;
  private Color fillColor;
  private static final Color INITIAL_FILL_COLOR = new Color(240, 250, 255);

  private String name = "";
  private UBounds minVetBounds;
  private boolean suppressContents = false;
  private UDimension rememberedTLOffset = new UDimension(0,0);
  private UDimension rememberedBROffset = new UDimension(0,0);
  private boolean displayOnlyIcon;

  private SimpleContainerFacet contents;
  private BasicNodeContainerFacet containerFacet = new ContainerFacetImpl();
  private BasicNodeAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private BasicNodeFigureFacet figureFacet;
  private TextableFacetImpl textableFacet = new TextableFacetImpl();
  private ResizeVetterFacetImpl resizeVetterFacet = new ResizeVetterFacetImpl();
  private BasicNamespaceNodeFacet namespaceFacet = new BasicNamespaceNodeFacetImpl();
  private DisplayAsIconFacet displayAsIconFacet = new DisplayAsIconFacetImpl();
  private StylableFacet stylableFacet = new StylableFacetImpl();
  private LocationFacet locationFacet = new LocationFacetImpl();
  private BasicNamespaceMiniAppearanceFacet miniAppearanceFacet;
  private BasicNamespaceAppearanceFacet featurelessClassifierAppearanceFacet;
  private String figureName;

  private String owner = "";
  private SuppressOwningPackageFacet showOwningPackageFacet = new SuppressOwningPackageFacetImpl();
  private boolean showOwningPackage = false;
  private boolean forceSuppressOwningPackage = false;
  private int stereotypeHashcode;

  private class StylableFacetImpl implements StylableFacet
  {
    public Object setFill(Color newFill)
    {
      Color oldFill = fillColor;
      fillColor = newFill;
      return oldFill;
    }

    public void unSetFill(Object memento)
    {
      fillColor = (Color) memento;
    }
  }
  
	private class LocationFacetImpl implements LocationFacet
	{
		/**
		 * @see com.hopstepjump.idraw.figurefacilities.selectionbase.LocationFacet#setLocation(MPackage)
		 */
		public Object setLocation()
		{
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
      
      // locate to the diagram, or a possible nesting package
      // look upwards, until we find one that has a PackageFacet registered
      Package pkg = (Package) figureFacet.getDiagram().getLinkedObject();
      Package currentPkg = (Package) subject.getOwner();
      Package containerPkg = repository.findVisuallyOwningPackage(
          figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
      if (containerPkg != null)
        pkg = containerPkg;
      
      // make sure that the package is not set to be owned by itself somehow
      for (Element owner = pkg; owner != null; owner = owner.getOwner())
        if (owner == subject)
          return null;
      
      if (subject instanceof Package)
      {
        currentPkg.getChildPackages().remove(subject);
        pkg.getChildPackages().add(subject);
      }
      else
      {
        currentPkg.getChildPackages().remove(subject);
        pkg.getChildPackages().add(subject);
      }

      return new Package[]{currentPkg, pkg};
		}

		/**
		 * @see com.hopstepjump.idraw.figurefacilities.selectionbase.LocationFacet#unSetLocation(Object)
		 */
		public void unSetLocation(Object memento)
		{
      // don't bother if the memento isn't set
      if (memento == null)
        return;
      
      Package[] pkgs = (Package[]) memento;
      Package oldPkg = pkgs[0];
      Package newPkg = pkgs[1];
      newPkg.getChildPackages().remove(subject);
      oldPkg.getChildPackages().add(subject);      
		}

	}

	private class SuppressOwningPackageFacetImpl implements SuppressOwningPackageFacet
	{
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.basicnamespacenode.ShowOwningPackageFacet#setShowOwningPackage(boolean)
		 */
		public Object setSuppressOwningPackage(boolean forceSuppressPackage)
		{
			boolean oldForceSuppressOwningPackage = forceSuppressOwningPackage;
	
			// make the change
			forceSuppressOwningPackage = forceSuppressPackage;
	
			return new Boolean(oldForceSuppressOwningPackage);
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.basicnamespacenode.ShowOwningPackageFacet#unSetShowOwningPackage(Object)
		 */
		public void unSetSupressOwningPackage(Object memento)
		{
			forceSuppressOwningPackage = ((Boolean)memento).booleanValue();
		}

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
	
			Command resizeCommand = null;
			contents.getFigureFacet().setShowing(!suppressContents && !displayOnlyIcon);

			// we are about to autosize, so need to make a resizings command
			ResizingFiguresFacet resizings = new ResizingFiguresGem(null, figureFacet.getDiagram()).getResizingFiguresFacet();
			resizings.markForResizing(figureFacet);
			
			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
			UBounds newBounds = info.makeActualSizes().getOuter();
			UBounds centredBounds = ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), newBounds.getDimension());
			resizings.setFocusBounds(centredBounds);			
			resizings.end();
		}
	}
  
  private class BasicNamespaceNodeFacetImpl implements BasicNamespaceNodeFacet
  {
    public Namespace getSubject()
    {
      return subject;
    }
    
	  public UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews)
	  {
	  	BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
	  	
			SimpleContainerPreviewFacet contentsPreviewFacet = (SimpleContainerPreviewFacet) previews.getCachedPreview(contents.getFigureFacet()).getDynamicFacet(SimpleContainerPreviewFacet.class);
	
			if (contentsPreviewFacet != null)  // should always be ok
			{
				UBounds minContentsBounds = contentsPreviewFacet.getMinimumBoundsFromPreviews(previews);
				info.setMinContentBounds(minContentsBounds);
			}
	
	  	BasicNamespaceSizes sizes = info.makeActualSizes();
			return sizes.getOuter();		
	  }
	  
		public void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds)
	  {
	  	// calculate the info structure, using the preview figures
			BasicNamespaceSizeInfo info = makeCurrentInfoFromPreviews(previews);
			info.setTopLeft(bounds.getTopLeftPoint());
			info.setEntire(bounds.getDimension());
	
			BasicNamespaceSizes sizes = info.makeActualSizes();
			PreviewFacet contentsPreview = previews.getCachedPreview(contents.getFigureFacet());
			contentsPreview.setFullBounds(sizes.getContents(), true);
	  }
	  
		public Shape formShapeForPreview(UBounds bounds)
		{
			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
			info.setEntire(bounds.getDimension());
			info.setTopLeft(bounds.getTopLeftPoint());
			BasicNamespaceSizes sizes = info.makeActualSizes();
			
			return featurelessClassifierAppearanceFacet.formShapeForPreview(bounds, sizes);
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
			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
			info.setEntire(figureFacet.getFullBounds().getDimension());
			BasicNamespaceSizes sizes = info.makeActualSizes();

			if (miniAppearanceFacet != null && displayOnlyIcon)
			{
				return sizes.getIcon().getMiddlePoint();
			}
			else
			{
				return sizes.getOuter().getMiddlePoint();
			}
		}

		public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, UBounds bounds, OrientedPoint oriented, boolean linkFromContained, UPoint boxPoint, UPoint insidePoint)
		{
			// if we can, use the bounds and perform the operation as quickly as possible
			boolean useBoxForOutsideBoundaryCalculation = featurelessClassifierAppearanceFacet.useBoxForOutsideBoundaryCalculation();
	    UPoint offsetPoint = oriented.getPoint();
			
			if (useBoxForOutsideBoundaryCalculation && !linkFromContained && !displayOnlyIcon)
		    return new BoundaryCalculator(bounds.addToExtent(new UDimension(1,1))).calculateBoundaryPoint(offsetPoint, boxPoint, insidePoint);
			
			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
			info.setTopLeft(bounds.getTopLeftPoint());
			info.setEntire(bounds.getDimension());
			BasicNamespaceSizes sizes = info.makeActualSizes();
	  	Shape shape = featurelessClassifierAppearanceFacet.formShapeForBoundaryCalculation(bounds, sizes);
	  	if (displayOnlyIcon)
	  		return new BoundaryCalculator(shape).calculateBoundaryPoint(oriented.getPoint(), null, insidePoint);

	    if (linkFromContained)
	    {
				info.setForceTextToTab(true);
				double insideTabHeight = info.makeActualSizes().getTab().getHeight();
	    	UBounds insideBounds = featurelessClassifierAppearanceFacet.getInsideBoxForBoundaryCalculation(bounds, insideTabHeight);
		    return new BoundaryCalculator(insideBounds.addToExtent(new UDimension(1,1))).calculateBoundaryPoint(offsetPoint, null, insidePoint);
	    }

		  return new BoundaryCalculator(shape).calculateBoundaryPoint(offsetPoint, null, insidePoint);
		}

		public UBounds getContainmentBounds(UBounds newBounds)
		{
			if (!displayOnlyIcon)
				return newBounds;

			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
			info.setTopLeft(newBounds.getTopLeftPoint());
			info.setEntire(newBounds.getDimension());
			info.setAutoSized(false);
			
			return info.makeActualSizes().getFull();
		}

    public UBounds getBoundsForDiagramZooming()
    {
			BasicNamespaceSizes sizes = makeCurrentSizeInfo().makeActualSizes();
			return sizes.getContents().inset(new UDimension(8, 8));
    }
	}
  
  public void suppressFeatures(boolean suppress)
	{
		// we will probably change size, so need to make a resizings command
		ResizingFiguresFacet resizings = new ResizingFiguresGem(null, figureFacet.getDiagram()).getResizingFiguresFacet();
		resizings.markForResizing(figureFacet);

		suppressContents = suppress;
		contents.getFigureFacet().setShowing(!suppress);
		resizings.setFocusBounds(getContentsSuppressedBounds(suppressContents));
		resizings.end();	
	}
	
  private class ContainerFacetImpl implements BasicNodeContainerFacet
  {
	  public boolean insideContainer(UPoint point)
	  {
	  	BasicNamespaceSizes sizes = makeCurrentSizeInfo().makeActualSizes();
	    return sizes.getContents().contains(point);
	  }
	
	  public Iterator<FigureFacet> getContents()
	  {
	    List<FigureFacet> list = new ArrayList<FigureFacet>();
	    list.add(contents.getFigureFacet());
	    return list.iterator();  // this is not a reference, but is a copy, so we don't need to make it unmodifiable
	  }
	  
	  public void removeContents(ContainedFacet[] containable)
	  {
	  }
	
	  public void addContents(ContainedFacet[] containable)
	  {
	  }
	
	  public boolean isWillingToActAsBackdrop()
	  {
	    return false;
	  }
	  
	  public boolean directlyAcceptsItems()
		{
			return true;
		}
		
		/**
		 * @see com.giroway.jumble.figurefacilities.containmentbase.IContainerable#getAcceptingSubcontainer(CmdContainable[])
		 */
		public ContainerFacet getAcceptingSubcontainer(ContainedFacet[] containables)
		{
			if (suppressContents || displayOnlyIcon)
				return null;
			return contents.getFigureFacet().getContainerFacet();
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
			previewCache.getCachedPreviewOrMakeOne(contents.getFigureFacet());
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeContainerFacet#setShowingForChildren(boolean)
		 */
		public void setShowingForChildren(boolean showing)
		{
			if (!showing || !suppressContents && !displayOnlyIcon)
			{
				for (Iterator iter = getContents(); iter.hasNext();)
				{
					FigureFacet figure = (FigureFacet) iter.next();
					figure.setShowing(showing);
				}
			}
		}
		
		public void persistence_addContained(FigureFacet contained)
		{
			// we should only get one of these
			contents = (SimpleContainerFacet) contained.getDynamicFacet(SimpleContainerFacet.class);
			contained.getContainedFacet().persistence_setContainer(this);
		}

		public void cleanUp()
		{
		}
  }

	private class BasicNodeAppearanceFacetImpl implements BasicNodeAppearanceFacet
	{		
		public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			return featurelessClassifierAppearanceFacet.getToolClassification(makeCurrentSizeInfo().makeActualSizes(), point);
		}

		public void acceptPersistentFigure(PersistentFigure pfig)
		{
			interpretPersistentFigure(pfig);
		}

	  public String getFigureName()
	  {
	    return figureName;
	  }
	
	  public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus)
	  {
			// get some size information first
			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
	    BasicNamespaceSizes sizes = info.makeActualSizes();
			UBounds bodyBounds = sizes.getBody();

	  	BasicNodePreviewGem basicGem = new BasicNodePreviewGem(figureFacet, start, isFocus, true);
	  	UBounds containerArea = bodyBounds;
			if (!displayOnlyIcon)
				containerArea = sizes.getContents().addToPoint(new UDimension(5, 5)).addToExtent(new UDimension(-10, -10));
			info.setForceTextToTab(true);
	  	BasicNamespacePreviewGem featurelessGem = new BasicNamespacePreviewGem(figureFacet.getFullBounds(), containerArea, info.makeActualSizes().getTab().getHeight());
			basicGem.connectPreviewCacheFacet(previews);
			basicGem.connectFigureFacet(figureFacet);
			basicGem.connectBasicNodePreviewAppearanceFacet(featurelessGem.getBasicNodePreviewAppearanceFacet());
			basicGem.connectContainerPreviewFacet(featurelessGem.getContainerPreviewFacet());
			featurelessGem.connectPreviewFacet(basicGem.getPreviewFacet());
			featurelessGem.connectFeaturelessClassifierNodeFacet(namespaceFacet);
			featurelessGem.connectPreviewCacheFacet(previews);

			return basicGem.getPreviewFacet();
	  }
	
	  public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators)
	  {
	    // make the manipulators
	    BasicNamespaceSizes sizes = makeCurrentSizeInfo().makeActualSizes();
	    ManipulatorFacet keyFocus = null;
	    if (favoured)
	    {
	      TextManipulatorGem textGem = new TextManipulatorGem(
	      		coordinator,
	          "changed " + figureFacet.getFigureName() + " name",
	          "restored " + figureFacet.getFigureName() + " name",
	          name,
	          font,
	          Color.black,
	          fillColor,
	          TextManipulatorGem.TEXT_PANE_CENTRED_TYPE);
	      textGem.connectTextableFacet(textableFacet);
	      keyFocus = textGem.getManipulatorFacet();
		  }
		  
	    return new Manipulators(
	        keyFocus,
	        new ResizingManipulatorGem(
	        		coordinator,
	            figureFacet,
	            diagramView,
	            sizes.getOuter(),
	            resizeVetterFacet,
	            firstSelected).getManipulatorFacet());
	  }
	
	  public ZNode formView()
	  {
			// calculate the sizes
			BasicNamespaceSizes sizes = makeCurrentSizeInfo().makeActualSizes();
			ZGroup group = featurelessClassifierAppearanceFacet.formView(
			    sizes, displayOnlyIcon, fillColor, lineColor, miniAppearanceFacet);
	
	    // add the interpretable properties
	    group.setChildrenPickable(false);
	    group.setChildrenFindable(false);
	    group.putClientProperty("figure", figureFacet);
	
	    return group;
	  }
	  
	  
	  /**
		 * @see com.giroway.jumble.foundation.interfaces.SelectableFigure#getContextMenu()
		 */
		public JPopupMenu makeContextMenu(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
			JPopupMenu popup = new JPopupMenu();
			boolean readOnly = diagramView.getDiagram().isReadOnly();
			if (!readOnly)
			{
  			popup.add(figureFacet.getBasicNodeAutoSizedFacet().getAutoSizedMenuItem(coordinator));
  			popup.add(getSuppressContentsItem(diagramView.getDiagram(), coordinator));
  
  			if (miniAppearanceFacet != null && miniAppearanceFacet.haveMiniAppearance())
  			{
  				popup.add(getDisplayAsIconItem(diagramView, coordinator));
  			}
  			popup.add(getSuppressOwnerItem(diagramView, coordinator));
        popup.add(getChangeColorItem(diagramView, coordinator, figureFacet, fillColor));
        
				// add expansions
				popup.addSeparator();
				JMenu expand = new JMenu("Expand");
				expand.setIcon(Expander.EXPAND_ICON);
				JMenuItem deps = new JMenuItem("dependencies");
				expand.add(deps);
				deps.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						new Expander().expand(
								figureFacet,
								null,
								UML2Package.eINSTANCE.getNamedElement_OwnedAnonymousDependencies(),
								new DependencyCreatorGem().getArcCreateFacet(),
								coordinator);
					}
				});
				popup.add(expand);
			}
			
      // if we have a mini-appearance, it may want to contribute
      if (miniAppearanceFacet != null)
        miniAppearanceFacet.addToContextMenu(popup, diagramView, coordinator);

			return popup;
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
					Command forceSuppressOwningPackageCommand = new SuppressOwningPackageCommand(figureFacet.getFigureReference(), !forceSuppressOwningPackage, forceSuppressOwningPackage ? "unsuppressed owner package" : "suppressed owner package", forceSuppressOwningPackage ? "suppressed owner package" : "unsuppressed owner package");
					coordinator.executeCommandAndUpdateViews(forceSuppressOwningPackageCommand);
				}
			});
			return showVisibilityItem;
		}
		
		private JMenuItem getDisplayAsIconItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
		{
			// for adding operations
			JCheckBoxMenuItem displayAsIconItem = new JCheckBoxMenuItem("Display as icon");
			displayAsIconItem.setState(displayOnlyIcon);
	
			displayAsIconItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// toggle the suppress operations flag
					coordinator.startTransaction(
							"displayed " + getFigureName() + (displayOnlyIcon ? " as box" : "as icon"),
							"displayed " + getFigureName() + (!displayOnlyIcon ? " as box" : "as icon"));
					DisplayAsIconTransaction.display(figureFacet, !displayOnlyIcon);
					coordinator.commitTransaction();
				}
			});
			return displayAsIconItem;
		}
		
		private JMenuItem getSuppressContentsItem(final DiagramFacet diagram, final ToolCoordinatorFacet coordinator)
		{
			// for adding attributes
			JCheckBoxMenuItem suppressContentsItem = new JCheckBoxMenuItem("Hide contents");
			suppressContentsItem.setState(suppressContents || displayOnlyIcon);
			suppressContentsItem.setEnabled(!displayOnlyIcon);
	
			suppressContentsItem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// toggle the suppress attributes flag
					coordinator.startTransaction(
							(suppressContents ? "showed" : "hid") + " contents for " + getFigureName(),
							(!suppressContents ? "showed " : "hid ") + " contents for " + getFigureName());
					suppressFeatures(!suppressContents);
					coordinator.commitTransaction();
				}
			});
			return suppressContentsItem;
		}
		
		public UBounds getAutoSizedBounds(boolean autoSized)
		{
			if (displayOnlyIcon && miniAppearanceFacet != null)
				return ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), miniAppearanceFacet.getMinimumDisplayOnlyAsIconExtent());
				
			if (!contents.isEmpty() && !suppressContents)
			{
				resizeVetterFacet.startResizeVet();
				UBounds centredBounds = new UBounds(figureFacet.getFullBounds().getTopLeftPoint(), new UDimension(0,0));
				return resizeVetterFacet.vetFreeResizedBounds(null, ResizingManipulatorGem.BOTTOM_RIGHT_CORNER, centredBounds, false);
			}
			else
			{
				BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
				info.setAutoSized(true);
				UBounds autoBounds = ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), info.makeActualSizes().getOuter().getDimension());
				return autoBounds;
			}
		}
	
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getCreationExtent()
		 */
		public UDimension getCreationExtent()
		{
			return new UDimension(0,0);
		}

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.nodesupport.BasicNodeAppearanceFacet#getActualFigureForSelection()
		 */
		public FigureFacet getActualFigureForSelection()
		{
			return figureFacet;
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
			
			return makeCurrentSizeInfo().makeActualSizes().getFull();
		}

		public UBounds getRecalculatedFullBounds(boolean diagramResize)
		{
			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
			BasicNamespaceSizes sizes = info.makeActualSizes();
      if (!diagramResize)
        return sizes.getOuter();
			return formCentredBounds(info, sizes.getContents()).getOuter();
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.nodesupport.BasicNodeAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("owner", owner));
			properties.add(new PersistentProperty("supC", suppressContents, false));
			properties.add(new PersistentProperty("tlOff", rememberedTLOffset, new UDimension(0,0)));
			properties.add(new PersistentProperty("brOff", rememberedBROffset, new UDimension(0,0)));
			properties.add(new PersistentProperty("icon", displayOnlyIcon, false));
			properties.add(new PersistentProperty("showVis", showOwningPackage, false));
			properties.add(new PersistentProperty("suppVis", forceSuppressOwningPackage, false));
      properties.add(new PersistentProperty("fill", fillColor, INITIAL_FILL_COLOR));
      properties.add(new PersistentProperty("stereoHash", stereotypeHashcode, 0));
		}

		private boolean locatedInCorrectView()
		{
			SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
			Object top = repository.getTopLevelModel();
			
			// look upwards to find a potential actual stratum
			Namespace actualNamespace = subject.getNamespace();
			Namespace actualStratum = actualNamespace;
			while (UML2DeltaEngine.isRawPackage(actualStratum))
				actualStratum = (Namespace) actualStratum.getOwner();
			if (actualStratum != top)
				actualNamespace = actualStratum;

			// locate to the diagram, or a possible nesting package
			// look upwards, until we find one that has a PackageFacet registered
			Package owningPkg = (Package) figureFacet.getDiagram().getLinkedObject();
			Namespace containingNamespace = repository.findVisuallyOwningPackage(
          figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
			Namespace containingStratum = repository.findVisuallyOwningStratum(
          figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
			if (containingStratum != top)
				owningPkg = (Package) containingStratum;
			else
			if (containingNamespace != null)
				owningPkg = (Package) containingNamespace; 
			
			// are we in the correct view?
			return owningPkg == actualNamespace;
		}

		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
			if (subject == null || pass != ViewUpdatePassEnum.LAST)
				return;
			
			// should we be displaying the owner?
			final boolean shouldBeDisplayingOwningPackage = !locatedInCorrectView() && !forceSuppressOwningPackage;

			// if neither the name or the namespace has changed, or the in-placeness, suppress any command
      final int actualStereotypeHashcode = StereotypeUtilities.calculateStereotypeHash(figureFacet, subject);
      if (!subject.getName().equals(name))
      {
      	name = subject.getName();
      	figureFacet.getDiagram().forceAdjust(figureFacet);
      }

      if (shouldBeDisplayingOwningPackage == showOwningPackage &&
          subject.getName().equals(name) && subject.getNamespace().getName().equals(owner) &&
          stereotypeHashcode == actualStereotypeHashcode)
				return;
			
			// set the new variables
			owner = GlobalSubjectRepository.repository.findOwningStratum(subject).getName();
			showOwningPackage = shouldBeDisplayingOwningPackage;
      stereotypeHashcode = actualStereotypeHashcode;
			
			// resize, using a text utility
      figureFacet.performResizingTransaction(textableFacet.vetTextResizedExtent(name));
		}

		public Command middleButtonPressed(ToolCoordinatorFacet coordinator)
		{
			return featurelessClassifierAppearanceFacet.middleButtonPressed(figureFacet.getDiagram());
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
    }

		public boolean canMoveContainers()
		{
			return true;
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet) || !locatedInCorrectView();
    }

    public Set<String> getDisplayStyles(boolean anchorIsTarget)
    {
      return null;
    }
	}
	
	private class TextableFacetImpl implements TextableFacet
	{
    public void setText(String text, Object listSelection, boolean unsuppress)
    {
      SetTextPayload payload = miniAppearanceFacet.setText(null, text, listSelection, unsuppress);

      if (payload != null && payload.getSubject() != null)
          subject = (Namespace) payload.getSubject();
    }

    public JList formSelectionList(String textSoFar)
    {
      return miniAppearanceFacet.formSelectionList(textSoFar);
    }
	  
		/**
		 * vetting for the text and resizing manipulators
		 * 
		 */
	
		public UBounds vetTextResizedExtent(String name)
	  {
	  	return vetTextChange(name).getOuter();
	  }
	
	  public UBounds getTextBounds(String name)
	  {
	  	return vetTextChange(name).getText();
	  }
	
		private BasicNamespaceSizes vetTextChange(String name)
		{
			// get the old sizes, the new sizes and then get the preferred topleft.  Then, get the sizes with this as the topleft point!!
	  	BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
	  	info.setName(name);
	  	BasicNamespaceSizes newSizes = info.makeActualSizes();
	
	    // centre this
	    UBounds possibleBounds = ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), newSizes.getOuter().getDimension());
	    if (!info.isNameInTab() || possibleBounds.getX() < newSizes.getOuter().getX())
			{
		    info.setTopLeft(possibleBounds.getPoint());  
		  	return info.makeActualSizes();
			}
			else
				return newSizes;
		}
		
		/**
		 * @see com.hopstepjump.jumble.figurefacilities.textmanipulationbase.TextableFacet#getFigureFacet()
		 */
		public FigureFacet getFigureFacet()
		{
			return figureFacet;
		}
	}
	
	private class ResizeVetterFacetImpl implements ResizeVetterFacet
	{
		public UDimension vetResizedExtent(UBounds bounds)
	  {
			if (displayOnlyIcon)
	  	{
		  	UDimension extent = bounds.getDimension();
		  	
		    // don't allow the extent to go less than the height or width
		    UDimension minExtent = miniAppearanceFacet.getMinimumDisplayOnlyAsIconExtent();
		    if (figureFacet.isAutoSized())
		      return minExtent;
		
		    double vettedWidth = Math.max(extent.getWidth(), minExtent.getWidth());
		    return new UDimension(vettedWidth, minExtent.getHeight() / minExtent.getWidth() * vettedWidth);  // preserve ratio
	  	}

			UDimension extent = bounds.getDimension();
	
	  	if (!contents.isEmpty() && !suppressContents)
	  		return extent;
	  	BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
	  	info.setAutoSized(true);
	  	BasicNamespaceSizes sizes = info.makeActualSizes();
	  	return sizes.getOuter().getDimension().maxOfEach(extent);
	  }
	  
		public void startResizeVet()
		{
			minVetBounds = null;
		}
		
		public UBounds vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
	  {
			if (displayOnlyIcon && miniAppearanceFacet != null)
				return bounds;
				
	   	if (figureFacet.isAutoSized())
	   	{
	   		BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
				return info.makeActualSizes().getOuter();
	   	}	
	   	return vetFreeResizedBounds(view, corner, bounds, fromCentre);
	  }
	   
	  private UBounds vetFreeResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre)
	  {
	  	if (contents.isEmpty() || suppressContents)
	  		return bounds;
	
			// NOTE: the hard code is now moved into SimpleContainer etc...
	  	if (minVetBounds == null)
	  	{
	  		// debug code -- display on the screen
				minVetBounds = contents.getMinimumResizeBounds(new ContainerSizeCalculator()
				{
					public ContainerSizeInfo makeInfo(UPoint topLeft, UDimension extent, boolean autoSized)
					{
						BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
						info.setTopLeft(topLeft);
						info.setEntire(extent);
						info.setAutoSized(autoSized);
						return info;
					}
				}, corner, bounds, figureFacet.getFullBounds().getTopLeftPoint(), fromCentre);
	  	}
	
			// return the bounds that are bigger or equal to the auto bounds (on a per-dimension basis)
			UPoint topLeft = bounds.getTopLeftPoint().minOfEach(minVetBounds.getTopLeftPoint());
			UPoint bottomRight = bounds.getBottomRightPoint().maxOfEach(minVetBounds.getBottomRightPoint());
			
			// calculate the sizes, just to get the tab height
	    return new UBounds(topLeft, bottomRight.subtract(topLeft));
	  }
	}

  public BasicNamespaceNodeGem(Namespace subject, String owningPackageName, DiagramFacet diagram, String figureId, String figureName, Color fillColor, boolean displayOnlyIcon)
  {
    this.subject = subject;
  	this.name = subject != null ? subject.getName() : "";
  	this.figureName = figureName;
  	this.fillColor = fillColor;
  	this.owner = owningPackageName;
  	this.displayOnlyIcon = displayOnlyIcon;
  	
		SimpleContainerGem simpleGem = SimpleContainerGem.createAndWireUp(
        diagram,
        figureId + "_C",
        containerFacet,
        new UDimension(0,0),
        new UDimension(4,4),
        null,
        true);
		contents = simpleGem.getSimpleContainerFacet();
  }

  public BasicNamespaceNodeGem(String figureName, PersistentFigure pfig)
  {
  	this.figureName = figureName;
		// reconstitute the subject
  	interpretPersistentFigure(pfig);
  }
  
  private void interpretPersistentFigure(PersistentFigure pfig)
	{
		PersistentProperties properties = pfig.getProperties();
		subject = (Namespace) pfig.getSubject();
    name = subject.getName();
		owner = properties.retrieve("owner").asString();
		suppressContents = properties.retrieve("supC", false).asBoolean();
		rememberedTLOffset = properties.retrieve("tlOff", new UDimension(0,0)).asUDimension();
		rememberedBROffset = properties.retrieve("brOff", new UDimension(0,0)).asUDimension();
		displayOnlyIcon = properties.retrieve("icon", false).asBoolean();

		showOwningPackage = properties.retrieve("showVis", false).asBoolean();
		forceSuppressOwningPackage = properties.retrieve("suppVis", false).asBoolean();
    fillColor = properties.retrieve("fill", INITIAL_FILL_COLOR).asColor();
    stereotypeHashcode = properties.retrieve("stereoHash", 0).asInteger();
	}

	/**
	 * geometry calculations
	 * 
	 */

	private BasicNamespaceSizeInfo makeCurrentSizeInfo()
	{
		UBounds fullBounds = figureFacet.getFullBounds();
		UPoint pt = fullBounds.getTopLeftPoint();
		UDimension resizedExtent = fullBounds.getDimension();
		
		boolean haveIcon = miniAppearanceFacet != null && miniAppearanceFacet.haveMiniAppearance();
		UDimension minimumIconExtent = (miniAppearanceFacet == null ? null : miniAppearanceFacet.getMinimumDisplayOnlyAsIconExtent());

		// work out the package name for visibility calcs
		String owningPackageString = null;
		if (showOwningPackage && owner != null)
			owningPackageString = "(from " + owner + ")";

		BasicNamespaceSizeInfo info =
		  new BasicNamespaceSizeInfo(
		      featurelessClassifierAppearanceFacet,
		      pt,
		      font,
		      packageFont,
		      name,
		      haveIcon,
		      displayOnlyIcon,
		      minimumIconExtent,
		      contents.getMinimumBounds(),
		      suppressContents,
		      owningPackageString);
		if (!figureFacet.isAutoSized())
		{
			// calculate the sizes, just to get the tab height
			info = new BasicNamespaceSizeInfo(
			    featurelessClassifierAppearanceFacet,
			    pt,
		      font,
		      packageFont,
			    name,
			    haveIcon,
			    displayOnlyIcon, 
			    minimumIconExtent,
			    resizedExtent,
			    contents.getMinimumBounds(),
			    suppressContents,
			    owningPackageString);
		}
		return info;
	}
	
	// finish of geometry calculations
	  
  private BasicNamespaceSizeInfo makeCurrentInfoFromPreviews(PreviewCacheFacet previews)
	{
		SimpleContainerPreviewFacet contentsPreviewFacet = (SimpleContainerPreviewFacet) previews.getCachedPreview(contents.getFigureFacet()).getDynamicFacet(SimpleContainerPreviewFacet.class);

		if (contentsPreviewFacet != null)  // should always be ok
		{
			BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
			UBounds minContentBounds = contentsPreviewFacet.getMinimumBoundsFromPreviews(previews);
			info.setMinContentBounds(contentsPreviewFacet.isEmpty() ? null : minContentBounds);
			return info;
		}
		else
			return null;
	}
	
	private UBounds getContentsSuppressedBounds(boolean suppressContents)
	{
		BasicNamespaceSizeInfo info = makeCurrentSizeInfo();
		info.setSuppressContents(suppressContents);
		boolean contentsShowing = !suppressContents && !contents.isEmpty();
		
		// if this is not suppressed, set the contents to the remembered bounds, translating for the offset
		if (contentsShowing)
		{
			UBounds minContents = info.getMinContentBounds();
			UPoint tl = minContents.getTopLeftPoint().subtract(rememberedTLOffset);
			UPoint br = minContents.getBottomRightPoint().add(rememberedBROffset);
			info.setMinContentBounds(new UBounds(tl, br.subtract(tl)));
		}
		
		BasicNamespaceSizes sizes = info.makeActualSizes();
		UBounds bounds = sizes.getOuter();
		if (suppressContents)
			bounds = formCentredBounds(info, sizes.getContents()).getOuter();
		
		// if this is to be suppressed then remember the content bounds and the top left bounds
		if (suppressContents)
		{
			if (contents.isEmpty())
			{
				rememberedTLOffset = new UDimension(0,0);
				rememberedBROffset = new UDimension(0,0);
			}
			else
			{
				UBounds fullBounds = figureFacet.getFullBounds();
				info.setTopLeft(fullBounds.getTopLeftPoint());
				info.setEntire(fullBounds.getDimension());
				info.setSuppressContents(false);
				UBounds fullContents = info.makeActualSizes().getContents();
				UBounds minContents = info.getMinContentBounds();
				rememberedTLOffset = minContents.getTopLeftPoint().subtract(fullContents.getTopLeftPoint());
				rememberedBROffset = fullContents.getBottomRightPoint().subtract(minContents.getBottomRightPoint());
			}
		}

		return bounds;
	}
	
	private BasicNamespaceSizes formCentredBounds(BasicNamespaceSizeInfo info, UBounds contentBoundsToPreserve)
	{
		BasicNamespaceSizes sizes = info.makeActualSizes();
		
		// ok to simply recentre and return, if the contents are empty
		if (contents.isEmpty() || suppressContents)
		{
			UBounds centred = ResizingManipulatorGem.formCentrePreservingBoundsExactly(figureFacet.getFullBounds(), sizes.getOuter().getDimension());
			info.setTopLeft(centred.getTopLeftPoint());
			return info.makeActualSizes();
		}
			
		// if this is autosized, don't move in the top left
		if (figureFacet.isAutoSized())
			return sizes;
			
		// case 3: preserve the contents region if we can, by pushing the topleft up or down, and
		// trying to centre horizontally
		info.setMinContentBounds(contentBoundsToPreserve);
		UBounds newBounds = info.makeActualSizes().getOuter();
		
		// adjust the new top left to make the width centred
		double widthDifference = (newBounds.getWidth() - contentBoundsToPreserve.getWidth())/2;
		double heightDifference = sizes.getContents().getHeight() - contentBoundsToPreserve.getHeight();
		info.setTopLeft(sizes.getOuter().getPoint().subtract(new UDimension(widthDifference, -heightDifference)));
		info.setEntire(info.getEntire().subtract(new UDimension(0, heightDifference)));
		
		return info.makeActualSizes();
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
  	figureFacet.registerDynamicFacet(showOwningPackageFacet, SuppressOwningPackageFacet.class);
  	figureFacet.registerDynamicFacet(locationFacet, LocationFacet.class);
    figureFacet.registerDynamicFacet(stylableFacet, StylableFacet.class);
	}
	
	public void connectBasicNamespaceMiniAppearanceFacet(BasicNamespaceMiniAppearanceFacet miniAppearanceFacet)
	{
		this.miniAppearanceFacet = miniAppearanceFacet;
	}
	
	public void connectFeaturelessClassifierAppearanceFacet(BasicNamespaceAppearanceFacet featurelessClassifierAppearanceFacet)
	{
		this.featurelessClassifierAppearanceFacet = featurelessClassifierAppearanceFacet;
	}
	
	public ResizeVetterFacet getResizeVetterFacet()
	{
		return resizeVetterFacet;
	}
	
	public FigureFacet getFigureFacet()
	{
		return figureFacet;
	}
	
	public BasicNodeContainerFacet getBasicNodeContainerFacet()
	{
		return containerFacet;
	}
	
	public BasicNamespaceNodeFacet getBasicNamespaceNodeFacet()
	{
		return namespaceFacet;
	}

  public static JMenuItem getChangeColorItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator, final FigureFacet figureFacet, final Color fillColor)
  {
    // for adding operations
    JMenuItem chooseColorItem = new JMenuItem("Select color");
    chooseColorItem.setIcon(COLOR_WHEEL_ICON);

    chooseColorItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ExtendedColorChooser chooser = new ExtendedColorChooser();
        chooser.setColor(fillColor);
        int chosen = coordinator.invokeAsDialog(
        		COLOR_WHEEL_ICON,
        		"Select color",
        		chooser,
        		new JButton[]{new JButton("OK"), new JButton("Cancel")},
        		null);
        if (chosen == 0)
        {
          ChangeColorCommand changeColor =
            new ChangeColorCommand(
              figureFacet.getFigureReference(),
              chooser.getColor(),
              "Changed fill color",
              "Reverted fill color");
          coordinator.executeCommandAndUpdateViews(changeColor);
        }
      }
    });
    return chooseColorItem;
  }
}
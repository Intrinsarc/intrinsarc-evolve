package com.hopstepjump.jumble.umldiagrams.packagenode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.basicnamespacenode.*;
import com.hopstepjump.layout.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 *
 * (c) Andrew McVeigh 29-Aug-02
 *
 */
public class PackageAppearanceGem implements Gem
{
  private static final int MIN_EXTRA_WIDTH_FOR_CONTENTS = 30;
  private static final int MIN_BODY_HEIGHT              = 60;
  private static final int MIN_TAB_HEIGHT               = 15;
  private static final int MIN_WIDTH                    = 87;
  private static final int MIN_TAB_WIDTH                = MIN_WIDTH * 7 / 16;
  
	private BasicNamespaceAppearanceFacet packageAppearanceFacet = new PackageAppearanceFacetImpl();
	private BasicNamespaceNodeFacet nodeFacet;

	public PackageAppearanceGem()
	{
	}
	
	public BasicNamespaceAppearanceFacet getPackageAppearanceFacet()
	{
		return packageAppearanceFacet;
	}

	public void connectFeaturelessClassifierNodeFacet(BasicNamespaceNodeFacet nodeFacet)
	{
		this.nodeFacet = nodeFacet;
	}
	
	private class PackageAppearanceFacetImpl implements BasicNamespaceAppearanceFacet
	{
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.packagenode.PackageAppearanceFacet#formView(PackageSizes, boolean, Color, Color, ClassifierMiniAppearanceFacet, boolean)
		 */
		public ZGroup formView(BasicNamespaceSizes sizes, boolean displayOnlyIcon, Color fillColor, Color lineColor, BasicNamespaceMiniAppearanceFacet miniAppearanceFacet)
		{
			ZGroup group = new ZGroup();
			if (displayOnlyIcon && miniAppearanceFacet.haveMiniAppearance())
			{
				group.addChild(miniAppearanceFacet.formView(displayOnlyIcon, sizes.getIcon()));
				group.addChild(sizes.getZName());
				if (sizes.getZOwningName() != null)
					group.addChild(sizes.getZOwningName());
			}
			else
			{
		    // add a tab
		    ZRectangle tab = new ZRectangle(sizes.getTab());
		    tab.setFillPaint(fillColor);
		    tab.setPenPaint(lineColor);
		
		    // make a box
		    ZRectangle body = new ZRectangle(sizes.getBody());
		    body.setFillPaint(fillColor);
		    body.setPenPaint(lineColor);
		
		    // place a transparent rectangle over the name, so we can get the events
		    ZRectangle rect = new ZRectangle(sizes.getText());
		    rect.setFillPaint(ScreenProperties.getTransparentColor());
		    rect.setPenPaint(null);
		    ZVisualLeaf transparentCover = new ZVisualLeaf(rect);
		
				// place a transparent rectangle over the 'from pkg' name, so we can get the events
 				ZVisualLeaf transparentPkgCover = null;
		    if (sizes.getOwner() != null)
		    {
		    	ZRectangle pkgRect = new ZRectangle(sizes.getOwner());
		    	pkgRect.setFillPaint(ScreenProperties.getTransparentColor());
			    pkgRect.setPenPaint(null);
			    transparentPkgCover = new ZVisualLeaf(pkgRect);
		    }
		
		    // group them
		    group.addChild(new ZVisualLeaf(tab));
		    group.addChild(new ZVisualLeaf(body));
		    group.addChild(sizes.getZName());
		    if (sizes.getZOwningName() != null)
			    group.addChild(sizes.getZOwningName());
	
				// possibly add an icon
				UBounds iconBounds = sizes.getIcon();
				if (iconBounds != null)
					group.addChild(miniAppearanceFacet.formView(displayOnlyIcon, iconBounds));
	
		    group.addChild(transparentCover);
		    if (transparentPkgCover != null)
			    group.addChild(transparentPkgCover);
			}
			sizes.setContents(new UBounds(sizes.getOuter().getPoint(), new UDimension(0,0)));
			sizes.setBody(sizes.getContents());
			
		  return group;
		}

		private BasicNamespaceSizes makeActualSizesForDisplayIconOnly(
				BasicNamespaceSizeInfo info,
				boolean moveTopLeftIn,
				UBounds currentBounds,
				ZTransformGroup zName,
				UDimension zNameDimension,
				ZTransformGroup zOwningPackageName,
				UDimension zOwningPackageNameDimension)
		{
			// this is much simpler than the other case, because it implies that the contents are suppressed

			// make an outer box, with an icon, and a possible owning package name
			ContainerBox contents = new ContainerBox("full", new VerticalLayout());
			OuterBox outer = new OuterBox("outer", currentBounds, info.getAutoSized(), moveTopLeftIn, true, contents);
			
			// add the icon
	    UDimension iconExtent = info.getMinIconExtent();
	    if (!info.getAutoSized())
	    {
	    	iconExtent = iconExtent.maxOfEach(info.getEntire());
	    	// make sure that the icon preserves its ratio, by keeping the width only, and making height a derived attribute
	    	double width = iconExtent.getWidth();
	    	iconExtent = new UDimension(width, info.getMinIconExtent().getHeight() / info.getMinIconExtent().getWidth() * width);
	    }
			ExpandingBox icon = new ExpandingBox("icon", iconExtent);
			contents.addBox(new ExpandingBox("tab", new UDimension(0,0)));
			contents.addBox(icon);
			
			// add the name
			ExpandingBox name = new ExpandingBox("text", zNameDimension);
			BorderBox nameBorder = new BorderBox(">textBorder", new UDimension(4,3), name);
			contents.addBox(nameBorder);
			
			// add the possible owner
			ExpandingBox owner = null;
			if (zOwningPackageName != null)
			{
				owner = new ExpandingBox("owner", zOwningPackageNameDimension);
				contents.addBox(owner);
			}
			
			// set the fields in sizes now...
			BasicNamespaceSizes sizes = new BasicNamespaceSizes();
			try
			{
				outer.computeLayoutBounds();

				// offset, to ensure that the icon starts where it did before
				UDimension offsetToKeepIconFixed = info.getTopLeft().subtract(icon.getLayoutBounds().getPoint());
				outer.offsetLayoutBounds(offsetToKeepIconFixed);

				outer.setBoundsInBean(sizes);
			}
			catch (LayoutException ex)
			{
				throw new IllegalStateException("Problem with layout: " + ex + "\nouter =\n" + outer);
			}

			// set up the text slots
			UPoint namePoint = name.getLayoutBounds().getPoint();
			zName = info.makeCentredZTexts(info.getName());
			zName.setTranslation(namePoint.getX(), namePoint.getY());
			sizes.setZName(zName);
			if (zOwningPackageName != null)
			{
				UPoint owningPoint = owner.getLayoutBounds().getPoint();
				zOwningPackageName.setTranslation(owningPoint.getX(), owningPoint.getY());
				sizes.setZOwningName(zOwningPackageName);
			}
			
			// set up some other fields
			sizes.setDisplayOnlyIcon(true);
			
			// null some other fields
			UBounds nullBounds = new UBounds(sizes.getOuter().getPoint(), new UDimension(0,0));
			sizes.setBody(sizes.getOuter());
			sizes.setContents(nullBounds);
			sizes.setOuter(sizes.getIcon());
			return sizes;
		}
	
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.packagenode.PackageAppearanceFacet#setUpSizeInfo(PackageSizeInfo)
		 */
		public BasicNamespaceSizes makeActualSizes(BasicNamespaceSizeInfo info, boolean moveTopLeftIn)
		{
			ZTransformGroup zName = info.makeCentredZTexts(info.getNonZeroName(info.getName()));
			UDimension zNameDimension = new UBounds(zName.getBounds()).getDimension();
			
			ZTransformGroup zOwningPackageName = null;
			String owningPackage = info.getOwningPackageName();
			UDimension zOwningPackageNameDimension = null;
			if (owningPackage != null)
			{
				zOwningPackageName = info.makeCentredZTexts(owningPackage, false);
				zOwningPackageNameDimension = new UBounds(zOwningPackageName.getBounds()).getDimension();
			}

			// work out the current bounds
			UBounds currentBounds = new UBounds(info.getTopLeft(), new UDimension(0,0));
			if (!info.getAutoSized())
				currentBounds = new UBounds(info.getTopLeft(), info.getEntire());
			
			// if this is iconified, then display very differently
			if (info.getHaveIcon() && info.getDisplayIconOnly())
				return makeActualSizesForDisplayIconOnly(info, moveTopLeftIn, currentBounds,
																								 zName, zNameDimension, zOwningPackageName, zOwningPackageNameDimension);
			
			// calculate the icon height
			UDimension iconExtent = null;
			if (info.getHaveIcon())
			{
				ZTransformGroup heightCalcGroup = info.makeCentredZTexts("A");
				double iconHeight = heightCalcGroup.getBounds().getHeight();
				iconExtent = new UDimension(iconHeight, iconHeight);
			}

			// if we have contents, then we place the text in the tab
			// otherwise, we leave it in the centre body
			ContainerBox boxes = new ContainerBox("full", new VerticalLayout());
			OuterBox outer = new OuterBox("outer", currentBounds, info.getAutoSized(), moveTopLeftIn, true, boxes);
			
			// make the tab
			// do it differently if we have no contents
			boolean haveContents = info.getMinContentBounds() != null && !info.getSuppressContents();
			
			// no contents -- tab in body
			if (!haveContents)
			{
				// set up the tab
				HorizontalSpaceLayout spacer = new HorizontalSpaceLayout(new double[]{7, 9});
				ContainerBox tabContainer = new ContainerBox(null, spacer);
				tabContainer.addBox(new ExpandingBox("tab", new UDimension(0, MIN_TAB_HEIGHT), ExpandingBox.EXPAND_X, ExpandingBox.X_LEFT));
				tabContainer.addBox(new ExpandingBox(null, new UDimension(0,0), ExpandingBox.EXPAND_X, ExpandingBox.X_LEFT));
				boxes.addBox(tabContainer);

				// set up the body
				BorderBox titleContainer = new BorderBox(null, new UDimension(6,4), null);
				VerticalSpaceLayout verticalSpaceLayout = new VerticalSpaceLayout(null);
				titleContainer.setLayout(verticalSpaceLayout);
				
				// if we have an icon, add it to the top right
				if (info.getHaveIcon())
				{
					titleContainer.addBox(new BorderBox(null, new UDimension(0,0),
																							new ExpandingBox("icon", iconExtent, ExpandingBox.EXPAND_NONE, ExpandingBox.X_RIGHT)));
					verticalSpaceLayout.addProportion(0);
				}
				
				titleContainer.addBoxes(
					new ExpandingBox(null, new UDimension(0,0), ExpandingBox.EXPAND_BOTH, ExpandingBox.X_CENTRE),
					new ExpandingBox("text", zNameDimension));
				verticalSpaceLayout.addProportion(1);
				verticalSpaceLayout.addProportion(0);
				
				if (zOwningPackageName != null)
				{
					titleContainer.addBoxes(
						new ExpandingBox(null, new UDimension(0,3)),
						new ExpandingBox("owner", zOwningPackageNameDimension));
					verticalSpaceLayout.addProportion(0);
					verticalSpaceLayout.addProportion(0);
				}
				titleContainer.addBox(
					new ExpandingBox(null, new UDimension(0,0), ExpandingBox.EXPAND_BOTH, ExpandingBox.X_CENTRE));
				ContainerBox body = new ContainerBox("body", new VerticalLayout());
				verticalSpaceLayout.addProportion(1.4);

				ContainerBox contents = new ContainerBox("contents", new VerticalLayout());
				contents.addBox(titleContainer);
				body.addBox(contents);
				boxes.addBox(body);
			}
			else
			// have contents -- tab has title
			{
				// set up the tab
				// make sure that the name is spaced in the centre
				ExpandingBox textName = new ExpandingBox("text", zNameDimension, ExpandingBox.EXPAND_X, ExpandingBox.X_CENTRE, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE);
				VerticalSpaceLayout verticalName = new VerticalSpaceLayout(new double[]{1,0,1});
				ContainerBox name = new ContainerBox(null, verticalName, true, true);
				name.addBoxes(new ExpandingBox(null, new UDimension(0,0), ExpandingBox.EXPAND_BOTH, ExpandingBox.X_CENTRE),
										  textName,
										  new ExpandingBox(null, new UDimension(0,0), ExpandingBox.EXPAND_BOTH, ExpandingBox.X_CENTRE));
				BorderBox plusOwner = new BorderBox(">plusOwner", new UDimension(0,0), name);
				BorderBox tabBorder = new BorderBox(">tabBorder", new UDimension(5,1), new UDimension(5,4), plusOwner);
				tabBorder.setLayout(new HorizontalLayout());
				BorderBox tab = new BorderBox("tab", new UDimension(0,0), tabBorder);
				tab.setForcedMinimumDimensions(new UDimension(MIN_TAB_WIDTH, MIN_TAB_HEIGHT));
				tab.setExpansionCharacteristics(false, false);

				// possibly add an icon
				if (info.getHaveIcon())
				{
					tabBorder.addBoxes(new ExpandingBox(">iconXOffset", new UDimension(2, 0)),
														 new BorderBox(null, new UDimension(4,2), new ExpandingBox("icon", iconExtent)));
				}

				// add a possible owner
				if (zOwningPackageName != null)
				{
					plusOwner.addBoxes(
						new ExpandingBox(null, new UDimension(0,3)),
						new ExpandingBox("owner", zOwningPackageNameDimension));
				}

				ContainerBox tabContainer = new ContainerBox(">tabContainer", new HorizontalLayout());
				tabContainer.addBoxes(tab, new ExpandingBox(">extraForOffset", new UDimension(MIN_EXTRA_WIDTH_FOR_CONTENTS, MIN_TAB_HEIGHT), ExpandingBox.EXPAND_X, ExpandingBox.X_LEFT));				
				boxes.addBox(tabContainer);

				// set up the body / contents
				ImmovableBox contents = new ImmovableBox("contents", info.getMinContentBounds());
				boxes.addBox(new BorderBox("body", new UDimension(0,0), contents));
			}
			
			// set the fields in sizes now...
			BasicNamespaceSizes sizes = new BasicNamespaceSizes();

			try
			{
				boxes.setForcedMinimumDimensions(new UDimension(MIN_WIDTH, MIN_TAB_HEIGHT + MIN_BODY_HEIGHT));
				outer.computeLayoutAndSetBoundsInBean(sizes);
			}
			catch (LayoutException ex)
			{
				throw new IllegalStateException("Problem with layout: " + ex + "\nouter =\n" + outer);
			}

			// set up the text slots
			UPoint namePoint = sizes.getText().getPoint();
			zName = info.makeCentredZTexts(info.getName());
			zName.setTranslation(namePoint.getX(), namePoint.getY());
			sizes.setZName(zName);
			if (sizes.getOwner() != null)
			{
				UPoint owningPoint = sizes.getOwner().getPoint();
				zOwningPackageName.setTranslation(owningPoint.getX(), owningPoint.getY());
				sizes.setZOwningName(zOwningPackageName);
			}
			
			// set up some other fields
			sizes.setDisplayOnlyIcon(false);
			
			return sizes;
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.packagenode.PackageAppearanceFacet#getInsideBoxForBoundaryCalculation(UBounds)
		 */
		public UBounds getInsideBoxForBoundaryCalculation(UBounds bounds, double insideTabHeight)
		{
			return new UBounds(new UPoint(bounds.getX(), bounds.getY() + insideTabHeight + 1), new UDimension(bounds.getWidth(), bounds.getHeight() - insideTabHeight - 2));
		}
		
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.packagenode.PackageAppearanceFacet#formShapeForPreview(UBounds, boolean, UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds, BasicNamespaceSizes sizes)
		{
			return bounds;
		}

		public Shape formShapeForBoundaryCalculation(UBounds bounds, BasicNamespaceSizes sizes)
		{
			bounds = bounds.addToExtent(new UDimension(1,1));

			if (sizes.isDisplayOnlyIcon())
			{
				bounds = bounds.union(sizes.getText());
				if (sizes.getOwner() != null)
					bounds = bounds.union(sizes.getOwner());
			}

			return bounds;
		}
		
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.basicnamespacenode.BasicNamespaceAppearanceFacet#useBoxForOutsideBoundaryCalculation()
		 */
		public boolean useBoxForOutsideBoundaryCalculation()
		{
			return true;
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.basicnamespacenode.BasicNamespaceAppearanceFacet#makeMiddleButtonCommand()
		 */
		public void middleButtonPressed(DiagramFacet diagram)
		{
			// want to open the diagram for this package
		  UBounds contentBounds = nodeFacet.getBoundsForDiagramZooming();
			GlobalPackageViewRegistry.activeRegistry.open(
			    (Package) nodeFacet.getSubject(), true, false, contentBounds, GlobalPackageViewRegistry.activeRegistry.getFocussedView().getFixedPerspective(), true);
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.basicnamespacenode.BasicNamespaceAppearanceFacet#isEmptyAndCanBeDeleted(MNamespace)
		 */
		public boolean isEmptyAndCanBeDeleted(Namespace subject)
		{
      J_DiagramHolder holder = ((Package) subject).getJ_diagramHolder();
      J_Diagram diagram = (holder == null) ? null : holder.getDiagram();
    	boolean emptyDiagram = diagram == null || diagram.getFigures().isEmpty();
	    boolean subjectsEmpty = subject.getOwnedElements().isEmpty();
		    
			return emptyDiagram && subjectsEmpty;
		}

		public ToolFigureClassification getToolClassification(BasicNamespaceSizes sizes, UPoint point)
		{
			UDimension offset = new UDimension(8, 8);
			if (sizes.getContents().addToPoint(offset).addToExtent(offset.multiply(2).negative()).contains(point))
				return new ToolFigureClassification("top", null);
			return new ToolFigureClassification("namespace", null);
		}
	}
}

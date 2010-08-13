package com.intrinsarc.evolve.umldiagrams.nodenode;

import java.awt.*;
import java.awt.geom.*;

import org.eclipse.uml2.*;

import com.intrinsarc.evolve.umldiagrams.basicnamespacenode.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.layout.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 *
 * (c) Andrew McVeigh 27-Aug-02
 *
 */
public class NodeAppearanceGem implements Gem
{
	private static final double NODE_SIDES    = 30;
  private static final int Y_MIN_TOP_OFFSET =  3;
  private static final int X_OFFSET         =  8;
  private static final int MIN_TAB_HEIGHT   = 15;
  private static final int MIN_BODY_HEIGHT  = 100;
  private static final int MIN_WIDTH        = MIN_BODY_HEIGHT + MIN_TAB_HEIGHT;

	private BasicNamespaceAppearanceFacet appearanceFacet = new NodeAppearanceFacetImpl();
	
	public NodeAppearanceGem()
	{
	}
	
	public BasicNamespaceAppearanceFacet getPackageAppearanceFacet()
	{
		return appearanceFacet;
	}
	
	private class NodeAppearanceFacetImpl implements BasicNamespaceAppearanceFacet
	{
		public ZGroup formView(BasicNamespaceSizes sizes, boolean displayOnlyIcon, Color fillColor, Color lineColor, BasicNamespaceMiniAppearanceFacet miniAppearanceFacet)
		{	
		  ZGroup group = new ZGroup();

			if (displayOnlyIcon)
			{
				group.addChild(miniAppearanceFacet.formView(displayOnlyIcon, sizes.getIcon()));
				group.addChild(sizes.getZName());
				if (sizes.getZOwningName() != null)
					group.addChild(sizes.getZOwningName());
			}
			else
			{
				UDimension nodeSidesExtent = new UDimension(NODE_SIDES, -NODE_SIDES);
				
		    // make a box
		    UBounds entire = sizes.getOuter();
				UBounds bodyBounds = entire.addToPoint(new UDimension(0, NODE_SIDES)).addToExtent(new UDimension(-NODE_SIDES, -NODE_SIDES));
		    ZRectangle body = new ZRectangle(bodyBounds);
		    body.setFillPaint(fillColor);
		    body.setPenPaint(lineColor);

				// make the top face
				UPoint start = bodyBounds.getTopLeftPoint();
				ZPolygon topPoly = new ZPolygon(start);
				topPoly.add(start.add(new UDimension(NODE_SIDES + 5, -NODE_SIDES)));
				topPoly.add(entire.getTopRightPoint());
				topPoly.add(entire.getTopRightPoint().subtract(nodeSidesExtent));
				topPoly.setFillPaint(fillColor);
				topPoly.setPenPaint(lineColor);
				topPoly.setPenWidth(0.75);
				
				// make the side face
				start = bodyBounds.getBottomRightPoint();
				ZPolygon sidePoly = new ZPolygon(start);
				sidePoly.add(bodyBounds.getTopRightPoint());
				sidePoly.add(entire.getTopRightPoint());
				sidePoly.add(start.add(new UDimension(NODE_SIDES, -NODE_SIDES)));
				sidePoly.setFillPaint(fillColor);
				sidePoly.setPenPaint(lineColor);
				sidePoly.setPenWidth(0.75);
		
		    // place a transparent rectangle over the name, so we can get the events
		    ZRectangle rect = new ZRectangle(sizes.getText());
		    rect.setFillPaint(ScreenProperties.getTransparentColor());
		    rect.setPenPaint(null);
		    ZVisualLeaf transparentCover = new ZVisualLeaf(rect);
		    	
		    // group them
		    group.addChild(new ZVisualLeaf(body));
		    group.addChild(new ZVisualLeaf(topPoly));
		    group.addChild(new ZVisualLeaf(sidePoly));
		    group.addChild(sizes.getZName());
		    if (sizes.getZOwningName() != null)
			    group.addChild(sizes.getZOwningName());
	
				// possibly add an icon
				UBounds miniAppearanceBounds = sizes.getIcon();
				if (miniAppearanceBounds != null)
					group.addChild(miniAppearanceFacet.formView(displayOnlyIcon, miniAppearanceBounds));
	
		    group.addChild(transparentCover);
			}
	
	    return group;
		}

		private BasicNamespaceSizes makeActualSizesForDisplayIconOnly(BasicNamespaceSizeInfo info, boolean moveTopLeftIn,
																																				 UBounds currentBounds,
																																				 ZTransformGroup zName, UDimension zNameDimension,
																																				 ZTransformGroup zOwningPackageName, UDimension zOwningPackageNameDimension)
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
			ContainerBox boxes = new ContainerBox(null, new VerticalLayout());
			ContainerBox outerBox = new ContainerBox(null, new HorizontalLayout());
			outerBox.addBoxes(boxes,
												new ExpandingBox(null, new UDimension(NODE_SIDES, 0)));
			ContainerBox heightOffsetBox = new ContainerBox("full", new VerticalLayout());
			heightOffsetBox.addBoxes(new ExpandingBox(null, new UDimension(0, NODE_SIDES)),
															 outerBox);
			OuterBox outer = new OuterBox("outer", currentBounds, info.getAutoSized(), moveTopLeftIn, true, heightOffsetBox);
			
			// set up the tab
			ExpandingBox name = new ExpandingBox("text", zNameDimension, ExpandingBox.EXPAND_X, ExpandingBox.X_CENTRE, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE);
			BorderBox plusOwner = new BorderBox(">plusOwner", new UDimension(0,0), name);
			BorderBox tabBorder = new BorderBox(">tabBorder", new UDimension(5,1), new UDimension(5,4), plusOwner);
			tabBorder.setLayout(new HorizontalLayout());
			BorderBox tab = new BorderBox("tab", new UDimension(0,0), tabBorder);

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
			tabContainer.addBox(tab);
			boxes.addBox(tabContainer);

			// set up the body / contents
			LayoutBox contents = new ExpandingBox("contents", new UDimension(0, 0), ExpandingBox.EXPAND_BOTH, ExpandingBox.X_CENTRE);
			if (info.getMinContentBounds() != null && !info.getSuppressContents())
				contents = new ImmovableBox("contents", info.getMinContentBounds());
			boxes.addBox(new BorderBox("body", new UDimension(0,0), contents));
			
			// set the fields in sizes now...
			BasicNamespaceSizes sizes = new BasicNamespaceSizes();

			try
			{
				heightOffsetBox.setForcedMinimumDimensions(new UDimension(MIN_WIDTH, MIN_TAB_HEIGHT + MIN_BODY_HEIGHT));
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
			return new UBounds(new UPoint(bounds.getX(), bounds.getY() + NODE_SIDES), new UDimension(bounds.getWidth() - NODE_SIDES, bounds.getHeight()));
		}
		
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.packagenode.PackageAppearanceFacet#formShapeForPreview(UBounds, boolean, UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds, BasicNamespaceSizes sizes)
		{
			if (sizes.isDisplayOnlyIcon())
				return bounds.union(sizes.getText());
			else
				return bounds;
		}

		public Shape formShapeForBoundaryCalculation(UBounds bounds, BasicNamespaceSizes sizes)
		{
			if (sizes.isDisplayOnlyIcon())
				return formShapeForPreview(bounds, sizes);
			bounds = bounds.addToExtent(new UDimension(1,1));
			UDimension heightOffset = new UDimension(0, NODE_SIDES/2);
			return new Area(bounds.addToPoint(heightOffset).addToExtent(heightOffset.negative()).addToExtent(new UDimension(-NODE_SIDES/2, 0)));
		}
		
		/**
		 * @see com.intrinsarc.evolve.umldiagrams.basicnamespacenode.BasicNamespaceAppearanceFacet#useBoxForOutsideBoundaryCalculation()
		 */
		public boolean useBoxForOutsideBoundaryCalculation()
		{
			return false;
		}

		/**
		 * @see com.intrinsarc.evolve.umldiagrams.basicnamespacenode.BasicNamespaceAppearanceFacet#middleButtonPressed()
		 */
		public void middleButtonPressed(DiagramFacet diagram)
		{
		}

		/**
		 * @see com.intrinsarc.evolve.umldiagrams.basicnamespacenode.BasicNamespaceAppearanceFacet#isEmptyAndCanBeDeleted(MNamespace)
		 */
		public boolean isEmptyAndCanBeDeleted(Namespace subject)
		{
			// only checked the owned elements as a node doesn't have any diagrams
			return subject.getOwnedElements().isEmpty();
		}
		
    public ToolFigureClassification getToolClassification(BasicNamespaceSizes sizes, UPoint point)
		{
			return new ToolFigureClassification("node", null);
		}
	}
}


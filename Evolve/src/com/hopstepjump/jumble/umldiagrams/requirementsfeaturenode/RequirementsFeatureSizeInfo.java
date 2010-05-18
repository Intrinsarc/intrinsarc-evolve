/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import java.awt.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.layout.*;

import edu.umd.cs.jazz.component.*;

/**
 * @version 	1.0
 * @author
 */
public final class RequirementsFeatureSizeInfo implements ContainerSizeInfo
{
	private UPoint topLeft;
	private String name;
	private Font font;
	private Font pkgFont;
	private UDimension extent;
	private boolean autoSized;
	private boolean haveIcon;
	private boolean displayIconOnly;
	private String owningPackageName;
	private UDimension minimumIconExtent;
	
	/** constructor */
	public RequirementsFeatureSizeInfo(UPoint topLeft,
	    											UDimension extent,
	    											boolean autoSized,
														String name,
														Font font,
														Font pkgFont,
														boolean haveIcon,
														boolean displayIconOnly,
														UDimension minimumIconExtent,
														String owningPackageName)
	{
		this.topLeft = topLeft;
		this.name = name;
		this.extent = extent;
		this.font = font;
		this.pkgFont = pkgFont;
		this.autoSized = autoSized;
		this.extent = extent;
		this.haveIcon = haveIcon;
		this.displayIconOnly = displayIconOnly;
		this.owningPackageName = owningPackageName;
		this.minimumIconExtent = minimumIconExtent;		
	}

	public UBounds getMinContentBounds()
	{
		return null;
	}

	public void setTopLeft(UPoint topLeft)
	{
		this.topLeft = topLeft;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Font getFont()
	{
		return font;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public UPoint getTopLeft()
	{
		return topLeft;
	}
	
	public boolean getAutoSized()
	{
		return autoSized;
	}
	
	public void setAutoSized(boolean autoSized)
	{
		this.autoSized = autoSized;
	}
	
	public ContainerCalculatedSizes makeSizes(boolean moveTopLeftIn)
	{
		return makeActualSizes(moveTopLeftIn);
	}

	public RequirementsFeatureSizes makeActualSizes()
	{
		return makeActualSizes(false);
	}

	public RequirementsFeatureSizes makeActualSizesForIconOnly(boolean moveTopLeftIn, ZText zName, UDimension zNameExtent, ZText zOwningName, UDimension zOwningNameExtent)
	{
		// no contents space, but have icon, and maybe operations and attributes
    // layout all the elements
		// add the boxes to form a normal class
		ContainerBox container = new ContainerBox("full", new VerticalLayout());
		OuterBox outer = new OuterBox("outer", new UBounds(topLeft, extent), autoSized, moveTopLeftIn, true, container);
		
		// have and icon, with the minimum dimensions.  Note: the minimum dimensions need to be a multiple of 2x4=8
		UDimension iconSize = minimumIconExtent;
		if (!autoSized)
			iconSize = new UDimension(extent.getHeight(), extent.getHeight()).maxOfEach(iconSize);
		ExpandingBox icon = new ExpandingBox("icon", iconSize, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE);
		BorderBox iconBorder = new BorderBox(null, new UDimension(0,0), icon);
		container.addBox(iconBorder);

		// the title
		ExpandingBox title = new ExpandingBox("name", zNameExtent, ExpandingBox.EXPAND_X, ExpandingBox.X_CENTRE, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE);
		BorderBox nameBorder = new BorderBox(">nameBorder", new UDimension(8,4), title);
		
		// add a possible from package
		ExpandingBox owner = null;
		if (zOwningName != null)
		{
			nameBorder.addBox(new ExpandingBox(null, new UDimension(0, 2)));
			owner = new ExpandingBox("owner", zOwningNameExtent);
			nameBorder.addBox(owner);
		}		
		container.addBox(nameBorder);

		// make the sizes structure
		RequirementsFeatureSizes sizes = new RequirementsFeatureSizes();
		
		try
		{
			outer.computeLayoutBounds();
			
			// offset, to ensure that the icon starts where it did before
			UDimension offsetToKeepIconFixed = topLeft.subtract(icon.getLayoutBounds().getPoint());
			outer.offsetLayoutBounds(offsetToKeepIconFixed);
			
			outer.setBoundsInBean(sizes);
		}
		catch (LayoutException ex)
		{
			throw new IllegalStateException("Problem with layout: " + ex + "\nouter =\n" + outer);
		}

		// set up some other fields in the sizes structure
		ZText nameText = makeZText(name, font);
		nameText.setTranslation(title.getLayoutBounds().getPoint());
		sizes.setZtext(nameText);
		sizes.setOuter(icon.getLayoutBounds());
		
		// set up the possible owner
		if (zOwningName != null)
		{
			zOwningName.setTranslation(owner.getLayoutBounds().getPoint());
			sizes.setZOwningText(zOwningName);
		}
		
		return sizes;		
	}

	public RequirementsFeatureSizes makeActualSizes(boolean moveTopLeftIn)
	{
		// work out the sizes of each of the enclosed elements
    ZText zName = makeZTextForMeasuring(name, font);  // need to get non-empty bounds to stop a pixel shift
    UDimension zNameExtent = new UBounds(zName.getBounds()).getDimension();
    double textHeight = zNameExtent.getHeight();

    // handle a possible owning package
    ZText zOwningName = null;
    UDimension zOwningNameExtent = null;
    if (owningPackageName != null)
    {
    	zOwningName = makeZText(owningPackageName, pkgFont);
    	zOwningNameExtent = new UBounds(zOwningName.getBounds()).getDimension();
    }
    
		if (haveIcon && displayIconOnly)
			return makeActualSizesForIconOnly(moveTopLeftIn, zName, zNameExtent, zOwningName, zOwningNameExtent);
		
		// add the boxes to form a normal class
		ContainerBox insideOuter = new ContainerBox("insideOuter", new VerticalLayout());
		
		// if we have ports, make sure we have a nice border around the edges
		int sideWidth = 0;
		int topHeight = 0;
		
		BorderBox borderBox = new BorderBox(
		    null, 
		    new UDimension(sideWidth, topHeight),
				new UDimension(sideWidth, 0),
		    insideOuter);
		
		ContainerBox container = new ContainerBox("full", new VerticalLayout());
		container.addBox(borderBox);
		container.setForcedMinimumDimensions(new UDimension(50, 30));
		if (extent == null)
			extent = new UDimension(0,0);
		OuterBox outer = new OuterBox("outer", new UBounds(topLeft, extent), autoSized, moveTopLeftIn, true, container);
		container = insideOuter;
		
		// the title
		ExpandingBox title = new ExpandingBox("name", zNameExtent, ExpandingBox.EXPAND_X, ExpandingBox.X_CENTRE, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE);
		ContainerBox horizontalNameContainer = new ContainerBox(">horizontalNameContainer", new HorizontalLayout());
		BorderBox verticalNameBorder = new BorderBox(">verticalNameBorder", new UDimension(12,8), horizontalNameContainer);
    if (haveIcon)
      verticalNameBorder.setBottomRightInset(new UDimension(4, 4));
		horizontalNameContainer.addBox(title);

		// add a possible from package
		ExpandingBox owner = null;
		if (zOwningName != null)
		{
			verticalNameBorder.addBox(new ExpandingBox(null, new UDimension(0, 2)));
			owner = new ExpandingBox("owner", zOwningNameExtent);
			verticalNameBorder.addBox(owner);
		}		

		// possibly add an icon to the title
		if (haveIcon)
		{
			ExpandingBox space = new ExpandingBox(null, new UDimension(5,1));
			ExpandingBox icon = new ExpandingBox("icon", new UDimension(textHeight, textHeight));
			horizontalNameContainer.addBoxes(space, icon);
		}
		container.addBox(verticalNameBorder);

		// make the sizes structure
		RequirementsFeatureSizes sizes = new RequirementsFeatureSizes();
		// for anonymous imported elements, which have displayonlyicon set also
		sizes.setIcon(UBounds.ZERO);
		
		try
		{
			outer.computeLayoutAndSetBoundsInBean(sizes);
		}
		catch (LayoutException ex)
		{
			throw new IllegalStateException("Problem with layout: " + ex + "\nouter =\n" + outer, ex);
		}

		// set up some other fields in the sizes structure
		ZText nameText = makeZText(name, font);
		nameText.setTranslation(title.getLayoutBounds().getPoint());
		sizes.setZtext(nameText);
		
		// set up the possible owner
		if (zOwningName != null)
		{
			zOwningName.setTranslation(owner.getLayoutBounds().getPoint());
			sizes.setZOwningText(zOwningName);
		}
		
		return sizes;
	}
	
  private ZText makeZText(String text, Font font)
  {
    ZText zName = new ZText(text);
    if (font != null)
      zName.setFont(font);
    return zName;
  }

  private ZText makeZTextForMeasuring(String text, Font font)
  {
    boolean empty = text.length() == 0;
    return makeZText(empty ? "a" : text, font);
  }
  
  public void setExtent(UDimension extent)
  {
  	this.extent = extent;
  }
  
  public UDimension getExtent()
  {
  	return extent;
  }
}

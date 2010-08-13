/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.basicnamespacenode;

import java.awt.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.layout.*;

import edu.umd.cs.jazz.*;

/**
 * encapsulates the size calculations for a package
 */

public final class BasicNamespaceSizeInfo implements ContainerSizeInfo
{
  private BasicNamespaceAppearanceFacet packageAppearanceFacet;

	/** variables indicating the current positioning */
	private UPoint topLeft;
	private UDimension entire;  // only used if not autosized

	/** variables which are effectively commands */
	private boolean autoSized;

	/** variables which affect the new size */
	private Font font;
	private Font pkgFont;
	private String name;
	private boolean forceTextToTab;
	private UBounds minContentBounds;
	private boolean suppressContents;
	private boolean haveIcon;
	private boolean displayIconOnly;
	private UDimension minIconExtent;
	private String owningPackageName;
	
	// autosized constructor
	public BasicNamespaceSizeInfo(BasicNamespaceAppearanceFacet packageAppearanceFacet, UPoint topLeft, Font font, Font pkgFont, String name, boolean haveIcon, boolean displayIconOnly, UDimension minIconExtent, UBounds minContentBounds, boolean suppressContents, String owningPackageName)
	{
		this.packageAppearanceFacet = packageAppearanceFacet;
		this.topLeft = topLeft;
		this.font = font;
		this.pkgFont = pkgFont;
		this.name = name;
		this.haveIcon = haveIcon;
		this.displayIconOnly = displayIconOnly;
		this.minIconExtent = minIconExtent;
		this.autoSized = true;
		this.minContentBounds = minContentBounds;
		this.suppressContents = suppressContents;
		this.owningPackageName = owningPackageName;
	}
	
	public String toString()
	{
		return "FeaturelessClassifierSizeInfo(topLeft = " + topLeft + ", name = " + name + ", autoSized = " + autoSized +
						", minContentBounds = " + minContentBounds + ", suppressContents = " + suppressContents + ", entire = " + entire;
	}

	public BasicNamespaceSizeInfo(BasicNamespaceAppearanceFacet packageAppearanceFacet, UPoint topLeft, Font font, Font pkgFont, String name, boolean haveIcon, boolean displayIconOnly, UDimension minIconExtent, UDimension entire, UBounds minContentBounds, boolean suppressContents, String packageForVisibility)
	{
		this(packageAppearanceFacet, topLeft, font, pkgFont, name, haveIcon, displayIconOnly, minIconExtent, minContentBounds, suppressContents, packageForVisibility);
		this.autoSized = false;
		this.entire = entire;
	}
	
	public void setAutoSized(boolean autoSized)
	{
		this.autoSized = autoSized;
	}

	public boolean getAutoSized()
	{
		return autoSized;
	}

	public boolean isNameInTab()
	{
		return minContentBounds != null && !suppressContents || forceTextToTab;
	}

	public void setTopLeft(UPoint topLeft)
	{
		this.topLeft = topLeft;
	}

	public UPoint getTopLeft()
	{
		return topLeft;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public BasicNamespaceSizes makeActualSizes()
	{
		return packageAppearanceFacet.makeActualSizes(this, false);
	}

	public ContainerCalculatedSizes makeSizes(boolean moveTopLeftIn)
	{
		return packageAppearanceFacet.makeActualSizes(this, moveTopLeftIn);
	}

	public ZTransformGroup makeCentredZTexts(String text)
  {
  	return TextUtilities.makeCentredZTexts(text, font);
  }

	public ZTransformGroup makeCentredZTexts(String text, boolean useMainFont)
  {
		return TextUtilities.makeCentredZTexts(text, useMainFont ? font : pkgFont);
  }

  public String getNonZeroName(String name)
  {
    return name.length() == 0 ? "a" : name;
  }
  
  public void setMinContentBounds(UBounds minContentBounds)
  {
  	this.minContentBounds = minContentBounds;
  }
  
  public UBounds getMinContentBounds()
  {
  	return minContentBounds;
  }
  
  public boolean getSuppressContents()
  {
  	return suppressContents;
  }
  
  public void setSuppressContents(boolean suppressContents)
  {
  	this.suppressContents = suppressContents;
  }
  
  public UDimension getEntire()
  {
  	return entire;
  }
  
  public void setEntire(UDimension entire)
  {
  	this.entire = entire;
  }

	public void setForceTextToTab(boolean forceTextToTab)
	{
		this.forceTextToTab = forceTextToTab;
	}

	public boolean getForceTextToTab()
	{
		return forceTextToTab;
	}
	
	public boolean getHaveIcon()
	{
		return haveIcon;
	}
	
	public UDimension getMinIconExtent()
	{
		return minIconExtent;
	}
	
	public boolean getDisplayIconOnly()
	{
		return displayIconOnly;
	}

	public void setOwningPackageName(String owningPackageName)
	{
		this.owningPackageName = owningPackageName;
	}

	public String getOwningPackageName()
	{
		return owningPackageName;
	}
}

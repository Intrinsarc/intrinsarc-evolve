/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.jumble.umldiagrams.featurenode;

import java.awt.*;

import com.hopstepjump.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


/**
 * @version 	1.0
 * @author
 */

public final class FeatureSizeInfo
{
	private static final int ICON_WIDTH = 11;
	private static final UDimension TEXT_FROM_ICON_OFFSET = new UDimension(3, 1);
	
	private UPoint topLeft;
	private Font font;
	private String name;
	private boolean linked;
	
	public FeatureSizeInfo(UPoint topLeft, Font font, String name, boolean linked)
	{
		this.topLeft = topLeft;
		this.font = font;
		this.name = name;
		this.linked = linked;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public void setTopLeft(UPoint topLeft)
	{
		this.topLeft = topLeft;
	}
	
	public UPoint getTopLeft()
	{
		return topLeft;
	}
	
	public FeatureSizes makeSizes()
	{
		// set up the sizes
		FeatureSizes sizes = new FeatureSizes();
		
		UDimension offset = new UDimension(0, 1);  // the corners are dragged in by (1,1)
		UDimension offsetForIcon = new UDimension(0, 1);  // bring it up a little higher
		
		// make up the text
		ZText zName = makeZTextForMeasuring(name);  // need to get non-empty bounds to stop a pixel shift
		
		// make the visibility icon
	  sizes.setIconPoint(topLeft.subtract(offsetForIcon));
		
		// set the translation for the text point
		UPoint textPt = topLeft.add(new UDimension(ICON_WIDTH,0)).add(TEXT_FROM_ICON_OFFSET).subtract(offset);
		zName.setTranslation(textPt);
		
		sizes.setTopLeft(topLeft);
		if (name.length() == 0)
			sizes.setText(new ZText());
		else
			sizes.setText(zName);
		sizes.setTextTopLeft(textPt);
		UPoint bottomRight = new UBounds(zName.getBounds()).getBottomRightPoint().add(offset).subtract(offset);
		
		// add a possible link mark
		if (linked)
		{
			UDimension linkMarkDimensions = new UDimension(4, 4);
			double linkMarkSpace = 10;  // width
			UPoint topLeftOfLineMark = new UPoint(bottomRight.getX(), topLeft.getY());
			bottomRight = bottomRight.add(new UDimension(linkMarkSpace, 0));
			
			UPoint linkMarkPoint = new UBounds(topLeftOfLineMark, bottomRight).getMiddlePoint().subtract(linkMarkDimensions.multiply(0.5)).subtract(new UDimension(0, 1));
			sizes.setLinkMarkPoint(linkMarkPoint);
			
			sizes.setLinkMark(makeLinkMark(linkMarkPoint, linkMarkDimensions));
		}
		
		UDimension extent = bottomRight.subtract(topLeft);
		// make sure rounding is performed, or it looks ugly on the screen
		sizes.setEntireBounds(new UBounds(topLeft.round(), extent.round()));
		
		return sizes;
	}
	
	private ZText makeZTextForMeasuring(String text)
  {
		boolean empty = text.length() == 0;
		return makeZText(empty ? "a" : text);
  }
	
  private ZText makeZText(String text)
  {
		ZText zName = new ZText(text);
		if (font != null)
			zName.setFont(font);
		return zName;
  }
	
	private ZNode makeLinkMark(UPoint linkMarkPoint, UDimension size)
	{
		UBounds ell = new UBounds(linkMarkPoint, size);
		ZEllipse ellipse = new ZEllipse(ell.getX(), ell.getY(), ell.getWidth(), ell.getHeight());
		return new ZVisualLeaf(ellipse);
	}
}


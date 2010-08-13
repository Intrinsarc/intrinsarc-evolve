/*
 * Created on Dec 31, 2003 by Andrew McVeigh
 */
package com.intrinsarc.evolve.freeform.grouper;

import java.awt.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.layout.*;

import edu.umd.cs.jazz.*;

/**
 * @author Andrew
 */
public class GrouperSizeInfo implements ContainerSizeInfo
{
	public static final UDimension MIN_CONTENT_DIMENSIONS = new UDimension(50, 50);
	public static final UDimension MIN_OUTER_DIMENSIONS = new UDimension(58, 58);
	public static final UDimension INSET_DIMENSIONS = new UDimension(4, 4);
	
	/** variables indicating the current positioning */
	private UPoint topLeft;
	private UDimension entire;  // only used if not autosized

	/** variables which are effectively commands */
	private boolean autoSized;

	/** variables which affect the new size */
	private Font font;
	private String name;
	private boolean textAtTop;
	private UBounds minContentBounds;

	public GrouperSizeInfo(UPoint topLeft, UDimension entire, boolean autoSized,
												 Font font, String name, UBounds minContentBounds, boolean textAtTop)
	{
		this.topLeft = topLeft;
		this.entire = entire;
		this.autoSized = autoSized;
		this.font = font;
		this.name = name;
		this.minContentBounds = minContentBounds;
		this.textAtTop = textAtTop;
	}

	public GrouperSizes makeActualSizes(boolean moveTopLeftIn)
	{
		// work out the current bounds
		UBounds currentBounds =
			new UBounds(topLeft, autoSized ? new UDimension(0,0) : entire);

		// add the border box
		BorderBox border = new BorderBox(null, INSET_DIMENSIONS, null);
		
		// if we have a name, work out some dimensions
		ZTransformGroup zName = null;
		UDimension zNameDimension = null;
		if (name.length() != 0)
		{
			zName = TextUtilities.makeCentredZTexts(name, font);
			zNameDimension = new UBounds(zName.getBounds()).getDimension();
		}
		
		// make the boxes
		LayoutBox textBox = null;
		if (zName != null)
			textBox = new ExpandingBox("text", zNameDimension, ExpandingBox.EXPAND_NONE, ExpandingBox.X_CENTRE); 
		// place the immovable contents box now
		// the compiler requires the cast for some reasons
		LayoutBox insideContentBox =
			(minContentBounds == null) ? (LayoutBox) new ExpandingBox("contents", MIN_CONTENT_DIMENSIONS, ExpandingBox.EXPAND_BOTH, ExpandingBox.X_CENTRE) :
																	 (LayoutBox) new ImmovableBox("contents", minContentBounds);
		BorderBox contentBox = new BorderBox(null, new UDimension(0,0), insideContentBox);
		contentBox.setForcedMinimumDimensions(MIN_CONTENT_DIMENSIONS); 
		
		// add the boxes now
		if (textAtTop)
			border.addBoxes(textBox, contentBox);
		else
			border.addBoxes(contentBox, textBox);

		// the outer box contains everything
		OuterBox outer = new OuterBox("outer", currentBounds, autoSized, moveTopLeftIn, true, border);

		// set the fields in sizes now...
		GrouperSizes sizes = new GrouperSizes();

		try
		{
			outer.computeLayoutAndSetBoundsInBean(sizes);
		}
		catch (LayoutException ex)
		{
			throw new IllegalStateException("Problem with layout: " + ex + "\nouter =\n" + outer);
		}

		// set up the text slot
		if (zName != null)
		{
			UPoint topLeft = sizes.getText().getPoint();
			zName.setTranslation(topLeft.getX(), topLeft.getY());
			sizes.setZName(zName);
		}
			
		return sizes;
	}

	/**
	 * @return
	 */
	public boolean isAutoSized()
	{
		return autoSized;
	}

	/**
	 * @return
	 */
	public UDimension getEntire()
	{
		return entire;
	}

	/**
	 * @param b
	 */
	public void setAutoSized(boolean b)
	{
		autoSized = b;
	}

	/**
	 * @param dimension
	 */
	public void setEntire(UDimension dimension)
	{
		entire = dimension;
	}

	/**
	 * @param font
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}

	/**
	 * @param bounds
	 */
	public void setMinContentBounds(UBounds bounds)
	{
		minContentBounds = bounds;
	}

	/**
	 * @param point
	 */
	public void setTopLeft(UPoint point)
	{
		topLeft = point;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/*
	 * @see com.intrinsarc.idraw.figures.simplecontainernode.ContainerSizeInfo#getMinContentBounds()
	 */
	public UBounds getMinContentBounds()
	{
		return minContentBounds;
	}

	/*
	 * @see com.intrinsarc.idraw.figures.simplecontainernode.ContainerSizeInfo#makeSizes(boolean)
	 */
	public ContainerCalculatedSizes makeSizes(boolean moveTopLeftIn)
	{
		return makeActualSizes(moveTopLeftIn);
	}
}

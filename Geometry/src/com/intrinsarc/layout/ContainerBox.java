package com.intrinsarc.layout;

import java.util.*;

import com.intrinsarc.geometry.*;

public class ContainerBox implements LayoutBox
{
	private String name;
	private List<LayoutBox> boxes = new ArrayList<LayoutBox>();
	private LayoutPolicy layout;
	private UBounds layoutBounds;
	private UDimension topLeftInset = new UDimension(0,0);
	private UDimension bottomRightInset = new UDimension(0,0);
	private UDimension forcedMinimumDimensions = new UDimension(0,0);
	private boolean ownExpansionType;
	private boolean isXExpandable;
	private boolean isYExpandable;
	
	/**
	 *@param name
	 *@param layout
	 */
	public ContainerBox(String name, LayoutPolicy layout)
	{
		this.name = name;
		this.layout = layout;
	}
	
	/**
	 *@param name
	 *@param layout
	 */
	public ContainerBox(String name, LayoutPolicy layout, boolean isXExpandable, boolean isYExpandable)
	{
		this.name = name;
		this.layout = layout;
		this.ownExpansionType = true;
		this.isXExpandable = isXExpandable;
		this.isYExpandable = isYExpandable;
	}
	
	public void setExpansionCharacteristics(boolean isXExpandable, boolean isYExpandable)
	{
		this.ownExpansionType = true;
		this.isXExpandable = isXExpandable;
		this.isYExpandable = isYExpandable;
	}
	
	/**
	 *@param box
	 */
	public void addBox(LayoutBox box)
	{
		if (box != null)
			boxes.add(box);
	}

	/**
	 *@param box1
	 *@param box2
	 */
	public void addBoxes(LayoutBox box1, LayoutBox box2)
	{
		addBox(box1);
		addBox(box2);
	}

	/**
	 *@param box1
	 *@param box2
	 *@param box3
	 */
	public void addBoxes(LayoutBox box1, LayoutBox box2, LayoutBox box3)
	{
		addBox(box1);
		addBox(box2);
		addBox(box3);
	}

	/**
	 *@param box1
	 *@param box2
	 *@param box3
	 *@param box4
	 */
	public void addBoxes(LayoutBox box1, LayoutBox box2, LayoutBox box3, LayoutBox box4)
	{
		addBox(box1);
		addBox(box2);
		addBox(box3);
		addBox(box4);
	}
	
	/**
	 *@param fullSpace
	 *@see com.intrinsarc.layout.Box#computeLayoutBounds(UBounds)
	 */
	public void computeLayoutBounds(UBounds fullSpace) throws LayoutException
	{
		// this gets the full space
		layoutBounds = fullSpace.addToPoint(topLeftInset).addToExtent(topLeftInset.add(bottomRightInset).negative());
		
		// fit each child into the space
		layout.computeLayoutBounds(name, layoutBounds, boxes);
	}

	/**
	 *@return @see com.intrinsarc.layout.Box#getMinimumDimensions()
	 */
	public UDimension getMinimumDimensions()
	{
		return layout.computeMinimumDimensions(boxes).add(topLeftInset).add(bottomRightInset).maxOfEach(forcedMinimumDimensions);
	}

	/**
	 *@return @see com.intrinsarc.layout.Box#isXExpandable()
	 */
	public boolean isXExpandable()
	{
		if (ownExpansionType)
			return isXExpandable;
		
		// if any child is x expandable, so is this
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			if (box.isXExpandable() == true)
				return true;
		}
		return false;
	}

	/**
	 *@return @see com.intrinsarc.layout.Box#isYExpandable()
	 */
	public boolean isYExpandable()
	{
		if (ownExpansionType)
			return isYExpandable;

		// if any child is x expandable, so is this
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			if (box.isYExpandable() == true)
				return true;
		}
		return false;
	}

	/**
	 *@param setter
	 *@see com.intrinsarc.layout.Box#setBoundsInBean(BeanBoundsSetter)
	 */
	public void setBoundsInBean(BeanBoundsSetter setter) throws LayoutException
	{
		setter.setBounds(name, layoutBounds);
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			box.setBoundsInBean(setter);
		}
	}

	/**
	 * Sets the topLeftInset.
	 * @param topLeftInset The topLeftInset to set
	 */
	public void setTopLeftInset(UDimension topLeftInset)
	{
		this.topLeftInset = topLeftInset;
	}

	/**
	 * Sets the bottomRightInset.
	 * @param bottomRightInset The bottomRightInset to set
	 */
	public void setBottomRightInset(UDimension bottomRightInset)
	{
		this.bottomRightInset = bottomRightInset;
	}

	/**
	 * Sets the layout.
	 * @param layout The layout to set
	 */
	public void setLayout(LayoutPolicy layout)
	{
		this.layout = layout;
	}

	/**
	 * @see com.intrinsarc.layout.Box#getImmovableBox()
	 */
	public LayoutBox getImmovableBox() throws LayoutException
	{
		int count = 0;
    LayoutBox immovable = null;
		
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
      LayoutBox possible = box.getImmovableBox();
			
			if (possible != null)
			{
				if (count++ >= 1)
					throw new LayoutException("Found more than 1 ImmovableBox in network");
				immovable = possible;
			}
		}
		return immovable;
	}

	/**
	 * @see com.intrinsarc.layout.Box#getLayoutBounds()
	 */
	public UBounds getLayoutBounds()
	{
		return layoutBounds;
	}

	/**
	 * @see com.intrinsarc.layout.Box#getLayoutBounds(String)
	 */
	public UBounds getLayoutBounds(String name)
	{
		if (name.equals(this.name))
			return layoutBounds;
			
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			UBounds bounds = box.getLayoutBounds(name);
			if (bounds != null)
				return bounds;
		}
		return null;
	}
	
	/**
	 * Sets the forcedMinimumDimensions.
	 * @param forcedMinimumDimensions The forcedMinimumDimensions to set
	 */
	public void setForcedMinimumDimensions(UDimension forcedMinimumDimensions)
	{
		this.forcedMinimumDimensions = forcedMinimumDimensions;
	}

	/**
	 * @see com.intrinsarc.layout.Box#getName()
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @see com.intrinsarc.layout.Box#toString(int)
	 */
	public String toString(int level)
	{
		StringBuffer buf = new StringBuffer(getType() + " (name=" + name + ", forcedMin=" + forcedMinimumDimensions + ", tlInset=" + topLeftInset + ", brInset=" + bottomRightInset +
																				", layout=" + layout + ")\n");
		level++;
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			buf.append(spacesForLevel(level) + box.toString(level) + "\n");
		}
		return buf.toString();
	}

	private String spacesForLevel(int level)
	{
		StringBuffer levelString = new StringBuffer("");
		for (int lp = 0; lp < level; lp++)
			levelString.append("  ");
		return levelString.toString();
	}

	protected String getType()
	{
		return "ContainerBox";
	}
	
	/**
	 * @see com.intrinsarc.layout.LayoutBox#offsetLayoutBounds(UDimension)
	 */
	public void offsetLayoutBounds(UDimension offset)
	{
		layoutBounds = layoutBounds.addToPoint(offset);
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			box.offsetLayoutBounds(offset);
		}		
	}

}

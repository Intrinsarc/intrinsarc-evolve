package com.intrinsarc.idraw.figures.simplecontainernode;

import com.intrinsarc.geometry.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class SimpleContainerSizes
{
	private UBounds contained;
	private UBounds entireBounds;
	
	public void setContained(UBounds contained)
	{
		this.contained = contained;
	}

	public UBounds getContained()
	{
		return contained;
	}

	public void setEntireBounds(UBounds entireBounds)
	{
		this.entireBounds = entireBounds;
	}

	public UBounds getEntireBounds()
	{
		return entireBounds;
	}

	public UPoint getTopLeft()
	{
		return entireBounds.getTopLeftPoint();
	}
}

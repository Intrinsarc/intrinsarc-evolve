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
public interface ContainerSizeInfo
{
	public UBounds getMinContentBounds();
	public void setTopLeft(UPoint topLeft);
	public ContainerCalculatedSizes makeSizes(boolean moveTopLeftIn);
}

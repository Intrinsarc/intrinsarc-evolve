package com.hopstepjump.idraw.figures.simplecontainernode;

import com.hopstepjump.geometry.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface ContainerSizeCalculator
{
	public ContainerSizeInfo makeInfo(UPoint topLeft, UDimension extent, boolean autoSized);
}

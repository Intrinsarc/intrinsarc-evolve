package com.intrinsarc.idraw.nodefacilities.nodesupport;

import javax.swing.*;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.resizebase.*;

/**
 *
 * (c) Andrew McVeigh 02-Aug-02
 *
 */

public interface BasicNodeAutoSizedFacet extends AutoSizedFacet
{
	public JMenuItem getAutoSizedMenuItem(final ToolCoordinatorFacet coordinator);
	public boolean isAutoSized();
}

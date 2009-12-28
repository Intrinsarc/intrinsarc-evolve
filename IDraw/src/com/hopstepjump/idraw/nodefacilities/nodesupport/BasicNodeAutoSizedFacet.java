package com.hopstepjump.idraw.nodefacilities.nodesupport;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;

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

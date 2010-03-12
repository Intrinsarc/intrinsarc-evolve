package com.hopstepjump.jumble.gui;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 15-Mar-03
 *
 */
public interface ApplicationWindowCoordinatorFacet extends Facet
{
	public void openNewApplicationWindow();
	/** indicates that browser is closing -- returns the number of left open browsers */
	public void closingApplicationWindow(ApplicationWindow window);
	public int getNumberOfOpenApplicationWindows();
	public void exitApplication();
  public void switchRepository();
  public void refreshWindowTitles();
}

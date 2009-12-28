package com.hopstepjump.jumble.repositorybrowser;


import javax.swing.*;

import com.hopstepjump.gem.*;

/**
 * @author Andrew
 */
public interface RepositoryBrowserFacet extends Facet
{
	public JComponent getVisualComponent();
  public void selectFirstElements();
	public void setTreeDivider(double location);
	public void setDetailDivider(double location);
	public void haveClosed();
  public void forceReusable(boolean reusable);
	
	/** for the node viewer to indicate that attributes have changed or are unaltered */
	public void haveAlteredAttributes();
	public void havePristineAttributes();
  public boolean isReusable();
  public void browseTo(Object subject);
  public void refresh();
}

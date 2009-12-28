package com.hopstepjump.idraw.nodefacilities.resizebase;

import com.hopstepjump.gem.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface AutoSizedFacet extends Facet
{
  public Object autoSize(boolean autoSized);
  public void unAutoSize(Object memento);
}

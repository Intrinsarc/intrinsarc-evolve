package com.hopstepjump.jumble.umldiagrams.featurenode;

import com.hopstepjump.gem.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface SuppressFeaturesFacet extends Facet
{
	public Object suppressFeatures(int featureType, boolean suppress);
  public void unSuppressFeatures(Object memento);
}

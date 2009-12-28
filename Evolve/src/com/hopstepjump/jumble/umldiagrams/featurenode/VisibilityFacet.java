/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.jumble.umldiagrams.featurenode;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;

/**
 * @version 	1.0
 * @author
 */
public interface VisibilityFacet extends Facet
{
  public Object setVisibility(VisibilityKind accessType);
  public void unSetVisibility(Object memento);
}

package com.hopstepjump.backbone.generator;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;

public interface ErrorLocatorFacet extends Facet
{
	public void locateErrorInBrowser(NamedElement elementInError);
	public void locateErrorInDiagram(NamedElement elementInError);
}

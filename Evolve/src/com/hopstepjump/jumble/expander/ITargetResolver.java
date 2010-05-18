package com.hopstepjump.jumble.expander;

import org.eclipse.uml2.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

public interface ITargetResolver
{
	public Element resolveTarget(Element relationship);
	public UPoint determineTargetLocation(Element target, int index);
	public NodeCreateFacet getNodeCreator(Element target);
}

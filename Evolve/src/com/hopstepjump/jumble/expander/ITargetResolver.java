package com.hopstepjump.jumble.expander;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

public interface ITargetResolver
{
	public List<Element> resolveTargets(Element relationship);
	public UPoint determineTargetLocation(Element target, int index);
	public NodeCreateFacet getNodeCreator(Element target);
}

package com.hopstepjump.backbonegenerator.hardcoded.flattened;

import java.util.*;

import com.hopstepjump.backbone.nodes.simple.*;
import com.hopstepjump.backbone.runtime.implementation.*;
import com.hopstepjump.backbonegenerator.hardcoded.common.*;

public class ExpandedTypeGWTDispatcher implements ExpandedTypeGenerator
{
	private Set<String> types = new LinkedHashSet<String>();
	
	public String constructClasses()
	{
		return ExpandedTypeGWTTerminal.constructClasses(types, "StateDispatcher", "com.hopstepjump.backbone.runtime.api.StateDispatcherMarker");
	}

	public String formConstructionAndRemember(BBSimpleElementRegistry registry, BBSimplePart part, String partName, List<String> profile)
	{
		return ExpandedTypeGWTTerminal.formConstruction(
				registry,
				StateDispatcher.class.getName(),
				part,
				partName,
				profile,
				types,
				"com.hopstepjump.backbone.runtime.api.IStateDispatcher",
				"IEvent",
				"StateDispatcher");
	}
}

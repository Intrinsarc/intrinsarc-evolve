package com.hopstepjump.backbone.generator.hardcoded.flattened;

import java.util.*;

import com.hopstepjump.backbone.generator.hardcoded.common.*;
import com.hopstepjump.backbone.nodes.simple.*;
import com.hopstepjump.backbone.runtime.implementation.*;

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

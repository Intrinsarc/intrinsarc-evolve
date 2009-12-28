package com.hopstepjump.backbonegenerator.hardcoded.common;

import java.util.*;

import com.hopstepjump.backbone.nodes.simple.*;

public interface ExpandedTypeGenerator
{
	String formConstructionAndRemember(BBSimpleElementRegistry registry, BBSimplePart part, String partName, List<String> profile);
	String constructClasses();
}

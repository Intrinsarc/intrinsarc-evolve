package com.intrinsarc.backbone.generator.hardcoded.common;

import java.util.*;

import com.intrinsarc.backbone.nodes.simple.*;

public interface ExpandedTypeGenerator
{
	String formConstructionAndRemember(BBSimpleElementRegistry registry, BBSimplePart part, String partName, List<String> profile);
	String constructClasses();
}

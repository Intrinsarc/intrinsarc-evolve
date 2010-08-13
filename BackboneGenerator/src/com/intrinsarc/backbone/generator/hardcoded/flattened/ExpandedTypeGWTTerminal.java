package com.intrinsarc.backbone.generator.hardcoded.flattened;

import java.util.*;

import com.intrinsarc.backbone.generator.hardcoded.common.*;
import com.intrinsarc.backbone.nodes.simple.*;
import com.intrinsarc.backbone.runtime.implementation.*;

public class ExpandedTypeGWTTerminal implements ExpandedTypeGenerator
{
	public static final Object GWT_PROFILE = "gwt";
	private Set<String> types = new LinkedHashSet<String>();
	
	public String constructClasses()
	{
		return constructClasses(types, "Terminal", "com.intrinsarc.backbone.runtime.api.TerminalMarker");
	}

	public static String constructClasses(Set<String> types, String suffix, String markerInterface)
	{
		StringBuilder s = new StringBuilder();
		for (String type : types)
		{
			s.append("class " + makeAcceptable(type) + "_" + suffix + " extends " + markerInterface + "<" + type + "> {}\n");
		}
		return s.toString();		
	}
	
	private static String makeAcceptable(String type)
	{
		return type.replace(".", "_");
	}

	public String formConstructionAndRemember(BBSimpleElementRegistry registry, BBSimplePart part, String partName, List<String> profile)
	{
		return formConstruction(registry, Terminal.class.getName(), part, partName, profile, types, "com.intrinsarc.backbone.runtime.api.IStateTerminalComponent", "ITransition", "Terminal");
	}
	
	public static String formConstruction(
			BBSimpleElementRegistry registry,
			String matchingClassName,
			BBSimplePart part,
			String partName,
			List<String> profile,
			Set<String> types,
			String ifaceName,
			String ifaceUUID,
			String suffix)
	{
		String impl = part.getType().getImplementationClassName();
		
		// only return something if this matches the start type and		
		if (impl.equals(matchingClassName) && profile.contains(GWT_PROFILE))
		{
			// extract the generic type
			String genericType = getRequiredImplementation(registry, part, ifaceUUID);
			types.add(genericType);
			String extendedGenericType = makeAcceptable(genericType + "_" + suffix);
			return "  private " + ifaceName + " " + partName + " = (" + ifaceName + ") com.google.gwt.core.client.GWT.create(" + extendedGenericType + ".class);";
		}
		return null;
	}
	
	private static String getRequiredImplementation(BBSimpleElementRegistry registry, BBSimplePart part, String provInterfaceUuid)
	{
		for (BBSimplePort p : part.getType().getPorts())
		{
			if (p.getRequires().size() == 0 && p.getProvides().size() == 1 && p.getProvides().get(0).getUuid().equals(provInterfaceUuid))
			{
				BBSimpleInterface iface = p.getProvides().get(0);
				return part.getRequiredImplementationNameForProvided(registry, p, iface);
			}
		}
		return null;
	}
}

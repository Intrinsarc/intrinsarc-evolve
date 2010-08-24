package com.intrinsarc.backbone.generator.hardcoded.flattened;

import static com.intrinsarc.backbone.generator.hardcoded.common.WriterHelper.*;

import java.io.*;
import java.util.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.backbone.generator.*;
import com.intrinsarc.backbone.generator.hardcoded.common.*;
import com.intrinsarc.backbone.nodes.simple.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.repositorybase.*;

/**
 * writes out hardcoded implementation representing a particular component at a particular perspective
 * @author andrew
 */

public class FlatteningHardcoder
{
	public int writeFlattened(BackboneGenerationChoice choice) throws BackboneGenerationException, VariableNotFoundException, BBBadRunPointException
	{
    List<DEStratum> ordered = choice.extractRelatedStrata();
    List<String> profile = choice.getGenerationProfile();
		org.eclipse.uml2.Package perspectivePkg = (org.eclipse.uml2.Package) ordered.get(0).getRepositoryObject();
		String prefix = StereotypeUtilities.extractStringProperty(perspectivePkg, CommonRepositoryFunctions.COMPOSITE_PACKAGE);
  	if (prefix == null || prefix.trim().length() == 0)
  		throw new BackboneGenerationException("Set " + CommonRepositoryFunctions.COMPOSITE_PACKAGE + " on the stratum", null);

		PreferencesFacet prefs = GlobalPreferences.preferences;
		
		String runParams[] = choice.extractRunParameters();
		String stratumName = runParams[0];
		DEStratum stratum = findNamedStratum(stratumName, new StringTokenizer(stratumName, "::"), GlobalDeltaEngine.engine.getRoot()); 
		String compName = runParams[1];
		DEComponent comp = findNamedComponent(stratum, compName);
		
		// flatten so we can get the factories
		DEStratum perspective = choice.getSingleStratum();
		BBSimpleElementRegistry registry = new BBSimpleElementRegistry(perspective, comp);
		BBSimpleComponent simple = new BBSimpleComponent(registry, comp);
		simple.flatten(registry);
		
		// see if we can get the composite full name
		UniqueNamer namer = new UniqueNamer();
  	String pkgName = prefix;
		String fullClassName = pkgName + "." + namer.getUniqueName(comp) + "Factory";
		
		// get the directory and remove it before writing
  	String name = extractFolder(prefs, perspective, perspective, 1, null, false);
  	File javaBase = new File(expandVariables(prefs, null, name));
  	
		// start at the top and make all the factories
		String directory = fullClassName.replace('.', '/');
		File realFile = new File(javaBase, directory + ".java");
		File tempFile = new File(javaBase, directory + ".java.temp");
		
		int index = fullClassName.lastIndexOf('.');
		String className = fullClassName;
		if (index != -1)
		{
			className = fullClassName.substring(index + 1);
			pkgName = fullClassName.substring(0, index);
		}
		else
			pkgName = null;

		realFile.getParentFile().mkdirs();
		
		// register expanders for different profiles
		ExpandedTypeManager expander = new ExpandedTypeManager(registry, profile);
		expander.addGenerator(new ExpandedTypeGWTTerminal());
		expander.addGenerator(new ExpandedTypeGWTDispatcher());
		
		return new HardcodedFactoryWriter().writeHardcodedFactory(registry, realFile, tempFile, className, pkgName, simple, 0, namer, expander);
	}
	
	private static DEComponent findNamedComponent(DEStratum runStratum, String componentName) throws BBBadRunPointException
	{
		for (DEElement elem : runStratum.getChildElements())
		{
			if (elem.asComponent() != null && elem.asComponent().getName().equals(componentName))
				return elem.asComponent(); 
		}
		throw new BBBadRunPointException("Cannot find component: " + componentName);
	}

	private static DEStratum findNamedStratum(String fullName, StringTokenizer tokens, DEStratum start) throws BBBadRunPointException
	{
		// are we the match?
		if (!tokens.hasMoreTokens())
			return start;

		String next = tokens.nextToken();
		for (DEStratum child : start.getDirectlyNestedPackages())
		{
			if (child.getName().equals(next))
			{
				DEStratum match = findNamedStratum(fullName, tokens, child);
				if (match != null)
					return match;
			}
		}
		// if we got here, we couldn't find a named match
		throw new BBBadRunPointException("Cannot find stratum: " + fullName);
	}

}

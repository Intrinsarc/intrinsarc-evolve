package com.intrinsarc.backbone.generator.hardcoded.composite;

import static com.intrinsarc.backbone.generator.hardcoded.common.WriterHelper.*;

import java.io.*;
import java.util.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.backbone.generator.*;
import com.intrinsarc.backbone.generator.hardcoded.common.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.repositorybase.*;

public class CompositeHardcoder
{
	public int writeComposites(BackboneGenerationChoice choice) throws BackboneGenerationException, VariableNotFoundException, BBBadRunPointException
	{
		PreferencesFacet prefs = GlobalPreferences.preferences;

		if (choice.getSingleStratum() == null)
			throw new BBBadRunPointException("A single top stratum muse be tagged for hardcoded generation");
		DEStratum perspective = choice.getSingleStratum(); 
		org.eclipse.uml2.Package perspectivePkg = (org.eclipse.uml2.Package) perspective.getRepositoryObject();
		
		int count = 0;
		String runParams[] = choice.extractRunParameters();
		String stratumName = runParams[0];
		DEStratum stratum = findNamedStratum(stratumName, new StringTokenizer(stratumName, "::"), GlobalDeltaEngine.engine.getRoot()); 
		String compName = runParams[1];
		DEComponent comp = findNamedComponent(stratum, compName);
		
  	// get the composite package prefix -- if not there, then use "hardcoded"
  	String name = extractFolder(prefs, stratum, stratum, 1, null, false);
  	File javaBase = new File(expandVariables(prefs, null, name));
  	String prefix = StereotypeUtilities.extractStringProperty(perspectivePkg, CommonRepositoryFunctions.COMPOSITE_PACKAGE);
  	if (prefix == null || prefix.trim().length() == 0)
  		throw new BackboneGenerationException("The " + CommonRepositoryFunctions.COMPOSITE_PACKAGE + " property must be set on the stratum to generate a hardcoded factory", null);
  	File base = new File(javaBase, prefix.replace('.', '/'));
  	base.mkdirs();
  	
  	// descend down through the specified composite and create the composites
  	Set<DEComponent> created = new HashSet<DEComponent>();
  	Set<DEComponent> toCreate = new LinkedHashSet<DEComponent>();
  	toCreate.add(comp);
  	
  	CompositeUniqueNamer pkgNamer = new CompositeUniqueNamer();
		FileTracker tracker = new FileTracker(".java");
		tracker.recordExisting(base);
  	while (!toCreate.isEmpty())
  	{
  		DEComponent next = toCreate.iterator().next();
  		toCreate.remove(next);
  		created.add(next);
  		// write the composite
  		new HardcodedCompositeWriter(pkgNamer, perspective, next, base, prefix, created, toCreate).writeComposite(tracker);
  		count++;
  	}
  	
  	// delete any unwanted files
  	tracker.deleteUnwantedFiles();
  	tracker.deleteEmptyDirectories(base);
  	return count;
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

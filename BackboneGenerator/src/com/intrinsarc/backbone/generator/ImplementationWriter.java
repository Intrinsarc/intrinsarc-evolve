package com.intrinsarc.backbone.generator;

import static com.intrinsarc.backbone.generator.hardcoded.common.WriterHelper.*;

import java.io.*;
import java.util.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.environment.*;

/**
 * write out (and refresh) any implementation leaves and interfaces
 * @author andrew
 *
 */
public class ImplementationWriter
{
  public int writeImplementation(BackboneGenerationChoice choice) throws BackboneGenerationException, VariableNotFoundException
  {
    List<DEStratum> ordered = choice.extractRelatedStrata();
		PreferencesFacet prefs = GlobalPreferences.preferences;

		int count = 0;
  	for (DEStratum stratum : ordered)
    {
      if (!isInBackboneBase(stratum))
      {
        // if this has a classpath, use it
      	boolean directClasspath[] = new boolean[1];
      	String nameClasspath = extractFolder(prefs, stratum, stratum, 2, directClasspath, false);
      	
      	// make sure we replace any file separators
      	nameClasspath = replaceSeparators(nameClasspath);
      	
        if (!extractSuppress(prefs, stratum, false))
        {
        	String name = extractFolder(prefs, stratum, stratum, 1, null, true);
	        File javaBase = new File(expandVariables(prefs, null, name));
          count += generateInterfaceSource(stratum, javaBase);
          count += generateLeafSource(stratum, javaBase);
        }
      }
    }
  	return count;
  }
  
  private int generateInterfaceSource(DEStratum stratum, File codeBase) throws BackboneGenerationException
  {
    codeBase.mkdirs();
    return generatePossibleImplementationInterfaces(stratum, codeBase);
  }
  
  private int generateLeafSource(DEStratum stratum, File codeBase) throws BackboneGenerationException
  {
    codeBase.mkdirs();
    return refreshLeafImplementations(stratum, codeBase);
  }
  
  
  private int generatePossibleImplementationInterfaces(DEStratum stratum, File base) throws BackboneGenerationException
  {
    // start with this package
  	int count = 0;
    for (DEElement element : stratum.getChildElements())
    {
      // if this is a leaf, refresh the code
      DEInterface iface = element.asInterface();
      if (iface != null && !iface.isBean(iface.getHomeStratum()))
      {
      	// possibly repoint to what we are substituting
      	if (iface.isReplacement())
      		iface = (DEInterface) iface.getReplaces().iterator().next();
      	
        InterfaceImplementationMaker maker = new InterfaceImplementationMaker(base, stratum, iface);
      	if (maker.possiblyMakeDefinition())
      		count++;
      }
    }
    return count;
  }
  
  private int refreshLeafImplementations(DEStratum stratum, File base) throws BackboneGenerationException
  {
    // start with this package
  	int count = 0;
    for (DEElement element : stratum.getChildElements())
      if (refreshLeaf(stratum, base, element))
      		count++;
    return count;
  }

	private boolean refreshLeaf(DEStratum stratum, File base, DEElement element) throws BackboneGenerationException
	{
		// if this is a leaf, refresh the code
		DEStratum home = element.getHomeStratum();
		DEComponent comp = element.asComponent();
		if (comp != null &&
				comp.isLeaf(home) &&
				!comp.isPlaceholder(home) &&
				!comp.isBean(home) &&
				!comp.isRawAbstract() &&
				comp.getComponentKind() != ComponentKindEnum.STEREOTYPE)
		{
			// if this is a substituter, take what it substitutes...
			if (comp.isReplacement())
				comp = (DEComponent) comp.getReplaces().iterator().next();
			LeafImplementationRefresher refresher = new LeafImplementationRefresher(base, stratum, comp);
			return refresher.refreshLeafCode();
		}
		return false;
	}
}

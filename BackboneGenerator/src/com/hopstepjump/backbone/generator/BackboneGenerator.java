package com.hopstepjump.backbone.generator;

import java.io.*;
import java.util.*;

import org.eclipse.uml2.Package;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.generator.hardcoded.common.*;
import com.hopstepjump.backbone.generator.hardcoded.composite.*;
import com.hopstepjump.backbone.generator.hardcoded.flattened.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.environment.*;

public class BackboneGenerator
{
  private BackboneGeneratorFacet generatorFacet = new BackboneGeneratorFacetImpl();
  
  public BackboneGenerator()
  {
  }
  
  public BackboneGeneratorFacet getBackboneGeneratorFacet()
  {
    return generatorFacet;
  }
  
  private class BackboneGeneratorFacetImpl implements BackboneGeneratorFacet
  {
    public File generate(BackboneGenerationChoice choice, List<String> classpaths, List<String> untranslatedClasspaths, boolean mixed, int modified[]) throws BackboneGenerationException
    {
    	try
			{
    		modified[0] = 0;
    		if (mixed)
    		{
    			File loadFile = new BackboneWriter().writeBackbone(choice, classpaths, untranslatedClasspaths);
    			modified[0] += new ImplementationWriter().writeImplementation(choice);
    			return loadFile;
    		}
    		else
    		{
      		List<String> profile = choice.getGenerationProfile();
  				modified[0] = new ImplementationWriter().writeImplementation(choice);
    			if (profile.contains("composite"))
    			{
    				modified[0] += new CompositeHardcoder().writeComposites(choice);
    			}
    			else
    			{
    				new FlatteningHardcoder().writeFlattened(choice);
    				modified[0] = 1;
    			}
    			return null;
    		}
			}
    	catch (VariableNotFoundException ex)
			{
    		throw new BackboneGenerationException(ex.getMessage(), null);
			}
    	catch (BBBadRunPointException ex)
			{
    		throw new BackboneGenerationException(ex.getMessage(), null);
			}
    }  	
  }
  
	public static List<String> getClasspathsOfStratum(Package pkg) throws BackboneGenerationException, VariableNotFoundException
	{
		DEStratum stratum = GlobalDeltaEngine.engine.locateObject(pkg).asStratum();
		// get the classpath from the stratum
		PreferencesFacet prefs = GlobalPreferences.preferences;
  	boolean directClasspath[] = new boolean[1];
  	String classpath = WriterHelper.extractFolder(prefs, stratum, stratum, 2, directClasspath, true);					
		String expanded = WriterHelper.expandVariables(prefs, null, classpath);
		expanded = WriterHelper.replaceSeparators(expanded);
		StringTokenizer tok = new StringTokenizer(expanded, File.pathSeparator);
		List<String> paths = new ArrayList<String>();
		while (tok.hasMoreTokens())
		{
			String cp = tok.nextToken();
			if (cp.startsWith("\"") && cp.endsWith("\"") && cp.length() >= 2)
				cp = cp.substring(1, cp.length() - 1);
			paths.add(cp);
		}
		return paths;
	}	
}

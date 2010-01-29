package com.hopstepjump.backbonegenerator;

import static com.hopstepjump.backbonegenerator.hardcoded.common.WriterHelper.expandVariables;
import static com.hopstepjump.backbonegenerator.hardcoded.common.WriterHelper.extractFolder;
import static com.hopstepjump.backbonegenerator.hardcoded.common.WriterHelper.extractSuppress;
import static com.hopstepjump.backbonegenerator.hardcoded.common.WriterHelper.isInBackboneBase;
import static com.hopstepjump.backbonegenerator.hardcoded.common.WriterHelper.replaceSeparators;

import java.io.*;
import java.util.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.environment.*;

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
      StratumCodeGenerator generator = new StratumCodeGenerator(stratum);

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
          count += generator.generateInterfaceSource(javaBase);
          count += generator.generateLeafSource(javaBase);
        }
      }
    }
  	return count;
  }
}

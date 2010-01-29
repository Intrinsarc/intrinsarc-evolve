package com.hopstepjump.backbonegenerator;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.uml2deltaengine.converters.*;
import com.thoughtworks.xstream.*;

import static com.hopstepjump.backbonegenerator.hardcoded.common.WriterHelper.*;

/**
 * write out the backbone for the strata
 * @author andrew
 *
 */
public class BackboneWriter
{
  public static final Preference GENERATION_DIR_PREF = new Preference("Backbone", "Base backbone source folder", new PersistentProperty("$BB/base"));
  public static final Preference BB_CLASS_PATH_PREF = new Preference("Backbone", "Base backbone classpath", new PersistentProperty("$EVOLVE/runtime.jar"));
  public static final Preference BB_JAVA_CMD_PREF = new Preference("Backbone", "Java command", new PersistentProperty("java"));
  public static final Preference BB_WRITE_BASE_PREF = new Preference("Backbone", "Generate Backbone base", new PersistentProperty(true));
	
  public static void registerPreferenceSlots()
  {
    // declare the preference types
    PreferenceType stringType = new PreferenceTypeString();
    PreferenceType directoryType = new PreferenceTypeDirectory();
    
    // add the slots for backbone
    GlobalPreferences.preferences.addPreferenceSlot(
        GENERATION_DIR_PREF,
        directoryType,
        "The folder for the Backbone source for the base stratum.  It will have 'backbone' added to the path.");
    GlobalPreferences.preferences.addPreferenceSlot(
        BB_WRITE_BASE_PREF,
        new PreferenceTypeBoolean(),
        "Whether the Backbone base will be (re-)generated.");
    GlobalPreferences.preferences.addPreferenceSlot(
    		BB_CLASS_PATH_PREF,
        stringType,
        "The classpath for the Backbone base");
    GlobalPreferences.preferences.addPreferenceSlot(
    		BB_JAVA_CMD_PREF,
        stringType,
        "The command used to run java.");
  }
 
  public File writeBackbone(BackboneGenerationChoice choice, List<String> classpaths, List<String> untranslatedClasspaths) throws BackboneGenerationException, VariableNotFoundException
  {
    List<DEStratum> ordered = choice.extractRelatedStrata();

    XStream x = new XStream();
    UML2XStreamConverters.registerConverters(x);
    BBXStreamConverters.registerConverters(x);
		PreferencesFacet prefs = GlobalPreferences.preferences;
    Set<String> referenced = new LinkedHashSet<String>();
    BBLoadList loadListUntranslated = new BBLoadList();
    File topmost = null;

    String backboneGenerationDir = null;
    try
    {
  		backboneGenerationDir = prefs.getPreference(GENERATION_DIR_PREF).asString() + "/backbone";
    }
		catch (PreferenceNotFoundException e)
		{
      throw new BackboneGenerationException("Cannot find Base backbone source folder or classpath preference", null);
		}
		
		// delete any directories first, as putting this in the main generation logic seems to lead to a race condition
		for (DEStratum stratum : ordered)
    {
      if (!isInBackboneBase(stratum))
      {
      	if (!extractSuppress(prefs, stratum, true))
      	{
        	boolean direct[] = new boolean[1];
	        String name = extractFolder(prefs, stratum, stratum, 0, direct, false);
        	String expanded = expandVariables(prefs, referenced, name); 
      		// delete the directory
      		new CommonRepositoryFunctions().deleteDir(new File(expanded));
      	}
    		
      }
      else
      {
      	boolean write = prefs.getRawPreference(BB_WRITE_BASE_PREF).asString().equals("true");
      	if (write)
        {
					String name = backboneGenerationDir;
      		if (!isBackboneBase(stratum))
      			name += "/" + stratum.getName();
      		String expanded = expandVariables(prefs, referenced, name);
        	File bbBase = new File(expanded);
	        new CommonRepositoryFunctions().deleteDir(bbBase);
        }
			}
    }

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
      	
    		if (untranslatedClasspaths != null && !untranslatedClasspaths.contains(nameClasspath))
    			untranslatedClasspaths.add(nameClasspath);
    		String expandedClasspath = expandVariables(prefs, null, nameClasspath); 
    		if (classpaths != null && !classpaths.contains(expandedClasspath))
    			classpaths.add(expandedClasspath);
      	
      	boolean direct[] = new boolean[1];
        String name = extractFolder(prefs, stratum, stratum, 0, direct, false);
      	loadListUntranslated.settable_getStrataDirectories().add(new BBStratumDirectory(name, stratum.getFullyQualifiedName()));
      	String expanded = expandVariables(prefs, referenced, name); 

      	if (!extractSuppress(prefs, stratum, true))
        {
	        try
	        {
		        File bbBase = new File(expanded);
	        	if (topmost == null)
	        		topmost = new File(expanded);
	          generator.generateBackboneSource(getPeerUniqueName(stratum), bbBase, x);
	        }
	        catch (IOException e)
	        {
	          throw new BackboneGenerationException("IOException while generating stratum " + stratum.getName(), null);
	        }
        }	        
      }
      else
      {
				try
				{
      		String classpath = prefs.getPreference(BB_CLASS_PATH_PREF).asString();
      		
      		// make sure we replace any file separators
      		classpath = replaceSeparators(classpath);
      		
      		if (untranslatedClasspaths != null && !untranslatedClasspaths.contains(classpath))
      			untranslatedClasspaths.add(classpath);
      		String expandedClasspath = expandVariables(prefs, null, classpath); 
      		if (classpaths != null && !classpaths.contains(expandedClasspath))
      			classpaths.add(expandedClasspath);

					String name = backboneGenerationDir;
      		if (!isBackboneBase(stratum))
      			name += "/" + stratum.getName();
      		String expanded = expandVariables(prefs, referenced, name);
        	loadListUntranslated.settable_getStrataDirectories().add(new BBStratumDirectory(name, stratum.getFullyQualifiedName()));
        	
        	boolean write = prefs.getRawPreference(BB_WRITE_BASE_PREF).asString().equals("true");
        	if (write)
	        {
		        try
		        {
		        	File bbBase = new File(expanded);
		          generator.generateBackboneSource(getPeerUniqueName(stratum), bbBase, x);
		        }
		        catch (IOException e)
		        {
		          throw new BackboneGenerationException("IOException while generating stratum " + stratum.getName(), null);
		        }
	        }
				}
				catch (PreferenceNotFoundException e)
				{
          throw new BackboneGenerationException("Cannot find Base backbone source folder or classpath preference", null);
				}
      }
    }
    
    // write the load list to the topmost area
    if (topmost == null)
      throw new BackboneGenerationException("Some backbone must be generated for the load list to be written", null);
    
    // add any referenced variables to the load list
    for (String name : referenced)
    	loadListUntranslated.settable_getVariables().add(new BBVariableDefinition(name, prefs.getRawVariableValue(name)));
    
    String name = "system.loadlist";
    write(topmost, name, x.toXML(loadListUntranslated));
    return new File(topmost, name);
  }

}

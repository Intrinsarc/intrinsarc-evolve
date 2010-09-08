package com.intrinsarc.backbone.generator;

import static com.intrinsarc.backbone.generator.hardcoded.common.WriterHelper.*;

import java.io.*;
import java.util.*;

import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.backbone.nodes.insides.*;
import com.intrinsarc.backbone.nodes.lazy.*;
import com.intrinsarc.backbone.printers.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

/**
 * write out the backbone for the strata
 * @author andrew
 *
 */
public class BackboneWriter
{
  public static final Preference GENERATION_DIR_PREF = new Preference("Backbone", "Base backbone source folder", new PersistentProperty("$BB/base"));
  public static final Preference BB_CLASS_PATH_PREF = new Preference("Backbone", "Base backbone classpath", new PersistentProperty("$EVOLVE/common/backbone.jar"));
  public static Preference BB_JAVA_CMD_PREF;
  public static final Preference BB_WRITE_BASE_PREF = new Preference("Backbone", "Generate Backbone base", new PersistentProperty(true));
  public static final Preference BB_WRITE_BACKUPS = new Preference("Backbone", "Backup leaf implementation files before writing", new PersistentProperty(true));
	
  public static void registerPreferenceSlots(String javaDir)
  {
    // declare the preference types
    PreferenceType stringType = new PreferenceTypeString();
    PreferenceType booleanType = new PreferenceTypeBoolean();
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
    
    String separator = System.getProperty("file.separator", "/");
    BB_JAVA_CMD_PREF = new Preference("Backbone", "Java command",
    		new PersistentProperty((javaDir == null ? "" : javaDir + separator) + "java")); 
    GlobalPreferences.preferences.addPreferenceSlot(
    		BB_JAVA_CMD_PREF,
        stringType,
        "The command used to run java.");
    GlobalPreferences.preferences.addPreferenceSlot(
    		BB_WRITE_BACKUPS,
        booleanType,
        "Should we create backup files before refreshing leaf implementations?");
  }
 
  public File writeBackbone(BackboneGenerationChoice choice, List<String> classpaths, List<String> untranslatedClasspaths) throws BackboneGenerationException, VariableNotFoundException
  {
    List<DEStratum> ordered = choice.extractRelatedStrata();

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
      		FileUtilities.deleteDir(new File(expanded));
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
	        FileUtilities.deleteDir(bbBase);
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
	          generator.generateBackboneSource(getPeerUniqueName(stratum), bbBase);
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
		          generator.generateBackboneSource(getPeerUniqueName(stratum), bbBase);
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
    write(topmost, name, new LoadListPrinter(loadListUntranslated, BackbonePrinterMode.REAL_UUIDS).makePrintString(""));
    return new File(topmost, name);
  }

}

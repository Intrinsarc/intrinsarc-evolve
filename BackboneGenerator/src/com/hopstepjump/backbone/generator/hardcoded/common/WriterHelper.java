package com.hopstepjump.backbone.generator.hardcoded.common;

import java.io.*;
import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.backbone.generator.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.repositorybase.*;

public class WriterHelper
{
	/** is this part of the backbone base distribution? */
	public static boolean isInBackboneBase(DEStratum stratum)
	{
		while (stratum != null)
		{
			if (isBackboneBase(stratum))
				return true;
			stratum = stratum.getHomeStratum();
		}
		return false; 
	}
	
	public static boolean isBackboneBase(DEStratum stratum)
	{
		return stratum.getUuid().equals(CommonRepositoryFunctions.BACKBONE_STRATUM_NAME);
	}
	
	public static String getPeerUniqueName(DEStratum start)
	{
		DEStratum parent = start.getHomeStratum();
		String name = makeAcceptable(start.getName());
		if (name.length() == 0)
			return start.getUuid();
		for (DEStratum p : parent.getDirectlyNestedPackages())
		{
			if (p != start)
			{
				String other = makeAcceptable(p.getName());
				if (name.equals(other))
					return name + "-" + start.getUuid();
			}
		}
		return name;
	}

	private static String makeAcceptable(String name)
	{
		StringBuilder b = new StringBuilder();
		int size = name.length();
		name = name.toLowerCase();
		for (int lp = 0; lp < size; lp++)
		{
			char c = name.charAt(lp);
			if (Character.isJavaIdentifierPart(c) || c == '-')
				b.append(c);
		}
		return b.toString();
	}
	
	public static String replaceSeparators(String path)
	{
		if (path != null)
			return path.replace(':', File.pathSeparatorChar).replace(';', File.pathSeparatorChar);
		return null;
	}

	public static void write(File directory, String fileName, String str) throws BackboneGenerationException
	{
    BufferedWriter writer = null;
    File file = new File(directory, fileName);
    try
    {
      writer = new BufferedWriter(new FileWriter(file));

      // write the XStream representation to the file
	    writer.append(str);
    }
    catch (IOException e)
    {
      throw new BackboneGenerationException("Cannot write file to " + file, null);
    }
    finally
    {
    	if (writer != null)
				try
				{
					writer.close();
				}
    		catch (IOException e)
				{
				}
    }
  }

	public static boolean extractSuppress(PreferencesFacet prefs, DEStratum stratum, boolean isBackbone)
	{
		// travel upwards, looking for the property
		// if we get to the top, throw an exception
		while (stratum != null)
		{
			boolean tri = isBackbone ?
			    StereotypeUtilities.extractBooleanProperty((Element) stratum.getRepositoryObject(), CommonRepositoryFunctions.SUPPRESS_BACKBONE_SOURCE) :
			      StereotypeUtilities.extractBooleanProperty((Element) stratum.getRepositoryObject(), CommonRepositoryFunctions.SUPPRESS_JAVA_SOURCE);				      
			if (tri == true)
				return tri;
			stratum = stratum.getHomeStratum();
		}
		return false;
	}
	
	public static String expandVariables(PreferencesFacet variables, Set<String> referenced, String text) throws VariableNotFoundException
	{
		if (referenced == null)
			return variables.expandVariables(text);
		else
			return variables.expandVariables(text, referenced);
	}
	
	public static String extractFolder(PreferencesFacet prefs, DEStratum stratum, DEStratum start, int folderType, boolean[] direct, boolean dontAddStrataName) throws BackboneGenerationException
	{
		if (stratum == null)
			throw new BackboneGenerationException("Problem finding " + new String[]{"Backbone", "Java", "classpath"}[folderType] + " base folder for " + start.getFullyQualifiedName(), start);

		// travel upwards, looking for the property
		// if we get to the top, throw an exception
		String value;
		switch (folderType)
		{
		case 0:
			{
				String bb = StereotypeUtilities.extractStringProperty((Element) stratum.getRepositoryObject(), CommonRepositoryFunctions.BACKBONE_SOURCE_FOLDER);
				if (bb == null)
					value = null;
				else
					value = bb + "/" + stratum.getName();
			}
			break;
		case 1:
      value = StereotypeUtilities.extractStringProperty((Element) stratum.getRepositoryObject(), CommonRepositoryFunctions.JAVA_SOURCE_FOLDER);
			break;
		default:
      value = StereotypeUtilities.extractStringProperty((Element) stratum.getRepositoryObject(), CommonRepositoryFunctions.BACKBONE_CLASSPATH);
			break;
		}
		
		if (value != null && value.trim().length() > 0)
		{
			if (direct != null)
				direct[0] = true;
			
			// could be relative?
			if (value.startsWith("."))
				return extractFolder(prefs, stratum.getHomeStratum(), start, folderType, null, dontAddStrataName) + "/" + value;
			return value;
		}
		else
		{
			if (dontAddStrataName)
				return extractFolder(prefs, stratum.getHomeStratum(), start, folderType, null, dontAddStrataName);
			else
				return extractFolder(prefs, stratum.getHomeStratum(), start, folderType, null, dontAddStrataName) + "/" +  getPeerUniqueName(stratum);
		}
	}
	
  public static String upper(String str)
  {
    if (str.length() > 1)
      return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    return str.toUpperCase();
  }
  
	public static String getAfterLastDot(String iname)
	{
		int index = iname.lastIndexOf('.');
		if (index == -1 || index + 1 > iname.length() - 1)
			return iname;
		return iname.substring(index + 1);
	}
	
	public static String makeSingular(String name)
	{
		// remove a possible 's'
		if (name.endsWith("s") && name.length() > 1)
			return name.substring(0, name.length() - 1);
		return name;
	}
}
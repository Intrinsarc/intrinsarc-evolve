package com.intrinsarc.backbone.generator;

import java.io.*;
import java.util.*;

import com.intrinsarc.idraw.environment.*;

public abstract class ImplementationRefresher
{
	private static final String START_GENERATED_CODE = "// start generated code";
	private static final String END_GENERATED_CODE   = "// end generated code";
	private File generationDir;
	private String fullClassName;

	/** fill in the methods to make a concrete refresher */
	public abstract void makePreamble(BufferedWriter writer) throws IOException;
	public abstract Map<String, String> makeGeneratedCode(BufferedWriter writer) throws IOException;
	public abstract void makePostamble(BufferedWriter writer, Map<String, String> newTypes) throws IOException;
	
	public ImplementationRefresher(File generationDir, String fullClassName)
	{
		this.generationDir = generationDir;
		// guaranteed to be a single impl class, by the well-formedness rules
		this.fullClassName = fullClassName;
	}
	
	public String getFullClassName()
	{
		return fullClassName;
	}
	
	/**
	 * generate or refresh the code
	 * @return true if the file was changed
	 * @throws BackboneGenerationException
	 */
	public boolean refreshCode() throws BackboneGenerationException
	{
		// turn the class name into a directory
		String directory = fullClassName.replace('.', '/');
		File realFile = new File(generationDir, directory + ".java");
		
		// make sure the directory is there
		realFile.getParentFile().mkdirs();
		
		File tempFile = new File(generationDir, directory + ".java.temp");
		File backupFile = new File(generationDir, directory + ".java.bak");		
		
		// if this doesn't exist, generate the preface
		boolean exists = realFile.exists();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		StringWriter complexWriter = new StringWriter();
		boolean deleteTemp = false;
		try
		{
			tempFile.getParentFile().mkdirs();
			writer = new BufferedWriter(new FileWriter(tempFile));
			if (exists)
				reader = new BufferedReader(new FileReader(realFile));

			// if we can, copy the preamble
			if (!exists)
			{
				makePreamble(writer);				
				addMarker(writer, START_GENERATED_CODE);				
				Map<String, String> vars = makeGeneratedCode(writer);
				writer.append(complexWriter.toString());				
				addMarker(writer, END_GENERATED_CODE);				
				makePostamble(writer, vars);
			}
			else
			{
				if (!copyPreamble(reader, writer))
				{
//					System.out.println("$$ skipping file " + realFile + ", as start generator marker not found...");
					deleteTemp = true;
					return false;
				}
				addMarker(writer, START_GENERATED_CODE);				
				makeGeneratedCode(writer);
				writer.append(complexWriter.toString());
				addMarker(writer, END_GENERATED_CODE);				
				if (!copyPostamble(reader, writer))
				{
//					System.out.println("$$ skipping file " + realFile + ", as end generator marker not found...");
					deleteTemp = true;
					return false;
				}
			}			
		}
		catch (IOException ex)
		{
			throw new BackboneGenerationException("Problem writing to file " + tempFile + " when refreshing leaves", null);
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
				if (reader != null)
					reader.close();
				if (deleteTemp)
					tempFile.delete();
			}
			catch (IOException ex)
			{
				throw new BackboneGenerationException("Problem closing file " + tempFile + " when writing hardcoded factory", ex);
			}
		}
		
		// move the files over now to complete the generation
		if (exists && identicalContents(realFile, tempFile))
		{
			tempFile.delete();
			return false;
		}
		else
		{
			if (GlobalPreferences.preferences.getRawPreference(BackboneWriter.BB_WRITE_BACKUPS).asBoolean())
				moveFile(realFile, backupFile);
			moveFile(tempFile, realFile);
			return true;
		}
	}

	private static final String BB_API = "com.intrinsarc.backbone.runtime.api.";
	public static String removeRedundantPrefixes(String impl)
	{    
    if (impl.startsWith(BB_API))
    	impl = impl.substring(BB_API.length());
    if (impl.startsWith("java.lang."))
    	impl = impl.substring("java.lang.".length());
    return impl;
	}
	
	private void addMarker(BufferedWriter writer, String marker) throws IOException
	{
		writer.write(marker);
		writer.newLine();
	}
	public static boolean identicalContents(File file1, File file2) throws BackboneGenerationException
	{
		BufferedReader reader1 = null;
		BufferedReader reader2 = null;
		try
		{
			reader1 = new BufferedReader(new FileReader(file1));
			reader2 = new BufferedReader(new FileReader(file2));
			
			for (;;)
			{
				String line1 = reader1.readLine();
				String line2 = reader2.readLine();
				
				if (line1 == null && line2 != null)
					return false;
				if (line1 != null && line2 == null)
					return false;
				// have we got to the end of the file?
				if (line1 == null)
					break;
				// are the lines identical?
				if (!line1.equals(line2))
					return false;
			}
		}
		catch (IOException ex)
		{
			throw new BackboneGenerationException("Problem comparing file " + file1 + " and " + file2, ex);
		}
		finally
		{
			try
			{
				if (reader1 != null)
					reader1.close();
				if (reader2 != null)
					reader2.close();
			}
			catch (IOException ex)
			{
				throw new BackboneGenerationException("Problem closing file", ex);
			}
		}
			
		// if we got here there is no change...
		return true;
	}

	/** copy the file from into the location to
	 * 
	 * @param realFile
	 * @param backupFile
	 */
	public static void moveFile(File from, File to)
	{
		to.delete();
		from.renameTo(to);
	}

	private boolean copyPreamble(BufferedReader reader, BufferedWriter writer) throws IOException
	{
		// copy lines until we get to the end of the file or to the start marker
		String line = null;
		boolean matched = false;
		while ((line = reader.readLine()) != null && !(matched = line.contains(START_GENERATED_CODE)))
		{
			writer.write(line);
			writer.newLine();
		}

		return matched;
	}

	private boolean copyPostamble(BufferedReader reader, BufferedWriter writer) throws IOException
	{
		// copy lines until we get to the end of the file or to the start marker
		String line = null;
		boolean matched = false;
		while ((line = reader.readLine()) != null && !(matched = line.contains(END_GENERATED_CODE)))
			;
		
		while ((line = reader.readLine()) != null)
		{
			writer.write(line);
			writer.newLine();
		}
		return matched;
	}
}
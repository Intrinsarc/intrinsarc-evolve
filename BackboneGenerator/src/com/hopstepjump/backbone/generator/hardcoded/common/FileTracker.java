package com.hopstepjump.backbone.generator.hardcoded.common;

import java.io.*;
import java.util.*;

public class FileTracker
{
	private Set<File> existingFiles = new HashSet<File>();
	private Set<File> newFiles = new HashSet<File>();
	private String extension;
	
	public FileTracker(String extension)
	{
		this.extension = extension;
	}
	
	public void recordExisting(File directory)
	{
		File[] files = directory.listFiles();
		if (files == null)
			return;
		for (File f : files)
			if (f.isDirectory())
				recordExisting(f);
			else
			if (f.getName().endsWith(extension))
				existingFiles.add(f);
	}
	
	public void recordNew(File file)
	{
		newFiles.add(file);
	}
	
	public void deleteUnwantedFiles()
	{
		Set<File> unwanted = new HashSet<File>(existingFiles);
		unwanted.removeAll(newFiles);
		for (File f : unwanted)
			f.delete();
	}

	public void possiblyWrite(File file, String contents) throws IOException
	{
		// compare the contents of the old file
		String old = "";
		try
		{
			old = loadFileIntoString(file);
		}
		catch (IOException ex)
		{
			// ignore, and try to write anyway
		}
		
		if (!old.equals(contents))
		{
			// write the file
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(contents);
			out.close();
		}
	}
	
  
  /**
   * read a file into a string
   * @param path
   * @param name
   * @return
   * @throws FileNotFoundException
   */
  public String loadFileIntoString(File file) throws IOException
  {
    FileReader in = null;
    BufferedReader reader = null;
    StringBuilder out = new StringBuilder();
    try
    {
      in = new FileReader(file);
      reader = new BufferedReader(in);
      String line;
      do
      {
        line = reader.readLine();
        if (line != null)
        {
          out.append(line);
          out.append("\n");
        }
      } 
      while (line != null);
     }
    finally
    {
      if (reader != null)
        reader.close();
      if (in != null)
        in.close();
    }
    return out.toString();
  }

  /**
   * recursively remove any redundant directories
   * @param directory
   */
  public void deleteEmptyDirectories(File directory)
  {
  	File[] files = directory.listFiles();
  	if (files == null)
  		return;

		for (File f : files)
			if (f.isDirectory())
				deleteEmptyDirectories(f);

  	if (directory.listFiles().length == 0)
  		directory.delete();
  }
}

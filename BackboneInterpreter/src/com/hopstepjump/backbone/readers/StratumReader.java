package com.hopstepjump.backbone.readers;

import java.io.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.converters.*;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.*;

/**
 * reads a stratum in from a directory.  will look through all child directories for child strata
 * @author andrew
 */
public class StratumReader
{
  private XStream x;
  private File directory;
  
  public StratumReader(XStream x, File directory, boolean top)
  {
    this.x = x;
    this.directory = directory;
  }
  
  public BBStratum readStratum() throws StratumLoadingException, BBNodeNotFoundException
  {
  	File fileList[] = directory.listFiles();
  	if (fileList != null)
	  	for (File child : fileList)
	  		if (child.getName().endsWith(".bb"))
	  			return readStratum(child);

  	// if we get here, then we have found no .bb files
  	throw new StratumLoadingException("Found no .bb files in " + directory);
  }
  
  private BBStratum readStratum(File file) throws StratumLoadingException, BBNodeNotFoundException
  {
    if (!file.exists())
      throw new StratumLoadingException("Cannot read " + file);
    
    BufferedReader buffered;
    try
    {
      buffered = new BufferedReader(new FileReader(file));
    }
    catch (FileNotFoundException ex)
    {
      throw new StratumLoadingException("Cannot find file to read stratum from " + file);
    }
    
    try
    {
      Object obj = x.fromXML(buffered);
      if (!(obj instanceof BBStratum))
        throw new StratumLoadingException("Did not find a stratum definition inside " + file);

      return (BBStratum) obj;
    }
    catch(ConversionException ex)
    {
      // possibly a node issue
      if (ex.getCause() instanceof BBNodeNotFoundException)
        throw (BBNodeNotFoundException) ex.getCause();
      ex.printStackTrace();
      throw new StratumLoadingException("Cannot load due to XStream conversion error");
    }
    finally
    {
      try
      {
        buffered.close();
      }
      catch (IOException e)
      {
      }
    }
  }
}

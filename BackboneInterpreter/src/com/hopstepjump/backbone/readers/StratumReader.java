package com.hopstepjump.backbone.readers;

import java.io.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.backbone.parser.*;
import com.hopstepjump.backbone.parserbase.*;

/**
 * reads a stratum in from a directory.  will look through all child directories for child strata
 * @author andrew
 */
public class StratumReader
{
  private File directory;
  
  public StratumReader(File directory)
  {
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
  
  private BBStratum readStratum(File stratum) throws StratumLoadingException, BBNodeNotFoundException
  {
    if (!stratum.exists())
      throw new StratumLoadingException("Cannot read " + stratum);
    
  	Expect expect = LoadListReader.makeExpect(stratum);
  	try
  	{
  		BBStratum st = new StratumParser(expect).parse();
  		return st;
  	}
  	catch (ParseException ex)
  	{
      throw new StratumLoadingException("Parsing problem with: " + stratum + ", message is: " + ex.getMessage());    	
  	}
  }
}

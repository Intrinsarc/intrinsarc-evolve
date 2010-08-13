package com.intrinsarc.backbone.readers;

import java.io.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.backbone.nodes.lazy.*;
import com.intrinsarc.backbone.parser.*;
import com.intrinsarc.backbone.parserbase.*;

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
  		long start = System.currentTimeMillis();
  		BBStratum st = new StratumParser(expect).parse();
  		long end = System.currentTimeMillis();
  		return st;
  	}
  	catch (ParseException ex)
  	{
      throw new StratumLoadingException("Parsing problem with: " + stratum + ", message is: " + ex.getMessage());    	
  	}
  }
}

package com.hopstepjump.backbone.generator;

import java.io.*;

import com.hopstepjump.backbone.printers.*;
import com.hopstepjump.deltaengine.base.*;

public class StratumCodeGenerator
{
  private DEStratum stratum;

  public StratumCodeGenerator(DEStratum stratum)
  {
    this.stratum = stratum;
  }

  public void generateBackboneSource(String name, File base) throws BackboneGenerationException, IOException
  {
    // generate the files for this stratum
    base.mkdirs();
    
    // create the stratum definition
    File sfile = new File(base, name + ".bb");
    BufferedWriter writer = null;
    try
    {
      writer = new BufferedWriter(new FileWriter(sfile));

      StratumPrinter printer = new StratumPrinter(stratum, stratum, BackbonePrinterMode.REAL_UUIDS);
	    writer.append(printer.makePrintString(""));
    }
    catch (IOException e)
    {
      throw new BackboneGenerationException("Cannot write interfaces to " + sfile, null);
    }
    finally
    {
    	if (writer != null)
    		writer.close();
    }
  }  
}

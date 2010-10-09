package com.intrinsarc.licensemanager;

import java.io.*;

public class FileUtilities
{
  public static String loadFileContents(File file)
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
    catch (Exception ex)
    {
    	
    }
    finally
    {
    	try
    	{
    		if (reader != null)
    			reader.close();
    		if (in != null)
    			in.close();
    	}
    	catch (Exception ex)
    	{
    		
    	}
    }
    return out.toString();
  }	

}

package com.hopstepjump.swing;

import java.io.*;
import java.net.*;

public class FileLoader
{
	public static final String loadFile(String fileName)
	{
		URL resource = ClassLoader.getSystemResource(fileName);
		StringBuilder b = new StringBuilder();
		
		try
		{
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
					resource.openStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
		}
	  catch (IOException e)
	  {
	  	return null; 
	  }

		return b.toString();
	}
}

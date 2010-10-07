package com.intrinsarc.lbase;

import java.io.*;
import java.util.*;

public class LDetails
{
	private Map<String, String> details = new LinkedHashMap<String, String>();
	
	public LDetails(String contents)
	{
		BufferedReader buf = new BufferedReader(new StringReader(contents));
		String line;
		try
		{
			while ((line = buf.readLine()) != null)
				addDetail(line);
		}
		catch (IOException e)
		{			
		}
	}
	
	private void addDetail(String line)
	{
		if (line.trim().length() == 0)
			return;
		
		try
		{
			StringTokenizer tok = new StringTokenizer(line, "=");
			String name = tok.nextToken();
			String value = tok.nextToken();
			details.put(name, value);
		}
		catch (NoSuchElementException ex)
		{
			System.err.println("$$ Problem tokenizing details line: " + line);
		}
	}
}
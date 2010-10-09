package com.intrinsarc.lbase;

import java.io.*;
import java.security.*;
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
			int index = line.indexOf('=');
			if (index != -1)
			{
				String name = line.substring(0, index);
				String value = line.substring(index+1);
				details.put(name, value);
			}
		}
		catch (NoSuchElementException ex)
		{
			System.err.println("$$ Problem tokenizing details line: " + line);
		}
	}
	
	public Map<String, String> getDetails()
	{
		return details;
	}
	
	public String toString()
	{
		String str = "";
		for (String name : details.keySet())
			str += name + "=" + details.get(name) + "\n";
		return str;
	}
	
	public byte[] encrypt(PublicKey key)
	{
		return null;
	}

	public byte[] decrypt(PrivateKey key)
	{
		return null;
	}

	public String get(String name)
	{
		return details.get(name);
	}
}

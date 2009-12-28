/*
 * Created on Dec 22, 2003 by Andrew McVeigh
 */
package com.hopstepjump.idraw.utility;

import java.io.*;


/**
 * @version 	1.0
 * @author
 */
public class HexUtility
{
	public static int hexStringToInt(String hex)
	{
		if (hex.length() != 2)
			throw new IllegalArgumentException("Hex string should be 2 chars: " + hex);
			
		return hexCharToInt(hex.charAt(0)) * 16 + hexCharToInt(hex.charAt(1));
	}
	
	public static int hexCharToInt(char ch)
	{
		String hex = "0123456789abcdef";
		int index = hex.indexOf(Character.toLowerCase(ch));
		if (index < 0)
			throw new IllegalArgumentException("Hex char should be from 0-9a-f: " + ch);
		return index;
	}
	
	public static String int8ToHexString(int i)
	{
		return "" + int4ToHexChar(i/16) + int4ToHexChar(i%16);
	}
	
	public static char int4ToHexChar(int i)
	{
		if (i < 0 || i > 15)
			throw new IllegalArgumentException("Int8 should be from 0-15: " + i);
		return "0123456789abcdef".charAt(i);
	}

	public static void writeRawString(OutputStream out, String str) throws IOException
	{
		int size = str.length();
		for (int lp = 0; lp < size; lp++)
			out.write(str.charAt(lp));
	}	
}

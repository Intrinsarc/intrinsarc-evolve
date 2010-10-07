package com.intrinsarc.lbase;

public class HexUtils
{
	private static final String[] HEX_CHARS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

	public static byte[] fromHex(String hex)
	{
		int size = hex.length() / 2;
		byte bytes[] = new byte[size];
		
		for (int lp = 0; lp < size; lp++)
			bytes[lp] = toByte(hex.charAt(lp*2), hex.charAt(lp*2+1)); 
		
		return bytes;
	}
	
	private static byte toByte(char char1, char char2)
	{
		return (byte) (num(char1) * 16 + num(char2));
	}

	private static int num(char ch)
	{
		if (Character.isDigit(ch))
			return ch - '0';
		return Character.toLowerCase(ch) - 'a';
	}

	public static String toHex(byte[] bytes)
	{
  	String hex = "";
  	for (byte b : bytes)
  		hex += toHex(b);
  	return hex;
	}
	
	public static String toHex(byte b)
	{
		if (b < 0)
			b += 256;
		return HEX_CHARS[(b&255)/16] + HEX_CHARS[b&15];
	}
}

/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class TextOutRecord extends WMFAbstractRecord
{
	private String text;
	private int x;
	private int y;

	public TextOutRecord()
	{
		super("0521", "TextOut", false);
	}

	public TextOutRecord(String text, int x, int y)
	{
		this();
		this.text = text;
		this.x = x;
		this.y = y;
	}

	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		int size = text.length();
		int count = (size+1)/2;
		WMFBytes[] bytes = new WMFBytes[count + 3];
		
		bytes[0] = new WMFWord(size);
		int lp = 0;
		for (; lp < count; lp++)
		{
			char c1 = text.charAt(lp*2);
			char c2 = '\0';
			if (lp*2+1 < size)
				c2 = text.charAt(lp*2+1);
			bytes[lp+1] = new WMFWord(c2, c1);
		}
		bytes[++lp] = new WMFWord(WMFFormat.scale(y));
		bytes[++lp] = new WMFWord(WMFFormat.scale(x));
		return bytes;
	}

	public void decodeParameters(long[] records)
	{
		int lp = 4;
		long size = records[3];
		String text = "";
		for (int index = 0; index < (size+1)/2; index++)
		{
			long word = records[lp + index];
			long char1 = word & 255;
			long char2 = (word >> 8) & 255;
			if (char1 == 0)
				break;
			text += new String(new char[]{(char) char1});
			if (char2 == 0)
				break;
			text += new String(new char[]{(char) char2});
		}
		System.out.println("  text = " + text);
	}

}

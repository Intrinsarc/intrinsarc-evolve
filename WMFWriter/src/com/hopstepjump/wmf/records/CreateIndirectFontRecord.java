package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.utility.*;

/**
 *
 * (c) Andrew McVeigh 14-Jan-03
 *
 */
public class CreateIndirectFontRecord extends WMFAbstractRecord
{
	private int height;
	private int width;
	private int escapement;
	private int orientation;
	private int weight;
	private boolean italic;
	private boolean underline;
	private boolean strikeout;
	private int charset;
	private int outprecision;
	private int clipprecision;
	private int quality;
	private int pitchfamily;
	private String family;
	
	public CreateIndirectFontRecord()
	{
		super("02fb", "CreateIndirectFontRecord", true);
	}

	public CreateIndirectFontRecord(int height, int width, int escapement, int orientation, int weight, boolean italic, boolean underline, boolean strikeout, int charset, int outprecision, int clipprecision, int quality, int pitchfamily, String family)
	{
		this();
		this.height = height;
		this.width = width;
		this.escapement = escapement;
		this.orientation = orientation;
		this.weight = weight;
		this.italic = italic;
		this.underline = underline;
		this.strikeout = strikeout;
		this.charset = charset;
		this.outprecision = outprecision;
		this.clipprecision = clipprecision;
		this.quality = quality;
		this.pitchfamily = pitchfamily;
		this.family = family;
	}
	
	public CreateIndirectFontRecord(int height, boolean bold, boolean italic, boolean underline, String family)
	{
		this();
		this.height = height;
		this.weight = bold ? (2*256+11*16+12) : 0;
		this.italic = italic;
		this.underline = underline;
		this.family = family;
		this.outprecision = 4;  // choose a true type font if possible
	}
		
	/**
	 * @see com.hopstepjump.wmf.WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{
			new WMFWord(WMFFormat.scale(height)),
			new WMFWord(WMFFormat.scale(width)),
			new WMFWord(escapement),
			new WMFWord(orientation),
			new WMFWord(WMFFormat.scale(weight)),
			new WMFBoolean(italic),
			new WMFBoolean(underline),
			new WMFBoolean(strikeout),
			new WMFByte(charset),
			new WMFByte(outprecision),
			new WMFByte(clipprecision),
			new WMFByte(quality),
			new WMFByte(pitchfamily),
			new WMFFixedString(family, 32)};
	}


	/**
	 * @see com.hopstepjump.wmf.records.WMFAbstractRecord#decodeParameters(long[])
	 */
	public void decodeParameters(long[] records)
	{
		int lp = 3;
		System.out.println("  height = " + records[lp++]);
		System.out.println("  width = " + records[lp++]);
		System.out.println("  escapement = " + records[lp++]);
		System.out.println("  orientation = " + records[lp++]);
		System.out.println("  weight = " + records[lp++]);
		System.out.println("  italic = " +        ((records[lp] >> 0) & 255));
		System.out.println("  underline = " +     ((records[lp] >> 8) & 255));
		lp++;
		System.out.println("  strikeout = " +     ((records[lp] >> 0) & 255));
		System.out.println("  charset = " +       ((records[lp] >> 8) & 255));
		lp++;
		System.out.println("  outprecision = " +  ((records[lp] >> 0) & 255));
		System.out.println("  clipprecision = " + ((records[lp] >> 8) & 255));
		lp++;
		System.out.println("  quality = " +       ((records[lp] >> 0) & 255));
		System.out.println("  pitchfamily = " +   ((records[lp] >> 8) & 255));
		lp++;
		String family = "";
		for (int index = 0; index < 32/2; index++)
		{
			long word = records[lp + index];
			long char1 = word & 255;
			long char2 = (word >> 8) & 255;
			if (char1 == 0)
				break;
			family += new String(new char[]{(char) char1});
			if (char2 == 0)
				break;
			family += new String(new char[]{(char) char2});
		}
		System.out.println("  family = " + family);
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CreateIndirectFontRecord))
			return false;
		CreateIndirectFontRecord font = (CreateIndirectFontRecord) obj;

		return
			font.height == height &&
			font.width == width &&
			font.escapement == escapement &&
			font.orientation == orientation &&
			font.weight == weight &&
			font.italic == italic &&
			font.underline == underline &&
			font.strikeout == strikeout &&
			font.charset == charset &&
			font.outprecision == outprecision &&
			font.clipprecision == clipprecision &&
			font.quality == quality &&
			font.pitchfamily == pitchfamily &&
			font.family.equals(family);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return height ^ width ^ pitchfamily ^ family.hashCode();
	}

}

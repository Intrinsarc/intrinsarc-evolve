/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf;

import java.awt.*;
import java.io.*;

import com.hopstepjump.wmf.records.*;
import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class WMFFormat
{
	public static double RES_SCALE = 10;
	private static final long twipsPerInch = (int) Math.round(20 * 72 / RES_SCALE) ;  // a twip is a 20th of a point
	private static final double twipsPerMM = twipsPerInch / 25.3;
	private CompositeRecord records = new CompositeRecord();
	private static final int headerSize = (5+2*2) * 2; // size in bytes
	private int twipsWidth;
	private int twipsHeight;
	private int scaleX;
	private int scaleY;

	public WMFFormat(int scaleX, int scaleY, int width, int height)
	{
		// picw1535\\pich3942\\picwgoal870\\pichgoal2235
		this.scaleX = Math.round(scaleX);
		this.scaleY = Math.round(scaleY);
		this.twipsWidth = pixelsToTwips(scale(width));
		this.twipsHeight = pixelsToTwips(scale(height));
	}

	/** works for my screen, but probably nobody elses */
	private int pixelsToTwips(int pixels)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		double pixelsPerInch = tk.getScreenResolution();
		return (int)Math.round(twipsPerInch * pixels / pixelsPerInch);
	}

/**
 * Writes out a set of rtf bytes for the format.  The header is as below:
typedef struct _WindowsMetaHeader
{
  WORD  FileType;       // Type of metafile (0=memory, 1=disk)
  WORD  HeaderSize;     // Size of header in WORDS (always 9)
  WORD  Version;        // Version of Microsoft Windows used
  DWORD FileSize;       // Total size of the metafile in WORDs
  WORD  NumOfObjects;   // Number of objects in the file
  DWORD MaxRecordSize;  // The size of largest record in WORDs
  WORD  NumOfParams;    // Not Used (always 0)
} WMFHEAD;

FileType contains a value which indicates the location of the metafile data. A value of 0 indicates that the metafile is stored in memory, while a 1 indicates that it is stored on disk. 

HeaderSize contains the size of the metafile header in 16-bit WORDs. This value is always 9. 

Version stores the version number of Microsoft Windows that created the metafile. This value is always read in hexadecimal format. For example, in a metafile created by Windows 3.0 and 3.1, this item would have the value 0x0300. 

FileSize specifies the total size of the metafile in 16-bit WORDs. 

NumOfObjects specifies the number of objects that are in the metafile. 

MaxRecordSize specifies the size of the largest record in the metafile in WORDs. 

NumOfParams is not used and is set to a value of 0. 
*/

	public void writeRTFBytes(OutputStream out, boolean deleteObjects) throws IOException
	{
		int mmWidth = (int) Math.round(twipsWidth / twipsPerMM * 100.0 * 0.7);
		int mmHeight = (int) Math.round(twipsHeight / twipsPerMM * 100.0 * 0.7);
		
		out.write(("{\\pict\\picscalex" + scaleX + "\\picscaley" + scaleY + "\\picw" + mmWidth + "\\pich" + mmHeight + "\\picwgoal" + twipsWidth + "\\pichgoal" + twipsHeight + "\\wmetafile8 ").getBytes());
		writeBytes(out, deleteObjects);
		out.write("}".getBytes());
	}
	
	/** write out the bytes.  note that the end record is automatically added */
	public void writeBytes(OutputStream out, boolean deleteObjects) throws IOException
	{
		final int headerWordSize = 9;
		int createdObjects = records.setObjectNumber(0);
		
		CompositeRecord all = new CompositeRecord();
		all.addRecord(records);

		if (deleteObjects)
		{
			CompositeRecord deleteRecords = new CompositeRecord();
			for (int lp = 0; lp < createdObjects; lp++)
				deleteRecords.addRecord(new DeleteObjectRecord(lp));
			all.addRecord(deleteRecords);
		}
		all.addRecord(new EndMetafileRecord());

		new WMFWord(1).writeBytes(out);  // filetype
		new WMFWord(headerSize/2).writeBytes(out);  // header size in words
		new WMFWord(3,0).writeBytes(out);
		new WMFDoubleWord(all.sizeInBytes()/2 + headerWordSize).writeBytes(out);
		new WMFWord(createdObjects).writeBytes(out);
		new WMFDoubleWord(all.getMaxLineSize()/2).writeBytes(out);
		new WMFWord(0).writeBytes(out);
		
		// now, write the records also
		all.writeBytes(out);
	}
	
	public void addRecord(WMFRecord record)
	{
		records.addRecord(record);
	}

	public static int scale(double value)
	{
		if (value < 0)
			return 65536 + scaleAndRound(value);
		return scaleAndRound(value);
	}
	
	private static int scaleAndRound(double value)
	{
		return (int) Math.round(value * RES_SCALE);
	}
}

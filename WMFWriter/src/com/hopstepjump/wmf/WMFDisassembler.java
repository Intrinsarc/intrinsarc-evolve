/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf;

import java.io.*;

import com.hopstepjump.wmf.records.*;
import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class WMFDisassembler
{
	public static void main(String args[])
	{
		if (args.length != 1)
		{
			System.out.println("Usage: WMFDisassembler filename");
			System.exit(-1);
		}
		
		try
		{
			FileReader r = new FileReader(args[0]);
			BufferedReader b = new BufferedReader(r);
			
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			String line;
			for (int lp = 0; (line = b.readLine()) != null; lp++)
			{
				System.out.println("Line = " + line);
				if (line.length() > 0 && isHexDigit(line.charAt(0)))
				{
					bytes.write(convertHexLineToBytes(line));
					System.out.println("Converted line " + lp + " ok");
				}
			}
			
			// turn into words now
			byte[] raw = bytes.toByteArray();
			long[] words = new long[raw.length/2];
			if (raw.length % 2 != 0)
				throw new IllegalArgumentException("Bytes is not an even number");
			for (int lp = 0; lp < raw.length / 2; lp++)
				words[lp] = byteToInt(raw[lp*2]) + byteToInt(raw[lp*2+1]) * 256;
			
			
			// disassemble
			WMFDisassembler disassembler = new WMFDisassembler();
			disassembler.disassemble(words);
			
		}
		catch (IOException ex)
		{
			System.out.println("Caught an IO Exception: " + ex);
		}
	}
	
	private static int byteToInt(byte b)
	{
		return b < 0 ? 256 + b : b;
	}
	
	private static boolean isHexDigit(char ch)
	{
		final String hexDigits = "0123456789abcdef";
		return hexDigits.indexOf(Character.toLowerCase(ch)) != -1;
	}
	
	private static byte[] convertHexLineToBytes(String line)
	{
		int length = line.length();
		if (length % 2 != 0)
			throw new IllegalArgumentException("Line is not an even number of hex digits: " + line);
		byte bytes[] = new byte[length/2];
		for (int lp = 0; lp < length/2; lp++)
		{
			String hexByte = line.substring(lp*2, lp*2+2);
			bytes[lp] = (byte) HexUtils.hexStringToInt(hexByte);
		}
		return bytes;
	}
	
	/**
	 * take the array of words as a long[], so we don't have to deal with signed issues
	 */
	public void disassemble(long[] words)
	{
		// get a list of subclasses of WMFAbstractRecord
		WMFAbstractRecord[] recordDisassemblers = new WMFAbstractRecord[]
		{
			new CreateIndirectBrushRecord(),
			new CreateIndirectFontRecord(),
			new CreateIndirectPenRecord(),
			new DeleteObjectRecord(),
			new EllipseRecord(),
			new EndMetafileRecord(),
			new ImageRecord(),
			new LineToRecord(),
			new MoveToRecord(),
			new PolygonRecord(),
			new PolyLineRecord(),
			new RectangleRecord(),
			new SelectObjectRecord(),
			new SetBackgroundColorRecord(),
			new SetTextColorRecord(),
			new SetBackgroundModeRecord(),
			new SetWindowExtentRecord(),
			new SetWindowOriginRecord(),
			new TextOutRecord()
		};
		
		System.out.println("WMF Header\n---------\n");
		System.out.println("Filetype = " + words[0]);
		System.out.println("Header size in words = " + words[1]);
		System.out.println("Windows version = " + words[2]);
		System.out.println("Length of wmf in words = " + words[3] + 65536 * words[4]);
		System.out.println("Number of objects created = " + words[5]);
		System.out.println("Maximum records size in words = " + words[6] + 65536 * words[7]);
		System.out.println("Extra byte = " + words[8]);
		System.out.println("");
		
		// extract each record in turn
		int recordSize;
		for (int offset = 9, number = 0; offset < words.length; offset += recordSize, number++)
		{
			recordSize = getRecordWordSize(words, offset);
			long[] record = readOneRecord(words, offset, recordSize);
			
			decodeOneRecord(recordDisassemblers, number, record);
		}
	}
	
	private int getRecordWordSize(long[] words, int offset)
	{
		return (int) (words[offset] + 65536 * words[offset+1]);
	}
	
	private long[] readOneRecord(long[] words, int offset, int recordSize)
	{
		long[] record = new long[recordSize];
		System.arraycopy(words, offset, record, 0, recordSize);
		return record;
	}

	private void decodeOneRecord(WMFAbstractRecord[] recordDisassemblers, long number, long[] record)
	{
		System.out.println("Record " + number + "\n----------");
		System.out.println("<< " + HexUtils.int8ToHexString((int) (record[2] / 256)) + HexUtils.int8ToHexString((int)(record[2] % 256)) + ">>, number of parameter words = " + (record.length - 3));

		boolean decoded = false;
		for (int lp = 0; lp < recordDisassemblers.length; lp++)
		{
			if (recordDisassemblers[lp].disassemble(record))
			{
				decoded = true;
			}
		}
		if (!decoded)
			System.out.println("Unknown function");
		System.out.println();
	}
}

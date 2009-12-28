/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.unittests;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import junit.framework.*;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.records.*;

/**
 * @version 	1.0
 * @author
 */
public class TestWMFSimple extends TestCase
{
	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(TestWMFSimple.class);
	}

	public static Test suite()
	{
		return new TestSuite(TestWMFSimple.class);
	}

	public TestWMFSimple(String name)
	{
		super(name);
	}
	
	public void testHeaderPlusOneRecord() throws IOException
	{
		WMFFormat data = new WMFFormat(100, 100, 200, 200/* picw1535\\pich3942\\picwgoal870\\pichgoal2235 */);
		data.addRecord(new SetWindowOriginRecord(10, 20));
		
		// make into a string and check
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		data.writeBytes(out, false);
		
		String expected =
"0100"+       // on disk
"0900"+       // size of header in words
"0003"+       // windows version
"11000000"+   // total number of words including header
"0000"+       // number of created objects
"05000000"+   // largest record in words
"0000"+       // always 0
"050000000b0214000a00"+
"030000000000";  // a SetWindowOrg record
		
		assertEquals(expected, out.toString());
	}

	public void testHeaderPlusTwoRecords() throws IOException
	{
		
		
		WMFFormat data = new WMFFormat(100, 100, 200, 200/* picw1535\\pich3942\\picwgoal870\\pichgoal2235 */);
		data.addRecord(new SetWindowOriginRecord(10, 20));
		data.addRecord(new SetWindowExtentRecord(50, 70));
		
		// make into a string and check
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		data.writeBytes(out, false);
		
		String expected =
"0100"+       // on disk
"0900"+       // size of header in words
"0003"+       // windows version
"16000000"+   // total number of words including header
"0000"+       // number of created objects
"05000000"+   // largest record in words
"0000"+       // always 0
"050000000b0214000a00"+  // a SetWindowOrg record
"050000000c0246003200"+
"030000000000";  // a SetWindowOrg record
		
		assertEquals(expected, out.toString());
	}
	
	public void testRectangle() throws IOException
	{
		WMFFormat data = new WMFFormat(100, 100, 600, 600);
		data.addRecord(new SetWindowOriginRecord(0, 0));
		data.addRecord(new SetWindowExtentRecord(600, 600));
		data.addRecord(new SetBackgroundColorRecord(Color.green));
		data.addRecord(new SetBackgroundModeRecord(true));
		WMFRecord brush = new CreateIndirectBrushRecord(CreateIndirectBrushRecord.BS_HATCHED, Color.red, CreateIndirectBrushRecord.HS_FDIAGONAL);
		data.addRecord(brush);
		data.addRecord(new SelectObjectRecord(brush));
		WMFRecord pen = new CreateIndirectPenRecord(CreateIndirectPenRecord.PS_SOLID, 15, Color.magenta);
		data.addRecord(pen);
		data.addRecord(new SelectObjectRecord(pen));
		data.addRecord(new RectangleRecord(0, 0, 300, 300));
		
		// draw a line
		WMFRecord pen2 = new CreateIndirectPenRecord(CreateIndirectPenRecord.PS_SOLID, 10, Color.cyan);
		data.addRecord(pen2);
		data.addRecord(new SelectObjectRecord(pen2));
		data.addRecord(new MoveToRecord(10, 190));
		data.addRecord(new LineToRecord(170, 190));
		
		// draw a circle
		WMFRecord brush3 = new CreateIndirectBrushRecord(CreateIndirectBrushRecord.BS_NULL, Color.white);
		data.addRecord(brush3);
		data.addRecord(new SelectObjectRecord(brush3));
		WMFRecord pen3 = new CreateIndirectPenRecord(CreateIndirectPenRecord.PS_SOLID, 0, Color.black);
		data.addRecord(pen3);
		data.addRecord(new SelectObjectRecord(pen3));
		data.addRecord(new EllipseRecord(180, 180, 150, 150));

		// some text
		data.addRecord(new SetBackgroundModeRecord(false));
		data.addRecord(new TextOutRecord("Hello there!", 200, 190));
		
		// change the font
		WMFRecord font = new CreateIndirectFontRecord(30, false, true, true, "SansSerif");
		data.addRecord(font);
		data.addRecord(new SelectObjectRecord(font));
		data.addRecord(new SetTextColorRecord(Color.ORANGE));
		data.addRecord(new TextOutRecord("Italics", 20, 10));
		
		// add a nice triangle)
		data.addRecord(new SelectObjectRecord(brush));
		data.addRecord(new SelectObjectRecord(pen));
		data.addRecord(
			new PolygonRecord(
				new Point2D[]
					{new Point2D.Double(150,320), new Point2D.Double(290,450), new Point2D.Double(10,450)}));

		// add an open triangle as a polyline
		data.addRecord(new SelectObjectRecord(brush3));
		data.addRecord(new SelectObjectRecord(pen2));
		data.addRecord(
			new PolyLineRecord(
				new Point2D[]
					{new Point2D.Double(350,320), new Point2D.Double(390,450), new Point2D.Double(310,450)}));
					
		// add an image
//		BufferedImage image = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
//		Graphics2D g2d = (Graphics2D) image.getGraphics();
//		g2d.setPaint(Color.PINK);
//		g2d.fillRect(0, 0, 80, 80);
//		data.addRecord(new ImageRecord(400, 400, image));

		// end the metafile

		String headerString = "{\\rtf1\\ansi\\ansicpg1252\\deff0\\deflang1033{\\fonttbl{\\f0\\fswiss\\fcharset0 Arial;}}\\viewkind4\\uc1\\pard\\f0\\fs20";
		String footerString = "\\par}";

		FileOutputStream out = new FileOutputStream("c:/temp/bytes.txt");
		out.write(headerString.getBytes());
		data.writeRTFBytes(out, true);
		out.write(footerString.getBytes());
		out.close();

		ByteArrayOutputStream clipOut = new ByteArrayOutputStream();
		clipOut.write(headerString.getBytes());
		data.writeRTFBytes(clipOut, true);
		clipOut.write(footerString.getBytes());

		InputStream s = new ByteArrayInputStream(clipOut.toByteArray());
		MetafileTransferable transferImage = new MetafileTransferable(s);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferImage, transferImage);
	}
}

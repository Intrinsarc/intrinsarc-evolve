package com.hopstepjump.wmf.unittests;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import javax.swing.*;
import junit.framework.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.records.*;

/**
 *
 * (c) Andrew McVeigh 15-Jan-03
 *
 */
public class TestWMFComplex extends TestCase
{

	/**
	 * Constructor for TestWMFComplex.
	 * @param arg0
	 */
	public TestWMFComplex(String arg0)
	{
		super(arg0);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(TestWMFComplex.class);
	}

	public void testCanvas() throws IOException
	{

		// --> make a drawing canvas
		ZCanvas canvas = new ZCanvas();

		// get details about the canvas
		ZLayerGroup canvasLayer = canvas.getLayer();

				// make a portal
		ZRoot root = new ZRoot();
		ZLayerGroup layer = new ZLayerGroup();
		root.addChild(layer);
		ZCamera objectCamera =
			new ZCamera(layer, canvas.getDrawingSurface());
		objectCamera.setFillColor(null);
		ZNode objectCameraNode = new ZVisualLeaf(objectCamera);
		objectCamera.setBounds(0, 0, 100000, 100000); // i.e. infinite
		canvasLayer.addChild(objectCameraNode);
		
		int width = 150;
		int height = 200;

		// set the rendering and interaction properties
		AffineTransform result = objectCamera.getViewTransform();
		result.scale(1, 1);
		objectCamera.setViewTransform(result);

		// use antialiasing for graphics, but not for text
		canvas.getDrawingSurface().setRenderQuality(
			ZDrawingSurface.RENDER_QUALITY_MEDIUM);

		// make it yellow
		canvas.setBackground(Color.YELLOW);
		
		// add a rectangle
		ZRectangle rect = new ZRectangle(new Rectangle(10, 10, 100, 100));
		rect.setFillPaint(Color.CYAN);
		rect.setPenPaint(Color.GREEN);
		rect.setPenWidth(10);
		canvasLayer.addChild(new ZVisualLeaf(rect));
		
		// add another rectangle
		ZRectangle rect2 = new ZRectangle(new Rectangle(20, 130, 50, 50));
		rect2.setFillPaint(null);
		rect2.setPenPaint(Color.BLACK);
		rect2.setPenWidth(10);
		canvasLayer.addChild(new ZVisualLeaf(rect2));
		
		// add some text
		ZText text = new ZText("Hello", new Font("Arial", Font.PLAIN, 20));
		text.setTranslation(50, 50);
		canvasLayer.addChild(new ZVisualLeaf(text));
		
		// add some more text
		ZText text2 = new ZText("Hello!", new Font("Arial", Font.PLAIN, 10));
		text2.setPenColor(Color.ORANGE);
		text2.setTranslation(20, 20);
		canvasLayer.addChild(new ZVisualLeaf(text2));
		
		// add a circle
		ZEllipse ellipse = new ZEllipse(30, 30, 30, 30);
		ellipse.setPenPaint(Color.RED);
		ellipse.setFillPaint(Color.RED);
		canvasLayer.addChild(new ZVisualLeaf(ellipse));

		// add a new layer on top
		ZLayerGroup tlayer = new ZLayerGroup();
		ZRectangle rect3 = new ZRectangle(new Rectangle(20, 20, 10, 10));
		rect3.setFillPaint(Color.BLUE);
		rect3.setPenPaint(Color.WHITE);
		rect3.setPenWidth(2);
		tlayer.addChild(new ZVisualLeaf(rect3));
		canvasLayer.addChild(tlayer);
		
		// move it to the clipboard
		JazzToWMFGraphics2D g2d = new JazzToWMFGraphics2D();
		canvas.setSize(new Dimension(width, height));
		canvas.paint(g2d);
		
		String headerString = "{\\rtf1\\ansi\\ansicpg1252\\deff0\\deflang1033{\\fonttbl{\\f0\\fswiss\\fcharset0 Arial;}}\\viewkind4\\uc1\\pard\\f0\\fs20";
		String footerString = "\\par}";

		// place the data on the clipboard
		WMFFormat data = new WMFFormat(100, 100, width, height);
		data.addRecord(new SetWindowOriginRecord(0, 0));
		data.addRecord(new SetWindowExtentRecord(width, height));
		data.addRecord(g2d.getRecords());
		
		ByteArrayOutputStream clipOut = new ByteArrayOutputStream();
		clipOut.write(headerString.getBytes());
		data.writeRTFBytes(clipOut, true);
		clipOut.write(footerString.getBytes());

		InputStream s = new ByteArrayInputStream(clipOut.toByteArray());
		MetafileTransferable transferImage = new MetafileTransferable(s);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferImage, transferImage);

		// place in a window and show
		JFrame frame = new JFrame("Canvas metafile test");
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		
		try
		{
		Thread.sleep(1000);
		}
		catch (Exception ex)
		{
		}

	}
}

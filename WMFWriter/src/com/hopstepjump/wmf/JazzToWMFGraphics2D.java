package com.hopstepjump.wmf;

import java.awt.*;
import java.awt.RenderingHints.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.text.*;
import java.util.*;
import java.util.List;

import com.hopstepjump.geometry.*;
import com.hopstepjump.wmf.records.*;


/**
 *
 * (c) Andrew McVeigh 15-Jan-03
 *
 */
public class JazzToWMFGraphics2D extends Graphics2D
{
	private boolean currentPenIsDashed;
	
	// caches
	private ReusableCreatedObjects fonts = new ReusableCreatedObjects();
	private ReusableCreatedObjects pens = new ReusableCreatedObjects();
	private ReusableCreatedObjects brushes = new ReusableCreatedObjects();
	private ReusableSetObjects backgroundModes = new ReusableSetObjects();
	private ReusableSetObjects textColors = new ReusableSetObjects();
	
	private double currentPenWidth;
	private Graphics2D real;
	private CompositeRecord records = new CompositeRecord();
	private ArrayList createdWMFGraphic2Ds = new ArrayList();
	double rotationTheta;
	UPoint rotationPoint = new UPoint(0, 0);
	private boolean loggingEnabled;

	
	public JazzToWMFGraphics2D()
	{
		this.real = (Graphics2D) new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB).getGraphics();
		real.setStroke(new BasicStroke(1));
	}

	public CompositeRecord getRecords()
	{
		CompositeRecord recs = new CompositeRecord();
		recs.addRecord(records);
		for (Iterator iter = createdWMFGraphic2Ds.iterator(); iter.hasNext();)
		{
			JazzToWMFGraphics2D g2d = (JazzToWMFGraphics2D) iter.next();
			recs.addRecord(g2d.getRecords());
		}
		return records;
	}
	
	/**
	 * @see java.awt.Graphics2D#addRenderingHints(Map)
	 */
	public void addRenderingHints(Map hints)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: addRenderingHints");
		real.addRenderingHints(hints);
	}

	/**
	 * @see java.awt.Graphics#clearRect(int, int, int, int)
	 */
	public void clearRect(int x, int y, int width, int height)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: clearRect");
//		real.clearRect(x,y,width,height);
	}

	/**
	 * @see java.awt.Graphics2D#clip(Shape)
	 */
	public void clip(Shape s)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: clip");
//		real.clip(s);
	}

	/**
	 * @see java.awt.Graphics#clipRect(int, int, int, int)
	 */
	public void clipRect(int x, int y, int width, int height)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: clipRect");
//		real.clipRect(x,y,width,height);
	}

	/**
	 * @see java.awt.Graphics#copyArea(int, int, int, int, int, int)
	 */
	public void copyArea(int x, int y, int width, int height, int dx, int dy)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: copyArea");
//		real.copyArea(x,y,width,height,dx,dy);
	}

	/**
	 * @see java.awt.Graphics#create()
	 */
	public Graphics create()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: create");
		return this;
	}

	/**
	 * @see java.awt.Graphics#dispose()
	 */
	public void dispose()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: dispose");
		real.dispose();
	}

	/**
	 * @see java.awt.Graphics2D#draw(Shape)
	 */
	
	private List<Segment> extractSegmentsFromShape(Shape s)
	{
		PathIterator iter = s.getPathIterator(null);
		UPoint firstPoint = null;
		UPoint lastPoint = null;
		List<Segment> segments = new ArrayList<Segment>();

		while (!iter.isDone())
		{
			float coords[] = new float[6];
			int type = iter.currentSegment(coords);
			
			UPoint point = new UPoint(coords[0], coords[1]);
			if (firstPoint == null)
			{
			  firstPoint = point;
			  lastPoint = point;
			}

			Segment segment = new Segment(type);
			boolean segmentUnknown = false;

			switch (type)
			{
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					segment.points[0] = rotateAccordingToTransform(firstPoint, point);
					break;
				case PathIterator.SEG_QUADTO:
					segment.points[0] = rotateAccordingToTransform(firstPoint, point);
					segment.points[1] = rotateAccordingToTransform(firstPoint, new UPoint(coords[2], coords[3]));
					break;
				case PathIterator.SEG_CUBICTO:
//				  if (firstCubicPoint)
	//			    firstPoint = new UPoint(coords[0], coords[1]);
					segment.points[0] = rotateAccordingToTransform(firstPoint, point);
					segment.points[1] = rotateAccordingToTransform(firstPoint, new UPoint(coords[2], coords[3]));
					segment.points[2] = rotateAccordingToTransform(firstPoint, new UPoint(coords[4], coords[5]));
					break;
				case PathIterator.SEG_CLOSE:
					segment.points[0] = rotateAccordingToTransform(firstPoint, firstPoint);
					break;
				default:
					segmentUnknown = true;
					break;
			}
			iter.next();
			if (!segmentUnknown)
			{
			  int size = segments.size();
			  lastPoint = firstPoint;
			  if (size != 0)
			    lastPoint = segments.get(size - 1).getLastPoint();
		    segment.expandInto(lastPoint, segments);
			} 
		}
		return segments;
	}

	
	private UPoint rotateAccordingToTransform(UPoint firstPoint, UPoint point)
	{
		// must do this using Point2D.Double, as UPoint is notionally immutable
		UDimension offset = point.subtract(firstPoint);
		Point2D srcPoint = new Point2D.Double(offset.getWidth(), offset.getHeight());
		Point2D destPoint = new Point2D.Double(0,0);
		getTransform().deltaTransform(srcPoint, destPoint);
		return new UPoint(destPoint).add(firstPoint.asDimension());
	}
	
	public void draw(Shape s)
	{
		Color currentColor = real.getColor();
		if (currentColor.getAlpha() == 0)
			return;
			
		// get the pen and the background mode correct
		pens.addSelectObjectRecords(records,
			currentPenIsDashed ?
			new CreateIndirectPenRecord(CreateIndirectPenRecord.PS_DOT, (int) Math.round(currentPenWidth), currentColor) :
			new CreateIndirectPenRecord(CreateIndirectPenRecord.PS_SOLID, (int) Math.round(currentPenWidth), currentColor));
		backgroundModes.addSetObjectRecords(records, new SetBackgroundModeRecord(false));

		if (loggingEnabled)
			System.out.println(">>>> testg2d: draw");

		List<Segment> segments = extractSegmentsFromShape(s);

		PolyLineRecord poly = new PolyLineRecord();
		for (Segment segment : segments)
			poly.addPoint(segment.points[0]);
		records.addRecord(poly);
	}
	
	/**
	 * @see java.awt.Graphics#drawArc(int, int, int, int, int, int)
	 */
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawArc");
//		real.drawArc(x,y,width,height,startAngle,arcAngle);
	}

	/**
	 * @see java.awt.Graphics2D#drawGlyphVector(GlyphVector, float, float)
	 */
	public void drawGlyphVector(GlyphVector g, float x, float y)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: ");
//		real.drawGlyphVector(g,x,y);
	}

	/**
	 * @see java.awt.Graphics2D#drawImage(BufferedImage, BufferedImageOp, int, int)
	 */
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage");
//		real.drawImage(img,op,x,y);
	}

	/**
	 * @see java.awt.Graphics2D#drawImage(Image, AffineTransform, ImageObserver)
	 */
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage2");
		return real.drawImage(img,xform,obs);
	}

	/**
	 * @see java.awt.Graphics#drawImage(Image, int, int, Color, ImageObserver)
	 */
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage3");
		return real.drawImage(img,x,y,bgcolor,observer);
	}

	/**
	 * @see java.awt.Graphics#drawImage(Image, int, int, ImageObserver)
	 */
	public boolean drawImage(Image img, int x, int y, ImageObserver observer)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage4");
		return real.drawImage(img,x,y,observer);
	}

	/**
	 * @see java.awt.Graphics#drawImage(Image, int, int, int, int, Color, ImageObserver)
	 */
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage5");
		return real.drawImage(img,x,y,width,height,bgcolor,observer);
	}

	/**
	 * @see java.awt.Graphics#drawImage(Image, int, int, int, int, ImageObserver)
	 */
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage6");
		return real.drawImage(img,x,y,width,height,observer);
	}

	/**
	 * @see java.awt.Graphics#drawImage(Image, int, int, int, int, int, int, int, int, Color, ImageObserver)
	 */
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage7");
		return real.drawImage(img,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,bgcolor,observer);
	}

	/**
	 * @see java.awt.Graphics#drawImage(Image, int, int, int, int, int, int, int, int, ImageObserver)
	 */
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawImage8");
		return real.drawImage(img,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,observer);
	}

	/**
	 * @see java.awt.Graphics#drawLine(int, int, int, int)
	 */
	public void drawLine(int x1, int y1, int x2, int y2)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawLine");
//		real.drawLine(x1,y1,x2,y2);
	}

	/**
	 * @see java.awt.Graphics#drawOval(int, int, int, int)
	 */
	public void drawOval(int x, int y, int width, int height)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawOval");
//		real.drawOval(x,y,width,height);
	}

	/**
	 * @see java.awt.Graphics#drawPolygon(int[], int[], int)
	 */
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawPolyGon");
//		real.drawPolygon(xPoints,yPoints,nPoints);
	}

	/**
	 * @see java.awt.Graphics#drawPolyline(int[], int[], int)
	 */
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawPolyLine");
//		real.drawPolyline(xPoints,yPoints,nPoints);
	}

	/**
	 * @see java.awt.Graphics2D#drawRenderableImage(RenderableImage, AffineTransform)
	 */
	public void drawRenderableImage(RenderableImage img, AffineTransform xform)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawRenderableImage");
//		real.drawRenderableImage(img,xform);
	}

	/**
	 * @see java.awt.Graphics2D#drawRenderedImage(RenderedImage, AffineTransform)
	 */
	public void drawRenderedImage(RenderedImage img, AffineTransform xform)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawRenderedImage");
//		real.drawRenderedImage(img,xform);
	}

	/**
	 * @see java.awt.Graphics#drawRoundRect(int, int, int, int, int, int)
	 */
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawRoundRect");
//		real.drawRoundRect(x,y,width,height,arcWidth,arcHeight);
	}

	/**
	 * @see java.awt.Graphics2D#drawString(AttributedCharacterIterator, float, float)
	 */
	public void drawString(AttributedCharacterIterator iterator, float x, float y)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawString");
//		real.drawString(iterator, x, y);
	}

	/**
	 * @see java.awt.Graphics#drawString(AttributedCharacterIterator, int, int)
	 */
	public void drawString(AttributedCharacterIterator iterator, int x, int y)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawString2");
//		real.drawString(iterator,x,y);
	}

	/**
	 * @see java.awt.Graphics2D#drawString(String, float, float)
	 */
	public void drawString(String str, float x, float y)
	{
		drawSomeText(str, x, y);
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawString3");
//		real.drawString(str,x,y);
	}

	/**
	 * @see java.awt.Graphics#drawString(String, int, int)
	 */
	public void drawString(String str, int x, int y)
	{
		drawSomeText(str, x, y);
		if (loggingEnabled)
			System.out.println(">>>> testg2d: drawString4");
//		real.drawString(str,x,y);
	}

	private void drawSomeText(String str, double x, double y)
	{
		Color currentColor = real.getColor();
		textColors.addSetObjectRecords(records, new SetTextColorRecord(currentColor));
		int xPos = (int) Math.round(real.getTransform().getTranslateX());
		int yPos = (int) Math.round(real.getTransform().getTranslateY() + y - getFont().getSize());
		records.addRecord(new TextOutRecord(str, xPos, yPos));
	}

	/**
	 * @see java.awt.Graphics2D#fill(Shape)
	 */
	public void fill(Shape s)
	{
		// if we aren't filled correctly, then (possibly) generate and select a fill brush
		// make sure that the fill, brush etc are set correctly
		Color currentColor = real.getColor();
		if (currentColor.getAlpha() == 0)
			return;
		
		brushes.addSelectObjectRecords(records, new CreateIndirectBrushRecord(CreateIndirectBrushRecord.BS_SOLID, currentColor));
		pens.addSelectObjectRecords(records, new CreateIndirectPenRecord(CreateIndirectPenRecord.PS_NULL, 0, Color.BLACK));
		backgroundModes.addSetObjectRecords(records, new SetBackgroundModeRecord(true));
				
		List<Segment> segments = extractSegmentsFromShape(s);

		PolygonRecord poly = new PolygonRecord();
		for (Segment segment : segments)
			poly.addPoint(segment.points[0]);
		records.addRecord(poly);
		
		if (loggingEnabled)
			System.out.println(">>>> testg2d: fill");
//		real.fill(s);
	}

	/**
	 * @see java.awt.Graphics#fillArc(int, int, int, int, int, int)
	 */
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: fillArc");
//		real.fillArc(x,y,width,height,startAngle,arcAngle);
	}

	/**
	 * @see java.awt.Graphics#fillOval(int, int, int, int)
	 */
	public void fillOval(int x, int y, int width, int height)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: fillOval");
//		real.fillOval(x,y,width,height);
	}

	/**
	 * @see java.awt.Graphics#fillPolygon(int[], int[], int)
	 */
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: fillPolygon");
//		real.fillPolygon(xPoints,yPoints,nPoints);
	}

	/**
	 * @see java.awt.Graphics#fillRect(int, int, int, int)
	 */
	public void fillRect(int x, int y, int width, int height)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: fillRect");
//		real.fillRect(x,y,width,height);
	}

	/**
	 * @see java.awt.Graphics#fillRoundRect(int, int, int, int, int, int)
	 */
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: fillRoundRect");
//		real.fillRoundRect(x,y,width,height,arcWidth,arcHeight);
	}

	/**
	 * @see java.awt.Graphics2D#getBackground()
	 */
	public Color getBackground()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: fillBackground");
		return real.getBackground();
	}

	/**
	 * @see java.awt.Graphics#getClip()
	 */
	public Shape getClip()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getClip");
		return real.getClip();
	}

	/**
	 * @see java.awt.Graphics#getClipBounds()
	 */
	public Rectangle getClipBounds()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getClipBounds");
		return real.getClipBounds();
	}

	/**
	 * @see java.awt.Graphics#getColor()
	 */
	public Color getColor()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getColor");
		return real.getColor();
	}

	/**
	 * @see java.awt.Graphics2D#getComposite()
	 */
	public Composite getComposite()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getComposite");
		return real.getComposite();
	}

	/**
	 * @see java.awt.Graphics2D#getDeviceConfiguration()
	 */
	public GraphicsConfiguration getDeviceConfiguration()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getDeviceConfiguration");
		return real.getDeviceConfiguration();
	}

	/**
	 * @see java.awt.Graphics#getFont()
	 */
	public Font getFont()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getFont");
		return real.getFont();
	}

	/**
	 * @see java.awt.Graphics#getFontMetrics(Font)
	 */
	public FontMetrics getFontMetrics(Font f)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getFontMetrics");
		return real.getFontMetrics();
	}

	/**
	 * @see java.awt.Graphics2D#getFontRenderContext()
	 */
	public FontRenderContext getFontRenderContext()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getFontRenderContext");
		return real.getFontRenderContext();
	}

	/**
	 * @see java.awt.Graphics2D#getPaint()
	 */
	public Paint getPaint()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getPaint");
		return real.getPaint();
	}

	/**
	 * @see java.awt.Graphics2D#getRenderingHint(Key)
	 */
	public Object getRenderingHint(Key hintKey)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getRenderingHint");
		return real.getRenderingHint(hintKey);
	}

	/**
	 * @see java.awt.Graphics2D#getRenderingHints()
	 */
	public RenderingHints getRenderingHints()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getRenderingHints");
		return real.getRenderingHints();
	}

	/**
	 * @see java.awt.Graphics2D#getStroke()
	 */
	public Stroke getStroke()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getStroke");
		return real.getStroke();
	}

	/**
	 * @see java.awt.Graphics2D#getTransform()
	 */
	public AffineTransform getTransform()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: getTransform");
		return real.getTransform();
	}

	/**
	 * @see java.awt.Graphics2D#hit(Rectangle, Shape, boolean)
	 */
	public boolean hit(Rectangle rect, Shape s, boolean onStroke)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: hit");
		return real.hit(rect,s,onStroke);
	}

	/**
	 * @see java.awt.Graphics2D#rotate(double, double, double)
	 */
	public void rotate(double theta, double x, double y)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: rotate");
		rotationTheta = theta;
		rotationPoint = new UPoint(x, y);
		real.rotate(theta,x,y);
	}

	/**
	 * @see java.awt.Graphics2D#rotate(double)
	 */
	public void rotate(double theta)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: rotate2");
		rotationTheta = theta;
		rotationPoint = new UPoint(0, 0);
		real.rotate(theta);
	}

	/**
	 * @see java.awt.Graphics2D#scale(double, double)
	 */
	public void scale(double sx, double sy)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: scale");
		real.scale(sx,sy);
	}

	/**
	 * @see java.awt.Graphics2D#setBackground(Color)
	 */
	public void setBackground(Color color)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setBackground");
		real.setBackground(color);
	}

	/**
	 * @see java.awt.Graphics#setClip(int, int, int, int)
	 */
	public void setClip(int x, int y, int width, int height)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setClip");
		real.setClip(x,y,width,height);
	}

	/**
	 * @see java.awt.Graphics#setClip(Shape)
	 */
	public void setClip(Shape clip)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setClip2");
		real.setClip(clip);
	}

	/**
	 * @see java.awt.Graphics#setColor(Color)
	 */
	public void setColor(Color c)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setColor");
		real.setColor(c);
	}

	/**
	 * @see java.awt.Graphics2D#setComposite(Composite)
	 */
	public void setComposite(Composite comp)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setComposite");
		real.setComposite(comp);
	}

	/**
	 * @see java.awt.Graphics#setFont(Font)
	 */
	public void setFont(Font font)
	{
		if (font == null)
			return;
			
		fonts.addSelectObjectRecords(records, new CreateIndirectFontRecord(-font.getSize(), font.isBold(), font.isItalic(), false, font.getName()));
	
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setFont " + font);
		real.setFont(font);
	}

	/**
	 * @see java.awt.Graphics2D#setPaint(Paint)
	 */
	public void setPaint(Paint paint)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setPaint");
		real.setPaint(paint);
	}

	/**
	 * @see java.awt.Graphics#setPaintMode()
	 */
	public void setPaintMode()
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setPaintMode");
		real.setPaintMode();
	}

	/**
	 * @see java.awt.Graphics2D#setRenderingHint(Key, Object)
	 */
	public void setRenderingHint(Key hintKey, Object hintValue)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setRenderingHint");
		real.setRenderingHint(hintKey,hintValue);
	}

	/**
	 * @see java.awt.Graphics2D#setRenderingHints(Map)
	 */
	public void setRenderingHints(Map hints)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setRenderingHints");
		real.setRenderingHints(hints);
	}

	/**
	 * @see java.awt.Graphics2D#setStroke(Stroke)
	 */
	public void setStroke(Stroke s)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setStroke: " + s);
		if (s instanceof BasicStroke)
		{
			BasicStroke b = (BasicStroke) s;
			currentPenWidth = b.getLineWidth();
			if (currentPenWidth <= 1)
				currentPenWidth = 0;
			float[] dashes = b.getDashArray();
			currentPenIsDashed = dashes != null;
			if (currentPenIsDashed)
				currentPenWidth = 0;
		}
		real.setStroke(s);
	}

	/**
	 * @see java.awt.Graphics2D#setTransform(AffineTransform)
	 */
	public void setTransform(AffineTransform Tx)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setTransform");
		real.setTransform(Tx);
	}

	/**
	 * @see java.awt.Graphics#setXORMode(Color)
	 */
	public void setXORMode(Color c1)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: setXORMode");
		real.setXORMode(c1);
	}

	/**
	 * @see java.awt.Graphics2D#shear(double, double)
	 */
	public void shear(double shx, double shy)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: shear");
		real.shear(shx,shy);
	}

	/**
	 * @see java.awt.Graphics2D#transform(AffineTransform)
	 */
	public void transform(AffineTransform Tx)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: transform");
		real.transform(Tx);
	}

	/**
	 * @see java.awt.Graphics2D#translate(double, double)
	 */
	public void translate(double tx, double ty)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: translate");
		real.translate(tx,ty);
	}

	/**
	 * @see java.awt.Graphics#translate(int, int)
	 */
	public void translate(int x, int y)
	{
		if (loggingEnabled)
			System.out.println(">>>> testg2d: translate");
		real.translate(x,y);
	}
	
	/**
	 * Returns the loggingEnabled.
	 * @return boolean
	 */
	public boolean isLoggingEnabled()
	{
		return loggingEnabled;
	}

	/**
	 * Sets the loggingEnabled.
	 * @param loggingEnabled The loggingEnabled to set
	 */
	public void setLoggingEnabled(boolean loggingEnabled)
	{
		this.loggingEnabled = loggingEnabled;
	}

}

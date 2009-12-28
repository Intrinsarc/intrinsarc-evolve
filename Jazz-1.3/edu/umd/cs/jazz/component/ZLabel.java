/**
 * Copyright (C) 1998-1999 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.component;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.font.GlyphMetrics;
import java.io.*;
import java.util.*;
import java.awt.font.TextAttribute;
import java.text.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.io.*;
import edu.umd.cs.jazz.util.*;

/**
 * <b>ZLabel</b> creates a lightweight visual component to support a label containing
 * one line of text.
 * The label object is positioned so that its upper-left corner is at the origin,
 * but this position can be altered with the setTranslate() methods.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 */
public class ZLabel
	extends ZVisualComponent
	implements ZPenColor, Serializable {

	/**
	 * The low quality graphic2D render context: not antiAliased, and
	 * does not use FractionalMetrics.
	 */
	static protected final FontRenderContext LOW_QUALITY_FONT_CONTEXT =
		new FontRenderContext(null, false, false);

	/**
	 * The high quality graphic2D render context: AntiAliased, and
	 * uses FractionalMetrics.
	 */
	static protected final FontRenderContext HIGH_QUALITY_FONT_CONTEXT =
		new FontRenderContext(null, true, true);

	/**
	 * Default font name of text.
	 */
	static protected final String DEFAULT_FONT_NAME = "Helvetica";

	/**
	 * Default font style for text.
	 */
	static protected final int DEFAULT_FONT_STYLE = Font.PLAIN;

	/**
	 * Default font size for text.
	 */
	static protected final int DEFAULT_FONT_SIZE = 12;

	/**
	 * Default font for text.
	 */
	static protected final Font DEFAULT_FONT =
		new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE);

	/**
	 * Default color for text.
	 */
	static protected final Color DEFAULT_PEN_COLOR = Color.black;

	/**
	 * Default background color for label.
	 */
	static protected final Color DEFAULT_BACKGROUND_COLOR = null;

	/**
	 * Default text when new text area is created.
	 */
	static protected final String DEFAULT_TEXT = "";

	/**
	 * Current pen color.
	 */
	protected Color penColor = DEFAULT_PEN_COLOR;

	/**
	 * Background color for label.
	 */
	protected Color backgroundColor = DEFAULT_BACKGROUND_COLOR;

	/**
	 * Current text font.
	 */
	protected Font font = DEFAULT_FONT;

	/**
	 * A label can hold one line of text.
	 */
	protected String line = null;

	/**
	 * jdk version <= 1.2.1 has a bug: font.getStringBounds() gives the
	 * bounds of a space " " as zero.
	 */
	protected boolean boundsBug = false;

	/**
	 * Translation offset X.
	 */
	protected double translateX = 0.0;

	/**
	 * Translation offset Y.
	 */
	protected double translateY = 0.0;

	/**
	 * Default constructor for ZLabel.
	 */
	public ZLabel() {
		this("", DEFAULT_FONT);
	}

	/**
	 * ZLabel constructor with initial text.
	 * @param <code>str</code> The initial text.
	 */
	public ZLabel(String str) {
		this(str, DEFAULT_FONT);
	}

	/**
	 * ZLabel constructor with initial text and font.
	 * @param <code>str</code> The initial text.
	 * @param <code>font</code> The font for this ZLabel component.
	 */
	public ZLabel(String str, Font font) {
		if ((System.getProperty("java.version").equals("1.2"))
			|| (System.getProperty("java.version").equals("1.2.1"))) {
			boundsBug = true;
		}

		setText(str);
		this.font = font;

		reshape();
	}

	/**
	 * Returns a clone of this object.
	 *
	 * @see ZSceneGraphObject#duplicateObject
	 */
	protected Object duplicateObject() {
		ZLabel newText = (ZLabel) super.duplicateObject();

		// Copy the line of text.
		newText.line = line;

		return newText;
	}

	//****************************************************************************
	//
	//			Get/Set and pairs
	//
	//***************************************************************************

	/**
	 * Returns the current pen color.
	 */
	public Color getPenColor() {
		return penColor;
	}

	/**
	 * Sets the current pen color.
	 * @param <code>color</code> use this color.
	 */
	public void setPenColor(Color color) {
		penColor = color;
		repaint();
	}

	/**
	 * Returns the current background color.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the current background color.
	 * @param <code>color</code> use this color.
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		repaint();
	}

	/**
	 * Returns the current font.
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Return the text within this label component.
	 */
	public String getText() {
		return line;
	}

	/**
	 * Sets the font for the text.
	 * <p>
	 * <b>Warning:</b> Java has a serious bug in that it does not support very small 
	 * fonts.  In particular, fonts that are less than about a pixel high just don't work.
	 * Since in Jazz, it is common to create objects of arbitrary sizes, and then scale them,
	 * an application can easily create a text object with a very small font by accident.
	 * The workaround for this bug is to create a larger font for the text object, and 
	 * then scale the node down correspondingly.
	 * @param <code>aFont</code> use this font.
	 */
	public void setFont(Font aFont) {
		font = aFont;
		reshape();
	}

	/**
	 * Sets the text of this visual component to str.
	 */
	public void setText(String str) {
		line = str;
		reshape();
	}

	/**
	 * Set label translation offset X.
	 * @param x the X translation.
	 */
	public void setTranslateX(double x) {
		setTranslation(x, translateY);
	}

	/**
	 * Get the X offset translation.
	 * @return the X translation.
	 */
	public double getTranslateX() {
		return translateX;
	}

	/**
	 * Set label translation offset Y.
	 * @param y the Y translation.
	 */
	public void setTranslateY(double y) {
		setTranslation(translateX, y);
	}

	/**
	 * Get the Y offset translation.
	 * @return the Y translation.
	 */
	public double getTranslateY() {
		return translateY;
	}

	/**
	 * Set the label translation offset to the specified position.
	 * @param x the X-coord of translation
	 * @param y the Y-coord of translation
	 */
	public void setTranslation(double x, double y) {
		translateX = x;
		translateY = y;
		reshape();
	}

	/**
	 * Set the label translation offset to point p.
	 * @param p The translation offset.
	 */
	public void setTranslation(Point2D p) {
		setTranslation(p.getX(), p.getY());
	}

	/**
	 * Get the text translation offset.
	 * @return The translation offset.
	 */
	public Point2D getTranslation() {
		Point2D p = new Point2D.Double(translateX, translateY);
		return p;
	}

	/**
	 * Renders the label object
	 * <p>
	 * The transform, clip, and composite will be set appropriately when this object
	 * is rendered.  It is up to this object to restore the transform, clip, and composite of
	 * the Graphics2D if this node changes any of them. However, the color, font, and stroke are
	 * unspecified by Jazz.  This object should set those things if they are used, but
	 * they do not need to be restored.
	 *
	 * @param <code>renderContext</code> Contains information about current render.
	 */
	public void render(ZRenderContext renderContext) {
		Graphics2D g2 = renderContext.getGraphics2D();
		AffineTransform at = null;
		boolean translated = false;
		if (line != null) {

			if ((translateX != 0.0) || (translateY != 0.0)) {
				at = g2.getTransform(); // save transform
				g2.translate(translateX, translateY);
				translated = true;
			}

			if (backgroundColor != null) {
				g2.setColor(backgroundColor);
				Rectangle2D rect =
					new Rectangle2D.Double(
						bounds.getX(),
						bounds.getY(),
						bounds.getWidth(),
						bounds.getHeight());
				g2.fill(rect);
			}

			// BBB: HACK ALERT - July 30, 1999
			// This is a workaround for a bug in Sun JDK 1.2.2 where
			// fonts that are rendered at very small magnifications show up big!
			// If the font is too small and not antialiased
			// don't display it.
			if (line.length() > 0 && (font.getSize() * renderContext.getCompositeMagnification()) >= 0.5) {
				g2.setColor(penColor);
				g2.setFont(font);
				FontRenderContext frc = g2.getFontRenderContext();
				LineMetrics lm = font.getLineMetrics(line, frc);
				g2.drawString(line, 0, lm.getAscent());
			}
			if (translated) {
				g2.setTransform(at); // restore transform
			}
		}
	}

	/**
	 * Notifies this object that it has changed and that it
	 * should update its notion of its bounding box.
	 */
	protected void computeBounds() {
		Rectangle2D rect = null;
		Rectangle2D bugBounds = null;
		double lineWidth;
		double maxWidth = 0.0;
		double maxHeight = 0.0;
		double height;
		FontRenderContext frc = LOW_QUALITY_FONT_CONTEXT;

		// We want to find the greatest bounds of the text in
		// low and high quality, so we get the max width and height
		// of the line checking both quality levels.
		for (int loop = 0; loop < 2; loop++) {
			// First, check width
			// If the only text is "" then it still needs a width
			if ((line != null) && !line.equals("")) {

				rect = font.getStringBounds(line, frc);
				lineWidth = rect.getWidth();

				if ((boundsBug) && (line.endsWith(" ")))
					lineWidth =
						font
							.getStringBounds(
								(line.substring(0, line.length() - 1)) + 't',
								frc)
							.getWidth();

				if (lineWidth > maxWidth) {
					maxWidth = lineWidth;
				}

				LineMetrics lm = font.getLineMetrics(line, frc);
				height = lm.getAscent() + lm.getDescent();

			} else {
				// If no text, then we want to have the bounds of a space character, 
				// so get those bounds here
				if (boundsBug) {
					rect = font.getStringBounds("t", frc);
				} else {
					rect = font.getStringBounds(" ", frc);
				}
				maxWidth = rect.getWidth();
				height = rect.getHeight();
			}

			if (maxHeight < height) {
				maxHeight = height;
			}

			frc = HIGH_QUALITY_FONT_CONTEXT;
		}
		// Finally, set the bounds of this text
		bounds.setRect(translateX, translateY, maxWidth, maxHeight);
	}

	/////////////////////////////////////////////////////////////////////////
	//
	// Saving
	//
	/////////////////////////////////////////////////////////////////////////

	/**
	 * Set some state of this object as it gets read back in.
	 * After the object is created with its default no-arg constructor,
	 * this method will be called on the object once for each bit of state
	 * that was written out through calls to ZObjectOutputStream.writeState()
	 * within the writeObject method.
	 * @param fieldType The fully qualified type of the field
	 * @param fieldName The name of the field
	 * @param fieldValue The value of the field
	 */
	public void setState(
		String fieldType,
		String fieldName,
		Object fieldValue) {
		super.setState(fieldType, fieldName, fieldValue);

		if (fieldName.compareTo("penColor") == 0) {
			setPenColor((Color) fieldValue);
		} else if (fieldName.compareTo("backgroundColor") == 0) {
			setBackgroundColor((Color) fieldValue);
		} else if (fieldName.compareTo("font") == 0) {
			setFont((Font) fieldValue);
		} else if (fieldName.compareTo("text") == 0) {
			setText((String) fieldValue);
		} else if (fieldName.compareTo("translateX") == 0) {
			setTranslateX(((Double) fieldValue).doubleValue());
		} else if (fieldName.compareTo("translateY") == 0) {
			setTranslateY(((Double) fieldValue).doubleValue());
		}

	}

	/**
	 * Write out all of this object's state.
	 * @param out The stream that this object writes into
	 */
	public void writeObject(ZObjectOutputStream out) throws IOException {
		super.writeObject(out);

		if ((penColor != null) && (penColor != DEFAULT_PEN_COLOR)) {
			out.writeState("java.awt.Color", "penColor", penColor);
		}
		if ((backgroundColor != null)
			&& (backgroundColor != DEFAULT_BACKGROUND_COLOR)) {
			out.writeState(
				"java.awt.Color",
				"backgroundColor",
				backgroundColor);
		}
		if (getFont() != DEFAULT_FONT) {
			out.writeState("java.awt.Font", "font", getFont());
		}
		if (translateX != 0.0d) {
			out.writeState("Double", "translateX", new Double(translateX));
		}
		if (translateY != 0.0d) {
			out.writeState("Double", "translateY", new Double(translateY));
		}
		if (getText() != DEFAULT_TEXT) {
			out.writeState("String", "text", getText());
		}
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}

}

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
 * <b>ZText</b> creates a visual component to support text. Multiple lines can
 * be entered, and basic editing is supported. A caret is drawn,
 * and can be repositioned with mouse clicks.  The text object is positioned
 * so that its upper-left corner is at the origin, though this can be changed
 * with the translate methods.
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 */
public class ZText extends ZVisualComponent implements ZPenColor, ZPenPaint, Serializable {

    /**
     * The low quality graphic2D render context: not antiAliased, and
     * does not use FractionalMetrics.
     */
    static protected final FontRenderContext LOW_QUALITY_FONT_CONTEXT = new FontRenderContext(null, false, false);

    /**
     * The high quality graphic2D render context: AntiAliased, and
     * uses FractionalMetrics.
     */
    static protected final FontRenderContext HIGH_QUALITY_FONT_CONTEXT = new FontRenderContext(null, true, true);

    /**
     * Below this magnification render text as 'greek'.
     */
    static protected final double   DEFAULT_GREEK_THRESHOLD = 5.5;

    /**
     * Default color of text rendered as 'greek'.
     */
    static protected final Color   DEFAULT_GREEK_COLOR = Color.gray;

    /**
     * Default font name of text.
     */
    static protected final String  DEFAULT_FONT_NAME = "Helvetica";

    /**
     * Default font style for text.
     */
    static protected final int     DEFAULT_FONT_STYLE = Font.PLAIN;

    /**
     * Default font size for text.
     */
    static protected final int     DEFAULT_FONT_SIZE = 12;

    /**
     * Default font for text.
     */
    static protected final Font    DEFAULT_FONT = new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE);

    /**
     * Default color for text.
     */
    static protected final Color   DEFAULT_PEN_COLOR = Color.black;

    /**
     * Default background color for text.
     */
    static protected final Color   DEFAULT_BACKGROUND_COLOR = null;

    /**
     * Default caret color for text.
     */

    static protected final Color   DEFAULT_CARET_COLOR = Color.red;

    /**
     * Default specifying if text is editable.
     */
    static protected final boolean DEFAULT_EDITABLE = false;

    /**
     * Default text when new text area is created.
     */
    static protected final String  DEFAULT_TEXT = "";

    /**
     * Below this magnification text is rendered as greek.
     */
    protected double             greekThreshold = DEFAULT_GREEK_THRESHOLD;

    /**
     * Color for greek text.
     */
    protected Color             greekColor = DEFAULT_GREEK_COLOR;

    /**
     * Current pen color.
     */
    protected Color             penColor  = DEFAULT_PEN_COLOR;

    /**
     * Background color for text.
     */
    protected Color             backgroundColor = DEFAULT_BACKGROUND_COLOR;

    /**
     * Current caret color.
     */
    protected Color             caretColor = DEFAULT_CARET_COLOR;

    /**
     * Character position of caret within the current line.
     */

    protected int                caretPos = 0;
    /**
     * Line number of caret - current line.
     */

    protected int                caretLine = 0;
    /**
     * X coordinate of caret relative to its coordinate frame.
     */

    protected double             caretX = 0.0;
    /**
     * Y coordinate of caret relative to its coordinate frame.
     */

    protected double             caretY = 0.0;
    /**
     * Drawn shape of the caret.
     */

    protected transient Line2D             caretShape = new Line2D.Double();

    /**
     * Current text font.
     */
    protected Font               font = DEFAULT_FONT;

    /**
     * Each vector element is one line of text.
     */
    protected ArrayList            lines = new ArrayList();

    /**
     * Specifies if text is editable.
     */
    protected boolean           editable = DEFAULT_EDITABLE;

    /**
     * The previously used font render context (i.e., from the last render).
     */
    protected transient FontRenderContext prevFRC = null;

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
     * Default constructor for ZText.
     */
    public ZText() {
        this("", DEFAULT_FONT);
    }

    /**
     * ZText constructor with initial text.
     * @param <code>str</code> The initial text.
     */
    public ZText(String str) {
        this(str, DEFAULT_FONT);
    }

    /**
     * ZText constructor with initial text and font.
     * @param <code>str</code> The initial text.
     * @param <code>font</code> The font for this ZText component.
     */
    public ZText(String str, Font font) {
        if ((System.getProperty("java.version").equals("1.2")) ||
            (System.getProperty("java.version").equals("1.2.1"))) {
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
        ZText newText = (ZText)super.duplicateObject();

                                // Copy the lines vector.
        newText.lines = (ArrayList)lines.clone();

        return newText;
    }


    //****************************************************************************
    //
    //                  Get/Set and pairs
    //
    //***************************************************************************

    /**
     * Returns the current pen color.
     */
    public Color getPenColor() {return penColor;}

    /**
     * Sets the current pen color.
     * @param <code>color</code> use this color.
     */
    public void setPenColor(Color color) {
        penColor = color;
        repaint();
    }

    /**
     * Returns the current pen paint.
     */
    public Paint getPenPaint() {
        return penColor;
    }

    /**
     * Sets the current pen paint.
     * @param <code>aPaint</code> use this paint.
     */
    public void setPenPaint(Paint aPaint) {
        penColor = (Color)aPaint;
    }

    /**
     * Returns the current background color.
     */
    public Color getBackgroundColor() {return backgroundColor;}

    /**
     * Sets the current background color.
     * @param <code>color</code> use this color.
     */
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
        repaint();
    }

    /**
     * Returns the current caret color.
     */
    public Color getCaretColor() {return caretColor;}

    /**
     * Sets the current caret color.
     * @param <code>color</code> use this color.
     */
    public void setCaretColor(Color color) {
        caretColor = color;
        repaint();
    }

    /**
     * Returns the current greek threshold. Below this magnification
     * text is rendered as 'greek'.
     */
    public double getGreekThreshold() {return greekThreshold;}

    /**
     * Sets the current greek threshold. Below this magnification
     * text is rendered as 'greek'.
     * @param <code>threshold</code> compared to renderContext magnification.
     */
    public void setGreekThreshold(double threshold) {
        greekThreshold = threshold;
        repaint();
    }

    /**
     * Determines if this text is editable.
     */
    public boolean getEditable() {return editable;}

    /**
     * Specifies whether this text is editable.
     * @param <code>editable</code> true or false.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
        repaint();
    }

    /**
     * Returns the current font.
     */
    public Font getFont() {return font;}

    /**
     * Return the text within this text component.
     * Multline text is returned as a single string
     * where each line is separated by a newline character.
     * Single line text does not have any newline characters.
     */
    public String getText() {
        String line;
        String result = new String();
        int lineNum = 0;

        for (Iterator i = lines.iterator() ; i.hasNext() ; ) {
            if (lineNum > 0) {
                result += '\n';
            }
            line = (String)i.next();
            result += line;
            lineNum++;
        }

        return result;
    }

    /**
       Returns the character position of the caret, within the current line.
    */
    public int getCaretPos() {
        return caretPos;
    }

    /**
     * Returns the current line.
     */
    public int getCaretLine() {
        return caretLine;
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
     * Sets the text of this visual component to str. Multiple lines
     * of text are separated by a newline character.
     * @param <code>str</code> use this string.
     */
    public void setText(String str) {
        int pos = 0;
        int index;
        boolean done = false;
        lines = new ArrayList();
        do {
            index = str.indexOf('\n', pos);
            if (index == -1) {
                lines.add(str);
                done = true;
            } else {
                lines.add(str.substring(0, index));
                str = str.substring(index + 1);
            }
        } while (!done);

        reshape();
    }

    /**
     * Adds a character before the caret position.
     * @param <code>c</code>Character to add.
     */
    public void addChar(char c) {
        String frontHalf = ((String)lines.get(caretLine)).substring(0, caretPos);
        String backHalf = ((String)lines.get(caretLine)).substring(caretPos);

        lines.set(caretLine, frontHalf + c + backHalf);
        caretPos++;
        reshape();
    }

    /**
     * Creates a new line of text, splitting the current line at the caret
     * position.
     */
    public void addEnterChar() {
        String frontHalf = ((String)lines.get(caretLine)).substring(0, caretPos);
        String backHalf = ((String)lines.get(caretLine)).substring(caretPos);

        lines.set(caretLine, frontHalf);
        caretLine++;
        lines.add(caretLine, backHalf);
        caretPos = 0;
        reshape();
    }

    /**
     * Deletes the character after the caret position.
     */
    public void deleteChar() {
        String currentLine = (String)lines.get(caretLine);
        if (caretPos == currentLine.length()) {
                                // At end of line, so merge with next line
            if (caretLine < (lines.size() - 1)) {
                lines.set(caretLine, currentLine + (String)lines.get(caretLine + 1));
                lines.remove(caretLine + 1);
            }
        } else {
            String frontHalf = currentLine.substring(0, caretPos);
            String backHalf = currentLine.substring(caretPos + 1);

            lines.set(caretLine, frontHalf + backHalf);
        }
        reshape();
    }

    /**
     * Deletes the character before the caret position.
     */
    public void deleteCharBeforeCaret() {
        if ((caretPos > 0) || (caretLine > 0)) {
            setCaretPos(getCaretPos() - 1);
            deleteChar();
        }
    }

    /**
     * Deletes from the caret position to the end of line.
     * If caret is at the end of the line, joins current line to the next.
     */
    public void deleteToEndOfLine() {
        if (caretPos == ((String)lines.get(caretLine)).length()) {
                                // Delete carriage return at end of line
            deleteChar();
        } else {
                                // Else, Delete to end of line
            String frontHalf = ((String)lines.get(caretLine)).substring(0, caretPos);
            lines.set(caretLine, frontHalf);
            reshape();
        }
    }

    /**
     * Sets the caretLine to line, if it exists.
     *  @param <code>line</code>Line number to use. Count starts
     * with zero.
     */
    public void setCaretLine(int line) {
        if (line < 0) {
            caretLine = 0;
        } else if (line >= lines.size()) {
            caretLine = lines.size()-1;
        } else {
            caretLine = line;
        }

        // new line may be too short for current caret position
        setCaretPos(getCaretPos());
        repaint();
    }


    /**
     * Set the caret this character position in the current line.
     *  @param <code>cp</code>Character position to use, starts with zero.
     */
    public void setCaretPos(int cp) {
        if (cp < 0) {
            if (caretLine > 0) {
                caretLine--;
                caretPos = ((String)lines.get(caretLine)).length();
            } else {
                caretPos = 0;
            }
        } else if (cp > ((String)lines.get(caretLine)).length()) {
            if (caretLine < (lines.size() - 1)) {
                caretLine++;
                caretPos = 0;
            } else {
                caretPos = ((String)lines.get(caretLine)).length();
            }
        } else {
            caretPos = cp;
        }
        repaint();
    }

    /**
     * Set caret position to character closest to specified point (in object coords)
     *  @param <code>pt</code> object coordinates of a mouse click.
     */
    public void setCaretPos(Point2D pt) {
        LineMetrics lm = font.getLineMetrics((String)lines.get(0), prevFRC);
        double height = lm.getHeight();
        double desc = lm.getDescent();
        caretLine = (int)((pt.getY()-desc)/height);
        if (pt.getY() < desc) caretLine = 0;
        if (caretLine >= lines.size()) caretLine = lines.size() - 1;

        Rectangle2D bounds = null;
        double strWid, charWid;
        String textLine = (String)lines.get(caretLine);
        String substr;
        caretPos = textLine.length();
        for (int ch=0; ch < textLine.length(); ch++) {
            substr = textLine.substring(0,ch);
            bounds = font.getStringBounds(substr, prevFRC);
            strWid = bounds.getWidth();
            charWid = font.getStringBounds(textLine.substring(ch, ch+1), prevFRC).getWidth();
            // jkd version <= 1.2.1 bug:
            // bounds of a space " " returned as zero
            if (boundsBug) {
                if ((substr != null) && (substr.length() > 0) &&
                    (substr.charAt(substr.length()-1) == ' ')) {
                    strWid += font.getStringBounds("t", prevFRC).getWidth();
                    charWid = font.getStringBounds("t", prevFRC).getWidth();
                }
            }

                                // position cursor before or after character clicked on
            if (strWid > pt.getX()) {
                if (pt.getX() > strWid - (charWid / 2)) {
                    caretPos = ch;
                } else {
                    caretPos = ch - 1;
                    if (caretPos < 0) {
                        caretPos = 0;
                    }
                }
                break;
            }
        }
        repaint();
    }

    /**
     * Set text translation offset X.
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
     * Set text translation offset Y.
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
     * Set the text translation offset to the specified position.
     * @param x the X-coord of translation
     * @param y the Y-coord of translation
     */
    public void setTranslation(double x, double y) {
        translateX = x;
        translateY = y;
        reshape();
    }

    /**
     * Set the text translation offset to point p.
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

    //****************************************************************************
    //
    //  Keyboard event handler
    //
    //***************************************************************************

    /** Processes keyboard events. Implements basic text editing
     * and cursor movement.
     * @param <code>e</code> keyboard event object.
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

                                // Skip modifier key movement
        if ((keyCode == KeyEvent.VK_SHIFT) ||
            (keyCode == KeyEvent.VK_CONTROL) ||
            (keyCode == KeyEvent.VK_ALT) ||
            (keyCode == KeyEvent.VK_META)) {
            return;
        }

        if (e.isControlDown()) {
                                // Control key down
            if (keyCode == KeyEvent.VK_LEFT) {
                                // LEFT (back one word)
                                // left skip over any blank spaces before skipping word
                if (getCaretPos() > 0) {
                    int startPos = getCaretPos() - 1;
                    char c = ((String)lines.get(caretLine)).charAt(startPos);
                    while ((c == ' ') && (startPos > 0)) {
                        c = ((String)lines.get(caretLine)).charAt(startPos);
                        startPos--;
                    }

                    int nextPos = 0;
                    boolean foundSpace = false;
                    for (int i = startPos; i >= 0; i--) {
                        c = ((String)lines.get(caretLine)).charAt(i);
                        if (c == ' ') {
                            nextPos = i+1;
                            break;
                        }
                    }
                    setCaretPos(nextPos);
                }
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                                // RIGHT (forward one word)
                int strLen = ((String)lines.get(caretLine)).length();
                if (getCaretPos() < strLen) {
                    int nextPos = strLen;
                    boolean foundSpace = false;
                                // if next character is a space, then
                                //   skip any spaces to beginning of next word
                                // else skip current word then any spaces.
                    for (int i = getCaretPos(); i < strLen; i++) {
                        char c = ((String)lines.get(caretLine)).charAt(i);
                        if (c == ' ') {
                            foundSpace = true;
                        }
                        if ((foundSpace == true) && (c != ' ')) {
                            nextPos = i;
                            break;
                        }
                    }
                    setCaretPos(nextPos);
                }
            } else if (keyCode == KeyEvent.VK_UP) {
                                // UP (up one line)
                setCaretLine(getCaretLine() - 1);
            } else if (keyCode == KeyEvent.VK_DOWN) {
                                // DOWN (down one line)
                setCaretLine(getCaretLine() + 1);
            }
        } else if (e.isAltDown()) {
                                // Alt key down
        } else {
                                // no modifiers down
            if (keyCode == KeyEvent.VK_LEFT) {
                                // LEFT (back one character)
                setCaretPos(getCaretPos() - 1);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                                // RIGHT (forward one character)
                setCaretPos(getCaretPos() + 1);
            } else if (keyCode == KeyEvent.VK_UP) {
                                // UP (up one line)
                setCaretLine(getCaretLine() - 1);
            } else if (keyCode == KeyEvent.VK_DOWN) {
                                // DOWN (down one line)
                setCaretLine(getCaretLine() + 1);
            } else if (keyCode == KeyEvent.VK_HOME) {
                                // HOME (beginning of line)
                setCaretPos(0);
            } else if (keyCode == KeyEvent.VK_END) {
                                // END (beginning of line)
                setCaretPos(((String)lines.get(caretLine)).length());
            } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                                // BACKSPACE (delete character before caret)
                deleteCharBeforeCaret();
            } else if (keyCode == KeyEvent.VK_DELETE) {
                                // DELETE (delete character after caret)
                deleteChar();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                addEnterChar();      // ENTER (add a line to text array)
            } else {
                                // Else, add a character
                addChar(keyChar);
            }
        }
    }

    /**
     * Renders the text object
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
        if (!lines.isEmpty()) {

            if ((translateX != 0.0) || (translateY != 0.0)) {
                at = g2.getTransform(); // save transform
                g2.translate(translateX, translateY);
                translated = true;
            }

                                // If font too small and not antialiased, then greek
            double renderedFontSize = font.getSize() * renderContext.getCompositeMagnification();
                                // BBB: HACK ALERT - July 30, 1999
                                // This is a workaround for a bug in Sun JDK 1.2.2 where
                                // fonts that are rendered at very small magnifications show up big!
                                // So, we render as greek if requested (that's normal)
                                // OR if the font is very small (that's the workaround)
            if ((renderedFontSize < 0.5) ||
                (renderedFontSize < greekThreshold) && (renderContext.getGreekText())) {
                paintAsGreek(renderContext);
            } else {
                paintAsText(renderContext);
            }
            if (translated) {
                g2.setTransform(at); // restore transform
            }
        }

        prevFRC = g2.getFontRenderContext();
    }

    /**
     * Paints this object as greek.
     * @param <code>renderContext</code> The graphics context to paint into.
     */
    public void paintAsGreek(ZRenderContext renderContext) {
            Graphics2D g2 = renderContext.getGraphics2D();

            if (greekColor != null) {
                g2.setColor(greekColor);
                Rectangle2D rect = new Rectangle2D.Double(0.0, 0.0, bounds.getWidth(), bounds.getHeight());
                g2.fill(rect);
            }
    }

    /**
     * Paints this object normally (show it's text).
     * Note that the entire text gets rendered so that it's upper
     * left corner appears at the origin of this local object.
     * @param <code>renderContext</code> The graphics context to paint into.
     */
    public void paintAsText(ZRenderContext renderContext) {
        Graphics2D g2 = renderContext.getGraphics2D();
        if (backgroundColor != null) {
            g2.setColor(backgroundColor);
            Rectangle2D rect = new Rectangle2D.Double(0.0, 0.0, bounds.getWidth(), bounds.getHeight());
            g2.fill(rect);
        }

                                // Get current font metrics information for multi-line and caret layout
        FontRenderContext frc = g2.getFontRenderContext();

                                // Render each line of text
                                // Note that the entire text gets rendered so that it's upper left corner
                                // appears at the origin of this local object.
        g2.setColor(penColor);
        g2.setFont(font);

        int lineNum = 0;
        String line;
        LineMetrics lm;
        GlyphVector gv;
        GlyphMetrics gm;
        double x, y;
        long startTime=0, endTime=0;
        int gLength;

        String character;
        Rectangle2D charBounds, lineSoFarBounds;

        for (Iterator i = lines.iterator() ; i.hasNext() ; ) {
            line = (String)i.next();

	    // ADDED BY LEG ON 2/25/03 - BUG CAUSING PROBLEMS AT CERTAIN
	    // SCALES WHEN LINE WAS EMPTY
	    line = (line.equals("")) ? " " : line;

            lm = font.getLineMetrics(line, frc);
            y = lm.getAscent() + (lineNum * lm.getHeight());

			if (line.length() > 0) {
            	g2.drawString(line, 0, (float)y);
			}
			
            lineNum++;
        }
                                // Draw the caret
        if (editable) {


            caretX = 0;
            String textLine = (String)lines.get(caretLine);
            lm = font.getLineMetrics(textLine, frc);
            if (caretPos > 0) {
                if ((boundsBug) && (textLine.substring(0,caretPos).endsWith(" "))) {
                    caretX = font.getStringBounds((textLine.substring(0, caretPos-1))+'t', frc).getWidth();
                } else {
                    caretX = font.getStringBounds(textLine, 0, caretPos, frc).getWidth();
                }
            }
            caretY = lm.getAscent() + (caretLine * lm.getHeight());

            g2.setColor(caretColor);
            g2.setStroke(new BasicStroke((float)(2.0 / renderContext.getCompositeMagnification()),
                                         BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            caretShape.setLine(caretX, caretY, caretX, (caretY - lm.getAscent()));
            g2.draw(caretShape);
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
                                // of a line checking both quality levels.
        for (int loop=0; loop<2; loop++) {
            height = 0.0;
                                // First, check width
                                // If the only text is "" then it still needs a width
            boolean hasText = true;
            if ((lines.size() == 1) && (((String)lines.get(0)).equals(""))) {
                hasText = false;
            }
            if (!lines.isEmpty() && hasText) {
                String line;
                LineMetrics lm;
                int lineNum = 0;
                for (Iterator i = lines.iterator() ; i.hasNext() ; ) {
                    line = (String)i.next();
                    lm = font.getLineMetrics(line, frc);

                                // Find the longest line in the text
                    rect = font.getStringBounds(line, frc);
                    lineWidth = rect.getWidth();

                    if ((boundsBug) && (line.endsWith(" ")))
                        lineWidth = font.getStringBounds((line.substring(0, line.length()-1))+'t', frc).getWidth();

                    if (lineWidth > maxWidth) {
                        maxWidth = lineWidth;
                    }
                                // Find the heighest line in the text
                    if (lineNum == 0) {
                        height += lm.getAscent() + lm.getDescent();
                    } else {
                        height += lm.getHeight();
                    }

                    lineNum++;
                }
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
    public void setState(String fieldType, String fieldName, Object fieldValue) {
        super.setState(fieldType, fieldName, fieldValue);

        if (fieldName.compareTo("penColor") == 0) {
            setPenColor((Color)fieldValue);
        } else if (fieldName.compareTo("backgroundColor") == 0) {
            setBackgroundColor((Color)fieldValue);
        } else if (fieldName.compareTo("caretColor") == 0) {
            setCaretColor((Color)fieldValue);
        } else if (fieldName.compareTo("font") == 0) {
            setFont((Font)fieldValue);
        } else if (fieldName.compareTo("editable") == 0) {
            setEditable(((Boolean)fieldValue).booleanValue());
        } else if (fieldName.compareTo("translateX") == 0) {
            setTranslateX(((Double)fieldValue).doubleValue());
        } else if (fieldName.compareTo("translateY") == 0) {
            setTranslateY(((Double)fieldValue).doubleValue());
        } else if (fieldName.compareTo("text") == 0) {
            setText((String)fieldValue);
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
        if ((backgroundColor != null) && (backgroundColor != DEFAULT_BACKGROUND_COLOR)) {
            out.writeState("java.awt.Color", "backgroundColor", backgroundColor);
        }
        if ((caretColor != null) && (caretColor != DEFAULT_CARET_COLOR)) {
            out.writeState("java.awt.Color", "caretColor", caretColor);
        }
        if (getFont() != DEFAULT_FONT) {
            out.writeState("java.awt.Font", "font", getFont());
        }
        if (getEditable() != DEFAULT_EDITABLE) {
            out.writeState("boolean", "editable", getEditable());
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        caretShape = new Line2D.Double();
        prevFRC = new FontRenderContext(null, true, true);
    }

}

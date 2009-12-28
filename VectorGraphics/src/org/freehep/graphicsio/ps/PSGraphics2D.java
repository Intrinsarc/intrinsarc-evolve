// Copyright 2000-2003 FreeHEP
package org.freehep.graphicsio.ps;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.util.*;

import org.freehep.graphics2d.*;
import org.freehep.graphicsio.*;
import org.freehep.graphicsio.font.*;
import org.freehep.graphicsio.raw.*;
import org.freehep.util.*;
import org.freehep.util.images.*;
import org.freehep.util.io.*;

/**
 * @author Charles Loomis
 * @author Mark Donszelmann
 * @version $Id: PSGraphics2D.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class PSGraphics2D extends AbstractVectorGraphicsIO implements MultiPageDocument, FontUtilities.ShowString
{

  private static final String rootKey = PSGraphics2D.class.getName();

  public static final String BACKGROUND = rootKey + "." + PageConstants.BACKGROUND;
  public static final String BACKGROUND_COLOR = rootKey + "." + PageConstants.BACKGROUND_COLOR;

  public static final String PAGE_SIZE = rootKey + "." + PageConstants.PAGE_SIZE;
  public static final String PAGE_MARGINS = rootKey + "." + PageConstants.PAGE_MARGINS;
  public static final String ORIENTATION = rootKey + "." + PageConstants.ORIENTATION;
  public static final String FIT_TO_PAGE = rootKey + "." + PageConstants.FIT_TO_PAGE;
  public static final String EMBED_FONTS = rootKey + "." + FontConstants.EMBED_FONTS;
  public static final String EMBED_FONTS_AS = rootKey + "." + FontConstants.EMBED_FONTS_AS;

  public static final String FOR = rootKey + "." + InfoConstants.FOR;
  public static final String TITLE = rootKey + "." + InfoConstants.TITLE;

  public static final String PREVIEW = rootKey + ".Preview";
  public static final String PREVIEW_BITS = rootKey + ".PreviewBits";

  public static final String WRITE_IMAGES_AS = rootKey + "." + ImageConstants.WRITE_IMAGES_AS;

  private static final UserProperties defaultProperties = new UserProperties();
  static
  {
    defaultProperties.setProperty(BACKGROUND, false);
    defaultProperties.setProperty(BACKGROUND_COLOR, Color.GRAY);

    defaultProperties.setProperty(PAGE_SIZE, PageConstants.INTERNATIONAL);
    defaultProperties.setProperty(PAGE_MARGINS, PageConstants.getMargins(PageConstants.SMALL));
    defaultProperties.setProperty(ORIENTATION, PageConstants.LANDSCAPE);
    defaultProperties.setProperty(FIT_TO_PAGE, true);
    defaultProperties.setProperty(EMBED_FONTS, false);
    defaultProperties.setProperty(EMBED_FONTS_AS, FontConstants.EMBED_FONTS_TYPE3);

    defaultProperties.setProperty(FOR, "");
    defaultProperties.setProperty(TITLE, "");

    defaultProperties.setProperty(PREVIEW, false);
    defaultProperties.setProperty(PREVIEW_BITS, 8);

    defaultProperties.setProperty(WRITE_IMAGES_AS, ImageConstants.SMALLEST);
  }

  public static Properties getDefaultProperties()
  {
    return defaultProperties;
  }

  public static final String version = "$Revision: 1.1 $";

  public static final int LEVEL_2 = 2;
  public static final int LEVEL_3 = 3;

  private static final double FONTSIZE_CORRECTION = 1.0;

  /**
   * Default flag for allowing clip regions to be written
   */
  private static boolean enableClip = true;

  // remember which fonts are used
  private PSFontTable fontTable;

  // The private writer used for this file.
  protected OutputStream ros;
  protected PrintStream os;

  // Remember if the font is already set. The font is not set when
  // setFont is called, but only when a string is actually drawn.
  private boolean fontSet = false;

  private boolean multiPage;
  private int currentPage;

  private int postscriptLevel = LEVEL_3;

  // Private array to do lookups of the symbols.
  private static String[] psSymbolNames = new String[NUMBER_OF_SYMBOLS];

  private static Hashtable compositeFonts = new Hashtable();

  static
  {

    for (int i = 0; i < NUMBER_OF_SYMBOLS; i++)
    {
      psSymbolNames[i] = "plus";
    }
    psSymbolNames[SYMBOL_VLINE] = "vline";
    psSymbolNames[SYMBOL_HLINE] = "hline";
    psSymbolNames[SYMBOL_PLUS] = "plus";
    psSymbolNames[SYMBOL_CROSS] = "cross";
    psSymbolNames[SYMBOL_STAR] = "star";
    psSymbolNames[SYMBOL_CIRCLE] = "dot";
    psSymbolNames[SYMBOL_BOX] = "box";
    psSymbolNames[SYMBOL_UP_TRIANGLE] = "triup";
    psSymbolNames[SYMBOL_DN_TRIANGLE] = "tridn";
    psSymbolNames[SYMBOL_DIAMOND] = "diamond";

    // these fonts are composite fonts in the prolog
    compositeFonts.put("SansSerif", "");
    compositeFonts.put("SansSerif-Bold", "");
    compositeFonts.put("SansSerif-Italic", "");
    compositeFonts.put("SansSerif-BoldItalic", "");

    compositeFonts.put("Serif", "");
    compositeFonts.put("Serif-Bold", "");
    compositeFonts.put("Serif-Italic", "");
    compositeFonts.put("Serif-BoldItalic", "");

    compositeFonts.put("Monospaced", "");
    compositeFonts.put("Monospaced-Bold", "");
    compositeFonts.put("Monospaced-Italic", "");
    compositeFonts.put("Monospaced-BoldItalic", "");

    compositeFonts.put("Dialog", "");
    compositeFonts.put("Dialog-Bold", "");
    compositeFonts.put("Dialog-Italic", "");
    compositeFonts.put("Dialog-BoldItalic", "");

    compositeFonts.put("DialogInput", "");
    compositeFonts.put("DialogInput-Bold", "");
    compositeFonts.put("DialogInput-Italic", "");
    compositeFonts.put("DialogInput-BoldItalic", "");

    // JDK 1.1
    compositeFonts.put("Typewriter", "");
    compositeFonts.put("Typewriter-Bold", "");
    compositeFonts.put("Typewriter-Italic", "");
    compositeFonts.put("Typewriter-BoldItalic", "");
  }

  // Private array to do lookups of the horizontal and vertical
  // alignments.
  private static String[][] psAlignments = new String[NUMBER_OF_HORIZ_ALIGNMENTS][NUMBER_OF_VERTICAL_ALIGNMENTS];

  static
  {

    for (int i = 0; i < NUMBER_OF_HORIZ_ALIGNMENTS; i++)
    {
      for (int j = 0; j < NUMBER_OF_VERTICAL_ALIGNMENTS; j++)
      {
        psAlignments[i][j] = "0";
      }
    }
    psAlignments[TEXT_LEFT][TEXT_TOP] = "0";
    psAlignments[TEXT_LEFT][TEXT_CENTER] = "1";
    psAlignments[TEXT_LEFT][TEXT_BASELINE] = "2";
    psAlignments[TEXT_LEFT][TEXT_BOTTOM] = "3";
    psAlignments[TEXT_RIGHT][TEXT_TOP] = "4";
    psAlignments[TEXT_RIGHT][TEXT_CENTER] = "5";
    psAlignments[TEXT_RIGHT][TEXT_BASELINE] = "6";
    psAlignments[TEXT_RIGHT][TEXT_BOTTOM] = "7";
    psAlignments[TEXT_CENTER][TEXT_TOP] = "8";
    psAlignments[TEXT_CENTER][TEXT_CENTER] = "9";
    psAlignments[TEXT_CENTER][TEXT_BASELINE] = "10";
    psAlignments[TEXT_CENTER][TEXT_BOTTOM] = "11";
  }

  /*
   * ================================================================================ |
   * 1. Constructors & Factory Methods
   * ================================================================================
   */

  public PSGraphics2D(File file, Dimension size) throws FileNotFoundException
  {
    this(new FileOutputStream(file), size);
  }

  public PSGraphics2D(File file, Component component) throws FileNotFoundException
  {
    this(new FileOutputStream(file), component);
  }

  public PSGraphics2D(OutputStream os, Dimension size)
  {
    super(size, false);
    init(os);
  }

  public PSGraphics2D(OutputStream os, Component component)
  {
    super(component, false);
    init(os);
  }

  private void init(OutputStream os)
  {
    ros = new BufferedOutputStream(os);
    initProperties(defaultProperties);
    fontTable = new PSFontTable(ros, getFontRenderContext());
    this.multiPage = false;
    this.currentPage = 0;
    this.fontSet = false;
  }

  /**
   * This protected method is used by the create() methods to create a clone of
   * the given graphics object.
   */
  protected PSGraphics2D(PSGraphics2D graphics, boolean doRestoreOnDispose)
  {

    super(graphics, doRestoreOnDispose);

    // Now initialize the new object.
    ros = graphics.ros;
    os = graphics.os;
    fontSet = graphics.fontSet;
    fontTable = graphics.fontTable;

    multiPage = graphics.multiPage;
    currentPage = graphics.currentPage;
  }

  /*
   * ================================================================================ |
   * 2. Document Settings
   * ================================================================================
   */

  public void setMultiPage(boolean multiPage)
  {
    this.multiPage = multiPage;
  }

  public boolean isMultiPage()
  {
    return multiPage;
  }

  /**
   * Set the clipping enabled flag. This will affect all output operations after
   * this call completes. In some circumstances the clipping region is set
   * incorrectly (not yet understood; AWT seems to not correctly dispose of
   * graphic contexts). A workaround is to simply switch it off.
   */
  public static void setClipEnabled(boolean enabled)
  {
    enableClip = enabled;
  }

  /**
   * Get the bounding box for this page.
   */
  private Rectangle getBoundingBox()
  {

    // Our PS Header has internal page orientation mode, all sizes given in
    // portrait
    Dimension pageSize = PageConstants.getSize(getProperty(PAGE_SIZE));
    Insets margins = getPropertyInsets(PAGE_MARGINS);
    boolean isPortrait = getProperty(ORIENTATION).equals(PageConstants.PORTRAIT);

    // Available width and height.
    double awidth = (pageSize.width - margins.left - margins.right);
    double aheight = (pageSize.height - margins.top - margins.bottom);

    // Image width and height (adjusted for portrait or landscape)
    Dimension size = getSize();
    double iwidth = (isPortrait ? size.width : size.height);
    double iheight = (isPortrait ? size.height : size.width);

    // Choose the minimum scale factor.
    double sf = Math.min(awidth / iwidth, aheight / iheight);
    if (!isProperty(FIT_TO_PAGE))
      sf = Math.min(sf, 1.);

    // Lower left corner.
    double x0 = awidth / 2. + margins.left - sf * iwidth / 2.;
    double y0 = aheight / 2. + margins.bottom - sf * iheight / 2.;

    // Upper right corner.
    double x1 = x0 + sf * iwidth;
    double y1 = y0 + sf * iheight;

    int llx = (int) x0;
    int lly = (int) y0;
    int urx = (int) Math.ceil(x1);
    int ury = (int) Math.ceil(y1);

    // Convert these back to integer values.
    return new Rectangle(llx, lly, urx - llx, ury - lly);
  }

  /*
   * ================================================================================ |
   * 3. Header, Trailer, Multipage & Comments
   * ================================================================================
   */
  /* 3.1 Header & Trailer */
  /**
   * Write out the header of this EPS file.
   */
  public void writeHeader() throws IOException
  {
    os = new PrintStream(ros, true);

    if (!isMultiPage())
    {
      Dimension size = getSize();
      // moved to openPage for multiPage
      resetClip(new Rectangle(0, 0, size.width, size.height));
    }

    os.println("%!PS-Adobe-3.0" + (isMultiPage() ? "" : " EPSF-3.0"));
    if (!isMultiPage())
    {
      Rectangle bbox = getBoundingBox();
      os
          .println("%%BoundingBox: " + bbox.x + " " + bbox.y + " " + (bbox.x + bbox.width) + " "
              + (bbox.y + bbox.height));
    }

    String producer = getClass().getName();
    if (!isDeviceIndependent())
    {
      producer += " " + version.substring(1, version.length() - 1);
    }
    os.println("%%Creator: " + getCreator());
    os.println("%%Producer: " + producer);
    os.println("%%For: " + getProperty(FOR));
    os.println("%%Title: " + getProperty(TITLE));
    if (!isDeviceIndependent())
    {
      os.println("%%CreationDate: "
          + DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL).format(new Date()));
    }
    os.println("%%LanguageLevel: " + postscriptLevel);
    os.println("%%EndComments");

    // write preview if possible
    if (isProperty(PREVIEW) && (getComponent() != null))
    {
      Rectangle size = getComponent().getBounds();
      BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
      Graphics imageGraphics = image.getGraphics();
      getComponent().print(imageGraphics);

      EPSIEncoder encoder = new EPSIEncoder(image, ros, getPropertyInt(PREVIEW_BITS), getProperty(ORIENTATION).equals(
          PageConstants.PORTRAIT));
      encoder.encode();
    }

    writeProlog();
  }

  private void writeProlog()
  {

    // The prolog is kept in a file PSProlog.txt in the same area
    // as this class definition. It is simply copied into the
    // output file.
    os.println("%%BeginProlog");
    copyResourceTo(this, "PSProlog.txt", os);
    os.println("%%EndProlog");
    os.println();

    if (!isMultiPage())
      openPage(getSize(), null, getComponent());
  }

  public void writeBackground() throws IOException
  {
    // since PS is non-transparent, write current background
    if (isProperty(BACKGROUND))
    {
      setBackground(getPropertyColor(BACKGROUND_COLOR));
      clearRect(0.0, 0.0, getSize().width, getSize().height);
    }
    else
    {
      setBackground(getComponent() != null ? getComponent().getBackground() : Color.WHITE);
      clearRect(0.0, 0.0, getSize().width, getSize().height);
    }
  }

  public void writeTrailer() throws IOException
  {
    if (!isMultiPage())
      closePage();
    os.println();
    os.println("%%Trailer");
    if (isMultiPage())
      os.println("%%Pages: " + currentPage);
    os.println("%%EOF");
  }

  public void closeStream() throws IOException
  {
    ros.close();
    os.close();
  }

  /* 3.2 MultipageDocument methods */
  public void openPage(Component component) throws IOException
  {
    openPage(component.getSize(), component.getName(), component);
  }

  public void openPage(Dimension size, String title) throws IOException
  {
    openPage(size, title, null);
  }

  private void openPage(Dimension size, String title, Component component)
  {
    if (size == null)
      size = component.getSize();
    currentPage++;
    resetClip(new Rectangle(0, 0, size.width, size.height));

    // Our PS Header has internal page orientation mode, all sizes given in
    // portrait
    Dimension pageSize = PageConstants.getSize(getProperty(PAGE_SIZE));
    Insets margins = getPropertyInsets(PAGE_MARGINS);

    title = (title == null) ? "" + currentPage : "(" + title + ")";
    if (isMultiPage())
      os.println("%%Page: " + title + " " + currentPage);
    os.println(isMultiPage() ? "%%BeginPageSetup" : "%%BeginSetup");
    os.println("save");
    os.println("procDict begin");
    os.println("printColorMap begin");
    os.println(pageSize.width + " " + pageSize.height + " setpagesize");
    os.println(margins.left + " " + margins.bottom + " " + margins.top + " " + margins.right + " setmargins");
    os.println("0 0 setorigin");
    os.println(size.width + " " + size.height + " setsize");
    os.println(isProperty(FIT_TO_PAGE) ? "fittopage" : "naturalsize");
    os.println(getProperty(ORIENTATION).equals(PageConstants.PORTRAIT) ? "portrait" : "landscape");
    os.println("imagescale");
    os.println("cliptobounds");
    os.println("setbasematrix");
    os.println("/Helvetica 10 sf");
    os.println("defaultGraphicsState");
    os.println(isMultiPage() ? "%%EndPageSetup" : "%%EndSetup");
    os.println();

    try
    {
      writeGraphicsState();
      writeBackground();
    }
    catch (Exception e)
    {
      handleException(e);
    }
  }

  public void closePage()
  {
    // os.println("% procDict and colorMap dictionaries");
    os.println("end end restore showpage");
    if (isMultiPage())
      os.println("%%PageTrailer");
  }

  public void setHeader(Font font, TagString left, TagString center, TagString right, int underlineThickness)
  {
  }

  public void setFooter(Font font, TagString left, TagString center, TagString right, int underlineThickness)
  {
  }


  /*
   * ================================================================================ |
   * 4. Create & Dispose
   * ================================================================================
   */
  public Graphics create()
  {
    try
    {
      writeGraphicsSave();
    }
    catch (IOException e)
    {
      handleException(e);
    }
    return new PSGraphics2D(this, true);
  }

  public Graphics create(double x, double y, double width, double height)
  {
    try
    {
      writeGraphicsSave();
    }
    catch (IOException e)
    {
      handleException(e);
    }
    PSGraphics2D graphics = new PSGraphics2D(this, true);
    graphics.translate(x, y);
    graphics.clipRect(0, 0, width, height);
    return graphics;
  }

  /**
   * Embed a gsave in the PostScript file.
   */
  protected void writeGraphicsSave() throws IOException
  {
    os.println("q");
  }

  /**
   * Embed a grestore in the PostScript file.
   */
  protected void writeGraphicsRestore() throws IOException
  {
    os.println("Q");
  }

  /*
   * ================================================================================ |
   * 5. Drawing Methods
   * ================================================================================
   */
  public void drawLine(double x1, double y1, double x2, double y2)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println(fixedPrecision(x2) + " " + fixedPrecision(y2) + " " + fixedPrecision(x1) + " " + fixedPrecision(y1)
          + " L");
  }

  public void drawRect(double x, double y, double width, double height)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println("bias " + fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " "
          + fixedPrecision(height) + " rs unbias");
  }

  public void fillRect(double x, double y, double width, double height)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println(fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " "
          + fixedPrecision(height) + " rf");
  }

  public void drawArc(double x, double y, double width, double height, double startAngle, double arcAngle)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println(fixedPrecision(startAngle) + " " + fixedPrecision(startAngle + arcAngle) + " " + fixedPrecision(x)
          + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " " + fixedPrecision(height) + " OVL");
  }

  public void fillArc(double x, double y, double width, double height, double startAngle, double arcAngle)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println(fixedPrecision(startAngle) + " " + fixedPrecision(startAngle + arcAngle) + " " + fixedPrecision(x)
          + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " " + fixedPrecision(height) + " FOVL");
  }

  public void drawOval(double x, double y, double width, double height)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println("0 360 " + fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " "
          + fixedPrecision(height) + " OVL");
  }

  public void fillOval(double x, double y, double width, double height)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println("0 360 " + fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " "
          + fixedPrecision(height) + " FOVL");
  }

  public void drawRoundRect(double x, double y, double width, double height, double arcWidth, double arcHeight)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println(fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " "
          + fixedPrecision(height) + " " + fixedPrecision(arcWidth) + " " + fixedPrecision(arcHeight) + " RREC");
  }

  public void fillRoundRect(double x, double y, double width, double height, double arcWidth, double arcHeight)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      os.println(fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(width) + " "
          + fixedPrecision(height) + " " + fixedPrecision(arcWidth) + " " + fixedPrecision(arcHeight) + " FRREC");
  }

  public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      if (nPoints > 1)
      {
        os.println((nPoints - 1) + " " + xPoints[0] + " " + yPoints[0] + " OPL");
        for (int i = 1; i < nPoints; i++)
        {
          os.println(xPoints[i] + " " + yPoints[i]);
        }
      }
  }

  public void drawPolyline(double[] xPoints, double[] yPoints, int nPoints)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      if (nPoints > 1)
      {
        os.println((nPoints - 1) + " " + xPoints[0] + " " + yPoints[0] + " OPL");
        for (int i = 1; i < nPoints; i++)
        {
          os.println(fixedPrecision(xPoints[i]) + " " + fixedPrecision(yPoints[i]));
        }
      }
  }

  public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      if (nPoints > 1)
      {
        os.println((nPoints - 1) + " " + xPoints[0] + " " + yPoints[0] + " CPL");
        for (int i = 1; i < nPoints; i++)
        {
          os.println(xPoints[i] + " " + yPoints[i]);
        }
      }
  }

  public void drawPolygon(double[] xPoints, double[] yPoints, int nPoints)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      if (nPoints > 1)
      {
        os.println((nPoints - 1) + " " + xPoints[0] + " " + yPoints[0] + " CPL");
        for (int i = 1; i < nPoints; i++)
        {
          os.println(fixedPrecision(xPoints[i]) + " " + fixedPrecision(yPoints[i]));
        }
      }
  }

  public void drawPolygon(Polygon p)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      drawPolygon(p.xpoints, p.ypoints, p.npoints);
  }

  public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      if (nPoints > 1)
      {
        os.println((nPoints - 1) + " " + xPoints[0] + " " + yPoints[0] + " FPL");
        for (int i = 1; i < nPoints; i++)
        {
          os.println(xPoints[i] + " " + yPoints[i]);
        }
      }
  }

  public void fillPolygon(double[] xPoints, double[] yPoints, int nPoints)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      if (nPoints > 1)
      {
        os.println((nPoints - 1) + " " + xPoints[0] + " " + yPoints[0] + " FPL");
        for (int i = 1; i < nPoints; i++)
        {
          os.println(fixedPrecision(xPoints[i]) + " " + fixedPrecision(yPoints[i]));
        }
      }
  }

  public void fillPolygon(Polygon p)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      fillPolygon(p.xpoints, p.ypoints, p.npoints);
  }

  private String escapeString(String str)
  {

    // Convert to unicode encoded string first if Font is not embedded
    // and Font is a composite font
    if (!isProperty(EMBED_FONTS)
        && isComposite(fontTable.fontReference(getFont(), isProperty(EMBED_FONTS), getProperty(EMBED_FONTS_AS))))
    {
      return convertToUnicodeString(str);
    }

    // Then protect against unbalanced parentheses in the string.
    // Copy string to temporary string buffer.
    StringBuffer temp = new StringBuffer(str);

    // Loop over all characters in the string and escape the
    // parentheses.
    int i = 0;
    while (i < temp.length())
    {
      char c = temp.charAt(i);
      if (c == '(' || c == ')' || c == '\\' || c == '%')
        temp.insert(i++, '\\');
      if (c == 0)
        temp.setCharAt(i, '?');
      i++;
    }
    return temp.toString();
  }

  public void drawSymbol(double x, double y, double size, int symbol)
  {
    if (size <= 0)
      return;
    os.print(fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(size) + " ");
    os.println((symbol < 0 || symbol >= NUMBER_OF_SYMBOLS) ? "plus" : psSymbolNames[symbol]);
  }

  public void fillSymbol(double x, double y, double size, int symbol)
  {
    if (size <= 0)
      return;
    os.print(fixedPrecision(x) + " " + fixedPrecision(y) + " " + fixedPrecision(size) + " ");

    if (symbol < 0 || symbol >= NUMBER_OF_SYMBOLS)
    {
      os.println("plus");
      return;
    }

    if (symbol >= SYMBOL_CIRCLE)
    {
      os.print("f");
    }
    os.println(psSymbolNames[symbol]);
  }

  /* 5.1.4. shapes */
  public void draw(Shape shape)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      try
      {
        writePath(shape);
        os.println("S");
      }
      catch (IOException e)
      {
        handleException(e);
      }
  }

  public void fill(Shape shape)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      try
      {
        boolean eofill = writePath(shape);
        os.println(((eofill) ? "f*" : "f"));
      }
      catch (IOException e)
      {
        handleException(e);
      }
  }

  public void fillAndDraw(Shape shape, Color fillColor)
  {
    // fixed by AMcV
    if (getColor().getAlpha() != 0)
      try
      {
        setPSColor(fillColor, true);
        boolean eofill = writePath(shape);
        os.println(((eofill) ? "B*" : "B"));
      }
      catch (IOException e)
      {
        handleException(e);
      }
  }

  /* 5.2 Images */
  public void copyArea(int x, int y, int width, int height, int dx, int dy)
  {
    writeWarning(getClass() + ": copyArea(int, int, int, int, int, int) not implemented.");
  }

  protected void writeImage(RenderedImage image, AffineTransform xform, Color bkg) throws IOException
  {
    // FIXME FREEHEP-374

    if (bkg == null)
      bkg = getBackground();
    image = ImageUtilities.createRenderedImage(image, bkg);

    // Write out the PostScript code to start an image
    // definition.
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    AffineTransform imageTransform = new AffineTransform(imageWidth, 0.0, 0.0, imageHeight, 0.0, 0.0);
    xform.concatenate(imageTransform);

    os.println("gsave /DeviceRGB setcolorspace");
    // os.println(x+" "+y+" translate");
    // os.println(width+" "+height+" scale");
    transform(xform);
    os.println("<<");
    os.println("/ImageType 1");
    os.println("/Width " + imageWidth + "  /Height " + imageHeight);
    os.println("/BitsPerComponent 8");
    os.println("/Decode [0 1 0 1 0 1]");
    os.println("/ImageMatrix [" + imageWidth + " 0 0 " + imageHeight + " 0 0]");

    String writeAs = getProperty(WRITE_IMAGES_AS);

    byte[] flateBytes = null;
    if (writeAs.equals(ImageConstants.ZLIB) || writeAs.equals(ImageConstants.SMALLEST))
    {
      ByteArrayOutputStream flate = new ByteArrayOutputStream();
      ASCII85OutputStream flate85 = new ASCII85OutputStream(flate);
      FlateOutputStream fos = new FlateOutputStream(flate85);
      UserProperties props = new UserProperties();
      props.setProperty(RawImageWriteParam.BACKGROUND, bkg);
      props.setProperty(RawImageWriteParam.CODE, "RGB");
      props.setProperty(RawImageWriteParam.PAD, 1);
      ImageGraphics2D.writeImage(image, "raw", props, fos);
      fos.close();
      flateBytes = flate.toByteArray();
    }

    byte[] jpgBytes = null;
    if (writeAs.equals(ImageConstants.JPG) || writeAs.equals(ImageConstants.SMALLEST))
    {
      ByteArrayOutputStream jpg = new ByteArrayOutputStream();
      ASCII85OutputStream jpg85 = new ASCII85OutputStream(jpg);
      ImageGraphics2D.writeImage(image, "jpg", new Properties(), jpg85);
      jpg85.close();
      jpgBytes = jpg.toByteArray();
    }

    String encode;
    byte[] imageBytes;
    if (writeAs.equals(ImageConstants.ZLIB))
    {
      encode = "Flate";
      imageBytes = flateBytes;
    }
    else
      if (writeAs.equals(ImageConstants.JPG))
      {
        encode = "DCT";
        imageBytes = jpgBytes;
      }
      else
      {
        encode = (jpgBytes.length < 0.5 * flateBytes.length) ? "DCT" : "Flate";
        imageBytes = encode.equals("DCT") ? jpgBytes : flateBytes;
      }

    os.println("/DataSource currentfile " + "/ASCII85Decode filter " + "/" + encode + "Decode filter ");
    os.println(">> image");

    os.write(imageBytes);

    os.println("");
    os.println("grestore");
  }

  /* 5.3. Strings */
  protected void writeString(String str, double x, double y) throws IOException
  {
    showCharacterCodes(str, x, y);
    // writeFont();
    // os.println("("+escapeString(str)+") "+
    // fixedPrecision(x)+" "+
    // fixedPrecision(y)+" STR");
  }

  // FIXME compare this with AVGIO
  public void drawString(String str, double x, double y, int horizontal, int vertical, boolean framed,
      Color frameColor, double frameWidth, boolean banner, Color bannerColor)
  {

    try
    {
      LineMetrics metrics = getFont().getLineMetrics(str, getFontRenderContext());
      double width = getFont().getStringBounds(str, getFontRenderContext()).getWidth();
      double height = metrics.getHeight();
      double descent = metrics.getDescent();
      Rectangle2D textSize = new Rectangle2D.Double(0, descent - height, width, height);
      double adjustment = (getFont().getSize2D() * 2) / 10;

      Point2D textUL = drawFrameAndBanner(x, y, textSize, adjustment, framed, frameColor, frameWidth, banner,
          bannerColor, horizontal, vertical);
      drawString(str, textUL.getX(), textUL.getY());
    }
    catch (IOException e)
    {
      handleException(e);
    }
  }

  // FIXME compare this with AVGIO
  public void drawString(TagString str, double x, double y, int horizontal, int vertical, boolean framed,
      Color frameColor, double frameWidth, boolean banner, Color bannerColor)
  {

    try
    {
      GenericTagHandler tagHandler = new GenericTagHandler(this);
      Rectangle2D r = tagHandler.stringSize(str);
      double adjustment = (getFont().getSize2D() * 2) / 10;

      Point2D textUL = drawFrameAndBanner(x, y, r, adjustment, framed, frameColor, frameWidth, banner, bannerColor,
          horizontal, vertical);

      tagHandler.print(str, textUL.getX(), textUL.getY());
    }
    catch (IOException e)
    {
      handleException(e);
    }
  }

  public void drawString(AttributedCharacterIterator iterator, float x, float y)
  {
    writeWarning(getClass() + ": drawString(AttributedCharacterIterator, float, float) not implemented.");
  }

  public void drawGlyphVector(GlyphVector g, float x, float y)
  {
    writeWarning(getClass() + ": drawGlyphVector(GlyphVector, float, float) not implemented.");
  }

  /*
   * ================================================================================ |
   * 6. Transformations
   * ================================================================================
   */
  public void setTransform(AffineTransform tx)
  {
    super.setTransform(tx);
    if (tx == null)
      tx = new AffineTransform();
    os.println("[ " + fixedPrecision(tx.getScaleX()) + " " + fixedPrecision(tx.getShearY()) + " "
        + fixedPrecision(tx.getShearX()) + " " + fixedPrecision(tx.getScaleY()) + " "
        + fixedPrecision(tx.getTranslateX()) + " " + fixedPrecision(tx.getTranslateY())
        + " ] defaultmatrix matrix concatmatrix setmatrix");
  }

  public void transform(AffineTransform transform)
  {
    super.transform(transform);
    os.println("[ " + fixedPrecision(transform.getScaleX()) + " " + fixedPrecision(transform.getShearY()) + " "
        + fixedPrecision(transform.getShearX()) + " " + fixedPrecision(transform.getScaleY()) + " "
        + fixedPrecision(transform.getTranslateX()) + " " + fixedPrecision(transform.getTranslateY()) + " ] concat");
  }

  public void translate(double x, double y)
  {
    super.translate(x, y);
    os.println(fixedPrecision(x) + " " + fixedPrecision(y) + " translate");
  }

  public void rotate(double theta)
  {
    super.rotate(theta);
    os.println(fixedPrecision(Math.toDegrees(theta)) + " rotate");
  }

  public void scale(double sx, double sy)
  {
    super.scale(sx, sy);
    os.println(fixedPrecision(sx) + " " + fixedPrecision(sy) + " scale");
  }

  public void shear(double shx, double shy)
  {
    super.shear(shx, shy);
    os.println("[ 1.0 " + fixedPrecision(shy) + " " + fixedPrecision(shx) + " 1.0 0.0 0.0 ] concat");
  }

  protected void writeTransform(AffineTransform tx) throws IOException
  {
    // all written with higher level methods
  }

  /*
   * ================================================================================ |
   * 7. Clipping
   * ================================================================================
   */
  /**
   * Clips shape. PS only allows to intersect the currentClip so this calls
   * clip(Shape).
   * 
   * @param shape
   *          used for clipping
   */
  public void setClip(Shape shape)
  {
    clip(shape);
  }

  protected void writeClip(Rectangle r) throws IOException
  {
    if (r == null)
      return;
    if (enableClip)
    {
      os.println(r.x + " " + r.y + " " + r.width + " " + r.height + " rc");
    }
  }

  protected void writeClip(Rectangle2D r2d) throws IOException
  {
    if (r2d == null)
      return;
    if (enableClip)
    {
      os.println(fixedPrecision(r2d.getX()) + " " + fixedPrecision(r2d.getY()) + " " + fixedPrecision(r2d.getWidth())
          + " " + fixedPrecision(r2d.getHeight()) + " rc");
    }
  }

  protected void writeClip(Shape s) throws IOException
  {
    if (s == null)
      return;
    boolean eofill = writePath(s);
    os.println(((eofill) ? "W*" : "W"));
  }

  /**
   * Write the path of the current shape to the output file. Return a boolean
   * indicating whether or not the even-odd rule for filling should be used.
   */
  private boolean writePath(Shape s) throws IOException
  {
    os.println("newpath");
    PSPathConstructor path = new PSPathConstructor(os, true, false);
    return path.addPath(s);
  }

  /*
   * ================================================================================ |
   * 8. Graphics State
   * ================================================================================
   */
  /* 8.1. stroke/linewidth */
  protected void writeWidth(float width) throws IOException
  {
    os.println(fixedPrecision(width) + " w");
  }

  protected void writeCap(int cap) throws IOException
  {
    switch (cap)
    {
      default :
      case BasicStroke.CAP_BUTT :
        os.println("0 J");
        break;
      case BasicStroke.CAP_ROUND :
        os.println("1 J");
        break;
      case BasicStroke.CAP_SQUARE :
        os.println("2 J");
        break;
    }
  }

  protected void writeJoin(int join) throws IOException
  {
    switch (join)
    {
      default :
      case BasicStroke.JOIN_MITER :
        os.println("0 j");
        break;
      case BasicStroke.JOIN_ROUND :
        os.println("1 j");
        break;
      case BasicStroke.JOIN_BEVEL :
        os.println("2 j");
        break;
    }
  }

  protected void writeMiterLimit(float limit) throws IOException
  {
    os.println(fixedPrecision(limit) + " M");
  }

  protected void writeDash(double[] dash, double phase) throws IOException
  {
    os.print("[ ");
    for (int i = 0; i < dash.length; i++)
    {
      os.print(fixedPrecision(dash[i]) + " ");
    }
    os.println("] " + fixedPrecision(phase) + " d");
  }

  /* 8.2. paint/color */
  public void setPaintMode()
  {
    writeWarning(getClass() + ": setPaintMode() not implemented.");
  }

  public void setXORMode(Color c1)
  {
    writeWarning(getClass() + ": setXORMode(Color) not implemented.");
  }

  protected void writePaint(Color c) throws IOException
  {
    setPSColor(c, false);
  }

  protected void writePaint(GradientPaint paint) throws IOException
  {
    if (postscriptLevel >= LEVEL_3)
    {
      float[] rgb1 = paint.getColor1().getRGBColorComponents(null);
      float[] rgb2 = paint.getColor2().getRGBColorComponents(null);
      Point2D p1 = paint.getPoint1();
      Point2D p2 = paint.getPoint2();
      os.println("<< /PatternType 2");
      os.println("   /Shading");
      os.println("   << /ShadingType 2");
      os.println("      /ColorSpace /DeviceRGB");
      os.println("      /Coords [" + p1.getX() + " " + p1.getY() + " " + p2.getX() + " " + p2.getY() + "]");
      os.println("      /Function");
      os.println("      << /FunctionType 2");
      os.println("         /Domain [0 1]");
      os.println("         /Range [0 1 0 1 0 1]");
      os.println("         /C0 [" + rgb1[0] + " " + rgb1[1] + " " + rgb1[2] + "]");
      os.println("         /C1 [" + rgb2[0] + " " + rgb2[1] + " " + rgb2[2] + "]");
      os.println("         /N 1");
      os.println("      >>");
      os.println("      /Extend [true true]");
      os.println("   >>");
      os.println(">>");
      os.println("matrix makepattern setpattern");
    }
    else
    {
      writeComment("Gradient fill not supported by ps level 2. " + "Replacing with intermediate color.");
      setColor(PrintColor.mixColor(paint.getColor1(), paint.getColor2()));
    }
  }

  protected void writePaint(TexturePaint paint) throws IOException
  {
    BufferedImage img = paint.getImage();
    os.println("<< /PatternType 1");
    os.println("   /PaintType 1");
    os.println("   /TilingType 1");
    os.println("   /BBox [0 0 " + img.getWidth() + " " + img.getHeight() + "]");
    os.println("   /XStep " + paint.getAnchorRect().getWidth());
    os.println("   /YStep " + paint.getAnchorRect().getHeight());
    os.println("   /PaintProc");
    os.println("   {");
    os.println("     begin");
    os.println("     /DeviceRGB setcolorspace");
    os.println("     0 0 translate");
    os.println("     " + img.getWidth() + " " + img.getHeight() + " scale");
    os.println("     <<");
    os.println("     /ImageType 1");
    os.println("     /Width " + img.getWidth());
    os.println("     /Height " + img.getWidth());
    os.println("     /BitsPerComponent 8");
    os.println("     /Decode [0 1 0 1 0 1]");
    os.println("     /ImageMatrix [" + img.getWidth() + " 0 0 " + img.getHeight() + " 0 0]");
    os.println("     /DataSource ( Z  Z  Z  Z  Z  Z  Z  Z Z ZZ ZZ ZZ ZZ ZZ ZZ ZZ Z)");
    os.println("     >> image");
    os.println("     end");
    os.println("   } bind");
    os.println(">>");
    os.println("matrix makepattern setpattern");
  }

  protected void writePaint(Paint p) throws IOException
  {
    writeWarning(getClass() + ": writePaint(Paint) not implemented for " + p.getClass());
  }

  /* 8.3 fonts */
  /**
   * This method sets the current font. However, it does not write it to the
   * file. When drawString() wants to show text, it has to call writeFont()
   * first, which will actually write the current font to the document. Thus we
   * avoid embedding unused fonts (e.g. the Dialog font which is set by java for
   * whatsoever reason
   */
  public void setFont(Font font)
  {
    // andrew fixed
    if (font == null)
      return;
    if (!font.equals(getFont()))
    {
      fontSet = false;
    }
    super.setFont(font);
  }


  /*
   * ================================================================================ |
   * 9. Auxiliary
   * ================================================================================
   */
  public GraphicsConfiguration getDeviceConfiguration()
  {
    writeWarning(getClass() + ": getDeviceConfiguration() not implemented.");
    return null;
  }

  public boolean hit(Rectangle rect, Shape s, boolean onStroke)
  {
    writeWarning(getClass() + ": hit(Rectangle, Shape, boolean) not implemented.");
    return false;
  }

  /**
   * Embed a PostScript comment into the output file.
   */
  public void writeComment(String s) throws IOException
  {
    os.println("% " + s);
  }

  public String toString()
  {
    return "PSGraphics2D";
  }

  /*
   * ================================================================================ |
   * 10. Private/Utility
   * ================================================================================
   */
  private String convertToUnicodeString(String str)
  {

    // Start with an empty buffer and copy the string contents
    // into it.
    StringBuffer codedString = new StringBuffer();

    for (int i = 0; i < str.length(); i++)
    {
      int chr = (int) str.charAt(i);
      int cvalue = (chr & 0x000000ff);
      int fvalue = (chr & 0x0000ff00) >>> 8;
      String cbyte = Integer.toOctalString(cvalue);
      String fbyte = Integer.toOctalString(fvalue);

      // Put in the font byte first.
      codedString.append('\\');
      for (int j = 0; j < (3 - fbyte.length()); j++)
      {
        codedString.append('0');
      }
      codedString.append(fbyte);

      // Now the character itself. Put in non-printable
      // characters, the backslash, the percent sign, and
      // parentheses as octal numbers instead.
      if (cvalue < 32 || cvalue > 126 || cvalue == '\\' || cvalue == '%' || cvalue == '(' || cvalue == ')')
      {
        codedString.append('\\');
        for (int j = 0; j < (3 - cbyte.length()); j++)
        {
          codedString.append('0');
        }
        codedString.append(cbyte);
      }
      else
      {
        codedString.append((char) cvalue);
      }
    }
    return codedString.toString();
  }

  private TagString escapeTagString(TagString str)
  {
    return str;
  }
  // private TagString escapeTagString(TagString str) {
  // // Must protect against unbalanced parentheses in the string,
  // // as well as, occurances of "%", and "\".

  // // Copy string to temporary string buffer.
  // StringBuffer temp = new StringBuffer(str.toString());

  // // Loop over all characters in the string and escape the
  // // parentheses.
  // int i = 0;
  // while (i<temp.length()) {
  // char c = temp.charAt(i);
  // if (c=='(' || c==')' || c=='%' || c=='\\') {
  // temp.insert(i++,'\\');
  // }
  // i++;
  // }
  // return new TagString(temp.toString());
  // }

  /**
   * Draws frame and banner around a string. The method calculates and returns
   * the point to which the text curser should be set before drawing the string.
   */
  // FIXME compare this with AVGIO
  private Point2D drawFrameAndBanner(double x, double y, Rectangle2D textSize, double adjustment, boolean framed,
      Color frameColor, double frameWidth, boolean banner, Color bannerColor, int horizontal, int vertical)
      throws IOException
  {

    double descent = textSize.getY() + textSize.getHeight();
    x = getXalignment(x, textSize.getWidth(), horizontal);
    y = getYalignment(y, textSize.getHeight(), descent, vertical);

    double rx = x - adjustment;
    double ry = y - textSize.getHeight() + descent - adjustment;
    double rw = textSize.getWidth() + 2 * adjustment;
    double rh = textSize.getHeight() + 2 * adjustment;

    if (banner)
    {
      Paint paint = getPaint();
      setPaint(bannerColor);
      fillRect(rx, ry, rw, rh);
      setPaint(paint);
    }
    if (framed)
    {
      Paint paint = getPaint();
      Stroke stroke = getStroke();
      setPaint(frameColor);
      setLineWidth(frameWidth);
      drawRect(rx, ry, rw, rh);
      setPaint(paint);
      setStroke(stroke);
    }
    return new Point2D.Double(x, y);
  }

  private void writeFont()
  {
    if (fontSet)
      return;
    fontSet = true;
    writeFont(getFont());
  }

  private void writeFont(Font font)
  {
    String fontName = fontTable.fontReference(font, isProperty(EMBED_FONTS), getProperty(EMBED_FONTS_AS));
    os.println("/" + fontName + " findfont " + font.getSize() * FONTSIZE_CORRECTION + " scalefont setfont");
  }

  public void showString(Font font, String str)
  {
    writeFont(font);
    os.println("(" + escapeString(str) + ") show");
  }

  private void showCharacterCodes(String str, double x, double y)
  {
    try
    {
      os.println(fixedPrecision(x) + " " + fixedPrecision(y) + " moveto");
      os.println("q 1 -1 scale");
      if (!isProperty(EMBED_FONTS)
          && isComposite(fontTable.fontReference(getFont(), isProperty(EMBED_FONTS), getProperty(EMBED_FONTS_AS))))
      {
        // use PS header for unicode to composite font encoding
        showString(getFont(), str);
      }
      else
      {
        // use embedded font encodings for unicode to PS
        FontUtilities.showString(getFont(), str, fontTable.getEncodingTable(), this);
      }
      os.println("Q");
    }
    catch (java.io.IOException e)
    {
      handleException(e);
    }
  }

  /**
   * A utility function which actually sets the color in the PS. We use the
   * stroke color as the PS current color and a special saved variable for the
   * fill color.
   */
  private void setPSColor(Color c, boolean fillColor)
  {
    if (c != null)
    {
      if (c instanceof MappedColor)
      {
        MappedColor mc = (MappedColor) c;
        if (!fillColor)
        {
          if (mc.getBrightness() == 0)
          {
            os.println(mc.getColorTag() + " vg&C");
          }
          else
          {
            os.println(mc.getColorTag() + " " + mc.getBrightness() + " darken vg&C");
          }
        }
        else
        {
          if (mc.getBrightness() == 0)
          {
            os.println(mc.getColorTag() + " vg&DFC");
          }
          else
          {
            os.println(mc.getColorTag() + " " + mc.getBrightness() + " darken vg&DFC");
          }
        }
      }
      else
      {
        Color pc = getPrintColor(c);
        double red = ((double) pc.getRed()) / 255.;
        double green = ((double) pc.getGreen()) / 255.;
        double blue = ((double) pc.getBlue()) / 255.;
        os.print(fixedPrecision(red) + " " + fixedPrecision(green) + " " + fixedPrecision(blue) + " ");
        os.println(((fillColor) ? "rg" : "RG"));
      }
    }
  }

  /**
   * Utility converts the vertical and horizontal alignments in VectorGraphics
   * to the appropriate number for the PS header code.
   */
  private String getPSAlignment(int vertical, int horizontal)
  {
    if (vertical >= 0 && vertical < NUMBER_OF_VERTICAL_ALIGNMENTS && horizontal >= 0
        && horizontal < NUMBER_OF_HORIZ_ALIGNMENTS)
    {
      return psAlignments[horizontal][vertical];
    }
    else
    {
      return "0";
    }
  }

  private static boolean isComposite(String name)
  {
    return compositeFonts.containsKey(name);
  }
}

package org.freehep.graphicsio.test;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import org.freehep.graphicsio.*;
import org.freehep.util.*;
import org.freehep.util.io.*;

/**
 * @author Mark Donszelmann
 * @version $Id: TestSuite.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class TestSuite
{

  private static boolean compare;

  public static class TestCase extends junit.framework.TestCase
  {

    private String name, fmt, ext;

    public TestCase(String name, String fmt, String ext)
    {
      super("Graphics2D Test for " + name + " in " + fmt);
      this.name = name;
      this.fmt = fmt;
      this.ext = ext;
    }

    protected void runTest() throws Throwable
    {
      String dir = "org/freehep/graphicsio/";
      if (fmt.equals("GIF"))
      {
        if (name.equals("TestPaint"))
          return; // Too Many Colors
        if (name.equals("TestImage2D"))
          return; // Too Many Colors
        if (name.equals("TestAll"))
          return; // Too Many Colors
      }

      Class cls = Class.forName("org.freehep.graphicsio.test." + name);
      String targetName = dir + ext + "/test/" + name + "." + ext;
      String refName = dir + ext + "/test/" + name + ".ref." + ext;
      String refGZIPName = dir + ext + "/test/" + name + ".ref." + ext + ".gz";
      String altRefName = dir + ext + "/test/" + name + "." + ext + ".ref";

      Object args;
      if (fmt.equals("GIF") || fmt.equals("PNG") || fmt.equals("PPM") || fmt.equals("JPG"))
      {
        args = Array.newInstance(String.class, 3);
        Array.set(args, 0, ImageGraphics2D.class.getName());
        Array.set(args, 1, fmt.toLowerCase());
        Array.set(args, 2, targetName);
      }
      else
      {
        args = Array.newInstance(String.class, 2);
        Array.set(args, 0, "org.freehep.graphicsio." + ext + "." + fmt + "Graphics2D");
        Array.set(args, 1, targetName);
      }
      Method main = cls.getMethod("main", new Class[]{args.getClass()});
      main.invoke(null, new Object[]{args});
      if (!compare)
        return;

      File refFile = new File(refGZIPName);
      if (!refFile.exists())
      {
        refFile = new File(altRefName);
        if (!refFile.exists())
        {
          refFile = new File(refName);
        }
      }

      boolean isBinary = !fmt.equals("PS");
      Assert.assertEquals(refFile, new File(targetName), isBinary);
    }
  }

  private static void addTests(junit.framework.TestSuite suite, String fmt, String ext)
  {
    if (fmt.equals("PS") || fmt.equals("PDF"))
    {
      // Disabled until files are smaller and performance is better
      // suite.addTest(new TestCase("TestFontType1", fmt, ext));
      // suite.addTest(new TestCase("TestFontType3", fmt, ext));
      suite.addTest(new TestCase("TestPreviewThumbnail", fmt, ext));
    }

    suite.addTest(new TestCase("TestPrintColors", fmt, ext));
    suite.addTest(new TestCase("TestLineStyles", fmt, ext));
    suite.addTest(new TestCase("TestPaint", fmt, ext));
    suite.addTest(new TestCase("TestSymbols2D", fmt, ext));
    suite.addTest(new TestCase("TestImages", fmt, ext));
    suite.addTest(new TestCase("TestImage2D", fmt, ext));
    suite.addTest(new TestCase("TestTransparency", fmt, ext));
    suite.addTest(new TestCase("TestTaggedString", fmt, ext));
    suite.addTest(new TestCase("TestText2D", fmt, ext));
    suite.addTest(new TestCase("TestTransforms", fmt, ext));
    suite.addTest(new TestCase("TestFonts", fmt, ext));
    suite.addTest(new TestCase("TestLabels", fmt, ext));
    suite.addTest(new TestCase("TestShapes", fmt, ext));
    suite.addTest(new TestCase("TestHTML", fmt, ext));
    suite.addTest(new TestCase("TestClip", fmt, ext));
    suite.addTest(new TestCase("TestColors", fmt, ext));
    suite.addTest(new TestCase("TestAll", fmt, ext));

  }

  public static junit.framework.TestSuite suite()
  {
    // get command line arguments from environment var (set by ANT)
    StringTokenizer st = new StringTokenizer(System.getProperty("args", ""), " ");
    List argList = new ArrayList();
    while (st.hasMoreTokens())
    {
      String arg = st.nextToken();
      System.out.println(arg);
      argList.add(arg);
    }
    String[] args = new String[argList.size()];
    argList.toArray(args);

    return suite(args);
  }

  public static junit.framework.TestSuite suite(String[] args)
  {
    junit.framework.TestSuite suite = new junit.framework.TestSuite();
    int first = 0;
    compare = true;
    if ((args.length > 0) && args[0].equals("-nc"))
    {
      compare = false;
      first = 1;
    }

    if (args.length - first > 0)
    {
      for (int i = first; i < args.length; i++)
      {
        addTests(suite, args[i].toUpperCase(), args[i].toLowerCase());
      }
    }
    else
    {
      addTests(suite, "CGM", "cgm");
      addTests(suite, "EMF", "emf");
      addTests(suite, "GIF", "gif");
      addTests(suite, "JPG", "jpg");
      addTests(suite, "PDF", "pdf");
      addTests(suite, "PNG", "png");
      addTests(suite, "PS", "ps");
      addTests(suite, "SVG", "svg");
      addTests(suite, "SWF", "swf");
      addTests(suite, "JAVA", "java");
    }
    return suite;
  }

  public static void main(String[] args)
  {
    UniquePrintStream stderr = new UniquePrintStream(System.err);
    System.setErr(stderr);
    junit.textui.TestRunner.run(suite(args));
    stderr.finish();
  }
}

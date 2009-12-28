package org.freehep.util;

import java.io.*;
import java.util.zip.*;

import junit.framework.*;

/**
 * Additional assert methods to the junit.framework.Assert class.
 * 
 * @author Mark Donszelmann
 * @version $Id: Assert.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class Assert extends junit.framework.Assert
{

  /**
   * static class only
   */
  protected Assert()
  {
  }

  /**
   * Compares to files. The files may be gzipped, and will then be uncompressed
   * on the fly.
   * 
   * @param expected
   *          reference file
   * @param actual
   *          file to be tested
   * @param isBinary
   *          when true will do byte-by-byte comparison rather than line by
   *          line.
   * @throws FileNotFoundException
   *           if one of the files cannot be found.
   * @throws IOException
   *           if one of the files cannot read.
   * @throws AssertionFailedError
   *           if the files are not equal.
   */
  public static void assertEquals(File expected, File actual, boolean isBinary) throws FileNotFoundException,
      IOException
  {
    InputStream ref = new BufferedInputStream(new FileInputStream(expected));
    if (expected.getPath().toLowerCase().endsWith(".gz"))
    {
      ref = new GZIPInputStream(ref);
    }

    InputStream tst = new BufferedInputStream(new FileInputStream(actual));
    if (actual.getPath().toLowerCase().endsWith(".gz"))
    {
      tst = new GZIPInputStream(tst);
    }

    int diff;
    if (isBinary)
    {
      diff = diff(ref, tst);
      if (diff >= 0)
        throw new AssertionFailedError("File " + actual + ": comparison failed at offset " + diff);
    }
    else
    {
      diff = diff(new BufferedReader(new InputStreamReader(ref)), new BufferedReader(new InputStreamReader(tst)));
      if (diff >= 0)
        throw new AssertionFailedError("File " + actual + ": comparison failed at line " + diff);
    }
  }


  private static int diff(InputStream ref, InputStream tst) throws IOException
  {
    int bRef, bTst;
    int i = 0;
    do
    {
      bRef = ref.read();
      bTst = tst.read();
      i++;
    }
    while ((bRef >= 0) && (bTst >= 0) && (bRef == bTst));
    ref.close();
    tst.close();
    return (bRef == bTst) ? -1 : i - 1;
  }

  private static int diff(BufferedReader ref, BufferedReader tst) throws IOException
  {
    String bRef, bTst;
    int i = 1;
    do
    {
      bRef = ref.readLine();
      bTst = tst.readLine();
      i++;
    }
    while ((bRef != null) && (bTst != null) && (bRef.equals(bTst)));
    ref.close();
    tst.close();

    return ((bRef == null) && (bTst == null)) ? -1 : i - 1;
  }
}

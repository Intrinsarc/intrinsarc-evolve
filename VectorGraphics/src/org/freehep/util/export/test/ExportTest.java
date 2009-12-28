package org.freehep.util.export.test;

import jas.hist.*;

import java.io.*;
import java.util.*;

import javax.swing.*;

import org.freehep.util.export.*;


class ExportTest extends JFrame implements Runnable
{
  private JASHist plot;

  public ExportTest() throws Exception
  {
    super("Export Test");
    setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);

    InputStream in = getClass().getResourceAsStream("ExportTest.plotml");
    XMLHistBuilder xhb = new XMLHistBuilder(new InputStreamReader(in), "ExportTest.plotml");
    plot = xhb.getSoloPlot();
    plot.setAllowUserInteraction(false);
    getContentPane().add(plot);
  }

  public static void main(String[] args) throws Exception
  {
    ExportTest xhv = new ExportTest();
    xhv.pack();
    xhv.show();
    Thread.currentThread().sleep(500);
    SwingUtilities.invokeAndWait(xhv);
    System.exit(0);
  }

  public void run()
  {
    try
    {
      BufferedReader control = new BufferedReader(new InputStreamReader(getClass()
          .getResourceAsStream("ExportTest.ini")));
      for (;;)
      {
        String line = control.readLine();
        if (line == null)
          break;
        if (line.startsWith("#"))
          continue;

        StringTokenizer st = new StringTokenizer(line, ",=");
        String title = st.nextToken().trim();
        String file = st.nextToken().trim();
        String klass = st.nextToken().trim();
        Properties props = new Properties();
        while (st.hasMoreTokens())
        {
          String key = st.nextToken().trim();
          if (!st.hasMoreTokens())
            break;

          String value = st.nextToken().trim();
          props.setProperty(key, value);
        }
        test(title, file, klass, props);
      }
      control.close();
    }
    catch (Exception x)
    {
      x.printStackTrace();
    }
  }

  private void test(String title, String file, String exported, Properties options) throws Exception
  {
    Collection c = ExportFileType.getExportFileTypes(exported);
    if ((c == null) || c.isEmpty())
    {
      System.out.println("No driver for \"" + exported + "\" found");
    }
    else
    {
      ExportFileType type = (ExportFileType) c.iterator().next();

      File f = new File(file);
      System.out.println(title + " running...");

      long start = System.currentTimeMillis();
      type.exportToFile(f, plot, this, options, getName());

      long stop = System.currentTimeMillis();
      System.out.println(title + " done, time=" + (stop - start) + "ms size=" + f.length());
    }
  }
}
